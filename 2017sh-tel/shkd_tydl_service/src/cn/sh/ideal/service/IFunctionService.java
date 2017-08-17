package cn.sh.ideal.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.sh.ideal.model.Function;

public interface IFunctionService {
	/******
	 * 根据用户ID查询用户权限菜单
	 * *******/
	public List<Function> getFunctionByUserid(Map<String, String> map);
	
	/******
	 * 根据用户ID以及父ID查询子节点
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	public List<Function> getChildFunByParam(Map<String, Integer> map);
	
	/******
	 * 根据用户ID查询该用户拥有系统权限  用于首页权限控制显示
	 * @author niewenqiang
	 * 2017-4-18
	 * *******/
	public List<Function> querySysRoleByUserId(int userId);
	
	
	/*******
	 * 获取用户对应的权限
	 * @author niewenqiang
	 * @date 2017-5-2
	 * **********/
	
	public List<Function> getPermissionByUser(int userId,String syscode);
	
	
	/*******
	 * 获取用户对应的报表权限
	 * @author niewenqiang
	 * @date 2017-5-2
	 * **********/
	
	public List<Function> getReportRoleByUser(int userId);
}
