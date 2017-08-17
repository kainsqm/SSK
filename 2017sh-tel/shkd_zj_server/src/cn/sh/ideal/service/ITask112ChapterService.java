package cn.sh.ideal.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sh.ideal.model.CdmaTaskComplete;
import cn.sh.ideal.model.Task;
import cn.sh.ideal.model.TaskChapter;
import cn.sh.ideal.model.WorkTelSum112;

public interface ITask112ChapterService{
    /**
     * @author cd 2017/4/26
     * @param task
     * @return
     * 查询112小结任务列表
     */
    public List<TaskChapter> gettask112Chapter(TaskChapter task);
    /**
     * @author cd 2017/4/26
     * @param task
     * @return int
     * 查询112小结任务列表总条数
     */
    public  int gettask112Chaptercount(TaskChapter task);
    /**
     * @author cd 2017/5/04
     * @param taskName
     * @return
     * 判断112小结任务名称是否重复
     */
    public int check112Chaptername(String taskName);
    /**
     * 新增112小结任务
     * @author cd 2017/5/04
     * @param task
     * @return
     */
    public int insert112ChapterTask(TaskChapter task);
    /**
     * 修改112小结任务
     * @author cd 2017/5/04
     * @param task
     * @return
     */
    public int update112ChapterTask(TaskChapter task);
    /**
     * 删除112小结任务
     * @author cd 2017/5/04
     * @param task
     * @return
     */
    public int del112ChapterTask(Map<String,String> map);
    /**
     * @author lk 2017/5/03
     * @param taskId
     * @return
     * 查询112小结任务详细
     */
    public TaskChapter get112ChapterTaskbyid(Integer taskId);
    
    /**
     * @author cd
     * @param task
     * @return
     * 质检员查询任务执行列表
     */
    public List<TaskChapter> getExecuteChapterList(TaskChapter taskChapter);
    public int getExecuteChapterListCount(TaskChapter taskChapter); 
    
    
    /**
     * 获取领取工单列表
     */
    public List<WorkTelSum112> getReviceChapterList(Map<String,String> map);
    
    /**
     * 单条领取
     * @param map
     * @return
     */
    public String updReviceTask(Map<String,Object> map);
    
    /**
     * 一键领取 
     */
    public Map<String,String> updateOneKeyReciveAll(Map<String,String> map);
    /**
     * 获取质检已领
     * @param autoList
     * @return
     */
    public List<WorkTelSum112> getYlChapterList(Set<String> orderlist);
    public int getYlChapterListCount(Set<String> orderlist);
    
    public Set<String> getyl112TelSumtaskidList(Map<String, String> map);
    
    /**
     * 质检员查询自己的完成情况
     * @param status
     * @return
     */
    public List<Task> getFinishChapterList(Map<String,String> map);
    public int getFinishChapterListCount(Map<String,String> map);
    
    /*********
     * 督导查询该任务完成情况列表
     * @author 
     * @date 2017-5-25
     * ************/
    public List<CdmaTaskComplete> queryTaskCompleteInfo(Map<String, String> mapdata);
    public int queryTaskCompleteInfoCount(Map<String, String> mapdata);
    
    /**
     * @author 督导查询个人工单详情
     * @param map
     * @return
     */
    List<Task> getChapterFinishDetails(Map<String,String> map);
    int getChapterFinishDetailsCount(Map<String,String> map);
    
    /**
     * @author lk 根据时间释放录音
     * @param map
     * @return
     */
    public String updrelease112ChapterBycDate(Map<String,String> map);
    
    /**
     * @author lk 根据用户id批量释放录音
     * @param map
     * @return
     */
    public String updrelease112ChapterByUser(Map<String,String> map);
    
    public List<TaskChapter> gettaskChapterBytelsum(Map<String,String> map);
    
    int updateTaskChapter(Map<String,String> map);
    
}
