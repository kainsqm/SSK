package cn.sh.ideal.model;

public class SysCode {
    private Integer autoId;

    private String itemFlag;

    private String name;

    private String valuees;

    private Integer pid;

    private String orders;

    private String isDisplay;

    private String type;

    private String remark;

    private String isUsed;
    private String isTl9000;

    public Integer getAutoId() {
        return autoId;
    }

    public void setAutoId(Integer autoId) {
        this.autoId = autoId;
    }

    public String getItemFlag() {
        return itemFlag;
    }

    public void setItemFlag(String itemFlag) {
        this.itemFlag = itemFlag == null ? null : itemFlag.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getValuees() {
        return valuees;
    }

    public void setValuees(String valuees) {
        this.valuees = valuees == null ? null : valuees.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders == null ? null : orders.trim();
    }

    public String getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay == null ? null : isDisplay.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed == null ? null : isUsed.trim();
    }

	public String getIsTl9000() {
		return isTl9000;
	}

	public void setIsTl9000(String isTl9000) {
		this.isTl9000 = isTl9000;
	}
    
    
}