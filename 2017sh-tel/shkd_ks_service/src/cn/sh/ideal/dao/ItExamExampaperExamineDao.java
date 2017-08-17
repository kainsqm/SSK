package cn.sh.ideal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sh.ideal.model.ExamManualScore;
import cn.sh.ideal.model.tExamExampaper;
import cn.sh.ideal.model.tExamExampaperExamine;

public interface ItExamExampaperExamineDao {
    int deleteByPrimaryKey(Integer pkAutoId);

    int insert(tExamExampaperExamine record);

    int insertSelective(tExamExampaperExamine record);
    
    tExamExampaperExamine selectByPrimaryKey(Integer pkAutoId);

    List<tExamExampaperExamine> selectByeEpId(Integer pkAutoId);

    int updateByPrimaryKeySelective(tExamExampaperExamine record);

    int updateByPrimaryKey(tExamExampaperExamine record);
    
    int selectByExamId(int examId);
    
    int deleteByFKID(int fkId);
    
    List<tExamExampaperExamine> selectPersonExams(tExamExampaperExamine examine);
    
    List<tExamExampaperExamine> selectResetExam(tExamExampaperExamine examine);
    
    List<ExamManualScore> getListByUser(ExamManualScore ee);
    
    tExamExampaperExamine selectUserExamIdByPrimaryKey(Integer pkAutoId);

    List<ExamManualScore> selectScoreManual(ExamManualScore score);
    
    ExamManualScore  getExampaperQuestionIFRandom(Integer pkAutoId);
    
    List queryExampaperForScore(ExamManualScore score);
    
    String getsltMeByEId(int pkAutoId);
    
    /**
     * 
     * @param pkAutoId
     * @return
     */
    int selectStatusByExamId(int pkAutoId);
    
    
    /******
     * 把所有的手动评分中状态的试卷 置为待手动评分
     * @author niewenqiang
     * @date 2017-6-5
     * *****/
    void updateExamineStatus(@Param("pkautoId") String pkautoId);
    
    /***
     * 获取所有处于手动评分中的试卷
     * @author niewenqiang
     * @date 2017-6-5
     * ****/
    List<tExamExampaperExamine> queryExamPaperByStatus();
}