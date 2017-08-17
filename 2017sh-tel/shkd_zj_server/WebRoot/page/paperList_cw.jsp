﻿<%@ page language="java" contentType="text/html;charset=UTF-8"%>  
<%@ taglib uri="/WEB-INF/shiro.tld" prefix="shiro"%>
<%
  String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>客调项目管理系统</title>
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="<%=path%>/css/css.css" rel="stylesheet" type="text/css" />
	<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/housing_covering.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=path%>/js/utils.js" type="text/javascript"></script>
	<style type="text/css">
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(<%=path%>/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		input,select{width:140px;height:22px}
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
	var wid;
	$(function () {
	wid=$("body").width()*0.8;
		curTime=getCurDateform2();
		$("#startcomplaintTime").val(curTime+" 00:00:00");
		$("#endcomplaintTime").val(curTime+" 23:59:59");
		var startTime=curTime+" 00:00:00";
		var stopTime=curTime+" 23:59:59";
            maingrid_gd= $("#record_list").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false,  name: 'recordId' ,minWidth : 180, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
						h += " <a href='javascript:void(0);' onclick='dhxjInfo(\""+rowdata.serialCdma+"\");'>电话小结</a> ";
						if(rowdata.is_Qc==0){
							h += "<shiro:hasPermission name='cworder:qc'>  <a href='javascript:void(0);' onclick='pingfen(\""+rowdata.serialCdma+"\",\""+rowdata.receiptOpId+"\",\""+rowdata.complaintTime+"\",\""+rowdata.dirNum+"\");'>评分</a> </shiro:hasPermission>";
						}else{
							h += "<shiro:hasPermission name='cworder:qc'>  <a href='javascript:void(0);' onclick='pingfen(\""+rowdata.serialCdma+"\",\""+rowdata.receiptOpId+"\",\""+rowdata.complaintTime+"\",\""+rowdata.dirNum+"\");'  style='color: red'>评分</a> </shiro:hasPermission>";
						}
						
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
                ], 
                
                url:'<%=path%>/controller/ordercdma/getwkordercdmaList.do',
                parms :[{name:"startcomplaintTime",value:$("#startcomplaintTime").val()},
                        {name:"endcomplaintTime",value:$("#endcomplaintTime").val()}],  
                sortName: 'serialCdma',
                usePager :true,
                height:'100%', 
                width:"auto", 
                pageSize:10 ,
                rownumbers :true
				/* onSuccess : function(data, grid) {
					var total = data.Total;
					if (total == 0) {
						$.ligerDialog.error('未查询到数据');
					}
				} */
              
            });
            $("#pageloading").hide(); 
            
       		// 查询申告业务大类   
            $.ajax({
				url:'<%=path%>/controller/ordercdma/getBsType.do',
				type:'post',
				success:function(data){
				var rec=eval("("+data+")");
				$("#complaintType").empty();
				$("#complaintType").append("<option value=''>--请选择--</option>");
				$.each(rec.bstype,function(index,value){
				$("#complaintType").append("<option value='"+value+"'>"+value+"</option>");
				});
				},
				error:function(){
				$.ligerDialog.error('加载数据异常，请稍后再试！');
				}
			});
            
         <%--  $.ajax({
				url:'<%=path%>/controller/user/listgruoupName.do',
				type:'post',
				success:function(data){
					var res=eval("("+data+")");
					 $("#groupName").append("<option value=''>——请选择——&nbsp;&nbsp;&nbsp;&nbsp;</option>");
					 $.each(res.listgroup,function(index,value){
	                        $("#groupName").append("<option value='"+value.groupName+"'>"+value.groupName+"</option>");
	                 })
				},
				error:function(){
					$.ligerDialog.error('加载数据异常，请稍后再试！');
				}
			}); --%>
					
			
        });
	function f_search() {
		var parmItems=[{ name:"serialCdma",value:$("#serialCdma").val()},      
		               { name:"receiptOpId",value:$("#receiptOpId").val()},
		               { name:"startcomplaintTime",value:$("#startcomplaintTime").val()},
		               { name:"endcomplaintTime",value:$("#endcomplaintTime").val()},	        	             
		               { name:"complaintType",value:$("#complaintType").val()}
		                    		];
			maingrid_gd.options.parms=parmItems;
			maingrid_gd.options.usePager =true;
			maingrid_gd.options.page='1';
			maingrid_gd.options.newPage=1;
		    maingrid_gd.options.url = '<%=path%>/controller/ordercdma/getwkordercdmaList.do';//必须这么写
		    maingrid_gd.loadData(f_getWhere());
	}
		function  f_getWhere() {
			if (!maingrid_gd)
				return null;
			var clause = function(rowdata, rowindex) {
				var smallWorkid = $("#smallWorkid").val();
				/* var key = $("#txtKey").val();
				var key = $("#txtKey").val();
				var key = $("#txtKey").val();
				var key = $("#txtKey").val();
				var key = $("#txtKey").val();
				var key = $("#txtKey").val(); */
				return rowdata.recordId.indexOf(smallWorkid) > -1;
			};
			return clause;
		}
		
		function chongzhi(){
		    $("#serialCdma").val("");
			$("#receiptOpId").val("");
			$("#startcomplaintTime").val(curTime+" 00:00:00");
			$("#endcomplaintTime").val(curTime+" 23:59:59");
			$("#complaintType").val("");
		}
		/* function dhxjInfo(serialCdma){
		location.href="xiaojieList_cw.jsp?serialCdma="+serialCdma;
	
		} */
		function dhxjInfo(serialCdma){
			var titleInfo="电话小结信息";
			$.ligerDialog.open({
				height:420,
				width: wid,
				title : titleInfo,
				url:'<%=path%>/page/xiaojieList_cw.jsp?serialCdma='+serialCdma, 
				showMax: false,
				showToggle: true,
				showMin: false,
				isResize: true,
				slide: false
			});
		}
		
		/***********
		评分
		**********/
		function pingfen(serialCdma,receiptOpId,complaintTime,dirNum){
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
			  var inputdirNum=$("<input>"); //dirNum
			 inputdirNum.attr("type","hidden");
			 inputdirNum.attr("name","dirNum");
			 inputdirNum.attr("value",dirNum);		 
			 $("body").append(form);//将表单放置在body中
			 form.append(inputserialCdma);
			 form.append(inputreceiptOpId);
			 form.append(inputcomplaintTime);
			  form.append(inputdirNum);
			 form.submit();//表单提交
			 $("#formid").remove();
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
			<form id="mysearch" >
			<table id="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr>
					<td class="td_lable">112流水：</td><td class="td_value"><input  type="text" name="serialCdma" id="serialCdma" onkeyup="input_shuziyinwen(this);strlenLimit(this,20);"/></td>
					<td class="td_lable">一级受理员：</td><td class="td_value"><input type="text" name="receiptOpId" id="receiptOpId" onkeyup="input_shuziyinwen(this);strlenLimit(this,20);"/></td>
					<td class="td_lable">申告开始时间：</td><td class="td_value">
					
					<input class="Wdate date_text"  name="startcomplaintTime" type="text" id="startcomplaintTime" 
					onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endcomplaintTime\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
					<td class="td_lable">申告结束时间：</td><td class="td_value">
					<input class="Wdate date_text"name="endcomplaintTime" type="text" id="endcomplaintTime" 
					onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startcomplaintTime\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
				</tr>
				<tr>
				<td class="td_lable">申告业务大类：</td>
					<td class="td_value">
					<select id="complaintType" name="complaintType">
					<option value=''>--请选择--</option>
					</select>
					</td>
				</tr>
				<tr>
					<td colspan="8" align="center">
						<shiro:hasPermission name='cworder:query'>
						<input type="button" value="查询" id="searchbtn" onclick="f_search()"/>&nbsp;&nbsp;&nbsp;&nbsp;
						</shiro:hasPermission>
						<input type="button" value="重置" onclick="chongzhi()" />
					</td>
				</tr>
			</table>
			</form>
		</div>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="record_list" style="margin-top:10px;"></div>
	</div>
</body>
</html>
