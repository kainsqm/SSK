/*
 * @(#)Monitor.java 1.0 2011-3-9
 * 
 * Copyright (C) 2007 OnlineCRM
 * 
 */ 
package cn.sh.ideal.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.sh.ideal.action.vo.ForceBean;
import cn.sh.ideal.action.vo.MonitorBean;
import cn.sh.ideal.serivce.MonitorService;

public class MonitorAction extends Action{
	public static Map examMap=new HashMap();
	public static List<ForceBean> forceBeans=new ArrayList();
	
	private MonitorService monitorService;
	protected final Log log = LogFactory.getLog(this.getClass());
	
	public void setMonitorService(MonitorService monitorService) {
		this.monitorService = monitorService;
	}


	/**
	 * 返回值：flg：0正常，3强制提交，4重复登陆关闭，6正常接收到强制提交请求
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		try{
			
			//接收强制交卷的请求
			String method=request.getParameter("method");
			if(method!=null && "forceSubmit".equals(method)){
				String examId=request.getParameter("examId"); 
				String userId=request.getParameter("userId");    
				ForceBean fb=new ForceBean();
				fb.setAccumulator(0);
				fb.setExamId(examId);
				fb.setUserId(userId);
				forceBeans.add(fb);
				
				String output = request.getParameter("callback")+"({'flg':6});";
				response.setContentType("text/html;charset=utf-8");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().println(output);
				return null;
			}
			request.setCharacterEncoding("UTF-8");
			String exampaper_exam_user_id=request.getParameter("kaoshiId");   //考试关联ID
			String userId=request.getParameter("userId");                     //座席工号
			String answerStr=request.getParameter("answerStr");               //考题答案字符串
			String ipAddr=request.getParameter("ipAddr");                     //当前考试的客户端IP地址
			String state=request.getParameter("state");                       //状态   0考试中， 1，正常提交 2，提交异常 3,无响应
			String state2=request.getParameter("state2");                       //状态   0考试中， 1，正常提交 2，提交异常 3,无响应
			state = state2==null?state:state2;
			String remainingTime=request.getParameter("remainingTime");       //剩余时间
			String examId=request.getParameter("examId");                    //考试编号
 			//String examName = new String(request.getParameter("examName").getBytes("UTF-8"),"utf-8");//用request获取URL传递的中文参数  考试名称
 			
 			
 			
			//将传参放入VO
			MonitorBean bean=new MonitorBean();
			if(exampaper_exam_user_id!=null && !"".equals(exampaper_exam_user_id.trim())) bean.setExampaper_exam_user_id(exampaper_exam_user_id);
			else return null;    //参数异常，不做处理
			
			bean.setUserId(userId);
			bean.setAnswerStr(answerStr);
			bean.setIpAddr(ipAddr);
			bean.setState(state);
			bean.setRemainingTime(remainingTime);
			bean.setExamId(examId);
			bean.setAccumulator(0);   //累加器，超过阀值将作为考试故障处理，每次新的心跳都重置为0.
			
			/**
			 * update by Neo 2011-4-26,同台电脑同一时间只能登录一个帐号。防止登录多个代考，也禁止一个帐号被同时登陆多次
			 */
			boolean existFlg=false;
			if("checkDk".equals(method)){
				Collection co= examMap.values();
				for (Iterator iter = co.iterator(); iter.hasNext();) {
					MonitorBean element = (MonitorBean) iter.next();
					
					//如果同一个工号重复登陆系统进行考试，则返回错误标识
					if(userId.equals(element.getUserId())){
						if(!(examId.equals(element.getExamId())&&ipAddr.equals(element.getIpAddr()))){
							existFlg=true;
							break;
						}
					}
					
					//如果同一IP同时多人进行考试，则返回错误标识
					if(ipAddr.equals(element.getIpAddr())){
						if(!(examId.equals(element.getExamId())&&userId.equals(element.getUserId()))){
							existFlg=true;
							break;
						}
					}
					
				}
			}
			if(existFlg){
				String output = request.getParameter("callback")+"({'flg':4});";
				response.setContentType("text/html;charset=utf-8");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().println(output);
				return null;
			}
			//××××××××××××××××××update by Neo 2011-4-26 end×××××××××××××××××××××××××××××××××××××××××××
			
			if("0".equals(bean.getState())){
				//以考试编号为key，将考试信息放入map保存
				examMap.put(exampaper_exam_user_id, bean);
			}else{
				//假如考试结束或者有提交有故障，则记录错误信息和系统时间
				if(!"1".equals(bean.getState()) && !monitorService.updateExamforId(bean.getExampaper_exam_user_id())){
					log.error("修改考试状态为故障时报错，考试编号："+bean.getExampaper_exam_user_id()+"----Exampaper_exam_user_id不存在或者不正确。");
				}
					
					monitorService.addMonitorExam(bean);
					//从容器中移除该考试
					examMap.remove(bean.getExampaper_exam_user_id());
			}
			
			//轮询强制提交的元素容器
			boolean forceFlg=false;
			for(ForceBean fb:forceBeans){
				//加入当前的考试属于强制提交的考试范围
				if(fb.getExamId()!=null && fb.getExamId().equals(bean.getExamId())){
					//如果是整个考试的强制提交，则直接强制提交
					if("******".equals(fb.getUserId())){
						forceFlg=true;
						break;
					}
					//如果不是整个考试的强制提交，则再验证当前的考生是否属于被强制提交范围
					if(fb.getUserId()!=null && fb.getUserId().equals(bean.getUserId())){
						forceFlg=true;
						break;
					}
					
				}
				
			}
			if(forceFlg){
				String output = request.getParameter("callback")+"({'flg':3});";
				response.setContentType("text/html;charset=utf-8");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().println(output);
				return null;
			}
			
	
			String output = request.getParameter("callback")+"({'flg':0});";
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(output);
	
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			log.equals(e1);
		}
		
		return null;
	}
	
	public ActionForward haha(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		return mapping.findForward("ok"); 
	}
	
	/*public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		try{
			//request.setCharacterEncoding("UTF-8");
			String exampaper_exam_user_id=request.getParameter("kaoshiId");   //考试关联ID
			String userId=request.getParameter("userId");                     //座席工号
			String answerStr=request.getParameter("answerStr");               //考题答案字符串
			String ipAddr=request.getParameter("ipAddr");                     //当前考试的客户端IP地址
			String state=request.getParameter("state");                       //状态   0考试中， 1，正常提交 2，提交异常 3,无响应
			String remainingTime=request.getParameter("remainingTime");       //剩余时间
			String examId=request.getParameter("examId");                     //考试编号
			String examName=request.getParameter("examName");                 //考试名称
			
			//将传参放入VO
			MonitorBean bean=new MonitorBean();
			if(exampaper_exam_user_id!=null && !"".equals(exampaper_exam_user_id.trim())) bean.setExampaper_exam_user_id(exampaper_exam_user_id);
			else return null;    //参数异常，不做处理
			
			bean.setUserId(userId);
			bean.setAnswerStr(answerStr);
			bean.setIpAddr(ipAddr);
			bean.setState(state);
			bean.setRemainingTime(remainingTime);
			bean.setExamId(examId);
			bean.setExamName(examName);
			bean.setAccumulator(0);   //累加器，超过阀值将作为考试故障处理，每次新的心跳都重置为0.
			
			
			if("0".equals(bean.getState())){
				//以考试编号为key，将考试信息放入map保存
				examMap.put(exampaper_exam_user_id, bean);
			}else{
				//假如考试结束或者有提交有故障，则记录错误信息和系统时间
				if(!"1".equals(bean.getState()) && !monitorService.updateExamforId(bean.getExampaper_exam_user_id())){
					log.error("修改考试状态为故障时报错，考试编号："+bean.getExampaper_exam_user_id()+"----Exampaper_exam_user_id不存在或者不正确。");
				}
					
					monitorService.addMonitorExam(bean);
					//从容器中移除该考试
					examMap.remove(bean.getExampaper_exam_user_id());
			}
		
		
		
	
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("flg=0");
	
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			log.equals(e1);
		}
		
		return mapping.findForward("ok");
	}*/
	
}

