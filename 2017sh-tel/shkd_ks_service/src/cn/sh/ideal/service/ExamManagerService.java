package cn.sh.ideal.service;

import java.util.List;
import java.util.Map;

import cn.sh.ideal.model.ExamManualScore;
import cn.sh.ideal.model.TQuestionsArea;
import cn.sh.ideal.model.tExampaper;
import cn.sh.ideal.model.tQuestions;

public interface ExamManagerService {
	/**
	 * 获取试卷管理列表页面
	 * @param exampaper
	 * @return
	 */
	public List<ExamManualScore> queryExampapersList(tExampaper exampaper);
	/**
	 * 获取试卷管理列表数据总数
	 * @param exampaper
	 * @return
	 */
	public int queryExampapersCount(tExampaper exampaper);
	/**
	 * 根据试卷Id查询试卷表
	 * @param pkAutoId
	 * @return
	 */
	public tExampaper getExampaperById(Integer pkAutoId);
	/**
	 * 根据试卷Id查询试卷关联考题表
	 * @param pkAutoId
	 * @return
	 */
	public List<tQuestions> getQuesByExampaperId(Integer pkAutoId);
	/**
	 * 检查导入的考题错误信息
	 * @param excelList
	 * @param paramMap
	 * @param titlelList
	 * @return
	 */
	public Map<String, Object> checkExamQuestion(List<List<Object>> excelList,
			Map<Object, Object> paramMap, List<List<Object>> titlelList);
	/**
	 * 执行考卷考题导入信息
	 * @param map
	 */
	public void insertBatCode(Map<String, Object> map);
	/**
	 * 删除考卷信息
	 * @param exampaper
	 */
	public void deleteExamById(tExampaper exampaper);
	/**
	 * 验证试卷是否被用于考试
	 * @param pkAutoId
	 * @return
	 */
	public int checkExampaperExaminee(Integer pkAutoId);
	/**
	 * 查询所有试题总数
	 * @param questions
	 * @return
	 */
	public int getQuestionListCount(
			tQuestions questions);
	/**
	 * 查询所有试题列表
	 * @param questions
	 * @return
	 */
	public List<tQuestions> getQuestionList(
			tQuestions questions);
	/**
	 * 根据试卷Id查询当前试卷列表
	 * @param questions
	 * @return
	 */
	public List<cn.sh.ideal.model.tQuestions> queryQeusByExamId(
			tQuestions questions);
	/**
	 * 根据试卷Id查询当前试卷列表
	 * @param questions
	 * @return
	 */
	public int queryQeusByExamIdCount(
			tQuestions questions);
	/**
	 * 根据用户ID查询用户要修改的题库
	 * @param userId
	 * @return
	 */
	public TQuestionsArea selectUpdateQuestionAreaById(String userId);
	/**
	 * 根据考卷Id查询考卷信息
	 * @param exampaperId
	 * @return
	 */
	public tExampaper selectExampaperById(Integer exampaperId);
	/**
	 * 根据试卷名称查询试卷
	 * @param examPaperName
	 * @return
	 */
	public int checkExampaperName(String examPaperName);
	/**
	 * 查询非模板试卷
	 * @param exampaper
	 * @return
	 */
	public List<ExamManualScore> selectExampapersList(tExampaper exampaper);
	/**
	 * 查询非模板试卷 总数
	 * @param exampaper
	 * @return
	 */
	public int selectExampapersCount(tExampaper exampaper);
	/**
	 * 查询模板和非模板试卷
	 * @param exampaper
	 * @return
	 */
	public List<ExamManualScore> selectRandomExampapersList(tExampaper exampaper);
	/**
	 * 查询模板和非模板总数
	 * @param exampaper
	 * @return
	 */
	public int selectRandomExampapersCount(tExampaper exampaper);
}
