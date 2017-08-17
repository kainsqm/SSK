<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt"  uri="/WEB-INF/fmt-rt.tld"%> 
<%@ taglib prefix="c"  uri="/WEB-INF/c.tld"%> 
<%@ taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<title>用户管理</title>
	<link href="${ctx}/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script> 
	<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="${ctx}/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
    <script src="${ctx}/js/ajaxSession.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(${ctx}/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input.date_text{border-radius:5px; width:135px; height:18px; line-height:18px;}
		table td{ height:30px; line-height:30px;}
		table .search_table td.td_lable{ text-align:right;}
		table .search_table td.td_value{ text-align:left;}
		a{text-decoration : none;color:blue;}
		red{color: red;}
		.reset{ 
		    background: -webkit-gradient(linear, 0% 0%, 0% 100%,from(#01aacf), to(#0165a2)) !important;
		    color: #FFFFFF !important;
		    border: 1px solid #0297bf !important;
		    padding: 0 15px;
		    border-radius: 3px;
		    width: initial !important;
		    height: 25px !important;
    	}
	</style>
	<script type="text/javascript"> 
	    var maingrid_gd ;
		$(function () {
			loadGroup();

			var showData = {Rows: [],Total: 0};
			loadData(showData);
			
			var combo2 = $("#roleCheck").ligerComboBox({
	             width: 155,
	             selectBoxWidth: 200,
	             selectBoxHeight: 200, valueField: 'id',treeLeafOnly:false,
	             tree: { url: "${ctx}/controller/sysuser/getRoles.do",
	            	 checkbox: false, ajaxType: 'post', idFieldName: 'id', parentIDFieldName:"pid",textFieldName:"text"
	            }
	        }); 
        });
		
		function loadGroup() {
			var url = "${ctx}/controller/sysuser/getGroups.do";
			$.ajax({
				url: url,
			  	type:"post",
			  	dataType:"json",
			  	data:{"1":"1"},
			  	success: function(data){
			  		for(var i=0;i<data.groups.length;i++) {
			  			document.getElementById("groupName").options.add(new Option(data.groups[i].groupName,data.groups[i].groupName));
			  		}
			  	}
			});
		}
		
		function loadData(resultData){
	  		maingrid_gd= $("#record_list").ligerGrid({
               columns: [                
				{ display: '工号', name: 'workId', align: 'left', minWidth :60,isSort:false},
				{ display: '姓名', name: 'userName', align: 'left', minWidth :60,isSort:false},
				{ display: '角色', name: 'roleName', align: 'left', minWidth :80,isSort:false},
				{ display: '组室', name: 'groupName', align: 'left', minWidth :60,isSort:false}
               ], 
               data:resultData, sortName: 'examId',height:'100%', pageSize:10 ,width:"auto",isScroll:true,rownumbers :true,checkbox:true,isAllowHide: false,
               onCheckRow:function(checked,data,rowid,rowdata){
               	window.parent.document.getElementById("userName").value = data.userName;
               	window.parent.document.getElementById("listUserID").value = data.userId;
               	frameElement.dialog.close(); 
       		}
            });
	  		$("#pageloading").hide();
		}
		function selectBtn(){
			if($("#roleCheck").val().length>0){
				$("#roleId").val($("#roleCheck_val").val());
			}else{
				$("#roleId").val('');
			}
			
			$("#pageloading").show();
			var url = "${ctx}/controller/sysuser/getUsers.do";
			 
			 $.ajax({
				url: url,
			  	type:"post",
			  	dataType:"json",
			  	data:$("#queryUser").serialize(),
			  	success: function(resultData){
			  		loadData(resultData);
			  	},
				error:function(e){
	       			$.ligerDialog.warn('获取数据异常，请稍候再试');
	       			$("#pageloading").hide();
	       	    }
			 }); 
		}
		
		 //回车查询
	    function keydown(){
	    	if(isEnter()){
	    		selectBtn();
	    	}
		}
    </script> 
</head>
<body> 
	<div onkeydown="keydown();">
		<div class="title_div" style="width:100%; margin-top:0px; display:none;"><label style="margin-left:15px;">查询条件</label></div>
		<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
		<form id="queryUser">
			<table class="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr>
					<td class="td_lable">工号：</td><td class="td_value"><input type="text" name="workId"/></td>
					<td class="td_lable">姓名：</td><td class="td_value"><input type="text" name="userName"/></td>
				</tr>
				<tr>
					<td class="td_lable">角色：</td>
					<td class="td_value">
						<input name="roleCheck" id="roleCheck" style="width: 135px;"/>
						<input name="roleId" id="roleId" style="display: none"/>
					</td>
					<td class="td_lable">组室：</td><td class="td_value">
						<select name="groupName" id="groupName" style="width: 135px">
			                <option value="">---请选择组室---</option>
			            </select> 
					</td>
				</tr>
				
				<tr>
					<td colspan="8" align="center">
						<input type="button" value="查询" onclick="selectBtn()"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="reset" class="reset" value="重置" id="resetbutton"/>
					</td>
				</tr>
			</table>	
		</form>			
		</div>
		
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="record_list" style="margin-top:10px;"></div>
	</div>
</body>
</html>
