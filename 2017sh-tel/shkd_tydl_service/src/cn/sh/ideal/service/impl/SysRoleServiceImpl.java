package cn.sh.ideal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.ISysRoleDao;
import cn.sh.ideal.model.SysRole;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.service.ISysRoleService;

/*****
 * 业务操作实现类
 * ******/
@Service("roleService")
public class SysRoleServiceImpl implements ISysRoleService {

	@Autowired
	public ISysRoleDao roleDao;

	/*****
	 * 根据用户ID查询该用户拥有的角色
	 * 
	 * @author niewenqiang 2017-4-12
	 * ******/
	public List<SysRole> queryRoleByUserId(Integer userId) {
		return roleDao.queryRoleByUserId(userId);
	}

	/******
	 * 根据用户ID，以及系统CODE查询在该系统中所属的角色
	 * 
	 * @author niewenqiang 2017-4-12
	 * *****/
	public SysRole queryRoleByCode(UserInfo info) {
		return roleDao.queryRoleByCode(info);
	}

}
