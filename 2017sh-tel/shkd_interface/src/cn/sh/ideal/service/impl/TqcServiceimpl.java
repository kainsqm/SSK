package cn.sh.ideal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.TqcInfoDao;
import cn.sh.ideal.model.TqcInfo;
import cn.sh.ideal.service.ITqcService;
@Service("tqcService")
public class TqcServiceimpl implements ITqcService {
	@Resource
	private TqcInfoDao tqcInfoDao;
	
	public List<TqcInfo> getTqcInfoList(String sdate){
	    return tqcInfoDao.getTqcInfoList(sdate);
	}
	
}
