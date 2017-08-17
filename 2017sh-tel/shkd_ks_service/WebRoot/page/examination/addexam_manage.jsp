<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt"  uri="/WEB-INF/fmt-rt.tld"%> 
<%@ taglib prefix="c"  uri="/WEB-INF/c.tld"%> 
<%@taglib prefix="shiro" uri="/WEB-INF/shiro.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<title>考试维护管理</title>
	<link href="${ctx}/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script> 
    <script src="${ctx}/lib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerListBox.js" type="text/javascript"></script>
    <script src="${ctx}/js/ajaxSession.js" type="text/javascript"></script>
    <script src="${ctx}/page/examination/js/exam_manage.js" type="text/javascript"></script>
    <script src="${ctx}/js/utils/utils.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		.middle input { display: block;width:30px; margin:2px;}
		.listbox {margin:4px;float:left;}
		.listbox td{height:15px; line-height:15px; }
		
		div.title_div{border-radius: 5px; text-align:left; margin-top:5px;border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		.reset{ 
		    background: -webkit-gradient(linear, 0% 0%, 0% 100%,from(#01aacf), to(#0165a2)) !important;
		    color: #FFFFFF !important;
		    border: 1px solid #0297bf !important;
		    padding: 0 15px;
		    border-radius: 3px;
		    width: initial !important;
		    height: 25px !important;
    	}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input.date_text{border-radius:5px; width:135px; height:18px; line-height:18px;}
		.search_table td{ height:30px; line-height:30px;}
		.search_table td.td_lable{ text-align:right;}
		.search_table td.td_value{ text-align:left;}
		a{text-decoration : none;color:blue;}
		red{color: red;}
		.middle input { display: block;width:30px; margin:2px;}
		.listbox {margin:4px;float:left;}
		.listbox td{height:15px; line-height:15px; }
	</style>
	<script type="text/javascript"> 
	var jsonArray;
	$(function () {
		 getSysCodestype("SYSEXAMTYPE","examType");     //获取考试类型
		 getSysCodestype("SYSEXAMOBJECT","examObject");   //获取考试对象
		 
		$("#listbox1,#listbox2").ligerListBox({
           isShowCheckBox: true,
           isMultiSelect: true,
           height: 110,
           width:120
       });
    });
	
	function submitExam(){
		if(checkIsNull($("input[name=examName]").val(),"考试名称不能为空")) return;
		if(checkIsNull($("input[name=toExamBeginTime]").val(),"开考开始时间不能为空")) return;
		if(checkIsNull($("input[name=toExamEndTime]").val(),"开考结束时间不能为空")) return;
		if(checkIsNull($("input[name=examTimeLength]").val(),"考试时长不能为空")) return;
		if(checkIsNull($("input[name=examPaperName]").val(),"试卷名称不能为空")) return;
		if(checkIsNull($("input[name=listUserName]").val(),"参考人员不能为空")) return;
		if(checkIsNull($("#examType").val(),"考试类型不能为空")) return;
		if(checkIsNull($("#examObject").val(),"考试对象不能为空")) return;
		if(checkIsNull($("input[name=examNormalScore]").val(),"合格分数不能为空")) return;
		
		if($("input[name=examName]").val().length>50){
			$.ligerDialog.warn("考试名称过长")
			return;
		}
		
		if(!text_req($("input[name=examName]").val(),true)){
			$.ligerDialog.warn("考试名称不能输入特殊字符")
			return;
		}
		
		if(parseInt($("input[name=examTimeLength]").val())>600){
			$.ligerDialog.warn("考试时长过长")
			return;
		}
		
		if(parseInt($("input[name=examNormalScore]").val())>600){
			$.ligerDialog.warn("合格分数过长")
			return;
		}
		if(parseInt($("input[name=examNormalScore]").val())>parseInt($("#examTotalScore").html())){
			$.ligerDialog.warn("合格分数不能大于试卷总分")
			return;
		}
		
		var url = "${ctx}/controller/exam/addExam.do";
		$.ajax({
			url: url,
		  	type:"post",
		  	dataType:"json",
		  	data:$("#addExamForm").serialize(),
		  	success: function(resultData){
		  		if(resultData.resultStr=="-1"){
		  			$.ligerDialog.warn('该考试名称已存在');
		  			return;
		  		}
		  		$.ligerDialog.success('添加成功','提示',function(yes){	
		  			back();	 
				});		  
		  	},
			error:function(e){
       			$.ligerDialog.warn('提交异常，请稍后再试');
       	    }
		 }); 
	}
	
	//查询是否为空
	function checkIsNull(ids,warns){
		 if(ids=='' || ids.length==0){
			 $.ligerDialog.warn(warns);
			 return true;
		 }else{
			 return false;
		 }
	}
	
	
	function back(){
		location.href = "${ctx}/controller/page/manageView.do";
	}
	
	function openChooseUsers(inputname){
		 $("#chooseUser").hide(); 
		 $.ligerDialog.open({
              height:420,
              width: 820,
              title : '用户管理',
              url: '${ctx}/page/publicdialog/choose_users.jsp', 
              showMax: false,
              showToggle: true,
              showMin: false,
              isResize: true,
              slide: false,
              data: {
                  name: inputname
              },
              //自定义参数
              myDataName: "自定义参数"
       });
	}
	
	function openChooseTestpaper(){
		 $.ligerDialog.open({
             height:420,
             width: 820,
             title : '试卷管理',
             url: '${ctx}/page/publicdialog/choose_testpaper.jsp', 
             showMax: false,
             showToggle: true,
             showMin: false,
             isResize: true,
             slide: false,
             data: {
                 ename: "examPaperName",
                 eid:"examPaperId",
                 lengthId:"examTotalScore"
             },
             //自定义参数
             myDataName: "自定义参数"
      });
	}
	
	// 编辑已选用户
	function editUsers(){
	   if(!$("#chooseUser").is(":hidden")){
			   $("#chooseUser").hide();
			   return;
	   }
	   if($("#listUserName").val().length<1){
		   $.ligerDialog.error('请先选择参考人员');
    	   return;
	   }
	   var txt= $("#listUserName").val().split(",");
	   var txtId= $("#listUserIDs").val().split(",");
	   var allUserJson = "[";
	   for(var i=0;i<txt.length;i++){
		   allUserJson = allUserJson + "{\"text\":\""+txt[i]+"\",\"id\":\""+txtId[i]+"\"},";
	   }
       if(allUserJson.length>3){
    	   jsonArray = $.parseJSON(allUserJson.substring(0,allUserJson.length-1) +"]");
    	   liger.get("listbox1").setData(jsonArray);
       }
       $("#chooseUser").show();
	}
	
	// 保存编辑
	function saveUsers(){
		jsonArray = liger.get("listbox1").data;
		var listUserName = "",listUserIDs="";
		for(var i in jsonArray){
			listUserName = listUserName + jsonArray[i].text + ",";
			listUserIDs = listUserIDs + jsonArray[i].id + ",";
		}
		$("#listUserName").val(listUserName.substring(0,listUserName.length-1));
		$("#listUserIDs").val(listUserIDs.substring(0,listUserIDs.length-1));
        $("#chooseUser").hide(); 
	}
	
	function moveToLeft()
    {
        var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
        var selecteds = box2.getSelectedItems();
        if (!selecteds || !selecteds.length) return;
        box2.removeItems(selecteds);
        box1.addItems(selecteds);
    }
    function moveToRight()
    {
        var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
        var selecteds = box1.getSelectedItems();
        if (!selecteds || !selecteds.length) return;
        box1.removeItems(selecteds);
        box2.addItems(selecteds);
    }
    function moveAllToLeft()
    { 
        var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
        var selecteds = box2.data;
        if (!selecteds || !selecteds.length) return;
        box1.addItems(selecteds);
        box2.removeItems(selecteds); 
    }
    function moveAllToRight()
    { 
        var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
        var selecteds = box1.data;
        if (!selecteds || !selecteds.length) return;
        box2.addItems(selecteds);
        box1.removeItems(selecteds);
        
    }
    </script> 
</head>
<body>  
	<form id="addExamForm">
		<div id="insertdiv" style="width:98%; margin:5px; padding-right:20px;padding-top:30px;">
		   <input type="text" name="listUserIDs" id="listUserIDs" style="display: none"/>
		   <input type="text" name="examPaperId" id="examPaperId" style="display: none"/>
		   <table class="search_table" cellpadding="3" cellspacing="3" width="100%" >
				<tr><td class="td_lable">考试名称：</td><td class="td_value"><input type="text" name="examName"/>&nbsp;<red>*</red></td><td class="td_value">考试名称不能为空</td></tr>
				<tr><td class="td_lable">开考时间：</td><td class="td_value"><input name="toExamBeginTime" id="toExamBeginTime" class="Wdate date_text" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'toExamEndTime\')||\'2120-10-01\'}'})" />&nbsp;<red>*</red></td><td class="td_value">开考开始时间不能为空</td></tr>
				<tr><td class="td_lable">结束时间：</td><td class="td_value"><input name="toExamEndTime" id="toExamEndTime" class="Wdate date_text" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'toExamBeginTime\')}',maxDate:'2120-10-01'})" />&nbsp;<red>*</red></td><td class="td_value">开考结束时间不能为空</td></tr>
				<tr><td class="td_lable">考试时长：</td><td class="td_value"><input type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" 
				name="examTimeLength"/>&nbsp;<red>*单位:(分钟)</red><td class="td_value">考试时长不能为空</td></tr>
				<tr><td class="td_lable">试卷名称：</td><td class="td_value"><input type="text" id="examPaperName" name="examPaperName" id="examPaperName" onclick="openChooseTestpaper()" />&nbsp;<red>*</red>
				&nbsp;<a style="cursor: pointer;"><img src="../../img/icon/delete.gif" height="14" onclick='$("#insertTestpaper").val("")' alt="编辑"></a></td></td><td class="td_value">试卷名称不能为空</td></tr>
				<tr><td class="td_lable">参考人员：</td><td class="td_value"><input type="text" name="listUserName" id="listUserName" value="" onclick="openChooseUsers('listUserName')" />&nbsp;<red>*</red>&nbsp;&nbsp;
				<a><img src="../../img/icon/edit.gif" height="14" onclick='editUsers()' alt="编辑"></a>&nbsp;&nbsp;&nbsp;
				</td><td class="td_value">参考人员不能为空</td></tr>
				<tr id="chooseUser" style="display: none"><td class="td_lable">重设参考人员：&nbsp;</td>
					<td align="left" valign="middle">
				        <div class="listbox">
					         <div id="listbox1"></div>  
					     </div>
					     <div style="margin:4px;float:left;" class="middle">
					         <input type="button" onclick="moveToLeft()" value="&lt;" />
					          <input type="button" onclick="moveToRight()" value=">" />
					          <input type="button" onclick="moveAllToLeft()" value="&lt;&lt;" />
					         <input type="button" onclick="moveAllToRight()" value=">>" />
					     </div>
					    <div class="listbox">
					        <div id="listbox2"></div> 
					    </div>
					    <td class="td_lable" style="text-align: left;">&nbsp;&nbsp;<a href='javascript:void(0);' onclick='saveUsers()'>保存</a></td>
			       </td>
				</tr>
				<tr><td class="td_lable">合格分数：</td><td class="td_value"><input type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" 
						name="examNormalScore"/>&nbsp;<red>*</red>(试卷总分：<span id="examTotalScore" name="examTotalScore"></span>)</td><td class="td_value">合格分数不能为空</td></tr>
				<tr>
				<td class="td_lable">考试类型：</td><td class="td_value">
						<select name="toExamtype" id="examType" style="width: 135px">
			                <option value="">---请选择---</option>
			            </select>&nbsp;<red>*</red> </td>
					<td class="td_value">考试类型不能为空</td></tr>
				</tr>
				<tr>
					<td class="td_lable">考试对象：</td><td class="td_value">
						<select name="toExamtarget" id="examObject" style="width: 135px">
			                <option value="">---请选择---</option>
			            </select><red>&nbsp;*</red></td>
			        <td class="td_value">考试对象不能为空</td>
				</tr>
				<tr><td class="td_lable">是否允许考生查询分数：</td><td class="td_value"> <input type="radio" name="examQuery" value="1" checked="checked">&nbsp;是&nbsp;&nbsp;&nbsp;<input type="radio" name="examQuery" value="0">&nbsp;否</td>	
				<tr>
					<td colspan="8" align="center" style="padding-top:6px;">
						<shiro:hasPermission name="kswhgl:tj">
							<input id="insertForm" type="button" value="提交" onclick="submitExam()" />&nbsp;&nbsp;&nbsp;&nbsp;
						</shiro:hasPermission>
						<input id="cancelinsertForm" type="button" value="取消" onclick="back();"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
