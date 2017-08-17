<%@ page language="java" contentType="text/html;charset=UTF-8"%>  
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%> 
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>
<%
  String path = request.getContextPath();
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>客调项目管理系统</title>
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <script src="<%=path %>/js/jquery-1.9.1.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="<%=path%>/js/util.js" type="text/javascript"></script>
    <script src="<%=path%>/js/utils.js" type="text/javascript"></script>
    <link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
	<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(<%=path%>/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input.date_text{border-radius:5px; width:140px; height:18px; line-height:18px;}
		
		table#search_table td{ height:30px; line-height:30px;}
		table#search_table td.td_lable{ text-align:right;}
		table#search_table td.td_value{ text-align:left;}
	</style>
	<script type="text/javascript"> 
	var maingrid_gd="";
	var startoperateDate;
	var stopoperateDate;
		$(function () {
		curTime=getCurDateform2();
		$("#startoperateDate").val(curTime+" 00:00:00");
		$("#stopoperateDate").val(curTime+" 23:59:59");
		startoperateDate=curTime+" 00:00:00";
		stopoperateDate=curTime+" 23:59:59";
           maingrid_gd= $("#workpaper_list").ligerGrid({
                columns: [                
				{ display: '操作时间', name: 'operateDate', align: 'left', minWidth :100,isSort:false},
                { display: '操作人工号', name: 'workId', align: 'left', minWidth :100,isSort:false},
				{ display: '操作模块', name: 'moduleName', align: 'left', minWidth :80,isSort:false},
				{ display: '操作类型', name: 'functionName', align: 'left', minWidth :80,isSort:false},
				{ display: '操作内容', name: 'operateContent', align: 'left', minWidth :200,isSort:false,render: function (rowdata, rowindex, value)
	                {
	                    var h = "<span class='span' title='"+value+"' style='overflow: hidden;text-overflow: hidden;display: block;text-overflow: ellipsis; white-space: nowrap;'>"+value+"<span>";
	                    return h;
	                }
				},
				{ display: 'IP', name: 'ip', align: 'left', minWidth :80,isSort:false}
				], 
				url:'<%=path%>/controller/operlog/getOperlist.do',
				 parms :[{name:"startoperateDate",value:$("#startoperateDate").val()},
                        {name:"stopoperateDate",value:$("#stopoperateDate").val()},
                        {name:"system",value:$("#system").val()}
                        ],  
                sortName: 'operateDate',    
                usePager :true,
                height:'100%', 
                width:"auto", 
                pageSize:10 ,
                rownumbers :true	
            });
            $("#pageloading").hide(); 
        });
        
        function agent_search(){
        var parmItems=[{ name:"workId",value:$("#workId").val()},
                       {name:"system",value:$("#system").val()},
		               { name:"startoperateDate",value:$("#startoperateDate").val()},
		               { name:"stopoperateDate",value:$("#stopoperateDate").val()}
		                    		];
			maingrid_gd.options.parms=parmItems;
			maingrid_gd.options.usePager =true;
			maingrid_gd.options.page='1';
			maingrid_gd.options.newPage=1;
		    maingrid_gd.options.url ='<%=path%>/controller/operlog/getOperlist.do',
        	maingrid_gd.loadData(f_getWhere());
        } 

        function  f_getWhere() {
			if (!maingrid_gd)
				return null;
			var clause = function(rowdata, rowindex) {
				var workId = $("#workId").val();
				return rowdata.recordId.indexOf(workId) > -1;
			};
			return clause;
		}
        function resets(){
         document.getElementById("workId").value = "";
         document.getElementById("startoperateDate").value =startoperateDate;  
         document.getElementById("stopoperateDate").value = stopoperateDate;  
        }
          //回车提交
	    function keydown(){
	    	if(isEnter()){
	    		agent_search();
	    	}
		}
          function input_shuziyinwen(obj){
				obj.value=obj.value.replace(/[\W]/g,'');
			}
    </script> 
</head>

<body> 
	<div style="" onkeydown="keydown();" >
		<div class="title_div" style="width:100%; margin-top:0px; display:none;"><label style="margin-left:15px;">查询条件</label></div>
		<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
			<table id="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr>
					<td>
					         系统：<select id="system">
					         <c:forEach items="${codeList}" var="list">
						        <option value="${list.valuees}">${list.name}</option>		  
			                 </c:forEach>
					        </select>&nbsp;&nbsp;&nbsp;&nbsp;
						操作人工号：<input id="workId" name="workId" type="text" maxlength="50"  onkeyup="input_shuziyinwen(this);" />&nbsp;&nbsp;&nbsp;&nbsp;
						操作时间：<input class="Wdate date_text" id="startoperateDate" name="startoperateDate" 
						type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'stopoperateDate\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />&nbsp;-&nbsp;
						<input class="Wdate date_text" id="stopoperateDate" name="stopoperateDate" 
						type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startoperateDate\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
						&nbsp;&nbsp;&nbsp;&nbsp;
						<shiro:hasPermission name='log:query'><input type="button" value="查询" onclick="agent_search()"/></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="重置" onclick="resets()"/>
					</td>					
				</tr>
			</table>			
		</div>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="workpaper_list" style="margin-top:10px;"></div>
	</div>
</body>
</html>
