package cn.sh.ideal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import cn.sh.ideal.model.AgentWorkPaper;
import cn.sh.ideal.model.UserInfo;

/*****
 * 员工管理
 * @author niewenqiang
 * 2017-04-11
 * *****/
@SuppressWarnings("rawtypes")
public interface IUserDao {
	
	/****
	 * 根据员工工号查询员工信息
	 * @author niewenqiang
	 * 2017-04-11
	 * ***/
	public UserInfo queryUserByWork(UserInfo info);
	
	
	
	/*******
	 * 查询待处理的反馈单  坐席
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	
	public List<AgentWorkPaper> queryAgentWorkpaperByAgentUser(@Param("userId") int userId);
	
	
	/*******
	 * 查询待处理的反馈单  质检员
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	
	public  List<AgentWorkPaper>  queryAgentWorkpaperByQcUser(@Param("userId") int userId);
	

	/*******
	 * 查询最近的公告信息
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	public List queryNotice();
	
	
	/*******
	 * 查询待处理的反馈单  受理员班长
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	
	public  List<AgentWorkPaper>  queryAgentWorkpaperByBZ(@Param("userId") int userId);
	
	
}
