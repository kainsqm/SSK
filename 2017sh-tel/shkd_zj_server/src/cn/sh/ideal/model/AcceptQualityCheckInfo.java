package cn.sh.ideal.model;

/***
 * @author niewq
 * 受理质量检查报表及详单详情
 * **/
public class AcceptQualityCheckInfo {
	private String id;//序号
	private String date;//日期 yyyy-MM-dd
	private String day;//时间 hh:mm:ss
	private String telusername;//受理者
	private String declarationNum;//申告号码
	private String istl9000;//是否tl9000
	private String declarationcont;//申告内容
	private String existingpProblems;//存在问题
	private String telcheck;//被检查人确认
	private String qcuser;//质检员
	private String noSpecial;//非专项
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getTelusername() {
		return telusername;
	}
	public void setTelusername(String telusername) {
		this.telusername = telusername;
	}
	public String getDeclarationNum() {
		return declarationNum;
	}
	public void setDeclarationNum(String declarationNum) {
		this.declarationNum = declarationNum;
	}
	public String getIstl9000() {
		return istl9000;
	}
	public void setIstl9000(String istl9000) {
		this.istl9000 = istl9000;
	}
	public String getDeclarationcont() {
		return declarationcont;
	}
	public void setDeclarationcont(String declarationcont) {
		this.declarationcont = declarationcont;
	}
	public String getExistingpProblems() {
		return existingpProblems;
	}
	public void setExistingpProblems(String existingpProblems) {
		this.existingpProblems = existingpProblems;
	}
	public String getTelcheck() {
		return telcheck;
	}
	public void setTelcheck(String telcheck) {
		this.telcheck = telcheck;
	}
	public String getQcuser() {
		return qcuser;
	}
	public void setQcuser(String qcuser) {
		this.qcuser = qcuser;
	}
	public String getNoSpecial() {
		return noSpecial;
	}
	public void setNoSpecial(String noSpecial) {
		this.noSpecial = noSpecial;
	}
	
}
