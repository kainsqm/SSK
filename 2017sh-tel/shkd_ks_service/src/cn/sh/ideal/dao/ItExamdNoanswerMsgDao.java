package cn.sh.ideal.dao;

import java.util.Map;

import cn.sh.ideal.model.tExamdNoanswerMsg;

public interface ItExamdNoanswerMsgDao {
    int insert(tExamdNoanswerMsg record);

    int insertSelective(tExamdNoanswerMsg record);
    
    /******
     * 根据时间删除记录
     * @author niewenqiang
     * @date 2017-4-28
     * ******/
    public void deleteByDate(Map<String, String> map);
}