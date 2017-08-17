<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String taskName= URLDecoder.decode(request.getParameter("taskName"),"UTF-8");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>领取任务${ctx}</title>
	<link href="${ctx}/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script> 
    <script src="${ctx}/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
	<script src="${ctx}/lib/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
	<script src="${ctx}/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="${ctx}/lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(${ctx}/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		input[type='text']{height:24px;width:140px}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;} 
		input[type='button']{ border:#d3d3d3 1px solid; width:80px; height:25px; cursor:pointer;}
		input.date_text{border-radius:5px; width:135px; height:18px; line-height:18px;}
		
		table#search_table td{ height:30px; line-height:30px;}
		table#search_table td.td_lable{ text-align:right;}
		table#search_table td.td_value{ text-align:left;}
		div.l-panel-topbar{ height:30px !important; }
	</style>
	<script type="text/javascript"> 
	 var maingrid_gd;
		$(function () {
		  var taskId=  $("#taskId").val();
           maingrid_gd= $("#task_qc_info").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false, minWidth : 80, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                    h += "<a href='javascript:void(0);' onclick='reciveTask("+rowdata.autoId+",\""+rowdata.telsumWorkid+"\");'>领取</a> ";
	                    h += "<a href='javascript:void(0);' onclick='to112grade("+rowdata.autoId+",\""+rowdata.workorderId+"\",\""+rowdata.telsumWorkid+"\");'>评分</a> ";
	                    //h += "<a href='${ctx}/controller/wk112/112gradeShow.do?workorderId="+rowdata.workorderId+"&autoid="+rowdata.autoId+"&channelType=chapter112Grade' target='_blank'>评分</a> ";
						return h;
	                }
                },
                //业务类型, 小结类型, 故障来源, 备注, 申告号码, 逻辑号码, 112流水, 电话小结流水, 电话小结时间, 电话小结工号
				{ display: '业务类型', name: 'businessType', align: 'left', minWidth:130,isSort:false},
                { display: '小结类型', name: 'telsumType', align: 'left', minWidth:120,isSort:false},
				{ display: '故障来源', name: 'errorSource', align: 'left', minWidth:80,isSort:false},
				{ display: '备注', name: 'remark', align: 'left', width:80,isSort:false},
				{ display: '申告号码', name: 'declarationNo', align: 'left', width:80,isSort:false},
				{ display: '逻辑号码', name: 'logicNo', align: 'left', width:100,isSort:false},
				{ display: '112流水', name: 'telsumId', align: 'left', minWidth:160,isSort:false},
				{ display: '电话小结时间', name: 'telsumTime', align: 'left', minWidth:100,isSort:false},
				{ display: '电话小结工号', name: 'telsumWorkid', align: 'left', width:100,isSort:false}
				], 
				url:'${ctx}/controller/chapter112/getReviceChapterList.do', 
				parms:[{name:"taskId",value:taskId},
					   {name:"type",value:"default"}
		        ],
				sortName: 'workorderId',height:'100%', width:"auto",width:"auto",usePager:false,rownumbers :true,
				toolbar: { 
					items: [
					{ text: '每次查询，随机加载10条'}
				]}
            });
            $("#pageloading").hide();    
            
            //加载下拉框
            $.ajax({
        		url:'<%=path%>/controller/chapter112/get112ChapterTypeByID.do',
        		type:'post',
        		data:{taskId:taskId},
        		dataType:'json',
        		success:function(data){	
	        		var businessType=JSON.stringify(data.businessType);
	        		var telsumType=JSON.stringify(data.telsumType);
	        		var errorSource=JSON.stringify(data.errorSource);
	       		    $("#businessType").ligerComboBox({ isShowCheckBox: true, isMultiSelect: true,
	       				data: data.businessType, valueFieldID: 'qjtype'
	       			});   
	       			$("#telsumType").ligerComboBox({ isShowCheckBox: true, isMultiSelect: true,
	       				data: data.telsumType, valueFieldID: 'telsumType'
	       			});
	       			$("#errorSource").ligerComboBox({ isShowCheckBox: true, isMultiSelect: true,
	       				data:data.errorSource, valueFieldID: 'errorSource'
	       			});
        		}
        	});
        });
		
		
        function f_search(){
	         var type;
	         if(businessType==""&&telsumType==""&&errorSource==""){
	          	type="default";  
	         }else{
	          	type="onclick";
	         }
	         var parmItems=[{ name:"taskId",value:$("#taskId").val()},
	               { name:"taskName",value:$("#taskName").val()},
	               { name:"businessType",value:$("#businessType").val()},
	               { name:"telsumType",value:$("#telsumType").val()},
	               { name:"errorSource",value:$("#errorSource").val()}
	         ];
	       	maingrid_gd.options.parms=parmItems;
			maingrid_gd.options.usePager =true;
			maingrid_gd.options.page='1';
			maingrid_gd.options.newPage=1;
		    maingrid_gd.options.url = '${ctx}/controller/chapter112/getReviceChapterList.do', 
		    maingrid_gd.loadData(f_getWhere());
        }

       function f_getWhere()
        {
            if (!maingrid_gd) return null;
            var clause = function (rowdata, rowindex)
            {
                var key = $("#taskId").val();
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
		
		function tq_record(){
			window.location.href="../gongdan_qc.html";
		}
		
		//一键领取
		function linquall(){
			$.ligerDialog.confirm('您确定要一键领取当天录音？', function (yes) {
				var taskId=$("#taskId").val();
				$.ajax({
					url:'${ctx}/controller/chapter112/oneKeyReciveAll.do',
					type:'post',
					data:{taskId:taskId},
					dataType:'json',
					success:function(data){	
						if(data.vmsg.indexOf("成功")>-1){
							$.ligerDialog.success(data.vmsg,'提示',function(yes){	
								f_search();
							});	
						}else{
							$.ligerDialog.warn(data.vmsg,'提示',function(yes){	
								f_search();
							});	
						}
					},
					error:function(e){
		       			$.ligerDialog.warn('领取异常，请稍后再试');
		       	    }
				});
			});
		}
		
		//单条领取
		function reciveTask(telSumId, teluser) {
			var taskId = $("#taskId").val();
			$.ajax({
				url : '${ctx}/controller/chapter112/getReciveTask.do',
				type : 'post',
				data : {
					taskId : taskId,
					telSumId : telSumId,
					teluser : teluser
				},
				dataType : 'json',
				success : function(data) {
					if(data.vmsg.indexOf("成功")>-1){
						$.ligerDialog.success(data.vmsg,'提示',function(yes){	
							f_search();
						});	
					}else{
						$.ligerDialog.warn(data.vmsg,'提示',function(yes){	
							f_search();
						});	
					}
				}
			});
		}
		
		//评分
		function to112grade(autoId,workorderId,telsumWorkid){
			var taskId = $("#taskId").val();
			$.ligerDialog.confirm("您确定要领取该条112小结并评分？", function (yes) { 
			if(yes){
				$.ajax({
					url : '${ctx}/controller/chapter112/getReciveTask.do',
					type : 'post',
					data : {
						taskId : taskId,
						telSumId : autoId,
						teluser : telsumWorkid
					},
					dataType : 'json',
					success : function(data) {
						console.log(data);
						if(data.vmsg.indexOf("成功")>-1){
							var form=$("<form>");//定义一个form表单
							 form.attr("style","display:none");
							 form.attr("target","_blank");
							 form.attr("id","pinfenform");
							 form.attr("method","post");
							 form.attr("action","<%=path%>/controller/wk112/112gradeShow.do");
							 var inputserialCdma=$("<input>"); //serialCdma
							 inputserialCdma.attr("type","hidden");
							 inputserialCdma.attr("name","autoid");
							 inputserialCdma.attr("value",autoId);
							 var inputcdmaAutoid=$("<input>"); //cdmaAutoid
							 inputcdmaAutoid.attr("type","hidden");
							 inputcdmaAutoid.attr("name","workorderId");
							 inputcdmaAutoid.attr("value",workorderId);
							 var inputchannelType=$("<input>");
							 inputchannelType.attr("type","hidden");
							 inputchannelType.attr("name","channelType");
							 inputchannelType.attr("value","chapter112Grade");
							 $("body").append(form);//将表单放置在body中
							 form.append(inputserialCdma);
							 form.append(inputcdmaAutoid);
							 form.append(inputchannelType);
							 form.submit();//表单提交
							 $("#formid").remove();
						}else{
							$.ligerDialog.warn(data.vmsg,'提示',function(yes){	
								f_search();
							});	
						}
					}
				});
			}
			});
		}
	</script> 
</head>
<body> 
	<div style="">
		<div class="title_div" style="width:100%; margin-top:0px; display:none;"><label style="margin-left:15px;">查询条件</label></div>
		<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
			<table id="search_table" cellpadding="3" cellspacing="5" width="100%" >
				<tr>
					<input type="hidden" id="taskId" value="<%=request.getParameter("taskId") %>" />
					<input type="hidden" id="taskTime" value="<%=request.getParameter("taskTime") %>" />
					<input type="hidden" id="taskQcUserId" value="<%=request.getParameter("taskQcUserId") %>" />
					<td class="td_lable">任务名称：</td>
					<td class="td_value"><input type="text" id="taskName" value="<%=taskName%>" readonly="readonly" /></td>
					<td class="td_lable">业务类型：</td>
					<td class="td_value"><input type="text" id="businessType" /></td>
					<td class="td_lable">小结类型：</td>
					<td class="td_value"><input type="text" id="telsumType" /></td>
					<td class="td_lable">故障来源：</td>
					<td class="td_value"><input type="text" id="errorSource" /></td>
				</tr>
				<tr>	
					<td colspan="8" align="center">
					<input type="button" value="查询"  onclick="f_search()" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="返回"  onclick="history.go(-1);" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="一键领取" onclick="linquall()" />
					</td>
					</tr>
			</table>			
		</div>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="task_qc_info" style="margin-top:10px;"></div>
	</div>
</body>
</html>
