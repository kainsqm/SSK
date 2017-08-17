package cn.sh.ideal.service;

import java.util.List;
import java.util.Map;

import cn.sh.ideal.model.OperateLog;

public interface IOperateLogService {

	void addLog(String moduleName, String funcName, String message,
			Object[] objects, Map<String, String> param,String code);
}
