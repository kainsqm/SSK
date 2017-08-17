package cn.sh.ideal.model;


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
}