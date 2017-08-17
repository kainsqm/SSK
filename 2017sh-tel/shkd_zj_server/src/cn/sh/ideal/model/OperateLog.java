package cn.sh.ideal.model;


public class OperateLog {
    private Integer autoId;

    private Integer operateUserid;//操作人ID

    private String operateUsername;//操作人姓名

    private String operateDate;//操作时间

    private String ip;//操作人IP

    private String moduleName;//模块名称

    private String functionName;//功能名称

    private String methodName;//请求的方法名

    private String className;//请求的类名

    private String operateContent;//操作内容

    private String remark;
    private  String workId;//操作人workid
    private String startoperateDate;
    private String stopoperateDate;
    private String code; //系统
    
    
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStartoperateDate() {
		return startoperateDate;
	}

	public void setStartoperateDate(String startoperateDate) {
		this.startoperateDate = startoperateDate;
	}

	public String getStopoperateDate() {
		return stopoperateDate;
	}

	public void setStopoperateDate(String stopoperateDate) {
		this.stopoperateDate = stopoperateDate;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public Integer getAutoId() {
        return autoId;
    }

    public void setAutoId(Integer autoId) {
        this.autoId = autoId;
    }

    public Integer getOperateUserid() {
        return operateUserid;
    }

    public void setOperateUserid(Integer operateUserid) {
        this.operateUserid = operateUserid;
    }

    public String getOperateUsername() {
        return operateUsername;
    }

    public void setOperateUsername(String operateUsername) {
        this.operateUsername = operateUsername == null ? null : operateUsername.trim();
    }

   

    public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName == null ? null : functionName.trim();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getOperateContent() {
        return operateContent;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent == null ? null : operateContent.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    
    public OperateLog(Integer operateUserid, String operateUsername, String operateDate,
			String ip, String moduleName, String functionName, String operateContent,
			String className, String methodName,String code) {
		super();
		this.operateUserid = operateUserid;
		this.operateUsername = operateUsername;
		this.operateDate = operateDate;
		this.ip = ip;
		this.moduleName = moduleName;
		this.functionName = functionName;
		this.operateContent = operateContent;
		this.className = className;
		this.methodName = methodName;
		this.code=code;
	}

	public OperateLog() {
		super();
	}
    
    
}