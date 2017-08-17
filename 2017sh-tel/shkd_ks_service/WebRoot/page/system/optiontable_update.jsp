<%@ page language="java" contentType="text/html;charset=UTF-8"
	import="java.util.*,cn.sh.ideal.model.tQuestions"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<%
	String path = request.getContextPath();
%>
<head>
<title>题库选题组卷或更新试卷</title>
<meta charset="UTF-8">

<link rel="stylesheet" href="../../js/ztree/css/demo.css"
	type="text/css">
<link href="../../css/public.css" rel="stylesheet" type="text/css" />

<link href="../../lib/ligerUI/skins/Gray2014/css/all.css"
	rel="stylesheet" />
<link href="../../lib/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link href="../../lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet"
	type="text/css" />
<link href="../../css/utils/css.css" rel="stylesheet" type="text/css">
<link href="../../css/utils/xtree.css" rel="stylesheet" type="text/css">
<link href="../../css/utils/repository.css" rel="stylesheet"
	type="text/css">
<link href="../../css/utils/exampaper.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript" src="../../js/utils/utils.js"></script>
<script type="text/javascript" src="../../js/utils/xtree.js"></script>
<script type="text/javascript" src="../../js/utils/xmlextras.js"></script>
<script type="text/javascript" src="../../js/utils/xloadtree.js"></script>
<script type="text/javascript" src="../../js/utils/check_char.js"></script>
<script type="text/javascript" src="../../js/utils/quality_check.js"></script>
<script type="text/javascript"
	src="../../js/ztree/js/jquery-1.4.4.min.js"></script>
<script src="../../lib/jquery/jquery-1.9.0.min.js"
	type="text/javascript"></script>
<script src="../../lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="../../lib/ligerUI/js/plugins/ligerPopupEdit.js"></script>
<script type="text/javascript"
	src="../../js/My97DatePicker/WdatePicker.js"></script>
<script src="../../lib/ligerUI/js/plugins/ligerRadio.js"
	type="text/javascript"></script>
<script src="../../js/ajaxSession.js" type="text/javascript"></script>

</head>
<style type="text/css">
#addExampaper {
	position: absolute;
	left: expression(( body.clientWidth-310)/2 -150 );
	top: expression(( body.clientHeight-80)/2 -40 );
	width: 622px;
	height: 192px;
	z-index: 999;
	margin: 0;
	background-color: #E0EBFE;
	border: 1px solid #FFFFFF;
	border-collapse: collapse;
	overflow: hidden;
}

#addExampaper tr.silver {
	height: 28px;
	padding-left: 8px;
	font-size: 13px;
	line-height: 18pt;
	background: url(../../img/utils/bg_title_silver.gif);
}

.one {
	text-align: center; //
	居中 font-size: 12px; //
	字体大小为12像素 line-height: 30px;
	height: 30px;
	font-weight: bold; //
	加粗 width: 200px;
	margin: auto;
}

<
style type ="text/css">div.title_div {
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

body {
	overflow: auto !important;
}
</style>


<style type="text/css">
<
style type ="text/css">div.title_div {
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


<script language="javascript">
	$(function(){
		$("#submitPrapre").click(function(){
			var examPaperName=$("#examPaperName").val();
			var examPaperRemark=$("#examPaperRemark").val();
			var isIndex=$("#isIndex").val();
			var examPaperType=$("#examPaperType").val();
			if(examPaperName==''){
				  $.ligerDialog.error('试卷名称不能为空');
				  return;
			}if(examPaperRemark==''){
				  $.ligerDialog.error('试卷备注不能为空');
				  return;
			}if(isIndex==''){
				  $.ligerDialog.error('是否乱序不能为空');
				  return;
			}if(examPaperType==''||examPaperType=='0'){
				  $.ligerDialog.error('试卷类型不能为空');
				  return;
			}
			var exampaperNameOld=$("#exampaperNameOld").val();
			if(exampaperNameOld!=""&&exampaperNameOld==examPaperName)
			{
				var num = 0 ;
				var strParm = "";
				var strParmScore = "";
				var scoreTotal = 0;
				var scCount = document.getElementsByName('sc');
				for(var i=0;i<document.getElementById("exampaperForm").elements.length; i++){
					if(document.getElementById("exampaperForm").elements[i].type == 'checkbox'&& document.getElementById("exampaperForm").elements[i].name == 'sc'){
						if(document.getElementById("exampaperForm").elements[i].checked){
							if(strParm == ''){
								if(scCount.length < 2){
								strParm = document.getElementById("exampaperForm").sc.value;
								strParmScore = document.getElementById("exampaperForm").typeScore.value;
								}else{
								strParm = document.getElementById("exampaperForm").sc[num].value;
								strParmScore = document.getElementById("exampaperForm").typeScore[num].value;
								}
							}else{
								strParm = strParm + ',' + document.getElementById("exampaperForm").sc[num].value;
								strParmScore =  strParmScore + ',' +document.getElementById("exampaperForm").typeScore[num].value;
							}
							if(scCount.length < 2){
								scoreTotal =parseInt(document.getElementById("exampaperForm").typeScore.value);
							}else{
								scoreTotal  +=parseInt(document.getElementById("exampaperForm").typeScore[num].value);
							}
							num++;
						}else{
							num++;
						}
					}
				}
				$("#selectQues").val(strParm);
				$('#selectQuesScore').val(strParmScore);
				$('#scoreTotal').val(new String(scoreTotal));
				$('#examPaperTypes').val(examPaperType);
				$('#exampaperNames').val(examPaperName);
				$('#exampaperRemarks').val(examPaperRemark);
				$('#isIndexs').val(isIndex);
				
				   var path = "<%=path%>/controller/optionQue/viewExampapar.do";  
				    $('#exampaperForm').attr("action", path).submit();; 
			}else{
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
						var num = 0 ;
						var strParm = "";
						var strParmScore = "";
						var scoreTotal = 0;
						var scCount = document.getElementsByName('sc');
						for(var i=0;i<document.getElementById("exampaperForm").elements.length; i++){
							if(document.getElementById("exampaperForm").elements[i].type == 'checkbox'&& document.getElementById("exampaperForm").elements[i].name == 'sc'){
								if(document.getElementById("exampaperForm").elements[i].checked){
									if(strParm == ''){
										if(scCount.length < 2){
										strParm = document.getElementById("exampaperForm").sc.value;
										strParmScore = document.getElementById("exampaperForm").typeScore.value;
										}else{
										strParm = document.getElementById("exampaperForm").sc[num].value;
										strParmScore = document.getElementById("exampaperForm").typeScore[num].value;
										}
									}else{
										strParm = strParm + ',' + document.getElementById("exampaperForm").sc[num].value;
										strParmScore =  strParmScore + ',' +document.getElementById("exampaperForm").typeScore[num].value;
									}
									if(scCount.length < 2){
										scoreTotal =parseInt(document.getElementById("exampaperForm").typeScore.value);
									}else{
										scoreTotal  +=parseInt(document.getElementById("exampaperForm").typeScore[num].value);
									}
									num++;
								}else{
									num++;
								}
							}
						}
						$("#selectQues").val(strParm);
						$('#selectQuesScore').val(strParmScore);
						$('#scoreTotal').val(new String(scoreTotal));
						$('#examPaperTypes').val(examPaperType);
						$('#exampaperNames').val(examPaperName);
						$('#exampaperRemarks').val(examPaperRemark);
						$('#isIndexs').val(isIndex);
						
						   var path = "<%=path%>/controller/optionQue/viewExampapar.do";  
						    $('#exampaperForm').attr("action", path).submit();; 
					  }else{
						 $.ligerDialog.warn("试卷名称已被使用!");
					  }
				   }
				});
			}
		});
	});

	//根据不同的考题类型动态增加行
	var num1 = 1;
	var num2 = 1;
	var num3 = 1;
	var num4 = 1;
	var num5 = 1;
	var num6 = 1;
	var num7 = 1;
	var num8 = 1;
	var num10=0;
	var quesContentCount = 0;
	function total()
	{

		if(num10==0){
		showInfo();
					num10++
		}
		var totalScore=0;
		var  typeScore = document.getElementsByName('typeScore');
		for(var i =0;i<typeScore.length;i++)
		{
			if(typeScore[i].value=='')
			{
				totalScore = eval(parseInt(totalScore) + parseInt('0'));
			}else
			{
				totalScore = eval(parseInt(totalScore) + parseInt(typeScore[i].value));
			}
		}
		document.getElementById('tatolScore').value = totalScore;
	}

	function addQuestions(id,quesType,quesScore)
	{	
		//1:判断题
		if(quesType=="1"){
 		document.getElementById("type1").style.display='';
 		var row = document.all.quesType1.insertRow();
			var cells = new Object;
			for(var i=0;i<3; i++)
			{
				cells[i] = row.insertCell();
			}
			cells[0].innerHTML="<input type=checkbox style='margin-left: 30px;' name=sc value='"+id+"' score='"+quesScore+"' quesType='"+quesType+"'>";
			cells[1].innerHTML=num1+"."+document.getElementById('quesContent'+quesContentCount).innerHTML;
			num1++;
			cells[2].innerHTML="<input type= text bm ='"+quesType+"' name=typeScore onblur=checkValue(this) onkeyup=value=value.replace(/[^0-9]/g,'0');total(); style=text-align:center; maxlength=2 size=3 style=height:18px;  value='"+quesScore+"'>";
			//2.判断改错题
		}else if(quesType=="2"){
			document.getElementById("type2").style.display='';
			var row = document.all.quesType2.insertRow();

			var cells = new Object;
			for(var i=0;i<3; i++)
			{
				cells[i] = row.insertCell();
			}
			cells[0].innerHTML="<input type=checkbox style='margin-left: 30px;' name=sc value='"+id+"' score='"+quesScore+"' quesType='"+quesType+"'>";
			cells[1].innerHTML=num2+"."+document.getElementById('quesContent'+quesContentCount).innerHTML;
			num2++;
			cells[2].innerHTML="<input type= text name=typeScore onblur=checkValue(this) onkeyup=value=value.replace(/[^0-9]/g,'0');total(); style=text-align:center; maxlength=2 size=3 style=height:18px; value='"+quesScore+"' bm ='"+quesType+"'>";
			//3.单选题
		}else if(quesType=="3"){
			document.getElementById("type3").style.display='';
			var row = document.all.quesType3.insertRow();

			var cells = new Object;
			for(var i=0;i<3; i++)
			{
				cells[i] = row.insertCell();
			}
			cells[0].innerHTML="<input type=checkbox style='margin-left: 30px;' name=sc value='"+id+"' score='"+quesScore+"' quesType='"+quesType+"'>";
			cells[1].innerHTML=num3+"."+document.getElementById('quesContent'+quesContentCount).innerHTML;
			num3++;
			cells[2].innerHTML="<input type= text name=typeScore onblur=checkValue(this)  onkeyup=value=value.replace(/[^0-9]/g,'0');total();  style=text-align:center  maxlength=2 size=3 style=height:18px; value='"+quesScore+"' bm ='"+quesType+"'>";
			//4.多选题
		}else if(quesType=="4"){
			document.getElementById("type4").style.display='';
			var row = document.all.quesType4.insertRow();

			var cells = new Object;
			for(var i=0;i<3; i++)
			{
				cells[i] = row.insertCell();
			}
			cells[0].innerHTML="<input type=checkbox style='margin-left: 30px;' name=sc value='"+id+"' score='"+quesScore+"' quesType='"+quesType+"'>";
			cells[1].innerHTML=num4+"."+document.getElementById('quesContent'+quesContentCount).innerHTML;
			num4++;
			cells[2].innerHTML="<input type= text name=typeScore onblur=checkValue(this) onkeyup=value=value.replace(/[^0-9]/g,'0');total(); style=text-align:center maxlength=2 size=3 style=height:18px; value='"+quesScore+"' bm ='"+quesType+"'>";
			//5.不定项选择
		}else if(quesType=="5"){
			document.getElementById("type5").style.display='';
			var row = document.all.quesType5.insertRow();

			var cells = new Object;
			for(var i=0;i<3; i++)
			{
				cells[i] = row.insertCell();
			}
			cells[0].innerHTML="<input type=checkbox style='margin-left: 30px;' name=sc value='"+id+"' score='"+quesScore+"' quesType='"+quesType+"'>";
			cells[1].innerHTML=num5+"."+document.getElementById('quesContent'+quesContentCount).innerHTML;
			num5++;
			cells[2].innerHTML="<input type= text name=typeScore onblur=checkValue(this) onkeyup=value=value.replace(/[^0-9]/g,'0');total(); style=text-align:center maxlength=2 size=3 style=height:18px; value='"+quesScore+"' bm ='"+quesType+"'>";
			//6.填空题
		}else if(quesType=="6"){
			document.getElementById("type6").style.display='';
			var row = document.all.quesType6.insertRow();

			var cells = new Object;
			for(var i=0;i<3; i++)
			{
				cells[i] = row.insertCell();
			}
			cells[0].innerHTML="<input type=checkbox style='margin-left: 30px;' name=sc value='"+id+"' score='"+quesScore+"' quesType='"+quesType+"'>";
			cells[1].innerHTML=num6+"."+document.getElementById('quesContent'+quesContentCount).innerHTML;
			num6++;
			cells[2].innerHTML="<input type= text name=typeScore onblur=checkValue(this) onkeyup=value=value.replace(/[^0-9]/g,'0');total(); style=text-align:center maxlength=2 size=3 style=height:18px; bm ='"+quesType+"' value='"+quesScore+"'>";
			//7.问答题
		}else if(quesType=="7"){
			document.getElementById("type7").style.display='';
			var row = document.all.quesType7.insertRow();

			var cells = new Object;
			for(var i=0;i<3; i++)
			{
				cells[i] = row.insertCell();
			}
			cells[0].innerHTML="<input type=checkbox style='margin-left: 30px;' name=sc value='"+id+"' score='"+quesScore+"' quesType='"+quesType+"'>";
			cells[1].innerHTML=num7+"."+document.getElementById('quesContent'+quesContentCount).innerHTML;
			num7++;
			cells[2].innerHTML="<input type= text name=typeScore onblur=checkValue(this) onkeyup=value=value.replace(/[^0-9]/g,'0');total(); style=text-align:center maxlength=2 size=3 style=height:18px; value='"+quesScore+"' bm ='"+quesType+"'>";
			//8.案例分析题
		}else if(quesType=="8"){
			document.getElementById("type8").style.display='';
			var row = document.all.quesType8.insertRow();

			var cells = new Object;
			for(var i=0;i<3; i++)
			{
				cells[i] = row.insertCell();
			}
			cells[0].innerHTML="<input type=checkbox style='margin-left: 30px;' name=sc value='"+id+"' score='"+quesScore+"' quesType='"+quesType+"'>";
			cells[1].innerHTML=num8+"."+document.getElementById('quesContent'+quesContentCount).innerHTML;
			num8++;
			cells[2].innerHTML="<input type= text name=typeScore onblur=checkValue(this) onkeyup=value=value.replace(/[^0-9]/g,'0');total(); style=text-align:center maxlength=2 size=3 style=height:18px; value='"+quesScore+"' bm ='"+quesType+"'>";
		}
		quesContentCount++;
	}
	//弹出生成试卷窗口
	function showInfo(){
		var scnum = 0;

		for(var i=0;i< document.getElementById("exampaperForm").elements.length; i++){
			if(document.getElementById("exampaperForm").elements[i].type == 'checkbox'&& document.getElementById("exampaperForm").elements[i].name == 'sc'){
				if(document.getElementById("exampaperForm").elements[i].checked){
					scnum++;
				}
			}
		}

			return false;
		
		if(scnum=="0"){
			alert("生成试卷前请选择题目！");
			return false;
		}
		showSysDiv();
		$('addExampaper').style.display="";
	}
	
	function skipHerf(){
		  window.location.href='<%=path%>/controller/examManager/queryQuesByExamIds.do?exampaperId='+${exampaper.pkAutoId}
		
	}
	
	//删除所选考题
	function removeSelectedQues(){
		var num=0;
		var strParm = "";
		for(var i=0;i< document.getElementById("exampaperForm").elements.length; i++){
			if(document.getElementById("exampaperForm").elements[i].type == 'checkbox'&& document.getElementById("exampaperForm").elements[i].name == 'sc'){
				if(document.getElementById("exampaperForm").elements[i].checked){
					if(strParm == ''){
						strParm = document.getElementById("exampaperForm").elements[i].value;
					}else{
						strParm = strParm + ',' + document.getElementById("exampaperForm").elements[i].value;
					}
					num++;
				}
			}
		}
		if(num=="0"){
			 $.ligerDialog.warn('请先选择考题');
			return false;
		}
		$('#selectQues').val(strParm);
		$.ligerDialog.confirm('确认要选中的删除考题？', function(yes) {
			if (yes) {
				$('#exampaperForm').submit();
			}else{
				return;
			}
		});
 		<%-- 	$('exampaperForm').selectQues.value = strParm;
			// <%
			// 	if(exampaperId!=null && !exampaperId.equals("")){
			// 	%>
			// 		$('exampaperForm').action="exampaperAction.do?method=removeQuesUpSeled&pkAutoId=<%=exampaperId%>";
			// 	<%
			// 	}else{
			// 	%>
			// 		$('exampaperForm').action="exampaperAction.do?method=removeQuesSelected";
			// 	<%
			// 	}
			// %> --%>
			
		/* 	$('exampaperForm').submit(); */
	
	}
	//关闭窗口
	function closeMsg() {
		$('addExampaper').style.display="none";
		closeSysDiv();
	}
	//清空
	function clearExampaper(){
		//document.getElementById("exampaperForm").examPaperName.value ="";
		//document.getElementById("exampaperForm").examPaperRemark.value ="";
		closeMsg();
	}
	//根据考题类型全选或不选
	function selectByType(a){
		var childs = a.parentNode.parentNode.parentNode.childNodes;
		for(var i=0;i<childs.length;i++){
			if(childs[i].nodeName=="TR"){
				var t = childs[i].childNodes[0].childNodes[0];
				if(t!=undefined){
					t.checked = a.checked;
				}
			}
		}
	}

		//全选或不选
	function selectAll(a){
				if(a.checked){
			for(var i=0;i< document.getElementById("exampaperForm").elements.length; i++){
					if(document.getElementById("exampaperForm").elements[i].type == 'checkbox'){
							document.getElementById("exampaperForm").elements[i].checked=true;
						}
				}
			}else{
				for(var i=0;i< document.getElementById("exampaperForm").elements.length; i++){
					if(document.getElementById("exampaperForm").elements[i].type == 'checkbox'){
							document.getElementById("exampaperForm").elements[i].checked=false;
						}

				}
			}
	}
	function relvalue(a)
	{

	/* 		var childs = a.parentNode.parentNode.parentNode.childNodes;
			for(var i=0;i<childs.length;i++){
				if(childs[i].nodeName=="TR"){
					var t = childs[i].childNodes[0].childNodes[0];
					if(t!=undefined){
						console.log(t);
						t.checked = a.checked;
					}
				}
		 */
		
		for(var i=0;i< document.getElementById("exampaperForm").elements.length; i++){

		 	if(a.name == 'pdt')
		 	{
		 		
				if(document.getElementById("exampaperForm").elements[i].type == 'text'&&document.getElementById("exampaperForm").elements[i].getAttribute("bm") == '1')
				{
					if(document.getElementById('pdt').value =='')
					{
						document.getElementById("exampaperForm").elements[i].value = 0;
					}else
					{
						document.getElementById("exampaperForm").elements[i].value = document.getElementById('pdt').value;
					}

				}
			}
			if(a.name == 'pdgct')
		 	{
				if(document.getElementById("exampaperForm").elements[i].type == 'text'&&document.getElementById("exampaperForm").elements[i].getAttribute("bm") == '2')
				{
					if(document.getElementById('pdgct').value =='')
					{
						document.getElementById("exampaperForm").elements[i].value = 0;
					}else
					{
						document.getElementById("exampaperForm").elements[i].value = document.getElementById('pdgct').value;
					}

				}
			}
			if(a.name == 'dxt')
		 	{
				if(document.getElementById("exampaperForm").elements[i].type == 'text'&&document.getElementById("exampaperForm").elements[i].getAttribute("bm") == '3')
				{
					console.log($("#dxt").val);
					if(document.getElementById('dxt').value =='')
					{
						document.getElementById("exampaperForm").elements[i].value = 0;
					}else
					{
						document.getElementById("exampaperForm").elements[i].value = document.getElementById('dxt').value;
					}

				}
			}
			if(a.name == 'ddxt')
		 	{
				if(document.getElementById("exampaperForm").elements[i].type == 'text'&&document.getElementById("exampaperForm").elements[i].getAttribute("bm") == '4')
				{
					if(document.getElementById('ddxt').value =='')
					{
						document.getElementById("exampaperForm").elements[i].value = 0;
					}else
					{
						document.getElementById("exampaperForm").elements[i].value = document.getElementById('ddxt').value;
					}

				}
			}
			if(a.name == 'bdxxzt')
		 	{
				if(document.getElementById("exampaperForm").elements[i].type == 'text'&&document.getElementById("exampaperForm").elements[i].getAttribute("bm") == '5')
				{
					if(document.getElementById('bdxxzt').value =='')
					{
						document.getElementById("exampaperForm").elements[i].value = 0;
					}else
					{
						document.getElementById("exampaperForm").elements[i].value = document.getElementById('bdxxzt').value;
					}

				}
			}
			if(a.name == 'tkt')
		 	{
				if(document.getElementById("exampaperForm").elements[i].type == 'text'&&document.getElementById("exampaperForm").elements[i].getAttribute("bm") == '6')
				{
					if(document.getElementById('tkt').value =='')
					{
						document.getElementById("exampaperForm").elements[i].value = 0;
					}else
					{
						document.getElementById("exampaperForm").elements[i].value = document.getElementById('tkt').value;
					}

				}
			}
			if(a.name == 'wdt')
		 	{
				if(document.getElementById("exampaperForm").elements[i].type == 'text'&&document.getElementById("exampaperForm").elements[i].getAttribute("bm") == '7')
				{
					if(document.getElementById('wdt').value =='')
					{
						document.getElementById("exampaperForm").elements[i].value = 0;
					}else
					{
						document.getElementById("exampaperForm").elements[i].value = document.getElementById('wdt').value;
					}

				}
			}
			if(a.name == 'alfxt')
		 	{
				if(document.getElementById("exampaperForm").elements[i].type == 'text'&&document.getElementById("exampaperForm").elements[i].getAttribute("bm") == '8')
				{
					if(document.getElementById('alfxt').value =='')
					{
						document.getElementById("exampaperForm").elements[i].value = 0;
					}else
					{
						document.getElementById("exampaperForm").elements[i].value = document.getElementById('alfxt').value;
					}

				}
			}
		}
	}
	function checkValue(obj)
	{
		if(obj.value == '')
		{
			obj.value='0';
		}
	}
	function reback()
	{
<%-- 		// <%
		// 	if(exampaperId!=null && !exampaperId.equals("")){
		// 	%>
		// 		$('exampaperForm').action="exampaperAction.do?method=queryQuestionsUpdate&pkAutoId=<%=exampaperId%>";
		// 		$('exampaperForm').submit();
		// 	<%
		// 	}else{
		// 	%>
		// 		$('exampaperForm').action="exampaperAction.do?method=queryQuestions";
		// 		$('exampaperForm').submit();
		// 	<%
		// 	}
		// %> --%>
	}
</script>
<base target="_self">
<body onload="total();">
	<%
		List list = (List) request.getAttribute("selectList");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				tQuestions questionsVO = (tQuestions) list.get(i);
	%>
	<span id="quesContent<%=i%>" style="display: none"><%=questionsVO.getQuesContent()%>
	</span>
	<span id="quesPick<%=i%>" style="display: none"><%=questionsVO.getQuesPick()%></span>

	<%
		}
		}
	%>



	<div align="center" style="margin-top: 10px;">
		<tr align="center">

			<td colspan="8" align="center"><input type="button"
				onclick="addExampaper()" value="生成试卷" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" onclick="removeSelectedQues()"
				style="width: 110px;" value="删除所选考题" />&nbsp;&nbsp;&nbsp;&nbsp; <input
				type="button" value="返回" id="insertOpen"
				onclick="skipHerf()"/></td>
		</tr>
	</div>
	<form name="exampaperForm" method="post" action="<%=path%>/controller/examManager/removeUpdateExamPapaer.do"
		id="exampaperForm">
		<input type="hidden" name="selectQues" id="selectQues" /> <input
			type="hidden" name="selectQuesScore" id="selectQuesScore" /> <input
			type="hidden" name="scoreTotal" id="scoreTotal" /> <input
			type="hidden" name="examPaperTypes" id="examPaperTypes" /> <input
			type="hidden" name="exampaperRemarks" id="exampaperRemarks" /> 
			<input type="hidden" name="exampaperNames" id="exampaperNames" /> 
			<input type="hidden" name="exampaperNameOld" id="exampaperNameOld" value="${exampaper.examPaperName}" /> 
			<input type="hidden" name="isIndexs" id="isIndexs" /> <input
			type="hidden" name="histroyFlag" id="histroyFlag" value="${requestScope.histroyFlag}" />
		<input id="examPaperId" name="examPaperId" type="hidden" value="${exampaper.pkAutoId }" >
		<div style="margin-top: 10px;">
			<table width="100%" style="border: solid 1px #add9c0;"
				cellspacing="0" cellpadding="0">
				<tr style="background-color: #3B98C8">
					<th colspan="3" height="23px"><img
						src="../../img/utils/icon_search.gif" width="16"
						style="position: relative; top: 3px;" height="16">&nbsp;已选择考题区
					</th>
				</tr>
				<tr>
					<!-- <td class="title" colspan="3"><img src="../../img/utils/del.gif" height="16" width="16"
				border="0"><a href="#" onClick="removeSelectedQues();" style="vertical-align:middle;">删除所选考题</a>
			</td> -->
				</tr>
				<tr align="center" style="border: 1pxsolid:#F00">
					<td colspan="3" style="border: solid 1px #add9c0;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="title">
							<tr>
								<td width="3%"></td>
								<td width="92%">考题</td>
								<td width="5%">分值</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="type99">
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							id="quesTypeAll" style="border: solid 1px #add9c0;">
							<tr align="center" class="title">
								<td width="3%"><input type="checkbox" name="selectAllName"
									onclick="selectAll(this);"></td>
								<td width="80%" align="left"><b>全选</b></td>
								<td width="12%" align="center">总分：&nbsp;&nbsp;&nbsp;【<input
									type="text" name="tatolScore" id="tatolScore" readonly="true"
									style="border: 0; background: transparent; text-align: center"
									size="3">】
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="type1" style="display: none">
					<td colspan="3">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							id="quesType1">
							<tr align="center" class="title"
								style="border: solid 1px #add9c0;">
								<td width="3%"><input type="checkbox" name="c1"
									onclick="selectByType(this);"></td>
								<td width="92%" align="left"><b>判断题</b>&nbsp;&nbsp;&nbsp;【<input
									type="text" name="pdt" id="pdt"
									style="border: 0; background: transparent; text-align: center"
									size="3" onblur="checkValue(this);"
									onkeyup="value=value.replace(/[^0-9]/g,'');relvalue(this);total();">】分</td>
								<td width="5%" align="center"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="type2" style="display: none">
					<td colspan="3">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="ques" id="quesType2">
							<tr align="center" class="title">
								<td width="3%"><input type="checkbox" name="c2"
									onclick="selectByType(this);"></td>
								<td width="92%" align="left"><b>判断改错题</b>&nbsp;&nbsp;&nbsp;【<input
									type="text" name="pdgct" id="pdgct"
									style="border: 0; background: transparent; text-align: center"
									size="3" onblur="checkValue(this);"
									onkeyup="value=value.replace(/[^0-9]/g,'');relvalue(this);total();">】分</td>
								<td width="5%" align="center"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="type3" style="display: none">
					<td colspan="3">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="ques" id="quesType3">
							<tr align="center" class="title">
								<td width="3%"><input type="checkbox" name="c3"
									onclick="selectByType(this);"></td>
								<td width="92%" align="left"><b>单选题</b>&nbsp;&nbsp;&nbsp;【<input
									type="text" name="dxt" id="dxt"
									style="border: 0; background: transparent; text-align: center"
									size="3" onblur="checkValue(this);"
									onkeyup="value=value.replace(/[^0-9]/g,'');relvalue(this);total();">】分</td>
								<td width="5%" align="center"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="type4" style="display: none">
					<td colspan="3">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="ques" id="quesType4">
							<tr align="center" class="title">
								<td width="3%"><input type="checkbox" name="c4"
									onclick="selectByType(this);"></td>
								<td width="92%" align="left"><b>多选题</b>&nbsp;&nbsp;&nbsp;【<input
									type="text" name="ddxt" id="ddxt"
									style="border: 0; background: transparent; text-align: center"
									size="3" onblur="checkValue(this);"
									onkeyup="value=value.replace(/[^0-9]/g,'');relvalue(this);total();">】分</td>
								<td width="5%" align="center"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="type5" style="display: none">
					<td colspan="3">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="ques" id="quesType5">
							<tr align="center" class="title">
								<td width="3%"><input type="checkbox" name="c5"
									onclick="selectByType(this);"></td>
								<td width="92%" align="left"><b>不定项选择</b>&nbsp;&nbsp;&nbsp;【<input
									type="text" name="bdxxzt" id="bdxxzt"
									style="border: 0; background: transparent; text-align: center"
									size="3" onblur="checkValue(this);"
									onkeyup="value=value.replace(/[^0-9]/g,'');relvalue(this);total();">】分</td>
								<td width="5%" align="center"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="type6" style="display: none">
					<td colspan="3">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="ques" id="quesType6">
							<tr align="center" class="title">
								<td width="3%"><input type="checkbox" name="c6"
									onclick="selectByType(this);"></td>
								<td width="92%" align="left"><b>填空题</b>&nbsp;&nbsp;&nbsp;【<input
									type="text" name="tkt" id="tkt"
									style="border: 0; background: transparent; text-align: center"
									size="3" onblur="checkValue(this);"
									onkeyup="value=value.replace(/[^0-9]/g,'');relvalue(this);total();">】分</td>
								<td width="5%" align="center"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="type7" style="display: none">
					<td colspan="3">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="ques" id="quesType7">
							<tr align="center" class="title">
								<td width="3%"><input type="checkbox" name="c7"
									onclick="selectByType(this);"></td>
								<td width="92%" align="left"><b>问答题</b>&nbsp;&nbsp;&nbsp;【<input
									type="text" name="wdt" id="wdt"
									style="border: 0; background: transparent; text-align: center"
									size="3" onblur="checkValue(this);"
									onkeyup="value=value.replace(/[^0-9]/g,'');relvalue(this);total();">】分</td>
								<td width="5%" align="center"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="type8" style="display: none">
					<td colspan="3">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="ques" id="quesType8">
							<tr align="center" class="title">
								<td width="3%"><input type="checkbox" name="c8"
									onclick="selectByType(this);"></td>
								<td width="92%" align="left"><b>案例分析题</b>&nbsp;&nbsp;&nbsp;【<input
									type="text" name="alfxt" id="alfxt"
									style="border: 0; background: transparent; text-align: center"
									size="3" onblur="checkValue(this);"
									onkeyup="value=value.replace(/[^0-9]/g,'');relvalue(this);total();">】分</td>
								<td width="5%" align="center"></td>
							</tr>
						</table>
					</td>
				</tr>

			</table>
		</div>
			<div id="addque"
		style="width: 98%; margin: 5px; padding-right: 30px; display: none;">
		<table class="search_table" cellpadding="3" cellspacing="3"
			width="100%">
			<tr>
				<td class="td_lable">试卷名称：</td>
				<td class="td_value"><input type="text" id="examPaperName" name="examPaperName" value="${exampaper.examPaperName}" />&nbsp;<red>*</red></td>
				<td class="td_value">试卷名称不能为空</td>
			</tr>
			<tr>
				<td class="td_lable">试卷备注：</td>
				<td class="td_value"><input type="text" id="examPaperRemark" name="examPaperRemark" value="${exampaper.examPaperRemark }" />&nbsp;<red>*</red>
				<td class="td_value">试卷备注不能为空</td>
			</tr>
			<tr>
				<td class="td_lable">是否乱序：</td>
				<c:if test="${exampaper.isindex==0}">
				<td class="td_value"> &nbsp;<input type="radio" name="isIndex" id="isIndex" value="1" style="cursor:pointer"/> 是 &nbsp;<input type="radio" name="isIndex" id="isIndex" value="0" style="cursor:pointer" checked="checked" /> 否 &nbsp;&nbsp;&nbsp;  <red>*</red></td>
				</c:if>
				<td class="td_value"> &nbsp;<input type="radio" name="isIndex" id="isIndex" value="1" style="cursor:pointer" checked="checked" /> 是 &nbsp;<input type="radio" name="isIndex" id="isIndex" value="0" style="cursor:pointer" /> 否 &nbsp;&nbsp;&nbsp;  <red>*</red></td>
				<td class="td_value">是否乱序不能为空</td>
			</tr>
			<tr>
				<td class="td_lable">试卷类型：</td>
			<td class="td_value"><select name="examPaperType" style="width:128px;" id="examPaperType"
						style="width: 135px;">
								<option value="0">---请选择---</option>
							<c:forEach items="${papersTyep}" var="paper">
							<c:choose>
								<c:when test="${paper.itemFlag==exampaper.examPaperType}">
									<option value="${paper.itemFlag}" selected="selected">
										${paper.name}</option>
								</c:when>
								<c:otherwise>
									<option value="${paper.itemFlag}">${paper.name}</option>
								</c:otherwise>
							</c:choose>

						</c:forEach>
					</select>&nbsp;<red>*</red></td>
					<td class="td_value">试卷类型不能为空</td>
			</tr>
			<tr style="margin-top: 50px;">
				<td colspan="8" align="center" "><input
					id="submitPrapre" type="button" value="提交" />
					<input id="updateForm" type="button" onclick="hiddenDialog();"
					value="取消" /></td>
			</tr>
		</table>
	</div>
	</form>

	<script type="text/javascript">
	
   function addExampaper(editType){
	   
		var scnum = 0;

		for(var i=0;i< document.getElementById("exampaperForm").elements.length; i++){
			if(document.getElementById("exampaperForm").elements[i].type == 'checkbox'&& document.getElementById("exampaperForm").elements[i].name == 'sc'){
				if(document.getElementById("exampaperForm").elements[i].checked){
					scnum++;
				}
			}
		}
		if(scnum=="0"){
			 $.ligerDialog.warn('生成考卷请先选择题目');
			return false;
		}
		
	   var titleInfo="新增试卷页面";
            $.ligerDialog.open({
               target:$('#addque'),
               height:'280',
               width: '600',
               title : titleInfo,
               showMax: false,
               showToggle: false,
               showMin: false,
               isResize: true,
               slide: false
           });
        }
        function hiddenDialog(){
			$(".l-dialog,.l-window-mask").css("display","none"); //隐藏窗口和遮罩层
		}
     function cancel(){
	    $("#addque").css("display","none");
	 }

</script>

	<script language="javascript">
	<%//if(request.getAttribute("selectList")!=null){
			//List list = (List)request.getAttribute("selectList");
			for (int i = 0; i < list.size(); i++) {
				tQuestions questionsVO = (tQuestions) list.get(i);%>
			addQuestions('<%=questionsVO.getPkAutoId()%>','<%=questionsVO.getQuesType()%>','<%=String.valueOf(questionsVO.getQuesScore())%>');
		<%}
			//}%>
</script>
</body>
</html:html>