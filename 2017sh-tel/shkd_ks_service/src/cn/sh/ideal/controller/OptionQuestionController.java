package cn.sh.ideal.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.TEAL;
import org.apache.poi.ss.formula.functions.Value;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageHelper;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.TQuestionsArea;
import cn.sh.ideal.model.tExampaper;
import cn.sh.ideal.model.tExampaperQuestions;
import cn.sh.ideal.model.tQuestions;
import cn.sh.ideal.model.tSysCode;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.ISysCodeService;
import cn.sh.ideal.service.OptionQuestionService;
import cn.sh.ideal.service.QuesTionsService;
import cn.sh.ideal.util.ArrayDistinct;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.CustomException;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.LogUitls;

@Controller
@RequestMapping(value = "controller/optionQue")
public class OptionQuestionController {
	private static Logger log = Logger.getLogger(OperateLogController.class);
	@Autowired
	private QuesTionsService quesTionsService;

	@Autowired
	private ISysCodeService iSysCodeService;

	@Autowired
	private OptionQuestionService optionQuestionService;

	@RequestMapping(value = "/toOptionque", method = RequestMethod.GET)
	public String toQuestionPages(HttpServletRequest request,
			HttpServletResponse response) {
		// 获取考题类型下拉列表
		List<tSysCode> quesTypeInfo = iSysCodeService
				.getItemFlagList(Constans.SYSQUETYPE);
		tSysCode code = new tSysCode();
		code.setItemFlag("0");
		code.setName(Constans.PLEASECHOOSE);
		// 获取考试难度下拉列表
		List<tSysCode> quesNanduInfo = iSysCodeService
				.getItemFlagList(Constans.SYSYNANDU);

		// 获取业务分类下拉列表

		quesTypeInfo.add(0, code);
		quesNanduInfo.add(0, code);
		request.setAttribute("quesTypeInfo", quesTypeInfo);
		request.setAttribute("quesNanduInfo", quesNanduInfo);

		return "system/sys_optionque";
	}

	/**
	 * 获取考题列表数据
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	
	@RequestMapping(value = "/getQuesTions", method = RequestMethod.POST)
	public void getQeusTionsinfoList(HttpServletRequest request,
			HttpServletResponse response, tQuestions questions) throws IOException {
		response.setContentType(Constans.CONTENTCHA);
		response.setCharacterEncoding(Constans.ENCODING);
		String json ="";
		String notQueryList= request.getParameter("notQueryList");
		if(notQueryList!=null){
			json = "{\"Total\":" + 0 + " , \"Rows\":"
					+ null + "}";
		}else{
		questions.setZuhuId(1);// 租户Id后期再加入
		questions.setQuesStatus("1");// 只查询有效数据
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		// 获取考题信息列表数据
		List<tQuestions> listopera=quesTionsService.getQuesTionsList(questions);
		int count=quesTionsService.getCountQuesTion(questions);
				json= "{\"Total\":" + count + " , \"Rows\":"
				+ JSONArray.fromObject(listopera).toString() + "}";
		}
		try {
			response.getWriter().print(json);
		} catch (Exception e) {
			log.error("考题列表数据获取异常:" + e);
		}
	}

	/**
	 * 将考题放入考题区操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequiresPermissions("kssjgl:import")
	@Log(methodname = "inputQuesArea", modulename = "试卷管理", funcname = "题库选题", description = "放入考题区,{0}")
	@RequestMapping(value = "/inputQuesArea", method = RequestMethod.POST)
	public void inputQuesArea(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String checkedCustomer = request.getParameter("checkedCustomer");
		 vSysuser user= (vSysuser) request.getAttribute("sysuser");
		// 根据用户Id和考题状态查询当前用户下的考题列表
		TQuestionsArea area = optionQuestionService
				.selectQuestionAreaById(user.getUserId().toString());
		try {
			if (area != null) {
				// 修改查询到的考题状态为无效
				area.setEnabled(Constans.FAILE);
				optionQuestionService.updateQuestionAreaExaMaprer(area);
				// 对考题Id数组进行去重
				String args1[] = area.getQuestionId().split(",");
				String args2[] = checkedCustomer.split(",");
				String questionId = ArrayDistinct.repetArray(args1, args2);
				area.setEnabled(Constans.SUCCESS);
				area.setQuestionId(questionId);// 将去重后的考题数组重新写入到考题字段中
				area.setCreateQuestionTime(DateUtil.getDateTimeStr(new Date()));
				area.setUpdateCount(area.getUpdateCount() + 1);
				optionQuestionService.insertQuestionAreaExaMaprer(area);
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
						.setObjects(new Object[] { "选择的考题Id："+checkedCustomer+"操作成功" }));
			} else {
				TQuestionsArea questionsArea = new TQuestionsArea();
				questionsArea.setUserId(user.getUserId().toString());// 先写死后期在改用户Id
				questionsArea.setUpdateCount(1);
				questionsArea.setIsTemplate(Constans.FAILE);// 是否是模板试卷 0：否 1：是
				questionsArea.setCreateQuestionTime(DateUtil
						.getDateTimeStr(new Date()));// 当前新增时间
				questionsArea.setEnabled(Constans.SUCCESS);// 是否有效 0无效 1:有效
				questionsArea.setQuestionId(checkedCustomer);// 考题数据，多个考题,分割
				optionQuestionService
						.insertQuestionAreaExaMaprer(questionsArea);
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
						.setObjects(new Object[] { "选择的考题Id："+checkedCustomer+"操作成功" }));
			}
		} catch (Exception e) {
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "操作异常：" + e.getMessage() }));
			log.error("放入考题区操作异常，异常信息：" + e.getMessage());
		}
		JSONObject json = new JSONObject();
		json.put("flag", "yes");
		response.getWriter().print(json);

	}

	/**
	 * 查询用户考题内容
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("kssjgl:query")
	@RequestMapping(value = "/queryQuesAreaUpSeled")
	public String queryQuesAreaUpSeled(HttpServletRequest request,
			HttpServletResponse response) {
		// 查询当前用户下选择的考题内容
		 vSysuser user= (vSysuser) request.getAttribute("sysuser");
		List<tQuestions> questions = new ArrayList<tQuestions>();
		TQuestionsArea area = optionQuestionService
				.selectQuestionAreaById(user.getUserId().toString());

		if (area != null) {
			// 判断如果用户当前考题区不为空,将考题Id拆分数组进行查询
			questions = quesTionsService.queryQuesSelected(area.getQuestionId()
					.split(","));
		}
		// 获取试卷类型下拉列表
		List<tSysCode> papersTyep = iSysCodeService
				.getItemFlagList(Constans.SYSPAPERTYPE);
		request.setAttribute("papersTyep", papersTyep);

		request.setAttribute("selectList", questions);
		return "system/optiontable";
	}

	/**
	 * 保存随机考题区内容
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("kstkgl:update")
	@Log(methodname = "inputRandomQuesArea", modulename = "试卷管理", funcname = "模板试卷", description = "放入随机考题区,{0}")
	@RequestMapping(value = "/inputRandomQuesArea")
	public void inputRandomQuesArea(HttpServletRequest request,
			HttpServletResponse response) {
		// 查询当前用户下选择的考题内容
		String checkedCustomer = request.getParameter("checkedCustomer");
		vSysuser user= (vSysuser) request.getAttribute("sysuser");
		// 根据用户Id查询随机考题区
		TQuestionsArea area = optionQuestionService
				.selectRandomQuestionAreaById(user.getUserId().toString());
		try {
			if (area != null) {
				// 修改查询到的考题状态为无效
				area.setEnabled(Constans.FAILE);
				optionQuestionService.updateQuestionAreaExaMaprer(area);
				// 对考题Id数组进行去重
				String args1[] = area.getQuestionId().split(",");
				String args2[] = checkedCustomer.split(",");
				String questionId = ArrayDistinct.repetArray(args1, args2);
				area.setEnabled(Constans.SUCCESS);
				area.setQuestionId(questionId);// 将去重后的考题数组重新写入到考题字段中
				area.setCreateQuestionTime(DateUtil.getDateTimeStr(new Date()));
				area.setUpdateCount(area.getUpdateCount() + 1);
				optionQuestionService.insertQuestionAreaExaMaprer(area);
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
						.setObjects(new Object[] { "添加随机考题"+checkedCustomer+"操作成功" }));
			} else {
				TQuestionsArea questionsArea = new TQuestionsArea();
				questionsArea.setUserId(user.getUserId().toString());// 先写死后期在改用户Id
				questionsArea.setUpdateCount(1);
				questionsArea.setIsTemplate(Constans.SUCCESS); // 是否是模板试卷 0：否
																// 1：是
				questionsArea.setCreateQuestionTime(DateUtil
						.getDateTimeStr(new Date()));// 当前新增时间
				questionsArea.setEnabled(Constans.SUCCESS);// 是否有效 0无效 1:有效
				questionsArea.setQuestionId(checkedCustomer);// 考题数据，多个考题,分割
				optionQuestionService
						.insertQuestionAreaExaMaprer(questionsArea);
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
						.setObjects(new Object[] { "添加随机考题"+checkedCustomer+"操作成功" }));
			}
		} catch (Exception e) {
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "操作异常：" + e.getMessage() }));
			log.error("放入随机考题区操作异常，异常信息：" + e.getMessage(),e);
		}
		JSONObject json = new JSONObject();
		json.put("flag", "yes");
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			log.error("放入随机考题区操作异常，异常信息：" + e.getMessage(),e);
		}
	}
	/**
	 * 查看随机考题区的内容
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("ksmbsj:frsjktq")
	@RequestMapping(value = "/queryRandomQuesArea")
	public String queryRandomQuesArea(HttpServletRequest request,
			HttpServletResponse response) {
		// 查询当前用户下选择的考题内容
		 vSysuser user= (vSysuser) request.getAttribute("sysuser");
		List<tQuestions> questions = new ArrayList<tQuestions>();
		TQuestionsArea area = optionQuestionService
				.selectRandomQuestionAreaById(user.getUserId().toString());
		if (area != null) {
			// 判断如果用户当前考题区不为空,将考题Id拆分数组进行查询
			questions = quesTionsService.selectRandomQuestionAreaById(area
					.getQuestionId().split(","));
		}
		// 获取试卷类型下拉列表
		List<tSysCode> papersTyep = iSysCodeService
				.getItemFlagList(Constans.SYSPAPERTYPE);
		request.setAttribute("papersTyep", papersTyep);

		request.setAttribute("selectList", questions);
		return "system/Randomoptiontable";
	}

	/**
	 * 删除考题区内容
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("kssjgl:delete")
	@Log(methodname = "removeQuesUpSeled", modulename = "试卷管理", funcname = "题库选题", description = "删除考题区内容,{0}")
	@RequestMapping(value = "/removeQuesUpSeled")
	public String removeQuesUpSeled(HttpServletRequest request,
			HttpServletResponse response) {
		String checkedCustomer = request.getParameter("selectQues");
		vSysuser user= (vSysuser) request.getAttribute("sysuser");
		TQuestionsArea area = optionQuestionService
				.selectQuestionAreaById(user.getUserId().toString());
		try {
			if (area != null) {
				// 修改查询到的考题状态为无效
				area.setEnabled(Constans.FAILE);
				optionQuestionService.updateQuestionAreaExaMaprer(area);
				// 对考题Id数组进行去重
				String args1[] = area.getQuestionId().split(",");
				String args2[] = checkedCustomer.split(",");
				// 删除用户选择的数据
				String questionId = ArrayDistinct.removeArray(args1, args2);
				area.setEnabled(Constans.SUCCESS);
				area.setQuestionId(questionId);// 将去重后的考题数组重新写入到考题字段中
				area.setCreateQuestionTime(DateUtil.getDateTimeStr(new Date()));
				area.setUpdateCount(area.getUpdateCount() + 1);
				optionQuestionService.insertQuestionAreaExaMaprer(area);
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
						.setObjects(new Object[] { "删除的考题ID为"+checkedCustomer+"操作成功" }));
			}
		} catch (Exception e) {
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "操作失败" + e.getMessage() }));
			log.error("删除考题区操作失败，错误信息：" + e.getMessage());
		}
		return "redirect:/controller/optionQue/queryQuesAreaUpSeled.do";
	}

	/**
	 * 删除随机考题区跳转刷新
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("ksmbsj:scsx")
	@Log(methodname = "removeRandomQuesUpSeled", modulename = "试卷管理", funcname = "随机模板试卷", description = "删除随机考题区内容,{0}")
	@RequestMapping(value = "/removeRandomQuesUpSeled")
	public String removeRandomQuesUpSeled(HttpServletRequest request,
			HttpServletResponse response) {
		String checkedCustomer = request.getParameter("selectQues");
		 vSysuser user= (vSysuser) request.getAttribute("sysuser");
		TQuestionsArea area = optionQuestionService
				.selectRandomQuestionAreaById(user.getUserId().toString());
		try {

			if (area != null) {
				// 修改查询到的考题状态为无效
				area.setEnabled(Constans.FAILE);
				optionQuestionService.updateQuestionAreaExaMaprer(area);
				// 对考题Id数组进行去重
				String args1[] = area.getQuestionId().split(",");
				String args2[] = checkedCustomer.split(",");
				// 删除用户选择的数据
				String questionId = ArrayDistinct.removeArray(args1, args2);
				area.setEnabled(Constans.SUCCESS);
				area.setQuestionId(questionId);// 将去重后的考题数组重新写入到考题字段中
				area.setCreateQuestionTime(DateUtil.getDateTimeStr(new Date()));
				area.setUpdateCount(area.getUpdateCount() + 1);
				optionQuestionService.insertQuestionAreaExaMaprer(area);
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
						.setObjects(new Object[] { "操作成功删除的随机考题为Id为："+checkedCustomer }));
			}
		} catch (Exception e) {
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "操作失败" + e.getMessage() }));
			log.error("删除随机考题区操作失败，错误信息：" + e.getMessage());
		}
		return "redirect:/controller/optionQue/queryRandomQuesArea.do";
	}

	/**
	 * 跳转到试卷预览页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("kssjgl:createexam")
	@RequestMapping(value = "/viewExampapar")
	public String viewExampapar(HttpServletRequest request,
			HttpServletResponse response) {
		String exampaperId = request.getParameter("examPaperId");
		String selectQues = request.getParameter("selectQues");// 考试编号串
		String selectQuesScore = request.getParameter("selectQuesScore");// 考试分数串
		String[] PkAutoid = selectQues.split(",");

		String[] sc = request.getParameterValues("sc");// 考题编号数组
		String scoreTotal = request.getParameter("scoreTotal");// 总分
		String examPaperName = request.getParameter("exampaperNames");// 试卷名称
		String exampaperRemark = request.getParameter("exampaperRemarks");// 试卷 // 备注
		String examPaperType = request.getParameter("examPaperTypes");// 考卷类型
		String isIndex = request.getParameter("isIndexs"); // 是否乱序
		List<tQuestions> questions = new ArrayList<tQuestions>();
		for (int i = 0; i < PkAutoid.length; i++) {
			questions.add(quesTionsService.querytQuestById(Integer
					.parseInt(PkAutoid[i])));
		}
		request.setAttribute("selectList", questions);
		request.setAttribute("sc", sc);
		request.setAttribute("selectQues", selectQues);
		request.setAttribute("selectQuesScore", selectQuesScore);
		request.setAttribute("scoreTotal", scoreTotal);
		request.setAttribute("exampaperName", examPaperName);
		request.setAttribute("exampaperRemark", exampaperRemark);
		request.setAttribute("isIndex", isIndex);
		request.setAttribute("examPaperType", examPaperType);
		request.setAttribute("exampaperId", exampaperId);

		return "system/viewExampaper";

	}

	/**
	 * 添加试卷和考题关联表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	
	@RequiresPermissions("kssjgl:add")
	@Log(methodname = "insertExampaparQeus", modulename = "试卷管理", funcname = "题库选题", description = "生成试卷操作,{0}")
	@RequestMapping(value = "insertExampaparQeus")
	public void insertExampaparQeus(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		vSysuser user= (vSysuser) request.getAttribute("sysuser");
		String zhuhuid = "1";
		String exampaperId = request.getParameter("exampaperId");// 需要修改的试卷Id
		String selectQues = request.getParameter("selectQues");// 考题编号串
		String[] sc = selectQues.split(",");// 考题编号数组
		String sq = request.getParameter("selectQuesScore");
		String[] ts = new String[sc.length];// 考题分数数组
		String scoreTotal = request.getParameter("scoreTotal");
		String isIndex = request.getParameter("isIndex"); // 是否乱序 add by
		String examPaperType = request.getParameter("examPaperType");// add by
		String examPaperName = request.getParameter("examPaperName");// 试卷名称
		String exampaperRemark = request.getParameter("exampaperRemark");// 试卷备注
		JSONObject json=new JSONObject();
		if (sq != null) {
			ts = sq.split(",");
		}
		tExampaper exampaper = new tExampaper();
		try {
			if (null != exampaperId && !"".equals(exampaperId)) {
				exampaper = quesTionsService.queryExampaperById(Integer
						.parseInt(exampaperId));
				exampaper.setExamPaperName(examPaperName);
				exampaper.setExamPaperScore(Integer.parseInt(scoreTotal));
				exampaper.setExamPaperRemark(exampaperRemark);
				exampaper.setExamPaperStatus(Constans.SUCCESS); // 0 无效；1//
																// 有效无效的试卷,以后的考试,本试卷不可选
				exampaper.setIsindex(isIndex);
				exampaper.setExamPaperFlag(Constans.SUCCESS); // 1表示考题试卷 2表示模板试卷
																// 3 //
																// 表示批量导入整张试卷
				exampaper.setExamPaperType(examPaperType); // 1-普通试卷 2-模板试卷
				exampaper.setFkInsertUserId(user.getUserId());
				exampaper.setInsertTime(DateUtil.getDateTimeStr(new Date()));
				exampaper.setZuhuId(Integer.parseInt(zhuhuid));
				quesTionsService.updateExampaper(exampaper);// 修改试卷
				tExampaperQuestions tExampaperQuestions=new tExampaperQuestions();
				tExampaperQuestions.setEnabled(Constans.FAILE);//讲关联表置为无效
				tExampaperQuestions.setPkAutoId(Integer.parseInt(exampaperId));
				quesTionsService.deleteExamQuesByExamId(tExampaperQuestions);// 删除试卷考题表中旧的对应关系
				for (int i = 0; i < sc.length; i++) {
					tExampaperQuestions exampaperQuestions = new tExampaperQuestions();
					exampaperQuestions.setEnabled(Constans.SUCCESS);// 是否有效
					exampaperQuestions.setFkExampaperId(Integer.parseInt(exampaperId));// 试卷Id
					exampaperQuestions
							.setFkQuestionsId(Integer.parseInt(sc[i]));// 考题Id
					exampaperQuestions.setQuesScore(Integer.parseInt(ts[i]));// 考题分数
					exampaperQuestions.setFkInsertUserId(user.getUserId());// 选题人Id
					exampaperQuestions.setInsertTime(DateUtil
							.getDateTimeStr(new Date()));
					exampaperQuestions.setUpdateTime(DateUtil
							.getDateTimeStr(new Date()));
					exampaperQuestions.setExampaperFlag(Constans.FAILE);// 试卷模板
					exampaperQuestions.setZuhuId(Integer.parseInt(zhuhuid));// 租户Id
					optionQuestionService
							.insertExampaperQuestions(exampaperQuestions);
				}
				TQuestionsArea area = optionQuestionService
						.selectUpdateQuestionAreaById(user.getUserId().toString());
				if (area != null) {
					// 修改查询到的考题状态为无效
					area.setEnabled(Constans.FAILE);
					area.setUpdateCount(area.getUpdateCount() + 1);
					// 将用户考题区置为无效
					optionQuestionService.updateQuestionAreaExaMaprer(area);
				}
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
						new Object[] { "修改试卷成功,生成的试卷Id为："+exampaperId + "操作成功" }));
				
			} else {
				// 新增一张试卷
				exampaper.setExamPaperName(examPaperName);
				exampaper.setExamPaperScore(Integer.parseInt(scoreTotal));
				exampaper.setExamPaperRemark(exampaperRemark);
				exampaper.setExamPaperStatus(Constans.SUCCESS); // 0 无效；1//
																// 有效无效的试卷,以后的考试,本试卷不可选
				exampaper.setIsindex(isIndex);
				exampaper.setExamPaperFlag(Constans.SUCCESS); // 1表示考题试卷 2表示模板试卷 3 表示批量导入整张试卷
				exampaper.setExamPaperType(examPaperType); // 1-普通试卷 2-模板试卷
				exampaper.setFkInsertUserId(user.getUserId());
				exampaper.setInsertTime(DateUtil.getDateTimeStr(new Date()));
				exampaper.setZuhuId(Integer.parseInt(zhuhuid));
				optionQuestionService.insertExampaper(exampaper);
				Integer exampaperAutoId = exampaper.getPkAutoId();// 获取生成的试卷的主键id
				// 新增试卷和考题关联信息
				if (null != exampaperAutoId) {
					
				for (int i = 0; i < sc.length; i++) {
					tExampaperQuestions exampaperQuestions = new tExampaperQuestions();
					exampaperQuestions.setEnabled(Constans.SUCCESS);// 是否有效
					exampaperQuestions.setFkExampaperId(exampaperAutoId);// 试卷Id
					exampaperQuestions
							.setFkQuestionsId(Integer.parseInt(sc[i]));// 考题Id
					exampaperQuestions.setQuesScore(Integer.parseInt(ts[i]));// 考题分数
					exampaperQuestions.setFkInsertUserId(user.getUserId());// 选题人Id
					exampaperQuestions.setInsertTime(DateUtil
							.getDateTimeStr(new Date()));
					exampaperQuestions.setUpdateTime(DateUtil
							.getDateTimeStr(new Date()));
					exampaperQuestions.setExampaperFlag(Constans.FAILE);// 试卷模板
					exampaperQuestions.setZuhuId(Integer.parseInt(zhuhuid));// 租户Id
					optionQuestionService
							.insertExampaperQuestions(exampaperQuestions);
				}
				TQuestionsArea area = optionQuestionService
						.selectQuestionAreaById(user.getUserId().toString());
				if (area != null) {
					// 修改查询到的考题状态为无效
					area.setEnabled(Constans.FAILE);
					area.setUpdateCount(area.getUpdateCount() + 1);
					// 将用户考题区置为无效
					optionQuestionService.updateQuestionAreaExaMaprer(area);
				}
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
						new Object[] { "生成试卷成功,生成的试卷Id为："+exampaperAutoId + "操作成功" }));
				json.put("result", "success");
			}else{
				json.put("result", "defeated");
				}
			}
		} catch (Exception e) {
			json.put("result", "defeated");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "生成试卷和考题关联异常" + e.getMessage() }));
			log.error("考题和试卷关联信息录入异常");
			throw new CustomException();
		}
		response.getWriter().print(json);
	}
}
