package cn.sh.ideal.model;

public class tMonitorExam {
    private Integer pkAutoId;

    private Integer fkUserId;

    private String ipaddr;

    private String state;

    private int ramainingtime;

    private String insertTime;

    private Integer fkExamId;

    private String examName;

    private Integer zuhuId;

    private String answerstr;

    public Integer getPkAutoId() {
        return pkAutoId;
    }

    public void setPkAutoId(Integer pkAutoId) {
        this.pkAutoId = pkAutoId;
    }

    public Integer getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(Integer fkUserId) {
        this.fkUserId = fkUserId;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr == null ? null : ipaddr.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    

    public int getRamainingtime() {
		return ramainingtime;
	}

	public void setRamainingtime(int ramainingtime) {
		this.ramainingtime = ramainingtime;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public Integer getFkExamId() {
        return fkExamId;
    }

    public void setFkExamId(Integer fkExamId) {
        this.fkExamId = fkExamId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName == null ? null : examName.trim();
    }

    public Integer getZuhuId() {
        return zuhuId;
    }

    public void setZuhuId(Integer zuhuId) {
        this.zuhuId = zuhuId;
    }

    public String getAnswerstr() {
        return answerstr;
    }

    public void setAnswerstr(String answerstr) {
        this.answerstr = answerstr == null ? null : answerstr.trim();
    }
}