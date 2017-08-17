<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>客调项目管理系统</title>
	<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
	<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script> 
	<script language="javascript" type="text/javascript" src="<%=path %>/js/My97DatePicker/WdatePicker.js"></script>
    <style type="text/css">
		body{ font-size:13px;}
		input[type='button']{ border:#d3d3d3 1px solid; width:80px; height:25px; cursor:pointer;}
		table tr td{ height:30px;}
		.td1{ text-align:right;}
		.td2{ text-align:left;}
	</style>
	<script type="text/javascript"> 
		
	</script> 
</head>
<body> 
	<div align="center" style="height:auto;">
		<table cellpadding="5" cellpadding="5" width="700px;"  border="0" style="">
			<tr>
				<td class="td1">周计划周期：</td><td class="td2"><input value="2000-01-01 00:00:00" disabled="disabled" class="Wdate date_text" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />&nbsp;~&nbsp;<input value="2000-01-01 00:00:00" disabled="disabled" class="Wdate date_text" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
			</tr>
			<tr>
				<td class="td1" valign="top">辅导项目：</td>
				<td class="td2">
					<input checked="checked" disabled="disabled" type="checkbox" name="xiangmu" id="check_01" /><label for="check_01">&nbsp;规范用语</label>&nbsp;&nbsp;
					<input checked="checked" disabled="disabled" type="checkbox" name="xiangmu" id="check_02" /><label for="check_02">&nbsp;礼貌语调</label>&nbsp;&nbsp;
					<input checked="checked" disabled="disabled" type="checkbox" name="xiangmu" id="check_03" /><label for="check_03">&nbsp;沟通能力</label>&nbsp;&nbsp;
					<input disabled="disabled" type="checkbox" name="xiangmu" id="check_04" /><label for="check_04">&nbsp;异议处理</label>&nbsp;&nbsp;
					<input disabled="disabled" type="checkbox" name="xiangmu" id="check_05" /><label for="check_05">&nbsp;业务流程规范</label>&nbsp;&nbsp;<br />
					<input disabled="disabled" type="checkbox" name="xiangmu" id="check_06" /><label for="check_06">&nbsp;业务差错</label>&nbsp;&nbsp;
					<input disabled="disabled" type="checkbox" name="xiangmu" id="check_07" /><label for="check_07">&nbsp;营销技巧</label>&nbsp;&nbsp;
					<input disabled="disabled" type="checkbox" name="xiangmu" id="check_08" /><label for="check_08">&nbsp;单据</label>
				</td>
			</tr>
			<tr>
				<td class="td1">辅导方法：</td>
				<td class="td2">
					<input disabled="disabled" type="radio" name="xiangmu1" id="check1_01" /><label for="check1_01">&nbsp;旁听</label>&nbsp;&nbsp;
					<input disabled="disabled" type="radio" name="xiangmu1" id="check1_02" /><label for="check1_02">&nbsp;业务指导</label>&nbsp;&nbsp;
					<input disabled="disabled" type="radio" name="xiangmu1" id="check1_03" /><label for="check1_03">&nbsp;员工关怀</label>&nbsp;&nbsp;
					<input disabled="disabled" type="radio" name="xiangmu1" id="check1_04" /><label for="check1_04">&nbsp;其他</label>&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td class="td1" valign="top">辅导记录：</td>
				<td class="td2"><textarea style="width:500px;" disabled="disabled">xxx</textarea></td>
			</tr>
			<tr>
				<td class="td1">辅导评价：</td>
				<td class="td2">
					<input disabled="disabled" type="radio" name="xiangmu11" id="check11_01" /><label for="check11_01">&nbsp;满意</label>&nbsp;&nbsp;
					<input disabled="disabled" type="radio" name="xiangmu11" id="check11_02" /><label for="check11_02">&nbsp;不满意</label>&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td class="td1" valign="top">不满意愿意：</td>
				<td class="td2"><textarea style="width:500px;" disabled="disabled">xxx</textarea></td>
			</tr>
			<tr>
				<td class="td1" valign="top">班长描述：</td>
				<td class="td2"><textarea style="width:500px;" disabled="disabled">xxx</textarea></td>
			</tr>
		</table> 
		<div style="margin-top:10px;">
			<input type="button" value="返回" onclick="frameElement.dialog.close();" />
		</div>
	</div>
</body>
</html>
