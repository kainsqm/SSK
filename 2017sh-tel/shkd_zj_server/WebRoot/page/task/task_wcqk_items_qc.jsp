<%@ page language="java" import="java.util.*,java.net.URLDecoder" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//String taskName=URLDecoder.decode(URLDecoder.decode(request.getParameter("taskName"),"UTF-8"), "utf-8"); 
//String qcUserName=URLDecoder.decode(URLDecoder.decode(request.getParameter("qcUserName"),"UTF-8"), "utf-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>客调项目管理系统</title>
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(<%=path%>/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input[type='button']{ border:#d3d3d3 1px solid; width:80px; height:25px; cursor:pointer;}
		input.date_text{border-radius:5px; width:140px; height:18px; line-height:18px;}
		
		table#search_table td{ height:30px; line-height:30px;}
		table#search_table td.td_lable{ text-align:right;}
		table#search_table td.td_value{ text-align:left;}
		div.l-panel-topbar{ height:30px !important; }
	</style>
	<script type="text/javascript">
		    var ctx = "${pageContext.request.contextPath}/page/task";
		</script>
	<script type="text/javascript"> 
	 var maingrid_gd=null;
		$(function () {
            maingrid_gd= $("#task_qc_info").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false, minWidth : 80, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";                                       //,taskTime,getRecordCount,qcDCount
	                    h += "<a href='javascript:void(0);'  onclick='showLingQuPage(\""+rowdata.taskId+"\",\""+rowdata.taskuserId+"\",\""+$("#TASK_NAME").val()+"\",\""+rowdata.cDate+"\",\""+rowdata.getRecordCount+"\",\""+rowdata.qcDCount+"\");'>继续完成</a> ";
					return h;
	                }
                },
				{ display: '日期', name: 'cDate', align: 'center', minWidth :130,isSort:false},
				{ display: '领取/完成(0~1min)', align: 'center', minWidth :130,isSort:false, render: function (rowdata, rowindex, value)
	                {
	                   return (rowdata.getRecordCountItem)[0]+"/"+(rowdata.completeCountItem)[0]
	                }},
				{ display: '领取/完成(1~2min)',  align: 'center', minWidth :130,isSort:false, render: function (rowdata, rowindex, value)
	                {
	                   return (rowdata.getRecordCountItem)[1]+"/"+(rowdata.completeCountItem)[1]
	                }},
				{ display: '领取/完成(2~3min)',  align: 'center', minWidth :130,isSort:false, render: function (rowdata, rowindex, value)
	                {
	                   return (rowdata.getRecordCountItem)[2]+"/"+(rowdata.completeCountItem)[2]
	                }},
				{ display: '领取/完成(3~5min)',  align: 'center', minWidth :130,isSort:false, render: function (rowdata, rowindex, value)
	                {
	                   return (rowdata.getRecordCountItem)[3]+"/"+(rowdata.completeCountItem)[3]
	                }},
				{ display: '领取/完成(5~10min)',  align: 'center', minWidth :130,isSort:false, render: function (rowdata, rowindex, value)
	                {
	                   return (rowdata.getRecordCountItem)[4]+"/"+(rowdata.completeCountItem)[4]
	                }},
				{ display: '领取/完成( > 10 min)',  align: 'center', minWidth :130,isSort:false, render: function (rowdata, rowindex, value)
	                {
	                   return (rowdata.getRecordCountItem)[5]+"/"+(rowdata.completeCountItem)[5]
	                }},
				{ display: '任务状态', name: 'eachDateStatus', align: 'center', minWidth :130,isSort:false,render : function(rowdata, rowindex, value) {
						var result="";
						switch(value){
						   case '1':
						    result="已完成";
						   break;
						   case '2':
						    result="未完成";
						    break;
						   default:
						     result="其他";
						   break;
						   
						}	
						return result;
						}
				}
				],url:'<%=path%>/controller/task/showTaskCompleteInforqc.do?taskQcUserId='+$("#taskQcUserId").val(),
				height:'100%', width:"auto", pageSize:10 ,rownumbers :true,
				toolbar: { items: [
						{ text: '任务规格(每天)：'},
						{ text: '0~1 min：'+$("#qcDcountItem0").val()+'条'},
						{ line: true },
						{ text: '1~2 min：'+$("#qcDcountItem1").val()+'条'},
						{ line: true },
						{ text: '2~3 min：'+$("#qcDcountItem2").val()+'条'},
						{ line: true },
						{ text: '3~5 min：'+$("#qcDcountItem3").val()+'条'},
						{ line: true },
						{ text: '5~10 min：'+$("#qcDcountItem4").val()+'条'},
						{ line: true },
						{ text: '10~more min：'+$("#qcDcountItem5").val()+'条'}
						
					]
                }
            });
            $("#pageloading").hide(); 
        });
        
        
		 function f_search()
        {
            var parmItems=[
				{ name:"taskQcUserId",value:$("#taskQcUserId").val()},
				{ name:"taskStatus",value:$("#TASK_STATUS").val()}
				];
			maingrid_gd.options.parms=parmItems;
			maingrid_gd.options.page=1;
			maingrid_gd.options.newPage=1;
            maingrid_gd.options.data = $.extend(true, {}, parmItems);//必须这么写
		    maingrid_gd.loadData(f_getWhere());
        }
        function f_getWhere()
        {
            if (!maingrid_gd) return null;
            var clause = function (rowdata, rowindex)
            {
                var key = $("#txtKey").val();
                return rowdata.CustomerID.indexOf(key) > -1;
            };
            return clause; 
        }
        
        
        
		/******************************
		 *  查看任务完成明细
		 *****************************/
		function showTaskWcqkItems(taskUserID){
			window.location.href="edit_taskInfo.html";
		}
		/********************************
		 * 打开领取录音界面
		 ********************************/
		function showLingQuPage(taskId,taskuserId,taskName,taskTime,getRecordCount,qcDCount){
			var taskEndTime=$("#taskEndTime").val();
			//var today=new Date();		
			//var todayTime=today.getFullYear()+"-"+(today.getMonth()+1)+"-"+today.getDate();
			var todayTime=$("#todayDate").val();
			//判断任务是否已过期
			if(bijiaoDate(taskEndTime,todayTime)){
				$.ligerDialog.warn("任务已经过期，只能质检已领取录音");
				return;
			}
			//判断该天任务是否已领取完
			if(qcDCount==getRecordCount){
				$.ligerDialog.warn("该天任务已领取完，请到质检已领取界面完成任务");
				return;
			}
			window.location.href=ctx+"/task_lingqu.jsp?taskId=" + taskId + "&taskTime=" + taskTime+ "&taskQcUserId=" + taskuserId+ "&taskName=" + encodeURI(encodeURI(taskName));
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
    </script> 
</head>
<body> 
	<div style="">
		<div class="title_div" style="width:100%; margin-top:0px; display:none;"><label style="margin-left:15px;">查询条件</label></div>
		<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
			<table id="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr>
					<td>
					    <input type="hidden" id="qcDcountItem0" value="${qcDcountItem[0] }">
					    <input type="hidden" id="qcDcountItem1" value="${qcDcountItem[1] }"/>
					    <input type="hidden" id="qcDcountItem2" value="${qcDcountItem[2] }"/>
					    <input type="hidden" id="qcDcountItem3" value="${qcDcountItem[3] }"/>
					    <input type="hidden" id="qcDcountItem4" value="${qcDcountItem[4] }"/>
					    <input type="hidden" id="qcDcountItem5" value="${qcDcountItem[5] }"/>
						<input type="hidden" id="taskId" value="${taskId}"/>
						<input type="hidden" id="taskEndTime" value="${taskEndTime}"/>
						<input type="hidden" id="todayDate" value="${todayDate}"/>
					    <input type="hidden" id="taskQcUserId" value="${taskQcUserId}"/>
					    <!-- <input type="hidden" id="qcUser" value="<%=request.getParameter("qcUser") %>"/> -->
						任务名称：<input type="text" id="TASK_NAME"  disabled="disabled" value="${taskName}" />&nbsp;&nbsp;&nbsp;&nbsp;
						质检员：<input type="text" id="QC_USER_NAME" disabled="disabled" value="${qcUserName}" />&nbsp;&nbsp;&nbsp;&nbsp;
						任务状态：<select id="TASK_STATUS">
						            <option value="">
										--全部--
									</option>
									<option value="1">
										已完成
									</option>
									<option value="2">
										未完成
									</option>
							</select>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="查询" onclick="f_search()"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="返回" onclick="history.go(-1);" />
					</td>					
				</tr>
			</table>			
		</div>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="task_qc_info" style="margin-top:10px;"></div>
	</div>
</body>
</html>
