package cn.sh.ideal.dao;

import cn.sh.ideal.model.SysRole;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.model.vSysuser;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IvSysuserDao {
    int countByExample(vSysuser user);

    int deleteByExample(vSysuser user);

    List<vSysuser> selectSysusers(vSysuser user);

    List<vSysuser> getGroups();
    
    List<vSysuser> getExamUsers(int examId);

    List<SysRole> getRoles();
}