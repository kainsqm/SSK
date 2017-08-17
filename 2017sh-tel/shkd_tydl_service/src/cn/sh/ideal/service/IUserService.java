package cn.sh.ideal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import cn.sh.ideal.model.AgentWorkPaper;
import cn.sh.ideal.model.SysRole;
import cn.sh.ideal.model.UserInfo;
/****
 * 员工操作业务类
 * ****/

public interface IUserService {
	/****
	 * 根据员工工号查询员工信息
	 * @author niewenqiang
	 * 2017-04-11
	 * ***/
	public UserInfo queryUserByWork(UserInfo info);
	
	
	
	/*******
	 * 查询待处理的反馈单
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	public  List<AgentWorkPaper>  queryAgentWorkpaperByAgentUser(int userId);
	
	
	/*******
	 * 查询待处理的反馈单  质检员
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	
	public  List<AgentWorkPaper>  queryAgentWorkpaperByQcUser(int userId);
	
	
	/*******
	 * 查询最近的公告信息
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	public List queryNotice();
	
	
	/*****
	 * 获取redis存储的userID
	 * ******/
	public UserInfo getRedisUser(HttpServletRequest request) ;
	
	
	/*****
	 * 获取当前token
	 * ******/
	public String gettoken(HttpServletRequest request) ;
	
	/*******
	 * 查询待处理的反馈单  受理员班长
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	
	public  List<AgentWorkPaper>  queryAgentWorkpaperByBZ(int userId);
	
}
