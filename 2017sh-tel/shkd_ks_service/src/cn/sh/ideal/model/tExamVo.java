package cn.sh.ideal.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class tExamVo {
    private Integer pkAutoId;
    private String examName;
    private Date toExamBeginTime;
    private Date toExamEndTime;
    private Integer examTimeLength;
    private Integer examNormalScore;
    private String examQuery;
    private String toExamQuery;
    private String examStatus;
    private Integer fkInsertUserId;
    private String insertTime;
    private Integer fkUpdateUserId;
    private String updateTime;
    private String toExamtype;
    private String toExamtarget;
    private String examPaperScore;
    private Integer zuhuId;
    
    //---
    private String examPaperName;
    private String listExamUser;
    private String listUserID;
    private Integer examPaperId;
    private String listUserIDs;
    private String listUserNames;
    
    private String addUserId;
    private String deleteUserId;
    
    public String getAddUserId() {
        return addUserId;
    }
    public void setAddUserId(String addUserId) {
        this.addUserId = addUserId;
    }
    public String getDeleteUserId() {
        return deleteUserId;
    }
    public void setDeleteUserId(String deleteUserId) {
        this.deleteUserId = deleteUserId;
    }
    public String getListUserNames() {
        return listUserNames;
    }
    public void setListUserNames(String listUserNames) {
        this.listUserNames = listUserNames;
    }
    public String getListUserIDs() {
        return listUserIDs;
    }
    public void setListUserIDs(String listUserIDs) {
        this.listUserIDs = listUserIDs;
    }
    
    public Integer getPkAutoId() {
        return pkAutoId;
    }
    public void setPkAutoId(Integer pkAutoId) {
        this.pkAutoId = pkAutoId;
    }
    public String getExamName() {
        return examName;
    }
    public void setExamName(String examName) {
        this.examName = examName;
    }
    public Date getToExamBeginTime() {
        return toExamBeginTime;
    }
    public void setToExamBeginTime(Date toExamBeginTime) {
        this.toExamBeginTime = toExamBeginTime;
    }
    public Date getToExamEndTime() {
        return toExamEndTime;
    }
    public void setToExamEndTime(Date toExamEndTime) {
        this.toExamEndTime = toExamEndTime;
    }
    public Integer getExamTimeLength() {
        return examTimeLength;
    }
    public void setExamTimeLength(Integer examTimeLength) {
        this.examTimeLength = examTimeLength;
    }
    public Integer getExamNormalScore() {
        return examNormalScore;
    }
    public void setExamNormalScore(Integer examNormalScore) {
        this.examNormalScore = examNormalScore;
    }
    public String getExamQuery() {
        return examQuery;
    }
    public void setExamQuery(String examQuery) {
        this.examQuery = examQuery;
    }
    public String getToExamQuery() {
        return toExamQuery;
    }
    public void setToExamQuery(String toExamQuery) {
        this.toExamQuery = toExamQuery;
    }
    public String getExamStatus() {
        return examStatus;
    }
    public void setExamStatus(String examStatus) {
        this.examStatus = examStatus;
    }
    public Integer getFkInsertUserId() {
        return fkInsertUserId;
    }
    public void setFkInsertUserId(Integer fkInsertUserId) {
        this.fkInsertUserId = fkInsertUserId;
    }
    public String getInsertTime() {
        return insertTime;
    }
    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }
    public Integer getFkUpdateUserId() {
        return fkUpdateUserId;
    }
    public void setFkUpdateUserId(Integer fkUpdateUserId) {
        this.fkUpdateUserId = fkUpdateUserId;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public String getToExamtype() {
        return toExamtype;
    }
    public void setToExamtype(String toExamtype) {
        this.toExamtype = toExamtype;
    }
    public String getToExamtarget() {
        return toExamtarget;
    }
    public void setToExamtarget(String toExamtarget) {
        this.toExamtarget = toExamtarget;
    }
    public String getExamPaperScore() {
        return examPaperScore;
    }
    public void setExamPaperScore(String examPaperScore) {
        this.examPaperScore = examPaperScore;
    }
    public Integer getZuhuId() {
        return zuhuId;
    }
    public void setZuhuId(Integer zuhuId) {
        this.zuhuId = zuhuId;
    }
    public String getExamPaperName() {
        return examPaperName;
    }
    public void setExamPaperName(String examPaperName) {
        this.examPaperName = examPaperName;
    }
    public String getListExamUser() {
        return listExamUser;
    }
    public void setListExamUser(String listExamUser) {
        this.listExamUser = listExamUser;
    }
    public String getListUserID() {
        return listUserID;
    }
    public void setListUserID(String listUserID) {
        this.listUserID = listUserID;
    }
    public Integer getExamPaperId() {
        return examPaperId;
    }
    public void setExamPaperId(Integer examPaperId) {
        this.examPaperId = examPaperId;
    }
    
    public String getToExamBeginTimeString() {
        if(toExamBeginTime!=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(toExamBeginTime);
        }
        return "";
    }
    
    public String getToExamEndTimeString() {
        if(toExamEndTime!=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(toExamEndTime);
        }
        return "";
    }
}