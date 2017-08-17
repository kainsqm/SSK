package cn.sh.ideal.dao;

import cn.sh.ideal.model.tMonitorExam;

public interface ItMonitorExamDao {
    int deleteByPrimaryKey(Integer pkAutoId);

    int insert(tMonitorExam record);

    int insertSelective(tMonitorExam record);

    tMonitorExam selectByPrimaryKey(Integer pkAutoId);

    int updateByPrimaryKeySelective(tMonitorExam record);

    int updateByPrimaryKeyWithBLOBs(tMonitorExam record);

    int updateByPrimaryKey(tMonitorExam record);
    
    tMonitorExam selectByExamUserId(tMonitorExam record);
}