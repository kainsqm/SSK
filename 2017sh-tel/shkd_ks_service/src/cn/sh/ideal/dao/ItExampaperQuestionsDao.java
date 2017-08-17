package cn.sh.ideal.dao;

import cn.sh.ideal.model.tExampaperQuestions;

public interface ItExampaperQuestionsDao {
    int deleteByPrimaryKey(Integer pkAutoId);

    int insert(tExampaperQuestions record);

    int insertSelective(tExampaperQuestions record);

    tExampaperQuestions selectByPrimaryKey(Integer pkAutoId);

    int updateByPrimaryKeySelective(tExampaperQuestions record);

    int updateByPrimaryKey(tExampaperQuestions record);
    
    int deleteByUserExamId(tExampaperQuestions record);
}