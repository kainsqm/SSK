package cn.sh.ideal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageHelper;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.AgentCallLog;
import cn.sh.ideal.model.CdmaTaskComplete;
import cn.sh.ideal.model.CnetVerdict;
import cn.sh.ideal.model.Modeltype;
import cn.sh.ideal.model.Task;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.model.WorkOrderCdmaSum;
import cn.sh.ideal.service.CnetVerdictService;
import cn.sh.ideal.service.ITaskService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.LogUitls;

@Controller
@RequestMapping(value = "/controller/verdict")
public class CnetVerdictController {
	private static Logger log = Logger.getLogger(CnetVerdictController.class);

	@Autowired
	private CnetVerdictService cnetVerdictService;

	@Autowired
	private ITaskService taskService;

	/**
	 * 跳转C网小结任务派发页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toCnetverdictsackList")
	public String toCnetverdictsackListPage(HttpServletRequest request,
			HttpServletResponse response) {
		return "task/cnet/cnet_verdictsackList";
	}

	/**
	 * 跳转C网小结领取电话小结页面
	 * 
	 * @author niewenqiang
	 * @date 2017-5-23
	 */
	@RequestMapping(value = "/toCnetverdictStartCdma")
	public String toCnetverdictStartCdma(HttpServletRequest request,
			HttpServletResponse response) {
		String taskid = request.getParameter("taskId");
		CnetVerdict cnet = cnetVerdictService.getCnetorderTaskbyid(Integer
				.parseInt(taskid));
		request.setAttribute("cnet", cnet);
		return "task/cnet/cnet_lingqu";
	}

	/**
	 * 查询c网小结任务派发列表
	 * 
	 * @param request
	 * @param response
	 * @param task
	 */
	@RequiresPermissions("taskcw:query")
	@RequestMapping(value = "/gettaskVerdictCnetList", method = RequestMethod.POST)
	public void gettaskVerdictCnetList(HttpServletRequest request,
			HttpServletResponse response, CnetVerdict cnetVerdict) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			String taskid = request.getParameter("taskid");
			if (taskid == null || "".equals(taskid)) {
				cnetVerdict.setTaskId(0);
			} else {
				cnetVerdict.setTaskId(Integer.parseInt(taskid));
			}
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			PageHelper.startPage(currentPage, pageSize);
			List<CnetVerdict> list = cnetVerdictService
					.getcNetVerdictList(cnetVerdict);
			int total = cnetVerdictService.getcNetVerdictListCount(cnetVerdict);
			String json = "{\"Total\":" + total + " , \"Rows\":"
					+ JSONArray.fromObject(list).toString() + "}";
			response.getWriter().print(json);
		} catch (Exception e) {
			log.error("C网小结任务列表查询出现异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 跳转c网任务小结新增页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toCnetstackInfo")
	public String toCnetstackInfo(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			List<UserInfo> qcUserList = taskService.getQcUser();// 查询质检员
			request.setAttribute("qcUserList", qcUserList);
		} catch (Exception e) {
			log.error("跳转c网任务小结新增页面出现异常：" + e.getMessage(), e);
		}
		return "task/cnet/edit_cnetstackInfo";
	}

	/**
	 * 获取新增任务小结页面下拉列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getCnetOrderType")
	public void getCnetOrderType(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject jsonObject = new JSONObject();
		try {
			List<Modeltype> serviceType = cnetVerdictService.getServiceType(); // 获取业务类型
			List<Modeltype> resultType = cnetVerdictService.getResultType(); // 获取小结类型
			List<Modeltype> complaintSource = cnetVerdictService
					.getComplaintSource(); // 获取故障来源
			jsonObject.put("serviceType", JSONArray.fromObject(serviceType));
			jsonObject.put("resultType", JSONArray.fromObject(resultType));
			jsonObject.put("complaintSource",
					JSONArray.fromObject(complaintSource));
			response.getWriter().print(jsonObject);
		} catch (Exception e) {
			log.error("获取新增任务小结页面下拉列表异常：" + e.getMessage(), e);
		}
	}

	/**
	 * 获取该任务选择的维度
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getCnetOrderTypeByTask")
	public void getCnetOrderTypeByTask(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject jsonObject = new JSONObject();
		try {
			int taskId = Integer.parseInt(request.getParameter("taskId"));
			CnetVerdict cnet = new CnetVerdict();
			cnet.setTaskId(taskId);
			List<CnetVerdict> list = cnetVerdictService
					.getcNetVerdictList(cnet);
			CnetVerdict netValue = list.get(0);
			List<Modeltype> serviceType = new ArrayList<Modeltype>();
			List<Modeltype> resultType = new ArrayList<Modeltype>();
			List<Modeltype> complaintSource = new ArrayList<Modeltype>();
			if (!netValue.getBussniessType().equals("")) {
				String[] serviceArray = netValue.getBussniessType().split(";");
				for (int i = 0; i < serviceArray.length; i++) {
					Modeltype model = new Modeltype();
					model.setId(serviceArray[i]);
					model.setText(serviceArray[i]);
					serviceType.add(model);
				}
			}
			if (!netValue.getTelsumType().equals("")) {
				String[] resultArray = netValue.getTelsumType().split(";");
				for (int i = 0; i < resultArray.length; i++) {
					Modeltype model = new Modeltype();
					model.setId(resultArray[i]);
					model.setText(resultArray[i]);
					resultType.add(model);
				}
			}
			if (!netValue.getGzlyType().equals("")) {
				String[] complaintArray = netValue.getGzlyType().split(";");
				for (int i = 0; i < complaintArray.length; i++) {
					Modeltype model = new Modeltype();
					model.setId(complaintArray[i]);
					model.setText(complaintArray[i]);
					complaintSource.add(model);
				}
			}
			jsonObject.put("serviceType", JSONArray.fromObject(serviceType));
			jsonObject.put("resultType", JSONArray.fromObject(resultType));
			jsonObject.put("complaintSource",
					JSONArray.fromObject(complaintSource));
			response.getWriter().print(jsonObject);
		} catch (Exception e) {
			log.error("领取页面获取C网小结该任务选择的维度出现异常：" + e.getMessage(), e);
		}
	}

	/**
	 * 检查c网任务名称是否存在
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkCnetTaskName", method = RequestMethod.POST)
	public void checkCnetTaskName(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		String taskName = request.getParameter("taskName");
		String oldName = request.getParameter("oldName");
		int count = 0;
		if (oldName == null || "".equals(oldName)) {
			count = cnetVerdictService.checkCnetTaskName(taskName);
		} else {
			if (oldName.equals(taskName)) {
				count = 0;
			} else {
				count = cnetVerdictService.checkCnetTaskName(taskName);
			}
		}
		JSONObject json = new JSONObject();
		json.put("data", count);
		response.getWriter().print(json);
	}

	/**
	 * C网任务派发修改
	 * 
	 * @param request
	 * @param response
	 * @param cnetVerdict
	 */
	@RequiresPermissions("taskcw:upd")
	@Log(methodname = "updCnetorderTask", modulename = "任务派发", funcname = "C网小结任务派发", description = "修改C网小结工单任务,{0}", code = "ZJ")
	@RequestMapping(value = "/updCnetorderTask", method = RequestMethod.POST)
	public void updCnetorderTask(HttpServletRequest request,
			HttpServletResponse response, CnetVerdict cnetVerdict) {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		JSONObject json =  new JSONObject();
		String userId = request.getAttribute("userid").toString();
		try {
			cnetVerdict.setCreateUserId(userId);
			cnetVerdict.setTaskStatus(cnetVerdict.getIsPublish());
			cnetVerdictService.updCnetOrderTask(cnetVerdict);
			json.put("result", "1");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "任务名称:【" + cnetVerdict.getTaskName()
							+ "】操作成功" }));
			response.getWriter().print(json.toString());
		} catch (Exception e) {
			try {
				json.put("result", "0");
				response.getWriter().print(json.toString());
			} catch (IOException e1) {
				log.error(e1);
			}
			log.error("操作人"+userId+"修改C网小结任务派发"+cnetVerdict.getTaskId()+"出现异常：" + e.getMessage(), e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { e.getMessage() }));
		}
	}

	/**
	 * C网任务派发新增
	 * 
	 * @param request
	 * @param response
	 * @param cnetVerdict
	 */
	@RequiresPermissions("taskcw:add")
	@Log(methodname = "addCnetorderTask", modulename = "任务派发", funcname = "C网小结任务派发", description = "新增C网小结工单任务,{0}", code = "ZJ")
	@RequestMapping(value = "/addCnetorderTask", method = RequestMethod.POST)
	public void addCnetorderTask(HttpServletRequest request,
			HttpServletResponse response, CnetVerdict cnetVerdict) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		String userId = request.getAttribute("userid").toString();
		try {
			cnetVerdict.setCreateUserId(userId);
			cnetVerdict.setTaskStatus(cnetVerdict.getIsPublish());
			cnetVerdictService.addCnetorderTask(cnetVerdict);
			json.put("result", "1");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "任务名称:【" + cnetVerdict.getTaskName()
							+ "】操作成功" }));
			response.getWriter().print(json.toString());
		} catch (Exception e) {
			try {
				json.put("result", "0");
				response.getWriter().print(json.toString());
			} catch (IOException e1) {
				log.error(e1);
			}
			log.error("用户"+userId+"新增c网小结任务派发出现异常：" + e.getMessage(), e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { e.getMessage() }));
		}
	}

	/**
	 * 修改跳转页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("taskcw:info")
	@RequestMapping(value = "updateCnetInfoShowPage")
	public String updateCnetInfoShowPage(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject json = null;
		String taskId = null;
		try {
			taskId = request.getParameter("taskId");
			String type = request.getParameter("type");
			if (taskId == null || "".equals(taskId)) {
				taskId = "0";
			}
			CnetVerdict cnetVerdict = cnetVerdictService
					.getCnetorderTaskbyid(Integer.parseInt(taskId));
			List<UserInfo> qcUserList = taskService.getQcUser();// 查询质检员
			String papersvalue = "";
			if ("".equals(cnetVerdict.getTelUser())
					|| null == cnetVerdict.getTelUser()) {

			} else {
				String Userid = cnetVerdict.getTelUser() + ",";
				String Customer = cnetVerdict.getTelUserName() + ",";
				String[] checkedUserid = Userid.split(",");
				String[] checkedCustomer = Customer.split(",");
				for (int i = 0; i < checkedUserid.length; i++) {
					papersvalue += checkedUserid[i] + "-" + checkedCustomer[i]
							+ ",";

				}
			}
			cnetVerdict.setPapers_hid(papersvalue);
			request.setAttribute("qcUserList", qcUserList);
			request.setAttribute("type", type);
			request.setAttribute("cnetVerdict", cnetVerdict);
			response.getWriter().print(json);
			return "task/cnet/edit_cnetstackInfo";
		} catch (Exception e) {
			log.error("进入C网小结任务派发"+taskId+"修改页面出现异常：" + e.getMessage(), e);
		}
		return "";
	}

	@RequiresPermissions("taskcw:del")
	@Log(methodname = "delCnetorderTask", modulename = "任务派发", funcname = "C网小结任务派发", description = "删除C网小结任务,{0}", code = "ZJ")
	@RequestMapping(value = "/delCnetorderTask", method = RequestMethod.POST)
	public void del112orderTask(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject json = null;
		String taskId = null;
		String userId = request.getAttribute("userid").toString();
		try {
			taskId = request.getParameter("taskId");
			if (taskId == null || "".equals(taskId)) {
				taskId = "0";
			}
			String status = request.getParameter("status");
			Map<String, String> map = new HashMap<>();
			map.put("taskStatus", status);
			map.put("taskId", taskId);
			cnetVerdictService.delCnetorderTask(map);
			json = new JSONObject();
			json.put("result", "1");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "任务名称:【" + taskId + "】操作成功" }));
			response.getWriter().print(json);
		} catch (Exception e) {
			log.error("操作人"+userId+"删除C网小结工单任务"+taskId+"出现异常：" + e.getMessage(), e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "任务名称:【" + taskId + "】" + e.getMessage() }));
		}
	}

	/*****
	 * 进入C网电话小结页面
	 * *****/
	@RequestMapping(value = "/toqueryTaskForQc", method = RequestMethod.GET)
	public String toqueryTaskForQc(HttpServletRequest request,
			HttpServletResponse response) {
		return "task/cnet/execute_centtask";
	}

	/******
	 * 质检员执行C网电话小结任务
	 * 
	 * @author niewenqiang
	 * @date 2017-5-22
	 * ******/
	@RequestMapping(value = "/queryTaskForQc", method = RequestMethod.POST)
	public void queryTaskForQc(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String qcUserId = request.getAttribute("userid").toString();
		try {
			Map<String, String> map = new HashMap<String, String>();
			String taskName = "";
			if (request.getParameter("taskName") != null) {
				taskName = java.net.URLDecoder.decode(
						request.getParameter("taskName"), "UTF-8");
			}
			map.put("taskName", taskName);
			map.put("taskId", request.getParameter("taskId"));
			map.put("taskStartTime", request.getParameter("taskStartTime"));
			map.put("taskSEndTime", request.getParameter("taskSEndTime"));
			map.put("qcUserId", qcUserId);
			map.put("taskStatus", request.getParameter("taskStatus"));
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			PageHelper.startPage(currentPage, pageSize);
			int counts = cnetVerdictService.queryListForQcCount(map);
			List<CdmaTaskComplete> taskList = cnetVerdictService
					.queryListForQc(map);
			JSONObject object = new JSONObject();
			object.put("Rows", taskList);
			object.put("Total", counts);
			response.getWriter().print(object.toString());
		} catch (Exception e) {
			log.error("操作人"+qcUserId+"查询C网电话小结任务（质检员）列表失败：" + e.getMessage(), e);
		}
	}

	/*****
	 * 任务领取页面加载数据
	 * 
	 * @author niewenqiang
	 * @date 2017-5-22
	 ****/
	@RequestMapping(value = "/getCdmaSumForQc", method = RequestMethod.POST)
	public void getCdmaSumForQc(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String taskUserId = request.getParameter("taskQcUserId");
		try {
			String taskTime = request.getParameter("taskTime");
			String bussniessType = request.getParameter("bussniessType"); // 业务类型
			String telsumType = request.getParameter("telsumType"); // 小结类型
			String gzlyType = request.getParameter("gzlyType"); // 故障来源
			String type = "";
			String teltype = "";
			String gztyle = "";
			// 为查询条件拼接in参数类型
			if (bussniessType != "") {
				String[] typeArray = bussniessType.split(";");
				for (int i = 0; i < typeArray.length; i++) {
					type += "'" + typeArray[i] + "',";
				}
				type = type.substring(0, type.length() - 1); // 去除最后一个,
			}
			if (telsumType != "") {
				String[] typeArray = telsumType.split(";");
				for (int i = 0; i < typeArray.length; i++) {
					teltype += "'" + typeArray[i] + "',";
				}
				teltype = teltype.substring(0, teltype.length() - 1); // 去除最后一个,
			}
			if (gzlyType != "") {
				String[] typeArray = gzlyType.split(";");
				for (int i = 0; i < typeArray.length; i++) {
					gztyle += "'" + typeArray[i] + "',";
				}
				gztyle = gztyle.substring(0, gztyle.length() - 1); // 去除最后一个,
			}
			List<WorkOrderCdmaSum> cdmaList = new ArrayList<WorkOrderCdmaSum>();
			int counts = 0;
			// 查询出C网电话小结供质检员领取
			Map<String, String> map = new HashMap<String, String>();
			map.put("taskUserId", taskUserId);
			map.put("taskTime", taskTime);
			map.put("bussniessType", type);
			map.put("telsumType", teltype);
			map.put("gzlyType", gztyle);
			String searchCdma = cnetVerdictService.searchCdma(map);
			if (searchCdma == null || searchCdma.trim().equals("")
					|| searchCdma.equals("' '")) {
				searchCdma = "''";
			} else {
				// 截取最后一个，字符串
				searchCdma = searchCdma.substring(0, searchCdma.length() - 1);
			}
			map.put("searchCdma", searchCdma);
			cdmaList = cnetVerdictService.searchCdmaSumToGet(map);
			counts = cnetVerdictService.searchCdmaSumToGetCount(map);
			JSONObject object = new JSONObject();
			object.put("Rows", cdmaList);
			object.put("Total", counts);
			response.getWriter().print(object.toString());
		} catch (Exception e) {
			log.error("taskUserId="+taskUserId+"查询C网电话小结任务领取页面显示失败：" + e.getMessage(), e);

		}
	}

	/**
	 * 质检员一键领取C网电话小结
	 * 
	 * @author niewenqiang
	 * @date 2017-5-23
	 * @return
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	@Log(methodname = "oneKeyGetCdmaSum", modulename = "质检员一键领取C网电话小结任务", funcname = "质检员一键领取C网电话小结任务", description = "质检员一键领取C网电话小结任务,{0}", code = "ZJ")
	@RequestMapping(value = "/oneKeyGetCdmaSum", method = RequestMethod.POST)
	public void oneKeyGetCdmaSum(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pwt = null;
		JSONObject object = new JSONObject();
		String taskUserId = request.getParameter("taskQcUserId");
		String taskTime = request.getParameter("taskTime");
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("taskUserId", taskUserId);
			map.put("taskTime", taskTime);
			log.info("C网电话小结一键领取跟踪：taskQcUserId：" + taskUserId + " taskTime："
					+ taskTime);
			String resultString = cnetVerdictService
					.updateOneKeyGetCdmaSum(map);
			
			object.put("result", resultString);
			
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "操作成功" }));
		} catch (Exception e) {
			object.put("result", "数据缺失");
			log.error("质检员一键领取C网电话小结任务taskQcUserId：" + taskUserId + " taskTime："
					+ taskTime+"出现异常：" + e.getMessage(),e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "出现异常：" + e.getMessage() }));
		} finally {
			try {
				pwt = response.getWriter();
				pwt.print(object.toString());
				pwt.close();
			} catch (IOException e) {
				log.error(e);
			}
		}
	}

	/**
	 * 质检员单个领取电话小结
	 * 
	 * @author niewenqiang
	 * @date 2017-5-23
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	@Log(methodname = "getCdma", modulename = "质检员领取C网电话小结", funcname = "质检员领取C网电话小结", description = "质检员领取C网电话小结,{0}", code = "ZJ")
	@RequestMapping(value = "/getCdma", method = RequestMethod.POST)
	public void getCdma(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String taskUserId = request.getParameter("taskQcUserId");
		String taskTime = request.getParameter("taskTime");
		try {
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("text/html;charset=UTF-8");
			String taskId = request.getParameter("taskId");
			String telUser = request.getParameter("telUser");
			String telUserWorkId = cnetVerdictService
					.getBigWorkIdBySmail(telUser);
			String userID = request.getAttribute("userid").toString();
			String pkAutoId = request.getParameter("pkAutoId");
			Map<String, String> map = new HashMap<String, String>();
			map.put("taskId", taskId);
			map.put("taskUserId", taskUserId);
			map.put("taskTime", taskTime);
			map.put("telUser", telUserWorkId);
			map.put("pkAutoId", pkAutoId);
			map.put("userID", userID);
			String resultString = cnetVerdictService.updateGetCdmaSum(map);
			JSONObject object = new JSONObject();
			object.put("result", resultString);
			response.getWriter().print(object.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "操作成功,任务id:" + taskId }));
		} catch (Exception e) {
			JSONObject object = new JSONObject();
			object.put("result", "领取失败");
			response.getWriter().print(object.toString());
			log.error("质检员C网电话小结领取taskUserId="+taskUserId+"taskTime="+taskTime+"失败异常：" + e.getMessage(),e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "出现异常：" + e.getMessage() }));
		} finally {
			try {
				response.getWriter().close();
			} catch (IOException e) {
				log.error(e);
			}
		}
	}

	/**
	 * 查询已领任务工单小结
	 * 
	 * @author niewenqiang
	 * @date 2017-5-25
	 */
	@RequestMapping(value = "/queryTaskCdmaSum", method = RequestMethod.POST)
	public void queryTaskRecord(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			Map<String, String> map = new HashMap<String, String>();
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			PageHelper.startPage(currentPage, pageSize);
			String taskId = request.getParameter("taskId");
			String qcUser = request.getParameter("qcUser");
			String taskStatus = request.getParameter("taskStatus");
			map.put("taskId", taskId);
			map.put("qcUser", qcUser);
			map.put("taskStatus", taskStatus);
			List<WorkOrderCdmaSum> cdmaList = cnetVerdictService
					.getCdmaedTaskByUser(map);
			int counts = cnetVerdictService.getCdmaedTaskByUserCount(map);
			JSONObject object = new JSONObject();
			object.put("Rows", cdmaList);
			object.put("Total", counts);
			response.getWriter().print(object.toString());
		} catch (Exception e) {
			log.error("查询C网电话小结已领任务工单小结出现异常：" + e.getMessage(), e);
		}
	}

	/**
	 * 质检员查询自己任务完成情况
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/showWcqkForQc", method = RequestMethod.GET)
	public String showWcqkForQc(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String taskQcUserId = request.getParameter("taskQcUserId");
		try {
			String taskName = URLDecoder.decode(URLDecoder.decode(
					request.getParameter("taskName"), "UTF-8"), "utf-8");
			String qcUserName = URLDecoder.decode(URLDecoder.decode(
					request.getParameter("qcUserName"), "UTF-8"), "utf-8");
			String taskId = request.getParameter("taskId");
			// 获取c网task详情
			CnetVerdict cnetver = cnetVerdictService
					.getCnetorderTaskbyid(Integer.parseInt(taskId));
			request.setAttribute("qcDcount", cnetver.getCsrTopDCount());
			request.setAttribute("taskName", taskName);
			request.setAttribute("qcUserName", qcUserName);
			request.setAttribute("taskId", taskId);
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");// yyyy-MM-dd
																				// HH:mm:ss
																				// E
			String taskEndtime = cnetver.getTaskEndTime();
			request.setAttribute("taskEndTime", taskEndtime);
			String todayDate = dateformat.format(new Date());
			request.setAttribute("todayDate", todayDate);
			request.setAttribute("taskQcUserId", taskQcUserId);
			return "task/cnet/cent_wcqk_items_qc";
		} catch (Exception e) {
			log.error("质检员taskQcUserId="+taskQcUserId+"查询自己C网电话小结任务完成情况出现异常：" + e.getMessage(), e);
		}
		return "";
	}

	/**
	 * 任务完成情况详细查询
	 * 
	 * @author niewenqiang
	 * @date 2017-5-25
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/showTaskCompleteInforqc", method = RequestMethod.POST)
	public void showTaskCompleteInforqc(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String taskQcUserId = request.getParameter("taskQcUserId");
		try {
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			PageHelper.startPage(currentPage, pageSize);
			String taskStatus = request.getParameter("taskStatus");
			Map<String, String> map = new HashMap<String, String>();
			map.put("taskQcUserId", taskQcUserId);
			map.put("taskStatus", taskStatus);
			List<CdmaTaskComplete> taskInfoList = cnetVerdictService
					.queryTaskCompleteInforc(map);
			int counts = cnetVerdictService.queryTaskCompleteInforcCount(map);
			JSONObject object = new JSONObject();
			object.put("Rows", taskInfoList);
			object.put("Total", counts);
			response.getWriter().print(object.toString());
		} catch (Exception e) {
			log.error("质检员taskQcUserId="+taskQcUserId+"查询C网电话小结任务完成情况列表出现异常：" + e.getMessage(), e);
		}
	}

	/**
	 * 督导查询任务完成情况
	 * 
	 * @author niewenqiang
	 * @date 2017-5-25
	 */
	@RequestMapping(value = "/getTaskCompleteInfoByDuDao", method = RequestMethod.POST)
	public void taskCompleteInfo(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			Map<String, String> map = new HashMap<String, String>(); // 日期
			String taskId = request.getParameter("taskId");
			String taskStatus = request.getParameter("taskStatus");
			map.put("taskId", taskId);
			map.put("taskStatus", taskStatus);
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			PageHelper.startPage(currentPage, pageSize);
			List<CdmaTaskComplete> taskInfoList = cnetVerdictService.queryTaskCompleteInfo(map);
			int counts = cnetVerdictService.queryTaskCompleteInfoCount(map);
			JSONObject object = new JSONObject();
			object.put("Rows", taskInfoList);
			object.put("Total", counts);
			response.getWriter().print(object.toString());
		} catch (Exception e) {
			log.error("督导查询C网电话小结任务完成情况出现异常" + e.getMessage(), e);
		}
	}

	/**
	 * 任务完成情况列表 已质检员为维度释放电话小结
	 * 
	 * @author niewenqiang
	 * @date 2017-5-26
	 */
	@Log(methodname = "releaseCdmaByUser", modulename = "执行工作任务", funcname = "批量释放小结", description = "批量释放小结,{0}", code = "ZJ")
	@RequestMapping(value = "/releaseCdmaByUser", method = RequestMethod.POST)
	public void releaseRecordByUser(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		String qcUserItem = request.getParameter("qcUserItem");
		String taskId = request.getParameter("taskId");
		String userId = request.getAttribute("userid").toString();
		String resultString="";
		try {
			resultString=cnetVerdictService.releaseCdmaByQcUser(qcUserItem, taskId);
			JSONObject json = new JSONObject();
			json.put("result", resultString);
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
					.setObjects(
							new Object[] { "操作成功,任务编号:" + taskId + ",质检员为"
									+ qcUserItem }));
		} catch (Exception e) {
			log.error("操作人"+userId+"批量释放C网电话小结,任务编号:" + taskId + ",质检员为"
									+ qcUserItem+"失败：" + e.getMessage(), e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "出现异常：" + e.getMessage() }));
		} finally {
			try {
				response.getWriter().close();
			} catch (IOException e) {
				log.error(e);
			}
		}

	}

	/**
	 * 督导查询任务中质检员的具体完成情况
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/showTaskWcqkItems", method = RequestMethod.GET)
	public String showTaskWcqkItems(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String taskQcUserId = request.getParameter("taskQcUserId");
		try {
			String taskName = URLDecoder.decode(URLDecoder.decode(
					request.getParameter("taskName"), "UTF-8"), "utf-8");
			String qcUserName = URLDecoder.decode(URLDecoder.decode(
					request.getParameter("qcUserName"), "UTF-8"), "utf-8");
			String taskId = request.getParameter("taskId");
			String qcUser = request.getParameter("qcUser");
			Map<String, String> map = new HashMap<String, String>();
			map.put("taskQcUserId", taskQcUserId);
			CnetVerdict cnetver = cnetVerdictService
					.getCnetorderTaskbyid(Integer.parseInt(taskId));
			request.setAttribute("qcDcountItem", cnetver.getCsrTopDCount());
			request.setAttribute("taskName", taskName);
			request.setAttribute("qcUser", qcUser);
			request.setAttribute("qcUserName", qcUserName);
			request.setAttribute("taskId", taskId);
			request.setAttribute("taskQcUserId", taskQcUserId);
			return "task/cnet/cnet_wcqk_items_sys";
		} catch (Exception e) {
			log.error("督导查询任务中质检员taskQcUserId="+taskQcUserId+"的具体完成情况异常：" + e.getMessage(), e);
		}
		return "";
	}

	/**
	 * 质检班长批量释放录音(以天为维度)
	 * 
	 * @author niewenqiang
	 * @date 2017-5-26
	 */
	@Log(methodname = "releaseCdma", modulename = "执行工作任务", funcname = "以天为维度批量释放C网电话小结", description = "以天为维度批量释放C网电话小结,{0}", code = "ZJ")
	@RequestMapping(value = "/releaseCdma", method = RequestMethod.POST)
	public void releaseCdma(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		String cDateItem = request.getParameter("cDateItem");
		String taskUserId = request.getParameter("taskUserId");// 质检员任务相关表ID
		String taskId = request.getParameter("taskId");
		String qcUser = request.getParameter("qcUser");
		String userId = request.getAttribute("userid").toString();
		try {
			//调用批量释放方法
			String resultString = cnetVerdictService.releaseCdmaByQcDate(cDateItem, taskId, qcUser, taskUserId);
			JSONObject json = new JSONObject();
			json.put("result", resultString);
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "操作成功,任务id:" + taskId + "质检员为" + qcUser }));
		} catch (Exception e) {
			log.error("操作人"+userId+"以天为维度批量释放C网电话小结任务id:" + taskId + "质检员为" + qcUser+"出现异常：" + e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "出现异常：" + e.getMessage() }));
		} 
	}
}
