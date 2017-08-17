package cn.sh.ideal.dao;

import java.util.List;

import cn.sh.ideal.model.tExamResults;

public interface ItExamResultsDao {
    int deleteByPrimaryKey(Integer pkAutoId);

    int insert(tExamResults record);

    int insertSelective(tExamResults record);

    tExamResults selectByPrimaryKey(Integer pkAutoId);

    int updateByPrimaryKeySelective(tExamResults record);

    int updateByPrimaryKey(tExamResults record);
    
    List<tExamResults> selectByExamineId(Integer fkEEpExamineeId);
    
    int updateUserScore (tExamResults record);
    
    int insertNullAnswer(tExamResults record);
}