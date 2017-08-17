package cn.sh.ideal.model;

/********
 * C网电话小结任务完成领取实体类
 * 
 * @author niewenqiang
 * @date 2017-5-25
 * 
 * ******/
public class CdmaTaskComplete {
	private int taskId; // 任务ID
	private String taskuserId;
	private String taskName;
	private String taskStartTime;
	private String taskEndTime;
	private String eachQcuserStatus;
	private String qcUserWorkId;
	private String createUserWorkId;
	private String qcUserName;

	private String completeID;
	private String cDate;
	private String getRecordCount;
	private String completeCount;
	private String eachDateStatus;
	private String qcDCount;


	private String qcUser;
	private String tasktype;
	private String taskDate;
	
	
	
	
	public String getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(String taskDate) {
		this.taskDate = taskDate;
	}

	public String getQcUser() {
		return qcUser;
	}

	public void setQcUser(String qcUser) {
		this.qcUser = qcUser;
	}

	public String getTasktype() {
		return tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskuserId() {
		return taskuserId;
	}

	public void setTaskuserId(String taskuserId) {
		this.taskuserId = taskuserId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

	public String getEachQcuserStatus() {
		return eachQcuserStatus;
	}

	public void setEachQcuserStatus(String eachQcuserStatus) {
		this.eachQcuserStatus = eachQcuserStatus;
	}

	public String getQcUserWorkId() {
		return qcUserWorkId;
	}

	public void setQcUserWorkId(String qcUserWorkId) {
		this.qcUserWorkId = qcUserWorkId;
	}

	public String getCreateUserWorkId() {
		return createUserWorkId;
	}

	public void setCreateUserWorkId(String createUserWorkId) {
		this.createUserWorkId = createUserWorkId;
	}

	public String getQcUserName() {
		return qcUserName;
	}

	public void setQcUserName(String qcUserName) {
		this.qcUserName = qcUserName;
	}

	public String getCompleteID() {
		return completeID;
	}

	public void setCompleteID(String completeID) {
		this.completeID = completeID;
	}

	public String getcDate() {
		return cDate;
	}

	public void setcDate(String cDate) {
		this.cDate = cDate;
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

	public String getEachDateStatus() {
		return eachDateStatus;
	}

	public void setEachDateStatus(String eachDateStatus) {
		this.eachDateStatus = eachDateStatus;
	}

	public String getQcDCount() {
		return qcDCount;
	}

	public void setQcDCount(String qcDCount) {
		this.qcDCount = qcDCount;
	}

}
