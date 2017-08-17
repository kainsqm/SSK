package cn.sh.ideal.service.impl;

import java.net.URL;
import java.util.Date;

import javax.annotation.Resource;
import javax.xml.namespace.QName;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.IRecordInfoDao;
import cn.sh.ideal.model.RecordInfo;
import cn.sh.ideal.service.IRecordInfoService;
import cn.sh.ideal.voicecyber.IWebSDK;
import cn.sh.ideal.voicecyber.WebSDK;
import cn.sh.ideal.voicecyber.WebSDKReturn;

@Service("recordInfoService")
public class RecordInfoService implements IRecordInfoService {

	@Resource
	private IRecordInfoDao recordInfoDao;

	/***
     * 判断数据库是否存在该录音流水号
     * **/
    public int checkExistRecordInfo(String RecordReference){
    	return recordInfoDao.checkExistRecordInfo(RecordReference);
    }

	public int insert(RecordInfo info) {
		return recordInfoDao.insert(info);
	}
}
