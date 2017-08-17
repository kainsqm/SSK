package cn.sh.ideal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.ItNoticeDao;
import cn.sh.ideal.model.Notice;
import cn.sh.ideal.service.ItNoticeService;
@Service("noticeService")
public class NoticeServiceImpl implements ItNoticeService {

	@Resource
	private ItNoticeDao noticeDao;
	
	/*****
	 * 查询公告列表
	 * 
	 * @author niewenqiang 2017-4-17
	 * ******/
	public List<Notice> queryNoticeList(Notice notice){
		return noticeDao.queryNoticeList(notice);
	}

	/*****
	 * 查询公告列表数量
	 * 
	 * @author niewenqiang 2017-4-17
	 * ******/
	public int queryNoticeListCount(Notice notice){
		return noticeDao.queryNoticeListCount(notice);
	}
	
	
	/*****
	 * 新增公告信息
	 * @author niewenqiang
	 * 2017-4-19
	 * *****/
	public void insertNotice(Notice notice){
		noticeDao.insertNotice(notice);
	}
	
	
	/*****
	 * 修改公告信息
	 * @author niewenqiang
	 * 2017-4-19
	 * *****/
	public void updateNotice(Notice notice){
		 noticeDao.updateNotice(notice);
	}
	
	/*****
	 * 删除公告信息
	 * @author niewenqiang
	 * 2017-4-19
	 * *****/
	public void deleteNotice(Notice notice){
		noticeDao.deleteNotice(notice);
	}
	
	/*****
	 * 根据公告ID查询该信息
	 * @author niewenqiang
	 * 2017-4-19
	 * *****/
	public Notice queryNotice(int id){
		return noticeDao.queryNotice(id);
	}
	
	

	/*****
	 * 查询公告列表  首页
	 * 
	 * @author niewenqiang 2017-4-17
	 * ******/
	public List<Notice> queryNoticeListByMain(Notice notice){
		return noticeDao.queryNoticeListByMain(notice);
	}

	/*****
	 * 查询公告列表数量  首页
	 * 
	 * @author niewenqiang 2017-4-17
	 * ******/
	public int queryNoticeListByMainCount(Notice notice){
		return noticeDao.queryNoticeListByMainCount(notice);
	}
}
