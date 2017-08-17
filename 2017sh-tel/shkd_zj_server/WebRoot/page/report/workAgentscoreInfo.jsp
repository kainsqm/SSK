<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>常出错考题统计</title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerListBox.js" type="text/javascript"></script>
<script src="<%=path%>/js/My97DatePicker/WdatePicker.js" language="javascript" type="text/javascript"></script>
<script src="<%=path%>/js/utils/utils.js" type="text/javascript"></script>
<style type="text/css">
div.title_div {
	border-radius: 5px;
	text-align: left;
	margin-top: 5px;
	background-image: url(../../img/login/title_bg.jpg);
	border: solid 1px #D7D7D7;
	width: 90%;
	height: 30px;
	line-height: 30px;
	vertical-align: middle;
	font-size: 14px;
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
	width: 135px;
	height: 18px;
	line-height: 18px;
}

.search_table td {
	height: 30px;
	line-height: 30px;
}

.search_table td.td_lable {
	text-align: right;
}

.search_table td.td_value {
	text-align: left;
}

a {
	text-decoration: none;
	color: blue;
}
</style>
<script type="text/javascript"> 
	 var maingrid_gd ; 
	$(function () {
        maingrid_gd = $("#record_list").ligerGrid({
            columns: [     
	  			{display: '受理员工号', name: 'smailworkid', align: 'left', minWidth :130,isSort:false},
	  			{display: '质检时间', name: 'qctime', align: 'left', minWidth :130,isSort:false},
	  			{display: '设备编号', name: 'qualified', align: 'left', minWidth :100,isSort:false}
            ], 
            url:'<%=path%>/controller/qc/workAgentscoreInfo.do',
            parms:[
                {name:"qcworkid",value:$("#workid").val()},
              	{name:"qctime",value:$("#qctime").val()},
            ],
            sortName: 'taskId',height:'100%', width:"auto", pageSize:10 ,rownumbers :true
        });
        $("#pageloading").hide(); 
    });
	function f_search(){
        var parmItems=[
             {name:"qcworkid",value:$("#workid").val()},
             {name:"qctime",value:$("#qctime").val()}
      	];
     	maingrid_gd.options.parms=parmItems;
		maingrid_gd.options.usePager =true;
		maingrid_gd.options.page='1';
		maingrid_gd.options.newPage=1;
   		maingrid_gd.options.url = '<%=path%>/controller/qc/workAgentscoreInfo.do',
		maingrid_gd.loadData(f_getWhere());
	}

	function f_getWhere() {
		if (!maingrid_gd)
			return null;
		var clause = function(rowdata, rowindex) {
			var key = $("#pkAutoId").val();
			return rowdata.CustomerID.indexOf(key) > -1;
		};
		return clause;
	}
	
	function back(){
		history.go(-1);
	}
	
</script>
</head>
<body>
	<div style="">
		<div class="title_div" style="width: 100%; margin-top: 0px; display: none;">
			<label style="margin-left: 15px;">查询条件</label>
		</div>
		<div align="left" style="margin-top: 10px; margin-left: 5px; padding-left: 10px;">
			<table class="search_table" cellpadding="3" cellspacing="3" width="100%">
				<tr>
					<td class="td_lable">日期：</td>
					<td class="td_value">
						<input type="hidden" value="<%=request.getParameter("workid")%>"  id="workid"  />
						<input type="hidden" value="<%=request.getParameter("qctime")%>"  id="qctime"  />
						<input type="text" id="sdate" value="<%=request.getParameter("qctime")%>" disabled="disabled" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="返回" onclick="back()" />
					</td>
				</tr>
			</table>
		</div>
		<div class="l-loading" style="display: block" id="pageloading"></div>
		<div id="record_list" style="margin-top: 10px;"></div>
	</div>
</body>
</html>
