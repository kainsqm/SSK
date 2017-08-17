package cn.sh.ideal.model;

import java.util.List;

public class tSysCode {
    private Integer pkAutoId;

    private String itemFlag;

    private String name;

    private String value;

    private Integer pid;

    private Integer codeOrder;

    private String isDisplay;

    private String remark;

    private String enabled;

    private Integer zuhuId;
    
    private String[] strList;


    public String[] getStrList() {
        return strList;
    }

    public void setStrList(String[] strList) {
        this.strList = strList;
    }

    @Override
	public String toString() {
		return "tSysCode [pkAutoId=" + pkAutoId + ", itemFlag=" + itemFlag
				+ ", name=" + name + ", value=" + value + ", pid=" + pid
				+ ", codeOrder=" + codeOrder + ", isDisplay=" + isDisplay
				+ ", remark=" + remark + ", enabled=" + enabled + ", zuhuId="
				+ zuhuId + "]";
	}

	public Integer getPkAutoId() {
        return pkAutoId;
    }

    public void setPkAutoId(Integer pkAutoId) {
        this.pkAutoId = pkAutoId;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCodeOrder() {
        return codeOrder;
    }

    public void setCodeOrder(Integer codeOrder) {
        this.codeOrder = codeOrder;
    }

    public String getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay == null ? null : isDisplay.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

    public Integer getZuhuId() {
        return zuhuId;
    }

    public void setZuhuId(Integer zuhuId) {
        this.zuhuId = zuhuId;
    }
}