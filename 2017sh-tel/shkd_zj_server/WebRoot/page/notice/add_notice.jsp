<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css"
	rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.metadata.js"></script>
<script src="<%=path%>/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script src="<%=path%>/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script src="<%=path%>/js/common.js"></script>
<script src="<%=path%>/js/util.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
body {
	font-size: 13px;
}
</style>
<script type="text/javascript"> 
	
	var contentAddEditor;
	$(function() {
	// $("#form1 input:radio").ligerRadio();
		//$("#form").ligerForm({});
		contentAddEditor = TT.createEditor("#table [name=content]");
	});
	
	function mysubmit(type){
	   var status="";
	   if(type=="0"){
	      status="已保存";
	   }else{
	      status="已发布";
	   }
	   var title=$("#title").val();
	   contentAddEditor.sync();
	   var content=$("#content").val();
	   var releaseDate=$("#releaseDate").val();
	   if(isempty(title)){
			$.ligerDialog.warn('公告标题不能为空');
			return false;
		}
		 if(isempty(content)){
			$.ligerDialog.warn('公告内容不能为空');
			return false;
		}
		 if(isempty(releaseDate)){
			$.ligerDialog.warn('发布时间不能为空');
			return false;
		}
	
	   $.ligerDialog.confirm('您确认新增吗？','提示', function (yes) { 
				    if(yes){	
						$.ajax({
								url:"<%=path%>/controller/notice/insertNotice.do",
								type:"post",
								data:{
										title:title,
										content:content,
										releaseDate:releaseDate,
										status:status
										},
								 dataType:"json",
								 success:function(result){
									 $.ligerDialog.success('提交成功!','提示',function(yes){
								         window.parent.f_search();
										 frameElement.dialog.close();
						          	});
								 },
								error:function(result,e){
									 $.ligerDialog.error('新增失败');
								}
						});
				}
		});
	}
	
	</script>
</head>
<body>
	<div align="center" style="height:auto;" id="table">
		<table cellpadding="5" cellpadding="5" width="100%" border="0"
			style="border-spacing:0px 10px;">
			<tr style="height:40px;">
				<td align="right" style="width:80px;">公告标题：</td>
				<td align="left"><input type="text" id="title"/><font color="#FF0000">&nbsp;*</font>
				</td>
			</tr>
			<tr>
				<td align="right">公告内容：</td>
				<td><textarea id="content" name="content" class="ui-textarea"
						style="resize: auto;width: 250px;height: 100px;"
						validate="{required:true}"></textarea><span style="color: red">*</span>
				</td>
			</tr>
			<tr>
				<td align="right">发布时间：</td>
				<td align="left"><input class="Wdate date_text" type="text" id="releaseDate"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})" /><span style="color: red">*</span>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center"
					style="padding-top:15px; padding-bottom:15px;">
					<shiro:hasPermission name='notice:add'><input type="button" value="保存" onclick="mysubmit(0)"/></shiro:hasPermission>&nbsp;&nbsp; <shiro:hasPermission name='notice:add'><input type="button"
					value="发布" onclick="mysubmit(1)"/></shiro:hasPermission>&nbsp;&nbsp; <input type="button" value="取消"
					onclick="frameElement.dialog.close();" /></td>
			</tr>
		</table>
	</div>
</body>
</html>
