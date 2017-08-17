<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
	String path = request.getContextPath();
	String heartbeatIpAdd=request.getAttribute("heartbeatIpAdd").toString();
	String userId=request.getAttribute("userId").toString();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>进行考试</title>
	<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/css/public.css" rel="stylesheet" type="text/css" />
    <script src="<%=path %>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script> 
    <script src="<%=path %>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="<%=path %>/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="<%=path %>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="<%=path %>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="<%=path %>/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
	<script src="<%=path %>/js/ajaxSession.js" type="text/javascript"></script>
	
	<style type="text/css">
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px;border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input.date_text{border-radius:5px; width:135px; height:18px; line-height:18px;}
		table#search_table td{ height:30px; line-height:30px;}
		table#search_table td.td_lable{ text-align:right;}
		table#search_table td.td_value{ text-align:left;}
		#body{overflow: auto !important;}
		a{text-decoration : none;color:blue;}
	</style>
	<script type="text/javascript"> 
	   var maingrid_gd;
		$(function () {
            maingrid_gd= $("#exam_list").ligerGrid({
                columns: [                
				{ display: '考试编号', name: 'pkAutoId', align: 'left', minWidth :80,isSort:false},
				{ display: '考试名称', name: 'examName', align: 'left', minWidth :145,isSort:false},
				{ display: '试卷名称', name: 'examPaperName', align: 'left', maxWidth :145,isSort:false},
				{ display: '开始考试时间', name: 'examBeginTimeString', align: 'left', minWidth :143,isSort:false},
				{ display: '结束考试时间', name: 'examEndTimeString', align: 'left', minWidth :143,isSort:false},
                { display: '考试时长', name: 'examTimeLength', align: 'left', width :100,isSort:false},
				{ display: '出题人', name: 'setQuestionName', align: 'left', minWidth :100,isSort:false},
				{ display: '操作', isSort: false, minWidth : 80, render: function (rowdata, rowindex, value){
				    return "<a href='#' onclick='joinExam(\""+rowdata.exampaperId+"\",\""+rowdata.examExampaperExamineId+"\",\""+rowdata.pkAutoId+"\",\""+rowdata.examTimeLength+"\")'title='开始考试'>开始考试</a>";
                }}
                ], 
                url:'<%=path%>/controller/examJoin/toExamJoinList.do',
				height:'100%', 
				width:"auto", 
				pageSize:10 ,
				rownumbers :true
            });
            $("#pageloading").hide(); 
        });
        
        
        function exam_search(){
			maingrid_gd.options.usePager =true;
			maingrid_gd.options.page='1';
			maingrid_gd.options.newPage=1;
		    maingrid_gd.options.url ='<%=path%>/controller/examJoin/toExamJoinList.do';
        	maingrid_gd.reload();
        } 
        
        function joinExam(exampaperId, examExampaperExamineId, examId, strdisplay) {
	$.ligerDialog.confirm('您确认要参加考试嘛？', function(yes) {
		if (yes) {
			try {
				myAjaxJsonpExamResult(examExampaperExamineId, strdisplay,
						examId,exampaperId);
			} catch (e) {
				$.ligerDialog.error('访问外域出错!');
			}
		}
	});
}

// start niewenqiang 2017-04-26 ajax跨域提交 用于判断该场考试该考试是否已在考试
function myAjaxJsonpExamResult(examExampaperExamineId, strdisplay, examId,exampaperId) {
	<%-- jQuery
			.ajax({
				'type' : 'get',
				'url' : '<%=heartbeatIpAdd%>',
				'data' : {
					'kaoshiId' : examExampaperExamineId,
					'userId' : '<%=userId%>',
					'answerStr' : "",
					'ipAddr' : '<%=request.getRemoteAddr() %>',
					'state' : "0",
					'remainingTime' : strdisplay,
					'examId' : examId,
					'method' : 'checkDk'
				},
				'dataType' : 'jsonp',// jsonp
				'jsonp' : 'callback',
				'contentType': "application/jsonp; charset=utf-8",
				'async' : false,
				'cache' : false,
				'success' : function(data) {
					if (data != undefined) {
						if (data.flg == "4") {
							$.ligerDialog
									.error("登录的电脑(IP)已经进行考试 或 登录的用户已在其他地方进行考试!");
							return;
						}
					}
					var dialog=$.ligerDialog
							.open({
								height : 560,
								width : 1010,
								showMax : true,
								modal : false,
								id : 'examname',
								isDrag : false,
								allowClose : false,
								title : '考试中',
								url : '<%=path%>/controller/examJoin/queryQuesByExampaperId.do?pkAutoId='
										+ examId
										+ '&exampaperId='
										+ exampaperId
										+ '&examExampaperExamineId='
										+ examExampaperExamineId
										+ '&isIntoPageByMain=0',
										
								isResize : true,
							});
					 
				},error: function(){
			         $.ligerDialog.error('系统异常!');
			     }
			}); --%>
			
			var dialog=$.ligerDialog
							.open({
								height : 560,
								width : 1010,
								showMax : true,
								modal : false,
								id : 'examname',
								isDrag : false,
								allowClose : false,
								title : '考试中',
								url : '<%=path%>/controller/examJoin/queryQuesByExampaperId.do?pkAutoId='
										+ examId
										+ '&exampaperId='
										+ exampaperId
										+ '&examExampaperExamineId='
										+ examExampaperExamineId
										+ '&isIntoPageByMain=0',
										
								isResize : true,
							});
}

       //回车查询
	    function keydown(){
	    	if(isEnter()){
	    		exam_search();
	    	}
		}
		

    </script> 
</head>
<body> 
	<div onkeydown="keydown();">
	    <input id="in_examId" type="text" style="display: none"/>
	    <input id="in_examTimeLen" type="text" style="display: none"/>
		<div class="title_div" style="width:100%; margin-top:0px; display:none;"><label style="margin-left:15px;">查询条件</label></div>
		<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
			请选择需要参加的考试：	
		</div>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="exam_list"></div>
	</div>
</body>
</html>
