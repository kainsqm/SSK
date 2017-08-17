/*
 * 创建时间2016-04-21
 * no1:支持单独弹出遮罩层
 * no2:支持弹出新窗体
 */

/**
 * 打开遮罩层
 * return  遮罩层ID
 */
function show_zhezhao(){
	var zheZhao = document.createElement("div");
	$(zheZhao).css("position","fixed");
	$(zheZhao).css("top","0");
	$(zheZhao).css("left","0");
	$(zheZhao).css("width","100%");
	$(zheZhao).css("height","100%");
	$(zheZhao).css("background-color","#666666");
	$(zheZhao).css("z-index","999");
	$(zheZhao).css("filter","alpha(Opacity=40)");
	$(zheZhao).css("-moz-opacity","0.4");
	$(zheZhao).css("opacity","0.4");
	$(zheZhao).attr("id","zhe_"+Math.random().toString().replace(".","").substr(0,10));
	document.body.appendChild(zheZhao);
	return $(zheZhao).attr("id");
}
/**
 * 移除元素
 */
function window_remove_obj(objId){
	document.body.removeChild(document.getElementById(objId));
}
/**
 * 打开一个新的窗口
 * @t_title 标题
 * @t_url 加载页面的地址
 * @t_width 窗口宽度
 * @t_height 窗口高度
 * @optionItems 扩展参数
 */
function window_open_iframe(t_title,t_url,t_width,t_height,optionItems){
	var title = (t_title == null) ? "弹出窗口" : t_title;
	var width = (t_width == null || t_width == "") ? "400" : t_width;
	var height = (t_height == null || t_height == "") ? "300" : t_height;
	var main_div_id = "show_"+Math.random().toString().replace(".","").substr(0,10);
	var isEye = (optionItems ==null || optionItems.isEye == null) ? true : optionItems.isEye; //是否显示遮罩层
	var eye_div_id = "";
	if(isEye){
		eye_div_id = show_zhezhao();
	}			
	
	var main_div = document.createElement("div");
	$(main_div).attr("id",main_div_id);
	$(main_div).css("position","fixed");
	$(main_div).css("left","50%");
	$(main_div).css("top","50%");
	$(main_div).css("background-color","#ffffff");
	$(main_div).css("z-index","1000");
	$(main_div).css("border-radius","5px");
	$(main_div).css("font-weight","bold");
	$(main_div).css("color","#535e66");
	$(main_div).css("display","none");			
	$(main_div).css("width",parseInt(width) + "px");
	$(main_div).css("height",parseInt(height) + "px");
	$(main_div).css("overflow","hidden");
	$(main_div).css("margin-left","-" + (parseInt(width)/2) + "px");
	$(main_div).css("margin-top","-" + (parseInt(height)/2) + "px");
	
	var top_div = document.createElement("div");
	$(top_div).css("height","30px");
	$(top_div).css("line-height","30px");
	$(top_div).css("padding","14px 30px");
	$(top_div).css("border-bottom","solid 1px #eef0f1");
	
	var top_div_a = document.createElement("a");
	$(top_div_a).attr("title","关闭");
	$(top_div_a).attr("href","javascript:void(0);");
	if(isEye){
		$(top_div_a).attr("onclick","window_remove_obj(\""+main_div_id+"\");window_remove_obj(\""+eye_div_id+"\");");
	}else{
		$(top_div_a).attr("onclick","window_remove_obj(\""+main_div_id+"\");");
	}			
	$(top_div_a).css("position","absolute");
	$(top_div_a).css("text-decoration","none");
	$(top_div_a).css("width","12px");
	$(top_div_a).css("height","12px");
	$(top_div_a).css("background","url(img/xcConfirm/icons.png) no-repeat -48px -96px");
	$(top_div_a).css("top","22px");
	$(top_div_a).css("right","30px");
	$(top_div_a).css("cursor","pointer");
	top_div.appendChild(top_div_a);
	
	var top_div_span = document.createElement("span");
	top_div_span.innerHTML = title;
	$(top_div_span).css("font-size","18px");
	$(top_div_span).css("height","30px");
	$(top_div_span).css("line-height","30px");
	$(top_div_span).css("position","absolute");
	$(top_div_span).css("top","15px");
	$(top_div_span).css("left","20px");
	top_div.appendChild(top_div_span);
	
	var center_div = document.createElement("div");
	var center_iframe = document.createElement("iframe");
	$(center_iframe).attr("width",(parseInt(width) - 2) + "px");//parseInt width height
	$(center_iframe).attr("height",(parseInt(height) - 60) + "px");
	$(center_iframe).attr("frameborder","0");
	$(center_iframe).attr("scrolling","no");
	$(center_iframe).attr("src",t_url);			
	
	center_div.appendChild(center_iframe);
	main_div.appendChild(top_div);
	main_div.appendChild(center_div);
	document.body.appendChild(main_div);
	$(main_div).slideDown();
}