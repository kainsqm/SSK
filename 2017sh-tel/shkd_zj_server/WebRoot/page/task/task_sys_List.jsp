<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/shiro.tld" prefix="shiro"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>客调项目管理系统</title>
		<!-- 工作任务派发页面 -->
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/core/base.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js"
			type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
		<script src="<%=path%>/js/util.js" type="text/javascript"></script>
		<style type="text/css">
div.title_div {
	border-radius: 5px;
	text-align: left;
	margin-top: 5px;
	background-image: url(<%=path%>/img/login/title_bg.jpg);
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

input[type='button'] {
	border: #d3d3d3 1px solid;
	width: 80px;
	height: 25px;
	cursor: pointer;
}

input.date_text {
	border-radius: 5px;
	width: 140px;
	height: 18px;
	line-height: 18px;
}

table#search_table td {
	height: 30px;
	line-height: 30px;
}

table#search_table td.td_lable {
	text-align: right;
}

table#search_table td.td_value {
	text-align: left;
}
</style>
		<script type="text/javascript"> 
      var grid=null;
		$(function () {
		var d = new Date();	
			var year = d.getFullYear();
			var month = d.getMonth() + 1; // 记得当前月是要+1的
			if(parseInt(month)<10) month = "0"+""+(month); 
			var dt = d.getDate();
			if(parseInt(dt)<10) dt = "0"+""+(dt);
			today = year + "-" + month + "-01";
			toendday= year + "-" + month + "-" +dt;
			$("#CREATE_START_TIME").val(today);
			$("#CREATE_END_TIME").val(toendday);
           grid = $("#task_sys_list").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false, minWidth : 180, render: function (rowdata, rowindex, value)
	                {
	                   var h = "";
	                    if(rowdata.isPublish!="0"){
	                        h += "<a href='javascript:void(0);' onclick='showEidtTask(\"show\",\""+rowdata.taskId+"\");'>查看</a> ";
	                    }else{
	                        h += "<shiro:hasPermission name='taskrecord:upd'> <a href='javascript:void(0);' onclick='updateTask(\"update\",\""+rowdata.taskId+"\");'>修改</a> </shiro:hasPermission>	";
	                    }
	                   
						h += "<shiro:hasPermission name='taskrecord:del'> <a href='javascript:void(0);' onclick='delTask(\""+rowdata.taskId+"\",\""+rowdata.taskStatus+"\");'>删除</a> </shiro:hasPermission>";
						 if(rowdata.taskStatus=="0" || rowdata.taskStatus=="1"){
	                        h += "";
	                    }else{
	                        h += "<a href='javascript:void(0);' onclick='showTaskWcqk(\""+rowdata.taskId+"\",\""+rowdata.taskName+"\");'>完成情况</a> ";
	                    }
						
	                    return h;
	                }
                },
				{ display: '任务编号', name: 'taskId', align: 'left', width :130,isSort:false},
                { display: '任务名称', name: 'taskName', align: 'left', width :140,isSort:false},
				{ display: '质检员工号', name: 'qcUserWorkId', align: 'left', minWidth :200,isSort:false},
				{ display: '任务状态', name: 'taskStatus', align: 'left', minWidth :80,isSort:false,render : function(rowdata, rowindex, value) {
						var result="";
						switch(value){
						   case "0":
						    result="未发布";
						   break;
						   case "1":
						    result="待执行";
						   break;
						   case "2":
						    result="执行中";
						   break;
						   case "3":
						    result="已完成";
						   break;
						   case "4":
						    result="未完成";
						   break;
						    case "5":
						    result="强制关闭";
						   break;
						   default:
						     result="其他";
						   break;
						   
						}	
						return result;
						}},
				{ display: '创建时间', name: 'createTime', align: 'left', minWidth :80,isSort:false},
				{ display: '创建人工号', name: 'createUserWorkId', align: 'left', minWidth :100,isSort:false}
                ], url:'<%=path%>/controller/task/getTaskDistribut.do',
                 parms:[{name:"CREATE_START_TIME",value:$("#CREATE_START_TIME").val()},
		               { name:"CREATE_END_TIME",value:$("#CREATE_END_TIME").val()}
		               ],
                sortName: 'TASK_ID',height:'100%', width:"auto", pageSize:10 ,rownumbers :true,usePager: true
            });
            $("#pageloading").hide(); 
        });
        
        function f_search(){
              var parmItems=[{ name:"TASK_ID",value:$("#TASK_ID").val()},
		               { name:"TASK_NAME",value:$("#TASK_NAME").val()},
		               { name:"QC_USER_WORKID",value:$("#QC_USER_WORKID").val()},
		               { name:"TASK_STATUS",value:$("#TASK_STATUS").val()},
		               { name:"CREATE_START_TIME",value:$("#CREATE_START_TIME").val()},
		               { name:"CREATE_END_TIME",value:$("#CREATE_END_TIME").val()}
		        ];
	        grid.options.parms=parmItems;
			grid.options.page=1;
			grid.options.newPage=1;
            grid.options.data = $.extend(true, {}, parmItems);//必须这么写
		    grid.loadData(f_getWhere());
        }

       function f_getWhere()
        {
            if (!grid) return null;
            var clause = function (rowdata, rowindex)
            {
                var key = $("#txtKey").val();
                return rowdata.CustomerID.indexOf(key) > -1;
            };
            return clause; 
        }

        /********************************
		 * 打开任务编辑页面
		 * @editType 编辑类型 新增：add 修改：update
		 * @taskID 任务ID
		 ********************************/
		function showEidtTask(type,taskId){
			//window.location.href="edit_taskInfo.jsp?type="+editType;
			//window.location.href="<%=path%>/controller/task/toTaskInfo.do?type="+editType;
			if(type=="add"){
			   //window.location.href="edit_taskInfo.jsp";
			   window.location.href="<%=path%>/controller/task/toTaskInfo.do";
			}else{
				window.location.href="<%=path%>/controller/task/updateTaskShow.do?taskId=" + taskId+"&type="+type ;
		    }
		}
		/********************************
		 * 打开任务完成情况页面
		 * @taskID 任务ID
		 ********************************/
		function showTaskWcqk(taskID,taskName){
			window.location.href="<%=path%>/page/task/task_wcqk_sys.jsp?taskId="+taskID+"&taskName="+encodeURI(encodeURI(taskName));
		}
			/**
		* 删除任务
		*
		*/
		function delTask(taskId,taskStatus){
			$.ligerDialog.confirm('确认删除吗？', function (yes){
			if(yes){
				 $.ajax({
						url:'<%=path%>/controller/task/deleteTask.do',
						type:'post',
						data:{taskId:taskId,taskStatus:taskStatus},
						error: function(request) {
							$.ligerDialog.error('删除失败，请稍候再试!');
						},
						success: function(data) {
							var res=eval("("+data+")");
							if(res.status=='0'){
								$.ligerDialog.warn('删除失败');
							}else if(res.status=='1'){
								$.ligerDialog.success('删除成功！');	
								f_search();
							}
						}
					});
				//window.location.href="<%=path%>/controller/task/deleteTask.do?taskId=" + taskId + "&taskStatus=" + taskStatus;
			}
		   });
		}
		
			/**
	* 修改任务
	*
	*/
	function updateTask(type,taskId){//taskId：任务名称  taskName：任务名称
				window.location.href="<%=path%>/controller/task/updateTaskShow.do?taskId=" + taskId+"&type="+type ;
			}
	/**
	* 重置
	*/
	function chongzhi(){
	    $("#TASK_ID").val("");
        $("#TASK_NAME").val("");
		$("#QC_USER_WORKID").val("");
		$("#TASK_STATUS").val("");
		var d = new Date();	
			var year = d.getFullYear();
			var month = d.getMonth() + 1; // 记得当前月是要+1的
			var dt = d.getDate();
			var today = year + "-" + month + "-01";
			var toendday = year + "-" + month + "-" +dt;
			$("#CREATE_START_TIME").val(today);
			$("#CREATE_END_TIME").val(toendday);
	}
	function input_shuziyinwen(obj){
				obj.value=obj.value.replace(/[\W]/g,'');
			}
    </script>
	</head>
	<!-- 
<body style="margin:0 auto; background-color:#FFFFFF; padding:5px;">
 -->
	<body>
		<div style="">
			<div class="title_div"
				style="width: 100%; margin-top: 0px; display: none;">
				<label style="margin-left: 15px;">
					查询条件
				</label>
			</div>
			<div align="left"
				style="margin-top: 10px; margin-left: 5px; padding-left: 10px;">
				<form id="formserch" method="post">
					<table id="search_table" cellpadding="3" cellspacing="3"
						width="100%">
						<td class="td_lable">
							任务编号：
						</td>
						<td class="td_value">
							<input id="TASK_ID" type="text" maxlength="50"  onkeyup="input_shuziyinwen(this);" />
						</td>
						<td class="td_lable">
							任务名称：
						</td>
						<td class="td_value">
							<input id="TASK_NAME" type="text" maxlength="50"  onkeyup="noSpecial(this)" />
						</td>
						<td class="td_lable">
							质检员工号：
						</td>
						<td class="td_value">
							<input id="QC_USER_WORKID" type="text" maxlength="50"  onkeyup="input_shuziyinwen(this);" />
						</td>
						<td class="td_lable">
							任务状态：
						</td>
						<td class="td_value">
							<select id="TASK_STATUS">
							    <option value="">
									--全部--
								</option>
								<option value="0">
									未发布
								</option>
								<option value="1">
									待执行
								</option>
								<option value="2">
									执行中
								</option>
								<option value="3">
									已完成
								</option>
								<option value="4">
									未完成
								</option>
								<option value="5">
									强制关闭
								</option>
							</select>
						</td>
						</tr>
						<tr>
							<td class="td_lable">
								创建开始时间：
							</td>
							<td class="td_value">
								<input id="CREATE_START_TIME" class="Wdate date_text"
									type="text"
									onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'CREATE_END_TIME\');}',dateFmt:'yyyy-MM-dd'})"  />
							</td>
							<td class="td_lable">
								创建结束时间：
							</td>
							<td class="td_value">
								<input id="CREATE_END_TIME" class="Wdate date_text" type="text"
									onfocus="WdatePicker({minDate:'#F{$dp.$D(\'CREATE_START_TIME\');}',dateFmt:'yyyy-MM-dd'})"/>
							</td>
							<td class="td_lable">
								&nbsp;
							</td>
							<td class="td_lable">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan="8" align="center">
							<shiro:hasPermission name='taskrecord:query'>
								<input type="button" value="查询" onclick="f_search()" />
							</shiro:hasPermission>	
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" value="重置" onclick="chongzhi()" />
								&nbsp;&nbsp;&nbsp;&nbsp;
								<shiro:hasPermission name='taskrecord:add'>
								<input type="button" value="新增"
									onclick="showEidtTask('add','');" />
								</shiro:hasPermission>	
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="l-loading" style="display: block" id="pageloading"></div>
			<div id="task_sys_list" style="margin-top: 10px;"></div>
		</div>
	</body>
</html>