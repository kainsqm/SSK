package cn.sh.ideal.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExamManualScore {
    private Integer pkAutoId;
    private String workId;
    private String userId;
    private String userName;
    private String groupName;
    private Integer exampaperId;
    private String examPaperName;
    private Integer examId;
    private String examName;
    private String examineeStatus;
    private String exampaperScore;
    private Integer examTimeLength;
    private Integer examQuery;
    private String examineeStatusDesc;
    private Date beginTime;
    private Date submitTime;
    private Integer fkScoreuserId;
    private String scoreuserName;
    private String isIndex;
    private Date examBeginTime;
    private Date examEndTime;  
    private Integer enabled;
    
    private Integer queCount;	//题目数量
    private Integer	queScore;	//分数
    private String insertTime;	//录入时间
    private String examPaperRemark;	//试卷备注
    private String examPaperStatus;	//试卷状态
    private String examPaperType;	//试卷类型
    private String userExamLength;//考试时长
    
    private String quesType;//考题类型
    private String quesTypeName;//考题类型名称
    private String quesContent;//考题内容
    private String quesScore;//考题分值
    private String quesAnswer;//考题答案
    private String quesCorrectAnswer;//改错题改错答案
    private String resultId;//考试结果ID
    private String resultsAnswer;//考生写的答案
    private String resultsCorrectAnswer;//考试填写改错题答案
    private String resultsScore;//考生得分
    private String exampaperPassRate;//试卷及格率
    private String exampaperAvgScore;//试卷平均分
    private String quesErrorNum;//题目出错个数
    private String quesPick; //考题可选项
    private String realName;//真实姓名 add by gaoweian 2010-07-21
    private String queueName;//队列名称 add by gaoweian 2010-07-22
    private String scoreuser;//判分人  add by gaoweian 2010-08-10
    private String areaId;//区域id
    private String examResultId;
    private String examPaperFlag;//模板试卷 1表示考题试卷 2表示模板试卷 3 表示批量导入整张试卷
    
    private boolean lookPaperInfoIs;//能否查看试卷(如果已经过了考试结束时间即可查看,否则不可以) start 2011-04-25 taoyang
    
    @Override
	public String toString() {
		return "ExamManualScore [pkAutoId=" + pkAutoId + ", workId=" + workId
				+ ", userId=" + userId + ", userName=" + userName
				+ ", groupName=" + groupName + ", exampaperId=" + exampaperId
				+ ", examPaperName=" + examPaperName + ", examId=" + examId
				+ ", examName=" + examName + ", examineeStatus="
				+ examineeStatus + ", exampaperScore=" + exampaperScore
				+ ", examTimeLength=" + examTimeLength + ", examQuery="
				+ examQuery + ", examineeStatusDesc=" + examineeStatusDesc
				+ ", beginTime=" + beginTime + ", submitTime=" + submitTime
				+ ", fkScoreuserId=" + fkScoreuserId + ", isIndex=" + isIndex
				+ ", examBeginTime=" + examBeginTime + ", examEndTime="
				+ examEndTime + ", enabled=" + enabled + ", queCount="
				+ queCount + ", queScore=" + queScore + ", insertTime="
				+ insertTime + ", examPaperRemark=" + examPaperRemark
				+ ", examPaperStatus=" + examPaperStatus + ", examPaperType="
				+ examPaperType + ", quesType=" + quesType + ", quesTypeName="
				+ quesTypeName + ", quesContent=" + quesContent
				+ ", quesScore=" + quesScore + ", quesAnswer=" + quesAnswer
				+ ", quesCorrectAnswer=" + quesCorrectAnswer + ", resultId="
				+ resultId + ", resultsAnswer=" + resultsAnswer
				+ ", resultsCorrectAnswer=" + resultsCorrectAnswer
				+ ", resultsScore=" + resultsScore + ", exampaperPassRate="
				+ exampaperPassRate + ", exampaperAvgScore="
				+ exampaperAvgScore + ", quesErrorNum=" + quesErrorNum
				+ ", quesPick=" + quesPick + ", realName=" + realName
				+ ", queueName=" + queueName + ", scoreuser=" + scoreuser
				+ ", areaId=" + areaId + ", lookPaperInfoIs=" + lookPaperInfoIs
				+ ", quesContentSumbit=" + quesContentSumbit + "]";
	}
    
    public String getExamPaperFlag() {
		return examPaperFlag;
	}

	public void setExamPaperFlag(String examPaperFlag) {
		this.examPaperFlag = examPaperFlag;
	}

	public String getExamResultId() {
        return examResultId;
    }
    public void setExamResultId(String examResultId) {
        this.examResultId = examResultId;
    }
	public String getExamPaperType() {
		return examPaperType;
	}
	public void setExamPaperType(String examPaperType) {
		this.examPaperType = examPaperType;
	}
	public Integer getQueCount() {
		return queCount;
	}
	public void setQueCount(Integer queCount) {
		this.queCount = queCount;
	}
	public Integer getQueScore() {
		return queScore;
	}
	public void setQueScore(Integer queScore) {
		this.queScore = queScore;
	}
	
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getExamPaperRemark() {
		return examPaperRemark;
	}
	public void setExamPaperRemark(String examPaperRemark) {
		this.examPaperRemark = examPaperRemark;
	}
	public String getExamPaperStatus() {
		return examPaperStatus;
	}
	public void setExamPaperStatus(String examPaperStatus) {
		this.examPaperStatus = examPaperStatus;
	}
	public String getQuesType() {
        return quesType;
    }
    public void setQuesType(String quesType) {
        this.quesType = quesType;
    }
    public String getQuesTypeName() {
        return quesTypeName;
    }
    public void setQuesTypeName(String quesTypeName) {
        this.quesTypeName = quesTypeName;
    }
    public String getQuesContent() {
        return quesContent;
    }
    public void setQuesContent(String quesContent) {
        this.quesContent = quesContent;
    }
    public String getQuesScore() {
        return quesScore;
    }
    public void setQuesScore(String quesScore) {
        this.quesScore = quesScore;
    }
    public String getQuesAnswer() {
        return quesAnswer;
    }
    public void setQuesAnswer(String quesAnswer) {
        this.quesAnswer = quesAnswer;
    }
    public String getQuesCorrectAnswer() {
        return quesCorrectAnswer;
    }
    public void setQuesCorrectAnswer(String quesCorrectAnswer) {
        this.quesCorrectAnswer = quesCorrectAnswer;
    }
    public String getResultId() {
        return resultId;
    }
    public void setResultId(String resultId) {
        this.resultId = resultId;
    }
    public String getResultsAnswer() {
        return resultsAnswer;
    }
    public void setResultsAnswer(String resultsAnswer) {
        this.resultsAnswer = resultsAnswer;
    }
    public String getResultsCorrectAnswer() {
        return resultsCorrectAnswer;
    }
    public void setResultsCorrectAnswer(String resultsCorrectAnswer) {
        this.resultsCorrectAnswer = resultsCorrectAnswer;
    }
    public String getResultsScore() {
        return resultsScore;
    }
    public void setResultsScore(String resultsScore) {
        this.resultsScore = resultsScore;
    }
    public String getExampaperPassRate() {
        return exampaperPassRate;
    }
    public void setExampaperPassRate(String exampaperPassRate) {
        this.exampaperPassRate = exampaperPassRate;
    }
    public String getExampaperAvgScore() {
        return exampaperAvgScore;
    }
    public void setExampaperAvgScore(String exampaperAvgScore) {
        this.exampaperAvgScore = exampaperAvgScore;
    }
    public String getQuesErrorNum() {
        return quesErrorNum;
    }
    public void setQuesErrorNum(String quesErrorNum) {
        this.quesErrorNum = quesErrorNum;
    }
    public String getQuesPick() {
        return quesPick;
    }
    public void setQuesPick(String quesPick) {
        this.quesPick = quesPick;
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getQueueName() {
        return queueName;
    }
    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
    public String getScoreuser() {
        return scoreuser;
    }
    public void setScoreuser(String scoreuser) {
        this.scoreuser = scoreuser;
    }
    public String getAreaId() {
        return areaId;
    }
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
    public boolean isLookPaperInfoIs() {
        return lookPaperInfoIs;
    }
    public void setLookPaperInfoIs(boolean lookPaperInfoIs) {
        this.lookPaperInfoIs = lookPaperInfoIs;
    }
    public String getQuesContentSumbit() {
        return quesContentSumbit;
    }
    public void setQuesContentSumbit(String quesContentSumbit) {
        this.quesContentSumbit = quesContentSumbit;
    }
    private String quesContentSumbit;
    
    
    
    public Integer getPkAutoId() {
        return pkAutoId;
    }
    public void setPkAutoId(Integer pkAutoId) {
        this.pkAutoId = pkAutoId;
    }
    public String getWorkId() {
        return workId;
    }
    public void setWorkId(String workId) {
        this.workId = workId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public Integer getExampaperId() {
        return exampaperId;
    }
    public void setExampaperId(Integer exampaperId) {
        this.exampaperId = exampaperId;
    }
    public String getExamPaperName() {
        return examPaperName;
    }
    public void setExamPaperName(String examPaperName) {
        this.examPaperName = examPaperName;
    }
    public String getExamineeStatus() {
        return examineeStatus;
    }
    public void setExamineeStatus(String examineeStatus) {
        this.examineeStatus = examineeStatus;
    }
    public String getExampaperScore() {
        return exampaperScore;
    }
    public void setExampaperScore(String exampaperScore) {
        this.exampaperScore = exampaperScore;
    }
    public Integer getExamTimeLength() {
        return examTimeLength;
    }
    public void setExamTimeLength(Integer examTimeLength) {
        this.examTimeLength = examTimeLength;
    }
    public Integer getExamQuery() {
        return examQuery;
    }
    public void setExamQuery(Integer examQuery) {
        this.examQuery = examQuery;
    }
    public String getExamineeStatusDesc() {
        return examineeStatusDesc;
    }
    public void setExamineeStatusDesc(String examineeStatusDesc) {
        this.examineeStatusDesc = examineeStatusDesc;
    }
    public Date getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    public Date getSubmitTime() {
        return submitTime;
    }
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }
    public Integer getExamId() {
        return examId;
    }
    public void setExamId(Integer examId) {
        this.examId = examId;
    }
    public String getExamName() {
        return examName;
    }
    public void setExamName(String examName) {
        this.examName = examName;
    }
    public Integer getFkScoreuserId() {
        return fkScoreuserId;
    }
    public void setFkScoreuserId(Integer fkScoreuserId) {
        this.fkScoreuserId = fkScoreuserId;
    }
    public String getIsIndex() {
        return isIndex;
    }
    public void setIsIndex(String isIndex) {
        this.isIndex = isIndex;
    }
    public Date getExamBeginTime() {
        return examBeginTime;
    }
    public void setExamBeginTime(Date examBeginTime) {
        this.examBeginTime = examBeginTime;
    }
    public Date getExamEndTime() {
        return examEndTime;
    }
    public void setExamEndTime(Date examEndTime) {
        this.examEndTime = examEndTime;
    }
    public Integer getEnabled() {
        return enabled;
    }
    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
    public String getBeginTimeToString() {
        if(beginTime!=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(beginTime);
        }
        return "";
    }
    public String getSubmitTimeToString() {
        if(submitTime!=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(submitTime);
        }
        return "";
    }
    public String getUserExamLength() {
        return userExamLength;
    }
    public void setUserExamLength(String userExamLength) {
        this.userExamLength = userExamLength;
    }
    public String getScoreuserName() {
        return scoreuserName;
    }
    public void setScoreuserName(String scoreuserName) {
        this.scoreuserName = scoreuserName;
    }
    
    
}