/**
 * 
 */
package cn.sh.ideal.util;
/**
	@author maxiaoni
	@date 创建时间：2015年12月18日 下午1:57:50
	@version V1.0
	@Description Constans.java
 */
public class Constans {

	

	/*status */
	public static final String	SUCCESS="1";
	public static final String	FAILE="0";
	public static final String	CONSTANT_LOG_DESCRIPTION_ARGS_OBJECT="LOG_ARGUMENTS";
	
	public static final String  USER_INFO_USERID="userid";
	public static final String USER_INFO_WORKID="userworkid";
	public static final String USER_INFO="userinfo";
	public static final String ROLE_FLAG="roleflag";
	
	/*质检合格、不合格*/
	public static final String PASS="合格";//合格
	public static final String NOT_PASS="不合格";//不合格
	
	/*syscode itemflag*/
	public static final Integer PID_COACH_ITEM=1;//辅导项目
	public static final Integer PID_112=10;//112处理情况
	public static final Integer PIDCHECK_112=124;//112处理情况
	public static final Integer PID_C_DEALDES=32;//C网处理情况
	public static final Integer PID_REDEAL_DEALDES=54;//预处理处理情况
	public static final Integer PID_GK_DEALDES=76;//管控处理情况
	/*是否tl9000*/
	public static final String TL9000="1";//是
	public static final String NOT_TL9000="0";//否
   /*数据同步*/
	public static final String DATASYNC="数据同步";
	public static final String DATASYNC_112WKORDER="112工单数据同步";
	public static final String DATASYNC_112WKORDER_SUM="112工单电话小结数据同步";
	public static final String DATASYNC_CWKORDER="C网工单数据同步";
	public static final String DATASYNC_CWKORDER_SUM="C网工单电话小结数据同步";
	public static final String DATASYNC_USERINFO="人员信息同步";
	public static final String DATASYNC_RECORDINFO="录音信息同步";
	
	/*数据来源（1：接口；2：系统新建员工）*/
	public static final String DATA_FROM_INF="1";
	public static final String DATA_FROM_SYS="2";
	
	
	
}

