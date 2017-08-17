<%@ page language="java" contentType="text/html;charset=UTF-8"%>  
<%@ page language="java" import="cn.sh.ideal.util.Constans" pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
%>
<style type="text/css">
	#div_top { padding-left:15px; height: 40px; line-height: 40px; font-size:14px; font-family:"黑体"; background-image:url(img/login/title_bg.jpg); border-bottom:#D7D7D7 1px solid;}
	
	.menuDiv{height:40px; vertical-align:middle; line-height:40px; cursor:pointer; border-top:#eef0f1 1px solid;position: relative;}
	.menuDiv .menu_img_show{ margin-left:15px;}
	.menuDiv .menu_text_show{ margin-left:10px; font-size:14px; font-family:"黑体";}
	.menuDiv .menu_yincang_show{position: absolute;right: 20px;top: 19px;}
	
	.childMenuDiv{ margin:0px; display:none; padding-bottom:10px; height:auto;}
	.childMenuDiv ul{ border:#000000 0px solid; margin-top:0px; padding-left:0px;}
	.childMenuDiv ul li{ list-style-type:none;border:#000000 0px solid; height:35px; line-height:35px; padding-left:50px; font-size:13px; font-family:"宋体"; cursor:pointer;}
	.childMenuDiv ul li:hover{ background-color:#0D5CA9; color:#FFFFFF;}
</style>
<script type="text/javascript">
	var menuTemp=[
		{id:"1",text:"系统管理",imgUrl:"img/login/icon1.png",childItems:[{id:"11",text:"用户管理",pid:"1",url:"page/system/sys_bumen_user.htm"},{id:"12",text:"角色管理",pid:"1",url:"page/system/roleInfoList.html"},{id:"16",text:"系统参数配置",pid:"1",url:"<%=path%>/controller/page/systemsQuery.do"},{id:"17",text:"系统日志查询",pid:"1",url:"page/system/logInfoList.html"}]},
		{id:"2",text:"题库管理",imgUrl:"img/login/tiku.png",childItems:[{id:"23",text:"考题维护",pid:"2",url:"<%=path%>/controller/quesTions/toQuestionPage.do"}]},
		{id:"3",text:"试卷管理",imgUrl:"img/login/sjk.png",childItems:[{id:"31",text:"题库选题",pid:"3",url:"<%=path%>/controller/optionQue/toOptionque.do"},{id:"34",text:"模板试卷",pid:"3",url:"<%=path%>/controller/exampaper/toManagerPage.do"},{id:"35",text:"试卷管理",pid:"3",url:"<%=path%>/examManager/controller/toExamManagerPage.do"}]},
		{id:"4",text:"考试管理",imgUrl:"img/login/kaoshi.png",childItems:[{id:"41",text:"进行考试",pid:"4",url:"page/examination/exam_ing.html"},{id:"42",text:"重考设置",pid:"4",url:"<%=path%>/controller/page/examResetView.do"},{id:"43",text:"考试维护管理",pid:"4",url:"<%=path%>/controller/page/manageView.do"}]},
		{id:"5",text:"阅卷管理",imgUrl:"img/login/yuejuan.png",childItems:[{id:"51",text:"试卷评分",pid:"5",url:"<%=path%>/controller/page/scoreView.do"}]},
		{id:"6",text:"考试情况查询",imgUrl:"img/login/chaxun1.png",childItems:[{id:"61",text:"考生查询",pid:"6",url:"<%=path%>/controller/page/userQuery.do"},{id:"62",text:"考试查询",pid:"6",url:"<%=path%>/controller/page/examQuery.do"},{id:"63",text:"个人考试查询",pid:"6",url:"<%=path%>/controller/page/examPersonQuery.do"}]},
		{id:"7",text:"统计报表",imgUrl:"img/login/icon4.png",childItems:[]},
		{id:"8",text:"公告列表",imgUrl:"img/login/yuejuan.png",childItems:[{id:"518",text:"公告列表",pid:"8",url:"<%=path%>/controller/notice/toNoticeList.do"}]},
	];
	$(function(){
		if(menuTemp!=null && menuTemp.length > 0){
			var divShowObj = document.getElementById("div_show");				
			for(var ii in menuTemp){
				divShowObj.appendChild(add_menu_info(menuTemp[ii].id,menuTemp[ii].imgUrl,menuTemp[ii].text,"close")); 
				if(menuTemp[ii].childItems!=null){
					divShowObj.appendChild(add_chlidMenu_info(menuTemp[ii].id,menuTemp[ii].childItems));
				}
			}				
		}
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
			menuInfoDiv.innerHTML += "<img class=\"menu_yincang_show\" src=\"img/login/s_02.png\" width=\"11\" align=\"middle\" />";
		}else{
			menuInfoDiv.innerHTML += "<img class=\"menu_yincang_show\" src=\"img/login/s_01.png\" width=\"11\" align=\"middle\" />";
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
			//li_temp.innerHTML = "<a target=\"main_page_iframe\" href=\"http://www.baidu.com\">"+childItems[ii].text+"</a>";
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
			//parent.document.getElementById("daohangLabel").innerHTML=pageText;
			//document.getElementById("main_page_iframe").src=pageUrl; 
			//{id:"11",text:"用户管理",pid:"1",url:"page/system/sys_bumen_user.htm"}
			//alert(pageId);
			f_addTab(pageId,pageText,pageUrl);
		}
	}
</script>
<div id="div_main" style="">
	<div id="div_show"></div>
</div>
