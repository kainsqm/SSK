package cn.sh.ideal.service;

import java.util.List;
import java.util.Map;

import cn.sh.ideal.model.tExam;
import cn.sh.ideal.model.tExamExampaperExamine;
import cn.sh.ideal.model.tExampaperRandom;

public interface IExamJoinService {
 
    
    /*******
     * 查询个人待考试列表
     * @author niewenqiang
     * @date 2017-4-26
     * *********/
    public List<tExam> queryToExamList(int userId,int zuhuId);
    
    
    /*******
     * 查询个人待考试列表数量
     * @author niewenqiang
     * @date 2017-4-26
     * *********/
    public int queryToExamListCount(int userId,int zuhuId);
    
    
    /******
     * 根据ID查询对应的记录
     * @author niewenqiang
     * @date 2017-4-26
     * ******/
    public tExamExampaperExamine queryExamExampaperExamineById(int pkAutoId);
    
    
    
    /******
     * 根据试卷ID查询该试卷是否为模板试卷
     * @author niewenqiang
     * @date 2017-4-26
     * *******/
    public List<tExampaperRandom> getExampaperRandomByExampaperID(int exampaperId);
        
    
    
    /******
     * 模板试卷进行考试时，获取对应的考题
     * ********/
    public List addTestByExamRandom(List<tExampaperRandom> exampaperRandom,tExam exam);
    
    
    
    /******
     * 普通试卷进行考试时，获取对应的考题
     * ********/
    public List addTestByExam(tExam exam,String isIndex);
    
    
    
    /******
     * 根据ID查询考试结果
     * @author niewenqiang
     * @date 2017-4-27
     * *******/
    public List queryResultMsgByeePexamineId(int examineId);
    
    
    
    /******
     * 更新考试的状态
     * @author niewenqiang
     * @date 2017-4-27
     * *******/
    public void updateExamStatus(tExamExampaperExamine examine);
    
    
    
    /******
     * 保存考试答案
     * @author niewenqiang
     * @date 2017-4-28
     * ******/
    public String saveExamRstToFileAndData(String examExampaperExamineId,
			String quesAnswer, String quesAnswerTwo,
			String quesAnswerFourAndFive, String statusFlag,String dirPath,boolean isSubmit);
    

}
