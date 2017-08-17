package cn.sh.ideal.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.sh.ideal.controller.UserController;
import cn.sh.ideal.dao.ICoachInfoDao;
import cn.sh.ideal.dao.ICoachMainDao;
import cn.sh.ideal.dao.ICoachServerDao;
import cn.sh.ideal.dao.ITaskDao;
import cn.sh.ideal.model.CoachInfo;
import cn.sh.ideal.model.CoachMain;
import cn.sh.ideal.model.CoachServer;
import cn.sh.ideal.service.ICoachService;
import cn.sh.ideal.util.SearchResultBean;
import cn.sh.ideal.util.Util;
@Service("coachService")
public class CoachServiceImpl implements ICoachService {
	private static Logger log = Logger.getLogger(CoachServiceImpl.class);
	@Resource
	public ICoachMainDao coachMainDao;
	@Resource
	private ITaskDao taskDao;
	@Resource
	private ICoachServerDao coachServerDao;
	@Resource
	private ICoachInfoDao coachInfoDao;
	
	
	@Override
	public List<CoachMain> queryCoachMain(CoachMain coach) {
		return coachMainDao.queryCoachMain(coach);
	}
   
	/**
     * 查询辅导月计划列表信息数量
     * */
    public int queryCoachMainCount(CoachMain cm){
    	return coachMainDao.queryCoachMainCount(cm);
    }
    
    /**
	 * 通过查询下拉辅导状态菜单获取辅导月计划状态
	 * @param selPass 下拉选择项值
	 * @return
	 */
	public String getPassState(String selPass){
		if(Util.isNull(selPass)) return null;
		switch (Integer.parseInt(selPass)) {
		case 0:
			return "0";
		case 1:
			return "1";
		case 2:
			return "2";
		case 3:
			return null;
		case 4:
			return null;
		case 5:
			return null;
		default:
			return null;
		}
	}
	
	/**
	 * 通过查询下拉辅导状态菜单获取辅导总结状态
	 * @param selPass 下拉选择项值
	 * @return
	 */
	public String getSummarizeState(String selPass){
		if(Util.isNull(selPass)) return null;
		switch (Integer.parseInt(selPass)) {
		case 0:
			return null;
		case 1:
			return null;
		case 2:
			return null;
		case 3:
			return "0";
		case 4:
			return "1";
		case 5:
			return "2";
		default:
			return null;
		}
	}
	
	public SearchResultBean queryUserList(Map<String,String> mapdata) {
		SearchResultBean result = new SearchResultBean();
		
		int rowCount = taskDao.getCountManager(mapdata);;
		result.setMaxRowCount(rowCount);
		result.setData(taskDao.getManager(mapdata));
		return result;
	}

	@Override
	public boolean insert(CoachMain record) {
		boolean blag=true;
		try {
			coachMainDao.insert(record);
		} catch (Exception e) {
			blag=false;
			log.error(e);
		}
		return blag;
	}

	@Override
	public boolean insertcoachserver(CoachServer record) {
		boolean blag=true;
		try {
			coachServerDao.insertcoachserver(record);
		} catch (Exception e) {
			blag=false;
			log.error(e);
		}
		return blag;
		
	}
	
	 /**
	    * 督导查询辅导月计划列表信息
	    * **/
		public List<CoachMain> queryCheckCoachMain(CoachMain coach){
			return coachMainDao.queryCheckCoachMain(coach);
		}
		
		/**
	     * 督导查询辅导月计划列表信息数量
	     * */
	    public int queryCheckCoachMainCount(CoachMain cm){
	    	return coachMainDao.queryCoachMainCount(cm);
	    }
	    
	    
	    /***
		 * 根据ID查询月计划详情
		 * **/
		public CoachMain selectByKeyCoachMain(Integer coachmainId){
			return coachMainDao.selectByPrimaryKey(coachmainId);
		}
		
		
		/***
		 * 根据月计划查询周计划详情
		 * **/
		public List<CoachInfo> selectByKeyCoachInfo(Integer coachmainId){
			return coachInfoDao.selectByCoachMain(coachmainId);
		}
		
		/***
		 * 根据月计划ID查询辅导服务细项指标
		 * **/
		public CoachServer selectByKeyCoachServer(Integer coachmainId){
			return coachServerDao.selectByPrimaryKey(coachmainId);
		}
		
		/***
		 * 根据月计划查询周计划详情
		 * **/
		public int selectByKeyCoachInfoCount(Integer coachmainId){
			return coachInfoDao.selectByCoachMainCount(coachmainId);
		}

	@Override
	public CoachMain selectByPrimaryKey(Integer coachmainId) {
		return coachMainDao.selectByPrimaryKey(coachmainId);
	}

	@Override
	public CoachServer selectCoachServerByid(Integer coachmainId) {
		return coachServerDao.selectByPrimaryKey(coachmainId);
	}

	@Override
	public List<CoachMain> getcoachteam() {
		return coachMainDao.getcoachteam();
	}


	@Override
	public boolean updateCoachByid(CoachMain record, CoachServer coach)throws SQLException{
		boolean blag=true;
		try {
			coachMainDao.updateByPrimaryKeySelective(record);
			coachServerDao.updateByPrimaryKeySelective(coach);
		} catch (Exception e) {
			blag=false;	
			log.error(e);
			throw e;
		}
		return blag;
	}

	@Override
	public boolean insertCoachInfo(CoachInfo coachinfo) {
		boolean blag=true;
		try {
			coachInfoDao.insert(coachinfo);
		} catch (Exception e) {
			blag=false;
			log.error(e);
		}
		return blag;
	}

	
	/**
	  * 删除辅导计划
	  * **/
	public int deleteCoachMain(Integer coachmainId){
		coachMainDao.deleteByPrimaryKey(coachmainId);
		coachServerDao.deleteCoachService(coachmainId);
		coachInfoDao.deleteCoachInfo(coachmainId);
		return 0;
	}
	
	/**
	 * 督导审批辅导计划
	 * **/
	public int  updateDuDaoCheckCoachMain(CoachMain cm){
		return coachMainDao.DuDaoCheckCoachMain(cm);
	}
	
	
	 /**
	 * 督导审批辅导总结
	 * **/
	public int  updateCoachSummary(CoachMain cm){
		return coachMainDao.checkCoachSummary(cm);
	}

	@Override
	public boolean deleteCoachInfo(Integer id) {
		boolean blag=true;
		try {
			coachInfoDao.delCoachInfo(id);
		} catch (Exception e) {
			blag=false;
			log.error(e);
		}
		return blag;
	}
	
	 /***
     * 根据辅导项目ID  查询辅导项目名称，并且拼接成一个字符串
     * **/
	public String getCoachProjectById(Map<String, String> mapdata){
		return coachInfoDao.getCoachProjectById(mapdata);
	}

	@Override
	public CoachInfo selecCoachInfotByid(Integer id) {
		
		return coachInfoDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean updatezjBycoachmainid(CoachMain coach) {
		boolean blag=true;
		try {
			coachMainDao.updatezjBycoachmainid(coach);
		} catch (Exception e) {
			blag=false;
			log.error(e);
		}
		return blag;
	}

	@Override
	public boolean updcoachInfo(CoachInfo coac) {
		boolean blag=true;
		try {
		coachInfoDao.updateByPrimaryKey(coac);
		} catch (Exception e) {
			blag=false;
			log.error(e);
		}
		return blag;
	}

	@Override
	public List<CoachInfo> selectByinfo(CoachInfo coac) {
		return coachInfoDao.selectByinfo(coac);
	}

	@Override
	public int selectByinfoCount(CoachInfo coac) {
		return coachInfoDao.selectByinfoCount(coac);
	}

}
