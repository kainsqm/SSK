package cn.sh.ideal.service;

import java.util.List;

import cn.sh.ideal.model.Function;

public interface IFunctionService{

	public List<Function> getFunction(Function fun);
	public List<Function> getFunctionByUserid(Integer userId);
	public List<Function> getChildFunByParam(Integer userId,Integer pId);
	//根据用户id获取用户具备的子功能列表
	public List<String> getsubFunctionByUserid(Integer userId);
}
