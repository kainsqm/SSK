package cn.sh.ideal.model;


public class RecordInfo {
    private Integer recordId;//

    private String reservedThree;//录音流水号

    private String directionFlag;//呼叫类型 1呼入,0:呼出

    private String callerid;//主叫号码

    private String calledid;//被叫号码
    
    private String startTime;//录音开始时间

    private String stopTime;//录音结束时间

    private Integer recordLength;//通话时长
    private Integer recordLengthmin;
    private Integer recordLengthmax;

    private String smallWorkid;//话务小工号

    private String bigWorkid;//话务大工号

    private String agentName;//话务员姓名

    private Integer groupId;//班组id

    private String groupName;//班组名称

    private String extension;//分机号

    private String recordFilename;//录音文件名

    private String isTask;//是否任务录音(1：是；0：不是)

    private String recordUrl;//录音存放路径
    
    private Integer isQC;

    private String AgentID; //录音接口华为话务员ID
    
    
    public String getAgentID() {
		return AgentID;
	}

	public void setAgentID(String agentID) {
		AgentID = agentID;
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
        this.reservedThree = reservedThree == null ? null : reservedThree.trim();
    }

    public String getDirectionFlag() {
        return directionFlag;
    }

    public void setDirectionFlag(String directionFlag) {
        this.directionFlag = directionFlag == null ? null : directionFlag.trim();
    }

    public String getCallerid() {
        return callerid;
    }

    public void setCallerid(String callerid) {
        this.callerid = callerid == null ? null : callerid.trim();
    }

    public String getCalledid() {
        return calledid;
    }

    public void setCalledid(String calledid) {
        this.calledid = calledid == null ? null : calledid.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

   
    
    
    public Integer getRecordLength() {
		return recordLength;
	}

	public void setRecordLength(Integer recordLength) {
		this.recordLength = recordLength;
	}

	public String getSmallWorkid() {
        return smallWorkid;
    }

    public void setSmallWorkid(String smallWorkid) {
        this.smallWorkid = smallWorkid == null ? null : smallWorkid.trim();
    }

    public String getBigWorkid() {
        return bigWorkid;
    }

    public void setBigWorkid(String bigWorkid) {
        this.bigWorkid = bigWorkid == null ? null : bigWorkid.trim();
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension == null ? null : extension.trim();
    }

    public String getRecordFilename() {
        return recordFilename;
    }

    public void setRecordFilename(String recordFilename) {
        this.recordFilename = recordFilename == null ? null : recordFilename.trim();
    }

    public String getIsTask() {
        return isTask;
    }

    public void setIsTask(String isTask) {
        this.isTask = isTask == null ? null : isTask.trim();
    }

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl == null ? null : recordUrl.trim();
    }

	public RecordInfo(Integer recordId, String reservedThree,
			String directionFlag, String callerid, String calledid,
			String startTime, String stopTime, Integer recordLength,
			String smallWorkid, String bigWorkid, String agentName,
			Integer groupId, String groupName, String extension,
			String recordFilename, String isTask, String recordUrl) {
		super();
		this.recordId = recordId;
		this.reservedThree = reservedThree;
		this.directionFlag = directionFlag;
		this.callerid = callerid;
		this.calledid = calledid;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.recordLength = recordLength;
		this.smallWorkid = smallWorkid;
		this.bigWorkid = bigWorkid;
		this.agentName = agentName;
		this.groupId = groupId;
		this.groupName = groupName;
		this.extension = extension;
		this.recordFilename = recordFilename;
		this.isTask = isTask;
		this.recordUrl = recordUrl;
	}

	public RecordInfo() {
		super();
	}

	public Integer getRecordLengthmin() {
		return recordLengthmin;
	}

	public void setRecordLengthmin(Integer recordLengthmin) {
		this.recordLengthmin = recordLengthmin;
	}

	public Integer getRecordLengthmax() {
		return recordLengthmax;
	}

	public void setRecordLengthmax(Integer recordLengthmax) {
		this.recordLengthmax = recordLengthmax;
	}

	public Integer getIsQC() {
		return isQC;
	}

	public void setIsQC(Integer isQC) {
		this.isQC = isQC;
	}
    
    
    
}