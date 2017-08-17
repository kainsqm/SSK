package cn.sh.ideal.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.sh.ideal.model.tExam;
import cn.sh.ideal.model.tExamExampaperExamine;
import cn.sh.ideal.model.tExamResults;
import cn.sh.ideal.model.tExampaperRandom;
/*****
 * 进行考试DAO类
 * *****/
public interface ItExamJoinDao {
	
	 /*******
     * 查询个人待考试列表
     * @author niewenqiang
     * @date 2017-4-26
     * *********/
    public List<tExam> queryToExamList(@Param("userId") int userId,@Param("zuhuId") int zuhuId);
    
    
    /*******
     * 查询个人待考试列表数量
     * @author niewenqiang
     * @date 2017-4-26
     * *********/
    public int queryToExamListCount(@Param("userId")int userId,@Param("zuhuId") int zuhuId);
    
    
    /******
     * 根据ID查询对应的记录
     * @author niewenqiang
     * @date 2017-4-26
     * ******/
    public tExamExampaperExamine queryExamExampaperExamineById(@Param("pkAutoId") int pkAutoId);
    
    
    /******
     * 根据试卷ID查询该试卷是否为模板试卷
     * @author niewenqiang
     * @date 2017-4-26
     * *******/
    public List<tExampaperRandom> getExampaperRandomByExampaperID(@Param("exampaperId") int exampaperId);
    
    
    
    /******
     * 根据模板元素查找考题  用于模板试卷考试时获取数据
     * @author niewenqiang
     * @date 2017-4-27
     * *******/
    public List getQuestionsByExampaperRandom(Map<String, String> map);
    
    /******
     * 根据试卷 查询该试卷相关的考题
     * @author niewenqiang
     * @date 2017-4-27
     * *******/
    public List queryQuesByExampaperId(Map<String, String> map);
    
    
    
    /******
     * 根据ID查询考试结果
     * @author niewenqiang
     * @date 2017-4-27
     * *******/
    public List queryResultMsgByeePexamineId(@Param("examineId") int examineId);
    
    
    /******
     * 更新考试的状态 --开始考试
     * @author niewenqiang
     * @date 2017-4-27
     * *******/
    public void updateExamStatus(tExamExampaperExamine examine);
    
    
    /*****
     * 删除之前保存的答案
     * @author niewenqiang
     * @date 2017-4-27
     * *****/
    public void deleteExamResult(@Param("examExampaperExamineId") int examExampaperExamineId);
    
    
    /*****
     * 保存考试结果
     * @author niewenqiang
     * @date 2017-4-27
     * 
     * ******/
    public void  insertExamResult(tExamResults re);
    
    
    /*******
     * 取出所有考试状态为'1:考试中'，开考时间距当前系统时间已超过考试容许的最大时间的考试
     * @author niewenqiang
     * @date 2017-5-19
     * ********/
    public List<tExam> queryStartTimeSysExam();
    
    
    /****
     * 根据t_exam_exampaper_examine中的ID 去考试结果表是否存在该考试交卷记录
     * @author niewenqiang
     * @date 2017-5-19
     * ****/
    public int getCountResultByExamineId(@Param("pkAutoId") int pkAutoId);
    
    
    /*****
     * 将该考试该场考试状态该为已考完
     * @author niewenqiang
     * @date 2017-5-19
     * *****/
    public void updateExamStatusByOver(@Param("pkAutoId") int pkAutoId,@Param("examTimeLength") int examTimeLength);
    
    /*****
     * 将该考试该场考试状态该为待考
     * @author niewenqiang
     * @date 2017-5-19
     * *****/
    public void updateExamStatusByStart(@Param("pkAutoId") int pkAutoId);
}
