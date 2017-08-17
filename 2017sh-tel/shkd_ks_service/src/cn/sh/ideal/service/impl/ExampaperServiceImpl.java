package cn.sh.ideal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.ItExampaperDao;
import cn.sh.ideal.model.ExamManualScore;
import cn.sh.ideal.model.tExam;
import cn.sh.ideal.model.tExampaper;
import cn.sh.ideal.service.IExampaperService;

@Service("exampaperService")
public class ExampaperServiceImpl implements IExampaperService {
	@Resource
	private ItExampaperDao  paperDao;

	public List<tExampaper> getExampapers(tExampaper paper){
	    return paperDao.getExampapers(paper);
	}

	@Override
	public List<ExamManualScore> getExampaperRandomList(tExampaper exampaper) {
		
		return paperDao.getExampaperRandomList(exampaper);
	}

	@Override
	public int getCountRandomCount(tExampaper exampaper) {

		return paperDao.getExampaperRandomCount(exampaper);
	}

	@Override
	public List<ExamManualScore> getExampaperList(tExampaper exampaper) {
		
		return paperDao.getExampaperList(exampaper);
	}

	@Override
	public int getExampaperCount(tExampaper exampaper) {
		// TODO Auto-generated method stub
		return paperDao.getExampaperCount(exampaper);
	}
	
	/*******
	 * 根据试卷ID查询该试卷详情
	 * @author niewenqiang
	 * @date 2017-4-26
	 * ******/
	public tExampaper quertExamPaperById(int pkAutoId){
		return paperDao.selectByPrimaryKey(pkAutoId);
	}
	
	/**
     * 查选试卷类型
     * @param params
     * @author chendi
     * @return
     */
    public List<tExampaper> selectByCodeId(int pkAutoId){
        return paperDao.selectByCodeId(pkAutoId);
    }
}
