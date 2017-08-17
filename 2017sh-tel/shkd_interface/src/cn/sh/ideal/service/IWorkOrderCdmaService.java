package cn.sh.ideal.service;

import cn.sh.ideal.model.WorkOrderCdma;

public interface IWorkOrderCdmaService {
    int deleteByPrimaryKey(Integer serialCdma);

    int insert(WorkOrderCdma record);

    int insertSelective(WorkOrderCdma record);

    WorkOrderCdma selectByPrimaryKey(Integer serialCdma);

    int updateByPrimaryKeySelective(WorkOrderCdma record);

    int updateByPrimaryKey(WorkOrderCdma record);
}