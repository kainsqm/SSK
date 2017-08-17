package cn.sh.ideal.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class tExamExampaperExamine {
    private Integer pkAutoId;

    private Integer fkExamExampaperId;

    private Integer fkUserId;

    private String examineeStatus;

    private Integer exampaperScore;

    private Date beginTime;

    private Date submitTime;

    private Integer fkScoreuserId;

    private String isnormal;

    private String enabled;

    private String isrevert;

    private Integer surplustime;

    private String tisforce;

    private Integer zuhuId;
    
    private Integer examId;
    private Integer exampaperId;
    private String examPaperName;
    private String examName;
    private String workId;
    private String userName;
    private String examPaperFlag;
    private String examQuery;
    
    private Date examBeginTime;
    private Date examEndTime;
    
    
    public Integer getExampaperId() {
        return exampaperId;
    }

    public void setExampaperId(Integer exampaperId) {
        this.exampaperId = exampaperId;
    }

    public Date getExamBeginTime() {
        return examBeginTime;
    }

    public String getExamPaperFlag() {
        return examPaperFlag;
    }

    public void setExamPaperFlag(String examPaperFlag) {
        this.examPaperFlag = examPaperFlag;
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

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToBeginTimeString() {
        if(beginTime!=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(beginTime);
        }
        return "";
    }
    
    public String getToSubmitTimeString() {
        if(submitTime!=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(submitTime);
        }
        return "";
    }
    
    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getExamPaperName() {
        return examPaperName;
    }

    public void setExamPaperName(String examPaperName) {
        this.examPaperName = examPaperName;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public Integer getPkAutoId() {
        return pkAutoId;
    }

    public void setPkAutoId(Integer pkAutoId) {
        this.pkAutoId = pkAutoId;
    }

    public Integer getFkExamExampaperId() {
        return fkExamExampaperId;
    }

    public void setFkExamExampaperId(Integer fkExamExampaperId) {
        this.fkExamExampaperId = fkExamExampaperId;
    }

    public Integer getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(Integer fkUserId) {
        this.fkUserId = fkUserId;
    }

    public String getExamineeStatus() {
        return examineeStatus;
    }

    public void setExamineeStatus(String examineeStatus) {
        this.examineeStatus = examineeStatus == null ? null : examineeStatus.trim();
    }

    public Integer getExampaperScore() {
        return exampaperScore;
    }

    public void setExampaperScore(Integer exampaperScore) {
        this.exampaperScore = exampaperScore;
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

	public Integer getFkScoreuserId() {
        return fkScoreuserId;
    }

    public void setFkScoreuserId(Integer fkScoreuserId) {
        this.fkScoreuserId = fkScoreuserId;
    }

    public String getIsnormal() {
        return isnormal;
    }

    public void setIsnormal(String isnormal) {
        this.isnormal = isnormal == null ? null : isnormal.trim();
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

    public String getIsrevert() {
        return isrevert;
    }

    public void setIsrevert(String isrevert) {
        this.isrevert = isrevert == null ? null : isrevert.trim();
    }

    public Integer getSurplustime() {
        return surplustime;
    }

    public void setSurplustime(Integer surplustime) {
        this.surplustime = surplustime;
    }

    public String getTisforce() {
        return tisforce;
    }

    public void setTisforce(String tisforce) {
        this.tisforce = tisforce == null ? null : tisforce.trim();
    }

    public Integer getZuhuId() {
        return zuhuId;
    }

    public void setZuhuId(Integer zuhuId) {
        this.zuhuId = zuhuId;
    }

    public String getExamQuery() {
        return examQuery;
    }

    public void setExamQuery(String examQuery) {
        this.examQuery = examQuery;
    }
    
    
}