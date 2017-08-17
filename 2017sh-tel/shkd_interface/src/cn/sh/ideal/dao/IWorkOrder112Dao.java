package cn.sh.ideal.dao;

import java.util.List;
import java.util.Map;

import cn.sh.ideal.model.RecordInfo;
import cn.sh.ideal.model.WorkOrder112;
import cn.sh.ideal.model.WorkTelSum112;

public interface IWorkOrder112Dao {
    int deleteByPrimaryKey(Integer workorderId);

    int insert(WorkOrder112 workorder);

    int insertSelective(WorkOrder112 workorder);

    WorkOrder112 selectByPrimaryKey(Integer workorderId);
    
    List<WorkOrder112> selectWorkOrder112(WorkOrder112 workorder);
    
    int selectCount(WorkOrder112 workorder);
    
    /**
     * 查询112工单
     * @author niewenqiang
     * **/
    List<WorkOrder112> selectOrder112(Map<String, String> mapdata);
    
    
    /**
     * 查询112工单数量
     * @author niewenqiang
     * **/
    int selectOrder112Count(Map<String, String> mapdata);
    
    
    /**
     * 查询112工单电话小结
     * @author niewenqiang
     * **/
    List<WorkTelSum112> select112Xj(WorkTelSum112 worksum112);
    
    
    /**
     * 查询112工单电话小结数量
     * @author niewenqiang
     * **/
    int select112XjCount(WorkTelSum112 worksum112);
    
    
    
    /***
     * 查询未关联的录音信息
     * **/
    List<RecordInfo> getNoGLRecord(RecordInfo recordinfo);
    
    
    /***
     * 查询未关联的录音信息数量
     * **/
     int getNoGLRecordCount(RecordInfo recordinfo);
     

     /***
      * 获取录音播放地址
      * **/
     public String getRecordFileUrl(Map<String, String> mapdata);
     
     /***
      * 插入112工单小结
      * **/
     
     int insert112sum(WorkTelSum112 record);
     
     /***
      * 获取申告大类
      * **/
     
     List<String> getDeclarationBigType();
}