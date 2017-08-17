$(function() {
	var businessType2 = $("#businessType2").val();
	var telsumType2 = $("#telsumType2").val();
	var gzlyType2 = $("#gzlyType2").val();
	console.log(businessType2+"-"+telsumType2);
	var flag = false;
	var type = $("#types").val();
	if (type == "show") {
		flag = true;
	}
	
	if($("#TEL_USER_NAME").val().length>0){
		$("#telNumber2").html($("#TEL_USER_NAME").val().split(",").length);
	}
	changeNumber("1");
	showErrorAorBMsg();
	
	$.ajax({
		url : ctx + '/controller/wk112/get112ChapterType.do',
		type : 'post',
		dataType : 'json',
		success : function(data) {
			
			var businessType = JSON.stringify(data.businessType);
			var telsumType = JSON.stringify(data.telsumType);
			var gzlyType = JSON.stringify(data.gzlyType);
			
			$("#test1").ligerComboBox({
				isShowCheckBox : true,
				isMultiSelect : true,
				data : data.businessType,
				valueFieldID : 'test3',
				initValue : businessType2,
				readonly : flag
			});

			$("#test11").ligerComboBox({
				isShowCheckBox : true,
				isMultiSelect : true,
				data : data.telsumType,
				valueFieldID : 'test33',
				initValue : telsumType2,
				readonly : flag
			});

			$("#test111").ligerComboBox({
				isShowCheckBox : true,
				isMultiSelect : true,
				data : data.gzlyType,
				valueFieldID : 'test333',
				initValue : gzlyType2,
				readonly : flag
			});
			
			
		}
	});
});

// 隐藏
function hiddenQc(id) {
	document.getElementById(id).style.display = "none";
}
// 选择话务员 operator
function getOperatorList(obj, value) {
	var values = "";
	var uID = document.getElementById("papers_hid").value;
	
	/*$(".closeCls").on("click", function(){
		alert('xxx');
	});*/
	
	$.ligerDialog.open({
		height : 500,
		width : 600,
		title : "员工信息查询",
		url : ctx + '/page/task/operListPage.jsp?userid='
				+ encodeURI(encodeURI(uID)),
		showMax : false,
		showToggle : false,
		showMin : false,
		isResize : true,
		slide : false
//		cls:"closeCls"
	});
	

}

function getvalue(geto) {
	var allvalue = "";
	for (var i = 0; i < geto.options.length; i++) {
		allvalue += geto.options[i].value + ",";
	}
	if (allvalue != "") {
		allvalue = allvalue.substring(0, allvalue.length - 1);
	}
	return allvalue;
}
function getName(geto) {
	var allvalue = "";
	var text = "";
	for (var i = 0; i < geto.options.length; i++) {
		text = geto.options[i].text;
		var textlist = text.split('/');
		allvalue += textlist[0] + ",";
	}
	if (allvalue != "") {
		allvalue = allvalue.substring(0, allvalue.length - 1);
	}
	return allvalue;
}

function getworkid(geto) {
	var allvalue = "";
	var text = "";
	for (var i = 0; i < geto.options.length; i++) {
		text = geto.options[i].text;
		var textlist = text.split('/');
		allvalue += textlist[1] + ",";
	}
	if (allvalue != "") {
		allvalue = allvalue.substring(0, allvalue.length - 1);
	}
	return allvalue;
}

// <!--操作全部-->
function moveAllOption(e1, e2) {
	var fromObjOptions = e1.options;
	for (var i = 0; i < fromObjOptions.length; i++) {
		fromObjOptions[0].selected = true;
		e2.appendChild(fromObjOptions[i]);
		i--;
	}

	document.getElementById("qcUserName").value = getName(document
			.getElementById("list3"));
	document.getElementById("qcUserWorkId").value = getworkid(document
			.getElementById("list3"));
	document.getElementById("QC_USER").value = getvalue(document
			.getElementById("list3"));
	// document.taskForm.callBusitpMod.value=(document.taskForm.list4);
	changeNumber("1");
}
// <!--操作单个-->
function moveOption(e1, e2) {
	var fromObjOptions = e1.options;
	for (var i = 0; i < fromObjOptions.length; i++) {
		if (fromObjOptions[i].selected) {
			e2.appendChild(fromObjOptions[i]);
			i--;
		}
	}
	document.getElementById("qcUserName").value = getName(document
			.getElementById("list3"));
	document.getElementById("qcUserWorkId").value = getworkid(document
			.getElementById("list3"));
	document.getElementById("QC_USER").value = getvalue(document
			.getElementById("list3"));
	changeNumber("1");
}


function addTaskSave(){
	showErrorAorBMsg();
	var ispublic;
	var taskName=$("#taskName").val();
	var oldName=$("#oldName").val();
	var STARTTIME=$("#STARTTIME").val();
	var ENDTIME=$("#ENDTIME").val();
	var qcUserName=$("#qcUserName").val();
	var telUserName=$("#TEL_USER_NAME").val();
	var checkcount=$("#checkCount").val();
	var csrTopDCount=$("#csrTopDCount").val();
	var TEL_USER_NAME=$("#TEL_USER_NAME").val();
	var type=$("#types").val();
	var chkObjs = document.getElementsByName("isPublish");
	
	var errorA1 = $("#errorA1").html();
	var errorA2 = $("#errorA2").html();

	for(var i=0;i<chkObjs.length;i++){
        if(chkObjs[i].checked){
        	ispublic=chkObjs[i].value;
            break;
        }
    }
    
	if(taskName==null || taskName==""){
		$.ligerDialog.warn("任务名称不能为空");
		return false;
	}
	
	$.ajax({
		 url:ctx+"/controller/chapter112/check112ChapterName.do",
	     type: "post",
	     data:{	
	    	 taskName:taskName,
	    	 oldName:oldName
	     },
	     dataType: "json",
	     success: function(result){ 
	    	 var count =result.data;
	    	 if(count>0){
	    		 $.ligerDialog.error("任务名称已存在，请重新输入");
	    		 return false;
	    	 }else{
	    		 
	    		 if(ispublic==null||ispublic==""){
	    				$.ligerDialog.warn("是否发布不能为空");
	    				return false;
	    			}
	    			if(STARTTIME==null||STARTTIME==""||ENDTIME==""||ENDTIME==null){
	    				$.ligerDialog.warn("任务周期不能为空");
	    				return false;
	    			}
	    			if(qcUserName==null||qcUserName==""){
	    				$.ligerDialog.warn("任务对象不能为空");
	    				return false;
	    			}
	    			if(telUserName==null||telUserName==""){
	    				$.ligerDialog.warn("受理员不能为空");
	    				return false;
	    			}
	    			if(checkcount==null||checkcount==""){
	    				$.ligerDialog.warn("受理员被抽检限制次数不能为空");
	    				return false;
	    			}
	    			if(csrTopDCount==null||csrTopDCount==""){
	    				$.ligerDialog.warn("质检员任务规格数不能为空");
	    				return false;
	    			}
	    			
	    			if((parseInt(errorA1)-parseInt(errorA2))<=0){
	    				$.ligerDialog.warn("违反约束：规范一");
	    				return false;
	    			}
	    			console.log($('#myform').serialize());
	    			$.ligerDialog.confirm('确认提交吗？','提示', function (yes) { 
	    				if(yes){
	    					if(type=="update"){	
	    						$.ajax({
	    							url:ctx+'/controller/chapter112/upd112ChapterTask.do',
	    							type:'post',
	    							data:$('#myform').serialize(),
	    							error: function(request) {
	    								$.ligerDialog.error('修改失败，请稍候再试!');
	    							},
	    							success: function(data) {
	    								var res=eval("("+data+")");
	    								if(res.result=='0'){
	    									$.ligerDialog.warn('修改失败');
	    								}else if(res.result=='1'){
	    								$.ligerDialog.success('修改成功','提示',function(yes){ 
	    					               location.href=ctx+"/controller/chapter112/show112Chapter.do";
	    					            });
	    								}
	    							}
	    					});
	    					}else{
	    						$.ajax({
	    							url:ctx+'/controller/chapter112/add112ChapterTask.do',
	    							type:'post',
	    							data:$('#myform').serialize(),
	    							error: function(request) {
	    								$.ligerDialog.error('新增失败，请稍候再试!');
	    							},
	    							success: function(data) {
	    								var res=eval("("+data+")");
	    								if(res.result=='0'){
	    									$.ligerDialog.warn('新增失败');
	    								}else if(res.result=='1'){
	    								$.ligerDialog.success('新增成功','提示',function(yes){ 
	    					               location.href=ctx+"/controller/chapter112/show112Chapter.do";
	    					            });
	    								}
	    							}
	    						});
	    					}
	    				}
	    			});	
	    	 }
	     }
    });

	
}
function ondis(){
	$("#conqcuser").toggle();
}

function changeNumber(obj){
	if(obj==1){// 质检员
		var taskQC_USER= document.getElementById("QC_USER").value;// 所选质检员
		document.getElementById('qcNumber').innerHTML = taskQC_USER.split(',').length;
		if(taskQC_USER==''){
			document.getElementById('qcNumber').innerHTML=0;
		}else{
			
		}
	}
	if(obj==2){// 话务员
		var TEL_USER= document.getElementById("TEL_USER").value;// 被质检话务员ID
		document.getElementById('telNumber2').innerHTML=TEL_USER.split(',').length;
		if(TEL_USER==''){
			document.getElementById('telNumber2').innerHTML=0;
		}
	}
	//showErrorAorBMsg();
}

// 显示规范信息，当条件符合时，才能保存成功
function showErrorAorBMsg(){
	var qcNumberCount = 0; // 质检员数量
	var telNumberCount = 0; // 话务员数量
	if($("#qcNumber") && $("#qcNumber").html() != null && $("#qcNumber").html() != ""){
		qcNumberCount = parseInt($("#qcNumber").html());
		telNumberCount = parseInt($("#telNumber2").html());
	}
	var checkcount = 0; // 话务员抽查次数/天
	if($("#checkCount").val() != null && $("#checkCount").val() != ""){
		checkcount = parseInt($("#checkCount").val());
	}
	
	var csrTopDCount=0;//质检员每天任务规格数
	if($("#csrTopDCount").val() != null && $("#csrTopDCount").val() != ""){
		csrTopDCount = parseInt($("#csrTopDCount").val());
	}
	
	// 规范一
	$("#errorA1").html(telNumberCount * checkcount == 0 ? "0" : telNumberCount * checkcount);
	$("#errorA2").html(qcNumberCount * csrTopDCount == 0 ? "0" : qcNumberCount * csrTopDCount);
	
	
}

//判断任务名称是否已存在
function checkName(){
var taskName= $("#taskName").val();
$.ajax({
	 url:ctx+"/controller/chapter112/check112ChapterName.do",
     type: "post",
     data:{taskName:taskName},
     dataType: "json",
     success: function(result){ 
    	 var count =result.data;
    	 if(count>0){
    		 $.ligerDialog.error("任务名称已存在，请重新输入");
    		 return false;
    	 }
      }
  });
}
// 设置开始时间的同时设置结束时间（如果结束时间为空）为开始时间向后延一周的时间
// 设置结束时间的同时设置开始时间（如果开始时间为空）为结束时间向前推一周的时间
function timeChaOherCha(timename1,timename2,nday,hmsType){ // n 天数
	var t1Obj = document.getElementsByName(timename1)[0];
	var t1v = t1Obj.value;
	var t2Obj = document.getElementsByName(timename2)[0];
	var t2v = t2Obj.value;
	if(t1v != "" && t2v == ""){
		$.ajax({
			 url: ctx+"/controller/task/timeChaOherCha.do",
		     type: "post",
		     data:{ymdhms:t1v,nday:nday,hmsType:hmsType},
		     dataType: "json",
		     success: function(result){ 
		    	 t2Obj.value =result.data;
		     }
	    });
	}
}