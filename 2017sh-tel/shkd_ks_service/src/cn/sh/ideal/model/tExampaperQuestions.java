package cn.sh.ideal.model;
/**
 * 考题试卷表
 *
 */
public class tExampaperQuestions {
    private Integer pkAutoId;	//主键ID

    private Integer fkExampaperId;	//试卷ID

    private Integer fkQuestionsId;	//考题ID

    private Integer quesScore;	//考题分数

    private Integer fkInsertUserId;	//选题人ID

    private String insertTime;	//选题时间

    private Integer fkUpdateUserId;	//修改人

    private String updateTime;	//修改时间

    private String exampaperFlag;	//试卷模板 0 表示与考题表关联 1 表示与考题模板表关联

    private Integer fkUserId;	//考生ID 值如果为1，表示是随机模板试卷，则该字段需填写考生ID

    private Integer fkExamId;	//考试 ID 值如果为1，表示是随机模板试卷，则该字段需填写考试ID

    private Integer zuhuId;		//租户ID

    private String enabled;		// 是否有效 0-否  1-是

    public Integer getPkAutoId() {
        return pkAutoId;
    }

    public void setPkAutoId(Integer pkAutoId) {
        this.pkAutoId = pkAutoId;
    }

    public Integer getFkExampaperId() {
        return fkExampaperId;
    }

    public void setFkExampaperId(Integer fkExampaperId) {
        this.fkExampaperId = fkExampaperId;
    }

    public Integer getFkQuestionsId() {
        return fkQuestionsId;
    }

    public void setFkQuestionsId(Integer fkQuestionsId) {
        this.fkQuestionsId = fkQuestionsId;
    }

    public Integer getQuesScore() {
        return quesScore;
    }

    public void setQuesScore(Integer quesScore) {
        this.quesScore = quesScore;
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

	public String getExampaperFlag() {
        return exampaperFlag;
    }

    public void setExampaperFlag(String exampaperFlag) {
        this.exampaperFlag = exampaperFlag == null ? null : exampaperFlag.trim();
    }

    public Integer getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(Integer fkUserId) {
        this.fkUserId = fkUserId;
    }

    public Integer getFkExamId() {
        return fkExamId;
    }

    public void setFkExamId(Integer fkExamId) {
        this.fkExamId = fkExamId;
    }

    public Integer getZuhuId() {
        return zuhuId;
    }

    public void setZuhuId(Integer zuhuId) {
        this.zuhuId = zuhuId;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }
}