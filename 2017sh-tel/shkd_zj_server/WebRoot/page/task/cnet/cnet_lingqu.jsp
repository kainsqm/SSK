<%@ page language="java" import="java.util.*,java.net.URLDecoder"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/shiro.tld" prefix="shiro"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String taskName=URLDecoder.decode(URLDecoder.decode(request.getParameter("taskName"),"UTF-8"), "utf-8"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>客调项目管理系统</title>
		<!-- 领取工单页面 -->
		<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
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
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js"
			type="text/javascript"></script>
	    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/util.js"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
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
	width: 135px;
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

div.l-panel-topbar {
	height: 30px !important;
}
</style>
		<script type="text/javascript"> 
	    var grid=null;
		$(function () {
            grid= $("#task_qc_info").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false, minWidth : 80, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                    h += "<a href='javascript:void(0);' onclick='getCdma(\""+rowdata.opId+"\",\""+rowdata.cdmaAutoid+"\");'>领取</a> ";
					    h += "<a href='javascript:void(0);' onclick='pingfen(\""+rowdata.opId+"\",\""+rowdata.serialCdma+"\",\""+rowdata.cdmaAutoid+"\");'>评分</a> ";
						return h;
	                }
                },
				{ display: 'c网关联流水', name: 'serialCdma', align: 'left', minWidth:100,isSort:false},
                { display: '业务类型', name: 'serviceType', align: 'left', minWidth:100,isSort:false},
				{ display: '小结类型', name: 'resultType', align: 'left', minWidth:100,isSort:false},
				{ display: '故障来源', name: 'complaintSource', align: 'left', minWidth:100,isSort:false},
				{ display: '申告地方', name: 'dirNum', align: 'left', minWidth:100,isSort:false},
				{ display: '电话小结流水', name: 'resultSerial', align: 'left', minWidth:150,isSort:false},
				{ display: '电话小结时间', name: 'resultTime', align: 'left', minWidth:100,isSort:false},
				{ display: '电话小结工号', name: 'opId', align: 'left', minWidth:100,isSort:false}
				],
                url:"<%=path%>/controller/verdict/getCdmaSumForQc.do",
				parms:[
		               { name:"taskTime",value:$("#taskTime").val()},
		               { name:"taskQcUserId",value:$("#taskQcUserId").val()},
		               { name:"bussniessType",value:$("#bussniessType").val()},
		               { name:"telsumType",value:$("#telsumType").val()},
		               { name:"gzlyType",value:$("#gzlyType").val()}
		               ], 
				height:'100%', width:"auto",usePager:false,rownumbers :true,
				toolbar: { items: [
						{ text: '每次查询，随机加载10条'}
					]
                }
            });
            $("#pageloading").hide(); 
            getComboboxValue();
        });
        
        
        function getComboboxValue(){
         var bussniessType=$("#bussniessType").val();
	     var telsumType=$("#telsumType").val();
	     var gzlyType=$("#gzlyType").val();
          $.ajax({	 
				url:'<%=path%>/controller/verdict/getCnetOrderTypeByTask.do',
				type:'post',
				data:{'taskId':$("#taskId").val()},
				dataType:'json',
				success:function(data){	
				    $("#test1").ligerComboBox({ isShowCheckBox: true, isMultiSelect: true,
					data: data.serviceType, valueFieldID: 'bussniessType',initValue:bussniessType,readonly:false
					});   
		
					$("#test11").ligerComboBox({ isShowCheckBox: true, isMultiSelect: true,
					data: data.resultType, valueFieldID: 'telsumType',initValue:telsumType,readonly:false
					});
					$("#test111").ligerComboBox({ isShowCheckBox: true, isMultiSelect: true,
					data:data.complaintSource, valueFieldID: 'gzlyType',initValue:gzlyType,readonly:false
					});
				}
			});
        }
        
        
         function f_search(){
	         var bussniessType=$("#bussniessType").val();
		     var telsumType=$("#telsumType").val();
		     var gzlyType=$("#gzlyType").val();
             var bussniessTypeValue=$("#bussniessTypeValue").val();
		     var telsumTypeValue=$("#telsumTypeValue").val();
		     var gzlyTypeValue=$("#gzlyTypeValue").val();
             if(bussniessTypeValue!="" && bussniessType==""){
                 $.ligerDialog.warn("业务类型不能为空!");
                 return false;
             }
             if(telsumTypeValue!="" && telsumType==""){
                 $.ligerDialog.warn("小结类型不能为空!");
                 return false;
             }
             if(gzlyTypeValue!="" && gzlyType==""){
                 $.ligerDialog.warn("故障来源不能为空!");
                 return false;
             }
              var parmItems=[
		               { name:"taskTime",value:$("#taskTime").val()},
		               { name:"bussniessType",value:$("#bussniessType").val()},
		               { name:"telsumType",value:$("#telsumType").val()},
		               { name:"gzlyType",value:$("#gzlyType").val()},
		               { name:"taskQcUserId",value:$("#taskQcUserId").val()}
		        ];
	        grid.options.parms=parmItems;
			grid.options.page=1;
			grid.options.newPage=1;
            grid.options.url = '<%=path%>/controller/verdict/getCdmaSumForQc.do';//必须这么写
		    grid.loadData(f_getWhere());
        }
        
        function f_getWhere()
        {
            if (!grid) return null;
            var clause = function (rowdata, rowindex)
            {
                var key = $("#txtKey").val();
                return rowdata.CustomerID.indexOf(key) > -1;
            };
            return clause; 
        }
        
		/******************************
		 *  查看任务完成明细
		 *****************************/
		function showTaskWcqkItems(taskUserID){
			window.location.href="edit_taskInfo.html";
		}
		
		
	/**
	*质检员领取录音
	*/
	function getCdma(smaliWorkId,pkAutoId){
		$.ligerDialog.confirm('您确定要领取该条电话小结？', function (yes) { 
		 if(yes){
			var taskQcUserId=document.getElementById("taskQcUserId").value;
			var taskId=document.getElementById("taskId").value;
			var taskTime=document.getElementById("taskTime").value;
			var parameters="taskId="+taskId+"&taskQcUserId="+taskQcUserId+"&taskTime="+taskTime+"&telUser="+smaliWorkId+"&pkAutoId="+pkAutoId;
			$.ajax({
				url:"<%=path%>/controller/verdict/getCdma.do",
				data:parameters,
				type:"post",
				dataType:"json",
				async:false,
				success:function(data){
						if(data.result=="通过"){						
							$.ligerDialog.success("领取成功！");	
							f_search();
						}else if(data.result=="通过2"){
						$.ligerDialog.success("领取成功,今日任务已领满");	
						f_search();
						}else{
							$.ligerDialog.warn(data.result);
							return;
						}
					}
				});
			}
			});
	}
	
	
	/**
	*质检员评分
	*/
	function pingfen(workID,serialCdma,cdmaAutoid){
		$.ligerDialog.confirm("您确定要领取该条电话小结并评分？", function (yes) { 
		 if(yes){
			var taskQcUserId=document.getElementById("taskQcUserId").value;
			var taskId=document.getElementById("taskId").value;
			var taskTime=document.getElementById("taskTime").value;
			var parameters="taskId="+taskId+"&taskQcUserId="+taskQcUserId+"&taskTime="+taskTime+"&telUser="+workID+"&pkAutoId="+cdmaAutoid;
			$.ajax({
				url:"<%=path%>/controller/verdict/getCdma.do",
				data:parameters,
				type:"post",
				dataType:"json",
				async:false,
				success:function(data){
						if(data.result=="通过"){						
							f_search();
							 var form=$("<form>");//定义一个form表单
							 form.attr("style","display:none");
							 form.attr("target","_blank");
							 form.attr("id","pinfenform");
							 form.attr("method","post");
							 form.attr("action","<%=path%>/controller/ordercdma/qcwkordercdma.do");
							 var inputserialCdma=$("<input>"); //serialCdma
							 inputserialCdma.attr("type","hidden");
							 inputserialCdma.attr("name","serialCdma");
							 inputserialCdma.attr("value",serialCdma);
							 var inputcdmaAutoid=$("<input>"); //cdmaAutoid
							 inputcdmaAutoid.attr("type","hidden");
							 inputcdmaAutoid.attr("name","autoid");
							 inputcdmaAutoid.attr("value",cdmaAutoid);
							 var inputchannelType=$("<input>");
							 inputchannelType.attr("type","hidden");
							 inputchannelType.attr("name","channelType");
							 inputchannelType.attr("value","taskcdma");
							 $("body").append(form);//将表单放置在body中
							 form.append(inputserialCdma);
							 form.append(inputcdmaAutoid);
							 form.append(inputchannelType);
							 form.submit();//表单提交
							 $("#formid").remove();
						}else{
							$.ligerDialog.warn(data.result);
							return;
						}
					}
				});
			}
			});
	}
		
	/**
	   *质检员一键领取录音
	 */
	function oneKeyGetRecord(){
		$.ligerDialog.confirm('您确定要一键领取当天录音？', function (yes) { 
		 if(yes){
			var taskQcUserId=document.getElementById("taskQcUserId").value;
			var taskTime=document.getElementById("taskTime").value;
			var parameters="taskQcUserId="+taskQcUserId+"&taskTime="+taskTime;
			$.ajax({
				url:"<%=path%>/controller/verdict/oneKeyGetCdmaSum.do",
				data:parameters,
				type:"post",
				async:false,
				dataType:"json",
				success:function(data){
						if(data.result=="成功"){						
							$.ligerDialog.success("一键领取完成！","提示",function(yes){
								back();
							});					
						}else if(data.result=="数据缺失"){
							$.ligerDialog.success("一键领取完成，部分因数据缺失未领取成功","提示",function(yes){
								f_search();
							});
							return;
						}else if(data.result=="当天电话小结领取数已满"){
						    $.ligerDialog.warn("当天电话小结领取数已满!");
							return;
						}
					},
					error:function(data){
					   $.ligerDialog.warn("一键领取失败，数据异常");
							return;
					}
				});
		}
		});
	}
		
		function back(){
		  history.back();
		}
		
    </script>
	</head>
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
						<td>
							<input type="hidden" id="taskId"
								value="<%=request.getParameter("taskId") %>" />
							<input type="hidden" id="taskTime"
								value="<%=request.getParameter("taskTime") %>" />
							<input type="hidden" id="taskQcUserId"
								value="<%=request.getParameter("taskQcUserId") %>" />
						   任务名称：
						</td>
						<td><input type="text" disabled="disabled" value="<%=taskName %>" /></td>
						<td>
						          业务类型：
						 </td>
						 <td><input type="text" id="test1"/></td>
						 <td>
						          小结类型：
						 </td>
						 <td><input type="text" id="test11"/></td>
						 <td>
						          故障来源：
						    <input type="hidden" id="bussniessType" name="bussniessType" value="${cnet.bussniessType}"/>
						    <input type="hidden" id="telsumType" name="telsumType" value="${cnet.telsumType}"/>
						    <input type="hidden" id="gzlyType" name="gzlyType" value="${cnet.gzlyType}"/>
						</td>
						<td><input type="text" id="test111"/></td>
				  </tr>
				  <tr>
					    <td colspan="8" align="center">
					        <input type="hidden" id="bussniessTypeValue" value="${cnet.bussniessType}"/>
						    <input type="hidden" id="telsumTypeValue" value="${cnet.telsumType}"/>
						    <input type="hidden" id="gzlyTypeValue" value="${cnet.gzlyType}"/>
							<input type="button" value="查询" onclick="f_search()" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="返回" onclick="back()" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="一键领取" onclick="oneKeyGetRecord()" />
						</td>
					</tr>
				</table>
			</div>
			<div class="l-loading" style="display: block" id="pageloading"></div>
			<div id="task_qc_info" style="margin-top: 10px;"></div>
		</div>
	</body>
</html>

