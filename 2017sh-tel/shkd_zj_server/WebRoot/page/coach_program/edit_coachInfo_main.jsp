<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%> 
<%@ taglib uri="/WEB-INF/shiro.tld" prefix="shiro"%> 
<%
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>客调项目管理系统</title>
	<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/js/jquery-1.9.1.js" type="text/javascript"></script> 
	<script language="javascript" type="text/javascript"
			src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
		<style type="text/css">
html,body {
	font-size: 12px;
	width:100%;
	height:100%;
}

input[type='button'] {
	border: #d3d3d3 1px solid;
	width: 80px;
	height: 25px;
	cursor: pointer;
}
.table_tr tr{height:40px}

.td1 {
	text-align: center;
}

.td2 {
	text-align: left;
}
</style>
		<script type="text/javascript"> 
		$(function(){
		  var date=new Date;
		  var month=date.getMonth();
		  $("#mon").html(month);
		   $("#username").on("click",function(){
			   var tcc_left = $(this).position().left;
			   var tcc_top =  $(this).position().top;
			   $(".tcc").show().css({
			        "left":tcc_left,
			        "top":tcc_top+20
			   });
		    
		   });
		   $("body").on("click",function(e){
				var b = $(".tcc")
				if($(e.target).closest("#username").length == 0 && b.css("display") == "block"){
					b.hide();
				}
			});
		})
	 function tz(){
	 	 /* $.ligerDialog.open({
				height:400,
				width:600,
				modal :false,
				title : '角色信息分配',
				url:'operSinglePage.jsp', 
				showMax: false,
				showToggle: true,
				showMin: false,
				isResize: true,
				slide: false
			}); */
			
	
	 }
	 function f_xz(value,userid){
	document.getElementById("username").value = value;
	document.getElementById("userid").value = userid;
	 }
	 
	 function addmain(){
	 var username=$("#username").val();
	 var userid=$("#userid").val();
	 var starttime=$("#starttime").val();
	 var endtime=$("#endtime").val();
	 var coachproject=""; 
	 var aa = document.getElementsByName("coachproject");
                for (var i = 0; i < aa.length; i++) {
                    if (aa[i].checked) {
                        coachproject += aa[i].value+",";
                    }
                }
              
	 
	 var improve=$("#improve").val();
	 
	 var specificationlanguage=$("#specificationlanguage").val();
	 var politetoneoofvoice=$("#politetoneoofvoice").val();
	 var abilitytocommunicate=$("#abilitytocommunicate").val();
	 var objectionhandling=$("#objectionhandling").val();
	 var flowstandard=$("#flowstandard").val();
	 var deadlyerror=$("#deadlyerror").val();
	 var marketingskills=$("#marketingskills").val();

	 if(username==""||username==null){
	 $.ligerDialog.warn('受理员工号不能为空');
	 return false;
	 }
	 if(starttime==""||starttime==null){
	 $.ligerDialog.warn('辅导开始时间不能为空');
	 return false;
	 }
	 if(endtime==""||endtime==null){
	 $.ligerDialog.warn('辅导结束时间不能为空');
	 return false;
	 }
	 if(coachproject==""||coachproject==null){
	 $.ligerDialog.warn('请选择辅导项目');
	 return false;
	 }
	 if(improve==""||improve==null){
	 $.ligerDialog.warn('改进措施不能为为空');
	 return false;
	 }
	 if(specificationlanguage==""||specificationlanguage==null){
	 $.ligerDialog.warn('规范用语不能为空');
	 return false;
	 }
	 if(politetoneoofvoice==""||politetoneoofvoice==null){
	 $.ligerDialog.warn('礼貌语调不能为空');
	 return false;
	 }
	 if(abilitytocommunicate==""||abilitytocommunicate==null){
	 $.ligerDialog.warn('沟通能力不能为空');
	 return false;
	 }
	 if(objectionhandling==""||objectionhandling==null){
	 $.ligerDialog.warn('异议处理不能为空');
	 return false;
	 }
	 if(flowstandard==""||flowstandard==null){
	 $.ligerDialog.warn('业务流程规范不能为空');
	 return false;
	 }
	 if(deadlyerror==""||deadlyerror==null){
	 $.ligerDialog.warn('致命差错率不能为空');
	 return false;
	 }
	 if(marketingskills==""||marketingskills==null){
	 $.ligerDialog.warn('营销技巧不能为空');
	 return false;
	 }
	$.ligerDialog.confirm('您确认新增吗？', function (yes) { 
				if(yes){ 
	 $.ajax({
	 		url:"<%=path%>/controller/coach/addCoachMonth.do",
	 		type:"post",
			data:{
					username:username,
					userid:userid,
					starttime:starttime,
					endtime:endtime,
					coachproject:coachproject,
					improve:improve,
					specificationlanguage:specificationlanguage,
					politetoneoofvoice:politetoneoofvoice,
					abilitytocommunicate:abilitytocommunicate,
					objectionhandling:objectionhandling,
					flowstandard:flowstandard,
					deadlyerror:deadlyerror,
					marketingskills:marketingskills			
			}, 
			dataType:"json",		
			success:function(result){
				if(result.flag){
				$.ligerDialog.success('添加成功','提示',function(yes){
					location.href="<%=path%>/controller/coach/togetCoachMonth.do";
				});
				}else{
				$.ligerDialog.warn('添加失败!');
				}
			
			},
			error:function(error){
			$.ligerDialog.warn('系统异常，请稍后再试!');
			}
	 });
	 
	 }
	  });
	 }
	 
	</script>
	</head>
	<body>
	<div style="width:600px;height:400px;position: absolute;display:none;border:1px solid#b7b1b1;border-radius:5px;" id="tcc" class="tcc">
	      <iframe id="main_page_iframe" name="main_page_iframe" src="<%=path%>/page/coach_program/operSinglePage.jsp" 
	      width="100%" height="100%" frameborder="0" scrolling="no" 
	      style="overflow:hidden;border-radius: 5px;"></iframe>
	 </div> 
		<input type="hidden" id="eidtType"
			value="<%=request.getParameter("eidtType") %>" />
		<div align="center" style="">
			<form id="myForm" method="post">
				<input type="hidden" id="userid" name="userid" />
				<table cellpadding="5" cellpadding="5" width="100%" border="0" class="table_tr">
					<tr>
						<td class="td1">
							受理员对象：
						</td>
						<td class="td2">
							<input type="text"  id="username" name="username" class="obj"
								readonly="readonly" value="" />
							<!-- <input type="hidden" id="userid" name="userid" value="" /> -->
							<font color="#FF0000">&nbsp;*</font>
						</td>
						<td class="td1">
							辅导开始时间：
						</td>
						<td class="td2">
							<input class="Wdate date_text" type="text" id="starttime" name="starttime"
								onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endtime\');}',dateFmt:'yyyy-MM-dd'})" />
							<font color="#FF0000">&nbsp;*</font>
						</td>
						<td class="td1">
							辅导结束时间：
						</td>
						<td class="td2">
							<input class="Wdate date_text" type="text" id="endtime" name="endtime"
								onfocus="WdatePicker({minDate:'#F{$dp.$D(\'starttime\');}',dateFmt:'yyyy-MM-dd '})" />
							<font color="#FF0000">&nbsp;*</font>
						</td>
					</tr>
					<tr>
						<td class="td1">
							辅导项目：
						</td>
						
						<td class="td2" colspan="5">
						<c:forEach items="${coachteam}" var="coachteam">
							<input type="checkbox" name="coachproject" id="check_0${coachteam.teamvalue}" value="${coachteam.teamvalue}"/>
							<label for="check_0${coachteam.teamvalue}">
								&nbsp;${coachteam.teamname}
							</label>
							&nbsp;&nbsp;
							</c:forEach>
							<font color="#FF0000">&nbsp;*</font>
						</td>
					</tr>
					<tr>
						<td class="td1" valign="top">
							备注：
						</td>
						<td class="td2" colspan="5">
							<textarea style="width: 90%;height:100px;max-height:100px;max-width:90%" id="improve" name="improve" ></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<span id="mon"></span>月份服务指标
						</td>
					</tr>
					<tr>
						<td class="td1">
							规范用语
						</td>
						<td class="td2">
							<input type="text" id="specificationlanguage" name="specificationlanguage"/>
							<font color="#FF0000">&nbsp;*</font>
						</td>
						<td class="td1">
							礼貌语调
						</td>
						<td class="td2">
							<input type="text" id="politetoneoofvoice" name="politetoneoofvoice" />
							<font color="#FF0000">&nbsp;*</font>
						</td>
						<td class="td1">
							沟通能力
						</td>
						<td class="td2">
							<input type="text" id="abilitytocommunicate" name="abilitytocommunicate" />
							<font color="#FF0000">&nbsp;*</font>
						</td>
					</tr>
					<tr>
						<td class="td1">
							异议处理
						</td>
						<td class="td2">
							<input type="text" id="objectionhandling" name="objectionhandling" />
							<font color="#FF0000">&nbsp;*</font>
						</td>
						<td class="td1">
							业务流程规范
						</td>
						<td class="td2">
							<input type="text" id="flowstandard" name="flowstandard" />
							<font color="#FF0000">&nbsp;*</font>
						</td>
						<td class="td1">
							致命差错数/率
						</td>
						<td class="td2">
							<input type="text" id="deadlyerror" name="deadlyerror" />
							<font color="#FF0000">&nbsp;*</font>
						</td>
					</tr>
					<tr>
						<td class="td1">
							营销技巧
						</td>
						<td class="td2">
							<input type="text" id="marketingskills" name="marketingskills"/>
							<font color="#FF0000">&nbsp;*</font>
						</td>
						<td class="td1">
							&nbsp;
						</td>
						<td class="td2">
							&nbsp;
						</td>
						<td class="td1">
							&nbsp;
						</td>
						<td class="td2">
							&nbsp;
						</td>
						</tr>
						<tr>
							<td colspan="6" align="center"
								style="padding-top: 15px; padding-bottom: 15px;">
								<shiro:hasPermission name='coach:add'>
								<input type="button" id="addid" value="提交"  onclick="addmain()" /></shiro:hasPermission>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" value="返回" onclick="history.go(-1);" />
							</td>
						</tr>
				</table>
			</form>
		</div>
	</body>
</html>
