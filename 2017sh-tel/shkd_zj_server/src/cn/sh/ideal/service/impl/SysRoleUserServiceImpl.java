/**
 * 
 */
/**
 * @author Administrator
 *
 */
package cn.sh.ideal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.ISysRoleUserDao;
import cn.sh.ideal.model.SysRoleUser;
import cn.sh.ideal.service.ISysRoleUserService;

@Service("sysRoleUserService")
public class SysRoleUserServiceImpl implements ISysRoleUserService {
	@Resource
	private ISysRoleUserDao sysRoleUserDao;

	@Override
	public List<SysRoleUser> selectByRoleId(Integer roleId) {
		return sysRoleUserDao.selectByRoleId(roleId);
	}
}