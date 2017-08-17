package cn.sh.ideal.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.ItExampaperDao;
import cn.sh.ideal.dao.ItExampaperQuestionsDao;
import cn.sh.ideal.dao.ItQuestionsAreaDao;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.TQuestionsArea;
import cn.sh.ideal.model.tExampaper;
import cn.sh.ideal.model.tExampaperQuestions;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.OptionQuestionService;
import cn.sh.ideal.util.ArrayDistinct;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.LogUitls;
@Service
public class OptionQuestionServiceImpl implements OptionQuestionService {
	@Autowired
	private ItQuestionsAreaDao questionsAreaMapper; 
	
	@Autowired
	private ItExampaperDao exampaperDao;

	@Autowired
	private ItExampaperQuestionsDao exampaperQuestionsDao;
	@Override
	public int insertQuestionAreaExaMaprer(TQuestionsArea questionsArea) {
		return questionsAreaMapper.insertSelective(questionsArea);
	}

	@Override
	public TQuestionsArea selectQuestionAreaById(String userId) {
		
		return questionsAreaMapper.selectQuestionAreaById(userId);
	}

	@Override
	public int updateQuestionAreaExaMaprer(TQuestionsArea area) {
		return questionsAreaMapper.updateQuestionAreaExaMaprer(area);
	}

	@Override
	public int insertExampaper(tExampaper exampaper) {
		return exampaperDao.insert(exampaper);
	}

	@Override
	public int insertExampaperQuestions(tExampaperQuestions exampaperQuestions) {
		return exampaperQuestionsDao.insertSelective(exampaperQuestions);
	}

	@Override
	public TQuestionsArea selectRandomQuestionAreaById(String userId) {
		return  questionsAreaMapper.selectRandomQuestionAreaById(userId);
	}

	@Override
	public TQuestionsArea selectUpdateQuestionAreaById(String userId) {
		// TODO Auto-generated method stub
		return questionsAreaMapper.selectUpdateQuestionAreaById(userId);
	}

	@Override
	public int updateQuestionAreaExaMaprer(TQuestionsArea area,
			String checkedCustomer) {
		area.setEnabled(Constans.FAILE);
		questionsAreaMapper.updateQuestionAreaExaMaprer(area);
		// 对考题Id数组进行去重
		String args1[] = area.getQuestionId().split(",");
		String args2[] = checkedCustomer.split(",");
		// 删除用户选择的数据
		String questionId = ArrayDistinct.removeArray(args1, args2);
		area.setEnabled(Constans.SUCCESS);
		area.setQuestionId(questionId);// 将去重后的考题数组重新写入到考题字段中
		area.setCreateQuestionTime(DateUtil.getDateTimeStr(new Date()));
		area.setUpdateCount(area.getUpdateCount() + 1);
		int count=questionsAreaMapper.insertSelective(area);
		
		return count;
	}

	@Override
	public int updateQuestionAreaExaMaprer(TQuestionsArea area,
			String checkedCustomer, vSysuser user) {
		
		if (area != null) {
			// 修改查询到的考题状态为无效
			area.setEnabled(Constans.FAILE);
			questionsAreaMapper.updateQuestionAreaExaMaprer(area);
			// 对考题Id数组进行去重
			String args1[] = area.getQuestionId().split(",");
			String args2[] = checkedCustomer.split(",");
			String questionId = ArrayDistinct.repetArray(args1, args2);
			area.setEnabled(Constans.SUCCESS);
			area.setQuestionId(questionId);// 将去重后的考题数组重新写入到考题字段中
			area.setCreateQuestionTime(DateUtil.getDateTimeStr(new Date()));
			area.setUpdateCount(area.getUpdateCount() + 1);
			questionsAreaMapper.insertSelective(area);
		
		} else {
			TQuestionsArea questionsArea = new TQuestionsArea();
			questionsArea.setUserId(user.getUserId().toString());// 先写死后期在改用户Id
			questionsArea.setUpdateCount(1);
			questionsArea.setIsTemplate(Constans.EXAMPAPERUPDATE);// 是否是模板试卷 0：否 1：是,2修改试题
			questionsArea.setCreateQuestionTime(DateUtil
					.getDateTimeStr(new Date()));// 当前新增时间
			questionsArea.setEnabled(Constans.SUCCESS);// 是否有效 0无效 1:有效
			questionsArea.setQuestionId(checkedCustomer);// 考题数据，多个考题,分割
			questionsAreaMapper.insertSelective(questionsArea);
		}
		return 0;
	}
	
}
