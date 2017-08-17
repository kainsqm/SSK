<%@ page language="java" contentType="text/html;charset=UTF-8"%>  
<%@ taglib uri="/WEB-INF/shiro.tld" prefix="shiro"%> 
<%
  	String path = request.getContextPath();
    //String role=request.getSession().getAttribute("roleflag").toString();
    String role=request.getAttribute("role_flag").toString();
%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<title>客调质量检查系统</title>
	<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path%>/js/util.js" ></script>
   <%--  <script src="<%=path%>/CustomersData.js" type="text/javascript"></script> --%>
   <!--  <script src="../js/plugins/ligerCheckBox.js" type="text/javascript"></script> -->
	<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		body{ font-size:12px;}
		.head_h3{ width:100%; height:36px; line-height:36px; background:url(<%=path%>/img/login/toppic.jpg); margin-top:-3px;}
		.head_h3 h3{ color:#ffffff; margin-left:70px; font-size:16px;}
		
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(<%=path%>/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input[type='button']{ border:#d3d3d3 1px solid; width:80px; height:25px; cursor:pointer;}
		input.date_text{border-radius:5px; width:140px; height:18px; line-height:18px;}
		
		table#search_table td{ height:30px; line-height:30px;}
		table#search_table td.td_lable{ text-align:right;}
		table#search_table td.td_value{ text-align:left;}
		.l-grid-body1 .l-grid-body-inner .l-grid-body-table div{color:#333}
	</style>
	<script type="text/javascript">
	var dealstateData = [{ dealstate: 1, text: '待受理员处理' }, { dealstate: 2, text: '受理员已处理'},{ dealstate: 3, text: '待受理员班长处理'},{ dealstate: 4, text: '受理员班长已处理'},{ dealstate: 5, text: '待质检员处理'},{ dealstate: 6, text: '质检员已处理'}];
	 var maingrid_gd=null;
	 var today;
	 var toendday;
	     $(function(){
	      var d = new Date();	
			var year = d.getFullYear();
			var month = d.getMonth() + 1; // 记得当前月是要+1的
			var dt = d.getDate();
			if(dt<10){
			 toendday = year + "-" + month + "-0" +dt+" 23:59:59";
			}else{
			 toendday = year + "-" + month + "-" +dt+" 23:59:59";
			}
			 today = year + "-" + month + "-01"+" 00:00:00";	
			$("#startqcworkpapertime").val(today);
			$("#stopqcworkpapertime").val(toendday);
	       maingrid_gd= $("#workpaper_list").ligerGrid({
                columns: [                
				{ display: '操作', isSort: false,name:'agentworkpaperid', width : 180, render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                    h += "<shiro:hasPermission name='paper:chul'><a href='javascript:void(0);' onclick='pingfen(\""+value+"\");'>反馈单详情</a> </shiro:hasPermission> ";
						return h;
	                }
                },
				{ display: '受理员工号', name: 'agentworkid', align: 'left', Width :100,isSort:false},
				{ display: '质检编号', name: 'qcid', align: 'left', width :100,isSort:false},
				{ display: '组室', name: 'groupName', align: 'left', minWidth :80,isSort:false},	
                { display: '反馈单编号', name: 'agentworkpaperid', align: 'left', Width :100,isSort:false},
				{ display: '反馈单时间', name: 'qcworkpapertime', align: 'left', Width :100,isSort:false},
				{ display: '反馈流程状态', name: 'dealstate', align: 'left', Width :130,isSort:false,
				  render: function (item)
                    {
                        if (parseInt(item.dealstate) == 1) return '待受理员处理';
                        if (parseInt(item.dealstate) == 2) return '受理员已处理';
                        if (parseInt(item.dealstate) == 3) return '待受理员班长处理';
                        if (parseInt(item.dealstate) == 4) return '受理员班长已处理';
                        if (parseInt(item.dealstate) == 5) return '待质检员处理';
                        if (parseInt(item.dealstate) == 6)  return '质检员已处理';
                     }
				},
				{ display: '质检类型', name: 'qc_type', align: 'left', minWidth :80,isSort:false,
				render : function(rowdata, rowindex, value) {
					var result="";
					 if (parseInt(value) == 1) return '录音评分';
                     if (parseInt(value) == 2) return '112工单评分';
                     if (parseInt(value) == 3) return 'c网工单评分';
                     if (parseInt(value) == 4) return '112电话小结';
                     if (parseInt(value) == 5) return 'c网电话小结';
					 if (parseInt(value) == 6) return '无工单/录音';
                     if (parseInt(value) == 7) return '录音+112工单';
                     if (parseInt(value) == 8) return '录音+c网工单';
                     if (parseInt(value) == 9) return '录音+112小结';
                     if (parseInt(value) == 10) return '录音+c网小结';
					return result;
					}
				}	
                ],     
                
                url:'<%=path%>/controller/agent/getAgentzcList.do',
                parms:[{ name:"agentworkid",value:$("#agentworkid").val()},
                	   {name:"startqcworkpapertime",value:$("#startqcworkpapertime").val()},
		               { name:"stopqcworkpapertime",value:$("#stopqcworkpapertime").val()}
		               ],
                sortName: 'agentworkpaperid',    
                usePager :true,
                height:'100%', 
                width:"auto", 
                pageSize:10 ,
                rownumbers :true,
                rowAttrRender: function (rowdata,rowindex){
                if(rowdata.dealstate==1&&'<%=role%>'=='role_1'){         
              	 return "style='color:red;'";
                }else if(rowdata.dealstate==3&&'<%=role%>'=='role_2'){ 
                return "style='color:red;'";
                }      
                } 
            });
            $("#pageloading").hide(); 
            $.ajax({
				url:'<%=path%>/controller/user/listgruoupName.do',
				type:'post',
				success:function(data){
					var res=eval("("+data+")");
					 $("#groupName").append("<option value=''>---请选择---</option>");
					 $.each(res.listgroup,function(index,value){
	                        $("#groupName").append("<option value='"+value.groupName+"'>"+value.groupName+"</option>");
	                 })
				},
				error:function(){
					$.ligerDialog.error('加载数据异常，请稍后再试！');
				}
			});
	       });
	       
	       
	        function agent_search(){
        var parmItems=[{ name:"agentworkid",value:$("#agentworkid").val()},
		               { name:"dealstate",value:$("#dealstate option:selected").val()},
		               { name:"startqcworkpapertime",value:$("#startqcworkpapertime").val()},
		               { name:"stopqcworkpapertime",value:$("#stopqcworkpapertime").val()},
		               { name:"groupName",value:$("#groupName").val()},
		               { name:"qc_type",value:$("#qc_type").val()},
		               {name:"qc_id",value:$("#qc_id").val()}        
		                    		];
			maingrid_gd.options.parms=parmItems;
			maingrid_gd.options.usePager =true;
			maingrid_gd.options.page='1';
			maingrid_gd.options.newPage=1;
		    maingrid_gd.options.url ='<%=path%>/controller/agent/getAgentzcList.do';
        	maingrid_gd.loadData(f_getWhere());
        } 
	         function  f_getWhere() {
			if (!maingrid_gd)
				return null;
			var clause = function(rowdata, rowindex) {
				var agentworkpaperid = $("#agentworkpaperid").val();
				/* var key = $("#txtKey").val();
				var key = $("#txtKey").val();
				var key = $("#txtKey").val();
				var key = $("#txtKey").val();
				var key = $("#txtKey").val();
				var key = $("#txtKey").val(); */
				return rowdata.recordId.indexOf(agentworkpaperid) > -1;
			};
			return clause;
		}
	       
	       	function  resets(){
			document.getElementById("dealstate").value = "";
			document.getElementById("startqcworkpapertime").value = today;
			document.getElementById("stopqcworkpapertime").value = toendday;
			document.getElementById("groupName").value = "";
			document.getElementById("qc_type").value = "";
			document.getElementById("qc_id").value = "";			
			
		}
	        function input_shuziyinwen(obj){
				obj.value=obj.value.replace(/[\W]/g,'');
			}	
				 //回车提交
	    function keydown(){
	    	if(isEnter()){
	    		user_search();
	    	}
		}
		
		function pingfen(agentid){//agentid
		 var form=$("<form>");//定义一个form表单
			 form.attr("style","display:none");
			 form.attr("target","_blank");
			 form.attr("id","pinfenform");
			 form.attr("method","post");
			 form.attr("action","<%=path%>/controller/agent/Agentbyid.do");
			 var inputagentid=$("<input>"); //agentid
			 inputagentid.attr("type","hidden");
			 inputagentid.attr("name","agentid");
			 inputagentid.attr("value",agentid);
			 $("body").append(form);//将表单放置在body中
			 form.append(inputagentid);
			 form.submit();//表单提交
			 $("#formid").remove();
		}
		
	</script>
</head>

<body>
		<div class="title_div" onkeydown="keydown();" style="width:100%; margin-top:0px; display:none;"><label style="margin-left:15px;">查询条件</label></div>
			<table id="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr>
					<td class="td_lable">工单开始时间：</td><td class="td_value"><input id="startqcworkpapertime" name="startqcworkpapertime" class="Wdate date_text" type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'stopqcworkpapertime\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
					<td class="td_lable">工单结束时间：：</td><td class="td_value"><input id="stopqcworkpapertime" name="stopqcworkpapertime" class="Wdate date_text" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startqcworkpapertime\');}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
					<td class="td_lable">反馈流程状态：</td><td class="td_value"><select name="dealstate" id="dealstate" >
					<option value="" style="text-align: center;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;----请选择----&nbsp;&nbsp;&nbsp;&nbsp;</option>
					<option value="1">待受理员处理</option>
					<option value="2">受理员已处理</option>
					<option value="3">待受理员班长处理</option>
					<option value="4">受理员班长已处理</option>
					<option value="5">待质检员处理</option>
					<option value="6">质检员已处理</option>
					</select></td>
					 <c:if test="${requestScope.role_flag=='role_2'}">
					<td class="td_lable">受理员工号：</td><td class="td_value"><input type="text" maxlength="50" onkeyup="input_shuziyinwen(this);"  id="agentworkid" name="agentworkid" /></td>
					</c:if>
					 <c:if test="${requestScope.role_flag=='role_1'}">
					<td class="td_lable">受理员工号：</td><td class="td_value"><input type="text" maxlength="50" onkeyup="input_shuziyinwen(this);" value="${requestScope.userworkid}" disabled="disabled"  id="agentworkid" name="agentworkid" /></td>
					</c:if> 
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
					<td class="td_lable">组室：</td><td class="td_value"><select name="groupName" id="groupName" >	
					</select></td>
					<td class="td_lable">质检编号：</td>
						<td class="td_value" >
							<input type="text" id="qc_id" name="qc_id" onkeyup="input_shuziyinwen(this);strlenLimit(this,20);"/>				
				</td>
					</tr>
				<tr>
					<td colspan="8" align="center">
						<shiro:hasPermission name='csrpaper:query'>
						<input type="button" value="查询" onclick="agent_search()" />&nbsp;&nbsp;&nbsp;&nbsp;
						</shiro:hasPermission>
						<input type="button" value="重置" onclick="resets()" />
					</td>
				</tr>
			</table>			
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="workpaper_list" style="margin-top:10px;"></div>
	
</body>
</html>
