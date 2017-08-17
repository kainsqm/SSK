package cn.sh.ideal.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;

import cn.sh.ideal.model.RecordInfo;
import cn.sh.ideal.model.UserInfo;

public interface IRecordInfoService{

	/***
     * 判断数据库是否存在该录音流水号
     * **/
    public int checkExistRecordInfo(String RecordReference);
	 
	 public int insert(RecordInfo info);
}
