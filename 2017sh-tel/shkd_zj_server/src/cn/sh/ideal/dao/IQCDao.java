package cn.sh.ideal.dao;

import cn.sh.ideal.model.QC;

public interface IQCDao {
	public int deleteByRecordid(Integer recordId);

    int insert(QC record);

    QC selectByRecordId(Integer recordId);

    int updateByPrimaryKey(QC record);
    /**
     * 根据工单ID查询关联的评分信息
     * */
    QC  selectByWorkOrderId(Integer workOrderId);
    
    /**
     * @author lk 2017/04/07
     * @param qcid
     * @return
     * 根据质检id查询评分信息
     */
    QC  selectByQcid(Integer qcid);
    /**
     * @author lk 2017/04/07
     * @param qcid
     * @return
     * 根据小结id查询评分信息
     */
    QC  selectBytelsumId(Integer qcid);
    
    /**
     * @author lk 2017/4/14
     * @param qcid
     * @return Integer
     * 根据质检id删除质检记录
     */
    int deleteByqcid(Integer qcid);
}