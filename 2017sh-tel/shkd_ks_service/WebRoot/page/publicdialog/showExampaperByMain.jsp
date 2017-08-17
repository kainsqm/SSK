<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt"  uri="/WEB-INF/fmt-rt.tld"%> 
<%@ taglib prefix="c"  uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<%	String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>查看考卷</title>
	<link href="<%=path%>/css/utils/css.css" rel="stylesheet" type="text/css"/>
	<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<script src="<%=path %>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/ligerUI/js/plugins/ligerTab.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
	<script src="<%=path %>/js/ajaxSession.js" type="text/javascript"></script>
	<script language="javascript" src="<%=path%>/js/jquery.nicescroll.min.js"></script>
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

.one {
	text-align: left; //居中
	font-size: 200px; //字体大小为12像素
	line-height: 20px;
	height: 20px;
	font-weight: bold; //加粗
	width: 200px;
	margin: auto;
}

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

.exampaper {
	border: 3px double #8ab8da;
	width: 100%;
}

.exampaper .titletable{
			width: 100%;
			border:0;
			background-color: #f8f8f8;
			margin-bottom:4px;
			margin-top:4px;
		}

.exampaper .title {
			text-align: left;
			height: 20px;
			font-family: 幼圆, 宋体;
			font-weight: bold;
			font-size: 11pt;
			margin: auto;
			background-color: #f8f8f8;
			padding: 3px;
			padding-left: 13px;
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

.right{
	float: right;
	padding-right: 20px;
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
}	</style>
	<script type="text/javascript"> 
	
	    //初始化滚动条
		$("body").niceScroll({
					cursorcolor:"#3B98C8", //颜色
					horizrailenabled: false,//水平滚动条
					railoffset:true,
					cursorwidth:"10px" //滚动条宽带
		});

		function closeBtn(){
			frameElement.dialog.close();
		}
	</script>

</head>

<body>
	<form name="manualScore" method="post" id="exampaperForm">
		<input type="text" id="examineId" value="${examExampaperExamineId}" style="display: none"/>
		<input type="text" id="userId" value="${userId}" style="display: none"/>
		<div class="exampaper">
			<table class="titletable">
				<tr> <td class="title">试卷名称：<blue>${exampaperName} </blue></td></tr>
				<tr> <td class="title">试卷总分：<blue>${scoreTotal} </blue></td></tr>
				<tr> <td class="title">考生工号：<blue>${workId} </blue></td></tr>
				<tr> <td class="title">考生得分：<blue>${exampaperScore} </blue></td></tr>
				<tr> <td class="title">考试状态：<blue>${examineeStatus} </blue></td></tr>
			</table>
			<div id="type1">
				<c:if test="${typeOneScoreTotal ne '0'}">
					<table class="qustype" border="0" cellspacing="0" cellpadding="0" id="quesType1">
						<tr>
							<td class="two" colspan="2">模块：判断题</td>
						</tr>
						<% int i=0; %>
						<c:forEach items="${artificialList}" var="tlist">
							<c:if test="${tlist.quesType == '1'}">
								<% i++; %>
								<tr>
									<td class="qust">
										&nbsp;&nbsp;<%=i%>.&nbsp;&nbsp;${tlist.quesContent}（${tlist.quesScore}分）
									</td>
									<td class="qust"><div class="right">${tlist.resultsScore}分</div></td>
								</tr>
								<tr>
									<td class="answ" colspan="2"><span>考生答案:${tlist.resultsAnswer}</span></td>
								</tr>
								<tr>  
									<td class="answ" colspan="2"><span>参考答案:${tlist.quesAnswer}</span></td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</c:if>
			</div>
			<div id="type2">
			<c:if test="${typeTwoScoreTotal ne '0'}">
					<table class="qustype" border="0" cellspacing="0" cellpadding="0" id="quesType2">
						<tr>
							<td class="two" colspan="2">模块：判断改错题<font style="color: red; font-weight: bold;">【当判断为错误时，请添加正确答案】</font></td>
						</tr>
						<% int i=0; %>
						<c:forEach items="${artificialList}" var="tlist">
							<c:if test="${tlist.quesType == '2'}">
								<% i++; %>
								<tr>
									<td class="qust">
										&nbsp;&nbsp;<%=i%>.&nbsp;&nbsp;${tlist.quesContent}（${tlist.quesScore}分）
									</td>
									<td class="qust"><div class="right">${tlist.resultsScore}分</div></td>
								</tr>
								<tr>
									<td class="answ" colspan="2"><span>考生答案:${tlist.resultsAnswer}</span></td>
								</tr>
								<tr>
									<td class="answ" colspan="2"><span>改错答案:${tlist.resultsCorrectAnswer}</span></td>
								</tr>
								<tr>  
									<td class="answ" colspan="2"><span>参考答案:${tlist.quesAnswer}</span></td>
								</tr>
								<tr>  
									<td class="answ" colspan="2"><span>改错参考答案:${tlist.quesCorrectAnswer}</span></td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</c:if>
			</div>
			<div id="type3">
				<c:if test="${typeThreeScoreTotal ne '0'}">
					<table class="qustype" border="0" cellspacing="0" cellpadding="0" id="quesType3">
						<tr>
							<td class="two" colspan="2">模块：单选题</td>
						</tr>
						<% int i=0; %>
						<c:forEach items="${artificialList}" var="tlist">
							<c:if test="${tlist.quesType == '3'}">
								<% i++; %>
								<tr>
									<td class="qust">
										&nbsp;&nbsp;<%=i%>.&nbsp;&nbsp;${tlist.quesContent}（${tlist.quesScore}分）
									</td>
									<td class="qust"><div class="right">${tlist.resultsScore}分</div></td>
								</tr>
								<tr>
									<td class="answ" colspan="2"><span>考生答案:${tlist.resultsAnswer}</span></td>
								</tr>
								<tr>  
									<td class="answ" colspan="2"><span>参考答案:${tlist.quesAnswer}</span></td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</c:if>
			</div>
			<div id="type4">
				<c:if test="${typeFourScoreTotal ne '0'}">
					<table class="qustype" border="0" cellspacing="0" cellpadding="0" id="quesType4">
						<tr>
							<td class="two" colspan="2">模块：多选题</td>
						</tr>
						<% int i=0; %>
						<c:forEach items="${artificialList}" var="tlist">
							<c:if test="${tlist.quesType == '4'}">
								<% i++; %>
								<tr>
									<td class="qust">
										&nbsp;&nbsp;<%=i%>.&nbsp;&nbsp;${tlist.quesContent}（${tlist.quesScore}分）
									</td>
									<td class="qust"><div class="right">${tlist.resultsScore}分</div></td>
								</tr>
								<tr>
									<td class="answ" colspan="2"><span>考生答案:${tlist.resultsAnswer}</span></td>
								</tr>
								<tr>  
									<td class="answ" colspan="2"><span>参考答案:${tlist.quesAnswer}</span></td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</c:if>
			</div>
			<div id="type5">
				<c:if test="${typeFiveScoreTotal ne '0'}">
					<table class="qustype" border="0" cellspacing="0" cellpadding="0" id="quesType5">
						<tr>
							<td class="two" colspan="2">模块：不定项选择</td>
						</tr>
						<% int i=0; %>
						<c:forEach items="${artificialList}" var="tlist">
							<c:if test="${tlist.quesType == '5'}">
								<% i++; %>
								<tr>
									<td class="qust">
										&nbsp;&nbsp;<%=i%>.&nbsp;&nbsp;${tlist.quesContent}（${tlist.quesScore}分）
									</td>
									<td class="qust"><div class="right">${tlist.resultsScore}分</div></td>
								</tr>
								<tr>
									<td class="answ" colspan="2"><span>考生答案:${tlist.resultsAnswer}</span></td>
								</tr>
								<tr>  
									<td class="answ" colspan="2"><span>参考答案:${tlist.quesAnswer}</span></td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</c:if>
			</div>
			<div id="type6">
				<c:if test="${typeSixScoreTotal ne '0'}">
					<table class="qustype" border="0" cellspacing="0" cellpadding="0" id="quesType6">
						<tr>
							<td class="two" colspan="2">模块：填空题<font style="color: red; font-weight: bold;">【____为填空部分】</font></td>
						</tr>
						<% int i=0; %>
						<c:forEach items="${artificialList}" var="tlist">
							<c:if test="${tlist.quesType == '6'}">
								<% i++; %>
								<tr>
									<td class="qust">
										&nbsp;&nbsp;<%=i%>.&nbsp;&nbsp;${tlist.quesContent}（${tlist.quesScore}分）
									</td>
									<td class="qust"><div class="right">${tlist.resultsScore}分</div></td>
								</tr>
								<tr>
									<td class="answ" colspan="2"><span>考生答案:${tlist.resultsAnswer}</span></td>
								</tr>
								<tr>  
									<td class="answ" colspan="2"><span>参考答案:${tlist.quesAnswer}</span></td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</c:if>
			</div>
			<div id="type7">
				<c:if test="${typeSevenScoreTotal ne '0'}">
					<table class="qustype" border="0" cellspacing="0" cellpadding="0" id="quesType7">
						<tr>
							<td class="two" colspan="2">模块：问答题</td>
						</tr>
						<% int i=0; %>
						<c:forEach items="${artificialList}" var="tlist">
							<c:if test="${tlist.quesType == '7'}">
								<% i++; %>
								<tr>
									<td class="qust">
										&nbsp;&nbsp;<%=i%>.&nbsp;&nbsp;${tlist.quesContent}（${tlist.quesScore}分）
									</td>
									<td class="qust"><div class="right">${tlist.resultsScore}分</div></td>
								</tr>
								<tr>
									<td class="answ" colspan="2"><span>考生答案:${tlist.resultsAnswer}</span></td>
								</tr>
								<tr>  
									<td class="answ" colspan="2"><span>参考答案:${tlist.quesAnswer}</span></td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</c:if>
			</div>
			<div id="type8">
				<c:if test="${typeEightScoreTotal ne '0'}">
					<table class="qustype" border="0" cellspacing="0" cellpadding="0" id="quesType8">
						<tr>
							<td class="two" colspan="2">模块：案例分析题</td>
						</tr>
						<% int i=0; %>
						<c:forEach items="${artificialList}" var="tlist">
							<c:if test="${tlist.quesType == '8'}">
								<% i++; %>
								<tr>
									<td class="qust">
										&nbsp;&nbsp;<%=i%>.&nbsp;&nbsp;${tlist.quesContent}（${tlist.quesScore}分）
									</td>
									<td class="qust"><div class="right">${tlist.resultsScore}分</div></td>
								</tr>
								<tr>
									<td class="answ" colspan="2"><span>考生答案:${tlist.resultsAnswer}</span></td>
								</tr>
								<tr>  
									<td class="answ" colspan="2"><span>参考答案:${tlist.quesAnswer}</span></td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</c:if>
			</div>
			<div> 
				<table class="search_table" cellpadding="3" cellspacing="3" width="100%" >
					<tr style="padding-top: 15px;height: 80px">
						<td colspan="8" align="center">
							<input type="button" value="关闭" onclick="closeBtn()" class="submit"/>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>