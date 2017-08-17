package cn.sh.ideal.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.SysRole;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.model.UserInfoList;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.realm.ShiroDbRealm;
import cn.sh.ideal.service.ISysRoleService;
import cn.sh.ideal.service.IUserInfoService;
import cn.sh.ideal.util.CipherUtil;
import cn.sh.ideal.util.ConfigProperties;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.DesUtil;
import cn.sh.ideal.util.LogUitls;
import cn.sh.ideal.util.ParseExcelUtil;
import cn.sh.ideal.util.PoiExcelUtil;

import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/controller/user")
public class UserController {
	private static Logger log = Logger.getLogger(UserController.class);
	@Resource
	private IUserInfoService userInfoService;
	@Resource
	private ISysRoleService sysRoleService;
    @Autowired
	private ShiroDbRealm shiroDbRealm;
	
	@Log(methodname = "toLogin", modulename = "系统登陆", funcname = "用户登陆", description = "用户登陆", code = "ZJ")
	@RequestMapping(value = "/toLogin", method = RequestMethod.GET)
	public String toLogin() {
		return "login";
	}

	@Log(methodname = "login", modulename = "系统登陆", funcname = "用户登陆", description = "用户【{0}】登陆,{1}", code = "ZJ")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		// 设置session
		HttpSession session = request.getSession();
		// 判断验证码
		String authCode = "";
		if (session.getAttribute(Constans.AUTH_CODE_IN_SESSION_KEY) != null) {
			authCode = session.getAttribute(Constans.AUTH_CODE_IN_SESSION_KEY)
					.toString();
		}
		String workId = (request.getParameter("workId") == null ? "" : request
				.getParameter("workId").trim());
		String passWord = (request.getParameter("passWD") == null ? ""
				: request.getParameter("passWD").trim());
		String code = (request.getParameter("authCode") == null ? "" : request
				.getParameter("authCode").trim());
		if ("".equals(authCode)) {
			return "login";
		}

		if (!code.equals(authCode)) {
			request.setAttribute("workId", workId);
			request.setAttribute("passWD", passWord);
			request.setAttribute("login_msg", "验证码不正确");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { workId, "验证码不正确" }));
			return "login";
		}

		if (workId.equals("") || passWord.equals("")) {
			request.setAttribute("login_msg", "工号或密码不能为空");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { workId, "工号或密码不能为空" }));
			return "login";
		} else {
			UserInfo user = new UserInfo();
			user.setWorkId(workId);
			List<UserInfo> userInfo = userInfoService.getUserInfo(user);
			if (userInfo == null || userInfo.size() == 0) {
				request.setAttribute("workId", workId);
				request.setAttribute("login_msg", "工号或密码不正确");
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
						.setObjects(new Object[] { workId, "工号不存在" }));
				return "login";
			} else if (userInfo.size() == 1) {
				try {
					if (!passWord.equals(DesUtil.decrypt(userInfo.get(0)
							.getPasswd(),
							"" + ConfigProperties.getProperty("DESKEY")))) {
						request.setAttribute("workId", workId);
						request.setAttribute("login_msg", "工号或密码不正确");
						LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
								.setObjects(new Object[] { workId, "密码错误" }));
						return "login";
					} else {
						// MD5加密
						String password = CipherUtil
								.generatePassword(DesUtil.encrypt(passWord,
										ConfigProperties.getProperty("DESKEY")));
						UsernamePasswordToken token = new UsernamePasswordToken(
								workId, password);
						Subject currentUser = SecurityUtils.getSubject();
						request.setAttribute("userName", userInfo.get(0)
								.getUserName());
						request.setAttribute("date",
								DateUtil.getDateTimeStr(new Date()));

						session.setAttribute(Constans.USER_INFO_WORKID, workId);
						session.setAttribute(Constans.USER_INFO,
								userInfo.get(0));
						session.setAttribute(Constans.USER_INFO_USERID,
								userInfo.get(0).getUserId());
						session.setAttribute(Constans.ROLE_FLAG, userInfo
								.get(0).getRole_flag());
						// 根据用户id获取roleFlag 存放至session
						SysRole role = sysRoleService
								.selectRoleByUserId(userInfo.get(0).getUserId());
						if (role != null) {
							session.setAttribute(Constans.ROLE_FLAG,
									role.getRoleFlag());
						}
						LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
								.setObjects(new Object[] { workId, "登陆成功" }));
						if (!currentUser.isAuthenticated()) {
							token.setRememberMe(true);
							currentUser.login(token);
						}
						return "index";
					}
				} catch (Exception e) {
					log.error("系统登陆异常：" + e.getMessage());
					LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
							.setObjects(new Object[] { workId, "登陆失败" }));
				}

			}
			request.setAttribute("workId", workId);
			request.setAttribute("login_msg", "工号或密码不正确");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { workId, "工号重复" }));
			return "login";

		}
	}

	@RequestMapping(value = "/loginOut", method = RequestMethod.GET)
	public void loginOut(HttpServletRequest request,
			HttpServletResponse response) {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
	}
	
	
	@RequestMapping(value = "/toUserList", method = RequestMethod.GET)
	public String toUserList() {
		return "system/userInfoList";
	}
	
	
    /*******
     * 员工信息查询列表
     * @author niewenqiang
     * @date 2017-5-10
     * *******/
	@RequiresPermissions("user:query")
	@RequestMapping(value = "/getuserlist")
	public void getUserList(HttpServletRequest request, UserInfo user,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String workId = request.getParameter("workId");
		String userName = request.getParameter("userName");
		String role_flag = request.getParameter("role_flag");
		String groupName = request.getParameter("groupName");
		String smailWorkid = request.getParameter("smailWorkid");
		user.setWorkId(workId);
		user.setUserName(userName);
		user.setRole_flag(role_flag);
		user.setGroupName(groupName);
		user.setSmailWorkid(smailWorkid);
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		List<UserInfoList> userlist = userInfoService.getUserInfoByparam(user);
		if (role_flag == null || "".equals(role_flag)) {
			for (UserInfoList userInfo : userlist) {
				List<UserInfoList> ch = userInfoService.getsmailworid(userInfo);
				userInfo.setChildren(ch);
			}
		}
		int total = userInfoService.countuser(user);
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(userlist).toString() + "}";
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			log.error("获取用户列表异常：" + e.getMessage(),e);
		}
	}
    
	
	
	
	/*******
     * 查询受理员信息
     * @author niewenqiang
     * @date 2017-5-10
     * *******/
	@RequestMapping(value = "/getAcceptoruserlist")
	public void getAcceptorUserList(HttpServletRequest request, UserInfo user,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String workId=request.getParameter("workId");
		String userName=request.getParameter("userName");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		Map<String, String> map=new HashMap<String, String>();
		map.put("workId", workId);
		map.put("userName", userName);
		List<UserInfo> userlist = userInfoService.getAcceptorUserList(map);
		int total = userInfoService.getAcceptorUserListCount(map);
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(userlist).toString() + "}";
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			log.error("获取用户列表异常：" + e.getMessage(),e);
		}
	}
	
	
	
	/******
	 * 员工信息新增操作
	 * @author niewenqiang
	 * @date 2017-5-10
	 * *******/
	@RequiresPermissions("user:add")
	@Log(methodname = "insertUserInfo", modulename = "员工信息维护", funcname = "系统新建员工", description = "新增了一个用户,工号为{0},{1}", code = "SYS")
	@RequestMapping(value = "/insertUserInfo")
	public void insertUserInfo(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String flag = "";
		String userName = request.getParameter("userName");
		String workId = request.getParameter("workId");
		String groupName = request.getParameter("groupName");
		String job = request.getParameter("job");
		String passwd = request.getParameter("passwd");
		String role_id = request.getParameter("role_id");
		String theirCenter = request.getParameter("theirCenter"); // 获取所属中心
		UserInfo user = new UserInfo();
		
		String userid = request.getAttribute("userid").toString();
		user.setWorkId(workId);
		user.setUserName(userName);
		user.setGroupName(groupName);
		user.setTheirCenter(theirCenter);
		user.setJob(job);
		user.setDataFrom("2");
		user.setPid(1);
		try {
			passwd = DesUtil.encrypt(passwd,
					ConfigProperties.getProperty("DESKEY"));
		} catch (Exception e1) {
			log.error(e1);
		}
		user.setPasswd(passwd);
		user.setCreateUserId(Integer.parseInt(userid));
		try {
			UserInfo userinfo = userInfoService.userinfoByworkid(workId);
			if (userinfo != null) {
				flag = "工号重复";
			} else {
				boolean blag = userInfoService.insert(user, role_id);
				if (blag) {
					flag = "添加成功";
				} else {
					flag = "添加失败";
				}

			}
			JSONObject json = new JSONObject();
			json.put("flag", flag);
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { workId,"操作成功" }));
		} catch (IOException e) {
			log.error("用户"+userid+"新增用户异常：" + e.getMessage(),e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] {workId, e.getMessage() }));
		}
	}

   /*******
    * 重置密码
    * @author niewenqiang
    * @date 2017-5-10
    * ******/
	@RequiresPermissions("user:resetpass")
	@Log(methodname = "resetPasswd", modulename = "员工信息维护", funcname = "重置密码", description = "重置了工号为{0}密码,{1}", code = "SYS")
	@RequestMapping(value = "/resetPasswd", method = RequestMethod.POST)
	public void resetPasswd(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String workId = request.getParameter("workId");
		UserInfo user = new UserInfo();
		user.setWorkId(workId);
		String passwd = ConfigProperties.getProperty("resetpasswd");
		try {
			passwd = DesUtil.encrypt(passwd,
					ConfigProperties.getProperty("DESKEY"));
		} catch (Exception e1) {
			log.error(e1);
		}
		user.setPasswd(passwd);
		boolean blag = userInfoService.releasePasswd(user);
		JSONObject json = new JSONObject();
		json.put("blag", blag);
		try {
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] {workId, "操作成功" }));
		} catch (IOException e) {
			log.error("重置工号"+workId+"密码异常：" + e.getMessage(),e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { workId,e.getMessage() }));
		}
	}
    /******
     * 删除用户操作
     * @author niewenqiang
     * @date 2017-5-10
     * ******/
	@Log(methodname = "deluserInfo", modulename = "员工信息维护", funcname = "删除用户", description = "删除了工号为{0}用户,{1}", code = "SYS")
	@RequestMapping(value = "/deluserInfo", method = RequestMethod.POST)
	public void deluserInfo(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String workId = request.getParameter("workId");
		String userId = request.getParameter("userId");
		vSysuser sysuser=(vSysuser) request.getAttribute("User");
		String userid = sysuser.getUserId().toString();
		boolean blag = userInfoService.deluserInfo(workId,
				Integer.parseInt(userId));
		JSONObject json = new JSONObject();
		json.put("blag", blag);
		try {
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { workId,"操作成功" }));
		} catch (IOException e) {
			log.error("用户"+userid+"删除工号"+workId+"异常：" + e.getMessage(),e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { workId,e.getMessage() }));
		}

	}

    /*****
     * 跳转到修改用户页面
     * ****/
	@RequiresPermissions("user:upd")
	@RequestMapping(value = "/userInfobyworid", method = RequestMethod.GET)
	public String userInfobyworid(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String workId = request.getParameter("workId");
		UserInfo userinfo = userInfoService.queryUserByworkId(workId);
		request.setAttribute("userinfo", userinfo);
		return "system/upd_userInfo";
	}

	/*******
	 * 修改用户操作
	 * @author niewenqiang
	 * @date 2017-5-10
	 * *******/
	@RequiresPermissions("user:upd")
	@Log(methodname = "upduserInfo", modulename = "员工信息维护", funcname = "修改用户信息", description = "修改了工号为{0}的用户信息,{1}", code = "SYS")
	@RequestMapping(value = "/upduserInfo", method = RequestMethod.POST)
	public void upduserInfo(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String flag = "";
		String userName = request.getParameter("userName");
		String workId = request.getParameter("workId");
		String userId = request.getParameter("userId");
		String groupName = request.getParameter("groupName");
		String job = request.getParameter("job");
		String theirCenter = request.getParameter("theirCenter"); // 获取所属中心
		UserInfo user = new UserInfo();
		String role_id = request.getParameter("role_id");
		vSysuser sysuser=(vSysuser) request.getAttribute("User");
		String userid = sysuser.getUserId().toString();
		user.setTheirCenter(theirCenter);
		user.setWorkId(workId);
		user.setUserName(userName);
		user.setGroupName(groupName);
		user.setJob(job);
		user.setUserId(Integer.parseInt(userId));
		user.setUpdateUserId(Integer.parseInt(userid));

		UserInfo userinfo = userInfoService.queryUserById(Integer
				.parseInt(userId));
		try {
			UserInfo userinfo2 = userInfoService.queryUserByworkId(workId);
			if (!workId.equals(userinfo.getWorkId()) && userinfo2 != null) {
				flag = "工号重复";
			} else {

				boolean bl = userInfoService.updateUserInfo(user, role_id);
				if (bl) {
					flag = "修改成功";

				} else {
					flag = "修改失败";
				}

			}
			JSONObject json = new JSONObject();
			json.put("flag", flag);

			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { workId,"操作成功" }));
		} catch (IOException e) {
			log.error("用户"+userid+"修改工号"+workId+"信息异常：" + e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] {workId,"操作失败"+e.getMessage() }));
		}
	}

	/*****
	 * 角色分配
	 * @author niewenqiang
	 * @date 2017-5-10
	 * ******/
	@RequestMapping(value = "/listRole", method = RequestMethod.GET)
	public String listRole(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		try {
			String tz = request.getParameter("tz");
			request.setAttribute("userId", request.getParameter("userID"));
			request.setAttribute("role_id", request.getParameter("role_id"));
			if ("add".equals(tz)) {
				return "system/edit_userInfo";
			} else {
				return "system/roleInfoChose";
			}
		} catch (Exception e) {
			log.error("获取角色列表异常：" + e.getMessage());
		}
		return "";
	}

	@RequiresPermissions("user:tempdown")
	@RequestMapping(value = "/toimportUser", method = RequestMethod.GET)
	public String toimportUser(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		return "system/userInfo_import";
			
	}
	
	/********
	 * 分配角色
	 * @author niewenqiang
	 * @date 2017-5-11
	 * *********/
	@RequiresPermissions("user:rolefp")
	@Log(methodname = "updRole", modulename = "员工信息维护", funcname = "角色分配", description = "为用户{0}分配了角色,角色ID为{1}{2}", code = "SYS")
	@RequestMapping(value = "/updRole", method = RequestMethod.POST)
	public void updRole(HttpServletRequest request,
			HttpServletResponse response, UserInfo user) {
		response.setContentType("text/html;charset=utf-8");
		String userId = request.getParameter("userId");
		String role_id = request.getParameter("roleid");
		vSysuser sysuser=(vSysuser) request.getAttribute("User");
		String userid = sysuser.getUserId().toString();
		user.setUserId(Integer.parseInt(userId));
		user.setUpdateUserId(Integer.parseInt(userid));
		user.setCreateUserId(Integer.parseInt(userid));
		boolean blag = userInfoService.updateuserRolebyuserid(user, role_id);
		JSONObject json = new JSONObject();
		json.put("blag", blag);
		try {
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { userId,role_id,"操作成功" }));
		} catch (IOException e) {
			log.error("操作人"+userid+"为用户"+userId+"分配角色异常：" + e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] {userId,role_id,"操作失败"+e.getMessage() }));
		}
	}

	/*******
	 * 员工导入模板下载
	 * @author niewenqiang
	 * *******/
	@RequiresPermissions("user:tempdown")
	@Log(methodname = "downTemplate", modulename = "员工信息维护", funcname = "模板下载", description = "模板下载,{0}", code = "SYS")
	@RequestMapping(value = "/downTemplate")
	public void downTemplate(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		// 生成提示信息，
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			// 进行转码，使其支持中文文件名
			codedFileName = java.net.URLEncoder.encode("员工信息模板", "UTF-8");
			codedFileName = codedFileName
					+ "_" + DateUtil.getCurDateStr("yyyyMMddHHmmss");
			response.setHeader("content-disposition", "attachment;filename="
					+ codedFileName + ".xls");
			fOut = response.getOutputStream();
			 //获取模板
			String readpath = request.getSession().getServletContext().getRealPath("page/template/员工信息模板.xls");
			List<String> strList=new ArrayList<String>();
			List<SysRole> sysRole=sysRoleService.queryRoleByCode("SYS"); //获取系统管理下的角色
			List<SysRole> ksRole=sysRoleService.queryRoleByCode("KS"); //获取考试系统下的角色
			List<SysRole> zjRole=sysRoleService.queryRoleByCode("ZJ"); //获取质检系统下的角色
			
			strList.add(0, strRole(sysRole));//拼接字符串
			strList.add(1, strRole(zjRole));//拼接字符串
			strList.add(2, strRole(ksRole));//拼接字符串
			PoiExcelUtil.poiCreateExcelByTemplate2003(strList,fOut,readpath,2);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] {"操作成功" }));
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			log.error("导出员工信息模板异常:"+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] {"操作失败"+e.getMessage() }));
		} finally {
			try {
				if(fOut!=null){
				fOut.flush();
				fOut.close();
				}
			} catch (IOException e) {
			}
		}
	
		
	}

	public String strRole(List<SysRole> sysrole) {
		String str = "";
		for (int i = 0; i < sysrole.size(); i++) {
			SysRole role = sysrole.get(i);
			str += role.getRoleId() + ":" + role.getRoleName() + " ";
		}
		return str;
	}
    @RequiresPermissions("user:infoimport")
	@Log(methodname = "importUser", modulename = "员工信息维护", funcname = "员工信息导入", description = "员工信息导入,{0}", code = "SYS")
	@RequestMapping(value = "/importUser", method = RequestMethod.POST)
	public void importUser(
			@RequestParam(value = "myFile", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			String basePath = request.getSession().getServletContext()
					.getRealPath("page/template/user")
					+ File.separatorChar; // 错误模板excel路径
			String fileName = file.getOriginalFilename();

			// 从第3行开始读取excel内容
			int readStratRow = 6;
			List<List<Object>> excelList = new ArrayList<List<Object>>();
			List<List<Object>> titlelList = new ArrayList<List<Object>>();
			ParseExcelUtil parseExcelUtil = new ParseExcelUtil();
			if (fileName.toLowerCase().endsWith(".xls")) {
				excelList = parseExcelUtil.readExcel(file.getInputStream(),
						readStratRow, true, false);
				titlelList = parseExcelUtil.readExcel(file.getInputStream(), 5,
						true, false);
			}
			if (fileName.toLowerCase().endsWith(".xlsx")) {
				excelList = parseExcelUtil.readExcel(file.getInputStream(), readStratRow, false,
						true);
				titlelList = parseExcelUtil.readExcel(file.getInputStream(), 1, false,
						true);
			}

			Map<Object, Object> paramMap = new HashMap<Object, Object>();
			String fileNamePath = basePath + "user_error" + ".xls";
			String currentDate = DateUtil.getCurDateStr("yyyyMMddHHmmss");
			String errorFileName = "user_error" + currentDate + ".xls";
			paramMap.put("basePath", basePath);
			paramMap.put("errorFileName", errorFileName); // 错误文件名
			paramMap.put("errorFileNamePath", fileNamePath); // 错误模板文件路径
			Map<String, Object> map = userInfoService.updateimportUser(excelList,
					paramMap, titlelList);
			if (Integer.parseInt((String.valueOf(map.get("errorCnt")))) > 0) {
				map.put("errorFileName", errorFileName);
			}
			log.info("用户导入后返回信息：" + JSONObject.fromObject(map));
			pw.print(JSONObject.fromObject(map));
			pw.flush();
			pw.close();
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] {"操作成功"}));
		} catch (Exception e) {
			log.error("员工导入出现异常"+e.getMessage(), e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] {"操作失败"+e.getMessage() }));
		} finally {
			if (pw != null)
				pw.close();
		}

	}

	@Log(methodname = "updpasswd", modulename = "员工信息维护", funcname = "密码修改", description = "{0}修改了密码,{1}", code = "SYS")
	@RequestMapping(value = "/updpasswd", method = RequestMethod.POST)
	public void updpasswd(HttpServletResponse response,
			HttpServletRequest request) {
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		String workId=request.getAttribute(Constans.USER_INFO_WORKID).toString();
		String passwd = request.getParameter("passwd");
		String newpasswd = request.getParameter("newpasswd");
		try {
			passwd = DesUtil.encrypt(passwd,
					ConfigProperties.getProperty("DESKEY"));
		} catch (Exception e) {
			log.error(e);
		}
		UserInfo userinfo = userInfoService.queryUserByworkId(workId);
		String flag = "";
		if (!userinfo.getPasswd().equals(passwd)) {
			flag = "原密码输入错误，请重新输入";
		} else {
			try {
				newpasswd = DesUtil.encrypt(newpasswd,
						ConfigProperties.getProperty("DESKEY"));
			} catch (Exception e) {
				log.error(e);
			}
			UserInfo user = new UserInfo();
			user.setUserId(Integer.parseInt(request
					.getAttribute(Constans.USER_INFO_USERID).toString()));
			user.setPasswd(newpasswd);
			boolean bl = userInfoService.updUserInfo(user);
			if (bl) {
				flag = "修改成功";
			} else {
				flag = "修改失败";
			}
		}
		JSONObject json = new JSONObject();
		json.put("flag", flag);
		try {
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] {workId, "操作成功" }));
		} catch (IOException e) {
			log.error("工号为"+workId+"修改密码异常：" + e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] {workId, "操作失败"+e.getMessage() }));
		}

	}

	@RequestMapping(value = "/listgruoupName", method = RequestMethod.POST)
	public void listgruoupName(HttpServletResponse response,
			HttpServletRequest request) {
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		List<UserInfo> listgroup = userInfoService.selectgroupName();
		JSONObject json = new JSONObject();
		json.put("listgroup", listgroup);
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			log.error("获取组室列表异常：" + e.getMessage());
		}

	}
	
	@RequestMapping(value="/listagentWorkid",method=RequestMethod.POST)
	public void listagentWorkid(HttpServletResponse response,HttpServletRequest request, UserInfo user){
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		if(user!=null){
		user.setSmailWorkid(user.getWorkId());
		}
		List<UserInfo> userlist = userInfoService.getagentWorkid(user);
		int total=userInfoService.getagentWorkidsum(user);
		JSONObject json=new JSONObject();
		json.put("Total", total);
		json.put("Rows", userlist);
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			log.error("获取组室列表异常："+e.getMessage());
		}
	
		
		
	}
	
}
