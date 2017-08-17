<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String coachproject=request.getAttribute("coachproject").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>客调项目管理系统</title>
		<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css"
			rel="stylesheet" type="text/css" />
		<script src="<%=path%>/js/jquery-1.9.1.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
		<script src="<%=path%>/lib/ligerUI/js/core/base.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js"
			type="text/javascript"></script>
		<style type="text/css">
html,body {
	font-size: 12px;
	width: 100%;
	height: 100%;
}

input[type='button'] {
	border: #d3d3d3 1px solid;
	width: 80px;
	height: 25px;
	cursor: pointer;
}

.table_tr tr {
	height: 30px
}

.td1 {
	text-align: center;
}

.td2 {
	text-align: left;
}
</style>
		<script type="text/javascript"> 
		 var ctx = "${pageContext.request.contextPath}";
		$(function () {
     
          $("input[type='radio']").change(function() {
					var $selectedvalue = $("input[name='checkresult']:checked").val();
					if ($selectedvalue == 1) {
						$("#nopass").css("display","none");
					} else {
						$("#nopass").css("display","");
					}
				});
          
          
		  var date=new Date;
		  var month=date.getMonth();
		  $("#mon").html(month);
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
			if(coachpro[i]=="8"){
			document.getElementById("check_08").checked="checked";
			}
		}  
		  }
		});
	  function submit(){
            var pass=$('input:radio:checked').val();
            var passinfo=$("#passinfo").val();
            var coachmainId=$("#coachmainId").val();
          $.ligerDialog.confirm('您确认要提交吗？', function (yes) {
          if(yes){
	           	$.ajax({
					 url: ctx+"/controller/coach/DuDaoCheckCoachMain.do",
				     type: "post",
				     data:{pass:pass,passinfo:passinfo,coachmainId:coachmainId},
				     dataType: "json",
				     success: function(result){ 
				    	 if(result.data==1){
				    	    $.ligerDialog.success("提交成功","提示",function(yes){
				    	    // location.href=ctx+"/page/coach_program/coachInfoList_dudao.jsp";
				    	    history.go(-1);
				    	    });
				    	   
				    	 }
				    	
				      },error:function(){
				       $.ligerDialog.error("提交失败");
				      }
			       });
		      }
            });
        }
	</script>
	</head>
	<body>
		<div align="center" style="">
				<input type="hidden" id="userid" name="userid" />
				<input type="hidden" value="${coac.coachproject}" id="coachproject" />
				<input type="hidden" value="${coachmainId}" id="coachmainId" />
				<table cellpadding="5" cellpadding="5" width="100%" border="0"
					class="table_tr">
					<tr>
						<td class="td1" width="120">
							受理员对象：
						</td>
						<td class="td2">
							<input type="text" id="username" name="username" class="obj"
								value="${coac.username}" disabled="disabled" />
							<!-- <input type="hidden" id="userid" name="userid" value="" /> -->
						</td>
						<td class="td1">
							辅导开始时间：
						</td>
						<td class="td2">
							<input class="Wdate date_text" type="text" id="starttime"
								disabled="disabled" name="starttime" value="${coac.starttime}"
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
						</td>
						<td class="td1">
							辅导结束时间：
						</td>
						<td class="td2">
							<input class="Wdate date_text" type="text" id="endtime"
								disabled="disabled" name="endtime" value="${coac.endtime}"
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
						</td>
					</tr>
					<tr>
						<td class="td1">
							辅导项目：
						</td>

						<td class="td2" colspan="5">
							<c:forEach items="${coachteam}" var="coachteam">
								<input type="checkbox" name="coachproject"
									id="check_0${coachteam.teamvalue}" disabled="disabled"
									value="${coachteam.teamvalue}" />
								<label for="check_0${coachteam.teamvalue}">
									&nbsp;${coachteam.teamname}
								</label>
							&nbsp;&nbsp;
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td class="td1" valign="top">
							改进措施
						</td>
						<td class="td2" colspan="5">
							<textarea
								style="width: 90%; height: 80px; max-height: 100px; max-width: 90%"
								disabled="disabled" id="improve" name="improve">${coac.remaker}</textarea>
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<span id="mon"></span>月份服务指标
						</td>
					</tr>
					<tr>
						<td class="td1">
							规范用语
						</td>
						<td class="td2">
							<input type="text" id="specificationlanguage" disabled="disabled"
								name="specificationlanguage"
								value="${coacs.specificationlanguage}" />
						</td>
						<td class="td1">
							礼貌语调
						</td>
						<td class="td2">
							<input type="text" id="politetoneoofvoice" disabled="disabled"
								name="politetoneoofvoice" value="${coacs.politetoneoofvoice}" />
						</td>
						<td class="td1">
							沟通能力
						</td>
						<td class="td2">
							<input type="text" id="abilitytocommunicate" disabled="disabled"
								name="abilitytocommunicate"
								value="${coacs.abilitytocommunicate}" />
						</td>
					</tr>
					<tr>
						<td class="td1">
							异议处理
						</td>
						<td class="td2">
							<input type="text" id="objectionhandling" disabled="disabled"
								name="objectionhandling" value="${coacs.objectionhandling}" />
						</td>
						<td class="td1">
							业务流程规范
						</td>
						<td class="td2">
							<input type="text" id="flowstandard" disabled="disabled"
								name="flowstandard" value="${coacs.flowstandard}" />
						</td>
						<td class="td1">
							致命差错数/率
						</td>
						<td class="td2">
							<input type="text" id="deadlyerror" disabled="disabled"
								name="deadlyerror" value="${coacs.deadlyerror}" />
						</td>
					</tr>
					<tr>
						<td class="td1">
							营销技巧
						</td>
						<td class="td2">
							<input type="text" id="marketingskills" disabled="disabled"
								name="marketingskills" value="${coacs.marketingskills}" />
						</td>
						<td class="td1">
							&nbsp;
						</td>
						<td class="td2">
							&nbsp;
						</td>
						<td class="td1">
							&nbsp;
						</td>
						<td class="td2">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td class="td1">
							审核结果
						</td>
						<td class="td2">
							<input type="radio" value="1" name="checkresult"/>
							&nbsp;通过&nbsp;&nbsp;
							<input type="radio" value="0" name="checkresult"/>
							&nbsp;不通过
						</td>
						<td class="td1">
							&nbsp;
						</td>
						<td class="td2">
							&nbsp;
						</td>
						<td class="td1">
							&nbsp;
						</td>
						<td class="td2">
							&nbsp;
						</td>
					</tr>
					<tr id="nopass" style="display:none">
						<td class="td1" valign="top">
							审核不通过原因
						</td>
						<td class="td2" colspan="5">
							<textarea
								style="width: 90%; height: 80px; max-height: 100px; max-width: 90%"
								id="passinfo" name="passinfo"></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="6" align="center"
							style="padding-top: 15px; padding-bottom: 15px;">
							<input type="button" value="提交" onclick="submit()" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="返回" onclick="history.go(-1);" />
						</td>
					</tr>
				</table>
		</div>
	</body>
</html>
