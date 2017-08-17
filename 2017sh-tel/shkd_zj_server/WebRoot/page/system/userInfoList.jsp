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
    <link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=path%>/js/util.js" type="text/javascript"></script>
	<style type="text/css">
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(<%=path%>/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input.date_text{border-radius:5px; width:140px; height:18px; line-height:18px;}
		
		table#search_table td{ height:30px; line-height:30px;}
		table#search_table td.td_lable{ text-align:right;}
		table#search_table td.td_value{ text-align:left;}
		div.l-panel-topbar{ height:30px !important; }
	</style>
	<script type="text/javascript"> 
		var manager;
		var dataFromData = [{ dataFrom: 1, text: '接口' }, { dataFrom: 2, text: '系统添加'}];
        $(function ()
        {
            	loadData();
				$.ajax({
				url:'<%=path%>/controller/user/listgruoupName.do',
				type:'post',
				success:function(data){
					var res=eval("("+data+")");
					 $("#groupName").append("<option value=''>---请选择---</option>");
					 $.each(res.listgroup,function(index,value){
	                        $("#groupName").append("<option value='"+value.groupName+"'>"+value.groupName+"</option>");
	                 });
				},
				error:function(){
					$.ligerDialog.error('加载数据异常，请稍后再试！');
				}
			});
        });
        
        
        function loadData(){
            manager = $("#workpaper_list").ligerGrid({
                columns: [
                    { display: '操作', isSort: false, name:'workId', width : 180, align: 'left' , isSort: false,render: function (rowdata, rowindex, value)
						{
							var h = "";
							if(rowdata.dataFrom=="2"){
								h += "<shiro:hasPermission name='user:upd'><a href='javascript:void(0);' onclick='eidt_user(\""+value+"\");'>编辑</a> </shiro:hasPermission>";
								h += "<shiro:hasPermission name='user:del'><a href='javascript:void(0);' onclick='deleteUser(\""+value+"\",\""+rowdata.userId+"\");'>删除</a></shiro:hasPermission> ";
								h += "<shiro:hasPermission name='user:resetpass'><a href='javascript:void(0);' onclick='resetPwd(\""+value+"\");'>重置密码</a></shiro:hasPermission> ";
								h += "<shiro:hasPermission name='user:rolefp'><a href='javascript:void(0);' onclick='changeRoleInfo(\""+rowdata.userId+"\",\""+rowdata.role_id+"\");'>角色分配</a></shiro:hasPermission>";
							}else if(rowdata.dataFrom =="1" && rowdata.userName !=""){
							    h += "<shiro:hasPermission name='user:resetpass'><a href='javascript:void(0);' onclick='resetPwd(\""+value+"\");'>重置密码</a></shiro:hasPermission> ";
								h += "<shiro:hasPermission name='user:rolefp'><a href='javascript:void(0);' onclick='changeRoleInfo(\""+rowdata.userId+"\",\""+rowdata.role_id+"\");'>角色分配</a></shiro:hasPermission>";
							}
							return h;
						}
					},
					{ display: '姓名', name: 'userName',  minWidth: 110, align: 'left' , isSort: false},
					{ display: '工号', name: 'workId',id: 'id1', minWidth: 80, align: 'left', isSort: false },
					{ display: '组室', name: 'groupName', minWidth: 100, align: 'left' , isSort: false},
					{ display: '岗位', name: 'job', minWidth: 80, align: 'left', isSort: false },
					{ display: '录入渠道', name: 'dataFrom', minWidth: 80, align: 'left', isSort: false ,
					 editor: { type: 'select', data: dataFromData, valueField: 'dataFrom'},
                     render: function (item)
                    {
                        if (parseInt(item.dataFrom) == 1) return '接口';
                         return '系统添加';
                     }
					},
					{ display: '角色', name: 'rolename', minWidth: 170, align: 'left', isSort: false }					
                ], 
                url:'<%=path%>/controller/user/getuserlist.do',
				sortName: 'workId',
				height:'100%', 
				width:"auto", 
				pageSize:10 ,
				rownumbers :true,
				alternatingRow: false,
				tree: { columnId: 'id1' }
            });  
			collapseAll();
			$("#pageloading").hide(); 
        }
        
        
        function collapseAll()
        {
            manager.collapseAll();
        }
        function expandAll()
        {
            manager.expandAll();
        }
		function itemclick(item)
        {
            alert(item.text);
        }
		/*********************
		 * 员工信息编辑
		 *********************/
		function eidt_user(editType){
			var titleInfo="弹出窗口";
			if(editType=="add"){
			titleInfo="员工信息新增";
				$.ligerDialog.open({
				height:445,
				width: 350,
				title : titleInfo,
				url:'<%=path%>/controller/user/listRole.do?tz=add', 
				showMax: false,
				showToggle: false,
				showMin: false,
				isResize: true,
				slide: false
			});
			}else{
			titleInfo="员工信息修改";
			$.ligerDialog.open({
				height:420,
				width: 350,
				title : titleInfo,
				url:'<%=path%>/controller/user/userInfobyworid.do?workId='+editType, 
				showMax: false,
				showToggle: false,
				showMin: false,
				isResize: true,
				slide: false
			});
			}
		
		
		}
		/*********************
		 * 删除员工信息
		 *********************/
		function deleteUser(workId,userID){
			$.ligerDialog.confirm('确认要删除员工['+workId+']吗？', function (yes) { 
				if(yes){
					$.ajax({
							url:"<%=path%>/controller/user/deluserInfo.do",
							type:"post",
							data:{workId:workId,
								  userId:userID
									},
							dataType:"json",
							success:function(result){
							if(result.blag){
							  $.ligerDialog.success('删除成功','提示',function(yes){
							  user_search();
							  });	  
							}else{
							  $.ligerDialog.warn('删除失败');
							}
							},
							error:function(result, e){
							  $.ligerDialog.warn('删除失败');
							}
					
					});
					
				} 
			});
		}
		/*********************
		 * 重置员工密码
		 *********************/
		function resetPwd(workId){
			$.ligerDialog.confirm('确认要重置员工['+workId+']的密码吗？', function (yes) { 
				if(yes){
				$.ajax({
							url:"<%=path%>/controller/user/resetPasswd.do",
							type:"post",
							data:{workId:workId,
									},
							dataType:"json",
							success:function(result){
							if(result.blag){
							  $.ligerDialog.success('重置成功');
							}else{
							  $.ligerDialog.warn('重置失败');
							}
							},
							error:function(result, e){
							  $.ligerDialog.warn('重置失败')
							}
					
					});
				} 
			});
		}
		/*********************
		 * 员工信息模板下载
		 *********************/
		function downUserTemplate(){
			$.ligerDialog.confirm('确认要下载员工信息模板吗？', function (yes) { 
				if(yes){	
		 		window.location.href ='<%=path%>/controller/user/downTemplate.do';
				} 
			});
		}
		/*********************
		 * 员工信息导入
		 *********************/
		function import_user_info(){
			$.ligerDialog.open({
				height:240,
				width: 570,
				title : '员工信息批量导入',
				url:'<%=path%>/controller/user/toimportUser.do', 
				showMax: false,
				showToggle: false,
				showMin: false,
				isResize: true,
				slide: false
			});
		}
		/*********************
		 * 角色信息分配
		 *********************/
		function changeRoleInfo(userID,role_id){
			$.ligerDialog.open({
				height:375,
				width: 340,
				title : '角色信息分配',
				url:"<%=path%>/controller/user/listRole.do?role_id="+role_id+"&userID="+userID, 
				showMax: false,
				showToggle: true,
				showMin: false,
				isResize: true,
				slide: false
			});
		}
		
		  function resets(){
            document.getElementById("workId").value = "";  
            document.getElementById("userName").value = "";
            document.getElementById("groupName").value = "";             
            }
            
           function user_search(){
        var parmItems=[{ name:"workId",value:$("#workId").val()},
		               { name:"userName",value:$("#userName").val()},
		               { name:"groupName",value:$("#groupName").val()}
		                    		];
			manager.options.parms=parmItems;
			manager.options.usePager =true;
			manager.options.page='1';
			manager.options.newPage=1;
		    manager.options.url ='<%=path%>/controller/user/getuserlist.do';
        	manager.loadData(f_getWhere());
        } 
        
         //回车提交
	    function keydown(){
	    	if(isEnter()){
	    		user_search();
	    	}
		}
        
        function input_shuziyinwen(obj){
				obj.value=obj.value.replace(/[\W]/g,'');
			}
        
           function  f_getWhere() {
			if (!manager)
				return null;
			var clause = function(rowdata, rowindex) {
				var workId = $("#workId").val();
				return rowdata.recordId.indexOf(workId) > -1;
			};
			return clause;
		}       
    </script> 
</head>
<body> 
	<div style="" onkeydown="keydown();">
		<div class="title_div" style="width:100%; margin-top:0px; display:none;"><label style="margin-left:15px;">查询条件</label></div>
		<div align="left" style="margin-top:10px; margin-left:5px; padding-left:10px;">
			<table id="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr>
					<td>
						大工号：<input type="text" maxlength="50" onkeyup="input_shuziyinwen(this);"  id="workId"  name="workId"/>&nbsp;&nbsp;&nbsp;&nbsp;
						姓名：<input type="text" maxlength="50" onblur="noSpecial(this);" onkeyup="noSpecial(this)"  id="userName" name="userName" />&nbsp;&nbsp;&nbsp;&nbsp;
						组室：<select name="groupName" id="groupName" >
					
					</select> &nbsp;&nbsp;&nbsp;&nbsp; 
						<shiro:hasPermission name='user:query'><input type="button" value="查询"  onclick="user_search()" /></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="重置"   onclick="resets()" />&nbsp;&nbsp;&nbsp;&nbsp;
						<shiro:hasPermission name='user:add'><input type="button" value="新增" onclick="eidt_user('add');" /></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp;
						<shiro:hasPermission name='user:tempdown'><input type="button" value="员工模板下载" onclick="downUserTemplate();" /></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp;
						<shiro:hasPermission name='user:infoimport'><input type="button" value="员工信息导入" onclick="import_user_info();" /></shiro:hasPermission>
					</td>					
				</tr>
			</table>			
		</div>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="workpaper_list" style="margin-top:10px;"></div>
	</div>
</body>
</html>
