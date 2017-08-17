﻿<%@ page language="java" contentType="text/html;charset=UTF-8"%>  
<%
  String path = request.getContextPath();
%>
<%@ taglib uri="/WEB-INF/shiro.tld" prefix="shiro"%>
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
	<script src="<%=path%>/js/utils.js" type="text/javascript"></script>
	<style type="text/css">
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(<%=path%>/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
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
		curTime=getCurDateform2();
		$("#resultTimestartid").val(curTime+" 00:00:00");
		$("#resultTimeendid").val(curTime+" 23:59:59");
           maingrid_gd= $("#record_list").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false, minWidth : 180, render: function (rowdata, rowindex, value)
	                {
	                   var h = "";
	                    if(rowdata.isQC==0){
						h += "<shiro:hasPermission name='cwxj:qc'> <a href='javascript:void(0);' onclick='pingfen(\""+rowdata.serialCdma+"\",\""+rowdata.cdmaAutoid+"\");'>评分</a> </shiro:hasPermission>";
	                    }else{
	                    h += "<shiro:hasPermission name='cwxj:qc'> <a href='javascript:void(0);' onclick='pingfen(\""+rowdata.serialCdma+"\",\""+rowdata.cdmaAutoid+"\");' style='color: red'>评分</a> </shiro:hasPermission>";
	                    }
	                    return h;
	                }
                },
				{ display: '业务类型', name: 'serviceType', align: 'left', width :130,isSort:false},
                { display: '小结类型', name: 'resultType', align: 'left', width :120,isSort:false},
                { display: '一级受理员', name: 'opId', align: 'left', minWidth :100,isSort:false},
				{ display: '故障来源', name: 'complaintSource', align: 'left', minWidth :80,isSort:false},	
				{ display: '用户号码', name: 'dirNum', align: 'left', minWidth :80,isSort:false},
				{ display: '112流水', name: 'serialCdma', align: 'left', minWidth :100,isSort:false},
				{ display: '电话小结流水', name: 'resultSerial', align: 'left', minWidth :100,isSort:false},
				{ display: '电话小结时间', name: 'resultTime', align: 'left', minWidth :100,isSort:false},
				{ display: '备注', name: 'remark', align: 'left', minWidth :80,isSort:false}
                ], 
                url:'<%=path%>/controller/workordersdmasum/getwkordercdmatelsum.do',
                parms:[{name:"resultTimestart",value:$("#resultTimestartid").val()},
                	   {name:"resultTimeend",value:$("#resultTimeendid").val()}
                		],
                sortName: 'serialCdma',height:'100%', width:"auto", pageSize:10 ,rownumbers :true
            });
            $("#pageloading").hide(); 
             $.ajax({
            	url:'<%=path%>/controller/workordersdmasum/getcwxjtype.do',
            	type:'post',
            	success:function(data){
            	var rec=eval("("+data+")");
            	$("#resultType").empty();
            	$("#serviceType").empty();
            	$("#resultType").append("<option value=''>--请选择--</option>");
            	$("#serviceType").append("<option value=''>--请选择--</option>");
            	$.each(rec.xjtype,function(index,value){
            	$("#resultType").append("<option value='"+value+"'>"+value+"</option>");       	
            	});
            	$.each(rec.bstype,function(index,value){
            	$("#serviceType").append("<option value='"+value+"'>"+value+"</option>");
            	});
            	},
            	error:function(){
					$.ligerDialog.error('加载数据异常，请稍后再试');
				}
            	
            });   
        });
        
         function search() {
		var parmItems=[{ name:"serialCdma",value:$("#serialCdma").val()},      
		               { name:"dirNum",value:$("#dirNum").val()},
		               { name:"serviceType",value:$("#serviceType").val()},        	             
		               { name:"resultType",value:$("#resultType").val()},
		               { name:"opId",value:$("#opId").val()},
		               {name:"resultTimestart",value:$("#resultTimestartid").val()},
                	   {name:"resultTimeend",value:$("#resultTimeendid").val()}
		                    		];
			maingrid_gd.options.parms=parmItems;
			maingrid_gd.options.usePager =true;
			maingrid_gd.options.page='1';
			maingrid_gd.options.newPage=1;
		    maingrid_gd.options.url = '<%=path%>/controller/workordersdmasum/getwkordercdmatelsum.do';//必须这么写
		    maingrid_gd.loadData(f_getWhere());
	}
		function  f_getWhere() {
			if (!maingrid_gd)
				return null;
			var clause = function(rowdata, rowindex) {
				var serialCdma = $("#serialCdma").val();
				return rowdata.recordId.indexOf(serialCdma) > -1;
			};
			return clause;
		}
         function btn_reset(){
        $("#serialCdma").val("");
        $("#dirNum").val("");
        $("#serviceType").val("");
        $("#opId").val("");
        $("#resultType").val("");
        $("#resultTimestartid").val(curTime+" 00:00:00");
		$("#resultTimeendid").val(curTime+" 23:59:59");
        }
        
        /***********
		评分
		**********/
		function pingfen(serialCdma,cdmaAutoid){
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
			 var inputcdmaAutoid=$("<input>"); //cdmaAutoid
			 inputcdmaAutoid.attr("type","hidden");
			 inputcdmaAutoid.attr("name","autoid");
			 inputcdmaAutoid.attr("value",cdmaAutoid);
			 $("body").append(form);//将表单放置在body中
			 form.append(inputserialCdma);
			 form.append(inputcdmaAutoid);
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
			<table id="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr>
					<td class="td_lable">112流水：</td><td class="td_value"><input id="serialCdma" name="serialCdma" type="text"/></td>
					<td class="td_lable">用户号码：</td><td class="td_value"><input id="dirNum" name="dirNum" type="text"/></td>
					<td class="td_lable">业务类型：</td><td class="td_value">
					<select id="serviceType" name="serviceType">
					<option value=''>--请选择--</option>
					</select>
					</td>
					<td class="td_lable">小结类型：</td><td class="td_value">
					<select id="resultType" name="resultType">
					<option value=''>--请选择--</option>
					</select>
					</td>
				</tr>
				<tr>
				<td class="td_lable">
							一级受理员：
						</td>
						<td class="td_value">
							<input type="text" id="opId" onblur="noSpecial(this);"
								onkeyup="strlenLimit(this,20);noSpecial(this);" />
						</td>
				
				<td class="td_lable">小结开始时间：</td><td class="td_value">
					<input class="Wdate date_text" id="resultTimestartid" name="resultTimestart" type="text" 
					onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'resultTimeendid\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
					
					<td class="td_lable">小结结束时间：</td><td class="td_value">
					<input class="Wdate date_text" id="resultTimeendid" name="resultTimeend" type="text" 
					onfocus="WdatePicker({minDate:'#F{$dp.$D(\'resultTimestartid\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>	
				</tr>
				<tr>
					<td colspan="8" align="center">
						<shiro:hasPermission name='cwxj:query'>
						<input type="button" value="查询" onclick="search()"/>&nbsp;&nbsp;&nbsp;&nbsp;
						</shiro:hasPermission>
						<input type="button" value="重置" onclick="btn_reset()" />
					</td>
				</tr>
			</table>			
		</div>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="record_list" style="margin-top:10px;"></div>
	</div>
</body>
</html>
