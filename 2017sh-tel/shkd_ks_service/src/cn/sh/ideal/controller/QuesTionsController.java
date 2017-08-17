package cn.sh.ideal.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpRequest;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.tQuestions;
import cn.sh.ideal.model.tSysCode;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.ISysCodeService;
import cn.sh.ideal.service.QuesTionsService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.LogUitls;
import cn.sh.ideal.util.ParseExcelUtil;

import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping(value = "/controller/quesTions")
public class QuesTionsController {
	private static Logger log = Logger.getLogger(OperateLogController.class);
	@Autowired
	private QuesTionsService quesTionsService;
	@Autowired
	private ISysCodeService iSysCodeService;

	/**
	 * 获取考题列表数据
	 * 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("kstkgl:query")
	@RequestMapping(value = "/getQuesTions", method = RequestMethod.POST)
	public void getQeusTionsinfoList(HttpServletRequest request,
			HttpServletResponse response, tQuestions questions) {
		response.setContentType(Constans.CONTENTCHA);
		response.setCharacterEncoding(Constans.ENCODING);
		questions.setZuhuId(1);// 租户Id后期再加入
		// questions.setQuesStatus("1");//只查询有效数据
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		// 获取考题信息列表数据
		List<tQuestions> listopera = quesTionsService
				.getQuesTionsList(questions);
		// 获取考题总数
		int count = quesTionsService.getCountQuesTion(questions);
		String json = "{\"Total\":" + count + " , \"Rows\":"
				+ JSONArray.fromObject(listopera).toString() + "}";
		try {
			response.getWriter().print(json);
		} catch (Exception e) {
			log.error("考题列表数据获取异常:" + e);
		}
	}

	/**
	 * 返回从数据字典中列表数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toQuestionPage", method = RequestMethod.GET)
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
		quesTypeInfo.add(0, code);
		quesNanduInfo.add(0, code);
		request.setAttribute("quesTypeInfo", quesTypeInfo);
		request.setAttribute("quesNanduInfo", quesNanduInfo);
		
		
		return "system/sys_maintainque";

	}

	/**
	 * 修改考题维护跳转页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getquesTionbyId", method = RequestMethod.GET)
	public String getquesTionbyId(HttpServletRequest request,
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

		tQuestions questions = quesTionsService.getQuesTionbyId(Integer
				.parseInt(request.getParameter("pkAutoId")));

		request.setAttribute("questions", questions);
		return "system/editqeupage";

	}

	/**
	 * 新增考题跳转页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("kstkgl:add")
	@RequestMapping(value = "/getquesTionAdd", method = RequestMethod.GET)
	public String getquesTionAdd(HttpServletRequest request,
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

		return "system/addqeupage";
	}

	/**
	 * 删除考题-做逻辑删除
	 * 
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("kstkgl:delete")
	@Log(methodname = "getquesTionDelete", modulename = "题库管理", funcname = "考题删除", description = "删除考题操作,{0}")
	@RequestMapping(value = "/getquesTionDelete", method = RequestMethod.POST)
	public void getquesTionDelete(HttpServletRequest request,
			HttpServletResponse response) {
		Integer PkAutoId = Integer.parseInt(request.getParameter("PkAutoId"));
		response.setContentType(Constans.CONTENTCHA);
		response.setCharacterEncoding(Constans.ENCODING);
		try {
			JSONObject json = new JSONObject();
			tQuestions questions = new tQuestions();
			questions.setPkAutoId(PkAutoId);
			questions.setQuesStatus("0");// 将考题置为无效
			int a = quesTionsService.countQuestion(PkAutoId);
			if (a > 0) // 判断a值大于0,说明考题被使用，不能删除
			{
				Integer exampaperid = quesTionsService
						.querExampaperId(PkAutoId);
				json.put("flag", "no");
				json.put("message", "此考题已经被试卷使用，不能删除");
			} else {
				quesTionsService.deleteQuesTion(questions);
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
						.setObjects(new Object[] { "考题ID为"+PkAutoId+"删除成功" }));
				json.put("flag", "yes");
			}
			response.getWriter().print(json);
		} catch (Exception e) {
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "考题ID为"+PkAutoId+"删除失败" + e.getMessage() }));
			log.error("删除考题操作异常，异常信息：" + e.getMessage());
		}
	}
	@RequiresPermissions("kstkgl:update")
	@RequestMapping(value="checkEditQuestion")
	public void checkEditQuestion(HttpServletRequest request,HttpServletResponse response){
		Integer PkAutoId = Integer.parseInt(request.getParameter("PkAutoId"));
		response.setContentType(Constans.CONTENTCHA);
		response.setCharacterEncoding(Constans.ENCODING);
		try {
			JSONObject json = new JSONObject();
			tQuestions questions = new tQuestions();
			questions.setPkAutoId(PkAutoId);
			int a = quesTionsService.countQuestion(PkAutoId);
			if (a > 0) // 判断a值大于0,说明考题被使用，不能删除
			{
				json.put("flag", "no");
				json.put("message", "此考题已经被试卷使用，不能修改");
			} else {
				json.put("flag", "yes");
			}
			response.getWriter().print(json);
		} catch (Exception e) {
			log.error("跳转修改考题页面操作异常，异常信息：" + e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="checkQuestions")
	public void checkQuestions(HttpServletRequest request,HttpServletResponse response){
		response.setContentType(Constans.CONTENTCHA);
		response.setCharacterEncoding(Constans.ENCODING);
		tQuestions questions = new tQuestions();
		questions.setQuesAnswer(request.getParameter("quesAnswer"));
		questions.setQuesCorrectAnswer(request
				.getParameter("quesCorrectAnswer"));
		questions
				.setQuesType(Integer.parseInt(request.getParameter("quesType")));
		questions.setQuesPick(request.getParameter("quesPick"));
		questions.setQuesContent(request.getParameter("quesContent"));
		questions.setQuesScore(Integer.parseInt(request
				.getParameter("quesScore")));
		questions.setQuesStatus(request.getParameter("quesStatus"));
		questions.setQuesNandu(request.getParameter("quesNandu"));
		
		if (request.getParameter("fk_codetype_id_val") != null
				&& !request.getParameter("fk_codetype_id_val").equals("")) {
			questions.setFkCodetypeId(Integer.parseInt(request
					.getParameter("fk_codetype_id_val")));
		} else {
			questions.setFkCodetypeId(Integer.parseInt(request
					.getParameter("businessId")));
		}
		try {
			JSONObject json = new JSONObject();
			int total=quesTionsService.checkQuestions(questions);
			if (total > 0) // 判断a值大于0,说明考题被使用，不能删除
			{
				json.put("flag", "no");
			} else {
				json.put("flag", "yes");
			}
			response.getWriter().print(json);
		} catch (Exception e) {
			log.error("跳转修改考题页面操作异常，异常信息：" + e.getMessage());
		}
	}
	/**
	 * 考题模板下载方法
	 * 
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("kstkgl:export")
	@RequestMapping(value = "/downTemplate")
	public void downTemplate(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(Constans.CONTENTCHA);
		response.setCharacterEncoding(Constans.ENCODING);
		InputStream is = null;
		OutputStream os = null;
		try {
			String templateName = "template/批量导入考题模板.xls";
			String CONTEXT_PATH = request.getSession().getServletContext()
					.getRealPath("/");
			String path = CONTEXT_PATH + "/" + templateName;
			path = URLDecoder.decode(path, "UTF-8");
			File file = new File(path);
			String fileName = file.getName();

			is = new FileInputStream(file);
			os = response.getOutputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			BufferedOutputStream bos = new BufferedOutputStream(os);

			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");// 处理中文文件名的问题
			// fileName = new String(fileName.getBytes("UTF-8"), "GBK");
			response.reset();
			String fileType = "";
			if (fileName != null) {
				fileType = fileName.substring(fileName.lastIndexOf("."));
				// 显示不同类型的文件对应不同的MIME类型
				if (".doc".equalsIgnoreCase(fileType)) {
					response.setContentType("application/msword");
				} else if (".xls".equalsIgnoreCase(fileType)) {
					response.setContentType("application/vnd.ms-excel");
				} else if (".pdf".equalsIgnoreCase(fileType)) {
					response.setContentType("application/pdf");
				} else if (".csv".equalsIgnoreCase(fileType)) {
					response.setContentType("application/mscsv");
				} else {
					response.setContentType("text/html");
				}

				fileName = fileName.substring(0, fileName.lastIndexOf("."))
						+ "_" + DateUtil.getCurDateStr("yyyyMMddHHmmss")
						+ fileType;
			}
			response.setHeader("Content-Disposition", "attachment; filename="
					+ fileName);
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, bytesRead);// 将文件发送到客户端
			}
			bos.flush();
			bis.close();
			bos.close();

			// }
		} catch (IOException e) {// 文件下载出现异常
			log.error(this.getClass(), e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				log.error(e);
			}
		}
	}

	/**
	 * 批量导入考题操作
	 * 
	 * @param file
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("kstkgl:import")
	@Log(methodname = "importQuestion", modulename = "题库管理", funcname = "考题维护", description = "考题导入操作,{0}")
	@RequestMapping(value = "importQuestion", method = RequestMethod.POST)
	public void importQuestion(
			@RequestParam(value = "myFile", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		vSysuser user= (vSysuser) request.getAttribute("sysuser");
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
			paramMap.put("user", user);
			paramMap.put("errorFileName", errorFileName); // 错误文件名
			paramMap.put("errorFileNamePath", fileNamePath); // 错误模板文件路径
			Map<String, Object> map = quesTionsService.insertQuesTion(
					excelList, paramMap, titlelList);
			if (Integer.parseInt((String.valueOf(map.get("errorCnt")))) > 0) {
				map.put("errorFileName", errorFileName);
			}
			log.info("用户导入后返回信息：" + JSONObject.fromObject(map));
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "导入成功" }));
			pw.print(JSONObject.fromObject(map));
			pw.flush();
			pw.close();
		} catch (Exception e) {
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "导入失败" + e.getMessage() }));
			log.error("用户导入考题操作异常，异常信息：" + e.getMessage());
		} finally {
			if (pw != null)
				pw.close();
		}
	}

	/**
	 * 获取下拉部门列表数据
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getDepartmentList", method = RequestMethod.POST)
	public void getDepartmentList(HttpServletRequest request,
			HttpServletResponse response) {

		response.setContentType(Constans.CONTENTCHA);
		response.setCharacterEncoding(Constans.ENCODING);
		JSONArray array = new JSONArray();
		// 查询父节点信息
		List<tSysCode> list = quesTionsService.getFistTreeDepart();
		try {
			for (tSysCode code : list) {
				JSONObject json = new JSONObject();
				json.put("id", code.getPkAutoId().toString());
				json.put("text", code.getName());
				json.put("children", getSubList(code.getPkAutoId()));
				array.add(json);
			}
			response.getWriter().print(array);
		} catch (Exception e) {
			log.error("考题列表数据获取异常:" + e);
		}
	}

	// 递归遍历父节点下子节点信息
	public JSONArray getSubList(Integer pid) {
		JSONArray array = new JSONArray();
		List<tSysCode> funList = quesTionsService.getAllTreeDepart(pid);
		for (int j = 0; j < funList.size(); j++) {
			JSONObject json = new JSONObject();
			json.put("id", funList.get(j).getPkAutoId().toString());
			json.put("text", funList.get(j).getName());
			json.put("pid", funList.get(j).getPid().toString());
			List<tSysCode> list = quesTionsService.getAllTreeDepart(funList
					.get(j).getPkAutoId());
			for (tSysCode code : list) {
				json.put("children", getSubList(code.getPid()));
			}
			array.add(json);
		}
		return array;
	}

	/**
	 * 新增考题方法
	 * 
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("kstkgl:add")
	@Log(methodname = "saveQuesTions", modulename = "题库管理", funcname = "考题维护", description = "新增考题操作,{0}")
	@RequestMapping(value = "/saveQuesTions", method = RequestMethod.POST)
	public void saveQuesTions(HttpServletRequest request,
			HttpServletResponse response) {
		 vSysuser user= (vSysuser) request.getAttribute("sysuser");
		tQuestions questions = new tQuestions();
		questions.setQuesAnswer(request.getParameter("quesAnswer"));
		questions.setQuesCorrectAnswer(request
				.getParameter("quesCorrectAnswer"));
		questions
				.setQuesType(Integer.parseInt(request.getParameter("quesType")));
		questions.setQuesPick(request.getParameter("quesPick"));
		questions.setQuesContent(request.getParameter("quesContent"));
		questions.setQuesScore(Integer.parseInt(request
				.getParameter("quesScore")));
		questions.setFkCodetypeId(Integer.parseInt(request
				.getParameter("fk_codetype_id_val")));
		questions.setQuesStatus(request.getParameter("quesStatus"));
		questions.setQuesNandu(request.getParameter("quesNandu"));
		questions.setFkInsertUserId(user.getUserId());//录入人
		questions.setInsertTime(DateUtil.getDateTimeStr(new Date()));
		questions.setZuhuId(1);// 先写死后面在session中获取
		String[] quesPick = null;

		if (null != questions.getQuesPick()
				&& !questions.getQuesPick().equals("")) {
			quesPick = questions.getQuesPick().split("\\;");

			if (quesPick.length > 1 && quesPick.length < 27) {
				addNum(quesPick, questions);
			}
		}
		String flag = "";
		try {

			int num = quesTionsService.saveQuesTion(questions);
			if (num == 1 ) {
				flag = "200";
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
						new Object[] { "新增成功,考题ID为"+questions.getPkAutoId() }));
			} else {
				flag = "400";
			}
			JSONObject json = new JSONObject();
			json.put("flag", flag);
			response.getWriter().print(json);
		} catch (Exception e) {
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "新增失败" + e.getMessage() }));
			log.error("新增考题操作异常，异常信息：" + e.getMessage());
		}

	}

	private void addNum(String[] quesPick, tQuestions questions) {
		String zimu[] = new String[] { "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
				"U", "V", "W", "X", "Y", "Z" };
		String QuesPick = "";
		for (int i = 0; i < quesPick.length; i++) {
			
			if(quesPick[i].length()>2&&(zimu[i].concat(".")).equals(quesPick[i].substring(0, 2))){
				return;
			}
			QuesPick += zimu[i] + "." + quesPick[i] + ";";
		}
		QuesPick = QuesPick.substring(0, QuesPick.length() - 1);
		questions.setQuesPick(QuesPick);
	}
	
	private void updNum(String[] quesPick, tQuestions questions) {
		String zimu[] = new String[] { "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
				"U", "V", "W", "X", "Y", "Z" };
		String QuesPick = "";
		for (int i = 0; i < quesPick.length; i++) {
			if((zimu[i].concat(".")).equals(quesPick[i].substring(0, 2))){
				return;
			}
			QuesPick += zimu[i] + "." + quesPick[i] + ";";
		}
		QuesPick = QuesPick.substring(0, QuesPick.length() - 1);
		questions.setQuesPick(QuesPick);
	}

	/**
	 * 修改考题
	 * 
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("ksmbsj:frsjktq")
	@Log(methodname = "updateQuesTions", modulename = "题库管理", funcname = "考题维护", description = "修改考题操作,{0}")
	@RequestMapping(value = "/updateQuesTions", method = RequestMethod.POST)
	public void updateQuesTions(HttpServletRequest request,
			HttpServletResponse response) {
		  vSysuser user= (vSysuser) request.getAttribute("sysuser");
		tQuestions questions = new tQuestions();
		Integer PkAutoId=Integer.parseInt(request.getParameter("pkAutoId"));
		questions.setPkAutoId(Integer.parseInt(request.getParameter("pkAutoId")));
		questions.setQuesAnswer(request.getParameter("quesAnswer"));
		questions.setQuesCorrectAnswer(request.getParameter("quesCorrectAnswer"));
		questions.setQuesType(Integer.parseInt(request.getParameter("quesType")));
		questions.setQuesPick(request.getParameter("quesPick"));
		questions.setQuesContent(request.getParameter("quesContent"));
		questions.setQuesScore(Integer.parseInt(request
				.getParameter("quesScore")));
		if (request.getParameter("fk_codetype_id_val") != null
				&& !request.getParameter("fk_codetype_id_val").equals("")) {
			questions.setFkCodetypeId(Integer.parseInt(request
					.getParameter("fk_codetype_id_val")));
		} else {
			questions.setFkCodetypeId(Integer.parseInt(request
					.getParameter("businessId")));
		}
		questions.setQuesStatus(request.getParameter("quesStatus"));
		questions.setQuesNandu(request.getParameter("quesNandu"));
		questions.setFkUpdateUserId(user.getUserId());//获取当前修改人
		questions.setInsertTime(request.getParameter("insertTime"));
		questions.setUpdateTime(DateUtil.getDateTimeStr(new Date()));
		questions.setZuhuId(1);// 先写死后面在session中获取
		String[] quesPick = null;

		if (null != questions.getQuesPick()
				&& !questions.getQuesPick().equals("")) {
			quesPick = questions.getQuesPick().split("\\;");

			if (quesPick.length > 1 && quesPick.length < 27) {
				updNum(quesPick, questions);
			}
		}
		String flag = "";
		try {
			int num = quesTionsService.updateQuesTion(questions);
			if (num == 1) {
				flag = "200";
			} else {
				flag = "400";
			}
			JSONObject json = new JSONObject();
			json.put("flag", flag);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] { "考题ID为"+PkAutoId+"修改成功" }));
			response.getWriter().print(json);
		} catch (IOException e) {
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(
					new Object[] {"考题ID为"+PkAutoId+",修改失败" +e.getMessage()}));
			log.error("修改考题失败，错误信息：",e);
		}

	}
}
