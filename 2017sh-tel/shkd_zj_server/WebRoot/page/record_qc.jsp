<%@ page language="java" contentType="text/html;charset=UTF-8"%>  
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%> 
<%@ taglib uri="/WEB-INF/shiro.tld" prefix="shiro"%> 
<%
  String path = request.getContextPath();
  String falg=request.getAttribute("display")!=null?request.getAttribute("display").toString():"";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>客调项目管理系统</title>
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
	<link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script> 
	<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="<%=path%>/js/utils.js" type="text/javascript"></script>
	<style type="text/css">
		body{ font-size:13px;overflow: auto !important;}
		.head_h3{ width:100%; height:36px; line-height:36px; background:url(<%=path%>/img/login/toppic.jpg); margin-top:-3px;}
		.head_h3 h3{ color:#ffffff; margin-left:70px; font-size:16px;}
		
		
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(<%=path%>/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:1300px; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		td.td_lable{ text-align:right;}
		td.td_value{ text-align:left;}
		div.button_div{ padding:10px;}
		input[type='button']{ border:#d3d3d3 1px solid; width:80px; height:25px; cursor:pointer;}
		input[type='radio']{height: inherit !important;margin-right: 5px;}
	</style>
	<script type="text/javascript">
	
	$(document).ready(function(){	
	if(<%=falg%>+'' == '0'){
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
					$.ligerDialog.error('加载数据异常，请稍后再试！');
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
	                 })
				},
				error:function(){
					$.ligerDialog.error('加载数据异常，请稍后再试！');
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
		/* var display=$("#displayid").val();
		 if(display!='0'){
			var workorid=$("#workorid").val();
			if(workorid=='' || workorid==null){
				$.ligerDialog.warn('请关联工单！');
				return;
			}
		}  */
		var dealDisid=$("#dealDisid").val();
			if(dealDisid=='' ){
				$.ligerDialog.warn('请选择处理情况');
				return;
		}
		
		var deviceIdid=$("#deviceIdid").val();
		if(deviceIdid=='' ){
			$.ligerDialog.warn('设备编号不能为空');
			return;
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
			
		
		$.ligerDialog.confirm('确认提交评分吗？', function (yes){
			if(yes){
				 $.ajax({
						url:'<%=path%>/controller/qc/score.do',
						type:'post',
						data:$('#qcfromid').serialize(),
						error: function(request) {
							$.ligerDialog.error('评分失败，请稍候再试!');
						},
						success: function(data) {
							var res=eval("("+data+")");
							if(res.status=='2'){
								$.ligerDialog.warn('评分冲突');
							}else if(res.status=='1'){
							 $.ligerDialog.success('评分成功！','提示',function(yes){ 
			               		var channelType=$("#channelType").val();
	   							if(channelType=="taskrecord"){
	     						window.opener.location.reload();
	  							 }
								 window.close();
			            	});		
							}else if(res.status=='0'&& display!='0'){
								$.ligerDialog.warn('请关联工单！');
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
	  function input_shuziyinwen(obj){
				obj.value=obj.value.replace(/[\W]/g,'');
			}
    </script> 
</head>
<body>
	<div class="head_h3" style="position: relative;"><h3>质量检查管理平台</h3></div>
	<div align="center" style=" margin:auto; margin-top:10px;">
		<object id="player" height="64" width="1300px" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6" type="application/x-oleobject">
			<param name="url" value="<%=path%>/controller/record/downRecord.do?reservedThree=${reservedThree}"/>
			<PARAM NAME="rate" VALUE="1"></PARAM>
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
		<div class="title_div"><label style="margin-left:15px;">录音评分</label></div>
		<form id="qcfromid" method="post">
		<input type="hidden" value="${stoptimeq}" id="stoptimeq"/>
		<input type="hidden" value="${stoptimeh}" id="stoptimeh"/>
		<input type="hidden" value="${agentworkid}" id="agentworkid"/>
		<!-- 判断进入该页面的渠道  taskrecord 为任务录音评分 -->
		<input type="hidden"  value="<%=request.getParameter("channelType")%>" id="channelType" name="channelType"/>
		<table cellpadding="3" cellspacing="3" width="1300px" >
			<tr>
				<td class="td_lable" width="80" >监听时间：</td><td class="td_value" width="120"><input class="Wdate date_text" type="text"  disabled="disabled" name="listentime" value="${listentime }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"  /></td>
				<td class="td_lable" width="80" >受理时间：</td><td class="td_value" width="120"><input class="Wdate date_text" type="text" disabled="disabled" name="startTime" value="${startTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" /></td>
				<td class="td_lable" width="80" >质检员：
				</td><td class="td_value" width="120">
				<input type="text" class="input_text"   disabled="disabled" value="${workname}"/>
				<input type="hidden" name="workname"  value="${workname}"/>
				</td>
				<td class="td_lable" width="80" >受理员：</td><td class="td_value" width="120"><input type="text" class="input_text" name="agentName" disabled="disabled" value="${agentName}"/></td>
				<td class="td_lable" width="80" >设备编号：</td>
				<td class="td_value" width="120">
					<input <c:if test="${display=='0'}">disabled="disabled"</c:if> type="text" class="input_text" maxlength="50" onkeyup="input_shuziyinwen(this);" name="deviceId" id="deviceIdid" value="${qcDetail.deviceId}"/><span style="color: red">*</span>
					<input value="${display}" id="displayid" type="hidden"/>
				</td>
			
			</tr>
			<tr>
				<td class="td_lable">业务类型：</td>	
				<td class="td_value">
				<select class="select_text" name="businessType" id="businessTypeid"  <c:if test="${display=='0'}">disabled="disabled"</c:if>>
							<option value="">---请选择---</option>		
							<option value="10"  <c:if test="${qcDetail.businessType=='10' or gl=='0'}">selected="selected"</c:if> >112</option>
							<option value="32" <c:if test="${qcDetail.businessType=='32' }"> selected="selected" </c:if> >c网</option>
							<option value="76" <c:if test="${qcDetail.businessType=='76' }"> selected="selected" </c:if> >管控</option>
							<option value="178" <c:if test="${qcDetail.businessType=='178' }">selected="selected"</c:if> >客调112</option>
							<option value="200" <c:if test="${qcDetail.businessType=='200' }">selected="selected"</c:if> >客调c网</option>
							<option value="54"  <c:if test="${qcDetail.businessType=='54' }">selected="selected"</c:if> >其他</option>
					</select><span style="color: red">*</span>	
				
				<%-- <input <c:if test="${display=='0'}">disabled="disabled"</c:if> type="radio" class="radioItem" name="businessType" value="10" <c:if test="${qcDetail.businessType=='10' or gl=='0'}">checked="checked"</c:if> />112 
				<input <c:if test="${display=='0'}">disabled="disabled"</c:if> type="radio" class="radioItem" name="businessType" value="32" <c:if test="${qcDetail.businessType=='32' }"> checked="checked" </c:if>/>C网
				<input <c:if test="${display=='0'}">disabled="disabled"</c:if> type="radio" class="radioItem" name="businessType" value="76" <c:if test="${qcDetail.businessType=='76' }">checked="checked"</c:if>/>管控
				<input <c:if test="${display=='0'}">disabled="disabled"</c:if> type="radio" class="radioItem" name="businessType" value="54" <c:if test="${qcDetail.businessType=='54' }">checked="checked"</c:if>/>其他
				<input <c:if test="${display=='0'}">disabled="disabled"</c:if> type="radio" class="radioItem" name="businessType" value="178" <c:if test="${qcDetail.businessType=='178' }">checked="checked"</c:if>/>客调112
				<input <c:if test="${display=='0'}">disabled="disabled"</c:if> type="radio" class="radioItem" name="businessType" value="200" <c:if test="${qcDetail.businessType=='200' }">checked="checked"</c:if>/>客调c网
 --%>
				</td>
					<%-- <input <c:if test="${display=='0'}">disabled="disabled"</c:if> type="radio" class="radioItem" name="businessType" value="10" <c:if test="${qcDetail.businessType=='10' or gl=='0'}">checked="checked"</c:if> />112
					<input <c:if test="${display=='0'}">disabled="disabled"</c:if> type="radio" class="radioItem" name="businessType" value="54" <c:if test="${qcDetail.businessType=='54' }">checked="checked"</c:if>/>预处理
					<br/><input <c:if test="${display=='0'}">disabled="disabled"</c:if> type="radio" class="radioItem" name="businessType" value="32" <c:if test="${qcDetail.businessType=='32' }"> checked="checked" </c:if>/>C网
					<input <c:if test="${display=='0'}">disabled="disabled"</c:if> type="radio" class="radioItem" name="businessType" value="76" <c:if test="${qcDetail.businessType=='76' }">checked="checked"</c:if>/>管控
				
				</td> --%>
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
					<select class="select_text" name="checkcontent" id="checkcontent"  <c:if test="${display=='0'}">disabled="disabled"</c:if>>
							<option value="">---请选择---</option>
						<c:forEach items="${checkcodelist}" var="code">
							<option <c:if test="${code.valuees==qcDetail.checkcontent}">selected="selected" </c:if> value="${code.valuees}" >${code.name}</option>
						</c:forEach>
					</select><span style="color: red">*</span>
				</td>
				<td class="td_lable">合格程度：</td>
				<td class="td_value">
					<input type="text" class="input_text" name="qualityLevel" readonly="readonly" id="qualityLevelid" value="${qcDetail.qualityLevel}" <c:if test="${subflag=='0'}">disabled="disabled"</c:if>/><span style="color: red">*</span>
				</td>
			</tr>
			<tr>	
			<td class="td_lable">处理结果：</td>
					<td class="td_value">
					<select class="select_text" name="dealRes" id="dealResid" <c:if test="${display=='0'}">disabled="disabled"</c:if>>
					<option value="">---请选择---</option>
					<option value="结案" <c:if test="${qcDetail.dealRes=='结案' }">selected="selected"  </c:if>  >结案</option>
					<option value="派修"  <c:if test="${qcDetail.dealRes=='派修' }">selected="selected"  </c:if>>派修</option>
					<option value="回访" <c:if test="${qcDetail.dealRes=='回访' }">selected="selected"  </c:if>  >回访</option>
					<option value="其他"  <c:if test="${qcDetail.dealRes=='其他' }">selected="selected"  </c:if>>其他</option>
				</select><span style="color: red">*</span>
					
						<%-- <input <c:if test="${display=='0'}">disabled="disabled"</c:if> maxlength="50"  onblur="noSpecial(this);" onkeyup="noSpecial(this)" class="input_text" id="dealResid" name="dealRes" value="${qcDetail.dealRes}" /><span style="color: red">*</span> --%>
					</td>
				<td class="td_lable">是否投诉：</td><td class="td_value">
				<select class="select_text" name="isComplain" id="isComplainid"<c:if test="${display=='0'}">disabled="disabled"</c:if>>
					<option value="">---请选择---</option>
					<option value="1" <c:if test="${qcDetail.isComplain=='1' }">selected="selected"  </c:if>  >是</option>
					<option value="0" <c:if test="${qcDetail.isComplain==''||qcDetail.isComplain==null}">selected="selected"  </c:if> <c:if test="${qcDetail.isComplain=='0' }">selected="selected"  </c:if>>否</option>
				</select><span style="color: red">*</span>
				
				</td>
					<td class="td_lable">满意度：</td><td class="td_value">
					<input <c:if test="${display=='0'}">disabled="disabled"</c:if> type="radio" name="satisfied" value="1" <c:if test="${qcDetail.satisfied=='1' or gl=='0' }"> checked="checked" </c:if>/>有
					<input <c:if test="${display=='0'}">disabled="disabled"</c:if> type="radio" name="satisfied" value="0" <c:if test="${qcDetail.satisfied==''||qcDetail.satisfied==null}"> checked="checked" </c:if>  <c:if test="${qcDetail.satisfied=='0' }"> checked="checked" </c:if>/>无
				<span style="color: red">*</span>
				</td>	
				<td class="td_lable">人工转接：</td><td class="td_value">
					<select class="select_text" name="agentTransfer" id="agentTransferid"<c:if test="${display=='0'}">disabled="disabled"</c:if>>
						<option value="">---请选择---</option>
						<option value="1" <c:if test="${qcDetail.agentTransfer=='1' }">selected="selected"  </c:if>>需转接</option>
						<option value="0" <c:if test="${qcDetail.agentTransfer==''||qcDetail.agentTransfer==null}">selected="selected"  </c:if> <c:if test="${qcDetail.agentTransfer=='0' }">selected="selected"  </c:if>>无需转接</option>
					</select><span style="color: red">*</span>
					
					</td>	
			</tr>
			<tr>
				
			<td class="td_lable">备注：</td>
					<td class="td_value" colspan="3">
						<textarea  name="remark" rows="3" onkeyup="strlenLimit(this,500);" style="overflow:auto; width:90%; margin-top:10px;" <c:if test="${display=='0'}">disabled="disabled"</c:if>>${qcDetail.remark}</textarea>
				 </td>
			</tr>
			
			
				<tr id="glgdid">
					<%-- <td colspan="10" style="font-size:13px; text-align:left;">
						关联工单信息 <a href="javascript:void(0);" onclick="$('#showGongDanDiv').slideToggle();">关联</a><br />
						<div id="showGongDanDiv" style="overflow:hidden; height:330px; margin-top:10px; border:#999999 1px solid;background-color:#FFFFFF;border-style:dashed; padding:10px;" align="center">
							<iframe width="100%" height="370" frameborder="0" scrolling="no" src="<%=path%>/page/guanliain_gd.jsp"></iframe>
						
						
						</div>
					</td> --%>
					<td colspan="10" style="font-size:13px; text-align:left;">
					关联工单信息 <a href="javascript:void(0);" onclick="$('#showGongDanDiv112').slideToggle();">关联工单</a><br />			
					<div id="showGongDanDiv112" style="overflow:hidden; height:375px; margin-top:10px; border:#999999 1px solid;border-style:dashed; padding:10px;" align="center">
						<iframe width="100%" height="370" frameborder="0" scrolling="no" src="<%=path%>/page/guanliain_gd.jsp"></iframe>
					</div>
					
					
				</td>
				</tr>
			<tr>
				<td colspan="10">
				  &nbsp;
				</td>
			</tr>	
		
		
			<tr>
				 <td class="td_lable">流水：</td>
				<td class="td_value">					
			<input type="hidden" id="qc_type" name="qc_type"/>
			<input type="hidden" value="${recordId}" name="recordId" id="recordid"/>
			<input type="hidden" id="recoid"  value="${rd.reservedThree}"/>
			<input type="hidden" name="qcid" value="${qcid}" id="qcidid"/>  
			<input type="hidden" id="bigWorkid" name="bigWorkid" value="${rd.bigWorkid}"/>
			<input type="hidden" id="smallWorkid" name="smallWorkid" value="${rd.smallWorkid}"/>	
				<c:if var="qctype" test="${qctype=='8'}">
				<input type="hidden" id="workorid"  value="${wk.serialCdma}" name="workorderId"/>
				<input class="input_text" id="workorderId" value="${wk.serialCdma }" <c:if test="${display=='0'}">disabled="disabled"</c:if> />
				<td class="td_lable">故障号：</td><td class="td_value"><input type="text" class="input_text" id="errorNo" value="${wk.dirNum}" <c:if test="${display=='0'}">disabled="disabled"</c:if>/></td>
				<td class="td_lable">申告时间：</td><td class="td_value"><input type="text" class="input_text" id="declarationTime" value="${wk.complaintTime }" <c:if test="${display=='0'}">disabled="disabled"</c:if>/></td>
				<td class="td_lable">申告大类：</td><td class="td_value"><input type="text" class="input_text" id="declarationBigType" value="${wk.complaintType }" <c:if test="${display=='0'}">disabled="disabled"</c:if>/></td>
				<td class="td_lable">申告内容：</td><td class="td_value"><input  type="text" class="input_text" id="declarationDescription" value="${wk.complaintReason}" <c:if test="${display=='0'}">disabled="disabled"</c:if>/></td>			
				</c:if>
				<c:if var="qctype" test="${qctype=='7'}">
				<input type="hidden" id="workorid"  value="${wk.workorderId}" name="workorderId"/>
				<input class="input_text" id="workorderId" value="${wk.workorderId }" <c:if test="${display=='0'}">disabled="disabled"</c:if> />
				<td class="td_lable">故障号：</td><td class="td_value"><input type="text" class="input_text" id="errorNo" value="${wk.errorNo}" <c:if test="${display=='0'}">disabled="disabled"</c:if>/></td>
				<td class="td_lable">申告时间：</td><td class="td_value"><input type="text" class="input_text" id="declarationTime" value="${wk.declarationTime }" <c:if test="${display=='0'}">disabled="disabled"</c:if>/></td>
				<td class="td_lable">申告大类：</td><td class="td_value"><input type="text" class="input_text" id="declarationBigType" value="${wk.declarationBigType }" <c:if test="${display=='0'}">disabled="disabled"</c:if>/></td>
				<td class="td_lable">申告内容：</td><td class="td_value"><input type="text" class="input_text" id="declarationDescription" value="${wk.declarationDescription }" <c:if test="${display=='0'}">disabled="disabled"</c:if>/></td>	
				</c:if>
				<c:if var="qctype" test="${qctype eq null }">
				<input class="input_text" id="workorderId" value=""  /></td>
				<td class="td_lable">故障号：</td><td class="td_value"><input type="text" class="input_text" id="errorNo" value="" /></td>
				<td class="td_lable">申告时间：</td><td class="td_value"><input type="text" class="input_text" id="declarationTime" value="" /></td>
				<td class="td_lable">申告大类：</td><td class="td_value"><input type="text" class="input_text" id="declarationBigType" value="" /></td>
				<td class="td_lable">申告内容：</td><td class="td_value"><input  type="text" class="input_text" id="declarationDescription" value="" /></td>	
				</c:if>
				</td> 
			</tr>
			
		<%-- <c:if var="qctype" test="${qctype=='7'}">
			<tr>
				<td class="td_lable">流水：</td><td class="td_value">
				<input type="hidden" value="${recordId}" name="recordId" id="recordid"/>
			  	<input type="hidden" name="qcid" value="${qcid}" id="qcidid"/>  		
				<input type="hidden" id="bigWorkid" name="bigWorkid" value="${rd.bigWorkid}"/>
				<input type="hidden" id="smallWorkid" name="smallWorkid" value="${rd.smallWorkid}"/>
				<input type="hidden" id="recoid"  value="${rd.reservedThree}"/>
				<input type="hidden" id="qc_type" name="qc_type"/>
				<input type="hidden" id="workorid"  value="${wk.workorderId}" name="workorderId"/>
				<input class="input_text" id="workorderId" value="${wk.workorderId }" <c:if test="${display=='0'}">disabled="disabled"</c:if> /></td>
				
			</tr>
			</c:if>	 --%>
		</table>
		<div class="button_div">
			<shiro:hasPermission name='record:senc'>	
			<c:if test="${secflag=='0'}">
			<input type="button" value="填写意见" onclick="secqc();" id="secid"/>&nbsp;&nbsp;
			</c:if>
			</shiro:hasPermission>
			<shiro:hasPermission name='record:check'>	
			<c:if test="${subflag!='0'}">
				<input type="button" value="提交" onclick="qcsub();" id="subid"/>&nbsp;&nbsp;
			</c:if>
			</shiro:hasPermission>
			<input type="button" value="返回" onclick="closewin();"/>
			
		</div>
		</form>		
	</div>	
</body>
</html>
