package cn.sh.ideal.model;

public class tExamdNoanswerMsg {
    private Integer fkEEpExamineeId;

    private String noanswerIds;

    private String submitDate;

    private Integer zuhuId;

    public Integer getFkEEpExamineeId() {
        return fkEEpExamineeId;
    }

    public void setFkEEpExamineeId(Integer fkEEpExamineeId) {
        this.fkEEpExamineeId = fkEEpExamineeId;
    }

    public String getNoanswerIds() {
        return noanswerIds;
    }

    public void setNoanswerIds(String noanswerIds) {
        this.noanswerIds = noanswerIds == null ? null : noanswerIds.trim();
    }

    

    public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	public Integer getZuhuId() {
        return zuhuId;
    }

    public void setZuhuId(Integer zuhuId) {
        this.zuhuId = zuhuId;
    }
}