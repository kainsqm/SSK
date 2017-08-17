<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>客调项目管理系统</title>
		<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css"
			rel="stylesheet" type="text/css" />
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
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js"
			type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
			<script src="<%=path%>/js/utils.js" type="text/javascript"></script>
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
</style>
		<script type="text/javascript"> 
	    var maingrid_gd=null;
	    var curTime=null;
	    var today;
	    var toendday;
		$(function () {
		var d = new Date();	
				var d = new Date();	
			var year = d.getFullYear();
			var month = d.getMonth() + 1; // 记得当前月是要+1的
			var dt = d.getDate();
			 today = year + "-" + month + "-" +dt+" 00:00";
			 toendday = year + "-" + month + "-" +dt+" 23:59";
			$("#qcstartdatetime").val(today);
			$("#qcstopdatetime").val(toendday);
             maingrid_gd= $("#qcBusiness").ligerGrid({
                columns: [                
				{ display: '序号', name: 'id', align: 'left', minWidth:130,isSort:false},
				{ display: '日期', name: 'qcdate', align: 'left', minWidth:130,isSort:true},
                { display: '时间', name: 'qctime', align: 'left', minWidth:120,isSort:true},
				{ display: '受理者', name: 'agentName', align: 'left', width:150,isSort:false},
				{ display: '检查部门', name: 'isTl9000', align: 'left', width:150,isSort:false},
				{ display: '申告号码', name: 'sgphone', align: 'left', width:80,isSort:false},
				{ display: '申告内容/处理', name: 'sgContent', align: 'left', width:80,isSort:false},
				{ display: '存在问题', name: 'quesstion', align: 'left', width:100,isSort:false},
				{ display: '被检查人确认', name: 'checkqr', align: 'left', minWidth:160,isSort:false},
				{ display: '质检员', name: 'qcName', align: 'left', minWidth:100,isSort:false},
				{ display: '专项非专项', name: 'zxFzx', align: 'left', width:100,isSort:false}
                ], url:'<%=path%>/controller/qc/getQcBusiness.do',
                parms :[{ name:"qcstartdatetime",value:$("#qcstartdatetime").val()},
                		{ name:"qcstopdatetime",value:$("#qcstopdatetime").val()},
                		{ name:"isTl9000",value:$("#isTl9000").val()}	
                	],  
                height:'100%', width:"auto", pageSize:10 ,rownumbers :true
            });
            $("#pageloading").hide(); 
        });
        
          function f_search()
        {
            var parmItems=[
				{ name:"agentName",value:$("#agentName").val()},
				{ name:"isTl9000",value:$("#isTl9000").val()},		
				{ name:"qcstartdatetime",value:$("#qcstartdatetime").val()},
                { name:"qcstopdatetime",value:$("#qcstopdatetime").val()}
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
        
        function exportExcel(){
        	var gridMgr = liger.get("qcBusiness");
			var data = gridMgr.getData();
			if (data.length == 0) { // 判断grid是否有数据
			$.ligerDialog.warn("没有需要导出的数据");
			return false;
			}
			var agentName = $("#agentName").val();
			var qcstartdatetime = $("#qcstartdatetime").val();
			var qcstopdatetime = $("#qcstopdatetime").val();
			var isTl9000=$("#isTl9000").val();
			window.location.href="<%=path%>/controller/qc/QcbusinessExcel.do?isTl9000="+isTl9000+"&agentName="+agentName+"&qcstopdatetime="+qcstopdatetime+"&qcstartdatetime="+qcstartdatetime;      
        }
        
       
		function chongzhi(){
		    $("#agentName").val("");
		    $("#isTl9000").val("");
			$("#qcstartdatetime").val(today);
			$("#qcstopdatetime").val(toendday);
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
							受理者：
						</td>
						<td class="td_value">
							<input id="agentName" type="text"/>
						</td>
						<td>
							检查部门：&nbsp;&nbsp;
							<select id="isTl9000">
							<option value="">---请选择---</option>		
							<option value="1" >tl9000</option>
							<option value="2" >业务室</option>
							<option value="3" >受理中心</option>
							<option value="4" >质检小组</option>
							<option value="5" >全部</option>
							</select>
						</td>
								
						<td class="td_lable">
							质检时间：
						</td>
							<td class="td_value">
							<input id="qcstartdatetime" class="Wdate date_text"  name="startqcworkpapertime" type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'qcstopdatetime\');}',dateFmt:'yyyy-MM-dd HH:mm'})" />
							&nbsp;&nbsp;-&nbsp;&nbsp;
							<input id="qcstopdatetime" class="Wdate date_text"   name="stopqcworkpapertime" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'qcstartdatetime\');}',dateFmt:'yyyy-MM-dd HH:mm'})" />
							</td>
							
							
										
					</tr>
					<tr>
						<td colspan="8" align="center">
							<input type="button" value="查询" onclick="f_search()" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="重置" onclick="chongzhi()" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" onclick="exportExcel()" value="导出" />
						</td>
					</tr>
				</table>
			</div>
			<div class="l-loading" style="display: block" id="pageloading"></div>
			<div id="qcBusiness" style="margin-top: 10px;"></div>
		</div>
	</body>
</html>
