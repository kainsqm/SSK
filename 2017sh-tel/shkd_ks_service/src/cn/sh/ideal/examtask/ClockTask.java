package cn.sh.ideal.examtask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.sh.ideal.dao.ItExamdNoanswerMsgDao;
import cn.sh.ideal.util.TSysOperFileServer;

public class ClockTask{
	@Resource
	private ItExamdNoanswerMsgDao examedNoAnswerMsgDao;
	private static Logger log = Logger.getLogger(ExamPingFenTask.class);

	public void executeClean() {
		String dirpath="ExamResultDir";
		//int dateNum=30;
		int dateNum=0;
		String allPath = this.getClass().getResource("").toString();
		if(allPath!=null && !allPath.equals("")&&allPath.indexOf("/WEB-INF")!=-1){
			try {
			String path = allPath.substring(6,allPath.indexOf("/WEB-INF")+1);
			TSysOperFileServer.deleteFileByDate(path+dirpath, dateNum);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -dateNum);
			Map<String,String> map = new HashMap<String,String>();
			map.put("submitDate", format.format(calendar.getTime()));
			examedNoAnswerMsgDao.deleteByDate(map);
			} catch (Exception e) {
				log.error("定时任务清除未达题出现异常:"+e.getMessage(),e);
			}
		}
	}
}
