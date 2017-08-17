<%@ page language="java" contentType="text/html;charset=UTF-8"
	import="java.util.*,cn.sh.ideal.model.tQuestions"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>模板试卷</title>
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
.reset{ 
    background: -webkit-gradient(linear, 0% 0%, 0% 100%,from(#01aacf), to(#0165a2)) !important;
    color: #FFFFFF !important;
    border: 1px solid #0297bf !important;
    padding: 0 15px;
    border-radius: 3px;
    width: initial !important;
    height: 25px !important;
}
/*#workpaper_list{width: 100% !important;	}*/
	</style>
	<script type="text/javascript"> 
	  var dataFromData = [{ directionFlag: 1, text: '合格' }, { directionFlag: 0, text: '不合格'}];
			var manager;
	        $(function ()
	        {
	            manager = $("#exam_list").ligerGrid({
	            	checkbox: true,
	                columns: [
	                { display: '操作', isSort: false, minWidth : 180, align: 'center' , isSort: false,render: function (rowdata, rowindex, value)
							{
								var h = "";
								h += "<shiro:hasPermission name='ksmbsj:delete'><a href='javascript:void(0);' onclick='deleteRandomExam(\""+rowdata.pkAutoId+"\");'>删除</a></shiro:hasPermission> ";
								return h;
							}
						},	
						{ display: '试卷编号', name: 'pkAutoId', minWidth: 100, align: 'left', isSort: false },
						{ display: '试卷名称', name: 'examPaperName', minWidth: 100, align: 'left' , isSort: false},
						{ display: '试卷描述', name: 'examPaperRemark', minWidth: 100, align: 'left', isSort: false },
						{ display: '生成日期', name:'insertTime', minWidth: 100, align: 'left', isSort: false },
						{ display: '操作人', name:'userName', minWidth: 100, align: 'left', isSort: false},					
						{ display: '试卷类型', name: 'examPaperType', minWidth: 100, align: 'left', isSort: false,
							editor: { type: 'select', data: dataFromData, valueField: 'examPaperType'},
							 render: function (item)
			                    {
			                        if (parseInt(item.examPaperType) == 15) return '日考';
			                        if (parseInt(item.examPaperType) == 16) return '月考';
								}
						}
					
	                ],
	               // url:'<%=path%>/controller/exampaper/getExampaperRandom.do?notQueryList='+1,
	                 url:'<%=path%>/controller/exampaper/getExampaperRandom.do?',
	                width: 'auto', 
	                height: '100%',
	                rownumbers :true
	            }
	            );
	          //collapseAll();
	    		$("#pageloading").hide();
	    		$(".l-trigger-cancel").click(function(){
	    			$(".l-selected").removeClass("l-selected");
	    		});
	    		
	    		
	    	  $("#query").click(function(){
				    f_search();
		          });
	        });
	   	
	   	function f_search() {
	   		var parmItems=[{ name:"pkAutoId",value:$('#pkAutoId').val()},
	   		               { name:"examPaperName",value:$('#examPaperName').val()},
	   		               { name:"fkInsertUserId",value:$('#fkInsertUserId').val()},
	   		               { name:"examPaperStatus",value:$('#examPaperStatus').val()},
	   		               { name:"examPaperType",value:$('#examPaperType').val()},
	   		               { name:"examPaperRemark",value:$('#examPaperRemark').val()}];
		   		manager.options.parms=parmItems;
		   		manager.options.usePager =true;
		   		manager.options.newPage=1;
		   		manager.options.page='1';
				manager.options.url = '<%=path%>/controller/exampaper/getExampaperRandom.do';//必须这么写
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
		
        function collapseAll()
        {
            manager.collapseAll();
        }
        function expandAll()
        {
            manager.expandAll();
        }
		function itemclick(item)
        {
            alert(item.text);
        }
		function resets(){
			$('#pkAutoId').val('');
			$('#examPaperName').val('');
			$('#fkInsertUserName').val('');
			$('#examPaperStatus').val('3');
			$('#examPaperType').val('3');
			$('#fkInsertUserId').val('');
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
		
		function deleteRandomExam(exautoId){
			$.ligerDialog.confirm('确认要删除编号为['+exautoId+']的考卷吗？', function (yes) { 
				if(yes){
					$.ajax({
						  url: "<%=path%>/controller/exampaperRandom/deleletExampaper.do?PkAutoId="
													+ exautoId,
											type : "post",
											dataType : "json",
											async : false,
											success : function(html) {
												if (html.flag == 'yes') {
													$.ligerDialog.success(
															"删除成功", '提示',
															function(yes) {
																f_search();
															});
												} else {
													$.ligerDialog
															.warn(html.message);
												}
											}
										});
							}
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
<body onkeydown="keydown();" > 
	<div >
	    <div align="left">
		<table class="search_table" cellpadding="3" cellspacing="3"
				style="margin: 10px 0 0 15px;" width="100%">
			<tr>
				<td class="td_lable" width="80px">试卷编号：</td>
				<td class="td_value"><input style="width:135px;" id="pkAutoId"
					name="pkAutoId" type="text" />
					<input type="hidden" name="fkInsertUserId" id="fkInsertUserId"/>
				</td>
				<td class="td_lable" width="80px">试卷名称：</td>
				<td class="td_value"><input style="width: 135px;"
					id="examPaperName" name="examPaperName" type="text" />
				</td>
				<td class="td_lable" width="90px">试卷生成人：</td>
				<td class="td_value"><input style="width: 135px;"
					id="fkInsertUserName" onclick="openChooseUser()"
					name="fkInsertUserName" type="text" /> <a style="cursor: pointer;"><img
						src="../../img/icon/delete.gif" height="14"
						onclick='$("#fkInsertUserName").val("");$("#fkInsertUserId").val("")'
						alt="编辑"/>
				</a>
				</td>
					<td class="td_lable" width="80px" >试卷类型：</td>
				<td class="td_value"><select name="examPaperType"
					id="examPaperType" style="width: 135px;">
						<c:forEach items="${examTypeInfo}" var="typeInfo">
							<option value="${typeInfo.itemFlag}">${typeInfo.name}</option>
						</c:forEach>
				</select></td>
			</tr>
	
			<tr>
				<td colspan="8" align="center"><shiro:hasPermission
						name="ksmbsj:query">
						<input type="button" value="查询" id="query" />&nbsp;&nbsp;&nbsp;&nbsp;</shiro:hasPermission>
					<input type="button" value="重置" onclick="resets()" />&nbsp;&nbsp;&nbsp;&nbsp;
					<shiro:hasPermission name="ksmbsj:cqkt">
						<input type="button" value="抽取考题" id="insertOpen"
							onclick="location.href='<%=path%>/controller/exampaper/toShowRandomPage.do' " />&nbsp;&nbsp;&nbsp;&nbsp;</shiro:hasPermission>
				</td>
			</tr>
		</table>
	</div>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="exam_list" style="margin-top:6px;"></div>
		</div>
</body>
</html>
