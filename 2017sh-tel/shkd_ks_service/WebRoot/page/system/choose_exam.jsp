<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt"  uri="/WEB-INF/fmt-rt.tld"%> 
<%@ taglib prefix="c"  uri="/WEB-INF/c.tld"%> 
<%@ taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<title>历史试卷</title>
	<link href="../../lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="../../lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <script src="../../lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script> 
	<link href="../../css/public.css" rel="stylesheet" type="text/css" />
	<script src="../../js/ajaxSession.js" type="text/javascript"></script>
    <script src="../../lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="../../lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="../../lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="../../lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script src="../../lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="../../lib/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
    <script src="../../lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="../../lib/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(${ctx}/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input.date_text{border-radius:5px; width:135px; height:18px; line-height:18px;}
		table td{ height:30px; line-height:30px;}
		table .search_table td.td_lable{ text-align:right;}
		table .search_table td.td_value{ text-align:left;}
		a{text-decoration : none;color:blue;}
		red{color: red;}
		.reset{ 
		    background: -webkit-gradient(linear, 0% 0%, 0% 100%,from(#01aacf), to(#0165a2)) !important;
		    color: #FFFFFF !important;
		    border: 1px solid #0297bf !important;
		    padding: 0 15px;
		    border-radius: 3px;
		    width: initial !important;
		    height: 25px !important;
    	}
	</style>
	<script type="text/javascript"> 
	    var maingrid_gd ;
		$(function () {
			var showData = {Rows: [],Total: 0};
			loadData(showData);
        });
		
		function loadData(resultData){
	  		maingrid_gd= $("#record_list").ligerGrid({
	  			columns : [ {
					display : '试卷编号',
					name : 'pkAutoId',
					align : 'left'
				}, {
					display : '试卷名称',
					name : 'examPaperName'
				}, {
					display : '试卷描述',
					name : 'examPaperRemark'
				}, {
					display : '生成日期',
					name : 'insertTime'
				}, {
					display : '操作人',
					name : 'userName'
				}, {
					display : '状态',
					name : 'examPaperStatus',
					editor: { type: 'select', valueField: 'examPaperStatus'},
					 render: function (item)
	                    {
	                        if (parseInt(item.examPaperStatus) == 0) return '无效';
	                        if (parseInt(item.examPaperStatus) == 1) return '有效';
						},
				}, {
					display : '试卷类型',
					name : 'examPaperType',
					editor: { type: 'select',  valueField: 'examPaperType'},
					 render: function (item)
	                    {
	                        if (parseInt(item.examPaperType) == 15) return '日考';
	                        if (parseInt(item.examPaperType) == 16) return '月考';
						},
				} ],
               data:resultData, sortName: 'examId',height:'100%', pageSize:10,isScroll:true,width:"auto",rownumbers :true,checkbox:true,isAllowHide: false,
               onCheckRow:function(checked,data,rowid,rowdata){
            	window.parent.document.getElementById("fkExamPaperid").value = data.pkAutoId;
            	window.parent.document.getElementById("fkExamPaperName").value = data.examPaperName;
            	frameElement.dialog.close(); 
       		}
            });
	  		$("#pageloading").hide();
		}
		function selectBtn(){
			$("#pageloading").show();
			var url = "${ctx}/controller/examManager/queryExampapersList.do";
			 $.ajax({
				url: url,
			  	type:"post",
			  	dataType:"json",
			  	data:$("#queryUser").serialize(),
			  	success: function(resultData){
			  		loadData(resultData);
			  	},
				error:function(e){
	       			$.ligerDialog.warn('获取数据异常，请稍候再试');
	       			$("#pageloading").hide();
	       	    }
			 }); 
		}
		 //回车查询
	    function keydown(){
	    	if(isEnter()){
	    		selectBtn();
	    	}
		}
		
    </script> 
</head>
<body> 
	<div onkeydown="keydown();">
		<div class="title_div" style="width:100%; margin-top:0px; display:none;"><label style="margin-left:15px;">查询条件</label></div>
		<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
		<form id="queryUser">
			<table class="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr>
					<td class="td_lable">试卷编号：</td><td class="td_value"><input type="text" name="pkAutoId" style="width: 140px;" /></td>
					<td class="td_lable">试卷名称 ：</td><td class="td_value"><input type="text" name="examPaperName" style="width: 140px;" /></td>
					<td class="td_lable">试卷描述 ：</td><td class="td_value"><input type="text" name="examPaperRemark" style="width: 140px;" /></td>
				</tr>
				<tr>
				<td class="td_lable">试卷类型：</td>
					<td class="td_value"><select name="examPaperType" id="examPaperType"
						style="width: 140px;">
							<c:forEach items="${ExamType}" var="type">
								<option value="${type.itemFlag}">${type.name}</option>
							</c:forEach>
					</select></td>
				<td class="td_lable">试卷状态：</td>
					<td class="td_value"><select name="examPaperStatus" id="examPaperStatus"
						style="width: 140px;">
							<option value="3">--请选择--</option>
							<c:forEach items="${ExamState}" var="state">
								<option value="${state.itemFlag}">${state.name}</option>
							</c:forEach>
					</select></td>
				</tr>
				
				<tr>
					<td colspan="8" align="center">
						<input type="button" value="查询" onclick="selectBtn()"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="reset" class="reset" value="重置" id="resetbutton"/>
					</td>
				</tr>
			</table>	
		</form>			
		</div>
		
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="record_list" style="margin-top:10px;"></div>
	</div>
</body>
</html>
