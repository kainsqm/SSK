<%@ page language="java" contentType="text/html;charset=UTF-8"%>  
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/js/jquery-1.9.1.js" type="text/javascript"></script> 
		<script src="<%=path%>/js/ajaxfileupload.js" type="text/javascript"></script> 
		<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
		 <script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<style type="text/css">
		body{ font-size:14px;}
		/*input{ vertical-align:middle; margin:0; padding:0}*/
		/*.file-box{ position:relative;width:540px}*/
		.txt{ height:22px; border:1px solid #cdcdcd; width:380px; line-height:22px;}
		.btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
		.file{ position:absolute; top:0; right:80px; height:24px; filter:alpha(opacity:0);opacity: 0;width:460px }
		
		div{ border:#000099 0px solid;}
		div.message—div{ width:530px; height:120px; margin-top:5px; padding:5px;}
		.btn{position:relative}
	</style>
	<script>
			function importf(){
				//清空上次上传的异常信息
				$("#er").html('');
				var myFile= $("#myFile").val();
		       		if(myFile !="" && myFile != null){
		       			var xls=/[^\.](\.xls)$/i;  
		       			if(xls.test(myFile)){
			       			importFile();
			       		}else{
			       			$.ligerDialog.warn('只支持xls格式的excel导入');
			       		} 
		       		}else{
		       			//alert("请选择文件");
		       			$.ligerDialog.warn('请选择文件');
		       		}
		}
		function importFile(){
		var filePath = "<%=basePath%>" + "page/imptemp/";
		  $.ajaxFileUpload({
		            url: '<%= path%>/controller/user/importUser.do', 
		            type: 'post',
		            dataType:"json",
		            secureuri: false, //一般设置为false
		            fileElementId: "myFile", // 上传文件的id、name属性名
		            success: function(result, status){
		            	if(result != null && result != ""){
		            		var errorRowStr = result.errorRowStr;	
			            	if(result.errorRowStr != null && result.errorRowStr != ""){			
			                	for ( var i = 0; i < errorRowStr.length; i++) {
									$("#er").append(errorRowStr[i]+"<br/>");
								}
			            	}
		               		if(result.successCnt<=0){
		               		$.ligerDialog.warn('导入异常','提示',function(yes){
		               		$("#zs").html(parseInt(result.errorCnt)+parseInt(result.successCnt));
			               	$("#cw").html(result.errorCnt);
			              	$("#zq").html(result.successCnt);
			              	});
			               	}else{
			               	$.ligerDialog.success('导入成功','提示',function(yes){
			               	$("#zs").html(parseInt(result.errorCnt)+parseInt(result.successCnt));
			               	$("#cw").html(result.errorCnt);
			              	$("#zq").html(result.successCnt);
			               	});      
			               	}	  
		            	}else{
		            		$.ligerDialog.error('导入失败！');
		            	}
		            },
		            error: function(data){ 
		            }
		        });
		
		}
	</script>
	
	
</head>
<body> 
	<div class="file-box" style="padding-top:5px;">
	  <form action="" method="post" enctype="multipart/form-data" style="padding:10px 0 0 10px ">
	 	<input type='text' style="width:180px;" name='textfield' id='textfield' class='txt' />  
	 	<input type='button' class='btn' value='浏览...' />
		<input type="file" name="myFile"  class="file" id="myFile" size="28" onchange="document.getElementById('textfield').value=this.value" />
	 	<shiro:hasPermission name='user:infoimport'><input type="button" name="submit" onclick="importf()" class="btn" value="上传" /></shiro:hasPermission>
	  </form>
	</div>
	<div class="message—div">
		<div>导入结果：&nbsp;&nbsp;&nbsp;&nbsp;总数<span id="zs"></span>条&nbsp;&nbsp;&nbsp;&nbsp;成功数<span id="zq"></span>条&nbsp;&nbsp;&nbsp;&nbsp;失败数<span id="cw"></span>条</div>
		<div id="er" style="margin-top:5px; height:90px; overflow:auto; border:#CCCCCC 1px solid; color:#FF0000;">
			
		</div>
	</div>
</body>
</html>
