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
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
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
			$("#taskStartTime").val(today);
			$("#taskEndTime").val(toendday);
            grid= $("#task_sys_list").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false, minWidth : 190, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                   // h += "<a href='javascript:void(0);' onclick='delTask(\""+rowdata.taskId+"\",\""+rowdata.taskStatus+"\");'>删除</a> ";taskuserId
	                    h += "<a href='javascript:void(0);' onclick='showLingQuPage(\""+rowdata.taskId+"\",\""+rowdata.taskuserId+"\",\""+rowdata.taskName+"\",\""+rowdata.taskStartTime+"\",\""+rowdata.taskEndTime+"\");'>去领录音</a> ";
						
						//h += "<a href='javascript:void(0);' onclick='showWcqkForQc(\""+rowdata.taskId+"\",\""+rowdata.taskuserId+"\",\""+rowdata.taskName+"\",\""+rowdata.taskEndTime+"\",\""+rowdata.qcUserName+"\");'>完成情况</a> ";
	                     if(rowdata.eachQcuserStatus=="1"){
	                        h += "";
	                    }else{
	                    h += "<a href='javascript:void(0);' onclick='showTaskRecordList(\""+rowdata.taskId+"\",\""+rowdata.taskName+"\",\""+rowdata.eachQcuserStatus+"\",\""+rowdata.taskuserId+"\");'>质检已领录音</a> ";
	                        h += "<a href='javascript:void(0);' onclick='showWcqkForQc(\""+rowdata.taskId+"\",\""+rowdata.taskuserId+"\",\""+rowdata.taskName+"\",\""+rowdata.taskEndTime+"\",\""+rowdata.qcUserName+"\");'>完成情况</a> ";
	                    }
	                    return h;
	                }
                },
				{ display: '任务编号', name: 'taskId', align: 'left', width :130,isSort:false},
                { display: '质检员工号', name: 'qcUserWorkId', align: 'left', width :120,isSort:false},
				{ display: '任务名称', name: 'taskName', align: 'left', minWidth :80,isSort:false},
				{ display: '任务状态', name: 'eachQcuserStatus', align: 'left', minWidth :80,isSort:false,render : function(rowdata, rowindex, value) {
						var result="";
						switch(value){
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
						   default:
						     result="其他";
						   break;
						   
						}	
						return result;
						}
				},
				{ display: '任务开始时间', name: 'taskStartTime', align: 'left', minWidth :80,isSort:false},
				{ display: '任务结束时间', name: 'taskEndTime', align: 'left', minWidth :100,isSort:false},
				{ display: '创建人工号', name: 'createUserWorkId', align: 'left', minWidth :100,isSort:false}
                ], url:'<%=path%>/controller/task/queryTaskForQc.do',
                parms:[/* {name:"taskStartTime",value:$("#taskStartTime").val()},
		               { name:"taskSEndTime",value:$("#taskEndTime").val()}, */
		               { name:"taskStatus",value:"1"}
		               ],
                height:'100%', width:"auto", pageSize:10 ,rownumbers :true
            });
            $("#pageloading").hide(); 
        });
        
          function f_search(){
              var parmItems=[{ name:"taskId",value:$("#taskId").val()},
		               { name:"taskName",value:$("#taskName").val()},
		               { name:"qcUserWorkId",value:$("#qcUserWorkId").val()},
		               { name:"taskStatus",value:$("#taskStatus").val()},
		               { name:"taskStartTime",value:$("#taskStartTime").val()},
		               { name:"taskSEndTime",value:$("#taskEndTime").val()}
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
		 * 打开任务完成页面
		 ********************************/
		function showWcqkForQc(taskId,taskQcUserId,taskName,taskEndTime,qcUserName){
		   // taskEndTime=taskEndTime.substring(0,10);
		   //window.location.href="<%=path%>/controller/task/toTaskInfo.do";
			window.location.href="<%=path%>/controller/task/showWcqkForQc.do?taskId="+taskId+"&taskName="+encodeURI(encodeURI(taskName))+"&taskQcUserId="+taskQcUserId+"&taskEndTime="+taskEndTime+"&qcUserName="+encodeURI(encodeURI(qcUserName));
		}

		/********************************
		 * 打开领取录音界面
		 ********************************/
		function showLingQuPage(taskId,taskuserId,taskName,taskStartTime,taskEndTime){
			var myDate = new Date();
	        var taskTime=myDate.getFullYear();
	        var month = myDate.getMonth() + 1;
 	        if (month >= 1 && month <= 9) {
 	          month = "0" + month;
 	        }
 	        var date=myDate.getDate();  //获取当前日(1-31)
 	        if (date >= 1 && date <= 9) {
 	          date = "0" + date;
 	        }
 	        taskTime=taskTime+"-"+month+"-"+date;
 	        
			//alert(taskTime);		
			//任务是否已经结束
			if(bijiaoDate(taskEndTime.substring(0,10),taskTime)){
				$.ligerDialog.warn("任务已经过期，质检已领取录音");
				return;
			}
			//任务是否开始
			if(bijiaoDate(taskTime,taskStartTime.substring(0,10))){
				$.ligerDialog.warn("还未到任务开始时间！");
				return;
			}		
			//var url="<%=path%>/action/TaskAction.do?method=getRecordForQc&taskId=" + taskId + "&taskTime=" + taskTime+ "&taskQcUserId=" + taskuserId+ "&taskName=" + encodeURI(encodeURI(taskName));
			//document.taskForm.action = encodeURI(encodeURI(url));
			//taskForm.submit();
			window.location.href="<%=path%>/page/task/task_lingqu.jsp?taskId=" + taskId + "&taskTime=" + taskTime+ "&taskQcUserId=" + taskuserId+ "&taskName=" + encodeURI(encodeURI(taskName));
		}
		/********************************
		 * 打开任务已领录音界面
		 ********************************/
		function showTaskRecordList(taskId,taskName,eachQcuserStatus,qcUser){
	//	function queryTaskRecord(obj,taskId,taskName,eachQcuserStatus,qcUser){
		  //document.taskForm.action = "<%=path%>/action/TaskAction.do?method=queryTaskRecord&autoId=" + taskId + "&qcUser=" + qcUser + "&dayflag=1" + "&taskNameTemp=" + taskName;//dayflag 当天录音查询1，历史录音查询2
		//javascript:queryTaskRecord(this,'${task.autoId}','${task.taskName}','${task.eachQcuserStatus}','${task.qcUser}'
			window.location.href="<%=path%>/page/task/task_record_list.jsp?taskId=" + taskId + "&qcUser=" + qcUser + "&dayflag=1" + "&taskName=" + encodeURI(encodeURI(taskName));//dayflag 当天录音查询1，历史录音查询2";
		}
		
		/**
	*str2 代表的时间 大于 str1 代表的时间 返回true 否者返回false
	*
	*/
	function bijiaoDate(str1,str2){//str1的格式必须为："2010-09-20"
		var date1 = Date.parse(str1.replace("-", "/"));
		var date2 = Date.parse(str2.replace("-", "/"));
		if(date2 > date1){
			return true;
		}else{
			return false;
		}
	}
		
	function chongzhi(){
	     $("#taskId").val("");
		 $("#taskName").val("");
		 $("#taskStatus").val("");
		    var d = new Date();	
			var year = d.getFullYear();
			var month = d.getMonth() + 1; // 记得当前月是要+1的
			var dt = d.getDate();
			var today = year + "-" + month + "-01";
			var toendday = year + "-" + month + "-" +dt;
			$("#taskStartTime").val(today);
			$("#taskEndTime").val(toendday);
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
				<table id="search_table" cellpadding="3" cellspacing="3"
					width="100%">
					<tr>
						<td class="td_lable"> 
							任务编号： 
						</td>
						<td class="td_value">
							<input id="taskId" type="text" maxlength="50"  onkeyup="input_shuziyinwen(this);" />
						</td>
						<td class="td_lable">
							任务名称：
						</td>
						<td class="td_value">
							<input id="taskName" type="text" maxlength="50"  onkeyup="noSpecial(this)"/>
						</td>
						<td class="td_lable">
							质检员工号：
						</td>
						<td class="td_value">
							<input id="qcUserWorkId" value="${userworkid}"  disabled="disabled"  type="text" maxlength="50"  onkeyup="input_shuziyinwen(this);" />
						</td>
						<td class="td_lable">
							任务状态：
						</td>
						<td class="td_value">
							<select id="taskStatus">
								<option value="">
									--全部--
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
							</select>
						</td>
					</tr>
					<tr>
						<td class="td_lable">
							任务开始时间：
						</td>
						<td class="td_value">
							<input class="Wdate date_text" id="taskStartTime" type="text"
								onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'taskEndTime\');}',dateFmt:'yyyy-MM-dd'})" />
						</td>
						<td class="td_lable">
							任务结束时间：
						</td>
						<td class="td_value">
							<input class="Wdate date_text" id="taskEndTime" type="text"
								onfocus="WdatePicker({minDate:'#F{$dp.$D(\'taskStartTime\');}',dateFmt:'yyyy-MM-dd'})" />
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
						<shiro:hasPermission name='ztaskrecord:query'>
							<input type="button" value="查询" onclick="f_search()" />
							&nbsp;&nbsp;&nbsp;&nbsp;
						</shiro:hasPermission>	
							<input type="button" value="重置" onclick="chongzhi()" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<!-- <input type="button" value="新增" /> -->
						</td>
					</tr>
				</table>
			</div>
			<div class="l-loading" style="display: block" id="pageloading"></div>
			<div id="task_sys_list" style="margin-top: 10px;"></div>
		</div>
	</body>
</html>
