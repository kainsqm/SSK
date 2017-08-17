package cn.sh.ideal.dao;

import java.util.List;

import cn.sh.ideal.model.ExamManualScore;
import cn.sh.ideal.model.tExamVo;
import cn.sh.ideal.model.tExampaper;

public interface ItExampaperDao {
    int deleteByPrimaryKey(Integer pkAutoId);

    int insert(tExampaper record);

    int insertSelective(tExampaper record);

    tExampaper selectByPrimaryKey(Integer pkAutoId);

    int updateByPrimaryKeySelective(tExampaper record);

    int updateByPrimaryKey(tExampaper record);
    
    tExampaper getPaperByExamId(String pkAutoId);
    
    List<tExampaper> getExampapers(tExampaper paper);

	List<ExamManualScore> getExampaperRandomList(tExampaper exampaper);

	int getExampaperRandomCount(tExampaper exampaper);

	List<ExamManualScore> getExampaperList(tExampaper exampaper);

	int getExampaperCount(tExampaper exampaper);
	
	List<tExampaper> selectByCodeId(Integer pkAutoId);

	List<ExamManualScore> queryExampapersList(tExampaper exampaper);

	int queryExampapersCount(tExampaper exampaper);

	int checkExampaperExaminee(Integer pkAutoId);

	int checkExampaperName(String examPaperName);

	List<ExamManualScore> selectExampapersList(tExampaper exampaper);

	int selectExampapersCount(tExampaper exampaper);

	List<ExamManualScore> selectRandomExampapersList(tExampaper exampaper);

	int selectRandomExampapersCount(tExampaper exampaper);
}