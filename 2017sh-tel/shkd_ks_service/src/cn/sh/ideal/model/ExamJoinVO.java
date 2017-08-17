package cn.sh.ideal.model;
public class ExamJoinVO{
	private String pkAutoId;
	private String exampaperName;
	private String examBeginTime;
	private String examTimeLen;
	private String examEndTime;
	private String quesContent;
	private String quesScore;
	private String quesType;
	private String quesPick;
	private String examExampaperExamineId;
	private String examStatus;
	private String resultsAnswer;  //考生答案
	private String examName;    //考试名称
	private String examId;       //考试编号
	private String examExampaperPostUserRealName;		//出题人
	
	private String userName;             //考生名字
	
	private String isNormal;//是否正常
	
	private String isNormalStr;
	private String examRltId;//答案编号
	private String rltAnswer;//答案内容
	private String rltCorrectAnswer;//判断改错题答案
	private String[] quesPickArr;//答案选项数组
	private String quesContentChange;
	private String quesContentSumbit;
	
	private String examPaperFlag;//试卷类型(1.表示考题试卷 2.表示模板试卷)
	
	public String getQuesContentSumbit() {
		return quesContentSumbit;
	}
	public void setQuesContentSumbit(String quesContentSumbit) {
		this.quesContentSumbit = quesContentSumbit;
	}
	public String getQuesContentChange() {
		if(quesContent!=null && !"".equals(quesContent)){
			if(rltAnswer!=null && !rltAnswer.equals("")){
				String[] rltas = rltAnswer.split("####");
				for(int i=0;i<rltas.length;i++){
					quesContent = quesContent.replace("____", "<input type='text' style='border-width:0;border-bottom:1 solid blue;text-align:center' name='txt_"+quesType+"_"+pkAutoId+"' value='"+rltas[i]+"' />");
				}
				return quesContent;
			}else{
				return quesContent.replaceAll("____", "<input type='text' style='border-width:0;border-bottom:1 solid blue;text-align:center' name='txt_"+quesType+"_"+pkAutoId+"' value='' />");
			}
		}
		return "";
	}
	public String[] getQuesPickArr() {
		if(quesPick!=null && !"".equals(quesPick))return quesPick.split(";");
		return null;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getExamExampaperPostUserRealName() {
		return examExampaperPostUserRealName;
	}
	public void setExamExampaperPostUserRealName(
			String examExampaperPostUserRealName) {
		this.examExampaperPostUserRealName = examExampaperPostUserRealName;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getExampaperName() {
		return exampaperName;
	}
	public void setExampaperName(String exampaperName) {
		this.exampaperName = exampaperName;
	}
	public String getExamTimeLen() {
		return examTimeLen;
	}
	public void setExamTimeLen(String examTimeLen) {
		this.examTimeLen = examTimeLen;
	}
	public String getPkAutoId() {
		return pkAutoId;
	}
	public void setPkAutoId(String pkAutoId) {
		this.pkAutoId = pkAutoId;
	}
	public String getQuesContent() {
		return quesContent;
	}
	public void setQuesContent(String quesContent) {
		this.quesContent = quesContent;
	}
	public String getQuesPick() {
		return quesPick;
	}
	public void setQuesPick(String quesPick) {
		this.quesPick = quesPick;
	}
	public String getQuesScore() {
		return quesScore;
	}
	public void setQuesScore(String quesScore) {
		this.quesScore = quesScore;
	}
	public String getQuesType() {
		return quesType;
	}
	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}
	public String getExamExampaperExamineId() {
		return examExampaperExamineId;
	}
	public void setExamExampaperExamineId(String examExampaperExamineId) {
		this.examExampaperExamineId = examExampaperExamineId;
	}
	public String getExamStatus() {
		return examStatus;
	}
	public void setExamStatus(String examStatus) {
		this.examStatus = examStatus;
	}
	public String getExamBeginTime() {
		return examBeginTime;
	}
	public void setExamBeginTime(String examBeginTime) {
		this.examBeginTime = examBeginTime;
	}
	public String getExamEndTime() {
		return examEndTime;
	}
	public void setExamEndTime(String examEndTime) {
		this.examEndTime = examEndTime;
	}
	public String getResultsAnswer() {
		return resultsAnswer;
	}
	public void setResultsAnswer(String resultsAnswer) {
		this.resultsAnswer = resultsAnswer;
	}
	public String getIsNormal() {
		return isNormal;
	}
	public void setIsNormal(String isNormal) {
		this.isNormal = isNormal;
	}
	public String getIsNormalStr() {
		if(this.getIsNormal()!=null && this.getIsNormal().equals("0")){
			return "异常";
		}else{
			return "正常";
		}
	}
	public String getExamRltId() {
		return examRltId;
	}
	public void setExamRltId(String examRltId) {
		this.examRltId = examRltId;
	}
	public String getRltAnswer() {
		return rltAnswer;
	}
	public void setRltAnswer(String rltAnswer) {
		this.rltAnswer = rltAnswer;
	}
	public String getRltCorrectAnswer() {
		return rltCorrectAnswer;
	}
	public void setRltCorrectAnswer(String rltCorrectAnswer) {
		this.rltCorrectAnswer = rltCorrectAnswer;
	}
	public String getExamPaperFlag() {
		return examPaperFlag;
	}
	public void setExamPaperFlag(String examPaperFlag) {
		this.examPaperFlag = examPaperFlag;
	}
	
}
