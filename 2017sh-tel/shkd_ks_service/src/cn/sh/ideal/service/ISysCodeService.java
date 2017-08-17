package cn.sh.ideal.service;

import java.util.List;

import cn.sh.ideal.model.tSysCode;

import net.sf.json.JSONObject;

public interface ISysCodeService {
    
    public List<tSysCode> getItemFlagList(String itemFlag);
    
    public List<tSysCode> getSysSettingLists();

    public List<tSysCode> getSysBusindess();

    public List<tSysCode> getSysByPidAndName(tSysCode record);

    public int insertSetting(tSysCode record);

    public int updateSetting(tSysCode record);
    
    public int delSetting(tSysCode record);

    public List<tSysCode> selectByPid(Integer pid);
    
    
}
