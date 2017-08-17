package cn.sh.ideal.dao;

import java.util.List;

import cn.sh.ideal.model.RecordInfo;

public interface IRecordInfoDao {

    int insert(RecordInfo record);

    int insertSelective(RecordInfo record);

    RecordInfo selectByPrimaryKey(Integer recordId);
    
    List<RecordInfo> selectRecordInfo(RecordInfo record);
    int selectCount(RecordInfo record);
}