package cn.sh.ideal.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.sh.ideal.model.CoachInfo;
import cn.sh.ideal.model.CoachMain;
import cn.sh.ideal.model.CoachServer;
import cn.sh.ideal.util.SearchResultBean;

public interface ICoachService {
	/**
	 * 查询辅导月计划列表信息
	 * **/
	public List<CoachMain> queryCoachMain(CoachMain coach);

	/**
	 * 查询辅导月计划列表信息数量
	 * */
	public int queryCoachMainCount(CoachMain cm);

	/**
	 * 通过查询下拉辅导状态菜单获取辅导总结状态
	 * 
	 * @param selPass
	 *            下拉选择项值
	 * @return
	 */
	public String getSummarizeState(String selPass);

	/**
	 * 通过查询下拉辅导状态菜单获取辅导月计划状态
	 * 
	 * @param selPass
	 *            下拉选择项值
	 * @return
	 */
	public String getPassState(String selPass);

	public SearchResultBean queryUserList(Map<String, String> mapdata);

	public boolean insert(CoachMain record);

	boolean insertcoachserver(CoachServer record);

	/**
	 * 督导查询辅导月计划列表信息
	 * **/
	public List<CoachMain> queryCheckCoachMain(CoachMain coach);

	/**
	 * 督导查询辅导月计划列表信息数量
	 * */
	public int queryCheckCoachMainCount(CoachMain cm);
	
	/***
	 * 根据ID查询月计划详情
	 * **/
	public CoachMain selectByKeyCoachMain(Integer coachmainId);
	
	
	/***
	 * 根据月计划查询周计划详情
	 * **/
	public List<CoachInfo> selectByKeyCoachInfo(Integer coachmainId);
	
	
	/***
	 * 根据月计划查询周计划详情
	 * **/
	public int selectByKeyCoachInfoCount(Integer coachmainId);
	
	/***
	 * 根据月计划ID查询辅导服务细项指标
	 * **/
	public CoachServer selectByKeyCoachServer(Integer coachmainId);
	
	CoachMain selectByPrimaryKey(Integer coachmainId);
	
	CoachServer selectCoachServerByid(Integer coachmainId);
	
	 public List<CoachMain> getcoachteam();

	 boolean updateCoachByid(CoachMain record,CoachServer coach)throws SQLException;
	 boolean insertCoachInfo(CoachInfo coachinfo);

	 /**
	  * 删除辅导计划
	  * **/
	int deleteCoachMain(Integer coachmainId);
	
	/**
	 * 督导审批辅导计划
	 * **/
	int  updateDuDaoCheckCoachMain(CoachMain cm);
	
	 /**
	 * 督导审批辅导总结
	 * **/
	public int  updateCoachSummary(CoachMain cm);
	
	boolean deleteCoachInfo(Integer id);

	CoachInfo selecCoachInfotByid(Integer id);
	
	boolean updatezjBycoachmainid(CoachMain coach);


    /***
     * 根据辅导项目ID  查询辅导项目名称，并且拼接成一个字符串
     * **/
	public String getCoachProjectById(Map<String, String> mapdata);
	
	public boolean updcoachInfo(CoachInfo coac);
	
	List<CoachInfo> selectByinfo(CoachInfo coac);
	
	int selectByinfoCount(CoachInfo coac);
}
