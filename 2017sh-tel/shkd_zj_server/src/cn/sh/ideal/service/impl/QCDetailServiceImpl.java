
/**
 * @author maxiaoni
 *
 */
package cn.sh.ideal.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.IQCDetailDao;
import cn.sh.ideal.model.AcceptQualityCheck;
import cn.sh.ideal.model.AcceptQualityCheckInfo;
import cn.sh.ideal.model.MonitorWorkload;
import cn.sh.ideal.model.QCDetail;
import cn.sh.ideal.service.IQCDetailService;
/**
 * 评分
 * 
 * **/

@Service("QCDetailService")
public class QCDetailServiceImpl implements IQCDetailService {
	@Resource
	private IQCDetailDao QCDetailDao;

	

	@Override
	public int insert(QCDetail record) {
		return QCDetailDao.insert(record);
	}

	@Override
	public int selectByQcid(Integer qcId) {
		return QCDetailDao.selectByQcid(qcId);
	}

	@Override
	public QCDetail selectDetailByQcid(Integer qcId) {
		return QCDetailDao.selectDetailByQcid(qcId);
	}

	@Override
	public QCDetail qcDetailByQcid(Integer qcId) {
		return QCDetailDao.qcDetailByQcid(qcId);
	}
   
	/**
     * 质检员监听工作量报表清单统计 
     * **/
    public List<MonitorWorkload> MonitorWorkload(Map<String, String> map){
    	return QCDetailDao.MonitorWorkload(map);
    }
    
    /**
     * 质检员监听工作量报表清单统计 历史数据
     * **/
    public List<MonitorWorkload> MonitorWorkloadOld(Map<String, String> map){
    	return QCDetailDao.MonitorWorkloadOld(map);
    }
    
    
    /***
     * 受理质量检查报表及详单 Tl9000
     * 
     * **/
    public List<AcceptQualityCheck> AcceptQualityCheckTl(Map<String, String> map){
    	return QCDetailDao.AcceptQualityCheckTl(map);
    }
    
    
    /***
     * 受理质量检查报表及详单 Tl9000 历史数据
     * 
     * **/
    public List<AcceptQualityCheck> AcceptQualityCheckTlOld(Map<String, String> map){
    	return QCDetailDao.AcceptQualityCheckTlOld(map);
    }
    
    /***
     * 受理质量检查报表及详单 本中心
     * 
     * **/
    public List<AcceptQualityCheck> AcceptQualityCheck(Map<String, String> map){
    	return QCDetailDao.AcceptQualityCheck(map);
    }
    
    
    /***
     * 受理质量检查报表及详单 本中心 历史数据
     * 
     * **/
    public List<AcceptQualityCheck> AcceptQualityCheckOld(Map<String, String> map){
    	return QCDetailDao.AcceptQualityCheckOld(map);
    }
    
    
    
    /**
     * 质检员监听工作量报表清单统计 导出
     * **/
    public List<LinkedHashMap<Object, Object>> MonitorWorkloadExport(Map<String, String> map){
    	return QCDetailDao.MonitorWorkloadExport(map);
    }
    
    /**
     * 质检员监听工作量报表清单统计 历史数据  导出
     * **/
    public List<LinkedHashMap<Object, Object>> MonitorWorkloadOldExport(Map<String, String> map){
    	return QCDetailDao.MonitorWorkloadOldExport(map);
    }
    
    
    /***
     * 受理质量检查报表及详单 Tl9000 导出
     * 
     * **/
    public List<LinkedHashMap<Object, Object>> AcceptQualityCheckTlExport(Map<String, String> map){
    	return QCDetailDao.AcceptQualityCheckTlExport(map);
    }
    
    
    /***
     * 受理质量检查报表及详单 Tl9000 历史数据导出
     * 
     * **/
    public List<LinkedHashMap<Object, Object>> AcceptQualityCheckTlOldExport(Map<String, String> map){
    	return QCDetailDao.AcceptQualityCheckTlOldExport(map);
    }
    
    /***
     * 受理质量检查报表及详单 本中心导出
     * 
     * **/
    public List<LinkedHashMap<Object, Object>> AcceptQualityCheckExport(Map<String, String> map){
    	return QCDetailDao.AcceptQualityCheckExport(map);
    }
    
    
    /***
     * 受理质量检查报表及详单 本中心 历史数据导出
     * 
     * **/
    public List<LinkedHashMap<Object, Object>> AcceptQualityCheckOldExport(Map<String, String> map){
    	return QCDetailDao.AcceptQualityCheckOldExport(map);
    }

	@Override
	public int delqcdetailbyQcid(Integer qcId) {
		return QCDetailDao.delqcdetailbyQcid(qcId);
	}

	@Override
	public List<AcceptQualityCheckInfo> getAcceptQualityCheckInfo(
			Map<String, String> map) {
		return QCDetailDao.getAcceptQualityCheckInfo(map);
	}

	@Override
	public List<LinkedHashMap<Object, Object>> exportAcceptQualityCheckInfo(
			Map<String, String> map) {
		return QCDetailDao.exportAcceptQualityCheckInfo(map);
	}
}