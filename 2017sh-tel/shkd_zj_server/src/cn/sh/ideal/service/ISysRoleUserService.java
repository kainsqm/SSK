package cn.sh.ideal.service;

import java.util.List;

import cn.sh.ideal.model.SysRoleUser;

public interface ISysRoleUserService {
    List<SysRoleUser> selectByRoleId(Integer roleId);

}