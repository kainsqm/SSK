<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
<title>随机题库选题</title>
<meta charset="UTF-8">
<link href="../../lib/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link href="../../css/public.css" rel="stylesheet" type="text/css" />
<link href="../../lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet"
	type="text/css" />
<script src="../../lib/jquery/jquery-1.9.0.min.js"
	type="text/javascript"></script>
<script src="../../lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="../../lib/ligerUI/js/plugins/ligerPopupEdit.js"></script>
<script src="../../js/utils.js"></script>
<script type="text/javascript"
	src="../../js/My97DatePicker/WdatePicker.js"></script>
<script src="../../js/ajaxSession.js" type="text/javascript"></script>

<style type="text/css">div.title_div {
	border-radius: 5px;
	text-align: left;
	margin-top: 5px;
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


input.date_text {
	border-radius: 5px;
	width: 135px;
	height: 18px;
	line-height: 18px;
}

table td {
	height: 30px;
	line-height: 30px;
}

table .search_table td.td_lable {
	text-align: right;
}

table .search_table td.td_value {
	text-align: left;
}

a {
	text-decoration: none;
	color: blue;
}

red {
	color: red;
}
/*#workpaper_list{width: 100% !important;	}*/
</style>
<SCRIPT type="text/javascript">
	/*	$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			$("#selectAll").bind("click", selectAll);
		});*/
</SCRIPT>
<script type="text/javascript">
	$(function() {

		$("#layout1").ligerLayout({
			leftWidth : 200,
			height : '99%'
		});
	});
</script>
<script type="text/javascript">

	var manager;
	$(function() {
		var combo2 = $("#fk_codetype_id").ligerComboBox({
            width: 155,
            selectBoxWidth: 200,
            selectBoxHeight: 200, valueField: 'id',treeLeafOnly:false,
            tree: { url: "<%=path%>/controller/quesTions/getDepartmentList.do",
           	 checkbox: false, ajaxType: 'post', idFieldName: 'id', parentIDFieldName:"pid",textFieldName:"text"
           	 }
        });
		 var now=new Date();
         var year=now.getFullYear();
         var month=now.getMonth();
         if(parseInt(month)<10) month = "0"+""+(month+1);
         $("#insert_time_start").val(year+"-"+(month)+"-01 00:00:00");
         $("#insert_time_end").val(new Date().format("yyyy-MM-dd hh:mm:ss"));
         
		$("#query").click(function(){
			f_search();
		});
		

		
		manager = $("#workpaper_list").ligerGrid({
			checkbox : true,
			columns : [ {
				display : '编号',
				name : 'pkAutoId',
				minWidth : 150,
				align : 'left',
				isSort : false
			}, {
				display : '题目内容',
				name : 'quesContent',
				minWidth : 100,
				align : 'left',
				isSort : false
			}, {
				display : '题目类型',
				name : 'quesType',
				minWidth : 100,
				align : 'left',
				isSort : false,
				 render: function (item)
                    {
                        if (parseInt(item.quesType) == 1) return '判断题';
                        if (parseInt(item.quesType) == 2) return '判断改错题';
                        if (parseInt(item.quesType) == 3) return '单选题';
                        if (parseInt(item.quesType) == 4) return '多选题';
                        if (parseInt(item.quesType) == 5) return '不定项选择';
                        if (parseInt(item.quesType) == 6) return '填空题';
                        if (parseInt(item.quesType) == 7) return '问答题';
                        if (parseInt(item.quesType) == 8) return '案例分析题';
					},
			}, {
				display : '题目难度',
				name : 'quesNandu',
				minWidth : 100,
				align : 'left',
				isSort : false,
                render: function (item)
                {
                    if (parseInt(item.quesNandu) == 1) return '难';
                    if (parseInt(item.quesNandu) == 2) return '中';
                    if (parseInt(item.quesNandu) == 3) return '易';
				},
			}, {
				display : '所属分类',
				name : 'businessType',
				minWidth : 100,
				align : 'left',
				isSort : false
			}, {
				display : '是否有效',
				name : 'quesStatus',
				minWidth : 100,
				align : 'left',
				isSort : false,
				 render: function (item)
                    {
                        if (parseInt(item.quesStatus) == 1) return '有效';
                        if (parseInt(item.quesStatus) == 0) return '无效';
					},
			}, {
				display : '录入时间',
				name : 'insertTime',
				minWidth : 100,
				align : 'left',
				isSort : false
			}, {
				display : '录入人',
				name : 'fkInsertUserName',
				minWidth : 100,
				align : 'left',
				isSort : false,
			} ],
		url:'<%=path%>/controller/optionQue/getQuesTions.do',
		width : 'auto',
		height : '100%',
		rownumbers : true,isChecked: f_isChecked, onCheckRow: f_onCheckRow, onCheckAllRow: f_onCheckAllRow
	});
		//collapseAll();
		$("#pageloading").hide();
	});
	
	function f_search() {
		var date1=$('#insert_time_start').val();
			var date2=$('#insert_time_end').val();
			if(date1==""){
				 $.ligerDialog.warn("开始时间不能为空");
				 return;
			}
			if(date2==""){
				 $.ligerDialog.warn("结束时间不能为空");
				 return;
			}
			var startTime=new Date(date1);
			var endTime=new Date(date2);
			var date=endTime.getTime()-startTime.getTime();
			var days=Math.floor(date/(24*3600*1000));	
			if(days>365){
				 $.ligerDialog.warn("开始时间与结束时间不能超过一年");
				 return;
			}
   		var parmItems=[{ name:"quesContent",value:$('#ques_content').val()},
   		               { name:"fkInsertUserId",value:$('#fkInsertUserId').val()},
   		               { name:"startInsertTime",value:$('#insert_time_start').val()},
   		               { name:"endInsertTime",value:$('#insert_time_end').val()},
   		               { name:"fkCodetypeId",value:$('#fk_codetype_id_val').val()},
   		               { name:"quesNandu",value:$('#ques_nandu').val()},
   		               { name:"exampaperId",value:$('#fkExamPaperid').val()},
   		               { name:"quesType",value:$('#ques_type').val()}];
	   		manager.options.parms=parmItems;
	   		manager.options.usePager =true;
	   		manager.options.newPage=1;
	   		manager.options.page='1';
			manager.options.url = '<%=path%>/controller/examManager/getQuesTions.do';
			manager.loadData(f_getWhere);
	}
	
	function f_getWhere() {
		if (!manager)
			return null;
		var clause = function(rowdata, rowindex) {
			var key = $("#bumen").val();
			return rowdata.bumen.indexOf(key) > -1;
		};
		return clause;
	}
	function collapseAll() {
		manager.collapseAll();
	}
	function expandAll() {
		manager.expandAll();
	}
	function itemclick(item) {
		alert(item.text);
	}
	function resets(){
		$('#ques_content').val('');
		$('#fk_insert_user_id').val('');
		$('#insert_time_start').val('');
		$('#insert_time_end').val('');
		$('#fk_codetype_id').val('');
		$('#ques_nandu').val('');
		$('#ques_type').val('');
		$('#ques_status').val('');
		$("#fk_codetype_id").ligerGetComboBoxManager().setText("");
		$("#fk_codetype_id_val").val("");
		$("#fkExamPaperName").val("");
		$("#fkExamPaperid").val("");
		$(".l-selected").removeClass("l-selected");
		$("#fkInsertUserName").val("");
		$("#fkInsertUserId").val("");
		 var now=new Date();
         var year=now.getFullYear();
         var month=now.getMonth();
         if(parseInt(month)<10) month = "0"+""+(month+1);
         $("#insert_time_start").val(year+"-"+(month)+"-01 00:00:00");
         $("#insert_time_end").val(new Date().format("yyyy-MM-dd hh:mm:ss"));
         
	}
    function f_onCheckAllRow(checked)
    {
        for (var rowid in this.records)
        {
            if(checked)
                addCheckedCustomer(this.records[rowid]['pkAutoId']);
            else
                removeCheckedCustomer(this.records[rowid]['pkAutoId']);
        }
    }
	function openChooseExam(){
		$.ligerDialog.open({
               height:420,
               width: 880,
               title : '历史试卷',
               url: '<%=path%>/controller/examManager/toChooseExamPage.do', 
               showMax: false,
               showToggle: true,
               showMin: false,
               isResize: true,
               slide: false,
               data: {
                   name: "userName"
               },
               //自定义参数
               myDataName: "自定义参数"
        });
	}
    /*
    该例子实现 表单分页多选
    即利用onCheckRow将选中的行记忆下来，并利用isChecked将记忆下来的行初始化选中
    */
    var checkedCustomer = [];
    function findCheckedCustomer(pkAutoId)
    {
        for(var i =0;i<checkedCustomer.length;i++)
        {
            if(checkedCustomer[i] == pkAutoId) return i;
        }
        return -1;
    }
    function addCheckedCustomer(pkAutoId)
    {
        if(findCheckedCustomer(pkAutoId) == -1)
            checkedCustomer.push(pkAutoId);
    }
	function openChooseUser(){
		$.ligerDialog.open({
               height:420,
               width: 820,
               title : '用户管理',
               url: '/shkd_ks_service/page/system/choose_user.jsp', 
               showMax: false,
               showToggle: true,
               showMin: false,
               isResize: true,
               slide: false,
               data: {
                   name: "userName"
               },
               //自定义参数
               myDataName: "自定义参数"
        });
	}
    
    function removeCheckedCustomer(pkAutoId)
    {
        var i = findCheckedCustomer(pkAutoId);
        if(i==-1) return;
        checkedCustomer.splice(i,1);
    }
    function f_isChecked(rowdata)
    {
        if (findCheckedCustomer(rowdata.pkAutoId) == -1)
            return false;
        return true;
    }
    function f_onCheckRow(checked, data)
    {
        if (checked) addCheckedCustomer(data.pkAutoId);
        else removeCheckedCustomer(data.pkAutoId);
    }
    function f_getChecked()
    {
    	if(checkedCustomer==""){
    		 $.ligerDialog.warn('请先选择考题');
    		 return;
    	}
    	$.ajax({
			  url: "<%=path%>/controller/optionQue/inputRandomQuesArea.do?checkedCustomer="+checkedCustomer,
			  type:"post",
			  dataType:"json",
			  async:false,
			  success: function(flag){
				  location.href = "/shkd_ks_service/controller/optionQue/queryRandomQuesArea.do";
			   }
			});
		
    }
    
    function f_queryQues(){
  	  location.href = "/shkd_ks_service/controller/optionQue/queryRandomQuesArea.do";
    }
    
    //回车查询
	    function keydown(){
	    	if(isEnter()){
	    		f_search();
	    	}
		}
    
</script>


</head>
	<body onkeydown="keydown();">
		<div align="left">
			<table class="search_table" cellpadding="3" cellspacing="3"
				style="margin: 10px 0 0 15px;" width="100%">
				<tr>	
					<td class="td_lable" width="60px">题目描述：</td>
					<td class="td_value"><input  type="text"
						id="ques_content" />
						<input type="hidden" id="fkInsertUserId" name="fkInsertUserId"  />
				        <input type="hidden" id="fkExamPaperid" name="fkExamPaperid" />
						</td>
						<td class="td_lable"width="90px">录入人：</td><td class="td_value"><input  id="fkInsertUserName" onclick="openChooseUser()" name="fkInsertUserName" type="text"/>
					<a style="cursor: pointer;"><img src="../../img/icon/delete.gif" height="14" onclick='$("#fkInsertUserName").val("");$("#fkInsertUserId").val("")' alt="编辑"></a></td>
				<td class="td_lable" width="85px">录入开始时间:</td>
				<td class="td_value"><input class="Wdate date_text" type="text"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'insert_time_end\')||\'2120-10-01\'}'})"
					id="insert_time_start" /></td>
				<td class="td_lable" width="90px">录入结束时间：</td>
				<td class="td_value"><input class="Wdate date_text" type="text"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'insert_time_start\')}',maxDate:'2120-10-01'})"
					 id="insert_time_end" /></td>
				</tr>
				<tr>
					<td class="td_lable">业务分类：</td>
					<td class="td_value"><input name="fk_codetype_id"
						id="fk_codetype_id" type="text"/></td>
					<td class="td_lable">考题难度：</td>
					<td class="td_value"><select name="ques_nandu" id="ques_nandu"
						style="width: 135px;">
							<c:forEach items="${quesNanduInfo}" var="mandu">
								<option value="${mandu.itemFlag}">${mandu.name}</option>
							</c:forEach>
					</select></td>
					<td class="td_lable">考题类别：</td>
					<td class="td_value"><select name="ques_type" id="ques_type"
						style="width: 140px;">
							<c:forEach items="${quesTypeInfo}" var="info">
								<option value="${info.itemFlag}">${info.name}</option>
							</c:forEach>
					</select></td>
								<td class="td_lable"width="90px">历史试卷：</td><td class="td_value">
								<input id="fkExamPaperName" onclick="openChooseExam()" name="fkExamPaperName" type="text"/>
					<a style="cursor: pointer;"><img src="../../img/icon/delete.gif" height="14" onclick='$("#fkExamPaperName").val("");$("#fkExamPaperid").val("");' alt="编辑"></a></td>
				</tr>

				<tr>
					<td colspan="8" align="center"><shiro:hasPermission name="ksmbsj:ktcx"><input type="button" id="query"
						value="查询" />&nbsp;&nbsp;&nbsp;</shiro:hasPermission>
						<input type="button" onclick="resets()" value="重置"  >
						<shiro:hasPermission name="ksmbsj:frsjktq"><input type="button"
						value="查看已选随机试题" id="insertOpen" width="110" onclick="f_queryQues()"
						style="width: 119px;" />&nbsp;&nbsp;&nbsp;&nbsp;</shiro:hasPermission><shiro:hasPermission name="ksmbsj:frsjktq"><input type="button"
						value="放入随机考题区" id="insertOpen" onclick="f_getChecked()"
						 />&nbsp;&nbsp;&nbsp;</shiro:hasPermission>
						<input type="button"value="返回" onclick="location.href='<%=path%>/controller/exampaper/toManagerPage.do'" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
			</table>
			</div>
			<div id="workpaper_list"></div>
	</body>
</html>

