package cn.sh.ideal.service.impl;

import java.util.List;

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
	  /**
     * @author lk 2017/3/20
     * @param WorkOrderCdmaSum
     * @return
     * c网电话小结查询
     */
	@Override
	public List<WorkOrderCdmaSum> selectByPrimaryKey(WorkOrderCdmaSum ordercdma) {
		return workOrderCdmaSumDao.selectByPrimaryKey(ordercdma);
	}

	@Override
	public int updateByPrimaryKeySelective(WorkOrderCdmaSum record) {
		return workOrderCdmaSumDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(WorkOrderCdmaSum record) {
		return workOrderCdmaSumDao.updateByPrimaryKey(record);
	}
	  /**
     * @author lk 2017/3/20
     * @param WorkOrderCdmaSum
     * @return
     * c网电话小结查询
     */
	@Override
	public int sumselectByPrimaryKey(WorkOrderCdmaSum ordercdma) {
		return workOrderCdmaSumDao.sumselectByPrimaryKey(ordercdma);
	}

	@Override
	public WorkOrderCdmaSum getcwbyid(Integer cdmaAutoid) {
		return workOrderCdmaSumDao.getcwbyid(cdmaAutoid);
	}

	@Override
	public List<String> getcwxjtype() {
		return workOrderCdmaSumDao.getcwxjtype();
	}

	@Override
	public List<String> getcwbstype() {
		return workOrderCdmaSumDao.getcwbstype();
	}

	
}
