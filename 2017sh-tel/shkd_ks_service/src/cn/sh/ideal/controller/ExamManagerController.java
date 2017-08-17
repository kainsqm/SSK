package cn.sh.ideal.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.ExamManualScore;
import cn.sh.ideal.model.TQuestionsArea;
import cn.sh.ideal.model.tExampaper;
import cn.sh.ideal.model.tQuestions;
import cn.sh.ideal.model.tSysCode;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.ExamManagerService;
import cn.sh.ideal.service.ISysCodeService;
import cn.sh.ideal.service.OptionQuestionService;
import cn.sh.ideal.service.QuesTionsService;
import cn.sh.ideal.util.ArrayDistinct;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.LogUitls;
import cn.sh.ideal.util.ParseExcelUtil;

@Controller
@RequestMapping(value="controller/examManager")
public class ExamManagerController {
	private static Logger log = Logger.getLogger(ExamManagerController.class);
	@Autowired
	private ISysCodeService iSysCodeService;
	@Autowired
	private QuesTionsService quesTionsService;
	@Autowired
	private ExamManagerService examManagerService;
	@Autowired
	private OptionQuestionService optionQuestionService;
	
	/**
	 * 跳转试卷管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/toExamManagerPage")
	public String toExamManagerPage(HttpServletRequest request,HttpServletResponse response){
		// 获取试卷类型下拉列表
		List<tSysCode> examTypeInfo = iSysCodeService
				.getItemFlagList(Constans.SYSPAPERTYPE);
		tSysCode code = new tSysCode();
		code.setPkAutoId(0);
		code.setName(Constans.PLEASECHOOSE);
		// 获取试卷状态下拉列表
		List<tSysCode> examStateInfo = iSysCodeService
				.getItemFlagList(Constans.QUETIONSTATE);
		examTypeInfo.add(0, code);
		examStateInfo.add(0, code);
		request.setAttribute("examTypeInfo", examTypeInfo);
		request.setAttribute("examStateInfo", examStateInfo);
		return "system/sys_managerque";
		
	}
	/**
	 * 查询试卷列表
	 * @param exampaper
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/queryExampapers")
	@RequiresPermissions("kssjwh:query")
	public void queryExampapers(tExampaper exampaper,HttpServletRequest request,HttpServletResponse response) throws IOException{
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
				paperLists = examManagerService.queryExampapersList(exampaper);
				count = examManagerService.queryExampapersCount(exampaper);
			json = "{\"Total\":" + count + " , \"Rows\":"
					+ JSONArray.fromObject(paperLists).toString() + "}";
		} catch (Exception e) {
			log.error(this.getClass(), e);
		}
		response.getWriter().print(json);
		}
	}
	/**
	 * 导出试卷操作
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequiresPermissions("kssjwh:export")
	@Log(methodname="downLoadExamPaperFile",modulename="试卷管理",funcname="试卷维护",description="导出试卷,{0}")
	@RequestMapping(value="/downLoadExamPaperFile")
	public void downLoadExamPaperFile(HttpServletResponse response,HttpServletRequest request) throws IOException{
		Integer pkAutoId=Integer.parseInt(request.getParameter("pkAutoIds"));
		//根据试卷Id查询试卷
		tExampaper exampaper=examManagerService.getExampaperById(pkAutoId);
		if(null==exampaper){
			return;
		}
		//根据试卷Id查询试卷关联考题信息
		List<tQuestions> questions =examManagerService.getQuesByExampaperId(pkAutoId);
		if(null!=questions&&questions.size()>0){
			int quesSize=questions.size();
			String webPath = request.getSession().getServletContext().getRealPath("/");
			String path = webPath+"template/批量导入考卷头模板.xls";
			String fileName = exampaper.getExamPaperName()+".xls";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment; filename=" + encodeChineseDownloadFileName(request,fileName));
			
			OutputStream outputStream = response.getOutputStream();
			outputStream.flush();
			Workbook wb = null;
			WritableWorkbook wwb = null;
			try {
				wb = Workbook.getWorkbook(new File(path));
				wwb = Workbook.createWorkbook(outputStream,wb);
				WritableSheet sheet = wwb.getSheet(0);
				for(int i=0;i<quesSize;i++){
					tQuestions vo = (tQuestions)questions.get(i);
					
					Integer quesType = vo.getQuesType();
					String quesPick = vo.getQuesPick();
					String quesAnswer = vo.getQuesAnswer();
					String quesSoce=vo.getQuesScore().toString();
					
					if(3==quesType || 4==quesType || 5==quesType){
						StringBuffer buffer = new StringBuffer();
						if(quesPick!=null && !"".equals(quesPick)){
							String[] picks = quesPick.split(";");
							int len = picks.length;
							for(int j=0;j<len;j++){
								if(!"".equals(picks[j]) && picks[j].length()>2){
									buffer.append(picks[j].substring(2)).append(";");
								}
							}
						}
						if(buffer.length()>0){
							quesPick = buffer.substring(0,buffer.length()-1);
						}
					}else if(6==quesType){
						if(quesAnswer!=null)
							quesAnswer = quesAnswer.replace(";", "#");
					}
					String quesConect = vo.getQuesContent();
					if(quesConect!=null && !"".equals(quesConect)){
						quesConect = quesConect.replace("&nbsp;", "");
					}
					sheet.addCell(new Label(0,i+3,vo.getQuesTypeStr()));//题型
					sheet.addCell(new Label(1,i+3,quesSoce));//考卷考题分值
					sheet.addCell(new Label(2,i+3,quesConect));//考题内容
					sheet.addCell(new Label(3,i+3,quesPick));//可选项
					sheet.addCell(new Label(4,i+3,quesAnswer));//参考答案
					sheet.addCell(new Label(5,i+3,vo.getQuesCorrectAnswer()));//改错信息答案
					sheet.addCell(new Label(6,i+3,vo.getBusinessType()));//业务分类
					sheet.addCell(new Label(7,i+3,vo.getQuesNanduStr()));//难度
				}
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
						new Object[] {"模板试卷Id为"+pkAutoId+"导出成功"}));
				wwb.write();
				outputStream.flush();
			} catch (RowsExceededException e) {
				log.error("导出试卷操作异常"+e.getMessage(),e);
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
						new Object[] {"模板试卷Id为"+pkAutoId+"导出失败"}));
			} catch (WriteException e) {
				log.error("导出试卷操作异常"+e.getMessage(),e);
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
						new Object[] {"模板试卷Id为"+pkAutoId+"导出失败"}));
			} catch (BiffException e){
				log.error("导出试卷操作异常"+e.getMessage(),e);
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
						new Object[] {"模板试卷Id为"+pkAutoId+"导出失败"}));
			} finally{
				try {
					if(wwb!=null)wwb.close();
				} catch (WriteException e) {
					log.error("导出试卷操作异常"+e.getMessage(),e);
					LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
							new Object[] {"模板试卷Id为"+pkAutoId+"导出失败"}));
				}finally{
					if(outputStream!=null){
						outputStream.close();
						outputStream = null;
					}
					if(wb!=null)wb.close();
				}
			}
		}else{
			
		}
	}
	
	 public static String encodeChineseDownloadFileName(  
	            HttpServletRequest request, String pFileName) throws UnsupportedEncodingException {  
	          
	         String filename = null;    
	            String agent = request.getHeader("USER-AGENT");    
	            if (null != agent){    
	                if (-1 != agent.indexOf("Firefox")) {//Firefox    
	                    filename = "=?UTF-8?B?" + (new String(org.apache.commons.codec.binary.Base64.encodeBase64(pFileName.getBytes("UTF-8"))))+ "?=";    
	                }else if (-1 != agent.indexOf("Chrome")) {//Chrome    
	                    filename = new String(pFileName.getBytes(), "ISO8859-1");    
	                } else {//IE7+    
	                    filename = java.net.URLEncoder.encode(pFileName, "UTF-8");    
	                    filename = StringUtils.replace(filename, "+", "%20");//替换空格    
	                }    
	            } else {    
	                filename = pFileName;    
	            }    
	            return filename;   
	    }
	/**
	 * 导入试卷操作
	 * @param file
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("kssjwh:import")
	@Log(methodname="uploadFile",modulename="试卷管理",funcname="试卷维护",description="导入试卷,{0}")
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	public void uploadFile(
			@RequestParam(value = "myFile", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		vSysuser user= (vSysuser) request.getAttribute("sysuser");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			String basePath = request.getSession().getServletContext()
					.getRealPath("template/")
					+ File.separatorChar; // 错误模板excel路径
			String fileName = file.getOriginalFilename();
			// 从第3行开始读取excel内容
			int readStratRow = 3;
			List<List<Object>> excelList = new ArrayList<List<Object>>();
			List<List<Object>> titlelList = new ArrayList<List<Object>>();
			ParseExcelUtil parseExcelUtil = new ParseExcelUtil();
			if (fileName.toLowerCase().endsWith(".xls")) {
				excelList = parseExcelUtil.readExcel(file.getInputStream(),
						readStratRow, true, false);
				titlelList = parseExcelUtil.readExcel(file.getInputStream(), 2,
						true, false);
			}
			if (fileName.toLowerCase().endsWith(".xlsx")) {
				excelList = parseExcelUtil.readExcel(file.getInputStream(),
						readStratRow, false, true);
				titlelList = parseExcelUtil.readExcel(file.getInputStream(), 2,
						false, true);
			}
			Map<Object, Object> paramMap = new HashMap<Object, Object>();
			String fileNamePath = basePath + "question_error" + ".xls";
			String currentDate = DateUtil.getCurDateStr("yyyyMMddHHmmss");
			String errorFileName = "question_error" + currentDate + ".xls";
			paramMap.put("basePath", basePath);
			paramMap.put("errorFileName", errorFileName); // 错误文件名
			paramMap.put("errorFileNamePath", fileNamePath); // 错误模板文件路径
			Map<String, Object> map = examManagerService.checkExamQuestion(
					excelList, paramMap, titlelList);
			if (Integer.parseInt((String.valueOf(map.get("errorCnt")))) > 0) {
				map.put("errorFileName", errorFileName);
			}else{
				//考题信息没有错误，进行导入考卷考题信息
				map.put("examPaperName", request.getParameter("examPaperName"));
				map.put("examPaperRemark", request.getParameter("examPaperRemark"));
				map.put("examPaperType", request.getParameter("examPaperType"));
				map.put("isIndex", request.getParameter("isIndex"));
				map.put("user", user);
				examManagerService.insertBatCode(map);
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
						new Object[] {"模板试卷导入成功"}));
				log.info("用户导入后返回信息：" + JSONObject.fromObject(map));
			}
			pw.print(JSONObject.fromObject(map));
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error("用户导入考题操作异常，异常信息：" + e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] {"模板试卷导入异常"+e.getMessage()}));
		} finally {
			if (pw != null)
				pw.close();
		}
	}
	/**
	 * 删除试卷
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Log(methodname="deleteExamPaper",modulename="试卷管理",funcname="试卷维护",description="删除试卷,{0}")
	@RequiresPermissions("kssjwh:delete")
	@RequestMapping("/deleteExamPaper")
	public void deleteExamPaper(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject jsonObject=new JSONObject();
		response.setContentType(Constans.CONTENTCHA);
		response.setCharacterEncoding(Constans.ENCODING);
		Integer pkAutoId=Integer.parseInt(request.getParameter("pkAutoIds"));
		//判断考卷是否用于考试
		tExampaper exampaper=new tExampaper();
		exampaper.setPkAutoId(pkAutoId);
		exampaper.setExamPaperStatus(Constans.FAILE);//讲试卷信息置为无效
		try {
			int count=examManagerService.checkExampaperExaminee(pkAutoId);
			if(count!=0){
				jsonObject.put("flag", "no");
				jsonObject.put("message", "试卷已用于考试,无法删除");
			}else{
			examManagerService.deleteExamById(exampaper);
			jsonObject.put("flag", "yes");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] {"试卷Id为："+pkAutoId+"删除成功"}));
			}
		} catch (Exception e) {
			log.error("删除试卷失败，请检查相关SQL"+e.getMessage());
			jsonObject.put("flag", "no");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] {"试卷Id为："+pkAutoId+"删除失败"+e.getMessage()}));
		}
		response.getWriter().print(jsonObject);
	}
	@RequestMapping("/checkExampaperExaminee")
	public void checkExampaperExaminee(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer pkAutoId=Integer.parseInt(request.getParameter("pkAutoIds"));
		//查询试卷是否被用于考试
		int count=examManagerService.checkExampaperExaminee(pkAutoId);
		response.getWriter().print(count);
	}
	/**
	 * 试卷修改跳转查询页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("kssjwh:update")
	@RequestMapping("/queryQuesByExamIds")
	public String queryQuesByExamIds(HttpServletRequest request,HttpServletResponse response){
		String exampaperId=request.getParameter("exampaperId");
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
		request.setAttribute("exampaperId", exampaperId);
		return "system/sys_optionque_update";
		
	}
	@RequiresPermissions("kssjgl:query")
	@RequestMapping("/getQuesTionsUpdate")
	public void getQuesTionsUpdate(HttpServletRequest request,HttpServletResponse response,tQuestions questions) {
		response.setContentType(Constans.CONTENTCHA);
		response.setCharacterEncoding(Constans.ENCODING);
		questions.setZuhuId(1);// 租户Id后期再加入
		String chooseType=questions.getChooseType();
		List<tQuestions> tQuestions=null;
		int count=0;
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		try {
			if("1".equals(chooseType)){
				//查询所有考题
				tQuestions=examManagerService.getQuestionList(questions);
				count=examManagerService.getQuestionListCount(questions);
			}else{
				//查询当前考卷考题
				tQuestions=examManagerService.queryQeusByExamId(questions);
				count=examManagerService.queryQeusByExamIdCount(questions);
			}
		} catch (Exception e) {
			log.error("考题列表数据获取异常:" + e.getMessage(),e);
		}
		// questions.setQuesStatus("1");//只查询有效数据
		
		request.setAttribute("exampaperId", questions.getExampaperId());
		String json = "{\"Total\":" + count + " , \"Rows\":"
				+ JSONArray.fromObject(tQuestions).toString() + "}";
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			log.error("考题列表数据获取异常:" + e.getMessage(),e);
		}
	}
	/**
	 * 保存考卷修改考题信息操作
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequiresPermissions("kssjgl:import")
	@Log(methodname="inputUpdateQuesArea",modulename="试卷管理",funcname="修改试卷",description="放入考题区,{0}")
	@RequestMapping(value = "/inputUpdateQuesArea", method = RequestMethod.POST)
	public void inputUpdateQuesArea(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String checkedCustomer = request.getParameter("checkedCustomer");
		 vSysuser user= (vSysuser) request.getAttribute("sysuser");
		// 根据用户Id和考题状态查询当前用户下的考题列表
		TQuestionsArea area = examManagerService.selectUpdateQuestionAreaById(user.getUserId().toString());
		try {
			optionQuestionService.updateQuestionAreaExaMaprer(area, checkedCustomer,user);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] {"放入考题区操作，考题信息为"+checkedCustomer+"放入考题区成功"}));
		} catch (Exception e) {
			log.error("放入考题区操作异常，异常信息：" + e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] {"放入考题区操作，考题信息为"+checkedCustomer+"放入考题区失败"}));
		}
		JSONObject json = new JSONObject();
		json.put("flag", "yes");
		response.getWriter().print(json);

	}
	/**
	 * 查看用户选择的考题信息内容
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("kssjgl:selected")
	@RequestMapping(value = "/queryUpdateQuesAreaUpSeled")
	public String queryUpdateQuesAreaUpSeled(HttpServletRequest request,
			HttpServletResponse response) {
		Integer exampaperId=Integer.parseInt(request.getParameter("exampaperId"));
		
		// 查询当前用户下选择的考题内容
		 vSysuser user= (vSysuser) request.getAttribute("sysuser");
		List<tQuestions> questions = new ArrayList<tQuestions>();
		TQuestionsArea area = examManagerService.selectUpdateQuestionAreaById(user.getUserId().toString());
		
		if (area != null) {
			// 判断如果用户当前考题区不为空,将考题Id拆分数组进行查询
			questions = quesTionsService.queryQuesSelected(area.getQuestionId()
					.split(","));
		}
		
		tExampaper exampaper=examManagerService.selectExampaperById(exampaperId);
		
		// 获取试卷类型下拉列表
		List<tSysCode> papersTyep = iSysCodeService
				.getItemFlagList(Constans.SYSPAPERTYPE);
		request.setAttribute("papersTyep", papersTyep);
		request.setAttribute("exampaper", exampaper);
		request.setAttribute("selectList", questions);
		return "system/optiontable_update";
	}
	/**
	 * 删除修改时考题区的题目
	 * @param request
	 * @param response
	 * @return
	 */
	@Log(methodname = "removeUpdateExamPapaer", modulename = "试卷管理", funcname = "试卷维护", description = "删除考题区内容,{0}")
	@RequestMapping(value="/removeUpdateExamPapaer")
	public String removeUpdateExamPapaer(HttpServletRequest request,HttpServletResponse response){
		String checkedCustomer = request.getParameter("selectQues");
		String exampaperId=request.getParameter("examPaperId");
		 vSysuser user= (vSysuser) request.getAttribute("sysuser");
		TQuestionsArea area = optionQuestionService
				.selectUpdateQuestionAreaById(user.getUserId().toString());
		try {
			if (area != null) {
				optionQuestionService.updateQuestionAreaExaMaprer(area, checkedCustomer);
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
						.setObjects(new Object[] { "删除的考题ID为"+checkedCustomer+"操作成功" }));
			}
		} catch (Exception e) {
			log.error("删除考题区操作失败，错误信息：" + e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
					.setObjects(new Object[] { "删除的考题ID为"+checkedCustomer+"操作失败" }));
		}
		return "redirect:/controller/examManager/queryUpdateQuesAreaUpSeled.do?exampaperId="+exampaperId;
	}
	/**
	 * 校验试卷名称是否被使用
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="checkExampaperName")
	public void checkExampaperName(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String examPaperName=request.getParameter("examPaperNames");
		int total=examManagerService.checkExampaperName(examPaperName);
		JSONObject json=new JSONObject();
		json.put("result", total);
		response.getWriter().print(json);
		
	}
	/**
	 * 跳转查询非模板试卷页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="toChooseExamPage")
	public String toChooseExamPage(HttpServletRequest request,HttpServletResponse response){
		// 获取试卷状态下拉列表
		List<tSysCode> ExamState = iSysCodeService
				.getItemFlagList(Constans.QUETIONSTATE);
		tSysCode code = new tSysCode();
		code.setItemFlag("0");
		code.setName(Constans.PLEASECHOOSE);
		// 获取试卷类型下拉列表
		List<tSysCode> ExamType = iSysCodeService
				.getItemFlagList(Constans.SYSPAPERTYPE);
		ExamType.add(0, code);
		request.setAttribute("ExamState", ExamState);
		request.setAttribute("ExamType", ExamType);
		return "system/choose_exam";
	}
	/**
	 * 查询 非模板试卷列表数据
	 * @param exampaper
	 * @param request
	 * @param response
	 */
		@RequestMapping(value="queryExampapersList")
		public void queryExampapersList(tExampaper exampaper, HttpServletRequest request,HttpServletResponse response){
		response.setContentType(Constans.CONTENTCHA);
		response.setCharacterEncoding(Constans.ENCODING);
		exampaper.setZuhuId(1);// 租户ID先写共固定值
		int count = 0;
		List<ExamManualScore> paperLists = null;
		try {
			//查询非模板试卷
			paperLists = examManagerService.selectExampapersList(exampaper);
			count = examManagerService.selectExampapersCount(exampaper);
			String json = "{\"Total\":" + count + " , \"Rows\":"
					+ JSONArray.fromObject(paperLists).toString() + "}";
			response.getWriter().print(json);
		} catch (Exception e) {
			log.error(this.getClass(), e);
		}
	}
	/**
	 * 跳转查询模板试卷和非模板试卷页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="toChooseExamPaparPage")
	public String toChooseExamPaparPage(HttpServletRequest request,HttpServletResponse response){
		// 获取试卷状态下拉列表
		List<tSysCode> ExamState = iSysCodeService
				.getItemFlagList(Constans.QUETIONSTATE);
		tSysCode code = new tSysCode();
		code.setItemFlag("0");
		code.setName(Constans.PLEASECHOOSE);
		// 获取试卷类型下拉列表
		List<tSysCode> ExamType = iSysCodeService
				.getItemFlagList(Constans.SYSPAPERTYPE);
		ExamType.add(0, code);
		request.setAttribute("ExamState", ExamState);
		request.setAttribute("ExamType", ExamType);
		return "system/choose_exampaper";
		}
	/**
	 * 查询模板和非模板试卷列表
	 * @param exampaper
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="queryRandomExampapersList")
	public void queryRandomExampapersList(tExampaper exampaper, HttpServletRequest request,HttpServletResponse response){
	response.setContentType(Constans.CONTENTCHA);
	response.setCharacterEncoding(Constans.ENCODING);
	exampaper.setZuhuId(1);// 租户ID先写共固定值
	int count = 0;
	List<ExamManualScore> paperLists = null;
	try {
		//查询模板试卷和非模板试卷
		paperLists = examManagerService.selectRandomExampapersList(exampaper);
		count = examManagerService.selectRandomExampapersCount(exampaper);
		String json = "{\"Total\":" + count + " , \"Rows\":"
				+ JSONArray.fromObject(paperLists).toString() + "}";
		response.getWriter().print(json);
	} catch (Exception e) {
		log.error(this.getClass(), e);
	}
}
	/**
	 * 查询考题内容-根据考卷查询考题信息
	 * @param request
	 * @param response
	 * @param questions
	 */
	
	@RequiresPermissions(value={"kssjgl:selected","ksmbsj:ktcx"},logical=Logical.OR)
	@RequestMapping(value = "/getQuesTions", method = RequestMethod.POST)
	public void getQeusTionsinfoList(HttpServletRequest request,
			HttpServletResponse response, tQuestions questions) {
		response.setContentType(Constans.CONTENTCHA);
		response.setCharacterEncoding(Constans.ENCODING);
		questions.setZuhuId(1);// 租户Id后期再加入
		questions.setQuesStatus("1");// 只查询有效数据
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		// 获取考题信息列表数据
		List<tQuestions> listopera=null;
		int count=0;
		if(null!=questions.getExampaperId()&&!"".equals(questions.getExampaperId())){
			if(null!=questions.getExamPaperFlag()&&questions.getExamPaperFlag().equals("2")){
				listopera=quesTionsService.getQUestionByRandomList(questions);
				count=quesTionsService.getQUestionByRandomCount(questions);
			}else{
				listopera=quesTionsService.getQuesTionbyExamIdList(questions);
				count=quesTionsService.getQuesTionbyExamIdCount(questions);
			}
		}else{
			listopera=quesTionsService.getQuesTionsList(questions);
			count=quesTionsService.getCountQuesTion(questions);
		}
		
		String json = "{\"Total\":" + count + " , \"Rows\":"
				+ JSONArray.fromObject(listopera).toString() + "}";
		try {
			response.getWriter().print(json);
		} catch (Exception e) {
			log.error("考题列表数据获取异常:" + e);
		}
	}
}
