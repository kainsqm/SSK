package cn.sh.ideal.model;



public class QCHistory {
	private Integer recordId;//录音主键
    private Integer qcId;

    private String reservedThree; //录音流水号
    
    private String qcUserWorkid;//质检员工号

    private String qcTime;//质检时间
    private String qcTimeStart;//质检开始时间
    private String qcTimeEnd;//质检结束时间
    
    
    private String qualityLevel;//合格程度（合格；不合格）
    
    private String smallWorkid;//话务小工号
    
    private String agentName;//话务员姓名
    
    private Integer workorderId;  //主键 流水
    
    private String declarationTime; //申告时间
    
    private String declarationBigType; //申告大类
    
    private String groupName;//组室
    
    private String businessType;//业务类型
    
    private String checkcontent;//检查内容
    
    private String isComplain;//是否投诉
    private String deviceId;//设备编号
    private String isTl9000;//是否TL9000（1：是；0：否）
    private String dealDis;//处理情况
    private String dealRes;//处理结果
    private String satisfied;//满意度（1：有；0：无）
    private String agentTransfer;//人工转接（1：需转接；0：无需转接）
    private String remark;//备注
    private String qctype;//质检类型
    
    private String telsumId;//电话小结流水
    private String telsumType;//小结类型
    
       
	public String getTelsumId() {
		return telsumId;
	}
	public void setTelsumId(String telsumId) {
		this.telsumId = telsumId;
	}
	public String getTelsumType() {
		return telsumType;
	}
	public void setTelsumType(String telsumType) {
		this.telsumType = telsumType;
	}
	public String getQctype() {
		return qctype;
	}
	public void setQctype(String qctype) {
		this.qctype = qctype;
	}
	public String getIsTl9000() {
		return isTl9000;
	}
	public void setIsTl9000(String isTl9000) {
		this.isTl9000 = isTl9000;
	}
	public String getDealDis() {
		return dealDis;
	}
	public void setDealDis(String dealDis) {
		this.dealDis = dealDis;
	}
	public String getDealRes() {
		return dealRes;
	}
	public void setDealRes(String dealRes) {
		this.dealRes = dealRes;
	}
	public String getSatisfied() {
		return satisfied;
	}
	public void setSatisfied(String satisfied) {
		this.satisfied = satisfied;
	}
	public String getAgentTransfer() {
		return agentTransfer;
	}
	public void setAgentTransfer(String agentTransfer) {
		this.agentTransfer = agentTransfer;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getIsComplain() {
		return isComplain;
	}
	public void setIsComplain(String isComplain) {
		this.isComplain = isComplain;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getCheckcontent() {
		return checkcontent;
	}
	public void setCheckcontent(String checkcontent) {
		this.checkcontent = checkcontent;
	}
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public String getReservedThree() {
		return reservedThree;
	}
	public void setReservedThree(String reservedThree) {
		this.reservedThree = reservedThree;
	}
	
	public String getQcUserWorkid() {
		return qcUserWorkid;
	}
	public void setQcUserWorkid(String qcUserWorkid) {
		this.qcUserWorkid = qcUserWorkid;
	}
	public String getQcTime() {
		return qcTime;
	}
	public void setQcTime(String qcTime) {
		this.qcTime = qcTime;
	}
	public String getQualityLevel() {
		return qualityLevel;
	}
	public void setQualityLevel(String qualityLevel) {
		this.qualityLevel = qualityLevel;
	}
	public String getSmallWorkid() {
		return smallWorkid;
	}
	public void setSmallWorkid(String smallWorkid) {
		this.smallWorkid = smallWorkid;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public Integer getWorkorderId() {
		return workorderId;
	}
	public void setWorkorderId(Integer workorderId) {
		this.workorderId = workorderId;
	}
	public String getDeclarationTime() {
		return declarationTime;
	}
	public void setDeclarationTime(String declarationTime) {
		this.declarationTime = declarationTime;
	}
	public String getDeclarationBigType() {
		return declarationBigType;
	}
	public void setDeclarationBigType(String declarationBigType) {
		this.declarationBigType = declarationBigType;
	}
	
	public Integer getQcId() {
		return qcId;
	}
	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}
	public QCHistory() {
		super();
	}
	public String getQcTimeStart() {
		return qcTimeStart;
	}
	public void setQcTimeStart(String qcTimeStart) {
		this.qcTimeStart = qcTimeStart;
	}
	public String getQcTimeEnd() {
		return qcTimeEnd;
	}
	public void setQcTimeEnd(String qcTimeEnd) {
		this.qcTimeEnd = qcTimeEnd;
	}
	
   
}