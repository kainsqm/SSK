package cn.sh.ideal.controller;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.Modeltype;
import cn.sh.ideal.model.QC;
import cn.sh.ideal.model.QCDetail;
import cn.sh.ideal.model.RecordInfo;
import cn.sh.ideal.model.SysCode;
import cn.sh.ideal.model.Task;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.model.WorkOrder112;
import cn.sh.ideal.model.WorkOrderCdma;
import cn.sh.ideal.model.WorkOrderCdmaSum;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.IQCDetailService;
import cn.sh.ideal.service.IQCService;
import cn.sh.ideal.service.IRecordInfoSevice;
import cn.sh.ideal.service.ISecondaryQcService;
import cn.sh.ideal.service.ISysCodeService;
import cn.sh.ideal.service.ITaskService;
import cn.sh.ideal.service.IUserInfoService;
import cn.sh.ideal.service.IWorkOrderCdmaService;
import cn.sh.ideal.service.IWorkOrderCdmaSumService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.LogUitls;
import cn.sh.ideal.util.ToolString;
import cn.sh.ideal.util.Util;

import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/controller/ordercdma")
public class WorkOrderCdmaController {
		
	
	private static Logger log = Logger.getLogger(WorkOrderCdmaController.class);
	@Resource
	private  IWorkOrderCdmaService   workOrderCdma;
	@Resource
	private IUserInfoService userInfoService;
	@Resource
	private IQCService QCService;
	@Resource
	private IRecordInfoSevice recordInfoService;
	@Resource
	private IQCDetailService QCDetailService;
	@Resource
	private  ISysCodeService sysCodeService;
	@Resource
	private IWorkOrderCdmaSumService workOrderCdmaSumService;
	@Resource
	private ISecondaryQcService secondaryQcService;
	@Resource
	private ITaskService taskService;
	
	@RequiresPermissions("cworder:query")
	@RequestMapping(value="/togetwkordercdmaList",method=RequestMethod.GET)
	public String togetwkordercdmaList(){		
		return "paperList_cw";
	}
	
	/**
	 * @author lk 2017/03/20
	 * @param request
	 * @param response
	 * @param workOrdercdma
	 * 获取c网工单列表
	 */
	@RequestMapping(value="/getwkordercdmaList",method = RequestMethod.POST)
	public void getwkordercdmaList(HttpServletRequest request,HttpServletResponse response,WorkOrderCdma workOrdercdma){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		List<WorkOrderCdma> wkList=workOrderCdma.selectByPrimaryKey(workOrdercdma);
		int total=workOrderCdma.countselectByPrimaryKey(workOrdercdma);
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(wkList).toString() + "}";	
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			log.error("获取工单列表异常："+e.getMessage());
		}
		
	}
	
	/**
	 * @author lk 2017/03/31
	 * @param request
	 * @param response
	 * @param workOrdercdma
	 * c网工单评分信息查询
	 */
	@RequiresPermissions(value = {"cworder:qc", "cwxj:qc"}, logical = Logical.OR)
	@RequestMapping(value="qcwkordercdma",method=RequestMethod.POST)
	  public String  qcwkordercdma(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		String autoid=null;
		try{
		autoid=request.getParameter("autoid");	//c网电话小结主键
		String serialCdma=request.getParameter("serialCdma");
		String receiptOpId=request.getParameter("receiptOpId");
		String complaintTime=request.getParameter("complaintTime");
		String dirNum=request.getParameter("dirNum");
		//WorkOrderCdma order	=workOrderCdma.getOrderCdma(Integer.parseInt(serialCdma));
		WorkOrderCdmaSum order=null;
		request.setAttribute("autoid", autoid);
		request.setAttribute("serialCdma", serialCdma);
		request.setAttribute("workname", ((vSysuser) request.getAttribute(Constans.USER)).getUserName());
		request.setAttribute("listentime", DateUtil
				.getDateTimeStr(new Date()));
		UserInfo user=new UserInfo();	
		String workid=null;
		if(autoid!=null){
			order=workOrderCdmaSumService.getcwbyid(Integer.parseInt(autoid));
			workid=order.getOpId();
		}else{
			workid=receiptOpId;
		}
		//workid=ToolString.subNumber(workid);	
		if(autoid!=null){
		request.setAttribute("receiptOpId", ToolString.subNumber(workid));
		user.setSmailWorkid(ToolString.subNumber(workid));
		request.setAttribute("complaintTime", order.getResultTime());
		request.setAttribute("stoptimeq", DateUtil.getinfoDate(order.getResultTime(),-5));
		request.setAttribute("stoptimeh", DateUtil.getinfoDate(order.getResultTime(), 5));
		request.setAttribute("dirNum", order.getDirNum());
		}else{
		request.setAttribute("receiptOpId", ToolString.subNumber(workid));
		user.setSmailWorkid(ToolString.subNumber(workid));
		request.setAttribute("complaintTime", complaintTime);
		request.setAttribute("stoptimeq", DateUtil.getinfoDate(complaintTime,-5));
		request.setAttribute("stoptimeh", DateUtil.getinfoDate(complaintTime, 5));
		request.setAttribute("dirNum", dirNum);
		}
		UserInfo user2=userInfoService.selectbysmailworkId(user);
		if(user2!=null){
			request.setAttribute("agentName", user2.getUserName());
			request.setAttribute("bigWorkid", user2.getWorkId());
		}
		/*
		 * 判断是否已评过分
		 */
		QC	qc=null;
		if(autoid!=null){
			qc=QCService.selectBytelsumId(Integer.parseInt(autoid));
		}else{
			qc=QCService.selectByWorkOrderId(Integer.parseInt(serialCdma));
		}
		if(qc!=null){ //已评过分
			if(qc.getRecordId()==null){//没有关联过录音
				request.setAttribute("display", "1");//查询关联录音
			}else{
				request.setAttribute("display", "0");//录音不再查询
				RecordInfo record= recordInfoService.selectByPrimaryKey(qc.getRecordId());	
				request.setAttribute("rd", record);
				request.setAttribute("qcid", qc.getQcId());
			}
			// 获取最新的评分信息
			QCDetail qcDetail = QCDetailService.selectDetailByQcid(qc
					.getQcId());
			request.setAttribute("qcDetail", qcDetail);
			// 根据业务类型和是否tl9000加载处理情况
			SysCode syscode = new SysCode();
			syscode.setPid(Integer.parseInt(qcDetail.getBusinessType()));
			syscode.setIsTl9000(qcDetail.getIsTl9000());
			List<SysCode> codelist = sysCodeService.selectSyscode(syscode);
			request.setAttribute("codelist", codelist);
			SysCode syscode2 = new SysCode();
			if ("10".equals(qcDetail.getBusinessType())) {
				syscode2.setPid(124);
			} else if ("54".equals(qcDetail.getBusinessType())) {
				syscode2.setPid(124);
			} else if ("32".equals(qcDetail.getBusinessType())) {
				syscode2.setPid(96);
			} else if ("76".equals(qcDetail.getBusinessType())) {
				syscode2.setPid(172);
			}else if("178".equals(qcDetail.getBusinessType())){
				syscode2.setPid(124);
			}else if("200".equals(qcDetail.getBusinessType())){
				syscode2.setPid(124);
			}else {
				syscode2.setPid(Integer
						.parseInt(qcDetail.getBusinessType()));
			}
			List<SysCode> checkcodelist = sysCodeService
					.selectSyscode(syscode2);
			request.setAttribute("checkcodelist", checkcodelist);
			// 合格的情况
			if (qcDetail.getQualityLevel().equals(Constans.PASS)) {
				// 提交按钮不显示
				request.setAttribute("subflag", "0");
			}
			// 判断是否被二级质检；根据工单流水判断
			int num = secondaryQcService.seccountbyqcId(qc.getQcId());
			if (num < 1) {
				// 填写意见按钮显示,其他情况不显示
				request.setAttribute("secflag", "0");
			}
		} else {// 没关联情况，初始化业务类型为112，是否tl9000为是，加载处理情况
			request.setAttribute("display", "1");
			request.setAttribute("gl", "0");
			SysCode syscode = new SysCode();
			syscode.setPid(Constans.PID_112);
			syscode.setIsTl9000(Constans.TL9000);
			List<SysCode> codelist = sysCodeService.selectSyscode(syscode);
			request.setAttribute("codelist", codelist);
			SysCode syscode2 = new SysCode();
			syscode2.setPid(Constans.PIDCHECK_112);
			List<SysCode> checkcodelist = sysCodeService
					.selectSyscode(syscode2);
			request.setAttribute("checkcodelist", checkcodelist);
			// 填写意见按钮不显示
			request.setAttribute("secflag", "1");
		}
		
	} catch (Exception e) {
		log.error("c网工单评分信息查询异常：" + e.getMessage());
	}
		if(autoid!=null){
			return "/dhxj/gongdan_cwxjqc";	
		}else{
		return "gongdancw_qc";
		}
	}	
	/**
	 * 查询c网申告业务大类
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getBsType",method=RequestMethod.POST)
	public void getBsType(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		try{
			List<String> bstype=workOrderCdma.getbstype();
			JSONObject json=new JSONObject();
			json.put("bstype", JSONArray.fromObject(bstype));
			response.getWriter().print(json);
		}catch(Exception e){
			log.error("查询c网申告业务大类异常"+e.getMessage());
		}		
	}
	
	
	/**
	 * 获取c网工单信息查询多选下拉框
	 * @author lk 2017/4/27
	 * **/	
	@RequestMapping(value = "/getcwOrderType", method = RequestMethod.POST)
	public void getcwOrderType( HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			JSONObject object=new JSONObject();
			List<Modeltype> sgdltype=workOrderCdma.getywdltype(); //获取申告业务大类
			List<Modeltype> sgxltype=workOrderCdma.getywxltype();  //获取申告业务小类
			List<Modeltype> sgxxtype=workOrderCdma.getsgxxtype();  // 获取申告现象
			List<Modeltype> clfstype=workOrderCdma.getclfstype();  //获取一级受理方式
			List<Modeltype> jadmtype=workOrderCdma.getjadmtype();  //获取一级结案代码
			List<Modeltype> secsgxxtype=workOrderCdma.getejsgxxtype();//获取二级申告现象
			List<Modeltype> zejtype=workOrderCdma.getzejtype();//获取是否应该转二级
			List<Modeltype> ycltype=workOrderCdma.getycltype();//获取二级预处理结案代码
			
			object.put("sgdltype", JSONArray.fromObject(sgdltype));
			object.put("sgxltype", JSONArray.fromObject(sgxltype));
			object.put("sgxxtype", JSONArray.fromObject(sgxxtype));
			object.put("clfstype", JSONArray.fromObject(clfstype));
			object.put("jadmtype", JSONArray.fromObject(jadmtype));	
			object.put("secsgxxtype", JSONArray.fromObject(secsgxxtype));
			object.put("zejtype", JSONArray.fromObject(zejtype));
			object.put("ycltype", JSONArray.fromObject(ycltype));
			System.out.println(object.toString());
			response.getWriter().print(object);
		} catch (Exception e) {
			log.error("获取工单信息查询下拉框异常：" + e.getMessage());		
		}
	}
	
	/**
	 * @author lk 2017/5/10
	 * 任务派发  获取任务选择的工单类型
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getcwordercheckdType", method = RequestMethod.POST)
	public void getcwordercheckdType(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String taskId=null;
		try {
			taskId=request.getParameter("taskId");
			if(taskId==null||"".equals(taskId)){
				taskId="0";
			}
			Task task=taskService.getcworderTaskbyid(Integer.parseInt(taskId));
			if(task!=null){
				String	sgdltype=task.getSgdltype();
				String  sgxltype=task.getSgxltype();
				String sgxxtype=task.getSgxxtype();
				String clfstype=task.getClfstype();
				String jadmtype=task.getJadmtype();
				String secsgxxtype=task.getSecsgxxtype();
				String zejtype=task.getZejtype();
				String ycltype=task.getYcltype();	
				String [] sgdltypes=null;
				String [] sgxltypes=null;
				String [] sgxxtypes=null;
				String [] clfstypes=null;
				String [] jadmtypes=null;
				String [] secsgxxtypes=null;
				String [] zejtypes=null;
				String [] ycltypes=null;
				List<Modeltype> sgdltypelist=new ArrayList<Modeltype>(); //获取申告大类
				List<Modeltype> sgxltypelist=new ArrayList<Modeltype>();  //获取结案方式
				List<Modeltype> sgxxtypelist=new ArrayList<Modeltype>();  // 获取受理来源
				List<Modeltype> clfstypelist=new ArrayList<Modeltype>();  //获取派修方向
				List<Modeltype> jadmtypelist=new ArrayList<Modeltype>();  //获取三级故障大类
				List<Modeltype> secsgxxtypelist=new ArrayList<Modeltype>();//获取区局
				List<Modeltype> zejtypelist=new ArrayList<Modeltype>();//获取申告现象
				List<Modeltype> ycltypelist=new ArrayList<Modeltype>();//获取测试代码
				if(sgdltype!=null){
					sgdltypes=sgdltype.split(";");
				for (String string : sgdltypes) {
					Modeltype mtype=new Modeltype();
					mtype.setId(string);
					mtype.setText(string);
					sgdltypelist.add(mtype);
				}
				}
				if(sgxltype!=null){
					sgxltypes=sgxltype.split(";");
					for (String string : sgxltypes) {
						Modeltype mtype=new Modeltype();
						mtype.setId(string);
						mtype.setText(string);
						sgxltypelist.add(mtype);
					}
					
				}
				if(sgxxtype!=null){
					sgxxtypes=sgxxtype.split(";");
					for (String string : sgxxtypes) {
						Modeltype mtype=new Modeltype();
						mtype.setId(string);
						mtype.setText(string);
						sgxxtypelist.add(mtype);
					}
				}
				if(clfstype!=null){
					clfstypes=clfstype.split(";");
					for (String string : clfstypes) {
						Modeltype mtype=new Modeltype();
						mtype.setId(string);
						mtype.setText(string);
						clfstypelist.add(mtype);
					}
				}
				if(jadmtype!=null){
					jadmtypes=jadmtype.split(";");		
					for (String string : jadmtypes) {
						Modeltype mtype=new Modeltype();
						mtype.setId(string);
						mtype.setText(string);
						jadmtypelist.add(mtype);
					}
				}
				if(secsgxxtype!=null){
					secsgxxtypes=secsgxxtype.split(";");
					for (String string : secsgxxtypes) {
						Modeltype mtype=new Modeltype();
						mtype.setId(string);
						mtype.setText(string);
						secsgxxtypelist.add(mtype);
					}
				}
				if(zejtype!=null){
					zejtypes=zejtype.split(";");
					for (String string : zejtypes) {
						Modeltype mtype=new Modeltype();
						mtype.setId(string);
						mtype.setText(string);
						zejtypelist.add(mtype);
					}
				}
				if(ycltype!=null){
					ycltypes=ycltype.split(";");
					for (String string : ycltypes) {
						Modeltype mtype=new Modeltype();
						mtype.setId(string);
						mtype.setText(string);
						ycltypelist.add(mtype);
					}
				}				
			    JSONObject object=new JSONObject();
				object.put("sgdltype", JSONArray.fromObject(sgdltypelist));
				object.put("sgxltype", JSONArray.fromObject(sgxltypelist));
				object.put("sgxxtype", JSONArray.fromObject(sgxxtypelist));
				object.put("clfstype", JSONArray.fromObject(clfstypelist));
				object.put("jadmtype", JSONArray.fromObject(jadmtypelist));	
				object.put("secsgxxtype", JSONArray.fromObject(secsgxxtypelist));
				object.put("zejtype", JSONArray.fromObject(zejtypelist));
				object.put("ycltype", JSONArray.fromObject(ycltypelist));
				System.out.println(object.toString());
				response.getWriter().print(object);	
			}			
		} catch (Exception e) {
			log.error("获取工单列表异常："+e.getMessage());
		}	
	}
	
	
	/**
	 * 获取任务工单列表
	 * @param request
	 * @param response
	 * @param workOrdercw
	 */
	@RequestMapping(value="/gettaskwkcwList",method = RequestMethod.POST)
	public void gettaskwkcwList(HttpServletRequest request,HttpServletResponse response,WorkOrder112 workOrder112){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			Map<String,String> maps=new HashMap<String, String>();//获取当前时间
			maps=DateUtil.getNowDateFront(new Date());		
			String taskId=null;		
			taskId=request.getParameter("taskId");
			String type=request.getParameter("type");
			String cDate=request.getParameter("cDate");
			if(taskId==null||"".equals(taskId)){
				taskId="0";
			}
			Task task=taskService.getcworderTaskbyid(Integer.parseInt(taskId));		
			String userid=task.getTelUser();
			String sgdltype=null;
			String sgxltype=null;
			String sgxxtype=null;
			String clfstype=null;
			String jadmtype=null;
			String secsgxxtype=null;
			String zejtype=null;
			String ycltype=null; 
			if("default".equals(type)){				
				if(task!=null){
					sgdltype=task.getSgdltype();
					sgxltype=task.getSgxltype();
					sgxxtype=task.getSgxxtype();
					clfstype=task.getClfstype();
					jadmtype=task.getJadmtype();
					secsgxxtype=task.getSecsgxxtype();
					zejtype=task.getZejtype();
					ycltype=task.getYcltype();
				}				
			}else{				
				sgdltype=request.getParameter("sgdltype");
				sgxltype=request.getParameter("sgxltype");
				sgxxtype=request.getParameter("sgxxtype");
				clfstype=request.getParameter("clfstype");
				jadmtype=request.getParameter("jadmtype");
				secsgxxtype=request.getParameter("secsgxxtype");
				zejtype=request.getParameter("zejtype");
				ycltype=request.getParameter("ycltype");
			}	
		if(sgdltype!=null&&!"".equals(sgdltype)){
			map.put("sgdltype", sgdltype.split(";"));
		}
		if(sgxltype!=null&&!"".equals(sgxltype)){
			map.put("sgxltype", sgxltype.split(";"));
		}
		if(sgxxtype!=null&&!"".equals(sgxxtype)){
			map.put("sgxxtype", sgxxtype.split(";"));
		}
		if(clfstype!=null&&!"".equals(clfstype)){
			map.put("clfstype", clfstype.split(";"));
		}
		if(jadmtype!=null&&!"".equals(jadmtype)){
			map.put("jadmtype", jadmtype.split(";"));
		}
		if(secsgxxtype!=null&&!"".equals(secsgxxtype)){
			map.put("secsgxxtype", secsgxxtype.split(";"));
		}
		if(zejtype!=null&&!"".equals(zejtype)){
			map.put("zejtype", zejtype.split(";"));
		}
		if(ycltype!=null&&!"".equals(ycltype)){
			map.put("ycltype", ycltype.split(";"));
		}
		
		if(cDate!=null&&!"null".equals(cDate)){
			String sdate=Util.getSpecifiedDayBefore(cDate);
			map.put("startdate", sdate+" 00:00:00");
			map.put("stopdate",sdate+" 23:59:59");
			}else{
		map.put("startdate", maps.get("startdate"));
		map.put("stopdate",maps.get("stopdate"));
			}
		map.put("userid", userid);
		map.put("checkcount", task.getCheckcount());
		map.put("taskId", taskId);
		List<WorkOrderCdma> wkList=	workOrderCdma.selecttaskWorkOrdercw(map);
		String json = "{\"Rows\":"
				+ JSONArray.fromObject(wkList).toString() + "}";	
			System.out.println(json.toString());
			response.getWriter().print(json.toString());		
		} catch (Exception e) {
			log.error("获取工单列表异常："+e.getMessage());		
		}		
	}
	
	
	/**
	 * 任务单条领取c网工单
	 * @param request
	 * @param response
	 */	
	@Log(methodname = "getcworderonelinqu", modulename = "任务执行", funcname = "执行c网工单任务", description = "单条录音领取,{0}", code = "ZJ")
	@RequestMapping(value="/getcworderonelinqu",method = RequestMethod.POST)
	public void getcworderonelinqu(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try{
		String teluserId=request.getAttribute(Constans.USER_INFO_USERID).toString();
		String taskId=request.getParameter("taskId");
		String serial_cdma=request.getParameter("serial_cdma");
		String teluser=request.getParameter("teluser");
		Task task=taskService.getcworderTaskbyid(Integer.parseInt(taskId));
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("taskId", taskId);//任务id
		map.put("serial_cdma", serial_cdma);//工单id
		map.put("task", task);
		map.put("teluserId", teluserId);//质检员
		map.put("teluser", teluser);//受理员
		String vmsg=workOrderCdma.updlinqucworder(map);		
		JSONObject json=new JSONObject();
		json.put("vmsg", vmsg);
		LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,c网工单号:"+serial_cdma}));
		response.getWriter().print(json.toString());
		}catch (Exception e) {
		LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作失败"+e.getMessage()}));
			log.error(e);
		}
	}
	
	
	/**
	 * 任务一键领取c网工单
	 * @param request
	 * @param response
	 */	
	@Log(methodname = "getcworderlinqu", modulename = "任务执行", funcname = "执行c网工单任务", description = "一键领取c网工单任务,{0}", code = "ZJ")
	@RequestMapping(value="/getcworderlinqu",method = RequestMethod.POST)
	public void getcworderlinqu(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String taskId=request.getParameter("taskId");		
		Task task=taskService.getcworderTaskbyid(Integer.parseInt(taskId));
		String teluserId=request.getAttribute(Constans.USER_INFO_USERID).toString();
		String userid=task.getTelUser();
		Map<String,Object> map=new HashMap<String, Object>();
		try{
		if(task!=null){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos1 = new ParsePosition(0);
			int countDay= DateUtil.getDays(formatter.parse(task.getTaskStartTime()
					.substring(0, 10), pos), formatter.parse(task.getTaskEndTime()
					.substring(0, 10), pos1));					
		String	sgdltype=task.getSgdltype();
		String	sgxltype=task.getSgxltype();
		String	sgxxtype=task.getSgxxtype();
		String	clfstype=task.getClfstype();
		String	jadmtype=task.getJadmtype();
		String	secsgxxtype=task.getSecsgxxtype();
		String	zejtype=task.getZejtype();
		String	ycltype=task.getYcltype();
		if(sgdltype!=null&&!"".equals(sgdltype)){
			map.put("sgdltype", sgdltype.split(";"));
		}
		if(sgxltype!=null&&!"".equals(sgxltype)){
			map.put("sgxltype", sgxltype.split(";"));
		}
		if(sgxxtype!=null&&!"".equals(sgxxtype)){
			map.put("sgxxtype", sgxxtype.split(";"));
		}
		if(clfstype!=null&&!"".equals(clfstype)){
			map.put("clfstype", clfstype.split(";"));
		}
		if(jadmtype!=null&&!"".equals(jadmtype)){
			map.put("jadmtype", jadmtype.split(";"));
		}
		if(secsgxxtype!=null&&!"".equals(secsgxxtype)){
			map.put("secsgxxtype", secsgxxtype.split(";"));
		}
		if(zejtype!=null&&!"".equals(zejtype)){
			map.put("zejtype", zejtype.split(";"));
		}
		if(ycltype!=null&&!"".equals(ycltype)){
			map.put("ycltype", ycltype.split(";"));
		}		
		map.put("teluserId", teluserId);
		map.put("countDay", countDay);
		map.put("taskId", taskId);
		map.put("userid", userid);
		map.put("startdate", task.getTaskStartTime());
		map.put("stopdate", task.getTaskEndTime());
		map.put("csrTopDCount", task.getCsrTopDCount());
		map.put("checkcount", task.getCheckcount());
		Map<String,String> msg=workOrderCdma.updalllinqucworder(map);		
		JSONObject json=new JSONObject();
		json.put("vmsg", msg.get("vmsg"));
		response.getWriter().print(json.toString());
		LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,工单编号:"+msg.get("orderid")}));
		}
		}catch (Exception e) {
		LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作失败"+e.getMessage()}));
		}
	}
	
	/**
	 * @author lk 2017/5/31
	 * @param request
	 * @param response
	 * 
	 */
	@RequestMapping(value="/getyltaskwkcwList",method = RequestMethod.POST)
	public void  getyltaskwkcwList(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		String type=request.getParameter("type");
		String userid=request.getAttribute((Constans.USER_INFO_USERID)).toString();
		PageHelper.startPage(currentPage, pageSize);
		String taskId=request.getParameter("taskId");
		String isqc=request.getParameter("isqc");
		String cDate=request.getParameter("cDate");
		if("null".equals(taskId)){
			taskId=null;
		}
		if("null".equals(cDate)){
			cDate=null;
		}
		Map<String,String> map=new HashMap<String, String>();
		map.put("taskId", taskId);
		map.put("isqc", isqc);	
		map.put("ctodate", cDate);
		map.put("userid", userid);
		map.put("ordertype", "2");
		Set<String> list=null;
		if(cDate!=null&&!"".equals(cDate)){
			list=taskService.getyl112ordertaskidList(map);
		}else{
			list=taskService.getyl112ordertaskList(map);
		}	
		String json="";
		if(!list.isEmpty()){
		List<WorkOrderCdma> orderlist=workOrderCdma.getylcwordertaskList(list);
		int total=workOrderCdma.getylcwordertaskListcount(list);
		json= "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(orderlist).toString() + "}";	
		}
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			log.error("获取工单列表异常："+e.getMessage());
			
		}	
	}
	
}
