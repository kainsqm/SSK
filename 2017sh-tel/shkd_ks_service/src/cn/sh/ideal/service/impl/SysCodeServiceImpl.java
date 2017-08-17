package cn.sh.ideal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.ItSysCodeDao;
import cn.sh.ideal.model.tSysCode;
import cn.sh.ideal.service.ISysCodeService;

@Service("sysCodeService")
public class SysCodeServiceImpl implements ISysCodeService {
	@Resource
	private ItSysCodeDao sysCodeDao;

    @Override
	public List<tSysCode> getItemFlagList(String itemFlag) {
		
		return sysCodeDao.selectByItemFlag(itemFlag);
	}
	
	@Override
    public List<tSysCode> getSysSettingLists(){
        return sysCodeDao.getSysSettingLists();
    }
    
    @Override
    public List<tSysCode> getSysBusindess(){
        return sysCodeDao.getSysBusindess();
    }
    
    @Override
    public List<tSysCode> getSysByPidAndName(tSysCode record) {
        return sysCodeDao.getSysByPidAndName(record);
    }

    @Override
    public int insertSetting(tSysCode record) {
        return sysCodeDao.insertSetting(record);
    }
    
    @Override
    public int updateSetting(tSysCode record) {
        return sysCodeDao.updateSetting(record);
    }
    
    @Override
    public int delSetting(tSysCode record) {
        return sysCodeDao.deleteByPrimaryKey(record.getPkAutoId());
    }
    
    @Override
    public List<tSysCode> selectByPid(Integer pid){
        return sysCodeDao.selectByPid(pid);
    }
    
}
