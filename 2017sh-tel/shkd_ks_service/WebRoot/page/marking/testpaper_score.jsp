<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt"  uri="/WEB-INF/fmt-rt.tld"%> 
<%@ taglib prefix="c"  uri="/WEB-INF/c.tld"%> 
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<title>试卷评分</title>
	<link href="${ctx}/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script> 
	<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/js/ajaxSession.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerTab.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
    
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
		table#search_table td.td_hide{display: none;}
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
			loadGroup();
			getSysCodestype();

			var showData = {Rows: [],Total: 0};
			loadData(showData);
			
			 now=new Date();
	         year=now.getFullYear();
	         month=now.getMonth();
	         if(parseInt(month)<10) month = "0"+""+(month+1);
	         $("#beginTime").val(year+"-"+(month)+"-01 00:00:00");
	         $("#submitTime").val(new Date().format("yyyy-MM-dd hh:mm:ss"));
	    });
		
		function resetBtn(){
			document.getElementById("scoreManual").reset();
			
			$("#beginTime").val(year+"-"+(month)+"-01 00:00:00");
	        $("#submitTime").val(new Date().format("yyyy-MM-dd hh:mm:ss"));
		}
		
		var getSysCodestype = function(obj,divid) {
			var url = "${ctx}/controller/syscode/getSyscodes.do";
			$.ajax({
				url: url,
			  	type:"post",
			  	dataType:"json",
			  	data:{"item_flag":obj},
			  	success: function(data){
			  		for(var i=0;i<data.codes.length;i++) {
			  			var code = data.codes[i];
			  			document.getElementById(divid).options.add(new Option(code.name,code.pkAutoId));
			  		}
			  	}
			});
		}

		function loadGroup() {
			var url = "${ctx}/controller/sysuser/getGroups.do";
			$.ajax({
				url: url,
			  	type:"post",
			  	dataType:"json",
			  	data:{"1":"1"},
			  	success: function(data){
			  		for(var i=0;i<data.groups.length;i++) {
			  			document.getElementById("groupName").options.add(new Option(data.groups[i].groupName,data.groups[i].groupName));
			  		}
			  	}
			});
		}
		
		function loadData(resultData){
	  		maingrid_gd= $("#record_list").ligerGrid({
	       	columns: [                
  	           	{ display: '操作', isSort: false, minWidth : 80, render: function (rowdata, rowindex, value){
  					return "<shiro:hasPermission name='kswhgl:sdpf'><a href='javascript:void(0);' onclick='checkScoreManual(\""+rowdata+"\",\""+rowindex+"\")'>手动评分</a> </shiro:hasPermission>";
  	            }},
  	            { display: '试卷编号', name: 'pkAutoId', align: 'left', width :"0",hide:true},
  				{ display: '试卷编号', name: 'exampaperId', align: 'left', minWidth :80,isSort:false},
  				{ display: '试卷名称', name: 'examPaperName', align: 'left', minWidth :160,isSort:false},
  				{ display: '考试名称', name: 'examName', align: 'left', minWidth :160,isSort:false},
  				{ display: '考生姓名', name: 'userName', align: 'left', minWidth :80,isSort:false},
  				{ display: '当前状态', name: 'examineeStatusDesc', align: 'left', minWidth :80,isSort:false,render: function (rowdata, rowindex, value){
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
  	            { display: '开考时间', name: 'beginTimeToString', align: 'left', width :140,isSort:false},
  				{ display: '交卷时间', name: 'submitTimeToString', align: 'left', minWidth :140,isSort:false},
  				{ display: '考试组室', name: 'groupName', align: 'left', width :100,isSort:false}
  				], 
  	             data:resultData, sortName: 'examId',height:'100%', pageSize:10 ,width:"100%",rownumbers :true
  	         });
  	         $("#pageloading").hide(); 
		}
		
		function selectBtn(){
			if($("input[name=examPaperName]").val().length>0 && !text_req($("input[name=examPaperName]").val(),true)){
				$.ligerDialog.warn("试卷名称不能输入特殊字符")
				return;
			}
			if($("input[name=examName]").val().length>0 && !text_req($("input[name=examName]").val(),true)){
				$.ligerDialog.warn("考试名称不能输入特殊字符")
				return;
			}
			
			$("#pageloading").show(); 
			var url = "${ctx}/controller/examine/getScoreManual.do";
			$.ajax({
				url: url,
			  	type:"post",
			  	dataType:"json",
			  	data:$("#scoreManual").serialize(),
			  	success: function(resultData){
			  		loadData(resultData);
			  	},
				error:function(e){
	       			$.ligerDialog.warn('获取数据异常，请稍候再试');
	       			$("#pageloading").hide();
	       	    }
			 });
		}

		function openChooseUser(){
			$.ligerDialog.open({
	               height:420,
	               width: 820,
	               title : '用户管理',
	               url: '${ctx}/page/publicdialog/choose_user.jsp', 
	               showMax: false,
	               showToggle: true,
	               showMin: false,
	               isResize: true,
	               slide: false,
	               data: {
	                   name: "userName"
	               },
	               //自定义参数
	               myDataName: "自定义参数"
	        });
		}
		
		
		function checkScoreManual(rowdata, rowindex){
			var uprow = maingrid_gd.getRow(rowindex);
			var url = "${ctx}/controller/examine/checkScoreManual.do";
			$.ajax({
				url: url,
			  	type:"post",
			  	dataType:"json",
			  	data:{"id":uprow.pkAutoId},
			  	success: function(data){
			  		if(data.canJoinNow=="0"){
			  			$.ligerDialog.warn("当前试卷正在批阅中，只有原评分人才可以进入！");
			  			return;
			  		}else{
			  			manualScore(rowdata, rowindex);
			  		}
			  	}
			});
		}
		
		function manualScore(rowdata, rowindex){
			var uprow = maingrid_gd.getRow(rowindex);
			location.href = "${ctx}/controller/examine/openUpdateView.do?id="+uprow.pkAutoId;
		}
		
		 function keydown(){
		    	if(isEnter()){
		    		selectBtn();
		    	}
			}
    </script> 
</head>
 
<body onkeydown="keydown();" > 
        <div style="display:none"></div>
		<div class="title_div" style="width:100%; margin-top:0px; display:none;"><label style="margin-left:15px;">查询条件</label></div>
		<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
			<form id="scoreManual">
			<table id="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr>
					<td class="td_lable">试卷编号：</td><td class="td_value">
					<input type="text" name="exampaperId" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/></td>
					<td class="td_lable">试卷名称：</td><td class="td_value"><input type="text" name="examPaperName"/></td>
					<td class="td_lable">考试名称：</td><td class="td_value"><input type="text" name="examName"/></td>
					<td class="td_lable">包含考生：</td><td class="td_value">
					<input type="text" id="userName" id="userName" onclick="openChooseUser()" readonly="readonly"/>
					<input type="text" name="userId" id="listUserID" style="display: none"/>
					<a style="cursor: pointer;"><img src="${ctx}/img/icon/delete.gif" height="14" onclick='$("#userName").val("");$("#listUserID").val("")' alt="编辑"></a></td>
				</tr>
				<tr>
					<td class="td_lable">开考时间：</td><td class="td_value"><input id="beginTime" name="beginTime" class="Wdate date_text" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'submitTime\')||\'2120-10-01\'}'})" /></td>
					<td class="td_lable">交卷时间：</td><td class="td_value"><input id="submitTime" name="submitTime"  class="Wdate date_text" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginTime\')}',maxDate:'2120-10-01'})" /></td>
					<td class="td_lable" >考试组室：</td><td class="td_value">
						<select name="groupName" id="groupName" style="width:135px;">
			                <option value="">---请选择---</option>
			            </select> 
					</td>
					<td class="td_lable">考生状态：</td><td class="td_value">
						<select name="examineeStatus" id="stuStatusForm" style="width:135px;">
			                <option value="">---请选择---</option>
			                <option value="5">已自动评分</option>
			                <option value="9">手动评分中</option>
			            </select> 
					</td>
				</tr>
				<tr>
					<td colspan="8" align="center">
						<shiro:hasPermission name="kssjpf:query">
							<input type="button" value="查询" onclick="selectBtn()"/>&nbsp;&nbsp;&nbsp;&nbsp;
						</shiro:hasPermission>
						<input type="button" value="重置"  id="resetbutton" onclick="resetBtn()"/>
					</td>
				</tr>
			</table>
		</div>
		</form>
		
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="record_list"></div>
	</div>
</body>
</html>
