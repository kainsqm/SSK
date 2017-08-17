package cn.sh.ideal.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.Function;
import cn.sh.ideal.service.IFunctionService;
import cn.sh.ideal.util.LogUitls;

@Controller
@RequestMapping("/controller/fun")
public class FunctionController {
	private static Logger log = Logger.getLogger(FunctionController.class);
	@Resource
	private IFunctionService functionService;
	
	@RequestMapping(value="/getFunList",method = RequestMethod.POST)
	public void getFunList(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String userId=request.getParameter("userId");
		int userid=0;
		if(StringUtils.isNotEmpty(userId)){
			userid=Integer.parseInt(userId);
		}
		JSONArray array =new JSONArray();
		List<Function> funList=functionService.getFunctionByUserid(userid);
		for(int i=0;i<funList.size();i++){
			JSONObject json =new JSONObject();
			json.put("id", funList.get(i).getFunctionId().toString());
			json.put("text", funList.get(i).getFuntionName());
			json.put("imgUrl", funList.get(i).getImgUrl());
			json.put("childItems", getSubList(funList.get(i).getFunctionId(),userid));
			array.add(json);
		}
		try {
			response.getWriter().print(array);	
		} catch (IOException e) {
			log.error("用户"+userId+"获取功能列表异常:"+e.getMessage());
		}
	}
	
	@Log(methodname="getFunLists",modulename="角色信息维护",funcname="新增角色，获取功能列表",description="新增角色，获取功能列表,{0}", code = "ZJ")
	@RequestMapping(value="/getFunLists",method = RequestMethod.POST)
	public void getFunLists(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		Function fun =new Function();
		
		List<Function> funList=functionService.getFunction(fun);
		try {
			response.getWriter().print(JSONArray.fromObject(funList).toString());
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{"操作成功"}));
		} catch (IOException e) {
			log.error("新增角色，获取功能列表异常:"+e.getMessage(),e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant().setObjects(new Object[]{e.getMessage()}));
		}
	}
	
	public JSONArray getSubList(int id){
		JSONArray array =new JSONArray();
		Function fun =new Function();
		fun.setpId(id);
		List<Function> funList=functionService.getFunction(fun);
		for(int j=0;j<funList.size();j++){
			JSONObject json =new JSONObject();
			json.put("id", funList.get(j).getFunctionId().toString());
			json.put("text", funList.get(j).getFuntionName());
			json.put("url", funList.get(j).getUrl());
			json.put("pid", funList.get(j).getpId().toString());
			array.add(json);
		}
		return array;
	}
	
	public JSONArray getSubList(int id,int userId){
		JSONArray array =new JSONArray();
		List<Function> funList=functionService.getChildFunByParam(userId, id);
		for(int j=0;j<funList.size();j++){
			JSONObject json =new JSONObject();
			json.put("id", funList.get(j).getFunctionId().toString());
			json.put("text", funList.get(j).getFuntionName());
			json.put("url", funList.get(j).getUrl());
			json.put("pid", funList.get(j).getpId().toString());
			array.add(json);
		}
		return array;
	}
	
	
	
}
