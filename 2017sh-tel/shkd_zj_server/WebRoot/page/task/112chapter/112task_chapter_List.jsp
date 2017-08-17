<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<title>客调项目管理系统</title>
	<link href="${ctx}/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
	<script src="${ctx}/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script src="${ctx}/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
	<script src="${ctx}/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
	<script src="${ctx}/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
	<script src="${ctx}/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
	html, body {
		height: 100%;
		width: 100%;
	}
	
	div.title_div {
		border-radius: 5px;
		text-align: left;
		margin-top: 5px;
		background-image: url(../../img/login/title_bg.jpg);
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
		width: 135px;
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
	.search_ul li{width:25%;float:left;line-height:30px;}
	.search_ul li label{display:inline-block;width:107px;text-align: right;}
</style>
<script type="text/javascript"> 
	 var maingrid_gd;
	 var today;
	 var toendday;
	 $(function () {
		//默认时间
		var d = new Date();	
		var year = d.getFullYear();
		var month = d.getMonth() + 1; 
		if(parseInt(month)<10) month = "0"+""+(month); // 记得当前月是要+1的
		var dt = d.getDate();
		if(parseInt(dt)<10) dt = "0"+""+(dt);
		today = year + "-" + month + "-01";
		toendday= year + "-" + month + "-" +dt;
		$("#taskStartTime").val(today);
		$("#taskEndTime").val(toendday);
         
        maingrid_gd = $("#task_sys_list").ligerGrid({
          columns: [                
			 {display: '操作', isSort: false, minWidth : 180, render: function (rowdata, rowindex, value){
				  var h='';
                  if(rowdata.isPublish!="0"){
                      h += "<shiro:hasPermission name='task112:info'> <a href='javascript:void(0);' onclick='showEidtTask(\"show\",\""+rowdata.taskId+"\");'>查看</a>&nbsp;&nbsp; </shiro:hasPermission>";
                  }else{
                      h += "<shiro:hasPermission name='task112:upd'> <a href='javascript:void(0);' onclick='showEidtTask(\"update\",\""+rowdata.taskId+"\");'>修改</a>&nbsp;&nbsp;</shiro:hasPermission>";
                  }
				  h += "<shiro:hasPermission name='task112:del'> <a href='javascript:void(0);' onclick='deleteTaskInfo(\""+rowdata.taskId+"\",\""+rowdata.taskStatus+"\");'>删除</a>&nbsp;&nbsp;</shiro:hasPermission>";
				  if(rowdata.taskStatus!="0" && rowdata.taskStatus!="1"){
				 	h += "<shiro:hasPermission name='task112:wcinfo'><a href='javascript:void(0);' onclick='showTaskWcqk(\""+rowdata.taskId+"\",\""+rowdata.taskName+"\",\""+rowdata.csrTopDCount+"\");'>完成情况</a> </shiro:hasPermission>";
				  }	
                  return h;
            }},
			{display: '任务编号', name: 'taskId', align: 'left', width :130,isSort:false},
	        {display: '任务名称', name: 'taskName', align: 'left', width :120,isSort:false},
			{display: '质检员工号', name: 'qcUser', align: 'left', minWidth :80,isSort:false},
			{display: '任务状态', name: 'taskStatus', align: 'left', minWidth :80,isSort:false,render : function(rowdata, rowindex, value) {
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
			{display: '创建时间', name: 'toCreateTime', align: 'left', minWidth :80,isSort:false},
			{display: '创建人工号', name: 'createUserId', align: 'left', minWidth :100,isSort:false}
            ], 
            url:'${ctx}/controller/chapter112/gettaskOrder112Chapter.do',
            parms:[
                {name:"createBeginTime",value:$("#taskStartTime").val()},
            	{name:"createEndTime",value:$("#taskEndTime").val()}
            ],
            sortName: 'taskId',height:'100%', width:"auto", pageSize:10 ,rownumbers :true
        });
        $("#pageloading").hide(); 
	});
        
    function f_search(){
    	console.log($("#taskStartTime").val()+"-"+$("#taskEndTime").val())
        var parmItems=[{ name:"taskid",value:$("#taskId").val()},
             { name:"taskName",value:$("#taskName").val()},
             { name:"qcUser",value:$("#qcUser").val()},
             { name:"taskStatus",value:$("#taskStatus").val()},
             { name:"createBeginTime",value:$("#taskStartTime").val()},
             { name:"createEndTime",value:$("#taskEndTime").val()}
      	];
     	maingrid_gd.options.parms=parmItems;
		maingrid_gd.options.usePager =true;
		maingrid_gd.options.page='1';
		maingrid_gd.options.newPage=1;
   		maingrid_gd.options.url = '${ctx}/controller/chapter112/gettaskOrder112Chapter.do',
  		maingrid_gd.loadData(f_getWhere());
    }

    function f_getWhere(){
         if (!maingrid_gd) return null;
         var clause = function (rowdata, rowindex){
             var key = $("#taskId").val();
             return rowdata.CustomerID.indexOf(key) > -1;
         };
         return clause; 
     }
        
	//打开任务编辑，新增，查看页面
	function showEidtTask(editType,taskID){
	if(editType=="add"){
		   window.location.href="${ctx}/controller/chapter112/to112ChapterAdd.do";
		}else{
			window.location.href="${ctx}/controller/chapter112/to112ChapterUpdate.do?taskId=" +taskID+"&type="+editType ;
	    }	
	}
	//打开任务完成情况页面
	function showTaskWcqk(taskID,taskName,csrTopDCount){
		window.location.href="${ctx}/page/task/112chapter/task_finishQueryManage.jsp?taskId="+taskID+"&csrTopDCount="+csrTopDCount+"&taskName="+encodeURI(encodeURI(taskName));
	}
	//删除任务信息
	function deleteTaskInfo(taskID,status){
		$.ligerDialog.confirm('确认要删除任务['+taskID+']吗？', function (yes) { 
			if(yes){
				$.ajax({
					url:'${ctx}/controller/chapter112/del112ChapterTask.do',
					data : {
						taskId : taskID,
						status : status
					},
					type:'post',
					dataType:'json',
					success:function(data) {
						if (data.result == '1') {
								$.ligerDialog.success('删除成功', '提示', function(yes) {
								f_search();
							});
						}
					}
				});
			}
		});
	}

	function reset() {
		$("#taskId").val("");
		$("#taskName").val("");
		$("#qcUser").val("");
		$("#taskStatus").val("");
		$("#taskStartTime").val(today);
		$("#taskEndTime").val(toendday);
	}
</script>
</head>
<body>
	<div style="height: 100%">
		<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;overflow:hidden">
			<ul class="search_ul">
		        <li>
		            <label>任务编号：</label>
		            <input type="text" name="taskId" id="taskId"/>
		        </li>
		        <li>
		            <label>任务名称：</label>
		            <input type="text" id="taskName" name="taskName" />
		        </li>
		        <li>
		            <label>质检员工号：</label>
		            <input type="text" id="qcUser" name="qcUser" />
		        </li>
		        <li>
		            <label>任务状态：</label>
		            <select class="select_text" id="taskStatus" name="taskStatus">
						<option value=''>--请选择--</option>
						<option value='0'>未发布</option>
						<option value='1'>待执行</option>
						<option value='2'>执行中</option>
						<option value='3'>已完成</option>
						<option value='4'>未完成</option>
						<option value='5'>强制关闭</option>
					</select>
		        </li>
		        <li>
		            <label>任务开始时间：</label>
		            <input class="Wdate date_text" id="taskStartTime" type="text" name="taskStartTime"
						onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'taskEndTime\');}',dateFmt:'yyyy-MM-dd'})" />
		        </li>
		        <li>
		            <label>任务结束时间：</label>
					<input class="Wdate date_text" id="taskEndTime" type="text" name="taskEndTime"
						onfocus="WdatePicker({minDate:'#F{$dp.$D(\'taskStartTime\');}',dateFmt:'yyyy-MM-dd'})" />
		        </li>
		        <li style="width:100%;text-align:center">
				    <shiro:hasPermission name='task112:query'>
						<input type="button" value="查询" onclick="f_search()"/>&nbsp;&nbsp;&nbsp;&nbsp;
					</shiro:hasPermission>
					<shiro:hasPermission name='task112:add'>
						<input type="button" value="新增" onclick="showEidtTask('add','');" />&nbsp;&nbsp;&nbsp;&nbsp;
					</shiro:hasPermission>
						<input type="button" value="重置" onclick="reset()"/>
				</li>
		    </ul>			
		</div>
		<div class="l-loading" style="display:block;" id="pageloading"></div>
		<div id="task_sys_list" ></div>
	</div>
</body>
</html>

