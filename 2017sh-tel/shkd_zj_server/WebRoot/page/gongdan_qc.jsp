<%@ page language="java" contentType="text/html;charset=UTF-8"%>  
<%
  String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>客调项目管理系统</title>
	<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script> 
	<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		body{ font-size:13px;}
		.head_h3{ width:100%; height:36px; line-height:36px; background:url(<%=path%>/img/login/toppic.jpg); margin-top:-3px;}
		.head_h3 h3{ color:#ffffff; margin-left:70px; font-size:16px;}
		
		
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(<%=path%>/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		
		td.td_lable{ text-align:right;}
		td.td_value{ text-align:left;}
		input.input_text{border-radius:5px; width:120px; height:18px; line-height:18px; padding-left:3px;}
		input.date_text{border-radius:5px; width:140px; height:18px; line-height:18px;}
		
		div.button_div{ padding:10px;}
		input[type='button']{ border:#d3d3d3 1px solid; width:80px; height:25px; cursor:pointer;}
	</style>
	<script type="text/javascript"> 
		
    </script> 
</head>
<body style="margin:0 auto; background-image: url(<%=path%>/img/login/bgmain.png); min-width:1200px;">
	<div class="head_h3" style="position: relative;"><h3>质量检查管理平台</h3></div>
	<div align="center" style=" margin:auto; margin-top:10px;">
		<object id="player" height="64" width="90%" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6" type="application/x-oleobject">
			<param name="url" value="">
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
		<div class="title_div"><label style="margin-left:15px;">录音评分</label></div>
		<table cellpadding="3" cellspacing="3" width="90%" >
			<tr>
				<td class="td_lable" width="80" >监听时间：</td><td class="td_value" width="120"><input class="Wdate date_text" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" /></td>
				<td class="td_lable" width="80" >受理时间：</td><td class="td_value" width="120"><input class="Wdate date_text" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" /></td>
				<td class="td_lable" width="80" >质检员：</td><td class="td_value" width="120"><input type="text" class="input_text" /></td>
				<td class="td_lable" width="80" >受理员：</td><td class="td_value" width="120"><input type="text" class="input_text" /></td>
				<td class="td_lable" width="80" >设备编号：</td><td class="td_value" width="120"><input type="text" class="input_text" /></td>
			</tr>
			<tr>
				<td class="td_lable">业务类型：</td><td class="td_value"><input type="text" class="input_text" /></td>
				<td class="td_lable">处理结果：</td><td class="td_value"><input type="text" class="input_text" /></td>
				<td class="td_lable">处理情况：</td><td class="td_value"><input type="text" class="input_text" /></td>
				<td class="td_lable">是否TL9000：</td><td class="td_value"><input type="text" class="input_text" /></td>
				<td class="td_lable">满意度：</td><td class="td_value"><input type="text" class="input_text" /></td>
			</tr>
			<tr>
				<td class="td_lable">是否投诉：</td><td class="td_value"><input type="text" class="input_text" /></td>
				<td class="td_lable">合格程度：</td><td class="td_value"><input type="text" class="input_text" /></td>
				<td class="td_lable">人工转接：</td><td class="td_value"><input type="text" class="input_text" /></td>
				<td class="td_lable">备注：</td><td class="td_value" colspan="3"><input type="text" class="input_text" /></td>
			</tr>
			<tr>
				<td colspan="10" style="font-size:13px; text-align:left;">
					关联录音信息 <a href="javascript:void(0);" onclick="$('#showGongDanDiv').slideToggle();">关联</a><br />
					<div id="showGongDanDiv" style="overflow:hidden; height:330px; margin-top:10px; border:#999999 1px solid;background-color:#FFFFFF;border-style:dashed; padding:10px;" align="center">
						<iframe width="100%" height="370" frameborder="0" scrolling="no" src="guanliain_record.html"></iframe>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="10">&nbsp;
					
				</td>
			</tr>
			<tr>
				<td class="td_lable">CTI流水号：</td><td class="td_value"><input type="text" class="input_text" /></td>
				<td class="td_lable">开始时间：</td><td class="td_value"><input type="text" class="input_text" /></td>
				<td class="td_lable">结束时间：</td><td class="td_value"><input type="text" class="input_text" /></td>
				<td class="td_lable">主叫号码：</td><td class="td_value"><input type="text" class="input_text" /></td>
				<td class="td_lable">被叫号码：</td><td class="td_value"><input type="text" class="input_text" /></td>
			</tr>
		</table>
		<div class="button_div">
			<input type="button" value="提交" />&nbsp;&nbsp;
			<input type="button" value="取消" />
		</div>		
	</div>	
</body>
</html>
