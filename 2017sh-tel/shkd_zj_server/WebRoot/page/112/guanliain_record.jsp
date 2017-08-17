<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>xxx后台管理系统</title>
		<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=path %>/css/public.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
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
		<script language="javascript" type="text/javascript" src="<%=path%>/js/housing_covering.js"></script>
			<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
			<script src="<%=path%>/js/util.js" type="text/javascript"></script>
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


input[type='button'] {
	border: #d3d3d3 1px solid;
	width: 80px;
	height: 25px;
	cursor: pointer;
}

</style>
		<script type="text/javascript"> 
		var maingrid_gd=null;
		$(function () {
			var starttime=$("#stoptimeq",parent.document).val();
			var endtime=$("#stoptimeh",parent.document).val();
			var agentworkid=$("#agentworkid",parent.document).val();
			$("#startTime").val(starttime);
			$("#stopTime").val(endtime);
			$("#agentName").val(agentworkid);
			
             maingrid_gd= $("#maingrid_gd").ligerGrid({
                columns: [                
				{ display: '操作', name:'recordId',isSort: false, width: 100, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                    h += "<a href='javascript:void(0);' onclick='relate(\""+value+"\");'>关联</a> ";
						h += "<a href='javascript:void(0);' onclick='playrecord(\""+rowdata.reservedThree+"\");'>录音播放</a> ";
	                    return h;
	                }
                },
                { display: '流水号', name: 'recordId', align: 'left', minWidth:130,isSort:false},
				{ display: 'CTI流水号', name: 'reservedThree', align: 'left', minWidth:160,isSort:false},
                { display: '受理员工号', name: 'bigWorkid', align: 'left', minWidth:120,isSort:false},
				{ display: '受理员姓名', name: 'agentName', align: 'left', minWidth:80,isSort:false},
				{ display: '呼入/呼出', name: 'directionFlag', align: 'left', minWidth:80,isSort:false,render : function(rowdata, rowindex, value) {
						var result="";
						switch(value){
						   case "0":
						    result="呼出";
						   break;
						   case "1":
						    result="呼入";
						   break;
						   default:
						     result="其他";
						   break;
						   
						}	
						return result;
						}},
				{ display: '主叫号码', name: 'callerid', align: 'left', minWidth:100,isSort:false},
				{ display: '被叫号码', name: 'calledid', align: 'left', minWidth:100,isSort:false},
				{ display: '来电开始时间', name: 'startTime', align: 'left', minWidth:140,isSort:false},
				{ display: '来电结束时间', name: 'stopTime', align: 'left', minWidth:140,isSort:false},
				{ display: '通话时长', name: 'recordLength', align: 'left', minWidth:100,isSort:false,render : function(rowdata, rowindex, value) {
						
						if(value!=""){
						  return value+"s";
						  }
						  return "";
						}
				}
                ], url : '<%=path%>/controller/wk112/getNoGLRecord.do',
                parms :[ {name : "startTime",value : $("#startTime").val()}, 
                         {name : "stopTime", value : $("#stopTime").val()},
                	     {name : "agentName",value : $("#agentName").val()}],  
							height : 250,
							width : "auto",
							pageSize : 10,
							rownumbers : true
							/* onSuccess : function(data, grid) {
								var total = data.Total;
								if (total == 0) {
									$.ligerDialog.error("未查询到数据");
								}
							} */
						});
		$("#pageloading").hide();
	});

	function f_search() {
		var parmItems = [ {
			name : "startTime",
			value : $("#startTime").val()
		}, {
			name : "agentName",
			value : $("#agentName").val()
		}, {
			name : "stopTime",
			value : $("#stopTime").val()
		} ];
		maingrid_gd.options.parms = parmItems;
		maingrid_gd.options.page = 1;
		maingrid_gd.options.newPage=1;
		maingrid_gd.options.data = $.extend(true, {}, parmItems);//必须这么写
		maingrid_gd.loadData(f_getWhere());
	}
	function f_getWhere() {
		if (!maingrid_gd)
			return null;
		var clause = function(rowdata, rowindex) {
			var key = $("#txtKey").val();
			return rowdata.CustomerID.indexOf(key) > -1;
		};
		return clause;
	}
	//工单关联录音
	function relate(recordId){
			var wordorderId=$("#workorderId",parent.document).val();
			var display=$("#displayid",parent.document).val();
			if(display=="0"){
					$.ligerDialog.error('关联失败，已评过分无法继续关联！');
					return false;
			}else{	
			var telsumId=$("#telsumId",parent.document).val();
				$.ligerDialog.confirm('确认关联该录音吗？关联后不能修改！','提示', function (yes) {
				if(yes){
					$.ajax({
						url:'<%=path%>/controller/qc/relateRecord.do',
						type:'post',
						data:{'recordid':recordId,'workid':wordorderId,'telsumId':telsumId},
						error: function(request) {
							$.ligerDialog.error('关联失败，请稍候再试!');
						},
						success: function(data) {
							var res=eval("("+data+")");
							if(res.status=='1'){
								//关联成功后回显工单信息
								/* $("#glflagid",parent.document).val(res.key); */
							    
							    $("#recordId",parent.document).val(res.rd.recordId);
								$("#reservedThree",parent.document).val(res.rd.reservedThree);
								$("#startTime",parent.document).val(res.rd.startTime);
								//$("#SLstartTime",parent.document).val(res.rd.startTime);
								//$("#agentName",parent.document).val(res.rd.agentName);
								$("#stopTime",parent.document).val(res.rd.stopTime);
								$("#callerid",parent.document).val(res.rd.callerid);
								$("#calledid",parent.document).val(res.rd.calledid); 
								$("#bigWorkid",parent.document).val(res.rd.bigWorkid);
								$("#smallWorkid",parent.document).val(res.rd.smallWorkid); 
								$("#reservedThree",parent.document).attr("disabled","disabled"); 	
								$("#startTime",parent.document).attr("disabled","disabled"); 
								$("#stopTime",parent.document).attr("disabled","disabled"); 
								$("#callerid",parent.document).attr("disabled","disabled"); 
								$("#calledid",parent.document).attr("disabled","disabled"); 
								
								 $.ligerDialog.success('关联成功！','提示',function(yes){
									  maingrid_gd.loadData(f_getWhere());
									  $("#showGongDanDiv",parent.document).slideToggle();
										 //自动播放录音
										 var player=window.parent.document.getElementById('player');
			                             player.URL="<%=path%>/controller/record/downRecord.do?reservedThree="+res.rd.reservedThree;
			                            // player.controls.play();
									  
								  });
								
							}else if(res.status=='0'){
								$.ligerDialog.warn('该工单已关联录音，不能修改关联');
							}else{
								$.ligerDialog.error('关联失败，请稍候再试!');
							}
						}
					});
				}
										
				});
			}
		}
		
		function playrecord(reservedThree){
		         //   var url = '<%=path%>/page/play.jsp?reservedThree='+reservedThree;
		          //   win_move();
		           //     $(".pop_up_window").css({
		  			//      "width":"340",
		  			 //     "height":"120",
		  			  //    "left":"0px",
		  			   //   "top":"0px"
		  			    //  }).children("iframe").attr("src",url);
			               //自动播放录音
			              var player=window.parent.document.getElementById('player');
			              player.URL="<%=path%>/controller/record/downRecord.do?reservedThree="+reservedThree;
			              player.controls.play();
							//var x2=document.getElementsByTagName("param")[0];
							//var x6=document.getElementsByTagName("param")[6];
                            //x2.value="<%=path%>/controller/record/downRecord.do?reservedThree="+reservedThree;
							//x6.value="1";
		}
		
		function OpenWindow(recordReference){                
         var newWindow = window.open("<%=request.getContextPath()%>/controller/wk112/listenRecordoBy112.do?recordReference="+recordReference,"_blank","height=80, width=400, top=0,left=0, toolbar=no, menubar=no, scrollbars=yes");
        }
		
</script>
	</head>
	<body>
		<div class="title_div" style="width: 100%; margin-top: 0px;">
			<label style="margin-left: 15px;">
				查询条件
			</label>
		</div>
		<div align="left"
			style="margin-top: 10px; margin-left: 5px; padding-left: 10px;">
			录音时间：
			<input class="Wdate date_text" type="text" id="startTime"
				name="startTime"
				onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'stopTime\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
				/>
			&nbsp;-&nbsp;
			<input class="Wdate date_text" type="text" id="stopTime"
				name="stopTime"
				onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 受理员：
			<input type="text" class="input_text" id="agentName" name="agentName"  disabled="disabled"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="查询" onclick="f_search()"/>
		</div>
		<div class="l-loading" style="display: block" id="pageloading"></div>
		<div id="maingrid_gd" style="margin-top: 10px;"></div>
	</body>
</html>
