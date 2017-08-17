<%@ page language="java" contentType="text/html;charset=UTF-8"%> 
<%@ page language="java" import="java.util.*,java.net.URLDecoder" pageEncoding="UTF-8"%> 
<%
  String path = request.getContextPath();
String qc_id=URLDecoder.decode(URLDecoder.decode(request.getParameter("qc_id"),"UTF-8"), "utf-8"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>客调项目管理系统</title>
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
	<link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/css/public.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script> 
	<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="<%=path%>/js/utils.js" type="text/javascript"></script>
    <style type="text/css">
    	td.td_lable{ text-align:right;}
		td.td_value{ text-align:left;}
		body{ font-size:13px;}
		input[type='button']{ border:#d3d3d3 1px solid; width:80px; height:25px; cursor:pointer;}
	</style>
	<script type="text/javascript"> 
	
	function subsecqc(){
		
		var sIspass=$("#sIspassid").val()
		if(isempty(sIspass)){
    		$.ligerDialog.warn('请选择质检结果');
    		return false;
    	} 
		
		$.ligerDialog.confirm('确认提交吗？', function (yes){
			if(yes){
				$.ajax({
					url:'<%=path%>/controller/secqc/addSecQc.do',
					type:'post',
					data:$("#secqcid").serialize(),
					success:function(data){
						var res=eval("("+data+")");
						if(res.status=='1'){
							$.ligerDialog.success('质检成功','提示',function(yes){
				              $("#secid",window.parent.document).css('display','none');
							   frameElement.dialog.close();	
				            });
							
						}else{
							$.ligerDialog.error('系统异常，请稍后再试！');
						}
					},
					error:function(error){
						$.ligerDialog.error('系统异常，请稍后再试！');
					}
					
				});
			}
         });	
	}
	</script> 
</head>
<body> 
	<div align="center" style="height:auto;">
		<form id="secqcid">
		<input type="hidden" name="qc_id" value="<%=qc_id%>"/>
		<table cellpadding="5" cellpadding="5" width="100%"  border="0">
			<tr>
				<td class="td_lable" >质检结果：</td>
				<td class="td_value">
				<select name="sIspass" id="sIspassid">
								<option value="">---请选择---</option>
								<option value="1">合格</option>
								<option value="0">不合格</option>
				</select><span  style="color: red">*</span>		
				</td>
			</tr>
			<tr>
			<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="td_lable">质检意见：</td>
				<td class="td_value">
				<textarea rows="4"   name="sRemark" style="overflow:auto; width:205px"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center" style="padding-top:30px; padding-bottom:5px;">
					<input type="button" value="提交" onclick="subsecqc();"/>&nbsp;&nbsp;
					<input type="button" value="取消" onclick="frameElement.dialog.close();" />
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
