<%@page contentType="text/html; charset=utf-8" import="java.util.*,cn.sh.ideal.model.ExamJoinVO" pageEncoding="UTF-8" language="java"%>
<%
	Object examTimeLenObj = request.getAttribute("examTimeLen");
	String examTimeLen = "0";
	if(examTimeLenObj!=null && !"".equals(examTimeLenObj)){
		examTimeLen = examTimeLenObj.toString();
	}
	int et = Integer.parseInt(examTimeLen);
	String examExampaperExamineId = (String)request.getAttribute("examExampaperExamineId");
	String localHostIp = request.getRemoteAddr();
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="true">
<head>
	<title>考试中...</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">

   <%--  <link href="<%=path%>/css/utils/css.css" rel="stylesheet" type="text/css"> --%>
	<link href="<%=path%>/css/utils/xtree.css" rel="stylesheet" type="text/css">
	<link href="<%=path%>/css/utils/repository.css" rel="stylesheet" type="text/css">
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
	<%-- <script language="javascript" src="<%=path%>/js/utils/ms-1.2.2-core-yc.js"></script>
	<script language="javascript" src="<%=path%>/js/utils/utils.js"></script>
	<script language="javascript" src="<%=path%>/js/utils/check_char.js"></script>
	<script language="javascript" src="<%=path%>/js/utils/quality_check.js"></script>
	 <script src="<%=path%>/js/jquery-1.9.1.js" type="text/javascript" charset="utf-8"></script> 
     <script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
     <script language="javascript" src="<%=path%>/js/jquery.nicescroll.min.js"></script> 
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script> 
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="<%=path %>/js/ajaxSession.js" type="text/javascript"></script> --%>
	<base target="_self">
</head>

 <script type="text/javascript"> 
 var path="<%=path%>";
document.write("<scr"+"ipt src=\""+path+"/js/jquery-1.9.1.js\"></sc"+"ript>") ;
document.write("<scr"+"ipt src=\""+path+"/lib/ligerUI/js/core/base.js\"></sc"+"ript>") ;
document.write("<scr"+"ipt src=\""+path+"/js/jquery.nicescroll.min.js\"></sc"+"ript>") ;
document.write("<scr"+"ipt src=\""+path+"/lib/ligerUI/js/plugins/ligerDrag.js\"></sc"+"ript>") ;
document.write("<scr"+"ipt src=\""+path+"/lib/ligerUI/js/plugins/ligerDialog.js\"></sc"+"ript>") ;
document.write("<scr"+"ipt src=\""+path+"/js/ajaxSession.js\"></sc"+"ript>") ; 
document.write("<scr"+"ipt src=\""+path+"/js/utils/ms-1.2.2-core-yc.js\"></sc"+"ript>") ; 
document.write("<scr"+"ipt src=\""+path+"/js/utils/utils.js\"></sc"+"ript>") ; 
document.write("<scr"+"ipt src=\""+path+"/js/utils/check_char.js\"></sc"+"ript>") ;
document.write("<scr"+"ipt src=\""+path+"/js/utils/quality_check.js\"></sc"+"ript>") ;
</script>
<style type="text/css">

#addExampaper {
	position: absolute;
	left: expression(( body.clientWidth-300)/ 2 -150 );
	top: expression(( body.clientHeight-90)/ 2 -100 );
	width: 620;
	height: 160;
	z-index: 999;
	margin: 0;
	background-color: #CACAFF;
	overflow: hidden;
}
*{margin:0}
input[type="button"]{
	FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0,startColorStr=#01aacf,endColorStr=#0165a2) !important; /*IE 6 7 8*/ 
	background: -ms-linear-gradient(top, #01aacf,  #0165a2) !important;        /* IE 10 */
	background:-moz-linear-gradient(top,#01aacf,#0165a2) !important;/*鐏嫄*/ 
	background:-webkit-gradient(linear, 0% 0%, 0% 100%,from(#01aacf), to(#0165a2)) !important;/*璋锋瓕*/ 
	background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#01aacf), to(#0165a2)) !important;      /* Safari 4-5, Chrome 1-9*/
	background: -webkit-linear-gradient(top, #01aacf, #0165a2) !important;   /*Safari5.1 Chrome 10+*/
	background: -o-linear-gradient(top, #01aacf, #0165a2) !important;;  /*Opera 11.10+*/
	color: #FFFFFF !important;
    border: 1px solid #0297bf !important;
    padding: 0 15px;
    border-radius:3px ;
    width: initial !important;
    height: 25px !important;
    cursor:pointer;
}
.l-dialog-tc {
    background: #3b98c8 !important;
}
.l-dialog-title {
    color: #FFFFFF !important;
}


.one {
	text-align: left; //居中
	font-size: 200px; //字体大小为12像素
	line-height: 20px;
	height: 20px;
	font-weight: bold; //加粗
	width: 200px;
	margin: auto;
}

.exampaper {
	border: 3px double #8ab8da;
	width: 100%;
}

.exampaper .title {
	text-align: center;
	height: 50px;
	line-height: 50px;
	font-family: 幼圆, 宋体;
	font-weight: bold;
	font-size: 20pt;
	color: #2e629d;
}

.exampaper .title2 {
	text-align: center;
	height: 28px;
	line-height: 28px;
	font-size: 11pt;
	color: #4f4f4f;
}

.exampaper .qustype {
	border-top: 1px solid #8ab8da;
	border-left: 0;
	border-right: 0;
	border-bottom: 0;
	width: 100%;
	border-collapse: collapse;
	color: #5a5a5a;
}

.exampaper .qustype td {
	border-top: 1px solid #bbd3e4;
	height: 22px;
}

.exampaper .qustype .qust {
	height: 24px;
	line-height: 24px;
	font-size: 11pt;
	font-family: Arial, 宋体;
	color: #4f4f4f;
	background-color: #f8f8f8;
}

.exampaper .qustype .answ {
	font-size: 10pt;
	font-family: Arial, 宋体;
	padding-left: 32px;
}

.exampaper .two {
	color: #4f4f4f;
	font-size: 11pt;
	font-weight: bold;
	height: 28px;
	line-height: 28px;
	padding-left: 4px;
	background: url(<%=path%>/img/utils/bg_title_exampaper.gif) repeat-x;
}

#timing {
	position: fixed;
	top: 20px;
	right: 15px;
	width: 120px;
	/*height: 105px;*/
	font-size: 11pt;
	border: 1px solid #feca9b;
	background-color: #ffffff;
}

#timing .title {
	width: 100%;
	height: 23px;
	line-height: 23px;
	font-size: 11pt;
	border-top: 1px solid #ffffff;
	border-bottom: 1px solid #feca9b;
	background-color: #ffd2aa;
	text-align: center;
	color: #4f4f4f;
}

#timing #minute {
	width: 100%;
	font-family: Arial;
	font-size: 16pt;
	border: 0;
	background-color: #ffffff;
	text-align: center;
	padding-top: 10px;
}

#timing #message {
	width: 100%;
	font-family: Arial;
	font-size: 10pt;
	border: 0;
	background-color: #ffffff;
	text-align: center;
	padding-top: 10px;
	color: green;
}
input[type="button"],.l-dialog-btn-inner,.l-button,input[type="reset"]{
	FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0,startColorStr=#01aacf,endColorStr=#0165a2) !important; /*IE 6 7 8*/ 
	background: -ms-linear-gradient(top, #01aacf,  #0165a2) !important;        /* IE 10 */
	background:-moz-linear-gradient(top,#01aacf,#0165a2) !important;/*鐏嫄*/ 
	background:-webkit-gradient(linear, 0% 0%, 0% 100%,from(#01aacf), to(#0165a2)) !important;/*璋锋瓕*/ 
	background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#01aacf), to(#0165a2)) !important;      /* Safari 4-5, Chrome 1-9*/
	background: -webkit-linear-gradient(top, #01aacf, #0165a2) !important;   /*Safari5.1 Chrome 10+*/
	background: -o-linear-gradient(top, #01aacf, #0165a2) !important;;  /*Opera 11.10+*/
	color: #FFFFFF !important;
    border: 1px solid #0297bf !important;
    padding: 0 15px;
    border-radius:3px ;
    width: initial !important;
    height: 25px !important;
    cursor:pointer;
    text-indent:0;
}
#shadowDiv {
	display: none;
	width: 100%;
	height: 100%;
	position: absolute;
	top: 0px;
	left: 0px;
	background-color: #606060;
	filter: alpha(opacity = 100);
	z-index: 3333
}

</style>
<script language="javascript">
	var global_isSubNow = false; // 当前是否正在提交答案
	var global_isHandIn = false;//当前是否正在交卷 
	var global_isDifferentSubNow = false;

	function startExamMinute() {
	    //初始化滚动条
		$("body").niceScroll({
					cursorcolor:"#3B98C8", //颜色
					horizrailenabled: false,//水平滚动条
					railoffset:true,
					cursorwidth:"10px" //滚动条宽带
		});
	
		var d = document.createElement("DIV");
		d.id = "shadowDiv";
		document.body.insertBefore(d, document.body.firstChild);
		d.style.display = 'block';
		d.style.height = document.body.scrollHeight;
		$.ligerDialog.question("开始考试后考试倒计时将执行，考试时间到会强制交卷！");
		d.style.display = 'none';

		setInterval("setTimeEnd()", 1000);// 开始考试倒计时
		setTimeEnd();
		
		saveExamResult(); // 开始自动保存试卷
		setTimeout("saveExamRstByTimer()", 30000);//
		
	}

	var endminutes = <%=et%> * 60;
	var isNoQZSubmit = true;
	var endExamTime = new Date(new Date().getTime()+endminutes*1000);
	function setTimeEnd() {
		var sysRemainderTime = Math.floor((endExamTime - new Date())/1000);
		endminutes = sysRemainderTime<endminutes?sysRemainderTime:(endminutes-1);//管理员强制提交判断  原始代码endminutes = endminutes - 1;
		if(endminutes<=0) {
			global_isHandIn=true;
			isNoQZSubmit = false;
			$.ligerDialog.warn("考试时间到了,系统将强制交卷!");
			setTimeout("qiangzhitijExam()", 200);
		}

		var strdisplay = parseInt(endminutes/60) + ":" + (endminutes%60<10?("0"+endminutes%60):endminutes%60);
		document.getElementById("minute").innerHTML=strdisplay;
		
	}
	
	//
	function qiangzhitijExam(){
	        submitExam(); // 考试时间到强制交卷
			isAutoSumbit = false;
			//$("#examJoinForm").attr("action", "<%=path%>/controller/examJoin/saveUserExamResultByAjax.do");
			//$('#examJoinForm').submit();
			myAjaxSubmitExamResult('<%=path%>/controller/examJoin/saveUserExamResultByAjax.do',function(data){
			   if(data==undefined || data==""){
				   $.ligerDialog.error("提交试卷失败!请联系管理员");
				}else{
					if(data["isScs"] == "1"){
						$.ligerDialog.success("交卷成功!");
					}else{
						$.ligerDialog.error("提交试卷失败!请联系管理员");
					}
					var isIntoPageByMain=$("#isIntoPageByMain").val();
					if(isIntoPageByMain=="0"){ //判断是否是从待考列表进入该页面   0标识是
					   window.parent.exam_search();
					}else if(isIntoPageByMain=="1"){ //判断 1是从首页进入
					   window.parent.getExam();
					}
			        frameElement.dialog.close();
				}
			}); 
			
	}

	function showValue(a) {
	}

	function checkValue(a)   //新增判断改错题，对于选择正确不给予错误答案填写。
	{
		var to_radio = a.name;
		var numm = to_radio.substring(7)-1;//截取typeTwo后的数字
		if(a.value == 'Y'){
			document.getElementsByName("typeTwoAnswer")[numm].value = "";
			document.getElementsByName("typeTwoAnswer")[numm].readOnly=true;
		}else{
			document.getElementsByName("typeTwoAnswer")[numm].readOnly=false;
		}
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

	function Trim(str,is_global)
	{
		var result = str.replace(/(^\s+)|(\s+$)/g,"");

		if(is_global.toLowerCase()=="g"){
			result = result.replace(/\s/g,"");
		}
		return result;
	}

	function addQuestions(id,quesType,quesScore,resultsAnswer,rltAnswer,rltCorrectAnswer)
	{
		var quesContent = document.getElementById('questionsContent'+quesContentCount).innerHTML;
		var quesPick = document.getElementById('questionsPick'+quesContentCount).innerHTML;
		quesPick = Trim(quesPick,'g'); //去空格函数
		
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
			if(rltAnswer == "Y"){
				cells[0].innerHTML="<input type=radio name=typeOne"+quesAnswer1+" value=Y checked onclick=showValue(this)>正确&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=radio name=typeOne"+quesAnswer1+" value=N onclick=showValue(this)>错误";
			}else if(rltAnswer == "N"){
				cells[0].innerHTML="<input type=radio name=typeOne"+quesAnswer1+" value=Y onclick=showValue(this)>正确&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=radio name=typeOne"+quesAnswer1+" value=N checked onclick=showValue(this)>错误";
			}else{
				cells[0].innerHTML="<input type=radio name=typeOne"+quesAnswer1+" value=Y onclick=showValue(this)>正确&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=radio name=typeOne"+quesAnswer1+" value=N onclick=showValue(this)>错误";
			}
			cells[1] = row.insertCell();
			cells[1].innerHTML="<input type=hidden name=quesIdOne value='"+id+"' >";
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
			var s = "";
			if(rltAnswer == "Y"){
				s = "<input type=radio name=typeTwo"+quesAnswer2+" value=Y checked onclick=checkValue(this)>正确&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=radio name=typeTwo"+quesAnswer2+" value=N onclick=checkValue(this)>错误";
				s += "<br/><textarea cols=100 rows=2 name=typeTwoAnswer readOnly=readOnly>";
			}else if(rltAnswer == "N"){
				s = "<input type=radio name=typeTwo"+quesAnswer2+" value=Y onclick=checkValue(this)>正确&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=radio name=typeTwo"+quesAnswer2+" value=N checked onclick=checkValue(this)>错误";
				s += "<br/><textarea cols=100 rows=2 name=typeTwoAnswer >"+rltCorrectAnswer+"</textarea>";
			}else{
				s = "<input type=radio name=typeTwo"+quesAnswer2+" value=Y onclick=checkValue(this)>正确&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=radio name=typeTwo"+quesAnswer2+" value=N onclick=checkValue(this)>错误";
				s += "<br/><textarea cols=100 rows=2 name=typeTwoAnswer readOnly=readOnly>";
			}
			cells[0].innerHTML = s;
			cells[1] = row.insertCell();
			cells[1].innerHTML="<input type=hidden name=quesIdTwo value='"+id+"' >";
			num2++;
			quesAnswer2++;
		}else if(quesType=="3"){//类型三:单选题
			document.getElementById("type3").style.display='block';
			var row = document.all.quesType3.insertRow();
			var cells = new Object;
			cells[0] = row.insertCell();
			cells[0].className="qust";
			cells[0].innerHTML="&nbsp;&nbsp;"+num3+".&nbsp;&nbsp;"+quesContent+"（"+quesScore+"分）";
			cells[1] = row.insertCell();
			cells[1].innerHTML="<input type=hidden name=quesIdThree value='"+id+"' >";
			var ques = quesPick.split(";");
			for(var i = 0 ; i< ques.length;i++){
				var row = document.all.quesType3.insertRow();
				var cells = new Object;
				cells[0] = row.insertCell();
				cells[0].className="answ";
				if(rltAnswer==ques[i].substring(0,1)){
					cells[0].innerHTML="<input type=radio name=typeThree"+quesAnswer3+" value="+ques[i].substring(0,1)+" checked onclick=showValue(this)>"+ques[i];
				}else{
					cells[0].innerHTML="<input type=radio name=typeThree"+quesAnswer3+" value="+ques[i].substring(0,1)+" onclick=showValue(this)>"+ques[i];
				}	
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
			cells[1] = row.insertCell();
			cells[1].innerHTML="<input type=hidden name=quesIdFour value='"+id+"' >";
			var ques = quesPick.split(";");
			if(rltAnswer!="null" && rltAnswer!=""){
				for(var i=0;i<ques.length;i++){
					var row = document.all.quesType4.insertRow();
					var cells = new Object;
					cells[0] = row.insertCell();
					cells[0].className="answ";
					
					var selOption = "";
					if(rltAnswer!=""){
						selOption = rltAnswer.substring(0,1);
					}
					if(selOption==ques[i].substring(0,1)){
						cells[0].innerHTML="<input type=checkbox name=typeFour"+quesAnswer4+" id="+id+" value="+ques[i].substring(0,1)+" checked onclick=showValue(this)>"+ques[i];
						rltAnswer = rltAnswer.substring(1);
					}else{
						cells[0].innerHTML="<input type=checkbox name=typeFour"+quesAnswer4+" id="+id+" value="+ques[i].substring(0,1)+" onclick=showValue(this)>"+ques[i];
					}
				}
				
		   }else{
				for(var i = 0 ; i< ques.length;i++){
					var row = document.all.quesType4.insertRow();
					var cells = new Object;
					cells[0] = row.insertCell();
					cells[0].className="answ";
					cells[0].innerHTML="<input type=checkbox name=typeFour"+quesAnswer4+" id="+id+" value="+ques[i].substring(0,1)+" onclick=showValue(this)>"+ques[i];
				}
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
			cells[1] = row.insertCell();
			cells[1].innerHTML="<input type=hidden name=quesIdFive value='"+id+"' >";
			var ques = quesPick.split(";");
			if(rltAnswer!="null" && rltAnswer!=""){
				for(var i=0;i<ques.length;i++){
					var row = document.all.quesType5.insertRow();
					var cells = new Object;
					cells[0] = row.insertCell();
					cells[0].className="answ";
					
					var selOption = "";
					if(rltAnswer!=""){
						selOption = rltAnswer.substring(0,1);
					}
					if(selOption==ques[i].substring(0,1)){
						cells[0].innerHTML="<input type=checkbox name=typeFive"+quesAnswer5+" id="+id+" value="+ques[i].substring(0,1)+" checked onclick=showValue(this)>"+ques[i];
						rltAnswer = rltAnswer.substring(1);
					}else{
						cells[0].innerHTML="<input type=checkbox name=typeFive"+quesAnswer5+" id="+id+" value="+ques[i].substring(0,1)+" onclick=showValue(this)>"+ques[i];
					}
				}
			}else{
				for(var i = 0 ; i< ques.length;i++){
					var row = document.all.quesType5.insertRow();
					var cells = new Object;
					cells[0] = row.insertCell();
					cells[0].className="answ";
					cells[0].innerHTML="<input type=checkbox name=typeFive"+quesAnswer5+" id="+id+"  value="+ques[i].substring(0,1)+" onclick=showValue(this)>"+ques[i];
				}
			}
			num5++;
			quesAnswer5++;
		}else if(quesType=="6"){//类型六:填空题 需要填空的部分，用“____”标识，正确答案,多个填空之间以“#”分隔。填空题答案个数必须与题目描述中的填空个数相同。
			if(rltAnswer!="null" && rltAnswer!=""){
				var rltArr = rltAnswer.split("####");
				for(var i=0;i<rltArr.length;i++){
					quesContent = quesContent.replace("____","<input type= 'text' maxlength='500' style= 'border-width:0;border-bottom:1 solid blue;text-align:center' name= 'tkt"+quesAnswer6+"' value= '"+rltArr[i]+"'>"); //把下划线替换成文本框
				}
				
			}else{
				quesContent = quesContent.replace(/____/g,"<input type= 'text' maxlength='500' style= 'border-width:0;border-bottom:1 solid blue;text-align:center' name= 'tkt"+quesAnswer6+"' value= ''>"); //把下划线替换成文本框
			}
			document.getElementById("type6").style.display='block';
			var row = document.all.quesType6.insertRow();
			var cells = new Object;
			cells[0] = row.insertCell();
			cells[0].className="qust";
			cells[0].innerHTML="&nbsp;&nbsp;"+num6+".&nbsp;&nbsp;"+quesContent+"（"+quesScore+"分）";
			cells[1] = row.insertCell();
			cells[1].innerHTML="<input type=hidden name=quesIdSix value='"+id+"' >";
			var row = document.all.quesType6.insertRow();
			var cells = new Object;
			cells[0] = row.insertCell();
			
			cells[0].innerHTML="答：<textarea cols=100 rows=3 name=typeSix"+quesAnswer6+" onclick=\"showValue(this);\" style=\"vertical-align:bottom;\">";
			
			document.getElementById( "quesType6").rows[quesAnswer6*2].style.display = "none";  //隐藏行
			num6++;
			quesAnswer6++;
		}else if(quesType=="7"){//类型七:问答题
			document.getElementById("type7").style.display='block';
			var row = document.all.quesType7.insertRow();
			var cells = new Object;
			cells[0] = row.insertCell();
			cells[0].className="qust";
			cells[0].innerHTML="&nbsp;&nbsp;"+num7+".&nbsp;&nbsp;"+quesContent+"（"+quesScore+"分）";
			cells[1] = row.insertCell();
			cells[1].innerHTML="<input type=hidden name=quesIdSeven value='"+id+"' >";
			var row = document.all.quesType7.insertRow();
			var cells = new Object;
			cells[0] = row.insertCell();
			if(rltAnswer!="null"){
				cells[0].innerHTML="答：<textarea cols=100 rows=5 name=typeSeven"+quesAnswer7+" onclick=\"showValue(this);\" style=\"vertical-align:bottom;\">"+rltAnswer+"</textarea>";
			}else{
				cells[0].innerHTML="答：<textarea cols=100 rows=5 name=typeSeven"+quesAnswer7+" onclick=\"showValue(this);\" style=\"vertical-align:bottom;\">";
			}
			num7++;
			quesAnswer7++;
		}/*else if(quesType=="8"){//案例分析题
			document.getElementById("type8").style.display='block';
			var row = document.all.quesType8.insertRow();
			var cells = new Object;
			cells[0] = row.insertCell();
			cells[0].className="qust";
			cells[0].innerHTML="&nbsp;&nbsp;"+num8+".&nbsp;&nbsp;<a href=# >"+quesContent+"</a>（"+quesScore+"分）";//onClick=openwindow('"+id+"');
			cells[1] = row.insertCell();
			cells[1].innerHTML="<input type=hidden name=quesIdEight value='"+id+"' >";
			var row = document.all.quesType8.insertRow();
			var cells = new Object;
			if(rltAnswer!="null"){
				cells[0].innerHTML="答：<textarea cols=100 rows=5 name=typeEight"+quesAnswer8+" onclick=\"showValue(this);\" style=\"vertical-align:bottom;\">"+rltAnswer+"</textarea>";
			}else{
				cells[0].innerHTML="答：<textarea cols=100 rows=5 name=typeEight"+quesAnswer8+" onclick=\"showValue(this);\" style=\"vertical-align:bottom;\">";
			}
			num8++;
			quesAnswer8++;
		}*/
		quesContentCount++;
	}

	function radioCount(radioflag)
	{
		var radio_count=0;
		var eles = document.getElementById("examJoinForm").elements;
		for(var i=0;i<eles.length;i++){
			if(eles[i].type == 'radio'&& eles[i].name == 'typeThree'+radioflag){
				radio_count++;
			}
		}
		return radio_count;
	}

	function checkbox11Count(checkbox1flag)
	{
		var checkbox_count=0;
		var eles = document.getElementById("examJoinForm").elements;
		for(var i=0;i<eles.length; i++){
			if(eles[i].type == 'checkbox'&& eles[i].name == 'typeFour'+checkbox1flag){
				checkbox_count++;
			}
		}
		return checkbox_count;
	}

	function checkbox22Count(checkbox2flag)
	{
		var checkbox22_count=0;
		var eles = document.getElementById("examJoinForm").elements;
		for(var i=0;i<eles.length; i++){
			if(eles[i].type == 'checkbox'&& eles[i].name == 'typeFive'+checkbox2flag){
				checkbox22_count++;
			}
		}
		return checkbox22_count;
	}
	
	function differentSaveExamResult(){
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
		var num1=0;
		var num2=0;
		var num3=0;
		var num4=0;
		var num5=0;
		var num6=0;
		var num7=0;
		var num8=0;

		var strParm1 = "";
		var strParm2 = "";
		var strParm3 = "";
		var strParm4 = "";
		var strParm5 = "";
		var strParm6 = "";
		var strParm7 = "";
		var strParm8 = "";

		var radioCount1 =0;  //设定判断题单选按钮的个数
		var radioCount2 =0;  //设定判断改错题单选按钮的个数
		var radioCount3 =0;  //设定单选题单选按钮的个数
		var checkbox1Count =0; //设定多选题单选按钮的个数
		var checkbox2Count =0; //设定不定项选题单选按钮的个数

		var eles = document.getElementById("examJoinForm").elements;
		var type_pdt="";
		var type_pdgct="";
		var type_dxt="";
		var type_ddxt="";
		var type_bdx="";
		var type_dxtByUserData="";
		var type_bdxxzByUserData="";
	  	for(var i=0;i<eles.length;i++){
	  		//判断题取值
			if(eles[i].type == 'radio'&& eles[i].name == 'typeOne'+quesAnswer1){
				radioCount1++;
				if(eles[i].checked){
				
					if(type_pdt == ""){
						type_pdt = document.getElementsByName("quesIdOne")[num1].value+"@&@"+eles[i].value;
					}else{
						type_pdt = type_pdt + eles[i].value;
					}
				}else{
					if(type_pdt == ""){
						type_pdt = document.getElementsByName("quesIdOne")[num1].value+"@&@";
					}else{
						type_pdt = type_pdt+"";
					}
				}
				if(radioCount1>=2)  //判断单选按钮是否超过2个，超过quesAnswer1++;到下一组
				{
					strParm1 = strParm1+"@%@"+type_pdt;
					type_pdt = "";
					quesAnswer1++;
					num1++;
					radioCount1 = 0;
				}
			}
			//判断改错题取值
			if(eles[i].type == 'radio'&& eles[i].name == 'typeTwo'+quesAnswer2){
				radioCount2++;
				if(eles[i].checked){

					if(type_pdgct == ""){
						type_pdgct = document.getElementsByName("quesIdTwo")[num2].value+"@&@"+eles[i].value+"@&@"+document.getElementsByName("typeTwoAnswer")[num2].value;
					}else{
						type_pdgct = document.getElementsByName("quesIdTwo")[num2].value+"@&@"+eles[i].value+"@&@"+document.getElementsByName("typeTwoAnswer")[num2].value;
					}
				}else{
					if(type_pdgct == ""){
						type_pdgct = document.getElementsByName("quesIdTwo")[num2].value+"@&@@&@";
					}else{
						type_pdgct = type_pdgct + "";
					}
				}
				if(radioCount2>=2)  //判断题单选按钮是否超过2个，超过quesAnswer2++;到下一组
				{
					strParm2 = strParm2+"@%@"+type_pdgct;
					type_pdgct = "";
					quesAnswer2++;
					num2++;
					radioCount2 = 0;
				}
			}
			//单选题取值
			if(eles[i].type == 'radio' && eles[i].name == 'typeThree'+quesAnswer3){
				radioCount3++;
				if(eles[i].checked){
		
					if(type_dxt == ""){
						type_dxt = document.getElementsByName("quesIdThree")[num3].value+"@&@"+eles[i].value;
					}else{
						type_dxt=  type_dxt + eles[i].value;
					}
				}else{
					if(type_dxt == ""){
						type_dxt = document.getElementsByName("quesIdThree")[num3].value+"@&@";
					}else{
						type_dxt=  type_dxt + "";
					}
				}
				if(radioCount3 == radioCount(quesAnswer3))  //判断单选题单选按钮是否超过指定个数，超过radioflag++;到下一组
				{
					strParm3 = strParm3+"@%@"+type_dxt;
					type_dxt = "";
					quesAnswer3++;
					num3++;
					radioCount3 = 0;
				}
			}
			//多选题取值
			if(eles[i].type == 'checkbox'&& eles[i].name == 'typeFour'+quesAnswer4){
				checkbox1Count++;
				if(eles[i].checked){
					
					if(type_ddxt == ""){
						type_ddxt = eles[i].id+"@&@"+eles[i].value;
					}else{
						type_ddxt = type_ddxt + eles[i].value;
					}
				}else{
					if(type_ddxt == ""){
						type_ddxt = eles[i].id+"@&@";
					}else{
						type_ddxt = type_ddxt + "";
					}
				}
				if(checkbox1Count == checkbox11Count(quesAnswer4))  //判断多选题单选按钮是否超过指定个数，超过checkbox1flag++;到下一组
				{
					strParm4 = strParm4+"@%@"+type_ddxt;
					type_ddxt = "";
					quesAnswer4++;
					num4++;
					checkbox1Count = 0;
					type_dxtByUserData = "";
				}
			}
			
			//不定项选择题取值
			if(eles[i].type == 'checkbox'&& eles[i].name == 'typeFive'+quesAnswer5){
				checkbox2Count++;
				if(eles[i].checked){
					
					if(type_bdx == ""){
						type_bdx = eles[i].id+"@&@"+eles[i].value;
					}else{
						type_bdx = type_bdx + eles[i].value;
					}
				}else{
					if(type_bdx == ""){
						type_bdx = eles[i].id+"@&@";
					}else{
						type_bdx = type_bdx + "";
					}
				}
				if(checkbox2Count == checkbox22Count(quesAnswer5))  //判断不定项选题单选按钮是否超过指定个数，超过checkbox2flag++;到下一组
				{
					strParm5 = strParm5+"@%@"+type_bdx;
					type_bdx = "";
					quesAnswer5++;
					num5++;
					checkbox2Count = 0;
					type_bdxxzByUserData = "";
				}
			}
			//填空题取值
			if(eles[i].type == 'textarea'&& eles[i].name == 'typeSix'+quesAnswer6){
				var tktValue="";
				var tkt = document.getElementsByName('tkt'+quesAnswer6);
				for(var j=0;j<tkt.length;j++){
					if(tktValue==""){
						tktValue = tkt[j].value+"####";
					}else{
						tktValue = tktValue + tkt[j].value+"####";
					}
				}
				tktValue = tktValue.substring(0,tktValue.length-4);
				//document.getElementById('typeSix'+quesAnswer6).value = tktValue;
				//tktValue="";
				if(strParm6 == ""){
					strParm6 = document.getElementsByName("quesIdSix")[num6].value+"@&@"+tktValue;
				}else{
					strParm6 = strParm6 + "@%@"+document.getElementsByName("quesIdSix")[num6].value+"@&@"+tktValue;
				}
				
				quesAnswer6++;
				num6++;
			}
			//问答题取值
			if(eles[i].type == 'textarea'&& eles[i].name == 'typeSeven'+quesAnswer7){
				if(strParm7 == ""){
					strParm7 = document.getElementsByName("quesIdSeven")[num7].value+"@&@"+eles[i].value;
				}else{
					strParm7 = strParm7 + "@%@"+document.getElementsByName("quesIdSeven")[num7].value+"@&@"+eles[i].value;
				}
				
				quesAnswer7++;
				num7++;
			}
			/* //案例分析题取值
			if(eles[i].type == 'textarea'&& eles[i].name == 'typeEight'+quesAnswer8){
				if(strParm8 == ""){
					strParm8 = document.getElementsByName("quesIdEight")[num8].value+"@&@"+eles[i].value;
				}else{
					strParm8 = strParm8 + "@%@"+document.getElementsByName("quesIdEight")[num8].value+"@&@"+eles[i].value;
				}
				
				
				quesAnswer8++;
				num8++;
			} */
		}
		var strdisplay = parseInt(endminutes/60);
		var answerStr ="";
		try{
			myAjaxJsonpExamResult(answerStr,strdisplay,0);
		}catch(e){
		}
		
		document.getElementById("quesAnswer").value = strParm6+"@%@"+strParm7;//+"@%@"+strParm8; //填空 问答 案例分析
		document.getElementById("quesAnswerTwo").value = strParm2; //判断改错
		document.getElementById("quesAnswerFourAndFive").value = strParm1+"@%@"+strParm3+"@%@"+strParm4+"@%@"+strParm5; //判断 单选 多选 不定项选择
	}
	
	function saveExamRstByTimer(){
		if (global_isDifferentSubNow) { // 若当前正在提交答案2分钟后再次保存答案
			setTimeout("saveExamRstByTimer()", 30000);
			return;
	 	}
	 	
	  	var strdisplay = parseInt(endminutes/60);
		var answerStr = "";
		try{
			myAjaxJsonpExamResult(answerStr,strdisplay,0);
		}catch(e){
		}
		setTimeout("saveExamRstByTimer()", 30000);
	}

	function saveExamResult() {
		if(global_isHandIn){//若当前正在交卷 
			return;
		}
		if (global_isSubNow) { // 若当前正在提交答案2分钟后再次保存答案
			setTimeout("saveExamResult()", 120000);
			return;
	 	}
	 	global_isSubNow = true; // 更新提交状态
		
		global_isDifferentSubNow = true;
	  	differentSaveExamResult();
		global_isDifferentSubNow = false;
		$.ajax({
		    url:'<%=path%>/controller/examJoin/addUserExamResultByAjax.do',
			type:'post',
			dataType:'json',
			data:$('#examJoinForm').serialize(),
			success:function(data) {
				global_isSubNow = false;
				document.getElementById('message').innerHTML=data["message"];
				setTimeout("saveExamResult()", 120000); // 2分钟后再次保存答案
			}
		});
	}
	//ajax跨域提交
	function myAjaxJsonpExamResult(answerStr,strdisplay,state){
			jQuery.ajax({
				'type':'get',
				'url':'${requestScope.heartbeatIpAdd}',
				'data':{
					'kaoshiId':'${requestScope.examExampaperExamineId}',
					'userId':'${requestScope.userId}',
					'answerStr':answerStr,
					'ipAddr':'<%=localHostIp%>',
					'state':state,
					'remainingTime':strdisplay,
					'examId':'${requestScope.mySelExamId }'
				},
				'dataType':'jsonp',// jsonp
				'jsonp':'callback',
				'contentType': "application/jsonp; charset=utf-8",
				'async':false,
				'cache':false,
				'success':function(data){
					if(isNoQZSubmit){
						if(data!=undefined){
							if(data.flg=="3"){
								$.ligerDialog.warn("此考卷已被强制提交，5秒后将强制提交!");
								endminutes = 5;
							}
						}
					}
				}
			});
	}
	//ajax提交答案
	function myAjaxSubmitExamResult(urlStr,rtnSuccessFun){
		$.ajax({
		   url:urlStr,
			type:'POST',
			dataType:'json',
			data:$('#examJoinForm').serialize(),
			success:rtnSuccessFun,
			error:rtnError
		});
		
	}
	//保存试卷回调函数
	function rtnError(data){
		$.ligerDialog.error("保存试卷失败，请检查网络\n"+data);
		isAutoSumbit = false;
	}
	//临时保存试卷
	function tempSaveExamResult(){
		submitExam(0);
		myAjaxSubmitExamResult('<%=path%>/controller/examJoin/addUserExamResultByAjax.do',function(data){
			if(data==undefined || data==""){
				$.ligerDialog.error("保存试卷失败，请检查网络");
			}else{
				if(data["isScs"] == "1"){
					document.getElementById('message').innerHTML=data["message"];
					$.ligerDialog.success(data["message"]);
				}else{
					$.ligerDialog.warn(data["message"]);
				}
			}
		});
	}

	function submitExam(tempState) {
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
		var num1=0;
		var num2=0;
		var num3=0;
		var num4=0;
		var num5=0;
		var num6=0;
		var num7=0;
		var num8=0;

		var strParm1 = "";
		var strParm2 = "";
		var strParm3 = "";
		var strParm4 = "";
		var strParm5 = "";
		var strParm6 = "";
		var strParm7 = "";
		var strParm8 = "";

		var radioCount1 =0;  //设定判断题单选按钮的个数
		var radioCount2 =0;  //设定判断改错题单选按钮的个数
		var radioCount3 =0;  //设定单选题单选按钮的个数
		var checkbox1Count =0; //设定多选题单选按钮的个数
		var checkbox2Count =0; //设定不定项选题单选按钮的个数

		document.getElementById('comitFlag').value = "1";//表示通过提交按钮提交

		var eles = document.getElementById("examJoinForm").elements;
		var type_pdt="";
		var type_pdgct="";
		var type_dxt="";
		var type_ddxt="";
		var type_bdx="";
		var type_dxtByUserData="";
		var type_bdxxzByUserData="";
	  	for(var i=0;i<eles.length; i++){
	  		//判断题取值
			if(eles[i].type == 'radio'&& eles[i].name == 'typeOne'+quesAnswer1){
				radioCount1++;
				if(eles[i].checked){
					
					if(type_pdt == ""){
						type_pdt = document.getElementsByName("quesIdOne")[num1].value+"@&@"+eles[i].value;
					}else{
						type_pdt = type_pdt + eles[i].value;
					}
				}else{
					if(type_pdt == ""){
						type_pdt = document.getElementsByName("quesIdOne")[num1].value+"@&@";
					}else{
						type_pdt = type_pdt+"";
					}
				}
				if(radioCount1>=2)  //判断单选按钮是否超过2个，超过quesAnswer1++;到下一组
				{
					strParm1 = strParm1+"@%@"+type_pdt;
					type_pdt = "";
					quesAnswer1++;
					num1++;
					radioCount1 = 0;
				}
			}
			//判断改错题取值
			if(eles[i].type == 'radio'&& eles[i].name == 'typeTwo'+quesAnswer2){
				radioCount2++;
				if(eles[i].checked){
					
					if(type_pdgct == ""){
						type_pdgct = document.getElementsByName("quesIdTwo")[num2].value+"@&@"+eles[i].value+"@&@"+document.getElementsByName("typeTwoAnswer")[num2].value;
					}else{
						type_pdgct = document.getElementsByName("quesIdTwo")[num2].value+"@&@"+eles[i].value+"@&@"+document.getElementsByName("typeTwoAnswer")[num2].value;
					}
				}else{
					if(type_pdgct == ""){
						type_pdgct = document.getElementsByName("quesIdTwo")[num2].value+"@&@@&@";
					}else{
						type_pdgct = type_pdgct + "";
					}
				}
				if(radioCount2>=2)  //判断题单选按钮是否超过2个，超过quesAnswer2++;到下一组
				{
					strParm2 = strParm2+"@%@"+type_pdgct;
					type_pdgct = "";
					quesAnswer2++;
					num2++;
					radioCount2 = 0;
				}
			}
			//单选题取值
			if(eles[i].type == 'radio' && eles[i].name == 'typeThree'+quesAnswer3){
				radioCount3++;
				if(document.getElementById("examJoinForm").elements[i].checked){
					
					if(type_dxt == ""){
						type_dxt = document.getElementsByName("quesIdThree")[num3].value+"@&@"+eles[i].value;
					}else{
						type_dxt=  type_dxt + eles[i].value;
					}
				}else{
					if(type_dxt == ""){
						type_dxt = document.getElementsByName("quesIdThree")[num3].value+"@&@";
					}else{
						type_dxt=  type_dxt + "";
					}
				}

				if(radioCount3 == radioCount(quesAnswer3))  //判断单选题单选按钮是否超过指定个数，超过radioflag++;到下一组
				{
					strParm3 = strParm3+"@%@"+type_dxt;
					type_dxt = "";
					quesAnswer3++;
					num3++;
					radioCount3 = 0;
				}
			}
			//多选题取值
			if(eles[i].type == 'checkbox'&& eles[i].name == 'typeFour'+quesAnswer4){
				checkbox1Count++;
				if(eles[i].checked){
					
					if(type_ddxt == ""){
						type_ddxt = eles[i].id+"@&@"+eles[i].value;
					}else{
						type_ddxt = type_ddxt + eles[i].value;
					}
				}else{
					if(type_ddxt == ""){
						type_ddxt = eles[i].id+"@&@";
					}else{
						type_ddxt = type_ddxt + "";
					}
				}
				if(checkbox1Count == checkbox11Count(quesAnswer4))  //判断多选题单选按钮是否超过指定个数，超过checkbox1flag++;到下一组
				{
					strParm4 = strParm4+"@%@"+type_ddxt;
					type_ddxt = "";
					quesAnswer4++;
					num4++;
					checkbox1Count = 0;
					type_dxtByUserData = "";
				}
			}
			//不定项选择题取值
			if(eles[i].type == 'checkbox'&& eles[i].name == 'typeFive'+quesAnswer5){
                checkbox2Count++;
				if(eles[i].checked){
					
					if(type_bdx == ""){
						type_bdx = eles[i].id+"@&@"+eles[i].value;
					}else{
						type_bdx = type_bdx + eles[i].value;
					}
				}else{
					if(type_bdx == ""){
						type_bdx = eles[i].id+"@&@";
					}else{
						type_bdx = type_bdx + "";
					}
				}
				if(checkbox2Count == checkbox22Count(quesAnswer5))  //判断不定项选题单选按钮是否超过指定个数，超过checkbox2flag++;到下一组
				{
					strParm5 = strParm5+"@%@"+type_bdx;
					type_bdx = "";
					quesAnswer5++;
					num5++;
					checkbox2Count = 0;
					type_bdxxzByUserData = "";
				}
			}
			//填空题取值
			if(eles[i].type == 'textarea'&& eles[i].name == 'typeSix'+quesAnswer6){
				var tktValue="";
				var tkt = document.getElementsByName('tkt'+quesAnswer6);
				for(var j=0;j<tkt.length;j++)
				{
					if(tktValue==""){
						tktValue = tkt[j].value+"####";
					}else{
						tktValue = tktValue + tkt[j].value+"####";
					}
				}
				tktValue = tktValue.substring(0,tktValue.length-4);
				//document.getElementById('typeSix'+quesAnswer6).value = tktValue;
				//tktValue="";
				if(strParm6 == ""){
					strParm6 = document.getElementsByName("quesIdSix")[num6].value+"@&@"+tktValue;
				}else{
					strParm6 = strParm6 + "@%@"+document.getElementsByName("quesIdSix")[num6].value+"@&@"+tktValue;
				}
				
				quesAnswer6++;
				num6++;
			}
			
			//问答题取值
			if(eles[i].type == 'textarea'&& eles[i].name == 'typeSeven'+quesAnswer7){
				if(strParm7 == ""){
					strParm7 = document.getElementsByName("quesIdSeven")[num7].value+"@&@"+eles[i].value;
				}else{
					strParm7 = strParm7 + "@%@"+document.getElementsByName("quesIdSeven")[num7].value+"@&@"+eles[i].value;
				}
				
				quesAnswer7++;
				num7++;
			}
			//案例分析题取值
			if(eles[i].type == 'textarea'&& eles[i].name == 'typeEight'+quesAnswer8){
				if(strParm8 == ""){
					strParm8 = document.getElementsByName("quesIdEight")[num8].value+"@&@"+eles[i].value;
				}else{
					strParm8 = strParm8 + "@%@"+document.getElementsByName("quesIdEight")[num8].value+"@&@"+eles[i].value;
				}
				
				quesAnswer8++;
				num8++;
			}
		}
		var strdisplay = parseInt(endminutes/60);
		var answerStr = "";
		try{
			if(tempState!=undefined){
				myAjaxJsonpExamResult(answerStr,strdisplay,tempState);
			}else{
				myAjaxJsonpExamResult(answerStr,strdisplay,1);
			}
		}catch(e){
		}
		
		document.getElementById("quesAnswer").value = strParm6+"@%@"+strParm7+"@%@"+strParm8; //填空 问答 案例分析
		document.getElementById("quesAnswerTwo").value = strParm2; //判断改错
		document.getElementById("quesAnswerFourAndFive").value = strParm1+"@%@"+strParm3+"@%@"+strParm4+"@%@"+strParm5; //判断 单选 多选 不定项选择
	 }

	 var cnt_message = "";
	 function checkExampaper() {
	 	var alertValue = getCheckResult();
	 	if(cnt_message!=""){
	 		$.ligerDialog.warn(cnt_message);
	 		cnt_message = "";
	 		return;
	 	}
	 	if(alertValue==''){
			$.ligerDialog.success("您已答题完毕！");
		}else{
			$.ligerDialog.warn(alertValue+"以上考题没作答!");
		}
	 }

	 function getCheckResult() {
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
		var num1=0;
		var num2=0;
		var num3=0;
		var num4=0;
		var num5=0;
		var num6=0;
		var num7=0;
		var num8=0;
		var strParm1 = "判断题";
		var strParm2 = "判断改错题";
		var strParm3 = "单选题";
		var strParm4 = "多选题";
		var strParm5 = "不定项选择题";
		var strParm6 = "填空题";
		var strParm7 = "问答题";
		var strParm8 = "案例分析题";

		var radioCount1 =0;  //设定判断题单选按钮的个数
		var radioCount2 =0;  //设定判断改错题单选按钮的个数
		var radioCount3 =0;  //设定单选题单选按钮的个数
		var checkbox1Count =0; //设定多选题单选按钮的个数
		var checkbox2Count =0; //设定不定项选题单选按钮的个数

		var eles = document.getElementById("examJoinForm").elements;
		var type_pdt="";
		var type_pdgct="";
		var type_dxt="";
		var type_ddxt="";
		var type_bdx="";
	  	for(var i=0;i<eles.length;i++){
	 	 	//判断题
			if(eles[i].type == 'radio'&& eles[i].name == 'typeOne'+quesAnswer1){
				radioCount1++;
				if(eles[i].checked){
					radioCount1 = 2;
					type_pdt = "";
				}else{
					type_pdt = "第"+quesAnswer1+"题";
				}
				if(radioCount1>=2)  //判断判断题单选按钮是否超过2个，超过quesAnswer1++;到下一组
				{
					strParm1 = strParm1+" "+type_pdt;
					type_pdt = "";
					quesAnswer1++;
					num1++;
					radioCount1 = 0;
				}
			}
			//判断改错题
			if(eles[i].type == 'radio'&& eles[i].name == 'typeTwo'+quesAnswer2){
				radioCount2++;
				if(eles[i].checked){
					radioCount2 = 2;
					type_pdgct = "";
				}else{
					type_pdgct = "第"+quesAnswer2+"题";
				}
				if(radioCount2>=2)  //判断判断题单选按钮是否超过2个，超过quesAnswer2++;到下一组
				{
					strParm2 = strParm2+" "+type_pdgct;
					type_pdgct = "";
					quesAnswer2++;
					num2++;
					radioCount2 = 0;
				}
			}
			//单选题
			if(eles[i].type == 'radio' && eles[i].name == 'typeThree'+quesAnswer3){
				radioCount3++;
				if(eles[i].checked){
					radioCount3 = radioCount(quesAnswer3);
					type_dxt = "";
				}else{
					type_dxt = "第"+quesAnswer3+"题";
				}

				if(radioCount3 == radioCount(quesAnswer3))  //判断单选题单选按钮是否超过指定个数，超过radioflag++;到下一组
				{
					strParm3 = strParm3+" "+type_dxt;
					type_dxt = "";
					quesAnswer3++;
					num3++;
					radioCount3 = 0;
				}
			}
			//多选题
			if(eles[i].type == 'checkbox'&& eles[i].name == 'typeFour'+quesAnswer4){
				checkbox1Count++;
				if(eles[i].checked){
					checkbox1Count = checkbox11Count(quesAnswer4);
					type_ddxt = "";
				}else{
					type_ddxt = "第"+quesAnswer4+"题";
				}
				if(checkbox1Count == checkbox11Count(quesAnswer4))  //判断多选题单选按钮是否超过指定个数，超过checkbox1flag++;到下一组
				{
					strParm4 = strParm4+" "+type_ddxt;
					type_ddxt = "";
					quesAnswer4++;
					checkbox1Count = 0;
				}
			}
			//不定项
			if(eles[i].type == 'checkbox'&& eles[i].name == 'typeFive'+quesAnswer5){
				checkbox2Count++;
				if(eles[i].checked){
					checkbox2Count = checkbox22Count(quesAnswer5);
					type_bdx = "";
				}else{
					type_bdx = "第"+quesAnswer5+"题";
				}
				if(checkbox2Count == checkbox22Count(quesAnswer5))  //判断不定项选题单选按钮是否超过指定个数，超过checkbox2flag++;到下一组
				{
					strParm5 = strParm5+" "+type_bdx;
					type_bdx = "";
					quesAnswer5++;
					num5++;
					checkbox2Count = 0;
				}
			}
			//填空题
			if(eles[i].type == 'textarea'&& eles[i].name == 'typeSix'+quesAnswer6){
				var tktValue="";
				var tkt = document.getElementsByName('tkt'+quesAnswer6);
				for(var j=0;j<tkt.length;j++)
				{
					if(tkt[j].value!=""){
						tktValue = "";
					}else{
						tktValue = "第"+quesAnswer6+"题";
						break;
					}
				}
				strParm6 = strParm6+" "+tktValue;
				quesAnswer6++;
				num6++;
			}
			//问答题
			if(eles[i].type == 'textarea'&& eles[i].name == 'typeSeven'+quesAnswer7){
				var type_wdt="";
				if(eles[i].value!=""){
					if(eles[i].value.length>2000)cnt_message += "问答题,第"+quesAnswer7+"题答案过长(不能超过2000字);";
					type_wdt ="" ;
				}else{
					type_wdt = "第"+quesAnswer7+"题";
				}
				strParm7 = strParm7+" "+type_wdt;
				quesAnswer7++;
				num7++;
			}
			//案例分析题
			if(eles[i].type == 'textarea'&& eles[i].name == 'typeEight'+quesAnswer8){
				var type_alfx="";
				if(document.getElementById("examJoinForm").elements[i].value!=""){
					if(eles[i].value.length>2000)cnt_message += "案例分析题,第"+quesAnswer8+"题答案过长(不能超过2000字);";
					type_alfx ="" ;
				}else{
					type_alfx = "第"+quesAnswer8+"题";
				}
				strParm8 = strParm8+" "+type_alfx;
				quesAnswer8++;
				num8++;
			}
		}

		var alertValue="";
		var tempValue1=strParm1.replaceAll(' ','');
		var tempValue2=strParm2.replaceAll(' ','');
		var tempValue3=strParm3.replaceAll(' ','');
		var tempValue4=strParm4.replaceAll(' ','');
		var tempValue5=strParm5.replaceAll(' ','');
		var tempValue6=strParm6.replaceAll(' ','');
		var tempValue7=strParm7.replaceAll(' ','');
		var tempValue8=strParm8.replaceAll(' ','');

		if(tempValue1!="判断题"){
			alertValue+=strParm1+"\n\n";
		}
		if(tempValue2!="判断改错题"){
			alertValue+=strParm2+"\n\n";
		}
		if(tempValue3!="单选题"){
			alertValue+=strParm3+"\n\n";
		}
		if(tempValue4!="多选题"){
			alertValue+=strParm4+"\n\n";
		}
		if(tempValue5!="不定项选择题"){
			alertValue+=strParm5+"\n\n";
		}
		if(tempValue6!="填空题"){
			alertValue+=strParm6+"\n\n";
		}
		if(tempValue7!="问答题"){
			alertValue+=strParm7+"\n\n";
		}
		if(tempValue8!="案例分析题"){
			alertValue+=strParm8+"\n\n";
		}

		return alertValue;
	}

	//统一调用窗口打开
	function openwindow(id) {
	   document.getElementById("pkAutoIdQuestion").value= id;
	   var url = "<%=path%>/exampaperAction.do?method=showVoice&pkAutoIdQuestion="+id;//转向网页的地址;
	   var name = "视频文件";                            //网页名称，可为空;
	   var iWidth = "700";                          //弹出窗口的宽度;
	   var iHeight = "700";                        //弹出窗口的高度;
	   var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
	   var iLeft = (window.screen.availWidth-10-iWidth)/2;           //获得窗口的水平位置;
	   window.open(url,name,'height=600,width=800,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');
	}
	var isAutoSumbit = true;
	window.onbeforeunload = function(){
		if(isAutoSumbit){
			window.event.returnValue = "(关闭考试窗口后，本次考试将自动提交)";//"(关闭考试窗口后，本次考试将自动提交)";
		}
	}
	
	window.onunload = function(){
		if(isAutoSumbit){
			submitExam(2);
			myAjaxSubmitExamResult('<%=path%>/controller/examJoin/saveUserExamResultByAjax.do',function(data){}); 
			isAutoSumbit = false;
			var isIntoPageByMain=$("#isIntoPageByMain").val();
					if(isIntoPageByMain=="0"){ //判断是否是从待考列表进入该页面   0标识是
					   window.parent.exam_search();
					}else if(isIntoPageByMain=="1"){ //判断 1是从首页进入
					   window.parent.getExam();
					}
			frameElement.dialog.close();
		}
	}

	function submitConfirm() {
		var alertValue = getCheckResult();
		if(cnt_message!=""){
	 		$.ligerDialog.warn(cnt_message);
	 		cnt_message = "";
	 		return;
	 	}
		if(alertValue!=""){
			$.ligerDialog.confirm(alertValue+"\n 以上考题没作答，确定提交试卷？", function(yes) {
	          if (yes) {
	             submitExamByConfi();
	          }
			});
		}else{
			$.ligerDialog.confirm('确定提交试卷吗？', function(yes) {
	          if (yes) {
	            submitExamByConfi();
	          }
			});
			
			
			
		}
		 
	}
	
	
	function submitExamByConfi(){
	    global_isHandIn=true;
		submitExam();
		myAjaxSubmitExamResult('<%=path%>/controller/examJoin/saveUserExamResultByAjax.do',function(data){
			if(data==undefined || data==""){
				$.ligerDialog.error("保存试卷失败，请检查网络");
			}else{
				if(data["isScs"] == "1"){
				   $.ligerDialog.success('交卷成功!','提示',function(yes){
					    var isIntoPageByMain=$("#isIntoPageByMain").val();
						if(isIntoPageByMain=="0"){ //判断是否是从待考列表进入该页面   0标识是
						   window.parent.exam_search();
						}else if(isIntoPageByMain=="1"){ //判断 1是从首页进入
						   window.parent.getExam();
						}
						frameElement.dialog.close();
			       });
					
				}else{
					$.ligerDialog.warn(data["message"]);
				}
			}
			isAutoSumbit = false;
		});
	}
</script>
<body onload="startExamMinute();" style="overflow-x: hidden;">
	<iframe name="iframe_sumbit" style="display:none"></iframe>
	<form name="examJoinForm" method="post" id="examJoinForm" action="<%=path%>/examJoinAction.do?method=queryExampapers">
		<input type="hidden" name="quesAnswer" id="quesAnswer" />
		<input type="hidden" name="quesAnswerTwo" id="quesAnswerTwo" />
		<input type="hidden" name="quesAnswerFourAndFive" id="quesAnswerFourAndFive" />
		<input type="hidden" name="examExampaperExamineId" id="examExampaperExamineId" value="<%=examExampaperExamineId%>">
		<input type="hidden" name="pkAutoIdQuestion" id="pkAutoIdQuestion" />
		<input type="hidden" name="comitFlag" id="comitFlag" />
		<input type="hidden" name="isIntoPageByMain" id="isIntoPageByMain" value="${exam.isIntoPageByMain}"/>
		
		<div id="timing">
			<div class="title">考试剩余时间</div>
			<div id="minute"></div>
			<div id="message"></div>
		</div>
		<div class="exampaper">
			<table width="100%" border="0" cellspacing="0" cellpadding="4" bgcolor="f8f8f8">
				<tr align="center">
					<td colspan="3">
						<div class="title">
							${exam.examPaperName}
						</div>
						<div class="title2">
							考试时长（<%=examTimeLen %>分钟）
						</div>
					</td>
				</tr>
			</table>
			<div id="type1" style="display: none">
				<table class="qustype" border="0" cellspacing="0" cellpadding="0"
					id="quesType1">
					<tr>
						<td class="two">
							<b><label id="labelType1"></label>、判断题</b>
						</td>
					</tr>
				</table>
			</div>
			<div id="type2" style="display: none">
				<table class="qustype" border="0" cellspacing="0" cellpadding="0"
					id="quesType2">
					<tr>
						<td class="two">
							<b><label id="labelType2"></label>、判断改错题<font style="color: red; font-weight: bold;">【当判断为错误时，请添加正确答案】</font>
							</b>
						</td>
					</tr>
				</table>
			</div>
			<div id="type3" style="display: none">
				<table class="qustype" border="0" cellspacing="0" cellpadding="0"
					id="quesType3">
					<tr>
						<td class="two">
							<b><label id="labelType3"></label>、单选题</b>
						</td>
					</tr>
				</table>
			</div>
			<div id="type4" style="display: none">
				<table class="qustype" border="0" cellspacing="0" cellpadding="0"
					id="quesType4">
					<tr>
						<td class="two">
							<b><label id="labelType4"></label>、多选题</b>
						</td>
					</tr>
				</table>
			</div>
			<div id="type5" style="display: none">
				<table class="qustype" border="0" cellspacing="0" cellpadding="0"
					id="quesType5">
					<tr>
						<td class="two">
							<b><label id="labelType5"></label>、不定项选择</b>
						</td>
					</tr>
				</table>
			</div>
			<div id="type6" style="display: none">
				<table class="qustype" border="0" cellspacing="0" cellpadding="0"
					id="quesType6">
					<tr>
						<td class="two">
							<b><label id="labelType6"></label>、填空题<font style="color: red; font-weight: bold;">【_______为填空部分】</font>
							</b>
						</td>
					</tr>
				</table>
			</div>
			<div id="type7" style="display: none">
				<table class="qustype" border="0" cellspacing="0" cellpadding="0"
					id="quesType7">
					<tr>
						<td class="two">
							<b><label id="labelType7"></label>、问答题</b>
						</td>
					</tr>
				</table>
			</div>
			<div id="type8" style="display: none">
				<table class="qustype" border="0" cellspacing="0" cellpadding="0"
					id="quesType8">
					<tr>
						<td class="two">
							<b><label id="labelType8"></label>、案例分析题</b>
						</td>
					</tr>
				</table>
			</div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr align="center" height="60">
					<td>
						<a><img src="<%=path %>/img/utils/btn_jianchashijuan_out.gif" border="0"
							onclick="checkExampaper();" style="cursor: pointer;"
							onmouseover="this.src='<%=path %>/img/utils/btn_jianchashijuan_over.gif';"
							onmouseout="this.src='<%=path %>/img/utils/btn_jianchashijuan_out.gif';" alt="检查试卷" />
						</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a><img src="<%=path %>/img/utils/btn_saveshijuan_out.gif" border="0"
							onclick="tempSaveExamResult();" style="cursor: pointer;"
							onmouseover="this.src='<%=path %>/img/utils/btn_saveshijuan_over.gif';"
							onmouseout="this.src='<%=path %>/img/utils/btn_saveshijuan_out.gif';" alt="保存答案" />
						</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a><img src="<%=path %>/img/utils/btn_jiaojuan_out.gif" border="0"
							onclick="submitConfirm();" style="cursor: pointer;"
							onmouseover="this.src='<%=path %>/img/utils/btn_jiaojuan_over.gif';"
							onmouseout="this.src='<%=path %>/img/utils/btn_jiaojuan_out.gif';" alt="交卷" />
						</a>
					</td>
				</tr>
			</table>
		</div>
	</form>
   <%
   Object obj = request.getAttribute("list");
   List list = new ArrayList();
   if(obj!=null){
	   list = (List)obj;
   }
   //try{
		for(int i = 0; i<list.size(); i++){
			ExamJoinVO examJoinVO = (ExamJoinVO) list.get(i);
			//String quesCnt = java.net.URLDecoder.decode(examJoinVO.getQuesContent(),"UTF-8");
			%>
			<span id="questionsContent<%=i%>" style="display: none"><%=examJoinVO.getQuesContent()%></span>
			<span id="questionsPick<%=i%>" style="display: none"><%=examJoinVO.getQuesPick()%></span>
			<script language="javascript">
				addQuestions('<%=examJoinVO.getPkAutoId()%>',
							 '<%=examJoinVO.getQuesType()%>',
							 '<%=String.valueOf(examJoinVO.getQuesScore())%>',
							 '<%=examJoinVO.getResultsAnswer()%>',
							 '<%=examJoinVO.getRltAnswer()%>',
							 '<%=examJoinVO.getRltCorrectAnswer()%>'
							 );
			</script>
	  <%}
%>

</body>
</html>
	
<script language="javascript">
	var arr_num = ["一","二","三","四","五","六","七","八"];
	var myIndex = 0;
	for(var i=1;i<9;i++){
	    if(document.getElementById("type"+i)!=undefined)
		if(document.getElementById("type"+i).style.display=="block"){
			document.getElementById("labelType"+i).innerText = arr_num[myIndex];
			myIndex = myIndex+1;
		}
	}
</script>
