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
				maingrid_gd= $("#notice_list").ligerGrid({
					columns: [                
					{ display: '操作', isSort: false, width : 100, render: function (rowdata, rowindex, value)
					{
						var h= "<a href='<%=path%>/controller/notice/toNoticeInfo.do?noticeId="+rowdata.pkAutoId+"' target='_blank' );'>查看</a>";
						return h;
					}
				},
				{ display: '公告标题', name: 'title', align: 'left', minWidth :130,isSort:false,render: function (rowdata, rowindex, value)
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
				{ display: '发布时间', name: 'releaseDate', align: 'left', width :140,isSort:false},
				{ display: '创建人', name: 'insertUserName', align: 'left', width :85,isSort:false}
				], height:'99%', 
				  width:"auto",  pageSize:10 ,rownumbers :true,
				  url:'<%=path%>/controller/notice/getNoticelistByMain.do',
			});
				$("#pageloading").hide(); 
			});
		
		 
	function f_search() {
   		var parmItems=[{ name:"title",value:$('#title').val()}];
	   		maingrid_gd.options.parms=parmItems;
	   		maingrid_gd.options.usePager =true;
	   		maingrid_gd.options.newPage=1;
	   		maingrid_gd.options.page='1';
			maingrid_gd.options.url = '<%=path%>/controller/notice/getNoticelistByMain.do';//必须这么写
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
					<input type="button" id="query"
					value="查询" onclick="f_search()"/>&nbsp;&nbsp;&nbsp;&nbsp; 
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
