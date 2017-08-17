package cn.sh.ideal.model;

import java.util.Date;

public class QC {
    private Integer qcId;

    private Integer recordId;//录音表主键

    private Integer workorderId;//工单表主键

    private Integer templateId;//模板id

    private String qcSum;//总次数

    private String enabled;//是否可用;1,有效；0，无效

    private Integer qcUserId;//质检员USERID
    private String qcWorkid;//质检员工号

    private Date qcTime;//质检时间
    
    private Integer agentWorkPaperId;//反馈单表主键
    
    private Integer telsumId;//电话小结id
    
    private Integer qc_type; //评分类型
    private String agentWorkid;//受理员工号

    
    public String getAgentWorkid() {
		return agentWorkid;
	}

	public void setAgentWorkid(String agentWorkid) {
		this.agentWorkid = agentWorkid;
	}

	public Integer getQcId() {
        return qcId;
    }

    public void setQcId(Integer qcId) {
        this.qcId = qcId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getWorkorderId() {
        return workorderId;
    }

    public void setWorkorderId(Integer workorderId) {
        this.workorderId = workorderId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getQcSum() {
        return qcSum;
    }

    public void setQcSum(String qcSum) {
        this.qcSum = qcSum == null ? null : qcSum.trim();
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

	public Integer getAgentWorkPaperId() {
		return agentWorkPaperId;
	}

	public void setAgentWorkPaperId(Integer agentWorkPaperId) {
		this.agentWorkPaperId = agentWorkPaperId;
	}

	public String getQcWorkid() {
		return qcWorkid;
	}

	public void setQcWorkid(String qcWorkid) {
		this.qcWorkid = qcWorkid;
	}

	public Integer getTelsumId() {
		return telsumId;
	}

	public void setTelsumId(Integer telsumId) {
		this.telsumId = telsumId;
	}

	public Integer getQc_type() {
		return qc_type;
	}

	public void setQc_type(Integer qc_type) {
		this.qc_type = qc_type;
	}
    
}