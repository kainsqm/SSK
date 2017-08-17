package cn.sh.ideal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.ItExampaperDao;
import cn.sh.ideal.dao.ItExampaperRandomDao;
import cn.sh.ideal.dao.ItQuestionsAreaDao;
import cn.sh.ideal.dao.ItQuestionsDao;
import cn.sh.ideal.dao.ItQuestionsRandomselDao;
import cn.sh.ideal.model.TQuestionsArea;
import cn.sh.ideal.model.tExampaper;
import cn.sh.ideal.model.tExampaperRandom;
import cn.sh.ideal.model.tQuestions;
import cn.sh.ideal.model.tQuestionsRandomsel;
import cn.sh.ideal.service.ExampaperRandomService;
@Service
public class ExampaperRandomServiceImpl implements ExampaperRandomService {
	@Autowired
	private ItQuestionsAreaDao questionsAreaDao;
	@Autowired
	private ItQuestionsDao itQuestionsDao;
	@Autowired
	private ItExampaperRandomDao exampaperRandomDao;
	@Autowired
	private ItQuestionsRandomselDao questionsRandomselDao;
	@Autowired
	private ItExampaperDao exampaperDao;
	
	@Override
	public TQuestionsArea selectRandomQuestionAreaById(String userId) {
		// TODO Auto-generated method stub
		return questionsAreaDao.selectRandomQuestionAreaById(userId);
	}


	@Override
	public int selectQuestionCount(tQuestions questions) {
		// TODO Auto-generated method stub
		return itQuestionsDao.selectQuestionCount(questions);
	}


	@Override
	public int insertExampaperRandom(tExampaperRandom exampaperRandom) {
		return exampaperRandomDao.insertSelective(exampaperRandom);
	}


	@Override
	public int insertQuestionsRandomSel(tQuestionsRandomsel questionsRandomsel) {
		return questionsRandomselDao.insertSelective(questionsRandomsel);
		
	}

	@Override
	public int updateRandomExampaper(tExampaper exampaper) {
		// TODO Auto-generated method stub
		return exampaperDao.updateByPrimaryKeySelective(exampaper);
	}

	@Override
	public int deleteRandomExampaper(Integer fkExampaperId) {
		return 	exampaperRandomDao.deleteByExampaperId(fkExampaperId);
	}
	
}
