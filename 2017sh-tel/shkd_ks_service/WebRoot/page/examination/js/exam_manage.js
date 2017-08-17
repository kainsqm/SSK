var getSysCodestype = function(obj,divid) {
	var url = "/shkd_ks_service/controller/syscode/getSyscodes.do";
	$.ajax({
		url: url,
	  	type:"post",
	  	dataType:"json",
	  	data:{"item_flag":obj},
	  	success: function(data){
	  		for(var i=0;i<data.codes.length;i++) {
	  			var code = data.codes[i];
	  			document.getElementById(divid).options.add(new Option(code.name,code.pkAutoId));
	  		}
	  	}
	});
}

