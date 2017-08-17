package cn.sh.ideal.service;

import java.util.List;

import cn.sh.ideal.model.WorkOrderCdmaSum;

public interface IWorkOrderCdmaSumService {
    int deleteByPrimaryKey(Integer cdmaAutoid);

    int insert(WorkOrderCdmaSum record);

    int insertSelective(WorkOrderCdmaSum record);
    
    /**
     * @author lk 2017/3/20
     * @param WorkOrderCdmaSum
     * @return
     * c网电话小结查询
     */
    List<WorkOrderCdmaSum> selectByPrimaryKey(WorkOrderCdmaSum serialCdma);
    int sumselectByPrimaryKey(WorkOrderCdmaSum serialCdma);
    
    int updateByPrimaryKeySelective(WorkOrderCdmaSum record);

    int updateByPrimaryKey(WorkOrderCdmaSum record);
    
    /**
     * @author lk 2017/4/12
     * @param cdmaAutoid
     * @return
     * 根据小结id查询小结信息
     */
    WorkOrderCdmaSum  getcwbyid(Integer cdmaAutoid);
    
    /**
     * @author lk 2017/4/17
     * @return
     * 查询c网小结类型
     */
    List<String> getcwxjtype();
    
    /**
     * @author lk 2017/4/17
     */
    List<String> getcwbstype();
}