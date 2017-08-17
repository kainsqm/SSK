package cn.sh.ideal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.IWorkOrderCdmaSumDao;
import cn.sh.ideal.model.WorkOrderCdmaSum;
import cn.sh.ideal.service.IWorkOrderCdmaSumService;

@Service("workOrderCdmaSumService")
public class WorkOrderCdmaSumServiceImpl implements IWorkOrderCdmaSumService {
	@Resource
	private IWorkOrderCdmaSumDao workOrderCdmaSumDao;

	@Override
	public int deleteByPrimaryKey(Integer cdmaAutoid) {
		return workOrderCdmaSumDao.deleteByPrimaryKey(cdmaAutoid);
	}

	@Override
	public int insert(WorkOrderCdmaSum record) {
		return workOrderCdmaSumDao.insert(record);
	}

	@Override
	public int insertSelective(WorkOrderCdmaSum record) {
		return workOrderCdmaSumDao.insertSelective(record);
	}

	@Override
	public WorkOrderCdmaSum selectByPrimaryKey(Integer cdmaAutoid) {
		return workOrderCdmaSumDao.selectByPrimaryKey(cdmaAutoid);
	}

	@Override
	public int updateByPrimaryKeySelective(WorkOrderCdmaSum record) {
		return workOrderCdmaSumDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(WorkOrderCdmaSum record) {
		return workOrderCdmaSumDao.updateByPrimaryKey(record);
	}

	
}
