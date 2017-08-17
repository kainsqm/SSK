package cn.sh.ideal.service;

import java.util.List;
import java.util.Map;

import cn.sh.ideal.model.tExampaper;
import cn.sh.ideal.model.tExampaperQuestions;
import cn.sh.ideal.model.tQuestions;
import cn.sh.ideal.model.tSysCode;

public interface QuesTionsService {
	/**
	 * 获取考题列表信息数据
	 * 
	 * @param questions
	 * @return
	 */
	public List<tQuestions> getQuesTionsList(tQuestions questions);

	/**
	 * 获取考题列表信息总数
	 * 
	 * @param questions
	 * @return
	 */
	public int getCountQuesTion(tQuestions questions);

	/**
	 * 根据考题Id获取考题
	 * 
	 * @return
	 */
	public tQuestions getQuesTionbyId(Integer pkAutoId);

	/**
	 * 从excel中导入考题数据
	 * 
	 * @param excelList
	 * @param paramMap
	 * @param titlelList
	 * @return
	 */
	public Map<String, Object> insertQuesTion(List<List<Object>> excelList,
			Map<Object, Object> paramMap, List<List<Object>> titlelList);

	/**
	 * 获取父节点信息
	 * 
	 * @return
	 */
	public List<tSysCode> getFistTreeDepart();

	/**
	 * 根据父Id获取子节点信息
	 * 
	 * @param pid
	 * @return
	 */
	public List<tSysCode> getAllTreeDepart(Integer pid);
	/**
	 * 
	 * 手工录入考题信息
	 * @param questions
	 * @return
	 */
	public int saveQuesTion(tQuestions questions);
	/**
	 * 查询考题是否试卷使用
	 * @param PkAutoId
	 * @return
	 */
	public int countQuestion(Integer PkAutoId);
	/***
	 * 查询被使用的试卷是哪一张
	 * @return
	 */
	public Integer querExampaperId(Integer PkAutoId);
	/**
	 * 删除考题操作
	 * @param questions
	 */
	public void deleteQuesTion(tQuestions questions);
	/**
	 * 修改考题操作
	 * @param questions
	 * @return
	 */
	public int updateQuesTion(tQuestions questions);
	/**
	 * 根据考题Id查询考题
	 * @param questionId
	 * @return
	 */
	public List<tQuestions> queryQuesSelected(String[] questionId);
	/**
	 * 根据主键查询考题对象
	 * @param string
	 * @return
	 */
	public tQuestions querytQuestById(Integer pkAutoId);
	
	/**
	 * 根据考题Id查询随机考题区考题内容
	 * @param split
	 * @return
	 */
	public List<tQuestions> selectRandomQuestionAreaById(String[] split);
	/**
	 * 
	 * @param codeId
	 * @return
	 */
	public List<tQuestions> selectByCodeId(int codeId);
	/**
	 * 
	 * @param parseInt
	 * @return
	 */
	public tExampaper queryExampaperById(int exampaperId);
	/**
	 * 修改试卷信息
	 * @param exampaper
	 */
	public void updateExampaper(tExampaper exampaper);
	/**
	 * 逻辑删除试卷和考题关系
	 * @param exampaperQuestions
	 */
	public void deleteExamQuesByExamId(tExampaperQuestions exampaperQuestions);
	/**
	 * 根据考卷Id查询考题信息
	 * @param exampaperId
	 * @return
	 */
	public List<tQuestions> getQuesTionbyExamIdList(tQuestions questions);
	/**
	 * 根据考卷Id查询考题总数
	 * @param exampaperId
	 * @return
	 */
	public int getQuesTionbyExamIdCount(tQuestions questions);
	/**
	 * 查询模板试卷表考题
	 * @param questions
	 * @return
	 */
	public List<tQuestions> getQUestionByRandomList(tQuestions questions);
	/**
	 * 查询模板试卷表考题总数
	 * @param questions
	 * @return
	 */
	public int getQUestionByRandomCount(tQuestions questions);
	/**
	 * 检测录入的考题是否已存在
	 * @param questions
	 * @return
	 */
	public int checkQuestions(tQuestions questions);
}
