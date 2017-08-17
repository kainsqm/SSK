package cn.sh.ideal.dao;

import cn.sh.ideal.model.WorkOrderCdma;

public interface IWorkOrderCdmaDao {
    int deleteByPrimaryKey(Integer serialCdma);

    int insert(WorkOrderCdma record);

    int insertSelective(WorkOrderCdma record);

    WorkOrderCdma selectByPrimaryKey(Integer serialCdma);

    int updateByPrimaryKeySelective(WorkOrderCdma record);

    int updateByPrimaryKey(WorkOrderCdma record);
}