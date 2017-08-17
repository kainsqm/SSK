package cn.sh.ideal.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import cn.sh.ideal.model.CdmaTaskComplete;
import cn.sh.ideal.model.Task;
import cn.sh.ideal.model.TaskChapter;
import cn.sh.ideal.model.WorkTelSum112;

public interface ItQc112xjtaskDao {

    public List<TaskChapter> getTask112Chapter(TaskChapter task);
    public int getTask112ChapterCount(TaskChapter task);
    public int check112ChapterName(String taskName);
    public int add112ChapterTask(TaskChapter task);
    public int update112ChapterTask(TaskChapter task);
    public int upd112Chapterstatus(TaskChapter task);
    int insertChapter112User(TaskChapter task);
    int insertToChapter112UserComplete(TaskChapter task);
    TaskChapter get112ChapterTaskbyid(Integer taskId);
    public int del112ChapterTask(Integer taskId);
    
    public int release112Telsum(Map<String, String> mapdata);
    
    public List<TaskChapter> getExecuteChapterList(TaskChapter taskChapter);
    public int getExecuteChapterListCount(TaskChapter taskChapter); 
    
    
    public List<WorkTelSum112> getReviceChapterList(WorkTelSum112 sum112);
    public int updTelsum112status(String autoId);
    
    public List<WorkTelSum112> getYlChapterList(@Param("orderlist")Set<String> orderlist);
    public int getYlChapterListCount(@Param("orderlist")Set<String> orderlist);
    
    public List<Task> getFinishChapterList(Map<String,String> map);
    public int getFinishChapterListCount(Map<String,String> map);
    
    public List<CdmaTaskComplete> queryTaskCompleteInfo(Map<String, String> mapdata);
    public int queryTaskCompleteInfoCount(Map<String, String> mapdata); 
    
    public List<Task> getChapterFinishDetails(Map<String,String> map);
    public int getChapterFinishDetailsCount(Map<String,String> map);
    
    public int release112Chapter(Map<String,String> map);
    
    public Set<String> getyl112TelSumtaskidList(Map<String, String> map);
    /**
     *  t_qc_taskrecord删除任务录音信息
     * **/
    public int releaseRecord(Map<String, String> mapdata);
    
    public List<String> selectRecordForLog(Map<String, String> mapdata);
    
    public List<String> selectRecordDateForLog(Map<String, String> mapdata);
    
    public List<TaskChapter> gettaskChapterBytelsum(Map<String,String> map);
    
    public int update112ChapterTaskInfo(Map<String,String> map);
    
}
