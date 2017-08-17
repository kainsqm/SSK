package cn.sh.ideal.model;

import java.util.Date;

public class tExam {
    private Integer pkAutoId;

    private String examName;

    private Date examBeginTime;

    private Date examEndTime;

    private Integer examTimeLength;

    private Integer examNormalScore;

    private String examQuery;

    private String examStatus;

    private String fkExamtypeId;

    private String fkExamtargetId;

    private Integer fkInsertUserId;

    private String insertTime;

    private Integer fkUpdateUserId;

    private String updateTime;

    private Integer zuhuId;
    
    private String setQuestionName; //出题人姓名  by niewenqiang  2017-4-26
    private String examPaperName;  //试卷名称  by niewenqiang  2017-4-26
    private String examBeginTimeString; // by niewenqiang  2017-4-26
    private String examEndTimeString; // by niewenqiang  2017-4-26
    private String examExampaperExamineId; //考试试卷考生主键ID  by niewenqiang  2017-4-26
    private String exampaperId ; //试卷ID 
    
    private int userId; //模板试卷获取考试题获取当前考试ID
    
    private int isIntoPageByMain; //判断进入考试是否由首页进入   0 从待考列表进入  1 从首页进入
    
    
    
    
    public int getIsIntoPageByMain() {
		return isIntoPageByMain;
	}

	public void setIsIntoPageByMain(int isIntoPageByMain) {
		this.isIntoPageByMain = isIntoPageByMain;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getExampaperId() {
		return exampaperId;
	}

	public void setExampaperId(String exampaperId) {
		this.exampaperId = exampaperId;
	}

	public String getExamExampaperExamineId() {
		return examExampaperExamineId;
	}

	public void setExamExampaperExamineId(String examExampaperExamineId) {
		this.examExampaperExamineId = examExampaperExamineId;
	}

	public String getExamBeginTimeString() {
		return examBeginTimeString;
	}

	public void setExamBeginTimeString(String examBeginTimeString) {
		this.examBeginTimeString = examBeginTimeString;
	}

	public String getExamEndTimeString() {
		return examEndTimeString;
	}

	public void setExamEndTimeString(String examEndTimeString) {
		this.examEndTimeString = examEndTimeString;
	}

	public String getExamPaperName() {
		return examPaperName;
	}

	public void setExamPaperName(String examPaperName) {
		this.examPaperName = examPaperName;
	}

	public String getSetQuestionName() {
		return setQuestionName;
	}

	public void setSetQuestionName(String setQuestionName) {
		this.setQuestionName = setQuestionName;
	}

	public Integer getPkAutoId() {
        return pkAutoId;
    }

    public void setPkAutoId(Integer pkAutoId) {
        this.pkAutoId = pkAutoId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName == null ? null : examName.trim();
    }

    public Integer getExamTimeLength() {
        return examTimeLength;
    }

    public void setExamTimeLength(Integer examTimeLength) {
        this.examTimeLength = examTimeLength;
    }

    public Integer getExamNormalScore() {
        return examNormalScore;
    }

    public void setExamNormalScore(Integer examNormalScore) {
        this.examNormalScore = examNormalScore;
    }

    public String getExamQuery() {
        return examQuery;
    }

    public void setExamQuery(String examQuery) {
        this.examQuery = examQuery == null ? null : examQuery.trim();
    }

    public String getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(String examStatus) {
        this.examStatus = examStatus == null ? null : examStatus.trim();
    }

    public String getFkExamtypeId() {
        return fkExamtypeId;
    }

    public void setFkExamtypeId(String fkExamtypeId) {
        this.fkExamtypeId = fkExamtypeId == null ? null : fkExamtypeId.trim();
    }

    public String getFkExamtargetId() {
        return fkExamtargetId;
    }

    public void setFkExamtargetId(String fkExamtargetId) {
        this.fkExamtargetId = fkExamtargetId == null ? null : fkExamtargetId.trim();
    }

    public Integer getFkInsertUserId() {
        return fkInsertUserId;
    }

    public void setFkInsertUserId(Integer fkInsertUserId) {
        this.fkInsertUserId = fkInsertUserId;
    }

    public Integer getFkUpdateUserId() {
        return fkUpdateUserId;
    }

    public void setFkUpdateUserId(Integer fkUpdateUserId) {
        this.fkUpdateUserId = fkUpdateUserId;
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

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getZuhuId() {
        return zuhuId;
    }

    public void setZuhuId(Integer zuhuId) {
        this.zuhuId = zuhuId;
    }
}