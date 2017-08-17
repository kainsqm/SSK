package cn.sh.ideal.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.TQuestionsArea;
import cn.sh.ideal.model.tExampaper;
import cn.sh.ideal.model.tExampaperRandom;
import cn.sh.ideal.model.tQuestions;
import cn.sh.ideal.model.tQuestionsRandomsel;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.ExamManagerService;
import cn.sh.ideal.service.ExampaperRandomService;
import cn.sh.ideal.service.OptionQuestionService;
import cn.sh.ideal.service.QuesTionsService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.LogUitls;

/**
 * 随机模板试卷
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/controller/exampaperRandom")
public class ExampaperRandomController {
	private static Logger log = Logger
			.getLogger(ExampaperRandomController.class);
	@Autowired
	private QuesTionsService quesTionsService;
	@Autowired
	private ExampaperRandomService exampaperRandomService;
	@Autowired
	private OptionQuestionService optionQuestionService;
	@Autowired
	private ExamManagerService examManagerService;

	/**
	 * 根据业务类型查询模板试卷题库是否存在
	 * 
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("ksmbsj:xz")
	@RequestMapping(value = "/isExistData")
	public void isExistData(HttpServletRequest request,
			HttpServletResponse response) {
		vSysuser user = (vSysuser) request.getAttribute("sysuser");
		try {
			tQuestions questions = new tQuestions();
			questions.setFkCodetypeId(Integer.parseInt(request
					.getParameter("fkcodetypevalId")));
			questions.setQuesNandu(request.getParameter("quesNandu"));
			questions.setQuesType(Integer.parseInt(request
					.getParameter("quesType")));
			TQuestionsArea area = exampaperRandomService
					.selectRandomQuestionAreaById(user.getUserId().toString());
			;
			questions.setQuestIds(area.getQuestionId().split(","));
			int result = exampaperRandomService.selectQuestionCount(questions);
			response.getWriter().print(result);
		} catch (IOException e) {
			log.error("查询模板试卷题库信息失败：" + e.getMessage());
		}
	}

	/**
	 * 新增考题模板试卷操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("ksmbsj:tj")
	@Log(methodname = "insertExampaperRandom", modulename = "试卷管理", funcname = "模板试卷", description = "生成模板试卷,{0}")
	@RequestMapping(value = "/insertExampaperRandom")
	public String insertExampaperRandom(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(Constans.CONTENTCHA);
		response.setCharacterEncoding(Constans.ENCODING);
		// 用户和租户先写死
		vSysuser user = (vSysuser) request.getAttribute("sysuser");
		Integer zuhuId = 1;
		String examPaperName = request.getParameter("examPaperName");
		String examPaperRemark = request.getParameter("examPaperRemark");
		String examPaperType = request.getParameter("examPaperType");
		String totalScoreValue = request.getParameter("totalScoreValue");

		// 将前台页面拼装的业务类型,考题类型,考题数量，考题难度，考题分数进行解析
		String[] fkcodetypeIdValue = request.getParameter("fkcodetypeIdValue")
				.split(",");
		String[] quesTypeValue = request.getParameter("quesTypeValue").split(
				",");
		String[] quesCountValue = request.getParameter("quesCountValue").split(
				",");
		String[] quesScoreValue = request.getParameter("quesScoreValue").split(
				",");
		String[] quesNanduValue = request.getParameter("quesNanduValue").split(
				",");

		// 生成一张模板试卷
		tExampaper exampaper = new tExampaper();
		try {
			exampaper.setExamPaperName(examPaperName);
			exampaper.setExamPaperScore(Integer.parseInt(totalScoreValue));
			exampaper.setExamPaperRemark(examPaperRemark);
			exampaper.setExamPaperStatus(Constans.SUCCESS);
			exampaper.setIsindex(Constans.SUCCESS);
			exampaper.setExamPaperFlag("2");// 表示导入模板试卷
			exampaper.setExamPaperType(examPaperType);
			exampaper.setFkInsertUserId(user.getUserId());
			exampaper.setInsertTime(DateUtil.getDateTimeStr(new Date()));
			exampaper.setZuhuId(zuhuId);
			optionQuestionService.insertExampaper(exampaper);
		} catch (Exception e) {
			log.error("生成模板试卷异常：" + e.getMessage());
		}

		Integer exAmpaperAutoId = exampaper.getPkAutoId();// 获取新增的试卷Id

		for (int i = 0; i < fkcodetypeIdValue.length; i++) {
			tExampaperRandom exampaperRandom = new tExampaperRandom();
			exampaperRandom.setFkCodetypeId(Integer
					.parseInt(fkcodetypeIdValue[i]));
			exampaperRandom.setQueType(quesTypeValue[i]);
			exampaperRandom.setQueCount(Integer.parseInt(quesCountValue[i]));
			exampaperRandom.setQueScore(Integer.parseInt(quesScoreValue[i]));
			exampaperRandom.setFkExampaperId(exAmpaperAutoId);
			exampaperRandom.setEnabled(Constans.SUCCESS);
			exampaperRandom.setQueNandu(quesNanduValue[i]);
			exampaperRandom.setZuhuId(zuhuId);
			exampaperRandomService.insertExampaperRandom(exampaperRandom);
		}
		TQuestionsArea area = optionQuestionService
				.selectRandomQuestionAreaById(user.getUserId().toString());
		List<tQuestions> questions = new ArrayList<tQuestions>();
		try {
			if (null != area) {
				questions = quesTionsService.selectRandomQuestionAreaById(area
						.getQuestionId().split(","));
				area.setEnabled(Constans.FAILE);
				optionQuestionService.updateQuestionAreaExaMaprer(area);
				for (tQuestions que : questions) {
					tQuestionsRandomsel questionsRandomsel = new tQuestionsRandomsel();
					questionsRandomsel.setFkQuestionid(que.getPkAutoId());
					questionsRandomsel.setScore(que.getQuesScore());
					questionsRandomsel.setFkExampaperid(exAmpaperAutoId);
					questionsRandomsel.setFkUserId(user.getUserId());
					questionsRandomsel.setZuhuId(zuhuId);
					questionsRandomsel.setEnabled(Constans.SUCCESS);
					exampaperRandomService
							.insertQuestionsRandomSel(questionsRandomsel);
				}
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
						.setObjects(
								new Object[] { "新增的试卷Id为" + exAmpaperAutoId
										+ "新增成功" }));
			}
		} catch (Exception e) {
			log.error("录入模板试卷考题表异常：" + e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "新增失败" + e.getMessage() }));
		}
		return "redirect:/controller/exampaper/toManagerPage.do";
	}

	@RequiresPermissions("ksmbsj:delete")
	@Log(methodname="deleletExampaper",modulename="试卷管理",funcname="模板试卷",description="删除模板试卷,{0}")
	@RequestMapping(value="deleletExampaper")
	public void deleletExampaper(HttpServletRequest request,HttpServletResponse response){
		response.setContentType(Constans.CONTENTCHA);
		response.setCharacterEncoding(Constans.ENCODING);
		JSONObject json=new JSONObject();
		try {
			Integer pkAutoId = Integer.parseInt(request
					.getParameter("PkAutoId"));
			tExampaper exampaper = new tExampaper();
			exampaper.setExamPaperStatus(Constans.FAILE);// 将试卷置为无效
			exampaper.setPkAutoId(pkAutoId);
			// 验证模板试卷是否用于考试
			int count = examManagerService.checkExampaperExaminee(pkAutoId);
			if (count > 0) {
				json.put("flag", "no");
				json.put("message", "试卷已用于考试,无法删除");
			} else {
				exampaperRandomService.updateRandomExampaper(exampaper);
				// 将随机考题和模板中间表进行物理删除
				exampaperRandomService.deleteRandomExampaper(pkAutoId);
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
						.setObjects(
								new Object[] { "模板试卷Id为" + pkAutoId + "删除成功" }));
				json.put("flag", "yes");
			}
			response.getWriter().print(json);
		} catch (Exception e) {
			json.put("flag", "no");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "删除失败" + e.getMessage()}));
			log.error("删除模板试卷失败，错误信息：", e);
		}
	}
	
}
