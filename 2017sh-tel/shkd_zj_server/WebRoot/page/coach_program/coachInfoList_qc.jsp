<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/shiro.tld" prefix="shiro"%> 
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%> 
<%
String path = request.getContextPath();

String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>客调项目管理系统</title>
	<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script> 
    <script type="text/javascript" src="<%=path%>/js/util.js" ></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(<%=path%>/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input[type='button']{ border:#d3d3d3 1px solid; width:80px; height:25px; cursor:pointer;}
		input.date_text{border-radius:5px; width:140px; height:18px; line-height:18px;}
		
		table#search_table td{ height:30px; line-height:30px;}
		table#search_table td.td_lable{ text-align:right;}
		table#search_table td.td_value{ text-align:left;}
	</style>
	<script type="text/javascript"> 
	   var maingrid_gd=null;
	   var today;
	   var toendday;
		$(function () {
		 	today = getfirstDay();
			toendday= getnowDay();
			$("#starttime").val(today);
			$("#endtime").val(toendday);
            maingrid_gd= $("#task_sys_list").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false,name:'coachmainId', minWidth : 80, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                    
	                    h += "<a href='javascript:void(0);' onclick='showinfo(\""+value+"\")'>查看</a> ";
						if(rowdata.pass=='0' && rowdata.sumImprove == ''){
	                       h += "<shiro:hasPermission name='coach:upd'> <a href='javascript:void(0);' onclick='updinfo(\""+value+"\");'>修改</a> </shiro:hasPermission>";
	                    }
						if(rowdata.pass=='1'){
						h += "<shiro:hasPermission name='coach:addmon'><a href='javascript:void(0);' onclick='show_coachInfo_items(\""+value+"\");'>计划设置</a>	</shiro:hasPermission> ";
						}
	                    return h;
	                }
                },
				{ display: '质检员姓名', name: 'qcUserName', align: 'left', width :130,isSort:false},
                { display: '受理员工号', name: 'acceptorWorkId', align: 'left', width :120,isSort:false},
				{ display: '受理员姓名', name: 'username', align: 'left', minWidth :80,isSort:false},
				{ display: '辅导开始时间', name: 'starttime', align: 'left', minWidth :80,isSort:false},
				{ display: '辅导结束时间', name: 'endtime', align: 'left', minWidth :80,isSort:false},
				{ display: '辅导状态', align: 'left', minWidth :80,isSort:false, render: function (rowdata, rowindex, value)
	                {
	                    var result="";
	                    if(rowdata.pass=='2' && rowdata.sumImprove == ''){
	                       result="月计划待审核 ";
	                    }else if(rowdata.pass=='1' && rowdata.sumImprove == ''){
	                       result="月计划已通过 ";
	                    }else if(rowdata.pass=='0' && rowdata.sumImprove == ''){
	                       result="月计划未通过 ";
	                    }else if(rowdata.sumImprove=='2'){
	                       result="辅导总结待审核 ";
	                    }else if(rowdata.sumImprove=='1'){
	                       result="辅导总结已通过 ";
	                    }
	                    else if(rowdata.sumImprove=='0'){
	                       result="辅导总结未通过 ";
	                    }
	                    return result;
	                }},
				{ display: '辅导说明', name: 'remaker', align: 'left', minWidth :100,isSort:false}
                ], url:'<%=path%>/controller/coach/getCoachMonth.do',
                 parms:[{name:"starttime",value:$("#starttime").val()},
		               { name:"endtime",value:$("#endtime").val()},
		               { name:"AcceptorWorkId",value:$("#AcceptorWorkId").val()}
		               ],
                height:'100%', width:"auto", pageSize:10 ,rownumbers :true,
                
				
            });
            $("#pageloading").hide(); 
        });
        
        function f_search()
        {
            var parmItems=[
				{ name:"AcceptorWorkId",value:$("#AcceptorWorkId").val()},
				{ name:"starttime",value:$("#starttime").val()},
				{ name:"endtime",value:$("#endtime").val()},
				{ name:"pass",value:$("#pass").val()}
				];
			maingrid_gd.options.parms=parmItems;
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
        
        /**
        *重置查询条件
        **/
        function reset(){
           $("#starttime").val(today);
		   $("#endtime").val(toendday);
           $("#pass").val("");
        }
        
		/*************************************
		 * 打开月计划编辑界面
		 ************************************/
		function edit_coachInfo_main(eidtType,coachMianID){
			window.location.href="<%=path%>/controller/coach/getcoachtime.do";
		}
		/*************************************
		 * 打开计划设置界面
		 ************************************/
		function show_coachInfo_items(coachMianID){
			window.location.href="<%=path%>/controller/coach/getcocahbyid.do?pd=addinfo&coachmainId="+coachMianID;
		}
		
		function showinfo(coachMianID){
		    window.location.href="<%=path%>/controller/coach/getcocahbyid.do?pd=info&coachmainId="+coachMianID;
		}
		
		function updinfo(coachMianID){
		    window.location.href="<%=path%>/controller/coach/getcocahbyid.do?pd=xg&coachmainId="+coachMianID;
		}
			 function input_shuziyinwen(obj){
				obj.value=obj.value.replace(/[\W]/g,'');
			}	
			   //回车提交
	    function keydown(){
	    	if(isEnter()){
	    		user_search();
	    	}
		}
    </script> 
</head>
<!-- 
<body style="margin:0 auto; background-color:#FFFFFF; padding:5px;">
 -->
<body> 
	<div style="" onkeydown="keydown();">
		<div class="title_div" style="width:100%; margin-top:0px; display:none;"><label style="margin-left:15px;">查询条件</label></div>
		<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
			<table id="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr>
				<c:if test="${roleflag=='role_3'}">
					<td class="td_lable">质检员工号：</td><td class="td_value"><input name="AcceptorWorkId" type="text" disabled="disabled" id="AcceptorWorkId" value="${userworkid}"/></td>
				</c:if>
				<c:if test="${roleflag!='role_3'}">
					<td class="td_lable">质检员工号：</td><td class="td_value"><input maxlength="50" type="text" onkeyup="input_shuziyinwen(this);"  name="AcceptorWorkId" id="AcceptorWorkId"/></td>
				</c:if>
					<td class="td_lable">辅导开始时间：</td><td class="td_value"><input class="Wdate date_text" type="text" name="starttime" id="starttime" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endtime\');}',dateFmt:'yyyy-MM-dd'})" /></td>
					<td class="td_lable">辅导结束时间：</td><td class="td_value"><input class="Wdate date_text" type="text" name="endtime" id="endtime"  onfocus="WdatePicker({minDate:'#F{$dp.$D(\'starttime\');}',dateFmt:'yyyy-MM-dd '})" /></td>		
				</tr>
				<tr>
				<td class="td_lable">任务状态：</td><td class="td_value">
					     <select name="pass" id="pass" class="s_input">
								<option value="" >全部</option>
								<option value="2" >月计划待审核</option>
								<option value="1" >月计划已通过</option>
								<option value="0" >月计划未通过</option>
								<option value="5" >辅导总结待审核</option>
								<option value="4" >辅导总结已通过</option>
								<option value="3" >辅导总结未通过</option>
						</select>
					</td>
				</td>
				</tr>
				<tr>
					<td colspan="8" align="center">
						<shiro:hasPermission name='coach:query'>
						<input type="button" value="查询" onclick="f_search()"/>&nbsp;&nbsp;&nbsp;&nbsp;
						</shiro:hasPermission>
						<input type="button" value="重置" onclick="reset()"/>&nbsp;&nbsp;&nbsp;&nbsp;			
						<shiro:hasPermission name='coach:add'>	
						<input type="button" value="新增" onclick="edit_coachInfo_main('add',null);" />
						</shiro:hasPermission>
					</td>
				</tr>
			</table>			
		</div>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="task_sys_list" style="margin-top:10px;"></div>
	</div>
</body>
</html>
