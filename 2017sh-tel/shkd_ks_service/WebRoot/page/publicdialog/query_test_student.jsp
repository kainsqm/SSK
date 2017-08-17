<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt"  uri="/WEB-INF/fmt-rt.tld"%> 
<%@ taglib prefix="c"  uri="/WEB-INF/c.tld"%> 
<%@ taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>查看参考人员</title>
	<link href="${ctx}/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script> 
	<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/js/ajaxSession.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${ctx}/js/utils/utils.js" type="text/javascript"></script>
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
			 $("#pageloading").hide();
			 selectBtn();
		});
	
	    function loadData(){
	      selectBtn();
	    }
	
	    function selectBtn(){
	    	$("#pageloading").show();
			var url = "${ctx}/controller/exam/getTestStudents.do";
			$.ajax({
				url: url,
			  	type:"post",
			  	dataType:"json",
			  	data:$("#examUserForm").serialize(),
			  	success: function(resultData){
			  		maingrid_gd= $("#record_list").ligerGrid({
			  			columns: [    
	  		   			{ display: '操作', isSort: false, minWidth : 60,render: function (rowdata, rowindex, value){
	  		   				if(rowdata.examineeStatus==6 || rowdata.examineeStatus==2 || rowdata.examineeStatus==5 || rowdata.examineeStatus==7 || rowdata.examineeStatus==8 ||rowdata.examineeStatus==9 ){
	  		   					return "<shiro:hasPermission name='kskssjck:queryexam'><a href='javascript:void(0);' onclick='query_testpaper(\""+rowdata+"\",\""+rowindex+"\")'>查看考卷</a>  </shiro:hasPermission>"; 
	  		   				} else if(rowdata.examineeStatus==0 || rowdata.examineeStatus==3 ){
	  		   					return "<shiro:hasPermission name='kskssjck:queryexam'><span style='color:gray'>查看考卷</span> </shiro:hasPermission>"
	  		   				} else if(rowdata.examineeStatus==1){
	  		   					return "<shiro:hasPermission name='kskssjck:qztj'><a href='javascript:void(0);' onclick='forceSubmit(\""+rowdata+"\",\""+rowindex+"\")'>强制提交</a>  </shiro:hasPermission>";
	  		   				}
	  		   			}},
	  		          	{ display: '考生工号', name: 'workId', align: 'left', minWidth :80,isSort:false},
	  		   			{ display: '考生姓名', name: 'userName', align: 'left', maxWidth :100,isSort:false},
	  		   			{ display: '组室', name: 'groupName', align: 'left', minWidth :100,isSort:false,hide:'true'},
	  		   			{ display: '开考时间', name: 'beginTime', align: 'left', width :130,isSort:false,render: function (rowdata, rowindex, value)
			                {
			                    var h = "<span class='span' title='"+value+"' style='overflow: hidden;text-overflow: hidden;display: block;text-overflow: ellipsis; white-space: nowrap;'>"+value+"<span>";
			                    return h;
			                }
	                   },
	  		   			{ display: '交卷(或自动保存)时间', name: 'submitTime', align: 'left', width :130,isSort:false,render: function (rowdata, rowindex, value)
			                {
			                    var h = "<span class='span' title='"+value+"' style='overflow: hidden;text-overflow: hidden;display: block;text-overflow: ellipsis; white-space: nowrap;'>"+value+"<span>";
			                    return h;
			                }
	                    },
	  		   			{ display: '考试时长', name: 'userExamLength', align: 'left', minWidth :60,isSort:false},
	  		   			{ display: '当前状态', name: 'examineeStatus', align: 'left', minWidth :60,isSort:false,render: function (rowdata, rowindex, value){
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
	  		   			{ display: '考试成绩', name: 'exampaperScore', align: 'left', minWidth :60,isSort:false},
	  		   			{ display: '判分人', name: 'scoreuserName', align: 'left', minWidth :80,isSort:false}
	  	                ], 
	  	                data:resultData, sortName: 'examId',height:'100%', pageSize:10 ,width:"auto",rownumbers :true,newPage:1
			        });
			        $("#pageloading").hide(); 
			  	},
			  	error:function(error){
			  		$.ligerDialog.warn("系统调用异常，请稍后再试!");
			  		$("#pageloading").hide(); 
			  	}
			 });
	    }
	
	function forceSubmit(rowdata, rowindex){
		var crow = maingrid_gd.getRow(rowindex);
		$.ajax({
			'type':'get',
			'url':$("#url").val(),
			'data':{
				'method':'forceSubmit',
				'userId':crow.userId,
				'examId':crow.examId
			},
			'dataType':'jsonp',//jsonp
			'jsonp':'callback',
			'async':false,
			'cache':false,
			'success':function(resultData){
				if(resultData.flg==6){
					/* $.ligerDialog.success('强制提交(或交卷)需1分钟~2分钟方可生效,并且在生效期内重置考试也会导致自动强制交卷,所以请勿在生效期内重置此考试！','提示',function(yes){	
	     		  		selectBtn();
	 				}); */
					$.ligerDialog.waitting('强制提交(或交卷)需1分钟~2分钟方可生效,并且在生效期内重置考试也会导致自动强制交卷,所以请勿在生效期内重置此考试！<p style="padding:10px 70px 10px 70px;color:blue;">正在提交中,请稍候...</p>'); setTimeout(function () { 
						 selectBtn();
						 $.ligerDialog.closeWaitting(); 
					}, 6000);
				}else{
					$.ligerDialog.warn("提交失败，请稍后再试!");
				}
				
			},
		  	'error':function(error){
		  		$.ligerDialog.warn("系统调用异常，请稍后再试!");
		  	}
		});
	}

	function query_testpaper(rowdata, rowindex){
		var uprow = maingrid_gd.getRow(rowindex);
		location.href = "${ctx}/controller/examine/openUpdateView.do?id="+uprow.pkAutoId+"&queryflag=1";
	}
	
	function back(){
		history.back();
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
			<form id="examUserForm">
			<input type="text" name="examId" value="${examId}"  style="display: none"/>
			<input type="text" id="url" value="${url}"   style="display: none"/>
			<input type="text" name="exampaperId" value="${listPaperID}"  style="display: none"/>
			<table id="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr>
					<td class="td_lable">考生工号：</td><td class="td_value"><input type="text" name="workId"/></td>
					<td class="td_lable">考试状态：</td><td class="td_value">
			            <select name="examineeStatus" id="stuStatusForm" style="width:135px;">
			                <option value="">---请选择---</option>
			                <option value="0">待考中</option>
			                <option value="1">考试中</option>
			                <option value="2">已考完</option>
			                <option value="3">取消考试资格</option>
			                <option value="4">缺考</option>
			                <option value="5">已自动评分</option>
			                <option value="6">已手动评分</option>
			                <option value="7">重新修改自动评分</option>
			                <option value="8">重新修改手动评分</option>
			                <option value="9">手动评分中</option>
			            </select> 
					</td>
					<td class="td_lable">开考时间：</td><td class="td_value"><input id="beginTime" name="beginTime" class="Wdate date_text" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'submitTime\')||\'2120-10-01\'}'})" /></td>
					<td class="td_lable">交卷时间：</td><td class="td_value"><input id="submitTime" name="submitTime"  class="Wdate date_text" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginTime\')}',maxDate:'2120-10-01'})" /></td>
				</tr>
				<tr>
					<td colspan="8" align="center">
						<input type="button" value="查询" onclick="selectBtn()"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="reset" value="重置" class="reset"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="返回" id="resetbutton" onclick="back()"/>
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
