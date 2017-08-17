package cn.sh.ideal.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.sh.ideal.model.vSysuser;

public interface UserSerivce {
	vSysuser getUserByToken(HttpServletRequest request,HttpServletResponse response);
	
	String[] getUserRole(String token);
}
