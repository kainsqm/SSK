 /**
   * 设置未来(全局)的AJAX请求默认选项
   * 主要设置了AJAX请求遇到Session过期的情况
   */
  $.ajaxSetup({
      type: 'POST',
      complete: function(xhr,status) {	  
          var sessionStatus = xhr.getResponseHeader('sessionstatus');
          var ipurl=window.location.host;
          var url="http://"+ipurl+":8081/shkd_tydl_service";
          if(sessionStatus == 'timeout') {
        	  $.ligerDialog.warn('会话超时,请重新登录','提示', function (yes) { 
                  	 var p = window; 
                  	 while(p != p.parent){ 
                  		 p = p.parent; 
                  	  } 
                  	 //获取主机地址之后的目录，如： 
                  	 p.location.href =url; 
           });
         }
     } 
 });

/**
 * 只能输入数字
 * @param obj
 * @return
 */
function input_shuzi(obj){
	obj.value=obj.value.replace(/[^\d]/g,'');
}
/**
 * 只能输入字母
 * @param obj
 * @return
 */
function input_zimu(obj){
	obj.value=obj.value.replace(/[^\a-zA-Z]/g,'');
}





function strlen(string) {
    var str = "";
    str = string;
    str = str.replace(/[^\x00-\xff]/g, "**");
    return str.length;
}
/**
 * 判空
 * @param obj
 * @return
 */
function isempty(string) {
	return string==null || string=="";
}
/**
 * 只能输入字母加数字
 * @param obj
 * @return
 */
function input_zimushuzi(obj){
	obj.value=obj.value.replace(/[^\a-zA-Z0-9]/g,'');
}
/**
 * 过滤所有符号
 * @param obj
 * @return
 */
function input_all(obj){
	obj.value= obj.value.replace(/[^\u4E00-\u9FA5a-zA-Z0-9]/g,'');
}



/**
 * 过滤指定字符
 * @param obj
 * @return
 */
function input_text(obj){
	obj.value = obj.value.replace(" ","");
	obj.value = obj.value.replace("%","");
	obj.value = obj.value.replace("‘","");
	obj.value = obj.value.replace("’","");
	obj.value = obj.value.replace("'","");
	obj.value = obj.value.replace("\"","");
	obj.value = obj.value.replace("\\","");
	obj.value = obj.value.replace("“","");
	obj.value = obj.value.replace("”","");
	obj.value = obj.value.replace("[","");
	obj.value = obj.value.replace("]","");
	obj.value = obj.value.replace(":","");
}
/**
 * 过滤长度
 * @param obj
 * @param len
 * @return
 */
function strlenText(obj,len){
	if(obj.value.length > parseInt(len)){
		obj.value=obj.value.substring(0,parseInt(len));
	}
}

/**************
文本框提交校验。
字符串长度限制
**************/
function strlenLimit(obj, len) {
    if (strlen(obj.value) > parseInt(len)) obj.value = obj.value.substring(0, parseInt(len));
}

/**
 * 判断是否回车事件
 * @return
 */
function isEnter(){
	//获取事件对象
	var elem = event.relatedTarget || event.srcElement || event.target ||event.currentTarget; 
	if (event.keyCode == 13){
		return true;
	}else{
		return false;
	}
}

/**
 * 禁止backspace 回退
 * @return
 */
function jinzhijs(){
	document.getElementsByTagName("body")[0].onkeydown =function(){
		//获取事件对象
		var elem = event.relatedTarget || event.srcElement || event.target ||event.currentTarget; 
		if(event.keyCode==8){//判断按键为backSpace键
			//获取按键按下时光标做指向的element
			var elem = event.srcElement || event.currentTarget; 
			//判断是否需要阻止按下键盘的事件默认传递
			var name = elem.nodeName;
			if(name!='INPUT' && name!='TEXTAREA'){
				return _stopIt(event);
			}
			var type_e = elem.type.toUpperCase();
			if(name=='INPUT' && (type_e!='TEXT' && type_e!='TEXTAREA' && type_e!='PASSWORD' && type_e!='FILE')){
					return _stopIt(event);
			}
			if(name=='INPUT' && (elem.readOnly==true || elem.disabled ==true)){
					return _stopIt(event);
			}
		}
	}
}
function _stopIt(e){
	if(e.returnValue){
		e.returnValue = false ;
	}
	if(e.preventDefault ){
		e.preventDefault();
	}				
	return false;
}

/**
 * 获取系统时间 格式yyyy-mm-dd hh:mi:ss
 * @returns {String}
 */
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}

/**************
文本框提交校验。
只能输入汉字，字母，数字或混编组合。
**************/
function noSpecial(obj) {
    obj.value = obj.value.replace(/[^\u4E00-\u9FA5a-zA-Z0-9]/g, '');
}

/**************
文本框提交校验。
字符串长度限制
**************/
function strlenLimit(obj, len) {
    if (strlen(obj.value) > parseInt(len)) obj.value = obj.value.substring(0, parseInt(len));
}

/**************
获取当前月份的第一天
**************/
function getfirstDay() {
	var d = new Date();	
	var year = d.getFullYear();
	var month = d.getMonth() + 1; // 记得当前月是要+1的
	if(month<10){
		month="0"+month;
	}
	var dt = d.getDate();
	var today = year + "-" + month + "-01";
	return today;
}


/**************
获取当前月份的当前天
**************/
function getnowDay() {
	var d = new Date();	
	var year = d.getFullYear();
	var month = d.getMonth() + 1; // 记得当前月是要+1的
	if(month<10){
		month="0"+month;
	}
	var dt = d.getDate();
	if(dt<10){
		dt="0"+dt;
	}
	var toendday = year + "-" + month + "-" +dt;
	return toendday;
}