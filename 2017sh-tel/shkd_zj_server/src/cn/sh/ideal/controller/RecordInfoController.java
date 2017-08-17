package cn.sh.ideal.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.taglibs.standard.tag.common.core.OutSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.QC;
import cn.sh.ideal.model.QCDetail;
import cn.sh.ideal.model.RecordInfo;
import cn.sh.ideal.model.SecondaryQc;
import cn.sh.ideal.model.SysCode;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.model.WorkOrder112;
import cn.sh.ideal.model.WorkOrderCdma;
import cn.sh.ideal.model.WorkOrderCdmaSum;
import cn.sh.ideal.model.WorkTelSum112;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.IQCDetailService;
import cn.sh.ideal.service.IQCService;
import cn.sh.ideal.service.IRecordInfoSevice;
import cn.sh.ideal.service.ISecondaryQcService;
import cn.sh.ideal.service.ISysCodeService;
import cn.sh.ideal.service.IUserInfoService;
import cn.sh.ideal.service.IWorkOrder112Service;
import cn.sh.ideal.service.IWorkOrderCdmaService;
import cn.sh.ideal.service.IWorkOrderCdmaSumService;
import cn.sh.ideal.util.ConfigProperties;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.LogUitls;
import cn.sh.ideal.util.ToolString;

import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/controller/record")
public class RecordInfoController {
	private static Logger log = Logger.getLogger(RecordInfoController.class);

	private static final String CHARSET = "UTF-8";
	@Resource
	private IRecordInfoSevice recordInfoService;
	@Resource
	private IQCService QCService;
	@Resource
	private IQCDetailService QCDetailService;
	@Resource
	private IWorkOrder112Service workOrder112Service;
	@Resource
	private ISysCodeService sysCodeService;
	@Resource
	private IUserInfoService userInfoService;
	@Resource
	private ISecondaryQcService secondaryQcService;
	@Resource
	private  IWorkOrderCdmaService   workOrderCdma;
	@Resource
	private IWorkOrderCdmaSumService workorderCdmaService;

	@Value("#{config['recordServer']}")
	private String recordServer = "";

	@Value("#{config['recordUserName']}")
	private String recordUserName = "";

	/*@Value("#{config['recordPwd']}")
	private String recordPwd = "";*/

	@Value("#{config['recordRootDir']}")
	private String recordRootDir = "";

	@Value("#{config['recordPort']}")
	private int recordPort = 0;

	@RequiresPermissions("record:query")
	@RequestMapping(value="/togetRecordList",method=RequestMethod.GET)
	public String togetRecordList(){		
		return "recordList";
	}
	

	@RequestMapping(value = "/getRecordList", method = RequestMethod.POST)
	public void getRecordList(HttpServletRequest request,
			HttpServletResponse response, RecordInfo recordInfo) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");

		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		List<RecordInfo> recordList = recordInfoService
				.selectRecordInfo(recordInfo);

		int total = recordInfoService.selectCount(recordInfo);
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(recordList).toString() + "}";

		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			log.error("获取录音列表异常：" + e.getMessage());			
		}

	}
	
	@RequiresPermissions("record:check")
	@RequestMapping(value = "/getRecord", method = RequestMethod.POST)
	public String getRecord(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			Integer recordid = null;
			if (request.getParameter("recordid") != null
					&& !request.getParameter("recordid").equals("")) {
				recordid = Integer.parseInt(request.getParameter("recordid"));
			}
			RecordInfo record = recordInfoService.selectByPrimaryKey(recordid);
			// 受理员
			request.setAttribute("rd", record);
			request.setAttribute("agentName", record.getAgentName());
			request.setAttribute("startTime", record.getStartTime());
			request.setAttribute("stoptimeq", DateUtil.getinfoDate(record
					.getStopTime(), -5));
			request.setAttribute("stoptimeh", DateUtil.getinfoDate(record
					.getStopTime(), 5));
			request.setAttribute("agentworkid", record.getSmallWorkid());
			request.setAttribute("recordId", record.getRecordId());
			request.setAttribute("reservedThree", record.getReservedThree());
			request.setAttribute("listentime", DateUtil
					.getDateTimeStr(new Date()));
			request.setAttribute("workname", ((vSysuser) request
					.getAttribute(Constans.USER)).getUserName());

			// 获取录音关联的工单信息以及最新的评分信息
			// 判断该录音是否关联工单
			QC qc = QCService.selectByRecordId(recordid);
			if (qc != null) {// 已经关联
				request.setAttribute("display", "0");// 关联工单不显示
				// 获取关联的工单信息
				if(qc.getQc_type()==7){
				WorkOrder112 wk = workOrder112Service.selectByPrimaryKey(qc
						.getWorkorderId());
				request.setAttribute("wk", wk);
				}else if(qc.getQc_type()==8){
					WorkOrderCdma wk=workOrderCdma.getOrderCdma(qc
							.getWorkorderId());
					request.setAttribute("wk", wk);
				}else{
					request.setAttribute("wk", "");
				}
				request.setAttribute("qctype", qc.getQc_type());
				// 获取最新的评分信息
				QCDetail qcDetail = QCDetailService.selectDetailByQcid(qc
						.getQcId());
				request.setAttribute("qcDetail", qcDetail);
				request.setAttribute("qcid", qc.getQcId());
				request
						.setAttribute("workname", userInfoService
								.userinfoByuserid(qcDetail.getQcUserId())
								.getUserName());
				// 根据业务类型和是否tl9000加载处理情况
				SysCode syscode = new SysCode();
				syscode.setPid(Integer.parseInt(qcDetail.getBusinessType()));
				if("1".equals(qcDetail.getIsTl9000())){
					syscode.setIsTl9000(qcDetail.getIsTl9000());
				}
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
				} else {
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
			log.error("获取录音详情异常：" + e.getMessage());
		}
		return "record_qc";
	}

	@Log(methodname = "downRecord", modulename = "录音信息查询", funcname = "录音下载", description = "录音下载,{0}", code = "ZJ")
	@RequestMapping(value = "/downRecord", method = RequestMethod.GET)
	public void downRecord(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String fileName = request.getParameter("reservedThree");
		HttpURLConnection connection = null;
		InputStream is = null;
		OutputStream out=null;
		PrintWriter p = null;  
		ServletOutputStream outputStream = null;
		if (fileName != null && !fileName.equals("")) {
			try {
				// 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
				String GET_URL = "http://"+ConfigProperties.getProperty("interfaceurl")+"/shkd_interface/controller/record/getFileHttpPath.do?refid="
						+ fileName;
				String getURL = GET_URL;
				URL getUrl = new URL(getURL);
				response.setHeader("Content-Disposition",
						"attachment; filename=" + fileName + ".wav");
				// 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
				// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
				connection = (HttpURLConnection) getUrl.openConnection();
				// 进行连接，但是实际上get
				// request要在下一句的connection.getInputStream()函数中才会真正发到
				// 服务器
				connection.connect();
				// 取得输入流，并使用Reader读取
				is = connection.getInputStream();
				  //获取URLConnection对象对应的输出流  
				outputStream =response.getOutputStream();
				try {
					int cache = 10 * 1024;
					byte[] buffer = new byte[cache];
					int ch = 0;
					while ((ch = is.read(buffer)) != -1) {
						outputStream.write(buffer, 0, ch);
					}
				} catch (Exception e) {
					
				}finally{
					outputStream.flush();
					outputStream.close();
				}

				LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
						.setObjects(new Object[] { "操作成功,录音流水号:"+fileName }));
			} catch (Exception e) {
				log.error("下载录音异常：" + e);
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
						.setObjects(new Object[] { e.getMessage() }));
			} finally {
				try {
					if(is!=null){
						is.close();
					}
					if(connection!=null){
						connection.disconnect();
					}
				} catch (IOException e) {
				}
				// 断开连接
				
			}

		}

	}
	
	/**
	 * 获取评分信息详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions(value = {"his112order:qc", "hiscworder:qc","his112xj:qc","hiscwxj:qc"}, logical = Logical.OR)
	@RequestMapping(value = "/getAllQc", method = RequestMethod.POST)
	public String getAllQc(HttpServletRequest request,
			HttpServletResponse response) {
			String qcflag=null;//判断评分类型1.录音评分2.112工单评分3.c网工单评分4.112小结评分5.c网小结评分
		try {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			String qcid=request.getParameter("qcid");
			qcflag =request.getParameter("qcflag");
			QC qc= QCService.selectByQcid(Integer.parseInt(qcid));
			Integer recordid = null;
			if(qc!=null){	
					recordid = qc.getRecordId();
			}	
			RecordInfo record = recordInfoService.selectByPrimaryKey(recordid);
			if(record!=null){
				request.setAttribute("rd", record);
				request.setAttribute("agentName", record.getAgentName());
				request.setAttribute("startTime", record.getStartTime());
				request.setAttribute("stoptimeq", DateUtil.getinfoDate(record
						.getStopTime(), -5));
				request.setAttribute("stoptimeh", DateUtil.getinfoDate(record
						.getStopTime(), 5));
				request.setAttribute("agentworkid", record.getSmallWorkid());
				request.setAttribute("recordId", record.getRecordId());
				request.setAttribute("reservedThree", record.getReservedThree());
			}
			// 受理员		
			request.setAttribute("listentime", DateUtil
					.getDateTimeStr(new Date()));
			request.setAttribute("workname", ((vSysuser) request
					.getAttribute(Constans.USER)).getUserName());

			// 获取录音关联的工单信息以及最新的评分信息
			// 判断该录音是否关联工单
			if (qc != null) {// 已经关联
				request.setAttribute("display", "0");// 关联工单不显示
				// 获取关联的工单信息
				if(qc.getQc_type()==7||qc.getQc_type()==2){
				WorkOrder112 wk = workOrder112Service.selectByPrimaryKey(qc
						.getWorkorderId());
				request.setAttribute("complaintTime", wk.getDeclarationTime());
				request.setAttribute("wk", wk);
				request.setAttribute("workorderId", qc
						.getWorkorderId());
				UserInfo user =new UserInfo();
				user.setSmailWorkid(ToolString.subNumber(wk.getFirstAgentUserid()));	
				if(record==null){
					request.setAttribute("rd", record);
					request.setAttribute("agentName", userInfoService.selectbysmailworkId(user).getUserName());
					request.setAttribute("agentworkid", ToolString.subNumber(wk.getFirstAgentUserid()));
				}
				
				}else if(qc.getQc_type()==8||qc.getQc_type()==3){
					WorkOrderCdma wk=workOrderCdma.getOrderCdma(qc
							.getWorkorderId());
					request.setAttribute("wk", wk);
					request.setAttribute("complaintTime", wk.getComplaintTime());
					UserInfo user=new UserInfo();
					user.setSmailWorkid(ToolString.subNumber(wk.getReceiptOpId()));
					UserInfo user2=userInfoService.selectbysmailworkId(user);
					if(user2!=null){
						request.setAttribute("agentName", user2.getUserName());
					}
					request.setAttribute("receiptOpId", ToolString.subNumber(wk.getReceiptOpId()));
					request.setAttribute("serialCdma", qc
							.getWorkorderId());
				}else if(qc.getQc_type()==9||qc.getQc_type()==4){
					if(qc.getTelsumId()!=null||qc.getTelsumId()!=0){
					WorkTelSum112 wk112=workOrder112Service.get112jbyid(qc.getTelsumId());
					WorkOrder112 wk = workOrder112Service.selectByPrimaryKey(wk112.getWorkorderId());
					request.setAttribute("complaintTime", wk112.getTelsumTime());
					request.setAttribute("wk", wk);
					UserInfo user =new UserInfo();
					user.setSmailWorkid(ToolString.subNumber(wk112.getTelsumWorkid()));	
					if(record==null){
						request.setAttribute("rd", record);
						request.setAttribute("agentName", userInfoService.selectbysmailworkId(user).getUserName());
						request.setAttribute("agentworkid", ToolString.subNumber(wk112.getTelsumWorkid()));
					}
					}
				}else if(qc.getQc_type()==10||qc.getQc_type()==5){
					if(qc.getTelsumId()!=null||qc.getTelsumId()!=0){
						WorkOrderCdmaSum wkcw=workorderCdmaService.getcwbyid(qc.getTelsumId());
						WorkOrderCdma wk = workOrderCdma.getOrderCdma(wkcw.getSerialCdma());
						request.setAttribute("complaintTime", wkcw.getResultTime());
						request.setAttribute("wk", wk);
						UserInfo user =new UserInfo();
						user.setSmailWorkid(ToolString.subNumber(wkcw.getOpId()));	
						if(record==null){
							request.setAttribute("rd", record);
							request.setAttribute("agentName", userInfoService.selectbysmailworkId(user).getUserName());
							request.setAttribute("agentworkid", ToolString.subNumber(wkcw.getOpId()));
						}
						}
				}
				request.setAttribute("qctype", qc.getQc_type());
				// 获取最新的评分信息
				QCDetail qcDetail = QCDetailService.selectDetailByQcid(qc
						.getQcId());
				request.setAttribute("qcDetail", qcDetail);
				request.setAttribute("qcid", qc.getQcId());
				request
						.setAttribute("workname", userInfoService
								.userinfoByuserid(qcDetail.getQcUserId())
								.getUserName());
				// 根据业务类型和是否tl9000加载处理情况
				SysCode syscode = new SysCode();
				syscode.setPid(Integer.parseInt(qcDetail.getBusinessType()));
				if("1".equals(qcDetail.getIsTl9000())){
					syscode.setIsTl9000(qcDetail.getIsTl9000());
				}	
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
				} else {
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
				int num = secondaryQcService.seccountbyqcId(Integer.parseInt(qcid));
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
			log.error("获取录音详情异常：" + e.getMessage());
		}
		if("1".equals(qcflag)){
		return "record_qc";
		}else if("2".equals(qcflag)){
		return "112/gongdan_qc";
		}else if("3".equals(qcflag)){
		return "gongdancw_qc";
		}else if("4".equals(qcflag)){
		return "dhxj/gongdan_112xjqc";	
		}else if("5".equals(qcflag)){
		return "dhxj/gongdan_cwxjqc";
		}else{
			return "record_qc";
		}
	}
	
	
	
}
