package cn.sh.ideal.dao;

import cn.sh.ideal.model.WorkOrderCdmaSum;

public interface IWorkOrderCdmaSumDao {
    int deleteByPrimaryKey(Integer cdmaAutoid);

    int insert(WorkOrderCdmaSum record);

    int insertSelective(WorkOrderCdmaSum record);

    WorkOrderCdmaSum selectByPrimaryKey(Integer cdmaAutoid);

    int updateByPrimaryKeySelective(WorkOrderCdmaSum record);

    int updateByPrimaryKey(WorkOrderCdmaSum record);
}