package cn.sh.ideal.model;

public class TQuestionsArea {
	private Integer pkAutoId;	//主键ID
	
    private String userId;	//用户ID

    private String questionId;	//考题ID

    private String createQuestionTime;	//创建时间

    private String isTemplate;	//是否模板  0-否  1-是   

    private Integer updateCount;	//修改次数

    private String enabled;	//是否有效    0-无效 1-有效  

    public Integer getPkAutoId() {
		return pkAutoId;
	}

	public void setPkAutoId(Integer pkAutoId) {
		this.pkAutoId = pkAutoId;
	}

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId == null ? null : questionId.trim();
    }


    public String getCreateQuestionTime() {
		return createQuestionTime;
	}

	public void setCreateQuestionTime(String createQuestionTime) {
		this.createQuestionTime = createQuestionTime;
	}

	public String getIsTemplate() {
        return isTemplate;
    }

    public void setIsTemplate(String isTemplate) {
        this.isTemplate = isTemplate == null ? null : isTemplate.trim();
    }

    public Integer getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(Integer updateCount) {
        this.updateCount = updateCount;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }
}