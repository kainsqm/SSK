package cn.sh.ideal.controller;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import cn.sh.ideal.model.OrderType;
import cn.sh.ideal.model.QC;
import cn.sh.ideal.model.QCDetail;
import cn.sh.ideal.model.RecordInfo;
import cn.sh.ideal.model.SecondaryQc;
import cn.sh.ideal.model.SysCode;
import cn.sh.ideal.model.Task;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.model.WorkOrder112;
import cn.sh.ideal.model.WorkTelSum112;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.IQCDetailService;
import cn.sh.ideal.service.IQCService;
import cn.sh.ideal.service.IRecordInfoSevice;
import cn.sh.ideal.service.ISecondaryQcService;
import cn.sh.ideal.service.ISysCodeService;
import cn.sh.ideal.service.ITaskService;
import cn.sh.ideal.service.IUserInfoService;
import cn.sh.ideal.service.IWorkOrder112Service;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.LogUitls;
import cn.sh.ideal.util.ToolString;
import cn.sh.ideal.util.Util;

import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/controller/wk112")
public class WorkOrder112Controller {
	private static Logger log = Logger.getLogger(WorkOrder112Controller.class);
	@Resource
	private IWorkOrder112Service workOrder112Service;
	
	@Resource
	private  ISysCodeService sysCodeService;
	
	@Resource
	private IRecordInfoSevice recordInfoService;
	
	@Resource
	private IQCService QCService;
	@Resource
	private IQCDetailService QCDetailService;
	
	@Resource
	private ISecondaryQcService secondaryQcService;
	@Resource
	private ITaskService taskService;
	@Resource
	private IUserInfoService userInfoService;
	/**
	 * @author lk 2017/5/8
	 * @return
	 * 跳转112工单查询界面
	 */
	@RequiresPermissions("112order:query")
	@RequestMapping(value="/togetwk112List",method=RequestMethod.GET)
	public String togetwk112List(){		
		return "112/paperList_112";
	}
	
	
	@RequestMapping(value="/getwk112List",method = RequestMethod.POST)
	public void getwk112List(HttpServletRequest request,HttpServletResponse response,WorkOrder112 workOrder112){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		if(currentPage==1){
			workOrder112.setEnd(pageSize);
        }else{
        	workOrder112.setStart((currentPage-1)*pageSize);
        	workOrder112.setEnd(pageSize);
        }     		
	//	PageHelper.startPage(currentPage, pageSize);
		//workOrder112.setIsGl("1");
		if(workOrder112!=null){
			if(workOrder112.getFirstAgentUserid()!=null&&workOrder112.getFirstAgentUserid()!=""){
		workOrder112.setFirstAgentUserid(ToolString.subNumber(workOrder112.getFirstAgentUserid()));
			}
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info("查询开始时间:"+sdf.format(new Date()));
		List<WorkOrder112> wkList=workOrder112Service.selectWorkOrder112(workOrder112);	
		int total=workOrder112Service.selectCount(workOrder112);
		log.info("查询結束时间:"+sdf.format(new Date()));
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(wkList).toString() + "}";	
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			log.error("获取工单列表异常："+e.getMessage());
			
		}		
	}
	@RequiresPermissions("112xj:query")
	@RequestMapping(value="/togetwk112XjList",method=RequestMethod.GET)
	public String togetwk112XjList(){		
		return "dhxj/112_dhxjInfo";
	}
	
	/**
	 * 工单112电话小结列表
	 * @param request
	 * @param response
	 * @param worksum112
	 */
	@RequestMapping(value="/getwk112XjList",method = RequestMethod.POST)
	public void getwk112XjList(HttpServletRequest request,HttpServletResponse response,WorkTelSum112 worksum112){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			PageHelper.startPage(currentPage, pageSize);
			List<WorkTelSum112> wkList=workOrder112Service.select112Xj(worksum112);
			int counts=workOrder112Service.select112XjCount(worksum112);
			// int total=recordInfoService.selectCount(recordInfo);
			String json = "{\"Total\":" + counts + " , \"Rows\":"
					+ JSONArray.fromObject(wkList).toString() + "}";

			System.out.println(json.toString());
			response.getWriter().print(json.toString());
			// return object.toString();
		} catch (Exception e) {
			log.error("获取工单112电话小结列表异常：" + e.getMessage());
		}
		
	}
	/**
	 * 获取未关联的录音信息
	 * **/
	
	@RequestMapping(value="/getNoGLRecord",method = RequestMethod.POST)
	public void getNoGLRecord(HttpServletRequest request,HttpServletResponse response,RecordInfo recordinfo){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			PageHelper.startPage(currentPage, pageSize);
			recordinfo.setAgentName(ToolString.subNumber(recordinfo.getAgentName()));
			List<RecordInfo> riList=workOrder112Service.getNoGLRecord(recordinfo);
			int counts=workOrder112Service.getNoGLRecordCount(recordinfo);
			
			// int total=recordInfoService.selectCount(recordInfo);
			String json = "{\"Total\":" + counts + " , \"Rows\":"
					+ JSONArray.fromObject(riList).toString() + "}";

			System.out.println(json.toString());
			response.getWriter().print(json.toString());
			// return object.toString();
		} catch (Exception e) {
			log.error("获取未关联的录音信息列表异常：" + e.getMessage());
		}
		
	}
	
	
	
	
	/**
	 * 进入112工单评分界面
	 * @author niewq 
	 * **/
	@RequiresPermissions(value = {"112order:qc", "112xj:qc"}, logical = Logical.OR)
	@RequestMapping(value = "/112gradeShow", method = RequestMethod.POST)
	public String gradeShow(HttpServletRequest request,
			HttpServletResponse response)  {
		String autoid=null;
		try{
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
	/*	response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");*/
	//	String type = request.getParameter("type");// 入口判断：判断是修改还是查看
			
		autoid=	request.getParameter("autoid"); //112电话小结主键
		request.setAttribute("channelType", request.getParameter("channelType"));
        
		String workorderId = request.getParameter("workorderId"); 
		WorkOrder112 workorder =null;
		WorkTelSum112 worksum=null;
		UserInfo user=new UserInfo();
		if(autoid!=null){
			worksum=workOrder112Service.get112jbyid(Integer.parseInt(autoid));
			user.setSmailWorkid(ToolString.subNumber(worksum.getTelsumWorkid()));
			request.setAttribute("stoptimeq", DateUtil.getinfoDate(worksum.getTelsumTime(),-5));
			request.setAttribute("stoptimeh", DateUtil.getinfoDate(worksum.getTelsumTime(), 5));
			request.setAttribute("complaintTime",  worksum.getTelsumTime());
			request.setAttribute("agentworkid",ToolString.subNumber(worksum.getTelsumWorkid()));
			request.setAttribute("declarationNo",worksum.getDeclarationNo());
		}else{
			workorder=workOrder112Service.selectByPrimaryKey(Integer.parseInt(workorderId));
			user.setSmailWorkid(ToolString.subNumber(workorder.getFirstAgentUserid()));
			request.setAttribute("stoptimeq", DateUtil.getinfoDate(workorder.getDeclarationTime(),-5));
			request.setAttribute("stoptimeh", DateUtil.getinfoDate(workorder.getDeclarationTime(), 5));
			request.setAttribute("complaintTime",  workorder.getDeclarationTime());
			request.setAttribute("agentworkid",workorder.getFirstAgentUserid());
			request.setAttribute("errorNo", workorder.getErrorNo());
		}
		
		UserInfo user2=userInfoService.selectbysmailworkId(user);
		if(user2!=null){
			request.setAttribute("agentName", user2.getUserName());
			request.setAttribute("bigWorkid", user2.getWorkId());
		}
		//受理员
	//	request.setAttribute("agentName", record.getAgentName());
	//	request.setAttribute("startTime", record.getStartTime());
	//	request.setAttribute("recordId", record.getRecordId());		
		request.setAttribute("workorderId",workorderId);
		request.setAttribute("xj112autoid", autoid);
		request.setAttribute("listentime", DateUtil.getDateTimeStr(new Date()));
		request.setAttribute("workname", (((vSysuser) request.getAttribute("User")).getUserName()));
		
		
		//获取录音关联的工单信息以及最新的评分信息
		//判断该录音是否关联工单	
		QC qc=null;
		if(autoid!=null){
			 qc=QCService.selectBytelsumId(Integer.parseInt(autoid));
		 }else{
			 qc=QCService.selectByWorkOrderId(Integer.parseInt(workorderId));
		 }
		if(qc!=null){//已经关联
			request.setAttribute("display", "0");//关联工单不显示
			//获取关联的录音信息
			RecordInfo record= recordInfoService.selectByPrimaryKey(qc.getRecordId());
			request.setAttribute("rd", record);
			/*request.setAttribute("reservedThree", record.getReservedThree());
			request.setAttribute("startTime",record.getStartTime() );
			request.setAttribute("stopTime", record.getStopTime());
			request.setAttribute("callerid", record.getCallerid());
			request.setAttribute("calledid", record.getCalledid());*/
			
			//获取最新的评分信息
			QCDetail qcDetail=QCDetailService.selectDetailByQcid(qc.getQcId());
			
			request.setAttribute("qcDetail", qcDetail);
			request.setAttribute("qcid", qc.getQcId());
			//根据业务类型和是否tl9000加载处理情况
			SysCode syscode=new SysCode();
			syscode.setPid(Integer.parseInt(qcDetail.getBusinessType()));
			syscode.setIsTl9000(qcDetail.getIsTl9000());
			List<SysCode> codelist=sysCodeService.selectSyscode(syscode);
			request.setAttribute("codelist", codelist);
			//根据业务类型加载检查内容
			SysCode syscode2=new SysCode();
			if("10".equals(qcDetail.getBusinessType())){
				syscode2.setPid(124);
			}else if("54".equals(qcDetail.getBusinessType())){
				syscode2.setPid(124);
			}else if("32".equals(qcDetail.getBusinessType())){
				syscode2.setPid(96);
			}else if("76".equals(qcDetail.getBusinessType())){
				syscode2.setPid(172);
			}else if("178".equals(qcDetail.getBusinessType())){
				syscode2.setPid(124);
			}else if("200".equals(qcDetail.getBusinessType())){
				syscode2.setPid(96);
			}else{
				syscode2.setPid(Integer.parseInt(qcDetail.getBusinessType()));
			}
			List<SysCode> checkcodelist=sysCodeService.selectSyscode(syscode2);
			request.setAttribute("checkcodelist", checkcodelist);
			
			//合格的情况
			if(qcDetail.getQualityLevel().equals(Constans.PASS)){
				//提交按钮不显示
				request.setAttribute("subflag", "0");
			}
			// 判断是否被二级质检；根据工单流水判断
				int num = secondaryQcService.seccountbyqcId(qc.getQcId());
				if (num < 1) {
					// 填写意见按钮显示,其他情况不显示
					request.setAttribute("secflag", "0");
						      }
		}else{
			//没关联情况，初始化业务类型为112，是否tl9000为是，加载处理情况
			request.setAttribute("display", "1");//关联工单不显示
			request.setAttribute("gl", "0");
			SysCode syscode=new SysCode();
			syscode.setPid(Constans.PID_112);
			syscode.setIsTl9000(Constans.TL9000);
			List<SysCode> codelist=sysCodeService.selectSyscode(syscode);
			request.setAttribute("codelist", codelist);
			
			SysCode syscode2=new SysCode();
			syscode2.setPid(Constans.PIDCHECK_112);
			List<SysCode> checkcodelist=sysCodeService.selectSyscode(syscode2);
			request.setAttribute("checkcodelist", checkcodelist);
			//填写意见按钮不显示
			request.setAttribute("secflag", "1");
			
		}
		}catch(Exception e){
			log.error("查询112工单详情异常：" + e.getMessage());
		}
		if(autoid==null){
		return "112/gongdan_qc";
		}else{
			return "dhxj/gongdan_112xjqc";
		}
	}
	
	
	
	/**
	 * 进入112工单听录音
	 * @author niewq 
	 * **/
	@Log(methodname = "listenRecordoBy112", modulename = "工单信息查询(112)", funcname = "112工单听录音", description = "112工单听录音,{0}", code = "ZJ")
	@RequestMapping(value = "/listenRecordoBy112", method = RequestMethod.GET)
	public String listenRecordoBy112( HttpServletRequest request,
			HttpServletResponse response) {
		String recordFilename = request.getParameter("recordReference");
		String xmlfilepath = request.getSession().getServletContext().getRealPath("/UserFiles/ftpServers.xml");
		try {
			request.setAttribute("recordFileURL", workOrder112Service.getRecordFileUrl(recordFilename,xmlfilepath));
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[] { "操作成功,录音流水号:"+recordFilename}));
		} catch (Exception e) {
			System.out.println(e.toString());
			log.error("112工单听录音异常：" + e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[] { e.getMessage() }));
		}

		return "112/recordplayer";
	}
	
	/**
	 * 获取工单信息查询下拉框
	 * @author maxiaoni
	 * **/
	@RequestMapping(value = "/getDeclarationBigType", method = RequestMethod.POST)
	public void getDeclarationBigType( HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			JSONObject object=new JSONObject();
			List<String> decList=workOrder112Service.getDeclarationBigType(); //获取申告大类
			List<String> closeedWay=workOrder112Service.getCloseedWay();  //获取结案方式
			List<String> acceptedSource=workOrder112Service.getAcceptedSource();  // 获取受理来源
			List<String> repairDirection=workOrder112Service.getrRepairDirection();  //获取派修方向
			List<String> threeErrorType=workOrder112Service.getThreeErrorType();  //获取三级故障大类
			object.put("decList", JSONArray.fromObject(decList));
			object.put("closeedWay", JSONArray.fromObject(closeedWay));
			object.put("acceptedSource", JSONArray.fromObject(acceptedSource));
			object.put("repairDirection", JSONArray.fromObject(repairDirection));
			object.put("threeErrorType", JSONArray.fromObject(threeErrorType));
			response.getWriter().print(object);
		} catch (Exception e) {
			log.error("获取工单信息查询下拉框异常：" + e.getMessage());
		}
	}
	/**
	 * @author lk 2017/4/17
	 * @param request
	 * @param response
	 * 查询112电话小结下拉
	 */
		@RequestMapping(value = "/get112xjtype", method = RequestMethod.POST)
		public void get112xjtype(HttpServletRequest request,HttpServletResponse response){
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			try{
			List<String> xjtype=workOrder112Service.get112xjtype();
			List<String> bstype=workOrder112Service.getxjbstype();
			JSONObject json=new JSONObject();
			json.put("xjtype", JSONArray.fromObject(xjtype));
			json.put("bstype", JSONArray.fromObject(bstype));
			response.getWriter().print(json);
			}catch (Exception e) {
				log.error("获取电话小结查询下拉框异常：" + e.getMessage());
			}
		}
	
		
		/**
		 * 获取112工单信息查询多选下拉框
		 * @author lk 2017/4/27
		 * **/	
		@RequestMapping(value = "/get112OrderType", method = RequestMethod.POST)
		public void get112OrderType( HttpServletRequest request,HttpServletResponse response) {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			try {
				JSONObject object=new JSONObject();
				List<Modeltype> decList=workOrder112Service.getDeclarationBigType2(); //获取申告大类
				List<Modeltype> closeedWay=workOrder112Service.getCloseedWay2();  //获取结案方式
				List<Modeltype> acceptedSource=workOrder112Service.getAcceptedSource2();  // 获取受理来源
				List<Modeltype> repairDirection=workOrder112Service.getrRepairDirection2();  //获取派修方向
				List<Modeltype> threeErrorType=workOrder112Service.getThreeErrorType2();  //获取三级故障大类
				List<Modeltype> qjList=workOrder112Service.getqjtype();//获取区局
				List<Modeltype> sgxxList=workOrder112Service.getsgxxtype();//获取申告现象
				List<Modeltype> csdmList=workOrder112Service.getcsdmtype();//获取测试代码
				List<Modeltype> sgbzList=workOrder112Service.getsgbztype();//获取申告备注
				List<Modeltype> lxinfoList=workOrder112Service.getlxinfotype();//获取申告联系信息
				List<Modeltype> gzxfList=workOrder112Service.getgzxftype();//获取故障修复代码
				
				object.put("decList", JSONArray.fromObject(decList));
				object.put("closeedWay", JSONArray.fromObject(closeedWay));
				object.put("acceptedSource", JSONArray.fromObject(acceptedSource));
				object.put("repairDirection", JSONArray.fromObject(repairDirection));
				object.put("threeErrorType", JSONArray.fromObject(threeErrorType));	
				object.put("qjList", JSONArray.fromObject(qjList));
				object.put("sgxxList", JSONArray.fromObject(sgxxList));
				object.put("csdmList", JSONArray.fromObject(csdmList));
				object.put("sgbzList", JSONArray.fromObject(sgbzList));
				object.put("lxinfoList", JSONArray.fromObject(lxinfoList));
				object.put("gzxfList", JSONArray.fromObject(gzxfList));
				System.out.println(object.toString());
				response.getWriter().print(object);
			} catch (Exception e) {
				log.error("获取工单信息查询下拉框异常：" + e.getMessage());		
			}
		}
		
		/**
         * 获取112小结信息查询多选下拉框
         * @author chendi
         * **/
        @RequestMapping(value = "/get112ChapterType", method = RequestMethod.POST)
        public void get112ChapterType( HttpServletRequest request,HttpServletResponse response) {
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            try {
                JSONObject object=new JSONObject();
                List<Modeltype> businessType=workOrder112Service.getBusinessType(); //获取申告大类
                List<Modeltype> telsumType=workOrder112Service.getTelsumType();  //获取结案方式
                List<Modeltype> gzlyType=workOrder112Service.getErrorSource();  // 获取受理来源
                object.put("businessType", JSONArray.fromObject(businessType));
                object.put("telsumType", JSONArray.fromObject(telsumType));
                object.put("gzlyType", JSONArray.fromObject(gzlyType));
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
		@RequestMapping(value = "/get112ordercheckdType", method = RequestMethod.POST)
		public void get112ordercheckdType(HttpServletRequest request,HttpServletResponse response){
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			String taskId=null;
			try {
				taskId=request.getParameter("taskId");
				if(taskId==null||"".equals(taskId)){
					taskId="0";
				}
				Task task=taskService.get112orderTaskbyid(Integer.parseInt(taskId));
				if(task!=null){
					String	qjtype=task.getQjtype();
					String  sgdltype=task.getSgdltype();
					String sgxxtype=task.getSgxxtype();
					String csdmtype=task.getCsdmtype();
					String sgbztype=task.getSgbztype();
					String sglxtype=task.getSglxtype();
					String jafstype=task.getJafstype();
					String sllytype=task.getSllytype();
					String pxfxtype=task.getPxfxtype();
					String sjgztype=task.getSjgztype();
					String gzxftype=task.getGzxftype();	
					String [] qjtypes=null;
					String [] sgdltypes=null;
					String [] sgxxtypes=null;
					String [] csdmtypes=null;
					String [] sgbztypes=null;
					String [] sglxtypes=null;
					String [] jafstypes=null;
					String [] sllytypes=null;
					String [] pxfxtypes=null;
					String [] sjgztypes=null;
					String [] gzxftypes=null;
					List<Modeltype> decList=new ArrayList<Modeltype>(); //获取申告大类
					List<Modeltype> closeedWay=new ArrayList<Modeltype>();  //获取结案方式
					List<Modeltype> acceptedSource=new ArrayList<Modeltype>();  // 获取受理来源
					List<Modeltype> repairDirection=new ArrayList<Modeltype>();  //获取派修方向
					List<Modeltype> threeErrorType=new ArrayList<Modeltype>();  //获取三级故障大类
					List<Modeltype> qjList=new ArrayList<Modeltype>();//获取区局
					List<Modeltype> sgxxList=new ArrayList<Modeltype>();//获取申告现象
					List<Modeltype> csdmList=new ArrayList<Modeltype>();//获取测试代码
					List<Modeltype> sgbzList=new ArrayList<Modeltype>();//获取申告备注
					List<Modeltype> lxinfoList=new ArrayList<Modeltype>();//获取申告联系信息
					List<Modeltype> gzxfList=new ArrayList<Modeltype>();//获取故障修复代码
					if(sgdltype!=null){
					qjtypes=qjtype.split(";");
					for (String string : qjtypes) {
						Modeltype mtype=new Modeltype();
						mtype.setId(string);
						mtype.setText(string);
						qjList.add(mtype);
					}
					}
					if(sgdltype!=null){
						sgdltypes=sgdltype.split(";");
						for (String string : sgdltypes) {
							Modeltype mtype=new Modeltype();
							mtype.setId(string);
							mtype.setText(string);
							decList.add(mtype);
						}
						
					}
					if(sgxxtype!=null){
						sgxxtypes=sgxxtype.split(";");
						for (String string : sgxxtypes) {
							Modeltype mtype=new Modeltype();
							mtype.setId(string);
							mtype.setText(string);
							sgxxList.add(mtype);
						}
					}
					if(csdmtype!=null){
						csdmtypes=csdmtype.split(";");
						for (String string : csdmtypes) {
							Modeltype mtype=new Modeltype();
							mtype.setId(string);
							mtype.setText(string);
							csdmList.add(mtype);
						}
					}
					if(sgbztype!=null){
						sgbztypes=sgbztype.split(";");		
						for (String string : sgbztypes) {
							Modeltype mtype=new Modeltype();
							mtype.setId(string);
							mtype.setText(string);
							sgbzList.add(mtype);
						}
					}
					if(sglxtype!=null){
						sglxtypes=sglxtype.split(";");
						for (String string : sglxtypes) {
							Modeltype mtype=new Modeltype();
							mtype.setId(string);
							mtype.setText(string);
							lxinfoList.add(mtype);
						}
					}
					if(jafstype!=null){
						jafstypes=jafstype.split(";");
						for (String string : jafstypes) {
							Modeltype mtype=new Modeltype();
							mtype.setId(string);
							mtype.setText(string);
							closeedWay.add(mtype);
						}
					}
					if(sllytype!=null){
						sllytypes=sllytype.split(";");
						for (String string : sllytypes) {
							Modeltype mtype=new Modeltype();
							mtype.setId(string);
							mtype.setText(string);
							acceptedSource.add(mtype);
						}
					}
					if(pxfxtype!=null){
						pxfxtypes=pxfxtype.split(";");
						for (String string : pxfxtypes) {
							Modeltype mtype=new Modeltype();
							mtype.setId(string);
							mtype.setText(string);
							repairDirection.add(mtype);
						}
					}
					if(sjgztype!=null){
						sjgztypes=sjgztype.split(";");
						for (String string : sjgztypes) {
							Modeltype mtype=new Modeltype();
							mtype.setId(string);
							mtype.setText(string);
							threeErrorType.add(mtype);
						}
					}
					if(gzxftype!=null){
						gzxftypes=gzxftype.split(";");
						for (String string : gzxftypes) {
							Modeltype mtype=new Modeltype();
							mtype.setId(string);
							mtype.setText(string);
							gzxfList.add(mtype);
						}
					}		
									
				    JSONObject object=new JSONObject();
					object.put("decList", JSONArray.fromObject(decList));
					object.put("closeedWay", JSONArray.fromObject(closeedWay));
					object.put("acceptedSource", JSONArray.fromObject(acceptedSource));
					object.put("repairDirection", JSONArray.fromObject(repairDirection));
					object.put("threeErrorType", JSONArray.fromObject(threeErrorType));	
					object.put("qjList", JSONArray.fromObject(qjList));
					object.put("sgxxList", JSONArray.fromObject(sgxxList));
					object.put("csdmList", JSONArray.fromObject(csdmList));
					object.put("sgbzList", JSONArray.fromObject(sgbzList));
					object.put("lxinfoList", JSONArray.fromObject(lxinfoList));
					object.put("gzxfList", JSONArray.fromObject(gzxfList));
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
		 * @param workOrder112
		 */
		@RequestMapping(value="/gettaskwk112List",method = RequestMethod.POST)
		public void gettaskwk112List(HttpServletRequest request,HttpServletResponse response,WorkOrder112 workOrder112){
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
				Task task=taskService.get112orderTaskbyid(Integer.parseInt(taskId));		
				String userid=task.getTelUser();
				String qjtype=null;
				String sgdltype=null;
				String sgxxtype=null;
				String csdmtype=null;
				String sgbztype=null;
				String sglxtype=null;
				String jafstype=null;
				String sllytype=null;
				String pxfxtype=null;
				String sjgztype=null;
				String gzxftype=null; 
				if("default".equals(type)){				
					if(task!=null){
						qjtype=task.getQjtype();
						sgdltype=task.getSgdltype();
						sgxxtype=task.getSgxxtype();
						csdmtype=task.getCsdmtype();
						sgbztype=task.getSgbztype();
						sglxtype=task.getSglxtype();
						jafstype=task.getJafstype();
						sllytype=task.getSllytype();
						pxfxtype=task.getPxfxtype();
						sjgztype=task.getSjgztype();
						gzxftype=task.getGzlytype();
					}				
				}else{				
				    qjtype=request.getParameter("qjtype");
				    sgdltype=request.getParameter("sgdltype");
					sgxxtype=request.getParameter("sgxxtype");
					csdmtype=request.getParameter("csdmtype");
					sgbztype=request.getParameter("sgbztype");
					sglxtype=request.getParameter("sglxtype");
					jafstype=request.getParameter("jafstype");
					sllytype=request.getParameter("sllytype");
					pxfxtype=request.getParameter("pxfxtype");
					sjgztype=request.getParameter("sjgztype");
					gzxftype=request.getParameter("gzxftype");
				}	
			if(qjtype!=null&&!"".equals(qjtype)){
				map.put("qjtype", qjtype.split(";"));
			}
			if(sgdltype!=null&&!"".equals(sgdltype)){
				map.put("sgdltype", sgdltype.split(";"));
			}
			if(sgxxtype!=null&&!"".equals(sgxxtype)){
				map.put("sgxxtype", sgxxtype.split(";"));
			}
			if(csdmtype!=null&&!"".equals(csdmtype)){
				map.put("csdmtype", csdmtype.split(";"));
			}
			if(sgbztype!=null&&!"".equals(sgbztype)){
				map.put("sgbztype", sgbztype.split(";"));
			}
			if(sglxtype!=null&&!"".equals(sglxtype)){
				map.put("sglxtype", sglxtype.split(";"));
			}
			if(jafstype!=null&&!"".equals(jafstype)){
				map.put("jafstype", jafstype.split(";"));
			}
			if(sllytype!=null&&!"".equals(sllytype)){
				map.put("sllytype", sllytype.split(";"));
			}
			if(pxfxtype!=null&&!"".equals(pxfxtype)){
				map.put("pxfxtype", pxfxtype.split(";"));
			}
			if(sjgztype!=null&&!"".equals(sjgztype)){
				map.put("sjgztype", sjgztype.split(";"));
			}
			if(gzxftype!=null&&!"".equals(gzxftype)){
				map.put("gzxftype", gzxftype.split(";"));
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
			List<WorkOrder112> wkList=workOrder112Service.selecttaskWorkOrder112(map);		
			String json = "{\"Rows\":"
					+ JSONArray.fromObject(wkList).toString() + "}";	
				System.out.println(json.toString());
				response.getWriter().print(json.toString());		
			} catch (Exception e) {
				log.error("获取工单列表异常："+e.getMessage());		
			}		
		}
		
		/**
		 * 任务一键领取112工单
		 * @param request
		 * @param response
		 */	
		@Log(methodname="get112orderlinqu",modulename="任务执行",funcname="执行112工单任务",description="一键领取112工单任务,{0}", code = "ZJ")
		@RequestMapping(value="/get112orderlinqu",method = RequestMethod.POST)
		public void get112orderlinqu(HttpServletRequest request,HttpServletResponse response){
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			String taskId=request.getParameter("taskId");		
			Task task=taskService.get112orderTaskbyid(Integer.parseInt(taskId));
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
			String	qjtype=task.getQjtype();
			String	sgdltype=task.getSgdltype();
			String	sgxxtype=task.getSgxxtype();
			String	csdmtype=task.getCsdmtype();
			String	sgbztype=task.getSgbztype();
			String	sglxtype=task.getSglxtype();
			String	jafstype=task.getJafstype();
			String	sllytype=task.getSllytype();
			String	pxfxtype=task.getPxfxtype();
			String	sjgztype=task.getSjgztype();
			String	gzxftype=task.getGzlytype();
			if(qjtype!=null&&!"".equals(qjtype)){
				map.put("qjtype", qjtype.split(";"));
			}
			if(sgdltype!=null&&!"".equals(sgdltype)){
				map.put("sgdltype", sgdltype.split(";"));
			}
			if(sgxxtype!=null&&!"".equals(sgxxtype)){
				map.put("sgxxtype", sgxxtype.split(";"));
			}
			if(csdmtype!=null&&!"".equals(csdmtype)){
				map.put("csdmtype", csdmtype.split(";"));
			}
			if(sgbztype!=null&&!"".equals(sgbztype)){
				map.put("sgbztype", sgbztype.split(";"));
			}
			if(sglxtype!=null&&!"".equals(sglxtype)){
				map.put("sglxtype", sglxtype.split(";"));
			}
			if(jafstype!=null&&!"".equals(jafstype)){
				map.put("jafstype", jafstype.split(";"));
			}
			if(sllytype!=null&&!"".equals(sllytype)){
				map.put("sllytype", sllytype.split(";"));
			}
			if(pxfxtype!=null&&!"".equals(pxfxtype)){
				map.put("pxfxtype", pxfxtype.split(";"));
			}
			if(sjgztype!=null&&!"".equals(sjgztype)){
				map.put("sjgztype", sjgztype.split(";"));
			}
			if(gzxftype!=null&&!"".equals(gzxftype)){
				map.put("gzxftype", gzxftype.split(";"));
			}				
			map.put("teluserId", teluserId);
			map.put("countDay", countDay);
			map.put("taskId", taskId);
			map.put("userid", userid);
			map.put("startdate", task.getTaskStartTime());
			map.put("stopdate", task.getTaskEndTime());
			map.put("csrTopDCount", task.getCsrTopDCount());
			map.put("checkcount", task.getCheckcount());
			Map<String,String> msg=workOrder112Service.updalllinqu112order(map);		
			JSONObject json=new JSONObject();
			json.put("vmsg", msg.get("vmsg"));
			System.out.println(msg.get("orderid"));
			response.getWriter().print(json.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,工单编号:【"+msg.get("orderid")+"】"}));
			}
			}catch (Exception e) {
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作失败"+e.getMessage()}));
			}
		}
		
		/**
		 * 任务单条领取112工单
		 * @param request
		 * @param response
		 */	
		@Log(methodname = "listenRecordoBy112", modulename = "任务执行", funcname = "执行112工单任务", description = "单条录音领取,{0}", code = "ZJ")
		@RequestMapping(value="/get112orderonelinqu",method = RequestMethod.POST)
		public void get112orderonelinqu(HttpServletRequest request,HttpServletResponse response){
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			try{
			String teluserId=request.getAttribute(Constans.USER_INFO_USERID).toString();
			String taskId=request.getParameter("taskId");
			String orderId=request.getParameter("orderId");
			String teluser=request.getParameter("teluser");
			Task task=taskService.get112orderTaskbyid(Integer.parseInt(taskId));
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("taskId", taskId);//任务id
			map.put("orderId", orderId);//工单id
			map.put("task", task);
			map.put("teluserId", teluserId);//质检员
			map.put("teluser", teluser);//受理员
			String vmsg=workOrder112Service.updlinqu112order(map);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功,112工单号:"+orderId}));
			JSONObject json=new JSONObject();
			json.put("vmsg", vmsg);
			response.getWriter().print(json.toString());
			}catch (Exception e) {
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作失败"+e.getMessage()}));
				log.error(e);
			}
		}
		@RequestMapping(value="/getyltaskwk112List",method = RequestMethod.POST)
		public void  getyltaskwk112List(HttpServletRequest request,HttpServletResponse response){
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
			map.put("ordertype", "1");
			Set<String> list=taskService.getyl112ordertaskidList(map);
			String json="";
			List<WorkOrder112> orderlist  = new ArrayList<WorkOrder112>();
			int total = 0;
			if(!list.isEmpty()){
				orderlist=workOrder112Service.getyl112ordertaskList(list);
				total=workOrder112Service.getyl112ordertaskListcount(list);
			}
			json= "{\"Total\":" + total + " , \"Rows\":"
					+ JSONArray.fromObject(orderlist).toString() + "}";	
			try {
				System.out.println(json.toString());
				response.getWriter().print(json.toString());
			} catch (IOException e) {
				log.error("获取工单列表异常："+e.getMessage());
				
			}	
		}
		
}
