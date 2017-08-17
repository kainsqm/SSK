<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/shiro.tld" prefix="shiro"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String coachproject=request.getAttribute("coachproject").toString();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>客调项目管理系统</title>
	<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script> 
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
    <style type="text/css">
		body{ font-size:13px;}
		table tr td{ height:30px;}
		.td1{ text-align:right;}
		.td2{ text-align:left;}
		div.l-panel-topbar{ height:30px !important; }
	</style>
		<script type="text/javascript"> 
		$(function () {
            var maingrid_gd= $("#coach_dudao_list").ligerGrid({
                columns: [                
				{ display: '操作',name:"id", isSort: false, width : 120, render: function (rowdata, rowindex, value)
	                {
	                  var h = "";
	                    h += "<a href='javascript:void(0);' onclick='showAddItemsInfo(\""+value+"\",\"info\");'>查看</a> ";
						h += "<a href='javascript:void(0);' onclick='delCoachInfo(\""+value+"\");'>删除</a>";
	                    return h;
	                }
                },
				{ display: '辅导项目', name: 'coachproject', align: 'left', minWidth :130,isSort:false,render: function (rowdata, rowindex, value)
	                {
	                    var h = "<span class='span' title='"+value+"' style='overflow: hidden;text-overflow: hidden;display: block;text-overflow: ellipsis; white-space: nowrap;'>"+value+"<span>";
	                    return h;
	                }
				},
                { display: '辅导开始时间', name: 'starttime', align: 'left', minWidth :120,isSort:false},
				{ display: '辅导结束时间', name: 'endtime', align: 'left', minWidth :80,isSort:false},
				{ display: '周计划创建时间', name: 'createtime', align: 'left', minWidth :80,isSort:false},
				{ display: '周计划状态', name: 'state', align: 'left', minWidth :80,isSort:false,render : function(rowdata, rowindex, value) {
						var result="";
						switch(value){
						   case "1":
						    result="待受理员处理";
						   break;
						   case "2":
						    result="待受理员班长处理";
						   break;
						   case "3":
						    result="受理员班长已处理";
						   break;
						   default:
						     result="其他";
						   break;
						   
						}	
						return result;
						}
					}
                ], url:'<%=path%>/controller/coach/getDDCheckCoachInfo.do?coachmainId='+$("#coachmainId").val(),
                height:300, width:"auto",
                height:'90%',pageSize:10 ,rownumbers :true
            });
            $("#pageloading").hide(); 
             
          var date=new Date;
		  var month=date.getMonth();
		  $("#mon").html(month);
		  var coachproject="<%=coachproject%>";
	      var sumImprove=$("#sumImprove").val();
	      if(sumImprove==""){
	         $("#checkMain").css('display','none'); 
	      }else if(sumImprove=="0" || sumImprove=="1"){
	         $("#checkMain").val("辅导总结查看");
	      }
		  var coachpro= new Array(); 
		  if(coachproject!=null||coachproject!=""){
		   coachpro=coachproject.split(",");
		 
		  for ( var i = 0; i < coachpro.length; i++) {
		
			if(coachpro[i]=="1"){
			document.getElementById("check_01").checked="checked";
			}
			if(coachpro[i]=="2"){
			document.getElementById("check_02").checked="checked";
			}
			if(coachpro[i]=="3"){
			document.getElementById("check_03").checked="checked";
			}
			if(coachpro[i]=="4"){
			document.getElementById("check_04").checked="checked";
			}
			if(coachpro[i]=="5"){
			document.getElementById("check_05").checked="checked";
			}
			if(coachpro[i]=="6"){
			document.getElementById("check_06").checked="checked";
			}
			if(coachpro[i]=="7"){
			document.getElementById("check_07").checked="checked";
			}
			if(coachpro[i]=="8"){
			document.getElementById("check_08").checked="checked";
			}
		 }
		 
		
		 
		}  
            
        });
		function showAddItemsInfo(id,msg){
		if(msg!="info"){
		id="0";
		}
		   var coachmainId=$("#coachmainId").val();
			$.ligerDialog.open({
				height:450,
				width: 760,
				title : "辅导周计划",
				url:"<%=path%>/controller/coach/getcoachtimeByDudao.do?tz=zjx&coachmainId="+coachmainId+"&id="+id, 
				showMax: false,
				showToggle: true,
				showMin: false,
				isResize: true,
				slide: false
			});
		}
		
		function delCoachInfo(id){
		 $.ligerDialog.confirm('您确认要删除吗？', function (yes) { 
		 if(yes){
			$.ajax({
					url:"<%=path%>/controller/coach/deleteCoachInfo.do",
					data:{id:id},
					type:"post",
					dataType:"json",
					success:function(result){
					   if(result.blag){
						 $.ligerDialog.success('删除成功','提示',function(yes){
				               location.reload(true);	
				            });
			 			}else{
						     $.ligerDialog.warn('删除失败!');
						}
					},
				   error:function(error){
					    $.ligerDialog.warn('系统异常，请稍后再试!');
					}
			
			    });
			}
			});
		}
		
		
		/********************
		 * 打开辅导总结审核界面
		 *******************/
		function showZongJiePage(){
		   var coachmainId=$("#coachmainId").val();
			$.ligerDialog.open({
				height:420,
				width: 660,
				title : "辅导总结",
				url:"<%=path%>/controller/coach/getCoachMainCheck.do?coachmainId="+coachmainId,
				showMax: false,
				showToggle: true,
				showMin: false,
				isResize: true,
				slide: false
			});
		}
		
		function diszj(){
		    $("#checkMain").css('display','none'); 
		    $("#checkMain").width(200);
		}
		
	</script>
	</head>
	<body>
		<div align="center" style="height:100%; overflow:hidden; ">
			<input type="hidden" value="${coachmainId}" id="coachmainId" />
			<input type="hidden" value="${coac.coachproject}" id="coachproject" />
			<input type="hidden" value="${coac.sumImprove}" id="sumImprove" />
			<table cellpadding="5" cellpadding="5" width="100%"  border="0" style="">
				<tr>
					<td class="td1">
						受理员对象：
					</td>
					<td class="td2">
						<input type="text" value="${coac.username}" disabled="disabled" />
					</td>
					<td class="td1">
						辅导开始时间：
					</td>
					<td class="td2">
						<input value="${coac.starttime}" disabled="disabled"
							class="Wdate date_text" type="text"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					</td>
					<td class="td1">
						辅导结束时间：
					</td>
					<td class="td2">
						<input value="${coac.endtime}" disabled="disabled"
							class="Wdate date_text" type="text"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					</td>
				</tr>
				<tr>
					<td class="td1">
						辅导项目：
					</td>
					<td class="td2" colspan="5">
						<c:forEach items="${coachteam}" var="coachteam">
							<input type="checkbox" name="coachproject"
								id="check_0${coachteam.teamvalue}"
								value="${coachteam.teamvalue}" disabled="disabled" />
							<label for="check_0${coachteam.teamvalue}">
								&nbsp;${coachteam.teamname}
							</label>
							&nbsp;&nbsp;
							</c:forEach>
					</td>
				</tr>
				<tr>
					<td class="td1" valign="top">
						备注：
					</td>
					<td class="td2" colspan="5">
						<textarea style="width: 90%;" disabled="disabled">${coac.remaker}</textarea>
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
						<input type="text" value="${coacs.specificationlanguage}" disabled="disabled" />
					</td>
					<td class="td1">
						礼貌语调
					</td>
					<td class="td2">
						<input type="text" value="${coacs.politetoneoofvoice}" disabled="disabled" />
					</td>
					<td class="td1">
						沟通能力
					</td>
					<td class="td2">
						<input type="text" value="${coacs.abilitytocommunicate}" disabled="disabled" />
					</td>
				</tr>
				<tr>
					<td class="td1">
						异议处理
					</td>
					<td class="td2">
						<input type="text" value="${coacs.objectionhandling}" disabled="disabled" />
					</td>
					<td class="td1">
						业务流程规范
					</td>
					<td class="td2">
						<input type="text" value="${coacs.flowstandard}" disabled="disabled" />
					</td>
					<td class="td1">
						致命差错数/率
					</td>
					<td class="td2">
						<input type="text" value="${coacs.deadlyerror}" disabled="disabled" />
					</td>
				</tr>
				<tr>
					<td class="td1">
						营销技巧
					</td>
					<td class="td2">
						<input type="text" value="${coacs.marketingskills}" disabled="disabled" />
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
			</table>
			<div id="coach_dudao_list" style="margin-top: 10px;"></div>
			<div style="margin-top: 15px;height:40px;">
				<shiro:hasPermission name='dbcoach:zjcheck'><input type="button"  value="辅导总结审核" id="checkMain"
					onclick="showZongJiePage()"/></shiro:hasPermission>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="返回" onclick="history.go(-1);" />
			</div>
		</div>
	</body>
</html>
