<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/shiro.tld" prefix="shiro"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String role=request.getAttribute("role_flag").toString();
String msg=(String)request.getAttribute("msg");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>客调项目管理系统</title>
	<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script> 
	 <script src="<%=path%>/js/util.js" type="text/javascript"></script>
	  <script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
	  <script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script> 
	<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
    <style type="text/css">
		body{ font-size:13px;}
		input[type='button']{ border:#d3d3d3 1px solid; width:80px; height:25px; cursor:pointer;}
		table tr td{ height:30px;}
		.td1{ text-align:right;}
		.td2{ text-align:left;}
	</style>
	<script type="text/javascript"> 
	$(function(){
	
	var coachmethod=$("#coachmethod").val();
	if(coachmethod=="1"){
	document.getElementById("check1_01").checked="checked";
	}else if(coachmethod=="2"){
	document.getElementById("check1_02").checked="checked";
	}else if(coachmethod=="3"){
	document.getElementById("check1_03").checked="checked";
	}else if(coachmethod=="4"){
	document.getElementById("qt").style.display="";
	document.getElementById("check1_04").checked="checked";
	}
	
	var coachproject=$("#coachproject").val();
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
		  var comforablestate=$("#comforablestate").val();		  
		  if(comforablestate=="0"){
				document.getElementById("check11_01").checked="checked";
			}else if(comforablestate=="1"){		
				document.getElementById("check11_02").checked="checked";
				document.getElementById("bmy").style.display="";	
				$("#uncomforable").attr("disabled",true); 
			}
		  var state=$("#state").val();
		  var role="<%=role%>";
		  var msg="<%=msg%>";
		  if(msg=="info"){
		  	document.getElementById("starttime").disabled=true;
			document.getElementById("endtime").disabled=true;
			document.getElementById("check_01").disabled=true;
			document.getElementById("check_02").disabled=true;
			document.getElementById("check_03").disabled=true;
			document.getElementById("check_04").disabled=true;
			document.getElementById("check_05").disabled=true;
			document.getElementById("check_06").disabled=true;
			document.getElementById("check_07").disabled=true;
			document.getElementById("check_08").disabled=true;
			document.getElementById("check1_01").disabled=true;
			document.getElementById("check1_02").disabled=true;
			document.getElementById("check1_03").disabled=true;
			document.getElementById("check1_04").disabled=true;
			document.getElementById("instructions").disabled=true;
			document.getElementById("coachrecord").disabled=true;
		  	/* document.getElementById("uncomforable").disabled=true; */
		/* 	document.getElementById("check11_01").disabled=true;
			document.getElementById("check11_02").disabled=true;
			document.getElementById("uncomforable").disabled=true;
			document.getElementById("describe").disabled=true;	   */
		  }
		  
			
		if(role=="role_1"){
			if(state=="1"){
			document.getElementById("pj").style.display="";			
			}else if(state=="2"){
			document.getElementById("pj").style.display="";
			document.getElementById("check11_01").disabled=true;
			document.getElementById("check11_02").disabled=true;
			document.getElementById("uncomforable").disabled=true;
			}else if(state=="3"){
			document.getElementById("pj").style.display="";
			document.getElementById("check11_01").disabled=true;
			document.getElementById("check11_02").disabled=true;
			document.getElementById("uncomforable").disabled=true;
			document.getElementById("ms").style.display="";
			document.getElementById("describe").disabled=true;
			}
			}
				
			if(role=="role_2"){
				if(state=="1"){
			}else if(state=="2"){
			document.getElementById("pj").style.display="";
			document.getElementById("check11_01").disabled=true;
			document.getElementById("check11_02").disabled=true;
			document.getElementById("uncomforable").disabled=true;
			document.getElementById("ms").style.display="";
			}else if(state=="3"){
			document.getElementById("pj").style.display="";
			document.getElementById("check11_01").disabled=true;
			document.getElementById("check11_02").disabled=true;
			document.getElementById("uncomforable").disabled=true;
			document.getElementById("ms").style.display="";
			document.getElementById("describe").disabled=true;
			}
			}
	
	}
	);
	
		function onpj(){
		  document.getElementById("bmy").style.display="";	
		}
		function onmy(){
		document.getElementById("uncomforable").value="";
		document.getElementById("bmy").style.display="none";	
		}
		
		function onother(){
		document.getElementById("qt").style.display="";
		     
		}
		function onelse(){
		document.getElementById("qt").style.display="none";
		     
		}
		
		function onsub(result){
		if("upd"==result){
		var role="<%=role%>";
		var comforablestate=$("input[name='comforablestate']:checked").val();
		var uncomforable=$("#uncomforable").val();
		var describe=$("#describe").val();
		if(role=="role_1"){
		if(comforablestate==null||comforablestate==""){
		 $.ligerDialog.error('辅导评价不能为空');
		 return false;
		}else{
		if(comforablestate=="1"&&(uncomforable==null| uncomforable=="")){
		 $.ligerDialog.error('不满意原因不能为空');
		 return false;
		}
		}
		}
		if(role=="role_2"){
		if(describe==null||describe==""){
		 $.ligerDialog.error('班长描述不能为空');
		  return false;
		}
		}
		
		var id=$("#id").val();
		$.ligerDialog.confirm('您确认操作吗？', function (yes) { 
				if(yes){ 
	 $.ajax({
	 		url:"<%=path%>/controller/coach/updCoachInfo.do",
	 		type:"post",
			data:{
					id:id,
					comforablestate:comforablestate,
					uncomforable:uncomforable,
					describe:describe,
			}, 
			dataType:"json",		
			success:function(result){
				if(result.flag){
					$.ligerDialog.success('操作成功','提示',function(yes){
						parent.location.reload(true);
					    frameElement.dialog.close();
					});
				}else{
				$.ligerDialog.warn('操作失败!');
				}
			
			},
			error:function(error){
			$.ligerDialog.warn('系统异常，请稍后再试!');
			}
	 });
	 
	 }
	 });	
		
		
		
		}else{
	 var coachmainId=$("#coachmainId").val();
	 var starttime=$("#starttime").val();
	 var endtime=$("#endtime").val();
	 var coachproject=""; 
	 var aa = document.getElementsByName("coachproject");
                for (var i = 0; i < aa.length; i++) {
                    if (aa[i].checked) {
                        coachproject += aa[i].value+",";
                    }
                }
              
	 var coachmethod=$("input[name='coachmethod']:checked").val();
	 
	 var instructions=$("#instructions").val();
	 var coachrecord=$("#coachrecord").val();
	 var comforablestate=$("input[name='comforablestate']:checked").val();
	 var uncomforable=$("#uncomforable").val();
	 var describe=$("#describe").val();
	 
	 var coachstarttime=$("#coachstarttime").val();
	 var coachendtime=$("#coachendtime").val();
	   var d1 = new Date(starttime.replace(/\-/g, "\/"));  
 	   var d2 = new Date(endtime.replace(/\-/g, "\/"));  
 	   var d3 = new Date(coachstarttime.replace(/\-/g, "\/"));  
 	   var d4 = new Date(coachendtime.replace(/\-/g, "\/"));  
	 
	 if(starttime==""||starttime==null){
	 $.ligerDialog.warn('周计划周期不能为空');
	 return false;
	 }
	 if(endtime==""||endtime==null){
	$.ligerDialog.warn('周计划周期不能为空');
	 return false;
	 }	
  	if(d1<d3){  
 	$.ligerDialog.warn('开始时间不能小于'+coachstarttime);
  	return false;  
 	}
	 if(d2>d4){  
 	$.ligerDialog.warn('结束时间不能大于'+coachendtime);
  	return false;  
 	}	 
	 if(coachproject==""||coachproject==null){
	 $.ligerDialog.warn('辅导项目不能为空');
	 return false;
	 }
	
	 if(coachmethod==""||coachmethod==null){
	 $.ligerDialog.warn('辅导方法不能为空');
	 return false;
	 }
	 if(coachrecord==""||coachrecord==null){
	 $.ligerDialog.warn('辅导记录不能为空');
	 return false;
	 }
	


	$.ligerDialog.confirm('您确认新增吗？', function (yes) { 
				if(yes){ 
	 $.ajax({
	 		url:"<%=path%>/controller/coach/addCoachInfo.do",
	 		type:"post",
			data:{
					coachmainId:coachmainId,
					starttime:starttime,
					endtime:endtime,
					coachproject:coachproject,
					coachmethod:coachmethod,
					instructions:instructions,
					coachrecord:coachrecord,	
					comforablestate:comforablestate,
					uncomforable:uncomforable,
					describe:describe,
			}, 
			dataType:"json",		
			success:function(result){
				if(result.flag){
				$.ligerDialog.success('添加成功','提示',function(yes){
					parent.location.reload(true);
				frameElement.dialog.close();
				});
				}else{
				$.ligerDialog.warn('添加失败!');
				}
			
			},
			error:function(error){
			$.ligerDialog.warn('系统异常，请稍后再试!');
			}
	 });
	 
	 }
	 });	
		
		}
		}
		
	</script> 
</head>
<body> 
	<div align="center" style="height:auto;">
	<input type="hidden" value="${coachmainId}" id="coachmainId" />
	<input  type="hidden" value="${coachinfo.coachproject}" id="coachproject" />
	<input  type="hidden" value="${coachinfo.coachmethod}" id="coachmethod" />
	<input  type="hidden" value="${coachinfo.comforablestate}" id="comforablestate" />
	<input  type="hidden" value="${coachinfo.state}" id="state" />
	<input   type="hidden" value="${coach.starttime}" id="coachstarttime" />
	<input   type="hidden" value="${coach.endtime}" id="coachendtime" />
	<input   type="hidden" value="${id}" id="id" />
	
	
		<table cellpadding="5" cellpadding="5" width="100%"  border="0" style="">
			<tr>
				<td class="td1" width="90">周计划周期：</td>
				<td class="td2" >
				<input id="starttime" name="starttime" value="${coachinfo.starttime}"  class="Wdate date_text" type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endtime\');}',dateFmt:'yyyy-MM-dd'})" />&nbsp;~&nbsp;
				<input id="endtime" name="endtime" value="${coachinfo.endtime}"  class="Wdate date_text" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'starttime\');}',dateFmt:'yyyy-MM-dd'})" /><span style="color: red">周计划周期必须在${coach.starttime}和${coach.endtime}之间</span></td>
			</tr>
			<tr>
				<td class="td1" valign="top" width="80">辅导项目：</td>
				<td class="td2">
					<c:forEach items="${coachteam}" var="coachteam">
							<input type="checkbox" name="coachproject" id="check_0${coachteam.teamvalue}" value="${coachteam.teamvalue}"/>
							<label for="check_0${coachteam.teamvalue}">
							${coachteam.teamname}
							</label>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td class="td1" width="80">辅导方法：</td>
				<td class="td2">
					<input  type="radio" name="coachmethod" id="check1_01" value="1" onclick="onelse()"/><label for="check1_01">&nbsp;旁听</label>&nbsp;&nbsp;
					<input  type="radio" name="coachmethod" id="check1_02" value="2" onclick="onelse()"/><label for="check1_02">&nbsp;业务指导</label>&nbsp;&nbsp;
					<input  type="radio" name="coachmethod" id="check1_03" value="3"  onclick="onelse()"/><label for="check1_03">&nbsp;员工关怀</label>&nbsp;&nbsp;
					<input  type="radio" name="coachmethod" id="check1_04"  value="4" onclick="onother()" /><label for="check1_04">&nbsp;其他</label>&nbsp;&nbsp;
				</td>
			</tr>

			<tr id="qt" style="display: none">
				<td class="td1" valign="top" width="80">辅导说明：</td>
				<td class="td2"><textarea style="width:500px;"   id="instructions" name="instructions" >${coachinfo.instructions}</textarea></td>
			</tr>
			<tr>
				<td class="td1" valign="top" width="80">辅导记录：</td>
				<td class="td2"><textarea style="width:500px;" id="coachrecord" name="coachrecord" >${coachinfo.coachrecord}</textarea></td>
			</tr>
			
			<tr id="pj" style="display: none">
				<td class="td1">辅导评价：</td>
				<td class="td2">
					<input  type="radio" name="comforablestate" id="check11_01" value="0" onclick="onmy()"/><label for="check11_01" >&nbsp;满意</label>&nbsp;&nbsp;
					<input  type="radio" name="comforablestate" id="check11_02" value="1" onclick="onpj()" /><label for="check11_02">&nbsp;不满意</label>&nbsp;&nbsp;
				</td>
			</tr>
			
			
			<tr id="bmy" style="display: none" >
				<td class="td1" valign="top">不满意原因：</td>
				<td class="td2"><textarea style="width:500px;"  id="uncomforable" name="uncomforable" >${coachinfo.uncomforable}</textarea></td>
			</tr>
			
			<tr id="ms" style="display: none">
				<td class="td1" valign="top">班长描述：</td>
				<td class="td2"><textarea style="width:500px;"   id="describe" name="describe" >${coachinfo.describe}</textarea></td>
			</tr>
			
		</table> 
		<div style="margin-top:10px;">
		<c:if test="${role_flag=='role_3'}">
		    <c:if test="${coachinfo.coachproject==null||coachinfo.coachproject==''}">
			     <shiro:hasPermission name='coach:upd'><input type="button" value="提交" onclick="onsub()" /></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp;
			</c:if>
		</c:if>
			<c:if test="${role_flag=='role_1'&&coachinfo.state=='1'}">
			<shiro:hasPermission name='csrcoach:sub'><input type="button" value="提交" onclick="onsub('upd')" /></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp;
			</c:if>
			<c:if test="${role_flag=='role_2'&&coachinfo.state=='2'}">
			<input type="button" value="提交" onclick="onsub('upd')" />&nbsp;&nbsp;&nbsp;&nbsp;
			</c:if>

			<input type="button" value="返回" onclick="frameElement.dialog.close();" />
		</div>
	</div>
</body>
</html>
