<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%> 
<%@ taglib uri="/WEB-INF/shiro.tld" prefix="shiro"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String falg=request.getAttribute("display")!=null?request.getAttribute("display").toString():"";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>客调项目管理系统</title>
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/css/public.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
	<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="<%=path%>/js/utils.js" type="text/javascript"></script>
	<style type="text/css">
		body{ font-size:13px;overflow: auto !important;}
		html{display: block;}
		.head_h3{ width:100%; height:36px; line-height:36px; background:url(<%=path%>/img/login/toppic.jpg); margin-top:-3px;}
		.head_h3 h3{ color:#ffffff; margin-left:70px; font-size:16px;}
		
		
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(<%=path%>/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:1300px; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		
		td.td_lable{ text-align:right;}
		td.td_value{ text-align:left;}
		div.button_div{ padding:10px;}
		input[type='button']{ border:#d3d3d3 1px solid; width:80px; height:25px; cursor:pointer;}
	</style>
	<script type="text/javascript"> 
			
	$(document).ready(function(){
		if(<%=falg%> == '0'){
			$('#showGongDanDiv').slideToggle();
		}
		
		$("#businessTypeid").change(function(){
			var businessType = $("#businessTypeid").val(); 
			var isTl9000 = $("#isTl9000id").val();
			$.ajax({
				url:'<%=path%>/controller/syscode/getcode.do',
				data:{'businessType':businessType,'isTl9000':isTl9000},
				type:'post',
				success:function(data){
					var res=eval("("+data+")");
					$("#dealDisid").empty();
					 $("#dealDisid").append("<option value=''>---请选择---</option>");
					 $.each(res.codeList,function(index,value){
	                        $("#dealDisid").append("<option value='"+value.valuees+"'>"+value.name+"</option>");
	                 });
	                   $("#checkcontent").empty();
					 $("#checkcontent").append("<option value=''>---请选择---</option>");
					 $.each(res.checkcodelist,function(index,value){
	                        $("#checkcontent").append("<option value='"+value.valuees+"'>"+value.name+"</option>");
	                 })
				},
				error:function(){
					$.ligerDialog.error('加载数据异常，请稍后再试');
				}
			});
			
		});
		
		$("#isTl9000id").change(function(){
			var businessType = $("#businessTypeid").val(); 
			var isTl9000 = $("#isTl9000id").val();
			
			$.ajax({
				url:'<%=path%>/controller/syscode/getcode.do',
				data:{'businessType':businessType,'isTl9000':isTl9000},
				type:'post',
				success:function(data){
					var res=eval("("+data+")");
					$("#dealDisid").empty();
					 $("#dealDisid").append("<option value=''>---请选择---</option>");
					 $.each(res.codeList,function(index,value){
	                        $("#dealDisid").append("<option value='"+value.valuees+"'>"+value.name+"</option>");
	                 });
				},
				error:function(){
					$.ligerDialog.error('加载数据异常，请稍后再试');
				}
			});
			
		});
	});
	//加载合格情况
	function loadpassdes(sel){
		var dealdes=$("#dealDisid").find("option:selected").text();
		if(dealdes=='合格' || dealdes=='正常'){
			$("#qualityLevelid").val('合格');
		}else if(dealdes=='---请选择---'){
			$("#qualityLevelid").val('');
		}else{
			$("#qualityLevelid").val('不合格');
		}
	}
	
	
	function qcsub(){
	/* 	var display=$("#displayid").val();
		if(display!='0'){
			var recordId=$("#recordId").val();
			if(recordId=='' || recordId==null){
				$.ligerDialog.warn('请关联录音！');
				return;
			}
		} */
		var dealDisid=$("#dealDisid").val();
		if(dealDisid=='' ){
			$.ligerDialog.warn('请选择处理情况');
			return;
		}
		
		var deviceIdid=$("#deviceIdid").val();
		if(deviceIdid=='' ){
			$.ligerDialog.warn('设备编号不能为空');
			return;
		}else if(/[@#\$%\^&\*]+/g.test(deviceIdid)){
				$.ligerDialog.warn("设备编号存在非法字符");
				return false;
		}else if(deviceIdid.length>20){
		      $.ligerDialog.warn("设备编号长度不超过20");
				return false;
		}
		var checkcontent=$("#checkcontent").val();
		if(checkcontent=='' ){
			$.ligerDialog.warn('请选择检查内容');
			return;
		}
		var dealResid=$("#dealResid").val();
		if(dealResid=='' ){
			$.ligerDialog.warn('处理结果不能为空');
			return;
		}
		var isComplainid=$("#isComplainid").val();
		if(isComplainid=='' ){
			$.ligerDialog.warn('请选择是否投诉');
			return;
		}
		var satisfied = $('input[name="satisfied"]').filter(':checked').val();
		
		if(satisfied=='' ){
			$.ligerDialog.warn('请选择满意度');
			return;
		}
		var agentTransferid=$("#agentTransferid").val();
		if(agentTransferid=='' ){
			$.ligerDialog.warn('请选择人工转接');
			return;
		}
		
		var qualityLevelid=$("#qualityLevelid").val();
		var qcidid=$("#qcidid").val();
		if(qcidid!=''&&qualityLevelid=='不合格' ){
			$.ligerDialog.warn('第二次评分只能是合格');
			return;
		}
		
			$.ligerDialog.confirm('确认提交评分吗？','提示', function (yes) {
				if(yes){	
					 $.ajax({
						url:'<%=path%>/controller/qc/workorderscore.do',
						type:'post',
						data:$('#qcfromid').serialize(),
						error: function(request) {
							$.ligerDialog.error('评分失败，请稍候再试!');
						},
						success: function(data) {
							var res=eval("("+data+")");
							if(res.status=='1'){
								$.ligerDialog.success('评分成功！','提示',function(yes){
								var istask=$("#istask").val();
	   							var channelType=$("#channelType").val();
	   							if(channelType!=null){
	     							window.opener.location.reload();
	  							 }
								 window.close();
								});	
							}else if(res.status=='2'){
								$.ligerDialog.warn('评分冲突！');
							}else if(res.status=='0'&& display!='0'){
								$.ligerDialog.warn('请关联录音！');
							}else{
								$.ligerDialog.error('评分失败，请稍候再试!');
							}
						}
					}); 
		}
	});
		
	}
	
	function closewin(){
	   //判断如果是任务录音评分  在点击关闭按钮时，刷新任务录音列表
	   var channelType=$("#channelType").val();
	   if(channelType=="taskrecord"){
	     window.opener.location.reload();
	   }
		 window.close();
		
	}
	
	/*********************
	 * 二级质检
	 *********************/
	function secqc(){
		//质检id
		var qc_id =$("#qcidid").val();
		$.ligerDialog.open({
			height:300,
			width: 320,
			title : '二级质检',
			url:'<%=path%>/page/secqc.jsp?qc_id='+encodeURI(encodeURI(qc_id)), 
			showMax: false,
			showMin: false,
			isResize: true,
			slide: false
		});
	}
    </script> 
</head>
<body>
	<div class="head_h3" style="position: relative;"><h3>质量检查管理平台</h3></div>
	<div align="center" style=" margin:auto; margin-top:10px;">
		<object id="player" height="64" width="1300px" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6" type="application/x-oleobject">
			<!--  <param name="url" value="<%=path %>/controller/wk112/listenRecordoBy112.do?recordReference=${rd.recordFilename}"> -->
			<param name="url" value="<%=path%>/controller/record/downRecord.do?reservedThree=${rd.reservedThree}"/>
			<PARAM NAME="rate" VALUE="1">
			<PARAM NAME="balance" VALUE="0">
			<PARAM NAME="currentPosition" VALUE="0">
			<PARAM name="defaultFrame" VALUE="">
			<!-- value="0"时为循环播放,value="1"为播放1次. -->
			<PARAM NAME="playCount" VALUE="1">
			<!-- value="0"时为不自动播放,“1”为自动播放；  -->
			<PARAM NAME="autoStart" VALUE="0">
			<PARAM NAME="currentMarker" VALUE="0">
			<PARAM NAME="invokeURLs" VALUE="-1">
			<PARAM NAME="baseURL" VALUE="">
			<PARAM NAME="volume" VALUE="100">
			<PARAM NAME="mute" VALUE="0">
			<PARAM NAME="uiMode" VALUE="full">
			<PARAM NAME="stretchToFit" VALUE="0">
			<PARAM NAME="windowlessVideo" VALUE="0">
			<PARAM NAME="enabled" VALUE="-1">
			<PARAM NAME="enableContextMenu" VALUE="-1">
			<PARAM NAME="fullScreen" VALUE="0">
			<PARAM NAME="SAMIStyle" VALUE="">
			<PARAM NAME="SAMILang" VALUE="">
			<PARAM NAME="SAMIFilename" VALUE="">
			<PARAM NAME="captioningID" VALUE="">
		</object>
		<div class="title_div"><label style="margin-left:15px;">工单评分</label></div>
		<form id="qcfromid" method="post">
		<input type="hidden" value="${stoptimeq}" id="stoptimeq"/>
		<input type="hidden" value="${stoptimeh}" id="stoptimeh"/>
		<input type="hidden" value="${agentworkid}" id="agentworkid" name="agentworkid"/>
		<!-- 判断进入该页面的渠道  taskrecord 为任务录音评分 -->
		<input type="hidden"  value="<%=request.getParameter("channelType")%>" id="channelType" name="channelType"/>
		<table cellpadding="3" cellspacing="3" width="1300px" >
			<tr>
				<td class="td_lable" width="80" >监听时间：</td><td class="td_value" width="120"><input class="Wdate date_text" type="text"  disabled="disabled" name="listentime" value="${listentime }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"  /></td>
				<td class="td_lable" width="80" >受理时间：</td><td class="td_value" width="120"><input class="Wdate date_text" type="text" disabled="disabled" id="SLstartTime"  name="SLstartTime" value="${complaintTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" /></td>
				<td class="td_lable" width="80" >质检员：</td><td class="td_value" width="120"><input type="text" class="input_text" name="workname" disabled="disabled" value="${workname}"/></td>
				<td class="td_lable" width="80" >受理员：</td><td class="td_value" width="120"><input type="text" class="input_text" name="agentName" disabled="disabled" id="agentName"  value="${agentName}"/></td>
				<td class="td_lable" width="80" >设备编号：</td>
				<td class="td_value" width="120">
				<c:if test="${display=='0'}">
					<input disabled="disabled" type="text" class="input_text" onblur="noSpecial(this);"  onkeyup="strlenLimit(this,20);noSpecial(this);"  name="deviceId" id="deviceIdid" value="${qcDetail.deviceId}"/><span style="color: red">*</span>
				</c:if>	
				<c:if test="${display!='0'}">
				<input  type="text" class="input_text" onblur="noSpecial(this);"  onkeyup="strlenLimit(this,20);noSpecial(this);"  name="deviceId" id="deviceIdid" value="${errorNo}"/><span style="color: red">*</span>
				</c:if>
					<input value="${display}" id="displayid" type="hidden"/>
				</td>
			</tr>
			<tr>
				<td class="td_lable">业务类型：</td><td class="td_value" >
				<select class="select_text" name="businessType" id="businessTypeid" onchange="loadpassdes(this);" <c:if test="${display=='0'}">disabled="disabled"</c:if> >
							<option value="">---请选择---</option>		
							<option value="10"  <c:if test="${qcDetail.businessType=='10' or gl=='0'}">selected="selected"</c:if> >112</option>
							<option value="32" <c:if test="${qcDetail.businessType=='32' }"> selected="selected" </c:if> >c网</option>
							<option value="76" <c:if test="${qcDetail.businessType=='76' }"> selected="selected" </c:if> >管控</option>
							<option value="178" <c:if test="${qcDetail.businessType=='178' }">selected="selected"</c:if> >客调112</option>
							<option value="200" <c:if test="${qcDetail.businessType=='200' }">selected="selected"</c:if> >客调c网</option>
							<option value="54"  <c:if test="${qcDetail.businessType=='54' }">selected="selected"</c:if> >其他</option>
					</select><span style="color: red">*</span>	
				</td>
				<td class="td_lable">检查部门：</td><td class="td_value">
				<select class="select_text" name="isTl9000" id="isTl9000id"  <c:if test="${display=='0'}">disabled="disabled"</c:if>>
							<option value="">---请选择---</option>		
							<option value="1"  <c:if test="${qcDetail.isTl9000=='1' or gl=='0'}">selected="selected"</c:if> >tl9000</option>
							<option value="2" <c:if test="${qcDetail.isTl9000=='2' }"> selected="selected" </c:if> >业务室</option>
							<option value="3" <c:if test="${qcDetail.isTl9000=='3' }"> selected="selected" </c:if> >受理中心</option>
							<option value="4" <c:if test="${qcDetail.isTl9000=='4' }">selected="selected"</c:if> >质检小组</option>
							<option value="5" <c:if test="${qcDetail.isTl9000=='5' }">selected="selected"</c:if> >全部</option>
					</select><span style="color: red">*</span>	
					<%-- <input <c:if test="${display=='0'}">disabled="disabled"</c:if> type="radio" class="radiotl9999" name="isTl9000" value="1" <c:if test="${qcDetail.isTl9000=='1'or gl=='0' }"> checked="checked" </c:if>/>是
					<input <c:if test="${display=='0'}">disabled="disabled"</c:if> type="radio" class="radiotl9999" name="isTl9000" value="0" <c:if test="${qcDetail.isTl9000=='0' }"> checked="checked" </c:if>/>否
				 --%>
				</td>
				<td class="td_lable">处理情况：</td>
				<td class="td_value">
					<select class="select_text" name="dealDis" id="dealDisid" onchange="loadpassdes(this);" <c:if test="${subflag=='0'}">disabled="disabled"</c:if>>
							<option value="">---请选择---</option>
						<c:forEach items="${codelist}" var="code">
							<option <c:if test="${code.valuees==qcDetail.dealDis}">selected="selected" </c:if> value="${code.valuees}" >${code.name}</option>
						</c:forEach>
					</select><span style="color: red">*</span>
				
				</td>
				
					<td class="td_lable">检查内容：</td>
				<td class="td_value">
					<select class="select_text" name="checkcontent" id="checkcontent"  <c:if test="${subflag=='0'}">disabled="disabled"</c:if>>
							<option value="">---请选择---</option>
						<c:forEach items="${checkcodelist}" var="code">
							<option <c:if test="${code.valuees==qcDetail.checkcontent}">selected="selected" </c:if> value="${code.valuees}" >${code.name}</option>
						</c:forEach>
					</select><span style="color: red">*</span>
				
				</td>
				<td class="td_lable">合格程度：</td>
				<td class="td_value">
					<input class="input_text" name="qualityLevel" readonly="readonly"  id="qualityLevelid" value="${qcDetail.qualityLevel}" <c:if test="${subflag=='0'}">disabled="disabled"</c:if>/><span style="color: red">*</span>
				</td>
			</tr>
			<tr>	
			<td class="td_lable">处理结果：</td>
					<td class="td_value">
					<select class="select_text" name="dealRes" id="dealResid"<c:if test="${display=='0'}">disabled="disabled"</c:if>>
					<option value="">---请选择---</option>
					<option value="结案" <c:if test="${qcDetail.dealRes=='结案' }">selected="selected"  </c:if>  >结案</option>
					<option value="派修"  <c:if test="${qcDetail.dealRes=='派修' }">selected="selected"  </c:if> >派修</option>
					<option value="回访" <c:if test="${qcDetail.dealRes=='回访' }">selected="selected"  </c:if>  >回访</option>
					<option value="其他"  <c:if test="${qcDetail.dealRes=='其他' }">selected="selected" </c:if> >其他</option>
				</select><span style="color: red">*</span>
						<%-- <input <c:if test="${display=='0'}">disabled="disabled"</c:if> class="input_text" id="dealResid"  onblur="noSpecial(this);"  onkeyup="strlenLimit(this,200);noSpecial(this);"  name="dealRes" value="${qcDetail.dealRes}" /><span style="color: red">*</span> --%>
					</td>
				<td class="td_lable">是否投诉：</td><td class="td_value">
				<select class="select_text" name="isComplain" <c:if test="${display=='0'}">disabled="disabled"</c:if> id="isComplainid">
					<option value="">---请选择---</option>
					<option value="1" <c:if test="${qcDetail.isComplain=='1' }">selected="selected"  </c:if>  >是</option>
					<option value="0" <c:if test="${qcDetail.isComplain==''||qcDetail.isComplain==null}">selected="selected"  </c:if> <c:if test="${qcDetail.isComplain=='0' }">selected="selected"  </c:if>>否</option>
				</select><span style="color: red">*</span>
				
				</td>
					<td class="td_lable">满意度：</td><td class="td_value">
					<input <c:if test="${display=='0'}">disabled="disabled"</c:if> type="radio" name="satisfied" value="1" <c:if test="${qcDetail.satisfied=='1' or gl=='0' }"> checked="checked" </c:if>/>有
					<input <c:if test="${display=='0'}">disabled="disabled"</c:if> type="radio" name="satisfied" value="0" <c:if test="${qcDetail.satisfied==''||qcDetail.satisfied==null}"> checked="checked" </c:if> <c:if test="${qcDetail.satisfied=='0' }"> checked="checked" </c:if>/>无
				</td>	
				<td class="td_lable">人工转接：</td><td class="td_value">
					<select class="select_text" name="agentTransfer" <c:if test="${display=='0'}">disabled="disabled"</c:if> id="agentTransferid">
						<option value="">---请选择---</option>
						<option value="1" <c:if test="${qcDetail.agentTransfer=='1' }">selected="selected"  </c:if>>需转接</option>
						<option value="0" <c:if test="${qcDetail.agentTransfer==''||qcDetail.agentTransfer==null}">selected="selected"  </c:if> <c:if test="${qcDetail.agentTransfer=='0' }">selected="selected"  </c:if>>无需转接</option>
					</select><span style="color: red">*</span></td>
			</tr>
			<tr>
			<td class="td_lable">备注：</td>
					<td class="td_value" colspan="3">
					<textarea id="remark" name="remark" rows="3" onkeyup="strlenLimit(this,500);" style=" overflow:auto; width:90%; margin-top:10px;" <c:if test="${display=='0'}">disabled="disabled"</c:if>>${qcDetail.remark}</textarea>
				 </td>
			</tr>
			<tr id="glgdid">
				<%-- <td colspan="10" style="font-size:13px; text-align:left;">
					关联录音信息 <a href="javascript:void(0);" onclick="$('#showGongDanDiv').slideToggle();">关联</a><br />
					<div id="showGongDanDiv" style="overflow:hidden; height:330px; margin-top:10px; border:#999999 1px solid;background-color:#FFFFFF;border-style:dashed; padding:10px;" align="center">
						<iframe width="100%" height="370" frameborder="0" scrolling="no" src="<%=basePath%>/page/112/guanliain_record.jsp"></iframe>
					   
					</div>
				</td> --%>
				<td colspan="10" style="font-size:13px; text-align:left;">
					关联录音信息 <a href="javascript:void(0);" onclick="$('#showGongDanDiv112').slideToggle();">关联录音</a><br />			
					<div id="showGongDanDiv112" style="overflow:hidden; height:375px; margin-top:10px; border:#999999 1px solid;border-style:dashed; padding:10px;" align="center">
						<iframe width="100%" height="370" frameborder="0" scrolling="no" src="<%=basePath%>/page/112/guanliain_record.jsp"></iframe>
					</div>
					
					
				</td>
			</tr>
			<tr>
				<td colspan="10">&nbsp;
					
				</td>
			</tr>
			<tr>
				<td class="td_lable">CTI流水号：</td><td class="td_value">
				<input type="text" class="input_text"  name="reservedThree" id="reservedThree"  value="${rd.reservedThree}" <c:if test="${display=='0'}">disabled="disabled"</c:if>/>
				 <%-- <input type="hidden" id="glflagid" name="glflag" value="${qcid}"/> --%>
				  <input type="hidden" name="qcid" value="${qcid}" id="qcidid"/> 
			    <input type="hidden" id="recordId"  value="${rd.recordId}" name="recordId"/>
				<input type="hidden" id="bigWorkid" name="bigWorkid" />
				<input type="hidden" id="smallWorkid" name="smallWorkid" />
				 <input type="hidden" value="${workorderId}" name="workorderId" id="workorderId"/>
				
				</td>
				<td class="td_lable">开始时间：</td><td class="td_value"><input type="text" class="input_text" name="startTime" id="startTime" value="${rd.startTime}" <c:if test="${display=='0'}">disabled="disabled"</c:if>/></td>
				<td class="td_lable">结束时间：</td><td class="td_value"><input type="text" class="input_text" name="stopTime"  id="stopTime" value="${rd.stopTime}" <c:if test="${display=='0'}">disabled="disabled"</c:if>/></td>
				<td class="td_lable">主叫号码：</td><td class="td_value"><input type="text" class="input_text" name="callerid"  id="callerid" value="${rd.callerid}" <c:if test="${display=='0'}">disabled="disabled"</c:if>/></td>
				<td class="td_lable">被叫号码：</td><td class="td_value"><input type="text" class="input_text" name="calledid"  id="calledid" value="${rd.calledid}" <c:if test="${display=='0'}">disabled="disabled"</c:if>/></td>
			</tr>
		</table>
		</form>
		<div class="button_div">
			<c:if test="${secflag=='0'}">
				<shiro:hasPermission name='112order:senc'>
				<input type="button" value="填写意见" onclick="secqc();" id="secid"/>&nbsp;&nbsp;
				</shiro:hasPermission>
			</c:if>
			
			<c:if test="${subflag!='0'}">
				<shiro:hasPermission name='112order:check'>
				<input type="button" value="提交" onclick="qcsub();" id="subid"/>&nbsp;&nbsp;
				</shiro:hasPermission>
			</c:if>
			<input type="button" value="返回" onclick="closewin();"/>
		</div>		
	</div>	
</body>
</html>
