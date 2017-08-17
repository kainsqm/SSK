package cn.sh.ideal.model;


public class SysRole {
    private Integer roleId;

    private String roleName;

    private String roleFlag;

    private String isDisplay;

    private String enabled;

    private Integer createUserId;

    private String createTime;

    private Integer updateUserId;

    private String updateTime;
    
    private String remark;
    private String roleFunctionid;
    
    private String pid; //父节点ID
    
    private String system;  //系统
    
    private String code; //标识
    
    private String systemName;  //所属系统
    
    

    public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName == null ? "" : roleName.trim();
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleFlag() {
        return roleFlag;
    }

    public void setRoleFlag(String roleFlag) {
        this.roleFlag = roleFlag == null ? null : roleFlag.trim();
    }

    public String getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay == null ? null : isDisplay.trim();
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

	public String getRemark() {
		return remark == null ?"" : remark.trim();
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRoleFunctionid() {
		return roleFunctionid;
	}

	public void setRoleFunctionid(String roleFunctionid) {
		this.roleFunctionid = roleFunctionid;
	}
    
    
}