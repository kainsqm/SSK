package cn.sh.ideal.service;

import java.util.List;
import java.util.Map;

import cn.sh.ideal.model.OperateLog;

public interface IOperateLogService {

	List<OperateLog> listoperatelog(OperateLog operatelog);

	int countOperatelog(OperateLog record);

	void addLog(String moduleName, String funcName, String message,
			Object[] objects, Map<String, String> param);
}
