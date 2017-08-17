package cn.sh.ideal.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.sh.ideal.controller.UserController;
import cn.sh.ideal.dao.IOperateLogDao;
import cn.sh.ideal.model.OperateLog;
import cn.sh.ideal.service.IOperateLogService;
import cn.sh.ideal.util.DateUtil;
@Service("operateLogService")
public class OperateLogServiceImpl implements IOperateLogService {
	private static Logger log = Logger.getLogger(OperateLogServiceImpl.class);
	@Resource
	private IOperateLogDao operateDao;
	
	@Override
	public void addLog(String moduleName,String funcName,String description, Object[] logDescriptionArgsObject, Map<String,String> param,String code){
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
		try {
			OperateLog log = new OperateLog(userId,userName,DateUtil.getDateTimeStr(new Date()), ip, moduleName,funcName,description_format_result, className,methodName,code);
			operateDao.insert(log);
		} catch (Exception e) {
			log.error("新增日志出现异常"+e.getMessage(),e);
		}
		
	}
	
}
