
/**
 * @author maxiaoni
 *
 */
package cn.sh.ideal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.ISecondaryQcDao;
import cn.sh.ideal.model.SecondaryQc;
import cn.sh.ideal.service.ISecondaryQcService;
/**
 * 二级质检
 * 
 * **/


@Service("secondaryQcService")
public class SecondaryQCServiceImpl implements ISecondaryQcService {
	@Resource
	private ISecondaryQcDao secondaryQcDao;

	@Override
	public int insert(SecondaryQc record) {
		return secondaryQcDao.insert(record);
	}

	@Override
	public List<SecondaryQc> selectByParam(SecondaryQc record) {
		return secondaryQcDao.selectByParam(record);
	}

	@Override
	public int selectCountByParam(SecondaryQc record) {
		return secondaryQcDao.selectCountByParam(record);
	}


	@Override
	public int updateByPrimaryKey(SecondaryQc record) {
		return secondaryQcDao.updateByPrimaryKey(record);
	}

	@Override
	public int seccountbyqcId(int qc_id) {
		return secondaryQcDao.seccountbyqcId(qc_id);
	}
   

}