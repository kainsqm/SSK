package cn.sh.ideal.model;

import java.util.Date;

public class Task {


	

	public String getSpecialflag() {
		return specialflag;
	}

	public void setSpecialflag(String specialflag) {
		this.specialflag = specialflag;
	}

	/*  T_QC_TASK  **/
	private int taskId;//任务id
    
    private String taskrecordId;//任务录音id

    private String templateId;// 模板id

    private String downNum;//任务当日下限

    private String qcUser;//质检员id(注：T_QC_TASKQCUSER表中也有此字段)
    
    private String qcUserWorkId;  //质检员工号
    
    private String startDate;//通话日志开始时间

    private String endDate;//通话日志结束时间
    
    private String isPublish; 

    private String papers_hid;
    private String callTimeStart;//通话日志通话时长范围开始

    private String callTimeEnd;//通话日志通话时长范围结束

    private int createUserId;//任务创建者
    
    private String createUserWorkId;

    private String createTime;//任务创建时间
    
    private int updateUserId;//任务更新者

    private String updateTime;//任务更新时间
    
    private String taskStatus;//任务状态,状态包括:0未发布1待执行2执行中3已完成4未完成5强制关闭

    private String telUserWorkId;  
    
    private String taskName;//任务名称
    
    private String enabled;
    
    private String platformRelegation;//创建者所属台式
    
    private String createUserName;//创建者姓名
    
    private String agentType;//创建者公商客类型（1：公客2：商客）
    
    private String taskStartTime;//任务开始时间

    private String taskEndTime;//任务结束时间
    
    private String StartTime;
    
    private String EndTime; 
	
    private String busyRestTime;//忙闲夜时段（1，忙，2闲，3夜）
    
    private String callBusiTp;//业务大类（注：T_RECORD_INFO表中也有此字段）
        
    private String telUser;//话务员工号
    
    private String telUserName;//话务员姓名
    
    private String groupId;//班组id
    
    private String groupName;//班组名称
    
    private String satisfy;//满意度
    
    private String specialflag; // 特殊符号
    
    //lfj 2012-11-23 add
    private String csrTopDCount;//每个CSR每天最高被质检次数
    private String csrTopWCount;//每个CSR每周最高被质检次数(六个挡)
    private String qcDCount;//每个质检员每天质检任务规格(六个挡)
    
    private String [] qcDcountItem=new String [6];
    
    private String csrAllCount;//任务期间每个CSR被同一个质检员最高质检次数
    private String qcUserName;//质检员名字
    

    
    /*  T_QC_TASKQCUSER  **/
    private int taskuserId;//任务质检员表主键id    
    
//    private String oneminRecnum;//每天计划一分钟以内录音数
    
//    private String fivminRecnum;//每天计划一到五分钟录音数
    
//    private String tenminRecnum;//每天计划五到十分钟录音数
    
//    private String tenmorminRecnum;//每天计划十分钟以上录音数
    
    private String planRecordNum;//每天计划录音总数（注：T_QC_TASKRECORD表中也有此字段）
    
    private String realRecordNum;//当天实际录音总数（注：T_QC_TASKRECORD表中也有此字段）
    
    private String finishRecordNum;//当天完成录音数（注：T_QC_TASKRECORD表中也有此字段）
        
    private String callerid;//主叫号码
    
    private String calledid;//被叫号码
    
    private String directionFlag;//1拨入，0拨出（注：T_RECORD_INFO表中也有此字段）
    
    private String createTimeTasqcu;//创建时间(T_QC_TASKQCUSER)
    
    private String createUserIdTasqcu;//创建者（T_QC_TASKQCUSER）
    
    private String updateUserIdTasqcu;//更新人(T_QC_TASKQCUSER)

    private String updateTimeTasqcu;//更新时间（T_QC_TASKQCUSER）
    
    private String eachQcuserStatus;//每个质检员的任务状态(1待执行2执行中3已完成4未完成)
    
    private String recordId;
    
    //lfj 2012-11-23 add
    private String getRecordCount;//领取录音数(六个挡，中间逗号隔开)
    private String completeCount;//完成录音数(六个挡，中间逗号隔开)    
    private String allCompleteCount;//单个质检员总的完成数
    private String [] allCompleteCountItem=new String [6];
    private String [] getRecordCountItem=new String [6];
    private String [] completeCountItem =new String [6];

    /*  T_QC_TASKRECORD  **/
    private String createTimeTasrec;//创建时间(T_QC_TASKRECORD)
    
    private String createUserIdTasrec;//创建者(T_QC_TASKRECORD)

    private String updateUserIdTasrec;//更新者（T_QC_TASKRECORD）

    private String updateTimeTasrec;//更新时间（T_QC_TASKRECORD）
    
    private String isQc;//是否已质检(0未质检，1已质检)
    
    private String callId;//cti流水号
    
    private String recordReference;//录音流水号
    
    private String taskRecordId;//任务相关录音表主键id
    
    /*  T_QC_TASKQCUSER_COMPLETE lfj 2012-11-29 add  **/
    private String completeID;//任务质检员完成情况表ID
    private String cDate;//那天的任务(日期)
    private String eachDateStatus;//质检员每天的任务状态，1，进行中，2已完成，3未完成
    /*  T_QC_SYSUSER  **/
    private String userId;//用户工号
    
    private String loginName;//用户姓名
    
    
    /*  T_QC_SYSUSER  **/
    private String agentGroupId;//班组id
    private String agentGroupName;//班组名称
    
    
    /*  T_RECORD_INFO  **/
    private String agentWorkId;//话务员工号
    
    private String startRecordTime; // 开始录音时间

	private String stopRecordTime; // 结束录音时间
	
	private Integer recordLength; // 录音时长
	
	private String recordLengthPart;//录音时长段(1-6)
	
	private String callerId;//主叫
	
	private String calledId;//被叫
	
	private String callBusiSubTp;//业务小类
	
	private String ivrSource;//ivr来源
	
	private String evtActTitle;//事物标题
	
	private String recordType; //录音渠道:1,VIP;2,星级服务。复选框
	
	private String tasktype;//任务类型 1.112工单2.c网工单3.112小结4.c网小结5.录音
	
	private String qcworkid;//质检员工号
	
	private String orderid;//工单表主键
	
	private String telsum;//电话小结表主键
	
	private String bussniesstype;//业务类型
	
	private String telsumtype;//小结类型
	
	private String gzlytype;//故障来源
	
	private String sgdltype;//申告业务大类
	
	private String sgxltype;//申告业务小类
	
	private String sgxxtype;//申告现象
	
	private String clfstype;//一级处理方式
	
	private String jadmtype;//一级结案代码
	
	private String secsgxxtype;//二级申告现象
	
	private String zejtype;//是否应该转二级
	
	private String ycltype;//二级预处理结案代码
	
	private String qjtype;//区局
	
	private String csdmtype;//测试代码
	
	private String sgbztype;//申告备注
	
	private String sglxtype;//申告联系信息
	
	private String jafstype;//结案方式
	
	private String sllytype;//受理来源
	
	private String pxfxtype;//派修方向
	
	private String sjgztype;//三级故障大类
	
	private String gzxftype;//三级故障修复代码
	
    private String checkcount;//受理员被抽查限制次数/天
	
	private String creatStartTime;//创建开始时间
	
	private String creatEndTime;//创建结束时间


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

	public String getQcworkid() {
		return qcworkid;
	}

	public void setQcworkid(String qcworkid) {
		this.qcworkid = qcworkid;
	}

	public String getCheckcount() {
		return checkcount;
	}

	public void setCheckcount(String checkcount) {
		this.checkcount = checkcount;
	}

	public String getTasktype() {
		return tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getTelsum() {
		return telsum;
	}

	public void setTelsum(String telsum) {
		this.telsum = telsum;
	}

	public String getBussniesstype() {
		return bussniesstype;
	}

	public void setBussniesstype(String bussniesstype) {
		this.bussniesstype = bussniesstype;
	}

	public String getTelsumtype() {
		return telsumtype;
	}

	public void setTelsumtype(String telsumtype) {
		this.telsumtype = telsumtype;
	}

	public String getGzlytype() {
		return gzlytype;
	}

	public void setGzlytype(String gzlytype) {
		this.gzlytype = gzlytype;
	}

	public String getSgdltype() {
		return sgdltype;
	}

	public void setSgdltype(String sgdltype) {
		this.sgdltype = sgdltype;
	}

	public String getSgxltype() {
		return sgxltype;
	}

	public void setSgxltype(String sgxltype) {
		this.sgxltype = sgxltype;
	}

	public String getSgxxtype() {
		return sgxxtype;
	}

	public void setSgxxtype(String sgxxtype) {
		this.sgxxtype = sgxxtype;
	}

	public String getClfstype() {
		return clfstype;
	}

	public void setClfstype(String clfstype) {
		this.clfstype = clfstype;
	}

	public String getJadmtype() {
		return jadmtype;
	}

	public void setJadmtype(String jadmtype) {
		this.jadmtype = jadmtype;
	}

	public String getSecsgxxtype() {
		return secsgxxtype;
	}

	public void setSecsgxxtype(String secsgxxtype) {
		this.secsgxxtype = secsgxxtype;
	}

	public String getZejtype() {
		return zejtype;
	}

	public void setZejtype(String zejtype) {
		this.zejtype = zejtype;
	}

	public String getYcltype() {
		return ycltype;
	}

	public void setYcltype(String ycltype) {
		this.ycltype = ycltype;
	}

	public String getQjtype() {
		return qjtype;
	}

	public void setQjtype(String qjtype) {
		this.qjtype = qjtype;
	}

	

	public String getCsdmtype() {
		return csdmtype;
	}

	public void setCsdmtype(String csdmtype) {
		this.csdmtype = csdmtype;
	}

	public String getSgbztype() {
		return sgbztype;
	}

	public void setSgbztype(String sgbztype) {
		this.sgbztype = sgbztype;
	}

	public String getSglxtype() {
		return sglxtype;
	}

	public void setSglxtype(String sglxtype) {
		this.sglxtype = sglxtype;
	}

	public String getJafstype() {
		return jafstype;
	}

	public void setJafstype(String jafstype) {
		this.jafstype = jafstype;
	}

	public String getSllytype() {
		return sllytype;
	}

	public void setSllytype(String sllytype) {
		this.sllytype = sllytype;
	}

	public String getPxfxtype() {
		return pxfxtype;
	}

	public void setPxfxtype(String pxfxtype) {
		this.pxfxtype = pxfxtype;
	}

	public String getSjgztype() {
		return sjgztype;
	}

	public void setSjgztype(String sjgztype) {
		this.sjgztype = sjgztype;
	}

	public String getGzxftype() {
		return gzxftype;
	}

	public void setGzxftype(String gzxftype) {
		this.gzxftype = gzxftype;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	
	
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	
	
	public String getTelUserWorkId() {
		return telUserWorkId;
	}

	public void setTelUserWorkId(String telUserWorkId) {
		this.telUserWorkId = telUserWorkId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	

//	public String getOneminRecnum() {
//		return oneminRecnum;
//	}
//
//	public void setOneminRecnum(String oneminRecnum) {
//		this.oneminRecnum = oneminRecnum;
//	}
//
//	public String getFivminRecnum() {
//		return fivminRecnum;
//	}
//
//	public void setFivminRecnum(String fivminRecnum) {
//		this.fivminRecnum = fivminRecnum;
//	}
//
//	public String getTenminRecnum() {
//		return tenminRecnum;
//	}
//
//	public void setTenminRecnum(String tenminRecnum) {
//		this.tenminRecnum = tenminRecnum;
//	}
//
//	public String getTenmorminRecnum() {
//		return tenmorminRecnum;
//	}
//
//	public void setTenmorminRecnum(String tenmorminRecnum) {
//		this.tenmorminRecnum = tenmorminRecnum;
//	}

	public String getPapers_hid() {
		return papers_hid;
	}

	public void setPapers_hid(String papersHid) {
		papers_hid = papersHid;
	}

	public String getStartTime() {
		return StartTime;
	}

	public void setStartTime(String startTime) {
		StartTime = startTime;
	}

	public String getEndTime() {
		return EndTime;
	}

	public void setEndTime(String endTime) {
		EndTime = endTime;
	}

	public String getCreateUserWorkId() {
		return createUserWorkId;
	}

	public void setCreateUserWorkId(String createUserWorkId) {
		this.createUserWorkId = createUserWorkId;
	}

	public String getPlanRecordNum() {
		return planRecordNum;
	}

	public void setPlanRecordNum(String planRecordNum) {
		this.planRecordNum = planRecordNum;
	}

	
	
	public String getQcUserWorkId() {
		return qcUserWorkId;
	}

	public void setQcUserWorkId(String qcUserWorkId) {
		this.qcUserWorkId = qcUserWorkId;
	}

	public String getRealRecordNum() {
		return realRecordNum;
	}

	public void setRealRecordNum(String realRecordNum) {
		this.realRecordNum = realRecordNum;
	}

	public String getFinishRecordNum() {
		return finishRecordNum;
	}

	public void setFinishRecordNum(String finishRecordNum) {
		this.finishRecordNum = finishRecordNum;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column T_QC_TASK.AUTO_ID
     *
     * @return the value of T_QC_TASK.AUTO_ID
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public int getTaskId() {
        return taskId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column T_QC_TASK.AUTO_ID
     *
     * @param autoId the value for T_QC_TASK.AUTO_ID
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public void setTaskId(int autoId) {
        this.taskId = autoId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column T_QC_TASK.TEMPLATE_ID
     *
     * @return the value of T_QC_TASK.TEMPLATE_ID
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column T_QC_TASK.TEMPLATE_ID
     *
     * @param templateId the value for T_QC_TASK.TEMPLATE_ID
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column T_QC_TASK.TASK_START_TIME
     *
     * @return the value of T_QC_TASK.TASK_START_TIME
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public String getTaskStartTime() {
        return taskStartTime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column T_QC_TASK.TASK_START_TIME
     *
     * @param taskStartTime the value for T_QC_TASK.TASK_START_TIME
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public void setTaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column T_QC_TASK.TASK_END_TIME
     *
     * @return the value of T_QC_TASK.TASK_END_TIME
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public String getTaskEndTime() {
        return taskEndTime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column T_QC_TASK.TASK_END_TIME
     *
     * @param taskEndTime the value for T_QC_TASK.TASK_END_TIME
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column T_QC_TASK.DOWN_NUM
     *
     * @return the value of T_QC_TASK.DOWN_NUM
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public String getDownNum() {
        return downNum;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column T_QC_TASK.DOWN_NUM
     *
     * @param downNum the value for T_QC_TASK.DOWN_NUM
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public void setDownNum(String downNum) {
        this.downNum = downNum;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column T_QC_TASK.QC_USER
     *
     * @return the value of T_QC_TASK.QC_USER
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public String getQcUser() {
        return qcUser;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column T_QC_TASK.QC_USER
     *
     * @param qcUser the value for T_QC_TASK.QC_USER
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public void setQcUser(String qcUser) {
        this.qcUser = qcUser;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column T_QC_TASK.START_DATE
     *
     * @return the value of T_QC_TASK.START_DATE
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column T_QC_TASK.START_DATE
     *
     * @param startDate the value for T_QC_TASK.START_DATE
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column T_QC_TASK.END_DATE
     *
     * @return the value of T_QC_TASK.END_DATE
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column T_QC_TASK.END_DATE
     *
     * @param endDate the value for T_QC_TASK.END_DATE
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column T_QC_TASK.CALL_TIME_START
     *
     * @return the value of T_QC_TASK.CALL_TIME_START
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public String getCallTimeStart() {
        return callTimeStart;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column T_QC_TASK.CALL_TIME_START
     *
     * @param callTimeStart the value for T_QC_TASK.CALL_TIME_START
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public void setCallTimeStart(String callTimeStart) {
        this.callTimeStart = callTimeStart;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column T_QC_TASK.CALL_TIME_END
     *
     * @return the value of T_QC_TASK.CALL_TIME_END
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public String getCallTimeEnd() {
        return callTimeEnd;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column T_QC_TASK.CALL_TIME_END
     *
     * @param callTimeEnd the value for T_QC_TASK.CALL_TIME_END
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public void setCallTimeEnd(String callTimeEnd) {
        this.callTimeEnd = callTimeEnd;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column T_QC_TASK.CREATE_USER_ID
     *
     * @return the value of T_QC_TASK.CREATE_USER_ID
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public int getCreateUserId() {
        return createUserId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column T_QC_TASK.CREATE_USER_ID
     *
     * @param createUserId the value for T_QC_TASK.CREATE_USER_ID
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column T_QC_TASK.CREATE_TIME
     *
     * @return the value of T_QC_TASK.CREATE_TIME
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column T_QC_TASK.CREATE_TIME
     *
     * @param createTime the value for T_QC_TASK.CREATE_TIME
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column T_QC_TASK.UPDATE_USER_ID
     *
     * @return the value of T_QC_TASK.UPDATE_USER_ID
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public int getUpdateUserId() {
        return updateUserId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column T_QC_TASK.UPDATE_USER_ID
     *
     * @param updateUserId the value for T_QC_TASK.UPDATE_USER_ID
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public void setUpdateUserId(int updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column T_QC_TASK.UPDATE_TIME
     *
     * @return the value of T_QC_TASK.UPDATE_TIME
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column T_QC_TASK.UPDATE_TIME
     *
     * @param updateTime the value for T_QC_TASK.UPDATE_TIME
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column T_QC_TASK.TASK_STATUS
     *
     * @return the value of T_QC_TASK.TASK_STATUS
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public String getTaskStatus() {
        return taskStatus;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column T_QC_TASK.TASK_STATUS
     *
     * @param taskStatus the value for T_QC_TASK.TASK_STATUS
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column T_QC_TASK.ENABLED
     *
     * @return the value of T_QC_TASK.ENABLED
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column T_QC_TASK.ENABLED
     *
     * @param enabled the value for T_QC_TASK.ENABLED
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column T_QC_TASK.TEL_USER
     *
     * @return the value of T_QC_TASK.TEL_USER
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public String getTelUser() {
        return telUser;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column T_QC_TASK.TEL_USER
     *
     * @param telUser the value for T_QC_TASK.TEL_USER
     *
     * @abatorgenerated Mon Dec 20 15:01:10 CST 2010
     */
    public void setTelUser(String telUser) {
        this.telUser = telUser;
    }

	public int getTaskuserId() {
		return taskuserId;
	}

	public void setTaskuserId(int taskuserId) {
		this.taskuserId = taskuserId;
	}

	public String getTaskrecordId() {
		return taskrecordId;
	}

	public void setTaskrecordId(String taskrecordId) {
		this.taskrecordId = taskrecordId;
	}

	public String getCreateTimeTasqcu() {
		return createTimeTasqcu;
	}

	public void setCreateTimeTasqcu(String createTimeTasqcu) {
		this.createTimeTasqcu = createTimeTasqcu;
	}

	public String getCreateUserIdTasqcu() {
		return createUserIdTasqcu;
	}

	public void setCreateUserIdTasqcu(String createUserIdTasqcu) {
		this.createUserIdTasqcu = createUserIdTasqcu;
	}

	public String getCreateTimeTasrec() {
		return createTimeTasrec;
	}

	public void setCreateTimeTasrec(String createTimeTasrec) {
		this.createTimeTasrec = createTimeTasrec;
	}

	public String getCreateUserIdTasrec() {
		return createUserIdTasrec;
	}

	public void setCreateUserIdTasrec(String createUserIdTasrec) {
		this.createUserIdTasrec = createUserIdTasrec;
	}

	public String getUpdateUserIdTasqcu() {
		return updateUserIdTasqcu;
	}

	public void setUpdateUserIdTasqcu(String updateUserIdTasqcu) {
		this.updateUserIdTasqcu = updateUserIdTasqcu;
	}

	public String getUpdateTimeTasqcu() {
		return updateTimeTasqcu;
	}

	public void setUpdateTimeTasqcu(String updateTimeTasqcu) {
		this.updateTimeTasqcu = updateTimeTasqcu;
	}

	public String getUpdateUserIdTasrec() {
		return updateUserIdTasrec;
	}

	public void setUpdateUserIdTasrec(String updateUserIdTasrec) {
		this.updateUserIdTasrec = updateUserIdTasrec;
	}

	public String getUpdateTimeTasrec() {
		return updateTimeTasrec;
	}

	public void setUpdateTimeTasrec(String updateTimeTasrec) {
		this.updateTimeTasrec = updateTimeTasrec;
	}

	public String getCallBusiTp() {
		return callBusiTp;
	}

	public void setCallBusiTp(String callBusiTp) {
		this.callBusiTp = callBusiTp;
	}

	public String getCallerid() {
		return callerid;
	}

	public void setCallerid(String callerid) {
		this.callerid = callerid;
	}

	public String getCalledid() {
		return calledid;
	}

	public void setCalledid(String calledid) {
		this.calledid = calledid;
	}

	public String getBusyRestTime() {
		return busyRestTime;
	}

	public void setBusyRestTime(String busyRestTime) {
		this.busyRestTime = busyRestTime;
	}

	public String getDirectionFlag() {
		return directionFlag;
	}

	public void setDirectionFlag(String directionFlag) {
		this.directionFlag = directionFlag;
	}

	public String getPlatformRelegation() {
		return platformRelegation;
	}

	public void setPlatformRelegation(String platformRelegation) {
		this.platformRelegation = platformRelegation;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	public String getIsQc() {
		return isQc;
	}

	public void setIsQc(String isQc) {
		this.isQc = isQc;
	}

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getRecordReference() {
		return recordReference;
	}

	public void setRecordReference(String recordReference) {
		this.recordReference = recordReference;
	}

	public String getAgentGroupId() {
		return agentGroupId;
	}

	public void setAgentGroupId(String agentGroupId) {
		this.agentGroupId = agentGroupId;
	}

	public String getAgentGroupName() {
		return agentGroupName;
	}

	public void setAgentGroupName(String agentGroupName) {
		this.agentGroupName = agentGroupName;
	}

	public String getAgentWorkId() {
		return agentWorkId;
	}

	public void setAgentWorkId(String agentWorkId) {
		this.agentWorkId = agentWorkId;
	}

	public String getStartRecordTime() {
		return startRecordTime;
	}

	public void setStartRecordTime(String startRecordTime) {
		this.startRecordTime = startRecordTime;
	}

	public String getStopRecordTime() {
		return stopRecordTime;
	}

	public void setStopRecordTime(String stopRecordTime) {
		this.stopRecordTime = stopRecordTime;
	}

	public Integer getRecordLength() {
		return recordLength;
	}

	public void setRecordLength(Integer recordLength) {
		this.recordLength = recordLength;
	}

	public String getCallerId() {
		return callerId;
	}

	public void setCallerId(String callerId) {
		this.callerId = callerId;
	}

	public String getCalledId() {
		return calledId;
	}

	public void setCalledId(String calledId) {
		this.calledId = calledId;
	}

	public String getCallBusiSubTp() {
		return callBusiSubTp;
	}

	public void setCallBusiSubTp(String callBusiSubTp) {
		this.callBusiSubTp = callBusiSubTp;
	}

	public String getIvrSource() {
		return ivrSource;
	}

	public void setIvrSource(String ivrSource) {
		this.ivrSource = ivrSource;
	}

	
	public String getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}

	public String getEvtActTitle() {
		return evtActTitle;
	}

	public void setEvtActTitle(String evtActTitle) {
		this.evtActTitle = evtActTitle;
	}

	public String getTaskRecordId() {
		return taskRecordId;
	}

	public void setTaskRecordId(String taskRecordId) {
		this.taskRecordId = taskRecordId;
	}

	public String getEachQcuserStatus() {
		return eachQcuserStatus;
	}

	public void setEachQcuserStatus(String eachQcuserStatus) {
		this.eachQcuserStatus = eachQcuserStatus;
	}

	public String getTelUserName() {
		return telUserName;
	}

	public void setTelUserName(String telUserName) {
		this.telUserName = telUserName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCsrTopDCount() {
		return csrTopDCount;
	}

	public void setCsrTopDCount(String csrTopDCount) {
		this.csrTopDCount = csrTopDCount;
	}

	public String getCsrTopWCount() {
		return csrTopWCount;
	}

	public void setCsrTopWCount(String csrTopWCount) {
		this.csrTopWCount = csrTopWCount;
	}

	public String getQcDCount() {
		return qcDCount;
	}

	public void setQcDCount(String qcDCount) {
		this.qcDCount = qcDCount;
	}

	public String getCsrAllCount() {
		return csrAllCount;
	}

	public void setCsrAllCount(String csrAllCount) {
		this.csrAllCount = csrAllCount;
	}

	public String getQcUserName() {
		return qcUserName;
	}

	public void setQcUserName(String qcUserName) {
		this.qcUserName = qcUserName;
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

	public void setGetRecordCountItem(String [] getRecordCountItem) {
		this.getRecordCountItem = getRecordCountItem;
	}

	public String [] getGetRecordCountItem() {
		return getRecordCountItem;
	}

	public void setCompleteCountItem(String [] completeCountItem) {
		this.completeCountItem = completeCountItem;
	}

	public String [] getCompleteCountItem() {
		return completeCountItem;
	}

	public void setCompleteID(String completeID) {
		this.completeID = completeID;
	}

	public String getCompleteID() {
		return completeID;
	}

	public void setcDate(String cDate) {
		this.cDate = cDate;
	}

	public String getcDate() {
		return cDate;
	}

	public void setEachDateStatus(String eachDateStatus) {
		this.eachDateStatus = eachDateStatus;
	}

	public String getEachDateStatus() {
		return eachDateStatus;
	}

	public void setQcDcountItem(String [] qcDcountItem) {
		this.qcDcountItem = qcDcountItem;
	}

	public String [] getQcDcountItem() {
		return qcDcountItem;
	}

	public void setRecordLengthPart(String recordLengthPart) {
		this.recordLengthPart = recordLengthPart;
	}

	public String getRecordLengthPart() {
		return recordLengthPart;
	}

	public String getAllCompleteCount() {
		return allCompleteCount;
	}

	public void setAllCompleteCount(String allCompleteCount) {
		this.allCompleteCount = allCompleteCount;
	}

	public String[] getAllCompleteCountItem() {
		return allCompleteCountItem;
	}

	public void setAllCompleteCountItem(String[] allCompleteCountItem) {
		this.allCompleteCountItem = allCompleteCountItem;
	}
	public String getSatisfy() {
		return satisfy;
	}

	public void setSatisfy(String satisfy) {
		this.satisfy = satisfy;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
}