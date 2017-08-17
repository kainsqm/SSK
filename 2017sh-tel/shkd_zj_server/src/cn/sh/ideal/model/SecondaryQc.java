package cn.sh.ideal.model;

import java.util.Date;

public class SecondaryQc {
    private Integer sId;

    private String sWorkid;//检查人(督导)工号

    private Integer sQcUserid;//质检员id

    private String sQcWorkid;///质检员工号

    private String sQcName;//质检员姓名

    private String sRecordThree;//录音流水号
    private Integer workorderId;//工单流水号

    private String sIspass;//自查结果（1：合格；0：不合格）

    private String sRemark;//督导意见

    private String enabled;//是否有效(0 无效 1 有效)

    private Integer createUserId;//创建人USERID

    private String createTime;//创建时间
    private String createTimeStart;//开始时间
    private String createTimeEnd;//结束时间

    private Integer updateUserId;//修改人USERID

    private Date updateTime;//修改时间

    private Integer qc_id;//质检id
    
    private Integer qc_type;//质检类型
    

    
    
	public Integer getQc_type() {
		return qc_type;
	}

	public void setQc_type(Integer qc_type) {
		this.qc_type = qc_type;
	}

	public Integer getQc_id() {
		return qc_id;
	}

	public void setQc_id(Integer qc_id) {
		this.qc_id = qc_id;
	}

	public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public String getsWorkid() {
        return sWorkid;
    }

    public void setsWorkid(String sWorkid) {
        this.sWorkid = sWorkid;
    }

    public Integer getsQcUserid() {
        return sQcUserid;
    }

    public void setsQcUserid(Integer sQcUserid) {
        this.sQcUserid = sQcUserid;
    }

    public String getsQcWorkid() {
        return sQcWorkid;
    }

    public void setsQcWorkid(String sQcWorkid) {
        this.sQcWorkid = sQcWorkid == null ? null : sQcWorkid.trim();
    }

    public String getsQcName() {
        return sQcName;
    }

    public void setsQcName(String sQcName) {
        this.sQcName = sQcName == null ? null : sQcName.trim();
    }

    public String getsRecordThree() {
        return sRecordThree;
    }

    public void setsRecordThree(String sRecordThree) {
        this.sRecordThree = sRecordThree;
    }

    public String getsIspass() {
        return sIspass;
    }

    public void setsIspass(String sIspass) {
        this.sIspass = sIspass == null ? null : sIspass.trim();
    }

    public String getsRemark() {
        return sRemark;
    }

    public void setsRemark(String sRemark) {
        this.sRemark = sRemark == null ? null : sRemark.trim();
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Integer getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Integer workorderId) {
		this.workorderId = workorderId;
	}
    
    
}