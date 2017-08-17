package cn.sh.ideal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.ItExamExampaperExamineDao;
import cn.sh.ideal.dao.ItExamResultsDao;
import cn.sh.ideal.dao.ItExampaperDao;
import cn.sh.ideal.dao.ItExampaperQuestionsDao;
import cn.sh.ideal.dao.ItMonitorExamDao;
import cn.sh.ideal.dao.ItQuestionsDao;
import cn.sh.ideal.model.ExamManualScore;
import cn.sh.ideal.model.SysRole;
import cn.sh.ideal.model.tExamExampaper;
import cn.sh.ideal.model.tExamExampaperExamine;
import cn.sh.ideal.model.tExamResults;
import cn.sh.ideal.model.tExampaperQuestions;
import cn.sh.ideal.model.tMonitorExam;
import cn.sh.ideal.model.tQuestions;
import cn.sh.ideal.service.IExamExampaperExamineService;

@Service("examExampaperExamineService")
public class ExamExampaperExamineServiceImpl implements IExamExampaperExamineService {
	@Resource
	private ItExamExampaperExamineDao  examExampaperExamineDao;
	@Resource
    private ItExampaperQuestionsDao  exampaperQuestionsDao;
	@Resource
    private ItExamResultsDao  examResultsDao;
	@Resource
    private ItQuestionsDao questionsDao;
	@Resource
    private ItExampaperDao exampaperDao;

	public List<tExamExampaperExamine> selectPersonExams(tExamExampaperExamine examine){
	    return examExampaperExamineDao.selectPersonExams(examine);
	}
	
	public List<tExamExampaperExamine> selectResetExam(tExamExampaperExamine examine){
	    return examExampaperExamineDao.selectResetExam(examine);
	}
	
	public String updateExamReset(tExamExampaperExamine examine){
	    if(examine.getIsrevert()!=null && examine.getIsrevert().equalsIgnoreCase("1")){ //获取剩余时间
	        String suptime = examExampaperExamineDao.getsltMeByEId(examine.getPkAutoId());
	        if(suptime==null){
	            examine.setSurplustime(0);
	        }else{
	            examine.setSurplustime(Integer.parseInt(suptime));
	        }
        } else{
            //删除答题答案
            tExampaperQuestions q = new tExampaperQuestions();
            q.setFkUserId(examine.getFkUserId());
            q.setFkExamId(examine.getExamId());
            q.setZuhuId(1);
            exampaperQuestionsDao.deleteByUserExamId(q);
        }
	    examExampaperExamineDao.updateByPrimaryKeySelective(examine);
	    return "success";
	}
	
	/**
	 * 自动评分
	 * 
	 */
	public String updateAutoSetMark(int examineId){
		String resultFlag = ""; // out
	     Integer v_ques_type; //t_questions.ques_type%type;
	     String v_ques_answer; //t_questions.ques_answer%type;
	     String v_ques_correct_answer; //t_questions.ques_correct_answer%type;
         Integer v_ques_score; //t_questions.ques_score%type;
	     String v_exampaper_flag; // t_exampaper.exampaper_flag%type;
	     
	     Integer v_exampaperId; //t_exampaper.pk_auto_id%type;
	     Integer v_examId;      //t_exampaper.exampaper_flag%type;
	     Integer v_userId;      //t_sys_user.user_id%type;
	     Integer v_total_score = 0;  //number;--考生最后得分(自动评卷得分，不包含手动评分) -考生成绩初始为0分
	     Integer v_flag = 1;         //number;  --标示，一张考卷是否包含填空，问答，或案例分析 --初始为1，表示不包含填空，问答，或案例分析
	     
	     //取出考生考试结果表记录
	     List<tExamResults> v_exam_results = examResultsDao.selectByExamineId(examineId);
	     
	     //考生没有做答案选择的题目
	     List<tQuestions> v_short_questions = questionsDao.queryNoAnswerByExampaperId(examineId);
	     
	     //获取 v_examId v_exampaperId v_userId
	     tExamExampaperExamine tExamine = examExampaperExamineDao.selectUserExamIdByPrimaryKey(examineId); 
	     v_examId = tExamine.getExamId();
	     v_exampaperId = tExamine.getExampaperId();
	     v_userId = tExamine.getFkUserId();
	     
	     //v_exampaper_flag
	     v_exampaper_flag = exampaperDao.selectByPrimaryKey(v_exampaperId).getExamPaperFlag();

	     for (tExamResults tExam : v_exam_results) {
             tExampaperQuestions teq = new tExampaperQuestions();
            if(v_exampaper_flag.equalsIgnoreCase("2")) {  //--判断为模板试卷增加 用户和考试查询条件
                teq.setFkQuestionsId(tExam.getFkQuestionsId());
                teq.setFkExampaperId(v_exampaperId);
                teq.setFkExamId(v_examId);
                teq.setFkUserId(v_userId);
            }else{
                teq.setFkQuestionsId(tExam.getFkQuestionsId());
                teq.setFkExampaperId(v_exampaperId);
            }
            //v_ques_type, v_ques_answer, v_ques_correct_answer, v_ques_score
            tQuestions t =  questionsDao.selectQuestionsByEEUId(teq);
            v_ques_type = t.getQuesType();
            v_ques_answer = t.getQuesAnswer();
            v_ques_correct_answer = t.getQuesCorrectAnswer();
            v_ques_score = t.getQuesScore();
            
            //判断是否包含填空，问答，或案例分析
            if(v_ques_type==2 || v_ques_type==6 || v_ques_type==7 || v_ques_type==8){ 
                v_flag = 0;
            }
            
            //判断题 单选题 多选题 不定项选择题
            if(v_ques_type==1 || v_ques_type==3 || v_ques_type==4 || v_ques_type==5){
                if(tExam.getResultsAnswer()!=null && v_ques_answer.equalsIgnoreCase(tExam.getResultsAnswer())){
                   tExamResults record = new tExamResults();
                   record.setPkAutoId(tExam.getPkAutoId());
                   record.setResultsScore(v_ques_score);
                   examResultsDao.updateUserScore(record);   //累加得分
                   v_total_score = v_total_score + v_ques_score;
                }else{
                   questionsDao.updateErrorNum(tExam.getFkQuestionsId());  //累加错误数
                }
            }
            
            if(v_ques_type==2){
                if(tExam.getResultsAnswer()!=null && v_ques_answer.equalsIgnoreCase(tExam.getResultsAnswer()) && v_ques_answer.equalsIgnoreCase("Y")){
                        tExamResults record = new tExamResults();
                        record.setPkAutoId(tExam.getPkAutoId());
                        record.setResultsScore(v_ques_score);
                        examResultsDao.updateUserScore(record);
                        v_total_score = v_total_score + v_ques_score;
                } else{
                    //当判断改错题为错误时，需要验证填写的正确答案
                    if(tExam.getResultsCorrectAnswer()!=null && v_ques_correct_answer.indexOf(tExam.getResultsCorrectAnswer())>-1){
                        tExamResults record = new tExamResults();
                        record.setPkAutoId(tExam.getPkAutoId());
                        record.setResultsScore(v_ques_score);
                        examResultsDao.updateUserScore(record);   //累加得分
                        v_total_score = v_total_score + v_ques_score;
                    }else{
                        questionsDao.updateErrorNum(tExam.getFkQuestionsId());  //累加错误数
                    }
                }
            }
         }
	     
	     //对于页面上面存在的考题，考生没有选择，以没有作答计入考试结果表
	     for(tQuestions tQuestions : v_short_questions) {
             tExamResults tr = new tExamResults();
             tr.setFkEEpExamineeId(examineId);
             tr.setFkQuestionsId(tQuestions.getPkAutoId());
             tr.setZuhuId(0);
             examResultsDao.insertNullAnswer(tr);
	     }

	     String status = "";
	     if(v_flag==0){
	         status = "5";
	     } else {
	         status = "6";
	     }
	     tExamExampaperExamine record = new tExamExampaperExamine();
	     record.setPkAutoId(examineId);
	     record.setExamineeStatus(status);
	     record.setExampaperScore(v_total_score);
	     examExampaperExamineDao.updateByPrimaryKeySelective(record);
	     return resultFlag;
	 }
	
	public List<ExamManualScore> selectScoreManual(ExamManualScore score){
	    return examExampaperExamineDao.selectScoreManual(score);
	}
	
	public tExamExampaperExamine selectByPrimaryKey(int pkAutoId){
	    return examExampaperExamineDao.selectByPrimaryKey(pkAutoId); 
	}
	
	public ExamManualScore selectExampaperQuestionIFRandom(int pkAutoId){
	    return examExampaperExamineDao.getExampaperQuestionIFRandom(pkAutoId);
	}

	public List queryExampaperForScore(int id,String userId,String examId){
	    ExamManualScore score = new ExamManualScore();
	    score.setPkAutoId(id);
	    if(!userId.equalsIgnoreCase("") && userId.length()>0){
	        score.setUserId(userId);
	        score.setExamId(Integer.parseInt(examId));
	    }
	    return examExampaperExamineDao.queryExampaperForScore(score);
	}
	
	/**
     * 手机评分
     * 
     */
    public String updateResultScore(String str,String examineId,int userId){
        String flag = "";
        int totalScore = 0;
        String params[] = str.split(";");
        for (String param : params) {
            String tmp[] = param.split("_");
            tExamResults record = new tExamResults();
            record.setPkAutoId(Integer.parseInt(tmp[0]));
            record.setResultsScore(Integer.parseInt(tmp[1]));
            examResultsDao.updateUserScore(record);
        }
        
        List<tExamResults> v_exam_results = examResultsDao.selectByExamineId(Integer.parseInt(examineId));
        for (tExamResults tExam : v_exam_results) {
            if(tExam.getResultsScore()!=null && !tExam.getResultsScore().equals(""))
                if(tExam.getResultsScore()!=null) {
                    totalScore = totalScore + tExam.getResultsScore();
                }
        }
        
        tExamExampaperExamine record = new tExamExampaperExamine();
        record.setPkAutoId(Integer.parseInt(examineId));
        record.setExamineeStatus("6");
        record.setExampaperScore(totalScore);
        record.setFkUserId(userId);
        examExampaperExamineDao.updateByPrimaryKeySelective(record);
        flag = totalScore +"";
        return flag;
    }
    
    public List<ExamManualScore> selectListByUser(ExamManualScore ee){
        return examExampaperExamineDao.getListByUser(ee);
    }
    
    public int updateByPrimaryKeySelective(tExamExampaperExamine record){
        return examExampaperExamineDao.updateByPrimaryKeySelective(record);
    }
    
    public int getsltMeByEId(int examineId){
        String suptime = examExampaperExamineDao.getsltMeByEId(examineId);
        if(suptime==null){
            return 0;
        }else{
            return Integer.parseInt(suptime);
        }
    }
}
