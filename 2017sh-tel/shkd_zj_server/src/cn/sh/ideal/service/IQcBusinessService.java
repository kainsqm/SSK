package cn.sh.ideal.service;

import java.util.LinkedHashMap;
import java.util.List;

import cn.sh.ideal.model.QcBusiness;

public interface IQcBusinessService {
	List<QcBusiness> listqcbusiness(QcBusiness qcbus);
	int selectQcBusinesscount(QcBusiness qcbus);
	public List<LinkedHashMap<Object, Object>> exportselectQcBusiness(QcBusiness qcbus);
			
}
