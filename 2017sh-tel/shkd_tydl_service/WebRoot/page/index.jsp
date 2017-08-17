<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="cn.sh.ideal.util.ConfigProperties"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//加载首页区域所有的菜单地址
	String token = "?token="
			+ request.getAttribute("desToken").toString();
	String tokenExam = request.getAttribute("desToken").toString();
	String isEni=request.getAttribute("isEni").toString();
	String monitoExam=ConfigProperties.getProperty("monitoExam").toString();
	String ksurl="";
	String zjurl="";
	String reporturl="";
	String heartbeatIpAdd="";
	if(isEni=="true"){//为ENI网络 10.4.15
	   ksurl = ConfigProperties.getProperty("eniksurl");
	   zjurl = ConfigProperties.getProperty("enizjurl");
	   reporturl=ConfigProperties.getProperty("enireporturl");
	   heartbeatIpAdd =ConfigProperties.getProperty("enimonitorExamUrl").toString()+monitoExam;
	}else{//为内网网络 172.16.67
	   ksurl = ConfigProperties.getProperty("ksurl");
	   zjurl = ConfigProperties.getProperty("zjurl");
	   reporturl=ConfigProperties.getProperty("reporturl");
	   heartbeatIpAdd =ConfigProperties.getProperty("monitorExamUrl").toString()+monitoExam;
	}
	
	String reportouturl=reporturl+ConfigProperties.getProperty("ksouturl") + token;
	String zjouturl= zjurl+ConfigProperties.getProperty("zjouturl") + token;
	String ksouturl =ksurl+ConfigProperties.getProperty("ksouturl") + token;
	String userId = request.getAttribute("userId").toString();
	String ksexamurl = ksurl
			+ ConfigProperties.getProperty("ksexamurl") + token;
	String zjupdateurl = zjurl
			+ ConfigProperties.getProperty("zjupdateurl") + token;
	String noticeinfo = zjurl
			+ ConfigProperties.getProperty("noticeinfo") + token;
	String noticelist = zjurl
			+ ConfigProperties.getProperty("noticelist") + token;
	String agentpaperinfo = zjurl
			+ ConfigProperties.getProperty("agentpaperinfo") + token;
	String agentpaperlistBySl = zjurl
			+ ConfigProperties.getProperty("agentpaperlistBySl")
			+ token;
	String agentpaperlistByQc = zjurl
			+ ConfigProperties.getProperty("agentpaperlistByQc")
			+ token;
	String needexaminfo = ksurl
			+ ConfigProperties.getProperty("needexaminfo") + token;
	String needexamlist = ksurl
			+ ConfigProperties.getProperty("needexamlist") + token;
	String examedinfo = ksurl
			+ ConfigProperties.getProperty("examedinfo") + token;
	String examedList = ksurl
			+ ConfigProperties.getProperty("examedList") + token;
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
<link rel="stylesheet" type="text/css" href="css/index.css" />
<link rel="stylesheet" type="text/css" href="css/public.css" />
<!-- <link href="css/font-awesome.min.css" rel="stylesheet">   -->
<!-- <script src="js/jquery-1.9.1.js" type="text/javascript" charset="utf-8"></script> -->
 <link href="lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet"
	type="text/css" /> 
 <link href="lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet"
	type="text/css" /> 
<!-- <script src="lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="lib/ligerUI/js/plugins/ligerResizable.js"
	type="text/javascript"></script>
<script src="lib/ligerUI/js/plugins/ligerDialog.js"
	type="text/javascript"></script>
<script src="js/util.js" type="text/javascript" charset="utf-8"></script> -->
<title></title>
<script type="text/javascript"> 
document.write("<scr"+"ipt src=\"js/jquery-1.9.1.js\"></sc"+"ript>") ;  
document.write("<scr"+"ipt src=\"lib/ligerUI/js/core/base.js\"></sc"+"ript>") ;
document.write("<scr"+"ipt src=\"lib/ligerUI/js/plugins/ligerResizable.js\"></sc"+"ript>") ;
document.write("<scr"+"ipt src=\"lib/ligerUI/js/plugins/ligerDialog.js\"></sc"+"ript>") ;
document.write("<scr"+"ipt src=\"js/util.js\"></sc"+"ript>") ;
document.write("<scr"+"ipt src=\"js/index.js\"></sc"+"ript>") ;
</script>

<style type="text/css">
body {
	margin: 0px;
	background-color: #FFFFFF;
	/* background-image: url(img/login/bgmain.png) */;
}
.all{
	width: 100%;
	height: 100%;
	background: url(img/top_back_min.jpg); 
	background-size:100% 100% ;
}
.head{
	height: 120px;
	width: 100%;
	background: url(img/top_back_min.jpg); 
	padding-top: 10px;
	background-size:100% 100% ;
}
.head .data{
	width: 88%;
    margin: 0 auto;
    position: relative;
    z-index: 999;
    height: 50px;
    padding: 0 50px;
    line-height: 45px;
    top: 0px;
    background: url(img/icon.png)  no-repeat;
    background-size:100% 100% ;
}

#div_main {
	float: left;
	background-color: #0a316a;
	font-weight: bold;
	color: #535e66;
	width: 270px;
	height: 100%;
	position: relative;
	FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0,
		startColorStr=#01add1, endColorStr=#0a316a ); /*IE 6 7 8*/
	background: -ms-linear-gradient(top, #01add1, #0a316a); /* IE 10 */
	background: -moz-linear-gradient(top, #01add1, #0a316a); /*火狐*/
	background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#01add1),
		to(#0a316a) ); /*谷歌*/
	background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#01add1),
		to(#0a316a) ); /* Safari 4-5, Chrome 1-9*/
	background: -webkit-linear-gradient(top, #01add1, #0a316a);
	/*Safari5.1 Chrome 10+*/
	background: -o-linear-gradient(top, #01add1, #0a316a); /*Opera 11.10+*/
}

#div_top {
	padding-left: 15px;
	height: 40px;
	line-height: 40px;
	font-size: 26px;
	font-family: "黑体";
	letter-spacing: 2px;
	color: #03717f;
	text-shadow: 3px 2px 2px #0385ae;
}

.menuDiv {
	height: 34px;
	vertical-align: middle;
	line-height: 34px;
	cursor: pointer;
	border-bottom: #b6e7ff 1px solid;
	FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0,
		startColorStr=#fff, endColorStr=#8ed9fb ); /*IE 6 7 8*/
	background: -ms-linear-gradient(top, #fff, #8ed9fb); /* IE 10 */
	background: -moz-linear-gradient(top, #fff, #8ed9fb); /*火狐*/
	background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#fff),
		to(#8ed9fb) ); /*谷歌*/
	background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#fff),
		to(#8ed9fb) ); /* Safari 4-5, Chrome 1-9*/
	background: -webkit-linear-gradient(top, #fff, #8ed9fb);
	/*Safari5.1 Chrome 10+*/
	background: -o-linear-gradient(top, #fff, #8ed9fb); /*Opera 11.10+*/
}

.menuDiv .menu_img_show {
	margin-left: 15px;
	position: relative;
	top: 3px;
}

.menuDiv .menu_text_show {
	margin-left: 10px;
	font-size: 14px;
	font-family: "黑体";
	color: #1c92bf
}

.menuDiv .menu_yincang_show {
	margin-left: 100px;
}

.childMenuDiv {
	margin: 0px;
	display: none;
	height: auto;
}

.childMenuDiv ul {
	border: #000000 0px solid;
	margin-top: 0px;
	padding-left: 0px;
	background: #f1fcff
}

.childMenuDiv ul li {
	list-style-type: none;
	border: #000000 0px solid;
	height: 35px;
	line-height: 35px;
	padding-left: 40px;
	font-size: 13px;
	font-family: "宋体";
	cursor: pointer;
}

.childMenuDiv ul li:hover {
	background-color: #01aacf;
	color: #FFFFFF;
}

.fa-angle-right:before {
	position: relative;
	left: -12px;
	font-size: 16px;
	top: 1px;
}

.fa-home {
	font-size: 20px;
	position: relative;
	left: -5px;
	top: 1px;
	color: #164ca8;
	cursor: pointer;
}

.fa-wrench {
	font-size: 16px;
	color: #164ca8;
	cursor: pointer;
	margin: 0 5px;
}

.fa-user-circle-o {
	font-size: 16px;
	color: #164ca8;
	margin-right: 5px;
}

.icon-quit {
	cursor: pointer;
}

.icon-quit:before {
	font-size: 16px;
	font-family: "FontAwesome", "microsoft yahei";
	margin: 0;
	content: "\f011";
	position: relative;
	top: 2px;
}

}
.middle_right_index,.middle_right_index ul {
	height: 100%;
}

.middle_right_index ul>li {
	width: 50%;
	float: left;
	height: 50%;
	padding: 20px 50px;
	position: relative;
}

.middle_right_index ul>li.le {
	padding-right: 0;
}

.middle_right_index ul>li.le .content {
	right: 0;
}

.middle_right_index ul>li .title {
	width: 100%;
	height: 28px;
	border: 2px solid #D7D7D7;
	line-height: 28px;
	border-radius: 3px;
	text-align: center;
	background: url(img/Icon2.jpg);
	background-size: 100% 110%;
	color: #0168a2;
	font-weight: 600;
}

.middle_right_index ul>li .content {
	border: 2px solid #D7D7D7;
	border-radius: 3px;
	position: absolute;
	top: 56px;
	bottom: 20px;
	left: 50px;
	right: 50px;
	padding: 5px;
}

body {
	background: #f1fcff;
}
/* .content div{padding-left:10px;} */
.content div a {
	text-decoration: none;
	font-size: 13px;
	cursor: pointer;
	color: #333333;
}

.fa-caret-right {
	color: #104190;
	margin-right: 5px;
}

.content div a:hover {
	color: #104190;
	text-decoration: underline;
}

.title a {
	text-decoration: none
}

li img {
	cursor: pointer;
	vertical-align: middle;
	position: relative;
	top: -2px;
}

.middle .middle_right .route img {
	cursor: pointer;
	vertical-align: middle;
	position: relative;
	top: -2px;
}

.middle_middle img {
	position: relative;
	top: 49%;
	width: 14px
}
</style>
<script type="text/javascript">
		    var index=0;
			$(function(){
				$(".middle_middle").on("click",function(){
					
			    	$(this).prev().toggle();
			    	$(this).next().toggleClass("all_win");
			    	if($(this).next().hasClass("all_win")){
			    	     $(this).find("img").attr("src","img/right.png");
			    	}else{
			    	     $(this).find("img").attr("src","img/left.png");
			    	}
			   });
			   $(".index").on("click",function(){
			   	    $(".middle .middle_right .route span").html("首页");
					$("#iframediv").css("display","none");
					$("#middle_right_index").css("display","block");
					//加载考试信息
				    getExam();
				    getNotice();
			   });
			  
				//切换菜单
				$(".head .head_nav input.nav").on("click",function(){
					    var texts = $(this).val();
					    $(".div_content h3").html(texts);
					    $(this).addClass("cur").siblings().removeClass("cur");
					    $(".middle .middle_right .route span").html("首页");
						$("#iframediv").css("display","none");
					    $("#middle_right_index").css("display","block");
					    $("#div_show").html("");
					    getFunction($(this).attr("name"));
				});
				
				//默认左侧菜单加载   如果没有系统管理 则加载质检系统菜单  否则加载考试系统菜单
				var SYS = $("#SYS");
				var ZJ = $("#ZJ");
				var KS = $("#KS");
				if(SYS.length>0){ 
					    getFunction("SYS");
					    var texts =$("#SYS").val(); 
					    $(".div_content h3").html(texts);
				}else if(ZJ.length>0){ 
					    getFunction("ZJ");
					    var texts =$("#ZJ").val(); 
					    $(".div_content h3").html(texts);
				}else if(KS.length>0){ 
					    getFunction("KS");
					    var texts =$("#KS").val(); 
					    $(".div_content h3").html(texts);
				}
				
				//加载考试信息
				getExam();
				getNotice();
				
			});
		
			//加载考试信息
			function getExam(){
			     jQuery.ajax({
					'type' : 'GET',
					'url' : '<%=ksexamurl%>',
					'data':{userId:<%=request.getAttribute("userId")%>},
					'dataType' : 'jsonp',// jsonp
					'jsonp' : 'callback',
					'cache' : false,
					'success' : function(data) {
					      $("#examList div").remove();
					      $("#examoverList div").remove();
						 //加载待考试
						for(var rowdata in data.examList){
						  var html="<div><img src='img/next.png'/><a href='javascript:void(0)' onclick='beginexam(\""+data.examList[rowdata].exampaperId+"\",\""+data.examList[rowdata].examExampaperExamineId+"\",\""+data.examList[rowdata].pkAutoId+"\",\""+data.examList[rowdata].examTimeLength+"\");'>"+data.examList[rowdata].examName+"</a></div>";
						   $("#examList").append(html);
						 };
						  //加载已考试卷列表
						for(var rowdata in data.examoverList){
						     var html="";
						    if(data.examoverList[rowdata].examQuery=="0"){
						       html ="<div ><img src='img/next.png'/><span style='color:gray'>"+data.examoverList[rowdata].examName+"</span></div>";
						    }else{
						       html ="<div><img src='img/next.png'/><a href='javascript:void(0)' onclick='showexamInfo(\""+data.examoverList[rowdata].pkAutoId+"\");'>"+data.examoverList[rowdata].examName+"</a></div>";
						    }
	
						    $("#examoverList").append(html);
						 };
					},
					 error: function(){
				         $.ligerDialog.error('获取待考试信息异常!!');
				     }
					
			    });
			    index=0;  
			}
			
			
			//加载反馈单，公告信息
			function getNotice(){
			     $.ajax({
					type : 'POST',
					url : '<%=path%>/controller/user/getAgentNotice.do',
					dataType : 'json',
					success : function(data) {
					      $("#noticeList div").remove();
					      $("#agentPaperList div").remove();
						 //加载公告
						for(var rowdata in data.noticeList){
						   var html="<div><img src='img/next.png'/><a href='<%=noticeinfo%>&noticeId="+data.noticeList[rowdata].pkAutoId+"' target='_blank'>"+data.noticeList[rowdata].title+"("+data.noticeList[rowdata].releaseDate+")</a></div>";
						   $("#noticeList").append(html);
						 };
						  //加载反馈单列表
						for(var rowdata in data.agentPaperList){
						     var html ="<div><img src='img/next.png'/><a href='javascript:void(0);' onclick='agentworkPaper(\""+data.agentPaperList[rowdata].agentPaperId+"\");'>反馈单编号:"+data.agentPaperList[rowdata].agentPaperId+"</a></div>";
						    $("#agentPaperList").append(html);
						 };
						 //设置反馈单更多链接地址
						  if(data.roleFlag=="role_1"){ //受理员
						     $("#agentpapermore").attr("href","<%=agentpaperlistBySl%>"); 
						  } else if(data.roleFlag=="role_3"){ //质检员
						     $("#agentpapermore").attr("href","<%=agentpaperlistByQc%>"); 
						  }  else if(data.roleFlag=="role_2"){ //受理员班长
						      $("#agentpapermore").attr("href","<%=agentpaperlistBySl%>"); 
						  } else{
						     // $('#agentpapermore').removeattr('href'); 
						      //$('#agentpapermore').removeattr('target'); 
						      $('#agentpapermore').attr('disabled',"true");//添加disabled属性
						  } 
                          
						 
					}
			});
			     
			}
			
			
			//加载反馈单详情
			function agentworkPaper(agentid){
			    var form=$("<form>");//定义一个form表单
				 form.attr("style","display:none");
				 form.attr("target","_blank");
				 form.attr("id","pinfenform");
				 form.attr("method","post");
				 form.attr("action","<%=agentpaperinfo%>");
				 var inputagentid=$("<input>"); //agentid
				 inputagentid.attr("type","hidden");
				 inputagentid.attr("name","agentid");
				 inputagentid.attr("value",agentid);
				 $("body").append(form);//将表单放置在body中
				 form.append(inputagentid);
				 form.submit();//表单提交
				 $("#formid").remove();
			}
			
			
			function showexamInfo(examid){
			   $.ligerDialog.open({
								height : 560,
								width : 1010,
								showMax : true,
								modal : false,
								id : 'examname',
								isDrag : false,
								allowClose : true,
								title : '查看试卷',
								url : '<%=examedinfo%>&id='+examid,
								isResize : true,
							});
			}
			
			
			 //获取左边功能菜单栏的菜单
		     function getFunction(syscode){
				   //加载左侧菜单栏
				 	$.ajax({
					url:'<%=path%>/controller/fun/getFunList.do',
					type:'post',
					data:{syscode:syscode},
					error: function(request) {
						$("#div_show").html('加载菜单异常，请稍候再试!');
					},
					success: function(data) {
						menuTemp=eval("("+data+")");
						if(menuTemp.timeout!=undefined){
						    $.ligerDialog.warn('由于您长时间没有操作,请重新登录','提示', function (yes) { 
		                  	 var p = window; 
		                  	 while(p != p.parent){ 
		                  		 p = p.parent; 
		                  	  } 
		                  	 //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
		                  	   var pathName=window.document.location.pathname;
		                  	  var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
		                  	  p.location.href = projectName+'/page/login.jsp'; 
		                   });
						}
						var divShowObj = document.getElementById("div_show");	
					 	if(menuTemp!=null && menuTemp.length > 0){
							var divShowObj = document.getElementById("div_show");				
							for(var ii in menuTemp){
								divShowObj.appendChild(add_menu_info(menuTemp[ii].id,menuTemp[ii].imgUrl,menuTemp[ii].text,"close")); 
								if(menuTemp[ii].childItems!=null){
									divShowObj.appendChild(add_chlidMenu_info(menuTemp[ii].id,menuTemp[ii].childItems,menuTemp[ii].text));
								}
							}				
						} 
					 }
			     	});
				 
		     }
				 
				
			
			
			
			/**
			 * 添加一级菜单
			 * @menuID 菜单ID
			 * @menuImgUrl 菜单对应展示的图片 url
			 * @menuName 菜单名称
			 * @showType 加载类型 show:展示 close:隐藏
			 */
			function add_menu_info(menuID,menuImgUrl,menuName,showType){
				var menuInfoDiv=document.createElement("div");
				$(menuInfoDiv).attr("class","menuDiv");
				$(menuInfoDiv).attr("id","menuDiv_"+menuID);
				$(menuInfoDiv).attr("onclick","showChildMenu(this,\""+menuID+"\");");
				menuInfoDiv.innerHTML=""+
					"<img class=\"menu_img_show\" src=\""+menuImgUrl+"\" width=\"20\"  />" +"<span class=\"menu_text_show\">"+menuName+"</span>";
				if(showType=="show"){
					menuInfoDiv.innerHTML += "<img class=\"menu_yincang_show\" src=\"img/login/s_02.png\" width=\"11\"  />";
				}else{
					menuInfoDiv.innerHTML += "<img class=\"menu_yincang_show\" src=\"img/login/s_01.png\" width=\"11\"  />";
				}
				return menuInfoDiv;
			}
			/**
			 * 添加二级菜单
			 * @menuID 父菜单对应的ID
			 * @childItems 二级菜单集合
			 */
			function add_chlidMenu_info(menuID,childItems,fisrtext){
				var childMenuDiv=document.createElement("div");
				$(childMenuDiv).attr("class","childMenuDiv");
				$(childMenuDiv).attr("id","childMenuDiv_"+menuID);
				var ul_temp = document.createElement("ul");
				var li_temp = null;
				for(var ii in childItems){
					li_temp = document.createElement("li");
					li_temp.innerHTML = childItems[ii].text;
					if(childItems[ii].remark=="ks"){
					  $(li_temp).attr("onClick","childMenuOnClick('"+childItems[ii].id+"','"+'<%=ksurl%>'+childItems[ii].url+"?token="+'<%=request.getAttribute("desToken")%>'+"','"+childItems[ii].text+"','"+fisrtext+"');");
					}else if(childItems[ii].remark=="zj"){
					  $(li_temp).attr("onClick","childMenuOnClick('"+childItems[ii].id+"','"+'<%=zjurl%>'+childItems[ii].url+"?token="+'<%=request.getAttribute("desToken")%>'+"','"+childItems[ii].text+"','"+fisrtext+"');");
					}else if(childItems[ii].remark=="report"){
					  $(li_temp).attr("onClick","childMenuOnClick('"+childItems[ii].id+"','"+'<%=reporturl%>'+childItems[ii].url+"?token="+'<%=request.getAttribute("desToken")%>'+"','"+childItems[ii].text+"','"+fisrtext+"');");
					}
					
					ul_temp.appendChild(li_temp);
				}
				childMenuDiv.appendChild(ul_temp);			
				return childMenuDiv;
			}
			/**
			 * 点击主菜单时，加载或隐藏子菜单
			 * @obj 主菜单div对象
			 * @childMenuNum 子菜单div对应的序号
			 */
			function showChildMenu(obj,childMenuNum){
				changeShowImg(obj);
				var childMenuObj = document.getElementById("childMenuDiv_"+childMenuNum); 
				if(childMenuObj!=null){
					$(childMenuObj).slideToggle();
				}			
				var childMenuDivItem = document.getElementsByTagName("div");
				for(var jj in childMenuDivItem){
					if(childMenuDivItem[jj].id != null && childMenuDivItem[jj].id.indexOf("childMenuDiv_") != -1){
						var menuDivID = childMenuDivItem[jj].id.replace("childMenuDiv_","menuDiv_");
						if(menuDivID != obj.id){
							if($(childMenuDivItem[jj]).css("display") == "block"){
								changeShowImg(document.getElementById(menuDivID));
								$(childMenuDivItem[jj]).slideToggle();
								break;
							}
						}
					} 
				}
			}
			/**
			 * 改变一级菜单后面的伸缩图片
			 * @obj 一级菜单所在的div对象 
			 */
			function changeShowImg(obj){
				var childNodesItem = obj.childNodes;
				for(var i in childNodesItem){
					if(childNodesItem[i].className=="menu_yincang_show"){
						if(childNodesItem[i].src.indexOf("s_01.png") != -1){
							childNodesItem[i].src=childNodesItem[i].src.replace("s_01.png","s_02.png");
						}else if(childNodesItem[i].src.indexOf("s_02.png") != -1){
							childNodesItem[i].src=childNodesItem[i].src.replace("s_02.png","s_01.png");
						}
						break;
					}
				}
			}
			/**
			 * 子菜单选中事件
			 * @obj 子菜单对象
			 */
			
			function childMenuOnClick(pageId,pageUrl,pageText,fisrtext){
				if(pageUrl!=null && pageUrl!=""){
					//parent.document.getElementById("daohangLabel").innerHTML=pageText;
					document.getElementById("main_page_iframe").src=pageUrl;
					$("#iframediv").css("display","block");
					$("#middle_right_index").css("display","none");
					var texts = $(".head .head_nav input.cur").val();
//					texts = texts.substring(1,texts.length-1);
					$(".middle .middle_right .route span").html(texts+"  <img src='img/next1.png'/>"+fisrtext+"  <img src='img/next1.png'/>"+pageText);
				}
			
			}
			
			function updatePwd(){
			   
				//$.ligerDialog.open({
				//	height:280,
			    //    width: 450,
				//	title : "修改密码",
				//	url:'<%=path%>/page/edit_pwd.jsp', 
				//	showMax: false,
				//	showToggle: true,
				//	showMin: false,
				//	isResize: true,
				//	slide: false
				// });
				$.ligerDialog.open({
					height:240,
					width: 300,
					title : "修改密码",
					url:'<%=zjupdateurl%>', 
					showMax: false,
					showToggle: false,
					showMin: false,
					isResize: true,
					slide: false
			     });
			}
			
			function loginout(){
				/*********************
				 *退出系统
				 *********************/
					$.ligerDialog.confirm('确认要退出嘛？', function(yes) {
						if (yes) {
						   jsonpOut();
						   if(index==1){
						       $.ligerDialog.confirm('你还有考试没有完成,确认要退出嘛？', function(yes) {
							      if (yes) {
								       index=0;
									   $.ajax({
											url:'<%=path%>/controller/user/loginOut.do',
											type:'post',
											error:function(request){
												$.ligerDialog.error('系统异常，请稍后再试！');
											},
											success:function(data){
													window.location.href='<%=path%>/page/login.jsp';
												}
										});
								  }
								});
						   }else{
						       $.ajax({
											url:'<%=path%>/controller/user/loginOut.do',
											type:'post',
											error:function(request){
												$.ligerDialog.error('系统异常，请稍后再试！');
											},
											success:function(data){
													window.location.href='<%=path%>/page/login.jsp';
												}
											});
								}

							}
						});
	}

	function beginexam(exampaperId, examExampaperExamineId, examId, strdisplay) {
			    joinExam(exampaperId, examExampaperExamineId, examId, strdisplay);
			    index = 1;
		
	}
	
	
	/****
	调用子系统的退出
	****/
	function jsonpOut(){
	     jQuery
			.ajax({
				'type' : 'get',
				'url' : '<%=zjouturl%>',
				'dataType' : 'jsonp',// jsonp
				'jsonp' : 'callback',
				'contentType': "application/jsonp; charset=utf-8",
				'async' : false,
				'cache' : false,
				success : function(json){ },
             error: function(xhr,status,error)
             { console.log(xhr); }
			  });

  jQuery
			.ajax({
				'type' : 'get',
				'url' : '<%=reportouturl%>',
				'dataType' : 'jsonp',// jsonp
				'jsonp' : 'callback',
				'contentType': "application/jsonp; charset=utf-8",
				'async' : false,
				'cache' : false,
				success : function(json){                      
                },
             error: function(xhr,status,error)
             { console.log(xhr); }
			  });

         jQuery
			.ajax({
				'type' : 'get',
				'url' : '<%=ksouturl%>',
				'dataType' : 'jsonp',// jsonp
				'jsonp' : 'callback',
				'contentType': "application/jsonp; charset=utf-8",
				'async' : false,
				'cache' : false,
				success : function(json){},
             error: function(xhr,status,error)
             { console.log(xhr); }
			  });
	} 
</script>
</head>
<body style="overflow:hidden">
	<!-- <script src="js/index.js" type="text/javascript" charset="utf-8"></script> -->
	<input value="<%=tokenExam%>" id="tokenExam" type="hidden">
	<input  id="clientY" type="hidden">
	<div class="all">
		<div class="head">
			<div class="logo fl">
				<img src="img/logo_min.png" width="200px" />
				<div>
					<span class="chinese">运营管理系统</span> <span class="english">Operate
						Administration system</span>
				</div>

			</div>
			<div class="head_nav fr">
				<c:forEach items="${funSys}" var="fun">
					<input type="button" name="${fun.code}" id="${fun.code}" 
						class="nav cur" id="" value="${fun.funtionName}" />
				</c:forEach>
			</div>
			<div class="data cb">
				<div class="fl">
					<ul>
						<li><img src="img/user.png" />你好：<span>${user.userName}</span>
						</li>
						<li>工号：<span>${user.workId}</span></li>
						<li>角色：<span>${roleName}</span></li>
					</ul>
				</div>
				<div class="fr">
					<ul>
						<li class="index"><img src="img/main.png" title="首页" /></li>
						<li>当前时间：<span class="current_time"></span></li>
						<li><img src="img/update.png" onclick="updatePwd()"
							title="修改密码" /></li>
						<li><img src="img/out.png" onclick="loginout()" title="退出" />
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="middle">
			<div id="div_main">
				<div id="div_top">MENU</div>
				<div class="div_content">
					<h3 id="sysname"></h3>
					<div class="back_div"></div>
					<div id="div_show"></div>
				</div>

			</div>
			<div class="middle_middle">
				<img src='img/left.png' onclick="leftClick();" />
			</div>
			<div class="middle_right">
				<div class="route">
					当前位置：<span>首页</span>
				</div>
				<div id="iframediv" class="iframe" style="display:none">
					<iframe id="main_page_iframe" src="" frameborder="0" width="100%"
						height="100%"></iframe>
				</div>
				<div id="middle_right_index" class="middle_right_index"
					style="overflow:hidden;position: absolute;left: 0;right: 0;bottom: 0;top: 60px;">
					<ul style="height:100%">
						<li class="le">
							<div class="title">
								公告栏<a href='<%=noticelist%>' target='_blank'
									style="right:1px;width: 57px;float: right;position:absolute;"><img
									src="img/other.png" title="更多" style="top:4px;left:15px;">
								</a>
							</div>
							<div class="content" id="noticeList"></div>
						</li>
						<li>
							<div class="title">
								我的反馈单<a target='_blank'
									id="agentpapermore"
									style="right:51px;width: 57px;float: right;position:absolute;"><img
									src="img/other.png" title="更多" style="top:4px;left:15px;">
								</a>
							</div>
							<div class="content" id="agentPaperList"></div>
						</li>
						<li class="le">
							<div class="title">
								待考试<a href='<%=needexamlist%>' target='_blank'
									style="right:1px;width: 57px;float: right;position:absolute;"><img
									src="img/other.png" title="更多" style="top:4px;left:15px;">
								</a>
							</div>
							<div class="content" id="examList"></div>
						</li>
						<li>
							<div class="title">
								查看已考试卷<a href='<%=examedList%>' target='_blank'
									style="right:51px;width: 57px;float: right;position:absolute;"><img
									src="img/other.png" title="更多" style="top:4px;left:15px;">
								</a>
							</div>
							<div class="content" id="examoverList"></div>
						</li>
					</ul>
				</div>
			</div>

		</div>
		<div class="font">中国电信 ©上海理想信息产业(集团)有限公司</div>
	</div>

</body>


<script type="text/javascript">
	 function joinExam(exampaperId, examExampaperExamineId, examId, strdisplay) {
	$.ligerDialog.confirm('您确认要参加考试嘛？', function(yes) {
		if (yes) {
			try {
				myAjaxJsonpExamResult(examExampaperExamineId, strdisplay,
						examId, exampaperId);
			} catch (e) {
			console.log(e); 
				$.ligerDialog.error('访问外域出错!');
			}
		}
	});
}

// start niewenqiang 2017-04-26 ajax跨域提交 用于判断该场考试该考试是否已在考试
function myAjaxJsonpExamResult(examExampaperExamineId, strdisplay, examId,
		 exampaperId) {
	<%-- jQuery
			.ajax({
				'type' : 'get',
				'url' : '<%=heartbeatIpAdd%>',
				'data' : {
					'kaoshiId' : examExampaperExamineId,
					'userId' : '<%=userId%>',
					'answerStr' : "",
					'ipAddr' : '<%=request.getRemoteAddr()%>',
					'state' : "0",
					'remainingTime' : strdisplay,
					'examId' : examId,
					'method' : 'checkDk'
				},
				'dataType' : 'jsonp',// jsonp
				'jsonp' : 'callback',
				'contentType': "application/jsonp; charset=utf-8",
				'async' : false,
				'cache' : false,
				'success' : function(data) {
					if (data != undefined) {
						if (data.flg == "4") {
							$.ligerDialog
									.error("登录的电脑(IP)已经进行考试 或 登录的用户已在其他地方进行考试!");
							return;
						}
					} 
					$.ligerDialog
							.open({
								height : 560,
								width : 1010,
								showMax : true,
								modal : false,
								id : 'examname',
								isDrag : false,
								allowClose : false,
								title : '考试中',
								url : '<%=needexaminfo%>&pkAutoId='
										+ examId
										+ '&exampaperId='
										+ exampaperId
										+ '&examExampaperExamineId='
										+ examExampaperExamineId
										+ '&isIntoPageByMain=1'+'&token='+$("#tokenExam").val(),
								isResize : true,
							});
				},error: function(){
			         $.ligerDialog.error('系统异常!');
			     }
			});--%>
			
			$.ligerDialog
			.open({
				height : 560,
				width : 1010,
				showMax : true,
				modal : false,
				id : 'examname',
				isDrag : false,
				allowClose : false,
				title : '考试中',
				url : '<%=needexaminfo%>&pkAutoId='
						+ examId
						+ '&exampaperId='
						+ exampaperId
						+ '&examExampaperExamineId='
						+ examExampaperExamineId
						+ '&isIntoPageByMain=1'+'&token='+$("#tokenExam").val(),
				isResize : true,
			});
			
}
</script>



</html>
