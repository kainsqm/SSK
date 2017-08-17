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
	<title></title>
	<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <script src="<%=path %>/js/jquery-1.9.1.js" type="text/javascript" charset="utf-8"></script>
    <link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
    <script src="<%=path %>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="<%=path %>/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="<%=path %>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="<%=path %>/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="<%=path%>/js/util.js" type="text/javascript"></script>
    <script language="javascript" type="text/javascript" src="<%=path %>/js/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input.date_text{border-radius:5px; width:135px; height:18px; line-height:18px;}
		table#search_table td{ height:30px; line-height:30px;}
		table#search_table td.td_lable{ text-align:right;}
		table#search_table td.td_value{ text-align:left;}
	</style>
	<script type="text/javascript"> 
		  var maingrid_gd;
		$(function () {
           loadData();
        });
        
        
        function loadData(){
             maingrid_gd= $("#workpaper_list").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false, width : 100, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                    h +="<shiro:hasPermission name='role:upd'><a href='javascript:void(0);' onclick='eidt_role(\""+rowdata.roleId+"\");'>修改</a></shiro:hasPermission>&nbsp;";
						h +="<shiro:hasPermission name='role:del'><a href='javascript:void(0);' onclick='deleteRoleInfo(\""+rowdata.roleId+"\",\""+rowdata.roleName+"\");'>删除</a></shiro:hasPermission>";
	                    return h;
	                }
                },
				{ display: '角色名称', name: 'roleName', align: 'left', minWidth :130,isSort:false},
				{ display: '所属系统', name: 'systemName', align: 'left', minWidth :130,isSort:false},
                { display: '角色标识', name: 'roleFlag', align: 'left', minWidth :100,isSort:false},
				{ display: '创建时间', name: 'createTime', align: 'left', minWidth :80,isSort:false}
				], 
				url:'<%=path%>/controller/role/getrolelist.do',
				sortName: 'createTime',
				height:'100%', 
				width:"auto", 
				pageSize:10 ,
				rownumbers :true,
				alternatingRow: false,
            });
            $("#pageloading").hide(); 
        }
        
        
        function f_search() {
			var parmItems=[{ name:"rolename",value:$("#rolename").val()},
			               { name:"system",value:$("#system").val()},
			               { name:"roleflag",value:$("#roleflag").val()}];
				maingrid_gd.options.parms=parmItems;
				maingrid_gd.options.usePager =true;
				maingrid_gd.options.page='1';
				maingrid_gd.options.newPage=1;
			    maingrid_gd.options.url ='<%=path%>/controller/role/getrolelist.do';//必须这么写
			    maingrid_gd.loadData(f_getWhere());
		}
        
          function  f_getWhere() {
			if (!maingrid_gd)
				return null;
			var clause = function(rowdata, rowindex) {
				var rolename = $("#rolename").val();
				return rowdata.recordId.indexOf(rolename) > -1;
			};
			return clause;
		}   
		/*********************
		 * 删除角色信息
		 *********************/
		function deleteRoleInfo(roleID,roleName){
			$.ligerDialog.confirm('确认要删除角色['+roleName+']吗？', function (yes) { 
				if(yes){
					$.ajax({
						type:"post",
						url:"<%=path%>/controller/role/deleteRole.do",
						data:{roleId:roleID,
							  rolename:roleName
							},
						dataType:"json",
						success:function(result){
							if(result.flag=="删除成功"){
								$.ligerDialog.success('删除成功','提示',function(yes){
								    f_search();
								});
							}else{
							 	$.ligerDialog.warn(result.flag);
							}	
						}
					});
				}	 
			});
		}
		/*********************
		 * 角色信息编辑
		 *********************/
		function eidt_role(roleId){
			$.ligerDialog.open({
				height:340,
				width: 850,
				title : "角色信息修改",
				url:'<%=path%>/controller/role/toUpdateRolePage.do?roleId='+ roleId,
				showMax : false,
				showToggle : false,
				showMin : false,
				isResize : true,
				slide : false
			});
		}

		function add_role() {
			$.ligerDialog.open({
				height : 340,
				width : 850,
				title : "角色信息新增",
				url : '<%=path%>/controller/role/toAddRolePage.do', 
				showMax: false,
				showToggle: false,
				showMin: false,
				isResize: true,
				slide: false
			});
		}
		
		function f_reset(){
		  $("#rolename").val("");
		  $("#system").val("");
		  $("#roleflag").val("");
		}
		
		
		function changeSystem(){
		    var system=$("#system").val();
		    if(system==""){
		       $("#roleflag").empty();
			   $("#roleflag").append("<option value=''>--请选择--</option>");
		    }else{
		        $.ajax({
            		url:'<%=path%>/controller/role/queryRoleByFlag.do',
					data:{system:system},
					type:'post',
					dataType:'json',
					success:function(data){
						$("#roleflag").empty();
						$("#roleflag").append("<option value=''>--请选择--</option>");
						$.each(data.rolelist,function(index,value){
						    $("#roleflag").append("<option value='"+value.valuees+"'>"+value.name+"</option>");
						});		
					},
               		error:function(){
					    $.ligerDialog.error('加载数据异常，请稍后再试！');
					}
            });	
		    } 
		    
		}
		
		
		  //回车查询
	    function keydown(){
	    	if(isEnter()){
	    		f_search();
	    	}
		}
    </script> 
</head>
<body > 
	<div onkeydown="keydown();">
		<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
			<table id="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr>
					<td>
						角色名称：<input id="rolename" type="text" maxlength="20"/>&nbsp;&nbsp;&nbsp;&nbsp;
						所属系统：<select id="system" onchange="changeSystem()">
						     <option value="">--请选择--</option>
					         <c:forEach items="${codeList}" var="list">
						        <option value="${list.code}">${list.roleName}</option>		  
			                 </c:forEach>
					        </select>&nbsp;&nbsp;&nbsp;&nbsp;
					           角色标识：<select id="roleflag">
					          <option value="">--请选择--</option>
					        </select>&nbsp;&nbsp;&nbsp;&nbsp;
						<shiro:hasPermission name='role:query'><input type="button" value="查询"  onclick="f_search()"/></shiro:hasPermission>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="重置" onclick="f_reset()"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<shiro:hasPermission name='role:add'><input type="button" value="新增" onclick="add_role();" /></shiro:hasPermission>
					</td>					
				</tr>
			</table>			
		</div>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="workpaper_list" style="margin-top:10px;"></div>
	</div>
</body>
</html>
