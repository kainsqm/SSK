package cn.sh.ideal.model;


public class WorkOrderCdmaSum {
    private Integer cdmaAutoid;

    private Integer serialCdma;

    private String serviceType;//业务类型

    private String resultType;//小结类型

    private String complaintSource;//故障来源

    private String remark;//备注

    private String dirNum;//申告号码

    private String resultSerial;//电话小结流水

    private String resultTime;//电话小结时间

    private String opId;//电话小结工号
    
    private Integer isQC;//是否质检
    
    private String resultTimestart;//小结开始时间
    
    private String resultTimeend;//小结结束时间
    

    
	public String getResultTimestart() {
		return resultTimestart;
	}

	public void setResultTimestart(String resultTimestart) {
		this.resultTimestart = resultTimestart;
	}

	public String getResultTimeend() {
		return resultTimeend;
	}

	public void setResultTimeend(String resultTimeend) {
		this.resultTimeend = resultTimeend;
	}

	public Integer getIsQC() {
		return isQC;
	}

	public void setIsQC(Integer isQC) {
		this.isQC = isQC;
	}

	public Integer getCdmaAutoid() {
        return cdmaAutoid;
    }

    public void setCdmaAutoid(Integer cdmaAutoid) {
        this.cdmaAutoid = cdmaAutoid;
    }

    public Integer getSerialCdma() {
        return serialCdma;
    }

    public void setSerialCdma(Integer serialCdma) {
        this.serialCdma = serialCdma;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType == null ? null : serviceType.trim();
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType == null ? null : resultType.trim();
    }

    public String getComplaintSource() {
        return complaintSource;
    }

    public void setComplaintSource(String complaintSource) {
        this.complaintSource = complaintSource == null ? null : complaintSource.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDirNum() {
        return dirNum;
    }

    public void setDirNum(String dirNum) {
        this.dirNum = dirNum == null ? null : dirNum.trim();
    }

    public String getResultSerial() {
        return resultSerial;
    }

    public void setResultSerial(String resultSerial) {
        this.resultSerial = resultSerial == null ? null : resultSerial.trim();
    }

    public String getResultTime() {
        return resultTime;
    }

    public void setResultTime(String resultTime) {
        this.resultTime = resultTime;
    }

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId == null ? null : opId.trim();
    }
}