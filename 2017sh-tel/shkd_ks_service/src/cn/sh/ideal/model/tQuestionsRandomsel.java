package cn.sh.ideal.model;

public class tQuestionsRandomsel {
    private Integer fkUserId;	//用户ID

    private Integer fkQuestionid;	//考题ID

    private Integer score;	//分值

    private Integer fkExampaperid;	//试卷ID

    private String enabled;		//是否有效

    private Integer zuhuId;		//租户Id

    public Integer getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(Integer fkUserId) {
        this.fkUserId = fkUserId;
    }

    public Integer getFkQuestionid() {
        return fkQuestionid;
    }

    public void setFkQuestionid(Integer fkQuestionid) {
        this.fkQuestionid = fkQuestionid;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getFkExampaperid() {
        return fkExampaperid;
    }

    public void setFkExampaperid(Integer fkExampaperid) {
        this.fkExampaperid = fkExampaperid;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

    public Integer getZuhuId() {
        return zuhuId;
    }

    public void setZuhuId(Integer zuhuId) {
        this.zuhuId = zuhuId;
    }
}