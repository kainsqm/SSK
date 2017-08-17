package cn.sh.ideal.model;

import java.util.Date;

public class WorkOrderCdma {
    private Integer serialCdma;//112流水

    private String appealStatus;//业务状态

    private String dirNum;//号码

    private String wxSerial;//WX编号

    private String complaintTime;//申告时间

    private String complaintType;//申告业务大类

    private String complaintService;//申告业务小类

    private String complaintReason;//申告现象

    private String appealingNum;//联系电话

    private String receiptOpId;//一级受理员

    private String pretreatType;//一级处理方式

    private String pretreatResult1;//一级结案代码

    private String pretreatTime;//一级结案时间

    private String pretreatRemark;//一级备注

    private String tlOpId;//二级抽单员

    private String tlTime;//二级抽单时间

    private String complaintType2;//二级申告业务大类

    private String complaintService2;//二级申告业务小类

    private String complaintReason2;//二级申告现象

    private String isZhuan;//是否应该转二级

    private String pretreatRemark2;//二级备注

    private String deal;//处理周期

    private String pretreatType2;//二级处理方式

    private String pretreatOpId;//二级处理员

    private String pretreatDir;//二级处理电话

    private String dispatchTime;//二级结案派修时间

    private String pretreatResult2;//二级预处理结案代码

    private String repairTime;//二级WX修复确认时间

    private String repairOpId;//二级确认工号

    private String satisfy;//用户满意度

    private String isEnglish;//英文

    private String complaintAddr;//参考地址

    private String dispachOp;//派修人

    public Integer getSerialCdma() {
        return serialCdma;
    }

    public void setSerialCdma(Integer serialCdma) {
        this.serialCdma = serialCdma;
    }

    public String getAppealStatus() {
        return appealStatus;
    }

    public void setAppealStatus(String appealStatus) {
        this.appealStatus = appealStatus == null ? null : appealStatus.trim();
    }

    public String getDirNum() {
        return dirNum;
    }

    public void setDirNum(String dirNum) {
        this.dirNum = dirNum == null ? null : dirNum.trim();
    }

    public String getWxSerial() {
        return wxSerial;
    }

    public void setWxSerial(String wxSerial) {
        this.wxSerial = wxSerial == null ? null : wxSerial.trim();
    }

    public String getComplaintTime() {
        return complaintTime;
    }

    public void setComplaintTime(String complaintTime) {
        this.complaintTime = complaintTime;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType == null ? null : complaintType.trim();
    }

    public String getComplaintService() {
        return complaintService;
    }

    public void setComplaintService(String complaintService) {
        this.complaintService = complaintService == null ? null : complaintService.trim();
    }

    public String getComplaintReason() {
        return complaintReason;
    }

    public void setComplaintReason(String complaintReason) {
        this.complaintReason = complaintReason == null ? null : complaintReason.trim();
    }

    public String getAppealingNum() {
        return appealingNum;
    }

    public void setAppealingNum(String appealingNum) {
        this.appealingNum = appealingNum == null ? null : appealingNum.trim();
    }

    public String getReceiptOpId() {
        return receiptOpId;
    }

    public void setReceiptOpId(String receiptOpId) {
        this.receiptOpId = receiptOpId == null ? null : receiptOpId.trim();
    }

    public String getPretreatType() {
        return pretreatType;
    }

    public void setPretreatType(String pretreatType) {
        this.pretreatType = pretreatType == null ? null : pretreatType.trim();
    }

    public String getPretreatResult1() {
        return pretreatResult1;
    }

    public void setPretreatResult1(String pretreatResult1) {
        this.pretreatResult1 = pretreatResult1 == null ? null : pretreatResult1.trim();
    }

    public String getPretreatTime() {
        return pretreatTime;
    }

    public void setPretreatTime(String pretreatTime) {
        this.pretreatTime = pretreatTime;
    }

    public String getPretreatRemark() {
        return pretreatRemark;
    }

    public void setPretreatRemark(String pretreatRemark) {
        this.pretreatRemark = pretreatRemark == null ? null : pretreatRemark.trim();
    }

    public String getTlOpId() {
        return tlOpId;
    }

    public void setTlOpId(String tlOpId) {
        this.tlOpId = tlOpId == null ? null : tlOpId.trim();
    }

    public String getTlTime() {
        return tlTime;
    }

    public void setTlTime(String tlTime) {
        this.tlTime = tlTime;
    }

    public String getComplaintType2() {
        return complaintType2;
    }

    public void setComplaintType2(String complaintType2) {
        this.complaintType2 = complaintType2 == null ? null : complaintType2.trim();
    }

    public String getComplaintService2() {
        return complaintService2;
    }

    public void setComplaintService2(String complaintService2) {
        this.complaintService2 = complaintService2 == null ? null : complaintService2.trim();
    }

    public String getComplaintReason2() {
        return complaintReason2;
    }

    public void setComplaintReason2(String complaintReason2) {
        this.complaintReason2 = complaintReason2 == null ? null : complaintReason2.trim();
    }

    public String getIsZhuan() {
        return isZhuan;
    }

    public void setIsZhuan(String isZhuan) {
        this.isZhuan = isZhuan == null ? null : isZhuan.trim();
    }

    public String getPretreatRemark2() {
        return pretreatRemark2;
    }

    public void setPretreatRemark2(String pretreatRemark2) {
        this.pretreatRemark2 = pretreatRemark2 == null ? null : pretreatRemark2.trim();
    }

    public String getDeal() {
        return deal;
    }

    public void setDeal(String deal) {
        this.deal = deal == null ? null : deal.trim();
    }

    public String getPretreatType2() {
        return pretreatType2;
    }

    public void setPretreatType2(String pretreatType2) {
        this.pretreatType2 = pretreatType2 == null ? null : pretreatType2.trim();
    }

    public String getPretreatOpId() {
        return pretreatOpId;
    }

    public void setPretreatOpId(String pretreatOpId) {
        this.pretreatOpId = pretreatOpId == null ? null : pretreatOpId.trim();
    }

    public String getPretreatDir() {
        return pretreatDir;
    }

    public void setPretreatDir(String pretreatDir) {
        this.pretreatDir = pretreatDir == null ? null : pretreatDir.trim();
    }

    public String getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(String dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public String getPretreatResult2() {
        return pretreatResult2;
    }

    public void setPretreatResult2(String pretreatResult2) {
        this.pretreatResult2 = pretreatResult2 == null ? null : pretreatResult2.trim();
    }

    public String getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(String repairTime) {
        this.repairTime = repairTime;
    }

    public String getRepairOpId() {
        return repairOpId;
    }

    public void setRepairOpId(String repairOpId) {
        this.repairOpId = repairOpId == null ? null : repairOpId.trim();
    }

    public String getSatisfy() {
        return satisfy;
    }

    public void setSatisfy(String satisfy) {
        this.satisfy = satisfy == null ? null : satisfy.trim();
    }

    public String getIsEnglish() {
        return isEnglish;
    }

    public void setIsEnglish(String isEnglish) {
        this.isEnglish = isEnglish == null ? null : isEnglish.trim();
    }

    public String getComplaintAddr() {
        return complaintAddr;
    }

    public void setComplaintAddr(String complaintAddr) {
        this.complaintAddr = complaintAddr == null ? null : complaintAddr.trim();
    }

    public String getDispachOp() {
        return dispachOp;
    }

    public void setDispachOp(String dispachOp) {
        this.dispachOp = dispachOp == null ? null : dispachOp.trim();
    }
}