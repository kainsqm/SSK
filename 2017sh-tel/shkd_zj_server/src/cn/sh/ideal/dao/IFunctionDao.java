package cn.sh.ideal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sh.ideal.model.Function;


public interface IFunctionDao {
	public List<Function> getFunction(Function fun);
	
	public List<Function> getFunctionByUserid(Integer userId);
	public List<Function> getChildFunByParam(@Param("userId")Integer userId,@Param("pId")Integer pId);
	//根据用户id获取用户具备的子功能列表
	public List<String> getsubFunctionByUserid(Integer userId);
	
}
