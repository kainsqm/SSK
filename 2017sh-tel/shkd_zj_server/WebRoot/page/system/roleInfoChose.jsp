<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=path%>/js/ztree/css/demo.css"
	type="text/css" />
<link rel="stylesheet"
	href="<%=path%>/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=path%>/js/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js"
	type="text/javascript"></script>


<script type="text/javascript">		var setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "level"
			},
			data: {
				simpleData: {
					enable: true
				}
			}

		};

		var code;		
		function setCheck() {
		     var zNodes;
		    //获取角色信息列表
		     $.ajax({
				url:'<%=path%>/controller/role/queryRole.do',
				type:'post',
				data:{userId:$("#userID").val()},
				error: function(request) {
					$.ligerDialog.error('系统异常，请稍后再试！');
				},
				success: function(data) {
				   var datas=eval("("+data+")");
				   zNodes=datas.roleList;
				   	var type = $("#level").attr("checked")? "level":"all";
					setting.check.radioType = 'level';
					showCode('setting.check.radioType = "' + type + '";');
					$.fn.zTree.init($("#treeDemo"), setting, zNodes);
				}
			});
		
		}
		function showCode(str) {
			if (!code) code = $("#code");
			code.empty();
			code.append("<li>"+str+"</li>");
		}
		
		$(document).ready(function(){
			setCheck();			
		});
		//-->
		//提交
		function fprole(){
 		        var zTree = $.fn.zTree.getZTreeObj("treeDemo");  
                var nodes=zTree.getCheckedNodes(true);  
                var result='';  
                var result='';  
		        var bool=true;
		        for (var i = 0; i < nodes.length; i++) {  
		              var halfCheck = nodes[i].getCheckStatus();  
		              if(nodes[i].level==1){
		                  if (!halfCheck.half){  
		                      result += nodes[i].id +',';  
		                  } 
		               }
		         }  
                 if(result==''){
                       $.ligerDialog.warn('请选择角色！！');
		               return false;  
                 }
		 		var userId=$("#userID").val();
		 		$.ajax({
		 			url:"<%=path%>/controller/user/updRole.do",
					type : "post",
					data : {
						roleid : result,
						userId : userId
					},
					dataType : "json",
					success : function(result) {
						if (result.blag) {
							$.ligerDialog.success('分配成功', '提示', function(yes) {
								window.parent.user_search();
								frameElement.dialog.close();
							});
		
						} else {
							$.ligerDialog.error('分配失败');
						}
					},
					error : function(result, e) {
						$.ligerDialog.error('系统异常');
					}
				});

	}
</script>
</head>

<body>
	<div style="">
		<input type="hidden" id="userID" value="${userId}" />
		<div
			style="width:200px; height:300px; overflow:auto;">
			<ul id="treeDemo" class="ztree">
			</ul>
		</div>
		<div
			style="margin-left: 91px;width: 183px;">
			<shiro:hasPermission name='user:rolefp'><input type="button" value="提交" onclick="fprole()" /></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="取消"
				onclick="frameElement.dialog.close();" />&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
	</div>
</body>
</html>
