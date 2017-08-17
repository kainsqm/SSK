package cn.sh.ideal.model;

/***
 * 质检员监听工作量报表清单统计
 * **/
public class MonitorWorkload {
	private String qcUserName; // 质检员
	private String qcUserWorkNo; // 工号
	private String qcDate; // 日期
	private String total;  //质检总数
	private String tl;// 112TL9000
	private String centre;// 112中心
	private String htyclTl;// 后台（预处理）TL9000
	private String htyclCentre; // 后台（预处理）本中心
	private String ctl; // C网TL9000
	private String cCentre; // C网本中心
	private String gk; // 管控
	private String zjdd; // 质检(督导）
	private String haveSatisfaction; // 有满意度
	private String noSatisfaction; // 无满意度
	private String other; // 其他

	
	
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getQcUserName() {
		return qcUserName;
	}

	public void setQcUserName(String qcUserName) {
		this.qcUserName = qcUserName;
	}

	public String getQcUserWorkNo() {
		return qcUserWorkNo;
	}

	public void setQcUserWorkNo(String qcUserWorkNo) {
		this.qcUserWorkNo = qcUserWorkNo;
	}

	public String getQcDate() {
		return qcDate;
	}

	public void setQcDate(String qcDate) {
		this.qcDate = qcDate;
	}

	public String getTl() {
		return tl;
	}

	public void setTl(String tl) {
		this.tl = tl;
	}

	public String getCentre() {
		return centre;
	}

	public void setCentre(String centre) {
		this.centre = centre;
	}

	public String getHtyclTl() {
		return htyclTl;
	}

	public void setHtyclTl(String htyclTl) {
		this.htyclTl = htyclTl;
	}

	public String getHtyclCentre() {
		return htyclCentre;
	}

	public void setHtyclCentre(String htyclCentre) {
		this.htyclCentre = htyclCentre;
	}

	public String getCtl() {
		return ctl;
	}

	public void setCtl(String ctl) {
		this.ctl = ctl;
	}

	public String getcCentre() {
		return cCentre;
	}

	public void setcCentre(String cCentre) {
		this.cCentre = cCentre;
	}

	public String getGk() {
		return gk;
	}

	public void setGk(String gk) {
		this.gk = gk;
	}

	public String getZjdd() {
		return zjdd;
	}

	public void setZjdd(String zjdd) {
		this.zjdd = zjdd;
	}

	public String getHaveSatisfaction() {
		return haveSatisfaction;
	}

	public void setHaveSatisfaction(String haveSatisfaction) {
		this.haveSatisfaction = haveSatisfaction;
	}

	public String getNoSatisfaction() {
		return noSatisfaction;
	}

	public void setNoSatisfaction(String noSatisfaction) {
		this.noSatisfaction = noSatisfaction;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

}
