package cn.sh.ideal.dao;

import java.util.List;
import java.util.Map;

import cn.sh.ideal.model.CoachInfo;

public interface ICoachInfoDao {
    int deleteByPrimaryKey(Integer id);

    void insert(CoachInfo record);

    int insertSelective(CoachInfo record);

    CoachInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CoachInfo record);

    int updateByPrimaryKey(CoachInfo record);
    
    List<CoachInfo> selectByCoachMain(Integer coachMainId);
    
    int selectByCoachMainCount(Integer coachMainId);
    
    /**
	  * 根据辅导计划ID删除记录
	  * **/
	public int deleteCoachInfo(Integer coachmainId);
	
	public void delCoachInfo(Integer id);
	
	
	 /***
     * 根据辅导项目ID  查询辅导项目名称，并且拼接成一个字符串
     * **/
	public String getCoachProjectById(Map<String, String> mapdata);
	
	List<CoachInfo> selectByinfo(CoachInfo record);
	
	int selectByinfoCount(CoachInfo record);
	
}