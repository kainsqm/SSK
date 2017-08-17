<%@ page language="java" import="java.util.*,java.net.URLDecoder" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//String papername=new String(request.getParameter("userid").getBytes("UTF-8"),"GB2312");
String papername=URLDecoder.decode(URLDecoder.decode(request.getParameter("userid"),"UTF-8"), "utf-8"); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>客调项目管理系统</title>
		<!-- 查询员工信息 -->
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/core/base.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js"
			type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
		<style type="text/css">
		body{
		padding-right:10px}
div.title_div {
	border-radius: 5px;
	text-align: left;
	margin-top: 5px;
	background-image: url(<%=path%>/img/login/title_bg.jpg);
	border: solid 1px #D7D7D7;
	width: 500px;
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
	    var isfirst=0; //判断是否为第一次加载页面 主要是为了避免GRID查询时，又再次新加 选择的人
        var maingrid_gd=null;
        var checkedCustomer = [];
      //   var checkedWorkid= [];
       var checkedUserid=[];
       var checkedWorkId=[];
         var dialog=null;
		$(function () {
		  dialog= frameElement.dialog; //调用页面的dialog对象(ligerui对象)
		  var papers=$("#papers_hid").val();
		  if(papers!="null" && papers!=undefined && papers!="" && papers!=","){ //
			var paperArr = papers.split(",");
			var len = paperArr.length;
			for(var i=0;i<len-1;i++){
				var paper=new Object;
				paper.userId=paperArr[i].split('-')[0];
				paper.userName=paperArr[i].split('-')[1];
				paper.userWorkId=paperArr[i].split('-')[2];
				checkedUserid.push(paperArr[i].split('-')[0]);
				//alert("paper.userName"+paper.userName);
				checkedCustomer.push(paperArr[i].split('-')[1]);
				checkedWorkId.push(paperArr[i].split('-')[2]);
				createElLabel(paper);
			}
			if(papers.substring(0,1)!=","){			
				document.getElementById("papers_hid").value = ","+papers+",";
			}else{
				document.getElementById("papers_hid").value = papers;
			}
			//checkChboxs(papers,true);
		}else{
			document.getElementById("papers_hid").value = ",";
		}
		 // 
		// var dialogData = dialog.get('data');//获取data参数
		 
	//	 var useridlist=dialog.get('myDataName');
		 
	//	 var ss= dialogData.name;
		//  if(useridlist!=""){
		 //    checkedUserid=useridlist.split(',');
		  //}
		 
           maingrid_gd = $("#user_list").ligerGrid({
                columns: [               
				{ display: '工号', name: 'workId', align: 'left', width :255,isSort:false},
                { display: '姓名', name: 'userName', align: 'left', width :255,isSort:false}
                ], url:'<%=path%>/controller/task/queryManagerBaTask.do',
               // usePager : true,
                usePager: true, height:'300', width:"100%", pageSize:10 ,
                rownumbers :true,checkbox: true,
                isChecked: f_isChecked, onCheckRow: f_onCheckRow, onCheckAllRow: f_onCheckAllRow,where: f_getWhere,
               // where : f_getWhere(),
				
				onSuccess : function(data, grid) {
					var total = data.Total;
					if (total == 0) {
						$.ligerDialog.success("未查询到数据");
					}
				},onAfterShowData:function(data){
				  if(isfirst==0){
				     for (var rowid in maingrid_gd.records)
                     {
		                var userid=maingrid_gd.records[rowid]["userId"];
		                 for(var i =0;i<checkedUserid.length;i++)
                         {
                             if(userid==checkedUserid[i])
		                     {
		                         maingrid_gd.select(rowid);  
		                         if(findCheckedCustomer(maingrid_gd.records[rowid])==-1){
		                          // checkedCustomer.push(maingrid_gd.records[rowid]["userName"]);
					            // checkedUserid.push(data["userId"]);
					           //    checkedWorkid.push(maingrid_gd.records[rowid]["workId"]);
					             //  createElLabel(maingrid_gd.records[rowid]);
		                         }
		                       //  removeCheckedCustomer(maingrid_gd.records[rowid]);
	                         }
                          }
		               
                     }
                   }
                 //  isfirst=1;
				}
            });
            $("#pageloading").hide(); 
        });
        
        function f_search()
        {
            var parmItems=[
				{ name:"workId",value:$("#workId").val()},
				{ name:"userName",value:$("#userName").val()},
				{ name:"smailworkid",value:$("#smailworkid").val()}
				];
			maingrid_gd.options.parms=parmItems;
			maingrid_gd.options.page=1;
			maingrid_gd.options.newPage=1;
            //grid.loadServerData(parmItems);
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
        
        function f_onCheckAllRow(checked)
        {
            for (var rowid in this.records)
            {
                if(checked)
                    addCheckedCustomer(this.records[rowid]);//['userName']
                else
                    removeCheckedCustomer(this.records[rowid]);
            }
        }
 
        /*
        该例子实现 表单分页多选
        即利用onCheckRow将选中的行记忆下来，并利用isChecked将记忆下来的行初始化选中
        */
        
        function findCheckedCustomer(data)
        {
            for(var i =0;i<checkedUserid.length;i++)
            {
                if(checkedUserid[i] == data["userId"]) return i;
            }
            return -1;
        }
        function addCheckedCustomer(data)
        {
         //   var papers = document.getElementById("papers_hid");
            if(findCheckedCustomer(data) == -1){
               checkedCustomer.push(data["userName"]);
               checkedUserid.push(data["userId"]);
               checkedWorkId.push(data["workId"]);
             //  papers.value = papers.value + data["userId"]+"-"+data["userName"]+",";
           //    checkedWorkid.push(data["workId"]);
              // alert(data["userId"]);
               createElLabel(data);
               }
        }
        
        function createElLabel(paper){
			if(paper['userId']==undefined || paper['userId']=="")return;
			var pfset = document.getElementById("paperFieldset"); 
			var lb = document.createElement("label");
			lb.id = "chose"+paper['userId'];
			lb["data-paper"] = paper["userId"]+"-"+paper["userName"];
			lb.style.whiteSpace = "nowrap";
			lb.insertAdjacentHTML("afterBegin","&nbsp;&nbsp;<label style='border-bottom:1px solid #C5DAEF;'>"+paper["userName"]+"</label><img src='<%=path %>/img/delete.gif'' alt='点击删除' style='cursor:pointer' onclick='removePaper(this)' >");
			pfset.appendChild(lb);
	    }
	    
	    function removePaper(ts){
			//document.getElementsByName("checkAll")[0].checked=false;//去掉全选的勾
		//	var papers = document.getElementById("papers_hid").value;
			var pts = ts.parentElement;
			var pfset = document.getElementById("paperFieldset"); 
		//	document.getElementById("papers_hid").value = papers.replace(","+pts["data-paper"]+",",",");
			pfset.removeChild(pts);
			var id=pts.id;  //获取id
			var workid=id.substring(5,id.length);
			//maingrid_gd.unselect(workid);
			for (var rowid in maingrid_gd.records)
            {
                var rowworkid=maingrid_gd.records[rowid]["userId"];
                if(rowworkid==workid)
                  {
                    maingrid_gd.unselect(rowid);  
                    removeCheckedCustomer(maingrid_gd.records[rowid]);
                    return ;
                  }
            }
		//	checkChboxs(pts["data-paper"],faunselectlse);
		   
	    }	
        function removeCheckedCustomer(data)
        {
            var i = findCheckedCustomer(data);
            if(i==-1) return;
            checkedCustomer.splice(i,1);
           // checkedWorkid.splice(i,1);
            checkedUserid.splice(i,1);
            checkedWorkId.splice(i,1);
            //var papers = document.getElementById("papers_hid");
           // papers.value = papers.value.replace(","+data['userId']+"-"+data["userName"]+",",",");
            var pts = document.getElementById("chose"+data['userId']);
			var pfset = document.getElementById("paperFieldset"); 
		//	document.getElementById("papers_hid").value = papers.replace(","+pts["data-paper"]+",",",");
			pfset.removeChild(pts);
			
        }
        function f_isChecked(rowdata)
        {
            if (findCheckedCustomer(rowdata) == -1)
                return false;
            return true;
        }
        function f_onCheckRow(checked, data)
        {
            if (checked) addCheckedCustomer(data);
            else removeCheckedCustomer(data);
        }
        
        
        function f_getChecked()
        {
            if(checkedUserid.length>100){
				$.ligerDialog.warn("所选受理员数量不能超过100人！");
				return false;
			}
            window.parent.document.getElementById("TEL_USER_NAME").value=checkedCustomer.join(',');
            window.parent.document.getElementById("TEL_USER").value=checkedUserid.join(',');
            window.parent.document.getElementById("TEL_USER_WORKID").value=checkedWorkId.join(',');            
            window.parent.document.getElementById("telNumber2").innerHTML=checkedUserid.length;
           var papersvalue="";
         
           for(var i =0;i<checkedUserid.length;i++)
            {
               papersvalue +=checkedUserid[i]+"-"+checkedCustomer[i]+"-"+checkedWorkId[i]+",";
            }
            window.parent.document.getElementById("papers_hid").value=papersvalue;
            parent.showErrorAorBMsg();
           // $("#TEL_USER_NAME",window.parent).val(checkedCustomer.join(','));
            //$("#TEL_USER",window.parent).val(checkedUserid.join(','));
            dialog.close();//关闭dialog 
            
          //  alert(checkedCustomer.join(','));
        }
        
    </script>
	</head>
	<!-- 
<body style="margin:0 auto; background-color:#FFFFFF; padding:5px;">
 -->
	<body>
		<div style="">
		    <input type="hidden" id="papers_hid"  value="<%=papername%>"/>
			<div class="title_div"
				style="width: 100%; margin-top: 0px; display: none;">
				<label style="margin-left: 15px;">
					查询条件
				</label>
			</div>
			<div align="left"
				style="margin-left: 5px; padding-left: 10px;">
				<form id="formserch" method="post">
					<table id="search_table" cellpadding="3" cellspacing="3"
						width="100%">
						<td class="td_lable">
							工号：
						</td>
						<td class="td_value">
							<input id="workId" type="text"/>
						</td>
						<td class="td_lable">
							姓名：
						</td>
						<td class="td_value">
							<input id="userName" type="text"/>
						</td>
						<td class="td_lable">
							所属中心:
						</td>
						<td class="td_value">
						<select id="smailworkid">
							    <option value="">
									--全部--
								</option>
								<option value="1">
									浦东
								</option>
								<option value="2">
									逸仙
								</option>
								<option value="3">
									新华
								</option>
							</select>
						</td>
						</tr>
						<tr>
							<td colspan="8" align="center">
								<input type="button" value="查询" onclick="f_search()" />
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" value="提交" onclick="f_getChecked()" />
								&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="l-loading" style="display: block" id="pageloading"></div>
			<div id="user_list" style="margin-top: 10px;"></div>
			
		</div>
		<fieldset id="paperFieldset" style="width: 99%;text-align: left;height:20px;">
			   <legend style="font-size: 12px;font-weight: bold;">已选择的员工</legend>
		    </fieldset>
	</body>
</html>