package cn.sh.ideal.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.sh.ideal.model.OperateLog;

public interface IOperateLogService {

	List<OperateLog> listoperatelog(OperateLog operatelog);

	int countOperatelog(OperateLog record);

	void addLog(String moduleName, String funcName, String message,
			Object[] objects, Map<String, String> param,String code) throws SQLException;
	
	
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
