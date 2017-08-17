package cn.sh.ideal.service;

import java.util.List;

import cn.sh.ideal.model.QCHistory;

public interface IQCHistoryService {
  
    /**
     * 查询历史评分记录
     * */
    List<QCHistory> selectQcHisByParam(QCHistory qcHistory);
    
    /**
     * 查询历史评分记录总数
     * */
    int  selectQcHisCountByParam(QCHistory qcHistory);
    
    /**
     * @author lk 2017/410
     * 查询无工单无录音的评分记录
     */
    
    List<QCHistory> noneQclist(QCHistory qcHistory);
    /**
     * @author lk 2017/410
     * 查询无工单无录音的评分记录数量
     * 
     */
    int noneQclistsum(QCHistory qcHistory);
    
    /**
     * @author lk 2017/4/13
     * 查询112工单质检记录
     * @param qcHistory
     * @return
     */
    List<QCHistory> getQcHisByorder(QCHistory qcHistory);
    /**
     * @author lk 2017/4/13
     * 查询112工单质检记录数量
     * @param qcHistory
     * @return
     */
    int getQcHisByordersum(QCHistory qcHistory);
    
    /**
     * @author lk 2017/4/13
     * 查询c网工单质检记录
     * @param qcHistory
     * @return
     */
    List<QCHistory> getcwQcHisByorder(QCHistory qcHistory);
    /**
     * @author lk 2017/4/13
     * 查询c网工单质检记录数量
     * @param qcHistory
     * @return
     */
    int getcwQcHisByordersum(QCHistory qcHistory);
    
    /**
     * @author lk 2017/4/14
     * @param qcHistory
     * @return
     * 查询112电话小结质检记录
     */
    List<QCHistory> get112xjQcHisByorder(QCHistory qcHistory);
    
    /**
     * @author lk 2017/4/14
     * @param qcHistory
     * @return
     * 查询112电话小结质检记录数量
     */
    int get112xjQcHisByordersum(QCHistory qcHistory);
    
    /**
     * @author lk 2017/4/14
     * @param qcHistory
     * @return
     * 查询c网电话小结质检记录
     */
    List<QCHistory> getcwxjQcHisByorder(QCHistory qcHistory);
    
    /**
     * @author lk 2017/4/14
     * @param qcHistory
     * @return
     * 查询c网电话小结质检记录数量
     */
    int getcwxjQcHisByordersum(QCHistory qcHistory);
}