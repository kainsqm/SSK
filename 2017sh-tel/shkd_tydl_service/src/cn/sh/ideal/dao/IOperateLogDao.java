package cn.sh.ideal.dao;


import cn.sh.ideal.model.OperateLog;

public interface IOperateLogDao {
	/**
	 * 插入日志
	 * @param SysLog
	 * @return
	 */
	public void insert(OperateLog record);

 
}