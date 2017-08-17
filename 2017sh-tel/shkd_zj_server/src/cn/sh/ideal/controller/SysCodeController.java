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
import cn.sh.ideal.model.SysCode;
import cn.sh.ideal.service.ISysCodeService;
import cn.sh.ideal.util.LogUitls;

@Controller
@RequestMapping("/controller/syscode")
public class SysCodeController {
	private static Logger log = Logger.getLogger(SysCodeController.class);
	@Resource
	private  ISysCodeService sysCodeService;
	
	
	@Log(methodname="getcode",modulename="系统常量",funcname="获取系统常量",description="获取系统常量,{0}", code = "ZJ")
	@RequestMapping(value="/getcode",method=RequestMethod.POST)
	public void getcode(HttpServletRequest request,HttpServletResponse response,SysCode record){
		response.setContentType("text/html;charset=utf-8");
		String businessType = request.getParameter("businessType");
		String isTl9000 = request.getParameter("isTl9000");	
		
		
		SysCode syscode=new SysCode();
		syscode.setPid(Integer.parseInt(businessType));
		if("1".equals(isTl9000)){
			syscode.setIsTl9000(isTl9000);
		}
		List<SysCode> codelist=sysCodeService.selectSyscode(syscode);
		SysCode syscode2=new SysCode();
		if("10".equals(businessType)){
			syscode2.setPid(124);
		}else if("54".equals(businessType)){
			syscode2.setPid(124);
		}else if("32".equals(businessType)){
			syscode2.setPid(96);
		}else if("76".equals(businessType)){
			syscode2.setPid(172);
		}else if("178".equals(businessType)){
			syscode2.setPid(124);
		}else if("200".equals(businessType)){
			syscode2.setPid(124);
		}else{
			syscode2.setPid(Integer.parseInt(businessType));
		}
		List<SysCode> checkcodelist=sysCodeService.selectSyscode(syscode2);
		
		String json = "{ \"codeList\":"
				+ JSONArray.fromObject(codelist).toString() + ",\"checkcodelist\":"
				+ JSONArray.fromObject(checkcodelist).toString() + "}";
		try {
			System.out.println(json.toString());
			response.getWriter().print(json.toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功"}));
		} catch (IOException e) {
			log.error("获取系统常量异常："+e.getMessage());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
		
	}

}
