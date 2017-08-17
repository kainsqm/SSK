<%@page import="cn.sh.ideal.model.UserInfo"%>
<%@page import="cn.sh.ideal.model.AgentWorkpaper"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>  
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%> 
<%
  String path = request.getContextPath();
  AgentWorkpaper agent=(AgentWorkpaper)request.getAttribute("agent");
  String validate=agent.getAgentvalidate();
  String state=agent.getDealstate(); 
  String role=request.getAttribute("role_flag").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<title>反馈单详情</title>
	<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/lib/jquery/jquery-1.4.4.min.js" type="text/javascript"></script> 
	<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="<%=path%>/js/util.js" type="text/javascript"></script>
	<style type="text/css">
		body{ font-size:13px;overflow: auto !important;}
		.head_h3{ width:100%; height:36px; line-height:36px; background:url(<%=path%>/img/login/toppic.jpg); margin-top:-3px;}
		.head_h3 h3{ color:#ffffff; margin-left:70px; font-size:16px;}
		
		.mainDiv{ width:90%; height:auto; border:#CCCCFF 1px solid; margin:auto; padding:20px; font-size:14px; margin-top:10px;}
		.inputText{ border:none; border-bottom:#CCCCFF 1px solid; width:120px;}
		fieldset{ padding:10px; border:#CCCCFF 1px solid; font-size:13px; margin-top:15px;}		
		legend{ margin-left:5px; font-size:14px;}
		textarea{ border:#CCCCFF 1px solid;}
		input[type='button']{ width:80px; height:30px; border: #CCCCCC 1px solid; cursor:pointer;}
		.maginTopDiv{ margin-top:5px;}
	</style>
	<script type="text/javascript">
	function checks(){
		var validate="<%=validate%>";
		var state="<%=state%>";
		var  role="<%=role%>";
		
		if("1"==state && role=="role_1"){
			$("#hideDate1").hide();
		}

		if("3"==state && role=="role_2"){
			$("#hideDate2").hide();
		}
		

		if("5"==state && role=="role_3"){
			$("#hideDate3").hide();
		}
		
		if("0"==validate){
				if("2"==(state)){
				document.getElementById("hwyyj2").checked="checked";
				}else if("3"==(state)){
				document.getElementById("hwyyj2").checked="checked";
				}else if("4"==(state)){
				document.getElementById("hwyyj2").checked="checked";
				document.getElementById("hwyyj4").checked="checked";
				}else if("5"==(state)){
				document.getElementById("hwyyj2").checked="checked";
				document.getElementById("hwyyj4").checked="checked";
				}else if("6"==(state)){
				document.getElementById("hwyyj2").checked="checked";
				document.getElementById("hwyyj4").checked="checked";
				}	
		}
				
		if("1"==(validate)){
				if("2"==(state)){
				document.getElementById("hwyyj1").checked="checked";
				}else if("3"==(state)){
				document.getElementById("hwyyj2").checked="checked";
				}else if("4"==(state)){
				document.getElementById("hwyyj2").checked="checked";
				document.getElementById("hwyyj3").checked="checked";
				}else if("5"==(state)){
				document.getElementById("hwyyj2").checked="checked";
				document.getElementById("hwyyj4").checked="checked";
				}else if("6"==(state)){
				document.getElementById("hwyyj2").checked="checked";
				document.getElementById("hwyyj4").checked="checked";
				}
		}
		
		if("role_0"==(role)){
				document.getElementById("hwyyj1").disabled=true;
				document.getElementById("hwyyj2").disabled=true;
				document.getElementById("hwyyj3").disabled=true;
				document.getElementById("hwyyj4").disabled=true;
				document.getElementById("agentfeedback").disabled=true;
				document.getElementById("seatmonitorfeedback").disabled=true;
				document.getElementById("qcfeedback").disabled=true;
				}
				
		if("role_1"==(role)){
				if("0"==(validate)){
				if("3"==(state)){
				document.getElementById("hwyyj1").disabled=true;
				document.getElementById("hwyyj2").disabled=true;
				document.getElementById("agentfeedback").disabled=true;
				document.getElementById("d2").style.display="none";
				document.getElementById("d3").style.display="none";
				}else if("5"==(state)){
				document.getElementById("hwyyj1").disabled=true;
				document.getElementById("hwyyj2").disabled=true;
				document.getElementById("hwyyj3").disabled=true;
				document.getElementById("hwyyj4").disabled=true;
				document.getElementById("agentfeedback").disabled=true;
				document.getElementById("seatmonitorfeedback").disabled=true;
				document.getElementById("d3").style.display="none";
				}else if("6"==(state)){
				document.getElementById("hwyyj1").disabled=true;
					document.getElementById("hwyyj2").disabled=true;
					document.getElementById("hwyyj3").disabled=true;
					document.getElementById("hwyyj4").disabled=true;
					document.getElementById("agentfeedback").disabled=true;
					document.getElementById("seatmonitorfeedback").disabled=true;
					document.getElementById("qcfeedback").disabled=true;
				}
				}
				
				if("1"==(validate)){
				if("2"==(state)){
					document.getElementById("hwyyj1").disabled=true;
					document.getElementById("hwyyj2").disabled=true;
					document.getElementById("agentfeedback").disabled=true;
					document.getElementById("d2").style.display="none";
					document.getElementById("d3").style.display="none";		
				}else if("4"==(state)){
					document.getElementById("hwyyj1").disabled=true;
					document.getElementById("hwyyj2").disabled=true;
					document.getElementById("hwyyj3").disabled=true;
					document.getElementById("hwyyj4").disabled=true;
					document.getElementById("agentfeedback").disabled=true;
					document.getElementById("seatmonitorfeedback").disabled=true;
					document.getElementById("d3").style.display="none";
				}else if("6"==(state)){
					document.getElementById("hwyyj1").disabled=true;
					document.getElementById("hwyyj2").disabled=true;
					document.getElementById("hwyyj3").disabled=true;
					document.getElementById("hwyyj4").disabled=true;
					document.getElementById("agentfeedback").disabled=true;
					document.getElementById("seatmonitorfeedback").disabled=true;
					document.getElementById("qcfeedback").disabled=true;
				}
				}
					
				if(validate=="null"||""==(validate)){
				if("1"==(state)){
				document.getElementById("tj").style.display="inline-block";
				document.getElementById("d2").style.display="none";
				document.getElementById("d3").style.display="none";
				}
				}
				}
				
				
			if("role_2"==(role)){
			if("0"==(validate)){
				if("3"==(state)){
				document.getElementById("hwyyj1").disabled=true;
				document.getElementById("hwyyj2").disabled=true;
				document.getElementById("agentfeedback").disabled=true;
				document.getElementById("tj").style.display="inline-block";
				document.getElementById("d3").style.display="none";
				}else if("5"==(state)){
				document.getElementById("hwyyj1").disabled=true;
				document.getElementById("hwyyj2").disabled=true;
				document.getElementById("hwyyj3").disabled=true;
				document.getElementById("hwyyj4").disabled=true;
				document.getElementById("agentfeedback").disabled=true;
				document.getElementById("seatmonitorfeedback").disabled=true;
				document.getElementById("d3").style.display="none";
				}
				}
				
				if("1"==(validate)){
				if("2"==(state)){
					document.getElementById("hwyyj1").disabled=true;
					document.getElementById("hwyyj2").disabled=true;
					document.getElementById("agentfeedback").disabled=true;
					document.getElementById("d2").style.display="none";
					document.getElementById("d3").style.display="none";		
				}else if("4"==(state)){
					document.getElementById("hwyyj1").disabled=true;
					document.getElementById("hwyyj2").disabled=true;
					document.getElementById("hwyyj3").disabled=true;
					document.getElementById("hwyyj4").disabled=true;
					document.getElementById("agentfeedback").disabled=true;
					document.getElementById("seatmonitorfeedback").disabled=true;
					document.getElementById("d3").style.display="none";
				}else if("6"==(state)){
					document.getElementById("hwyyj1").disabled=true;
					document.getElementById("hwyyj2").disabled=true;
					document.getElementById("hwyyj3").disabled=true;
					document.getElementById("hwyyj4").disabled=true;
					document.getElementById("agentfeedback").disabled=true;
					document.getElementById("seatmonitorfeedback").disabled=true;
					document.getElementById("qcfeedback").disabled=true;
				}
				}
				if(validate=="null"||""==(validate)){
				if("1"==(state)){
				document.getElementById("hwyyj1").disabled=true;
				document.getElementById("hwyyj2").disabled=true;
				document.getElementById("agentfeedback").disabled=true;
				document.getElementById("d2").style.display="none";
				document.getElementById("d3").style.display="none";
				}
				}
				
				}
				
		if("role_3"==(role)){
			if("0"==(validate)){
				if("3"==(state)){
				document.getElementById("hwyyj1").disabled=true;
				document.getElementById("hwyyj2").disabled=true;
				document.getElementById("agentfeedback").disabled=true;
				document.getElementById("d2").style.display="none";
				document.getElementById("d3").style.display="none";
				}else if("5"==(state)){
				document.getElementById("hwyyj1").disabled=true;
				document.getElementById("hwyyj2").disabled=true;
				document.getElementById("hwyyj3").disabled=true;
				document.getElementById("hwyyj4").disabled=true;
				document.getElementById("agentfeedback").disabled=true;
				document.getElementById("seatmonitorfeedback").disabled=true;
				document.getElementById("tj").style.display="inline-block";
				}
			 }
			if("1"==(validate)){
				if("2"==(state)){
					document.getElementById("hwyyj1").disabled=true;
					document.getElementById("hwyyj2").disabled=true;
					document.getElementById("agentfeedback").disabled=true;
					document.getElementById("d2").style.display="none";
					document.getElementById("d3").style.display="none";		
				}else if("4"==(state)){
					document.getElementById("hwyyj1").disabled=true;
					document.getElementById("hwyyj2").disabled=true;
					document.getElementById("hwyyj3").disabled=true;
					document.getElementById("hwyyj4").disabled=true;
					document.getElementById("agentfeedback").disabled=true;
					document.getElementById("seatmonitorfeedback").disabled=true;
					document.getElementById("d3").style.display="none";
				}else if("6"==(state)){
					document.getElementById("hwyyj1").disabled=true;
					document.getElementById("hwyyj2").disabled=true;
					document.getElementById("hwyyj3").disabled=true;
					document.getElementById("hwyyj4").disabled=true;
					document.getElementById("agentfeedback").disabled=true;
					document.getElementById("seatmonitorfeedback").disabled=true;
					document.getElementById("qcfeedback").disabled=true;
				}
			}	
			if(validate=="null"||""==(validate)){
				if("1"==(state)){
				document.getElementById("hwyyj1").disabled=true;
				document.getElementById("hwyyj2").disabled=true;
				document.getElementById("agentfeedback").disabled=true;
				document.getElementById("d2").style.display="none";
				document.getElementById("d3").style.display="none";
				}
				}	
			}
			if("role_4"==(role)){
			if("0"==(validate)){
			if("1"==(state)){
				document.getElementById("hwyyj1").disabled=true;
				document.getElementById("hwyyj2").disabled=true;
				document.getElementById("agentfeedback").disabled=true;
				document.getElementById("d2").style.display="none";
				document.getElementById("d3").style.display="none";
			}else if("3"==(state)){
				document.getElementById("hwyyj1").disabled=true;
				document.getElementById("hwyyj2").disabled=true;
				document.getElementById("hwyyj3").disabled=true;
				document.getElementById("hwyyj4").disabled=true;
				document.getElementById("agentfeedback").disabled=true;
				document.getElementById("seatmonitorfeedback").disabled=true;
				document.getElementById("d3").style.display="none";
				}else if("5"==(state)){
				document.getElementById("hwyyj1").disabled=true;
				document.getElementById("hwyyj2").disabled=true;
				document.getElementById("hwyyj3").disabled=true;
				document.getElementById("hwyyj4").disabled=true;
				document.getElementById("agentfeedback").disabled=true;
				document.getElementById("seatmonitorfeedback").disabled=true;
				document.getElementById("qcfeedback").disabled=true;
				}
			 }
			if("1"==(validate)){
				if("2"==(state)){
					document.getElementById("hwyyj1").disabled=true;
					document.getElementById("hwyyj2").disabled=true;
					document.getElementById("agentfeedback").disabled=true;
					document.getElementById("d2").style.display="none";
					document.getElementById("d3").style.display="none";		
				}else if("4"==(state)){
					document.getElementById("hwyyj1").disabled=true;
					document.getElementById("hwyyj2").disabled=true;
					document.getElementById("hwyyj3").disabled=true;
					document.getElementById("hwyyj4").disabled=true;
					document.getElementById("agentfeedback").disabled=true;
					document.getElementById("seatmonitorfeedback").disabled=true;
					document.getElementById("d3").style.display="none";
				}else if("6"==(state)){
					document.getElementById("hwyyj1").disabled=true;
					document.getElementById("hwyyj2").disabled=true;
					document.getElementById("hwyyj3").disabled=true;
					document.getElementById("hwyyj4").disabled=true;
					document.getElementById("agentfeedback").disabled=true;
					document.getElementById("seatmonitorfeedback").disabled=true;
					document.getElementById("qcfeedback").disabled=true;
				}
			}	
			if(validate=="null"||""==(validate)){
				if("1"==(state)){
				document.getElementById("hwyyj1").disabled=true;
				document.getElementById("hwyyj2").disabled=true;
				document.getElementById("agentfeedback").disabled=true;
				document.getElementById("d2").style.display="none";
				document.getElementById("d3").style.display="none";
				}
				}	
			}
		
		
		}	
 		function subagent(){
 		var validate="<%=validate%>";
		var state="<%=state%>";
		var  role="<%=role%>";
 		var agentfeedback=$("#agentfeedback").val();
 		var seatmonitorfeedback=$("#seatmonitorfeedback").val();
 		var qcfeedback=$("#qcfeedback").val();
 		var agentworkpaperid=$("#agentworkpaperid").val();
 		var rad=$('input:radio[name="hwyyj"]:checked').val();
 		var rad2=$('input:radio[name="hwyyj2"]:checked').val();
 		//var rad3=$('input:radio[name="hwyyj3"]:checked').val();
 		var blag=true;
 		
 		if("1"==(state)){
 						if(rad==null){
 						$.ligerDialog.warn('请选择同意不同意');
 						blag=false;
 						}
 						if((agentfeedback==null||""==(agentfeedback))&&rad=='0'){
 						$.ligerDialog.warn('请填写处理意见');
 						blag=false;
 						}
 					validate=rad;
 				}
 		if("3"==(state)){
 						if(rad2==null){
 						$.ligerDialog.warn('请选择同意不同意');
 						blag=false;
 						}
 						if((seatmonitorfeedback==null||""==(seatmonitorfeedback))&&rad2=='0'){
 						$.ligerDialog.warn('请填写处理意见');
 						blag=false;
 						}
 					validate=rad2;
 				}
 		/* if("5"==(state)){
 						if(rad3==null){
 						$.ligerDialog.warn('请选择同意不同意');
 						blag=false;
 						}
 						if(qcfeedback==null||""==(qcfeedback)){
 						$.ligerDialog.warn('请填写处理意见');
 						blag=false;
 						}
 					validate=rad3;
 				} */
 		if(blag){	
 		$.ajax({
				 url: "<%=path%>/controller/agent/updAgent.do",
			     type: "post",
			     data:{	state:state,
			     	    validate:validate,
			     		agentfeedback:agentfeedback,
			     		seatmonitorfeedback:seatmonitorfeedback,
			     		agentworkpaperid:agentworkpaperid,
			     		qcfeedback:qcfeedback
			     				},
			     dataType: "json",
			    	success: function(result){ 
			    		if(result.blag){
			            $.ligerDialog.success('处理成功','提示',function(yes){ 
			               //location.reload(true);
			              // history.go(0);
			              window.close();
			              window.opener.location.reload();  
			            });
			            }else{
			            $.ligerDialog.success('处理失败');
			            }
			            },
			            error: function(result, e){ 
			            $.ligerDialog.success('提交失败');
			            }
		});	
 		
 		}				
 		
 			}
 			
 			 function input_shuziyinwen(obj){
				obj.value=obj.value.replace(/[\W]/g,'');
			}
        
</script>
</head>

<body onload="checks()" style="overflow:auto" >
	<div class="head_h3" style="position: relative;"><h3>质量检查管理平台</h3></div>
	<div class="mainDiv">
	<input type="hidden" value="${agent.agentworkpaperid}" id="agentworkpaperid" />
		<h2 align="center" style=" height:20px; line-height:20px; border:#0000CC 0px solid; margin-top:-10px;">质检反馈单</h2>
		反馈单编号：<span>${agent.agentworkpaperid}</span>&nbsp;&nbsp;&nbsp;&nbsp;反馈单时间：<span>${agent.qcworkpapertime}</span>&nbsp;&nbsp;&nbsp;&nbsp;反馈单状态：<span><c:if test="${agent.dealstate=='1'}">待受理员处理</c:if>
		<c:if test="${agent.dealstate=='2'}">受理员已处理</c:if>
		<c:if test="${agent.dealstate=='3'}">待受理员班长处理</c:if>
		<c:if test="${agent.dealstate=='4'}">受理员班长已处理</c:if>
		<c:if test="${agent.dealstate=='5'}">待质检员处理</c:if>
		<c:if test="${agent.dealstate=='6'}">质检员已处理</c:if>
		</span>
		<fieldset>
			<legend>质量检查信息</legend>
				<table cellpadding="5" cellpadding="5" width="100%" border="0">
					<tr>
						<td>合格程度：</td><td> <input type="text" readonly="readonly" value="${qcdetail.qualityLevel}" /></td>
						<td>处理结果：</td><td><input type="text" readonly="readonly" value="${qcdetail.dealRes}"/></td>
						<td>处理情况：</td><td><input type="text" readonly="readonly" value="${code2.name}" /></td>
						<td>是否TL9000：</td><td>
						<c:if test="${qcdetail.isTl9000=='1'}"> <input type="text" readonly="readonly" value="是" /></c:if>
						<c:if test="${qcdetail.isTl9000=='0'||qcdetail.isTl9000==null||qcdetail.isTl9000==''}"> <input type="text" readonly="readonly" value="否" /></c:if></td>
					</tr>
					<tr>
						<td>满意度：</td><td><c:if test="${qcdetail.satisfied=='1'}"> <input type="text" readonly="readonly" value="有" /></c:if>
						<c:if test="${qcdetail.satisfied=='0'||qcdetail.satisfied==null||qcdetail.satisfied==''}"> <input type="text" readonly="readonly" value="无" /></c:if></td>
						
						<td>是否投诉：</td><td><c:if test="${qcdetail.isComplain=='1'}"> <input type="text" readonly="readonly" value="是" /></c:if>
						<c:if test="${qcdetail.isComplain=='0'||qcdetail.isComplain==null||qcdetail.isComplain==''}"> <input type="text" readonly="readonly" value="否" /></c:if></td>
						<td>业务类型：</td>
					<td>
					<input type="text" readonly="readonly" value="${qcdetail.businessType}"/>
					</td>
						<td>设备编号：</td>
					<td>
					<input type="text" readonly="readonly" value="${qcdetail.deviceId}"/>
					</td>
					</tr>
					<tr>
					
					<td>检查内容：</td>
					<td>
					<input type="text" readonly="readonly" value="${code.name}"/>
					</td>
					</tr>	
					<tr>
					   <td>备注：</td>
					   <td colspan="7">
						   <textarea readonly="readonly" style="width: 91.5%">${qcdetail.remark}</textarea>
					   </td>
					</tr>				
				</table>
	  	</fieldset>
	  	<div id="d1">
		<fieldset>
			<legend>受理员</legend>
				<div class="maginTopDiv">
					<label>是否同意：</label><input name="hwyyj" id="hwyyj1" type="radio" value="1" /><label for="hwyyj1">同意</label><input name="hwyyj" id="hwyyj2" type="radio" value="0" /><label for="hwyyj2">不同意</label>	
				</div>
				<div>
					<label style="float:left; margin-top:10px;">处理意见：</label><textarea rows="4" id="agentfeedback" style=" overflow:auto; width:90%; margin-top:10px;">${agent.agentfeedback}</textarea>
				</div>
				<div style="margin-top:10px; padding-right:40px;" id="hideDate1">
					<div style="width:auto; float:right;">
						<label>处理人：<input type="text" name="qc_user_id" disabled="disabled" id="qc_user_id" class="inputText" value="${agent.agentusername}" /></label>&nbsp;&nbsp;
						<label>处理日期：<input type="text" class="inputText" disabled="disabled" id="agentfeedbacktime" value="${agent.agentfeedbacktime}" /></label>
					</div>
				</div>
	  	</fieldset>
	  	</div>
	  	<div id="d2">
		<fieldset>
			<legend>受理员班长</legend>
				<div class="maginTopDiv">
					<label>是否同意：</label><input name="hwyyj2" id="hwyyj3" type="radio" value="1" /><label for="hwyyj1">同意</label><input name="hwyyj2" id="hwyyj4" type="radio" value="0" /><label for="hwyyj4">不同意</label>	
				</div>
				<div>
					<label style="float:left; margin-top:10px;">处理意见：</label><textarea rows="4" id="seatmonitorfeedback" style=" overflow:auto; width:90%; margin-top:10px;">${agent.seatmonitorfeedback}</textarea>
				</div>
				<div style="margin-top:10px; padding-right:40px;"  id="hideDate2">
					<div style="width:auto; float:right;">
						<label>处理人：<input type="text" class="inputText" disabled="disabled"  id="seatmonitoruserid" value="${agent.seatmoniusername}" /></label>&nbsp;&nbsp;
						<label>处理日期：<input type="text" class="inputText" disabled="disabled"  id="seatmonitortime" value="${agent.seatmonitortime}" /></label>
					</div>
				</div>
	  	</fieldset>
	  	</div>
	  	<div id="d3">
		<fieldset>
			<legend>质检员</legend>
				<!-- <div class="maginTopDiv">
					<label>是否同意：</label><input name="hwyyj3" id="hwyyj5" type="radio" value="1" /><label for="hwyyj1">同意</label><input name="hwyyj3" id="hwyyj6" type="radio"value="0"  /><label for="hwyyj2">不同意</label>	
				</div> -->
				<div>
					<label style="float:left; margin-top:10px;">处理意见：</label><textarea rows="4" id="qcfeedback" style=" overflow:auto; width:90%; margin-top:10px;">${agent.qcfeedback}</textarea>
				</div>
				<div style="margin-top:10px; padding-right:40px;"  id="hideDate3">
					<div style="width:auto; float:right;">
						<label>处理人：<input type="text" id="agentuserid" disabled="disabled"  class="inputText" value="${agent.qcusername}" /></label>&nbsp;&nbsp;
						<label>处理日期：<input type="text" id="agenttime" disabled="disabled"  class="inputText"  value="${agent.agenttime}" /></label>
					</div>
				</div>
	  	</fieldset>
	  	</div>
		<div align="center" style="margin-top:15px;">
			<input type="button" style="display: none;" id="tj" onclick="subagent()"  value="提交" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="window.close();"  value="返回" />			
		</div>
	</div>
</body>
</html>

