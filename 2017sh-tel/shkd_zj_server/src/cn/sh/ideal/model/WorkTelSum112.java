package cn.sh.ideal.model;

import java.util.List;

public class WorkTelSum112 {
    private Integer autoId;//主键   

    private Integer workorderId; //112流水工单表主键

    private String businessType; //业务类型

    private String telsumType;//小结类型

    private String errorSource;//故障来源

    private String remark; //备注

    private String declarationNo;//申告号码

    private String logicNo; //逻辑号码

    private String telsumId;//电话小结流水

    private String telsumTime;//电话小结时间

    private String telsumWorkid;//电话小结工号
    
    private Integer isQC;//是否质检
    
    private String telsumTimestart;//小结开始时间
    
    private String telsumTimeend;//小结结束时间
    
    
    private List<String> userIds;
    private List<String> notUserIds;
    private List<String> businessTypeList; //业务类型
    private List<String> telsumTypeList;//小结类型
    private List<String> errorSourceList;//故障来源
    private String startdate;
    private String stopdate;

    

	public String getTelsumTimestart() {
		return telsumTimestart;
	}

	public void setTelsumTimestart(String telsumTimestart) {
		this.telsumTimestart = telsumTimestart;
	}

	

	public String getTelsumTimeend() {
		return telsumTimeend;
	}

	public void setTelsumTimeend(String telsumTimeend) {
		this.telsumTimeend = telsumTimeend;
	}

	public Integer getIsQC() {
		return isQC;
	}

	public void setIsQC(Integer isQC) {
		this.isQC = isQC;
	}

	public Integer getAutoId() {
        return autoId;
    }

    public void setAutoId(Integer autoId) {
        this.autoId = autoId;
    }

    public Integer getWorkorderId() {
        return workorderId;
    }

    public void setWorkorderId(Integer workorderId) {
        this.workorderId = workorderId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getTelsumType() {
        return telsumType;
    }

    public void setTelsumType(String telsumType) {
        this.telsumType = telsumType == null ? null : telsumType.trim();
    }

    public String getErrorSource() {
        return errorSource;
    }

    public void setErrorSource(String errorSource) {
        this.errorSource = errorSource == null ? null : errorSource.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDeclarationNo() {
        return declarationNo;
    }

    public void setDeclarationNo(String declarationNo) {
        this.declarationNo = declarationNo == null ? null : declarationNo.trim();
    }

    public String getLogicNo() {
        return logicNo;
    }

    public void setLogicNo(String logicNo) {
        this.logicNo = logicNo == null ? null : logicNo.trim();
    }

    public String getTelsumId() {
        return telsumId;
    }

    public void setTelsumId(String telsumId) {
        this.telsumId = telsumId == null ? null : telsumId.trim();
    }

    public String getTelsumTime() {
        return telsumTime;
    }

    public void setTelsumTime(String telsumTime) {
        this.telsumTime = telsumTime;
    }

    public String getTelsumWorkid() {
        return telsumWorkid;
    }

    public void setTelsumWorkid(String telsumWorkid) {
        this.telsumWorkid = telsumWorkid == null ? null : telsumWorkid.trim();
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getStopdate() {
        return stopdate;
    }

    public void setStopdate(String stopdate) {
        this.stopdate = stopdate;
    }

    public List<String> getBusinessTypeList() {
        return businessTypeList;
    }

    public void setBusinessTypeList(List<String> businessTypeList) {
        this.businessTypeList = businessTypeList;
    }

    public List<String> getTelsumTypeList() {
        return telsumTypeList;
    }

    public void setTelsumTypeList(List<String> telsumTypeList) {
        this.telsumTypeList = telsumTypeList;
    }

    public List<String> getErrorSourceList() {
        return errorSourceList;
    }

    public void setErrorSourceList(List<String> errorSourceList) {
        this.errorSourceList = errorSourceList;
    }

    public List<String> getNotUserIds() {
        return notUserIds;
    }

    public void setNotUserIds(List<String> notUserIds) {
        this.notUserIds = notUserIds;
    }
    
    
}