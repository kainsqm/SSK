$(function(){

   $(".tcc").prepend("<div class='housing_covering'><div class='pop_up_window' id='moveId'><h3><b>播放录音</b><span>×</span></h3>"+
   "<iframe name='I1' id='iframe'width='100%' height='100%' src='' target='maincontent'   frameborder='0' scrolling='auto' ></div></div>"
   )
   
})
//可拖动

function win_move(){
     $(".housing_covering").css({
         "width":"",
         "height":""
     }).show().find("h3").show();
     $(".pop_up_window h3 span").on("click",function(){
         $(".housing_covering").hide();
         $(".pop_up_window").children("iframe").attr("src","");
 }); 
    (function($) {
					//拖拽插件,参数:id或object
					$.Move = function(_this) {
						if (typeof(_this) == 'object') {
							_this = _this;
						} else {
							_this = $("#" + _this);
						}
						if (!_this) {
							return false;
						}
						_this.css({
							'position': 'absolute'
						}).hover(function() {
							$(this).css("cursor", "move");
						}, function() {
							$(this).css("cursor", "default");
						})
						_this.mousedown(function(e) { //e鼠标事件
							var offset = $(this).offset();
							var x = e.pageX - offset.left;
							var y = e.pageY - offset.top;
							
							$(document).bind("mousemove", function(ev) { //绑定鼠标的移动事件，因为光标在DIV元素外面也要有效果，所以要用doucment的事件，而不用DIV元素的事件
								_this.bind('selectstart', function() {
									return false;
								});
								var _x = ev.pageX - x; //获得X轴方向移动的值
								var _y = ev.pageY - y; //获得Y轴方向移动的值
								_this.css({
									'left': _x + "px",
									'top': _y + "px"
								});
							});
						});
						$(document).mouseup(function() {
							$(this).unbind("mousemove");
						})
					};
				})(jQuery)
		$.Move($('#moveId'));
}
//无法拖动
function no_move(){
        $(".housing_covering").css({
           "width":"100%",
           "height":"100%"
        }).show().find("h3").hide();
		$(".housing_covering").on("click",function(e){
			var b = $(".housing_covering").find(".pop_up_window");
			if($(e.target).closest(".pop_up_window").length == 0 && b.css("display") == "block")
			{
				$(".housing_covering").hide();
			}
        })
}