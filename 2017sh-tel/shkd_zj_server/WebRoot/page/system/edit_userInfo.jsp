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
	<title>客调项目管理系统</title>
	<script type="text/javascript" src="<%=path%>/js/ztree/js/jquery-1.4.4.min.js"></script>
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
	<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="<%=path%>/js/ztree/css/demo.css" type="text/css"/>
	<link rel="stylesheet" href="<%=path%>/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
	<script type="text/javascript" src="<%=path%>/js/ztree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=path%>/js/ztree/js/jquery.ztree.excheck-3.5.js"></script>
	<script src="<%=path%>/js/util.js" type="text/javascript"></script>
    <style type="text/css">
		body{ font-size:13px;}
		.wid{width:155px}
		input[type="text"]{
		margin:2px 0}
	</style>
	<script type="text/javascript">
 			function subuser(){
 			var flag=true;
						var userName=$("#userName").val();
						var workId=$("#workId").val();
						var groupName=$("#groupName").val();
						var job=$("#job").val();
						var zTree = $.fn.zTree.getZTreeObj("treeDemo");  
						var theirCenter=$("#theirCenter").val();
		                var nodes=zTree.getCheckedNodes(true);  
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
                       
						var passwd=$("#passwd").val();
						if(isempty(userName)){
						$.ligerDialog.warn('姓名不能为空');
							return false;
						}
						 if(isempty(workId)){
						$.ligerDialog.warn('工号不能为空');
							return false;
						}
						if(isempty(passwd)){
						$.ligerDialog.warn('密码不能为空');
							return false;
						}
						if(isempty(groupName)){
						$.ligerDialog.warn('组室不能为空');
							return false;
						}
						if(isempty(job)){
						$.ligerDialog.warn('岗位不能为空');
							return false;
						}
                        if(theirCenter=="0"){
                           $.ligerDialog.warn('请选择所属中心');
		                    return false;  
                        }
						 if(result==''){
                           $.ligerDialog.warn('请选择角色！！');
		                    return false;  
                        }else{
                            result=result.substring(0,result.length-1);
                        }
						$.ligerDialog.confirm('您确认新增吗？','提示', function (yes) { 
				    if(yes){	
						$.ajax({
								url:"<%=path%>/controller/user/insertUserInfo.do",
								type:"post",
								data:{
										userName:userName,
										workId:workId,
										passwd:passwd,
										groupName:groupName,
										job:job,
										role_id:result,
										theirCenter:theirCenter
										},
								 dataType:"json",
								 success:function(result){
								     if(result.flag=='工号重复'){
								        $.ligerDialog.warn('工号重复');
								     }else{
										 $.ligerDialog.success(result.flag,"提示",function(yes){
												 window.parent.user_search();
												 frameElement.dialog.close();	
										 });
									 }
								 },
								error:function(result,e){
									 $.ligerDialog.error('新增失败');
								}
						});
						}
						});			
					}
				 function input_shuziyinwen(obj){
				    obj.value=obj.value.replace(/[\W]/g,'');
			     }	
			
			var setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "level",
				ocheckInherit: true
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
				url:'<%=path%>/controller/role/queryRoleAdd.do',
				type:'post',
				//data:{userId:$("#userID").val()},
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
		</script>

</head>

<body> 
	<div align="center" style="height:auto;">
	<form action="/user/insertUserInfo.do" method="post" id="userinfo" >
		<table cellpadding="5" cellpadding="5" width="100%"  border="0">
			<tr>
				<td align="right"  width="60">姓　　名：</td><td align="left"><input type="text" onkeyup="noSpecial(this)" id="userName" name="userName"/><span style="color: red">*</span></td>
	
			</tr>
			<tr>
				<td align="right"  width="60">大 &nbsp;工 &nbsp;号：</td><td align="left"><input type="text" onkeyup="input_shuziyinwen(this);" id="workId" name="workId" /><span style="color: red">*</span></td>
			</tr>
			<tr>
				<td align="right"  width="60">密　　码：</td><td align="left"><input type="text" type="password"  value="123456" id="passwd" name="passwd"/><span style="color: red">*</span></td>
			</tr>
			<tr>
				<td align="right"  width="60">组　　室：</td><td align="left" ><input type="text" id="groupName" name="groupName" /><span style="color: red">*</span></td>
			</tr>
			<tr>
				<td align="right"  width="60">岗　　位：</td><td align="left"><input type="text" id="job"  name="job" /><span style="color: red">*</span></td>
			</tr>
			<tr>
				<td align="right"  width="80">所属中心：</td><td align="left">
				   <select id="theirCenter" style="width:150px;">
				        <option value="0">--请选择--</option>
				        <option value="浦东">浦东</option>
				        <option value="新华">新华</option>
				        <option value="逸仙">逸仙</option>
				   </select><span style="color: red">*</span></td>
			</tr>
			<tr>
				<td align="right"  width="60">角　　色：</td>
				<td align="left"><!-- <input id="role_id"  name="role_id" /></td> -->
				    <div style="width:200px; height:200px;float:left; border:1px solid #ccc; overflow:auto;  ">
					  <ul id="treeDemo" class="ztree">
				     </div>&nbsp;&nbsp;<span  style="color: red">*</span>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center" style="padding-top:7px; padding-bottom:15px;">
					<shiro:hasPermission name='user:add'><input type="button" value="提交" onclick="subuser()" /></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="取消" onclick="frameElement.dialog.close();" />
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
