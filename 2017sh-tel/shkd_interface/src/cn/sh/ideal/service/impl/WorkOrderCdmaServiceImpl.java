package cn.sh.ideal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.IWorkOrderCdmaDao;
import cn.sh.ideal.model.WorkOrderCdma;
import cn.sh.ideal.service.IWorkOrderCdmaService;

@Service("workOrderCdmaService")
public class WorkOrderCdmaServiceImpl implements IWorkOrderCdmaService {
	@Resource
	private IWorkOrderCdmaDao workOrderCdmaDao;

	@Override
	public int deleteByPrimaryKey(Integer serialCdma) {
		return workOrderCdmaDao.deleteByPrimaryKey(serialCdma);
	}

	@Override
	public int insert(WorkOrderCdma record) {
		return workOrderCdmaDao.insert(record);
	}

	@Override
	public int insertSelective(WorkOrderCdma record) {
		return workOrderCdmaDao.insertSelective(record);
	}

	@Override
	public WorkOrderCdma selectByPrimaryKey(Integer serialCdma) {
		return workOrderCdmaDao.selectByPrimaryKey(serialCdma);
	}

	@Override
	public int updateByPrimaryKeySelective(WorkOrderCdma record) {
		return workOrderCdmaDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(WorkOrderCdma record) {
		return workOrderCdmaDao.updateByPrimaryKey(record);
	}

	
}
