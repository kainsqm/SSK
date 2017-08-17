package cn.sh.ideal.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.Function;
import cn.sh.ideal.model.OperateLog;
import cn.sh.ideal.model.SysCode;
import cn.sh.ideal.model.SysRole;
import cn.sh.ideal.model.SysRoleFunction;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.IFunctionService;
import cn.sh.ideal.service.ISysCodeService;
import cn.sh.ideal.service.ISysRoleFunctionService;
import cn.sh.ideal.service.ISysRoleService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.LogUitls;

import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/controller/role")
public class RoleController {
	private static Logger log = Logger.getLogger(RoleController.class);
	@Resource
	private ISysCodeService sysCodeService;
	@Resource
	private ISysRoleService sysRoleService;
	@Resource
	private IFunctionService functionService;
	@Resource
	private ISysRoleFunctionService sysRoleFunctionService;

	/**
	 * 角色查询
	 * 
	 * @param request
	 * @param response
	 * @author lk
	 */

	@RequiresPermissions("role:query")
	@RequestMapping(value = "/getrolelist")
	public void getRoleList(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String rolename = request.getParameter("rolename"); // 角色名称
		String roleflag = request.getParameter("roleflag"); // 角色标识
		String system = request.getParameter("system"); // 系统名称
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		SysRole role = new SysRole();
		role.setRoleFlag(roleflag);
		role.setRoleName(rolename);
		role.setSystem(system);
		List<SysRole> rlist = sysRoleService.queryRoleList(role);
		int count = sysRoleService.queryRoleListCount(role);
		String json = "{\"Total\":" + count + " , \"Rows\":"
				+ JSONArray.fromObject(rlist).toString() + "}";
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			log.error("获取角色列表异常：" + e.getMessage(),e);
		}

	}

	/******
	 * 进入角色查询页面
	 * 
	 * @author niewenqiang
	 * @date 2017-4-24
	 * ******/
	@RequestMapping(value = "/toRolePage", method = RequestMethod.GET)
	public String toRolePage(HttpServletRequest request,
			HttpServletResponse response, OperateLog operatelog) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		List<SysRole> codeList = sysRoleService.queryParentRole(); // 获取配置表中系统列表信息
		request.setAttribute("codeList", codeList);
		return "system/roleInfoList";
	}

	/******
	 * 进入角色新增页面
	 * 
	 * @author niewenqiang
	 * @date 2017-4-24
	 * ******/
	@RequiresPermissions("role:add")
	@RequestMapping(value = "/toAddRolePage", method = RequestMethod.GET)
	public String toAddRolePage(HttpServletRequest request,
			HttpServletResponse response, OperateLog operatelog) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		List<SysRole> codeList = sysRoleService.queryParentRole(); // 获取配置表中系统列表信息
		request.setAttribute("codeList", codeList);
		return "system/add_roleInfo";
	}
	
	
	
	/******
	 * 进入角色修改页面
	 * 
	 * @author niewenqiang
	 * @date 2017-4-24
	 * ******/
	@RequiresPermissions("role:upd")
	@RequestMapping(value = "/toUpdateRolePage", method = RequestMethod.GET)
	public String toUpdateRolePage(HttpServletRequest request,
			HttpServletResponse response, OperateLog operatelog) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		int roleId=Integer.parseInt(request.getParameter("roleId"));
		SysRole role=sysRoleService.selectByPrimaryKey(roleId); //根据roleId查询角色信息详情
		SysRole roleCode=sysRoleService.selectByPrimaryKey(Integer.parseInt(role.getPid())); //获取当前所属系统
		List<SysCode> codeRoleList=sysCodeService.queryChildrenByValue(roleCode.getCode());
		request.setAttribute("role", role);
		request.setAttribute("roleFlag",role.getRoleFlag());
		request.setAttribute("roleCode",roleCode);
		request.setAttribute("codeRoleList",codeRoleList);
		return "system/edit_roleInfo";
	}
	
	
	

	/******
	 * 根据系统标识查询该系统下的所有的角色信息
	 * 
	 * @author niewenqiang
	 * @date 2017-4-24
	 * ******/
	@RequestMapping(value = "/queryRoleByFlag", method = RequestMethod.POST)
	@ResponseBody
	public void queryRoleByFlag(HttpServletRequest request,
			HttpServletResponse response, OperateLog operatelog) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String system = request.getParameter("system");
		List<SysCode> codeList = sysCodeService.queryChildrenByValue(system); // 获取配置表中系统列表信息
		JSONObject object = new JSONObject();
		object.put("rolelist", codeList);
		try {
			response.getWriter().print(object.toString());
		} catch (IOException e) {
			log.error("系统标识查询该系统下的所有的角色信息异常" + e.getMessage(),e);
		}
	}

	/******
	 * 新增/修改角色信息时，验证角色名称是否已存在
	 * 
	 * @author niewenqiang
	 * @date 2017-4-24
	 * ******/
	@RequestMapping(value = "/checkRoleName", method = RequestMethod.POST)
	@ResponseBody
	public void checkRoleName(HttpServletRequest request,
			HttpServletResponse response, OperateLog operatelog) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String type = request.getParameter("type");
		String roleName = request.getParameter("roleName");
		String roleId = request.getParameter("roleId");
		Map<String, String> map = new HashMap<String, String>();
		map.put("roleName", roleName);
		if (type.equals("update")) {
			map.put("roleId", roleId);
		}
		int count = sysRoleService.checkRoleName(map);
		JSONObject object = new JSONObject();
		object.put("count", count);
		try {
			response.getWriter().print(object.toString());
		} catch (IOException e) {
			log.error("验证角色名称是否已存在异常" + e.getMessage(),e);
		}
	}

	/**
	 * 获取角色权限树(新增)
	 * 
	 * @author niewenqiang
	 * @date 2017-4-24
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/getRoletree", method = RequestMethod.POST)
	public void getRoletree(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String code=request.getParameter("system");
		Function functionSys = new Function();
		functionSys.setFunctionType("m");
		functionSys.setCode(code);
		String tr1 = "";
		String tr2 = "";
		String tr3 = "";

		String treeNode = "";
		List<Function> lf = functionService.getFunction(functionSys);
		Function function = new Function();
		for (Function function2 : lf) {
			tr1 = tr1 + "{ id:" + function2.getFunctionId() + ", pId:0,name:\""
					+ function2.getFuntionName() + "\", open:true},";
			function.setFunctionType("p");
			function.setpId(function2.getFunctionId());
			List<Function> lf2 = functionService.getFunction(function);
			for (Function function3 : lf2) {
				function.setFunctionType("b");
				function.setpId(function3.getFunctionId());
				List<Function> lf3 = functionService.getFunction(function);
				tr3 = "";
				for (Function function4 : lf3) {
					tr3 = tr3 + "{btn_name:\"" + function4.getFuntionName()
							+ "\",id:\"" + function4.getFunctionId() + "\"},";
				}
				if (tr3 != null && !tr3.equals("")) {
					tr3 = tr3.substring(0, tr3.length() - 1);
				}
				tr2 = tr2 + "{ id:" + function3.getFunctionId() + ",pId:"
						+ function3.getpId() + ", name:\""
						+ function3.getFuntionName() + "\",btnItems:[" + tr3
						+ "]},";

			}
			treeNode = tr1 + tr2;
		}
		if(treeNode!=""){
			treeNode = treeNode.substring(0, treeNode.length() - 1);
		}
		String result = "{fList:[" + treeNode + "]}";
		net.sf.json.JSONObject jn = new net.sf.json.JSONObject();
		try {
			response.getWriter().print(jn.fromObject(result));
		} catch (IOException e) {
			log.error("获取权限树异常：" + e.getMessage(),e);
		}

	}

	/**
	 * 角色新增
	 * 
	 * @author niewenieqiang
	 * @throws Exception 
	 * @date 2017-4-24
	 */
	@RequiresPermissions("role:add")
	@Log(methodname = "saveRole",modulename="角色管理",funcname="角色信息新增",description="新增了一条角色信息,角色名称[{0}],{1}",code="SYS")
	@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
	public void saveRole(HttpServletRequest request,
			HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String rolename = request.getParameter("rolename"); // 角色名称
		vSysuser user=(vSysuser) request.getAttribute("User");
		int userid = user.getUserId();
		try {
			int blag = 1;
			String system = request.getParameter("system"); // 所属系统
			String roleflag = request.getParameter("roleflag"); // 用户标识
			String treedata = request.getParameter("treedata");
			SysRole role = new SysRole();
			role.setRoleName(rolename);  //角色名称
			role.setRoleFlag(roleflag);  //角色标识
			String pid = String.valueOf(sysRoleService
					.queryRoleIdByCode(system)); // 获取所属系统的ID
			role.setPid(pid);  //父类ID
			role.setCreateUserId(userid);
			sysRoleService.addRole(role, treedata); //新增角色
			net.sf.json.JSONObject jn = new net.sf.json.JSONObject();
			jn.put("blag", blag);
			response.getWriter().print(jn.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { rolename,"操作成功" }));
		} catch (Exception e) {
			log.error("操作人"+userid+"新增角色出现异常：" + e.getMessage(),e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { rolename,"操作失败"+e.getMessage() }));
		}

	}
	
	
	

	/**
	 * 角色修改
	 * @author niewenieqiang
	 * @throws Exception 
	 * @date 2017-4-24
	 */
	 @RequiresPermissions("role:upd")
	 @Log(methodname="updateRole",modulename="角色管理",funcname="角色信息修改",description="修改了角色{0}的信息,{1}",code="SYS")
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	public void updateRole(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String rolename = request.getParameter("rolename"); // 角色名称
		vSysuser user=(vSysuser) request.getAttribute("User");
		int userid = user.getUserId();
		String roleId = request.getParameter("roleId"); // 角色名称
		try {
			int blag = 1;
			String roleflag = request.getParameter("roleflag"); // 用户标识
			String treedata = request.getParameter("treedata");
			SysRole role = new SysRole();
			role.setRoleName(rolename);  //角色名称
			role.setRoleFlag(roleflag);  //角色标识
			role.setRoleId(Integer.parseInt(roleId));
			role.setUpdateUserId(userid);
			sysRoleService.updateRole(role, treedata); //新增角色
			net.sf.json.JSONObject jn = new net.sf.json.JSONObject();
			jn.put("blag", blag);
			response.getWriter().print(jn.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { rolename,"操作成功" }));
		} catch (Exception e) {
			log.error("操作人"+userid+"修改角色roleId"+roleId+"出现异常：" + e.getMessage(),e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] {rolename,"操作失败"+e.getMessage() }));
		}

	}
	
	
	
	
	
	/**
	 * 获取角色权限树(修改)
	 * @author niewenqiang
	 * @date 2017-4-24
	 */
	@RequestMapping(value="/roleTree",method = RequestMethod.POST)
	@ResponseBody
	public void roleTree(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String roleId=request.getParameter("roleId");	
		String code=request.getParameter("system");  //获取系统CODE
		List<SysRoleFunction> lrf=sysRoleFunctionService.selectByRoleId(Integer.parseInt(roleId));
		Function functionSys=new Function();
		Function function=new Function();
		functionSys.setFunctionType("m");
		functionSys.setCode(code);
		String tr1="";
		String tr2="";
		String tr3="";
		String checks="";
		String treeNode="";
		List<Function>	lf=functionService.getFunction(functionSys);		
	for (Function function2 : lf) {
		checks="},";
		for (SysRoleFunction rfn : lrf) {
			if(rfn.getFunctionId().equals(function2.getFunctionId())){
				checks=",checked:true},";
				break;
			}else{
				checks="},";
			}	
		}
		tr1=tr1+"{ id:"+function2.getFunctionId()+", pId:0, name:\""+function2.getFuntionName()+"\", open:true"+checks;
		function.setFunctionType("p");
		function.setpId(function2.getFunctionId());
		List<Function>	lf2=functionService.getFunction(function);
		for (Function function3 : lf2) {		
			tr3="";
			tr3=getTree(function3.getFunctionId(),lrf);	
			checks=getcheck(lrf,function3.getFunctionId());
			
			tr2=tr2+"{ id:"+function3.getFunctionId()+", pId:"+function3.getpId()+", name:\""+function3.getFuntionName()+"\","+checks+"btnItems:["+tr3+"]},";
		}
		treeNode=tr1+tr2;	
	}
	  treeNode=treeNode.substring(0, treeNode.length()-1);
	  String result="{fList:["+treeNode+"]}";
	  net.sf.json.JSONObject jn=new net.sf.json.JSONObject();
	  try {
		  	response.getWriter().print(jn.fromObject(result));
		} catch (IOException e) {
			log.error("获取角色权限树(修改)出现异常："+e.getMessage(),e);
		}	

	}

	
	public String getTree(int pid, List<SysRoleFunction> lrf){
		String tr3="";
		String checks="";
		Function function=new Function();
		function.setFunctionType("b");
		function.setpId(pid);
		List<Function>	lf3=functionService.getFunction(function);
		for (Function function4 : lf3) {
			checks="},";
			for (SysRoleFunction rfn : lrf) {
				if(rfn.getFunctionId().equals(function4.getFunctionId())){
					checks=",check:true},";
					break;
				}else{
					checks="},";
				}	
			}
			tr3=tr3+"{btn_name:\""+function4.getFuntionName()+"\",id:\""+function4.getFunctionId()+"\""+checks;			
		}
		if(!tr3.equals("")){
			tr3=tr3.substring(0, tr3.length()-1);
		  }
		return tr3;
	}
	
	public String getcheck(List<SysRoleFunction> lrf,int functionid){
		
		String checks="";
		for (SysRoleFunction rfn2 : lrf) {
			if(rfn2.getFunctionId()==functionid){
				checks="checked:true,";
				break;
			}else{
				checks="";
			}
		}
		return checks;
	} 
	
	
	
	/**
	 * 角色删除
	 * @author niewenqiang
	 * @date 2017-4-24
	 */
	@RequiresPermissions("role:del")
	@Log(methodname="deleteRole",modulename="角色管理",funcname="角色信息删除",description="删除了一条角色信息,角色名称[{0}],{1}",code="SYS")
	@RequestMapping(value="/deleteRole",method = RequestMethod.POST)
	public void deleteRole(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String roleId=request.getParameter("roleId");
		String rolename=request.getParameter("rolename");
		vSysuser user=(vSysuser) request.getAttribute("User");
		int userid = user.getUserId();
		String flag="";
		int blag=0;
		try {
			int count=sysRoleService.queryRoleIsUsed(Integer.parseInt(roleId));
			if(count>0){
				flag="该角色已经在使用";
			}else{		
				SysRole role=new SysRole();
			    role.setUpdateUserId(userid);
				role.setEnabled("0");
				role.setRoleId(Integer.parseInt(roleId));
				blag=sysRoleService.updateByPrimaryKey(role);
				if(blag>0){
					flag="删除成功";
				}else{
					flag="删除失败";
				}
			}
			 net.sf.json.JSONObject jn=new net.sf.json.JSONObject();
			 jn.put("flag", flag);
			 response.getWriter().print(jn);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{rolename,"操作成功"}));
		} catch ( Exception e) {
			log.error("操作人"+userid+"删除角色roleId"+roleId+"出现异常："+e.getMessage(),e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{rolename,"操作失败"+e.getMessage()}));
		}
	
	}
	
	
	/**
	 * 展示角色列表(树形  修改
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryRole", method = RequestMethod.POST)
	public void getNode(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 获取系统角色信息
		response.setContentType("text/plain;charset=UTF-8");
		List<SysRole> parentList = sysRoleService.queryParentRole(); // 获取父节点
		int userId = Integer.parseInt(request.getParameter("userId")); // 获取用户ID
		String tr1 = "";
		// 查询该用户拥有的角色
		List<SysRole> userRole = sysRoleService.queryRoleByUserId(userId);
		for (int i = 0; i < parentList.size(); i++) {
			SysRole role = (SysRole) parentList.get(i);
			tr1 = tr1 + "{ " + Constans.ID + ":" + role.getRoleId() + ","
					+ Constans.PID + ":" + role.getPid() + "," + Constans.NAME
					+ ":\"" + role.getRoleName() + "\", open:true},";
			List<SysRole> childList = sysRoleService.queryChildRole(role
					.getRoleId()); // 获取子类信息
			tr1 = tr1 + checkIsUserRole(childList, userRole);
		}
		tr1 = tr1.substring(0, tr1.length() - 1);
		String result = "{roleList:[" + tr1 + "]}";
		JSONObject object = JSONObject.fromObject(result);
		response.getWriter().write(object.toString());
	}

	// 判断用户现在拥有的角色 默认选中
	public String checkIsUserRole(List<SysRole> childList,
			List<SysRole> userRole) {
		String str = "";
		for (int i = 0; i < childList.size(); i++) {
			int index=0;
			SysRole childrole = childList.get(i);
			for (int j = 0; j < userRole.size(); j++) {
				SysRole userrole = userRole.get(j);
				if (childrole.getRoleId() == userrole.getRoleId()) {
					index=1;
				} 
			}
			
			if(index==1){  //表示当前角色有该权限
				str = str + "{ " + Constans.ID + ":"
						+ childrole.getRoleId() + "," + Constans.PID + ":"
						+ childrole.getPid() + "," + Constans.NAME + ":\""
						+ childrole.getRoleName()
						+ "\", open:true,checked:true},";
			}else{
				str = str + "{ " + Constans.ID + ":"
						+ childrole.getRoleId() + "," + Constans.PID + ":"
						+ childrole.getPid() + "," + Constans.NAME + ":\""
						+ childrole.getRoleName() + "\", open:true},";
			}

		}
		return str;
	}
	


	
	/**
	 * 展示角色列表(树形  新增
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryRoleAdd", method = RequestMethod.POST)
	public void queryRoleAdd(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 获取系统角色信息
		response.setContentType("text/plain;charset=UTF-8");
		List<SysRole> parentList = sysRoleService.queryParentRole(); // 获取父节点
		String tr1 = "";
		for (int i = 0; i < parentList.size(); i++) {
			SysRole role = (SysRole) parentList.get(i);
			tr1 = tr1 + "{ " + Constans.ID + ":" + role.getRoleId() + ","
					+ Constans.PID + ":" + role.getPid() + "," + Constans.NAME
					+ ":\"" + role.getRoleName() + "\", open:true},";
			List<SysRole> childList = sysRoleService.queryChildRole(role
					.getRoleId()); // 获取子类信息
			for (int j = 0; j < childList.size(); j++) {
				SysRole childrole = childList.get(j);
					tr1 = tr1 + "{ " + Constans.ID + ":"
							+ childrole.getRoleId() + "," + Constans.PID + ":"
							+ childrole.getPid() + "," + Constans.NAME + ":\""
							+ childrole.getRoleName() + "\", open:true},";
			}
		}
		tr1 = tr1.substring(0, tr1.length() - 1);
		String result = "{roleList:[" + tr1 + "]}";
		JSONObject object = JSONObject.fromObject(result);
		response.getWriter().write(object.toString());
	}

}
