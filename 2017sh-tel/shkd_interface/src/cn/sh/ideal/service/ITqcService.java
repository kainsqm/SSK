package cn.sh.ideal.service;

import java.util.List;

import cn.sh.ideal.model.TqcInfo;

public interface ITqcService {
	public List<TqcInfo> getTqcInfoList(String sdate);
}
