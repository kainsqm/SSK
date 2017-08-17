package cn.sh.ideal.examtask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.sh.ideal.controller.ExamineController;
import cn.sh.ideal.dao.ItExamExampaperExamineDao;
import cn.sh.ideal.model.tExamExampaperExamine;
public class ExamPingFenTask{
	@Resource
	private ItExamExampaperExamineDao  examExampaperExamineDao;
	private static Logger log = Logger.getLogger(ExamPingFenTask.class);
	/********
	 * 每天凌晨两点执行  释放昨天没有评完分的试卷
	 * *******/
	public void executeClean() {
		try {
			List<tExamExampaperExamine> list=examExampaperExamineDao.queryExamPaperByStatus();
			String pkautoId="";
			for (tExamExampaperExamine tExamExampaperExamine : list) {
				pkautoId+=tExamExampaperExamine.getPkAutoId()+",";
			}
			if(pkautoId!=""){
				pkautoId=pkautoId.substring(0,pkautoId.length()-1);
				examExampaperExamineDao.updateExamineStatus(pkautoId);
				log.info("释放昨天没有评完分的试卷examineID为："+pkautoId);
			}
		} catch (Exception e) {
			log.error("释放昨天没有评完分的试卷出现异常："+e.getMessage(),e);
		}
		
		
	}
}
