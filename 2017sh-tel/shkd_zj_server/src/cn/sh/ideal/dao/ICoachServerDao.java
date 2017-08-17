package cn.sh.ideal.dao;

import cn.sh.ideal.model.CoachServer;

public interface ICoachServerDao {
    int deleteByPrimaryKey(Integer sid);

    void  insertcoachserver(CoachServer record);

    int insertSelective(CoachServer record);

    CoachServer selectByPrimaryKey(Integer sid);

    int updateByPrimaryKeySelective(CoachServer record);

    int updateByPrimaryKey(CoachServer record);
    
    /**
	  * 根据辅导计划ID删除记录
	  * **/
	public int deleteCoachService(Integer coachmainId);
}