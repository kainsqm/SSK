package cn.sh.ideal.model;


public class tExamdErrorMsg {
    private Integer fkEEpExamineeId;

    private String errorContent;

    private String errorDate;

    private Integer zuhuId;

    public Integer getFkEEpExamineeId() {
        return fkEEpExamineeId;
    }

    public void setFkEEpExamineeId(Integer fkEEpExamineeId) {
        this.fkEEpExamineeId = fkEEpExamineeId;
    }

    public String getErrorContent() {
        return errorContent;
    }

    public void setErrorContent(String errorContent) {
        this.errorContent = errorContent == null ? null : errorContent.trim();
    }


    public Integer getZuhuId() {
        return zuhuId;
    }

    public void setZuhuId(Integer zuhuId) {
        this.zuhuId = zuhuId;
    }

	public String getErrorDate() {
		return errorDate;
	}

	public void setErrorDate(String errorDate) {
		this.errorDate = errorDate;
	}
    
    
}