package cn.sh.ideal.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.sh.ideal.dao.IOperateLogDao;
import cn.sh.ideal.model.AgentWorkPaper;
import cn.sh.ideal.model.Function;
import cn.sh.ideal.model.OperateLog;
import cn.sh.ideal.model.SysRole;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.redis.RedisClientTemplate;
import cn.sh.ideal.service.IFunctionService;
import cn.sh.ideal.service.ISysRoleService;
import cn.sh.ideal.service.IUserService;
import cn.sh.ideal.util.ConfigProperties;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.DES3Util;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.DesUtil;
import cn.sh.ideal.util.RSAUtil;
import cn.sh.ideal.util.WebUtil;

/***
 * @author niewq 用户管理操作类
 * ***/
@Controller
@RequestMapping("/controller/user")
public class UserController {
	private static Logger log = Logger.getLogger(UserController.class);
	@Autowired
	private IUserService userService;
	@Resource
	private ISysRoleService roleService;
	@Resource
	private IFunctionService functionService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Resource
	private IOperateLogDao operateDao;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/toIndex", method = RequestMethod.GET)
	public String toIndex(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		UserInfo user = userService.getRedisUser(request);
		String indexUrl = "";
		if (null != user) {
			String token = userService.gettoken(request);			
			int userId = user.getUserId();
			// 获取当前登录用户所属的角色
			List<SysRole> listRole = roleService.queryRoleByUserId(userId);
			String roleName = "";
			for (SysRole sysRole : listRole) {
				roleName += sysRole.getRoleName() + ",";
			}
			if (!roleName.equals("")) {
				roleName = roleName.substring(0, roleName.length() - 1); // 截取最后一个字符串,
			}
			request.setAttribute("roleName", roleName);
			// 显示当前登录角色待处理的反馈单 默认显示5条
			request.setAttribute("user", user);
			// 加载首页显示的系统
			List<Function> funSys = functionService
					.querySysRoleByUserId(userId);
			request.setAttribute("funSys", funSys);
			request.setAttribute("userId", userId);
			request.setAttribute("desToken",
					DES3Util.encryptStringURLEncoder(token));
			// 获取地址栏访问的地址IP
			String ip = request.getServerName();
			// 判断当前访问的地址是ENI地址还是内网地址 eni10开头 内网172开头
			String isEni = "false";
			if (ip.contains("10.4.15")) { // ENI地址
				isEni = "true";
			} else if (ip.contains("172.16.67")) { // 内网地址
				isEni = "false";
			}
			indexUrl = "index";
			request.setAttribute("isEni", isEni);
		} else {
			indexUrl = "login";
		}
		
		return indexUrl;
	}

	public String rsaDecrypt(String value,RSAPrivateKey key) { 
		    String str="";
	        try {
	        	byte[] en_result = hexStringToBytes(value);     
	        	RSAUtil util=new RSAUtil();
		        byte[] de_result = util.decrypt(key,    
		                en_result);     
		        StringBuffer sb = new StringBuffer();    
		        sb.append(new String(de_result));    
		        
		        str = sb.reverse().toString();   
		        str = str.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
		        str = str.replaceAll("\\+", "%2B");
		        str = URLDecoder.decode(str,"UTF-8");  
			} catch (Exception e) {
				log.error("解密RSA出现错误，异常为"+e.getMessage(),e);
			}
	        return str;
		
	}
	
	
	
	/***
	 * @author niewenqiang 用户登录验证 2017-4-11
	 * ***/
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String userLogin(HttpServletRequest request,
			HttpServletResponse response) {
		String result = "";
		String workId = request.getParameter(Constans.WORKID) == null ? ""
				: request.getParameter(Constans.WORKID).trim(); // 获取工号
		// RSAPrivateKey privateKey=(RSAPrivateKey)request.getSession().getAttribute("privateKey");
		 String workNo="";
		try {
			String passWord = request.getParameter(Constans.PASS_WD) == null ? ""
					: request.getParameter(Constans.PASS_WD).trim(); // 密码
		//	workNo=rsaDecrypt(workId,privateKey); //解密
			workNo=workId;			
			if(redisClientTemplate.isexist(workNo)){ //判断redis中是否存在此工号
				request.setAttribute(Constans.WORKID, workNo);
				request.setAttribute("title", "此工号已登录,不能重复登录");
				result = Constans.LOGIN;
			}else{
			String rsaPW=passWord;
			//String rsaPW=rsaDecrypt(passWord,privateKey); //解密
			UserInfo user = new UserInfo();
			user.setWorkId(workNo);
			UserInfo userInfo = userService.queryUserByWork(user); // 根据工号查询该用户信息
			if (null == userInfo) {
				request.setAttribute(Constans.WORKID, workNo);
				request.setAttribute("title", "工号错误");
				result = Constans.LOGIN;
			} else {
				// 判断查询到的工号密码是否与数据库中的一样
				String pwd=DesUtil.decrypt(userInfo.getPasswd(),
						Constans.DESKEY);
				if (rsaPW.equals(pwd)) {
					// 生成token的值
					//String token = UUID.randomUUID().toString()
						//	.replace("-", "");
					String token=workNo;
					log.info("统一登录生成的token:"+token);
					Cookie cookietoken = new Cookie("cookieToken", token);
					cookietoken.setPath("/");
					response.addCookie(cookietoken); // 将token值放于cookie中
					List<SysRole> listRole = roleService
							.queryRoleByUserId(userInfo.getUserId());
					String roleFlag = "";
					for (SysRole sysRole : listRole) {
						roleFlag += sysRole.getRoleFlag() + ",";
					}
					// 获取该用户按钮权限
					String zjlistFun = "";
					// 判断用户是否拥有该系统的角色 如果有则在后面加, 主要是为了下面三个系统按钮级权限数据拼接
					List<Function> zjFun = functionService.getPermissionByUser(
							userInfo.getUserId(), "zj");
					for (Function function : zjFun) {
						zjlistFun += function.getPermission() + ",";
					}
					String kslistFun = "";
					List<Function> ksFun = functionService.getPermissionByUser(
							userInfo.getUserId(), "ks");
					for (Function function : ksFun) {
						kslistFun += function.getPermission() + ",";
					}
					String syslistFun = "";
					List<Function> sysFun = functionService
							.getPermissionByUser(userInfo.getUserId(), "sys");
					for (Function function : sysFun) {
						syslistFun += function.getPermission() + ",";
					}
					
					String sysreportFun = "";
					List<Function> reportFun = functionService
							.getReportRoleByUser(userInfo.getUserId());
					for (Function function : reportFun) {
						sysreportFun += function.getPermission() + ",";
					}
					StringBuilder listFun = new StringBuilder(zjlistFun)
							.append(syslistFun);
					JSONObject object = new JSONObject();
					object.put("userId", userInfo.getUserId());
					object.put("listFun", listFun.toString());
					object.put("listKsFun", kslistFun);
					object.put("listReportFun", sysreportFun);
					object.put("workId", userInfo.getWorkId());
					object.put("userName", userInfo.getUserName());
					object.put("roleFlag", roleFlag);
					redisClientTemplate.set(token, object, Integer
							.parseInt(ConfigProperties
									.getProperty("redisTimeOut")));
					OperateLog log = new OperateLog(userInfo.getUserId(),
							userInfo.getUserName(),
							DateUtil.getDateTimeStr(new Date()),
							WebUtil.getIpAddr(request), "系统登录", "用户登陆", "用户["
									+ workNo + "]登陆,登陆成功", "", "login", "SYS");
					operateDao.insert(log);
					result = "redirect:toIndex.do";
				} else {
					request.setAttribute(Constans.WORKID, workNo);
					request.setAttribute("title", "密码错误");
					result = Constans.LOGIN;
				}
			}
			}
		} catch (Exception e) {
			result = Constans.LOGIN;
			log.error("工号" + workNo + "登录失败,失败原因为:" + e.getMessage(), e);
		}
		return result;
	}
	
	
	
	@RequestMapping(value = "/loginOut", method = RequestMethod.POST)
	public void loginOut(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(Constans.CONTENTCHA);
		response.setCharacterEncoding(Constans.ENCODING);
		String token = userService.gettoken(request);
		UserInfo user = userService.getRedisUser(request);
		try {
			// 调用子系统的退出方法
			if (redisClientTemplate.isexist(token)) {
				redisClientTemplate.delete(token);
			}
			Cookie newCookie = new Cookie("cookieToken", null); // 假如要删除名称的Cookie
			newCookie.setMaxAge(0); // 立即删除型
			OperateLog log = new OperateLog(user.getUserId(),
					user.getUserName(), DateUtil.getDateTimeStr(new Date()),
					WebUtil.getIpAddr(request), "系统退出", "用户退出", "用户["
							+ user.getWorkId() + "]退出登陆,退出成功", "", "loginOut",
					"SYS");
			operateDao.insert(log);
		} catch (Exception e) {
			log.error("退出登陆异常：" + e.getMessage(), e);
		
		}/*
		 * finally{ if(redisClientTemplate.isexist(token)){
		 * redisClientTemplate.delete(token); } Cookie newCookie=new
		 * Cookie("cookieToken",null); //假如要删除名称的Cookie newCookie.setMaxAge(0);
		 * //立即删除型 }
		 */

	}

	/******
	 * 加载首页数据 反馈单 公告信息
	 * ******/
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/getAgentNotice", method = RequestMethod.POST)
	public void getAgentNotice(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(Constans.CONTENTCHA);
		response.setCharacterEncoding(Constans.ENCODING);
		UserInfo user = userService.getRedisUser(request);
		int userId = user.getUserId();
		// 获取当前登录用户所属的角色
		List<SysRole> listRole = roleService.queryRoleByUserId(userId);
		List<AgentWorkPaper> agentPaperList = new ArrayList();
		String roleFlag = "";
		for (SysRole sysRole : listRole) {
			// 加载个人反馈单查询列表
			String role = sysRole.getRoleFlag();
			if (role.equals("role_3")) {
				roleFlag = role;
				agentPaperList = userService
						.queryAgentWorkpaperByQcUser(userId);
			} else if (role.equals("role_1")) {
				roleFlag = role;
				agentPaperList = userService
						.queryAgentWorkpaperByAgentUser(userId);
			} else if (role.equals("role_2")) {
				roleFlag = role;
				agentPaperList = userService.queryAgentWorkpaperByBZ(userId);
			}
		}

		// 显示当前系统公告 默认显示5条
		List noticeList = userService.queryNotice();
		try {
			JSONObject object = new JSONObject();
			object.put("noticeList", noticeList);
			object.put("agentPaperList", agentPaperList);
			object.put("roleFlag", roleFlag);
			response.getWriter().print(object.toString());
		} catch (Exception e) {
			log.error("加载首页数据反馈单公告信息异常：" + e.getMessage(), e);
		}

	}

	
	   /**  
     * 16进制 To byte[]  
     * @param hexString  
     * @return byte[]  
     */  
    public static byte[] hexStringToBytes(String hexString) {  
        if (hexString == null || hexString.equals("")) {  
            return null;  
        }  
        hexString = hexString.toUpperCase();  
        int length = hexString.length() / 2;  
        char[] hexChars = hexString.toCharArray();  
        byte[] d = new byte[length];  
        for (int i = 0; i < length; i++) {  
            int pos = i * 2;  
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1])& 0xFF);  
        }  
        return d;  
    }  
    /**  
     * Convert char to byte  
     * @param c char  
     * @return byte  
     */  
     private static byte charToByte(char c) {  
        return (byte) "0123456789ABCDEF".indexOf(c);  
    }  
}
