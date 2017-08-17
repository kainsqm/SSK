package cn.sh.ideal.service;

import java.util.List;

import cn.sh.ideal.model.RecordInfo;

public interface IRecordInfoSevice {

    int insert(RecordInfo record);

    int insertSelective(RecordInfo record);

    RecordInfo selectByPrimaryKey(Integer recordId);
    
    List<RecordInfo> selectRecordInfo(RecordInfo record);
    int selectCount(RecordInfo record);
}