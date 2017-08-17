<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公告信息查看</title>
<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script src="<%=path%>/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script src="<%=path%>/js/common.js"></script>
<style>
* {
	margin: 0;
	padding: 0;
	list-style: none;
	font-family: "微软雅黑";
}

body {
	padding: 0 50px;
}

}
h1 div {
	font-size: 13px;
	color: #535353;
	line-height: 35px;
}

p {
	text-indent: 2em;
	font-size: 13px;
	color: #535353;
	line-height: 24px;
}

h1 {
	line-height: 100px;
	border-bottom: 1px solid #9e9c9c;
	font-weight: 500;
	text-align: center;
	font-size:22px;
}

.p1 {
	text-align: center;
	color: #b3b1b1;
	line-height: 30px;
	font-size: 13px;
}

.p2 {
	text-indent: 2em;
	padding-bottom: 50px;
	line-height: 30px;
	font-size: 14px;
}

.p3 {
	color: #b3b1b1;
	line-height: 30px;
	border-top: 1px solid #9e9c9c;
	font-size: 13px;
	text-indent: 0em;
}

span {
	color: #535353;
}

.p4 {
	padding: 20px;
	border: 1px solid #9E9C9C;
	border-top: 2px solid #01AAEE;
	margin-top: 10px;
	font-size: 13px;
}

.p4 a,h5 {
	color: #01AAEE;
}

img {
	margin: 10px auto;
	display: block;
}
</style>
</head>
<body>
	<h1>${notice.title}</h1>
	<p class="p1">
		发布人：<span>${notice.insertUserName}</span> 发布日期：<span>${notice.releaseDate}</span>
	</p>
	<p>
		${notice.content}
	</p>
</body>
</html>
