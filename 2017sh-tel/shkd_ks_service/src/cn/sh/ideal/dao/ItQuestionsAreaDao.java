package cn.sh.ideal.dao;

import cn.sh.ideal.model.TQuestionsArea;

public interface ItQuestionsAreaDao {
	
    int insertSelective(TQuestionsArea record);

	TQuestionsArea selectQuestionAreaById(String userId);

	int updateQuestionAreaExaMaprer(TQuestionsArea area);

	TQuestionsArea selectRandomQuestionAreaById(String userId);

	TQuestionsArea selectUpdateQuestionAreaById(String userId);
}