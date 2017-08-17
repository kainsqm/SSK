/**
 * @author maxiaoni
 *
 */
package cn.sh.ideal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.ISysCodeDao;
import cn.sh.ideal.model.SysCode;
import cn.sh.ideal.service.ISysCodeService;

/**
 * syscode
 * 
 * **/

@Service("sysCodeService")
public class SysCodeServiceImpl implements ISysCodeService {
	@Resource
	private ISysCodeDao sysCodeDao;

	@Override
	public List<SysCode> selectSyscode(SysCode record) {
		return sysCodeDao.selectSyscode(record);
	}

	@Override
	public SysCode selectcodebyId(SysCode record) {
		return sysCodeDao.selectcodebyId(record);
	}

	/*****
	 * 根据传入的父节点标识查询该节点下的所有的子节点
	 * 
	 * @author niewenqiang
	 * @date 2017-4-24
	 * ******/
	public List<SysCode> queryChildrenByValue(String value) {
		return sysCodeDao.queryChildrenByValue(value);
	}
	
	
	  /*****
	    * 根据传入的父节点标识查询该节点下的所有的子节点
	    * @author niewenqiang
	    * @date 2017-5-10
	    * ******/
	   public List<SysCode> queryChildrenByFlag(String flag){
		   return sysCodeDao.queryChildrenByFlag(flag);
	   }

}