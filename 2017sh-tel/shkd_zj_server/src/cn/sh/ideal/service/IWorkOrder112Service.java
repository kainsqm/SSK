package cn.sh.ideal.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sh.ideal.model.Modeltype;
import cn.sh.ideal.model.OrderType;
import cn.sh.ideal.model.RecordInfo;
import cn.sh.ideal.model.WorkOrder112;
import cn.sh.ideal.model.WorkTelSum112;
public interface IWorkOrder112Service {
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
     public String getRecordFileUrl(String filename,String xmlPath);
     
     /***
      * 插入112工单小结
      * **/
     
     int insert112sum(WorkTelSum112 record);
     
     /***
      * 获取申告大类
      * **/
     
     List<String> getDeclarationBigType();
     
     /**
      * 获取获取结案方式
      * */
     List<String> getCloseedWay();
     
     /**
      * 获取受理来源
      * */
     List<String> getAcceptedSource();
     
     /**
      * 获取派修方向
      * */
     List<String> getrRepairDirection();
     
     /**
      * 获取三级故障大类
      * */
     List<String> getThreeErrorType();
     
     /**
      * @author lk 2017/4/12  
      * @param autoId
      * @return
      *根据112小结id获取小结信息
      */
     WorkTelSum112   get112jbyid(Integer autoId); 
       
       /**
        * @author lk 2017/4/17
        * @return
        * 获取112小结类型
        */
     List<String> get112xjtype();
       
       /**
        * @author lk 2017/4/17
        * @return
        * 获取112小结业务类型 
        */
     List<String> getxjbstype(); 
     
     /**
      * @author lk 2017/4/27
      * @return
      * 获取112工单区局
      */
     List<Modeltype> getqjtype();
     /**
      * @author lk 2017/4/27
      * @return
      * 获取112工单申告现象
      */
     List<Modeltype> getsgxxtype();
     /**
      * @author lk 2017/4/27
      * @return
      * 获取112工单测试代码
      */
     List<Modeltype> getcsdmtype();
     /**
      * @author lk 2017/4/27
      * @return
      * 获取112工单申告备注
      */
     List<Modeltype> getsgbztype();
     /**
      * @author lk 2017/4/27
      * @return
      * 获取112工单申告联系信息
      */
     List<Modeltype> getlxinfotype();
     
     /**
      * @author lk 2017/4/27
      * @return
      * 获取112工单故障修复代码
      */
     List<Modeltype> getgzxftype();
     /***
      * 获取申告大类
      * **/
     
     List<Modeltype> getDeclarationBigType2();
     
     /**
      * 获取结案方式
      * */
     List<Modeltype> getCloseedWay2();
     
     /**
      * 获取受理来源
      * */
     List<Modeltype> getAcceptedSource2();
     
     /**
      * 获取派修方向
      * */
     List<Modeltype> getrRepairDirection2();
     
     /**
      * 获取三级故障大类
      * */
     List<Modeltype> getThreeErrorType2();
     /**
      * 查询112小结业务类型
      * @return
      */
     List<Modeltype> getBusinessType();
     
     /**
      * 查询112小结小结类型
      * @return
      */
     List<Modeltype> getTelsumType();
     
     /**
      * 查询112小结故障来源
      * @return
      */
     List<Modeltype> getErrorSource();
     
     /**
      * 查询112任务工单 2017/5/10
      */
     List<WorkOrder112> selecttaskWorkOrder112(Map<String,Object> map);
     
     /**
      * 一键领取112工单任务
      * @param map
      * @return
      */
     Map<String,String> updalllinqu112order(Map<String, Object> map);
     
     /**
      * 单条领取录音
      */
     String  updlinqu112order(Map<String,Object> map);
     
     /**
      * @author lk 2017/5/16
      * @param orderlist
      * @return
      * 获取已领112工单任务列表
      */
     List<WorkOrder112> getyl112ordertaskList(Set<String> orderlist);
     
     int getyl112ordertaskListcount(Set<String> orderlist);
}