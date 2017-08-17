<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt"  uri="/WEB-INF/fmt-rt.tld"%> 
<%@ taglib prefix="c"  uri="/WEB-INF/c.tld"%> 
<%@ taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>考生查询</title> 
	<link href="${ctx}/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script> 
    <script src="${ctx}/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${ctx}/js/utils/utils.js" type="text/javascript"></script>
	<script src="${ctx}/js/ajaxSession.js" type="text/javascript"></script>
	<style type="text/css">
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(${ctx}/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input.date_text{border-radius:5px; width:135px; height:18px; line-height:18px;}
		table#search_table td{ height:30px; line-height:30px;}
		table#search_table td.td_lable{ text-align:left;}
		table#search_table td.td_value{ text-align:left;}
		a{text-decoration : none;color:blue;}
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
	var maingrid_gd;
	var now;var year;var month;
	$(function () {
		var showData = {Rows: [],Total: 0};
		loadDataResult(showData);
		
		 now=new Date();
         year=now.getFullYear();
         month=now.getMonth();
        if(parseInt(month)<10) month = "0"+""+(month+1);
        $("#beginTime").val(year+"-"+(month)+"-01 00:00:00");
        $("#submitTime").val(new Date().format("yyyy-MM-dd hh:mm:ss"));
    });
	
	function resetBtn(){
		document.getElementById("queryExamine").reset();
		
        $("#beginTime").val(year+"-"+(month)+"-01 00:00:00");
        $("#submitTime").val(new Date().format("yyyy-MM-dd hh:mm:ss"));
	}
	
	function loadDataResult(resultData){
  		maingrid_gd= $("#record_list").ligerGrid({
  			 columns: [   
					{ display: '操作', isSort: false, minWidth : 80, render: function (rowdata, rowindex, value){
						//考生考试状态   0 -待考中 1-考试中 2-已考完 3-取消考试资格 4-缺考 5-已自动评分 6-已手动评分 7-重新修改自动评分 8-重新修改手动评分 9-手动评分中
						if(rowdata.examineeStatus==2 || rowdata.examineeStatus==5 || rowdata.examineeStatus==6 || rowdata.examineeStatus==9){
							return "<shiro:hasPermission name='kskscx:showexam'><a href='javascript:void(0);' onclick='query_testpaper(\""+rowdata+"\",\""+rowindex+"\")'>查看试卷</a> </shiro:hasPermission>";
						} else{
							return "<span style='color:gray'>查看试卷</span>"
						}
					}},
					{ display: '试卷编号', name: 'examId', align: 'left', minWidth :80,isSort:false},
					{ display: '试卷名称', name: 'examPaperName', align: 'left', maxWidth :150,isSort:false},
					{ display: '考试名称', name: 'examName', align: 'left', minWidth :120,isSort:false},
					{ display: '考生工号', name: 'workId', align: 'left', minWidth :110,isSort:false},
			        { display: '当前状态', name: 'examineeStatus', align: 'left', width :100,isSort:false,render: function (rowdata, rowindex, value){
						//考生考试状态   0 -待考中 1-考试中 2-已考完 3-取消考试资格 4-缺考 5-已自动评分 6-已手动评分 7-重新修改自动评分 8-重新修改手动评分 9-手动评分中
						if(rowdata.examineeStatus==0) return "待考中";
						if(rowdata.examineeStatus==1) return "考试中";
						if(rowdata.examineeStatus==2) return "已考完";
						if(rowdata.examineeStatus==3) return "取消考试资格";
						if(rowdata.examineeStatus==4) return "缺考";
						if(rowdata.examineeStatus==5) return "已自动评分";
						if(rowdata.examineeStatus==6) return "已手动评分";
						if(rowdata.examineeStatus==7) return "重新修改自动评分";
						if(rowdata.examineeStatus==8) return "重新修改手动评分 ";
						if(rowdata.examineeStatus==9) return "手动评分中";
					}},
					{ display: '开考时间', name: 'beginTime', align: 'left', minWidth :100,isSort:false},
					{ display: '交卷时间', name: 'submitTime', align: 'left', minWidth :100,isSort:false}
	            ], 
	            data:resultData, sortName: 'examId',height:'100%', pageSize:10 ,width:"100%",rownumbers :true,newPage:1
        });
        $("#pageloading").hide(); 
	}
	
	function loadData(){
	    selectBtn();
	}
	
	function selectBtn(){
		if($("input[name=examPaperName]").val().length>0 && !text_req($("input[name=examPaperName]").val(),true)){
			$.ligerDialog.warn("试卷名称不能输入特殊字符");
			return;
		}
		
		if($("#beginTime").val() == ""){
			$.ligerDialog.warn("开考时间不能为空");
			return;
		}
		
		if($("#submitTime").val() == ""){
			$.ligerDialog.warn("交卷时间不能为空");
			return;
		}
		
		 $("#pageloading").show(); 
		 var url = "${ctx}/controller/examine/getPersonExams.do";
		 $.ajax({
			url: url,
		  	type:"POST",
		  	data:$("#queryExamine").serialize(),
		  	success: function(resultData){
		  		loadDataResult(resultData);
		  	},
			error:function(e){
       			$.ligerDialog.warn('获取数据异常，请稍候再试');
       			$("#pageloading").hide();
       	    }
		 });
	}
	
	function query_testpaper(rowdata, rowindex){
		var uprow = maingrid_gd.getRow(rowindex);
		var uprow = maingrid_gd.getRow(rowindex);
		location.href = "${ctx}/controller/examine/openUpdateView.do?id="+uprow.pkAutoId+"&queryflag=1";
	}
	
	 //回车查询
	    function keydown(){
	    	if(isEnter()){
	    		selectBtn();
	    	}
		}
	
    </script> 
</head>
<body onkeydown="keydown();" > 
	<div>
		<div class="title_div" style="width:100%; margin-top:0px; display:none;"><label style="margin-left:15px;">查询条件</label></div>
		<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
		<form id="queryExamine">
			<input type="text" name="enabled" value="1" style="display: none"/>
			<table id="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr>
					<td class="td_lable">考生工号：</td><td class="td_value"><input type="text" name="workId"/></td>
					<td class="td_lable">试卷编号：</td><td class="td_value"><input type="text" name="examId"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/></td>
					<td class="td_lable">开考时间：</td><td class="td_value"><input id="beginTime" name="beginTime" class="Wdate date_text" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'submitTime\')||\'2120-10-01\'}'})" /></td>
					<td class="td_lable">交卷时间：</td><td class="td_value"><input id="submitTime" name="submitTime"  class="Wdate date_text" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginTime\')}',maxDate:'2120-10-01'})" /></td>
				</tr>
				<tr>
					<td class="td_lable">试卷名称：</td><td class="td_value"><input type="text" name="examPaperName"/></td>
				</tr>
				<tr>
					<td colspan="8" align="center">
						<shiro:hasPermission name="kskscx:query">
							<input type="button" value="查询" onclick="selectBtn()"/>&nbsp;&nbsp;&nbsp;&nbsp;
						</shiro:hasPermission>
						<input type="button" value="重置"  id="resetbutton" onclick="resetBtn()"/>
					</td>
				</tr>
			</table>	
		</form>		
		</div>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="record_list"></div>
	</div>
</body>
</html>
