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
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/css/public.css" rel="stylesheet" type="text/css" />
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
	$(function () {
		curTime=getCurDateform2();
		$("#startTime").val(curTime+" 00:00:00");
		$("#stopTime").val(curTime+" 23:59:59");
		var startTime=curTime+" 00:00:00";
		var stopTime=curTime+" 23:59:59";
		var dataFromData = [{ directionFlag: 1, text: '呼入' }, { directionFlag: 0, text: '呼出'}];
            maingrid_gd= $("#record_list").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false,  name: 'recordId' ,minWidth : 180, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                    h += "<a href='javascript:void(0);' onclick='playrecord(\""+rowdata.reservedThree+"\");'>录音播放</a> ";
						h += "<a href='javascript:void(0);' onclick='downloadfile(\""+rowdata.reservedThree+"\");'>录音下载</a> ";
						if(rowdata.isQC==0){
							h += "<shiro:hasPermission name='record:qc'><a href='javascript:void(0);' onclick='pingfen(\""+value+"\");'>评分</a> </shiro:hasPermission>";
						}else{
							h += "<shiro:hasPermission name='record:qc'><a href='javascript:void(0);' onclick='pingfen(\""+value+"\");' style='color: red' >评分</a> </shiro:hasPermission>";
						}
						
						return h;
	                }
                },
               /*  { display: '录音id', name: 'recordId', align: 'left', width :40,isSort:false,hide:false}, */
				{ display: '受理员工号', name: 'smallWorkid', align: 'left', width :130,isSort:false},
                { display: '受理员姓名', name: 'agentName', align: 'left', width :120,isSort:false},
                 { display: '组室', name: 'groupName', align: 'left', width :120,isSort:false},
				{ display: '呼入/呼出', name: 'directionFlag', align: 'left', minWidth :80,isSort:false,
                	
                	editor: { type: 'select', data: dataFromData, valueField: 'directionFlag'},
                    render: function (item)
                   {
                       if (parseInt(item.directionFlag) == 1) return '呼入';
                       if (parseInt(item.directionFlag) == 0) return '呼出';
                    }
				},
				{ display: '主叫号码', name: 'callerid', align: 'left', minWidth :80,isSort:false},
				{ display: '被叫号码', name: 'calledid', align: 'left', minWidth :80,isSort:false},
				{ display: '录音流水号', name: 'reservedThree', align: 'left', width: 200,minWidth :100,isSort:false},
				{ display: '来电开始时间', name: 'startTime', align: 'left', minWidth :150,isSort:false},
				{ display: '来电结束时间', name: 'stopTime', align: 'left', minWidth :150,isSort:false},
				{ display: '通话时长', name: 'recordLength', align: 'left', minWidth :100,isSort:false}
				/* { display: '质检状态', name: 'CustomerID', align: 'left', minWidth :100,isSort:false} */
                ], 
        
                url:'<%=path%>/controller/record/getRecordList.do',
                parms :[{name:"startTime",value:$("#startTime").val()},
                        {name:"stopTime",value:$("#stopTime").val()}],  
                sortName: 'recordId',
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
          $.ajax({
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
			});
           
        });
	function f_search() {
	    var recordLengthmin=$("#recordLengthmin").val();
	    var recordLengthmax=$("#recordLengthmax").val();
	    if(parseInt(recordLengthmin)>=parseInt(recordLengthmax)){
	    $.ligerDialog.error("通话时长填写不规范");
	    return fasle;
	    }   
		var parmItems=[{ name:"smallWorkid",value:$("#smallWorkid").val()},
		               { name:"directionFlag",value:$("#directionFlag option:selected").val()},
		               { name:"callerid",value:$("#callerid").val()},
		               { name:"calledid",value:$("#calledid").val()},
		               { name:"startTime",value:$("#startTime").val()},
		               { name:"stopTime",value:$("#stopTime").val()},
		               { name:"recordLengthmin",value:$("#recordLengthmin").val()},
		               { name:"recordLengthmax",value:$("#recordLengthmax").val()},
		               { name:"reservedThree",value:$("#reservedThree").val()},
		               { name:"groupName",value:$("#groupName").val()}
		                    		];
			maingrid_gd.options.parms=parmItems;
			maingrid_gd.options.usePager =true;
			maingrid_gd.options.page='1';
			maingrid_gd.options.newPage=1;
		    maingrid_gd.options.url = '<%=path%>/controller/record/getRecordList.do';//必须这么写
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
		    $("#smallWorkid").val("");
			$("#directionFlag").val("");
			$("#callerid").val("");
			$("#calledid").val("");
			$("#recordLengthmin").val("");
			$("#recordLengthmax").val("");
			$("#reservedThree").val("");
			$("#startTime").val(curTime+" 00:00:00");
			$("#stopTime").val(curTime+" 23:59:59");
			$("#groupName").val("");
		}
		
		function downloadfile(reservedThree){
			window.location.href="<%=path%>/controller/record/downRecord.do?reservedThree="+reservedThree;
		}
		
		function playrecord(reservedThree){
		            var url = '<%=path%>/page/play.jsp?reservedThree='+reservedThree;
		             win_move();
		                $(".pop_up_window").css({
		  			      "width":"340",
		  			      "height":"120",
		  			      "left":"0px",
		  			      "top":"0px"
		  			      }).children("iframe").attr("src",url);
		}
		
		
			/***
		* 评分
		**/
		
		function pingfen(recordid){//recordid
		 var form=$("<form>");//定义一个form表单
			 form.attr("style","display:none");
			 form.attr("target","_blank");
			 form.attr("id","pinfenform");
			 form.attr("method","post");
			 form.attr("action","<%=path%>/controller/record/getRecord.do");
			 var inputrecordid=$("<input>"); //recordid
			 inputrecordid.attr("type","hidden");
			 inputrecordid.attr("name","recordid");
			 inputrecordid.attr("value",recordid);
			 $("body").append(form);//将表单放置在body中
			 form.append(inputrecordid);
			 form.submit();//表单提交
			 $("#formid").remove();
		}
		
		
		
    </script> 
</head>
<!-- 
<body style="margin:0 auto; background-color:#FFFFFF; padding:5px;">
 -->
<body> 
	<div class="tcc">
	    
		<div class="title_div" style="width:100%; margin-top:0px; display:none;"><label style="margin-left:15px;">查询条件</label></div>
		<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
			<form id="mysearch" >
			<table id="search_table" cellpadding="3" cellspacing="3" width="100%" >
			
				<tr>
					<td class="td_lable">受理员工号：</td><td class="td_value"><input  type="text" name="smallWorkid" id="smallWorkid" onkeyup="input_shuziyinwen(this);strlenLimit(this,20);"/></td>
					<td class="td_lable">呼入/呼出：</td><td class="td_value"><select name="directionFlag" id="directionFlag" >
					<option value="" style="text-align: center;">——请选择——&nbsp;&nbsp;&nbsp;&nbsp;</option>
					<option value="1">呼入</option>
					<option value="0">呼出</option>
					</select></td>
					<td class="td_lable">主叫号码：</td><td class="td_value"><input type="text" name="callerid" id="callerid" onkeyup="input_shuzi(this);strlenLimit(this,20);"/></td>
					<td class="td_lable">被叫号码：</td><td class="td_value"><input type="text" name="calledid" id="calledid" onkeyup="input_shuzi(this);strlenLimit(this,20);"/></td>
				</tr>
				<tr>
					<td class="td_lable">来电开始时间：</td><td class="td_value">
					
					<input class="Wdate date_text"  name="startTime" type="text" id="startTime" 
					onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'stopTime\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
					<td class="td_lable">来电结束时间：</td><td class="td_value">
					<input class="Wdate date_text"name="stopTime" type="text" id="stopTime" 
					onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
					
					<td class="td_lable">通话时长：</td><td class="td_value">
					<input size="5" style="width: 45px" name="recordLengthmin" id="recordLengthmin"  onkeyup="input_shuzi(this);strlenLimit(this,10);"/>&nbsp;-&nbsp;
					<input size="5" style="width: 45px" id="recordLengthmax" name="recordLengthmax" onkeyup="input_shuzi(this);strlenLimit(this,10);"/>(分钟)</td>
					<td class="td_lable">录音流水号：</td><td class="td_value" >
					<input type="text" name="reservedThree" id="reservedThree" onkeyup="input_zmszhg(this);strlenLimit(this,60);" /></td>
				</tr>
				
				<tr>
				<td class="td_lable">组室：</td><td><select name="groupName" id="groupName" >	
					</select></td>
				</tr>
					
				<tr>
					<td colspan="8" align="center">
					<shiro:hasPermission name='record:query'>
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
