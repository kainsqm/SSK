package cn.sh.ideal.dao;

import java.util.List;

import cn.sh.ideal.model.TqcInfo;

public interface TqcInfoDao {
    
    List<TqcInfo> getTqcInfoList(String sdate);
}