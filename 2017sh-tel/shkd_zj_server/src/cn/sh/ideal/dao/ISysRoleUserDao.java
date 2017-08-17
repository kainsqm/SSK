package cn.sh.ideal.dao;

import java.util.List;

import cn.sh.ideal.model.SysRoleUser;

public interface ISysRoleUserDao {
    List<SysRoleUser> selectByRoleId(Integer roleId);
}