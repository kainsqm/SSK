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

</style>
<script type="text/javascript"> 
		
		function onsub(){
		var coachmainId=$("#coachmainId").val();
		 var sumCoachproject=""; 
	 	var aa = document.getElementsByName("sumCoachproject");
                for (var i = 0; i < aa.length; i++) {
                    if (aa[i].checked) {
                        sumCoachproject += aa[i].value+",";
                    }
                }
              
	 var sumSummarize=$("#sumSummarize").val();
	 var isgj=$("input[name='isgj']:checked").val();
	 var qcremark=$("#qcremark").val();
	 if(sumCoachproject==""||sumCoachproject==null){
	 			$.ligerDialog.warn('辅导项目不能为空');
	 			return false;
		}
	 if(sumSummarize==""||sumSummarize==null){
	 			$.ligerDialog.warn('辅导总结不能为空');
	 			return false;
		}	
	 if(isgj==""||isgj==null){
	 			$.ligerDialog.warn('请选择是否改进');
	 			return false;
		}
	 if(qcremark==""||qcremark==null){
	 			$.ligerDialog.warn('质检建议不能为空');
	 			return false;
		}		
			
		$.ligerDialog.confirm('您确认提交吗？', function (yes) { 
				if(yes){
		$.ajax({
				url:"<%=path%>/controller/coach/addCoachzj.do",
					data : {
						coachmainId : coachmainId,
						sumCoachproject : sumCoachproject,
						sumSummarize : sumSummarize,
						isgj : isgj,
						qcremark : qcremark,
					},
					type : "post",
					dataType : "json",
					success : function(result) {
						if (result.blag) {
							$.ligerDialog.success('发布成功', '提示', function(yes) {
								//window.parent.diszj();
								frameElement.dialog.close();
								window.location.href="<%=path%>/controller/coach/togetCoachMonth.do";
							});
						} else {
							$.ligerDialog.warn('发布失败');
						}
					},
					error : function(error) {
						$.ligerDialog.warn('发布异常');
					}

				});
			}
		});

	}
	
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
			
			var isgj=$("#isgjqian").val();
			if(isgj==1){
			   $("#check1_01").attr("checked","checked");
			}else if(isgj==2){
			   $("#check1_02").attr("checked","checked");
			}
			
			
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
				<td class="td1" valign="top" width="80">本月辅导项目：</td>
				<td class="td2"><c:forEach items="${coachteam}" var="coachteam">
						<input type="checkbox" name="sumCoachproject"
							id="check_0${coachteam.teamvalue}" value="${coachteam.teamvalue}" />
						<label for="check_0${coachteam.teamvalue}"> ${coachteam.teamname} </label>
					</c:forEach></td>
			</tr>
			<tr>
				<td class="td1" valign="top">辅导总结：</td>
				<td class="td2"><textarea style="width:500px;"
						id="sumSummarize" name="sumSummarize">${coac.sumSummarize}</textarea>
				</td>
			</tr>
			<tr>
				<td class="td1">是否改进：</td>
				<td class="td2">
				<input id="isgjqian" value="${coac.isgj}" type="hidden" />
				<input type="hidden" value="${coac.sumImprove}" id="sumImprove" />
				<input type="radio" name="isgj" id="check1_01"
					value="1" /><label for="check1_01">&nbsp;已改进</label>&nbsp;&nbsp; <input
					type="radio" name="isgj" id="check1_02" value="2" /><label
					for="check1_02">&nbsp;未改进</label>&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td class="td1">质检建议：</td>
				<td class="td2"><textarea style="width:500px;" name="qcremark"
						id="qcremark">${coac.qcremark}</textarea>
				</td>
			</tr>
		</table>
		<div style="margin-top:10px;">
			<shiro:hasPermission name='coach:zjsub'>
			<input type="button" value="提交" onclick="onsub()" /></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="返回"
				onclick="frameElement.dialog.close();" />
		</div>
	</div>
</body>
</html>
