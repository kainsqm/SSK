<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib uri="/WEB-INF/shiro.tld" prefix="shiro"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%> 
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
	   var stateData = [{ state: 1, text: '待受理员处理' }, { state: 2, text: '待受理员班长处理'},{ state: 3, text: '受理员班长已处理'}];
		$(function () {
		    today = getfirstDay();
			toendday= getnowDay();
			$("#starttime").val(today);
			$("#endtime").val(toendday);
            maingrid_gd= $("#task_sys_list").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false,name:'id', minWidth : 80, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
						h += "<shiro:hasPermission name='csrcoach:info'><a href='javascript:void(0);' onclick='showAddItemsInfo(\""+value+"\",\""+rowdata.coachmainId+"\");'>查看</a></shiro:hasPermission> ";
	                    return h;
	                }
                },
              	{ display: '受理员工号', name: 'acceptorWorkId', align: 'left', minWidth :130,isSort:false},
                { display: '辅导开始时间', name: 'starttime', align: 'left', minWidth :120,isSort:false},
				{ display: '辅导结束时间', name: 'endtime', align: 'left', minWidth :80,isSort:false},
				{ display: '周计划创建时间', name: 'createtime', align: 'left', minWidth :80,isSort:false},
				{ display: '周计划状态', name: 'state', align: 'left', minWidth :80,isSort:false,
					editor: { type: 'select', data: stateData, valueField: 'state'},
                     render: function (item)
                    {
                        if (parseInt(item.state) == 1) return '待受理员处理';
                        if (parseInt(item.state) == 2) return '待受理员班长处理';
                        if (parseInt(item.state) == 3) return '受理员班长已处理';
                     }
				
				}
                ], 
                url:'<%=path%>/controller/coach/getCoachInfo.do',
                 parms:[{name:"starttime",value:$("#starttime").val()},
		               { name:"endtime",value:$("#endtime").val()},
		                { name:"acceptorWorkId",value:$("#acceptorWorkId").val()}
		               ],
                height:'100%', width:"auto", pageSize:10 ,rownumbers :true,		
            });
            $("#pageloading").hide(); 
        });
        
        function f_search()
        {
            var parmItems=[
				{ name:"acceptorWorkId",value:$("#acceptorWorkId").val()},
				{ name:"starttime",value:$("#starttime").val()},
				{ name:"endtime",value:$("#endtime").val()}
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
        var roleflag=$("#roleflag").val();
        if(roleflag!="role_1"){
         $("#acceptorWorkId").val("");  
        }
           $("#AcceptorWorkId").val("");  
           $("#starttime").val(today);
		   $("#endtime").val(toendday);
        }
  
  function showAddItemsInfo(id,coachmainId){
			$.ligerDialog.open({
				height:450,
				width: 760,
				title : "辅导周计划",
				url:"<%=path%>/controller/coach/getcoachtime.do?tz=zjx&id="+id+"&coachmainId="+coachmainId, 
				showMax: false,
				showToggle: true,
				showMin: false,
				isResize: true,
				slide: false
			});
		}
  
		function input_shuziyinwen(obj){
				obj.value=obj.value.replace(/[\W]/g,'');
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
		<input type="hidden" value="${roleflag}" id="roleflag"></input>
			<table id="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr>
				<c:if test="${roleflag=='role_1'}">
					<td class="td_lable">受理员工号：</td><td class="td_value"><input maxlength="50" type="text" onkeyup="input_shuziyinwen(this);"  disabled="disabled" value="${userworkid}" name="acceptorWorkId" id="acceptorWorkId"/></td>
				</c:if>	
					<c:if test="${roleflag!='role_1'}">
					<td class="td_lable">受理员工号：</td><td class="td_value"><input maxlength="50" type="text" onkeyup="input_shuziyinwen(this);"  name="acceptorWorkId" id="acceptorWorkId"/></td>
				</c:if>
					<td class="td_lable">辅导开始时间：</td><td class="td_value"><input class="Wdate date_text" type="text" name="starttime" id="starttime" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endtime\');}',dateFmt:'yyyy-MM-dd'})" /></td>
					<td class="td_lable">辅导结束时间：</td><td class="td_value"><input class="Wdate date_text" type="text" name="endtime" id="endtime"  onfocus="WdatePicker({minDate:'#F{$dp.$D(\'starttime\');}',dateFmt:'yyyy-MM-dd'})"/></td>
				</tr>
				<tr>
					<td colspan="8" align="center">
					<shiro:hasPermission name='csrcoach:query'>
						<input type="button" value="查询" onclick="f_search()"/>&nbsp;&nbsp;&nbsp;&nbsp;
					</shiro:hasPermission>	
						<input type="button" value="重置" onclick="reset()"/>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>			
		</div>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="task_sys_list" style="margin-top:10px;"></div>
	</div>
</body>
</html>
