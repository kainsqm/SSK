<%@ page language="java" contentType="text/html;charset=UTF-8"%>  
<%
  String path = request.getContextPath();
%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>客调质检</title> 
  <link rel="stylesheet" type="text/css" href="<%=path%>/css/login.css"/>
  <script type="text/javascript" src="<%=path%>/js/jquery-1.9.1.js" ></script>
  <script type="text/javascript" src="<%=path%>/js/util.js" ></script>
  <style type="text/css">
  	.msg{
  		margin-left: 90px;color: red;font-size: 13px;
  	}
  	input{
  		padding-left: 5px;
  	}
  	html {
			height: 100%;
		}
		body {
			height: 100%;
			margin: 0;
			background: url("<%=path %>/img/bg11.png") center no-repeat;
			background-size: cover;
			-ms-behavior: url(css/backgroundsize.min.htc);
			behavior: url(css/backgroundsize.min.htc);
		}
		h1{
			text-align: center;
			position: absolute;
			font-family: "华文行楷";
			color: #F5F6F7;
            font-size: 16pt;
            top: 33%;
            left: 26%;
            width: 20%;
			}
		.login label{width: 90px;text-align: right;display: block;color: #FFFFFF;}
		.logo1{   
		  position: absolute;
		  bottom:92%;
          margin-left: 90px;
          width: 280px}
  </style>
  <script type="text/javascript">
		$(function(){
	  		$(".pwd1 input").focus(function(){
	  			$(".pwd").hide().siblings().show();
	  		})
	  	});
  	
	  	/* 光标获取事件 */
	  	function inputFocus(obj){
	  		
	  		if(obj.value=="请输入工号" || obj.value=="请输入验证码" || obj.value=="请输入密码"){
	  			
	  			obj.value="";
	  		}
	  		/* if(obj.type=="password"){
	  			document.getElementById("passWordMsg").innerHTML="&nbsp;";
	  		} */
	   	}
	   	function closePassWordMsg(){
			document.getElementById("passWD").focus();
		}
	   	
	  	/* 光标离开事件 */
	  	function inputBlur(obj){
	  		if(obj.name=="workId"){
	  			if(obj.value==""){obj.value="请输入工号";}
	  		}else if(obj.name=="passWD"){
	  			if(obj.value==""){obj.value="请输入密码";}
	  		}else if(obj.name=="authCode"){
	  			if(obj.value==""){obj.value="请输入验证码";}
	  		}
	  	}
	  	//验证
	  	function xiaoyan(con){
	  		var conn=true;
	  		if(con=="workId"){
	  			if($("#workId")[0].value=="" || $("#workId")[0].value=="请输入工号"){
	  				$("#msg").html("请输入工号");
	  				conn=false;
	  			}
	  		}else if(con=="passWD"){
	  			if($("#passWD")[0].value==""|| $("#passWD")[0].value=="请输入密码"){
	  				$("#msg").html("请输入密码");
	  				conn=false;
	  			}
	  		}else if(con=="authCode"){
	  			 if($("#authCode")[0].value=="" || $("#authCode")[0].value=="请输入验证码"){
	  				$("#msg").html("请输入验证码");
	  				conn=false;
	  			} 
	  		}
	  		return conn;
	  	}
	  <%-- 	//刷新验证码
	    function refreshAuthIMG(){
	    	var path="<%=path%>";
	      	$('#authIMG')[0].src = (path ? (path + '/') : '')+ 'getAuthImage?' + Math.random();
	    } --%>
	    //刷新验证码
	    function refreshAuthIMG(){
	    	$('#authIMG').attr('src', ('<%=path%>' ? ('<%=path%>' + '/') : '../')+ 'getAuthImage?' + Math.random());
	    }
	  	
	    //回车提交
	    function keydown(){
	    	if(isEnter()){
	    		doLogin();
	    	}
		}
	    //登录
	  	function doLogin(){
	  		if(xiaoyan("workId") && xiaoyan("passWD") && xiaoyan("authCode")){
	  			document.forms[0].submit();
	  		}
	  	}
	  	$(function(){
			jinzhijs();
			$("#workId").focus();
		});
  </script>
</head>
<body>
    <h1>客调质检管理系统</h1>
	<form action="<%=path%>/controller/user/login.do" method="post">
	     
		 <div class="login" onkeydown="keydown();">
		  	    
		  	    	<table border="0" cellspacing="0" cellpadding="0" width="100%">
		  	    		<tr height="50">
		  	    			<th colspan="2">用户登录</th>
		  	    		</tr>
		  	    		<tr height="36">
		  	    			<td><label>工&nbsp;&nbsp;&nbsp;&nbsp;号：</label></td>
		  	    			<td><input class="s_input" style="color: #999;font-size:12px" type="text" name="workId" id="workId" maxlength="20"  onfocus="inputFocus(this);" onblur="inputBlur(this);" value="${workId}"/></td>
		  	    		</tr>
		  	    		<tr height="36">
		  	    			<td><label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label></td>
		  	    			<td><input type="password" class="s_input password" name="passWD" id="passWD" maxlength="20" value="${passWD}" onfocus="inputFocus(this);" onblur="inputBlur(this);" /></td>
		  	    		</tr>
		  	    		<!-- <tr >
		  	    		    <td></td>
		  	    			<td><div id="passWordMsg" style="margin-top: -35px;cursor:text;font-size: 12px; text-indent: 5px;font-family: 微软雅黑;color: #999;" onclick="closePassWordMsg();">请输入密码</div></td>
		  	    		</tr> -->
		  	    		
		  	    		<tr height="36">
							<td><label>验证码：</label></td>
							<td><input type="text" name="authCode" id="authCode"  style="width: 120px!important;color: #999;font-size:12px" maxlength="5" class="s_input" onfocus="inputFocus(this);" onblur="inputBlur(this);"/>
								<img border="0" style="margin-left:1px;cursor:pointer;" align="middle" title="点击刷新"
				    				src="<%=request.getContextPath()%>/getAuthImage" id="authIMG" onclick="refreshAuthIMG();"></img></td>
						</tr>
		  	    		
		  	    		
		  	    		<tr height="18">
		  	    			<td></td>
		  	    			<td id="msg" style=" font-size: 12px;color: #000;">${login_msg}</td>
		  	    		</tr>
		  	    		<tr height="40">
		  	    			<td></td>
		  	    			<td><input type="button" name="login_b" id="login_b" onclick="doLogin();" value="登 录" class="sub" /></td>
		  	    		</tr>
		  	    		
		  	    	</table>
		
		</div>
	</form>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" style="position: absolute;top: 85%;">
	    <tr>
	    <td align="center"><span style="line-height:2em;">中国电信 <span style="font-family:Arial, Helvetica, sans-serif">&copy;</span>上海理想信息产业(集团)有限公司</span></td>
	  </tr>
	</table>
</body>
</html>