package cn.sh.ideal.service;

import cn.sh.ideal.model.TQuestionsArea;
import cn.sh.ideal.model.tExampaper;
import cn.sh.ideal.model.tExampaperRandom;
import cn.sh.ideal.model.tQuestions;
import cn.sh.ideal.model.tQuestionsRandomsel;

public interface ExampaperRandomService {
	/**
	 * 查询当前用户下选择的随机模板试题
	 * @param userId
	 * @return
	 */
	TQuestionsArea selectRandomQuestionAreaById(String userId);
	
	/**
	 * 查询考题中是否有题目
	 * @param questionId 
	 * @param quesType 
	 * @param quesNandu 
	 * @param fkCodetypeId 
	 * @param questions
	 * @return
	 */
	int selectQuestionCount(tQuestions questions);
	/**
	 * 录入模板试卷
	 * @param exampaperRandom
	 */
	int insertExampaperRandom(tExampaperRandom exampaperRandom);
	/**
	 * 添加试题模板试卷
	 * @param questionsRandomsel
	 */
	int insertQuestionsRandomSel(tQuestionsRandomsel questionsRandomsel);
	/**
	 * 将模板试卷进行逻辑删除
	 * @param exampaper
	 * @return
	 */
	int updateRandomExampaper(tExampaper exampaper);
	/**
	 * 删除模板和试卷关联中间表信息（物理删除）
	 * @param pkAutoId
	 * @return
	 */
	int deleteRandomExampaper(Integer pkAutoId);

	
}
