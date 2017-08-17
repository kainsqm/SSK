package cn.sh.ideal.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.sh.ideal.model.AcceptQualityCheck;
import cn.sh.ideal.model.AcceptQualityCheckInfo;
import cn.sh.ideal.model.MonitorWorkload;
import cn.sh.ideal.model.QCDetail;

public interface IQCDetailDao {

    int insert(QCDetail record);

    int selectByQcid(Integer qcId);
    
    QCDetail selectDetailByQcid(Integer qcId);
    
    QCDetail qcDetailByQcid(Integer qcId);
    
    /**
     * @author lk 2017/4/14
     * @param qcId
     * @return Integer
     * 删除质检相关信息
     */
    int delqcdetailbyQcid(Integer qcId);
    
    /**
     * 质检员监听工作量报表清单统计 
     * **/
    public List<MonitorWorkload> MonitorWorkload(Map<String, String> map);
    
    
    /**
     * 质检员监听工作量报表清单统计  历史数据
     * **/
    public List<MonitorWorkload> MonitorWorkloadOld(Map<String, String> map);
    
    
    /***
     * 受理质量检查报表及详单 Tl9000
     * 
     * **/
    public List<AcceptQualityCheck> AcceptQualityCheckTl(Map<String, String> map);
    
    
    /***
     * 受理质量检查报表及详单 Tl9000 历史数据
     * 
     * **/
    public List<AcceptQualityCheck> AcceptQualityCheckTlOld(Map<String, String> map);
    
    /***
     * 受理质量检查报表及详单 本中心
     * 
     * **/
    public List<AcceptQualityCheck> AcceptQualityCheck(Map<String, String> map);
    
    
    /***
     * 受理质量检查报表及详单 本中心 历史数据
     * 
     * **/
    public List<AcceptQualityCheck> AcceptQualityCheckOld(Map<String, String> map);
    
    
    
    /**
     * 质检员监听工作量报表清单统计 导出
     * **/
    public List<LinkedHashMap<Object, Object>> MonitorWorkloadExport(Map<String, String> map);
    
    /**
     * 质检员监听工作量报表清单统计 历史数据  导出
     * **/
    public List<LinkedHashMap<Object, Object>> MonitorWorkloadOldExport(Map<String, String> map);
    
    
    /***
     * 受理质量检查报表及详单 Tl9000 导出
     * 
     * **/
    public List<LinkedHashMap<Object, Object>> AcceptQualityCheckTlExport(Map<String, String> map);
    
    
    /***
     * 受理质量检查报表及详单 Tl9000 历史数据导出
     * 
     * **/
    public List<LinkedHashMap<Object, Object>> AcceptQualityCheckTlOldExport(Map<String, String> map);
    
    /***
     * 受理质量检查报表及详单 本中心导出
     * 
     * **/
    public List<LinkedHashMap<Object, Object>> AcceptQualityCheckExport(Map<String, String> map);
    
    
    /***
     * 受理质量检查报表及详单 本中心 历史数据导出
     * 
     * **/
    public List<LinkedHashMap<Object, Object>> AcceptQualityCheckOldExport(Map<String, String> map);
    
    /**
     * 受理质量检查报表及详单详情
     * @param map
     * @return
     */
    public List<AcceptQualityCheckInfo> getAcceptQualityCheckInfo(Map<String,String> map);
    
    /**
     * 受理质量检查报表及详单详情导出
     */
    public  List<LinkedHashMap<Object, Object>>  exportAcceptQualityCheckInfo(Map<String,String> map);
}