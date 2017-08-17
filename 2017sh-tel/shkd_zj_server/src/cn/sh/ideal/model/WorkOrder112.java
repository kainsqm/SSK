package cn.sh.ideal.model;



public class WorkOrder112 {
    private Integer workorderId;  //主键 流水

    private String errorNo; //故障号

    private String isGw;//是否光网

    private String rc; //区局

    private String branch; //分局

    private String sites; //站点

    private String declarationTime; //申告时间
    private String declarationTimestart; //
    private String  declarationTimeend;

    private String businessType; //业务类型

    private String declarationBigType; //申告大类

    private String declarationDescription; //申告现象

    private String declarationRemark; //申告备注

    private String testCode; //测试代码

    private String testResult; //测试结果

    private String declarationLinkinfo; //申告联系信息

    private String isSecondRedeal; //是否二级预处理

    private String closeedWay; //结案方式

    private int closeedTimelength;  //结案历时

    private String acceptedSource; //受理来源

    private String firstAgentUserid; //一级受理员

    private String firstRedealWorkid; //一级预处理工号

    private String firstRedealSuggest; //一级预处理建议

    private String firstRedealClosedcode; //一级预处理结案代码

    private String secondRedealClosedcode; //二级预处理结案代码

    private String secondRedealRemark;//二级预处理备注

    private String secondWorkid; //二级工号

    private String secondClosetime; //二级结案时间

    private String repairTime; //派修时间

    private String repairWorkid;//派修工号

    private String repairDirection; //派修方向

    private String repairCenter; //派修中心

    private String threeErrorType; //三级故障大类

    private String threeErrorRepairCode; //三级故障修复代码

    private String threeWorkid; //三级工号

    private String threeRepairTime; //三级修复时间
 /*   是否关联*/
    private String isGl;
   /* 是够质检*/
    private Integer isQC;
    
    
    private int start;//开始条数
    private int end;//结束条数

    public Integer getWorkorderId() {
        return workorderId;
    }

    public void setWorkorderId(Integer workorderId) {
        this.workorderId = workorderId;
    }

    public String getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo == null ? null : errorNo.trim();
    }

    public String getIsGw() {
        return isGw;
    }

    public void setIsGw(String isGw) {
        this.isGw = isGw == null ? null : isGw.trim();
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc == null ? null : rc.trim();
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch == null ? null : branch.trim();
    }

    public String getSites() {
        return sites;
    }

    public void setSites(String sites) {
        this.sites = sites == null ? null : sites.trim();
    }

    public String getDeclarationTime() {
        return declarationTime;
    }

    public void setDeclarationTime(String declarationTime) {
        this.declarationTime = declarationTime;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getDeclarationBigType() {
        return declarationBigType;
    }

    public void setDeclarationBigType(String declarationBigType) {
        this.declarationBigType = declarationBigType == null ? null : declarationBigType.trim();
    }

    public String getDeclarationDescription() {
        return declarationDescription;
    }

    public void setDeclarationDescription(String declarationDescription) {
        this.declarationDescription = declarationDescription == null ? null : declarationDescription.trim();
    }

    public String getDeclarationRemark() {
        return declarationRemark;
    }

    public void setDeclarationRemark(String declarationRemark) {
        this.declarationRemark = declarationRemark == null ? null : declarationRemark.trim();
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode == null ? null : testCode.trim();
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult == null ? null : testResult.trim();
    }

    public String getDeclarationLinkinfo() {
        return declarationLinkinfo;
    }

    public void setDeclarationLinkinfo(String declarationLinkinfo) {
        this.declarationLinkinfo = declarationLinkinfo == null ? null : declarationLinkinfo.trim();
    }

    public String getIsSecondRedeal() {
        return isSecondRedeal;
    }

    public void setIsSecondRedeal(String isSecondRedeal) {
        this.isSecondRedeal = isSecondRedeal == null ? null : isSecondRedeal.trim();
    }

    public String getCloseedWay() {
        return closeedWay;
    }

    public void setCloseedWay(String closeedWay) {
        this.closeedWay = closeedWay == null ? null : closeedWay.trim();
    }

    public int getCloseedTimelength() {
        return closeedTimelength;
    }

    public void setCloseedTimelength(int closeedTimelength) {
        this.closeedTimelength = closeedTimelength;
    }

    public String getAcceptedSource() {
        return acceptedSource;
    }

    public void setAcceptedSource(String acceptedSource) {
        this.acceptedSource = acceptedSource == null ? null : acceptedSource.trim();
    }

    public String getFirstAgentUserid() {
        return firstAgentUserid;
    }

    public void setFirstAgentUserid(String firstAgentUserid) {
        this.firstAgentUserid = firstAgentUserid == null ? null : firstAgentUserid.trim();
    }

    public String getFirstRedealWorkid() {
        return firstRedealWorkid;
    }

    public void setFirstRedealWorkid(String firstRedealWorkid) {
        this.firstRedealWorkid = firstRedealWorkid == null ? null : firstRedealWorkid.trim();
    }

    public String getFirstRedealSuggest() {
        return firstRedealSuggest;
    }

    public void setFirstRedealSuggest(String firstRedealSuggest) {
        this.firstRedealSuggest = firstRedealSuggest == null ? null : firstRedealSuggest.trim();
    }

    public String getFirstRedealClosedcode() {
        return firstRedealClosedcode;
    }

    public void setFirstRedealClosedcode(String firstRedealClosedcode) {
        this.firstRedealClosedcode = firstRedealClosedcode == null ? null : firstRedealClosedcode.trim();
    }

    public String getSecondRedealClosedcode() {
        return secondRedealClosedcode;
    }

    public void setSecondRedealClosedcode(String secondRedealClosedcode) {
        this.secondRedealClosedcode = secondRedealClosedcode == null ? null : secondRedealClosedcode.trim();
    }

    public String getSecondRedealRemark() {
        return secondRedealRemark;
    }

    public void setSecondRedealRemark(String secondRedealRemark) {
        this.secondRedealRemark = secondRedealRemark == null ? null : secondRedealRemark.trim();
    }

    public String getSecondWorkid() {
        return secondWorkid;
    }

    public void setSecondWorkid(String secondWorkid) {
        this.secondWorkid = secondWorkid == null ? null : secondWorkid.trim();
    }

    public String getSecondClosetime() {
        return secondClosetime;
    }

    public void setSecondClosetime(String secondClosetime) {
        this.secondClosetime = secondClosetime;
    }

    public String getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(String repairTime) {
        this.repairTime = repairTime;
    }

    public String getRepairWorkid() {
        return repairWorkid;
    }

    public void setRepairWorkid(String repairWorkid) {
        this.repairWorkid = repairWorkid == null ? null : repairWorkid.trim();
    }

    public String getRepairDirection() {
        return repairDirection;
    }

    public void setRepairDirection(String repairDirection) {
        this.repairDirection = repairDirection == null ? null : repairDirection.trim();
    }

    public String getRepairCenter() {
        return repairCenter;
    }

    public void setRepairCenter(String repairCenter) {
        this.repairCenter = repairCenter == null ? null : repairCenter.trim();
    }

    public String getThreeErrorType() {
        return threeErrorType;
    }

    public void setThreeErrorType(String threeErrorType) {
        this.threeErrorType = threeErrorType == null ? null : threeErrorType.trim();
    }

    public String getThreeErrorRepairCode() {
        return threeErrorRepairCode;
    }

    public void setThreeErrorRepairCode(String threeErrorRepairCode) {
        this.threeErrorRepairCode = threeErrorRepairCode == null ? null : threeErrorRepairCode.trim();
    }

    public String getThreeWorkid() {
        return threeWorkid;
    }

    public void setThreeWorkid(String threeWorkid) {
        this.threeWorkid = threeWorkid == null ? null : threeWorkid.trim();
    }

    public String getThreeRepairTime() {
        return threeRepairTime;
    }

    public void setThreeRepairTime(String threeRepairTime) {
        this.threeRepairTime = threeRepairTime;
    }

	public WorkOrder112(Integer workorderId, String errorNo, String isGw,
			String rc, String branch, String sites, String declarationTime,
			String businessType, String declarationBigType,
			String declarationDescription, String declarationRemark,
			String testCode, String testResult, String declarationLinkinfo,
			String isSecondRedeal, String closeedWay, int closeedTimelength,
			String acceptedSource, String firstAgentUserid,
			String firstRedealWorkid, String firstRedealSuggest,
			String firstRedealClosedcode, String secondRedealClosedcode,
			String secondRedealRemark, String secondWorkid,
			String secondClosetime, String repairTime, String repairWorkid,
			String repairDirection, String repairCenter, String threeErrorType,
			String threeErrorRepairCode, String threeWorkid,
			String threeRepairTime) {
		super();
		this.workorderId = workorderId;
		this.errorNo = errorNo;
		this.isGw = isGw;
		this.rc = rc;
		this.branch = branch;
		this.sites = sites;
		this.declarationTime = declarationTime;
		this.businessType = businessType;
		this.declarationBigType = declarationBigType;
		this.declarationDescription = declarationDescription;
		this.declarationRemark = declarationRemark;
		this.testCode = testCode;
		this.testResult = testResult;
		this.declarationLinkinfo = declarationLinkinfo;
		this.isSecondRedeal = isSecondRedeal;
		this.closeedWay = closeedWay;
		this.closeedTimelength = closeedTimelength;
		this.acceptedSource = acceptedSource;
		this.firstAgentUserid = firstAgentUserid;
		this.firstRedealWorkid = firstRedealWorkid;
		this.firstRedealSuggest = firstRedealSuggest;
		this.firstRedealClosedcode = firstRedealClosedcode;
		this.secondRedealClosedcode = secondRedealClosedcode;
		this.secondRedealRemark = secondRedealRemark;
		this.secondWorkid = secondWorkid;
		this.secondClosetime = secondClosetime;
		this.repairTime = repairTime;
		this.repairWorkid = repairWorkid;
		this.repairDirection = repairDirection;
		this.repairCenter = repairCenter;
		this.threeErrorType = threeErrorType;
		this.threeErrorRepairCode = threeErrorRepairCode;
		this.threeWorkid = threeWorkid;
		this.threeRepairTime = threeRepairTime;
	}

	public WorkOrder112() {
		super();
	}

	public String getDeclarationTimestart() {
		return declarationTimestart;
	}

	public void setDeclarationTimestart(String declarationTimestart) {
		this.declarationTimestart = declarationTimestart;
	}

	public String getDeclarationTimeend() {
		return declarationTimeend;
	}

	public void setDeclarationTimeend(String declarationTimeend) {
		this.declarationTimeend = declarationTimeend;
	}

	public String getIsGl() {
		return isGl;
	}

	public void setIsGl(String isGl) {
		this.isGl = isGl;
	}

	public Integer getIsQC() {
		return isQC;
	}

	public void setIsQC(Integer isQC) {
		this.isQC = isQC;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	
	
	
    
}