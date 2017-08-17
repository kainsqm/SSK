<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt"  uri="/WEB-INF/fmt-rt.tld"%> 
<%@ taglib prefix="c"  uri="/WEB-INF/c.tld"%> 
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>手动评分</title>
	<link href="${ctx}/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
	<script src="${ctx}/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script src="${ctx}/js/ajaxSession.js" type="text/javascript"></script>
	<script src="${ctx}/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
	<script src="${ctx}/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
	<script src="${ctx}/lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
	<script src="${ctx}/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
	<script src="${ctx}/lib/ligerUI/js/plugins/ligerTab.js" type="text/javascript"></script>
	<script src="${ctx}/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="${ctx}/lib/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
	<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		.submit{ 
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
		    border-radius: 3px;
		    width: initial !important;
		    height: 25px !important;
    	}
		blue{color: blue;}
		.exampaper {
			border: 3px double #8ab8da;
			width: 100%;
			padding: 5px;
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
			color: #4f4f4f;
			background-color: #f8f8f8;
		}
		
		.exampaper .qustype .answ {
			padding-left: 32px;
		}
		
		.exampaper .two {
			color: #4f4f4f;
			font-size: 11pt;
			font-weight: bold;
			height: 28px;
			line-height: 28px;
			padding-left: 4px;
			background: url(/img/img/bg_title_exampaper.gif) repeat-x;
		}
		
		td .right{
			text-align: left;
			float:right;
			height: 24px;
			line-height: 24px;
			width:100px;
			display:inline;
			padding-right: 20px;
			padding-left: 10px;
		}
	</style>
	<script type="text/javascript"> 
		function checkScore(obj, quesScore){
			if(obj.value == "") {
				$.ligerDialog.warn("请给该题评分！");
				obj.value='';
				return;
			}else if(obj.value > quesScore) {
				$.ligerDialog.warn("所给分值不能超过最大分值！");
				obj.value='';
				return;
			}
		}
		
		
		function submitBtn(){
			var params = "";
			
			var pdgct = $("input[name='pdgct']");
			var tkt = $("input[name='tkt']");
			var wdt = $("input[name='wdt']");
			var alfxt = $("input[name='alfxt']");
			
			for(var i=0;i<pdgct.length;i++){
				var t = pdgct.eq(i);
				params += t.attr("id") +"_"+t.val()+";";
			}
			
			for(var i=0;i<tkt.length;i++){
				var t = tkt.eq(i);
				params += t.attr("id") +"_"+t.val()+";";
			}
			
			for(var i=0;i<wdt.length;i++){
				var t = wdt.eq(i);
				params += t.attr("id") +"_"+t.val()+";";
			}
			
			for(var i=0;i<alfxt.length;i++){
				var t = alfxt.eq(i);
				params += t.attr("id") +"_"+t.val()+";";
			}
			var url = "${ctx}/controller/examine/updateResultScore.do";
			 $.ajax({
				url: url,
			  	type:"post",
			  	dataType:"json",
			  	data:{"params":params.substring(0,params.length-1),"examineId":$("#examineId").val(),"userId":$("#userId").val()},
			  	success: function(resultData){
			  		$.ligerDialog.success('手动评分成功，试卷得分：'+resultData.totalScore+'分','提示',function(yes){	
			  			closeBtn();
	 				});
			  	},
				error:function(e){
	       			$.ligerDialog.error('系统异常，请稍候再试');
	       	    }
			 });
			
		}
		
		function closeBtn(){
			//location.href = "${ctx}/page/marking/testpaper_score.jsp";
			window.history.back(-1);
		}
	</script>

</head>

<body style="overflow:auto">
	<form name="manualScore" method="post" id="exampaperForm">
		<input type="text" id="examineId" value="${examExampaperExamineId}" style="display: none"/>
		<input type="text" id="userId" value="${userId}" style="display: none"/>
		<div class="exampaper">
			<table class="titletable">
				<tr> <td class="title">试卷名称：<blue>${exampaperName} </blue></td></tr>
				<tr> <td class="title">考生工号：<blue>${workId} </blue></td></tr>
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
									<td class="qust">
										<div class="right">
										<input type="text" class="num" size="2" style="width:40px;" id="${tlist.examResultId}" name="pdgct" value="${tlist.quesScore}" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" onblur="checkScore(this,${tlist.quesScore})"></input>
										&nbsp;分</div>
									</td>
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
									<td class="qust">
										<div class="right">
										<input type="text" size="2" class="num" id="${tlist.examResultId}" name="tkt" value="${tlist.quesScore}" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" onblur="checkScore(this,${tlist.quesScore})"></input>
										&nbsp;分</div>
									</td>
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
									<td class="qust">
										<div class="right">
										<input type="text" size="2" class="num" id="${tlist.examResultId}" name="wdt" value="${tlist.quesScore}" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" onblur="checkScore(this,${tlist.quesScore})"></input>
										&nbsp;分</div>
									</td>
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
									<td class="qust">
										<div class="right">
										<input type="text" class="num" size="2" style="width:40px;" id="${tlist.examResultId}" name="alfxt" value="${tlist.quesScore}" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" onblur="checkScore(this,${tlist.quesScore})"></input>
										&nbsp;分</div>
									</td>
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
							<shiro:hasPermission name="kswhgl:tj">
								<input type="button" value="提交" onclick="submitBtn()" class="submit"/>&nbsp;&nbsp;&nbsp;&nbsp;
							</shiro:hasPermission>
							<input type="button" value="关闭" onclick="closeBtn();" class="submit"/>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>