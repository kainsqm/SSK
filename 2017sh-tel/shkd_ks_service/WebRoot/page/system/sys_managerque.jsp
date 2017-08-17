<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>试卷管理</title>
 	<link href="../../lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="../../lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<link href="../../css/public.css" rel="stylesheet" type="text/css" />
    <script src="../../lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>  
    <script src="../../lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script> 
    <script src="../../lib/ligerUI/js/plugins/ligerPopupEdit.js"></script>
    <script src="../../js/ajaxSession.js" type="text/javascript"></script>
    <script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>

	<SCRIPT type="text/javascript">
		
		
	/*	$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			$("#selectAll").bind("click", selectAll);
		});*/
		//-->
	</SCRIPT>
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

		$(function ()
		{
		  	$("#query").click(function(){
				f_search();
		});
		});
	</script>
	<script type="text/javascript"> 
		var manager;
        $(function ()
        {
            manager = $("#workpaper_list").ligerGrid({
            	checkbox: true,
                columns: [
                { display: '操作', isSort: false, minWidth : 180, align: 'center' , isSort: false,render: function (rowdata, rowindex, value)
						{
							var h = "";
							h += "<shiro:hasPermission name='kssjwh:update'><a href='javascript:void(0);' onclick='checkExampaper(\""+rowdata.pkAutoId+"\",\""+rowdata.examPaperFlag+"\");'>修改</a> </shiro:hasPermission>";
							h += "<shiro:hasPermission name='kssjwh:export'><a href='javascript:void(0);' onclick='exportShijuan(\""+rowdata.pkAutoId+"\");'>导出</a>  </shiro:hasPermission> ";
							h += "<shiro:hasPermission name='kssjwh:delete'><a href='javascript:void(0);' onclick='deleteExamPaper(\""+rowdata.pkAutoId+"\");'>删除</a>  </shiro:hasPermission> ";
							return h;
						}
					},	
					{ display: '试卷编号', name: 'pkAutoId', minWidth: 100, align: 'left', isSort: false },
					{ display: '试卷名称', name: 'examPaperName', minWidth: 100, align: 'left' , isSort: false},
					{ display: '试卷描述', name: 'examPaperRemark', minWidth: 100, align: 'left', isSort: false },
					{ display: '生成日期', name:'insertTime', minWidth: 100, align: 'left', isSort: false },
					{ display: '操作人', name:'userName', minWidth: 100, align: 'left', isSort: false},					
					{ display: '试卷类型', name: 'examPaperType', minWidth: 100, align: 'left', isSort: false,
						editor: { type: 'select',  valueField: 'examPaperType'},
						 render: function (item)
		                    {
		                        if (parseInt(item.examPaperType) == 15) return '日考';
		                        if (parseInt(item.examPaperType) == 16) return '月考';
							},
					},

                ],width: '100%', height: '99%',rownumbers :true,
                //url:'<%=path%>/controller/examManager/queryExampapers.do?notQueryList='+1
                url:'<%=path%>/controller/examManager/queryExampapers.do'
            }
            );
          //collapseAll();
    		$("#pageloading").hide();
    		$(".l-trigger-cancel").click(function(){
    			$(".l-selected").removeClass("l-selected");
    		});
        });
        
      	function f_search() {
	   		var parmItems=[{ name:"pkAutoId",value:$('#pkAutoId').val()},
	   		               { name:"examPaperName",value:$('#examPaperName').val()},
	   		               { name:"fkInsertUserId",value:$('#fkInsertUserId').val()},
	   		               { name:"examPaperType",value:$('#examPaperType').val()},
	   		               { name:"examPaperRemark",value:$('#examPaperRemark').val()}];
		   		manager.options.parms=parmItems;
		   		manager.options.usePager =true;
		   		manager.options.newPage=1;
		   		manager.options.page='1';
				manager.options.url = '<%=path%>/controller/examManager/queryExampapers.do';//必须这么写
			manager.loadData(f_getWhere);
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
		
		function deleteExamPaper(pkAutoId){
			$.ligerDialog.confirm('确认要删除考题编号为['+pkAutoId+']吗？', function (yes) { 
				if(yes){
					$.ajax({
						  url: "<%=path%>/controller/examManager/deleteExamPaper.do?pkAutoIds="+pkAutoId,
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
		function resets(){
			$('#pkAutoId').val('');
			$('#examPaperName').val('');
			$('#fkInsertUserName').val('');
			$('#examPaperType').val('3');
			$('#fkInsertUserId').val('');
			$('#examPaperRemark').val('');
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
		function exportShijuan(pkAutoId){
			$.ligerDialog.confirm('是否导出该试卷', function (yes){ 
				if(yes){
					var exampaperId=pkAutoId;
					window.location.href= '<%=path%>/controller/examManager/downLoadExamPaperFile.do?pkAutoIds='+exampaperId;
					}
				});
		}
		function checkExampaper(pkAutoId,examPaperFlag){
			if(examPaperFlag=="2"){
				 $.ligerDialog.warn("该套试卷为随机模板试卷，此处不可以修改！");
				 return;
			}else{
				$.ajax({
					  url: "<%=path%>/controller/examManager/checkExampaperExaminee.do?pkAutoIds="+pkAutoId,
					  type:"post",
					  dataType:"json",
					  async:false,
					  success: function(html){
						  if(html==0){
							  window.location.href='<%=path%>/controller/examManager/queryQuesByExamIds.do?exampaperId='+pkAutoId;
						  }else{
							 $.ligerDialog.warn("该试卷已经用于考试不能修改");
						  }
					   }
					});
			}
		}

		function impotShijuan(){
			$.ligerDialog.open({
				height:260,
				width: 600,
				title : '批量导入试卷',
				url:"<%=path%>/page/system/examManager_import.jsp",
				showMax: false,
				showToggle: false,
				showMin: false,
				isResize: true,
				slide: false
			});
		}
    </script> 
<script type="text/javascript">
   function valid()
     {
         var form = new liger.get("form1");
         alert(form.valid());
     }

   function f_getWhere()
	{
		if (!manager)
			return null;
		var clause = function(rowdata, rowindex) {
			var key = $("#bumen").val();
			return rowdata.departmentId.indexOf(key) > -1;
		};
		return clause;
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
	<div class="title_div"
		style="width:100%; margin-top:0px; display:none;">
		<label style="margin-left:15px;">查询条件</label>
	</div>
	<div align="left"
		style="margin-top:10px; margin-left:5px; padding-left:10px;">
		<table class="search_table" cellpadding="3" cellspacing="3"
			width="100%">
				<tr>
					<td class="td_lable" width="80px">试卷编号：</td><td class="td_value">
					<input type="hidden" id="fkInsertUserId" name="fkInsertUserId" />
					<input style="width:135px;" id="pkAutoId" name="pkAutoId" type="text"/></td>
					<td class="td_lable" width="80px">试卷名称：</td><td class="td_value"><input style="width: 135px;" id="examPaperName"  name="examPaperName" type="text"/></td>
					<td class="td_lable"width="90px">试卷生成人：</td><td class="td_value"><input style="width: 135px;" id="fkInsertUserName" onclick="openChooseUser()" name="fkInsertUserName" type="text"/>
					<a style="cursor: pointer;"><img src="../../img/icon/delete.gif" height="14" onclick='$("#fkInsertUserName").val("");$("#fkInsertUserId").val("")' alt="编辑"/></a></td>
					<td class="td_lable" width="80px">试卷类型：</td><td class="td_value">
						<select name="examPaperType" id="examPaperType" style="width: 135px;" >
							<c:forEach items="${examTypeInfo}" var="typeInfo">
								<option value="${typeInfo.pkAutoId}">${typeInfo.name}</option>
							</c:forEach>
			            </select> 
					</td>
					</tr>
					<tr>
					
						<td class="td_lable">试卷描述：</td><td class="td_value"><input style="width: 135px;" id="examPaperRemark" name="examPaperRemark" type="text"/></td>
				</tr>
				<tr >
					<td colspan="8" align="center">
						<shiro:hasPermission name="kssjwh:query"><input type="button" value="查询" id="query" />&nbsp;&nbsp;&nbsp;&nbsp;</shiro:hasPermission>
						<input type="button" value="重置" onclick="resets()"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<shiro:hasPermission name="kssjwh:import"><input type="button" value="试卷导入" id="impor" onclick="impotShijuan()" />&nbsp;&nbsp;&nbsp;&nbsp;</shiro:hasPermission>
					</td>
				</tr>
			</table>			
		</div>
		<div id="workpaper_list"></div>
<div id="addusers" style="width:98%; margin:5px; padding-right:20px;display:none;" title="sss">

</div>
</body>
</html>

