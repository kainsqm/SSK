
/**
 * @author maxiaoni
 *
 */
package cn.sh.ideal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.IQCDao;
import cn.sh.ideal.model.QC;
import cn.sh.ideal.service.IQCService;
/**
 * 评分
 * 
 * **/


@Service("QCService")
public class QCServiceImpl implements IQCService {
	@Resource
	private IQCDao QCDao;
   

	@Override
	public int deleteByRecordid(Integer recordId) {
		return  QCDao.deleteByRecordid(recordId);
	}

	@Override
	public int insert(QC record) {
		return QCDao.insert(record);
	}

	@Override
	public QC selectByRecordId(Integer recordId) {
		return QCDao.selectByRecordId(recordId);
	}

	@Override
	public int updateByPrimaryKey(QC record) {
		return QCDao.updateByPrimaryKey(record);
	}
	
	  /**
     * 根据工单ID查询关联的评分信息
     * */
    public QC selectByWorkOrderId(Integer workOrderId){
	   return QCDao.selectByWorkOrderId(workOrderId);
    }

	@Override
	public QC selectByQcid(Integer qcid) {
		return QCDao.selectByQcid(qcid);
	}

	@Override
	public QC selectBytelsumId(Integer telsumId) {
		return QCDao.selectBytelsumId(telsumId);
	}

	@Override
	public int deleteByqcid(Integer qcid) {
		return QCDao.deleteByqcid(qcid);
	}
}