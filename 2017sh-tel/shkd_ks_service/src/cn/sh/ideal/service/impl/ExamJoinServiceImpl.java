package cn.sh.ideal.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.ItExamJoinDao;
import cn.sh.ideal.dao.ItExamdErrorMsgDao;
import cn.sh.ideal.dao.ItExamdNoanswerMsgDao;
import cn.sh.ideal.dao.ItExampaperQuestionsDao;
import cn.sh.ideal.model.tExam;
import cn.sh.ideal.model.tExamExampaperExamine;
import cn.sh.ideal.model.tExamResults;
import cn.sh.ideal.model.tExamdErrorMsg;
import cn.sh.ideal.model.tExamdNoanswerMsg;
import cn.sh.ideal.model.tExampaperQuestions;
import cn.sh.ideal.model.tExampaperRandom;
import cn.sh.ideal.model.tQuestions;
import cn.sh.ideal.service.IExamExampaperExamineService;
import cn.sh.ideal.service.IExamJoinService;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.TSysOperFileServer;
/****
 * 进行考试业务类
 * @author niewenqiang
 * @date 2017-4-26
 * *****/
@Service("examJoinService")
public class ExamJoinServiceImpl implements IExamJoinService {
	private static Logger log = Logger.getLogger(ExamJoinServiceImpl.class);
      @Resource
      public ItExamJoinDao examJoinDao;
	  @Resource
      public ItExampaperQuestionsDao eqDao;
	  @Resource
	  public ItExamdNoanswerMsgDao examedNoAnswerMsgDao;
	  @Resource
	  public ItExamdErrorMsgDao examedErrorMsgDao;
	  @Autowired
	  private IExamExampaperExamineService examExampaperExamineService;
	  
	 /*******
     * 查询个人待考试列表
     * @author niewenqiang
     * @date 2017-4-26
     * *********/
    public List<tExam> queryToExamList(int userId,int zuhuId){
    	return examJoinDao.queryToExamList(userId,zuhuId);
    }
    
    
    /*******
     * 查询个人待考试列表数量
     * @author niewenqiang
     * @date 2017-4-26
     * *********/
    public int queryToExamListCount(int userId,int zuhuId){
    	return examJoinDao.queryToExamListCount(userId,zuhuId);
    }
    
    
    /******
     * 根据ID查询对应的记录
     * @author niewenqiang
     * @date 2017-4-26
     * ******/
    public tExamExampaperExamine queryExamExampaperExamineById(int pkAutoId){
    	return examJoinDao.queryExamExampaperExamineById(pkAutoId);
    }
    
    
    /******
     * 根据试卷ID查询该试卷是否为模板试卷
     * @author niewenqiang
     * @date 2017-4-26
     * *******/
    public List<tExampaperRandom> getExampaperRandomByExampaperID(int exampaperId){
    	return examJoinDao.getExampaperRandomByExampaperID(exampaperId);
    }
    
    
    /******
     * 模板试卷进行考试时，获取对应的考题
     * ********/
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List addTestByExamRandom(List<tExampaperRandom> exampaperRandom,tExam exam){
    	List<tQuestions> questionsList = new ArrayList<tQuestions>();
    	int randomSize=exampaperRandom.size();
		String[] queTypes=new String[randomSize];//获得模板试卷考题类型
		String[] queCounts=new String[randomSize];//获得每种类型有几套题目
		String[] queCodeType=new String[randomSize];
		String[] queScores=new String[randomSize];//获得每套题目的分数
		String[] queNanDus=new String[randomSize];
		for (int i = 0; i < exampaperRandom.size(); i++) {
			tExampaperRandom er=exampaperRandom.get(i);
			queTypes[i]=er.getQueType();
			queCounts[i]=er.getQueCount().toString();
			queCodeType[i]=er.getFkCodetypeId().toString();
			queScores[i]=er.getQueScore().toString();
			queNanDus[i]=er.getQueNandu();
		}
		//根据随机模板来随机获取考题
		//单独为每种考题类型获取考题
		int len = queTypes.length;
		Map quesMap = new HashMap();
		for(int i=0;i<len;i++){
			String strKey = queTypes[i]+","+queCodeType[i]+","+queNanDus[i];
			List quesCScorelist = null;
			if(quesMap.get(strKey)!=null){ //为了避免用户选择模板考题时选择了一样的数据
				quesCScorelist = (List)quesMap.get(strKey);
				quesCScorelist.add(new String[]{queTypes[i],queCodeType[i],queNanDus[i],queCounts[i],queScores[i]});
			}else{
				quesCScorelist = new ArrayList();
				quesCScorelist.add(new String[]{queTypes[i],queCodeType[i],queNanDus[i],queCounts[i],queScores[i]});
				quesMap.put(strKey, quesCScorelist);
			}
			
		}
		for(Iterator iterator=quesMap.keySet().iterator();iterator.hasNext();){
			List quesCScorelist = (List)quesMap.get(iterator.next().toString());
			int queCount = 0;
			int size = quesCScorelist.size();
			Map map = new HashMap();
			for(int i=0;i<size;i++){
				String[] arr = (String[])quesCScorelist.get(i);
				String queType = arr[0];
				String queSort = arr[1];
				String queNanDu = arr[2];
				String queCntStr = arr[3];
				map.put("queType", queType);
				map.put("queSort", queSort); //业务类型
				map.put("queNanDu", queNanDu);
				queCount += Integer.parseInt(queCntStr);
			}
			map.put("queCount", queCount);
			map.put("exampaperId",exam.getExampaperId());
			questionsList = examJoinDao.getQuestionsByExampaperRandom(map);
			if (questionsList != null && questionsList.size() > 0) {// 如果获取到了考题
				int quesCSSize = quesCScorelist.size();
				int qSize = questionsList.size();
				for(int i=0;i<quesCSSize;i++){
					String[] arr = (String[])quesCScorelist.get(i);
					queCount = Integer.parseInt(arr[3]);
					String score = arr[4];
					for(int j=0;j<queCount&&qSize>0;j++){
						tExampaperQuestions exampaperQuestions = new tExampaperQuestions();//考试考卷对象
						exampaperQuestions.setFkExampaperId(Integer.parseInt(exam.getExampaperId()));
						exampaperQuestions.setFkQuestionsId(questionsList.get(--qSize).getPkAutoId());
						exampaperQuestions.setQuesScore(Integer.parseInt(score));
						exampaperQuestions.setFkInsertUserId(exam.getUserId());
						exampaperQuestions.setExampaperFlag("1");//表示模板试卷
						exampaperQuestions.setFkUserId(exam.getUserId()); //一个考生一套题
						exampaperQuestions.setFkExamId(exam.getPkAutoId());//考式ID
						exampaperQuestions.setZuhuId(1);
						//将获取的题目保存到考卷考题表
						eqDao.insert(exampaperQuestions);//添加到试卷考题表
					}
				}
			}
		}
		Map<String, String> examMap=new HashMap<String, String>();
		examMap.put("exampaperId", exam.getExampaperId());
		examMap.put("examId", exam.getPkAutoId().toString());
		examMap.put("isIndex","");
		examMap.put("exampaperFlag", "1");
		examMap.put("userId", String.valueOf(exam.getUserId()));
		return  examJoinDao.queryQuesByExampaperId(examMap);
    }
    
    
    /******
     * 普通试卷进行考试时，获取对应的考题
     * ********/
    public List addTestByExam(tExam exam,String isIndex){
    	Map<String, String> examMap=new HashMap<String, String>();
		examMap.put("exampaperId", exam.getExampaperId());
		examMap.put("examId", exam.getPkAutoId().toString());
		if("1".equals(isIndex)){
			examMap.put("isIndex", isIndex);//要乱序排列
		}
    	return examJoinDao.queryQuesByExampaperId(examMap);//isIndex为“1”,表示要乱序排列
    }
    
    
    /******
     * 根据ID查询考试结果
     * @author niewenqiang
     * @date 2017-4-27
     * *******/
    public List queryResultMsgByeePexamineId(int examineId){
    	return examJoinDao.queryResultMsgByeePexamineId(examineId);
    }
    
    
    
    /******
     * 更新考试的状态
     * @author niewenqiang
     * @date 2017-4-27
     * *******/
    public void updateExamStatus(tExamExampaperExamine examine){
    	 examJoinDao.updateExamStatus(examine);
    }

    
    /******
     * 保存考试答案
     * @author niewenqiang
     * @date 2017-4-28
     * ******/
    public String saveExamRstToFileAndData(String examExampaperExamineId,
			String quesAnswer, String quesAnswerTwo,
			String quesAnswerFourAndFive, String statusFlag,String dirPath,boolean isSubmit){
    	StringBuffer buffer = new StringBuffer("");
		StringBuffer errorMsg_bf = new StringBuffer("");
		String noAnswerQuestionIds = "";
		boolean isSucsData = false;
		//isSubmit  为true  表示交卷  为FALSE 表示临时保存试卷
		//保存答案
		try{
			noAnswerQuestionIds = saveExamResult(examExampaperExamineId,quesAnswer,quesAnswerTwo,
				quesAnswerFourAndFive,statusFlag);
			isSucsData = true;
		}catch(Exception ex){
			String message = ex.getMessage();
			log.error("examExampaperExamineId为"+examExampaperExamineId+"保存答案到数据库异常==>ExamJoinServiceImpl.saveExamRstToFileAndData:"+message,ex);
			if(message!=null){
				if(message.length()>500)message = message.substring(0,500);
				errorMsg_bf.append("保存答案到数据库==>"+message);
			}
			isSucsData = false;
		}
		//保存未答考题
		if(isSubmit){
			try{
				if(noAnswerQuestionIds.length()>0){
					tExamdNoanswerMsg msg=new tExamdNoanswerMsg();
					msg.setFkEEpExamineeId(Integer.parseInt(examExampaperExamineId));
					msg.setNoanswerIds(noAnswerQuestionIds);
					msg.setZuhuId(1);
					examedNoAnswerMsgDao.insert(msg);
				}
			}catch(Exception ex){
				String message = ex.getMessage();
				log.error("examExampaperExamineId为"+examExampaperExamineId+"保存未答考题到数据库异常==>ExamJoinServiceImpl.saveExamRstToFileAndData:"+message,ex);
				if(message!=null){
					if(message.length()>500)message = message.substring(0,500);
					errorMsg_bf.append("保存未答考题到数据库==>"+message);
				}
			}
		}
		//备份答案到file
		try {
			String fileName = examExampaperExamineId+".txt";
			StringBuffer content_bf = new StringBuffer();
			content_bf.append(";")
				.append(DateUtil.formatDate())
				.append(";").append(examExampaperExamineId)
				.append(";").append(quesAnswer.replaceAll(";", "；"))
				.append(";").append(quesAnswerTwo.replaceAll(";", "；"))
				.append(";").append(quesAnswerFourAndFive.replaceAll(";", "；"));
			
			File file = TSysOperFileServer.createFile(dirPath, fileName);
			TSysOperFileServer.writerCntToFile(file, content_bf.toString(), true);
		} catch (Exception e) {
			String message = e.getMessage();
			log.info("examExampaperExamineId为"+examExampaperExamineId+"保存答案到文件异常==>ExamJoinServiceImpl.saveExamRstToFileAndData:"+message,e);
			if(message!=null){
				if(message.length()>500)message = message.substring(0,500);
				errorMsg_bf.append("保存答案到文件==>"+message);
			}
		}
		
		//保存可能出现的bug信息
		try {
			if(errorMsg_bf.length()>0){
				tExamdErrorMsg errorMsg=new tExamdErrorMsg();
				errorMsg.setFkEEpExamineeId(Integer.parseInt(examExampaperExamineId));
				errorMsg.setErrorContent(errorMsg_bf.toString());
				errorMsg.setZuhuId(1);
				examedErrorMsgDao.insert(errorMsg);
			}
		} catch (Exception e) {
			log.error("examExampaperExamineId为"+examExampaperExamineId+"保存可能出现的bug信息异常==>ExamJoinServiceImpl.saveExamRstToFileAndData:"+e.getMessage(),e);
		}
		
		if(isSucsData){ //如果考试信息保存成功
			if(isSubmit){
				try {
					examExampaperExamineService.updateAutoSetMark(Integer.parseInt(examExampaperExamineId));
				} catch (Exception e) {
					String message = e.getMessage();
					log.error("examExampaperExamineId为"+examExampaperExamineId+"考完后自动阅卷异常==>ExamJoinServiceImpl.saveExamRstToFileAndData:"+message,e);
					if(message!=null){
						if(message.length()>500)message = message.substring(0,500);
						errorMsg_bf.append("考完后自动阅卷==>"+message);
					}
				}
			}
			buffer.append("{").append("'isScs':'1").
			append("','message':'").append("试卷于" + DateUtil.formatDate() + "保存成功"+"'}");
			
		}else{
			if(isSubmit){
				tExamExampaperExamine examExampaperExamine = new tExamExampaperExamine();
				examExampaperExamine.setPkAutoId(Integer.parseInt(examExampaperExamineId));
				examExampaperExamine.setIsnormal("0");
				examJoinDao.updateExamStatus(examExampaperExamine);
			}
			buffer.append("{").append("'isScs':'0").
			append("','message':'").append("保存试卷失败!(系统已记录错误日志,请联系相关技术支持)"+"'}");
		}
		
		return buffer.toString();
    }
    
    /*****
     * 保存考试结果
     * *****/
    public String saveExamResult(String examExampaperExamineId,
			String quesAnswer, String quesAnswerTwo,
			String quesAnswerFourAndFive, String statusFlag) {
		StringBuffer noAnswerMsg_bf = new StringBuffer("");
		
		// 删除以前保存的答案
		examJoinDao.deleteExamResult(Integer.parseInt(examExampaperExamineId));
		// 填空、问答、案例分析  ('18556','Y'@%@'18557','N'@%@'18558','A'@%@'18559','ddd##eee'@%@;'18560','fffff'@%@'18561','fffddd')
		String[] quesAnswerArray = null;
		if (quesAnswer != null)
			quesAnswerArray = quesAnswer.split("@%@"); // 题目之间用“@%@”分隔

		if (quesAnswerArray != null && quesAnswerArray.length > 0) {
			for (int i = 0; i < quesAnswerArray.length; i++) { // 将填空、问答、案例分析考题结果添加到考试结果表
				if (!"".equals(quesAnswerArray[i])) {
					String[] quesAnswerArrayNext = quesAnswerArray[i].split("@&@", 2);
					tExamResults examResults = new tExamResults();
					examResults.setFkEEpExamineeId(Integer.parseInt(examExampaperExamineId));
					examResults.setFkQuestionsId(Integer.parseInt(quesAnswerArrayNext[0]));
					String resultsAnswer = "";
					if (quesAnswerArrayNext.length == 1) {
						resultsAnswer = "";
					} else {
						resultsAnswer = this.htmlEncode(quesAnswerArrayNext[1]);
						if (resultsAnswer.length() > 2000)
							resultsAnswer = "考生填写的答案已超过最大长度限制：2000字符";
					}
					if(resultsAnswer.equals(""))noAnswerMsg_bf.append(quesAnswerArrayNext[0]).append(",");
					examResults.setResultsAnswer(resultsAnswer);
					examResults.setResultsScore(0);
					examResults.setZuhuId(1);
					examResults.setResultsCorrectAnswer("");
					examJoinDao.insertExamResult(examResults);
				}
			}
		}

		// 判断改错('18666','Y'@%@'18667','N','如何改错')
		String[] quesAnswerTwoArray = null;
		if (quesAnswerTwo != null)
			quesAnswerTwoArray = quesAnswerTwo.split("@%@"); // 题目之间用“@%@”分隔

		if (quesAnswerTwoArray != null && quesAnswerTwoArray.length > 0) {
			for (int i = 0; i < quesAnswerTwoArray.length; i++) { // 将判断改错结果添加到考试结果表
				if (!"".equals(quesAnswerTwoArray[i])) {
					String[] quesAnswerTwoArrayNext = quesAnswerTwoArray[i].split("@&@", 3);
					tExamResults examResults = new tExamResults();
					examResults.setFkEEpExamineeId(Integer.parseInt(examExampaperExamineId));
					examResults.setFkQuestionsId(Integer.parseInt(quesAnswerTwoArrayNext[0]));
					String resultsAnswer = this.htmlEncode(quesAnswerTwoArrayNext[1]);
					if (resultsAnswer.length() > 2000)
						resultsAnswer = "考生填写的答案已超过最大长度限制：2000字符";
					examResults.setResultsAnswer(resultsAnswer);
					String resultsCorrectAnswer = this.htmlEncode(quesAnswerTwoArrayNext[2]);
					if (resultsCorrectAnswer.length() > 2000)
						resultsCorrectAnswer = "考生填写的答案已超过最大长度限制：2000字符";
					if(resultsAnswer.equals("")){
						noAnswerMsg_bf.append(quesAnswerTwoArrayNext[0]).append(",");
					}else if(resultsAnswer.equals("N")&&resultsCorrectAnswer.equals("")){
						noAnswerMsg_bf.append(quesAnswerTwoArrayNext[0]).append(",");
					}
					
					examResults.setResultsCorrectAnswer(resultsCorrectAnswer);
					examResults.setResultsScore(0);
					examResults.setZuhuId(1);
					examJoinDao.insertExamResult(examResults);
				}
			}
		}

		// 判断、单选多选、不定项选择('18999','A';'18999','B';'18999','C';'18889','A';)
		String[] quesAnswerFourAndFiveArray = null;
		if (quesAnswerFourAndFive != null)
			quesAnswerFourAndFiveArray = quesAnswerFourAndFive.split("@%@"); // 题目之间用“@%@”分隔
		if (quesAnswerFourAndFiveArray != null && quesAnswerFourAndFiveArray.length > 0) {
			for (int i = 0; i < quesAnswerFourAndFiveArray.length; i++) {
				if (!"".equals(quesAnswerFourAndFiveArray[i])) {
					String[] quesAnswerFourAndFiveArrayNext = quesAnswerFourAndFiveArray[i].split("@&@", 2);
					tExamResults examResults = new tExamResults();
					examResults.setFkEEpExamineeId(Integer.parseInt(examExampaperExamineId));
					examResults.setFkQuestionsId(Integer.parseInt(quesAnswerFourAndFiveArrayNext[0]));
					String resultsAnswer = this.htmlEncode(quesAnswerFourAndFiveArrayNext[1]);
					if (resultsAnswer.length() > 2000)
						resultsAnswer = "考生填写的答案已超过最大长度限制：2000字符";
					if(resultsAnswer.equals(""))noAnswerMsg_bf.append(quesAnswerFourAndFiveArrayNext[0]).append(",");
					examResults.setResultsAnswer(resultsAnswer);
					examResults.setResultsScore(0);
					examResults.setZuhuId(1);
					examJoinDao.insertExamResult(examResults);
				}
			}
		}

		// 更新考试状态
		tExamExampaperExamine examExampaperExamine = new tExamExampaperExamine();
		examExampaperExamine.setPkAutoId(Integer.parseInt(examExampaperExamineId));
		examExampaperExamine.setExamineeStatus(statusFlag);
		examExampaperExamine.setSubmitTime(new Date());
		examJoinDao.updateExamStatus(examExampaperExamine);
		return noAnswerMsg_bf.toString();
	}
    
    
    /**
	 * 网页特殊字符转换（写入）
	 *
	 * @param str
	 * @return
	 */
	public String htmlEncode(String str) {
		if (str == null || str.length() < 1)
			return "";
		str = str.replace(">", "&gt;");
		str = str.replace("<", "&lt;");
		str = str.replace("&", "&amp;");
		str = str.replace(" ", "&nbsp;");
		str = str.replace("\"", "&quot;");
		str = str.replace("\'", "&#39;");
		str = str.replace("\r\n", "<br/>");
		str = str.replace("\n", "<br/>");
		return str;
	}
	

}
