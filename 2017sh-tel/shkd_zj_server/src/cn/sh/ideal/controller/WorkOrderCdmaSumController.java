package cn.sh.ideal.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageHelper;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.WorkOrderCdma;
import cn.sh.ideal.model.WorkOrderCdmaSum;
import cn.sh.ideal.service.IWorkOrderCdmaSumService;
import cn.sh.ideal.util.LogUitls;

@Controller
@RequestMapping("/controller/workordersdmasum")
public class WorkOrderCdmaSumController {
	private static Logger log = Logger.getLogger(WorkOrderCdmaSumController.class);			
	
	@Resource
	private IWorkOrderCdmaSumService workorderCdmaService;
	
	
	@RequiresPermissions("cworder:query")
	@RequestMapping(value="/togetwkordercdmaList",method=RequestMethod.GET)
	public String togetwkordercdmaList(){		
		return "dhxj/cw_dhxjInfo";
	}
	
	/**
	 * @author lk 2017/03/20
	 * @param request
	 * @param response
	 * @param workOrdercdma
	 * 根据c网工单查询电话小结列表
	 */
	@RequestMapping(value="/getwkordercdmatelsum",method = RequestMethod.POST)
	public void getwkordercdmatelsum(HttpServletRequest request,HttpServletResponse response ,WorkOrderCdmaSum ordercdma){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		PageHelper.startPage(currentPage, pageSize);
		//String serialCdma=request.getParameter("serialCdma"); //c网工单主键
		List<WorkOrderCdmaSum> list=workorderCdmaService.selectByPrimaryKey(ordercdma);
		int total=workorderCdmaService.sumselectByPrimaryKey(ordercdma);
		JSONObject json=new JSONObject();
		json.put("Total", total);
		json.put("Rows", list);
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			log.error("获取电话小结列表异常："+e.getMessage());
		}
	}
	
	/**
	 * @author lk 2017/4/17
	 * @param request
	 * @param response
	 * 查询c网电话小结下拉
	 */
		@RequestMapping(value = "/getcwxjtype", method = RequestMethod.POST)
		public void getcwxjtype(HttpServletRequest request,HttpServletResponse response){
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			try{
			List<String> bstype=workorderCdmaService.getcwbstype();
			List<String> xjtype=workorderCdmaService.getcwxjtype();
			JSONObject json=new JSONObject();
			json.put("xjtype", JSONArray.fromObject(xjtype));
			json.put("bstype", JSONArray.fromObject(bstype));
			response.getWriter().print(json);
			}catch (Exception e) {
				log.error("获取c网电话小结查询下拉框异常：" + e.getMessage());
			}
		}
	
	
}
