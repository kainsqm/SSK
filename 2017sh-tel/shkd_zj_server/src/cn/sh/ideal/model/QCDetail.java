package cn.sh.ideal.model;

import java.util.Date;

public class QCDetail {
    private Integer detailId;

    private Integer qcId;//质检编号

    private String qcNum;//评分次数

    private String deviceId;//设备编号

    private String businessType;//业务类型（10：112；54：预处理；32：C网；76：管控）

    private String dealRes;//处理结果

    private String dealDis;//处理情况

    private String isTl9000;//是否TL9000（1：是；0：否）

    private String satisfied;//满意度（1：有；0：无）

    private String isComplain;//是否投诉（1：是；0：否）

    private String qualityLevel;//合格程度（合格；不合格）

    private String agentTransfer;//人工转接（1：需转接；0：无需转接）

    private String remark;//备注

    private String enabled;//是否可用;1,有效；0，无效

    private Integer qcUserId;//质检员USERID
    private String qcUserWorkId;//质检员工号

    private Date qcTime;//质检时间
    
    private String  channelType;  //进入的渠道（record 或者为taskrecord）
    
    private String checkcontent; //检查内容

    
	public String getCheckcontent() {
		return checkcontent;
	}

	public void setCheckcontent(String checkcontent) {
		this.checkcontent = checkcontent;
	}

	public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    public Integer getQcId() {
        return qcId;
    }

    public void setQcId(Integer qcId) {
        this.qcId = qcId;
    }

    public String getQcNum() {
        return qcNum;
    }

    public void setQcNum(String qcNum) {
        this.qcNum = qcNum == null ? null : qcNum.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }
    
    

    public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getDealRes() {
        return dealRes;
    }

    public void setDealRes(String dealRes) {
        this.dealRes = dealRes == null ? null : dealRes.trim();
    }

    public String getDealDis() {
        return dealDis;
    }

    public void setDealDis(String dealDis) {
        this.dealDis = dealDis == null ? null : dealDis.trim();
    }

    public String getIsTl9000() {
        return isTl9000;
    }

    public void setIsTl9000(String isTl9000) {
        this.isTl9000 = isTl9000 == null ? null : isTl9000.trim();
    }

    public String getSatisfied() {
        return satisfied;
    }

    public void setSatisfied(String satisfied) {
        this.satisfied = satisfied == null ? null : satisfied.trim();
    }

    public String getIsComplain() {
        return isComplain;
    }

    public void setIsComplain(String isComplain) {
        this.isComplain = isComplain == null ? null : isComplain.trim();
    }

    public String getQualityLevel() {
        return qualityLevel;
    }

    public void setQualityLevel(String qualityLevel) {
        this.qualityLevel = qualityLevel == null ? null : qualityLevel.trim();
    }

    public String getAgentTransfer() {
        return agentTransfer;
    }

    public void setAgentTransfer(String agentTransfer) {
        this.agentTransfer = agentTransfer == null ? null : agentTransfer.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

    public Integer getQcUserId() {
        return qcUserId;
    }

    public void setQcUserId(Integer qcUserId) {
        this.qcUserId = qcUserId;
    }

    public Date getQcTime() {
        return qcTime;
    }

    public void setQcTime(Date qcTime) {
        this.qcTime = qcTime;
    }

	public String getQcUserWorkId() {
		return qcUserWorkId;
	}

	public void setQcUserWorkId(String qcUserWorkId) {
		this.qcUserWorkId = qcUserWorkId;
	}
    
}