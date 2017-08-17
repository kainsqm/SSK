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
<link rel="stylesheet" href="<%=path%>/js/ztree/css/demo.css"
	type="text/css">
<link rel="stylesheet"
	href="<%=path%>/js/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=path%>/js/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js"
	type="text/javascript"></script>
<script src="<%=path%>/js/util.js" type="text/javascript"></script>
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

		var zNodes ="";
		
		
		
		var code;
		
		function setCheck() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			py = $("#py").attr("checked")? "ps":"",
			sy = $("#sy").attr("checked")? "ps":"",
			pn = $("#pn").attr("checked")? "ps":"",
			sn = $("#sn").attr("checked")? "ps":"",
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
		    init();
			changeSystem(); //加载下拉框数据
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
		  var system=$("#system").val();
		  $.ajax({
			type:"post",
			url:"<%=path%>/controller/role/getRoletree.do",
			data:{system:system},
			dataType:"json",
			async:false,
          	success:function(result){		
         		 zNodes=result.fList;  
         		    	
			},
			 error:function(e){
       			 $.ligerDialog.warn('获取树异常');
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
						editStr+="<input style='vertical-align:middle;'  id='"+btn_id+"' value='"+treeNode.btnItems[i].id+"'  name='"+btn_name+"' type='checkbox' checked='checked' />&nbsp;<label style='cursor:pointer;' for='"+btn_id+"'>"+treeNode.btnItems[i].btn_name+"</label>&nbsp;&nbsp;";
					}else{
						editStr+="<input style='vertical-align:middle;' id='"+btn_id+"' value='"+treeNode.btnItems[i].id+"'  name='"+btn_name+"' type='checkbox' />&nbsp;<label style='cursor:pointer;' for='"+btn_id+"'>"+treeNode.btnItems[i].btn_name+"</label>&nbsp;&nbsp;";
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
		
		
		function saverole(){
		var rolename=$("#roleName").val();
		var system=$("#system").val();
		var roleflag=$("#roleflag").val();
		if(rolename==null || rolename==""){
		    $.ligerDialog.warn('角色名称不能为空!');
		    return false;
		}
		if(roleflag==null || roleflag==""){
		    $.ligerDialog.warn('请选择角色标识!');
		    return false;
		}
		
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var btn_name="";
		//var treenode=zTree.getNodes()[0].children;
	//	for (  i=0; i<treenode.length; i++) {
			//var aa = document.getElementsByName("btn_"+treenode[i].id);
		 var aa=$("input[name^='btn_']");  //查找所有name以btn_开头的元素
         for (var j = 0; j < aa.length; j++) {
                    if (aa[j].checked) {
                        btn_name += aa[j].value+",";
                    }
           }
			
		//} 
		/*var treenode2=zTree.getNodes()[1].children;
		for (  i=0; i<treenode2.length; i++) {
		var aa = document.getElementsByName("btn_"+treenode2[i].id);
         for (var j = 0; j < aa.length; j++) {
                    if (aa[j].checked) {
                        btn_name += aa[j].value+",";
                    }
                }
			
		} */
		var nodes=zTree.getCheckedNodes(true);
		 var nod ="";
		 for(a = 0; a < nodes.length; a++) {
          nod =nod+ nodes[a].id+",";
     	}
		var treedata=nod+btn_name;
		
		$.ajax({
			type:"post",
			url:"<%=path%>/controller/role/saveRole.do",
			data : {
				rolename : rolename,
				system : system,
				roleflag:roleflag,
				treedata : treedata
			},
			dataType : "json",
			success : function(result) {
				if (result.blag>0) {
					$.ligerDialog.success("角色新增成功", '提示', function(yes) {
						window.parent.f_search();
						frameElement.dialog.close();
					});
				} 
			},
			error : function(e) {
				$.ligerDialog.error('新增异常');
			}

		});

	}
//-->
  
        //系统跟角色下拉框数据加载
		function changeSystem(){
		    init();  //加载对应系统的菜单项
		    var system=$("#system").val();
		    if(system==""){
		       $("#roleflag").empty();
			   $("#roleflag").append("<option value=''>--请选择--</option>");
		    }else{
		        $.ajax({
            		url:'<%=path%>/controller/role/queryRoleByFlag.do',
					data:{system:system},
					type:'post',
					dataType:'json',
					success:function(data){
						$("#roleflag").empty();
						$("#roleflag").append("<option value=''>--请选择--</option>");
						$.each(data.rolelist,function(index,value){
						    $("#roleflag").append("<option value='"+value.valuees+"'>"+value.name+"</option>");
						});		
					},
               		error:function(){
					    $.ligerDialog.error('加载数据异常，请稍后再试！');
					}
            });	
		    } 
		    
		}
		
		
		//验证角色名是否已存在
		function checkRoleName(){
		   var rolename=$("#roleName").val();
		   $.ajax({
			type:"post",
			url:"<%=path%>/controller/role/checkRoleName.do",
			data : {
				roleName : rolename,
				type : "add"
			},
			dataType : "json",
			success : function(result) {
				if (result.count > 0) {
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
	<div style="display:none;">
		被勾选时：<input type="checkbox" id="py" class="checkbox first" checked /><span>关联父</span>
		<input type="checkbox" id="sy" class="checkbox first" checked/><span>关联子</span><br />
		取消勾选时：<input type="checkbox" id="pn" class="checkbox first" checked /><span>关联父</span>
		<input type="checkbox" id="sn" class="checkbox first" checked/><span>关联子</span><br />
	</div>
	<div>
		<table cellpadding="5" cellspacing="5">
			<tr>
				<td style="height:30px;padding-top:5px;" width="240">角色名称： <input
					type="text" id="roleName" name="roleName" maxlength="30"
					onblur="checkRoleName()" /><font color="#FF0000">&nbsp;*</font></td>
				<td style="height:26px;" width="240">所属系统： <select
					onchange="changeSystem()" id="system">
						<c:forEach items="${codeList}" var="list">
							<option value="${list.code}">${list.roleName}</option>
						</c:forEach>
				</select> <font color="#FF0000">&nbsp;*</font></td>
				<td style="height:30px;">角色标识： <select id="roleflag">
				</select> <font color="#FF0000">&nbsp;*</font></td>
			</tr>
			<tr>
				<td colspan="3">
					<div
						style=" border:#bed5f3 1px solid; width:820px; height:220px; overflow:auto;">
						<ul id="treeDemo" class="ztree"></ul>
					</div>
				</td>
			</tr>
			<tr>
				<td align="center" style="padding-top:15px;" colspan="3">
				  <shiro:hasPermission name='role:add'><input type="button" value="提交" onclick="saverole()" /></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="取消"
					onClick="frameElement.dialog.close();" /></td>
			</tr>
		</table>
	</div>
</BODY>
</HTML>