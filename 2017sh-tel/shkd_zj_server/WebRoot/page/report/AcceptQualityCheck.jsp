<%@ page language="java" import="java.util.*,java.net.URLDecoder" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		select.select_text{border-radius:5px; width:127px; height:24px; line-height:24px; padding-left:2px;}
		table#search_table td{ height:30px; line-height:30px;}
		table#search_table td.td_lable{ text-align:right;}
		table#search_table td.td_value{ text-align:left;}
		div.l-panel-topbar{ height:30px !important; }
	</style>
	<script type="text/javascript"> 
	   var maingrid_gd=null;
		$(function () {
		    var d = new Date();	
			var year = d.getFullYear();
			var month = d.getMonth() + 1; // 记得当前月是要+1的
			if(month<10){
			  month="0"+month;
			}
			var dt = d.getDate();
			var today = year + "-" + month;
			//var today = year + "-" + month + "-01";
			var toendday = year + "-" + month + "-" +dt;
			$("#startTime").val(today);
		   //$("#stopTime").val(toendday);
			
          maingrid_gd = $("#task_qc_info").ligerGrid({
                columns: [  
                	{ display: '操作', isSort: false, minWidth : 190, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";	               
	                    h += "<a href='javascript:void(0);' onclick='qcInfo(\""+rowdata.qcworkid+"\",\""+rowdata.qcDate+"\");'>查看详情&nbsp;&nbsp;</a> ";
		                return h;
	                }
                },            
					{ display: '受理员',name :'acceptName', minWidth:130, isSort: false, totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return '<div>合计</div>';
                        },
                        align: 'left'
                    }},
                    { display: '日期',  name: 'qcDate',align: 'center', minWidth :130,isSort:false},
					{ display: '监听数（总）',  name: 'monitorTotal',align: 'center', minWidth :130,isSort:false,
	                    totalSummary:
	                    {
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
                    },
					{ display: '监听数（结案）',  name: 'monitorClose',align: 'center', minWidth :130,isSort:false,
	                    totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
	                 },
					{ display: '监听数（派单）', name: 'monitorOrder', align: 'center', minWidth :130,isSort:false,
	                    totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
	                 },
					{ display: '正常数',  name: 'normal',align: 'center', minWidth :150,isSort:false,
	                    totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
	                 },
					{ display: '差错数（总）', name: 'mistakeTotal', align: 'center', minWidth :150,isSort:false,
	                    totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
	                 },
					{ display: '差错数（结案）',  name: 'mistakeClose',align: 'center', minWidth :130,isSort:false,
	                    totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
	                 },
					{ display: '差错数（派单）', name: 'mistakeOrder', align: 'center', minWidth :130,isSort:false,
	                    totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
	                 }
				], 
				url:'<%=path%>/controller/qc/AcceptQualityCheck.do',
				parms:[{name:"startTime",value:$("#startTime").val()},
		               ],
				usePager: true,where: f_getWhere,height:'100%', width:"auto", pageSize:10 ,rownumbers :true,checkbox :false
            });
            $("#pageloading").hide(); 
        });
        
         function f_search()
        {
          var time=$("#startTime").val();
           if(time==""){
            $.ligerDialog.error("日期不能为空");
             return false;
           }
            var parmItems=[
				  {name:"startTime",value:$("#startTime").val()}
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
        
        
        function chongzhi(){
			var d = new Date();	
			var year = d.getFullYear();
			var month = d.getMonth() + 1; // 记得当前月是要+1的
			if(month<10){
			  month="0"+month;
			}
			var dt = d.getDate();
			var today = year + "-" + month;
			//var today = year + "-" + month + "-01";
			var toendday = year + "-" + month + "-" +dt;
			$("#startTime").val(today);
			//$("#stopTime").val(toendday);
			$("#workNo").val("");
        }
        
        
            function exportExcel(){
	        	var gridMgr = liger.get("task_qc_info");
				var data = gridMgr.getData();
				if (data.length == 0) { // 判断grid是否有数据
				$.ligerDialog.warn("没有需要导出的数据");
				return false;
				}
			 var startTime=$("#startTime").val();
			window.location.href="<%=path%>/controller/qc/ExportAcceptQualityCheck.do?startTime="+startTime;      
        }
        
        function qcInfo(qcworkid,qcDate){
        	window.location.href="<%=path%>/page/report/AcceptQualityCheckInfo.jsp?qcDate="+qcDate+"&qcworkid="+qcworkid;      
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
						&nbsp;&nbsp;&nbsp;&nbsp;
					          日期：
					          <input class="Wdate date_text" name="startTime" type="text" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM'})"  />
					          <!-- <input class="Wdate date_text" name="startTime" type="text" id="startTime" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'stopTime\')},minDate:'%Y-%M-01',dateFmt:'yyyy-MM-dd'})"  />-
					     <input class="Wdate date_text" name="stopTime" type="text" id="stopTime" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\');}',maxDate:'%Y-%M-%ld',dateFmt:'yyyy-MM-dd'})"/> -->&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="查询" onclick="f_search()" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="重置" onclick="chongzhi()"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="导出" onclick="exportExcel()"/>
					</td>					
				</tr>
			</table>			
		</div>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="task_qc_info" style="margin-top:10px;"></div>
	</div>
</body>
</html>
