<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
     String fileURL = (String) request.getAttribute("recordFileURL");
%>
<html:html>
	<head>
		<title></title>
	</head>
	<body>
		<table>
			<object id="Player" width=300 height=65 classid="CLSID:6BF52A52-394A-11D3-B153-00C04F79FAA6">
				<param name="URL" value="<%=fileURL%>">
				<param name="autoStart" value="1">
				<param name="balance" value="0">
				<param name="baseURL" value>
				<param name="captioningID" value>
				<param name="currentPosition" value="0">
				<param name="currentMarker" value="0">
				<param name="defaultFrame" value="0">
				<param name="enabled" value="1">
				<param name="enableErrorDialogs" value="0">
				<param name="enableContextMenu" value="1">
				<param name="fullScreen" value="0">
				<param name="invokeURLs" value="1">
				<param name="mute" value="0">
				<param name="playCount" value="1">
				<param name="rate" value="1">
				<param name="SAMIStyle" value>
				<param name="SAMILang" value>
				<param name="SAMIFilename" value>
				<param name="stretchToFit" value="0">
				<param name="uiMode" value="full">
				<param name="volume" value="100">
				<param name="windowlessVideo" value="0">
			</object>
		</table>

	</body>
</html:html>
