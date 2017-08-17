<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@page import="cn.sh.ideal.model.Task"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>客调项目管理系统</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>

		 <link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-dialog.css"
			rel="stylesheet" type="text/css" /> 
		<!-- <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css"
			rel="stylesheet" type="text/css" />  -->
		<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />	
		<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/core/base.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js"
			type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/page/task/taskupdate.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/util.js"></script>
		<style type="text/css">
body {
	font-size: 13px;
}

input[type='button'] {
	border: #d3d3d3 1px solid;
	width: 80px;
	height: 25px;
	cursor: pointer;
}

td {
	text-align: left;
}

.td_1 {
	text-align: right;
}
</style>
<script type="text/javascript">
		    var ctx = "${pageContext.request.contextPath}";
		</script>
  </head>
  
 <body onload="init('${taskInfo.csrTopWCount }','${taskInfo.qcDCount }','${taskInfo.qcUser }','${taskInfo.isPublish}');">
 <div align="center" style="height: auto;">
			<form method="post"
				id="myform">
				<table border="0" cellpadding="3" cellspacing="3" width="90%">
					<tr>
						<td colspan="3">  
							任务信息  
						</td>
					</tr>
					<tr>
						<td width="140" class="td_1">
							任务名称
						</td>
						<td width="180">
						    <input id="type" type="hidden" value="${type}">
						    <input name="TASK_ID" value="${taskInfo.taskId}" type="hidden"/>
						    <input name="TASK_STATUS" value="${taskInfo.taskStatus}" type="hidden"/>
						     <input id="oldName" name="oldName" value="${taskInfo.taskName}" type="hidden"/>
						    <input type="text" name="TASK_NAME" id="TASK_NAME"  onkeyup="strlenLimit(this,50);noSpecial(this);"  onblur="checkName();strlenLimit(this,50);noSpecial(this);" value="${taskInfo.taskName}"/>
							<font color="#FF0000">&nbsp;*</font>
						</td>
						<td width="459">
							是否发布
							<input type="radio" name="IS_PUBLISH" value="1" />
							是&nbsp;&nbsp;&nbsp;
							<input type="radio" name="IS_PUBLISH" value="0" />
							否
							<font color="#FF0000">&nbsp;*</font>
						</td>
					</tr>
					<tr>
						<td class="td_1">
							任务周期
						</td>
						<td colspan="2">										
							<input type="text" name="taskStartTime"
									class="Wdate s_input"  id="taskStartTime"
									onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'taskEndTime\');}',dateFmt:'yyyy-MM-dd'})"
									onchange="timeChaOherCha('taskStartTime','taskEndTime','7','1')"
									value="${taskInfo.taskStartTime}"/>
							&nbsp;-&nbsp;
							<!-- <input class="Wdate date_text" type="text" name="ENDTIME"
								onfocus="WdatePicker({minDate:'#F{$dp.$D(\'STARTTIME');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/> -->
							<input type="text" name="taskEndTime"
									class="Wdate s_input"  id="taskEndTime"
									onfocus="WdatePicker({minDate:'#F{$dp.$D(\'taskStartTime\');}',dateFmt:'yyyy-MM-dd'})"
									value="${taskInfo.taskEndTime}"
									/>
							<font color="#FF0000">&nbsp;*</font>
								 		
						</td>
					</tr>
					<tr>
						<td class="td_1">
							任务对象
						</td>
						<td colspan="2">
							<input type="hidden" id="QC_USER" name="QC_USER"
								style="width: 75%" readonly="readonly" value="${taskInfo.qcUser}"  />
							<input type="hidden" id="QC_USER_WORKID" name="QC_USER_WORKID" value="${taskInfo.qcUserWorkId}"/>
							<input type="text" style="width: 500px;" id="QC_USER_NAME" value="${taskInfo.qcUserName}"
								name="QC_USER_NAME" onclick="showQc('qcList');"
								readonly="readonly" onchange="changeNumber(1);" />
							&nbsp;
							<span id="qcNumber" style="color: gray;">0</span>人 
							<font color="#FF0000">&nbsp;*(质检员，30人以内)</font>
						</td>
					</tr>
					<tr id="qcList" align="center" class="td_1" style="display: none;">
					    <td></td>
						<td align="left" colspan="2">
							<table>
								<tr>
									<td>
										<select style="height: 120px; width: 150px" multiple
											name="list1" size="10" id="list1"
											ondblclick="moveOption(document.taskForm.list1, document.taskForm.list2)">
											<c:forEach var="qcUser" items="${qcUserList}">
												<option value="${qcUser.userId}">
													${qcUser.userName}/${qcUser.workId}
												</option>
											</c:forEach>
										</select>
									</td>
									<td align="center">
										<input type="button" value="全部添加"
											onclick="moveAllOption(document.getElementById('list1'),document.getElementById('list2'))">
										<br />
										<input type="button" value="添加"
											onclick="moveOption(document.getElementById('list1'), document.getElementById('list2'))">
										<br />
										<input type="button" value="移除"
											onclick="moveOption(document.getElementById('list2'), document.getElementById('list1'))">
										<br />
										<input type="button" value="全部移除"
											onclick="moveAllOption(document.getElementById('list2'), document.getElementById('list1'))">
									</td>
									<td>
										<select style="height: 120px; WIDTH: 150px" multiple
											name="list2" size="10" id="list2"
											ondblclick="moveOption(document.getElementById('list2'), document.getElementById('list1'))">
										</select>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="3">
							录音筛选信息
						</td>
					</tr>
					<tr>
						<td class="td_1">
							受理员
						</td>
						<td colspan="2">
							<input type="text" style="width: 500px;" id="TEL_USER_NAME"
								name="TEL_USER_NAME" value="${taskInfo.telUserName}"
								onclick="hiddenQc('qcList');getOperatorList(this,this.value);" />
							&nbsp;
							<span id="telNumber2" style="color: gray;">0</span>人
							<font color="#FF0000">&nbsp;*(30人以内)</font>
							<input type="hidden" id="TEL_USER" name="TEL_USER" value="${taskInfo.telUser}" />
							<input type="hidden" id="TEL_USER_WORKID" name="TEL_USER_WORKID" value="${taskInfo.telUserWorkId}" />
							<input type="hidden" id="papers_hid" value="${taskInfo.papers_hid}"/>
						</td>
					</tr>
					<tr>
						<td class="td_1">
							受理员抽查次数/天
						</td>
						<td colspan="2">
							<input type="text" id="CSR_TOP_D_COUNT" name="CSR_TOP_D_COUNT" value="${taskInfo.csrTopDCount}"
								onkeyup="input_shuzi(this);strlenLimit(this,'2')"
								onblur="input_shuzi(this);strlenLimit(this,'2');showErrorAorBMsg();" />
							&nbsp;&nbsp;&nbsp;
							<font color="#FF0000">&nbsp;*</font>

						</td>
					</tr>
					<tr>
						<td class="td_1">
							受理员抽查次数/周
						</td>
						<td colspan="2">
							0~1m&nbsp;
							<input size="1" id="oneCsrWCount1" name="oneCsrWCount1"
								title="一分钟以内录音数" style="width: 20px"
								onkeyup="input_shuzi(this);strlenLimit(this,'2')"
								onblur="input_shuzi(this);strlenLimit(this,'2');showErrorAorBMsg();" />
							&nbsp;&nbsp;1~2m&nbsp;
							<input size="1" id="oneCsrWCount2" name="oneCsrWCount2"
								title="一到二分钟录音数" style="width: 20px"
								onkeyup="input_shuzi(this);strlenLimit(this,'2')"
								onblur="input_shuzi(this);strlenLimit(this,'2');showErrorAorBMsg();" />
							&nbsp;&nbsp;2~3m&nbsp;
							<input size="1" id="oneCsrWCount3" name="oneCsrWCount3"
								title="二到三分钟录音数" style="width: 20px"
								onkeyup="input_shuzi(this);strlenLimit(this,'2')"
								onblur="input_shuzi(this);strlenLimit(this,'2');showErrorAorBMsg();" />
							&nbsp;&nbsp;3~5m&nbsp;
							<input size="1" id="oneCsrWCount4" name="oneCsrWCount4"
								title="三到五分钟录音数" style="width: 20px"
								onkeyup="input_shuzi(this);strlenLimit(this,'2')"
								onblur="input_shuzi(this);strlenLimit(this,'2');showErrorAorBMsg();" />
							&nbsp;&nbsp;5~10m&nbsp;
							<input size="1" id="oneCsrWCount5" name="oneCsrWCount5"
								title="五到十分钟录音数" style="width: 20px"
								onkeyup="input_shuzi(this);strlenLimit(this,'2')"
								onblur="input_shuzi(this);strlenLimit(this,'2');showErrorAorBMsg();" />
							&nbsp;&nbsp;10~more&nbsp;
							<input size="1" id="oneCsrWCount6" name="oneCsrWCount6"
								title="十分钟以上录音数" style="width: 20px"
								onkeyup="input_shuzi(this);strlenLimit(this,'2')"
								onblur="input_shuzi(this);strlenLimit(this,'2');showErrorAorBMsg();" />
							&nbsp;
							<font color="#FF0000">&nbsp;*</font>
						</td>
					</tr>
					<tr>
						<td class="td_1">
							质检员任务规格数/天
						</td>
						<td colspan="2">
							0~1m&nbsp;
							<input size="1" id="oneQcDCount1" name="oneQcDCount1"
								title="一分钟以内录音数" style="width: 20px"
								onkeyup="input_shuzi(this);strlenLimit(this,'2')"
								onblur="input_shuzi(this);strlenLimit(this,'2');showErrorAorBMsg();" />
							&nbsp;&nbsp;1~2m&nbsp;
							<input size="1" id="oneQcDCount2" name="oneQcDCount2"
								title="一到二分钟录音数" style="width: 20px"
								onkeyup="input_shuzi(this);strlenLimit(this,'2')"
								onblur="input_shuzi(this);strlenLimit(this,'2');showErrorAorBMsg();" />
							&nbsp;&nbsp;2~3m&nbsp;
							<input size="1" id="oneQcDCount3" name="oneQcDCount3"
								title="二到三分钟录音数" style="width: 20px"
								onkeyup="input_shuzi(this);strlenLimit(this,'2')"
								onblur="input_shuzi(this);strlenLimit(this,'2');showErrorAorBMsg();" />
							&nbsp;&nbsp;3~5m&nbsp;
							<input size="1" id="oneQcDCount4" name="oneQcDCount4"
								title="三到五分钟录音数" style="width: 20px"
								onkeyup="input_shuzi(this);strlenLimit(this,'2')"
								onblur="input_shuzi(this);strlenLimit(this,'2');showErrorAorBMsg();" />
							&nbsp;&nbsp;5~10m&nbsp;
							<input size="1" id="oneQcDCount5" name="oneQcDCount5"
								title="五到十分钟录音数" style="width: 20px"
								onkeyup="input_shuzi(this);strlenLimit(this,'2')"
								onblur="input_shuzi(this);strlenLimit(this,'2');showErrorAorBMsg();" />
							&nbsp;&nbsp;10~more&nbsp;
							<input size="1" id="oneQcDCount6" name="oneQcDCount6"
								title="十分钟以上录音数" style="width: 20px"
								onkeyup="input_shuzi(this);strlenLimit(this,'2')"
								onblur="input_shuzi(this);strlenLimit(this,'2');showErrorAorBMsg();" />
							&nbsp;
							<font color="#FF0000">&nbsp;*</font>
						</td>
					</tr>
					<tr>
						<td colspan="3" style="text-align: center;">
							<input type="button" value="提交" id="btn"  onclick="addTaskSave()" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" name="back" value="返回"
								onclick="history.go(-1);" />
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<div style="margin-left: 20px;">
								规范一： 受理员数量乘以受理员抽查次数/天,必须大于,质检员数量乘以一个质检员一天规格的总和 (
								<font id="errorA1" color="red"></font> &gt;
								<font id="errorA2" color="red"></font>)
								<br />
								规范二： 受理员每周最高被抽查次数乘以受理员人数,必须大于,质检员一周(每天总和*5)的任务量乘以质检员人数 (
								<font id="errorB1" color="red"></font> &gt;
								<font id="errorB2" color="red"></font>)
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
  </body>
</html>
