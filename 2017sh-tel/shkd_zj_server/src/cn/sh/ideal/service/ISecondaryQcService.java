package cn.sh.ideal.service;

import java.util.List;

import cn.sh.ideal.model.SecondaryQc;

public interface ISecondaryQcService {

    int insert(SecondaryQc record);

    List<SecondaryQc> selectByParam(SecondaryQc record);
    
    int selectCountByParam(SecondaryQc record);

    int updateByPrimaryKey(SecondaryQc record);
    
    /**
     * @author lk 2017/4/19
     * @param qc_id
     * @return
     * 查询二级质检是否存在
     */
    int seccountbyqcId(int qc_id);
}