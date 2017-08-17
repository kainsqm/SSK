package cn.sh.ideal.service.impl;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.IOperateLogDao;
import cn.sh.ideal.model.OperateLog;
import cn.sh.ideal.service.IOperateLogService;
import cn.sh.ideal.util.DateUtil;
@Service("operateLogService")
public class IOperateLogServiceimpl implements IOperateLogService {
	@Resource
	private IOperateLogDao operateDao;
	
	@Override
	public List<OperateLog> listoperatelog(OperateLog operatelog) {
		
		return operateDao.listOperatelog(operatelog);
	}

	@Override
	public int countOperatelog(OperateLog record) {
		return operateDao.countOperatelog(record);
	}
	@Override
	public void addLog(String moduleName,String funcName,String description, Object[] logDescriptionArgsObject, Map<String,String> param,String code) throws SQLException {
		String description_format_result = null;
		MessageFormat mFormat = null;
		if(null == logDescriptionArgsObject || logDescriptionArgsObject.length == 0){//没有传参数
			description_format_result = description;
		}else{//格式化description内容（填充参数value）
			mFormat = new MessageFormat(description);
			description_format_result = mFormat.format(logDescriptionArgsObject);
		}
		if (!StringUtils.isNotBlank(description_format_result)) {
			return;
		}
		Integer userId = null;
		String userName="";
		String ip="";
		String className="";
		String methodName="";
		if(param!=null){
			if(param.get("userid")!=null){
				userId=	Integer.parseInt(param.get("userid"));
			}
			 userName = param.get("username");
			 ip = param.get("ip");
			 className = param.get("class");
			 methodName= param.get("method");
		}
		
		//保存log
		OperateLog log = new OperateLog(userId,userName,DateUtil.getDateTimeStr(new Date()), ip, moduleName,funcName,description_format_result, className,methodName,code);
		operateDao.insert(log);
	}

	@Override
	public List<OperateLog> listKsOperatelog(OperateLog log) {
		return operateDao.listKsOperatelog(log);
	}

	@Override
	public int listKsOperatelogCount(OperateLog log) {
		return operateDao.listKsOperatelogCount(log);
	}
}
