package cn.sh.ideal.model;

public class vSysuser {
    private Integer userId;

    private String workId;
    private String roleId;

    private String roleFlag;
    
    
    
    public String getRoleFlag() {
		return roleFlag;
	}

	public void setRoleFlag(String roleFlag) {
		this.roleFlag = roleFlag;
	}

	public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    private String userName;

    private String groupName;
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    private String job;
    private String token;
    private String listFun;
    private String listKsFun;
    private String listReportFun;
    
    
    
    
    public String getListReportFun() {
		return listReportFun;
	}

	public void setListReportFun(String listReportFun) {
		this.listReportFun = listReportFun;
	}

	public String getListKsFun() {
		return listKsFun;
	}

	public void setListKsFun(String listKsFun) {
		this.listKsFun = listKsFun;
	}

	public vSysuser(){
        
    }
    
    public vSysuser(Integer userId, String workId, String userName, String groupName, String job) {
        super();
        this.userId = userId;
        this.workId = workId;
        this.userName = userName;
        this.groupName = groupName;
        this.job = job;
    }



    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getListFun() {
		return listFun;
	}

	public void setListFun(String listFun) {
		this.listFun = listFun;
	}

	public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }
}