package cn.sh.ideal.service;

import java.util.List;

import cn.sh.ideal.model.ExamManualScore;
import cn.sh.ideal.model.tExamExampaper;
import cn.sh.ideal.model.tExamExampaperExamine;

public interface IExamExampaperExamineService {
    /**
     * 
     * @param examine
     * @return
     */
    public List<tExamExampaperExamine> selectPersonExams(tExamExampaperExamine examine);

    /**
     * 
     * @param examine
     * @return
     */
    public List<tExamExampaperExamine> selectResetExam(tExamExampaperExamine examine);
    
    /**
     * 
     * @param examine
     * @return
     */
    public String updateExamReset(tExamExampaperExamine examine);
    
    /**
     * 自动评分 
     * @param examineId
     * @return
     */
    public String updateAutoSetMark(int examineId);
    
    /**
     * 获取剩余时间
     * @param examineId
     * @return
     */
    public int getsltMeByEId(int examineId);
    
    /**
    * 手动评分查询
    *  
    */
    public List<ExamManualScore> selectScoreManual(ExamManualScore score);
    
    /**
     * 
     */
    public tExamExampaperExamine selectByPrimaryKey(int pkAutoId);
    
    /**
     * 
     */
    public ExamManualScore selectExampaperQuestionIFRandom(int pkAutoId);
    
    /**
     * 查询试卷分值总体信息
     *Description:IExamExampaperExamineService queryExampaperForScore
     *throws:
     *@param id
     *@return
     */
    public List queryExampaperForScore(int id,String userId,String integer);
    
    /**
     * 手动评分
     * @param str
     * @return
     */
    public String updateResultScore(String str,String examineId,int userId);
    
    /**
     * 查询参考人员
     * @param ee
     * @return
     */
    public List<ExamManualScore> selectListByUser(ExamManualScore ee);
    
    /**
     * 修改状态
     * @param record
     * @return
     */
    public int updateByPrimaryKeySelective(tExamExampaperExamine record);
}
