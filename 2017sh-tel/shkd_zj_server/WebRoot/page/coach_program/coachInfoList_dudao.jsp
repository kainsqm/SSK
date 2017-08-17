<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/shiro.tld" prefix="shiro"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>客调项目管理系统</title>
	<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script> 
    <script src="<%=path%>/js/util.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(<%=path%>/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input[type='button']{ border:#d3d3d3 1px solid; width:80px; height:25px; cursor:pointer;}
		input.date_text{border-radius:5px; width:140px; height:18px; line-height:18px;}
		
		table#search_table td{ height:30px; line-height:30px;}
		table#search_table td.td_lable{ text-align:right;}
		table#search_table td.td_value{ text-align:left;}
		.search_ul li{width:25%;float:left;line-height:30px;}
		.search_ul li label{display:inline-block;width:107px;text-align: right;}
	</style>
	<script type="text/javascript"> 
	    var maingrid_gd=null;
	    var today;
	    var toendday;
		$(function () {
			today = getfirstDay();
			toendday= getnowDay();
			$("#starttime").val(today);
			$("#endtime").val(toendday);
             maingrid_gd= $("#coach_dudao_list").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false, width : 140, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                    if(rowdata.pass=='2' && rowdata.sumImprove == ''){
	                       h += "<shiro:hasPermission name='dbcoach:check'><a href='javascript:void(0);' onclick='check_coachInfo(\""+rowdata.coachmainId+"\");'>审核</a> </shiro:hasPermission>";
	                    }
	                    
						h += "<shiro:hasPermission name='dbcoach:getinfo'><a href='javascript:void(0);' onclick='show_coachInfo_items(\""+rowdata.coachmainId+"\");'>计划明细</a></shiro:hasPermission> ";
						h += "<shiro:hasPermission name='dbcoach:del'><a href='javascript:void(0);' onclick='delCoachMain(\""+rowdata.coachmainId+"\");'>删除</a></shiro:hasPermission>";
	                    return h;
	                }
                },
				{ display: '质检员', name: 'qcUserName', align: 'left', width :130,isSort:false},
                { display: '辅导对象', name: 'username', align: 'left', width :120,isSort:false},
				{ display: '辅导时间', name: 'starttime', align: 'left', minWidth :80,isSort:false},
				{ display: '辅导状态', align: 'left', minWidth :80,isSort:false, render: function (rowdata, rowindex, value)
	                {
	                    var result="";
	                    if(rowdata.pass=='2' && rowdata.sumImprove == ''){
	                       result="月计划待审核 ";
	                    }else if(rowdata.pass=='1' && rowdata.sumImprove == ''){
	                       result="月计划已通过 ";
	                    }else if(rowdata.pass=='0' && rowdata.sumImprove == ''){
	                       result="月计划未通过 ";
	                    }else if(rowdata.sumImprove=='2'){
	                       result="辅导总结待审核 ";
	                    }else if(rowdata.sumImprove=='1'){
	                       result="辅导总结已通过 ";
	                    }
	                    else if(rowdata.sumImprove=='0'){
	                       result="辅导总结未通过 ";
	                    }
	                    return result;
	                }}
                ], url:'<%=path%>/controller/coach/getDDCheckCoachMonth.do',
                parms:[{name:"starttime",value:$("#starttime").val()},
		               { name:"endtime",value:$("#endtime").val()}
		               ],
                height:'100%', width:"auto", pageSize:10 ,rownumbers :true
            });
            $("#pageloading").hide(); 
        });
		/*************************************
		 * 打开计划明细界面
		 ************************************/
		function show_coachInfo_items(coachmainId){
			window.location.href="<%=path%>/controller/coach/getCoachInfoDuDao.do?coachmainId="+coachmainId;
		}
		
		/**
		*打开审核页面
		**/
		function check_coachInfo(coachmainId){
			window.location.href="<%=path%>/controller/coach/getCoachInfoCheck.do?coachmainId="+coachmainId;
		}
		
		 function f_search()
        {
            var parmItems=[
				{ name:"AcceptorWorkId",value:$("#AcceptorWorkId").val()},
				{ name:"starttime",value:$("#starttime").val()},
				{ name:"endtime",value:$("#endtime").val()},
				{ name:"pass",value:$("#pass").val()},
				{ name:"qcUserName",value:$("#qcUserName").val()}
				];
			maingrid_gd.options.parms=parmItems;
			maingrid_gd.options.page=1;
			maingrid_gd.options.newPage=1;
            maingrid_gd.options.data = $.extend(true, {}, parmItems);//必须这么写
		    maingrid_gd.loadData(f_getWhere());
        }
        function f_getWhere()
        {
            if (!maingrid_gd) return null;
            var clause = function (rowdata, rowindex)
            {
                var key = $("#txtKey").val();
                return rowdata.CustomerID.indexOf(key) > -1;
            };
            return clause; 
        }
        
		/**
		* 删除辅导计划
		*
		*/
		function delCoachMain(coachmainId){
			$.ligerDialog.confirm('您确定要删除该计划?', function (yes) { 
					if(yes){
					    $.ajax({
					 		url:"<%=path%>/controller/coach/deleteCoachMain.do",
					 		type:"post",
							data:{
									coachmainId:coachmainId			
							}, 
							dataType:"json",		
							success:function(result){
								if(result.status=="1"){
									$.ligerDialog.success('删除成功','提示',function(yes){
										f_search();
									});	
								}else{
								    $.ligerDialog.warn('删除失败!');
								}
							
							},
							error:function(error){
							   $.ligerDialog.warn('系统异常，请稍后再试!');
							}
					 });
				     }
				});
		}
		
		
		  /**
        *重置查询条件
        **/
        function reset(){
           $("#AcceptorWorkId").val("");  
           $("#starttime").val(today);
		   $("#endtime").val(toendday);
           $("#qcUserName").val("");
           $("#pass").val("");
        }
         function input_shuziyinwen(obj){
				obj.value=obj.value.replace(/[\W]/g,'');
			}
    </script> 
</head>
<body> 
	<div style="">
		<div class="title_div" style="width:100%; margin-top:0px; display:none;"><label style="margin-left:15px;">查询条件</label></div>
		<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;overflow: hidden;">
			<ul class="search_ul">
				<li>
					<label>质检员工号：</label>
					<input maxlength="50" type="text" onkeyup="input_shuziyinwen(this);" name="AcceptorWorkId"
						id="AcceptorWorkId" />
				</li>
				<li>
					<label>辅导对象：</label>
					<input maxlength="50" type="text" onkeyup="noSpecial(this)" name="qcUserName" id="qcUserName" />
				</li>
				<li>
					<label>辅导开始时间：</label>
					<input class="Wdate date_text" type="text" name="starttime" id="starttime"
						onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endtime\');}',dateFmt:'yyyy-MM-dd'})" />
				</li>
				<li>
					<label>辅导结束时间：</label>
					<input class="Wdate date_text" type="text" name="endtime"
						id="endtime" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'starttime\');}',dateFmt:'yyyy-MM-dd '})" />
				</li>
				<li>
					<label>辅导状态：</label>
					<select name="pass" id="pass" class="s_input">
						<option value="">全部</option>
						<option value="2">月计划待审核</option>
						<option value="1">月计划已通过</option>
						<option value="0">月计划未通过</option>
						<option value="5">辅导总结待审核</option>
						<option value="4">辅导总结已通过</option>
						<option value="3">辅导总结未通过</option>
					</select>
				</li>
				<li style="width:100%;text-align:center">
					<shiro:hasPermission name='dbcoach:query'>
						<input type="button" value="查询" onclick="f_search()" />
					</shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp; 
					<input type="button" value="重置" onclick="reset()" /></li>
			</ul>
		</div>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="coach_dudao_list" style="margin-top:10px;"></div>
	</div>
</body>
</html>
