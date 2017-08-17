$(function() {
	//alert(".height()*2"+$("body").height()*2);
	//alert(".height()/2"+$(".all").height()/2);
   // $(".all").css("top",$("body").height()*2-$(".all").height()/2);
	$(".all").css("top","-34");
	$(".nav li").eq(0).addClass("cur");
	$(".content ul").eq(0).addClass("hex");
	hex_jion();
	$(".nav li").on("click",function(){
			var i = $(this).index();
			$(".content>ul").eq(i).addClass("hex").show().siblings().hide().removeClass("hex");
			$(this).addClass("cur").siblings().removeClass("cur");
			hex();
		})
})
function hex_jion(){
	$(".hex>li").each(function() {
//		$(this).css({
//			"left":"290px",
//			"top":"165px"
//		})
//		$(".content").css("background","");
		var li_num = $(".hex>li").length;
		var i = $(this).index();
				if (li_num == 3) {
					switch (i) {
						case 0:
							li_left = "130px",
							li_top = "30px",
							li_color="#2DA0FF"
							break;
						case 1:
							li_left = "450px",
							li_top = "30px",
							li_color="#F9A92C"
							break;
						case 2:
							li_left = "300px",
							li_top = "310px",
							li_color="#8A0000"
							break;
					}
				}else if (li_num == 4) {
					switch (i) {
						case 0:
							li_left = "130px",
							li_top = "30px",
							li_color="#2DA0FF"
							break;
						case 1:
							li_left = "450px",
							li_top = "30px",
							li_color="#3FA79C"
							break;
						case 2:
							li_left = "450px",
							li_top = "290px",
							li_color="#8A0000"
							break;
						case 3:
							li_left = "130px",
							li_top = "290px",
							li_color="#643AE6"
							break;
					}
				}else if(li_num == 5) {
					switch (i) {
						case 0:
							li_left = "160px",
							li_top = "15px",
							li_color="#2DA0FF"
							break;
						case 1:
							li_left = "440px",
							li_top = "15px",
							li_color="#4EC102"
							break;
						case 2:
							li_left = "550px",
							li_top = "240px",
							li_color="#8A0000"
							break;
						case 3:
							li_left = "300px",
							li_top = "310px",
							li_color="#643AE6"
							break;
						case 4:
							li_left = "50px",
							li_top = "240px",
							li_color="#EC34F8"
							break;
					}
				}else if(li_num == 6) {
					switch (i) {
						case 0:
							li_left = "170px",
							li_top = "5px",
							li_color="#3DB3A3"
							break;
						case 1:
							li_left = "440px",
							li_top = "5px",
							li_color="#2DA0FF"
							break;
						case 2:
							li_left = "580px",
							li_top = "160px",
							li_color="#4EC102"
							break;
						case 3:
							li_left = "440px",
							li_top = "310px",
							li_color="#8A0000"
							break;
						case 4:
							li_left = "170px",
							li_top = "310px",
							li_color="#643AE6"
							break;
						case 5:
							li_left = "20px",
							li_top = "160px",
							li_color="#EC34F8"
							break;
					}
				}
				$(this).find(".triangle_left").css("border-right-color",li_color);
				$(this).find(".square").css("background-color",li_color);
				$(this).find(".triangle_right").css("border-left-color",li_color);
				$(this).css({
						"left": li_left,
						"top": li_top
				});
//              $(this).animate({
//              	left:li_left,
//              	top:li_top
//              	},1000,function(){
//              		$(".content").css("background","url(img/icon"+i+".png) no-repeat center");
//              	});
                $(".content").css("background","url(../img/icon"+i+".png) no-repeat center");
	})
}


function hex(){
	$(".hex>li").each(function() {
		$(this).css({
			"left":"290px",
			"top":"165px"
		})
		$(".content").css("background","");
		var li_num = $(".hex>li").length;
		var i = $(this).index();
				if (li_num == 3) {
					switch (i) {
						case 0:
							li_left = "130px",
							li_top = "30px",
							li_color="#2DA0FF"
							break;
						case 1:
							li_left = "450px",
							li_top = "30px",
							li_color="#F9A92C"
							break;
						case 2:
							li_left = "300px",
							li_top = "310px",
							li_color="#8A0000"
							break;
					}
				}else if (li_num == 4) {
					switch (i) {
						case 0:
							li_left = "130px",
							li_top = "30px",
							li_color="#2DA0FF"
							break;
						case 1:
							li_left = "450px",
							li_top = "30px",
							li_color="#3FA79C"
							break;
						case 2:
							li_left = "450px",
							li_top = "290px",
							li_color="#8A0000"
							break;
						case 3:
							li_left = "130px",
							li_top = "290px",
							li_color="#643AE6"
							break;
					}
				}else if(li_num == 5) {
					switch (i) {
						case 0:
							li_left = "160px",
							li_top = "15px",
							li_color="#2DA0FF"
							break;
						case 1:
							li_left = "440px",
							li_top = "15px",
							li_color="#4EC102"
							break;
						case 2:
							li_left = "550px",
							li_top = "240px",
							li_color="#8A0000"
							break;
						case 3:
							li_left = "300px",
							li_top = "310px",
							li_color="#643AE6"
							break;
						case 4:
							li_left = "50px",
							li_top = "240px",
							li_color="#EC34F8"
							break;
					}
				}else if(li_num == 6) {
					switch (i) {
						case 0:
							li_left = "170px",
							li_top = "5px",
							li_color="#3DB3A3"
							break;
						case 1:
							li_left = "440px",
							li_top = "5px",
							li_color="#2DA0FF"
							break;
						case 2:
							li_left = "580px",
							li_top = "160px",
							li_color="#4EC102"
							break;
						case 3:
							li_left = "440px",
							li_top = "310px",
							li_color="#8A0000"
							break;
						case 4:
							li_left = "170px",
							li_top = "310px",
							li_color="#643AE6"
							break;
						case 5:
							li_left = "20px",
							li_top = "160px",
							li_color="#EC34F8"
							break;
					}
				}
				$(this).find(".triangle_left").css("border-right-color",li_color);
				$(this).find(".square").css("background-color",li_color);
				$(this).find(".triangle_right").css("border-left-color",li_color);
//				$(this).css({
//						"left": li_left,
//						"top": li_top
//				});
                $(this).animate({
                	left:li_left,
                	top:li_top
                	},1000,function(){
                		$(".content").css("background","url(../img/icon"+i+".png) no-repeat center");
                	});
	})
}