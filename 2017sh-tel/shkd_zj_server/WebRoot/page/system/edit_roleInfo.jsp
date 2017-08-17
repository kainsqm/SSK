<%@ page language="java" contentType="text/html;charset=UTF-8"%>  
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<%
  String path = request.getContextPath(); 
%> 
<!DOCTYPE html>
<HTML>
<HEAD>
<TITLE>ZTREE DEMO - checkbox</TITLE>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=path %>/js/ztree/css/demo.css"
	type="text/css">
<link rel="stylesheet"
	href="<%=path %>/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=path %>/js/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="<%=path %>/js/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="<%=path %>/js/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript"
	src="<%=path %>/js/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script src="<%=path %>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/plugins/ligerDialog.js"
	type="text/javascript"></script>
<script src="<%=path%>/js/util.js" type="text/javascript"></script>
<style type="text/css">
ul.ztree {
	width: auto;
	height: auto;
	border: #0033CC 0px solid;
}

.ztree li {
	margin-top: 5px;
}

</style>
</HEAD>

<BODY style=" padding-left:5px;">
	<input type="hidden" id="roleId" value="${role.roleId}">
	<input type="hidden" id="oldroleFlag" value="${roleFlag}">
	<input type="hidden" id="system" value="${roleCode.code}">
	<div style="display:none;">
		被勾选时：<input type="checkbox" id="py" class="checkbox first" checked /><span>关联父</span>
		<input type="checkbox" id="sy" class="checkbox first" /><span>关联子</span><br />
		取消勾选时：<input type="checkbox" id="pn" class="checkbox first" checked /><span>关联父</span>
		<input type="checkbox" id="sn" class="checkbox first" /><span>关联子</span><br />
	</div>
	<div>
		<table cellpadding="5" cellspacing="5">
			<tr>
				<td style="height:30px;padding-top:5px;">角色名称： <input type="text" id="roleName" name="roleName"  value="${role.roleName}" maxlength="30" onblur="checkRoleName()"/><font
					color="#FF0000">&nbsp;*</font>
				</td>
				<td style="height:26px;">所属系统： 
			        <input type="text" value="${roleCode.roleName}" disabled="disabled">
			        <font color="#FF0000">&nbsp;*</font>
				</td>
				<td style="height:30px;">角色标识： 
				   <select id="roleflag">
				        <c:forEach items="${codeRoleList}" var="list">
						   <option value="${list.valuees}">${list.name}</option>		  
			           </c:forEach>
			        </select>
			        <font color="#FF0000">&nbsp;*</font>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<div
						style=" border:#bed5f3 1px solid; width:820px; height:220px; overflow:auto;">
						<ul id="treeDemo" class="ztree"></ul>
					</div></td>
			</tr>
			<tr>
				<td align="center"  style="padding-top:15px;" colspan="3">
				   <shiro:hasPermission name='role:upd'><input type="button" value="提交" onclick="updrole()" /></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp; <input type="button"
					value="取消" onClick="frameElement.dialog.close();" /></td>
			</tr>
		</table>
	</div>
</BODY>
<SCRIPT type="text/javascript">
		var IDMark_A = "_a";
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			view: {
				addDiyDom: addDiyDom
			},
			callback: {
				beforeCheck:zTreeBeforeCheck,
				onCheck: zTreeOnCheck
			}
		};
		
		var code;
		var zNodes ="";
		function setCheck() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			py = $("#py").attr("checked")? "p":"",
			sy = $("#sy").attr("checked")? "s":"",
			pn = $("#pn").attr("checked")? "p":"",
			sn = $("#sn").attr("checked")? "s":"",
			type = { "Y":"ps", "N":"ps"};
			zTree.setting.check.chkboxType = type;
			showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
		}
		function showCode(str) {
			if (!code) code = $("#code");
			code.empty();
			code.append("<li>"+str+"</li>");
		}
		
		$(document).ready(function(){
			init(); //加载下拉框数据
			$("#roleflag").val($("#oldroleFlag").val());
			$("#system").val($("#system").val());
		});
		
		//初始化
		function init(){
		    loadData();
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			setCheck();
			$("#py").bind("change", setCheck);
			$("#sy").bind("change", setCheck);
			$("#pn").bind("change", setCheck);
			$("#sn").bind("change", setCheck);
			initCheck();
		}
		
		
		//加载数据
		function loadData(){
		        var roleId=$("#roleId").val();
				$.ajax({  
					type:"post",
					url:"<%=path%>/controller/role/roleTree.do",
					data:{roleId:roleId,system:$("#system").val()},
					dataType:"json",
					async:false,
		          	success:function(result){		
		         		 zNodes=result.fList;     	
					},
					 error:function(e){
		       			 $.ligerDialog.error('获取树异常');
		       		}	
				});
		}
		
		function addDiyDom(treeId, treeNode) {
			var aObj = $("#" + treeNode.tId + IDMark_A);
			if (treeNode.btnItems!=null) {
				var editStr="&nbsp;&nbsp;&nbsp;&nbsp;";
				var btn_id="";
				var btn_name="";
				for(var i in treeNode.btnItems){
					btn_name="btn_"+treeNode.id;
					btn_id="btn_"+treeNode.id+"_"+i;
					if(treeNode.btnItems[i].check){
						editStr+="<input style='vertical-align:middle;'   id='"+btn_id+"' value='"+treeNode.btnItems[i].id+"' name='"+btn_name+"' type='checkbox' checked='checked' />&nbsp;<label style='cursor:pointer;' for='"+btn_id+"'>"+treeNode.btnItems[i].btn_name+"</label>&nbsp;&nbsp;";
					}else{
						editStr+="<input style='vertical-align:middle;'   id='"+btn_id+"' value='"+treeNode.btnItems[i].id+"' name='"+btn_name+"' type='checkbox' />&nbsp;<label style='cursor:pointer;' for='"+btn_id+"'>"+treeNode.btnItems[i].btn_name+"</label>&nbsp;&nbsp;";
					}
					
				}
				aObj.after(editStr);
			}
		}
		function initCheck(){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			for(var i in zTree.getNodes()){
				if(zTree.getNodes()[i].isParent){
					child_nodes_init(zTree.getNodes()[i]);
				}else{
					if(zTree.getNodes()[i].btnItems!=null){
						child_nodes_btn_init(zTree.getNodes()[i]);
					}
				}
			}
		}
		function child_nodes_init(nodeObj){
			var j = 0;
			for(j in nodeObj.children){
				if(nodeObj.children[j].isParent){
					child_nodes_init(nodeObj.children[j]);
				}else{
					if(nodeObj.children[j].btnItems!=null){
						child_nodes_btn_init(nodeObj.children[j]);
					}
				}
			}
		}
		
		
		function zTreeOnCheck(event, treeId, treeNode) {
		    if(treeNode.isParent){
		    	if(treeNode.checked){
		    		var j = 0;
					for(j in treeNode.children){
						var checkboxItems = document.getElementsByName("btn_"+treeNode.children[j].id);
						if(checkboxItems!=null){
							for(var ii =0;ii<checkboxItems.length;ii++){
								checkboxItems[ii].disabled="";
								checkboxItems[ii].checked="checked";
							}
						}
					}
		    	}else{
		    		var j = 0;
					for(j in treeNode.children){
						var checkboxItems = document.getElementsByName("btn_"+treeNode.children[j].id);
						if(checkboxItems!=null){
							for(var ii =0;ii<checkboxItems.length;ii++){
								checkboxItems[ii].disabled="disabled";
								checkboxItems[ii].checked="";
							}
						}
					}
		    	}
		    	
		    }
		}
		
		
		function child_nodes_btn_init(nodeObj1){
			if(!nodeObj1.checked){
				var checkboxItems = document.getElementsByName("btn_"+nodeObj1.id);
				if(checkboxItems!=null){
					for(var ii =0;ii<checkboxItems.length;ii++){
						checkboxItems[ii].disabled="disabled";
					}
				}
			}
		}
		function zTreeBeforeCheck(treeId, treeNode) {
			var disabled="disabled";
			var checked=false;
			if(!treeNode.checked){
				disabled="";
				checked=true;
			}
			var checkboxItems = document.getElementsByName("btn_"+treeNode.id);
			if(checkboxItems!=null){
				for(var ii =0;ii<checkboxItems.length;ii++){
					checkboxItems[ii].disabled=disabled;
					checkboxItems[ii].checked=checked;
				}
			}
			return true;
		}
		
			function updrole(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var btn_name="";
				var treenode=null;
				for( ii=0 ;ii<zTree.getNodes().length;ii++){
					treenode=zTree.getNodes()[ii].children;
					for (  i=0; i<treenode.length; i++) {
						var aa = document.getElementsByName("btn_"+treenode[i].id);
			         	for (var j = 0; j < aa.length; j++) {
			         		if (aa[j].checked) {
			                    btn_name += aa[j].value+",";
			                }
			            }
			        
					}
				}
			var nodes=zTree.getCheckedNodes(true);
			 var nod ="";
			 for(i = 0; i < nodes.length; i++) {
	          nod =nod+ nodes[i].id+",";
	     	}	
			var treedata=nod+btn_name;
			var roleId=$("#roleId").val();
			var rolename=$("#roleName").val();
			var roleflag=$("#roleflag").val();
			if(roleflag==null || roleflag==""){
			    $.ligerDialog.warn('请选择角色标识!');
			    return false;
		    }	
			if(rolename==null||rolename==""){
				$.ligerDialog.warn('角色名称不能为空!');
				return false;
			}
			$.ajax({
				type:"post",
				url:"<%=path%>/controller/role/updateRole.do",
				data : {
					rolename : rolename,
					roleflag : roleflag,
					treedata : treedata,
					roleId : roleId
				},
				dataType : "json",
				success : function(result) {
					if (result.blag>0) {
						$.ligerDialog.success("角色修改成功", '提示', function(yes) {
							window.parent.f_search();
							frameElement.dialog.close();
						});
				   } 
				},
				error : function(e) {
					$.ligerDialog.error('修改异常');
				}

		});
	}

		//验证角色名是否已存在
		function checkRoleName(){
		   var rolename=$("#roleName").val();
		   $.ajax({
			type:"post",
			url:"<%=path%>/controller/role/checkRoleName.do",
			data : {
				roleName : rolename,
				type : "update",
				roleId:$("#roleId").val()
			},
			dataType : "json",
			success : function(result) {
				if(result.count>0){
				    $.ligerDialog.warn('该角色名称已存在');
				    $("#roleName").val("");
				}
			},
			error : function(e) {
				$.ligerDialog.warn('验证角色名称异常');
			}

		});
		}
</SCRIPT>
</HTML>