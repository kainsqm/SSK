package cn.sh.ideal.dao;

import java.util.List;

import cn.sh.ideal.model.SysRoleFunction;

public interface ISysRoleFunctionDao {
	int deleteByRoleId(Integer roleId);

    int insert(SysRoleFunction record);

    int updateByRoleId(SysRoleFunction record);
    
    List<SysRoleFunction> selectByRoleId(Integer roleId);
}