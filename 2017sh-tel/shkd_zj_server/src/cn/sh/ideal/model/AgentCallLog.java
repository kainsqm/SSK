package cn.sh.ideal.model;

import java.util.Date;

public class AgentCallLog {

	/*
	 * 通话记录相关
	 */
	
	/**
	 * TASK
	 * **/
	private String isQc;
	private String createTime;
	private String createTimeTasrec;
	private String cDate;
	
	
	
	private String recordReference; // 录音流水号

	private String voiceIP; // 录音服务器IP地址

	private String rootDisk; // 录音文件保存盘符

	private String reservedThree; // callid (备用字段三)
	
	private String voiceID; //录音服务器编号
	
	private String channel; //分机通道号

	private Date startRecordTime; // 开始录音时间

	private Date stopRecordTime; // 结束录音时间
	
	
	private String startTime;
	private String stopTime; 
	

	private Integer recordLength; // 录音时长

	private String workID; // 工号
	
	private String userAutoId; //话务员Id

	private String callerID; // 主叫号码

	private String calledID; // 被叫号码

	private String extension; // 分机号码

	private String directionFlag; // 电话方向

	private String call_busi_tp; // 商客大类;公客大类

	private String call_busi_sub_tp; // 商客小类;公客小类

	private String ivr_source; // IVR来源

	private String contentment_investigate; // 满意度调查
	
	private String evt_Act_Title; // 事务标题
	private String isOK;
	private String qc_User_ID;// 质检员USERID

	private Date date_input; // 质检日期
	private String recordId;
	private String satisfy;//满意度
	private String danjunumber;
	
	private String roleID;

	private String agent_Type;

	private String agentGroupName;
	private String agentGroupID;
	
	private String responType;
	private String callType;
	
	private String call_prom_tp;
	private String call_prom_sub_tp;
	
	private String agentWorkID;
	
	private String agentgroupname;
	
	private String loginname;
	
	private String recordStr = "0分0秒";
	
	private String isDisplay;//标记是否有申请校验
	
	//ftp下载路径
	private String ftpDownLoadPath;
	
	private String agentPlatform;
	private String agentPosttype;
	private String agentUserAutoid;
	private String agentGroupid;
	private String agentGroupname;
	private String agentName;
	private String kdxfUrl;
	private String infractionType;
	
	
	private String taskFlag;//是否为任务录音  lfj 2012-08-15 新增属性
	
	private String recordType;//语音渠道( 1 vip ; 2 星级服务)
	
	private String voiceUploadFlag; //语音上传状态（未上传为0，上传中为1，上传成功为2）
	
	private String voiceAnalyseFlag; //语音解析状态（1.未解析，2.解析中，3.已解析）
	
	private String voiceUploadPath; //语音上传路径
	
	private String voiceResultPath; //语音解析结果路径
	
	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentPlatform() {
		return agentPlatform;
	}

	public void setAgentPlatform(String agentPlatform) {
		this.agentPlatform = agentPlatform;
	}

	
	
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getAgentPosttype() {
		return agentPosttype;
	}

	public void setAgentPosttype(String agentPosttype) {
		this.agentPosttype = agentPosttype;
	}

	public String getAgentUserAutoid() {
		return agentUserAutoid;
	}

	
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	
	

	public String getIsQc() {
		return isQc;
	}

	public void setIsQc(String isQc) {
		this.isQc = isQc;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeTasrec() {
		return createTimeTasrec;
	}

	public void setCreateTimeTasrec(String createTimeTasrec) {
		this.createTimeTasrec = createTimeTasrec;
	}

	public String getcDate() {
		return cDate;
	}

	public void setcDate(String cDate) {
		this.cDate = cDate;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public void setAgentUserAutoid(String agentUserAutoid) {
		this.agentUserAutoid = agentUserAutoid;
	}

	public String getAgentGroupid() {
		return agentGroupid;
	}

	public void setAgentGroupid(String agentGroupid) {
		this.agentGroupid = agentGroupid;
	}

	public String getAgentGroupname() {
		return agentGroupname;
	}

	public void setAgentGroupname(String agentGroupname) {
		this.agentGroupname = agentGroupname;
	}

	public String getResponType() {
		return responType;
	}

	public void setResponType(String responType) {
		this.responType = responType;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getAgent_Type() {
		return agent_Type;
	}

	public void setAgent_Type(String agent_Type) {
		this.agent_Type = agent_Type;
	}

	public String getCall_busi_sub_tp() {
		return call_busi_sub_tp;
	}

	public void setCall_busi_sub_tp(String call_busi_sub_tp) {
		this.call_busi_sub_tp = call_busi_sub_tp;
	}

	public String getCall_busi_tp() {
		return call_busi_tp;
	}

	public void setCall_busi_tp(String call_busi_tp) {
		this.call_busi_tp = call_busi_tp;
	}

	public String getCalledID() {
		return calledID;
	}

	public void setCalledID(String calledID) {
		this.calledID = calledID;
	}

	public String getCallerID() {
		return callerID;
	}

	public void setCallerID(String callerID) {
		this.callerID = callerID;
	}

	public String getContentment_investigate() {
		return contentment_investigate;
	}

	public void setContentment_investigate(String contentment_investigate) {
		this.contentment_investigate = contentment_investigate;
	}

	public Date getDate_input() {
		return date_input;
	}

	public void setDate_input(Date date_input) {
		this.date_input = date_input;
	}

	public String getDirectionFlag() {
		return directionFlag;
	}

	public void setDirectionFlag(String directionFlag) {
		this.directionFlag = directionFlag;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getIvr_source() {
		return ivr_source;
	}

	public void setIvr_source(String ivr_source) {
		this.ivr_source = ivr_source;
	}

	public Integer getRecordLength() {
		return recordLength;
	}

	public void setRecordLength(Integer recordLength) {
		this.recordLength = recordLength;
	}

	public String getRecordReference() {
		return recordReference;
	}

	public void setRecordReference(String recordReference) {
		this.recordReference = recordReference;
	}

	public String getReservedThree() {
		return reservedThree;
	}

	public void setReservedThree(String reservedThree) {
		this.reservedThree = reservedThree;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String getRootDisk() {
		return rootDisk;
	}

	public void setRootDisk(String rootDisk) {
		this.rootDisk = rootDisk;
	}

	public Date getStartRecordTime() {
		return startRecordTime;
	}

	public void setStartRecordTime(Date startRecordTime) {
		this.startRecordTime = startRecordTime;
	}

	public Date getStopRecordTime() {
		return stopRecordTime;
	}

	public void setStopRecordTime(Date stopRecordTime) {
		this.stopRecordTime = stopRecordTime;
	}

	public String getVoiceIP() {
		return voiceIP;
	}

	public void setVoiceIP(String voiceIP) {
		this.voiceIP = voiceIP;
	}

	public String getWorkID() {
		return workID;
	}

	public void setWorkID(String workID) {
		this.workID = workID;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getVoiceID() {
		return voiceID;
	}

	public void setVoiceID(String voiceID) {
		this.voiceID = voiceID;
	}

	public String getQc_User_ID() {
		return qc_User_ID;
	}

	public void setQc_User_ID(String qc_User_ID) {
		this.qc_User_ID = qc_User_ID;
	}

	public String getEvt_Act_Title() {
		return evt_Act_Title;
	}

	public void setEvt_Act_Title(String evt_Act_Title) {
		this.evt_Act_Title = evt_Act_Title;
	}

	public String getAgentGroupID() {
		return agentGroupID;
	}

	public void setAgentGroupID(String agentGroupID) {
		this.agentGroupID = agentGroupID;
	}

	public String getAgentGroupName() {
		return agentGroupName;
	}

	public void setAgentGroupName(String agentGroupName) {
		this.agentGroupName = agentGroupName;
	}

	public String getCall_prom_tp() {
		return call_prom_tp;
	}

	public void setCall_prom_tp(String call_prom_tp) {
		this.call_prom_tp = call_prom_tp;
	}

	public String getCall_prom_sub_tp() {
		return call_prom_sub_tp;
	}

	public void setCall_prom_sub_tp(String call_prom_sub_tp) {
		this.call_prom_sub_tp = call_prom_sub_tp;
	}

	public String getAgentgroupname() {
		return agentgroupname;
	}

	public void setAgentgroupname(String agentgroupname) {
		this.agentgroupname = agentgroupname;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getFtpDownLoadPath() {
		return ftpDownLoadPath;
	}

	public void setFtpDownLoadPath(String ftpDownLoadPath) {
		this.ftpDownLoadPath = ftpDownLoadPath;
	}


	public String getRecordStr() {
		if(this.getRecordLength() != null){
			recordStr = (this.getRecordLength()-this.getRecordLength()%60)/60 + "分" + this.getRecordLength()%60 + "秒";
		}
			return recordStr;
	}

	public void setRecordStr(String recordStr) {
		this.recordStr = recordStr;
	}

	public String getIsOK() {
		return isOK;
	}

	public void setIsOK(String isOK) {
		this.isOK = isOK;
	}

	public String getKdxfUrl() {
		return kdxfUrl;
	}

	public void setKdxfUrl(String kdxfUrl) {
		this.kdxfUrl = kdxfUrl;
	}

	public String getInfractionType() {
		return infractionType;
	}

	public void setInfractionType(String infractionType) {
		this.infractionType = infractionType;
	}

	public String getTaskFlag() {
		return taskFlag;
	}

	public void setTaskFlag(String taskFlag) {
		this.taskFlag = taskFlag;
	}

	public String getSatisfy() {
		return satisfy;
	}

	public void setSatisfy(String satisfy) {
		this.satisfy = satisfy;
	}
	
	public String getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}

	public String getUserAutoId() {
		return userAutoId;
	}

	public void setUserAutoId(String userAutoId) {
		this.userAutoId = userAutoId;
	}

	public String getAgentWorkID() {
		return agentWorkID;
	}

	public void setAgentWorkID(String agentWorkID) {
		this.agentWorkID = agentWorkID;
	}

	public String getVoiceUploadFlag() {
		return voiceUploadFlag;
	}

	public void setVoiceUploadFlag(String voiceUploadFlag) {
		this.voiceUploadFlag = voiceUploadFlag;
	}

	public String getVoiceAnalyseFlag() {
		return voiceAnalyseFlag;
	}

	public void setVoiceAnalyseFlag(String voiceAnalyseFlag) {
		this.voiceAnalyseFlag = voiceAnalyseFlag;
	}

	public String getVoiceUploadPath() {
		return voiceUploadPath;
	}

	public void setVoiceUploadPath(String voiceUploadPath) {
		this.voiceUploadPath = voiceUploadPath;
	}

	public String getVoiceResultPath() {
		return voiceResultPath;
	}

	public void setVoiceResultPath(String voiceResultPath) {
		this.voiceResultPath = voiceResultPath;
	}

	public String getDanjunumber() {
		return danjunumber;
	}

	public void setDanjunumber(String danjunumber) {
		this.danjunumber = danjunumber;
	}
	
	
	
}
