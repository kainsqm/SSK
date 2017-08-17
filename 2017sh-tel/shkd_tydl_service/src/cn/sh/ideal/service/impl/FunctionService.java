package cn.sh.ideal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.IFunctionDao;
import cn.sh.ideal.model.Function;
import cn.sh.ideal.service.IFunctionService;
/******
 * 菜单业务类
 * @author niewenqiang
 * 2017-4-12
 * *****/
@Service("functionService")
public class FunctionService implements IFunctionService {

	@Autowired
	public IFunctionDao functionDao;
	
	@Override
	public List<Function> getFunctionByUserid(Map<String, String> map) {
		return functionDao.getFunctionByUserid(map);
	}

	/******
	 * 根据用户ID以及父ID查询子节点
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	public List<Function> getChildFunByParam(Map<String, Integer> map){
		return functionDao.getChildFunByParam(map);
	}
	
	/******
	 * 根据用户ID查询该用户拥有系统权限  用于首页权限控制显示
	 * @author niewenqiang
	 * 2017-4-18
	 * *******/
	public List<Function> querySysRoleByUserId(int userId){
		return functionDao.querySysRoleByUserId(userId);
	}
	
	
	/*******
	 * 获取用户对应的权限
	 * @author niewenqiang
	 * @date 2017-5-2
	 * **********/
	
	public List<Function> getPermissionByUser(int userId,String syscode){
		return functionDao.getPermissionByUser(userId,syscode);
	}
	
	/*******
	 * 获取用户对应的报表权限
	 * @author niewenqiang
	 * @date 2017-5-2
	 * **********/
	
	public List<Function> getReportRoleByUser(int userId){
		return functionDao.getReportRoleByUser(userId);
	}
}
