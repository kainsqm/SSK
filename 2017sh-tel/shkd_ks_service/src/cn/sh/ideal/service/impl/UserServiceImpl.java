package cn.sh.ideal.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.sh.ideal.controller.ExamController;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.UserSerivce;
import cn.sh.ideal.util.ConfigProperties;
import cn.sh.ideal.util.DES3Util;
import cn.sh.ideal.util.HttpUtil;

@Service
public class UserServiceImpl implements UserSerivce {
	private static Logger log = Logger.getLogger(ExamController.class);
	@Value("#{config['SSO_BASE_URL']}")
	private String SSO_BASE_URL;
	@Value("#{config['SSO_USER_TOKEN_SERVICE']}")
	private String SSO_USER_TOKEN_SERVICE;

	@Override
	public vSysuser getUserByToken(HttpServletRequest request,
			HttpServletResponse response) {
		// 取token
		String token="";
		try {
			token = DES3Util.decryptStringURLDecoder(request.getParameter("token"));
			token = DES3Util.decrypt(request.getParameter("token"));
		} catch (UnsupportedEncodingException e) {
			log.error("解析URL参数token出现异常:"+e.getMessage(),e);
		}
		// 判断token是否有值
		if (StringUtils.isBlank(token)) {
			return null;
		}
	//	String token="632304699ab448c6aad8033d0cf21c49";

		Map<String, String> map=new HashMap<String, String>();
		map.put("token", token);
		// 调用sso的服务查询用户信息
		String json=HttpUtil.doGet(ConfigProperties.getProperty("SSO_BASE_URL")+ConfigProperties.getProperty("SSO_USER_TOKEN_SERVICE")+token);

		if (StringUtils.isBlank(json)) {
			return null;
		}
		// 把json转换成java对象
		JSONObject object = JSONObject.fromObject(json);
		@SuppressWarnings("static-access")
		vSysuser vSysuser = (cn.sh.ideal.model.vSysuser) object.toBean(object,
				vSysuser.class);
		if (null == vSysuser) {
			return null;
		}
		vSysuser.setToken(token);
		// 取用户对象
		return vSysuser;
	}

	@Override
	public String[] getUserRole(String token) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("token", token);
		//String json = HttpClientUtil.dtoGet("http://172.31.21.13:8081/shkd_tydl_service/controller/redis/getRedis.do?token=",map);
		String json=HttpUtil.doGet(ConfigProperties.getProperty("SSO_BASE_URL")+ConfigProperties.getProperty("SSO_USER_TOKEN_SERVICE")+token);
		if (StringUtils.isBlank(json)) {
			return null;
		}
		// 把json转换成java对象
		JSONObject object = JSONObject.fromObject(json);
		String role[]=null;
		@SuppressWarnings("static-access")
		vSysuser vSysuser = (cn.sh.ideal.model.vSysuser) object.toBean(object,
				vSysuser.class);
		if (null != vSysuser) {
			role=vSysuser.getListFun().split(",");
		}
		return role;
	}
}
