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
		input.date_text{border-radius:5px; width:135px; height:18px; line-height:18px;}
		
		table#search_table td{ height:30px; line-height:30px;}
		table#search_table td.td_lable{ text-align:right;}
		table#search_table td.td_value{ text-align:left;}
		div.l-panel-topbar{ height:30px !important; }
	</style>
	<script type="text/javascript"> 
	 var maingrid_gd;
	 var dataFromData = [{ isQC: 1, text: '已质检' }, { directionFlag: 0, text: '未质检'}];
		$(function () {
          maingrid_gd= $("#task_qc_info").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false, minWidth : 80, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                    h += "<a href='javascript:void(0);' onclick='pinfen("+rowdata.workorderId+");'>评分</a> ";
						return h;
	                }
                },
				{ display: '流水', name: 'workorderId', align: 'left', minWidth:130,isSort:false},
                { display: '故障号', name: 'errorNo', align: 'left', minWidth:120,isSort:false},
               { display: '评分状态', name: 'isQC', align: 'center', minWidth :130,isSort:false,render : function(rowdata, rowindex, value) {
					var result="";
					if(value==null || value=='' || value=='0'){
					   result="未评分"; 
					}else{
					   result="已评分";
					}
					return result;
					}
				},
				{ display: '是否光网', name: 'isGw', align: 'left', minWidth:80,isSort:false},
				{ display: '区局', name: 'rc', align: 'left', width:80,isSort:false},
				{ display: '分局', name: 'branch', align: 'left', width:80,isSort:false},
				{ display: '站点', name: 'sites', align: 'left', width:100,isSort:false},
				{ display: '申告时间', name: 'declarationTime', align: 'left', minWidth:160,isSort:false},
				{ display: '业务类型', name: 'businessType', align: 'left', minWidth:100,isSort:false},
				{ display: '申告大类', name: 'declarationBigType', align: 'left', width:100,isSort:false},
				{ display: '申告现象', name: 'declarationDescription', align: 'left', minWidth:100,isSort:false},
				{ display: '申告备注', name: 'declarationRemark', align: 'left', minWidth:100,isSort:false},
				{ display: '测试代码', name: 'testCode', align: 'left', minWidth:100,isSort:false},
                { display: '测试结果', name: 'testResult', align: 'left', width:100,isSort:false},
				{ display: '申告联系信息', name: 'declarationLinkinfo', align: 'left', width:100,isSort:false},
				{ display: '是否二级预处理', name: 'isSecondRedeal', align: 'left', minWidth:100,isSort:false},
				{ display: '结案方式', name: 'closeedWay', align: 'left', minWidth:100,isSort:false},
				{ display: '结案历时', name: 'closeedTimelength', align: 'left', minWidth:100,isSort:false},
				{ display: '受理来源', name: 'acceptedSource', align: 'left', minWidth:100,isSort:false},
				{ display: '一级受理员', name: 'firstAgentUserid', align: 'left', minWidth:100,isSort:false},
				{ display: '一级预处理工号', name: 'firstRedealWorkid', align: 'left', width:100,isSort:false},
				{ display: '一级预处理建议', name: 'firstRedealSuggest', align: 'left', width:100,isSort:false},
                { display: '一级预处理结案代码', name: 'firstRedealClosedcode', align: 'left', width:150,isSort:false},
				{ display: '二级预处理结案代码', name: 'secondRedealClosedcode', align: 'left', width:150,isSort:false},
				{ display: '二级预处理备注', name: 'secondRedealRemark', align: 'left', width:100,isSort:false},
				{ display: '二级工号', name: 'secondWorkid', align: 'left', width:100,isSort:false},
				{ display: '二级结案时间', name: 'secondClosetime', align: 'left', minWidth:160,isSort:false},
				{ display: '派修时间', name: 'repairTime', align: 'left', minWidth:160,isSort:false},
				{ display: '派修工号', name: 'repairWorkid', align: 'left', width:100,isSort:false},
				{ display: '派修方向', name: 'repairDirection', align: 'left', width:100,isSort:false},
				{ display: '派修中心', name: 'repairCenter', align: 'left', width:100,isSort:false},
				{ display: '三级故障大类', name: 'threeErrorType', align: 'left', width:100,isSort:false},
				{ display: '三级故障修复代码', name: 'threeErrorRepairCode', align: 'left', width:160,isSort:false},
				{ display: '三级工号', name: 'threeWorkid', align: 'left', width:100,isSort:false},
                { display: '三级修复时间', name: 'threeRepairTime',minWidth:160 ,isSort:false}
				],
				url:'<%=path%>/controller/wk112/getyltaskwk112List.do', 
				parms:[{name:"taskId",value:$("#taskId").val()},
					   {name:"cDate",value:$("#cDate").val()}
		               ],
				sortName: 'workorderId',height:'100%', width:"auto",pageSize :10,rownumbers :true
            });
            $("#pageloading").hide(); 
        });
        
        
         function f_search(){
              var parmItems=[{ name:"taskId",value:$("#taskId").val()},
		               { name:"isqc",value:$("#isqc").val()},
		               {name:"cDate",value:$("#cDate").val()}
		        ];
	        	maingrid_gd.options.parms=parmItems;
				maingrid_gd.options.usePager =true;
				maingrid_gd.options.page='1';
				maingrid_gd.options.newPage=1;
			    maingrid_gd.options.url = '<%=path%>/controller/wk112/getyltaskwk112List.do',
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
        
		
		/***
		* 评分
		**/
		
		function pinfen(orderId){//recordid
		 var form=$("<form>");//定义一个form表单
			 form.attr("style","display:none");
			 form.attr("target","_blank");
			 form.attr("id","pinfenform");
			 form.attr("method","post");
			 form.attr("action","<%=path%>/controller/wk112/112gradeShow.do");
			 var inputworkorderId=$("<input>"); //workorderId
			 inputworkorderId.attr("type","hidden");
			 inputworkorderId.attr("name","workorderId");
			 inputworkorderId.attr("value",orderId);
			 var inputchannelType=$("<input>");
			 inputchannelType.attr("type","hidden");
			 inputchannelType.attr("name","channelType");
			 inputchannelType.attr("value","task112order");
			 $("body").append(form);//将表单放置在body中
			 form.append(inputworkorderId);
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
						任务名称：<input  type="text" id="taskName" value="<%=taskName%>" readonly="readonly"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="hidden" id="taskId" value="<%=request.getParameter("taskId")%>" />
						<input type="hidden" id="cDate" value="<%=request.getParameter("cDate")%>" />
						评分状态：<select class="select_text" id="isqc">
				<option value="">--请选择--</option>
				<option value="1">已评分</option>
				<option value="0">未评分</option>
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
