package cn.sh.ideal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageHelper;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.ExamJoinVO;
import cn.sh.ideal.model.OperateLog;
import cn.sh.ideal.model.tExam;
import cn.sh.ideal.model.tExamExampaperExamine;
import cn.sh.ideal.model.tExamResults;
import cn.sh.ideal.model.tExampaper;
import cn.sh.ideal.model.tExampaperRandom;
import cn.sh.ideal.model.tQuestions;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.IExamJoinService;
import cn.sh.ideal.service.IExampaperService;
import cn.sh.ideal.util.ConfigProperties;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.LogUitls;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/***
 * 进行考试操作
 * 
 * @author niewenqiang
 * @date 2017-4-26
 * ***/
@Controller
@RequestMapping(value = "/controller/examJoin")
public class ExamJoinController {

	private static Logger log = Logger.getLogger(ExamJoinController.class);
	@Resource
	private IExamJoinService examJoinService;
	@Resource
	private IExampaperService exampaperService;

	/******
	 * 进入待考考试页面
	 * 
	 * @author niewenqiang
	 * @date 2017-4-26
	 * ******/
	@RequestMapping(value = "/toExamJoinPage", method = RequestMethod.GET)
	public String toExamJoinPage(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			// 获取地址栏访问的地址IP
			String ip = request.getServerName();
			// 判断当前访问的地址是ENI地址还是内网地址 eni10开头 内网172开头
			String url = "";
			if (ip.contains("10.4.15")) { // ENI地址
				url = ConfigProperties.getProperty("eniurl"); // 加载心跳程序访问地址
			} else { // 内网地址
				url = ConfigProperties.getProperty("url"); // 加载心跳程序访问地址
			}
			request.setAttribute("heartbeatIpAdd", url);
			vSysuser sysuser = (vSysuser) request.getAttribute("sysuser");
			request.setAttribute("userId", sysuser.getUserId());
		} catch (Exception e) {
			log.error("开始考试时加载心跳程序访问地址失败!" + e.getMessage());
		}
		return "examjoin/exam_ing";
	}

	/******
	 * 查询待考考试列表
	 * 
	 * @author niewenqiang
	 * @date 2017-4-26
	 * ******/
	@RequestMapping(value = "/toExamJoinList", method = RequestMethod.POST)
	public void toExamJoinList(HttpServletRequest request,
			HttpServletResponse response, OperateLog operatelog) {
		response.setContentType(Constans.CONTENTCHA);
		response.setCharacterEncoding(Constans.ENCODING);
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		// 获取当前用户ID
		vSysuser sysuser = (vSysuser) request.getAttribute("sysuser");
		int userId = sysuser.getUserId();
		int zuhuId = 1;
		PageHelper.startPage(currentPage, pageSize);
		try {
			List<tExam> examList = examJoinService.queryToExamList(userId,
					zuhuId); // 获取待考试列表
			int count = examJoinService.queryToExamListCount(userId, zuhuId);
			String json = "{\"Total\":" + count + " , \"Rows\":"
					+ JSONArray.fromObject(examList).toString() + "}";
			response.getWriter().print(json);
		} catch (Exception e) {
			log.error("查询待考考试列表异常：" + e);
		}
	}

	/****
	 * 查询考试试卷对应的题目
	 * 
	 * @author niewenqiang
	 * @date 2017-4-26
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Log(methodname = "queryQuesByExampaperId", modulename = "进行考试", funcname = "开始考试", description = "工号{0}开始考试了，考试ID为{1},进入考试页面{2}")
	@RequestMapping(value = "/queryQuesByExampaperId", method = RequestMethod.GET)
	public String queryQuesByExampaperId(tExam exam,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("mySelExamId", exam.getPkAutoId()); // 获取考试ID
		vSysuser sysuser = (vSysuser) request.getAttribute("sysuser");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info("考试查询开始时间:"+sdf.format(new Date()));
		int userId = sysuser.getUserId();
		exam.setUserId(userId);
		// 根据ID查询该考试这场考试的状态
		tExamExampaperExamine examine = examJoinService
				.queryExamExampaperExamineById(Integer.parseInt(exam
						.getExamExampaperExamineId()));

		if (!"0".equals(examine.getExamineeStatus())) {// 避免
														// 用户copy考试链接打开新的浏览器重新考试
			String showMsg = "";
			if ("1".equals(examine.getExamineeStatus()))
				showMsg = "您已经在其他地址登陆考试！";
			else if ("2".equals(examine.getExamineeStatus()))
				showMsg = "考试已结束！";
			else if ("3".equals(examine.getExamineeStatus()))
				showMsg = "无考试资格！";
			else if ("4".equals(examine.getExamineeStatus()))
				showMsg = "考试已结束！";
			else
				showMsg = "考试已结束！";
			response.setContentType("text/html;charset=UTF-8");
			try {
				String style = "background: -ms-linear-gradient(top, #01aacf,  #0165a2) !important;color: #FFFFFF !important;"
						+ "border: 1px solid #0297bf !important;padding: 0 15px;border-radius:3px ;width: initial !important;height: 25px !important;cursor:pointer;";
				PrintWriter wirter = response.getWriter();
				wirter.println("<br><br><div style='color: red;font-size:25;'><center><b>"
						+ showMsg
						+ "</b></center><div style='padding-top:10px;'><center><input type='button' style='"
						+ style
						+ "' value='关闭' onclick='frameElement.dialog.close();' ></center></div></div>");
				wirter.close();
				wirter = null;
			} catch (Exception e) {
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
						.setObjects(
								new Object[] { sysuser.getWorkId(),
										exam.getPkAutoId(),
										"失败" + e.getMessage() }));
			}
			return null;
		}
		try {
			// 根据试卷ID查找试卷
			tExampaper exampaper = exampaperService.quertExamPaperById(Integer
					.parseInt(exam.getExampaperId()));
			// 判断试卷是否乱序生成考题
			String isIndex = exampaper.getIsindex();

			// 根据试卷ID查找随机模板，判断该套试卷是否为模板试卷
			List<tExampaperRandom> exampaperRandom = examJoinService
					.getExampaperRandomByExampaperID(Integer.parseInt(exam
							.getExampaperId()));

			List list = new ArrayList();

			Map examResultMap = new HashMap();

			if (exampaperRandom.size() > 0) {// 表示根据随机模板试卷选题
				list = examJoinService.addTestByExamRandom(exampaperRandom,
						exam); // 获取模板试卷选择的考题
			} else {// 正常选题
				list = examJoinService.addTestByExam(exam, isIndex);// isIndex为“1”,表示要乱序排列
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						ExamJoinVO examJoinVO = (ExamJoinVO) list.get(i);
						examResultMap.put(examJoinVO.getPkAutoId(), examJoinVO);
					}
				}
			}

			if (list != null && list.size() > 0) {
				if (examine != null && "1".equals(examine.getIsrevert())) { // 判断是否为重考
					exam.setExamTimeLength(examine.getSurplustime());
					List examResultList = examJoinService
							.queryResultMsgByeePexamineId(examine.getPkAutoId());
					if (examResultList != null) {
						int examRltSize = examResultList.size();
						for (int i = 0; i < examRltSize; i++) {
							tExamResults trlt = (tExamResults) examResultList
									.get(i);
							Object examJoinVoObj = examResultMap.get(String
									.valueOf(trlt.getFkQuestionsId()));
							if (examJoinVoObj != null) {
								ExamJoinVO examJoinVO = (ExamJoinVO) examResultMap
										.get(String.valueOf(trlt
												.getFkQuestionsId()));
								examJoinVO
										.setRltAnswer(trlt.getResultsAnswer());
								examJoinVO.setRltCorrectAnswer(trlt
										.getResultsCorrectAnswer());
							}
						}
					}
				} else {
					exam.setExamTimeLength(Integer.parseInt(((ExamJoinVO) list
							.get(0)).getExamTimeLen()));
				}

			} else {// 如果试卷没有考试，则页面提示
				String style = "background: -ms-linear-gradient(top, #01aacf,  #0165a2) !important;color: #FFFFFF !important;"
						+ "border: 1px solid #0297bf !important;padding: 0 15px;border-radius:3px ;width: initial !important;height: 25px !important;cursor:pointer;";
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter wirter = response.getWriter();
				wirter.println("<br><br><div style='color: red;font-size:25;'><center><b>该试卷没有生成考题！</b></center><div style='padding-top:10px;'><center><input type='button' style='"
						+ style
						+ "' value='关闭' onclick='frameElement.dialog.close();' ></center></div></div>");
				wirter.close();
				wirter = null;
				return null;
			}
			exam.setExamPaperName(((ExamJoinVO) list.get(0)).getExampaperName());
			String[][] array = new String[list.size()][4];
			for (int i = 0; i < list.size(); i++) {
				ExamJoinVO examJoinVO = (ExamJoinVO) list.get(i);
				array[i][1] = examJoinVO.getQuesContent().replace("\n", "<br>");
				String[] aa = array[i][1].split("</DIV>");
				StringBuffer buffer = new StringBuffer();
				for (int j = 0; j < aa.length; j++) {
					if (!"".equals(aa[j])) {
						buffer.append(aa[j] + "</DIV>");
					}
				}
				array[i][1] = buffer.toString().replace("<DIV", "<SPAN")
						.replace("</DIV>", "</SPAN>");
				examJoinVO.setQuesContent(array[i][1].trim());
			}

			/**
			 * --------------------------更新考试考试考试状态(考试中)------------------------
			 * -------------
			 **/
			tExamExampaperExamine examExampaperExamine = new tExamExampaperExamine();
			examExampaperExamine.setPkAutoId(Integer.parseInt(exam
					.getExamExampaperExamineId()));
			examExampaperExamine.setExamineeStatus("1");
			examExampaperExamine.setBeginTime(new Date());
			examJoinService.updateExamStatus(examExampaperExamine);
			/**
			 * --------------------------更新考试考试考试状态(考试中)------------------------
			 * -------------
			 **/
			request.setAttribute("examTimeLen", exam.getExamTimeLength());
			request.setAttribute("examExampaperExamineId",
					exam.getExamExampaperExamineId());
			request.setAttribute("exam", exam);
			request.setAttribute("list", list);
			log.info("examExampaperExamineId为："
					+ exam.getExamExampaperExamineId() + "开始考试");
		} catch (Exception e) {
			log.info(
					"examExampaperExamineId为："
							+ exam.getExamExampaperExamineId() + "开始考试失败，原因为"
							+ e.getMessage(), e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { sysuser.getWorkId(), exam.getPkAutoId(),
							"失败" + e.getMessage() }));
		}
		// 获取配置文件中心跳程序地址
		try {
			// 获取地址栏访问的地址IP
			String ip = request.getServerName();
			// 判断当前访问的地址是ENI地址还是内网地址 eni10开头 内网172开头
			String url = "";
			if (ip.contains("10.4.15")) { // ENI地址
				url = ConfigProperties.getProperty("eniurl"); // 加载心跳程序访问地址
			} else { // 内网地址
				url = ConfigProperties.getProperty("url"); // 加载心跳程序访问地址
			}
			request.setAttribute("heartbeatIpAdd", url);
			request.setAttribute("userId", userId);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { sysuser.getWorkId(), exam.getPkAutoId(),
							"成功" }));
		} catch (Exception e) {
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { sysuser.getWorkId(), exam.getPkAutoId(),
							"失败" + e.getMessage() }));
			log.error("开始考试时加载心跳程序访问地址失败!" + e.getMessage());
		}
		log.info("考试查询结束时间:"+sdf.format(new Date()));
		return "examjoin/exampaperJoin";
	}

	/*********
	 * 考试提交后，使用AJAX提交考生的当前答案并且备份考生答案，记录未答题信息及可能出现的服务器错误 交卷
	 * 
	 * @author niewenqiang
	 * @throws IOException
	 * @date 2017-4-28
	 * *************/
	@RequestMapping(value = "/saveUserExamResultByAjax", method = RequestMethod.POST)
	public void saveUserExamResultByAjax(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String examExampaperExamineId = request
				.getParameter("examExampaperExamineId");
		String quesAnswer = request.getParameter("quesAnswer");
		String quesAnswerTwo = request.getParameter("quesAnswerTwo");
		String quesAnswerFourAndFive = request
				.getParameter("quesAnswerFourAndFive");
		String pathName = request.getSession().getServletContext()
				.getRealPath("/")
				+ "ExamResultDir";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = response.getWriter();
		String str = examJoinService.saveExamRstToFileAndData(
				examExampaperExamineId, quesAnswer, quesAnswerTwo,
				quesAnswerFourAndFive, "2", pathName, true);
		JSONObject object = JSONObject.fromObject(str);
		writer.write(object.toString());
		writer.flush();
	}

	/*********
	 * 考试提交后，使用AJAX提交考生的当前答案并且备份考生答案，记录未答题信息及可能出现的服务器错误 临时保存试卷
	 * 
	 * @author niewenqiang
	 * @throws IOException
	 * @date 2017-4-28
	 * *************/
	@RequestMapping(value = "/addUserExamResultByAjax", method = RequestMethod.POST)
	public void addUserExamResultByAjax(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String examExampaperExamineId = request
				.getParameter("examExampaperExamineId");
		String quesAnswer = request.getParameter("quesAnswer");
		String quesAnswerTwo = request.getParameter("quesAnswerTwo");
		String quesAnswerFourAndFive = request
				.getParameter("quesAnswerFourAndFive");
		String pathName = request.getSession().getServletContext()
				.getRealPath("/")
				+ "ExamResultDir";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = response.getWriter();
		String str = examJoinService.saveExamRstToFileAndData(
				examExampaperExamineId, quesAnswer, quesAnswerTwo,
				quesAnswerFourAndFive, "1", pathName, false);
		JSONObject object = JSONObject.fromObject(str);
		writer.write(object.toString());
		writer.flush();
	}

}
