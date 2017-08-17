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