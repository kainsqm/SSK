package cn.sh.ideal.model;

import java.util.Date;

public class tOperateLog {
    private Integer pkAutoId;

    private Integer operateUserid;

    private String operateWorkid;

    private Date operateDate;

    private String ip;

    private String modelName;

    private String functionName;

    private String operateContent;

    private Integer zuhuId;

    public Integer getPkAutoId() {
        return pkAutoId;
    }

    public void setPkAutoId(Integer pkAutoId) {
        this.pkAutoId = pkAutoId;
    }

    public Integer getOperateUserid() {
        return operateUserid;
    }

    public void setOperateUserid(Integer operateUserid) {
        this.operateUserid = operateUserid;
    }

    public String getOperateWorkid() {
        return operateWorkid;
    }

    public void setOperateWorkid(String operateWorkid) {
        this.operateWorkid = operateWorkid == null ? null : operateWorkid.trim();
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName == null ? null : modelName.trim();
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName == null ? null : functionName.trim();
    }

    public String getOperateContent() {
        return operateContent;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent == null ? null : operateContent.trim();
    }

    public Integer getZuhuId() {
        return zuhuId;
    }

    public void setZuhuId(Integer zuhuId) {
        this.zuhuId = zuhuId;
    }
}