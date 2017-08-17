package cn.sh.ideal.dao;

import cn.sh.ideal.model.tExamdErrorMsg;

public interface ItExamdErrorMsgDao {
    int insert(tExamdErrorMsg record);

    int insertSelective(tExamdErrorMsg record);
}