<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt"  uri="/WEB-INF/fmt-rt.tld"%> 
<%@ taglib prefix="c"  uri="/WEB-INF/c.tld"%> 
<%@ taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<%@ page language="java"  import="org.apache.shiro.SecurityUtils"  contentType="text/html;charset=UTF-8"%>
<%@ page language="java"  import="org.apache.shiro.subject.Subject"  contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<%
	Subject user= SecurityUtils.getSubject(); 
	String updcode="";
	String delcode="";
	if (user.isPermitted("kscspz:update")) {  //判断有没有修改的权限 kscspz:query kscspz:add  kscspz:update kscspz:delete
	   updcode="yes";
    }
    if (user.isPermitted("kscspz:delete")) {  //判断有没有删除的权限
	   delcode="yes";
    }
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<TITLE>系统参数设置</TITLE>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${ctx}/js/ztree/css/demo.css" type="text/css">
		<link href="${ctx}/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${ctx}/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="${ctx}/js/ztree/js/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.exedit-3.5.js"></script>
		<script src="${ctx}/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
		<script src="${ctx}/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
        <script src="${ctx}/js/ajaxSession.js" type="text/javascript"></script>
		<SCRIPT type="text/javascript">
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			$("#selectAll").bind("click", selectAll);
		});
		
		var setting = {
			view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false
			},
			edit: {
				enable: true,
				editNameSelectAll: true,
				showRemoveBtn: showRemoveBtn,
				showRenameBtn: showRenameBtn,
				removeTitle:"删除类别",
				renameTitle:"编辑名称"
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeDrag: beforeDrag,
				beforeEditName: beforeEditName,
				beforeRemove: beforeRemove,
				beforeRename: beforeRename,
				onRemove: onRemove,
				onRename: onRename
			}
		};
		
		var zNodes ="";
		$.ajax({
			type:"post",
			url:"${ctx}/controller/syscode/getSysSetting.do",
			data:"",
			dataType:"json",
			async:false,
          	success:function(resultData){	
         		zNodes=resultData.nodes;  
			},
			error:function(e){
       			window.parent.getNode();
       	    }	
		});
		 		
		var log, className = "dark";
		function beforeDrag(treeId, treeNodes) {
			return false;
		}
		function beforeEditName(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			return true;
		}
		function beforeRemove(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			return confirm("确认删除 [" + treeNode.name + "] 吗？");
		}
		function onRemove(e, treeId, treeNode) {
			showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			return $.ajax({
				type:"post",
				url:"${ctx}/controller/syscode/delSysCode.do",
				data:{
					  id:treeNode.id,
					  name:treeNode.name
					 },
				dataType:"json",
				async:false,
      			success:function(result){		
	     		 	 if(result.delFlag=="1"){
	     		 		 window.parent.deleteNode();
	     		 		return true;
	     		 	 }else if(result.delFlag=="-1"){
	     		 		window.parent.isused();
		     		 	return false;
		     		 }else{
		     			window.parent.deletefail();
	     		 		return false;
	     		 	 }
				},
		 		error:function(e){
	   			 	window.parent.dodail();
	   			 	history.go(0);
	   			 	return false;
   				}	
			});	
		}
		function beforeRename(treeId, treeNode, newName, isCancel) {
			var flag = "";
			if(treeNode.name==""){
				flag = "add";
			}else{
				flag = "update";
			}
			if (newName.length == 0) {
				window.parent.checknameisnull();
				return false;
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				setTimeout(function(){zTree.editName(treeNode)}, 10);
				return false;
			}
			if(newName.length > 30){
				window.parent.checknamelength();
				return false;
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				setTimeout(function(){zTree.editName(treeNode)}, 10);
				return false;
			}
			if(flag=="add") {
				$.ajax({
					type:"post",
					url:"${ctx}/controller/syscode/addSysCode.do",
					data:{
						  pId:treeNode.pId,
						  name:newName	
						 },
					dataType:"json",
					async:false,
	      			success:function(result){
		     		 	if(result.insertFlag==1){
		     		 		$.ligerDialog.success('新增成功','提示',function(yes){	
		     		 			window.parent.insertsuccess();
	    					});	
		     		 		className = (className === "dark" ? "":"dark");
		     				showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
		     		 	}else if(result.insertFlag==-1){ 	
		     		 		$.ligerDialog.warn(result.flag,'提示',function(yes){	
								window.parent.insertsuccess();
	    					});	
		     		 	}else{ 	
		     		 		 window.parent.dodail();
		     		 		 removeHoverDom(treeNode.id, treeNode);
		     		 	}
					},
			 		error:function(e){
		   			 	$window.parent.dodail();
		   			 	return false;
	   				}	
				});	
			}else if(flag=="update") {
				$.ajax({
					type:"post",
					url:"${ctx}/controller/syscode/updateSysCode.do",
					data:{
						 id:treeNode.id,
						 pId:treeNode.pId,
						 name:newName	
						 },
					dataType:"json",
					async:false,
	      			success:function(result){
		     		 	if(result.updateFlag==1){
		     		 		window.parent.updatesuccess();	
		     		 		className = (className === "dark" ? "":"dark");
		     				showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
		     		 	}else if(result.updateFlag==-1){ 	
		     		 		$.ligerDialog.warn(result.flag,'提示',function(yes){	
								window.parent.insertsuccess();
	    					});
		     		 	}else{ 	
		     		 		 window.parent.dodail();
		     		 		 removeHoverDom(treeNode.id, treeNode);
		     		 		 
		     		 	}
					},
			 		error:function(e){
		   			 	window.parent.dodail();
		   			 	return false;
	   				}	
				});	
			}
			return true;
		}
		function onRename(e, treeId, treeNode, isCancel) {
			showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
		}
		function showRemoveBtn(treeId, treeNode) {
			var delcode="<%=delcode%>";
			var conn=true;
			if(treeNode.level==0 || treeNode.level==1 || delcode==""){
				conn=false;
			}
			return conn;
		}
		function showRenameBtn(treeId, treeNode) {
			var updcode="<%=updcode%>";
			var result=false;  //判断有没有修改的权限
			if(treeNode.level!=0 && updcode=="yes"){
			   result=true;
			}
			return result;
		}
		function showLog(str) {
			if (!log) log = $("#log");
			log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length > 8) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}

		var newCount = 1;
		function addHoverDom(treeId, treeNode) {
			if(treeNode.level==0 || treeNode.level==2){
				return false;
			}
			if(treeNode.level==1){
				newCount=newCount+1;
				var sObj = $("#" + treeNode.tId + "_span");
				if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
				var addStr = "<shiro:hasPermission name='kscspz:add'><span class='button add' id='addBtn_" + treeNode.tId + "' title='新增业务小类' onfocus='this.blur();'></span></shiro:hasPermission>";
				sObj.after(addStr);
				var btn = $("#addBtn_"+treeNode.tId);
				if (btn) btn.bind("click", function(e){
				    window.parent.sure("seting",e,treeNode);
					return false;
				});
			}			
		};
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
		};
		function selectAll() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
		}
		
		function addNextDom(e,treeNode){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:""});
			var node = zTree.getNodeByParam("id", (100 + newCount), null);
			if(node!=null){
					zTree.editName(node);
			} 
		}
	</SCRIPT>
	<style type="text/css">
		.ztree li span.button.add {
			margin-left: 2px;
			margin-right: -1px;
			background-position: -144px 0;
			vertical-align: top;
			*vertical-align: middle
		}
		
		.ztree li {
			margin-top: 5px;
		}
		
		body {
			overflow: auto !important;
		}
</style>
</HEAD>
<BODY style="background: #FFFFFF; border-right: 1px solid #bad3f1;">
	<div class="">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
</BODY>
</HTML>