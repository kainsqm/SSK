<%@ page language="java" contentType="text/html;charset=UTF-8"%>  
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script> 
		<script src="<%=path%>/js/ajaxfileupload.js" type="text/javascript"></script> 
		<script src="<%=path%>/js/ajaxSession.js" type="text/javascript"></script>
		 <script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
		<script src="<%=path%>/page/examination/js/exam_manage.js" type="text/javascript"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="../../css/public.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body{ font-size:14px;}
		/*input{ vertical-align:middle; margin:0; padding:0}*/
		.file-box{ position:relative;width:540px}
		.txt{ height:22px; border:1px solid #cdcdcd; width:380px; line-height:22px;}
		.btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
		.file{ position:absolute; top:0; right:80px; height:24px; filter:alpha(opacity:0);opacity: 0;width:460px }
		
		div{ border:#000099 0px solid;}
		div.message—div{ width:530px; height:120px; margin-top:5px; padding:5px;}
	</style>
	<script>
	$(function(){
		 getSysCodestype("SYSPAPERTYPE","examPaperType");     //获取考试类型
	});
	
			function importf(){
				//清空上次上传的异常信息
				var examPaperName=$("#examPaperName").val();
				var examPaperRemark=$("#examPaperRemark").val();
				var examPaperType=$("#examPaperType").val();
				var isIndex=$("#isIndex").val();
				if(examPaperName==''){
					$.ligerDialog.warn('试卷名称不能为空');
					return;
				}
				if(examPaperRemark==''){
					$.ligerDialog.warn('试卷备注不能为空');
					return;
				}
				if(examPaperType==''||examPaperType=="0"){
					$.ligerDialog.warn('试卷类型不能为空');
					return;
				}
				if(isIndex==''){
					$.ligerDialog.warn('是否乱序不能为空')
					return;
				}
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
		var examPaperName=$("#examPaperName").val();
		var examPaperRemark=$("#examPaperRemark").val();
		var examPaperType=$("#examPaperType").val();
		var isIndex=$("#isIndex").val();
		var filePath = "<%=basePath%>" + "page/imptemp/";
		  $.ajaxFileUpload({
		            url: '<%=path%>/controller/examManager/uploadFile.do', 
		            type: 'post',
		            dataType:"json",
		            secureuri: false, //一般设置为false
		            fileElementId: "myFile", // 上传文件的id、name属性名
		            data:{'examPaperName':examPaperName,'examPaperRemark':examPaperRemark,'examPaperType':examPaperType,'isIndex':isIndex},
		            success: function(result, status){
		            	if(result != null && result != ""){
		            		var errorRowStr = result.errorRowStr;	
			            	if(result.errorRowStr != null && result.errorRowStr != ""){			
			                	for ( var i = 0; i < errorRowStr.length; i++) {
									$("#er").append(errorRowStr[i]+"<br/>");
								}
			            	}
		               		if(result.errorCnt>0){
		               		$.ligerDialog.warn('导入失败','提示',function(yes){
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
	<div class="file-box">
	  <form action="" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<td colspan="4"><input type='text' name='textfield'
						id='textfield' class='txt'style="width: 370px !important;margin-right: 10px" /> <input type='button' class='btn'
						value='浏览...' /> <input type="file" name="myFile" class="file"
						id="myFile" size="28"
						onchange="document.getElementById('textfield').value=this.value" />
						<input type="button" name="submit" onclick="importf()" class="btn"
						value="上传" />&nbsp;</td>
				</tr>
				<tr style="margin-top: 10px;">
					<td>试卷名称<span style="color: red">*</span></td>
					<td><input type="text" id="examPaperName" name="examPaperName" /></td>
					<td>试卷备注<span style="color: red">*</span></td>
					<td><input type="text" id="examPaperRemark"
						name="examPaperRemark" /></td>
				</tr>
				<tr>
					<td>试卷类型<span style="color: red">*</span></td>
					<td>
						<select id="examPaperType" name="examPaperType">
							<option value="0">--请选择--</option>
						</select>
					</td>
					<td>是否乱序<span style="color: red">*</span></td>
					<td>
						<input type="radio" id="isIndex" name="isIndex" value="1" checked="checked" />是
						<input type="radio" id="isIndex" name="isIndex" value="0" />否
					</td>																
				</tr>
			</table>
		</form>
	</div>
	<div class="message—div">
		<div>导入结果：&nbsp;&nbsp;&nbsp;&nbsp;总数<span id="zs"></span>条&nbsp;&nbsp;&nbsp;&nbsp;成功数<span id="zq"></span>条&nbsp;&nbsp;&nbsp;&nbsp;失败数<span id="cw"></span>条</div>
		<div id="er" style="margin-top:5px; height:90px; overflow:auto; border:#CCCCCC 1px solid; color:#FF0000;">
			
		</div>
	</div>
</body>
</html>
