package cn.sh.ideal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.IRecordInfoDao;
import cn.sh.ideal.model.RecordInfo;
import cn.sh.ideal.service.IRecordInfoSevice;

@Service("recordInfoService")
public class RecordInfoServiceImpl implements IRecordInfoSevice {
	@Resource
	private IRecordInfoDao recordInfoDao;

	@Override
	public int insert(RecordInfo record) {
		// TODO Auto-generated method stub
		return recordInfoDao.insert(record);
	}

	@Override
	public int insertSelective(RecordInfo record) {
		return recordInfoDao.insertSelective(record);
	}

	@Override
	public RecordInfo selectByPrimaryKey(Integer recordId) {
		return recordInfoDao.selectByPrimaryKey(recordId);
	}

	@Override
	public List<RecordInfo> selectRecordInfo(RecordInfo record) {
		//将通话时长装化成以分钟计时
		
		Integer min=record.getRecordLengthmin();
		if(min!=null){
			record.setRecordLengthmin(min);
		}
		Integer max=record.getRecordLengthmax();
		if(max!=null){
			record.setRecordLengthmax(max);
		}
		return recordInfoDao.selectRecordInfo(record);
	}

	@Override
	public int selectCount(RecordInfo record) {
		return recordInfoDao.selectCount(record);
	}
	

}
