package cn.sh.ideal.service;

import java.util.List;

import cn.sh.ideal.model.SysRoleFunction;

public interface ISysRoleFunctionService {
	 int deleteByRoleId(Integer roleId);

    int insert(SysRoleFunction record);

    int updateByRoleId(SysRoleFunction record);
    
    List<SysRoleFunction> selectByRoleId(Integer roleId);
    
}