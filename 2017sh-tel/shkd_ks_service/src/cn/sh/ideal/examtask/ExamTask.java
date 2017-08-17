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
import cn.sh.ideal.dao.ItExamJoinDao;
import cn.sh.ideal.model.tExam;

/*****
 * 考试状态的自动维护
 * 
 * @author niewenqiang
 * @date 2017-5-19
 * *******/
public class ExamTask {
	@Resource
	private ItExamJoinDao examJoinDao;
	private static Logger log = Logger.getLogger(ExamTask.class);

	// 每30分钟执行一次
	public void executeClean() {
		// 取出所有考试状态为'1:考试中'，开考时间距当前系统时间已超过考试容许的最大时间的考试
		List<tExam> examList = examJoinDao.queryStartTimeSysExam();
		try {
			// 判断答案表中是否有该考试记录
			for (int i = 0; i < examList.size(); i++) {
				tExam exam = examList.get(i);
				int pkAutoId = exam.getPkAutoId();
				int count = examJoinDao.getCountResultByExamineId(pkAutoId);
				if (count > 0) { // 表示答案表中存在该考试答案 将考试状态改成'2:已考完'
					int examTimeLength=exam.getExamTimeLength();
					examJoinDao.updateExamStatusByOver(pkAutoId, examTimeLength);
				} else { // 否则，代表考生没有正常完成考试，将考试的状态改成'-1:待考中'
					examJoinDao.updateExamStatusByStart(pkAutoId);
				}
			}
		} catch (Exception e) {
			log.error("考试状态的自动维护出现异常：" + e.getMessage(), e);
		}
	}
}
