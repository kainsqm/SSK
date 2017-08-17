package cn.sh.ideal.model;

/***
 * @author niewq
 * 受理质量检查报表及详单
 * **/
public class AcceptQualityCheck {
   
	private String acceptName ; //受理员
	private String qcDate;  //日期
	private String monitorTotal;  //监听数（总）
	private String monitorClose; //监听数（结案）
	private String monitorOrder; //监听数（派单）
	private String normal;  //正常
	private String mistakeTotal; //差错数（总）
	private String mistakeClose; //差错数（结案）
	private String mistakeOrder; //差错数（派单）
	private String qcworkid;//受理员工号
	
	public String getQcDate() {
		return qcDate;
	}
	public void setQcDate(String qcDate) {
		this.qcDate = qcDate;
	}
	public String getAcceptName() {
		return acceptName;
	}
	public void setAcceptName(String acceptName) {
		this.acceptName = acceptName;
	}
	public String getMonitorTotal() {
		return monitorTotal;
	}
	public void setMonitorTotal(String monitorTotal) {
		this.monitorTotal = monitorTotal;
	}
	public String getMonitorClose() {
		return monitorClose;
	}
	public void setMonitorClose(String monitorClose) {
		this.monitorClose = monitorClose;
	}
	public String getMonitorOrder() {
		return monitorOrder;
	}
	public void setMonitorOrder(String monitorOrder) {
		this.monitorOrder = monitorOrder;
	}
	public String getNormal() {
		return normal;
	}
	public void setNormal(String normal) {
		this.normal = normal;
	}
	public String getMistakeTotal() {
		return mistakeTotal;
	}
	public void setMistakeTotal(String mistakeTotal) {
		this.mistakeTotal = mistakeTotal;
	}
	public String getMistakeClose() {
		return mistakeClose;
	}
	public void setMistakeClose(String mistakeClose) {
		this.mistakeClose = mistakeClose;
	}
	public String getMistakeOrder() {
		return mistakeOrder;
	}
	public void setMistakeOrder(String mistakeOrder) {
		this.mistakeOrder = mistakeOrder;
	}
	public String getQcworkid() {
		return qcworkid;
	}
	public void setQcworkid(String qcworkid) {
		this.qcworkid = qcworkid;
	}
	
		
	
}
