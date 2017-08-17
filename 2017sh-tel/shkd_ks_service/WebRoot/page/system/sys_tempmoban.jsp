<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>模板试卷新增页面</title>
<link href="../../lib/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link href="../../css/public.css" rel="stylesheet" type="text/css" />
<link href="../../lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet"
	type="text/css" />
<script src="../../lib/jquery/jquery-1.9.0.min.js"
	type="text/javascript"></script>
<script src="../../lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="../../lib/ligerUI/js/plugins/ligerDialog.js"
	type="text/javascript"></script>
<script src="../../lib/json2.js" type="text/javascript"></script>
<script src="../../lib/ligerUI/js/plugins/ligerGrid.js"
	type="text/javascript"></script>
<script src="../../lib/ligerUI/js/plugins/ligerPopupEdit.js"></script>
<script language="javascript" type="text/javascript"
	src="../../js/My97DatePicker/WdatePicker.js"></script>
<script src="../../js/ajaxSession.js" type="text/javascript"></script>
<style type="text/css">
div.title_div {
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
.l-text-wrapper{display:inline-block;top: 3px; margin-right: 3px;}
</style>
<script type="text/javascript"> 
				var manager;
				$(function ()
				{
					var combo2 = $("#fk_codetype_id").ligerComboBox({
			            width: 155,
			            selectBoxWidth: 200,
			            selectBoxHeight: 200, valueField: 'id',treeLeafOnly:false,
			            tree: { url: "<%=path%>/controller/quesTions/getDepartmentList.do",
							checkbox : false,
							ajaxType : 'post',
							idFieldName : 'id',
							parentIDFieldName : "pid",
							textFieldName : "text"
						}
					});
					manager = $("#workpaper_list").ligerGrid({
						columns: [
						{ display: '操作', isSort: false, minWidth : 180, align: 'center' ,name: 'id', isSort: false,render: function (rowdata, rowindex, value)
						{
							var h = "";
							h += "<a href='javascript:void(0);' onclick='deleteRow("+value+")'>删除选择模板试卷</a> ";
							return h;
						}
					},
					{ display: '业务分类', name: 'fkcodetypeId', minWidth: 100, align: 'left', isSort: false },
					{ display: '考题类型', name: 'quesType', minWidth: 100, align: 'left' , isSort: false},
					{ display: '数量', name: 'quesCount', minWidth: 100, align: 'left', isSort: false },
					{ display: '分数', name:'quesScore', minWidth: 100, align: 'left', isSort: false },
					{ display: '考题难度', name:'quesNandu', minWidth: 100, align: 'left', isSort: false },					
					
					], width: 'auto', height: '99%'
				}
				);
			//collapseAll();
			$("#pageloading").hide(); 
			
			
			$("#submitPrapre").click(function(){
				var examPaperName=$("#examPaperName").val();
				var examPaperRemark=$("#examPaperRemark").val();
				var examPaperType=$("#examPaperType").val();
				if(examPaperName==''){
					  $.ligerDialog.error('考卷名称不能为空');
					  return;
				}if(examPaperRemark==''){
					  $.ligerDialog.error('考卷备注不能为空');
					  return;
				}if(examPaperType==''||examPaperType=='0'){
					  $.ligerDialog.error('试卷类型不能为空');
					  return;
				}
				var exam={examPaperNames:examPaperName};
				$.ajax({
					  url: '<%=path%>/controller/examManager/checkExampaperName.do',
					  data:exam,
					  type:"post",
					  contentType: "application/x-www-form-urlencoded; charset=utf-8", 
					  dataType:"json",
					  async:false,
					  success: function(data){
						  if(data.result==0){
					$('#randomformAction').submit();
						  }else{
					$.ligerDialog.error('试卷名称已被使用');	  
						  }
					  }
				});
			});
		});
				function collapseAll()
				{
					manager.collapseAll();
				}
				function expandAll()
				{
					manager.expandAll();
				}
				function itemclick(item)
				{
					alert(item.text);
				}
		</script>
<script type="text/javascript">
			var rowNum=0;
			function addRow_withInsert(){
				var fkcodetypevalId=$("#fk_codetype_id_val").val();
				var quesCount=$("#ques_count").val();
				var quesScore=$("#ques_score").val();
				var quesType=$("#ques_type").val();
				var quesNandu=$("#ques_nandu").val();
				var fkcodetypeId=$("#fk_codetype_id").val();
				if(fkcodetypevalId==""){
					$.ligerDialog.warn('业务类型不能为空');
					$("#fk_codetype_id").focus();
					return;
				}
				if(quesCount==""){
					$.ligerDialog.warn('考题数量不能为空');
					$("#ques_count").focus();
					return;
				}
				if(quesScore==""){
					$.ligerDialog.warn('考题分数不能为空');
					$("#ques_score").focus();
					return;
				}
				
				if(quesType==""||quesType=="0"){
					$.ligerDialog.warn('考题类型不能为空');
					$("#ques_score").focus();
					return;
				}
				
				var qname="";
			    if(quesType==1){
			  	 	 qname="判断题";
			  	 }else if(quesType==2){
			  	 	 qname="判断改错题";
			  	 }else if(quesType==3){
			  	 	qname="单选题";
			  	 }else if(quesType==4){
			  	 	qname="多选题";
			  	 }else if(quesType==5){
			  	 	qname="不定项选择";
			  	 }else if(quesType==6){
			  	 	 qname="填空题";
			  	 }else if(quesType==7){
			  	 	 qname="问答题";
			  	 }
			    var queNanDuStr ="";
				if(quesNandu=="1"){
					queNanDuStr="难";
				}else if(quesNandu=="2"){
					queNanDuStr="中";
				}else if(quesNandu=="3"){
					queNanDuStr="易";
				}
				 var data =manager.getData();
				for(var i in data){
		           if(data[i].fkcodetypeId==fkcodetypeId&&data[i].quesNandu==queNanDuStr&&data[i].quesType==qname){
		        	   $.ligerDialog.warn("业务类型为："+fkcodetypeId+","+queNanDuStr+",题目类型为:"+qname+"的规则只能存在一条");
		        	   return;
		           }
				}
			 	$.ajax({
					  url: "<%=path%>/controller/exampaperRandom/isExistData.do",
					data : "fkcodetypevalId=" + fkcodetypevalId + "&quesType="
							+ quesType + "&quesNandu=" + quesNandu,
					type : "post",
					dataType : "json",
					async : false,
					success : function(msg) {
						var res = msg - quesCount;
						if (msg == 0) {
							alert(fkcodetypeId + "的" + qname + queNanDuStr
									+ "题量剩余:0!");
						} else if (res < 0) {
							res = msg;
							if (res == 0)
								alert(fkcodetypeId + " " + qname + queNanDuStr
										+ "题量剩余:0!");
							else
								alert(fkcodetypeId + " " + qname + queNanDuStr
										+ "题量剩余:" + res + ",您输入的题量超出了范围")
						} else {
							addNewRow(fkcodetypevalId, fkcodetypeId, qname,
									quesCount, quesScore, queNanDuStr);
						}
					}
				});
	}

	function addNewRow(fkcodetypevalId, fkcodetypeId, qname, quesCount,
			quesScore, queNanDuStr) {
		manager.addRow({
		    id:rowNum++,
			fkcodetypeIdval : fkcodetypevalId,
			fkcodetypeId : fkcodetypeId,
			quesType : qname,
			quesCount : quesCount,
			quesScore : quesScore,
			quesNandu : queNanDuStr
		});
	}

	function deleteRow(obj) {
		var row = manager.getRow(obj);
		/* if (!row) {
			$.ligerDialog.warn('请选择要删除的模板试卷');
			return;
		} */
		manager.remove(row);
		rowNum=rowNum-1;
	}

	function submit_ShoWindow() {
		if (manager.getRow(0) == undefined) {
			$.ligerDialog.warn("请先添加模板规则");
			return;
		}
		var fkcodetypeId="";
		var quesType="";
		var quesCount="";
		var quesScore="";
		var quesNandu="";
		var tempCount=0;
		var tempScore=0
		var totalScore=0;
		
		
		 var data =manager.getData();
			for(var i in data){
				fkcodetypeId+=data[i].fkcodetypeIdval+",";
				quesCount+=data[i].quesCount+",";
				tempCount=parseInt(data[i].quesCount);
				quesScore+=data[i].quesScore+",";
				tempScore=parseInt(data[i].quesScore);
			  	 if(data[i].quesType=="判断题"){
			  		 quesType+=1+',';
			  	 }else if(data[i].quesType=="判断改错题"){
			  		quesType+=2+',';
			  	 }else if(data[i].quesType=="单选题"){
			  		quesType+=3+',';
			  	 }else if(data[i].quesType=="多选题"){
			  		quesType+=4+',';
			  	 }else if(data[i].quesType=="不定项选择"){
			  		quesType+=5+',';
			  	 }else if(data[i].quesType=="填空题"){
			  		quesType+=6+',';
			  	 }else if(data[i].quesType=="问答题"){
			  		quesType+=7+',';
			  	 }
				
				if(data[i].quesNandu=="难"){
				quesNandu+=1+",";
				}else if(data[i].quesNandu=="中"){
				quesNandu+=2+",";
				}else if(data[i].quesNandu=="易"){
				quesNandu+=3+",";
				}else{
				quesNandu+=",";
				}
				totalScore+=tempCount*tempScore;
			}
		$("#fkcodetypeIdValue").val(fkcodetypeId);
		$("#quesTypeValue").val(quesType);
		$("#quesCountValue").val(quesCount);
		$("#quesScoreValue").val(quesScore);
		$("#quesNanduValue").val(quesNandu);
		$("#totalScoreValue").val(totalScore);
			
		var titleInfo = "新增模板试卷页面";
		$.ligerDialog.open({
			target : $('#addrandom'),
			height : '280',
			width : '600',
			title : titleInfo,
			showMax : false,
			showToggle : false,
			showMin : false,
			isResize : true,
			slide : false
		});
	}
	function hiddenDialog() {
		$(".l-dialog,.l-window-mask").css("display", "none"); //隐藏窗口和遮罩层
	}
	function cancel() {
		$("#addrandom").css("display", "none");
	}
	
	
</script>

</head>
	<body>
		<div class="title_div"
			style="width: 100%; margin-top: 0px; display: none;">
			<label style="margin-left: 15px;">查询条件</label>
		</div>
		<div align="left"
			style="margin-top: 4px; margin-left: 5px; padding-left: 10px;"
			id="div1">
			<table class="search_table" cellpadding="3" cellspacing="3"
				width="100%">
				<tr>
					<td class="td_lable">业务分类：</td>
					<td class="td_value"><input name="fk_codetype_id"
						id="fk_codetype_id"/><red>*</red></td>
					<td class="td_lable" width="60px">考题难度：</td>
					<td class="td_value"><select name="ques_nandu" id="ques_nandu"
						style="width: 135px;">
							<c:forEach items="${quesNanduInfo}" var="nandu">
								<c:choose>
									<c:when test="${nandu.itemFlag==2}">
										<option value="${nandu.itemFlag}" selected="selected">
											${nandu.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${nandu.itemFlag}">${nandu.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</select>
					<red>*</red></td>
				<td class="td_lable" width="60px">考题类别：</td>
					<td class="td_value"><select name="ques_type" id="ques_type"
						style="width: 140px;">
							<c:forEach items="${quesTypeInfo}" var="info">
								<option value="${info.itemFlag}">${info.name}</option>
							</c:forEach>
					</select>
					<red>*</red></td>
					<td class="td_lable" width="40px">分数：</td>
					<td class="td_value"><input style="width: 135px;" type="text"
						id="ques_score"
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
						onafterpaste="this.value=this.value.replace(/[^0-9]/g,'')" />
					<red>*</red></td>
					
				</tr>
				<tr>
						<td class="td_lable" width="60px">数量：</td>
					<td class="td_value"><input style="width: 135px;" type="text"
						id="ques_count"
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
						onafterpaste="this.value=this.value.replace(/[^0-9]/g,'')" />
					<red>*</red></td>

				</tr>

				<div style="margin-top: 20px;">
					<tr>
						<td colspan="8" align="center"><shiro:hasPermission name="ksmbsj:xz"><input type="button"
							value="新增" onclick="addRow_withInsert()" />&nbsp;&nbsp;&nbsp;&nbsp;</shiro:hasPermission>
							<shiro:hasPermission name="ksmbsj:tj"><input type="button" value="提交" id="insertOpen"
							onclick="submit_ShoWindow()" />&nbsp;&nbsp;&nbsp;&nbsp;</shiro:hasPermission> <input
							type="button" value="返回" id="backbutton"
							onclick="window.history.back()" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
			</table>
		</div>
		<br/>
		<div id="workpaper_list"></div>
		<div id="addrandom"
			style="width: 98%; margin: 5px; padding-right: 30px; display: none;"
			title="sss">
		<form action="<%=path %>/controller/exampaperRandom/insertExampaperRandom.do" id="randomformAction" method="post" name="randomformAction" >
			<table class="search_table" cellpadding="3" cellspacing="3"
				width="100%">
				<input type="hidden" id="fkcodetypeIdValue" name="fkcodetypeIdValue" >
				<input type="hidden" id="quesTypeValue" name="quesTypeValue" >
				<input type="hidden" id="quesCountValue" name="quesCountValue" >
				<input type="hidden" id="quesScoreValue" name="quesScoreValue" >
				<input type="hidden" id="quesNanduValue" name="quesNanduValue" >
				<input type="hidden" id="totalScoreValue" name="totalScoreValue" >
				<tr>
					<td class="td_lable">试卷名称：</td>
					<td class="td_value"><input type="text" id="examPaperName"
						name="examPaperName" />&nbsp;<red>*</red></td>
					<td class="td_value">试卷名称不能为空</td>
				</tr>
				<tr>
					<td class="td_lable">试卷备注：</td>
					<td class="td_value"><input type="text" id="examPaperRemark"
						name="examPaperRemark" />&nbsp;<red>*</red>
					<td class="td_value">试卷备注不能为空</td>
				</tr>
				<tr>
					<td class="td_lable">试卷类型：</td>
					<td class="td_value"><select name="examPaperType"
						style="width: 135px;" id="examPaperType">
							<c:forEach items="${quesPapersTyep}" var="paper">
								<option value="${paper.pkAutoId}">${paper.name}</option>
							</c:forEach>
					</select>&nbsp;<red>*</red></td>
					<td class="td_value">试卷类型不能为空</td>
				</tr>
				<tr style="margin-top: 50px;">
					<td colspan="8" align="center"><input id="submitPrapre"
						type="button" value="提交" /> <input id="updateForm" type="button"
						onclick="hiddenDialog();" value="取消" /></td>
				</tr>
			</table>
		</form>
		</div>
	</body>
</html>