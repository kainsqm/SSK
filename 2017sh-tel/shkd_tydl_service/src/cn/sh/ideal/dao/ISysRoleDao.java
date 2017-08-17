package cn.sh.ideal.dao;

import java.util.List;

import cn.sh.ideal.model.SysRole;
import cn.sh.ideal.model.UserInfo;

public interface ISysRoleDao {
   /*****
    * 根据用户ID查询该用户拥有的角色
    * @author niewenqiang
    * 2017-4-12
    * ******/
	public List<SysRole> queryRoleByUserId(Integer userId);
	
	
	/******
	 * 根据用户ID，以及系统CODE查询在该系统中所属的角色
	 * @author niewenqiang
	 * 2017-4-12
	 * *****/
	public SysRole queryRoleByCode(UserInfo info);
	
	
}
