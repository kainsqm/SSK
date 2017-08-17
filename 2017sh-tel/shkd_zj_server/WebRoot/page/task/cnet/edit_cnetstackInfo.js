$(function(){
	var bussniessType=$("#bussniessType").val();
	var telsumType=$("#telsumType").val();
	var gzlyType=$("#gzlyType").val();
	var flag=false;
	var type=$("#types").val();
	if($("#TEL_USER_NAME").val().length>0){
		$("#telNumber2").html($("#TEL_USER_NAME").val().split(",").length);
	}
	changeNumber("1");
	showErrorAorBMsg();
	if(type=="show"){
	flag=true;
	}
	$.ajax({	 
		url:ctx+'/controller/verdict/getCnetOrderType.do',
		type:'post',
		dataType:'json',
		success:function(data){	
		    $("#test1").ligerComboBox({ isShowCheckBox: true, isMultiSelect: true,
			data: data.serviceType, valueFieldID: 'bussniessType',initValue:bussniessType,readonly:flag 
			});   

			$("#test11").ligerComboBox({ isShowCheckBox: true, isMultiSelect: true,
			data: data.resultType, valueFieldID: 'telsumType',initValue:telsumType,readonly:flag 
			});
			
			$("#test111").ligerComboBox({ isShowCheckBox: true, isMultiSelect: true,
			data:data.complaintSource, valueFieldID: 'gzlyType',initValue:gzlyType,readonly:flag 
			});
		}
	});
	
});

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


// 隐藏
	function hiddenQc(id){
		document.getElementById(id).style.display="none";
	}
	// 选择话务员 operator
	function getOperatorList(obj,value) {		
		var values="";
		var uID=document.getElementById("papers_hid").value;	
		$.ligerDialog.open({
			height:500,
			width: 600,
			title : "员工信息查询",
			url:ctx+'/page/task/operListPage.jsp?userid='+encodeURI(encodeURI(uID)),
			showMax: false,
			showToggle: false,
			showMin: false,
			isResize: true,
			slide: false
		});

	}
	/* function moveAllOption(e1,e2){

	$("#list3").empty();
	
	var array=new Array();//存值
	var array2=new Array();//存显示值
	$("#list1 option").each(function(){
	var value=$(this).val();
	var text=$(this).text();
	if(value!=''&&value!=null){
	array.push(value);
	array2.push(text);
	}
	});
	$.each(array2,function(index,value){

	$("#list3").append("<option value='"+array[index]+"'>"+value+"</option>");
	}); 
	$("#list1").empty();
	var username=getName(array2);
	$("#qcUserName").val($.trim(username));
	} */
function getvalue(geto){ 
	var allvalue = ""; 
	for(var i=0;i<geto.options.length;i++){ 
		allvalue +=geto.options[i].value + ","; 
	}
	if(allvalue!=""){
		allvalue=allvalue.substring(0,allvalue.length-1);
	}
	return allvalue; 
} 
function getName(geto){ 
	var allvalue = ""; 
	var text="";
	for(var i=0;i<geto.options.length;i++){ 
		text=geto.options[i].text;
		var textlist=text.split('/');
		allvalue +=textlist[0] + ","; 
	} 
	if(allvalue!=""){
		allvalue=allvalue.substring(0,allvalue.length-1);
	}
	return allvalue; 
} 

function getworkid(geto){ 
	var allvalue = ""; 
	var text="";
	for(var i=0;i<geto.options.length;i++){ 
		text=geto.options[i].text;
		var textlist=text.split('/');
		allvalue +=textlist[1] + ","; 
	} 
	if(allvalue!=""){
		allvalue=allvalue.substring(0,allvalue.length-1);
	}
	return allvalue; 
} 

	// <!--操作全部-->
function moveAllOption(e1, e2){ 
 var fromObjOptions=e1.options; 
  for(var i=0;i<fromObjOptions.length;i++){ 
   fromObjOptions[0].selected=true; 
   e2.appendChild(fromObjOptions[i]); 
   i--; 
  } 
  
  document.getElementById("qcUserName").value=getName(document.getElementById("list3"));
  document.getElementById("qcUserWorkId").value=getworkid(document.getElementById("list3"));
  document.getElementById("QC_USER").value=getvalue(document.getElementById("list3"));
 // document.taskForm.callBusitpMod.value=(document.taskForm.list4);
 changeNumber("1");
}
// <!--操作单个-->
function moveOption(e1, e2){ 
 var fromObjOptions=e1.options; 
  for(var i=0;i<fromObjOptions.length;i++){ 
   if(fromObjOptions[i].selected){ 
    e2.appendChild(fromObjOptions[i]); 
    i--; 
   } 
  } 
  document.getElementById("qcUserName").value=getName(document.getElementById("list3"));
  document.getElementById("qcUserWorkId").value=getworkid(document.getElementById("list3"));
  document.getElementById("QC_USER").value=getvalue(document.getElementById("list3"));
 // document.taskForm.callBusitpMod.value=getvalue(document.taskForm.list4);
 changeNumber("1");
} 

	function addTaskSave(){
		showErrorAorBMsg();
			var taskName=$("#taskName").val();
			var oldName=$("#oldName").val();
			var ispublic;
			var STARTTIME=$("#STARTTIME").val();
			var ENDTIME=$("#ENDTIME").val();
			var qcUserName=$("#qcUserName").val();
			var checkCount=$("#checkCount").val();
			var csrTopDCount=$("#csrTopDCount").val();
			var TEL_USER_NAME=$("#TEL_USER_NAME").val();
			var type=$("#types").val();
			var errorA1 = $("#errorA1").html();
			var errorA2 = $("#errorA2").html();
		var chkObjs = document.getElementsByName("isPublish");
        for(var i=0;i<chkObjs.length;i++){
            if(chkObjs[i].checked){
            	ispublic=chkObjs[i].value;
                break;
            }
        }
    	$.ajax({
    		 url:ctx+"/controller/verdict/checkCnetTaskName.do",
    	     type: "post",
    	     data:{taskName:taskName,
    	    	   oldName:oldName
    	    	 	},
    	     dataType: "json",
    	     success: function(result){ 
    	    	 var count =result.data;
    	    	 if(count>0){
    	    		 $.ligerDialog.error("任务名称已存在，请重新输入");
    	    		 return false;
    	    	 }
    	      }
          });
        
		if(taskName==null||taskName==""){
		$.ligerDialog.warn("任务名称不能为空");
		return false;
		}
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
		if(checkCount==null||checkCount==""){
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
		$.ligerDialog.confirm('确认提交吗？','提示', function (yes) { 
			if(yes){
			if(type=="update"){	
		 $.ajax({
					url:ctx+'/controller/verdict/updCnetorderTask.do',
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
			               location.href=ctx+"/controller/verdict/toCnetverdictsackList.do";
			            });
						}
					}
				});
		
			}else{
			 $.ajax({
					url:ctx+'/controller/verdict/addCnetorderTask.do',
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
			               location.href=ctx+"/controller/verdict/toCnetverdictsackList.do";
			            });
						}
					}
				});
				}
			}
		});	
	}
	function ondis(){
	/* document.getElementById("conqcuser").style.display="";	 */
	$("#conqcuser").toggle();
	}
	
    //判断任务名称是否已存在
	function checkName(){
	var taskName= $("#taskName").val();
	$.ajax({
		 url:ctx+"/controller/task/check112TaskName.do",
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
	
	
	
	// 设置开始时间的同时设置结束时间（如果结束时间为空）为开始时间向后延一周的时间
	// 设置结束时间的同时设置开始时间（如果开始时间为空）为结束时间向前推一周的时间
	function timeChaOherCha(timename1,timename2,nday,hmsType){// n 天数
	// hmsType:[0]输出时间的时分秒为00：00：00, [1]输出时间的时分秒为23：59：59, [其他]输出时间的时分秒为输入时间的时分秒
		// textname1列表头text的名称，textname2循环列text的名称，boxname循环复选框的名称
		var t1Obj = document.getElementsByName(timename1)[0];
		var t1v = t1Obj.value;
		var t2Obj = document.getElementsByName(timename2)[0];
		var t2v = t2Obj.value;
		// var formObj = document.getElementsByName(formname)[0];
		
		if(t1v != "" && t2v == ""){
			//$.post(ctx+"/controller/task/timeChaOherCha.do?ymdhms=" + t1v + "&nday=" + nday + "&hmsType=" + hmsType,null,function (data){
			//	t2Obj.value =data;
			//});
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