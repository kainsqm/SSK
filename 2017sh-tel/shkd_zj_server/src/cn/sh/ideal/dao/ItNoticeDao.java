package cn.sh.ideal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sh.ideal.model.Notice;

public interface ItNoticeDao {	
	
	/*****
	 * 查询公告列表
	 * 
	 * @author niewenqiang 2017-4-17
	 * ******/
	public List<Notice> queryNoticeList(Notice notice);

	/*****
	 * 查询公告列表数量
	 * 
	 * @author niewenqiang 2017-4-17
	 * ******/
	public int queryNoticeListCount(Notice notice);
	
	
	/*****
	 * 新增公告信息
	 * @author niewenqiang
	 * 2017-4-19
	 * *****/
	public void insertNotice(Notice notice);
	
	

	/*****
	 * 修改公告信息
	 * @author niewenqiang
	 * 2017-4-19
	 * *****/
	public void updateNotice(Notice notice);
	
	/*****
	 * 删除公告信息
	 * @author niewenqiang
	 * 2017-4-19
	 * *****/
	public void deleteNotice(Notice notice);
	
	
	/*****
	 * 根据公告ID查询该信息
	 * @author niewenqiang
	 * 2017-4-19
	 * *****/
	public Notice queryNotice(@Param("id") int id);
	
	
	/*****
	 * 查询公告列表  首页
	 * 
	 * @author niewenqiang 2017-4-17
	 * ******/
	public List<Notice> queryNoticeListByMain(Notice notice);

	/*****
	 * 查询公告列表数量  首页
	 * 
	 * @author niewenqiang 2017-4-17
	 * ******/
	public int queryNoticeListByMainCount(Notice notice);
	
}