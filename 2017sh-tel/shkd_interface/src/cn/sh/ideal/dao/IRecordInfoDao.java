package cn.sh.ideal.dao;

import cn.sh.ideal.model.RecordInfo;


public interface IRecordInfoDao{
     /***
      * 判断数据库是否存在该录音流水号
      * **/
     public int checkExistRecordInfo(String RecordReference);
	 
	 public int insert(RecordInfo info);
}
