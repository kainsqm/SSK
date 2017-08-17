﻿<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt"  uri="/WEB-INF/fmt-rt.tld"%> 
<%@ taglib prefix="c"  uri="/WEB-INF/c.tld"%> 
<%@ taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>查看考卷</title>
	<link href="${ctx}/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script> 
	<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/js/ajaxSession.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="../CustomersData.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input.date_text{border-radius:5px; width:135px; height:18px; line-height:18px;}
		table.search_table td.td_lable{ text-align:left;height:20px; }
		table.search_table td.td_right{ text-align:right;height:20px; padding-right: 10px;padding-left: 20px;}
		table.search_table td.td_answer{ text-align:left;padding-left: 27px;height:20px; }
		.bottom{padding-bottom: 14px;}
		a{text-decoration : none;color:blue;}
		textarea{resize:none;margin-top:3px}
		red{color: red}
		body{overflow: auto !important;}
	</style>
	<script type="text/javascript"> 
		var dialog = frameElement.dialog;
		var dialogData = dialog.get('data');//获取data参数
	</script> 
</head>

<body> 
	<div style="background-color:#EEEEEE;padding: 3px 15px 0 15px">
			<div align="left" id="message" style="margin-top:10px; margin-left:5px; padding-left:10px; ">
			<div style="padding:10px;font-size: 14px" class="bottom"> <!-- 头部 -->
				<p>试卷名称：<span id="exampaperName" style="color: blue">期末考试</span>
				</p>
				<p>试卷总分：<span id="exampaperName" style="color: blue">60分</span>
				</p>
				<p>考生工号： <span id="examTimeLen" style="color: blue">AKB208</span>
				</p>
				考生得分： <span id="examTimeLen" style="color: blue">20分</span>
			</div>
			
			<div> <!-- 判断题 -->
				<table class="search_table" cellpadding="3" cellspacing="3" width="100%" >
					<tr>
						<td class="td_lable">
							<b><label id="labelType1">模块：</label>判断题（总分：40分）</b>
						</td>					
					</tr>
					<tr>
						<td class="td_lable">
							考题1.CDMA用户通过11808拨打台港澳电话无需具有国际长途直拨功能（2分）
						</td>	
						<td class="td_right">
							2&nbsp;&nbsp;分
						</td>					
					</tr>
					<tr>
						<td class="td_answer">
							参考答案：Y
						</td>					
					</tr>
					<tr>
						<td class="td_answer bottom">
							考生答案：Y
						</td>					
					</tr>
					<tr>
						<td class="td_lable">
							考题2.积分归集是指经过手机登记的产权客户授权同意，将某个政企手机积分转移到某一私人客户积分账户上的操作。这一私人客户可以是手机的实际使用人，也可以是手机产权人指定的任一具有电信后付费资产的私人客户。（2分）
						</td>	
						<td class="td_right">
							2&nbsp;&nbsp;分
						</td>					
					</tr>
					<tr>
						<td class="td_answer">
							参考答案：Y
						</td>					
					</tr>
					<tr>
						<td class="td_answer bottom">
							考生答案：Y
						</td>					
					</tr>
					<tr>
						<td class="td_lable">
							考题3.“同名同址”如用户有两门宽带和一门C网语音设备，改光纤后，二门宽带都可以升速。（2分）
						</td>	
						<td class="td_right">
							2&nbsp;&nbsp;分
						</td>					
					</tr>
					<tr>
						<td class="td_answer">
							参考答案：Y
						</td>					
					</tr>
					<tr>
						<td class="td_answer bottom">
							考生答案：Y
						</td>					
					</tr>
					<tr>
						<td class="td_lable">
							考题4.直线宽带同时移机时，可以变更加载关系。（2分）
						</td>	
						<td class="td_right">
							2&nbsp;&nbsp;分
						</td>					
					</tr>
					<tr>
						<td class="td_answer">
							参考答案：Y
						</td>					
					</tr>
					<tr>
						<td class="td_answer bottom">
							考生答案：Y
						</td>					
					</tr>
				</table>
			</div>
		
		
			<div> <!-- 判断改错题 -->
				<table class="search_table" cellpadding="3" cellspacing="3" width="100%" >
					<tr>
						<td class="td_lable">
							<b><label id="labelType2">模块：</label>判断改错题</b>
						</td>					
					</tr>
					<tr>
						<td class="td_lable">
							考题1.CDMA用户通过11808拨打台港澳电话无需具有国际长途直拨功能（2分）
						</td>	
						<td class="td_right">
							2&nbsp;&nbsp;分
						</td>					
					</tr>
					<tr>
						<td class="td_answer">
							参考答案：Y
						</td>					
					</tr>
					<tr>
						<td class="td_answer">
							考生答案：N
						</td>					
					</tr>
					<tr>
						<td class="td_answer bottom">
							<span>xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</span>
						</td>					
					</tr>
				</table>
			</div>
		
			<div> <!-- 单选题 -->
				<table class="search_table" cellpadding="3" cellspacing="3" width="100%" >
					<tr>
						<td class="td_lable">
							<b><label id="labelType2">模块：</label>单选题</b>
						</td>					
					</tr>
					<tr>
						<td class="td_lable">
							考题1.CDMA用户通过11808拨打台港澳电话无需具有国际长途直拨功能（2分）
						</td>		
						<td class="td_right">
							2&nbsp;&nbsp;分
						</td>				
					</tr>
					<tr>
						<td class="td_answer">
							参考答案：A
						</td>					
					</tr>
					<tr>
						<td class="td_answer bottom">
							考生答案：B
						</td>					
					</tr>
				</table>
			</div>
		
			<div> <!-- 多选题-->
				<table class="search_table" cellpadding="3" cellspacing="3" width="100%" >
					<tr>
						<td class="td_lable">
							<b><label id="labelType2">模块：</label>多选题</b>
						</td>					
					</tr>
					<tr>
						<td class="td_lable">
							考题1.CDMA用户通过11808拨打台港澳电话无需具有国际长途直拨功能（2分）
						</td>			
						<td class="td_right">
							2&nbsp;&nbsp;分
						</td>			
					</tr>
					<tr>
						<td class="td_answer">
							参考答案：AB
						</td>					
					</tr>
					<tr>
						<td class="td_answer bottom">
							考生答案：AB
						</td>					
					</tr>
				</table>
			</div>
		
			<div sytle=""> <!-- 不定项选择-->
				<table class="search_table" cellpadding="3" cellspacing="3" width="100%" >
					<tr>
						<td class="td_lable">
							<b><label id="labelType2">模块：</label>不定项选择</b>
						</td>					
					</tr>
					<tr>
						<td class="td_lable">
							考题1.CDMA用户通过11808拨打台港澳电话无需具有国际长途直拨功能（2分）
						</td>		
						<td class="td_right">
							2&nbsp;&nbsp;分
						</td>				
					</tr>
					<tr>
						<td class="td_answer">
							参考答案：AB
						</td>					
					</tr>
					<tr>
						<td class="td_answer bottom">
							考生答案：AB
						</td>					
					</tr>
				</table>
			</div>
		
			<div sytle=""> <!-- 填空题 -->
				<table class="search_table" cellpadding="3" cellspacing="3" width="100%" >
					<tr>
						<td class="td_lable">
							<b><label id="labelType2">模块：</label>填空题<red>【____为填空部分】</red></b>
						</td>					
					</tr>
					<tr>
						<td class="td_lable">
							考题1.CDMA用户通过11808拨打台港澳电话无需具有国际___________________长途直拨功能（2分）
						</td>	
						<td class="td_right">
							3&nbsp;&nbsp;分
						</td>					
					</tr>
					<tr>
						<td class="td_answer">
							参考答案：xxxxxxxxxxxxxx
						</td>					
					</tr>
					<tr>
						<td class="td_answer bottom">
							考生答案：xxxxxxxx
						</td>					
					</tr>
				</table>
			</div>
		
			<div sytle=""> <!-- 问答题 -->
				<table class="search_table" cellpadding="3" cellspacing="3" width="100%" >
					<tr>
						<td class="td_lable">
							<b><label id="labelType2">模块：</label>问答题</b>
						</td>					
					</tr>
					<tr>
						<td class="td_lable">
							考题1.CDMA用户通过11808拨打台港澳电话无需具有国际长途直拨功能？（2分）
						</td>	
						<td class="td_right">
							3&nbsp;&nbsp;分
						</td>					
					</tr>
					<tr>
						<td class="td_answer">
							参考答案：xxxxxxxxxxxxxx
						</td>					
					</tr>
					<tr>
						<td class="td_answer bottom">
							考生答案：xxxxxxxx
						</td>					
					</tr>
				</table>
			</div>
		
			<div sytle=""> <!-- 案例分析题 -->
				<table class="search_table" cellpadding="3" cellspacing="3" width="100%" >
					<tr>
						<td class="td_lable">
							<b><label id="labelType2">模块：</label>案例分析题</b>
						</td>	
					</tr>
					<tr>
						<td class="td_lable">
							考题1.CDMA用户通过11808拨打台港澳电话无需具有国际长途直拨功能？（2分）
						</td>	
						<td class="td_right">
							3&nbsp;&nbsp;分
						</td>					
					</tr>
					<tr>
						<td class="td_answer">
							参考答案：xxxxxxxxxxxxxx
						</td>					
					</tr>
					<tr>
						<td class="td_answer bottom">
							考生答案：xxxxxxxx
						</td>					
					</tr>
					<tr>
					</tr>
					<tr style="padding-top: 15px;height: 80px">
						<td colspan="8" align="center">
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- <div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="record_list"></div> -->
	</div>
</body>
</html>
