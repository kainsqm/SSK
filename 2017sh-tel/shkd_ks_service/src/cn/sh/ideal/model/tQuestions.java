package cn.sh.ideal.model;

import java.util.Arrays;

/**
 * 考题表
 */
public class tQuestions {
	private Integer pkAutoId; // 主键

	private String quesContent; // 考题内容

	private Integer quesScore; // 考题分数

	private Integer quesType; // 考题类型 1.判断题 2.判断改错题 3.单选题 4.多选题 5.不定项选择 6.填空题
								// 7.问答题 8.案例分析题
	private String quesTypeStr; //考题类型中文名称
	private String quesPick; // 可选项 当考题类型为3、4、5时,使用该列，且必填

	private String quesAnswer; // 考题答案

	private String quesCorrectAnswer; // 改错题改错答案 判断改错题，判断为N(错误)时,存放改错的答案

	private Integer fkCodetypeId; // 所属分类 T_SYS_CODE.PK_AUTO_ID

	private String quesStatus; // 考题状态 0 无效；1 有效无效的考题，以后生成试卷时，不可选

	private Integer quesErrorNum; // 考题出错数统计

	private String quesNandu; // 考题难度 T_SYS_CODE.PK_AUTO_ID

	private Integer fkInsertUserId; // 录入人
	
	private String  fkInsertUserName; //录入人名称
	
	private String insertTime; // 录入时间

	private Integer fkUpdateUserId; // 修改人

	private String updateTime; // 修改时间

	private String startInsertTime; // 录入开始时间
	
	private String endInsertTime; // 录入结束时间
	
	private String businessType;//业务分类Name
	
	private Integer businessId; //业务分类ID
	
	private Integer zuhuId; // 租户ID
	
	private String quesNanduStr; //考题难度
	private Integer exampaperId;//试卷Id
	private String exampaperName;  //试卷名称  by niewenqiang  2017-4-27
	private String examBeginTime; //考试开始时间  by niewenqiang  2017-4-27
	private String examLength;  //考试时长 by niewenqiang  2017-4-27
	private String examEndTime; //考试结束时间     by niewenqiang  2017-4-27
	private String examPaperFlag;//试卷类型
	private String chooseType;//查询试卷类型标识 1.查询所有试卷  0.查询当前试卷
	private String questIds[];
	

	@Override
	public String toString() {
		return "tQuestions [pkAutoId=" + pkAutoId + ", quesContent="
				+ quesContent + ", quesScore=" + quesScore + ", quesType="
				+ quesType + ", quesTypeStr=" + quesTypeStr + ", quesPick="
				+ quesPick + ", quesAnswer=" + quesAnswer
				+ ", quesCorrectAnswer=" + quesCorrectAnswer
				+ ", fkCodetypeId=" + fkCodetypeId + ", quesStatus="
				+ quesStatus + ", quesErrorNum=" + quesErrorNum
				+ ", quesNandu=" + quesNandu + ", fkInsertUserId="
				+ fkInsertUserId + ", fkInsertUserName=" + fkInsertUserName
				+ ", insertTime=" + insertTime + ", fkUpdateUserId="
				+ fkUpdateUserId + ", updateTime=" + updateTime
				+ ", startInsertTime=" + startInsertTime + ", endInsertTime="
				+ endInsertTime + ", businessType=" + businessType
				+ ", businessId=" + businessId + ", zuhuId=" + zuhuId
				+ ", quesNanduStr=" + quesNanduStr + ", exampaperId="
				+ exampaperId + ", exampaperName=" + exampaperName
				+ ", examBeginTime=" + examBeginTime + ", examLength="
				+ examLength + ", examEndTime=" + examEndTime + ", chooseType="
				+ chooseType + ", questIds=" + Arrays.toString(questIds) + "]";
	}

	public String getExamPaperFlag() {
		return examPaperFlag;
	}

	public void setExamPaperFlag(String examPaperFlag) {
		this.examPaperFlag = examPaperFlag;
	}

	public String getChooseType() {
		return chooseType;
	}

	public void setChooseType(String chooseType) {
		this.chooseType = chooseType;
	}


	public String getFkInsertUserName() {
		return fkInsertUserName;
	}

	public void setFkInsertUserName(String fkInsertUserName) {
		this.fkInsertUserName = fkInsertUserName;
	}


	public Integer getExampaperId() {
		return exampaperId;
	}

	public void setExampaperId(Integer exampaperId) {
		this.exampaperId = exampaperId;
	}

	public String getQuesNanduStr() {
		if("1".equals(quesNandu)){
			quesNanduStr = "难";
		}else if("2".equals(quesNandu)){
			quesNanduStr = "中";
		}else{
			quesNanduStr = "易";
		}
		return quesNanduStr;
	}
	
	public String getQuesTypeStr() {
		if(quesType!=null){
		if(1==quesType){
			quesTypeStr="判断题";
		}else if(2==quesType){
			quesTypeStr="判断改错题";
		}else if(3==quesType){
			quesTypeStr="单选题";
		}else if(4==quesType){
			quesTypeStr="多选题";
		}else if(5==quesType){
			quesTypeStr="不定项选择";
		}else if(6==quesType){
			quesTypeStr="填空题";
		}else if(7==quesType){
			quesTypeStr="问答题";
		}
			}
		return quesTypeStr;
	}

	public void setQuesTypeStr(String quesTypeStr) {
		this.quesTypeStr=quesTypeStr;
	}

	public void setQuesNanduStr(String quesNanduStr) {
		this.quesNanduStr = quesNanduStr;
	}

	public Integer getBusinessId() {
		return businessId;
	}
	
	public String[] getQuestIds() {
		return questIds;
	}

	public void setQuestIds(String[] questIds) {
		this.questIds = questIds;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}


	public String getStartInsertTime() {
		return startInsertTime;
	}

	public void setStartInsertTime(String startInsertTime) {
		this.startInsertTime = startInsertTime;
	}

	public String getEndInsertTime() {
		return endInsertTime;
	}

	public void setEndInsertTime(String endInsertTime) {
		this.endInsertTime = endInsertTime;
	}

	public Integer getPkAutoId() {
		return pkAutoId;
	}

	public void setPkAutoId(Integer pkAutoId) {
		this.pkAutoId = pkAutoId;
	}

	public String getQuesContent() {
		return quesContent;
	}

	public void setQuesContent(String quesContent) {
		this.quesContent = quesContent == null ? null : quesContent.trim();
	}

	public Integer getQuesScore() {
		return quesScore;
	}

	public void setQuesScore(Integer quesScore) {
		this.quesScore = quesScore;
	}

	public Integer getQuesType() {
		return quesType;
	}

	public void setQuesType(Integer quesType) {
		
		this.quesType = (quesType==0?null:quesType);
	}

	public String getQuesPick() {
		return quesPick;
	}

	public void setQuesPick(String quesPick) {
		this.quesPick = quesPick == null ? null : quesPick.trim();
	}

	public String getQuesAnswer() {
		return quesAnswer;
	}

	public void setQuesAnswer(String quesAnswer) {
		this.quesAnswer = quesAnswer == null ? null : quesAnswer.trim();
	}

	public String getQuesCorrectAnswer() {
		return quesCorrectAnswer;
	}

	public void setQuesCorrectAnswer(String quesCorrectAnswer) {
		this.quesCorrectAnswer = quesCorrectAnswer == null ? null
				: quesCorrectAnswer.trim();
	}

	public Integer getFkCodetypeId() {
		return fkCodetypeId;
	}

	public void setFkCodetypeId(Integer fkCodetypeId) {
		this.fkCodetypeId = fkCodetypeId;
	}

	public String getQuesStatus() {
		return quesStatus;
	}

	public void setQuesStatus(String quesStatus) {
		this.quesStatus = quesStatus == null ? null : quesStatus.trim();
	}

	public Integer getQuesErrorNum() {
		return quesErrorNum;
	}

	public void setQuesErrorNum(Integer quesErrorNum) {
		this.quesErrorNum = quesErrorNum;
	}

	public String getQuesNandu() {
		return quesNandu;
	}

	public void setQuesNandu(String quesNandu) {
		this.quesNandu = quesNandu == null||quesNandu.equals("0") ? null : quesNandu.trim();
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

	public Integer getZuhuId() {
		return zuhuId;
	}

	public void setZuhuId(Integer zuhuId) {
		this.zuhuId = zuhuId;
	}

	public String getExampaperName() {
		return exampaperName;
	}

	public void setExampaperName(String exampaperName) {
		this.exampaperName = exampaperName;
	}

	public String getExamBeginTime() {
		return examBeginTime;
	}

	public void setExamBeginTime(String examBeginTime) {
		this.examBeginTime = examBeginTime;
	}

	public String getExamLength() {
		return examLength;
	}

	public void setExamLength(String examLength) {
		this.examLength = examLength;
	}

	public String getExamEndTime() {
		return examEndTime;
	}

	public void setExamEndTime(String examEndTime) {
		this.examEndTime = examEndTime;
	}
	
}