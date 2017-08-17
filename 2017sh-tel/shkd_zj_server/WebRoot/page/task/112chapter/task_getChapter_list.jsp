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
	<title>质检已领</title>
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
	                    if(rowdata.isQC==null || rowdata.isQC=='' || rowdata.isQC=='0'){
	 					    h += "<a href='javascript:void(0);' onclick='to112grade("+rowdata.autoId+",\""+rowdata.workorderId+"\",\""+rowdata.telsumWorkid+"\");'>评分</a> ";
	 					}else{
	 						h += "<a href='javascript:void(0);' onclick='to112grade("+rowdata.autoId+",\""+rowdata.workorderId+"\",\""+rowdata.telsumWorkid+"\");' style='color: red'>评分</a> ";
	 					}
						return h;
	                }
                },
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
                { display: '业务类型', name: 'businessType', align: 'left', minWidth:130,isSort:false},
                { display: '小结类型', name: 'telsumType', align: 'left', minWidth:120,isSort:false},
				{ display: '故障来源', name: 'errorSource', align: 'left', minWidth:80,isSort:false},
				{ display: '备注', name: 'remark', align: 'left', width:80,isSort:false},
				{ display: '申告号码', name: 'declarationNo', align: 'left', width:80,isSort:false},
				{ display: '逻辑号码', name: 'logicNo', align: 'left', width:100,isSort:false},
				{ display: '112流水', name: 'telsumId', align: 'left', minWidth:160,isSort:false},
				{ display: '电话小结时间', name: 'telsumTime', align: 'left', minWidth:100,isSort:false},
				{ display: '电话小结工号', name: 'telsumWorkid', align: 'left', width:100,isSort:false}
				],
				url:'<%=path%>/controller/chapter112/getylChapterList.do', 
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
			    maingrid_gd.options.url = '<%=path%>/controller/chapter112/getylChapterList.do', 
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
		function to112grade(autoId,workorderId,telsumWorkid){
			 var form=$("<form>");//定义一个form表单
			 form.attr("style","display:none");
			 form.attr("target","_blank");
			 form.attr("id","pinfenform");
			 form.attr("method","post");
			 form.attr("action","<%=path%>/controller/wk112/112gradeShow.do");
			 var inputserialCdma=$("<input>"); //serialCdma
			 inputserialCdma.attr("type","hidden");
			 inputserialCdma.attr("name","autoid");
			 inputserialCdma.attr("value",autoId);
			 var inputcdmaAutoid=$("<input>"); //cdmaAutoid
			 inputcdmaAutoid.attr("type","hidden");
			 inputcdmaAutoid.attr("name","workorderId");
			 inputcdmaAutoid.attr("value",workorderId);
			 var inputchannelType=$("<input>");
			 inputchannelType.attr("type","hidden");
			 inputchannelType.attr("name","channelType");
			 inputchannelType.attr("value","chapter112Grade");
			 $("body").append(form);//将表单放置在body中
			 form.append(inputserialCdma);
			 form.append(inputcdmaAutoid);
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