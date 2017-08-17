﻿<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>考题维护</title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css"
	rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerPopupEdit.js"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.validate.min.js"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerRadio.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<script src="<%=path%>/js/ajaxSession.js" type="text/javascript"></script>
<script src="<%=path%>/js/utils.js" type="text/javascript"></script>
<style type="text/css">
div.title_div {
	border-radius: 5px;
	text-align: left;
	margin-top: 5px;
	border: solid 1px #D7D7D7;
	width: 90%;
	height: 30px;
	line-height: 30px;
	vertical-align: middle;
	font-size: 14px;
	font-family: "黑体";
}

input.input_text {
	border-radius: 5px;
	width: 130px;
	height: 18px;
	line-height: 18px;
	padding-left: 3px;
}


input.date_text {
	border-radius: 5px;
	width: 135px;
	height: 18px;
	line-height: 18px;
}

table td {
	height: 30px;
	line-height: 30px;
}

table .search_table td.td_lable {
	text-align: right;
}

table .search_table td.td_value {
	text-align: left;
}

a {
	text-decoration: none;
	color: blue;
}

red {
	color: red;
}
</style>
<script type="text/javascript">
	$(function() {
		$("#div1 input:radio").ligerRadio();

		$("#form1").ligerForm({
			validate : true
		});
		$("#layout1").ligerLayout({
			leftWidth : 200,
			height : '99%'
		});
	});
</script>
<script type="text/javascript">
	var manager;
	var dataFromData = [{ directionFlag: 22, text: '难' }, { directionFlag: 23, text: '中'},{ directionFlag: 24, text: '易'}];
	$(function() {
		var combo2 = $("#fk_codetype_id").ligerComboBox({
             width: 155,
             selectBoxWidth: 200,
             selectBoxHeight: 200, valueField: 'id',treeLeafOnly:false,
             tree: { url: "<%=path%>/controller/quesTions/getDepartmentList.do",
            	 checkbox: false, ajaxType: 'post', idFieldName: 'id', parentIDFieldName:"pid",textFieldName:"text"
            	 }
         });
		 var now=new Date();
         var year=now.getFullYear();
         var month=now.getMonth();
         if(parseInt(month)<10) month = "0"+""+(month+1);
         $("#insert_time_start").val(year+"-"+(month)+"-01 00:00:00");
         $("#insert_time_end").val(new Date().format("yyyy-MM-dd ")+" 23:59:59");
		manager = $("#workpaper_list")
				.ligerGrid(
						{
							columns : [
									{
										display : '操作',
										minWidth : 50,
										align : 'centen',
										isSort : false,
										render : function(rowdata, rowindex,
												value) {
											var h = "";
											h += "<shiro:hasPermission name='kstkgl:update'><a href='javascript:void(0);' onclick='eidt_question(\"update\",\""+rowdata.pkAutoId+"\");'>编辑</a> </shiro:hasPermission>";
											h += "<shiro:hasPermission name='kstkgl:delete'><a href='javascript:void(0);' onclick='deleteQuestion(\""+ rowdata.pkAutoId+ "\");'>删除</a></shiro:hasPermission> ";
											return h;
										}
									}, {
										display : '编号',
										name : 'pkAutoId',
										minWidth : 150,
										align : 'left',
										isSort : false
									}, {
										display : '题目内容',
										name : 'quesContent',
										minWidth : 100,
										align : 'left',
										isSort : false
									}, {
										display : '题目类型',
										name : 'quesType',
										minWidth : 100,
										align : 'left',
										isSort : false,
										 render: function (item)
						                    {
						                        if (parseInt(item.quesType) == 1) return '判断题';
						                        if (parseInt(item.quesType) == 2) return '判断改错题';
						                        if (parseInt(item.quesType) == 3) return '单选题';
						                        if (parseInt(item.quesType) == 4) return '多选题';
						                        if (parseInt(item.quesType) == 5) return '不定项选择';
						                        if (parseInt(item.quesType) == 6) return '填空题';
						                        if (parseInt(item.quesType) == 7) return '问答题';
						                        if (parseInt(item.quesType) == 8) return '案例分析题';
											},
									}, {
										display : '题目难度',
										name : 'quesNandu',
										minWidth : 100,
										align : 'left',
										isSort : false,
					                    render: function (item)
					                    {
					                        if (parseInt(item.quesNandu) == 1) return '难';
					                        if (parseInt(item.quesNandu) == 2) return '中';
					                        if (parseInt(item.quesNandu) == 3) return '易';
										},
									}, {
										display : '所属分类',
										name : 'businessType',
										minWidth : 100,
										align : 'left',
										isSort : false
									}, {
										display : '录入时间',
										name : 'insertTime',
										minWidth : 100,
										align : 'left',
										isSort : false
									}, {
										display : '录入人',
										name : 'fkInsertUserName',
										minWidth : 100,
										align : 'left',
										isSort : false,
										
										
									} ],
							url:'<%=path%>/controller/optionQue/getQuesTions.do?notQueryList='+1,
							width : 'auto',
							height : '100%',
							rownumbers : true
						});
	
		
		$("#query").click(function(){
				f_search();
		});
		
		$(".l-trigger-cancel").click(function(){
			$(".l-selected").removeClass("l-selected");
		});
		
	});
 	function f_search() {
 			var date1=$('#insert_time_start').val();
 			var date2=$('#insert_time_end').val();
 			if(date1==""){
 				 $.ligerDialog.warn("开始时间不能为空");
 				 return;
 			}
 			if(date2==""){
 				 $.ligerDialog.warn("结束时间不能为空");
 				 return;
 			}
 			var startTime=new Date(date1);
 			var endTime=new Date(date2);
 			var date=endTime.getTime()-startTime.getTime();
 			var days=Math.floor(date/(24*3600*1000));	
 			if(days>365){
 				 $.ligerDialog.warn("开始时间与结束时间不能超过一年");
 				 return;
 			}
   		var parmItems=[{ name:"quesContent",value:$('#ques_content').val()},
   		               { name:"fkInsertUserId",value:$('#fkInsertUserId').val()},
   		               { name:"startInsertTime",value:$('#insert_time_start').val()},
   		               { name:"endInsertTime",value:$('#insert_time_end').val()},
   		               { name:"fkCodetypeId",value:$('#fk_codetype_id_val').val()},
   		               { name:"quesType",value:$('#ques_type').val()},
   		               { name:"quesNandu",value:$('#ques_nandu').val()},
   		               { name:"quesStatus",value:$('input:radio[name="isyouxiao"]:checked').val()}];
	   		manager.options.parms=parmItems;
	   		manager.options.usePager =true;
	   		manager.options.newPage=1;
	   		manager.options.page='1';
			manager.options.url = '<%=path%>/controller/quesTions/getQuesTions.do';//必须这么写
			manager.loadData(f_getWhere);
	}
	function f_getWhere() {
		if (!manager)
			return null;
		var clause = function(rowdata, rowindex) {
			var key = $("#bumen").val();
			return rowdata.departmentId.indexOf(key) > -1;
		};
		return clause;
	}
	function collapseAll() {
		manager.collapseAll();
	}
	function expandAll() {
		manager.expandAll();
	}
	function itemclick(item) {
	}
	/*********************
	 * 考题维护
	 *********************/
	function eidt_question(editType,pkAutoId) {
		var titleInfo = "考题信息修改";
		$.ajax({
			  url: "<%=path%>/controller/quesTions/checkEditQuestion.do?PkAutoId="+pkAutoId,
			  type:"post",
			  dataType:"json",
			  async:false,
			  success: function(html){
				  if(html.flag=='yes'){
						$.ligerDialog.open({
							height : 420,
							width : 900,
							title : titleInfo,
							url : '<%=path%>/controller/quesTions/getquesTionbyId.do?pkAutoId='+pkAutoId,
							showMax : false,
							showToggle : false,
							showMin : false,
							isResize : true,
							slide : false
						});
				  }else{
					  $.ligerDialog.warn(html.message);
					  return;
				  }
			   }
			});
	}
	function deleteQuestion(autoId){
		$.ligerDialog.confirm('确认要删除考题编号为['+autoId+']吗？', function (yes) { 
			if(yes){
				$.ajax({
					  url: "<%=path%>/controller/quesTions/getquesTionDelete.do?PkAutoId="+autoId,
					  type:"post",
					  dataType:"json",
					  async:false,
					  success: function(html){
						  if(html.flag=='yes'){
							  $.ligerDialog.success("删除成功",'提示',function(yes){
								  f_search();
								});	
						  }else{
							  $.ligerDialog.warn(html.message);
						  }
					   }
					});
			} 
		});
	}
	function add_question(editType) {
		var titleInfo = "考题信息新增";
		$.ligerDialog.open({
			height : 420,
			width : 900,
			title : titleInfo,
			url : '<%=path%>/controller/quesTions/getquesTionAdd.do',
			showMax : false,
			showToggle : false,
			showMin : false,
			isResize : true,
			slide : false
		});
	}
	/*
		考题模板下载
	*/
	function downTemp(){
	 $.ligerDialog.confirm('是否下载模板？', function (yes) { 
		if(yes){
		  window.location.href= "<%=path%>/controller/quesTions/downTemplate.do";
		}
	  });
	}
	function resets(){
		$('#ques_content').val('');
		$('#fk_insert_user_id').val('');
		$('#insert_time_start').val('');
		$('#insert_time_end').val('');
		$('#fk_codetype_id').val('');
		$('#ques_nandu').val('');
		$('#ques_type').val('');
		$('#ques_status').val('');
		$("#fk_codetype_id").ligerGetComboBoxManager().setText("");
		$("#fk_codetype_id_val").val("");
		$(".l-selected").removeClass("l-selected");
		$("#fkInsertUserName").val("");
		$("#fkInsertUserId").val("");
		 var now=new Date();
         var year=now.getFullYear();
         var month=now.getMonth();
         if(parseInt(month)<10) month = "0"+""+(month+1);
         $("#insert_time_start").val(year+"-"+(month)+"-01 00:00:00");
         $("#insert_time_end").val(new Date().format("yyyy-MM-dd")+" 23:59:59");
	}
	
	function impor_add() {
		var titleInfo = "考题导入";
		$.ligerDialog.open({
			height : 260,
			width : 555,
			title : titleInfo,
			url : "<%=path%>/page/system/option_import.jsp",
			showMax : false,
			showToggle : false,
			showMin : false,
			isResize : true,
			slide : false
		});

	}
</script>
<script type="text/javascript">
	function valid() {
		var form = new liger.get("form1");
		alert(form.valid());
	}
	function f_getWhere() {
		if (!manager)
			return null;
		var clause = function(rowdata, rowindex) {
			var key = $("#bumen").val();
			return rowdata.bumen.indexOf(key) > -1;
		};
		return clause;
	}
	function openChooseUser(){
		$.ligerDialog.open({
               height:420,
               width: 820,
               title : '用户管理',
               url: '/shkd_ks_service/page/system/choose_user.jsp', 
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
	
	 //回车查询
	    function keydown(){
	    	if(isEnter()){
	    		f_search();
	    	}
		}
	
</script>

</head>
<body onkeydown="keydown();">
	<div align="left"
		id="div1">
		<input type="hidden" name="fkInsertUserId" id="fkInsertUserId" style="display: none"/>
		<table class="search_table" cellpadding="3" cellspacing="3" style="margin: 10px 0 0 15px;"
			width="100%">
			<tr>
				<td class="td_lable" style="min-width: 60px;" width="60px">题目内容：</td>
				<td class="td_value"><input  type="text"
					id="ques_content" /></td>
				<td class="td_lable"width="60px">录入人：</td><td class="td_value"><input  id="fkInsertUserName" onclick="openChooseUser()" name="fkInsertUserName" type="text"/>
					<a style="cursor: pointer;"><img src="../../img/icon/delete.gif" height="14" onclick='$("#fkInsertUserName").val("");$("#fkInsertUserId").val("")' alt="编辑"/></a></td>
				<td class="td_lable" width="85px">录入开始时间:</td>
				<td class="td_value"><input class="Wdate date_text" type="text"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'insert_time_end\')||\'2120-10-01\'}'})"
					 id="insert_time_start" /></td>
				<td class="td_lable" width="90px">录入结束时间：</td>
				<td class="td_value"><input class="Wdate date_text" type="text"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'insert_time_start\')}',maxDate:'2120-10-01'})"
					 id="insert_time_end" /></td>
			</tr>
			<tr>
				<td class="td_lable">业务分类：</td>
				<td class="td_value"><input name="fk_codetype_id"
					id="fk_codetype_id" style="width: 135px;"/></td>
				<td class="td_lable">考题难度：</td>
				<td class="td_value"><select name="ques_nandu" id="ques_nandu"
					>
						<c:forEach items="${quesNanduInfo}" var="mandu">
							<option value="${mandu.itemFlag}">${mandu.name}</option>
						</c:forEach>
				</select></td>
				<td class="td_lable">考题类别：</td>
				<td class="td_value"><select name="ques_type" id="ques_type"
					>
						<c:forEach items="${quesTypeInfo}" var="info">
							<option value="${info.itemFlag}">${info.name}</option>
						</c:forEach>
				</select></td>
				<!-- <td class="td_lable">是否有效：</td>
				<td class="td_value">
					<input type="radio" value="1" name="isyouxiao" id="1"/>是&nbsp;&nbsp;&nbsp;
					<input type="radio" value="0" name="isyouxiao" id="0" />否</td> -->
			</tr>

			<tr style="padding-top: 20px;">
				<td colspan="8" align="center">
				<shiro:hasPermission name="kstkgl:query">
				<input type="button" id="query"
					value="查询" />&nbsp;&nbsp;&nbsp;&nbsp;</shiro:hasPermission><shiro:hasPermission name="kstkgl:add"><input type="button"
					value="新增" id="insertOpen" onclick="add_question('add')" />&nbsp;&nbsp;&nbsp;&nbsp;</shiro:hasPermission>
					<input type="button" value="重置" onclick="resets()" />&nbsp;&nbsp;&nbsp;&nbsp;
					<shiro:hasPermission name="kstkgl:export"><input type="button" value="考题模板下载" id="insertQue"
					onclick="downTemp()" style="width: 110px;" />&nbsp;&nbsp;&nbsp;&nbsp;</shiro:hasPermission>
					<shiro:hasPermission name="kstkgl:import"><input type="button" value="考题导入" onclick="impor_add()"
					id="insertQue" /></shiro:hasPermission>
					</td>
			</tr>
		</table>
	</div>
	<div id="workpaper_list" style="margin-top: 2px;"></div>
	<div id="imporque"
		style="width: 98%; margin: 5px; padding-right: 20px;" title="sss">

		<table class="search_table" cellpadding="3" cellspacing="3"
			width="100%">
			<tr>
				<td><input type="file" name="浏览" value="浏览"/></td>
				<td><input style="margin-left: 100px;" type="button" name=""
					value="上传"/></td>
			</tr>


		</table>
	</div>
</body>
</html>

