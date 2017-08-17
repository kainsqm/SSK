package cn.sh.ideal.dao;

import java.util.List;

import cn.sh.ideal.model.tSysCode;

public interface ItSysCodeDao {
    int deleteByPrimaryKey(Integer pkAutoId);

    int insert(tSysCode record);

    int insertSelective(tSysCode record);

    tSysCode selectByPrimaryKey(Integer pkAutoId);

    int updateByPrimaryKeySelective(tSysCode record);

    int insertSetting(tSysCode record);

    int updateSetting(tSysCode record);

    int updateByPrimaryKey(tSysCode record);
    
    List<tSysCode> selectByItemFlag(String itemFlag);
    
    tSysCode selectByAutoId(String fkCodetypeId);
    
    List<tSysCode> getFistTreeDepat();
    
    List<tSysCode> getAllTreeDepart(Integer pid);
    
    List<tSysCode> getSysSettingLists();

    List<tSysCode> getSysBusindess();

    List<tSysCode> getSysByPidAndName(tSysCode record);
    
    List<tSysCode> selectByPid(Integer pid);
    
}