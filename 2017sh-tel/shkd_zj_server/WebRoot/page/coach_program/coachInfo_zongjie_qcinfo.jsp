<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/shiro.tld" prefix="shiro"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String coachproject=request.getAttribute("coachproject").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客调项目管理系统</title>
<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js"
	type="text/javascript"></script>
<script src="<%=path%>/js/util.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js"
	type="text/javascript"></script>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-dialog.css"
	rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css"
	rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript"
	src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
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

table tr td {
	height: 30px;
}

.td1 {
	text-align: right;
}

.td2 {
	text-align: left;
}
.showtr{
    display:none;
}
</style>
	<script type="text/javascript"> 
		 var ctx = "${pageContext.request.contextPath}";
		 var dialog=frameElement.dialog; //调用页面的dialog对象(ligerui对象)
		$(function(){
		  var coachproject="<%=coachproject%>";
		  var coachpro= new Array(); 
		  if(coachproject!=null||coachproject!=""){
		   coachpro=coachproject.split(",");
		 
			  for ( var i = 0; i < coachpro.length; i++) {
			
				if(coachpro[i]=="1"){
				document.getElementById("check_01").checked="checked";
				}
				if(coachpro[i]=="2"){
				document.getElementById("check_02").checked="checked";
				}
				if(coachpro[i]=="3"){
				document.getElementById("check_03").checked="checked";
				}
				if(coachpro[i]=="4"){
				document.getElementById("check_04").checked="checked";
				}
				if(coachpro[i]=="5"){
				document.getElementById("check_05").checked="checked";
				}
				if(coachpro[i]=="6"){
				document.getElementById("check_06").checked="checked";
				}
				if(coachpro[i]=="7"){
				document.getElementById("check_07").checked="checked";
				}
			}  
			
			
		 }
		 var isgj=$("#isgj").val();
			if(isgj==1){
			   $("#check1_01").attr("checked","checked");
			}else if(isgj==2){
			   $("#check1_02").attr("checked","checked");
			}
		 var sumImprove=$("#sumImprove").val();
				if(sumImprove=="0" || sumImprove=="1"){
					if(sumImprove==1){
					   $("#checkresultshow1").attr("checked","checked");
					}else if(sumImprove==0){
					   $("#checkresultshow0").attr("checked","checked");
					}
					$(".showtr").css("display","table-row");
				}
		});
		
	</script>
</head>
<body>
	<div align="center" style="height:auto;">
		<input type="hidden" id="coachmainId" value="${coachmainId}" />
		<table cellpadding="5" cellpadding="5" width="600px;" border="0"
			style="">
			<tr>
					<td class="td1" valign="top" width="100">
						本月辅导项目：
					</td>
					<td class="td2">
						<c:forEach items="${coachteam}" var="coachteam">
							<input type="checkbox" name="sumCoachproject"
								id="check_0${coachteam.teamvalue}"
								value="${coachteam.teamvalue}" disabled="disabled"/>
							<label for="check_0${coachteam.teamvalue}">
								${coachteam.teamname}
							</label>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td class="td1" valign="top">
						辅导总结：
					</td>
					<td class="td2">
						<textarea style="width: 500px;" disabled="disabled">${coac.sumSummarize}</textarea>
					</td>
				</tr>
				<tr>
					<td class="td1">
						是否改进：
					</td>
					<td class="td2">
						<input id="isgj" value="${coac.isgj}" type="hidden" />
						<input disabled="disabled" type="radio" name="xiangmu1"
							id="check1_01" />
						<label for="check1_01">
							&nbsp;已改进
						</label>
						&nbsp;&nbsp;
						<input disabled="disabled" type="radio" name="xiangmu1"
							id="check1_02" />
						<label for="check1_02">
							&nbsp;未改进
						</label>
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td class="td1">
						质检建议：
					</td>
					<td class="td2">
						<textarea style="width: 500px;" disabled="disabled">${coac.qcremark}</textarea>
					</td>
				</tr>
				<tr class="showtr">
					<td class="td1">
						审核结果
					</td>
					<td class="td2">
					   <input type="hidden" value="${coac.sumImprove}" id="sumImprove" />
						<input type="radio" value="1" id="checkresultshow1" disabled="disabled" name="checkresultshow"/>
						&nbsp;通过&nbsp;&nbsp;
						<input type="radio" value="0" id="checkresultshow0" disabled="disabled" name="checkresultshow"/>
						&nbsp;不通过
					</td>
				</tr>
				<tr class="showtr">
					<td class="td1" valign="top">
						督导审核建议
					</td>
					<td class="td2">
						<textarea style="width: 500px;" disabled="disabled">${coac.sumSuggest}</textarea>
					</td>
				</tr>
		</table>
		<div style="margin-top:10px;">
			<input type="button" value="关闭"
				onclick="frameElement.dialog.close();" />
		</div>
	</div>
</body>
</html>
