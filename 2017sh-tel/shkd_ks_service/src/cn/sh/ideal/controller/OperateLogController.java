package cn.sh.ideal.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.OperateLog;
import cn.sh.ideal.service.IOperateLogService;
import cn.sh.ideal.util.LogUitls;

import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/controller/operlog")
public class OperateLogController {
	private static Logger log = Logger.getLogger(OperateLogController.class);
	@Resource 
	 private  IOperateLogService operatelogService;
	
	@Log(methodname="test",modulename="日志列表",funcname="日子列表查询",description="获取日志列表,{0}")
	@RequestMapping(value="/test",method = RequestMethod.GET)
	public void test(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");	
		try {
			response.getWriter().print("11111");
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功"}));
		} catch (IOException e) {
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}	
	}
	
	@Log(methodname="getOperlist",modulename="日志列表",funcname="日志列表查询",description="获取日志列表,{0}")
	@RequestMapping(value="/getOperlist",method = RequestMethod.POST)
	public void getOperlist(HttpServletRequest request,HttpServletResponse response,OperateLog operatelog){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");	
		String workId=request.getParameter("workId");
		String startoperateDate=request.getParameter("startoperateDate");
		String stopoperateDate=request.getParameter("stopoperateDate");
		operatelog.setWorkId(workId);
		operatelog.setStartoperateDate(startoperateDate);
		operatelog.setStopoperateDate(stopoperateDate);
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		List<OperateLog> listopera=operatelogService.listoperatelog(operatelog);
		int count=operatelogService.countOperatelog(operatelog);
		String json = "{\"Total\":" + count + " , \"Rows\":"
				+ JSONArray.fromObject(listopera).toString() + "}";
		try {
			response.getWriter().print(json.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功"}));
		} catch (IOException e) {
			log.error("获取日志列表异常："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}	
	}
}
