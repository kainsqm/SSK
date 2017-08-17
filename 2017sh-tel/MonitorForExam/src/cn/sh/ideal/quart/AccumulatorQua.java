/*
 * @(#)AccumulatorQua.java 1.0 2011-3-11
 * 
 * Copyright (C) 2007 OnlineCRM
 * 
 */ 
package cn.sh.ideal.quart;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.sh.ideal.action.MonitorAction;
import cn.sh.ideal.action.vo.ForceBean;
import cn.sh.ideal.action.vo.MonitorBean;
import cn.sh.ideal.serivce.MonitorService;

/**
 * 30秒执行一次，用于监查每场考生考试的状态是否正常
 * AccumulatorQua.java
 * <p></p>
 * Created on 2011-3-11
 * Modification history
 * <p></p>
 * @author Neo
 * @vesion 1.0
 * @since 1.0
 *
 */
public class AccumulatorQua extends QuartzJobBean{
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private MonitorService monitorService;
	public void setMonitorService(MonitorService monitorService) {
		this.monitorService = monitorService;
	}


	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		
		Map examMap=MonitorAction.examMap;
		Collection col=examMap.values();
		//轮询容器中的每个考生考卷，将累加值+1，如果超过阀值依然没有更新，则设置考卷为故障
		for (int i=0;i<col.size();i++){
			MonitorBean bean=(MonitorBean)col.toArray()[i];
			int acc=bean.getAccumulator();
			bean.setAccumulator(++acc);
			
			//加入超过了2分钟，此考生的考试依然没有发出心跳，则认为该考生的考试已经故障，将该试卷的flg设置为故障并在容器中移除该考生
			if(acc>=5 || !"0".equals(bean.getState())){
				if(!monitorService.updateExamforId(bean.getExampaper_exam_user_id())){
					log.error("修改考试状态为故障时报错，考试编号："+bean.getExampaper_exam_user_id()+"----Exampaper_exam_user_id不存在或者不正确。");
				}
					//将此次考试的最后答题情况保存到数据库，并设置为故障超时状态
					if("0".equals(bean.getState()))bean.setState("3");
					
					monitorService.addMonitorExam(bean);
					
					//从容器中移除该考试
					examMap.remove(bean.getExampaper_exam_user_id());

				
			}
		}
		
		//add by Neo at2011-4-27  将强制提交容器中的元素，超过30秒的清除掉   
		List<ForceBean> forceBeans=MonitorAction.forceBeans;
		if(forceBeans!=null){
			int size = forceBeans.size();
			for(int i=size-1;i>=0;i--){
				ForceBean fb = forceBeans.get(i);
				int acc = fb.getAccumulator();
				fb.setAccumulator(++acc);
				if(acc>2){ //每个强制提交项最多保留一分钟
					forceBeans.remove(fb);
				}
			}
		}
		/*for(ForceBean fb:forceBeans){
			int acc=fb.getAccumulator();
			fb.setAccumulator(++acc);
			if(acc>2){ //每个强制提交项最多保留一分钟
				forceBeans.remove(fb);
			}
		}*/
		
	}
}

