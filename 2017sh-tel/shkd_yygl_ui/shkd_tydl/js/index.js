$(function(){
//  $(".login_time").html(login);
    $(".current_time").html(current);
})

 
//获取当前时间
function current(){
	var mydate = new Date();
	var yyyy = mydate.getFullYear(); //获取完整的年份(4位,1970-????)
	var Month = mydate.getMonth()+1; //获取当前月份(0-11,0代表1月)
	var Date1= mydate.getDate(); //获取当前日(1-31)
	var Hours = mydate.getHours(); //获取当前小时数(0-23)
	var Minutes= mydate.getMinutes(); //获取当前分钟数(0-59)
	var send = mydate.getSeconds();
	var day=mydate.getDay(); 
	var show_day=new Array('星期日','星期一','星期二','星期三','星期四','星期五','星期六');
	Minutes = String(Minutes);
	send = String(send);
	if(Minutes.length==1){
		Minutes = "0"+Minutes;
	}
	if(send.length==1){
		send = "0"+send;
	}
    var current_time = yyyy+"年"+Month+"月"+Date1+"日 "+show_day[day]+" "+Hours+" : "+Minutes+" : "+send;
    return current_time;
	}
//获取登录时间
//function login(){
//	var mydate = new Date();
//	var yyyy = mydate.getFullYear(); //获取完整的年份(4位,1970-????)
//	var Month = mydate.getMonth()+1; //获取当前月份(0-11,0代表1月)
//	var Date1= mydate.getDate(); //获取当前日(1-31)
//	var Hours = mydate.getHours(); //获取当前小时数(0-23)
//	var Minutes= mydate.getMinutes(); //获取当前分钟数(0-59)
//	var send = mydate.getSeconds();
//	Minutes = String(Minutes);
//	send = String(send);
//	if(Minutes.length==1){
//		Minutes = "0"+Minutes;
//	}
//	if(send.length==1){
//		send = "0"+send;
//	}
//  var login_time = yyyy+"-"+Month+"-"+Date1+"　"+Hours+" : "+Minutes+" : "+send;
//  return login_time;
//	}
setInterval(function(){
	    $(".current_time").html(current)
	},1000);