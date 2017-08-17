package cn.sh.ideal.dao;

import cn.sh.ideal.model.tExamExampaper;

public interface ItExamExampaperDao {
    int insert(tExamExampaper record);

    int insertSelective(tExamExampaper record);
    
    int selectByExamId(int fkExamId);
    
    int deleteByPrimaryKey(int pkAutoId);
    
    int selectPrimaryKey(tExamExampaper record);
    
}