package cn.sh.ideal.service;

import cn.sh.ideal.model.TQuestionsArea;
import cn.sh.ideal.model.tExampaper;
import cn.sh.ideal.model.tExampaperQuestions;
import cn.sh.ideal.model.vSysuser;

public interface OptionQuestionService {
	/**
	 * 放入考题区操作
	 * @param questionsArea
	 */
	int insertQuestionAreaExaMaprer(TQuestionsArea questionsArea);
	/**
	 * @param userId 
	 * 根据用户Id获取当前用户选择的考题信息
	 * @return 
	 */
	TQuestionsArea selectQuestionAreaById(String userId);
	/**
	 * 修改放入考题区的考题状态数据
	 * @param area
	 * @return
	 */
	int updateQuestionAreaExaMaprer(TQuestionsArea area);
	
	/**
	 * 录入考卷表
	 * @param exampaper
	 * @return
	 */
	int insertExampaper(tExampaper exampaper);
	/**
	 * 插入试卷考题关联表操作
	 * @param exampaperQuestions
	 * @return
	 */
	int insertExampaperQuestions(tExampaperQuestions exampaperQuestions);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	TQuestionsArea selectRandomQuestionAreaById(String userId);
	/**
	 * 查询用户修改的考题区考题
	 * @param userId
	 * @return
	 */
	TQuestionsArea selectUpdateQuestionAreaById(String userId);
	/**
	 * 修改考题信息
	 */
	int updateQuestionAreaExaMaprer(TQuestionsArea area, String checkedCustomer);
	/**
	 * 修改考题信息
	 * @param area
	 * @param checkedCustomer
	 * @param user
	 */
	int updateQuestionAreaExaMaprer(TQuestionsArea area,
			String checkedCustomer, vSysuser user);
	
}
