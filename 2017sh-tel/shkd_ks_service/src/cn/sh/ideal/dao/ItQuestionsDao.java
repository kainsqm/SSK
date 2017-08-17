package cn.sh.ideal.dao;

import java.util.List;

import cn.sh.ideal.model.tExampaperQuestions;
import cn.sh.ideal.model.tQuestions;

public interface ItQuestionsDao {
    int deleteByPrimaryKey(Integer pkAutoId);

    int insert(tQuestions record);

    int insertSelective(tQuestions record);

    tQuestions selectByPrimaryKey(Integer pkAutoId);
    
    tQuestions selectByPrimaryCodeName(Integer pkAutoId);
    
    int updateByPrimaryKeySelective(tQuestions record);

    int updateByPrimaryKey(tQuestions record);
    
    List<tQuestions> getQuestionList(tQuestions questions);
    
    int getQuestionCount(tQuestions questions);

	int countQuestion(Integer pkAutoId);

	Integer querExampaperId(Integer pkAutoId);

	List<tQuestions> queryQuesSelected(String[] questionId);
	
	List<tQuestions> queryNoAnswerByExampaperId(Integer pkAutoId);
	
	tQuestions selectQuestionsByEEUId(tExampaperQuestions eq);
	
	int updateErrorNum(Integer pkAutoId);

	List<tQuestions> selectRandomQuestion(String[] questionId);

	int selectQuestionCount(tQuestions questions);

	List<tQuestions> selectByCodeId(int codeId);

	List<tQuestions> getQuesByExampaperId(Integer pkAutoId);

	List<tQuestions> queryQeusByExamId(tQuestions questions);

	int queryQeusByExamIdCount(tQuestions questions);

	List<tQuestions> queryQuestionList(tQuestions questions);

	int getQuestionListCount(tQuestions questions);

	List<tQuestions> getQuesTionbyExamIdList(tQuestions questions);

	int getQuesTionbyExamIdCount(tQuestions questions);

	List<tQuestions> getQUestionByRandomList(tQuestions questions);

	int getQUestionByRandomCount(tQuestions questions);

	int checkQuestions(tQuestions questions);

	Integer checkQuestion(tQuestions questions);

}