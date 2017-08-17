package cn.sh.ideal.model;
/**
 * 试卷表
 * @author Administrator
 *
 */
public class tExampaper {
    private Integer pkAutoId;	//主键ID

    private String examPaperName;	//试卷名称

    private Integer examPaperScore;	//试卷总分

    private String examPaperRemark;	//试卷备注
    private String examPaperStatus;	//试卷状态 0 无效；1 有效无效的试卷,以后的考试,本试卷不可选

    private String examPaperPassRate;	//试卷及格率

    private String examPaperAvgScore;	//试卷平均分

    private String isindex;		//是否乱序   0-不乱序  1-乱序

    private String examPaperFlag;	//试卷模板  1表示考题试卷  2表示模板试卷 3 表示批量导入整张试卷

    private String examPaperType;	//试卷类型  日考 月考

    private Integer fkInsertUserId;	//录入人

    private String insertTime; //录入时间

    private Integer zuhuId;	//租户 Id
    
	@Override
	public String toString() {
		return "tExampaper [pkAutoId=" + pkAutoId + ", examPaperName="
				+ examPaperName + ", examPaperScore=" + examPaperScore
				+ ", examPaperRemark=" + examPaperRemark + ", examPaperStatus="
				+ examPaperStatus + ", examPaperPassRate=" + examPaperPassRate
				+ ", examPaperAvgScore=" + examPaperAvgScore + ", isindex="
				+ isindex + ", examPaperFlag=" + examPaperFlag
				+ ", examPaperType=" + examPaperType + ", fkInsertUserId="
				+ fkInsertUserId + ", insertTime=" + insertTime + ", zuhuId="
				+ zuhuId + "]";
	}

	public Integer getPkAutoId() {
        return pkAutoId;
    }

    public void setPkAutoId(Integer pkAutoId) {
        this.pkAutoId = pkAutoId;
    }

    public String getExamPaperName() {
        return examPaperName;
    }

    public void setExamPaperName(String examPaperName) {
        this.examPaperName = examPaperName == null ? null : examPaperName.trim();
    }

    public Integer getExamPaperScore() {
        return examPaperScore;
    }

    public void setExamPaperScore(Integer examPaperScore) {
        this.examPaperScore = examPaperScore;
    }

    public String getExamPaperRemark() {
        return examPaperRemark;
    }

    public void setExamPaperRemark(String examPaperRemark) {
        this.examPaperRemark = examPaperRemark == null ? null : examPaperRemark.trim();
    }

    public String getExamPaperStatus() {
        return examPaperStatus;
    }

    public void setExamPaperStatus(String examPaperStatus) {
        this.examPaperStatus = examPaperStatus == null||examPaperStatus.equals("3") ? null : examPaperStatus.trim();
    }

    public String getExamPaperPassRate() {
        return examPaperPassRate;
    }

    public void setExamPaperPassRate(String examPaperPassRate) {
        this.examPaperPassRate = examPaperPassRate == null ? null : examPaperPassRate.trim();
    }

    public String getExamPaperAvgScore() {
        return examPaperAvgScore;
    }

    public void setExamPaperAvgScore(String examPaperAvgScore) {
        this.examPaperAvgScore = examPaperAvgScore == null ? null : examPaperAvgScore.trim();
    }

    public String getIsindex() {
        return isindex;
    }

    public void setIsindex(String isindex) {
        this.isindex = isindex == null ? null : isindex.trim();
    }

    public String getExamPaperFlag() {
        return examPaperFlag;
    }

    public void setExamPaperFlag(String examPaperFlag) {
        this.examPaperFlag = examPaperFlag == null ? null : examPaperFlag.trim();
    }

    public String getExamPaperType() {
        return examPaperType;
    }

    public void setExamPaperType(String examPaperType) {
        this.examPaperType = examPaperType == null||examPaperType.equals("0") ? null : examPaperType.trim();
    }

    public Integer getFkInsertUserId() {
        return fkInsertUserId;
    }

    public void setFkInsertUserId(Integer fkInsertUserId) {
        this.fkInsertUserId = fkInsertUserId;
    }

   

    public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public Integer getZuhuId() {
        return zuhuId;
    }

    public void setZuhuId(Integer zuhuId) {
        this.zuhuId = zuhuId;
    }
}