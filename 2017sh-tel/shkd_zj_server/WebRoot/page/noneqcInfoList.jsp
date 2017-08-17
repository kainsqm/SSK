﻿<%@ page language="java" contentType="text/html;charset=UTF-8"%>  
<%@ taglib uri="/WEB-INF/shiro.tld" prefix="shiro"%> 
<%
  String path = request.getContextPath();
    Object roleflag=request.getAttribute("roleflag");
  String role=null;
  if(roleflag!=null && roleflag!=""){
  role=roleflag.toString();
  } 
 
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
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="CustomersData.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=path%>/js/utils.js" type="text/javascript"></script>
	<style type="text/css">
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(../img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		input,select{width:140px;height:22px}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input[type='button']{ border:#d3d3d3 1px solid; width:80px; height:25px; cursor:pointer;}
		input.date_text{border-radius:5px; width:135px; height:18px; line-height:18px;}
		
		table#search_table td{ height:30px; line-height:30px;}
		table#search_table td.td_lable{ text-align:right;}
		table#search_table td.td_value{ text-align:left;}
	</style>
	<script type="text/javascript">
	var maingrid_gd; 
	var curTime=null;
		$(function () {
			var roleflag="<%=role%>";
			curTime=getCurDateform2();
			$("#qcTimeStartid").val(curTime+" 00:00:00");
			$("#qcTimeEndid").val(curTime+" 23:59:59");
			var qcTimeStartid=curTime+" 00:00:00";
			var qcTimeEndid=curTime+" 23:59:59";
           maingrid_gd= $("#history_qc_list").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false, width : 180, frozen: true,render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
						h += "<shiro:hasPermission name='none:qc'> <a href='<%=path%>/controller/noneqc/getNoneqc.do?qcid="+rowdata.qcId+"' >评分</a> </shiro:hasPermission>";
					
						h += "<shiro:hasPermission name='none:del'> <a href='javascript:delqchis("+rowdata.qcId+")' >删除</a> </shiro:hasPermission>";
						
	                    return h;
	                }
                },
                { display: '质检编号', name: 'qcId', align: 'left', width :100,isSort:false},
                { display: '质检员工号', name: 'qcUserWorkid', align: 'left', width :120,isSort:false},
				{ display: '质检时间', name: 'qcTime', align: 'left', width :140,isSort:false},
				{ display: '受理员工号', name: 'smallWorkid', align: 'left', width :80,isSort:false},
				{ display: '受理员姓名', name: 'agentName', align: 'left', width :100,isSort:false},
				{ display: '设备编号', name: 'deviceId', align: 'left', width :100,isSort:false},
				{ display: '业务类型', name: 'businessType', align: 'left', width :100,isSort:false},
				{ display: '检查部门', name: 'isTl9000', align: 'left', width :100,isSort:false},
				{ display: '处理情况', name: 'dealDis', align: 'left', width :100,isSort:false},
				{ display: '合格程度', name: 'qualityLevel', align: 'left', width :100,isSort:false},
				{ display: '检查内容', name: 'checkcontent', align: 'left', width :100,isSort:false},
				{ display: '处理结果', name: 'dealRes', align: 'left', width :100,isSort:false},
				{ display: '是否投诉', name: 'isComplain', align: 'left', width :100,isSort:false},
				{ display: '满意度', name: 'satisfied', align: 'left', width :100,isSort:false},
				{ display: '人工转接', name: 'agentTransfer', align: 'left', width :100,isSort:false},
				{ display: '备注', name: 'remark', align: 'left', width :100,isSort:false}
                ], 
                 url:'<%=path%>/controller/qchis/getQcNone.do',
                 parms :[{name:"qcTimeStart",value:$("#qcTimeStartid").val()},
                        {name:"qcTimeEnd",value:$("#qcTimeEndid").val()}],  
                 sortName: 'CustomerID',height:'100%', width:"auto", pageSize:10 ,rownumbers :true
            });
            $("#pageloading").hide(); 
            $.ajax({
				url:'<%=path%>/controller/user/listgruoupName.do',
				type:'post',
				success:function(data){
					var res=eval("("+data+")");
					 $("#groupName").append("<option value=''>---请选择---</option>");
					 $.each(res.listgroup,function(index,value){
	                        $("#groupName").append("<option value='"+value.groupName+"'>"+value.groupName+"</option>");
	                 })
				},
				error:function(){
					$.ligerDialog.error('加载数据异常，请稍后再试！');
				}
			});
        });
        
        	function f_search() {
			 var parmItems=[{ name:"qcUserWork",value:$("#qcUserWorkid").val()},
			               { name:"smallWorkid",value:$("#smallWorkid").val()},
			               { name:"qcTimeStart",value:$("#qcTimeStartid").val()},
			               { name:"qcTimeEnd",value:$("#qcTimeEndid").val()},
			               { name:"isComplain",value:$("#isComplain").val()},
			               { name:"satisfied",value:$("#satisfied").val()}, //name
			               { name:"qualityLevel",value:$("#qualityLevel").val()},
			               { name:"groupName",value:$("#groupName").val()},
			               {name:"qcId",value:$("#qc_id").val()}               
			               ];
				maingrid_gd.options.parms=parmItems;
				maingrid_gd.options.usePager =true;
				maingrid_gd.options.page='1';
				maingrid_gd.options.newPage=1;
			    maingrid_gd.options.url = '<%=path%>/controller/qchis/getQcNone.do';//必须这么写
			    maingrid_gd.loadData(f_getWhere());
		}
		function  f_getWhere() {
			if (!maingrid_gd)
				return null;
			var clause = function(rowdata, rowindex) {
				var qcTimeStartid = $("#qcTimeStartid").val();
				return rowdata.workorderId.indexOf(qcTimeStartid) > -1;
			};
			return clause;
		}
        
        function chongzhi(){
		    $("#qcUserWorkid").val("");
			$("#smallWorkid").val("");
			$("#isComplain").val("");
			$("#satisfied").val("");
			$("#qualityLevel").val("");
			$("#groupName").val("");
			$("#qc_id").val("");
			$("#qcTimeStartid").val(curTime+" 00:00:00");
			$("#qcTimeEndid").val(curTime+" 23:59:59");
		} 
        
        function delqchis(qcid){
			$.ligerDialog.confirm('确认删除该质检记录吗？', function (yes){
				if(yes){
					 $.ajax({
						url:'<%=path%>/controller/qchis/deleteAllQcHistory.do',
						type:'post',
						data:{'qcid':qcid},
						success:function(data){
							var res=eval("("+data+")");
							if(res.status=='1'){
							    $.ligerDialog.success('删除成功','提示',function(yes){
							    	maingrid_gd.loadData(f_getWhere());
							    });
							}else if(res.status=='0'){
								    $.ligerDialog.success('删除失败，请稍后再试','提示',function(yes){
								    	maingrid_gd.loadData(f_getWhere());
								    });
								}
						},
						error:function(error){
							$.ligerDialog.error('系统异常，请稍后再试！');
						}
						
					});
				}
				
           });	
        }
        
		function tz_none(){
			window.location.href="<%=path%>/controller/noneqc/getNoneqc.do";
		}
    </script> 
</head>
<!-- 
<body style="margin:0 auto; background-color:#FFFFFF; padding:5px;">
 -->
<body> 
	<div style="">
		<div class="title_div" style="width:100%; margin-top:0px; display:none;"><label style="margin-left:15px;">查询条件</label></div>
		<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
			<table id="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr>
					<td class="td_lable">质检员工号：</td><td class="td_value"><input type="text" id="qcUserWorkid" name="qcUserWorkid"/></td>
					<td class="td_lable">受理员工号：</td><td class="td_value"><input type="text" id="smallWorkid" name="smallWorkid"/></td>
					<td class="td_lable">质检开始时间：</td><td class="td_value">
					<input class="Wdate date_text" id="qcTimeStartid" name="qcTimeStart" type="text" 
					onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'qcTimeEndid\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
					
					<td class="td_lable">质检结束时间：</td><td class="td_value">
					<input class="Wdate date_text" id="qcTimeEndid" name="qcTimeEnd" type="text" 
					onfocus="WdatePicker({minDate:'#F{$dp.$D(\'qcTimeStartid\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
				</tr>
				<tr>	
					<td class="td_lable">是否投诉：</td><td class="td_value"><select class="select_text" id="isComplain" name="isComplain">
				<option value="">--请选择--</option>
				<option value="1">是</option>
				<option value="0">否</option>
				</select></td>
					<td class="td_lable">满意度：</td><td class="td_value">
					<select class="select_text" id="satisfied" name="satisfied">
				<option value="">--请选择--</option>
				<option value="1">有</option>
				<option value="0">无</option>
				</select>
				</td>
					<!-- <td class="td_lable">满意度：</td><td class="td_value"><input type="text"/></td> -->
					<td class="td_lable">合格程度：</td><td class="td_value"><select id="qualityLevel" name="qualityLevel" class="select_text">
				<option value="">--请选择--</option>
				<option value="合格">合格</option>
				<option value="不合格">不合格</option>
				</select></td>
					<td class="td_lable">组室：</td><td><select name="groupName" id="groupName" >	
					</select></td>
				</tr>
				<tr>
				<td class="td_lable">质检编号：</td>
						<td class="td_value" >
							<input type="text" id="qc_id" name="qc_id" onkeyup="input_shuziyinwen(this);strlenLimit(this,20);"/>				
						</td>	
				</tr>		
				<tr>
					<td colspan="8" align="center">
					<shiro:hasPermission name='none:query'>
						<input type="button" value="查询" onclick="f_search()"/>&nbsp;&nbsp;&nbsp;&nbsp;
					</shiro:hasPermission>	
						<input type="button" value="重置" onclick="chongzhi()"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<shiro:hasPermission name='none:qc'>
						<input type="button" value="评分" onclick="tz_none()" />	
						</shiro:hasPermission>
					</td>
				</tr>
			</table>			
		</div>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="history_qc_list" style="margin-top:10px;"></div>
	</div>
</body>
</html>
