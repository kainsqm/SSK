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

import cn.sh.ideal.dao.ISysRoleFunctionDao;
import cn.sh.ideal.model.SysRoleFunction;
import cn.sh.ideal.service.ISysRoleFunctionService;

@Service("sysRoleFunctionService")
public class SysRoleFunctionServiceImpl implements ISysRoleFunctionService {
	@Resource
	private ISysRoleFunctionDao sysRoleFunctionDao;

	@Override
	public int deleteByRoleId(Integer roleId) {
		return sysRoleFunctionDao.deleteByRoleId(roleId);
	}

	@Override
	public int insert(SysRoleFunction record) {
		return sysRoleFunctionDao.insert(record);
	}

	

	@Override
	public int updateByRoleId(SysRoleFunction record) {
		return sysRoleFunctionDao.updateByRoleId(record);
	}

	@Override
	public List<SysRoleFunction> selectByRoleId(Integer roleId) {
		return sysRoleFunctionDao.selectByRoleId(roleId);
	}
	
}