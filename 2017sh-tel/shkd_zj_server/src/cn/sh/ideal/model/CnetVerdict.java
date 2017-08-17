package cn.sh.ideal.model;


public class CnetVerdict {
	  	private Integer taskId;//任务id
	    private String taskName;//任务名称
	    private String qcUser; //质检员id,多个用逗号分隔
	    private String qcUserName; //质检员姓名,多个以逗号隔开
	    private String telUser; //受理员ID，多个用逗号
	    private String telUserName; //受理员姓名，多个用逗号分隔
	    private String taskStatus; //任务状态,状态包括:0未发布1待执行2执行中3已完成4未完成5强制关闭
	    private String enabled; //1有效,0无效
	    private String isPublish; //是否发布：1是，0否
	    private String taskStartTime; //任务开始时间
	    private String taskEndTime; //任务结束时间
	    private String csrTopDCount; //质检员任务规格/天
	    private String checkCount; //受理员被抽查限制次数/天
	    private String bussniessType; //业务类型(多个类型以分号隔开)
	    private String telsumType; //小结类型 (多个类型以分号隔开)
	    private String gzlyType; //故障来源 (多个类型以分号隔开)
	    private String createUserId; //创建人USERID
	    private String createTime; //创建时间
	    private String updateUserId; //修改人USERID
	    private String updateTime;  //修改时间
	    private String creatStartTime; //创建开始时间
	    private String creatEndTime; //创建结束时间
	    private String qcworkid;//创建人工号
	    private String eachQcuserStatus;//每个质检员的任务状态(1待执行2执行中3已完成4未完成)
	    private String getRecordCount;//领取录音数(六个挡，中间逗号隔开)
	    private String completeCount;//完成录音数(六个挡，中间逗号隔开) 
	    private String tasktype;//任务类型 1.112工单2.c网工单3.112小结4.c网小结5.录音
	    private int taskuserId;//任务质检员表主键id
	    private String cDate;//那天的任务(日期)
	    private String qcUserWorkId;  //质检员工号
	    private String papers_hid;
	    
	    
	    
	    
		public String getPapers_hid() {
			return papers_hid;
		}
		public void setPapers_hid(String papers_hid) {
			this.papers_hid = papers_hid;
		}
		public String getQcUserWorkId() {
			return qcUserWorkId;
		}
		public void setQcUserWorkId(String qcUserWorkId) {
			this.qcUserWorkId = qcUserWorkId;
		}
		public String getcDate() {
			return cDate;
		}
		public void setcDate(String cDate) {
			this.cDate = cDate;
		}
		public int getTaskuserId() {
			return taskuserId;
		}
		public void setTaskuserId(int taskuserId) {
			this.taskuserId = taskuserId;
		}
		public String getEachQcuserStatus() {
			return eachQcuserStatus;
		}
		public void setEachQcuserStatus(String eachQcuserStatus) {
			this.eachQcuserStatus = eachQcuserStatus;
		}
		public String getGetRecordCount() {
			return getRecordCount;
		}
		public void setGetRecordCount(String getRecordCount) {
			this.getRecordCount = getRecordCount;
		}
		public String getCompleteCount() {
			return completeCount;
		}
		public void setCompleteCount(String completeCount) {
			this.completeCount = completeCount;
		}
		public String getTasktype() {
			return tasktype;
		}
		public void setTasktype(String tasktype) {
			this.tasktype = tasktype;
		}
		public String getTaskStartTime() {
			return taskStartTime;
		}
		public void setTaskStartTime(String taskStartTime) {
			this.taskStartTime = taskStartTime;
		}
		public String getTaskEndTime() {
			return taskEndTime;
		}
		public void setTaskEndTime(String taskEndTime) {
			this.taskEndTime = taskEndTime;
		}
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		public String getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(String updateTime) {
			this.updateTime = updateTime;
		}
		public String getQcworkid() {
			return qcworkid;
		}
		public void setQcworkid(String qcworkid) {
			this.qcworkid = qcworkid;
		}
		public Integer getTaskId() {
			return taskId;
		}
		public void setTaskId(Integer taskId) {
			this.taskId = taskId;
		}
		public String getTaskName() {
			return taskName;
		}
		public void setTaskName(String taskName) {
			this.taskName = taskName;
		}
		public String getQcUser() {
			return qcUser;
		}
		public void setQcUser(String qcUser) {
			this.qcUser = qcUser;
		}
		public String getQcUserName() {
			return qcUserName;
		}
		public void setQcUserName(String qcUserName) {
			this.qcUserName = qcUserName;
		}
		public String getTelUser() {
			return telUser;
		}
		public void setTelUser(String telUser) {
			this.telUser = telUser;
		}
		public String getTelUserName() {
			return telUserName;
		}
		public void setTelUserName(String telUserName) {
			this.telUserName = telUserName;
		}
		public String getTaskStatus() {
			return taskStatus;
		}
		public void setTaskStatus(String taskStatus) {
			this.taskStatus = taskStatus;
		}
		public String getEnabled() {
			return enabled;
		}
		public void setEnabled(String enabled) {
			this.enabled = enabled;
		}
		public String getIsPublish() {
			return isPublish;
		}
		public void setIsPublish(String isPublish) {
			this.isPublish = isPublish;
		}
		public String getCsrTopDCount() {
			return csrTopDCount;
		}
		public void setCsrTopDCount(String csrTopDCount) {
			this.csrTopDCount = csrTopDCount;
		}
		public String getCheckCount() {
			return checkCount;
		}
		public void setCheckCount(String checkCount) {
			this.checkCount = checkCount;
		}
		public String getBussniessType() {
			return bussniessType;
		}
		public void setBussniessType(String bussniessType) {
			this.bussniessType = bussniessType;
		}
		public String getTelsumType() {
			return telsumType;
		}
		public void setTelsumType(String telsumType) {
			this.telsumType = telsumType;
		}
		public String getGzlyType() {
			return gzlyType;
		}
		public void setGzlyType(String gzlyType) {
			this.gzlyType = gzlyType;
		}
		public String getCreateUserId() {
			return createUserId;
		}
		public void setCreateUserId(String createUserId) {
			this.createUserId = createUserId;
		}
		public String getUpdateUserId() {
			return updateUserId;
		}
		public void setUpdateUserId(String updateUserId) {
			this.updateUserId = updateUserId;
		}
		public String getCreatStartTime() {
			return creatStartTime;
		}
		public void setCreatStartTime(String creatStartTime) {
			this.creatStartTime = creatStartTime;
		}
		public String getCreatEndTime() {
			return creatEndTime;
		}
		public void setCreatEndTime(String creatEndTime) {
			this.creatEndTime = creatEndTime;
		}
	    
}