package cn.sh.ideal.service;

import java.util.List;

import cn.sh.ideal.model.ExamManualScore;
import cn.sh.ideal.model.tExampaper;

public interface IExampaperService {
    
    List<tExampaper> getExampapers(tExampaper paper);
    /**
     * 获取模板试卷列表集合
     * @param exampaper
     * @return
     */
	List<ExamManualScore> getExampaperRandomList(tExampaper exampaper);
	/**
	 * 获取模板试卷列表总数
	 * @param exampaper
	 * @return
	 */
	int getCountRandomCount(tExampaper exampaper);
	/**
	 * 获取历史模板试卷列表
	 * @param exampaper
	 * @return
	 */
	List<ExamManualScore> getExampaperList(tExampaper exampaper);
	/**
	 * 获取历史模板试卷总数
	 * @param exampaper
	 * @return
	 */
	int getExampaperCount(tExampaper exampaper);
    
	
	
	
	/*******
	 * 根据试卷ID查询该试卷详情
	 * @author niewenqiang
	 * @date 2017-4-26
	 * ******/
	public tExampaper quertExamPaperById(int pkAutoId);
    
	/**
     * 查选试卷类型
     * @param params
     * @author chendi
     * @return
     */
    public List<tExampaper> selectByCodeId(int id);
}
