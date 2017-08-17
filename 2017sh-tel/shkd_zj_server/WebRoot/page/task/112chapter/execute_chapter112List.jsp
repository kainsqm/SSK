<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>执行112小结任务${ctx}</title>
	<link href="${ctx}/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script> 
    <script src="${ctx}/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="${ctx}/js/util.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(${ctx}/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input[type='button']{ border:#d3d3d3 1px solid; width:80px; height:25px; cursor:pointer;}
		input.date_text{border-radius:5px; width:135px; height:18px; line-height:18px;}
		
		table#search_table td{ height:30px; line-height:30px;}
		table#search_table td.td_lable{ text-align:right;}
		table#search_table td.td_value{ text-align:left;}
	</style>
	<script type="text/javascript"> 
	 var maingrid_gd;
	 var today;
	 var toendday;
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
             maingrid_gd= $("#task_sys_list").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false, minWidth : 190, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                    if(rowdata.taskStatus=="3" || rowdata.taskStatus=="5"){
	                    	h += "<shiro:hasPermission name='ztask112:linqu'><span style='color:gray'>去领小结&nbsp;&nbsp;</span></shiro:hasPermission>";
	                    }else{
	                    	h += "<shiro:hasPermission name='ztask112:linqu'><a href='javascript:void(0);' onclick='showLingQuPage(\""+rowdata.taskId+"\",\""+rowdata.taskName+"\",\""+rowdata.taskStartTime+"\",\""+rowdata.taskEndTime+"\");'>去领小结&nbsp;&nbsp;</a> </shiro:hasPermission>";
	                    }
	                    if(rowdata.taskStatus=="1"){
	                    	h += "<shiro:hasPermission name='ztask112:ylinqu'><span style='color:gray'>质检已领&nbsp;&nbsp;</span> </shiro:hasPermission>";
							h += "<shiro:hasPermission name='ztask112:wcinfo'><span style='color:gray'>完成情况&nbsp;&nbsp;</span> </shiro:hasPermission>";
	                    }else{
	                    	h += "<shiro:hasPermission name='ztask112:ylinqu'><a href='javascript:void(0);' onclick='showTaskRecordList(\""+rowdata.taskId+"\",\""+rowdata.taskName+"\");'>质检已领&nbsp;&nbsp;</a> </shiro:hasPermission>";
							h += "<shiro:hasPermission name='ztask112:wcinfo'><a href='javascript:void(0);' onclick='showWcqkForQc(\""+rowdata.taskId+"\",\""+rowdata.taskName+"\",\""+rowdata.csrTopDCount+"\");'>完成情况&nbsp;&nbsp;</a> </shiro:hasPermission>";
	                    }
	                    return h;
	                }
                },
				{ display: '任务编号', name: 'taskId', align: 'left', width :130,isSort:false},
                { display: '任务名称', name: 'taskName', align: 'left', width :120,isSort:false},
				{ display: '质检员工号', name: 'qcUser', align: 'left', minWidth :80,isSort:false},
				{ display: '任务状态', name: 'taskStatus', align: 'left', minWidth :80,isSort:false,render : function(rowdata, rowindex, value) {
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
						    case "5":
						    result="强制关闭";
						   break;
						   default:
						     result="其他";
						   break;
						}	
						return result;
						}},
				{ display: '任务开始时间', name: 'taskStartTime', align: 'left', minWidth :80,isSort:false},
				{ display: '任务结束时间', name: 'taskEndTime', align: 'left', minWidth :100,isSort:false},
				{ display: '创建人工号', name: 'qcworkid', align: 'left', minWidth :100,isSort:false}
                ], 
                 url:'${ctx}/controller/chapter112/getExecuteChapterList.do',
                 parms:[{name:"createBeginTime",value:$("#taskStartTime").val()},
		               { name:"createEndTime",value:$("#taskEndTime").val()},
		               { name:"qcUser",value:$("#qcUser").val()},
		               { name:"taskStatus",value:"1"}
		               ],
                sortName: 'taskId',height:'100%', width:"auto", pageSize:10 ,rownumbers :true
            });
            $("#pageloading").hide(); 
        });
        
        
          function f_search(){
        	    var parmItems=[
     	                 { name:"taskid",value:$("#taskId").val()},
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
			    maingrid_gd.options.url = '${ctx}/controller/chapter112/getExecuteChapterList.do',
			    maingrid_gd.loadData(f_getWhere());
        }

       function f_getWhere()
        {
            if (!maingrid_gd) return null;
            var clause = function (rowdata, rowindex)
            {
                var key = $("#taskId").val();
                return rowdata.CustomerID.indexOf(key) > -1;
            };
            return clause; 
        }
        
		/********************************
		 * 打开任务完成页面
		 ********************************/
		function showWcqkForQc(taskUserID,taskName,csrTopDCount){
			window.location.href="${ctx}/page/task/112chapter/task_chapter112_finish.jsp?taskId="+taskUserID+"&csrTopDCount="+csrTopDCount+"&taskName="+encodeURI(encodeURI(taskName));
		}
		/********************************
		 * 打开领取
		 ********************************/
		function showLingQuPage(taskUserID,taskName,taskStartTime,taskEndTime){
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
			//任务是否已经结束
			if(bijiaoDate(taskEndTime.substring(0,10),taskTime)){
				$.ligerDialog.warn("任务已经过期，质检已领取小结");
				return;
			}
			//任务是否开始
			if(bijiaoDate(taskTime,taskStartTime.substring(0,10))){
				$.ligerDialog.warn("还未到任务开始时间！");
				return;
			}		
			window.location.href="${ctx}/page/task/112chapter/reciveChapter112.jsp?taskId="+taskUserID+"&taskName="+encodeURI(encodeURI(taskName));
		}
		
		
		function bijiaoDate(str1,str2){//str1的格式必须为："2010-09-20"
			var date1 = Date.parse(str1.replace("-", "/"));
			var date2 = Date.parse(str2.replace("-", "/"));
			if(date2 > date1){
				return true;
			}else{
				return false;
			}
		}
		
		/********************************
		 * 打开任务已领录音界面
		 ********************************/
		function showTaskRecordList(taskUserID,taskName){
			window.location.href="${ctx}/page/task/112chapter/task_getChapter_list.jsp?taskId="+taskUserID+"&taskName="+encodeURI(encodeURI(taskName));
		}
		
		function reset(){
			$("#taskId").val("");
			$("#taskName").val("");
			$("#taskStatus").val("");
			$("#taskStartTime").val(today);
			$("#taskEndTime").val(toendday);	
		}
    </script> 
</head>
<!-- 
<body style="margin:0 auto; background-color:#FFFFFF; padding:5px;">
 -->
<body> 
	<div style="">
		<div class="title_div" style="width:100%; margin-top:0px; display:none;"><label style="margin-left:15px;">查询条件</label></div>
		<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
			<table id="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr>
					<td class="td_lable">任务编号：</td><td class="td_value">
					<input type="text" name="taskId" id="taskId"  maxlength="50"  onkeyup="input_shuzi(this);"/>
					</td>
					<td class="td_lable">任务名称：</td><td class="td_value">
					<input type="text" id="taskName" name="taskName"  onkeyup="noSpecial(this)"/>
					</td>
					<td class="td_lable">质检员工号：</td><td class="td_value">
					<input type="text" value="${userworkid}" disabled="disabled"  id="qcUser" name="qcUser" />
					</td>
					<td class="td_lable">任务状态：</td><td class="td_value">
						<select class="select_text" id="taskStatus"> 
						<option value=''>--请选择--</option>
						<option value='0'>未发布</option>
						<option value='1'>待执行</option>
						<option value='2'>执行中</option>
						<option value='3'>已完成</option>
						<option value='4'>未完成</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td_lable">任务开始时间：</td><td class="td_value">
					<input class="Wdate date_text" id="taskStartTime" type="text" name="taskStartTime"
								onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'taskEndTime\');}',dateFmt:'yyyy-MM-dd'})" />
					</td>
					<td class="td_lable">任务结束时间：</td><td class="td_value">
				<input class="Wdate date_text" id="taskEndTime" type="text" name="taskEndTime"
								onfocus="WdatePicker({minDate:'#F{$dp.$D(\'taskStartTime\');}',dateFmt:'yyyy-MM-dd'})" />
					</td>
					<td class="td_lable">&nbsp;</td>
					<td class="td_lable">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="8" align="center">
						<shiro:hasPermission name='ztask112:query'>
							<input type="button" value="查询" onclick="f_search()" />&nbsp;&nbsp;&nbsp;&nbsp;
						</shiro:hasPermission>
						<input type="button" value="重置"  onclick="reset()" />&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>			
		</div>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="task_sys_list" style="margin-top:10px;"></div>
	</div>
</body>
</html>
