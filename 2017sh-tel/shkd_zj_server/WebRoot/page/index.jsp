
<%@ page language="java" contentType="text/html;charset=UTF-8"%>  
<%
  String path = request.getContextPath();
%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>客调质量检查系统</title>
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/xcConfirm.css"/>
	<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script> 
	<script src="<%=path%>/js/xcConfirm.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=path%>/js/div-alert-1.0.0.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	
	<style type="text/css">
		body{ margin:0px; padding:1px; padding-left:2px; padding-right:2px; min-height:550px; min-width:1000px; overflow:auto; background-image:url(<%=path%>/img/login/bgmain.png);}
		/* 添加头部样式 */
		#loyout_div_top{ width:100%; height:40px; line-height:40px; background-image:url(<%=path%>/img/login/toppic.jpg);/* */ border:#eef0f1 0px solid;}
		#loyout_div_top #syste_matic_name{ font-family: "宋体"; font-size:20px; color:#FFFFFF; font-weight: bold; margin-left:50px;}
		#loyout_div_top #top_div span font{margin-left:20px;}
		#loyout_div_top #label_a label{ cursor:pointer; margin-left:5px;}
		
		/* 添加中间样式 */
		#loyout_div_center{
			border:#eef0f1 0px solid; margin-top:10px;
		}
		
		/* 添加底部样式 */
		#loyout_div_bottom{
			border-top:#eef0f1 1px solid; height:30px; width:100%; 
			text-align:center; vertical-align:middle; line-height:30px; 
			font-size:13px; color:#999999; margin-top:5px; 
		}
		#loyout_div_bottom label{ cursor:pointer;}
		
		div#eidt_pwd_div{position:absolute; border:#CCFF00 1px solid; width:300px; height:200px;border-radius:5px; display:none;}
		div#eidt_pwd_div .eidt_pwd_div_in,div#eidt_pwd_div .eidt_pwd_div_out{position:absolute;width:0px; height:0px;}
		div#eidt_pwd_div .eidt_pwd_div_in{border:15px solid transparent;border-bottom-color:#fff; top: -30px; left:261px;}
		div#eidt_pwd_div .eidt_pwd_div_out{border:16px solid transparent;border-bottom-color:#CCFF00; top:-32px; left:260px;}
	</style>
	<script type="text/javascript">
		$(function(){  
    		changeCenterHeight();
		});  
		window.onresize=function(){
			changeCenterHeight();
		}
		function changeCenterHeight(){
			var totalHeight=($(window).height() > document.body.scrollHeight) ? $(window).height() :  document.body.scrollHeight;
			$("#loyout_div_center").css("height",(totalHeight - $("#loyout_div_top").height() - $("#loyout_div_bottom").height() - 21)+"px");
			
			//document.getElementById("main_iframe").width=$("#loyout_div_center").css("width");
			document.getElementById("main_iframe").height=$("#loyout_div_center").css("height");
		}
		/***************
		 * 系统退出
		 ***************/
		function login_out(){
			
			$.ligerDialog.confirm('退出提示', function (yes) { if(yes){
				$.ajax({
					url:'<%=path%>/controller/user/loginOut.do',
					type:'get',
					error:function(request){
						$.ligerDialog.error('系统异常，请稍后再试！');
					},
					success:function(data){
							window.location.href='<%=path%>/page/login.jsp';
						}
				});
				
			}else{
				$.ligerDialog.close();
				
			} });
			
			<%-- var txt=  "确认要退出吗？";
			var option = {
				title:"退出提示",
				width: 350,
				height: 250,
				onOk:function(){
					$.ajax({
						url:'<%=path%>/controller/user/loginOut.do',
						type:'get',
						error:function(request){
							$.ligerDialog.error('系统异常，请稍后再试！');
						},
						success:function(data){
								window.location.href='<%=path%>/page/login.jsp';
							}
						
						
						
					});
					
				}
			} --%>
			//window.wxc.xcConfirm(txt, "confirm",option);
		}
		
		function show_editPwd_info(){
		titleInfo="个人密码修改";
			$.ligerDialog.open({
				height:240,
				width: 300,
				title : titleInfo,
				url:'<%=path%>/page/eidt_pwd.jsp', 
				showMax: false,
				showToggle: false,
				showMin: false,
				isResize: true,
				slide: false
			});
			
	
		}
	</script>
</head>
<body>
	<div id="loyout_div_top">
		<!--  -->
		<span id="syste_matic_name">客调质量检查系统</span>
		<span style="float:right; margin-right:40px; font-size:13px; color:#FFFFFF;">
			<font>欢迎你：<label>${userName}</label></font>
			<font>登录时间：<label>${date}</label></font>
			<font id="label_a"><label onclick="show_editPwd_info();">个人密码修改</label><label onclick="login_out();">退出系统</label></font>			
		</span>
		
	</div>
	<div id="loyout_div_center">
		<iframe id="main_iframe" name="main_iframe" width="100%" height="100%" frameborder="0" scrolling="no" src="<%=path%>/page/main_page.jsp"></iframe>
	</div>
	<div id="loyout_div_bottom">
		<span>
			中国电信 ©上海理想信息产业(集团)有限公司
		</span>
	</div>
</body>
</html>
