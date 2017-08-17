
 /**
	 * 保存任务 author zrx
	 */
	function addTaskSave(){
	// getTelNumberByAgid();
		if(!befSavCheck())return false;
		var taskName= document.getElementsByName("TASK_NAME")[0].value;
		$.ajax({
			 url: ctx+"/controller/task/checkTaskName.do",
		     type: "post",
		     data:{taskName:taskName},
		     dataType: "json",
		     success: function(result){ 
		    	 var count =result.data;
		    	 if(count>0){
		    		 $.ligerDialog.error("任务名称已存在，请重新输入");
		    	 }else{
		    		 $.ligerDialog.confirm('确认提交吗？','提示', function (yes) { 
		    				if(yes){
		    				 $.ajax({
		    						url:ctx+'/controller/task/addTask.do',
		    						type:'post',
		    						data:$('#myform').serialize(),
		    						error: function(request) {
		    							$.ligerDialog.error('新增失败，请稍候再试!');
		    						},
		    						success: function(data) {
		    							var res=eval("("+data+")");
		    							if(res.status=='0'){
		    								$.ligerDialog.warn('新增失败');
		    							}else if(res.status=='1'){
		    								$.ligerDialog.success('新增成功','提示',function(yes){ 
		    								location.href=ctx+"/controller/task/togetTaskDistribut.do";
		    								 });
		    								$("#btn").css('display','none');
		    							}
		    						}
		    					});
		    				}
		    			}); 
		    	 }
		      }
	      });
		
	}
	function getTelNumberByAgid(){
		var agID= document.getElementById("agID").value;// 被质检话务员班组ID
		var csrCount=0;
		if(agID!=""){
			jQuery.ajax({
						url:"<%=path%>/action/SystemManageAction.do?method=getCsrCountByGroups",
						data:"agid="+agID,
						type:"post",
						async:false,
						success:function(data,textStatus){
								eval("var sta="+data);
								csrCount=sta.result;
								// alert(sta.result);
							}
						});
			document.getElementById("csrCount").value=csrCount;
		}
	}
	
	
	// 选择话务员 operator
	function getOperatorList(obj,value) {
		/*
		 * obj.value="请稍等..."; var values=""; var
		 * uID=document.getElementById("TEL_USER").value; if(uID!=null &&
		 * uID!=''){ for ( var i = 0; i < uID.split(',').length; i++) {
		 * values+=uID.split(',')[i]+'-'+value.split(',')[i]+','; }
		 * values=values.substring(0,values.length-1);
		 * values=encodeURI(encodeURI(values)); }
		 */
		// win_move();
		var values="";
		var uID=document.getElementById("papers_hid").value;
	/*
	 * if(uID!=null && uID!=''){ for ( var i = 0; i < uID.split(',').length;
	 * i++) { values+=uID.split(',')[i]+'-'+value.split(',')[i]+','; }
	 * values=values.substring(0,values.length-1); alert("values"+values);
	 * values=encodeURI(encodeURI(values)); }
	 */
		
		$.ligerDialog.open({
			height:500,
			width: 600,
			title : "员工信息查询",
			url:ctx+'/page/task/operListPage.jsp?userid='+encodeURI(encodeURI(uID)),
			// url:'<%=path%>/jsp/task/task_lingqu.html',
			showMax: false,
			showToggle: false,
			showMin: false,
			isResize: true,
			slide: false
		});

	}
	// 显示隐藏
	function showQc(id){
		var qcList=document.getElementById(id);
		if(qcList.style.display==""){
			qcList.style.display="none";
		}else{
			qcList.style.display="";
		}
	}
	
	// 隐藏
	function hiddenQc(id){
		document.getElementById(id).style.display="none";
	}
	
	
	
	// <!--操作全部-->
function moveAllOption(e1, e2){ 
 var fromObjOptions=e1.options; 
  for(var i=0;i<fromObjOptions.length;i++){ 
   fromObjOptions[0].selected=true; 
   e2.appendChild(fromObjOptions[i]); 
   i--; 
  } 
  document.getElementById("QC_USER").value=getvalue(document.getElementById("list2"));
  document.getElementById("QC_USER_NAME").value=getName(document.getElementById("list2"));
  document.getElementById("QC_USER_WORKID").value=getworkid(document.getElementById("list2"));
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
  document.getElementById("QC_USER").value=getvalue(document.getElementById("list2"));
  document.getElementById("QC_USER_NAME").value=getName(document.getElementById("list2"));
  document.getElementById("QC_USER_WORKID").value=getworkid(document.getElementById("list2"));

 // document.taskForm.callBusitpMod.value=getvalue(document.taskForm.list4);
 changeNumber("1");
} 
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

	/*
	 * if(obj==4){//公商客大类 var callBusitpMod=
	 * document.getElementById("callBusitpMod").value;
	 * document.getElementById('callBusitpModNumber').innerHTML=callBusitpMod.split(',').length;
	 * if(callBusitpMod==''){
	 * document.getElementById('callBusitpModNumber').innerHTML=0; } }
	 */
	showErrorAorBMsg();
}

    //判断任务名称是否已存在
function checkName(){
	var taskName= document.getElementsByName("TASK_NAME")[0].value;
	$.ajax({
		 url: ctx+"/controller/task/checkTaskName.do",
	     type: "post",
	     data:{taskName:taskName},
	     dataType: "json",
	     success: function(result){ 
	    	 var count =result.data;
	    	 if(count>0){
	    		 $.ligerDialog.error("任务名称已存在，请重新输入");
	    	 }
	      }
      });
}
   

	// 显示规范信息，当条件符合时，才能保存成功
	function showErrorAorBMsg(){
		var qcNumberCount = 0; // 质检员数量
		var telNumberCount = 0; // 话务员数量
		if($("#qcNumber") && $("#qcNumber").html() != null && $("#qcNumber").html() != "")
			qcNumberCount = parseInt($("#qcNumber").html());
		
			telNumberCount = parseInt($("#telNumber2").html());
		
		var CSR_TOP_D_COUNT = 0; // 话务员抽查次数/天
		if($("#CSR_TOP_D_COUNT").val() != null && $("#CSR_TOP_D_COUNT").val() != "")
			CSR_TOP_D_COUNT = parseInt($("#CSR_TOP_D_COUNT").val());
		
		var oneCsrWCount = 0; // 话务员一周抽查次数总和
		var oneCsrWCount1 = 0; // 话务员规格0~1数
		var oneCsrWCount2 = 0; // 话务员规格1~2数
		var oneCsrWCount3 = 0; // 话务员规格2~3数
		var oneCsrWCount4 = 0; // 话务员规格3~5数
		var oneCsrWCount5 = 0; // 话务员规格5~10数
		var oneCsrWCount6 = 0; // 话务员规格10~数
		var oneQcDCount = 0; // 质检员一天任务规格总和
		if($("#oneCsrWCount1").val() != null && $("#oneCsrWCount1").val() != "")
			oneCsrWCount1 = parseInt($("#oneCsrWCount1").val());
		if($("#oneCsrWCount2").val() != null && $("#oneCsrWCount2").val() != "")
			oneCsrWCount2 = parseInt($("#oneCsrWCount2").val());
		if($("#oneCsrWCount3").val() != null && $("#oneCsrWCount3").val() != "")
			oneCsrWCount3 = parseInt($("#oneCsrWCount3").val());
		if($("#oneCsrWCount4").val() != null && $("#oneCsrWCount4").val() != "")
			oneCsrWCount4 = parseInt($("#oneCsrWCount4").val());
		if($("#oneCsrWCount5").val() != null && $("#oneCsrWCount5").val() != "")
			oneCsrWCount5 = parseInt($("#oneCsrWCount5").val());
		if($("#oneCsrWCount6").val() != null && $("#oneCsrWCount6").val() != "")
			oneCsrWCount6 = parseInt($("#oneCsrWCount6").val());
		oneCsrWCount = oneCsrWCount1+oneCsrWCount2+oneCsrWCount3+oneCsrWCount4+oneCsrWCount5+oneCsrWCount6;
		
		var oneQcDCount = 0; // 质检员一天任务规格总和
		var oneQcDCount1 = 0; // 质检员规格0~1数
		var oneQcDCount2 = 0; // 质检员规格1~2数
		var oneQcDCount3 = 0; // 质检员规格2~3数
		var oneQcDCount4 = 0; // 质检员规格3~5数
		var oneQcDCount5 = 0; // 质检员规格5~10数
		var oneQcDCount6 = 0; // 质检员规格10~数
		if($("#oneQcDCount1").val() != null && $("#oneQcDCount1").val() != "")
			oneQcDCount1 = parseInt($("#oneQcDCount1").val());
		if($("#oneQcDCount2").val() != null && $("#oneQcDCount2").val() != "")
			oneQcDCount2 = parseInt($("#oneQcDCount2").val());
		if($("#oneQcDCount3").val() != null && $("#oneQcDCount3").val() != "")
			oneQcDCount3 = parseInt($("#oneQcDCount3").val());
		if($("#oneQcDCount4").val() != null && $("#oneQcDCount4").val() != "")
			oneQcDCount4 = parseInt($("#oneQcDCount4").val());
		if($("#oneQcDCount5").val() != null && $("#oneQcDCount5").val() != "")
			oneQcDCount5 = parseInt($("#oneQcDCount5").val());
		if($("#oneQcDCount6").val() != null && $("#oneQcDCount6").val() != "")
			oneQcDCount6 = parseInt($("#oneQcDCount6").val());
		oneQcDCount = oneQcDCount1+oneQcDCount2+oneQcDCount3+oneQcDCount4+oneQcDCount5+oneQcDCount6;
		
		// 规范一
		$("#errorA1").html(telNumberCount * CSR_TOP_D_COUNT == 0 ? "0" : telNumberCount * CSR_TOP_D_COUNT);
		$("#errorA2").html(qcNumberCount * oneQcDCount == 0 ? "0" : qcNumberCount * oneQcDCount);
		// 规范二
		$("#errorB1").html(oneCsrWCount * telNumberCount == 0 ? "0" : oneCsrWCount * telNumberCount);
		$("#errorB2").html(oneQcDCount * 5 * qcNumberCount == 0 ? "0" : oneQcDCount * 5 * qcNumberCount);
	}
	
	function befSavCheck(){				
		var taskName= document.getElementsByName("TASK_NAME")[0].value;
		var taskStartTime = document.getElementsByName("STARTTIME")[0].value;
		var taskEndTime = document.getElementsByName("ENDTIME")[0].value;
		var taskQcUserID= document.getElementById("QC_USER").value;// 所选质检员
		var csrID= document.getElementById("TEL_USER").value;// 被质检话务员ID
		var IS_PUBLISH="";
		
		var chkObjs = document.getElementsByName("IS_PUBLISH");
        for(var i=0;i<chkObjs.length;i++){
            if(chkObjs[i].checked){
            	IS_PUBLISH=chkObjs[i].value;
                break;
            }
        }
	// var agID= document.getElementById("agID").value;//被质检话务员班组ID

		var oneCsrDCount=document.getElementById("CSR_TOP_D_COUNT").value;// 每个CSR每天最高被质检次数
		var oneCsrWCount1=document.getElementById("oneCsrWCount1").value;// 每个CSR每周最高被质检次数
		var oneCsrWCount2=document.getElementById("oneCsrWCount2").value;
		var oneCsrWCount3=document.getElementById("oneCsrWCount3").value;
		var oneCsrWCount4=document.getElementById("oneCsrWCount4").value;
		var oneCsrWCount5=document.getElementById("oneCsrWCount5").value;
		var oneCsrWCount6=document.getElementById("oneCsrWCount6").value;
		var oneQcDCount1=document.getElementById("oneQcDCount1").value;// 每个质检员每天质检任务规格
		var oneQcDCount2=document.getElementById("oneQcDCount2").value;
		var oneQcDCount3=document.getElementById("oneQcDCount3").value;
		var oneQcDCount4=document.getElementById("oneQcDCount4").value;
		var oneQcDCount5=document.getElementById("oneQcDCount5").value;
		var oneQcDCount6=document.getElementById("oneQcDCount6").value;
// var
// csrCountByOneQc=document.getElementById("csrCountByOneQc").value;//任务期间每个CSR被同一个质检员最高质检次数
		if(taskName==""){
			$.ligerDialog.warn("任务名称不能为空");
			document.getElementsByName("TASK_NAME")[0].focus();
			return false;
		}
		
		if(IS_PUBLISH==""){
			$.ligerDialog.warn("是否发布选项不能为空");
			document.getElementsByName("IS_PUBLISH")[0].focus();
			return false;
		}
		if(taskStartTime==""){
			$.ligerDialog.warn("任务开始时间不能为空");
			document.getElementsByName("STARTTIME")[0].focus();
			return false;
		}else if(taskEndTime==""){
			$.ligerDialog.warn("任务结束时间不能为空");
			document.getElementsByName("ENDTIME")[0].focus();
			return false;
		}else if(!bijiaoDate(taskStartTime.substring(0,10),taskEndTime.substring(0,10))){
			$.ligerDialog.warn("任务开始时间不能大于“任务结束时间”");
			document.getElementsByName("ENDTIME")[0].focus();
			return false;
		}
		if(taskQcUserID==""){
			$.ligerDialog.warn("请选择要派发的质检员");
			document.getElementById("QC_USER_NAME").focus();
			return false;
		}else{
			if(taskQcUserID.split(',').length >30){
				$.ligerDialog.warn("质检员人数过多");
				document.getElementById("QC_USER_NAME").focus();
				return false;
			}
		}
		if(csrID==""){					
			$.ligerDialog.warn("请选择受理员");
		    document.getElementById("TEL_USER_NAME").focus();
			return false;
		}

		
		
		if(oneCsrDCount=="" || oneCsrDCount <=0){
			$.ligerDialog.warn("受理员抽查次数/天不能为空且必须大于0");
			document.getElementById("CSR_TOP_D_COUNT").focus();
			return false;
		}
		if(oneCsrWCount1=="" || oneCsrWCount2=="" || oneCsrWCount3=="" || oneCsrWCount4=="" || oneCsrWCount5=="" || oneCsrWCount6==""){
			$.ligerDialog.warn("受理员抽查次数/周每个档不能为空");
			document.getElementById("oneCsrWCount1").focus();
			return false;
		}
		if(oneQcDCount1=="" || oneQcDCount2=="" || oneQcDCount3=="" || oneQcDCount4=="" || oneQcDCount5=="" || oneQcDCount6==""){
			$.ligerDialog.warn("质检员任务规格数/天每个档不能为空");
			document.getElementById("oneQcDCount1").focus();
			return false;
		}
		// if(csrCountByOneQc=="" || csrCountByOneQc <= 0){
		// alert("任务期间每个CSR被同一个质检员最高质检次数不能为空且必须大于0");
		// document.getElementById("csrCountByOneQc").focus();
		// return false;
		// }
		var csrWTaskCount=parseInt(oneCsrWCount1)+parseInt(oneCsrWCount2)+parseInt(oneCsrWCount3)+parseInt(oneCsrWCount4)+parseInt(oneCsrWCount5)+parseInt(oneCsrWCount6);// 每个话务员每周限制
		if(csrWTaskCount <= oneCsrDCount){
			$.ligerDialog.warn("受理员抽查次数/周总数必须大于“受理员抽查次数/天”");
			document.getElementById("oneCsrWCount1").focus();
			return false;
		}
		var qcCount=taskQcUserID.split(',').length;// 所选质检员数量
		var qcDayTaskCount=parseInt(oneQcDCount1)+parseInt(oneQcDCount2)+parseInt(oneQcDCount3)+parseInt(oneQcDCount4)+parseInt(oneQcDCount5)+parseInt(oneQcDCount6);// 每个质检员每天的任务规格
		if(qcDayTaskCount == "0"){
			$.ligerDialog.warn("质检员任务规格数/天总和不能为空");
			document.getElementById("oneQcDCount1").focus();
			return false;
		}
		var csrCount=0;// 所选话务员数量
		var dCount=0;// 任务总天数
		dCount=DateDiff(taskEndTime.substring(0,10),taskStartTime.substring(0,10))+1;
		// 如果选择了条件：受理员
		if(csrID!=""){
			csrCount=csrID.split(',').length;// 话务员数量
		}
		if((csrCount*oneCsrDCount-qcCount*qcDayTaskCount)<=0){
			$.ligerDialog.warn("违反约束：规范一");
			return false;
		}
		if((csrWTaskCount*csrCount-qcCount*qcDayTaskCount*5)<=0){
			$.ligerDialog.warn("违反约束：规范二");
			return false;
		}
		return true;
	}
	
	/**
	 * str2 代表的时间 大于 str1 代表的时间 返回true 否者返回false
	 * 
	 */
	function bijiaoDate(str1,str2){// str1的格式必须为："2010-09-20"
		var date1 = Date.parse(str1.replace("-", "/"));
		var date2 = Date.parse(str2.replace("-", "/"));
		if(date2 >= date1){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 比较两个日期相差的天数，可为负值
	 */  
	function DateDiff(sDate1, sDate2)   
	  
	{ // sDate1和sDate2是2002-12-18格式
	    var aDate, oDate1, oDate2, iDays;   
	    aDate = sDate1.split("-");   
	    oDate1 = new Date(aDate[0],aDate[1]-1,aDate[2]);   
	    aDate = sDate2.split("-");   
	    oDate2 = new Date(aDate[0],aDate[1]-1,aDate[2]);   
	       
	    iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 /24);     
	    if((oDate1 - oDate2)<0){   
	        return -iDays;   
	    }   
	    return iDays;  
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