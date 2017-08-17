<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/shiro.tld" prefix="shiro"%> 
<%
  String path = request.getContextPath();
 /*  Object roleflag=request.getSession().getAttribute("roleflag");
  String role=null;
  String userworkid=null;
  if(roleflag!=null && roleflag!=""){
    role=roleflag.toString();
    userworkid =request.getSession().getAttribute("userworkid").toString();
  } */
   String role=request.getAttribute("role_flag").toString();
   String userworkid=request.getAttribute("userworkid").toString();
   
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>客调项目管理系统</title>
		<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=path %>/css/public.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js"
			type="text/javascript"></script> 
		<script src="<%=path%>/lib/ligerUI/js/core/base.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js"
			type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js"
			type="text/javascript"></script>
		<script src="<%=path%>/js/utils.js" type="text/javascript"></script>
		<style type="text/css">
div.title_div {
	border-radius: 5px;
	text-align: left;
	margin-top: 5px;
	background-image: url(<%=path%>/img/login/title_bg.jpg);
	border: solid 1px #D7D7D7;
	width: 90%;
	height: 30px;
	line-height: 30px;
	vertical-align: middle;
	font-size: 14px;
	font-family: "黑体";
}

input.input_text {
	border-radius: 5px;
	width: 130px;
	height: 18px;
	line-height: 18px;
	padding-left: 3px;
}

input[type='button'] {
	border: #d3d3d3 1px solid;
	width: 80px;
	height: 25px;
	cursor: pointer;
}

input.date_text {
	border-radius: 5px;
	width: 140px;
	height: 18px;
	line-height: 18px;
}

table#search_table td {
	height: 30px;
	line-height: 30px;
}

table#search_table td.td_lable {
	text-align: right;
}

table#search_table td.td_value {
	text-align: left;
}
</style>
		<script type="text/javascript"> 
	 var maingrid_gd=null;	
	 var columns=null;
	 var roleflag="<%=role%>";
	  <%-- var roleflags="<%=roleflag%>";
	   if(roleflags==null || roleflags==""){
	        issession();
	   } --%>
	 var dataFromData = [{ directionFlag: 1, text: '合格' }, { directionFlag: 0, text: '不合格'}];
	 var qctypeData = [{ dealstate: 6, text: '无工单/录音' }, { dealstate: 7, text: '录音+112工单'},{ dealstate: 8, text: '录音+c网工单'},{ dealstate: 9, text: '录音+112小结'},{ dealstate: 10, text: '录音+c网小结'}];
		$(function () {
			var curTime=getCurDateform2();
			$("#createTimeStartid").val(curTime+" 00:00:00");
			$("#createTimeEndid").val(curTime+" 23:59:59");
			var createTimeStartid=curTime+" 00:00:00";
			var createTimeEndid=curTime+" 23:59:59";
			if(roleflag=='role_4'){
				maingrid_gd= $("#secondary_check_list").ligerGrid({
	                columns: [
					{ display: '操作', name:'sId',isSort: false, width : 80, render: function (rowdata, rowindex, value)
					    {
					    var h = "";
					    	h += "<shiro:hasPermission name='senc:del'> <a href='javascript:delqchis("+value+");' >删除</a> </shiro:hasPermission>";
					    return h;
						}
					},
					{ display: '检查人工号', name: 'sWorkid', align: 'left', width :'10%',isSort:false},
					{ display: '评分编号', name: 'qc_id', align: 'left', width :'10%',isSort:false},	
					{ display: '质检员工号', name: 'sQcWorkid', align: 'left', width :'12%',isSort:false},
					{ display: '质检员姓名', name: 'sQcName', align: 'left', width :'12%',isSort:false},
					{ display: '自查结果', name: 'sIspass', align: 'left', width :'12%',isSort:false,
						editor: { type: 'select', data: dataFromData, valueField: 'sIspass'},
	                    render: function (item)
	                   {
	                       if (parseInt(item.sIspass) == 1) return '合格';
	                       if (parseInt(item.sIspass) == 0) return '不合格';
	                    }
					
					},
				
					{ display: '自查信息', name: 'sRemark', align: 'left', width :'20%',isSort:false},
							{ display: '质检类型', name: 'qc_type', align: 'left', minWidth :'12%',isSort:false,
				editor: { type: 'select', data: qctypeData, valueField: 'qc_type'},
                     render: function (item)
                    {
                    	 if (parseInt(item.qc_type) == 1) return '录音评分';
                    	 if (parseInt(item.qc_type) == 2) return '112工单评分';
                    	 if (parseInt(item.qc_type) == 3) return 'c网工单评分';
                    	 if (parseInt(item.qc_type) == 4) return '112电话小结';
                     	 if (parseInt(item.qc_type) == 5) return 'c网电话小结';
                        if (parseInt(item.qc_type) == 6) return '无工单/录音';
                        if (parseInt(item.qc_type) == 7) return '录音+112工单';
                        if (parseInt(item.qc_type) == 8) return '录音+c网工单';
                        if (parseInt(item.qc_type) == 9) return '录音+112小结';
                        if (parseInt(item.qc_type) == 10) return '录音+c网小结';
       
                     }
				},
					{ display: '检查时间', name: 'createTime', align: 'left', width :'12%',isSort:false}	
	                ], 
	                url:'<%=path%>/controller/secqc/getSecondaryQc.do',
	                parms :[{name:"createTimeStart",value:$("#createTimeStartid").val()},
	                        {name:"createTimeEnd",value:$("#createTimeEndid").val()}],  
	                sortName: 'CustomerID',height:'100%', width:"auto", pageSize:10 ,rownumbers :true
	            });
			}else{
				maingrid_gd= $("#secondary_check_list").ligerGrid({
	                columns: [ 
					{ display: '检查人工号', name: 'sWorkid', align: 'left', width :'10%',isSort:false},
					{ display: '质检编号', name: 'qc_id', align: 'left', width :'10%',isSort:false},		
					{ display: '质检员工号', name: 'sQcWorkid', align: 'left', width :'12%',isSort:false},
					{ display: '质检员姓名', name: 'sQcName', align: 'left', width :'12%',isSort:false},
					{ display: '自查结果', name: 'sIspass', align: 'left', width :'12%',isSort:false,
						editor: { type: 'select', data: dataFromData, valueField: 'sIspass'},
	                    render: function (item)
	                   {
	                       if (parseInt(item.sIspass) == 1) return '合格';
	                       if (parseInt(item.sIspass) == 0) return '不合格';
	                    }
					
					},
					{ display: '自查信息', name: 'sRemark', align: 'left', minWidth :'20%',isSort:false},
					{ display: '质检类型', name: 'qc_type', align: 'left', minWidth :'12%',isSort:false,
				editor: { type: 'select', data: qctypeData, valueField: 'qc_type'},
                     render: function (item)
                    {
                    	 if (parseInt(item.qc_type) == 1) return '录音评分';
                    	 if (parseInt(item.qc_type) == 2) return '112工单评分';
                    	 if (parseInt(item.qc_type) == 3) return 'c网工单评分';
                    	 if (parseInt(item.qc_type) == 4) return '112电话小结';
                     	 if (parseInt(item.qc_type) == 5) return 'c网电话小结';
                        if (parseInt(item.qc_type) == 6) return '无工单/录音';
                        if (parseInt(item.qc_type) == 7) return '录音+112工单';
                        if (parseInt(item.qc_type) == 8) return '录音+c网工单';
                        if (parseInt(item.qc_type) == 9) return '录音+112小结';
                        if (parseInt(item.qc_type) == 10) return '录音+c网小结';
       
                     }
				},
					{ display: '检查时间', name: 'createTime', align: 'left', width :'12%',isSort:false}
					
	                ], 
	                url:'<%=path%>/controller/secqc/getSecondaryQc.do',
	                parms :[{name:"createTimeStart",value:$("#createTimeStartid").val()},
	                        {name:"createTimeEnd",value:$("#createTimeEndid").val()},
	                        {name:"sQcWorkid",value:$("#sQcWorkidid").val()}],  
	                sortName: 'CustomerID',height:'100%', width:"auto", pageSize:10 ,rownumbers :true
	            });
			}
			
            $("#pageloading").hide(); 
        });
		
		function f_search() {
			var parmItems=[{ name:"sWorkid",value:$("#sWorkidid").val()},
			               { name:"sQcWorkid",value:$("#sQcWorkidid").val()},
			               { name:"sIspass",value:$("#sIspassid").val()},
			               { name:"qc_type",value:$("#qc_type").val()},
			               { name:"createTimeStart",value:$("#createTimeStartid").val()},
			               {name:"qc_id",value:$("#qc_id").val()},  
			               { name:"createTimeEnd",value:$("#createTimeEndid").val()}];
				maingrid_gd.options.parms=parmItems;
				maingrid_gd.options.usePager =true;
				maingrid_gd.options.page='1';
				maingrid_gd.options.newPage=1;
			    maingrid_gd.options.url = '<%=path%>/controller/secqc/getSecondaryQc.do';
			    maingrid_gd.loadData(f_getWhere());
		}
		function  f_getWhere() {
			if (!maingrid_gd)
				return null;
			var clause = function(rowdata, rowindex) {
				var sWorkid = $("#sWorkidid").val();
				return rowdata.workorderId.indexOf(sWorkid) > -1;
			};
			return clause;
		}
		
		
		function chongzhi(){
		    $("#sIspassid").val("");
			$("#sWorkidid").val("");
			$("#qc_id").val("");	
			
		}
		function delqchis(sid){
			$.ligerDialog.confirm('确认删除该质检记录吗？', function (yes){
				if(yes){
				  $.ajax({
						url:'<%=path%>/controller/secqc/deleteSecQc.do',
						type:'post',
						data:{'sid':sid},
						success:function(data){
							var res=eval("("+data+")");
							if(res.status=='1'){
								$.ligerDialog.success('删除成功','提示',function(yes){
									maingrid_gd.loadData(f_getWhere());
								});
								
							}
						},
						error:function(error){
							$.ligerDialog.error('删除历史质检记录失败，请稍后再试！');
						}
						
					}); 
                }
			});
		}
			
    </script>
	</head>
	<!-- 
<body style="margin:0 auto; background-color:#FFFFFF; padding:5px;">
 -->
	<body>
		<div style="">
			<div class="title_div"
				style="width: 100%; margin-top: 0px; display: none;">
				<label style="margin-left: 15px;">
					查询条件
				</label>
			</div>
			<div align="left"
				style="margin-top: 10px; margin-left: 5px; padding-left: 10px;">
				<table id="search_table" cellpadding="3" cellspacing="3"
					width="100%">
					<tr>
						<td class="td_lable">
							检查人工号：
							</td>
							<td class="td_value">
							<input type="text" name="sWorkid" id="sWorkidid"
								onkeyup="input_shuziyinwen(this);strlenLimit(this,20);" />
							</td>
							<td class="td_lable">
							 质检员工号：
							 </td>
							 <td class="td_value">
							<c:if test="${requestScope.role_flag=='role_3'}">
								<input type="text" name="sQcWorkid" id="sQcWorkidid"
									value="<%=userworkid %>" readonly="readonly"
									disabled="disabled" />
							</c:if>
							<c:if test="${requestScope.role_flag=='role_4'}" >
								<input type="text" name="sQcWorkid" id="sQcWorkidid"
									onkeyup="input_shuziyinwen(this);strlenLimit(this,20);" />
							</c:if>
							</td>
							<td class="td_lable">
							 检查结果：
							</td>
							<td class="td_value">
							<select name="sIspass" id="sIspassid" style="width: 135px">
								<option value="">
									---请选择---
								</option>
								<option value="1">
									合格
								</option>
								<option value="0">
									不合格
								</option>
							</select>
							</td>
							<td class="td_lable">
							检查时间：
							</td>
							<td class="td_value">
							<input name="createTimeStart" id="createTimeStartid"
								class="Wdate date_text" type="text"
								onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'createTimeEndid\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
							&nbsp;-&nbsp;
							<input class="Wdate date_text" name="createTimeEnd"
								id="createTimeEndid" type="text"
								onfocus="WdatePicker({minDate:'#F{$dp.$D(\'createTimeStartid\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
						</td>
					</tr>
					<tr>
					<td class="td_lable">质检类型：</td><td class="td_value">
					<select name="qc_type" id="qc_type" >
					<option value="" style="text-align: center;">--请选择--</option>
					<option value="1">录音评分</option>
					<option value="2">112工单评分</option>
					<option value="3">c网工单评分</option>
					<option value="4">112电话小结</option>
					<option value="5">c网电话小结</option>
					<option value="6">无工单/录音</option>
					<option value="7">录音+112工单</option>
					<option value="8">录音+c网工单</option>
					<option value="9">录音+112小结</option>
					<option value="10">录音+c网小结</option>
					</select></td>
					<td class="td_lable">质检编号：</td>
						<td class="td_value" >
							<input type="text" id="qc_id" name="qc_id" onkeyup="input_shuziyinwen(this);strlenLimit(this,20);"/>				
				</td>
					</tr>
					<tr>
						<td colspan="8" align="center">
						<shiro:hasPermission name='senc:query'>
							<input type="button" value="查询" onclick="f_search();" />
							&nbsp;&nbsp;&nbsp;&nbsp;
						</shiro:hasPermission>	
							<input type="button" value="重置" onclick="chongzhi()" />
						</td>
					</tr>
				</table>
			</div>
			<div class="l-loading" style="display: block" id="pageloading"></div>
			<div id="secondary_check_list" style="margin-top: 10px;"></div>
		</div>
	</body>
</html>
