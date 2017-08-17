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
	<!-- <link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
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
		input.date_text{border-radius:5px; width:135px; height:18px; line-height:18px;}
		
		table#search_table td{ height:30px; line-height:30px;}
		table#search_table td.td_lable{ text-align:right;}
		table#search_table td.td_value{ text-align:left;}
		div.l-panel-topbar{ height:30px !important; }
	</style> -->
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
	   var maingrid_gd=null;
		$(function () {
          maingrid_gd = $("#task_qc_info").ligerGrid({
                columns: [                
				{ display: '质检员', isSort: false, minWidth : 80, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";  
	                    h += "<a href='javascript:void(0);' title='查看详细完成情况' onclick='showTaskWcqkItems(\""+rowdata.taskId+"\",\""+rowdata.taskuserId+"\",\""+rowdata.qcUser+"\",\""+rowdata.qcUserName+"\");'>"+rowdata.qcUserName+"</a> ";
						return h;
	                }
                },
				{ display: '领取/完成(0~1min)', align: 'center', minWidth :130,isSort:false, render: function (rowdata, rowindex, value)
	                {
	                   return (rowdata.getRecordCountItem)[0]+"/"+(rowdata.completeCountItem)[0]
	                }
	             },
				{ display: '领取/完成(1~2min)',align: 'center', minWidth :130,isSort:false ,render: function (rowdata, rowindex, value)
	                {
	                   return (rowdata.getRecordCountItem)[1]+"/"+(rowdata.completeCountItem)[1]
	                }},
				{ display: '领取/完成(2~3min)', align: 'center', minWidth :130,isSort:false, render: function (rowdata, rowindex, value)
	                {
	                   return (rowdata.getRecordCountItem)[2]+"/"+(rowdata.completeCountItem)[2]
	                }},
				{ display: '领取/完成(3~5min)', align: 'center', minWidth :130,isSort:false, render: function (rowdata, rowindex, value)
	                {
	                   return (rowdata.getRecordCountItem)[3]+"/"+(rowdata.completeCountItem)[3]
	                }},
				{ display: '领取/完成(5~10min)', align: 'center', minWidth :130,isSort:false, render: function (rowdata, rowindex, value)
	                {
	                   return (rowdata.getRecordCountItem)[4]+"/"+(rowdata.completeCountItem)[4]
	                }},
				{ display: '领取/完成( > 10 min)', align: 'center', minWidth :130,isSort:false, render: function (rowdata, rowindex, value)
	                {
	                   return (rowdata.getRecordCountItem)[5]+"/"+(rowdata.completeCountItem)[5]
	                }},
				{ display: '任务状态', name: 'eachQcuserStatus', align: 'center', minWidth :130,isSort:false,render : function(rowdata, rowindex, value) {
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
						   default:
						     result="其他";
						   break;
						   
						}	
						return result;
						}}
				], 
				url:'<%=path%>/controller/task/gettaskCompleteInfo.do?taskId='+$("#taskId").val(),
				usePager: true,where: f_getWhere,height:'100%', width:"auto", pageSize:10 ,rownumbers :true,checkbox :true
            });
            $("#pageloading").hide(); 
        });
        
         function f_search()
        {
            var parmItems=[
				{ name:"taskId",value:$("#taskId").val()},
				{ name:"taskStatus",value:$("#TASK_STATUS").val()}
				];
			maingrid_gd.options.parms=parmItems;
			maingrid_gd.options.page=1;
			maingrid_gd.options.newPage=1;
            //grid.loadServerData(parmItems);
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
		function showTaskWcqkItems(taskId,taskQcUserId,qcUser,qcUserName){//var url="<%=path%>/action/TaskAction.do?
		   //method=showTaskCompleteInforqc&taskId="+autoId+"&taskName="+taskName+"&taskQcUserId="+taskQcUserId+"&qcUser="+qcUser;
			window.location.href="<%=path%>/controller/task/showTaskWcqkItems.do?taskId="+taskId+"&taskName="+encodeURI(encodeURI($("#task_name").val()))+"&taskQcUserId="+taskQcUserId+"&qcUser="+qcUser+"&qcUserName="+encodeURI(encodeURI(qcUserName));
		}
		
		
		//批量释放录音(以人为维度)
	function releaseRecordItem(){
		var qcUserItem="";
		var rows = maingrid_gd.getSelecteds();
		
	
	   
		for(var i=0;i<rows.length;i++){
		    qcUserItem=qcUserItem+rows[i].qcUser+",";
		}
		if(qcUserItem==""){
			$.ligerDialog.warn("请选择质检员");
			return; 
		}
		
			 //判断是否有需要释放的录音
	    var index=0;
		for(var i=0;i<rows.length;i++){
		   var getRecordCountItem=rows[i].getRecordCountItem;
		   var completeCountItem=rows[i].completeCountItem;
		    for(var j=0;j<6;j++){
		       if(parseInt(getRecordCountItem[j])>parseInt(completeCountItem[j])){
		         index=1;
		         break;
		       }
		    }
		    if(index==1){
		      break;
		    }
		}
		
	   if(index==0){
	   
	     $.ligerDialog.warn("您没有需要释放的录音");
			return;
	   }
	   
		qcUserItem=qcUserItem.substring(0,qcUserItem.length-1);
		var taskId=document.getElementById("taskId").value;
		$.ligerDialog.confirm('您确认要释放？', function (yes) { 
		 if(yes){
			var parameters="taskId="+taskId+"&qcUserItem="+qcUserItem;
			jQuery.ajax({
			url:"<%=path%>/controller/task/releaseRecordByUser.do",
			data:parameters,
			type:"post",
			async:false,
			dataType: "json",
			success:function(data){
				if(data.result=="成功"){						
					$.ligerDialog.success("释放录音成功！");
					f_search();
				}else{
					$.ligerDialog.warn("释放录音失败！");
					return;
				}
			}
		  });
		  
		}
		});
		
	  }
		
    </script> 
</head>
<body> 
	<div style="">
		<div class="title_div" style="width:100%; margin-top:0px; display:none;"><label style="margin-left:15px;">查询条件</label></div>
		<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
		     <input type="hidden" value="<%=request.getParameter("taskId") %>" id="taskId"/>
			<table id="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr>
					<td>
						任务名称：<input id="task_name" disabled="disabled" value="<%=taskName%>"/>&nbsp;&nbsp;&nbsp;&nbsp;
						任务状态：<select id="TASK_STATUS">
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
							</select>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="查询" onclick="f_search()" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="释放" onclick="releaseRecordItem()"/>&nbsp;&nbsp;&nbsp;&nbsp;
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
