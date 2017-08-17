<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>表单插件-修改</title>
<link href="../../lib/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link href="../../css/public.css" rel="stylesheet" type="text/css" />
<link href="../../lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet"
	type="text/css" />
<script src="../../js/ajaxSession.js" type="text/javascript"></script>
<script src="../../lib/jquery/jquery-1.9.0.min.js"
	type="text/javascript"></script>
<script src="../../lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="../../lib/ligerUI/js/plugins/ligerPopupEdit.js"></script>
<script language="javascript" type="text/javascript"
	src="../../js/My97DatePicker/WdatePicker.js"></script>
<script src="../../lib/ligerUI/js/plugins/ligerRadio.js"
	type="text/javascript"></script>
<script src="../../lib/ligerUI/js/plugins/ligerDialog.js"
	type="text/javascript"></script>
<script src="../../lib/jquery-validation/jquery.metadata.js"></script>
<script src="../../js/ajaxSession.js" type="text/javascript"></script>
<script src="../../js/kindeditor-4.1.10/kindeditor.js"></script>
<script src="../../js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script src="../../js/common.js"></script>
<script src="<%=path %>/js/utils.js"></script>

<script type="text/javascript">
var quesAnswerAddEditor;
var quesContentAddEditor;
	$(function(){
		quesAnswerAddEditor = TT
					.createEditor("#form1[name=quesAnswer]");
		quesContentAddEditor = TT
					.createEditor("#form1[name=quesContent]");
			$("#form1 input:radio").ligerRadio();
			$("#form").ligerForm({});
			
		initHint('${questions.quesType}');
		
		$("#quesType").change(function(){
			var questType=$("#quesType").val();
			var h = [];
			if(questType==1 || questType==2){
				h.push('<span style="color:red">提示</span>：判断题和判断改错题的答案只能输（Y/N）');
			}
			if(questType==3 || questType==4 ||questType==5){
				h.push('<span style="color:red">提示</span>：可选项中每个答案以分号隔开，答案只能输入（A-F）');
			}
			if(questType==6){
				h.push('<span style="color:red">提示</span>：填空题格式每4个连续的\"_\"代表一个填空!<p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;填空题多个答案用#隔开');
			}
			if(questType==7){
				h.push('');
			}
			$("#pointout").html(h.join());
			if(questType==3||questType==4||questType==5){
				$("#quesPicktr").css('display' ,''); 
				$("#quesCorrectAnswer").css('display' ,'none'); 
			}else if(questType==2){
				$("#quesCorrectAnswer").css('display' ,''); 
			}else{
				$("#quesPicktr").css('display' ,'none'); 
				$("#quesCorrectAnswer").css('display' ,'none'); 
			}
		});
		
		$("#subQuest").click(function(){
			quesAnswerAddEditor.sync();
			quesContentAddEditor.sync();
			//可选项
			var quesPick=$("#quesPick").val().replace(/；/g ,';');
			
			//考试答案	
			var quesAnswer1=$("#quesAnswer").val().replace(/<\/?[^>]*>/gim,"");
			var quesAnswer=quesAnswer1.replace(/\s/g,"");
			
			//考题内容
			var quesContent1=$("#quesContent").val().replace(/<\/?[^>]*>/gim,"");
			var quesContent=quesContent1.replace(/\s/g,"");
			
			//改错题改错答案
			var quesCorrectAnswer=$("#quesCorrectAnswer").val();
			//业务分类
			var fk_codetype_id=$("#fk_codetype_id").val();
			var fk_codetype_id_val=$("#fk_codetype_id_val").val();
			if(fk_codetype_id==""){
				  $.ligerDialog.error('业务分类不能为空');
				  return ;
			}
			//考试难度
			var quesNandu=$("#quesNandu").val();
			if(quesNandu==""||quesNandu==0){
				  $.ligerDialog.error('考试难度不能为空');
				  return ;
				
			}
			//试题类型
			var quesType=$("#quesType").val();
			if(quesType==""||quesType==0){
				  $.ligerDialog.error('试题类型不能为空');
				  return ;
				  
			}
			//试题分数
			var quesScore=$("#quesScore").val();
			if(quesScore==""){
				  $.ligerDialog.error('试题分数不能为空');
				  return;
			}
			if(quesContent==""){
				  $.ligerDialog.error('考题内容不能为空');
				  return;
			}
			if(quesAnswer==""){
				  $.ligerDialog.error('请输入考试答案');
				  return;
			}
			if(quesType==3||quesType==4||quesType==5){
				if(quesPick==""){
				  $.ligerDialog.error('选择题	须提供可选项');
				  return ;
				}
			}
			if(quesType==1||quesType==2){
				if(quesAnswer!="Y"&&quesAnswer!="N"){
				  $.ligerDialog.error('判断题和判断改错题的答案只能输（Y/N）！');
				  return;
				}
			}
			if(quesType==3){
				var sp=quesPick.split(';');
				var alertMsg="单选题的答案必须在可选项中(A,B,C,D,E)并且必须为大写字母！";
				if(sp.length==1&&quesAnswer!='A'){$.ligerDialog.error(alertMsg);return false;}
				if(sp.length==2&&quesAnswer!='A'&&quesAnswer!='B'){$.ligerDialog.error(alertMsg);return false;}
				if(sp.length==3&&quesAnswer!='A'&&quesAnswer!='B'&&quesAnswer!='C'){$.ligerDialog.error(alertMsg);return false;}
				if(sp.length==4&&quesAnswer!='A'&&quesAnswer!='B'&&quesAnswer!='C'&&quesAnswer!='D'){$.ligerDialog.error(alertMsg);return false;}
				if(sp.length==5&&quesAnswer!='A'&&quesAnswer!='B'&&quesAnswer!='C'&&quesAnswer!='D'&&quesAnswer!='E'){$.ligerDialog.error(alertMsg);return false;}
				if(sp.length==6&&quesAnswer!='A'&&quesAnswer!='B'&&quesAnswer!='C'&&quesAnswer!='D'&&quesAnswer!='E'&&quesAnswer!='F'){$.ligerDialog.error(alertMsg);return false;}
			}
			if(quesType==4){
				var sp=quesPick.split(';');
				if(sp.length<2){
					  $.ligerDialog.error('多选题的可选项不能少于2个！');
					return;
				}
				var quesIsBol = false;
				if(quesAnswer){
					var spAlen = quesAnswer.length;
					for(var i=0;i<spAlen;i++){
						if("Z;X;C;V;B;N;M;A;S;D;F;G;H;J;K;L;Q;W;E;R;T;Y;U;I;O;P".indexOf(quesAnswer.substring(i,1+i))==-1){
							quesIsBol = true;
							break;
						}
					}
				}
				if(quesIsBol){
					  $.ligerDialog.error('多选题的答案必须为大写字母!');
					return false;
				}
			}
			
			
		if(quesType==6){
			var sp=$("#quesAnswer").val().split('#');
			var spNum = sp.length;
			if(sp[spNum-1]=="")spNum=spNum-1;
			var qContent = quesContent;
			var tkNum = qContent.length - qContent.replaceAll("____","").length;
			if(tkNum<4){
				  $.ligerDialog.error('填空格式不正确，每4个连续的\"_\"代表一个填空!');
				return false;
			}
			if(spNum!=(tkNum/4)){
				  $.ligerDialog.error("填空题填空(每4个连续的\"_\"代表一个填空)需和答案数量一致! \n目前：填空"+(tkNum/4)+"个,答案"+spNum+"个");
				return false;
			}
		}
		$("#quesContent").val(quesContent);
		$("#quesAnswer").val(quesAnswer);
		$("#quesPick").val(quesPick);
		var data1=$('#form1').serialize();
		$.ajax({
			  url: '<%=path%>/controller/quesTions/checkQuestions.do',
			  type:"post",
			  data:data1,
			  dataType:"json",
			  async:false,
			  success: function(html){
				  if(html.flag=='yes'){
					  $.post("<%=path%>/controller/quesTions/updateQuesTions.do",
								$("#form1").serialize(), function(data){
								if('200'){
									$.ligerDialog.success('修改成功', '提示', function(yes) {
										window.parent.f_search();
										frameElement.dialog.close();
									});
								}else if('400'){
									$.ligerDialog.error('修改失败', '提示', function(yes) {
										window.parent.f_search();
										frameElement.dialog.close();
									});
									
								}else{
									$.ligerDialog.error('系统错误，请稍后再试', '提示', function(yes) {
										window.parent.f_search();
										frameElement.dialog.close();
									});
									
								}
						});
				  }else{
					  $.ligerDialog.warn("考题信息重复，无法修改");
					  return;
				  }
			   }
			});
			
		});
	});
	
	$(function() {
		var insertTime=$("#insertTime").val();
		if(insertTime==""){
			 var date = new Date();
			$("#insertTime").val(date.format("yyyy-MM-dd hh:mm:ss"));
		}
		    
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
		$("#fk_codetype_id option").each(function(){
			if($('#yewufenlei').val()==this.value){
				$(this).attr("selected",true);
				return;
			}
		});
		
	});
	
	function initHint(questType){
		var hint;
		if(questType==1 || questType==2){
			hint = '<span style="color:red">提示</span>：判断题和判断改错题的答案只能输（Y/N）';
		}else if(questType==3 || questType==4 ||questType==5){
			hint = '<span style="color:red">提示</span>：可选项中每个答案以分号隔开，答案只能输入（A-F）';
		}else if(questType==6){
			hint = '<span style="color:red">提示</span>：填空题格式每4个连续的\"_\"代表一个填空!<p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;填空题多个答案用#隔开';
		}else{
			hint = '<span style="color:red">提示</span>';
		}
		$("#pointout").html(hint);
	}
	// 验证完成后讲富文本编辑器内容进行刷新
</script>


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

html,body {
	overflow: auto !important;
}

red {
	color: red;
}
</style>
</head>

<body style="padding: 10px">

	<form id="form1" >
		<table>
			<tr>
			<input id="pkAutoId" name="pkAutoId" type="hidden" value="${questions.pkAutoId}" >
			<input id="businessId" name="businessId" type="hidden" value="${questions.businessId}" >
				<td><label for="textbox1">业务分类：</label></td>
				<td><input id="fk_codetype_id" value="${questions.businessType}" name="fkCodetypeId"
					class="td_value" /></td>

				<td class="td_lable">录入日期：</td>
				<td class="td_value"><input id="insertTime" name="insertTime"
					class="Wdate date_text" type="text"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					value="${questions.insertTime}" />&nbsp;
				<td><label for="pwd1">录入人：</label></td>
				<input id="fkInsertUserId" name="fkInsertUserId" type="hidden"  >
				<td><input id="fkInsertUserName" name="fkInsertUserName"
					type="text" validate="{required:true}"
					value="${questions.fkInsertUserName}" disabled="disabled" /></td>
			</tr>

			<tr>
				<td><label for="pwd1">是否有效：</label></td>
				<td><input id="quesStatus" name="quesStatus" type="radio"
					class="ui-password" validate="{required:true}" value="1"
					checked="checked" />是 <input id="quesStatus" name="quesStatus"
					type="radio" class="ui-password" validate="{required:true}"
					value="0" />否</td>
				<td align="left" class="l-table-edit-td">试题类型：</td>
				<td align="left" class="l-table-edit-td"><select
					name="quesType" id="quesType" style="width: 135px;">
						<c:forEach items="${quesTypeInfo}" var="info">
							<c:choose>
								<c:when test="${info.itemFlag==questions.quesType}">
									<option value="${info.itemFlag}" selected="selected">
										${info.name}</option>
								</c:when>
								<c:otherwise>
									<option value="${info.itemFlag}">${info.name}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select></td>
				<td><label for="pwd1">试题难度：</label></td>
				<td><select name="quesNandu" id="quesNandu"
					style="width: 135px;">
						<c:forEach items="${quesNanduInfo}" var="nandu">
							<c:choose>
								<c:when test="${nandu.itemFlag==questions.quesNandu}">
									<option value="${nandu.itemFlag}" selected="selected">
										${nandu.name}</option>
								</c:when>
								<c:otherwise>
									<option value="${nandu.itemFlag}">${nandu.name}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>

				</select></td>
			</tr>
			<tr>
			<td><label for="pwd1">考题分数：</label></td>
				<td><input id="quesScore" name="quesScore" value="${questions.quesScore}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
					type="text" validate="{required:true}" /></td>
			</tr>
			<tr>
				<td><label for="textarea1">考题编辑：</label></td>
				<td colspan="5"><textarea id="quesContent"visibility:hidden name="quesContent" 
						class="ui-textarea"
						style="resize: auto; visibility:hidden; width: 700px; height: 400px;"
						validate="{required:true}">${questions.quesContent}</textarea></td>
			</tr>
			<c:choose>  
				<c:when test="${questions.quesType == '3' or questions.quesType == '4' or questions.quesType == '5'}">
				<tr id="quesPicktr">
				</c:when>
				<c:otherwise>
					<tr style="display: none" id="quesPicktr">
				</c:otherwise>
			</c:choose>  
				<td><label for="textarea1">可选项：</label></td>
				<td colspan="5"><textarea id="quesPick" name="quesPick"
						class="ui-textarea"
						style="resize: auto; width: 700px; height: 100px;"
						validate="{required:true}">${questions.quesPick}</textarea></td>
			</tr>
			<tr>
				<td><label for="textarea1">答案：</label></td>
				<td colspan="5"><textarea id="quesAnswer" name="quesAnswer"
						class="ui-textarea"
						style="resize: auto; width: 700px; height: 200px;"
						validate="{required:true}">${questions.quesAnswer}</textarea></td>
			</tr>
				<tr style="display: none" id="quesCorrectAnswer">
				<td><label for="textarea1">改错信息：</label></td>
				<td colspan="5"><textarea id="quesCorrectAnswer" name="quesCorrectAnswer"
						class="ui-textarea"
						style="resize: auto; width: 700px; height: 100px;"
						validate="{required:true}">${questions.quesCorrectAnswer}</textarea></td>
			</tr>
			<tr><td colspan="5" id="pointout"><span style="color:red">提示</span></td></tr>
			<tr>
				<td><br /></td>
			</tr>
			<td colspan="8" align="center"><input type="button" value="提交" id="subQuest"/>&nbsp;&nbsp;&nbsp;&nbsp; <input type="button"
				value="取消" id="insertOpen" onclick="frameElement.dialog.close();" /></td>
		</table>
	</form>
</body>
</html>
