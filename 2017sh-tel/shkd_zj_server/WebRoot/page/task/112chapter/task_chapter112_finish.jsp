<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String taskName= URLDecoder.decode(request.getParameter("taskName"),"UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>完成情况</title>
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
	<script src=".<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(../img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input[type='button']{ border:#d3d3d3 1px solid; width:80px; height:25px; cursor:pointer;}
		input.date_text{border-radius:5px; width:135px; height:18px; line-height:18px;}
		
		table#search_table td{ height:30px; line-height:30px;}
		table#search_table td.td_lable{ text-align:right;}
		table#search_table td.td_value{ text-align:left;}
		div.l-panel-topbar{ height:30px !important; }
	</style>
	<script type="text/javascript"> 
	var maingrid_gd;
		$(function () {
            maingrid_gd= $("#task_qc_info").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false, minWidth : 80, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                    if(rowdata.eachDateStatus!='1'){
	                    	h += "<a href='javascript:void(0);' onclick='showLingQuPage(\""+rowdata.taskId+"\",\""+rowdata.cDate+"\",\""+rowdata.csrTopDCount+"\",\""+rowdata.getRecordCount+"\");'>继续完成</a> ";
	                    }else{
	                    	h += "<span style='color:gray'>继续完成</span>";
	                    }
						return h;
	                }
                },
				{ display: '日期', name: 'cDate', align: 'center', minWidth :130,isSort:false},
			
				{ display: '领取/完成', align: 'center', minWidth :130,isSort:false, render: function (rowdata, rowindex, value)
	                {
	                   return (rowdata.getRecordCount)+"/"+(rowdata.completeCount);
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
				], 
				url:'<%=path%>/controller/chapter112/getChapter112Finish.do', 
				parms:[{name:"taskId",value:$("#taskId").val()}
		               ],
				sortName: 'taskId',height:'100%', width:"auto", pageSize:10 ,rownumbers :true,
				toolbar: { 
					items: [
					{ text: '任务规格(每天)：'+$("#csrTopDCount").val()+' 条'}
				]}
				
            });
            $("#pageloading").hide(); 
        });
        
        
        function f_search(){
              var parmItems=[{ name:"taskId",value:$("#taskId").val()}, 
		               		{ name:"eachDateStatus",value:$("#eachDateStatus").val()}
		        ];
	        	maingrid_gd.options.parms=parmItems;
				maingrid_gd.options.usePager =true;
				maingrid_gd.options.page='1';
				maingrid_gd.options.newPage=1;
			    maingrid_gd.options.url = '<%=path%>/controller/chapter112/getChapter112Finish.do',
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
		/******************************
		 *  继续完成
		 *****************************/
		function showLingQuPage(taskUserID,cDate,csrTopDCount,getRecordCount){
 	       	var taskName=$("#taskName").val();		
 	        if(csrTopDCount==getRecordCount){//任务领满未完成跳转已领任务界面
				window.location.href="<%=path%>/page/task/112chapter/task_getChapter_list.jsp?taskId="+taskUserID+"&cDate="+cDate+"&taskName="+encodeURI(encodeURI(taskName));
			}else{//任务没有领满跳转领取界面
				window.location.href="<%=path%>/page/task/112chapter/reciveChapter112.jsp?taskId="+taskUserID+"&taskName="+encodeURI(encodeURI(taskName));	
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
						<input type="hidden" id="csrTopDCount" value="<%=request.getParameter("csrTopDCount") %>" />
						任务名称：<input  type="text" id="taskName" value="<%=taskName%>" readonly="readonly"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="hidden" id="taskId" value="<%=request.getParameter("taskId")%>" />
						质检员：<input type="text" id="qcuserworkid" value="${userworkid}" readonly="readonly" />&nbsp;&nbsp;&nbsp;&nbsp;
						任务状态：<select class="select_text" id="eachDateStatus">
				  					<option value="">
										--请选择--
									</option>
									<option value="1">
										已完成
									</option>
									<option value="2">
										未完成
									</option>
				</select>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="查询" onclick="f_search()" />&nbsp;&nbsp;&nbsp;&nbsp;
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
