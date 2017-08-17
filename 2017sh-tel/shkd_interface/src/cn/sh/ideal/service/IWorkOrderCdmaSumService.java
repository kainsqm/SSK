package cn.sh.ideal.service;

import cn.sh.ideal.model.WorkOrderCdmaSum;

public interface IWorkOrderCdmaSumService {
    int deleteByPrimaryKey(Integer cdmaAutoid);

    int insert(WorkOrderCdmaSum record);

    int insertSelective(WorkOrderCdmaSum record);

    WorkOrderCdmaSum selectByPrimaryKey(Integer cdmaAutoid);

    int updateByPrimaryKeySelective(WorkOrderCdmaSum record);

    int updateByPrimaryKey(WorkOrderCdmaSum record);
}