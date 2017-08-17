package cn.sh.ideal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sh.ideal.annotation.Log;
import cn.sh.ideal.intercept.LogDescriptionArgsObject;
import cn.sh.ideal.model.Function;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.service.IFunctionService;
import cn.sh.ideal.service.IUserService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.LogUitls;
import cn.sh.ideal.util.RedisUtil;

@Controller
@RequestMapping("/controller/fun")
public class FunctionController {
	private static Logger log = Logger.getLogger(FunctionController.class);
	@Resource
	private IFunctionService functionService;
	@Autowired
	private IUserService userService;

	@SuppressWarnings("unused")
	@RequestMapping(value = "/getFunList", method = RequestMethod.POST)
	public void getFunList(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String code = request.getParameter("syscode");
		
	
		UserInfo user = userService.getRedisUser(request);
		int userId=0;
		try {
		if (user == null) {
			JSONObject json = new JSONObject();
			json.put("timeout", "timeout");
			response.getWriter().print(json);
		} else {
			userId= user.getUserId();
			Map<String, String> map = new HashMap<String, String>();
			JSONArray array = new JSONArray();
			map.put("userId", String.valueOf(userId));
			map.put("code", code);
			List<Function> funList = functionService.getFunctionByUserid(map);
			for (int i = 0; i < funList.size(); i++) {
				JSONObject json = new JSONObject();
				json.put("id", funList.get(i).getFunctionId().toString());
				json.put("text", funList.get(i).getFuntionName());
				json.put("imgUrl", funList.get(i).getImgUrl());
				json.put("childItems",
						getSubList(funList.get(i).getFunctionId(), userId));
				array.add(json);
			}

				response.getWriter().print(array);
				LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
						.setObjects(new Object[] { "操作成功" }));
			
		}
		} catch (IOException e) {
			log.error("用户" + userId + "获取功能列表异常:" + e.getMessage(), e);
			LogUitls.setArgs(LogDescriptionArgsObject.newInstant()
					.setObjects(new Object[] { e.getMessage() }));
		}
	}

	public JSONArray getSubList(int id, int userId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		JSONArray array = new JSONArray();
		map.put("userId", userId);
		map.put("pId", id);
		List<Function> funList = functionService.getChildFunByParam(map);
		for (int j = 0; j < funList.size(); j++) {
			JSONObject json = new JSONObject();
			json.put("id", funList.get(j).getFunctionId().toString());
			json.put("text", funList.get(j).getFuntionName());
			json.put("url", funList.get(j).getUrl());
			json.put("remark", funList.get(j).getRemark());
			json.put("pid", funList.get(j).getpId().toString());
			array.add(json);
		}
		return array;
	}

}
