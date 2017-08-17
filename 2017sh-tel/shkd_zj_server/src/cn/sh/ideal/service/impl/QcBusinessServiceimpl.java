package cn.sh.ideal.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.IQcBusinessDao;
import cn.sh.ideal.model.AgentScore;
import cn.sh.ideal.model.QcBusiness;
import cn.sh.ideal.service.IQcBusinessService;
@Service("qcBusinessService")
public class QcBusinessServiceimpl implements IQcBusinessService {
	
	@Resource
	private IQcBusinessDao qcbusinessDao;
	
	@Override
	public List<QcBusiness> listqcbusiness(QcBusiness qcbus) {
		return qcbusinessDao.listqcbusiness(qcbus);
	}

	@Override
	public int selectQcBusinesscount(QcBusiness qcbus) {
		return qcbusinessDao.selectQcBusinesscount(qcbus);
	}

	@Override
	public List<LinkedHashMap<Object, Object>> exportselectQcBusiness(
			QcBusiness qcbus) {
		return qcbusinessDao.exportselectQcBusiness(qcbus);
	}
	

	

}
