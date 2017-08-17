<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String workorderid=request.getParameter("workorderId")!=null?request.getParameter("workorderId").toString():"";
		
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>客调项目管理系统</title>
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/css/public.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		
	</style>
	<script type="text/javascript"> 
		$(function () {
            var maingrid_gd= $("#paperList_112").ligerGrid({
                columns: [                
				{ display: '业务类型', name: 'businessType', align: 'left', minWidth:100,isSort:false},
                { display: '小结类型', name: 'telsumType', align: 'left', minWidth:100,isSort:false},
				{ display: '故障来源', name: 'errorSource', align: 'left', minWidth:100,isSort:false},
				{ display: '备注', name: 'remark', align: 'left', minWidth:100,isSort:false},
				{ display: '申告号码', name: 'declaration', align: 'left', minWidth:100,isSort:false},
				{ display: '逻辑号码', name: 'logicNo', align: 'left', minWidth:100,isSort:false},
				{ display: '112流水', name: 'workOrderId', align: 'left', minWidth:100,isSort:false},
				{ display: '电话小结流水', name: 'telsumId', align: 'left', minWidth:100,isSort:false},
				{ display: '电话小结时间', name: 'telsumTime', align: 'left', minWidth:100,isSort:false},
				{ display: '电话小结工号', name: 'telsumWorkid', align: 'left', minWidth:100,isSort:false}
                ], url:'<%=path%>/controller/wk112/getwk112XjList.do',
                parms :[{ name:"workorderId",value:<%=workorderid%>}], 
                height:'100%', width:"auto", pageSize:10 ,rownumbers :true
            });
            $("#pageloading").hide(); 
        });
    </script> 
</head>
<body> 
	<div style="">
	    <input type="hidden" id="workorderId" value="<%=request.getParameter("workorderId") %>"/>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="paperList_112" style="margin-top:10px;"></div>
	</div>
</body>
</html>
