package cn.sh.ideal.service;

import java.util.List;

import cn.sh.ideal.model.SysRole;
import cn.sh.ideal.model.vSysuser;

public interface VSysuserService {
    public List<vSysuser> selectSysusers(vSysuser vo);
    
    public List<vSysuser> getGroups();
    
    List<SysRole> getRoles();
    
}
