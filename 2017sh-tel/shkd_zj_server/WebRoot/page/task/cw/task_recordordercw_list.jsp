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
		.a_target{color:blue;text-decoration: underline;cursor: pointer;}
	</style>
	<script type="text/javascript"> 
	 var maingrid_gd;
	 var dataFromData = [{ isQC: null, text: '已质检' }, { directionFlag: 0, text: '未质检'}];
		$(function () {
          maingrid_gd= $("#task_qc_info").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false, minWidth : 80, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                    h += "<a href='javascript:void(0);'  onclick='pingfen(\""+rowdata.serialCdma+"\",\""+rowdata.receiptOpId+"\",\""+rowdata.complaintTime+"\");'>评分</a>";
						return h;
	                }
                },
				{ display: '业务状态', name: 'appealStatus', align: 'left', width:100,isSort:false},
				{ display: '112流水', name: 'serialCdma', align: 'left', width:130,isSort:false},
               
				{ display: '评分状态', name: 'is_Qc', align: 'center', minWidth :130,isSort:false,render : function(rowdata, rowindex, value) {
					var result="";
					if(value==null || value=='' || value=='0'){
					   result="未评分"; 
					}else{
					   result="已评分";
					}
					return result;
					}
				},
                { display: '号码', name: 'dirNum', align: 'left', width:120,isSort:false},
				{ display: 'WX编号', name: 'wxSerial', align: 'left', width:80,isSort:false},
				{ display: '申告时间', name: 'complaintTime', align: 'left', width:80,isSort:false},
				{ display: '申告业务大类', name: 'complaintType', align: 'left', width:80,isSort:false},
				{ display: '申告业务小类', name: 'complaintService', align: 'left', width:100,isSort:false},
				{ display: '申告现象', name: 'complaintReason', align: 'left', width:100,isSort:false},
				{ display: '联系电话', name: 'appealingNum', align: 'left', width:100,isSort:false},
				{ display: '一级受理员', name: 'receiptOpId', align: 'left', width:100,isSort:false},
				{ display: '一级处理方式', name: 'pretreatType', align: 'left', width:100,isSort:false},
				{ display: '一级结案代码', name: 'pretreatResult1', align: 'left', width:100,isSort:false},
                { display: '一级结案时间', name: 'pretreatTime', align: 'left', width:100,isSort:false},
				{ display: '一级备注', name: 'pretreatRemark', align: 'left', width:100,isSort:false},
				{ display: '二级抽单员', name: 'tlOpId', align: 'left', width:100,isSort:false},
				{ display: '二级抽单时间', name: 'tlTime', align: 'left', width:100,isSort:false},
				{ display: '二级申告业务大类', name: 'complaintType2', align: 'left', width:120,isSort:false},
				{ display: '二级申告业务小类', name: 'complaintService2', align: 'left', width:120,isSort:false},
				{ display: '二级申告现象', name: 'complaintReason2', align: 'left', width:100,isSort:false},
				{ display: '是否应该转二级', name: 'isZhuan', align: 'left', width:100,isSort:false},
				{ display: '二级备注', name: 'pretreatRemark2', align: 'left', width:100,isSort:false},
				{ display: '处理周期', name: 'deal', align: 'left', width:100,isSort:false},
                { display: '二级处理方式', name: 'pretreatType2', align: 'left', width:150,isSort:false},
				{ display: '二级处理员', name: 'pretreatOpId', align: 'left', width:150,isSort:false},
				{ display: '二级处理电话', name: 'pretreatDir', align: 'left', width:100,isSort:false},
				{ display: '二级结案派修时间', name: 'dispatchTime', align: 'left', width:120,isSort:false},
				{ display: '二级预处理结案代码', name: 'pretreatResult2', align: 'left', width:140,isSort:false},
				{ display: '二级WX修复确认时间', name: 'repairTime', align: 'left', width:140,isSort:false},
				{ display: '二级确认工号', name: 'repairOpId', align: 'left', width:100,isSort:false},
				{ display: '用户满意度', name: 'satisfy', align: 'left', width:100,isSort:false},
				{ display: '英文', name: 'isEnglish', align: 'left', width:100,isSort:false},
				{ display: '参考地址', name: 'complaintAddr', align: 'left', width:100,isSort:false},
				{ display: '派修人', name: 'dispachOp', align: 'left', width:100,isSort:false}
				],
				url:'<%=path%>/controller/ordercdma/getyltaskwkcwList.do', 
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
			    maingrid_gd.options.url = '<%=path%>/controller/ordercdma/getyltaskwkcwList.do',
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
		
		function pingfen(serialCdma,receiptOpId,complaintTime){//recordid
		   	var form=$("<form>");//定义一个form表单
			 form.attr("style","display:none");
			 form.attr("target","_blank");
			 form.attr("id","pinfenform");
			 form.attr("method","post");
			 form.attr("action","<%=path%>/controller/ordercdma/qcwkordercdma.do");
			 var inputserialCdma=$("<input>"); //serialCdma
			 inputserialCdma.attr("type","hidden");
			 inputserialCdma.attr("name","serialCdma");
			 inputserialCdma.attr("value",serialCdma);
			 var inputreceiptOpId=$("<input>"); //receiptOpId
			 inputreceiptOpId.attr("type","hidden");
			 inputreceiptOpId.attr("name","receiptOpId");
			 inputreceiptOpId.attr("value",receiptOpId);
			 var inputcomplaintTime=$("<input>"); //complaintTime
			 inputcomplaintTime.attr("type","hidden");
			 inputcomplaintTime.attr("name","complaintTime");
			 inputcomplaintTime.attr("value",complaintTime);		 
			 var inputchannelType=$("<input>");
			 inputchannelType.attr("type","hidden");
			 inputchannelType.attr("name","channelType");
			 inputchannelType.attr("value","taskcworder");
			 $("body").append(form);//将表单放置在body中
			 form.append(inputserialCdma);
			 form.append(inputreceiptOpId);
			 form.append(inputcomplaintTime);
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
