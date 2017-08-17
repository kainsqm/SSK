package cn.sh.ideal.dao;

import java.util.LinkedHashMap;
import java.util.List;

import cn.sh.ideal.model.QcBusiness;

public interface IQcBusinessDao {
				List<QcBusiness> listqcbusiness(QcBusiness qcbus);
				int selectQcBusinesscount(QcBusiness qcbus);
				List<LinkedHashMap<Object, Object>> exportselectQcBusiness(
						QcBusiness qcbus);
				
}
