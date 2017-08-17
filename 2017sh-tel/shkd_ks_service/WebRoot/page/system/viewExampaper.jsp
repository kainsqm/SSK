<%@page contentType="text/html; charset=utf-8" import="java.util.*,cn.sh.ideal.model.tQuestions" pageEncoding="UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<%
	String path = request.getContextPath();
  String exampaperId =(String)request.getAttribute("exampaperId");
  String[] sc = (String[])request.getAttribute("sc");
  String selectQues = (String)request.getAttribute("selectQues");
  String selectQuesScore = (String)request.getAttribute("selectQuesScore");
  String scoreTotal = (String)request.getAttribute("scoreTotal");
  String exampaperRemark=(String)request.getAttribute("exampaperRemark");
  String tSysRightId = request.getParameter("tSysRightId");
  String isIndex = request.getAttribute("isIndex").toString();//是否乱序
  String examPaperType = request.getAttribute("examPaperType").toString();//是否乱序
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	 <title>考题管理</title>
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />  
	<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="<%=path%>/js/ajaxSession.js" type="text/javascript"></script>
    
</head>

<style type="text/css">
	body{
		overflow:auto;
	}
.button{ 
		    background: -webkit-gradient(linear, 0% 0%, 0% 100%,from(#01aacf), to(#0165a2)) !important;
		    color: #FFFFFF !important;
		    border: 1px solid #0297bf !important;
		    padding: 0 15px;
		    border-radius: 3px;
		    width: initial !important;
		    height: 25px !important;
    	}
<!--
#addExampaper {
	position: absolute;
	left: expression((body.clientWidth-300)/2-150);
	top: expression((body.clientHeight-90)/2-100);
	width:620;
	height:160;
	z-index:999;
	margin:0;
	background-color: #CACAFF;
	overflow: hidden;
}
.one{
	text-align:left;//居中
	font-size:12px;//字体大小为12像素
	line-height:20px;
	height:20px;
	font-weight:bold;//加粗
	width:200px;
	margin:auto;
}
.exampaper {border:3px double #8ab8da;width:100%;}
.exampaper .title {
	text-align:center;
	height:50px;
	line-height:50px;
	font-family:幼圆,宋体;
	font-weight:bold;
	font-size:20pt;
	margin: auto;
	color:#2e629d;
	background-color:#f8f8f8;
}
.exampaper .qustype{
	border-top:1px solid #8ab8da;
	border-left:0;
	border-right:0;
	border-bottom:0;
	width:100%;
	border-collapse:collapse;
	color:#5a5a5a;
}
.exampaper .qustype td{
	border-top:1px solid #bbd3e4;
	height:22px;
}
.exampaper .qustype .qust{
	height:24px;line-height:24px;font-size:11pt;font-family:Arial,宋体;color:#4f4f4f;background-color:#f8f8f8;
}
.exampaper .qustype .answ{
	font-size:10pt;font-family:Arial,宋体;padding-left:32px;
}
-->
</style>
<script language="javascript">

	function showValue(a){
		/*alert(a.name);
		alert(a.value);*/
	}
	//根据不同的考题类型动态增加行
	var num1 = 1;
	var num2 = 1;
	var num3 = 1;
	var num4 = 1;
	var num5 = 1;
	var num6 = 1;
	var num7 = 1;
	var num8 = 1;
	var quesAnswer1 = 1;
	var quesAnswer2 = 1;
	var quesAnswer3 = 1;
	var quesAnswer4 = 1;
	var quesAnswer5 = 1;
	var quesAnswer6 = 1;
	var quesAnswer7 = 1;
	var quesAnswer8 = 1;
	var quesAnswer9 = 1;
	var quesAnswer10 = 1;
	var quesContentCount = 0;
	function addQuestions(id,quesType,quesScore)
	{
		var quesContent = document.getElementById('quesContent'+quesContentCount).innerHTML;
		var quesPick = document.getElementById('quesPick'+quesContentCount).innerHTML;
		if(quesType=="1"){	//类型一:判断题  正确答案（Y/N）
			document.getElementById("type1").style.display='block';
			var row = document.all.quesType1.insertRow();
			var cells = new Object;
			cells[0] = row.insertCell();
			cells[0].className="qust";
			cells[0].innerHTML="&nbsp;&nbsp;"+num1+".&nbsp;&nbsp;"+quesContent+"（"+quesScore+"分）";
			var row = document.all.quesType1.insertRow();
			var cells = new Object;
			cells[0] = row.insertCell();
			cells[0].className="answ";
			cells[0].innerHTML="<input type=radio name=typeOne"+quesAnswer1+" value=Y onclick=showValue(this)>正确&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=radio name=typeOne"+quesAnswer1+" value=N onclick=showValue(this)>错误";
			num1++;
			quesAnswer1++;
		}else if(quesType=="2"){//类型二:判断改错题 正确答案（Y/N），同时改错
			document.getElementById("type2").style.display='block';
			var row = document.all.quesType2.insertRow();
			var cells = new Object;
			cells[0] = row.insertCell();
			cells[0].className="qust";
			cells[0].innerHTML="&nbsp;&nbsp;"+num2+".&nbsp;&nbsp;"+quesContent+"（"+quesScore+"分）";
			var row = document.all.quesType2.insertRow();
			var cells = new Object;
			cells[0] = row.insertCell();
			cells[0].className="answ";
			var s = "<input type=radio name=typeTwo"+quesAnswer2+" value=Y onclick=showValue(this)>正确&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=radio name=typeTwo"+quesAnswer2+" value=N onclick=showValue(this)>错误";
			s += "<br/><textarea cols=80 rows=2 name=typeTwoAnswer"+quesAnswer2+" >";
			cells[0].innerHTML = s;
			num2++;
			quesAnswer2++;
		}else if(quesType=="3"){//类型三:单选题
			document.getElementById("type3").style.display='block';
			var row = document.all.quesType3.insertRow();
			var cells = new Object;
			cells[0] = row.insertCell();
			cells[0].className="qust";
			cells[0].innerHTML="&nbsp;&nbsp;"+num3+".&nbsp;&nbsp;"+quesContent+"（"+quesScore+"分）";
			var ques = new Array();//定义的新的数组
			ques = quesPick.split(";");
			for(var i = 0 ; i< ques.length;i++){
				var row = document.all.quesType3.insertRow();
				var cells = new Object;
				cells[0] = row.insertCell();
				cells[0].className="answ";
				cells[0].innerHTML="<input type=radio name=typeThree"+quesAnswer3+" value="+ques[i].substring(0,1)+" onclick=showValue(this)>"+ques[i]+"";
			}
			num3++;
			quesAnswer3++;
		}else if(quesType=="4"){//类型四:多选题 正确答案（答案为两个及两个以上，多个答案之间用“，”分隔）
			document.getElementById("type4").style.display='block';
			var row = document.all.quesType4.insertRow();
			var cells = new Object;
			cells[0] = row.insertCell();
			cells[0].className="qust";
			cells[0].innerHTML="&nbsp;&nbsp;"+num4+".&nbsp;&nbsp;"+quesContent+"（"+quesScore+"分）";
			var ques = new Array();//定义的新的数组
			ques = quesPick.split(";");
			for(var i = 0 ; i< ques.length;i++){
				var row = document.all.quesType4.insertRow();
				var cells = new Object;
				cells[0] = row.insertCell();
				cells[0].className="answ";
				cells[0].innerHTML="<input type=checkbox name=typeFour"+quesAnswer4+" value="+ques[i].substring(0,1)+" onclick=showValue(this)>"+ques[i];
			}
			num4++;
			quesAnswer4++;
		}else if(quesType=="5"){//类型五:不定项选择 正确答案（答案为一个及多个不等，多个答案之间用“，”分隔）
			document.getElementById("type5").style.display='block';
			var row = document.all.quesType5.insertRow();
			var cells = new Object;
			cells[0] = row.insertCell();
			cells[0].className="qust";
			cells[0].innerHTML="&nbsp;&nbsp;"+num5+".&nbsp;&nbsp;"+quesContent+"（"+quesScore+"分）";
			var ques = new Array();//定义的新的数组
			ques = quesPick.split(";");
			for(var i = 0 ; i< ques.length;i++){
				var row = document.all.quesType5.insertRow();
				var cells = new Object;
				cells[0] = row.insertCell();
				cells[0].className="answ";
				cells[0].innerHTML="<input type=checkbox name=typeFive"+quesAnswer5+" value="+ques[i].substring(0,1)+" onclick=showValue(this)>"+ques[i];
			}
			num5++;
			quesAnswer5++;
		}else if(quesType=="6"){//类型六:填空题 需要填空的部分，用“####”标识，正确答案,多个填空之间以“#”分隔。填空题答案个数必须与题目描述中的填空个数相同。
			document.getElementById("type6").style.display='block';
			var row = document.all.quesType6.insertRow();
			var cells = new Object;
			cells[0] = row.insertCell();
			cells[0].className="qust";
			cells[0].innerHTML="&nbsp;&nbsp;"+num6+".&nbsp;&nbsp;"+quesContent+"（"+quesScore+"分）";
			var row = document.all.quesType6.insertRow();
			var cells = new Object;
			cells[0] = row.insertCell();
			cells[0].innerHTML="答：<textarea  cols=80 rows=3 name=typeSix"+quesAnswer6+" onclick=\"showValue(this)\" style=\"vertical-align:bottom;\">";
			num6++;
			quesAnswer6++;
		}else if(quesType=="7"){//类型七:问答题
			document.getElementById("type7").style.display='block';
			var row = document.all.quesType7.insertRow();
			var cells = new Object;
			cells[0] = row.insertCell();
			cells[0].className="qust";
			cells[0].innerHTML="&nbsp;&nbsp;"+num7+".&nbsp;&nbsp;"+quesContent+"（"+quesScore+"分）";
			var row = document.all.quesType7.insertRow();
			var cells = new Object;
			cells[0] = row.insertCell();
			cells[0].innerHTML="答：<textarea cols=80 rows=5 name=typeSeven"+quesAnswer7+" onclick=\"showValue(this)\" style=\"vertical-align:bottom;\">";
			num7++;
			quesAnswer7++;
		}
		quesContentCount++;
	}

	function addExampls(){
		var exampaperId =$('#exampaperId').val();
		$.ligerDialog.warn('该套考题总分为'+'<%=scoreTotal%>'+'分,确定要生成试卷吗?', function (yes) {
			if(yes){
				var path="<%=path%>/controller/optionQue/insertExampaparQeus.do";
				$.ajax({
					url: path,
				  	type:"post",
				  	dataType:"json",
				  	data:$("#exampaperForm").serialize(),
				  	success: function(result){
				  		if(result.success="yes"){
				  		$.ligerDialog.success('添加成功','提示',function(yes){	
				  			 window.location.href="<%=path%>/controller/examManager/toExamManagerPage.do";
						});		  
				  		}
				  	},
					error:function(e){
		       			$.ligerDialog.warn('提交异常，请稍后再试');
		       	    }
				 }); 
			}
		});
		
	}
	//跳转到选题界面
	function backToSelect(){
		<%if(exampaperId!=null && !exampaperId.equals("")){%>
			$('#exampaperForm').action="<%=path%>/exampaperAction.do?method=inputQuesAreaUpdate&pkAutoId=<%=exampaperId%>";
		<%}else{%>
			$('#exampaperForm').action="<%=path%>/exampaperAction.do?method=inputQuesArea";
		<%}%>
		$('exampaperForm').submit();
	}
	//统一调用窗口打开
</script>
<base target="_self">
<body>
<%
	List list = (List)request.getAttribute("selectList");
	//try {
		for(int i = 0 ; i<list.size();i++){
			tQuestions questionsVO = (tQuestions)list.get(i);
			//String quesCnt = java.net.URLDecoder.decode(questionsVO.getQuesContent(),"UTF-8");
			%>
				<span id="quesContent<%=i%>" style="display: none"><%=questionsVO.getQuesContent()%></span>
				<span id="quesPick<%=i%>" style="display: none"><%=questionsVO.getQuesPick()%></span>
			<%
		}
		
 %>
<form name="exampaperForm"  method="post" action="<%=path%>/exampaperAction.do?method=list&tSysRightId=<%=tSysRightId %>" id="exampaperForm" >
	<input type="hidden" name="selectQues" id="selectQues" value="${selectQues}" />
	<input type="hidden" name="scoreTotal" id="scoreTotal" value="${scoreTotal}" />
	<input type="hidden" name="selectQuesScore" id="selectQuesScore" value="${selectQuesScore}" />
	<input type="hidden" name="exampaperRemark" id="exampaperRemark" value="<%=(String)request.getAttribute("exampaperRemark")%>">
	<input type="hidden" name="examPaperName" id="exampaperName" value="<%=(String)request.getAttribute("exampaperName")%>">
	<input type="hidden" name="exampaperId" id="exampaperId" value="${exampaperId}" >
	<input type="hidden" name="isIndex" id="isIndex" value="${isIndex}" >
	<input type="hidden" name="examPaperType" id="examPaperType" value="${examPaperType}" >
	
	<input type="hidden" name="sc" id="sc" value="${sc}" >
	
	<input type="hidden" name="histroyFlag" id="histroyFlag" value="${requestScope.histroyFlag}"/>
	<div class="exampaper">
		<table width="100%"  border="0" cellspacing="0" cellpadding="4" bgcolor="f8f8f8">
			<tr align="center">
				<td colspan="3" class="title"><%=(String)request.getAttribute("exampaperName")%></td>
			</tr>
	    </table>
		<div id="type1" style="display: none">
		<table class="qustype" border="0" cellspacing="0" cellpadding="0" id="quesType1">
			<tr>
				<td class="two">模块：判断题</td>
			</tr>
		</table>
		</div>
		<div id="type2" style="display: none">
		<table class="qustype" border="0" cellspacing="0" cellpadding="0" id="quesType2">
			<tr>
				<td class="two">模块：判断改错题<font style="color:red;font-weight:bold;">【当判断为错误时，请添加正确答案】</font></td>
			</tr>
		</table>
		</div>
		<div id="type3" style="display: none">
		<table class="qustype" border="0" cellspacing="0" cellpadding="0" id="quesType3">
			<tr>
				<td class="two">模块：单选题</td>
			</tr>
		</table>
		</div>
		<div id="type4" style="display: none">
		<table class="qustype" border="0" cellspacing="0" cellpadding="0" id="quesType4">
			<tr>
				<td class="two">模块：多选题</td>
			</tr>
		</table>
		</div>
		<div id="type5" style="display: none">
		<table class="qustype" border="0" cellspacing="0" cellpadding="0" id="quesType5">
			<tr>
				<td class="two">模块：不定项选择</td>
			</tr>
		</table>
		</div>
		<div id="type6" style="display: none">
		<table class="qustype" border="0" cellspacing="0" cellpadding="0" id="quesType6">
			<tr>
				<td class="two">模块：填空题<font style="color:red;font-weight:bold;">【____为填空部分】</font></td>
			</tr>
		</table>
		</div>
		<div id="type7" style="display: none">
		<table class="qustype" border="0" cellspacing="0" cellpadding="0" id="quesType7">
			<tr>
				<td class="two">模块：问答题</td>
			</tr>
		</table>
		</div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" >
			<tr align="center" height="60">
				<td colspan="8" align="center"><shiro:hasPermission name='kssjgl:add'><input
					id="submitPrapre" type="button" value="提交" onclick="addExampls()" /></shiro:hasPermission>
					<input id="updateForm" type="button" onclick="window.history.back();" value="取消" /></td>
			</tr>
	  </table>
	</div>
</form>

	<script language="javascript">
	<%//if(request.getAttribute("selectList")!=null){
			//List list = (List)request.getAttribute("selectList");
			for (int i = 0; i < list.size(); i++) {
				tQuestions questionsVO = (tQuestions) list.get(i);%>
			addQuestions('<%=questionsVO.getPkAutoId()%>','<%=questionsVO.getQuesType()%>','<%=selectQuesScore.split(",")[i]%>');
		<%}
			//}%>
</script>
</body>

</html:html>