package cn.sh.ideal.dao;

import java.util.List;

import cn.sh.ideal.model.OperateLog;

public interface IOperateLogDao {
    int deleteByPrimaryKey(Integer autoId);

	/**
	 * 插入日志
	 * @param SysLog
	 * @return
	 */
	public void insert(OperateLog record);

    int insertSelective(OperateLog record);

    OperateLog selectByPrimaryKey(Integer autoId);

    int updateByPrimaryKeySelective(OperateLog record);

    int updateByPrimaryKey(OperateLog record);
    
    List<OperateLog> listOperatelog(OperateLog record);
    int countOperatelog(OperateLog record);
    
    
    
    /******
     * 考试日志查询
     * @author niewenqiang
     * 2017-4-19
     * *****/
    public List<OperateLog> listKsOperatelog(OperateLog log);
    
    
    /******
     * 考试日志查询数量
     * @author niewenqiang
     * 2017-4-19
     * *****/
    public int listKsOperatelogCount(OperateLog log);
}