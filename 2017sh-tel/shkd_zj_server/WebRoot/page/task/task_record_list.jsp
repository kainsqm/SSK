<%@ page language="java" import="java.util.*,java.net.URLDecoder" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String taskName=URLDecoder.decode(URLDecoder.decode(request.getParameter("taskName"),"UTF-8"), "utf-8");
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
	    var grid=null;
		$(function () {
            grid= $("#task_qc_info").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false, minWidth : 80, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                    h += "<a href='javascript:void(0);' onclick='pingfen(\""+rowdata.recordId+"\");'>评分</a> ";
						return h;
	                }
                },
				{ display: '受理员工号', name: 'workID', align: 'center', minWidth :130,isSort:false},
				{ display: '受理员姓名', name: 'loginname', align: 'center', minWidth :130,isSort:false},
				{ display: '呼叫类型', name: 'directionFlag', align: 'center', minWidth :130,isSort:false, render: function (rowdata, rowindex, value)
	                {
	                    if(value="1"){
	                       return "呼入";
	                    }else if(value=="0"){
	                       return "呼出";
	                    }
	                    return "其他";
	                }
	             },
				{ display: '主叫号码', name: 'callerID', align: 'center', minWidth :130,isSort:false},
				{ display: '被叫号码', name: 'calledID', align: 'center', minWidth :130,isSort:false},
				{ display: 'CTI流水号', name: 'reservedThree', align: 'center', minWidth :160,isSort:false},
				{ display: '录音开始时间', name: 'startTime', align: 'center', minWidth :130,isSort:false},
				{ display: '录音结束时间', name: 'stopTime', align: 'center', minWidth :130,isSort:false},
				{ display: '通话时长', name: 'recordLength', align: 'center', minWidth :130,isSort:false,render : function(rowdata, rowindex, value) {
						return value+"s";
						}},
				{ display: '评分状态', name: 'isQc', align: 'center', minWidth :130,isSort:false,render : function(rowdata, rowindex, value) {
						var result="";
						switch(value){
						   case "0":
						    result="未质检";
						   break;
						   case "1":
						    result="已质检";
						   break;
						}	
						return result;
						}
				},
				{ display: '任务时间', name: 'cDate', align: 'center', minWidth :130,isSort:false},
				{ display: '领取时间', name: 'createTime', align: 'center', minWidth :130,isSort:false},
				{ display: '评分时间', name: 'createTimeTasrec', align: 'center', minWidth :130,isSort:false}
				],url:'<%=path%>/controller/task/queryTaskRecord.do?taskId='+$("#taskId").val()+"&qcUser="+$("#qcUser").val(),
				height:'100%', width:"auto",pageSize :10,rownumbers :true
            });
            $("#pageloading").hide(); 
        });
        
         function f_search(){
              var parmItems=[{ name:"taskId",value:$("#taskId").val()},
		               { name:"taskStatus",value:$("#taskStatus").val()},
		               { name:"qcUser",value:$("#qcUser").val()}
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
        
        
		/******************************
		 *  查看任务完成明细
		 *****************************/
		function showTaskWcqkItems(taskUserID){
			window.location.href="edit_taskInfo.html";
		}
		
		/***
		* 评分
		**/
		
		function pingfen(recordId){//recordid
		 var form=$("<form>");//定义一个form表单
			 form.attr("style","display:none");
			 form.attr("target","_blank");
			 form.attr("id","pinfenform");
			 form.attr("method","post");
			 form.attr("action","<%=path%>/controller/record/getRecord.do");
			 var inputrecordid=$("<input>"); //workorderId
			 inputrecordid.attr("type","hidden");
			 inputrecordid.attr("name","recordid");
			 inputrecordid.attr("value",recordId);
			 var inputchannelType=$("<input>");
			 inputchannelType.attr("type","hidden");
			 inputchannelType.attr("name","channelType");
			 inputchannelType.attr("value","taskrecord");
			 $("body").append(form);//将表单放置在body中
			 form.append(inputrecordid);
			 form.append(inputchannelType);
			 form.submit();//表单提交
			 $("#formid").remove();
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
					    <input type="hidden" id="taskId"  value="<%=request.getParameter("taskId") %>"/>
					    <input type="hidden" id="qcUser" value="<%=request.getParameter("qcUser") %>"/>
						任务名称：<input type="text" id="taskName" disabled="disabled" value="<%=taskName %>" />&nbsp;&nbsp;&nbsp;&nbsp;
						评分状态：
						<select id="taskStatus">
						    <option value="">--全部--</option>
							<option value="0">未质检</option>
							<option value="1">已质检</option>	
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
