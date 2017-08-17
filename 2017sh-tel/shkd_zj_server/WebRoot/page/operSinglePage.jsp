<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
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
<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/js/jquery-1.9.1.js" type="text/javascript"></script> 
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
<script src="<%=path%>/js/util.js" type="text/javascript"></script>
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
	font-size: 12px;
	font-family: "黑体";
}

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
	var maingrid_gd="";
	var dialog=null;		
		$(function () {
		  dialog= frameElement.dialog; //调用页面的dialog对象(ligerui对象)
		    var olduserName=window.parent.document.getElementById("agentName").value;
            maingrid_gd= $("#workpaper_list").ligerGrid({
                columns: [                
				{ display: '', isSort: false, name:'userName', width :20, align:'center', render: function (rowdata, rowindex, value)
	                {
	                    var h = "<input type='radio' name='rd' style='margin-top:8px' onclick='fzname(\""+value+"\",\""+rowdata.smailWorkid+"\")'/>";
	                    return h;
	                }
                },
				{ display: '受理员工号', name: 'smailWorkid', align: 'left', width :268,isSort:false},
                { display: '受理员姓名', name: 'userName', align: 'left', width :268,isSort:false}
                ],
                 url:'<%=path%>/controller/user/listagentWorkid.do',
				sortName: 'workId',
				height:'100%', 
				width:"auto", 
				pageSize:10 ,
				rownumbers :true,
				alternatingRow: false,
				tree: { columnId: 'id1' },
				onAfterShowData:function(data){
				   var i=0;
				     for (var rowid in maingrid_gd.records)
                     {
		                var userName=maingrid_gd.records[rowid]["userName"];
		                   i++;
                             if(userName==olduserName)
		                     {
		                         $("input[name='rd']").eq(i-1).attr("checked","checked");
		                         maingrid_gd.select(rowid);  
		                         break;
	                         }
                          }
		               
                     }
                 //  isfirst=1;
            });
            $("#pageloading").hide(); 
        });
        
        function search(){
         var parmItems=[{ name:"workId",value:$("#workId").val()},
		               { name:"userName",value:$("#userName").val()},
		                    		];
			maingrid_gd.options.parms=parmItems;
			maingrid_gd.options.usePager =true;
			maingrid_gd.options.page='1';	
		    maingrid_gd.options.url ='<%=path%>/controller/user/listagentWorkid.do';
			maingrid_gd.loadData(f_getWhere());

	}

	function fzname(username,workid) {
		document.getElementById("name").value = username;
		document.getElementById("workid").value = workid;
	}
	function f_getWhere() {
		if (!maingrid_gd)
			return null;
		var clause = function(rowdata, rowindex) {
			var workId = $("#workId").val();
			return rowdata.recordId.indexOf(workId) > -1;
		};
		return clause;
	}
	function resets() {
		document.getElementById("workId").value = "";
		document.getElementById("userName").value = "";
	}

	function chooes() {
		var username = $("#name").val();
		var workid = $("#workid").val();
		if (username == null || username == "") {
			$.ligerDialog.warn('请选择人员');
			return false;
		} else {
			window.parent.f_xz(username, workid);
			 dialog.close();//关闭dialog 
		}

	}
	function input_shuziyinwen(obj) {
		obj.value = obj.value.replace(/[\W]/g, '');
	}
</script>
</head>
<!-- 
<body style="margin:0 auto; background-color:#FFFFFF; padding:5px; background-image: url(<%=path%>/img/login/bgmain.png);"> 
 -->
<body
	style="padding: 3px; overflow: hidden;border:0px solid; ">
	<div style="">
		<div class="title_div"
			style="width: 100%; margin-top: 0px; display: none;">
			<label style="margin-left: 15px;">查询条件</label>
		</div>
		<div align="left"
			style="margin-top: 10px; margin-left: 5px; padding-left: 10px;">
			<input type="hidden" id="name" /> <input type="hidden" id="workid" />
			<table id="search_table" cellpadding="3" cellspacing="3" width="85%"
				style="margin: 0 auto">
				<tr>
					<td class="td_lable">受理员工号：</td>
					<td class="td_value"><input maxlength="50"
						onkeyup="input_shuziyinwen(this);" id="workId" name="workId"
						type="text" /></td>
					<td class="td_lable">受理员姓名：</td>
					<td class="td_value"><input maxlength="50"
						onkeyup="noSpecial(this)" id="userName" name="userName"
						type="text" /></td>
				</tr>
				<tr>
					<td colspan="8" align="center"><input type="button" value="查询"
						onclick="search()" />&nbsp;&nbsp; <input type="button" value="重置"
						onclick="resets()" />&nbsp;&nbsp; <input type="button" value="确定"
						onclick="chooes()" /></td>
				</tr>
			</table>
		</div>
		<div class="l-loading" style="display: block" id="pageloading"></div>
		<div id="workpaper_list" style="margin-top: 10px;"></div>
	</div>
</body>
</html>
