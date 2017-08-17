<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt"  uri="/WEB-INF/fmt-rt.tld"%> 
<%@ taglib prefix="c"  uri="/WEB-INF/c.tld"%> 
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>考试维护管理</title>
	<link href="${ctx}/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script> 
    <script src="${ctx}/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerListBox.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${ctx}/js/ajaxSession.js" type="text/javascript"></script>
	<script src="${ctx}/page/examination/js/exam_manage.js" type="text/javascript"></script>
	<script src="${ctx}/js/utils/utils.js" type="text/javascript"></script>
	<style type="text/css">
		.middle input { display: block;width:30px; margin:2px;}
		.listbox {margin:4px;float:left;}
		.listbox td{height:15px; line-height:15px; }
		
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(${ctx}/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input.date_text{border-radius:5px; width:135px; height:18px; line-height:18px;}
		table td{ height:30px; line-height:30px;}
		table .search_table td.td_lable{ text-align:left;}
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
	 var chooseAllUsers;
	 var jsonArray;
	 
	 var now;var year;var month;
	$(function () {
		 getSysCodestype("SYSEXAMTYPE","examType");     //获取考试类型
		 getSysCodestype("SYSEXAMOBJECT","examObject");   //获取考试对象
		 var showData = {Rows: [],Total: 0};
		 loadDataResult(showData);
		 
         $("#listbox1,#listbox2").ligerListBox({
            isShowCheckBox: true,
            isMultiSelect: true,
            height: 110,
            width:120
         });
         
         now=new Date();
         year=now.getFullYear();
         month=now.getMonth();
         if(parseInt(month)<10) month = "0"+""+(month+1);
         $("#begin_time").val(year+"-"+(month)+"-01 00:00:00");
         $("#end_time").val(new Date().format("yyyy-MM-dd hh:mm:ss"));
    });
	
	function resetBtn(){
		document.getElementById("queryExamForm").reset();
		
		$("#begin_time").val(year+"-"+(month)+"-01 00:00:00");
        $("#end_time").val(new Date().format("yyyy-MM-dd hh:mm:ss"));
	}
	
	function loadDataResult(resultData){
  		maingrid_gd= $("#record_list").ligerGrid({
             columns: [  
			{ display: '操作', isSort: false, minWidth : 80,  render: function (rowdata, rowindex, value){
				var u = [];
				console.log(rowdata.examStatus+'xxx')
				if(rowdata.examStatus==0 || rowdata.examStatus=='0'){
					u.push("<shiro:hasPermission name='kscspz:update'> <a href='javascript:void(0);' onclick='updateRow(\""+rowindex+"\")'>修改</a></shiro:hasPermission>");
					u.push("&nbsp;&nbsp;&nbsp;&nbsp;");
				}
				u.push("<shiro:hasPermission name='kscspz:delete'> <a href='javascript:void(0);' onclick='deleteRow(\""+rowdata+"\",\""+rowindex+"\")'>删除</a></shiro:hasPermission>");
				return u.join("");
			}},
			{ display: '考试名称', name: 'examName', align: 'left', minWidth :100,isSort:false},
			{ display: '试卷名称', name: 'examPaperName', align: 'left', minWidth :100,isSort:false},
			{ display: '开始考试时间', name: 'toExamBeginTimeString', align: 'left', minWidth :120,isSort:false},
			{ display: '结束考试时间', name: 'toExamEndTimeString', align: 'left', minWidth :120,isSort:false},
	        { display: '考试时长', name: 'examTimeLength', align: 'center', width :100,isSort:false},
			{ display: '合格分数', name: 'examNormalScore', align: 'center', minWidth :70,isSort:false},
			{ display: '是否允许查询', name: 'toExamQuery', align: 'center', minWidth :70,isSort:false},
			{ display: '考试类型', name: 'toExamtype', align: 'center', minWidth :70,isSort:false},
			{ display: '考试对象', name: 'toExamtarget', align: 'center', minWidth :70,isSort:false},
			{ display: 'pkAutoId', name: 'pkAutoId', align: 'left', width:"0", hide:true},
			{ display: 'examPaperId', name: 'examPaperId', align: 'left', width:"0", hide:true}
            ], 
            data:resultData, sortName: 'examId',height:'100%', pageSize:10 ,width:"100%",checkbox:true,rownumbers :true,newPage:1
        });
        $("#pageloading").hide();
	}
	
	function selectBtn(){ 
		if($("input[name=examName]").val().length>50){
			$.ligerDialog.warn("考试名称过长");
			return;
		}
		
		if($("input[name=examName]").val().length>0 && !text_req($("input[name=examName]").val(),true)){
			$.ligerDialog.warn("考试名称不能输入特殊字符");
			return;
		}
		
		$("#pageloading").show();
		var url = "${ctx}/controller/exam/getExamsManage.do";
		$.ajax({
			url: url,
		  	type:"post",
		  	dataType:"json",
		  	data:$("#queryExamForm").serialize(),
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
	
	
	function insertRow(){
		location.href = "${ctx}/controller/page/addView.do";
	};
	
	function updateRow(rowindex){
		var uprow = maingrid_gd.getRow(rowindex);
		var enddate=new Date(Date.parse(uprow.toExamEndTimeString .replace(/-/g,"/")));
        var date = new Date();   
        if(enddate<date) {  
            $.ligerDialog.warn("考试时间已过不能修改");
            return false;  
        } else {
    		location.href = "${ctx}/controller/exam/openUpdateView.do?pkAutoId="+uprow.pkAutoId+"&examPaperId="+uprow.examPaperId;
        }
	}
	
	function deleteRow(rowdata, rowindex){
		 var uprow = maingrid_gd.getRow(rowindex);
		 $.ligerDialog.confirm('是否删除考试：'+uprow.examName, function (flag) { //for (var i in rows) 
	       	if(flag){
	       		deleteData(uprow.pkAutoId);
	       	}
	     }); 
	};
	
	function deleteRows(){
		var selected = maingrid_gd.getSelectedRows();
   		if(selected.length>0){
		   	$.ligerDialog.confirm('确认删除所选？', function (flag) { 
	       		if(flag){
	       			var chooseStr = "";
	       		 	for (var i in selected){
	       		 		chooseStr = chooseStr + selected[i].pkAutoId +",";
	    		 	} 
	       		 	chooseStr = chooseStr.substring(0,chooseStr.length-1);
	       			var url = "${ctx}/controller/exam/deleteExamLists.do";
	    			$.ajax({
	    			url: url,
	    		  	type:"post",
	    		  	dataType:"json",
	    		  	data:{"ids":chooseStr},
	    		  	success: function(resultData){
	    		  		if(resultData.resultStr=="-1"){
	    		  			$.ligerDialog.warn('已有人参加考试，此考试不能删除！');
	    		  			return;
	    		  		}else{
	    		  			$.ligerDialog.success('删除成功','提示',function(yes){	
	    		  				refresh();
	    					});	
	    		  		}
	    		  	},
	    			error:function(e){
	           			$.ligerDialog.warn('删除失败，请稍候再试');
	           	    }
	    		 });
	       		}
	        }); 
	   	} else{
	   		$.ligerDialog.warn('请先选择记录，再进行操作');
	   	} 
	}
	
	function deleteData(pkAutoId){
		 var url = "${ctx}/controller/exam/deleteExam.do";
		 $.ajax({
			url: url,
		  	type:"post",
		  	dataType:"json",
		  	data:{"pkAutoId":pkAutoId},
		  	success: function(resultData){
		  		if(resultData.resultStr=="-1"){
		  			$.ligerDialog.warn('已有人参加考试，此考试不能删除！');
		  			return;
		  		}else{
		  			$.ligerDialog.success('删除成功','提示',function(yes){	
		  				refresh();
					});	
		  		}
		  		
		  	},
			error:function(e){
       			$.ligerDialog.warn('删除失败，请稍候再试');
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
	
	function openChooseTestpaper(){
		 $.ligerDialog.open({
              height:420,
              width: 820,
              title : '试卷管理',
              url: '${ctx}/page/publicdialog/choose_testpaper.jsp', 
              showMax: false,
              showToggle: true,
              showMin: false,
              isResize: true,
              slide: false,
              data: {
                  ename: "testpaper",
                  eid:"examPaperId"
              },
              //自定义参数
              myDataName: "自定义参数"
       });
	}
	
	function insertdivcancel(){
	   $("#insertdiv").css("display","none");
	}
	
	function updatedivcancel(){
	   $("#updatediv").css("display","none");
	}
	function hiddenDialog(){
		$(".l-dialog,.l-window-mask").css("display","none"); //隐藏窗口和遮罩层
	}
	
	function refresh(){
		location.href = "${ctx}/controller/page/manageView.do";
	}
	
	
		  //回车查询
	    function keydown(){
	    	if(isEnter()){
	    		selectBtn();
	    	}
		}
	
	
    </script> 
</head>
<body onkeydown="keydown();"> 
	<div>
		<input type="text" id="chooseAllUsers" style="display: none"/>
		<div class="title_div" style="width:100%; margin-top:0px; display:none;"><label style="margin-left:15px;">查询条件</label></div>
		<form id="queryExamForm">
		<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
			<input type="text" name="listUserID"  id="listUserID" style="display: none"/>
			<input type="text" name="examPaperId" id="examPaperId" style="display: none"/>
			<table class="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr>
					<td class="td_lable">考试名称：</td><td class="td_value"><input type="text" id="examName" name="examName"/></td>
					<td class="td_lable">考试时长：</td><td class="td_value">
					<input type="text" id="examTimeLen" name="examTimeLen" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/></td>
					<td class="td_lable">考试开始时间：</td><td class="td_value"><input id="begin_time" name="toExamBeginTime" class="Wdate date_text" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'end_time\')||\'2120-10-01\'}'})" /></td>
					<td class="td_lable">考试结束时间：</td><td class="td_value"><input id="end_time" name="toExamEndTime"  class="Wdate date_text" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'begin_time\')}',maxDate:'2120-10-01'})" /></td>
				</tr>
				<tr>
					<td class="td_lable">试卷名称：</td><td class="td_value"><input type="text" id="testpaper" name="examPaperName" onclick="openChooseTestpaper()" />
							<a style="cursor: pointer;"><img src="${ctx}/img/icon/delete.gif" height="14" onclick='$("#testpaper").val("");$("#examPaperId").val("")' alt="编辑"></a></td>
					<td class="td_lable">参考人员：</td><td class="td_value"><input type="text" id="userName" name="listExamUser" onclick="openChooseUser()" />
							<a style="cursor: pointer;"><img src="${ctx}/img/icon/delete.gif" height="14" onclick='$("#userName").val("");$("#listUserID").val("")' alt="编辑"></a></td>
					<td class="td_lable">考试类型：</td><td class="td_value">
						<select name="toExamtype" id=examType style="width: 135px">
			                <option value="">---请选择---</option>
			            </select> 
					</td>
					<td class="td_lable">考试对象：</td><td class="td_value">
						<select name="toExamtarget" id="examObject" style="width: 135px">
			                <option value="">---请选择---</option>
			            </select> 
					</td>
				</tr>
				<tr>
					<td class="td_lable">合格分数：</td><td class="td_value"><input type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
					id="examNormalScore" name="examNormalScore"/></td>
				</tr>
				<tr>
					<td colspan="8" align="center">
						<shiro:hasPermission name="kswhgl:query">
							<input type="button" value="查询" onclick="selectBtn()"/>&nbsp;&nbsp;&nbsp;&nbsp;
						</shiro:hasPermission>
						<shiro:hasPermission name="kscspz:add">
							<input type="button" value="新增" id="insertOpen" onclick="insertRow();"/>&nbsp;&nbsp;&nbsp;&nbsp;
						</shiro:hasPermission>
						<shiro:hasPermission name="kscspz:delete">
							<input type="button" value="删除" id="deleteTest" onclick="deleteRows()"/>&nbsp;&nbsp;&nbsp;&nbsp;
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
