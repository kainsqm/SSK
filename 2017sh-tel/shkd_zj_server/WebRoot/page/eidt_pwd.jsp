<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客调项目管理系统</title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-dialog.css"
	rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css"
	rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js"
	type="text/javascript"></script>
<script src="<%=path%>/js/utils.js" type="text/javascript"></script>
<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
<style type="text/css">
body {
	font-size: 13px;
}

input[type='button'] {
	border: #d3d3d3 1px solid;
	width: 80px;
	height: 25px;
	cursor: pointer;
}
</style>
<script type="text/javascript">
 			function subuser(){
 						var passwd=$("#passwd").val();
						var newpasswd=$("#newpasswd").val();
						var checknewpasswd=$("#checknewpasswd").val();
						if(isempty(passwd)){
				    		$.ligerDialog.warn('原密码不能为空');
				    		return false;
				    	} 
						if(isempty(newpasswd)){
				    		$.ligerDialog.warn('新密码不能为空');
				    		return false;
				    	} 
						if(isempty(checknewpasswd)){
				    		$.ligerDialog.warn('确认密码不能为空');
				    		return false;
				    	} 
				    	if(passwd==newpasswd){
				    	    $.ligerDialog.warn('新密码与原密码不能一样');
				    	    $("#newpasswd").val("");
						    $("#checknewpasswd").val("");
				    		return false;
				    	}
				    	
						if(newpasswd!=checknewpasswd){
						$.ligerDialog.warn('新密码与确认密码输入不一致');
						return false;
						}
						
						$.ligerDialog.confirm('您确认修改吗？', function (yes) { 
				if(yes){		
						$.ajax({
								url:"<%=path%>/controller/user/updpasswd.do",
					type : "post",
					data : {
						passwd : passwd,
						newpasswd : newpasswd,
					},
					dataType : "json",
					success : function(result) {
						if(result.flag=='原密码输入错误，请重新输入'){
							$.ligerDialog.warn(result.flag, '提示', function(yes) {
								//frameElement.dialog.close();
								$("#passwd").focus();
							});
							
						}else if(result.flag=='修改成功'){
							$.ligerDialog.success(result.flag, '提示', function(yes) {
								frameElement.dialog.close();
							});
							
						}else if('修改失败'){
							$.ligerDialog.error(result.flag, '提示', function(yes) {
								frameElement.dialog.close();
							});
							
						}else{
							$.ligerDialog.error('系统错误，请稍后再试', '提示', function(yes) {
								frameElement.dialog.close();
							});
							
						}
					},
					error : function(result, e) {
						$.ligerDialog.error('修改失败');
					}

				});
			}
		});

	}
	function input_shuziyinwen(obj) {
		obj.value = obj.value.replace(/[\W]/g, '');
	}
</script>

</head>

<body>
	<div align="center" style="height: auto;">
		<form action="" method="post" id="userinfo">
			<table cellpadding="5" cellpadding="5" width="100%" border="0">
				<tr>
					<td align="right" width="60">原密码：</td>
					<td align="left"><input maxlength="50" type="password"
						id="passwd" name="passwd" /></td>
				</tr>
				<tr>
					<td align="right">新密码：</td>
					<td align="left"><input type="password" maxlength="50"
						id="newpasswd" name="newpasswd" onkeyup="input_shuziyinwen(this);"/></td>
				</tr>
				<tr>
					<td align="right" width="70">确认密码：</td>
					<td align="left"><input type="password" maxlength="50"
						id="checknewpasswd" name="checknewpasswd" onkeyup="input_shuziyinwen(this);"/></td>
				</tr>
				<tr>
					<td colspan="2" align="center"
						style="padding-top: 15px; padding-bottom: 15px;"><input
						type="button" value="提交" onclick="subuser()" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="取消"
						onclick="frameElement.dialog.close();" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
