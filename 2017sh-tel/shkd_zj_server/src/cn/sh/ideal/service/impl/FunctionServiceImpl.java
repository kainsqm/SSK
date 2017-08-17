/**
 * 
 */
/**
 * @author Administrator
 *
 */
package cn.sh.ideal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.IFunctionDao;
import cn.sh.ideal.model.Function;
import cn.sh.ideal.service.IFunctionService;

@Service("functionService")
public class FunctionServiceImpl implements IFunctionService {
	@Resource
	private IFunctionDao functionDao;

	@Override
	public List<Function> getFunction(Function fun) {
		return functionDao.getFunction(fun);
	}

	@Override
	public List<Function> getFunctionByUserid(Integer userId) {
		return functionDao.getFunctionByUserid(userId);
	}

	@Override
	public List<Function> getChildFunByParam(Integer userId, Integer pId) {
		return functionDao.getChildFunByParam(userId, pId);
	}

	@Override
	public List<String> getsubFunctionByUserid(Integer userId) {
		return functionDao.getsubFunctionByUserid(userId);
	}
	
}