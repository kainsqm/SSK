<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/shiro.tld" prefix="shiro"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>客调项目管理系统</title>
		<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=path %>/css/public.css" rel="stylesheet" type="text/css" />	
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
		<script src="<%=path%>/js/utils.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
		<style type="text/css">

div.title_div {
	border-radius: 5px;
	text-align: left;
	margin-top: 5px;
	background-image: url(<%=path%>/img/login/title_bg.jpg);
	border: solid 1px #D7D7D7;
	width: 90%;
	height: 30px;
	line-height: 30px;
	vertical-align: middle;
	font-size: 14px;
	font-family: "黑体";
}
input,select{width:140px;height:22px}
input.input_text {
	border-radius: 5px;
	width: 130px;
	height: 18px;
	line-height: 18px;
	padding-left: 3px;
}

input[type='button'] {
	border: #d3d3d3 1px solid;
	width: 80px;
	height: 25px;
	cursor: pointer;
}

input.date_text {
	border-radius: 5px;
	width: 140px;
	height: 18px;
	line-height: 18px;
}

table#search_table td {
	height: 30px;
	line-height: 30px;
}

table#search_table td.td_lable {
	text-align: right;
}

table#search_table td.td_value {
	text-align: left;
}
</style>
		<script type="text/javascript"> 
	    var maingrid_gd=null;
	    var curTime=null;
	    var wid;
		$(function () {
		wid=$("body").width()*0.8;
			curTime=getCurDateform2();
			$("#declarationTimestart").val(curTime+" 00:00:00");
			$("#declarationTimeend").val(curTime+" 23:59:59");
			var declarationTimestart=curTime+" 00:00:00";
			var declarationTimeend=curTime+" 23:59:59";
             maingrid_gd= $("#paperList_112").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false, minWidth : 150, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                    h += "<a href='javascript:void(0);' onclick='show_xiaojie(\""+rowdata.workorderId+"\");'>电话小结  </a>";
	                    if(rowdata.isQC==0){
	                    	 h += "<shiro:hasPermission name='112order:qc'><a href='javascript:void(0);' onclick='pingfen(\""+rowdata.workorderId+"\");' >评分</a> </shiro:hasPermission>";
	                    }else{
	                    	 h += "<shiro:hasPermission name='112order:qc'><a href='javascript:void(0);' onclick='pingfen(\""+rowdata.workorderId+"\");' style='color: red'>评分</a> </shiro:hasPermission> ";
	                    }
						
						return h;
	                }
                },
				{ display: '流水', name: 'workorderId', align: 'left', minWidth:130,isSort:false},
                { display: '故障号', name: 'errorNo', align: 'left', minWidth:120,isSort:false},
                { display: '一级受理员', name: 'firstAgentUserid', align: 'left', minWidth:100,isSort:false},
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
                ], url:'<%=path%>/controller/wk112/getwk112List.do',
                parms :[{ name:"declarationTimestart",value:$("#declarationTimestart").val()},
        				{ name:"declarationTimeend",value:$("#declarationTimeend").val()}],  
                height:'100%', width:"auto", pageSize:10 ,rownumbers :true
            });
            $("#pageloading").hide(); 
            
            $.ajax({
				url:'<%=path%>/controller/wk112/getDeclarationBigType.do',
				type:'post',
				success:function(data){
					var res=eval("("+data+")");
					$.each(res.decList,function(index,value){
                        $("#declarationBigType").append("<option value='"+value+"'>"+value+"</option>");
                    })
                    $.each(res.closeedWay,function(index,value){
                        $("#closeedWay").append("<option value='"+value+"'>"+value+"</option>");
                    })
                    $.each(res.acceptedSource,function(index,value){
                        $("#acceptedSource").append("<option value='"+value+"'>"+value+"</option>");
                    })
                    $.each(res.repairDirection,function(index,value){
                        $("#repairDirection").append("<option value='"+value+"'>"+value+"</option>");
                    })
                    $.each(res.threeErrorType,function(index,value){
                        $("#threeErrorType").append("<option value='"+value+"'>"+value+"</option>");
                    })
				},
				error:function(error){
					$.ligerDialog.error('系统异常，请稍后再试！');
				}
				
			});
			
			
			
        });
        
          function f_search()
        {
            var parmItems=[
				{ name:"workorderId",value:$("#workorderId").val()},
				{ name:"firstAgentUserid",value:$("#firstAgentUserid").val()},
				{ name:"declarationTimestart",value:$("#declarationTimestart").val()},
				{ name:"declarationTimeend",value:$("#declarationTimeend").val()},
				{ name:"declarationBigType",value:$("#declarationBigType").val()},
			//	{ name:"groupName",value:$("#groupName").val()},
				{ name:"rc",value:$("#rc").val()},	
				{ name:"declarationDescription",value:$("#declarationDescription").val()},	
			    { name:"testCode",value:$("#testCode").val()},	
			    { name:"declarationRemark",value:$("#declarationRemark").val()},	
			    { name:"declarationLinkinfo",value:$("#declarationLinkinfo").val()},
			    { name:"closeedWay",value:$("#closeedWay").val()},
			    { name:"acceptedSource",value:$("#acceptedSource").val()},
			    { name:"repairDirection",value:$("#repairDirection").val()},
			    { name:"threeErrorType",value:$("#threeErrorType").val()},
		        { name:"threeErrorRepairCode",value:$("#threeErrorRepairCode").val()}
				];
			maingrid_gd.options.parms=parmItems;
		//	maingrid_gd.options.usePager =true;
			maingrid_gd.options.page=1;
			maingrid_gd.options.newPage=1;
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
        
        
        
        
		/*********************
		 * 112电话小结信息
		 *********************/
		function show_xiaojie(gongdanID){
			var titleInfo="电话小结信息";
			$.ligerDialog.open({
				height:420,
				width: wid,
				title : titleInfo,
				url:'<%=path%>/page/112/xiaojieList_112.jsp?workorderId='+gongdanID, 
				showMax: false,
				showToggle: true,
				showMin: false,
				isResize: true,
				slide: false
				
			});
		}
		
		function chongzhi(){
		    $("#workorderId").val("");
			$("#firstAgentUserid").val("");
			$("#declarationBigType").val("");
			$("#declarationTimestart").val(curTime+" 00:00:00");
			$("#declarationTimeend").val(curTime+" 23:59:59");
			$("#rc").val("");
			$("#declarationDescription").val("");
			$("#testCode").val("");
			$("#declarationRemark").val("");
			$("#declarationLinkinfo").val("");
			$("#closeedWay").val("");
			$("#acceptedSource").val("");
			$("#repairDirection").val("");
			$("#threeErrorType").val("");
			$("#threeErrorRepairCode").val("");
		}
		
			/***
		* 评分
		**/
		
		function pingfen(workorderId){//workorderId
		 var form=$("<form>");//定义一个form表单
			 form.attr("style","display:none");
			 form.attr("target","_blank");
			 form.attr("id","pinfenform");
			 form.attr("method","post");
			 form.attr("action","<%=path%>/controller/wk112/112gradeShow.do");
			 var inputrecordid=$("<input>"); //recordid
			 inputrecordid.attr("type","hidden");
			 inputrecordid.attr("name","workorderId");
			 inputrecordid.attr("value",workorderId);
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
							流水：
						</td>
						<td class="td_value">
							<input type="text" id="workorderId"
								onkeyup="strlenLimit(this,20);input_shuzi(this);" />
						</td>
						<td class="td_lable">
							一级受理员：
						</td>
						<td class="td_value">
							<input type="text" id="firstAgentUserid" onblur="noSpecial(this);"
								onkeyup="strlenLimit(this,20);noSpecial(this);" />
						</td>
						<td class="td_lable">
							申告开始时间：
						</td>
						<td class="td_value">
							<input class="Wdate date_text" type="text"
								onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'declarationTimeend\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								id="declarationTimestart" />
						</td>
						<td class="td_lable">
							申告结束时间：
						</td>
						<td class="td_value">
							<input class="Wdate date_text" type="text"
								onfocus="WdatePicker({minDate:'#F{$dp.$D(\'declarationTimestart\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								id="declarationTimeend" />
						</td>

					</tr>

					<tr>
						<td class="td_lable">
							申告大类：
						</td>
						<td class="td_value">
							<select id="declarationBigType" name="declarationBigType"
								>
								<option value="">
									---请选择---
								</option>

							</select>
						</td>
						<td class="td_lable">
							区局：
						</td>
						<td class="td_value">
							<input type="text" id="rc" onblur="noSpecial(this);"
								onkeyup="strlenLimit(this,20);noSpecial(this);" />
						</td>
						<td class="td_lable">
							申告现象：
						</td>
						<td class="td_value">
							<input type="text" id="declarationDescription" onblur="noSpecial(this);"
								onkeyup="strlenLimit(this,20);noSpecial(this);" />
						</td>
						<td class="td_lable">
							测试代码：
						</td>
						<td class="td_value">
							<input type="text" id="testCode" onblur="noSpecial(this);"
								onkeyup="strlenLimit(this,20);noSpecial(this);" />
						</td>

					</tr>


					<tr>
						<td class="td_lable">
							申告备注：
						</td>
						<td class="td_value">
							<input type="text" id="declarationRemark" onblur="noSpecial(this);"
								onkeyup="strlenLimit(this,20);noSpecial(this);" />
						</td>
						<td class="td_lable">
							申告联系信息：
						</td>
						<td class="td_value">
							<input type="text" id="declarationLinkinfo" onblur="noSpecial(this);"
								onkeyup="strlenLimit(this,20);noSpecial(this);" />
						</td>
						<td class="td_lable">
							结案方式：
						</td>
						<td class="td_value">
							<select id="closeedWay" name="closeedWay" >
								<option value="">
									---请选择---
								</option>

							</select>
						</td>
						<td class="td_lable">
							受理来源：
						</td>
						<td class="td_value">
							<select id="acceptedSource" name="acceptedSource"
								>
								<option value="">
									---请选择---
								</option>

							</select>
						</td>

					</tr>
					<tr>
						<td class="td_lable">
							派修方向：
						</td>
						<td class="td_value">
							<select id="repairDirection" name="repairDirection"
								>
								<option value="">
									---请选择---
								</option>

							</select>
						</td>
						<td class="td_lable">
							三级故障大类：
						</td>
						<td class="td_value">
							<select id="threeErrorType" name="threeErrorType"
								>
								<option value="">
									---请选择---
								</option>

							</select>
						</td>
						<td class="td_lable">
							三级故障修复代码：
						</td>
						<td class="td_value">
							<input type="text" id="threeErrorRepairCode" onblur="noSpecial(this);"
								onkeyup="strlenLimit(this,20);noSpecial(this);" />
						</td>
					</tr>
					<tr>
						<td colspan="8" align="center">
						<shiro:hasPermission name='112order:query'>
							<input type="button" value="查询" onclick="f_search()" />
							&nbsp;&nbsp;&nbsp;&nbsp;
						</shiro:hasPermission>	
							<input type="button" value="重置" onclick="chongzhi()" />
						</td>
					</tr>
				</table>
			</div>
			<div class="l-loading" style="display: block" id="pageloading"></div>
			<div id="paperList_112" style="margin-top: 10px;"></div>
		</div>
	</body>
</html>
