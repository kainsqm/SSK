<%@ page language="java" contentType="text/html;charset=UTF-8"%>  
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<%
  String path = request.getContextPath(); 

%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>客调项目管理系统</title>
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=path%>/js/util.js" type="text/javascript"></script>
	<style type="text/css">
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(<%=path%>/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input.date_text{border-radius:5px; width:140px; height:18px; line-height:18px;}
		
		table#search_table td{ height:30px; line-height:30px;}
		table#search_table td.td_lable{ text-align:right;}
		table#search_table td.td_value{ text-align:left;}
		div.l-panel-topbar{ height:30px !important; }
	</style>
	<script type="text/javascript"> 
	        var maingrid_gd;
			$(function () {
				loadData();
			});
			
			function loadData(){
			   maingrid_gd= $("#notice_list").ligerGrid({
					columns: [                
					{ display: '操作', isSort: false, width : 100, render: function (rowdata, rowindex, value)
					{
						var h = "";
						if(rowdata.status=='已保存'){
						   h += "<shiro:hasPermission name='notice:update'><a href='javascript:void(0);' onclick='eidt_notice(\"update\",\""+rowdata.pkAutoId+"\");'>修改</a></shiro:hasPermission> ";
						}
						
						h += "<shiro:hasPermission name='notice:delete'><a href='javascript:void(0);' onclick='deletenotice(\""+rowdata.title+"\",\""+rowdata.pkAutoId+"\");'>删除</a></shiro:hasPermission> ";
						return h;
					}
				},
				{ display: '公告标题', name: 'title', align: 'left',  minWidth :140,isSort:false,render: function (rowdata, rowindex, value)
	                {
	                    var h = "<span class='span' title='"+value+"' style='overflow: hidden;text-overflow: hidden;display: block;text-overflow: ellipsis; white-space: nowrap;'>"+value+"<span>";
	                    return h;
	                }
				},
				{ display: '公告内容', name: 'content', align: 'left', minWidth :200,isSort:false,render: function (rowdata, rowindex, value)
	                {
	                    var h = "<span class='span' title='"+value+"' style='overflow: hidden;text-overflow: hidden;display: block;text-overflow: ellipsis; white-space: nowrap;'>"+value+"<span>";
	                    return h;
	                }
				},
				{ display: '发布时间', name: 'releaseDate', align: 'left', width :110,isSort:false},
				{ display: '状态', name: 'status', align: 'left', width :75,isSort:false},
				{ display: '创建人', name: 'insertUserName', align: 'left', width :85,isSort:false},
				{ display: '创建时间', name: 'insertTime', align: 'left', width :92,isSort:false}
				], height:'100%', 
				  width:"auto",  pageSize:10 ,rownumbers :true,
				  url:'<%=path%>/controller/notice/getNoticelist.do',
			});
				$("#pageloading").hide(); 
			}
			
		/*********************
		 * 删除公告信息
		 *********************/
		 function deletenotice(noticeTitle,id){
		 	$.ligerDialog.confirm('确认要删除公告['+noticeTitle+']吗？', function (yes) { 
		 		if(yes){
		 			$.ajax({
								url:"<%=path%>/controller/notice/deleteNotice.do",
								type:"post",
								data:{
										pkAutoId:id
										},
								 dataType:"json",
								 success:function(result){
									 $.ligerDialog.success('删除成功!','提示',function(yes){
								         f_search();
						          	});
								 },
								error:function(result,e){
									 $.ligerDialog.error('删除失败');
								}
						});
		 		} 
		 	});
		 }
		/*********************
		 * 公告信息编辑
		 *********************/
		 function eidt_notice(editType,id){
		 	var titleInfo="弹出窗口";
		 	if(editType=="add"){$.ligerDialog.open({
		 		height:365,
		 		width: 750,
		 		title : "公告信息新增",
		 		url:'<%=path%>/controller/notice/toNoticeAdd.do', 
		 		showMax: false,
		 		showToggle: false,
		 		showMin: false,
		 		isResize: true,
		 		slide: false
		 	});}
		 	if(editType=="update"){$.ligerDialog.open({
		 		height:365,
		 		width: 750,
		 		title : "公告信息修改",
		 		url:'<%=path%>/controller/notice/toNoticeUpdate.do?noticeId='+id, 
		 		showMax: false,
		 		showToggle: false,
		 		showMin: false,
		 		isResize: true,
		 		slide: false
		 	});}
		 	
		 }
		 
		 
	function f_search() {
   		var parmItems=[{ name:"title",value:$('#title').val()},
   		               { name:"status",value:$('#status').val()}];
	   		maingrid_gd.options.parms=parmItems;
	   		maingrid_gd.options.usePager =true;
	   		maingrid_gd.options.newPage=1;
	   		
	   		maingrid_gd.options.height="100%";
	   		maingrid_gd.options.page='1';
			maingrid_gd.options.url = '<%=path%>/controller/notice/getNoticelist.do';//必须这么写
		    maingrid_gd.loadData(f_getWhere);
	}
	function f_getWhere() {
		if (!manager)
			return null;
		var clause = function(rowdata, rowindex) {
			var key = $("#title").val();
			return rowdata.departmentId.indexOf(key) > -1;
		};
		return clause;
	}
	
	
	function resets(){
	    $("#title").val("");
	    $("#status").val("");
	}
	
		</script> 
	</head>
<body> 
	<div style="">
		<div align="left"
		style="margin-top: 4px; margin-left: 5px; padding-left: 10px;"
		id="div1">
		<table class="search_table" cellpadding="3" cellspacing="3"
			width="100%">
			<tr>
			  <td>
				公告标题：<input  type="text" id="title" />&nbsp;&nbsp;&nbsp;&nbsp;
				状态：
				   <select id="status">
					    <option value="">--请选择--</option>
					    <option value="已发布">已发布</option>
					    <option value="已保存">已保存</option>
				   </select>&nbsp;&nbsp;&nbsp;&nbsp;
					<shiro:hasPermission name='notice:query'><input type="button" id="query"
					value="查询" onclick="f_search()"/></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp;<shiro:hasPermission name='notice:add'> <input type="button"
					value="新增" id="insertOpen" onclick="eidt_notice('add','0')" /></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="重置" onclick="resets()" />
				</td>
			</tr>
		</table>
	</div>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="notice_list" style="margin-top:10px;"></div>
	</div>
</body>
</html>
