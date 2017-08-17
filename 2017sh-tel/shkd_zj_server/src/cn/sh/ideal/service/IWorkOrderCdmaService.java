package cn.sh.ideal.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.sun.org.glassfish.gmbal.ParameterNames;

import cn.sh.ideal.model.Modeltype;
import cn.sh.ideal.model.WorkOrderCdma;

public interface IWorkOrderCdmaService {
    int deleteByPrimaryKey(Integer serialCdma);

    int insert(WorkOrderCdma record);

    int insertSelective(WorkOrderCdma record);
    /**
     * @author lk 2017/03/16
     * @param record
     * @return WorkOrderCdma
     * 	查询c网工单列表
     */
    List<WorkOrderCdma> selectByPrimaryKey(WorkOrderCdma record);
    /**
     * @author lk 2017/03/16
     * @param record
     * @return int
     * 	查询c网工单列表数量
     */
    int countselectByPrimaryKey(WorkOrderCdma record);
    /**
     * @author lk 2017/04/10
     * @param serialCdma
     * @return
     */
    WorkOrderCdma getOrderCdma(Integer serialCdma);

    int updateByPrimaryKeySelective(WorkOrderCdma record);

    int updateByPrimaryKey(WorkOrderCdma record);
    
    /**
     * @author lk 2017/04/18
     * @return
     * 查询c网业务申告大类
     */
    List<String> getbstype();
    
    List<Modeltype> getywdltype();
    
    List<Modeltype> getywxltype();
    
    List<Modeltype> getsgxxtype();
    
    List<Modeltype> getclfstype();
    
    List<Modeltype> getjadmtype();
    
    List<Modeltype> getejsgxxtype();
    
    List<Modeltype> getzejtype();
    
    List<Modeltype> getycltype();
    
    /**
     * @author lk 2017/5/27
     * @param map
     * @return
     * 查询c网符合条件的任务工单
     */
    public List<WorkOrderCdma> selecttaskWorkOrdercw(Map<String,Object> map);
    
    /**
     * 单条领取录音
     */
    String  updlinqucworder(Map<String,Object> map);
    
    /**
     * 一键领取c网工单任务
     * @param map
     * @return
     */
    Map<String,String> updalllinqucworder(Map<String,Object> map);
    
    /**
     * @author lk 2017/5/31
     * @param map
     * @return
     * 查询已领c网工单
     */
    List<WorkOrderCdma> getylcwordertaskList(Set<String> orderlist);
    
    int getylcwordertaskListcount(Set<String> orderlist);
}