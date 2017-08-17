package cn.sh.ideal.dao;

import java.util.List;
import java.util.Map;

import cn.sh.ideal.model.CoachMain;
import cn.sh.ideal.model.UserInfo;

public interface ICoachMainDao {
	int deleteByPrimaryKey(Integer coachmainId);

	int insert(CoachMain record);

	int insertSelective(CoachMain record);

	CoachMain selectByPrimaryKey(Integer coachmainId);


    int updateByPrimaryKeySelective(CoachMain record); 



	int updateByPrimaryKey(CoachMain record);

	/**
	 * 查询辅导月计划列表信息
	 * */
	public List<CoachMain> queryCoachMain(CoachMain cm);

	/**
	 * 查询辅导月计划列表信息数量
	 * */
	public int queryCoachMainCount(CoachMain cm);

	/**
	 * 督导查询辅导月计划列表信息
	 * **/
	public List<CoachMain> queryCheckCoachMain(CoachMain coach);

	/**
	 * 督导查询辅导月计划列表信息数量
	 * */
	public int queryCheckCoachMainCount(CoachMain cm);

    public List<CoachMain> getcoachteam();
    
    
    /**
	 * 督导审批辅导计划
	 * **/
	public int  DuDaoCheckCoachMain(CoachMain cm);
	
	
	  /**
	 * 督导审批辅导总结
	 * **/
	public int  checkCoachSummary(CoachMain cm);
	
	void updatezjBycoachmainid(CoachMain coach);
    
}