package cn.sh.ideal.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.model.ExamManualScore;
import cn.sh.ideal.model.tExampaper;
import cn.sh.ideal.model.tSysCode;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.IExampaperService;
import cn.sh.ideal.service.ISysCodeService;
import cn.sh.ideal.util.Constans;

import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping(value = "/controller/exampaper")
public class ExampaperController {

	private static Logger log = Logger.getLogger(OperateLogController.class);
	@Autowired
	private IExampaperService exampaperService;
	@Autowired
	private ISysCodeService iSysCodeService;

	//****测试
    int zuhuId = 1;
	/**
     * 考试维护管理查询 查询试卷
     * @param request
     * @param response
     * @author chendi
     * @return
     */
	@RequestMapping(value = "/getExampaperss", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getExampaperss(
			@ModelAttribute("queryExampaper") tExampaper paper,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType(Constans.CONTENTCHA);
		response.setCharacterEncoding(Constans.ENCODING);   
        vSysuser user= (vSysuser) request.getAttribute("sysuser");
		JSONObject result = new JSONObject();
		try {
		    log.info("操作用户:["+user.getUserId()+"]"+"查询试卷开始");
			List<tExampaper> paperLists = exampaperService.getExampapers(paper);
			log.info("操作用户:["+user.getUserId()+"]"+"查询试卷结束");
			result.put("Rows", JSONArray.fromObject(paperLists));
			return result;
		} catch (Exception e) {
		    log.error("操作用户:["+user.getUserId()+"]"+"查询试卷开失败："+e.getMessage(),e); 
		}
		return null;
	}

	/**
	 * 获取随机模板试卷列表
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequiresPermissions("ksmbsj:query")
	@RequestMapping(value = "getExampaperRandom", method = RequestMethod.POST)
	public void getExampaperRandom(tExampaper exampaper,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType(Constans.CONTENTCHA);
		response.setCharacterEncoding(Constans.ENCODING);
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		exampaper.setZuhuId(1);// 租户ID先写共固定值
		PageHelper.startPage(currentPage, pageSize);
		int count = 0;
		String json="";
		List<ExamManualScore> paperLists = null;
		if(request.getParameter("notQueryList")!=null){
			json = "{\"Total\":" + count + " , \"Rows\":"+json+"}";
		}else{
			try {
			// 判断用户是否要查询历史模板试卷
				paperLists = exampaperService.getExampaperRandomList(exampaper);
				count = exampaperService.getCountRandomCount(exampaper);
			json = "{\"Total\":" + count + " , \"Rows\":"
					+ JSONArray.fromObject(paperLists).toString() + "}";
		} catch (Exception e) {
			log.error(this.getClass(), e);
		}
		response.getWriter().print(json);
		}
	}

	@RequestMapping(value = "toManagerPage")
	public String toManagerquePage(HttpServletRequest request,
			HttpServletResponse response) {
		// 获取试卷类型下拉列表
		List<tSysCode> examTypeInfo = iSysCodeService
				.getItemFlagList(Constans.SYSPAPERTYPE);
		tSysCode code = new tSysCode();
		code.setItemFlag("0");
		code.setName(Constans.PLEASECHOOSE);
		// 获取试卷状态下拉列表
		List<tSysCode> examStateInfo = iSysCodeService
				.getItemFlagList(Constans.QUETIONSTATE);
		examTypeInfo.add(0, code);
		examStateInfo.add(0, code);
		request.setAttribute("examTypeInfo", examTypeInfo);
		request.setAttribute("examStateInfo", examStateInfo);

		return "system/sys_templateque";
	}

	/**
	 * 跳转随机题库选题页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("ksmbsj:cqkt")
	@RequestMapping(value = "/toShowRandomPage")
	public String toShowRandomPage(HttpServletRequest request,
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

		quesTypeInfo.add(0, code);
		quesNanduInfo.add(0, code);
		request.setAttribute("quesTypeInfo", quesTypeInfo);
		request.setAttribute("quesNanduInfo", quesNanduInfo);

		return "system/sys_showRandomQues";
	}
	/**
	 * 跳转随机模板试卷
	 * @param request
	 * @param httpServletResponse
	 * @return
	 */
	@RequiresPermissions("ksmbsj:scmb")
	@RequestMapping(value="addExampaperRandom")
	public String addExampaperRandom(HttpServletRequest request,HttpServletResponse response){
		// 获取考题类型下拉列表
		List<tSysCode> quesTypeInfo = iSysCodeService
				.getItemFlagList(Constans.SYSQUETYPE);
		tSysCode code = new tSysCode();
		code.setItemFlag("0");
		code.setName(Constans.PLEASECHOOSE);
		// 获取考试难度下拉列表
		List<tSysCode> quesNanduInfo = iSysCodeService
				.getItemFlagList(Constans.SYSYNANDU);

		// 试卷类型下拉列表
		List<tSysCode> quesPapersTyep = iSysCodeService
				.getItemFlagList(Constans.SYSPAPERTYPE);
		quesTypeInfo.add(0, code);
		quesPapersTyep.add(0,code);
		quesNanduInfo.add(0, code);
		request.setAttribute("quesTypeInfo", quesTypeInfo);
		request.setAttribute("quesNanduInfo", quesNanduInfo);
		request.setAttribute("quesPapersTyep", quesPapersTyep);
		return "system/sys_tempmoban";
		
	}
}
