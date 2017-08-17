<%@ page language="java" contentType="text/html;charset=UTF-8"%>  
<%
  String path = request.getContextPath();
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
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="<%=path%>/js/utils.js" type="text/javascript"></script>
	<style type="text/css">
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(<%=path%>/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input[type='button']{ border:#d3d3d3 1px solid; width:80px; height:25px; cursor:pointer;}
		input.date_text{border-radius:5px; width:140px; height:18px; line-height:18px;}
	</style>
	<script type="text/javascript"> 
	var maingrid_gd=null;	
	maingrid_gd2=null;
	$(function () {
		
		var starttime=$("#stoptimeq",parent.document).val();
		var endtime=$("#stoptimeh",parent.document).val();
		var agentworkid=$("#agentworkid",parent.document).val();
		$("#declarationTimestart").val(starttime);
		$("#declarationTimeend").val(endtime);
		$("#firstAgentUserid").val(agentworkid);
		$("#declarationTimestart2").val(starttime);
		$("#declarationTimeend2").val(endtime);
		$("#firstAgentUserid2").val(agentworkid);
            maingrid_gd= $("#112maingrid_gd").ligerGrid({
                columns: [                
				{ display: '操作', name:'workorderId',isSort: false, width: 60, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                    h += "<a href='javascript:void(0);' onclick='relate(\""+value+"\",\"7\");'>关联</a> ";
	                    return h;
	                }
                },
				{ display: '流水', name: 'workorderId', align: 'left', width:130,isSort:false},
                { display: '故障号', name: 'errorNo', align: 'left', width:120,isSort:false},
				{ display: '是否光网', name: 'isGw', align: 'left', width:80,isSort:false},
				{ display: '区局', name: 'rc', align: 'left', width:80,isSort:false},
				{ display: '分局', name: 'branch', align: 'left', width:80,isSort:false},
				{ display: '站点', name: 'sites', align: 'left', width:100,isSort:false},
				{ display: '申告时间', name: 'declarationTime', align: 'left', width:150,isSort:false},
				{ display: '业务类型', name: 'businessType', align: 'left', width:100,isSort:false},
				{ display: '申告大类', name: 'declarationBigType', align: 'left', width:100,isSort:false},
				{ display: '申告现象', name: 'declarationDescription', align: 'left', width:100,isSort:false},
				{ display: '测试代码', name: 'testCode', align: 'left', width:100,isSort:false},
                { display: '测试结果', name: 'testResult', align: 'left', width:100,isSort:false},
				{ display: '申告备注', name: 'declarationRemark', align: 'left', width:100,isSort:false},
				{ display: '申告联系信息', name: 'declarationLinkinfo', align: 'left', width:100,isSort:false},
				{ display: '是否二级预处理', name: 'isSecondRedeal', align: 'left', width:100,isSort:false},
				{ display: '结案方式', name: 'closeedWay', align: 'left', width:100,isSort:false},
				{ display: '结案历时', name: 'closeedTimelength', align: 'left', width:100,isSort:false},
				{ display: '受理来源', name: 'acceptedSource', align: 'left', width:100,isSort:false},
				{ display: '一级受理员', name: 'firstAgentUserid', align: 'left', width:100,isSort:false},
				{ display: '一级预处理工号', name: 'firstRedealWorkid', align: 'left', width:100,isSort:false},
				{ display: '一级预处理建议', name: 'firstRedealSuggest', align: 'left', width:100,isSort:false},
                { display: '一级预处理结案代码', name: 'firstRedealClosedcode', align: 'left', width:150,isSort:false},
				{ display: '二级预处理结案代码', name: 'secondRedealClosedcode', align: 'left', width:150,isSort:false},
				{ display: '二级预处理备注', name: 'secondRedealRemark', align: 'left', width:100,isSort:false},
				{ display: '二级工号', name: 'secondWorkid', align: 'left', width:100,isSort:false},
				{ display: '二级结案时间', name: 'secondClosetime', align: 'left', width:150,isSort:false},
				{ display: '排修时间', name: 'repairTime', align: 'left', width:150,isSort:false},
				{ display: '排修工号', name: 'repairWorkid', align: 'left', width:100,isSort:false},
				{ display: '排修方向', name: 'repairDirection', align: 'left', width:100,isSort:false},
				{ display: '排修中心', name: 'repairCenter', align: 'left', width:100,isSort:false},
				{ display: '三级故障大类', name: 'threeErrorType', align: 'left', width:100,isSort:false},
				{ display: '三级故障修复代码', name: 'threeErrorRepairCode', align: 'left', width:100,isSort:false},
				{ display: '三级工号', name: 'threeWorkid', align: 'left', width:100,isSort:false},
                { display: '三级修复时间', name: 'threeRepairTime',width:150 ,isSort:false}
                ], 
                url:'<%=path%>/controller/wk112/getwk112List.do',
               // method :'get',
                parms :[{ name:"declarationTimestart",value:$("#declarationTimestart").val()},
			            { name:"declarationTimeend",value:$("#declarationTimeend").val()},
			            { name:"firstAgentUserid",value:$("#firstAgentUserid").val()},
			            { name:"isGl",value:'1'}],  
                sortName: 'workorderId',height:'99%', width:"auto", pageSize:10 ,rownumbers :true
            });
            $("#pageloading").hide(); 
        });
		
		function f_search(type) {
			var parmItems=[{ name:"declarationTimestart",value:$("#declarationTimestart").val()},
			               { name:"declarationTimeend",value:$("#declarationTimeend").val()},
			               { name:"isGl",value:'1'},
			               { name:"firstAgentUserid",value:$("#firstAgentUserid").val()}];
				maingrid_gd.options.parms=parmItems;
				maingrid_gd.options.usePager =true;
				maingrid_gd.options.page='1';
				maingrid_gd.options.newPage=1;		
			    maingrid_gd.options.url = '<%=path%>/controller/wk112/getwk112List.do';//必须这么写  
			    maingrid_gd.loadData(f_getWhere());
		}
		function  f_getWhere() {
			if (!maingrid_gd)
				return null;
			var clause = function(rowdata, rowindex) {
				var declarationTimestart = $("#declarationTimestart").val();
				alert(declarationTimestart);
				return rowdata.workorderId.indexOf(declarationTimestart) > -1;
			};
			return clause;
		}
		
		function relate(workid,type){
			var recordid=$("#recordid",parent.document).val();
			var display=$("#displayid",parent.document).val();
			if(display=="0"){
					$.ligerDialog.error('关联失败，已评过分无法继续关联！');
					return false;
			}else{
			if(type=="8"){
			$.ligerDialog.confirm('确认关联该工单吗？', function (yes) {
				if(yes){		
					 $.ajax({
						url:'<%=path%>/controller/qc/relate.do',
						type:'post',
						data:{'recordId':recordid,'workid':workid,'type':type},
						error: function(request) {
							$.ligerDialog.error('关联失败，请稍候再试！');
						},
						success: function(data) {
							var res=eval("("+data+")");
							if(res.status=='1'){
								//关联成功后回显工单信息
								//$("#glflagid",parent.document).val(res.key);
							    $("#qc_type",parent.document).val(type);
							    $("#workorid",parent.document).val(res.work.serialCdma);
								$("#workorderId",parent.document).val(res.work.serialCdma);
								$("#errorNo",parent.document).val(res.work.dirNum);
								$("#declarationTime",parent.document).val(res.work.complaintTime);
								$("#declarationBigType",parent.document).val(res.work.complaintType);
								$("#declarationDescription",parent.document).val(res.work.complaintReason); 
								$("#deviceIdid",parent.document).val(res.work.dirNum); 		
								$("#workorderId",parent.document).attr("disabled","disabled"); 
								$("#errorNo",parent.document).attr("disabled","disabled"); 
								$("#declarationTime",parent.document).attr("disabled","disabled"); 
								$("#declarationBigType",parent.document).attr("disabled","disabled"); 
								$("#declarationDescription",parent.document).attr("disabled","disabled"); 
								  $.ligerDialog.success('关联成功！','提示',function(yes){
									  maingrid_gd.loadData(f_getWhere());
									  $("#showGongDanDiv",parent.document).slideToggle();
									  
								  });
								  		  
							}else if(res.status=='0'){
								$.ligerDialog.warn('该录音已关联工单，不能修改关联');
							}else{
								$.ligerDialog.error('关联失败，请稍候再试!');
								
							}
						}
					}); 
					}
				
			});
			}else{
			$.ligerDialog.confirm('确认关联该工单吗？', function (yes) {
				if(yes){		
					 $.ajax({
						url:'<%=path%>/controller/qc/relate.do',
						type:'post',
						data:{'recordId':recordid,'workid':workid,'type':type},
						error: function(request) {
							$.ligerDialog.error('关联失败，请稍候再试！');
						},
						success: function(data) {
							var res=eval("("+data+")");
							if(res.status=='1'){
								//关联成功后回显工单信息
								//$("#glflagid",parent.document).val(res.key);
							    $("#qc_type",parent.document).val(type);
							    $("#workorid",parent.document).val(res.work.workorderId);
								$("#workorderId",parent.document).val(res.work.workorderId);
								$("#errorNo",parent.document).val(res.work.errorNo);
								$("#deviceIdid",parent.document).val(res.work.errorNo); 
								$("#declarationTime",parent.document).val(res.work.declarationTime);
								$("#declarationBigType",parent.document).val(res.work.declarationBigType);
								$("#declarationDescription",parent.document).val(res.work.declarationDescription); 	
								$("#workorderId",parent.document).attr("disabled","disabled"); 
								$("#errorNo",parent.document).attr("disabled","disabled"); 
								$("#declarationTime",parent.document).attr("disabled","disabled"); 
								$("#declarationBigType",parent.document).attr("disabled","disabled"); 
								$("#declarationDescription",parent.document).attr("disabled","disabled"); 
								  $.ligerDialog.success('关联成功！','提示',function(yes){
									  maingrid_gd.loadData(f_getWhere());
									  $("#showGongDanDiv",parent.document).slideToggle();		  
								  });
								  		  
							}else if(res.status=='0'){
								$.ligerDialog.warn('该录音已关联工单，不能修改关联');
							}else{
								$.ligerDialog.error('关联失败，请稍候再试!');
								
							}
						}
					}); 
					}
				
			});
			}
			}
		}
	function disfloat(blag){
		if(blag=="112"){
		document.getElementById("cwdata").style.display="none";//隐藏
		document.getElementById("112data").style.display="";//显示
		}else if(blag=="cw"){
		document.getElementById("cwdata").style.display="";//显示
		document.getElementById("112data").style.display="none";//隐藏
		 maingrid_gd2= $("#cwmaingrid_gd").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false, width: 60, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                    h += "<a href='javascript:void(0);' onclick='relate(\""+rowdata.serialCdma+"\",\"8\");'>关联</a> ";
	                    return h;
	                }
                },
			   { display: '业务状态', name: 'appealStatus', align: 'left', width:100,isSort:false},
				{ display: '112流水', name: 'serialCdma', align: 'left', width:130,isSort:false},
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
                ],  url:'<%=path%>/controller/ordercdma/getwkordercdmaList.do',
                parms :[{ name:"startcomplaintTime",value:$("#declarationTimestart2").val()},
			            { name:"endcomplaintTime",value:$("#declarationTimeend2").val()},
			            { name:"receiptOpId",value:$("#firstAgentUserid2").val()},
			            { name:"isGl",value:'1'},
			            ],  
                sortName: 'serialCdma',
                usePager :true,
                height:'99%', 
                width:"auto", 
                pageSize:10 ,
                rownumbers :true
            });
			$("#pageloading2").hide(); 
		
		}
		
		}
	
			function f_search2() {
			var parmItems=[{ name:"startcomplaintTime",value:$("#declarationTimestart2").val()},
			               { name:"endcomplaintTime",value:$("#declarationTimeend2").val()},
			               { name:"receiptOpId",value:$("#firstAgentUserid2").val()},
			               { name:"isGl",value:'1'}];
				maingrid_gd2.options.parms=parmItems;
				maingrid_gd2.options.usePager =true;
				maingrid_gd2.options.page='1';
				maingrid_gd2.options.newPage=1;	
			    maingrid_gd2.options.url = '<%=path%>/controller/ordercdma/getwkordercdmaList.do';//必须这么写	    
			    maingrid_gd2.loadData(f_getWhere2());
		}
		function  f_getWhere2() {
			if (!maingrid_gd2)
				return null;
			var clause = function(rowdata, rowindex) {
				var declarationTimestart = $("#declarationTimestart").val();
				alert(declarationTimestart);
				return rowdata.workorderId.indexOf(declarationTimestart) > -1;
			};
			return clause;
		}
			
    </script> 
</head>
<body>
<div  style="width:100%; margin-top:0px;"><a href="javascript:void(0)" style="margin-left:15px;" onclick="disfloat('112')">112工单</a>  <a href="javascript:void(0)" style="margin-left:30px;" onclick="disfloat('cw')">c网工单</a></div>
	<div id="112data">	
	<div class="title_div" style="width:100%; margin-top:0px;"><label style="margin-left:15px;">查询条件</label></div>
	<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
		申告时间：<input class="Wdate date_text" type="text" name="declarationTimestart" id="declarationTimestart"
		onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'declarationTimeend\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />&nbsp;-&nbsp;
		<input class="Wdate date_text" type="text"  name="declarationTimeend" id="declarationTimeend"
		onfocus="WdatePicker({minDate:'#F{$dp.$D(\'declarationTimestart\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		一级受理员：<input type="text" class="input_text" name="firstAgentUserid" id="firstAgentUserid" disabled="disabled" value="${agentName}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="查询" onclick="f_search('112');" />
	</div>
	<div class="l-loading" style="display:block" id="pageloading"></div>
	<div id="112maingrid_gd" style="margin-top:10px;"></div>
	</div>
	
	<div id="cwdata" style="display:none">	
	<div class="title_div" style="width:100%; margin-top:0px;"><label style="margin-left:15px;">查询条件</label></div>
	<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
		申告时间：<input class="Wdate date_text" type="text" name="startcomplaintTime" id="declarationTimestart2"
		onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'declarationTimeend\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />&nbsp;-&nbsp;
		<input class="Wdate date_text" type="text"  name="endcomplaintTime" id="declarationTimeend2"
		onfocus="WdatePicker({minDate:'#F{$dp.$D(\'declarationTimestart\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		一级受理员：<input type="text" class="input_text" name="receiptOpId" id="firstAgentUserid2" disabled="disabled" value="${agentName}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="查询" onclick="f_search2('112');" />
	</div>
	<div class="l-loading" style="display:block" id="pageloading2"></div>
	<div id="cwmaingrid_gd" style="margin-top:10px;"></div>
	</div>
</body>
</html>
