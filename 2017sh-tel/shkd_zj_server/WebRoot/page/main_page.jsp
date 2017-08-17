﻿<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%
  String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>xxx后台管理系统</title>
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/css/xcConfirm.css" />
		<script src="<%=path%>/js/jquery-1.9.1.js" type="text/javascript"
			charset="utf-8"></script>
		<style type="text/css">
body {
	background-image: url(<%=path%>/img/login/bgmain.png);
	margin: 0px;
	padding-left: 20px;
	padding-right: 20px;
}

body,html {
	height: 100%;
	overflow: hidden;
}

#daohang_div {
	border-radius: 5px;
	background-image: url(<%=path%>/img/login/title_bg.jpg);
	border: solid 1px #D7D7D7;
	width: 99.8%;
	height: 40px;
	line-height: 40px;
	vertical-align: middle;
	font-size: 14px;
	font-family: "黑体";
}

#daohangLabel {
	font-size: 13px;
	font-family: "宋体";
}
</style>
		<script type="text/javascript">
		function topage(){
			$("#main_page_iframe").attr("src","center_page.jsp");
			
		}
		function reframepage(){
		 //   alert(document.getElementById("main_page_iframe").contentWindow.location.href);
		 var src=document.getElementById("main_page_iframe").contentWindow.location.href;
			$("#main_page_iframe").attr("src",src);
		}
</script>
	</head>
	<body>
		<table id="main_iframe_table" cellpadding="0" cellspacing="0"
			width="100%" height="100%"
			style="width: 100%; border: #000000 0px solid;">
			<tr>
				<td valign="top" width="226px" height="100%">
					<iframe id="iframe" name="main" src="left_page.jsp" width="100%"
						width="100%" height="100%" frameborder="0" scrolling="no"
						style="overflow: hidden; border-radius: 5px;"></iframe>
				</td>
				<td valign="middle" width="20px" height="100%">
					&nbsp;
				</td>
				<td valign="top" height="100%">
					<div style="width: 100%; height: 100%; position: relative;">
						<div id="daohang_div"
							style="height: 42px; position: absolute; top: 0; left: 0; right: 0; bottom: 0">
							<label style="margin-left: 15px;">
								<img width="20" onclick="topage();" src="<%=path%>/img/Home.png"
									align="middle" style="cursor: pointer; margin-right: 10px;"
									title="返回首页" />
								当前位置：
							</label>
							<label id="daohangLabel">
								首页
							</label>
							<img width="20" src="<%=path%>/img/Refresh.png"
								onclick="reframepage();" align="middle"
								style="cursor: pointer; float: right; margin-right: 10px; margin-top: 10px;"
								title="刷新当页" />
						</div>
						<div
							style="position: absolute; top: 42px; left: 0; right: 0; bottom: 0">
							<iframe id="main_page_iframe" name="main_page_iframe"
								src="center_page.jsp" width="100%" height="100%" frameborder="0"
								scrolling="no" style="overflow: hidden; border-radius: 5px;"></iframe>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
