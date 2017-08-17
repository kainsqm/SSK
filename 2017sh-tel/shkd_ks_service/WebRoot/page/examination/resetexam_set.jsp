<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt"  uri="/WEB-INF/fmt-rt.tld"%> 
<%@ taglib prefix="c"  uri="/WEB-INF/c.tld"%> 
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<title>重考设置</title>
	<link href="${ctx}/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${ctx}/js/ajaxSession.js" type="text/javascript"></script>
	<style type="text/css">
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px;border: solid 1px #D7D7D7;
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
		$(function () {
			var showData = {Rows: [],Total: 0};
			loadDataResult(showData);
	    });
		
		function loadDataResult(resultData){
			maingrid_gd= $("#record_list").ligerGrid({
                columns: [               
				{ display: 'pkAutoId', name: 'pkAutoId', align: 'left',width:"0",hide:true},
				{ display: 'fkUserId', name: 'pkAutoId', align: 'left',width:"0",hide:true},
				{ display: 'examId', name: 'examId', align: 'left',width:"0",hide:true},
				{ display: '已考过的考试名称', name: 'examName', align: 'left', minWidth :150,isSort:false},
				{ display: '是否恢复答案及剩余时间', name: 'isrevert', align: 'left', minWidth :150,isSort:false,render: function (rowdata, rowindex, value){
						//是否恢复答案及剩余考试时间   0-否 1-是
						if(rowdata.isnormal==0 && rowdata.examPaperFlag!=2) return "是";
						else return "无需恢复";
					}},
				{ display: '考生姓名', name: 'userName', align: 'left', minWidth :150,isSort:false},
				{ display: '考卷状态', name: 'examineeStatus', align: 'left', minWidth :150,isSort:false,render: function (rowdata, rowindex, value){
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
					}}
                ], 
                data:resultData, sortName: 'examName',height:"100%", pageSize:10 ,width:'auto',rownumbers :true,checkbox:true,newPage:1
            });
            $("#pageloading").hide(); 
		}
		
		function selectBtn(){
			 $("#pageloading").show(); 
			 var url = "${ctx}/controller/examine/getResetExams.do";
			 $.ajax({
				url: url,
			  	type:"post",
			  	dataType:"json",
			  	data:$("#resetExamine").serialize(),
			  	success: function(resultData){
			  		loadDataResult(resultData);
			  	},
				error:function(e){
	       			$.ligerDialog.warn('获取数据异常，请稍候再试');
	       			$("#pageloading").hide();
	       	    }	
			 });
		}	
			
		function loadData(){
		     selectBtn();
		}	
			
		function resetSubmit(){
			var selected = maingrid_gd.getSelectedRows();
			var url = "${ctx}/controller/examine/restartExam.do";
	   		if(selected.length>0){
			   	$.ligerDialog.confirm('确认重考？', function (flag) { 
		       		if(flag){ //examineid_examid_fkUserId_isvert
		       		    var str = "";
		       		 	for (var i in selected){
		       		 		var t = selected[i].pkAutoId+"_"+selected[i].examId+"_"+selected[i].fkUserId;
		       		 		if(selected[i].isnormal==0 && selected[i].examPaperFlag!=2){
		       		 			t = t+"_1";
		       		 		}
		       		 		str += t+',';
		    		 	} 
			       		$.ajax({
			     			url: url,
			     		  	type:"post",
			     		  	dataType:"json",
			     		  	data:{"params":str},
			     		  	success: function(resultData){
				     		  	$.ligerDialog.success('重置考试成功','提示',function(yes){	
				     		  		selectBtn();
				 				});
			     		  	},
			    			error:function(e){
			           			$.ligerDialog.warn('重置考试失败，请稍候再试');
			           	    }
			       		}); 
		       		}
		        }); 
		   	} else{
		   		$.ligerDialog.warn('请先选择记录，再进行操作');
		   	} 
		}
		
		 //回车查询
	    function keydown(){
	    	if(isEnter()){
	    		selectBtn();
	    	}
		}
    </script> 
</head>
<!-- 
<body style="margin:0 auto; background-color:#FFFFFF; padding:5px;">
 -->
<body onkeydown="keydown();"> 
	<div class="title_div" style="width:100%; margin-top:0px; display:none;"><label style="margin-left:15px;">查询条件</label></div>
	<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
	<form id="resetExamine">
		<table id="search_table" cellpadding="3" cellspacing="3" width="100%" >
			<tr>
				<td class="td_lable" style="text-align: left;">
				考生工号：&nbsp;&nbsp;
					<input type="text" name="workId" id="userId"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				考生姓名：&nbsp;&nbsp;
					<input type="text" name="userName" id="userName"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<shiro:hasPermission name="kscksz:query">
					<input type="button" value="查询" onclick="selectBtn()"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</shiro:hasPermission>
				<shiro:hasPermission name="kscksz:ck">
					<input type="button" value="重考" onclick="resetSubmit()"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</shiro:hasPermission>
				<input type="reset" class="reset" value="重置" id="resetbutton"/></td>
			</tr>
		</table>	
	</form>
	</div>
	<div class="l-loading" style="display:block" id="pageloading"></div>
	<div id="record_list"></div>
	
	<div style="margin-top:20px;text-align:center;">
		
</body>
</html>
