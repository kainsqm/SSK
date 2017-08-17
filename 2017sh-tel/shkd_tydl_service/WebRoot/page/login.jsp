<%@ page language="java" import="java.util.*,cn.sh.ideal.util.RSAUtil,java.security.KeyPair,java.security.interfaces.RSAPublicKey,java.security.interfaces.RSAPrivateKey" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	/* 	RSAUtil util=new RSAUtil();
        KeyPair rsap = (KeyPair) util.generateKeyPair();  
        RSAPublicKey publicKey = (RSAPublicKey)rsap.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey)rsap.getPrivate();
        String publicKeyModulus = publicKey.getModulus().toString(16);  
        //私钥保存在session中，用于解密  
        request.getSession().setAttribute("privateKey", privateKey);  */
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>运营管理系统</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!-- <script src="js/jquery-1.9.1.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="js/RSA.js"></script>    
<script type="text/javascript" src="js/BigInt.js"></script>    
<script type="text/javascript" src="js/Barrett.js"></script>   -->
<script type="text/javascript"> 
document.write("<scr"+"ipt src=\"js/jquery-1.9.1.js\"></sc"+"ript>") ;  
document.write("<scr"+"ipt src=\"js/RSA.js\"></sc"+"ript>") ;
document.write("<scr"+"ipt src=\"js/BigInt.js\"></sc"+"ript>") ;
document.write("<scr"+"ipt src=\"js/Barrett.js\"></sc"+"ript>") ;
</script> 
<style>
* {
	margin: 0;
	padding: 0;
	list-style: none;
}

body,html {
	width: 100%;
	height: 100%;
}

form {
	width: 100%;
	height: 100%;
	position: absolute;
	background: rgba(171, 175, 178, 0.2);
}

table,.login {
	width: 500px;
	height: 400px;
	margin: 0 auto;
	position: relative;
	top: 50%;
	margin-top: -200px;
	opacity: 1;
	text-align: center;
}

table th,h3 {
	font-size: 28px;
	color: #FFFFFF;
	height: 40px;
	letter-spacing: 10px;
}

.english,h4 {
	font-size: 20px;
	color: #dddfe2;
	height: 50px;
	line-height: 50px;
}

table td {
	color: #FFFFFF;
	text-align: center;
}

input[type="text"],input[type="password"] {
	border-radius: 3px;
	border: none;
	outline: none;
	text-indent: 10px;
	width: 270px;
	height: 36px;
	line-height: 36px;
	background: #f5f5f5;
	background: rgba(245, 245, 245, .35);
	-moz-border-radius: 6px;
	-webkit-border-radius: 6px;
	border-radius: 6px;
	border: 1px solid #3d3d3d;
	border: 1px solid rgba(255, 255, 255, .15);
	/*-moz-box-shadow: 0 2px 3px 0 rgba(0,0,0,.1) inset;
			    -webkit-box-shadow: 0 2px 3px 0 rgba(0,0,0,.1) inset;
			    box-shadow: 0 2px 3px 0 rgba(0,0,0,.1) inset;*/
	font-family: 'PT Sans', Helvetica, Arial, sans-serif;
	font-size: 14px;
	color: #fff;
	text-shadow: 0 1px 2px rgba(0, 0, 0, .1);
	-o-transition: all .2s;
	-moz-transition: all .2s;
	-webkit-transition: all .2s;
	-ms-transition: all .2s;
}

:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
	color: #FFFFFF;
	opacity: 1;
}

::-moz-placeholder { /* Mozilla Firefox 19+ */
	color: #FFFFFF;
	opacity: 1;
}

input:-ms-input-placeholder {
	color: #FFFFFF;
	opacity: 1;
}

input::-webkit-input-placeholder {
	color: #FFFFFF;
	opacity: 1;
}

input[type="button"] {
	FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0,
		startColorStr=#01aacf, endColorStr=#0165a2 ) !important; /*IE 6 7 8*/
	background: -ms-linear-gradient(top, #01aacf, #0165a2) !important;
	/* IE 10 */
	background: -moz-linear-gradient(top, #01aacf, #0165a2) !important;
	/*火狐*/
	background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#01aacf),
		to(#0165a2) ) !important; /*谷歌*/
	background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#01aacf),
		to(#0165a2) ) !important; /* Safari 4-5, Chrome 1-9*/
	background: -webkit-linear-gradient(top, #01aacf, #0165a2) !important;
	/*Safari5.1 Chrome 10+*/
	background: -o-linear-gradient(top, #01aacf, #0165a2) !important;;
	/*Opera 11.10+*/
	width: 270px;
	height: 36px;
	border-radius: 3px;
	border: none;
	outline: none;
	color: #FFFFFF;
	margin-top: 20px;
	text-align: center;
	font-size: 14px;
	cursor: pointer;
}

img {
	position: absolute;
	left: 70px;
	top: 50px;
}

.user {
	width: 400px;
	height: 200px;
	margin: 0 auto;
	padding-top: 30px;
	background: #2d2d2d;
	background: rgba(45, 45, 45, .15);
	-moz-border-radius: 6px;
	-webkit-border-radius: 6px;
	border-radius: 6px;
	border: 1px solid #3d3d3d;
	border: 1px solid rgba(255, 255, 255, .15);
	-moz-box-shadow: 0 2px 3px 0 rgba(0, 0, 0, .1) inset;
	-webkit-box-shadow: 0 2px 3px 0 rgba(0, 0, 0, .1) inset;
	box-shadow: 0 2px 3px 0 rgba(0, 0, 0, .1) inset;
	font-family: 'PT Sans', Helvetica, Arial, sans-serif;
	font-size: 14px;
	color: #fff;
	text-shadow: 0 1px 2px rgba(0, 0, 0, .1);
	-o-transition: all .2s;
	-moz-transition: all .2s;
	-webkit-transition: all .2s;
	-ms-transition: all .2s;
}

.user li {
	height: 50px;
}

.title {
	margin-top: 15px;
	font-size: 12px;
}

.font {
	text-align: center;
	margin-top: 70px;
	color: #FFFFFF;
	font-size: 13px;
}
</style>
<script type="text/javascript">
       var key ;  
       $(function(){
	  		if (window != top) {
              top.location.href = location.href; 
             }
	  		$("#workId").focus();  
	  	});
	
		 //判断前台参数
		 function checkSubmit(){
		      var workId = $("#workId").val();
			  var passwd = $("#wd").val();
			  
		     if(workId == ''){
				  $(".title").html("工号不能为空!");
			 }else if(passwd == ''){
				  $(".title").html("密码不能为空!");
			 }else{
			     // bodyRSA(); 
				 // var acePwd=encryptedString(key, encodeURIComponent(passwd));
				//  var aesworkId=encryptedString(key, encodeURIComponent(workId));
				  $("#pass").val(passwd);
				  $("#work").val(workId);
				document.forms[0].submit();
			 }
		 }
		  
		<%-- function bodyRSA(){    
		    setMaxDigits(130);    
		    key = new RSAKeyPair("10001","","<%=publicKeyModulus%>");     
		}   --%>
			 
		//回车提交
	    function keydown(){
	    	if(isEnter()){
	    		checkSubmit();
	    	}
		}
		
		/**
		 * 判断是否回车事件
		 * @return
		 */
		function isEnter(){
			//获取事件对象
			var elem = event.relatedTarget || event.srcElement || event.target ||event.currentTarget; 
			if (event.keyCode == 13){
				return true;
			}else{
				return false;
			}
		};

		/**
		 * 禁止backspace 回退
		 * @return
		 */
		function jinzhijs(){
			document.getElementsByTagName("body")[0].onkeydown =function(){
				//获取事件对象
				var elem = event.relatedTarget || event.srcElement || event.target ||event.currentTarget; 
				if(event.keyCode==8){//判断按键为backSpace键
					//获取按键按下时光标做指向的element
					var elem = event.srcElement || event.currentTarget; 
					//判断是否需要阻止按下键盘的事件默认传递
					var name = elem.nodeName;
					if(name!='INPUT' && name!='TEXTAREA'){
						return _stopIt(event);
					}
					var type_e = elem.type.toUpperCase();
					if(name=='INPUT' && (type_e!='TEXT' && type_e!='TEXTAREA' && type_e!='PASSWORD' && type_e!='FILE')){
							return _stopIt(event);
					}
					if(name=='INPUT' && (elem.readOnly==true || elem.disabled ==true)){
							return _stopIt(event);
					}
				}
			}
		}
</script>
</head>
<body style="background: url(img/top_back_min.jpg) no-repeat; background-size:100% 100% ;" onkeydown="keydown();">
		  <img src="img/logo_min.png" width="200px"/>
		  <form action="<%=path%>/controller/user/login.do" method="post" >
		         <input type="hidden" name="passwd" id="pass"/>
		         <input type="hidden" name="workId" id="work"/>
		  	     <div class="login">
		  	     	   <h3>运营管理系统</h3>
		  	     	   <h4>Operate Administration system</h4>
		  	     	   <div class="user">
		  	     	   	    <ul>
		  	     	   	    	<li><input type="text"  id="workId" value="${workId}" placeholder="工号" autocomplete="off"/></li>
		  	     	   	    	<li><input type="password"  id="wd" value="" placeholder="密码"/></li>
		  	     	   	    	<li><input type="button" onclick="checkSubmit()" value="登　录"/></li>
		  	     	   	    	
		  	     	   	    	<li class="title">${title}</li>
		  	     	   	    </ul>
		  	     	   </div>
		  	     	   <div class="font">中国电信 ©上海理想信息产业(集团)有限公司</div>
		  	     </div>
		  	    
		  </form>
	</body>
</html>
