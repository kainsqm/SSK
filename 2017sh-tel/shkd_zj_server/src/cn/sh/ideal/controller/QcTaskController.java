package cn.sh.ideal.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.AgentCallLog;
import cn.sh.ideal.model.AgentWorkpaper;
import cn.sh.ideal.model.Task;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.service.ITaskService;
import cn.sh.ideal.util.CommUtil;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.LogUitls;

import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/controller/task")
public class QcTaskController {
	private static Logger log = Logger.getLogger(QcTaskController.class);
	@Resource
	private ITaskService taskService;

	/**
	 * 查询质检员
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("taskrecord:add")
	@RequestMapping(value = "/toTaskInfo", method = RequestMethod.GET)
	public String toTaskInfo(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try{
		List<UserInfo> qcUserList = taskService.getQcUser();// 查询质检员
		request.setAttribute("qcUserList", qcUserList);
		return "task/edit_taskInfo";
		}catch(Exception e){
			log.error("查询质检员异常："+e.getMessage());
			
		}
		return "";
	}
	/**
	 * 质检员查询自己任务完成情况
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
		try{
		String taskName=URLDecoder.decode(URLDecoder.decode(request.getParameter("taskName"),"UTF-8"), "utf-8"); 
		String qcUserName=URLDecoder.decode(URLDecoder.decode(request.getParameter("qcUserName"),"UTF-8"), "utf-8");
		String taskId=request.getParameter("taskId");
		String taskQcUserId=request.getParameter("taskQcUserId");
		Map<String, String> map = new HashMap<String, String>();
		map.put("taskQcUserId", taskQcUserId);
		 List<Task> taskInfoList= taskService.queryTaskCompleteInforc(map);
		 if (taskInfoList!=null) {
		for (int i = 0; i < taskInfoList.size(); i++) {
			taskInfoList.get(i).setQcDcountItem(taskInfoList.get(i).getQcDCount().split(","));
		}	
			request.setAttribute("qcDcountItem", taskInfoList.get(0)
					.getQcDcountItem());
		}
		request.setAttribute("taskName", taskName);
		request.setAttribute("qcUserName", qcUserName);
		request.setAttribute("taskId", taskId);
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");//yyyy-MM-dd HH:mm:ss E
		Task taskInfo = taskService.getOneTaskInfo(Integer.parseInt(taskId));
		String taskEndtime=taskInfo.getTaskEndTime();
		request.setAttribute("taskEndTime",taskEndtime);
	
		String todayDate=dateformat.format(new Date());
		request.setAttribute("todayDate", todayDate);
		request.setAttribute("taskQcUserId", taskQcUserId);
		return "task/task_wcqk_items_qc";
		}catch(Exception e){
			log.error("质检员查询自己任务完成情况异常："+e.getMessage());
		}
		return "";
	}
	
	
	/**
	 * 查询任务完成情况
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/showTaskWcqkItems", method = RequestMethod.GET)
	public String showTaskWcqkItems(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try{
		String taskName=URLDecoder.decode(URLDecoder.decode(request.getParameter("taskName"),"UTF-8"), "utf-8"); 
		String qcUserName=URLDecoder.decode(URLDecoder.decode(request.getParameter("qcUserName"),"UTF-8"), "utf-8");
		String taskId=request.getParameter("taskId");
		String taskQcUserId=request.getParameter("taskQcUserId");
		String qcUser=request.getParameter("qcUser");
		Map<String, String> map = new HashMap<String, String>();
		map.put("taskQcUserId", taskQcUserId);
		List<Task> taskInfoList = taskService.queryTaskCompleteInforc(map);
		if ( null !=taskInfoList) {
		for (int i = 0; i < taskInfoList.size(); i++) {
			taskInfoList.get(i).setQcDcountItem(taskInfoList.get(i).getQcDCount().split(","));
		}
			request.setAttribute("qcDcountItem", taskInfoList.get(0)
					.getQcDcountItem());
		}
		request.setAttribute("taskName", taskName);
		request.setAttribute("qcUser", qcUser);
		request.setAttribute("qcUserName", qcUserName);
		request.setAttribute("taskId", taskId);
		request.setAttribute("taskQcUserId", taskQcUserId);
		return "task/task_wcqk_items_sys";
		}catch(Exception e){
			log.error("查询任务完成情况异常："+e.getMessage());
		}
		return "";
	}
	
	
	/**
	 * 查询受理员
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryManagerBaTask", method = RequestMethod.POST)
	public void queryManagerBaTask(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			Map<String, String> map = new HashMap<String, String>(); // 
			String page = request.getParameter("page");// 页码
			String pagesize = request.getParameter("pagesize");// 
			String workId = request.getParameter("workId");// 工号
			String userName = request.getParameter("userName");// 姓名
			String smailworkid=request.getParameter("smailworkid");//所属中心
			if (page == null || page == "") {
				page = "1";
			}
			if (pagesize == null || pagesize == "") {
				pagesize = "10";
			}
			map.put("smailworkid", smailworkid);	
			map.put("workId", workId);
			map.put("userName", userName);
			int counts = taskService.getCountManager(map);
			if (Integer.parseInt(page) == 1) {
				map.put("start", "0");
				map.put("end", String.valueOf(Integer.parseInt(pagesize)));
			} else if (Integer.parseInt(page) > 1) {
				map.put("start", String.valueOf((Integer.parseInt(page) - 1)
						* Integer.parseInt(pagesize)));
				map.put("end", String.valueOf(pagesize));
			}		
			List<UserInfo> qcUserList = taskService.getManager(map);// 查询受理员
			JSONObject object = new JSONObject();
			object.put("Rows", qcUserList);
			object.put("Total", counts);
			response.getWriter().print(object.toString());
		} catch (Exception e) {
			log.error("新增工作任务派发获取受理员列表失败：" + e.getMessage());
		}
	}

	@RequiresPermissions("taskrecord:query")
	@RequestMapping(value = "/togetTaskDistribut", method = RequestMethod.GET)
	public String togetTaskDistribut(HttpServletRequest request,
			HttpServletResponse response) {
		return "task/task_sys_List";
	}
	
	/**
	 * 工作任务派发获取任务列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getTaskDistribut", method = RequestMethod.POST)
	public void getTaskDistribut(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			String taskName = "";
			if (request.getParameter("TASK_NAME") != null) {
				taskName = java.net.URLDecoder.decode(request
						.getParameter("TASK_NAME"), "UTF-8");
			}
			Map<String, String> map = new HashMap<String, String>(); // 日期
			map.put("TASK_ID", request.getParameter("TASK_ID"));
			map.put("TASK_NAME", taskName);
			map.put("QC_USER_WORKID", request.getParameter("QC_USER_WORKID"));
			map.put("TASK_STATUS", request.getParameter("TASK_STATUS"));
			map.put("CREATE_START_TIME", request
					.getParameter("CREATE_START_TIME"));
			map.put("CREATE_END_TIME", request.getParameter("CREATE_END_TIME"));
		
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			PageHelper.startPage(currentPage, pageSize);
			int counts = taskService.getCountTaskDistribut(map);
			List<Task> TaskList = taskService.getTaskDistribut(map);
			String json = "{\"Total\":" + counts + " , \"Rows\":"
					+ JSONArray.fromObject(TaskList).toString() + "}";
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
		} catch (Exception e) {
			log.error("获取工作任务派发列表异常：" + e.getMessage());
		}

	}

	/**
	 * @author niewq 新增任务信息
	 * **/
	@Log(methodname = "addTask", modulename = "工作任务派发", funcname = "任务新增", description = "新增任务信息,{0}", code = "ZJ")
	@RequestMapping(value = "/addTask", method = RequestMethod.POST)
	public void addTask(HttpServletRequest request, HttpServletResponse response,Task task)
			 {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		JSONObject object=new JSONObject();
		try {
			taskService.addTask();
			object.put("status", "1");
			response.getWriter().write(object.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[] { "操作成功,任务名称:"+task.getTaskName() }));
		} catch (Exception e) {
			log.error("新增工作任务异常：" + e.getMessage());
			object.put("status", "0");
			try {
				response.getWriter().write(object.toString());
			} catch (IOException e1) {
			log.error(e1);
			}
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { e.getMessage() }));
		}
  
	}

	/**
	 * @author niewq 删除任务
	 * **/
	@RequiresPermissions("taskrecord:del")
	//@RequiresRoles("role_4")
	@Log(methodname = "deleteTask", modulename = "工作任务派发", funcname = "任务删除", description = "删除任务信息,{0}", code = "ZJ")
	@RequestMapping(value = "/deleteTask", method = RequestMethod.POST)
	public void deleteTask(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		JSONObject object=new JSONObject();
		try {
			String taskId = request.getParameter("taskId");
			String taskStatus = request.getParameter("taskStatus");
			Map<String, String> map = new HashMap<String, String>();
			map.put("taskId", taskId);
			map.put("taskStatus", taskStatus);
			
			if (taskId != null && !"".equals(taskId.trim())
					&& taskStatus != null && !"".equals(taskStatus.trim())) {
				taskService.deleteTask(map);
			}
			object.put("status", "1");
			response.getWriter().write(object.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "操作成功,任务编号:"+taskId }));
		} catch (Exception e) {
			object.put("status", "0");
			log.error("删除任务失败异常：" + e.getMessage());
			response.getWriter().write(object.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { e.getMessage() }));
		}

	}

	/**
	 * @author niewq 进入修改页面
	 * **/
	@RequiresPermissions("taskrecord:upd")
	@RequestMapping(value = "/updateTaskShow", method = RequestMethod.GET)
	public String updateTaskShow(HttpServletRequest request,
			HttpServletResponse response)  {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		try {
		String type = request.getParameter("type");// 入口判断：判断是修改还是查看
		String taskId = request.getParameter("taskId");
		Task taskInfo = taskService.getOneTaskInfo(Integer.parseInt(taskId));
		List<UserInfo> qcUserList = taskService.getQcUser();// 查询质检员
		String papersvalue = "";
		if ("".equals(taskInfo.getTelUser()) || null == taskInfo.getTelUser()) {

		} else {
			String Userid = taskInfo.getTelUser() + ",";
			String Customer = taskInfo.getTelUserName() + ",";
			String[] checkedUserid = Userid.split(",");
			String[] checkedCustomer = Customer.split(",");
			for (int i = 0; i < checkedUserid.length; i++) {
				papersvalue += checkedUserid[i] + "-" + checkedCustomer[i]
						+ ",";

			}
		}

		taskInfo.setPapers_hid(papersvalue);
		request.setAttribute("taskInfo", taskInfo);
		request.setAttribute("qcUserList", qcUserList);
		request.setAttribute("type", type);
		return "task/taskupdate";
		} catch (Exception e) {
			log.error("查询任务详情异常：" + e.getMessage());
		}
		return "";
	}

	/**
	 * 设置任务开始时间，结束时间
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws NumberFormatException 
	 * @throws Exception
	 */
	@RequestMapping(value = "/timeChaOherCha", method = RequestMethod.POST)
	public void timeChaOherCha(HttpServletRequest request,
			HttpServletResponse response){
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		String ymdhms = (String) request.getParameter("ymdhms");
		String nday = (String) request.getParameter("nday");
		String hmsType = (String) request.getParameter("hmsType");
		String data = "";
		try {
			data = CommUtil.afterNDayNWQ(CommUtil.createDateNWQ(ymdhms), 0, Integer
					.parseInt(nday), hmsType);
		} catch (NumberFormatException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		JSONObject json = new JSONObject();
		json.put("data", data);
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			log.error(e);
		}
	}
    
	
	/**
	 * 新增任务时，判断任务名称是否存在
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkTaskName", method = RequestMethod.POST)
	public void checkTaskName(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		String taskName = (String) request.getParameter("taskName");
		String oldName=request.getParameter("oldName");
		Map<String, String> map=new HashMap<String, String>();
		map.put("taskName", taskName);
		int count=0;
		if(oldName==null||"".equals(oldName)){
		count=taskService.checkTaskName(map);
		}else{
			if(oldName.equals(taskName)){
				count=0;
			}else{
			 count=taskService.checkTaskName(map);
			}
		}
		
       
		JSONObject json = new JSONObject();
		json.put("data", count);
		response.getWriter().print(json);
	}
    
	
	
	
	/**
	 * @author niewq 修改任务信息
	 * **/
	@Log(methodname = "updateTask", modulename = "工作任务派发", funcname = "任务修改", description = "修改任务信息,{0}", code = "ZJ")
	@RequestMapping(value = "/updateTask", method = RequestMethod.POST)
	public void updateTask(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		JSONObject object=new JSONObject();
		try {
			String taskName = request.getParameter("TASK_NAME");
			taskService.updateTask();
			object.put("status", "1");
			response.getWriter().write(object.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "操作成功,任务名称:"+taskName}));
		} catch (Exception e) {
			log.error("工作任务修改异常：" + e.getMessage());
			object.put("status", "0");
			response.getWriter().write(object.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { e.getMessage() }));
		}

	}

	/**
	 * 任务完成情况 质检班长
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/gettaskCompleteInfo", method = RequestMethod.POST)
	public void taskCompleteInfo(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			Map<String, String> map = new HashMap<String, String>(); // 日期
			String page = request.getParameter("page");// 页码
			String pagesize = request.getParameter("pagesize");// 规格
			String taskId = request.getParameter("taskId");
			String taskStatus = request.getParameter("taskStatus");
			if (page == null || page == "") {
				page = "1";
			}
			if (pagesize == null || pagesize == "") {
				pagesize = "10";
			}
			map.put("taskId", taskId);
			map.put("taskStatus", taskStatus);
			int counts = taskService.getCountTaskCompleteInfo(map);
			if (Integer.parseInt(page) == 1) {
				map.put("start", "0");
				map.put("end", String.valueOf(Integer.parseInt(pagesize)));
			} else if (Integer.parseInt(page) > 1) {
				map.put("start", String.valueOf((Integer.parseInt(page) - 1)
						* Integer.parseInt(pagesize)));
				map.put("end", String.valueOf(pagesize));
			}

			List<Task> taskInfoList = taskService.queryTaskCompleteInfo(map);
			for (int i = 0; i < taskInfoList.size(); i++) {
				taskInfoList.get(i).setGetRecordCountItem(
						taskInfoList.get(i).getGetRecordCount().split(","));
				taskInfoList.get(i).setCompleteCountItem(
						taskInfoList.get(i).getCompleteCount().split(","));
			}
			JSONObject object = new JSONObject();
			object.put("Rows", taskInfoList);
			object.put("Total", counts);
			response.getWriter().print(object.toString());
		} catch (Exception e) {
			log.error("查询任务完成情况列表失败：" + e.getMessage());
		}
	}

	/**
	 * 任务完成情况列表 批量释放录音
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Log(methodname = "releaseRecordByUser", modulename = "执行工作任务", funcname = "批量释放录音", description = "批量释放录音,{0}", code = "ZJ")
	@RequestMapping(value = "/releaseRecordByUser", method = RequestMethod.POST)
	public void releaseRecordByUser(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		String qcUserItem = request.getParameter("qcUserItem");
		String taskId = request.getParameter("taskId");
		try {
			Map<String, String> map = new HashMap<String, String>();
			String[] qcUserItems = qcUserItem.split(",");
			String resultString = "";
			for (int i = 0; i < qcUserItems.length; i++) {
				map.clear();
				map.put("qcUser", qcUserItems[i]);
				map.put("taskId", taskId);
				resultString = taskService.releaseRecordByQcUser(map);
			}
			JSONObject json = new JSONObject();
			json.put("result", resultString);
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "操作成功,任务编号:"+taskId}));
		} catch (Exception e) {
			log.error("批量释放录音失败：" + e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { e.getMessage() }));
		} finally {
			try {
				response.getWriter().close();
			} catch (IOException e) {
				log.error(e);
			}
		}

	}

	/**
	 * 质检班长批量释放录音(以天为维度)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@Log(methodname = "releaseRecord", modulename = "执行工作任务", funcname = "以天为维度批量释放录音", description = "以天为维度批量释放录音,{0}", code = "ZJ")
	@RequestMapping(value = "/releaseRecord", method = RequestMethod.POST)
	public void releaseRecord(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		String cDateItem = request.getParameter("cDateItem");
		String taskUserId = request.getParameter("taskUserId");// 质检员任务相关表ID
		String taskId = request.getParameter("taskId");
		String qcUser = request.getParameter("qcUser");
		try {
			Map<String, String> map = new HashMap<String, String>();
			String[] cDateItems = cDateItem.split(",");
			String resultString = "";
			for (int i = 0; i < cDateItems.length; i++) {
				map.clear();
				map.put("cDate", cDateItems[i]);
				map.put("taskId", taskId);
				map.put("qcUser", qcUser);
				map.put("taskUserId", taskUserId);
				resultString = taskService.releaseRecord(map);
			}
			JSONObject json = new JSONObject();
			json.put("result", resultString);
			response.getWriter().print(json);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "操作成功,任务id:"+taskId}));
		} catch (Exception e) {
			log.error("以天为维度批量释放录音失败：" + e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { e.getMessage() }));
		} finally {
			try {
				response.getWriter().close();
			} catch (IOException e) {
				log.error(e);
			}
		}
	}

	/**
	 * 任务完成情况详细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/showTaskCompleteInforqc", method = RequestMethod.POST)
	public void showTaskCompleteInforqc(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			String page = request.getParameter("page");// 页码
			String pagesize = request.getParameter("pagesize");// 规格
			String taskQcUserId = request.getParameter("taskQcUserId");
			String taskStatus = request.getParameter("taskStatus");
			Map<String, String> map = new HashMap<String, String>();
			map.put("taskQcUserId", taskQcUserId);
			map.put("taskStatus", taskStatus);

			if (page == null || page == "") {
				page = "1";
			}
			if (pagesize == null || pagesize == "") {
				pagesize = "10";
			}
			int counts = taskService.getCountTaskCompleteInforc(map);
			if (Integer.parseInt(page) == 1) {
				map.put("start", "0");
				map.put("end", String.valueOf(Integer.parseInt(pagesize)));
			} else if (Integer.parseInt(page) > 1) {
				map.put("start", String.valueOf((Integer.parseInt(page) - 1)
						* Integer.parseInt(pagesize)));
				map.put("end", String.valueOf(pagesize));
			}

			List<Task> taskInfoList = taskService.queryTaskCompleteInforc(map);
			for (int i = 0; i < taskInfoList.size(); i++) {
				taskInfoList.get(i).setGetRecordCountItem(
						taskInfoList.get(i).getGetRecordCount().split(","));
				taskInfoList.get(i).setCompleteCountItem(
						taskInfoList.get(i).getCompleteCount().split(","));
				taskInfoList.get(i).setQcDcountItem(taskInfoList.get(i).getQcDCount().split(","));
			}
			if (taskInfoList != null && taskInfoList.size() != 0) {
				request.setAttribute("qcDcountItem", taskInfoList.get(0)
						.getQcDcountItem());
				//request
			}
			JSONObject object = new JSONObject();
			object.put("Rows", taskInfoList);
			object.put("Total", counts);
			response.getWriter().print(object.toString());
		} catch (Exception e) {
			log.error("查询任务完成情况列表失败：" + e.getMessage());
		}
	}

	@RequiresPermissions("ztaskrecord:query")
	@RequestMapping(value = "/toqueryTaskForQc", method = RequestMethod.GET)
	public String toqueryTaskForQc(HttpServletRequest request,
			HttpServletResponse response) {
		return "task/task_qc_List";
	}
	
	/**
	 *  执行工作任务,任务查询（质检员）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTaskForQc", method = RequestMethod.POST)
	public void queryTaskForQc(HttpServletRequest request,
			HttpServletResponse response)  {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			Map<String, String> map = new HashMap<String, String>();
			String page = request.getParameter("page");// 页码
			String pagesize = request.getParameter("pagesize");// 规格
			String taskName = "";
			if (request.getParameter("taskName") != null) {
				taskName = java.net.URLDecoder.decode(request
						.getParameter("taskName"), "UTF-8");
			}
			String qcUserId=String.valueOf(request.getAttribute(Constans.USER_INFO_USERID)); 
			map.put("taskName", taskName);
			map.put("taskId", request.getParameter("taskId"));
			map.put("taskStartTime", request.getParameter("taskStartTime"));
			map.put("taskSEndTime", request.getParameter("taskSEndTime"));
			map.put("qcUserId", qcUserId);
			map.put("taskStatus", request.getParameter("taskStatus"));

			if (page == null || page == "") {
				page = "1";
			}
			if (pagesize == null || pagesize == "") {
				pagesize = "10";
			}
			int counts = taskService.queryListForQcCount(map);
			if (Integer.parseInt(page) == 1) {
				map.put("start", "0");
				map.put("end", String.valueOf(Integer.parseInt(pagesize)));
			} else if (Integer.parseInt(page) > 1) {
				map.put("start", String.valueOf((Integer.parseInt(page) - 1)
						* Integer.parseInt(pagesize)));
				map.put("end", String.valueOf(pagesize));
			}
			List<Task> taskList = taskService.queryListForQc(map);
			JSONObject object = new JSONObject();
			object.put("Rows", taskList);
			object.put("Total", counts);
			response.getWriter().print(object.toString());
		} catch (Exception e) {
			log.error("查询任务查询（质检员）列表失败：" + e.getMessage());
		}
	}

	/**
	 * 任务领取页面显示
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getRecordForQc", method = RequestMethod.POST)
	public void getRecordForQc(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			String taskId = request.getParameter("taskId");
			String taskName = "";
			if (request.getParameter("taskName") != null) {
				taskName = java.net.URLDecoder.decode(request
						.getParameter("taskName"), "UTF-8");
			}
			String taskUserId = request.getParameter("taskQcUserId");
			String taskTime = request.getParameter("taskTime");
			String recordLength = request.getParameter("recordLength");
			String telUserID = request.getParameter("telUserID");
			if (telUserID == null || telUserID.trim() == "") {
				telUserID = null;
			}
			List<AgentCallLog> callLogList = new ArrayList<AgentCallLog>();
			int counts = 0;
			// 查询出录音供质检员领取
			if (recordLength != null && !recordLength.equals("")) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("taskUserId", taskUserId);
				map.put("taskTime", taskTime);
				map.put("recordLength", recordLength);
				map.put("telUserID", telUserID);
				String reservedthrees = taskService.searchReservedthrees(map);
				if (reservedthrees == null || reservedthrees.trim().equals("")
						|| reservedthrees.equals("' '")) {
					reservedthrees = "''";
				}else{
					// 然后剩下的从结果集中随机抽取 结束
					reservedthrees = reservedthrees.substring(0,
							reservedthrees.length() - 1);
				}
				map.put("reservedthrees", reservedthrees);
				callLogList = taskService.searchRecordToGet(map);
				counts = taskService.searchRecordToGetCount(map);
			}

			JSONObject object = new JSONObject();
			object.put("Rows", callLogList);
			object.put("Total", counts);
			Task task = taskService.getOneTaskInfo(Integer.parseInt(taskId));
			Task taskInfo = new Task();
			taskInfo.setTaskId(Integer.parseInt(taskId));
			taskInfo.setTaskName(taskName);
			taskInfo.setTaskuserId(Integer.parseInt(taskUserId));
			taskInfo.setTaskEndTime(task.getTaskEndTime());
			taskInfo.setQcDCount(task.getQcDCount());
			request.setAttribute("taskInfo", taskInfo);
			response.getWriter().print(object.toString());
		} catch (Exception e) {
			log.error("查询任务领取页面显示失败：" + e.getMessage());
			
		}
		/*
		 * return mapping.findForward("getrecordforqc");
		 */
	}

	/**
	 * 质检员一键领取任务录音
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * @throws UnsupportedEncodingException
	 */
	@Log(methodname = "oneKeyGetRecord", modulename = "质检员一键领取任务录音", funcname = "质检员一键领取任务录音", description = "质检员一键领取任务录音,{0}", code = "ZJ")
	@RequestMapping(value = "/oneKeyGetRecord", method = RequestMethod.POST)
	public void oneKeyGetRecord(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			String taskUserId = request.getParameter("taskQcUserId");
			String taskTime = request.getParameter("taskTime");
			Map<String, String> map = new HashMap<String, String>();
			map.put("taskUserId", taskUserId);
			map.put("taskTime", taskTime);
			log.info("一键领取跟踪：taskQcUserId：" + taskUserId + " taskTime："
					+ taskTime);
			String resultString = taskService.updoneKeyGetRecord(map);
			JSONObject object = new JSONObject();
			object.put("result", resultString);
			response.getWriter().print(object.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "操作成功" }));
		} catch (Exception e) {
			log.error("质检员一键领取任务录音异常：" + e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { e.getMessage() }));
		} finally {
			try {
				response.getWriter().close();
			} catch (IOException e) {
				log.error(e);
			}
		}
	}

	/**
	 * 质检员领取录音
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws UnsupportedEncodingException
	 */
	@Log(methodname = "getRecord", modulename = "质检员领取录音", funcname = "质检员领取录音", description = "质检员领取录音,{0}", code = "ZJ")
	@RequestMapping(value = "/getRecord", method = RequestMethod.POST)
	public void getRecord(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("text/html;charset=UTF-8");
			String taskId = request.getParameter("taskId");
			String taskUserId = request.getParameter("taskQcUserId");
			String taskTime = request.getParameter("taskTime");
			String telUser = request.getParameter("telUser");
			String recordLength = request.getParameter("recordLength");
			String userID = String.valueOf(request.getAttribute(Constans.USER_INFO_USERID));
			String reservedThree = request.getParameter("reservedThree");
			String recordId = request.getParameter("recordId");
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("taskId", taskId);
			map.put("taskUserId", taskUserId);
			map.put("taskTime", taskTime);
			map.put("telUser", telUser);
			map.put("recordId", recordId);
			map.put("recordLength", recordLength);
			map.put("userID", userID);
			map.put("reservedThree", reservedThree);
			String resultString = taskService.updateRecord(map);
			JSONObject object = new JSONObject();
			object.put("result", resultString);
			response.getWriter().print(object.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "操作成功,任务id:"+taskId }));
		} catch (Exception e) {
			JSONObject object = new JSONObject();
			object.put("result", "领取失败");
			response.getWriter().print(object.toString());
			log.error("质检员领取录音失败异常：" + e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { e.getMessage() }));
		} finally {
			try {
				response.getWriter().close();
			} catch (IOException e) {
				log.error(e);
			}
		}
	}

	/**
	 * 查询任务录音
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Log(methodname = "queryTaskRecord", modulename = "查询任务录音", funcname = "查询任务录音", description = "查询任务录音,{0}", code = "ZJ")
	@RequestMapping(value = "/queryTaskRecord", method = RequestMethod.POST)
	public void queryTaskRecord(HttpServletRequest request,
			HttpServletResponse response)  {
		try {
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			String page = request.getParameter("page");// 页码
			String pagesize = request.getParameter("pagesize");// 规格
			Map<String, String> map = new HashMap<String, String>();
			if (page == null || page == "") {
				page = "1";
			}
			if (pagesize == null || pagesize == "") {
				pagesize = "10";
			}
			if (Integer.parseInt(page) == 1) {
				map.put("start", "0");
				map.put("end", String.valueOf(Integer.parseInt(pagesize)));
			} else if (Integer.parseInt(page) > 1) {
				map.put("start", String.valueOf((Integer.parseInt(page) - 1)
						* Integer.parseInt(pagesize)));
				map.put("end", String.valueOf(pagesize));
			}
			String taskId = request.getParameter("taskId");
			String qcUser = request.getParameter("qcUser");
			String taskStatus = request.getParameter("taskStatus");
			map.put("taskId", taskId);
			map.put("qcUser", qcUser);
			map.put("taskStatus", taskStatus);
			List<AgentCallLog> taskList = taskService.queryRecordList(map);
			int counts = taskService.queryRecordListCount(map);
			JSONObject object = new JSONObject();
			object.put("Rows", taskList);
			object.put("Total", counts);
            response.getWriter().print(object.toString());
		} catch (Exception e) {
			log.error("查询任务录音失败：" + e.getMessage());
		}
	}
	
	/**
	 * @author lk 跳转112任务派发界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/totaskOrder112List", method = RequestMethod.GET)
	public String totaskOrder112List(HttpServletRequest request,HttpServletResponse response){
		return "task/112/task_order112_List";
	}
	
	/**
	 * @author lk 跳转112执行任务界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("ztask112ord:query")
	@RequestMapping(value = "/totaskqcOrder112List", method = RequestMethod.GET)
	public String totaskqcOrder112List(HttpServletRequest request,HttpServletResponse response){
		return "task/112/task_qc_order112List";
	}
	@RequestMapping(value="/gettaskOrder112List" ,method=RequestMethod.POST)
	public void gettaskOrder112List(HttpServletRequest request,HttpServletResponse response ,Task task){
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			String taskid=request.getParameter("taskid");
			if(taskid==null||"".equals(taskid)){
				task.setTaskId(0);
			}else{
				task.setTaskId(Integer.parseInt(taskid));
			}
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			PageHelper.startPage(currentPage, pageSize);
			List<Task> list=taskService.gettask112Order(task);
			int total=taskService.gettask112Ordercount(task);
			String json = "{\"Total\":" + total + " , \"Rows\":"
					+ JSONArray.fromObject(list).toString() + "}";	
			response.getWriter().print(json);
		} catch (Exception e) {
			log.error("112工单任务列表查询失败:"+e.getMessage());
		}
	}
	/**
	 * @author lk  2017/5/10
	 * @param request
	 * @param response
	 * @param task
	 * 质检员查询任务执行列表
	 */
	
	@RequestMapping(value="/getimptaskOrder112List" ,method=RequestMethod.POST)
	public void getimptaskOrder112List(HttpServletRequest request,HttpServletResponse response ,Task task){
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			String taskid=request.getParameter("taskid");
			if(taskid==null||"".equals(taskid)){
				task.setTaskId(0);
			}else{
				task.setTaskId(Integer.parseInt(taskid));
			}
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			PageHelper.startPage(currentPage, pageSize);
			
			List<Task> list=taskService.getimptask112Order(task);
			int total=taskService.getimptask112Ordercount(task);
			String json = "{\"Total\":" + total + " , \"Rows\":"
					+ JSONArray.fromObject(list).toString() + "}";	
			response.getWriter().print(json);
		} catch (Exception e) {
			log.error("112工单任务列表查询失败:"+e.getMessage());
		}
	}
	
	
	@RequiresPermissions("task112ord:add")
	@Log(methodname = "to112OrderTaskInfo", modulename = "任务派发", funcname = "查询质检员", description = "查询质检员,{0}", code = "ZJ")
	@RequestMapping(value = "/to112OrderTaskInfo", method = RequestMethod.GET)
	public String to112OrderTaskInfo(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try{
		List<UserInfo> qcUserList = taskService.getQcUser();// 查询质检员
		request.setAttribute("qcUserList", qcUserList);
		LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功"}));
		return "task/112/edit_order112taskInfo";
		}catch(Exception e){
			log.error("查询质检员异常："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
		return "";
	}
	/**
	 * @author lk
	 * @param request
	 * @param response
	 * @param task
	 * 112任务派发新增
	 */
	
	@Log(methodname = "add112orderTask", modulename = "任务派发", funcname = "112工单任务派发", description = "新增112工单任务,{0}", code = "ZJ")
	@RequestMapping(value = "/add112orderTask", method = RequestMethod.POST)
	public void add112orderTask(HttpServletRequest request,
			HttpServletResponse response,Task task){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject json=new JSONObject();
		try{
			task.setCreateUserIdTasqcu(request.getAttribute(Constans.USER_INFO_USERID).toString());
			task.setTaskStatus(task.getIsPublish());
			taskService.add112orderTask(task);
			json.put("result", "1");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"任务名称:【"+task.getTaskName()+"】操作成功"}));
			response.getWriter().print(json.toString());
			}catch(Exception e){		
				try {
					json.put("result", "0");
					response.getWriter().print(json.toString());
				} catch (IOException e1) {
					log.error(e1);
				}
				log.error("112工单任务派发："+e.getMessage());
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
			}
	}
	@RequiresPermissions("task112ord:del")
	@Log(methodname = "del112orderTask", modulename = "任务派发", funcname = "112工单任务派发", description = "删除112工单任务,{0}", code = "ZJ")
	@RequestMapping(value="/del112orderTask",  method=RequestMethod.POST)
	public void del112orderTask(HttpServletRequest request,
			HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject json=null;
		String taskId=null;
		try {
			taskId=request.getParameter("taskId");
			if(taskId==null||"".equals(taskId)){
				taskId="0";
			}
			String status=request.getParameter("status");
			Map<String,String> map=new HashMap<>();
			map.put("taskStatus", status);
			map.put("taskId", taskId);
			taskService.del112orderTask(map);
			json=new JSONObject();
			json.put("result", "1");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"任务名称:【"+taskId+"】操作成功"}));
			response.getWriter().print(json);
		} catch (Exception e) {
			log.error("删除112工单任务："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"任务名称:【"+taskId+"】"+e.getMessage()}));
		}
	} 
	@RequiresPermissions(value = {"task112ord:upd", " task112ord:info"}, logical = Logical.OR)
	@RequestMapping(value="/update112TaskShow",  method=RequestMethod.GET)
	public String update112TaskShow(HttpServletRequest request,
			HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject json=null;
		String taskId=null;
		try {
			taskId=request.getParameter("taskId");
			String type=request.getParameter("type");
			if(taskId==null||"".equals(taskId)){
				taskId="0";
			}
			 Task task=taskService.get112orderTaskbyid(Integer.parseInt(taskId));
			 String papersvalue = "";
	            if ("".equals(task.getTelUser())
	                    || null == task.getTelUser()) {

	            } else {
	                String Userid = task.getTelUser() + ",";
	                String Customer = task.getTelUserName() + ",";
	                String[] checkedUserid = Userid.split(",");
	                String[] checkedCustomer = Customer.split(",");
	                for (int i = 0; i < checkedUserid.length; i++) {
	                    papersvalue += checkedUserid[i] + "-" + checkedCustomer[i]
	                            + ",";

	                }
	            }
	            task.setPapers_hid(papersvalue);
			 List<UserInfo> qcUserList = taskService.getQcUser();// 查询质检员
			request.setAttribute("qcUserList", qcUserList);
			request.setAttribute("type", type);
			request.setAttribute("task", task);	
			response.getWriter().print(json);
			return "task/112/edit_order112taskInfo";
		} catch (Exception e) {
			log.error("查看112工单任务："+e.getMessage());
		}
		return "";
	}
	
	
	/**
	 * 新增任务时，判断任务名称是否存在
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/check112TaskName", method = RequestMethod.POST)
	public void check112TaskName(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		String taskName =request.getParameter("taskName");
		String oldName=request.getParameter("oldName");
		int count=0;
		if(oldName==null||"".equals(oldName)){
		count=taskService.check112Ordername(taskName);
		}else{
			if(oldName.equals(taskName)){
				count=0;
			}else{
				count=taskService.check112Ordername(taskName);
			}
		}
		JSONObject json = new JSONObject();
		json.put("data", count);
		response.getWriter().print(json);	
	}
	
	
	@Log(methodname = "upd112orderTask", modulename = "任务派发", funcname = "112工单任务派发", description = "修改112工单任务,{0}", code = "ZJ")
	@RequestMapping(value="/upd112orderTask",  method=RequestMethod.POST)
	public void upd112orderTask(HttpServletRequest request,
			HttpServletResponse response,Task task){
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		JSONObject json=new JSONObject();
		try{
			task.setUpdateUserIdTasqcu(request.getAttribute(Constans.USER_INFO_USERID).toString());
			task.setTaskStatus(task.getIsPublish());
			taskService.upd112OrderTask(task);
			json.put("result", "1");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"任务名称:【"+task.getTaskName()+"】操作成功"}));
			response.getWriter().print(json.toString());
			}catch(Exception e){		
				try {
					json.put("result", "0");
					response.getWriter().print(json.toString());
				} catch (IOException e1) {			
					log.error(e1);
				}
				log.error("112工单任务派发："+e.getMessage());
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
			}
	}
	/**
	 * @author lk 2017/5/17
	 * @param request
	 * @param response
	 * 质检员查看自己完成情况
	 */
	@RequestMapping(value="/getwcqkitemsbyworkid",  method=RequestMethod.POST)
	public void getwcqkitemsbyworkid(HttpServletRequest request,
			HttpServletResponse response){
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		try{
		PageHelper.startPage(currentPage, pageSize);
		String taskId=request.getParameter("taskId");
		String userid=request.getAttribute(Constans.USER_INFO_USERID).toString();
		String eachDateStatus=request.getParameter("eachDateStatus");
		String type=request.getParameter("type");
		Map<String,String> map=new HashMap<String, String>();
		map.put("taskId", taskId);
		map.put("qcUser", userid);
		map.put("eachDateStatus", eachDateStatus);
		List<Task> list=null;
		int total=0;
		if("1".equals(type)){
			list=taskService.get112ordertaskwcqkList(map);
			total=taskService.get112ordertaskwcqkcount(map);
		}else{
		list=taskService.getcwordertaskwcqkList(map);
		total=taskService.getcwordertaskwcqkcount(map);
		}
		System.out.println(list.get(0).getCsrTopDCount());
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(list).toString() + "}";
		response.getWriter().print(json.toString());
		}catch (Exception e) {
			log.error("质检员查看自己完成情况异常："+e.getMessage());
		}
	}
	
	
	/**
	 * @author lk 2017/5/18
	 * @param request
	 * @param response
	 * 督导查看所有人完成情况
	 */
	@RequiresPermissions(value = {"task112ord:wcinfo", " task112ord:info"}, logical = Logical.OR)
	@RequestMapping(value="/getwcqkitemsall",  method=RequestMethod.POST)
	public void getwcqkitemsall(HttpServletRequest request,
			HttpServletResponse response){
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		try{
		PageHelper.startPage(currentPage, pageSize);
		String taskId=request.getParameter("taskId");
		String eachQcuserStatus=request.getParameter("eachQcuserStatus");
		String tasktype=request.getParameter("tasktype");
		Map<String,String> map=new HashMap<String, String>();
		map.put("taskId", taskId);
		map.put("eachQcuserStatus", eachQcuserStatus);
		map.put("tasktype", tasktype);
		List<Task> list=taskService.get112ordertaskwcqkallList(map);
		int total=taskService.get112ordertaskwcqkallcount(map);
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(list).toString() + "}";
		response.getWriter().print(json.toString());
		}catch (Exception e) {
			log.error("质检员查看自己完成情况异常："+e.getMessage());
		}
	}
	
	/**
	 * @author lk 2017/5/19
	 * @param request
	 * @param response
	 * 督导查看个人完成情况
	 */
	@RequestMapping(value="/getwcqkitemsbytaskuserid",  method=RequestMethod.POST)
	public void getwcqkitemsbytaskuserid(HttpServletRequest request,
			HttpServletResponse response){
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		try{
		PageHelper.startPage(currentPage, pageSize);
		String taskId=request.getParameter("taskId");
		String taskuserId=request.getParameter("taskuserId");
		String eachDateStatus=request.getParameter("eachDateStatus");
		String type=request.getParameter("type");
		
		Map<String,String> map=new HashMap<String, String>();
		map.put("taskId", taskId);
		map.put("eachDateStatus", eachDateStatus);
		map.put("taskuserId", taskuserId);
		List<Task> list=new ArrayList<Task>();
		int total=0;
		if("1".equals(type)){
			list=taskService.get112ordertaskwcqkqcList(map);
			total=taskService.get112ordertaskwcqkqccount(map);
		}else{
			list=taskService.getcwordertaskwcqkqcList(map);
			total=taskService.getcwordertaskwcqkqccount(map);
		}
		String json = "{\"Total\":" + total + " , \"Rows\":"
				+ JSONArray.fromObject(list).toString() + "}";
		response.getWriter().print(json.toString());
		}catch (Exception e) {
			log.error("质检员查看自己完成情况异常："+e.getMessage());
		}
	}
	
	/**
	 * @author lk 2017/5/22
	 * 一键释放质检员领取工单
	 */
	@RequiresPermissions("task112ord:clean")
	@Log(methodname = "releasecwOrderByUser", modulename = "任务派发", funcname = "112工单任务派发", description = "一键释放质检员领取工单,{0}", code = "ZJ")
	@RequestMapping(value="/release112OrderByUser",  method=RequestMethod.POST)
	public void release112OrderByUser(HttpServletRequest request,
			HttpServletResponse response){
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		String taskId=null;
		try {
			String qcUserItem=request.getParameter("qcUserItem"); //质检员userid
			taskId=request.getParameter("taskId");//任务id
			Map<String,String> map=new HashMap<String, String>();
			map.put("qcUserItem", qcUserItem);
			map.put("taskId", taskId);
		 String orderIdList	=taskService.updrelease112OrderByUser(map);
			JSONObject object=new JSONObject();
			object.put("result", "成功");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"112工单id:【"+orderIdList+"】操作成功"}));
			response.getWriter().print(object);
		} catch (Exception e) {
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"释放失败，任务编号:【"+taskId+"】"+e}));
			log.error(e.getMessage());
		}
	}
	
	/**
	 * @author lk 2017/5/22
	 * @param request
	 * @param response
	 * 一键释放112工单 以天为维度
	 */	
	@Log(methodname="get112orderlinqu",modulename="任务派发",funcname="112工单任务派发",description="一键释放112任务工单,{0}", code = "ZJ")
	@RequestMapping(value="/release112Order",  method=RequestMethod.POST)
	public void release112Order(HttpServletRequest request,
			HttpServletResponse response){	
		response.setContentType("text/html;charset=UTF-8");
		String taskId=null;
		try {
			String cDateItem=request.getParameter("cDateItem"); //质检员userid 
			taskId=request.getParameter("taskId");//任务id
			String taskUserId=request.getParameter("taskUserId");//任务员工中间表id
			Map<String,String> map=new HashMap<String, String>();
			map.put("cDateItem", cDateItem);
			map.put("taskId", taskId);
			map.put("taskUserId", taskUserId);
			String orderIdList=taskService.updrelease112OrderBycDate(map);
			JSONObject object=new JSONObject();
			object.put("result", "成功");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"112工单id:【"+orderIdList+"】操作成功"}));
			response.getWriter().print(object);
		} catch (Exception e) {
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"释放失败，任务编号:【"+taskId+"】"+e}));
			log.error(e.getMessage());
		}
		
		
	}
	
	
	/**
	 * @author lk 跳转1c网执行任务界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("taskcword:query")
	@RequestMapping(value="/togetOrdercwList",method=RequestMethod.GET)
	public String togetOrdercwList(){		
		return "task/cw/task_ordercw_List";
	}
	
	@RequestMapping(value="/gettaskOrdercwList" ,method=RequestMethod.POST)
	public void gettaskOrdercwList(HttpServletRequest request,HttpServletResponse response ,Task task){
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			String taskid=request.getParameter("taskid");
			if(taskid==null||"".equals(taskid)){
				task.setTaskId(0);
			}else{
				task.setTaskId(Integer.parseInt(taskid));
			}
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			PageHelper.startPage(currentPage, pageSize);
			List<Task> list=taskService.gettaskcwOrder(task);
			int total=taskService.gettaskcwOrdercount(task);
			String json = "{\"Total\":" + total + " , \"Rows\":"
					+ JSONArray.fromObject(list).toString() + "}";	
			response.getWriter().print(json);
		} catch (Exception e) {
			log.error("c网工单任务列表查询失败:"+e.getMessage());
		}
	}
	
	@RequiresPermissions("taskcword:add")
	@RequestMapping(value = "/tocwOrderTaskInfo", method = RequestMethod.GET)
	public String tocwOrderTaskInfo(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try{
		List<UserInfo> qcUserList = taskService.getQcUser();// 查询质检员
		request.setAttribute("qcUserList", qcUserList);
		return "task/cw/edit_ordercwtaskInfo";
		}catch(Exception e){
			log.error("查询质检员异常："+e.getMessage());
		}
		return "";
	}
	
	
	/**
	 * @author lk
	 * @param request
	 * @param response
	 * @param task
	 * c网任务派发新增
	 */
	
	@Log(methodname = "addcworderTask", modulename = "任务派发", funcname = "c网工单任务派发", description = "新增C网工单任务,{0}", code = "ZJ")
	@RequestMapping(value = "/addcworderTask", method = RequestMethod.POST)
	public void addcworderTask(HttpServletRequest request,
			HttpServletResponse response,Task task){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject json=new JSONObject();
		try{
			task.setCreateUserIdTasqcu(request.getAttribute(Constans.USER_INFO_USERID).toString());
			task.setTaskStatus(task.getIsPublish());
			taskService.addcworderTask(task);
			json.put("result", "1");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"任务名称:【"+task.getTaskName()+"】操作成功"}));
			response.getWriter().print(json.toString());
			}catch(Exception e){		
				try {
					json.put("result", "0");
					response.getWriter().print(json.toString());
				} catch (IOException e1) {
					log.error(e1);
				}
				log.error("112工单任务派发："+e.getMessage());
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
			}
	}
	
	/**
	 * @author lk 2017/5/24
	 * @param request
	 * @param response
	 * @param task
	 * 修改c网工单任务派发
	 */
	@Log(methodname = "updcworderTask", modulename = "任务派发", funcname = "c网工单任务派发", description = "修改c网工单任务,{0}", code = "ZJ")
	@RequestMapping(value="/updcworderTask",  method=RequestMethod.POST)
	public void updcworderTask(HttpServletRequest request,
			HttpServletResponse response,Task task){
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		JSONObject json=new JSONObject();
		try{
			task.setUpdateUserIdTasqcu(request.getAttribute(Constans.USER_INFO_USERID).toString());
			task.setTaskStatus(task.getIsPublish());
			taskService.updcwOrderTask(task);	
			json.put("result", "1");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"任务名称:【"+task.getTaskName()+"】操作成功"}));
			response.getWriter().print(json.toString());
			}catch(Exception e){		
				try {
					json.put("result", "0");
					response.getWriter().print(json.toString());
				} catch (IOException e1) {			
					log.error(e1);
				}
				log.error("112工单任务派发："+e.getMessage());
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
			}
	}
	@RequiresPermissions("taskcword:upd")
	@RequestMapping(value="/updatecwTaskShow",  method=RequestMethod.GET)
	public String updatecwTaskShow(HttpServletRequest request,
			HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject json=null;
		String taskId=null;
		try {
			taskId=request.getParameter("taskId");
			String type=request.getParameter("type");
			if(taskId==null||"".equals(taskId)){
				taskId="0";
			}
			 Task task=taskService.getcworderTaskbyid(Integer.parseInt(taskId));
			 String papersvalue = "";
	            if ("".equals(task.getTelUser())
	                    || null == task.getTelUser()) {

	            } else {
	                String Userid = task.getTelUser() + ",";
	                String Customer = task.getTelUserName() + ",";
	                String[] checkedUserid = Userid.split(",");
	                String[] checkedCustomer = Customer.split(",");
	                for (int i = 0; i < checkedUserid.length; i++) {
	                    papersvalue += checkedUserid[i] + "-" + checkedCustomer[i]
	                            + ",";

	                }
	            }
	            task.setPapers_hid(papersvalue);	 
			 List<UserInfo> qcUserList = taskService.getQcUser();// 查询质检员
			request.setAttribute("qcUserList", qcUserList);
			request.setAttribute("type", type);
			request.setAttribute("task", task);	
			response.getWriter().print(json);
			return "task/cw/edit_ordercwtaskInfo";
		} catch (Exception e) {
			log.error("查看112工单任务："+e.getMessage());
		}
		return "";
	}
	
	
	/**
	 * 新增任务时，判断任务名称是否存在
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkcwTaskName", method = RequestMethod.POST)
	public void checkcwTaskName(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		String taskName =request.getParameter("taskName");
		String oldName=request.getParameter("oldName");
		int count=0;
		if(oldName==null||"".equals(oldName)){
		count=taskService.checkcwOrdername(taskName);
		}else{
			if(oldName.equals(taskName)){
				count=0;
			}else{
				count=taskService.checkcwOrdername(taskName);
			}
		}
		JSONObject json = new JSONObject();
		json.put("data", count);
		response.getWriter().print(json);	
	}
	@RequiresPermissions("taskcword:del")
	@Log(methodname = "delcworderTask", modulename = "任务派发", funcname = "c网工单任务派发", description = "删除c网工单任务,{0}", code = "ZJ")
	@RequestMapping(value="/delcworderTask",  method=RequestMethod.POST)
	public void delcworderTask(HttpServletRequest request,
			HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject json=null;
		String taskId=null;
		try {
			taskId=request.getParameter("taskId");
			if(taskId==null||"".equals(taskId)){
				taskId="0";
			}
			String status=request.getParameter("status");
			Map<String,String> map=new HashMap<>();
			map.put("taskStatus", status);
			map.put("taskId", taskId);
			taskService.delcworderTask(map);
			json=new JSONObject();
			json.put("result", "1");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"任务名称:【"+taskId+"】操作成功"}));
			response.getWriter().print(json);
		} catch (Exception e) {
			log.error("删除c网工单任务："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"任务名称:【"+taskId+"】"+e.getMessage()}));
		}
	} 

	
	/**
	 * @author lk 跳转c网执行任务界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("ztaskcword:query")
	@RequestMapping(value = "/totaskqcOrdercwList", method = RequestMethod.GET)
	public String totaskqcOrdercwList(HttpServletRequest request,HttpServletResponse response){
		return "task/cw/task_qc_ordercwList";
	}
	
	/**
	 * @author lk  2017/5/26
	 * @param request
	 * @param response
	 * @param task
	 * 质检员查询任务执行列表
	 */
	
	@RequestMapping(value="/getimptaskOrdercwList" ,method=RequestMethod.POST)
	public void getimptaskOrdercwList(HttpServletRequest request,HttpServletResponse response ,Task task){
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			String taskid=request.getParameter("taskid");
			if(taskid==null||"".equals(taskid)){
				task.setTaskId(0);
			}else{
				task.setTaskId(Integer.parseInt(taskid));
			}
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			PageHelper.startPage(currentPage, pageSize);
			
			List<Task> list=taskService.getimptaskcwOrder(task);
			int total=taskService.getimptaskcwOrdercount(task);
			String json = "{\"Total\":" + total + " , \"Rows\":"
					+ JSONArray.fromObject(list).toString() + "}";	
			response.getWriter().print(json);
		} catch (Exception e) {
			log.error("112工单任务列表查询失败:"+e.getMessage());
		}
	}
	
	/**
	 * @author lk 2017/6/1
	 * 一键释放质检员领取工单(c网)
	 */
	@Log(methodname = "releasecwOrderByUser", modulename = "任务派发", funcname = "c网工单任务派发", description = "一键释放质检员领取工单,{0}", code = "ZJ")
	@RequestMapping(value="/releasecwOrderByUser",  method=RequestMethod.POST)
	public void releasecwOrderByUser(HttpServletRequest request,
			HttpServletResponse response){
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=UTF-8");
		String taskId=null;
		try {
			String qcUserItem=request.getParameter("qcUserItem"); //质检员userid
			taskId=request.getParameter("taskId");//任务id
			Map<String,String> map=new HashMap<String, String>();
			map.put("qcUserItem", qcUserItem);
			map.put("taskId", taskId);
			String orderIdList=	taskService.updreleasecwOrderByUser(map);		
			JSONObject object=new JSONObject();
			object.put("result", "成功");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"c网工单id:【"+orderIdList+"】操作成功"}));
			response.getWriter().print(object);
		} catch (Exception e) {
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"释放失败，任务编号:【"+taskId+"】"+e.getMessage()}));
			log.error(e);
		}
	}
	
	/**
	 * @author lk 2017/6/1
	 * @param request
	 * @param response
	 * 一键释放c网工单 以天为维度
	 */
	@Log(methodname = "releasecwOrderByUser", modulename = "任务派发", funcname = "c网工单任务派发", description = "一键释放质检员领取工单,{0}", code = "ZJ")
	@RequestMapping(value="/releasecwOrder",  method=RequestMethod.POST)
	public void releasecwOrder(HttpServletRequest request,
			HttpServletResponse response){	
		response.setContentType("text/html;charset=UTF-8");
		String taskId=null;
		try {
			String cDateItem=request.getParameter("cDateItem"); //质检员userid 
			taskId=request.getParameter("taskId");//任务id
			String taskUserId=request.getParameter("taskUserId");//任务员工中间表id
			Map<String,String> map=new HashMap<String, String>();
			map.put("cDateItem", cDateItem);
			map.put("taskId", taskId);
			map.put("taskUserId", taskUserId);
			String maporder=taskService.updreleasecwOrderBycDate(map);
			JSONObject object=new JSONObject();
			object.put("result", "成功");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"c网工单id:【"+maporder+"】操作成功"}));
			response.getWriter().print(object);
		} catch (Exception e) {
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"释放失败，任务编号:【"+taskId+"】"+e}));
			log.error(e);			
		}
		
		
	}
	
}
