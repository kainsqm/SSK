package cn.sh.ideal.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.AcceptQualityCheck;
import cn.sh.ideal.model.AcceptQualityCheckInfo;
import cn.sh.ideal.model.AgentScore;
import cn.sh.ideal.model.AgentWorkpaper;
import cn.sh.ideal.model.CdmaTaskComplete;
import cn.sh.ideal.model.MonitorWorkload;
import cn.sh.ideal.model.QC;
import cn.sh.ideal.model.QCDetail;
import cn.sh.ideal.model.QcBusiness;
import cn.sh.ideal.model.RecordInfo;
import cn.sh.ideal.model.Task;
import cn.sh.ideal.model.TaskChapter;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.model.WorkOrder112;
import cn.sh.ideal.model.WorkOrderCdma;
import cn.sh.ideal.service.CnetVerdictService;
import cn.sh.ideal.service.IAgentScoreService;
import cn.sh.ideal.service.IAgentWorkpaperService;
import cn.sh.ideal.service.IQCDetailService;
import cn.sh.ideal.service.IQCService;
import cn.sh.ideal.service.IQcBusinessService;
import cn.sh.ideal.service.IRecordInfoSevice;
import cn.sh.ideal.service.ITask112ChapterService;
import cn.sh.ideal.service.ITaskService;
import cn.sh.ideal.service.IUserInfoService;
import cn.sh.ideal.service.IWorkOrder112Service;
import cn.sh.ideal.service.IWorkOrderCdmaService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.LogUitls;
import cn.sh.ideal.util.PoiExcelUtil;
import cn.sh.ideal.util.ToolString;

import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/controller/qc")
public class QCController {
	private static Logger log = Logger.getLogger(QCController.class);
	@Resource
	private IQCService QCService;
	@Resource
	private IQCDetailService QCDetailService;
	@Resource
	private ITaskService taskService;
	@Resource
	private ITask112ChapterService chapter112Service;
	@Resource
	private IWorkOrder112Service workOrder112Service;
	@Resource
	private IRecordInfoSevice recordInfoService;
	@Resource
	private IAgentWorkpaperService agentService;
	@Resource
	private IUserInfoService userInfoService;
	@Resource
	private IAgentScoreService agentScoreService;
	@Resource
	private IQcBusinessService qcBusinessService;
	@Resource
	private  IWorkOrderCdmaService   workOrderCdma;
	
	@Autowired
	private CnetVerdictService cnetVerdictService;
	
	@RequestMapping(value="/relate",method = RequestMethod.POST)
	public void relate(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String recordId = request.getParameter("recordId");
		Integer recordid = (recordId!=null &&!recordId.equals(""))?Integer.parseInt(recordId):null;
		String workId = request.getParameter("workid");
		Integer workid = (workId!=null &&!workId.equals(""))?Integer.parseInt(workId):null;
		String json="";
		String type=request.getParameter("type");
		//判断该录音是否已经关联工单
		QC qc=QCService.selectByRecordId(recordid);
		if(qc!=null){//已经关联
			json = "{\"status\":\"0\"}";
		}else{//未关联
			/*QC qcnew =new QC();
			qcnew.setRecordId(recordid);
			qcnew.setWorkorderId(workid);
			QCService.insert(qcnew);*/
			//根据工单id获取工单内容
			if("8".equals(type)){
				WorkOrderCdma work=workOrderCdma.getOrderCdma(workid);
				//json = "{\"status\":\"1\",\"key\":"+qcnew.getQcId()+",\"work\":"+JSONObject.fromObject(work)+"}";
				json = "{\"status\":\"1\",\"work\":"+JSONObject.fromObject(work)+"}";	
			}else{
			WorkOrder112 work=workOrder112Service.selectByPrimaryKey(workid);
			//json = "{\"status\":\"1\",\"key\":"+qcnew.getQcId()+",\"work\":"+JSONObject.fromObject(work)+"}";
			json = "{\"status\":\"1\",\"work\":"+JSONObject.fromObject(work)+"}";
			}
		}	
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			log.error("录音关联工单异常："+e.getMessage());
		}
	}
	
	
	/***
	 * 工单关联录音信息
	 * @author niewenqiang
	 * **/
	@RequestMapping(value="/relateRecord",method = RequestMethod.POST)
	public void relateRecord(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String recordId = request.getParameter("recordid");
		Integer recordid = (recordId!=null &&!recordId.equals(""))?Integer.parseInt(recordId):null;
		String workId = request.getParameter("workid");
		Integer workid = (workId!=null &&!workId.equals(""))?Integer.parseInt(workId):null;
		String telsumId=request.getParameter("telsumId");
		Integer telsumid = (telsumId!=null &&!telsumId.equals(""))?Integer.parseInt(telsumId):null;
		String json ="";
		//判断该工单是否已经关联工单
		QC qc=null;
		if(telsumId!=null){
			qc=QCService.selectBytelsumId(telsumid);
		}else{
				qc=QCService.selectByWorkOrderId(workid);
		}
				if(qc!=null){//已经关联
			json = "{\"status\":\"0\"}";
		}else{
		/*	QC qcnew =new QC();
			qcnew.setRecordId(recordid);
			qcnew.setWorkorderId(workid);
			QCService.insert(qcnew);*/
			//根据录音id获取录音内容
			RecordInfo record= recordInfoService.selectByPrimaryKey(recordid);
			json = "{\"status\":\"1\",\"rd\":"+JSONObject.fromObject(record)+"}";
		}
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			log.error("工单关联录音异常："+e.getMessage());
		}
	}
	
	@RequiresPermissions("record:qc")
	@Log(methodname="score",modulename="录音信息查询",funcname="评分",description="评分,{0}", code = "ZJ")
	@RequestMapping(value="/score",method = RequestMethod.POST)
	public void score(HttpServletRequest request,HttpServletResponse response,QCDetail qCDetail){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String smallWorkid = request.getParameter("smallWorkid");
		String bigWorkid = request.getParameter("bigWorkid");
		String recordId = request.getParameter("recordId");
		Integer recordid = (recordId!=null &&!recordId.equals(""))?Integer.parseInt(recordId):null;
		String workorderId = request.getParameter("workorderId");
		Integer workid = (workorderId!=null &&!workorderId.equals(""))?Integer.parseInt(workorderId):null;
		String channelType=qCDetail.getChannelType();
		String qcid = request.getParameter("qcid");
		Integer qcId = (qcid!=null &&!qcid.equals(""))?Integer.parseInt(qcid):null;
		String qctype = request.getParameter("qc_type");
		Integer qc_type = (qctype!=null &&!qctype.equals(""))?Integer.parseInt(qctype):null;
		String json = "{\"status\":\"-1\"}";
			QC qc1=QCService.selectByRecordId(recordid);
				if(qc1!=null && !qc1.getWorkorderId().equals(workid)){
					json = "{\"status\":\"2\"}";//两个质检员同时操作冲突了;进行第一次评分冲突
			    }else if(qc1!=null && qc1.getWorkorderId().equals(workid) && qcId==null){
			    	json = "{\"status\":\"2\"}";//两个质检员同时操作冲突了，进行第一次评分冲突
			    }else{				
					QC qc=new QC();
					//说明是第二次评分
					if(qcId!=null){
						//如果是第二次评分，则除合格程度和处理情况外其他字段和第一次评分一致
						QCDetail qcDetail1=QCDetailService.selectDetailByQcid(qcId);
						if(qcDetail1!=null){
							sencqc(request,qcDetail1,qCDetail,qcId);
						}
						//更新主表
						qc.setQcId(qcId);
						qc.setQcSum(2+"");
						qc.setQcUserId(Integer.parseInt((request.getAttribute(Constans.USER_INFO_USERID).toString())));
						qc.setQcWorkid(request.getAttribute(Constans.USER_INFO_WORKID).toString());
						QCService.updateByPrimaryKey(qc);
						
					}else{//第一次评分
						QC qcnew =new QC();
						if(workorderId==null ||workorderId.equals("")){
							qc_type=1;
						}else{
							qcnew.setWorkorderId(workid);
					
						}
						qcnew.setRecordId(recordid);		
						firstqc(request,qCDetail,smallWorkid,qc_type,qcnew);			
						if("taskrecord".equals(channelType)){ //判断进入该页面的渠道  taskrecord 为任务录音评分 
							Map<String,String> taskMap = new HashMap<String,String>();
							//String recordId=request.getParameter("recordId");
							Task task=taskService.getRecordTask(recordid);//根据录音ID获取该录音相关信息
							if(task!=null){
								taskMap.put("taskId", String.valueOf(task.getTaskId()));
								taskMap.put("qcUser", task.getQcUser());
								taskMap.put("callId", task.getRecordReference());
								taskMap.put("isQc", "1");
								taskMap.put("taskEndTime", task.getTaskEndTime());
								taskMap.put("QcOrDel", "Qc");
								taskMap.put("recordLengthPart",task.getRecordLengthPart());
								taskMap.put("taskuserId", String.valueOf(task.getTaskuserId()));
								taskMap.put("cDate", task.getcDate());
								taskMap.put("qcDCount", task.getQcDCount());
								taskService.updateTaskRecInfo(taskMap);
							}
					}							
					}
					json = "{\"status\":\"1\"}";//评分成功
				
		}
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,录音id:"+recordId}));
		} catch (IOException e) {
			log.error("评分："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
	}
	
	@RequiresPermissions(value = {"112xj:check", "112order:check"}, logical = Logical.OR)
	@Log(methodname="workorderscore",modulename="工单信息查询(112)",funcname="112工单评分",description="评分,{0}", code = "ZJ")
	@RequestMapping(value="/workorderscore",method = RequestMethod.POST)
	public void workorderscore(HttpServletRequest request,HttpServletResponse response,QCDetail qCDetail) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		
		String smallWorkid = request.getParameter("agentworkid");
		//String bigWorkid = request.getParameter("bigWorkid");
		String recordId = request.getParameter("recordId");
		Integer recordid = (recordId!=null &&!recordId.equals(""))?Integer.parseInt(recordId):null;
		String telsumId=request.getParameter("telsumId");
		Integer telsumid = (telsumId!=null &&!telsumId.equals(""))?Integer.parseInt(telsumId):null;
		String workorderId = request.getParameter("workorderId");
		Integer workid = (workorderId!=null &&!workorderId.equals(""))?Integer.parseInt(workorderId):null;
		String json = "{\"status\":\"-1\"}";;
		String channelType=qCDetail.getChannelType();
			QC qc1=null;
			if(telsumId==null){
				qc1=QCService.selectBytelsumId(telsumid);
			}else{
				qc1=QCService.selectByWorkOrderId(workid);
			}
				if(qc1!=null&&StringUtils.isNotEmpty(qc1.getQcSum()) && Integer.parseInt(qc1.getQcSum())>=2){
					json = "{\"status\":\"2\"}";//两个质检员同时操作冲突了
			    }else{
					
					String qcid = request.getParameter("qcid");
					Integer qcId = (qcid!=null &&!qcid.equals(""))?Integer.parseInt(qcid):null;
					
					QC qc=new QC();
					//说明是第二次评分
					if(qcId!=null){
						//如果是第二次评分，则除合格程度和处理情况外其他字段和第一次评分一致
						QCDetail qcDetail1=QCDetailService.selectDetailByQcid(qcId);
						if(qcDetail1!=null){
							sencqc(request,qcDetail1,qCDetail,qcId);
						}
						//更新主表
						qc.setQcId(qcId);
						qc.setQcSum(2+"");
						qc.setQcUserId(Integer.parseInt((request.getAttribute(Constans.USER_INFO_USERID).toString())));
						qc.setQcWorkid(request.getAttribute(Constans.USER_INFO_WORKID).toString());
						QCService.updateByPrimaryKey(qc);
						
					}else{//第一次评分
						QC qcnew =new QC();
					int qc_type=0;
						if(telsumid==null){
							if(recordId==null ||recordId.equals("")){
								qc_type=2;
							}else{
							qcnew.setRecordId(recordid);
							qc_type=7;	
							}
							qcnew.setWorkorderId(workid);
						}else{
							if(recordId==null ||recordId.equals("")){
								qc_type=4;
							}else{
							qcnew.setRecordId(recordid);
							qc_type=9;
							}
							qcnew.setTelsumId(telsumid);
						}
						firstqc(request,qCDetail,smallWorkid,qc_type,qcnew);
						
						if("task112order".equals(channelType)){ //判断进入该页面的渠道  task112order 为任务录音评分 
	                        Map<String,String> taskMap = new HashMap<String,String>();
	                        Map<String,String> map=new HashMap<String, String>(); 
	                        map.put("orderId", workorderId);
	                        List<Task> task=taskService.gettaskby112orderId(map);//根据工单id查询任务信息
	                        if(task!=null){
	                            taskMap.put("taskId", String.valueOf(task.get(0).getTaskId()));
	                            taskMap.put("taskuserId", String.valueOf(task.get(0).getTaskuserId()));
	                            taskMap.put("cDate", task.get(0).getcDate());
	                            taskMap.put("orderId", workorderId);                    
	                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	                            ParsePosition pos = new ParsePosition(0);
	                            ParsePosition pos1 = new ParsePosition(0);
	                            int countDay = DateUtil.getDays(formatter.parse(task.get(0).getTaskStartTime()
	                                    .substring(0, 10), pos), formatter.parse(task.get(0).getTaskEndTime()
	                                    .substring(0, 10), pos1));
	                            String sumtaskorder=((countDay+1)*Integer.parseInt(task.get(0).getCsrTopDCount())+"");//总任务条数
	                            taskMap.put("sumtaskorder", sumtaskorder);
	                            taskMap.put("csrTopDCount", task.get(0).getCsrTopDCount());
	                            taskService.updateTaskorder(taskMap);
	                        }
	                    }else if(channelType.equalsIgnoreCase("chapter112Grade")){ //进入112小结评分
	                        Map<String,String> taskMap = new HashMap<String,String>();
	                        Map<String,String> map=new HashMap<String, String>(); 
	                        map.put("telsum", telsumId);
	                        List<TaskChapter> taskChapter=chapter112Service.gettaskChapterBytelsum(map);//根据工单id查询任务信息
	                        if(!taskChapter.isEmpty() && taskChapter!=null){
	                            taskMap.put("taskId", String.valueOf(taskChapter.get(0).getTaskId()));
	                            taskMap.put("taskuserId", String.valueOf(taskChapter.get(0).getTaskuserId()));
	                            taskMap.put("cDate", taskChapter.get(0).getcDate());
	                            taskMap.put("telsumId", telsumId);                    
	                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	                            ParsePosition pos = new ParsePosition(0);
	                            ParsePosition pos1 = new ParsePosition(0);
	                            int countDay = DateUtil.getDays(formatter.parse(taskChapter.get(0).getTaskStartTime()
	                                    .substring(0, 10), pos), formatter.parse(taskChapter.get(0).getTaskEndTime()
	                                    .substring(0, 10), pos1));
	                            String sumtaskorder=((countDay+1)*Integer.parseInt(taskChapter.get(0).getCsrTopDCount())+"");//总任务条数
	                            taskMap.put("sumtaskorder", sumtaskorder);
	                            taskMap.put("csrTopDCount", taskChapter.get(0).getCsrTopDCount());
	                            chapter112Service.updateTaskChapter(taskMap);
	                        }
	                    }
					}
					
					
							
					json = "{\"status\":\"1\"}";//评分成功
				}
		
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
			if(telsumId!=null){
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,112电话小结id:"+telsumId}));	
			}else{
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,112工单id:"+workorderId}));
			}
		} catch (IOException e) {
			log.error("评分："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
	}
	
	/***
	 * 质检员监听工作量报表清单统计
	 * **/
	@Log(methodname="MonitorWorkload",modulename="质检员监听工作量报表清单统计",funcname="质检员监听工作量报表清单统计",description="质检员监听工作量报表清单统计,{0}", code = "ZJ")
	@RequestMapping(value="/MonitorWorkload",method = RequestMethod.POST)
	public void MonitorWorkload(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String workNo=request.getParameter("workNo"); //工号
		String startTime=request.getParameter("startTime"); //开始日期
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH); 
		month=month+1;
		String date="";
		if(month<10){
			date=year+"-0"+month;
		}else{
			date=year+"-"+month;
		}
	//	String stopTime=request.getParameter("stopTime"); //结束日期
		Map<String, String> map=new HashMap<String, String>();
		map.put("workNo", workNo);
		map.put("startTime", startTime);
		List<MonitorWorkload> wkList=new ArrayList<MonitorWorkload>();
		if(date.equals(startTime)){
			wkList=QCDetailService.MonitorWorkload(map);
		}else{
			wkList=QCDetailService.MonitorWorkloadOld(map);
		}
		
		//int total=workOrder112Service.selectCount(workOrder112);
		String json = "{\"Total\":" + wkList.size() + " , \"Rows\":"
				+ JSONArray.fromObject(wkList).toString() + "}";
		try {
			response.getWriter().print(json.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功"}));
		} catch (IOException e) {
			log.error("质检员监听工作量报表清单统计异常："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
		
	}
	
	
	
	/***
	 * 质检员监听工作量报表清单统计 导出
	 * **/
	@Log(methodname="ExportMonitorWorkload",modulename="质检员监听工作量报表清单统计 导出",funcname="质检员监听工作量报表清单统计导出",description="质检员监听工作量报表清单统计导出,{0}", code = "ZJ")
	@RequestMapping(value="/ExportMonitorWorkload",method = RequestMethod.GET)
	public void ExportMonitorWorkload(HttpServletRequest request,HttpServletResponse response){
		String workNo=request.getParameter("workNo"); //工号
		String startTime=request.getParameter("startTime"); //开始日期
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH); 
		month=month+1;
		String date="";
		if(month<10){
			date=year+"-0"+month;
		}else{
			date=year+"-"+month;
		}
	//	String stopTime=request.getParameter("stopTime"); //结束日期
		Map<String, String> map=new HashMap<String, String>();
		map.put("workNo", workNo);
		map.put("startTime", startTime);
		boolean blag=true;
		JSONObject json=new JSONObject();
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			codedFileName = java.net.URLEncoder.encode("质检员监听工作量报表清单统计", "UTF-8");
			response.setHeader("content-disposition", "attachment;filename="
					+ codedFileName + ".xls");
			fOut = response.getOutputStream();
			List<LinkedHashMap<Object, Object>> dataList=new ArrayList<LinkedHashMap<Object, Object>>();
			if(date.equals(startTime)){
				dataList=QCDetailService.MonitorWorkloadExport(map);
			}else{
				dataList=QCDetailService.MonitorWorkloadOldExport(map);
			}
			PoiExcelUtil.poiCreateExcel2003(dataList, "质检员监听工作量报表清单统计", fOut);
			json.put("true", blag);	
			fOut.flush();
			fOut.close();					
		} catch (IOException e) {
			log.error("导出失败："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
		
	}
	
	
	
	/***
	 * 受理质量检查报表及详单
	 * **/
	@Log(methodname="AcceptQualityCheck",modulename="受理质量检查报表及详单",funcname="受理质量检查报表及详单",description="受理质量检查报表及详单,{0}", code = "ZJ")
	@RequestMapping(value="/AcceptQualityCheck",method = RequestMethod.POST)
	public void AcceptQualityCheck(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String startTime=request.getParameter("startTime"); //开始日期
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH); 
		month=month+1;
		String date="";
		if(month<10){
			date=year+"-0"+month;
		}else{
			date=year+"-"+month;
		}
	//	String stopTime=request.getParameter("stopTime"); //结束日期
		Map<String, String> map=new HashMap<String, String>();
		map.put("startTime", startTime);
		List<AcceptQualityCheck> aqList=new ArrayList<AcceptQualityCheck>();
		if(date.equals(startTime)){			
				aqList=QCDetailService.AcceptQualityCheckTl(map);
		}else{	
				aqList=QCDetailService.AcceptQualityCheckTlOld(map);
		}
		
		//int total=workOrder112Service.selectCount(workOrder112);
		String json = "{\"Total\":" + aqList.size() + " , \"Rows\":"
				+ JSONArray.fromObject(aqList).toString() + "}";
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功"}));
		} catch (IOException e) {
			log.error("受理质量检查报表及详单异常："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
		
	}
	
	
	/***
	 * 受理质量检查报表及详单详情
	 * **/
	@RequestMapping(value="/AcceptQualityCheckgetInfo",method = RequestMethod.POST)
	public void AcceptQualityCheckgetInfo(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String startTime=request.getParameter("startTime"); //开始日期
		String telworkid=request.getParameter("telworkid");//工号
		Map<String, String> map=new HashMap<String, String>();
		map.put("startTime", startTime);
		map.put("telworkid", telworkid);
		List<AcceptQualityCheckInfo> aqList=new ArrayList<AcceptQualityCheckInfo>();		
				aqList=QCDetailService.getAcceptQualityCheckInfo(map);
		String json = "{\"Total\":" + aqList.size() + " , \"Rows\":"
				+ JSONArray.fromObject(aqList).toString() + "}";
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功"}));
		} catch (IOException e) {
			log.error("受理质量检查报表及详单详情异常："+e.getMessage());
		}
		
	}
	/***
	 * 导出受理质量检查报表及详单详情
	 * **/
	@RequestMapping(value="/ExportAcceptQualityCheckgetInfo",method = RequestMethod.GET)
	public void ExportAcceptQualityCheckgetInfo(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String startTime=request.getParameter("startTime"); //开始日期
		String telworkid=request.getParameter("telworkid");//工号
		Map<String, String> map=new HashMap<String, String>();
		map.put("startTime", startTime);
		map.put("telworkid", telworkid);
		List<AcceptQualityCheckInfo> aqList=new ArrayList<AcceptQualityCheckInfo>();		
				aqList=QCDetailService.getAcceptQualityCheckInfo(map);
		String json = "{\"Total\":" + aqList.size() + " , \"Rows\":"
				+ JSONArray.fromObject(aqList).toString() + "}";
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		Calendar a=Calendar.getInstance();
		String year=Integer.toString(a.get(Calendar.YEAR));
		String bh="编号：WLYX/F/JL/A/0/KD-022-"+year;
		try {
			codedFileName = java.net.URLEncoder.encode("受理质量报表及详单详情", "UTF-8");
			response.setHeader("content-disposition", "attachment;filename="
					+ codedFileName + ".xls");
			fOut = response.getOutputStream();
			List<LinkedHashMap<Object, Object>> dataList = QCDetailService.exportAcceptQualityCheckInfo(map); // 获取结果集
			PoiExcelUtil.QcBusinessCreateExcel2003(dataList, "受理质量报表及详单详情", fOut,bh);
			fOut.flush();
			fOut.close();					
		} catch (IOException e) {
			log.error("导出失败："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
		
	}
	
	
	
	/***
	 * 受理质量检查报表及详单 导出
	 * **/
	@Log(methodname="ExportAcceptQualityCheck",modulename="受理质量检查报表及详单导出",funcname="受理质量检查报表及详单导出",description="受理质量检查报表及详单导出,{0}", code = "ZJ")
	@RequestMapping(value="/ExportAcceptQualityCheck",method = RequestMethod.GET)
	public void ExportAcceptQualityCheck(HttpServletRequest request,HttpServletResponse response){
		String isTl=request.getParameter("isTl"); //是否是Tl9000
		String startTime=request.getParameter("startTime"); //开始日期
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH); 
		month=month+1;
		String date="";
		if(month<10){
			date=year+"-0"+month;
		}else{
			date=year+"-"+month;
		}
	//	String stopTime=request.getParameter("stopTime"); //结束日期
		Map<String, String> map=new HashMap<String, String>();
		map.put("startTime", startTime);
		boolean blag=true;
		JSONObject json=new JSONObject();
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			codedFileName = java.net.URLEncoder.encode("受理质量检查报表及详单", "UTF-8");
			response.setHeader("content-disposition", "attachment;filename="
					+ codedFileName + ".xls");
			fOut = response.getOutputStream();
			List<LinkedHashMap<Object, Object>> dataList=new ArrayList<LinkedHashMap<Object, Object>>();
			if(date.equals(startTime)){
					dataList=QCDetailService.AcceptQualityCheckTlExport(map);				
			}else{
				dataList=QCDetailService.AcceptQualityCheckTlOldExport(map);			
			}
			PoiExcelUtil.poiCreateExcel2003(dataList, "受理质量检查报表及详单", fOut);
			json.put("true", blag);	
			fOut.flush();
			fOut.close();					
		} catch (IOException e) {
			log.error("导出失败："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
		
	}
	
	
	
	
	@Log(methodname="selectAgentscore",modulename="报表三",funcname="受理员质量总分查询列表",description="受理员质量总分查询列表,{0}", code = "ZJ")
	@RequestMapping(value="/selectAgentscore",method = RequestMethod.POST)
	public void selectAgentscore(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		AgentScore agentscore=new AgentScore(); 
		String isTl9000=request.getParameter("isTl9000");
		String qctime=request.getParameter("qctime");
		String smailworkid=request.getParameter("smailworkid");
		if("".equals(isTl9000)||isTl9000==null){
			isTl9000="";
		}
		agentscore.setIsTl9000(isTl9000);
		agentscore.setQctime(qctime);
		agentscore.setSmailworkid(smailworkid);
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM");
		Date da=new Date();
		String qcdate=df.format(da);
		List<AgentScore> listagent=null;
		int total=0;
		if(qcdate.equals(qctime)){
			listagent=agentScoreService.nowselectAgentscore(agentscore);
			total=agentScoreService.nowselectAgentscorecount(agentscore);
		}else{
		listagent=agentScoreService.selectAgentscore(agentscore);
		total=agentScoreService.selectAgentscorecount(agentscore);
		}
		if(listagent!=null){		
			for (AgentScore agentScore2 : listagent) {
				agentScore2.setZldf(100+((agentScore2.getLxcw()*-5)+(agentScore2.getCp()*-5)+(agentScore2.getLp()*-5)+(agentScore2.getYwbs()*-2)
									+(agentScore2.getYwbl()*-1)+(agentScore2.getWfl()*-1)+(agentScore2.getYwbgf()*-3)+(agentScore2.getTdsy()*-5)
									+(agentScore2.getFlxzcw()*-3)+(agentScore2.getWfxc()*-5)+(agentScore2.getBsbgf()*-2)+(agentScore2.getJndmc()*-2)
									+(agentScore2.getPdcs()*-2)+(agentScore2.getCzbgf()*-2)+(agentScore2.getYxbg()*-5)+(agentScore2.getBzxxc()*-3)
									+(agentScore2.getYdbgf()*-3)+(agentScore2.getGkcp()*-5)+(agentScore2.getGkfl()*-3)+(agentScore2.getLgk()*-5)
									+(agentScore2.getYwgkbs()*-2)
									));
			}
			
		}
		
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(listagent).toString() + "}";
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功"}));
		} catch (IOException e) {
			log.error("获取受理员质量总分查询列表异常："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
			
	}
	@Log(methodname="exportExcel",modulename="报表三",funcname="受理员质量总分表导出",description="受理员质量总分表导出,{0}", code = "ZJ")
	@RequestMapping(value="/exportExcel",method = RequestMethod.GET)
	 public void  exportExcel(HttpServletRequest request,HttpServletResponse response){	 
		AgentScore agentscore=new AgentScore(); 
		String isTl9000=request.getParameter("isTl9000");
		String qctime=request.getParameter("qctime");
		String smailworkid=request.getParameter("smailworkid");
		if("".equals(isTl9000)||isTl9000==null){
			isTl9000="";
		}
		agentscore.setIsTl9000(isTl9000);
		agentscore.setQctime(qctime);
		agentscore.setSmailworkid(smailworkid);
		boolean blag=true;
		JSONObject json=new JSONObject();
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM");
		Date da=new Date();
		String qcdate=df.format(da);
		try {
			Map<String, String> map = new HashMap<String, String>();
			codedFileName = java.net.URLEncoder.encode("受理员质量总分表", "UTF-8");
			response.setHeader("content-disposition", "attachment;filename="
					+ codedFileName + ".xls");
			fOut = response.getOutputStream();
			List<LinkedHashMap<Object, Object>> dataList =new ArrayList<LinkedHashMap<Object,Object>>();
			if(qcdate.equals(qctime)){
				dataList=agentScoreService.exportselectAgentscorenew(agentscore);
			}else{
				dataList=agentScoreService.exportselectAgentscore(agentscore); // 获取结果集
			}				
			PoiExcelUtil.poiCreateExcel2003(dataList, "受理员质量总分表", fOut);
			json.put("true", blag);	
			fOut.flush();
			fOut.close();					
		} catch (IOException e) {
			log.error("导出失败："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
		
	 }

	@Log(methodname="getQcBusiness",modulename="报表四",funcname="质检关联业务查询列表",description="质检关联业务查询列表,{0}", code = "ZJ")
	@RequestMapping(value="/getQcBusiness",method = RequestMethod.POST)
	 public void getQcBusiness(HttpServletRequest request,HttpServletResponse response){
		
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		QcBusiness qcbus=new QcBusiness();
		String qcstartdatetime=request.getParameter("qcstartdatetime");
		String qcstopdatetime=request.getParameter("qcstopdatetime");
		String agentName=request.getParameter("agentName");
		String isTl9000=request.getParameter("isTl9000");	
		qcbus.setQcstartdatetime(qcstartdatetime);
		qcbus.setQcstopdatetime(qcstopdatetime);
		qcbus.setAgentName(agentName);
		qcbus.setIsTl9000(isTl9000);
		List<QcBusiness> listqcbus=qcBusinessService.listqcbusiness(qcbus);
		int total=qcBusinessService.selectQcBusinesscount(qcbus);
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(listqcbus).toString() + "}";
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功"}));
		} catch (IOException e) {
			log.error("获取质检关联业务列表异常："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
	}
	@Log(methodname="QcbusinessExcel",modulename="报表四",funcname="质检关联业务表导出",description="质检关联业务表导出,{0}", code = "ZJ")
	@RequestMapping(value="/QcbusinessExcel",method = RequestMethod.GET)
	 public void  QcbusinessExcel(HttpServletRequest request,HttpServletResponse response){	 
		QcBusiness qcbus=new QcBusiness();
		String qcstartdatetime=request.getParameter("qcstartdatetime");
		String qcstopdatetime=request.getParameter("qcstopdatetime");
		String agentName=request.getParameter("agentName");
		String isTl9000=request.getParameter("isTl9000");
		qcbus.setIsTl9000(isTl9000);
		qcbus.setQcstartdatetime(qcstartdatetime);
		qcbus.setQcstopdatetime(qcstopdatetime);
		qcbus.setAgentName(agentName);
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		Calendar a=Calendar.getInstance();
		String year=Integer.toString(a.get(Calendar.YEAR));
		String bh="编号：WLYX/F/JL/A/0/KD-022-"+year;
		try {
			Map<String, String> map = new HashMap<String, String>();
			codedFileName = java.net.URLEncoder.encode("质检关联业务表", "UTF-8");
			response.setHeader("content-disposition", "attachment;filename="
					+ codedFileName + ".xls");
			fOut = response.getOutputStream();
			List<LinkedHashMap<Object, Object>> dataList = qcBusinessService.exportselectQcBusiness(qcbus); // 获取结果集
			PoiExcelUtil.QcBusinessCreateExcel2003(dataList, "质检关联业务表", fOut,bh);
			fOut.flush();
			fOut.close();					
		} catch (IOException e) {
			log.error("导出失败："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
		
	 }
	
	@RequestMapping(value="/workAgentscoreInfo",method = RequestMethod.POST)
	public void workAgentscoreInfo(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String qcworkid=request.getParameter("qcworkid"); //小工号
		String qctime=request.getParameter("qctime"); //质检时间
		Map<String,String> map=new HashMap<String, String>();
		map.put("qctime", qctime);
		map.put("qcworkid", qcworkid);
		List<AgentScore> list = agentScoreService.getworkAgentscoreInfo(map);
		String json = "{\"Total\":" + list.size() + " , \"Rows\":"
				+ JSONArray.fromObject(list).toString() + "}";
		try{
		System.out.println(json.toString());	
		response.getWriter().print(json.toString());
		}catch (Exception e) {
			log.error("导出失败："+e.getMessage());
		}
	}
	
	
	/**
	 * @author lk 2016/04/07
	 * @param request
	 * @param response
	 * @param qCDetail
	 * c网工单评分
	 */
	@RequiresPermissions(value = {"cworder:check", " cwxj:check"}, logical = Logical.OR)
	@Log(methodname="workordercwQC",modulename="工单信息查询(c网)",funcname="工单评分",description="c网工单评分,{0}", code = "ZJ")
	@RequestMapping(value="/workordercwQC",method = RequestMethod.POST)
	public void workordercwQC(HttpServletRequest request,HttpServletResponse response,QCDetail qCDetail) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");		
		String smallWorkid = request.getParameter("agentworkid");
		//String bigWorkid = request.getParameter("bigWorkid");
		
		String  telsumId=request.getParameter("telsumId");//电话小结id
		try {
		String recordId = request.getParameter("recordId");
		Integer recordid = (recordId!=null &&!recordId.equals(""))?Integer.parseInt(recordId):null;
		String serialCdma = request.getParameter("serialCdma");//c网工单id
		Integer serialcdma = (serialCdma!=null &&!serialCdma.equals(""))?Integer.parseInt(serialCdma):null;
		String json = "{\"status\":\"-1\"}";
			QC qc1=QCService.selectByWorkOrderId(serialcdma);
				if(qc1!=null&&StringUtils.isNotEmpty(qc1.getQcSum()) && Integer.parseInt(qc1.getQcSum())>=2){
					json = "{\"status\":\"2\"}";//两个质检员同时操作冲突了
			    }else{
					
					String qcid = request.getParameter("qcid");
					Integer qcId = (qcid!=null &&!qcid.equals(""))?Integer.parseInt(qcid):null;		
					QC qc=new QC();
					//说明是第二次评分
					if(qcId!=null){
						//如果是第二次评分，则除合格程度和处理情况外其他字段和第一次评分一致
						QCDetail qcDetail1=QCDetailService.selectDetailByQcid(qcId);
						if(qcDetail1!=null){
							sencqc(request,qcDetail1,qCDetail,qcId);
						}
						//更新主表
						qc.setQcId(qcId);
						qc.setQcSum(2+"");
						qc.setQcUserId(Integer.parseInt((request.getAttribute(Constans.USER_INFO_USERID).toString())));
						qc.setQcWorkid(request.getAttribute(Constans.USER_INFO_WORKID).toString());
						QCService.updateByPrimaryKey(qc);
						
					}else{//第一次评分
						QC qcnew =new QC();
						qcnew.setRecordId(recordid);
						int qc_type=0;
						if(telsumId!=null){
							if(recordId==null ||recordId.equals("")){
								qc_type=5;
							}else{	
								qc_type=10;
							}
							qcnew.setTelsumId(Integer.parseInt(telsumId));
						}else{
							if(recordId==null ||recordId.equals("")){
								qc_type=3;
							}else{
								qc_type=8;
							}
							qcnew.setWorkorderId(serialcdma);
						}
						firstqc(request,qCDetail,smallWorkid,qc_type,qcnew);
						//如果从任务领取或者完成页面直接进入该页面评分 则修改该质检员完成任务数
						String channelType=qCDetail.getChannelType();
						if("taskcworder".equals(channelType)){ //判断进入该页面的渠道  task112order 为任务录音评分 
	                        Map<String,String> taskMap = new HashMap<String,String>();
	                        Map<String,String> map=new HashMap<String, String>(); 
	                        map.put("orderId", serialCdma);
	                        List<Task> task=taskService.gettaskbycworderId(map);//根据工单id查询任务信息
	                        if(task!=null){
	                            taskMap.put("taskId", String.valueOf(task.get(0).getTaskId()));
	                            taskMap.put("taskuserId", String.valueOf(task.get(0).getTaskuserId()));
	                            taskMap.put("cDate", task.get(0).getcDate());
	                            taskMap.put("orderId", serialCdma);                    
	                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	                            ParsePosition pos = new ParsePosition(0);
	                            ParsePosition pos1 = new ParsePosition(0);
	                            int countDay = DateUtil.getDays(formatter.parse(task.get(0).getTaskStartTime()
	                                    .substring(0, 10), pos), formatter.parse(task.get(0).getTaskEndTime()
	                                    .substring(0, 10), pos1));
	                            String sumtaskorder=((countDay+1)*Integer.parseInt(task.get(0).getCsrTopDCount())+"");//总任务条数
	                            taskMap.put("sumtaskorder", sumtaskorder);
	                            taskMap.put("csrTopDCount", task.get(0).getCsrTopDCount());
	                            taskService.updatecwTaskorder(taskMap);
	                        }
	                    }else if("taskcdma".equals(channelType)){ //判断进入该页面的渠道  taskcdma 为任务电话小结评分 
							Map<String,String> taskMap = new HashMap<String,String>();
							CdmaTaskComplete cdmaTask=cnetVerdictService.getTaskInfoByCdmaAutoId(Integer.parseInt(telsumId));//根据电话小结id查询任务信息
							if(cdmaTask!=null){
								taskMap.put("taskId", String.valueOf(cdmaTask.getTaskId()));
								taskMap.put("taskuserId", String.valueOf(cdmaTask.getTaskuserId()));
								taskMap.put("cDate", cdmaTask.getcDate());
								taskMap.put("cdmaId", telsumId);					
								SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
								ParsePosition pos = new ParsePosition(0);
								ParsePosition pos1 = new ParsePosition(0);
								int	countDay = DateUtil.getDays(formatter.parse(cdmaTask.getTaskStartTime()
										.substring(0, 10), pos), formatter.parse(cdmaTask.getTaskEndTime()
										.substring(0, 10), pos1));
								String sumtaskorder=((countDay+1)*Integer.parseInt(cdmaTask.getQcDCount())+"");//总任务条数
								taskMap.put("sumtaskorder", sumtaskorder);
								taskMap.put("csrTopDCount", cdmaTask.getQcDCount());
								cnetVerdictService.updateTaskCdma(taskMap);
							}
							
						}
					}
					json = "{\"status\":\"1\"}";//评分成功
				}
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,c网工单id:"+serialCdma}));
		} catch (IOException e) {
			log.error("c网工单评分出现异常："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
		
		
	}
	/**
	 * @author lk 2016/04/07
	 * @param request
	 * @param response
	 * @param qCDetail
	 * 无工单无录音评分
	 */
	
	@RequiresPermissions("none:check")
	@Log(methodname="noneQC",modulename="质检评分",funcname="无工单/录音评分查询",description="无工单/录音评分,{0}", code = "ZJ")
	@RequestMapping(value="/noneQC",method = RequestMethod.POST)
	public void noneQC(HttpServletRequest request,HttpServletResponse response,QCDetail qCDetail) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");		
					String qcid = request.getParameter("qcid");
					Integer qcId = (qcid!=null &&!qcid.equals(""))?Integer.parseInt(qcid):null;
					String workid=request.getParameter("agentworkid");
					String json=null;
					QC qc=new QC();
					//说明是第二次评分
					if(qcId!=null){
						//如果是第二次评分，则除合格程度和处理情况外其他字段和第一次评分一致
						QCDetail qcDetail1=QCDetailService.selectDetailByQcid(qcId);
						if(qcDetail1!=null){
							sencqc(request,qcDetail1,qCDetail,qcId);
						}
						//更新主表
						qc.setQcId(qcId);
						qc.setQcSum(2+"");
						qc.setQcUserId(Integer.parseInt((request.getAttribute(Constans.USER_INFO_USERID).toString())));
						qc.setQcWorkid(request.getAttribute(Constans.USER_INFO_WORKID).toString());
						QCService.updateByPrimaryKey(qc);
						
					}else{//第一次评分
						QC qcnew =new QC();
						firstqc(request,qCDetail,workid,6,qcnew);
					}
					json = "{\"status\":\"1\"}";//评分成功	
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功"}));
		} catch (IOException e) {
			log.error("评分："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
		
		
	}
	
	
	/**
	 * @author lk  第二次评分公共方法
	 * 2017/6/22
	 */
	public void  sencqc(HttpServletRequest request,QCDetail qcDetail1,QCDetail qCDetail,Integer qcId){	
		String desdis=qCDetail.getDealDis();
		qCDetail=qcDetail1;
		qCDetail.setQualityLevel(Constans.PASS);
		qCDetail.setDealDis(desdis);	
		qCDetail.setQcId(qcId);
		qCDetail.setQcNum(2+"");
		qCDetail.setQcUserId(Integer.parseInt((request.getAttribute(Constans.USER_INFO_USERID).toString())));
		qCDetail.setQcUserWorkId(request.getAttribute(Constans.USER_INFO_WORKID).toString());
		QCDetailService.insert(qCDetail);	
	}
	/**
	 * @author lk  第一次评分公共方法
	 * 2017/6/22
	 */
	public void firstqc(HttpServletRequest request,QCDetail qCDetail,String workid,Integer qc_type,QC qcnew){
		//合格程度如果为不合格情况，则发送问题反馈单
		if(qCDetail.getQualityLevel().equals(Constans.NOT_PASS)){
			//发送问题反馈单，并生成反馈单id
			AgentWorkpaper agent=new  AgentWorkpaper();
			//根据大工号、小工号获取userid
			UserInfo user=new UserInfo();		
			if(workid!=null&&!"".equals(workid)){	
				user.setSmailWorkid(ToolString.subNumber(workid));
			}
			Integer userid=userInfoService.selectUserId(user);
			agent.setAgentuserid(userid);
			String qcuserid =request.getAttribute(Constans.USER_INFO_USERID).toString();
			if(qcuserid!=null&&!"".equals(qcuserid)){
				agent.setQc_user_id(Integer.parseInt(qcuserid));
			}
			agentService.addagentWorkpaper(agent);
			qcnew.setAgentWorkPaperId(agent.getAgentworkpaperid());
		}
		qcnew.setQcSum(1+"");
		qcnew.setQcUserId(Integer.parseInt((request.getAttribute(Constans.USER_INFO_USERID).toString())));
		qcnew.setQcWorkid(request.getAttribute(Constans.USER_INFO_WORKID).toString());
		qcnew.setAgentWorkid(ToolString.subNumber(workid));
		qcnew.setQc_type(qc_type);
		QCService.insert(qcnew);
		
		qCDetail.setQcId(qcnew.getQcId());
		qCDetail.setQcNum(1+"");
		qCDetail.setQcUserId(Integer.parseInt((request.getAttribute(Constans.USER_INFO_USERID).toString())));
		qCDetail.setQcUserWorkId(request.getAttribute(Constans.USER_INFO_WORKID).toString());
		QCDetailService.insert(qCDetail);
	}
}
