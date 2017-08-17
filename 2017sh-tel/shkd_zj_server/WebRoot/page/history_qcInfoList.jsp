<%@ page language="java" contentType="text/html;charset=UTF-8"%>  
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
	var dataFromData = [{ directionFlag: 1, text: '合格' }, { directionFlag: 0, text: '不合格'}];
		$(function () {
			curTime=getCurDateform2();
			$("#qcTimeStartid").val(curTime+" 00:00:00");
			$("#qcTimeEndid").val(curTime+" 23:59:59");
			var qcTimeStartid=curTime+" 00:00:00";
			var qcTimeEndid=curTime+" 23:59:59";
			
            maingrid_gd= $("#history_qc_list").ligerGrid({
                columns: [                
				{ display: '操作',name:'recordId', isSort: false, minWidth : 180, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                    h += "<a href='javascript:void(0);' onclick='playrecord(\""+rowdata.reservedThree+"\");'>录音播放</a> ";
						h += "<a href='javascript:void(0);' onclick='downloadfile(\""+rowdata.reservedThree+"\");'>录音下载</a> ";
						h += "<shiro:hasPermission name='hisrecord:qc'> <a href='javascript:void(0);' onclick='pingfen(\""+rowdata.qcId+"\");'>评分</a> </shiro:hasPermission>";	
						h += "<shiro:hasPermission name='hisrecord:del'> <a href='javascript:delqchis("+value+")' >删除</a> </shiro:hasPermission>";					
	                    return h;
	                }
                },
                { display: '质检编号', name: 'qcId', align: 'left', width :100,isSort:false},
				{ display: '录音流水号', name: 'reservedThree', align: 'left', width :200,isSort:false},
                { display: '质检员工号', name: 'qcUserWorkid', align: 'left', width :100,isSort:false},
				{ display: '质检时间', name: 'qcTime', align: 'left', width :150,isSort:false},
				{ display: '合格程度', name: 'qualityLevel', align: 'left', minWidth :80,isSort:false},
				{ display: '受理员工号', name: 'smallWorkid', align: 'left', minWidth :80,isSort:false},
				{ display: '受理员姓名', name: 'agentName', align: 'left', minWidth :100,isSort:false},
				{ display: '组室', name: 'groupName', align: 'left', minWidth :100,isSort:false},
				{ display: '业务类型', name: 'businessType', align: 'left', minWidth :100,isSort:false,
					editor: { type: 'select', data: dataFromData, valueField: 'businessType'},
                    render: function (item)
                   {
                       if (parseInt(item.businessType) == 10) return '112';
                       if (parseInt(item.businessType) == 54) return '预处理';
                       if (parseInt(item.businessType) == 32) return 'C网';
                       if (parseInt(item.businessType) == 76) return '其他';
                       if (parseInt(item.businessType) == 178) return '客调112';
                       if (parseInt(item.businessType) == 200) return '客调c网';
                    }
				
				},
				{ display: '检查内容', name: 'checkcontent', align: 'left', minWidth :100,isSort:false},
				{ display: '112工单流水', name: 'workorderId', align: 'left', minWidth :100,isSort:false}
                ], 
                sortName: 'recordId',
                url:'<%=path%>/controller/qchis/getQcHistory.do',
                parms :[{name:"qcTimeStart",value:$("#qcTimeStartid").val()},
                        {name:"qcTimeEnd",value:$("#qcTimeEndid").val()}],  
                height:'100%', 
                width:"auto", 
                pageSize:10 ,
                rownumbers :true
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
		
		function downloadfile(reservedThree){
			window.location.href="<%=path%>/controller/record/downRecord.do?reservedThree="+reservedThree;
		}

		
		function f_search() {
			var parmItems=[{ name:"qcUserWorkid",value:$("#qcUserWorkidid").val()},
			               { name:"smallWorkid",value:$("#smallWorkidid").val()},
			               { name:"qcTimeStart",value:$("#qcTimeStartid").val()},
			               { name:"qcTimeEnd",value:$("#qcTimeEndid").val()},
			               { name:"reservedThree",value:$("#reservedThreeid").val()},
			               { name:"declarationBigType",value:$("#declarationBigTypeid").val()},
			               { name:"workorderId",value:$("#workorderIdid").val()},
			               { name:"qualityLevel",value:$("#qualityLevelid").val()},
						   {name:"groupName",value:$("#groupName").val()},
						   {name:"businessType",value:$("#businessType").val()},
						   {name:"checkcontent",value:$("#checkcontent").val()},
						   {name:"qcId",value:$("#qc_id").val()}	               	   
			               ];
				maingrid_gd.options.parms=parmItems;
				maingrid_gd.options.usePager =true;
				maingrid_gd.options.page='1';
				maingrid_gd.options.newPage=1;
			    maingrid_gd.options.url = '<%=path%>/controller/qchis/getQcHistory.do';//必须这么写
			    maingrid_gd.loadData(f_getWhere());
		}
		function  f_getWhere() {
			if (!maingrid_gd)
				return null;
			var clause = function(rowdata, rowindex) {
				var declarationTimestart = $("#declarationTimestart").val();
				return rowdata.workorderId.indexOf(declarationTimestart) > -1;
			};
			return clause;
		}
		function chongzhi(){
		    $("#qcUserWorkidid").val("");
			$("#smallWorkidid").val("");
			$("#reservedThreeid").val("");
			$("#workorderIdid").val("");
			$("#declarationBigTypeid").val("");
			$("#qualityLevelid").val("");
			$("#groupName").val("");
			$("#businessType").val("");
			$("#checkcontent").val("");
			$("#qc_id").val("");
			$("#qcTimeStartid").val(curTime+" 00:00:00");
			$("#qcTimeEndid").val(curTime+" 23:59:59");
		} 
		
		
		function delqchis(recordid){
			$.ligerDialog.confirm('确认删除该质检记录吗？', function (yes){
				if(yes){
					 $.ajax({
						url:'<%=path%>/controller/qchis/deleteQcHistory.do',
						type:'post',
						data:{'recordId':recordid},
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
		
		function  playrecord(reservedThree){
            var url = '<%=path%>/page/play.jsp?reservedThree='+reservedThree;
             win_move();
                $(".pop_up_window").css({
  			      "width":"340",
  			      "height":"120",
  			      "left":"0px",
  			      "top":"0px"
  			      }).children("iframe").attr("src",url);
		}
		
		function checkcode(){
		var businessType = $("#businessType").val(); 
		if(businessType==''){
			businessType=0;
		}		
			$.ajax({
				url:'<%=path%>/controller/syscode/getcode.do',
				data:{'businessType':businessType},
				type:'post',
				success:function(data){
					var res=eval("("+data+")");
	                 $("#checkcontent").empty();
	                 
	                 $("#checkcontent").append("<option value=''>---请选择---</option>");
					 $.each(res.checkcodelist,function(index,value){
	                        $("#checkcontent").append("<option value='"+value.valuees+"'>"+value.name+"</option>");
	                 })     
				},
				error:function(){
					$.ligerDialog.error('加载数据异常，请稍后再试！');
				}
			});
		
		
		}
		
		function pingfen(qcid){//qcid
		 var form=$("<form>");//定义一个form表单
			 form.attr("style","display:none");
			 form.attr("target","_blank");
			 form.attr("id","pinfenform");
			 form.attr("method","post");
			 form.attr("action","<%=path%>/controller/record/getAllQc.do");
			 var inputqcid=$("<input>"); //qcid
			 inputqcid.attr("type","hidden");
			 inputqcid.attr("name","qcid");
			 inputqcid.attr("value",qcid);
			  var inputqcflag=$("<input>"); //qcflag
			 inputqcflag.attr("type","hidden");
			 inputqcflag.attr("name","qcflag");
			 inputqcflag.attr("value","1");
			 $("body").append(form);//将表单放置在body中
			 form.append(inputqcid);
			 form.append(inputqcflag);
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
					<td class="td_lable">质检员工号：</td><td class="td_value"><input type="text" id="qcUserWorkidid" name="qcUserWorkid" onkeyup="input_shuziyinwen(this);strlenLimit(this,20);"/></td>
					<td class="td_lable">受理员工号：</td><td class="td_value"><input type="text" id="smallWorkidid" name="smallWorkid" onkeyup="input_shuziyinwen(this);strlenLimit(this,20);"/></td>
					
					<td class="td_lable">质检开始时间：</td><td class="td_value">
					<input class="Wdate date_text" id="qcTimeStartid" name="qcTimeStart" type="text" 
					onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'qcTimeEndid\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
					
					<td class="td_lable">质检结束时间：</td><td class="td_value">
					<input class="Wdate date_text" id="qcTimeEndid" name="qcTimeEnd" type="text" 
					onfocus="WdatePicker({minDate:'#F{$dp.$D(\'qcTimeStartid\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
				</tr>
				<tr>
					<td class="td_lable">录音流水号：</td><td class="td_value"><input type="text" id="reservedThreeid" name="reservedThree" onkeyup="input_zmszhg(this);strlenLimit(this,60);"/></td>
					<td class="td_lable">合格程度：</td>
						<td class="td_value" >
							<select id="qualityLevelid" name="qualityLevel" >
								<option value="">---请选择---</option>
								<option value="合格">合格</option>
								<option value="不合格">不合格</option>
							</select>
							
						</td>
						<td class="td_lable">组室：</td><td><select name="groupName" id="groupName" >	
					</select></td>
					<td class="td_lable">业务类型：</td><td class="td_value">
					<select id="businessType" name="businessType" onchange="checkcode()">
					<option value="">---请选择---</option>
					<option value="10">112</option>
					<option value="32">c网</option>
					<option value="76">管控</option>
					<option value="54">其他</option>
					<option value="10">客调112</option>
					<option value="32">客调c网</option>
					</select>
					</td>	
				</tr>
				<tr>
					<td class="td_lable">检查内容：</td>
						<td class="td_value" >
							<select id="checkcontent" name="checkcontent">
							<option value="">---请选择---</option>
							</select>
							
						</td>
						<td class="td_lable">质检编号：</td>
						<td class="td_value" >
							<input type="text" id="qc_id" name="qc_id" onkeyup="input_shuziyinwen(this);strlenLimit(this,20);"/>				
						</td>
				</tr>
				<tr>
					<td colspan="8" align="center">
						<shiro:hasPermission name='hisrecord:query'>
						<input type="button" value="查询" onclick="f_search();"/>&nbsp;&nbsp;&nbsp;&nbsp;
						</shiro:hasPermission>
						<input type="button" value="重置"  onclick="chongzhi()"/>
					</td>
				</tr>
			</table>			
		</div>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="history_qc_list" style="margin-top:10px;"></div>
	</div>
</body>
</html>
