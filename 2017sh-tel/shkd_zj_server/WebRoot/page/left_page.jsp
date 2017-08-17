<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="cn.sh.ideal.util.Constans" pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
  String userId=request.getSession().getAttribute(Constans.USER_INFO_USERID).toString();
%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>>客调质量检查系统</title>
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/xcConfirm.css"/>
	<script src="<%=path %>/js/jquery-1.9.1.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<style type="text/css">
		body{margin:0px; background-color:#FFFFFF; background-image:url(<%=path %>/img/login/bgmain.png);}
		body, html{ height:100%; width:100%;}
		#div_main {background-color: #ffffff;border-radius: 5px; font-weight: bold; color: #535e66; width:220px; height:99%;border: solid 1px #D7D7D7;}
		#div_top { padding-left:15px; height: 40px; line-height: 40px; font-size:14px; font-family:"黑体"; background-image:url(../img/login/title_bg.jpg); border-bottom:#D7D7D7 1px solid;}
		
		.menuDiv{height:40px; vertical-align:middle; line-height:40px; cursor:pointer; border-top:#eef0f1 1px solid;}
		.menuDiv .menu_img_show{ margin-left:15px;}
		.menuDiv .menu_text_show{ margin-left:10px; font-size:14px; font-family:"黑体";}
		.menuDiv .menu_yincang_show{margin-left:60px;}
		
		.childMenuDiv{ margin:0px; display:none; padding-bottom:10px; height:auto;}
		.childMenuDiv ul{ border:#000000 0px solid; margin-top:0px; padding-left:0px;}
		.childMenuDiv ul li{ list-style-type:none;border:#000000 0px solid; height:35px; line-height:35px; padding-left:50px; font-size:13px; font-family:"宋体"; cursor:pointer;}
		.childMenuDiv ul li:hover{ background-color:#0D5CA9; color:#FFFFFF;}
	</style>
	<script type="text/javascript">
	var menuTemp=[];
		$(function(){
			
			$.ajax({
				url:'<%=path%>/controller/fun/getFunList.do',
				type:'post',
				data:{'userId':<%=userId%>},
				error: function(request) {
					$("#div_show").html('加载菜单异常，请稍候再试!');
				},
				success: function(data) {
					menuTemp=eval("("+data+")");
					var divShowObj = document.getElementById("div_show");	
				 	if(menuTemp!=null && menuTemp.length > 0){
						var divShowObj = document.getElementById("div_show");				
						for(var ii in menuTemp){
							divShowObj.appendChild(add_menu_info(menuTemp[ii].id,menuTemp[ii].imgUrl,menuTemp[ii].text,"close")); 
							if(menuTemp[ii].childItems!=null){
								divShowObj.appendChild(add_chlidMenu_info(menuTemp[ii].id,menuTemp[ii].childItems));
							}
						}				
					} 
				}
			});
			
			
		});
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
				"<img class=\"menu_img_show\" src=\""+menuImgUrl+"\" width=\"22\" align=\"middle\" />" +"<span class=\"menu_text_show\">"+menuName+"</span>";
			if(showType=="show"){
				menuInfoDiv.innerHTML += "<img class=\"menu_yincang_show\" src=\"../img/login/s_02.png\" width=\"11\" align=\"middle\" />";
			}else{
				menuInfoDiv.innerHTML += "<img class=\"menu_yincang_show\" src=\"../img/login/s_01.png\" width=\"11\" align=\"middle\" />";
			}
			return menuInfoDiv;
		}
		/**
		 * 添加二级菜单
		 * @menuID 父菜单对应的ID
		 * @childItems 二级菜单集合
		 */
		function add_chlidMenu_info(menuID,childItems){
			var childMenuDiv=document.createElement("div");
			$(childMenuDiv).attr("class","childMenuDiv");
			$(childMenuDiv).attr("id","childMenuDiv_"+menuID);
			var ul_temp = document.createElement("ul");
			var li_temp = null;
			for(var ii in childItems){
				li_temp = document.createElement("li");
				li_temp.innerHTML = childItems[ii].text;
				$(li_temp).attr("onClick","childMenuOnClick('"+childItems[ii].id+"','"+childItems[ii].url+"','"+childItems[ii].text+"');");
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
		function childMenuOnClick(pageId,pageUrl,pageText){
			//$(obj).css("background-color","#0D5CA9");
			//$(obj).css("color","#FFFFFF");
			if(pageUrl!=null && pageUrl!=""){
				parent.document.getElementById("daohangLabel").innerHTML=pageText;
				parent.document.getElementById("main_page_iframe").src=pageUrl;
			}
		}
	</script>
</head>
<body>
	<div id="div_main">
		<div id="div_top">菜单栏</div>
		<div id="div_show"></div>
	</div>
</body>
</html>
