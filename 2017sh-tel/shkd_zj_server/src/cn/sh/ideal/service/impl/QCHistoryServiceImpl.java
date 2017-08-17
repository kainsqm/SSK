
/**
 * @author maxiaoni
 *
 */
package cn.sh.ideal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.IQCHistoryDao;
import cn.sh.ideal.model.QCHistory;
import cn.sh.ideal.service.IQCHistoryService;
/**
 * 历史评分记录
 * 
 * **/


@Service("QCHistoryService")
public class QCHistoryServiceImpl implements IQCHistoryService {
	@Resource
	private IQCHistoryDao QCHistoryDao;

	@Override
	public List<QCHistory> selectQcHisByParam(QCHistory qcHistory) {
		return QCHistoryDao.selectQcHisByParam(qcHistory);
	}

	@Override
	public int selectQcHisCountByParam(QCHistory qcHistory) {
		return QCHistoryDao.selectQcHisCountByParam(qcHistory);
	}

	@Override
	public List<QCHistory> noneQclist(QCHistory qcHistory) {
		return QCHistoryDao.noneQclist(qcHistory);
	}

	@Override
	public int noneQclistsum(QCHistory qcHistory) {
		return QCHistoryDao.noneQclistsum(qcHistory);
	}

	@Override
	public List<QCHistory> getQcHisByorder(QCHistory qcHistory) {
		return QCHistoryDao.getQcHisByorder(qcHistory);
	}

	@Override
	public int getQcHisByordersum(QCHistory qcHistory) {
		return QCHistoryDao.getQcHisByordersum(qcHistory);
	}

	@Override
	public List<QCHistory> getcwQcHisByorder(QCHistory qcHistory) {
		return QCHistoryDao.getcwQcHisByorder(qcHistory);
	}

	@Override
	public int getcwQcHisByordersum(QCHistory qcHistory) {
		return QCHistoryDao.getcwQcHisByordersum(qcHistory);
	}

	@Override
	public List<QCHistory> get112xjQcHisByorder(QCHistory qcHistory) {
		return QCHistoryDao.get112xjQcHisByorder(qcHistory);
	}

	@Override
	public int get112xjQcHisByordersum(QCHistory qcHistory) {
		return QCHistoryDao.get112xjQcHisByordersum(qcHistory);
	}

	@Override
	public List<QCHistory> getcwxjQcHisByorder(QCHistory qcHistory) {
		return QCHistoryDao.getcwxjQcHisByorder(qcHistory);
	}

	@Override
	public int getcwxjQcHisByordersum(QCHistory qcHistory) {
		return QCHistoryDao.getcwxjQcHisByordersum(qcHistory);
	}
   

	
}