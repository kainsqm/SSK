package cn.sh.ideal.service.impl;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.IUserDao;
import cn.sh.ideal.model.AgentWorkPaper;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.redis.RedisClientTemplate;
import cn.sh.ideal.service.IUserService;
/******
 * 员工业务操作实现类
 * ****/
@Service("userService")
@SuppressWarnings("rawtypes")
public class UserServiceImpl implements IUserService {

	@Autowired
	public IUserDao userDao;
	 @Autowired
	 private RedisClientTemplate redisClientTemplate;
	/****
	 * 根据员工工号查询员工信息
	 * @author niewenqiang
	 * 2017-04-11
	 * ***/
	public UserInfo queryUserByWork(UserInfo info) {
		return userDao.queryUserByWork(info);
	}
    
	
	/*******
	 * 查询待处理的反馈单 话务员
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	@SuppressWarnings("rawtypes")
	public  List<AgentWorkPaper>  queryAgentWorkpaperByAgentUser(int userId){
		return userDao.queryAgentWorkpaperByAgentUser(userId);
	}
	
	
	/*******
	 * 查询待处理的反馈单  质检员
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	
	public  List<AgentWorkPaper>  queryAgentWorkpaperByQcUser(int userId){
		return userDao.queryAgentWorkpaperByQcUser(userId);
	}
    
	

	/*******
	 * 查询最近的公告信息
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	public List queryNotice(){
		return userDao.queryNotice();
	}
	
	
	/*****
	 * 获取redis存储的userID
	 * ******/
	public UserInfo getRedisUser(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String str = "";
		UserInfo info=null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("cookieToken")) {
				str = redisClientTemplate.get(cookie.getValue());
			}
		}
		if (null != str&&!"".equals(str)) {
			info=new UserInfo();
			JSONObject object = JSONObject.fromObject(str);
		    info.setUserId(Integer.parseInt(object.get("userId").toString()));
		    info.setUserName(object.getString("userName"));
		    info.setWorkId(object.getString("workId"));
		}
		return info;
	}
	
	
	/*****
	 * 获取当前token
	 * @author niewenqiang
	 * @date 2017-5-4
	 * ******/
	public String gettoken(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		String str = "";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("cookieToken")) {
				str =cookie.getValue();
			}
		}
		return str;
	}
	
	
	
	/*******
	 * 查询待处理的反馈单  受理员班长
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	
	public  List<AgentWorkPaper>  queryAgentWorkpaperByBZ(int userId){
		return userDao.queryAgentWorkpaperByBZ(userId);
	}

}
