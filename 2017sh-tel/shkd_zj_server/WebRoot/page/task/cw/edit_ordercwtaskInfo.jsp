<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>客调项目管理系统</title>
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	 <link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-dialog.css"
			rel="stylesheet" type="text/css" /> 
	<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script> 
	<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="<%=path%>/page/task/cw/edit_ordercwtask.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/util.js"></script>
    <style type="text/css">
		body{ font-size:13px;}
		input[type='button']{ border:#d3d3d3 1px solid; width:80px; height:25px; cursor:pointer;}
		td{ text-align:left;}
		/*.td_1{ text-align:right;}*/
		ul{width: 1000px;overflow: hidden;}
		li{float: left;text-align: left;height: 30px;line-height: 30px;}
		h3{
			text-align: left;
			width: 1000px;
			margin: 10px auto;
			color: darkblue;
			font-size: 13px;
		}
		.l-text input{height: 20px;}
	</style>
<script type="text/javascript"> 
		    var ctx = "${pageContext.request.contextPath}";
	</script> 
</head>
<body> 
	<div align="center" style="height:auto;">
		<h3>任务信息</h3>
		<form id="myform" method="post">
		<ul>
		
			 <li style="width: 260px;">任务名称：<input type="text" name="taskName"  id="taskName" onkeyup="strlenLimit(this,50);noSpecial(this);" maxlength="50" onblur="checkName();strlenLimit(this,50);noSpecial(this);" <c:if test="${type=='show'}">disabled="disabled"</c:if> value="${task.taskName}" /><font color="#FF0000">&nbsp;*</font>
			 <input  type="hidden"  id="oldName" value="${task.taskName}"/>
			<c:if test="${task.taskId ne null &&task.taskId !=''}">
			 <input type="hidden" id="taskId" name="taskId" value="${task.taskId}" />
			 </c:if>
			 <c:if test="${task.taskId eq null||task.taskId ==''}">
			 <input type="hidden" id="taskId" name="taskId" value="0" />
			 </c:if>
			 <input  type="hidden"  id="types" value="${type}"/>
			 </li>
			 <li style="width: 200px;position: relative;top: -2px;">
			 	  是否发布：<input type="radio" name="isPublish" value='1' <c:if test="${task==null}">checked="checked"</c:if>  <c:if test="${type=='show'}">disabled="disabled"</c:if> <c:if test="${task.isPublish=='1'}">checked="checked"</c:if>   />
			          是&nbsp;&nbsp;&nbsp;<input type="radio" name="isPublish" value='0' <c:if test="${type=='show'}">disabled="disabled"</c:if> <c:if test="${task.isPublish=='0'}">checked="checked"</c:if>  />
			          否<font color="#FF0000">&nbsp;*</font>
			 </li>
			 <li >
			 	任务周期：
			 	<input type="text" name="taskStartTime" <c:if test="${type=='show'}">disabled="disabled"</c:if>
									class="Wdate s_input"  id="STARTTIME"
									onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'ENDTIME\');}',dateFmt:'yyyy-MM-dd'})"
									onchange="timeChaOherCha('STARTTIME','ENDTIME','7','1')"
								 value="${task.taskStartTime}"	/>
							&nbsp;-&nbsp;>
							<input type="text" name="taskEndTime" <c:if test="${type=='show'}">disabled="disabled"</c:if>
									class="Wdate s_input"  id="ENDTIME"
									onfocus="WdatePicker({minDate:'#F{$dp.$D(\'STARTTIME\');}',dateFmt:'yyyy-MM-dd'})"
								value="${task.taskEndTime}"	 />
								
			 	<font color="#FF0000">&nbsp;*</font>
			 </li>
			 <li style="width: 1000px;">任务对象：	 
			 <input type="text" name="qcUserName" id="qcUserName" <c:if test="${type=='show'}">disabled="disabled"</c:if>  value="${task.qcUserName}" readonly="readonly" onclick="ondis()" style="width:760px ;"/><span id="qcNumber" style="color: gray;">0</span>人<font color="#FF0000">&nbsp;*(质检员，30人以内)</font>
			 <input type="hidden" id="qcUserWorkId" name="qcUserWorkId" value="${task.qcUserWorkId}"/>
			 <input type="hidden" id="QC_USER" name="qcUser" style="width: 75%"  value="${task.qcUserWorkId}" />
			 </li> 	
			<li style="height: 120px;width:100%;padding-left:62px;display: none;" id="conqcuser" >
			<table   cellpadding="3" cellspacing="3">
			<tr>
			<td>
			<select style="height: 120px; width: 150px" multiple <c:if test="${type=='show'}">readonly="readonly"</c:if>
											name="list1" size="10" id="list1"
											ondblclick="moveOption(document.getElementById('list1'), document.getElementById('list2'))">
											<c:forEach var="qcUser" items="${qcUserList}">
												<option value="${qcUser.userId}">
													${qcUser.userName}/${qcUser.workId}
												</option>
											</c:forEach>
										</select>
			</td>
			<td>							
										<input type="button" value="全部添加"
											onclick="moveAllOption(document.getElementById('list1'),document.getElementById('list3'))" />
										<br />
										<input type="button" value="添加"
											onclick="moveOption(document.getElementById('list1'), document.getElementById('list3'))" />
										<br />
										<input type="button" value="移除"
											onclick="moveOption(document.getElementById('list3'), document.getElementById('list1'))" />
										<br />
										<input type="button" value="全部移除"
											onclick="moveAllOption(document.getElementById('list3'), document.getElementById('list1'))" />							
					</td>
					
			<td>
			<select style="height: 120px; width: 150px" multiple
											name="list2" size="10" id="list3"
											ondblclick="moveOption(document.getElementById('list1'), document.getElementById('list2'))">
										</select>
			</td>		
					</tr>
					</table>	
								
			</li>
			
		
		     <li style="width: 260px;">受理员被抽检限制次数/天：<input type="text" onblur="showErrorAorBMsg()" name="checkcount" onkeyup="input_shuzi(this);strlenLimit(this,5);" maxlength="5" id="checkcount" <c:if test="${type=='show'}">disabled="disabled"</c:if> value="${task.checkcount}" style="width:52px ;"/><font color="#FF0000">&nbsp;*</font></li>
		     <li>质检员任务规格数/天：<input type="text" onblur="showErrorAorBMsg()" name="csrTopDCount" id="csrTopDCount" onkeyup="input_shuzi(this);strlenLimit(this,5);" maxlength="5" value="${task.csrTopDCount}" <c:if test="${type=='show'}">disabled="disabled"</c:if> style="width:52px ;"/><font color="#FF0000">&nbsp;*</font></li>
		</ul>
	
		<h3>工单c网筛选信息</h3>
		<table border="0" cellpadding="3" cellspacing="3" width="1000px" style="clear: both;">
			<tr height="30">
				<td class="td_1" width="60">受理员：</td>
				<td colspan="7">
				<input style="width: 760px;" id="TEL_USER_NAME" <c:if test="${type=='show'}">disabled="disabled"</c:if>
								name="telUserName" value="${task.telUserName}"
								onclick="hiddenQc('conqcuser');getOperatorList(this,this.value);" onblur="showErrorAorBMsg()" />
							&nbsp;
							<span id="telNumber2" style="color: gray;">0</span>人
							<font color="#FF0000">&nbsp;*(30人以内)</font>
							<input type="hidden" id="TEL_USER" name="telUser" value="${task.telUser}" />
							<input type="hidden" id="TEL_USER_WORKID" name="TEL_USER_WORKID" value="" />
							<input type="hidden" id="papers_hid" value="${task.papers_hid}"/>
				</td>			
				<!-- <input style="width:760px ;"/>&nbsp;xx人&nbsp;&nbsp;<font color="#FF0000">&nbsp;*(30人以内)</font></td> -->
			</tr>
			
			<tr height="30">
				<td class="td_1">申告业务大类：</td>
				<td>
				<input type="text" id="sgdltype"  name="sgdltype"  /> 
				<input type="hidden" id="sgdltype2" value="${task.sgdltype}" />			
				</td>
				<td class="td_1" width="90px" >申告业务小类：</td>
				<td>
				<input type="text" id="sgxltype" name="sgxltype" /> 
				<input type="hidden" id="sgxltype2" value="${task.sgxltype}" />
				</td>
				<td class="td_1" width="110px">申告现象：</td>
				<td >
				<input type="text" id="sgxxtype" name="sgxxtype"  /> 
				<input type="hidden" id="sgxxtype2" value="${task.sgxxtype}" />
				</td>
				<td class="td_1" width="60px">一级处理方式：</td>
				<td>
				<input type="text" id="clfstype" name="clfstype"  /> 
				<input type="hidden" id="clfstype2" value="${task.clfstype}" />
				</td>
			</tr>
			<tr height="30">
				<td class="td_1">一级结案代码：</td>
				<td>
				<input type="text" id="jadmtype" name="jadmtype"  /> 
				<input type="hidden" id="jadmtype2" value="${task.jadmtype}" />
				</td>
				<td class="td_1">二级申告现象：</td>
				<td>
				<input type="text" id="secsgxxtype" name="secsgxxtype"  /> 
				<input type="hidden" id="secsgxxtype2" value="${task.secsgxxtype}" />
				</td>
				<td class="td_1">是否应该转二级：</td>
				<td>
				<input type="text" id="zejtype" name="zejtype"   /> 
				<input type="hidden" id="zejtype2" value="${task.zejtype}" />
				</td>
				<td class="td_1">二级预处理结案代码：</td>
				<td>
				<input type="text" id="ycltype" name="ycltype"   /> 
				<input type="hidden" id="ycltype2" value="${task.ycltype}" />
				</td>
			</tr>
			<tr>
			<td colspan="8" class="td_1">
			<div >
								规范一： 受理员数量乘以受理员抽查次数/天,必须大于,质检员数量乘以一个质检员一天规格的总和 (
								<font id="errorA1" color="red"></font> &gt;
								<font id="errorA2" color="red"></font>)
			</div>
			</td>
			</tr>
			<tr height="100">
				<td colspan="8" style="text-align:center;">
					<c:if test="${type!='show'}">
					<input type="button" value="提交" onclick="addTaskSave()" />&nbsp;&nbsp;&nbsp;&nbsp;
					</c:if>
					<input type="button" value="返回" onclick="history.go(-1);" />
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
