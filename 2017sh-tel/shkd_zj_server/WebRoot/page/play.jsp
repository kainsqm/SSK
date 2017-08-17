<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
String reservedThree = request.getParameter("reservedThree").toString();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%
		String num = request.getParameter("recordReference");
		String rname = request.getParameter("n");
	%>
	<head>
		<META content="text/html; charset=utf-8" http-equiv=Content-Type>
		<title>播放录音</title>
	</head>
	<body onload="" >
		<table width="300">
			<tr>
				<td>
					<object id="player" height="64" width="300" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6" type="application/x-oleobject">
						<param name="url" value="<%=path%>/controller/record/downRecord.do?reservedThree=<%=reservedThree%>">
						<PARAM NAME="rate" VALUE="1">
						<PARAM NAME="balance" VALUE="0">
						<PARAM NAME="currentPosition" VALUE="0">
						<PARAM name="defaultFrame" VALUE="">
						<!-- value="0"时为循环播放,value="1"为播放1次. -->
						<PARAM NAME="playCount" VALUE="1">
						<!-- value="0"时为不自动播放,“1”为自动播放；  -->
						<PARAM NAME="autoStart" VALUE="1">
						<PARAM NAME="currentMarker" VALUE="0">
						<PARAM NAME="invokeURLs" VALUE="-1">
						<PARAM NAME="baseURL" VALUE="">
						<PARAM NAME="volume" VALUE="50">
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
				</td>
			</tr>
			<tr>
				<td>
					<span id="playerinfo" style="font-size: 12px;"></span>
				</td>
			</tr>
		</table>
	</body>
</html>