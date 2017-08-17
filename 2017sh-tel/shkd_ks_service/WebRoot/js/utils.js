

/**************
简化document.getElementById
用法：
$id("d").innerHTML; 
(HTMLcode:<div id="d">cssrain</div>)
**************/
function $id(id) {
	return document.getElementById(id);
	}


/**************
函数：getElementsByClassName
使用方法：
获取document内的超链接class是“info-links”的。
getElementsByClassName(document, "a", "info-links");
获取container内的div的class是col的.
getElementsByClassName(document.getElementById("container"), "div", "col"); 
获取document内的所有class是“click-me”的。
getElementsByClassName(document, "*", "click-me");
例子：
HTML code:
<a class="a">ccc</a>
<a class="info-links">aaa</a>
<a class="info-links">bbb</a>
<br/><br/>
<div class="co">dddd</div>
<div class="col">dddd</div>
<div id="container">
<div class="co">dddd</div>
<div class="col">dddd</div>
<div class="co">dddd</div>
<div class="col">dddd</div>
<a class="a">ccc</a>
</div>

JS code:
test 1 :
var a = getElementsByClassName(document,"a","info-links");
alert(a[0].innerHTML +"   "+a[1].innerHTML);
for(var i=0;i<a.length;i++){
a[i].style.color="red";
}
//test 2 :
var b = getElementsByClassName(document.getElementById("container"), "div", "col");
for(var m=0;m<b.length;m++){
b[m].style.color="red";
}
**************/
function getElementsByClassName(oElm, strTagName, strClassName){
var arrElements = (strTagName == "*" && oElm.all)? oElm.all : oElm.getElementsByTagName(strTagName);
var arrReturnElements = new Array();
strClassName = strClassName.replace(/-/g, "\-");
var oRegExp = new RegExp("(^|\s)" + strClassName + "(\s|$)");	
var oElement;	
for(var i=0; i<arrElements.length; i++){	
oElement = arrElements[i];	
if(oRegExp.test(oElement.className)){	
arrReturnElements.push(oElement);	
  }	
}
return (arrReturnElements)
}




/**************
replaceAll：
替换字符串中的字符。
用法：
yourstring.replaceAll("要替换的字符", "替换成什么");
例子:
"cssrain".replaceAll("s", "a");
"   cs   sr   ai   n".replaceAll(" ", "");
**************/
String.prototype.replaceAll = function (AFindText,ARepText){
                raRegExp = new RegExp(AFindText,"g");
                return this.replace(raRegExp,ARepText);
}


/**************
 * 字符串前后空格处理。
 * 如果想替换中间的空格，请用replaceAll方法。
 * 用法：
 * "  cssrain   ".trim();
**************/
String.prototype.trim=function()
{
          return this.replace(/(^\s*)|(\s*$)/g,"");//将字符串前后空格,用空字符串替代。
}


/**************
format：
格式化时间。
用法：
yourdate.format("你的日期格式");
例子:
obj0 = new Date("Sun May 04 2008").format("yyyy-MM-dd");
obj1 = new Date().format("yyyy-MM-dd hh:mm:ss");
obj2 = new Date().format("yyyy-MM-dd");
obj3 = new Date().format("yyyy/MM/dd");
obj4 = new Date().format("MM/dd/yyyy");
**************/
Date.prototype.format = function(format)   
{   
   var o = {   
     "M+" : this.getMonth()+1, //month   
     "d+" : this.getDate(),    //day   
     "h+" : this.getHours(),   //hour   
     "m+" : this.getMinutes(), //minute   
     "s+" : this.getSeconds(), //second   
     "q+" : Math.floor((this.getMonth()+3)/3), //quarter   
     "S" : this.getMilliseconds() //millisecond   
   }   
   if(/(y+)/.test(format)) format=format.replace(RegExp.$1,   
     (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
   for(var k in o)if(new RegExp("("+ k +")").test(format))   
     format = format.replace(RegExp.$1,   
       RegExp.$1.length==1 ? o[k] :    
         ("00"+ o[k]).substr((""+ o[k]).length));   
   return format;   
} 


/**************
 * 得到单选框选中的值。
 * 用法：
 *<input type="radio"  value="1" name="cssrain"/>
 *<input type="radio"  value="2" name="cssrain" checked/>
 *<input type="radio"  value="3" name="cssrain"/>
 *<input type="button" onclick="alert(getRadioValue('cssrain'))" value="test"/>
**************/
function getRadioValue(radioName){
	var obj=document.getElementsByName(radioName);
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked){
			return obj[i].value;
		}
	}
} 

/**************
 * 复选框全选/不选/反选
 * 用法：
<form id="form_a">
<input type="checkbox"  value="1" name="a"/>
<input type="checkbox"  value="2" name="a" checked/>
<input type="checkbox"  value="3" name="a"/>
<input type="button" value="全选" onclick="checkAll(document.getElementById('form_a'),'all')"/>
<input type="button" value="不选" onclick="checkAll(document.getElementById('form_a'),'none')"/>
<input type="button" value="反选" onclick="checkAll(document.getElementById('form_a'),'')"/>
</form>
**************/
function checkAll(form, sel) {
	for (i = 0, n = form.elements.length; i < n; i++) {
		if(form.elements[i].type == "checkbox") {
			if(form.elements[i].checked == true) {
				form.elements[i].checked = (sel == "all" ? true : false);
			} else {
				form.elements[i].checked = (sel == "none" ? false : true);
			}
		}else{
			if(form.elements[i].checked == true){
				form.elements[i].checked = false;
			}
		}
	}
}


/**************
 * 复选框检查是否选中。
 * 如果没一个选中，会返回false.
 * 用法：
 <form id="form_a" name="form_a">
<input type="checkbox"  value="1" name="a"/>
<input type="checkbox"  value="2" name="a" checked/>
<input type="checkbox"  value="3" name="a"/>
<input type="button" value="全选" onclick="alert( SCheckBox('form_a','a') )"/>
</form>
**************/
function SCheckBox(_formName,_checkboxName){
	var selflag = {'checked':0,'cvalues':[]};
	_scheckbox = eval('document.'+_formName+'.'+_checkboxName);
	if(_scheckbox){
		if(eval(_scheckbox.length)){
			for(i=0;i<_scheckbox.length;i++){
				if(_scheckbox[i].checked){
					selflag.checked++;
					selflag.cvalues.push(_scheckbox[i].value);
				}
			};
		}else if(_scheckbox.checked){
			selflag.checked++;
			selflag.cvalues.push(_scheckbox.value);
		}
		if(selflag.checked){
			return selflag;
		}
	}
	return false;
}


/**************
收藏到书签.(兼容IE和FF)。
用法:
<input type="button" value="收藏" onclick="addBookmark('cssrain(前端开发)','http://www.cssrain.cn')"/> 
**************/
function addBookmark(title,url) {
	if (window.sidebar) {
		window.sidebar.addPanel(title, url,"");
	} else if( document.all ) {
		window.external.AddFavorite( url, title);
	} else if( window.opera && window.print ) {
		return true;
	}
}

/**************
函数 ： 文本框得到与失去焦点 操作。
这个方法经常在文本框搜索的时候出现。
文本里显示 “ 搜索 ”，然后当用户鼠标点击此文本，
文本框内容清空。如果用户没填写内容，那么文本的值又复原。
如果填写了，就显示用户填写的。
 用法:
 <input type="" value="关键字搜索" name="a" onfocus="clearTxt('a','关键字搜索')" onblur="fillTxt('a','关键字搜索')"/>
<input type="text" value="test" name="test" />
**************/
function clearTxt(id,txt) {
  if (document.getElementById(id).value == txt)
    document.getElementById(id).value="" ;
  return ;
}
function fillTxt(id,txt) {
  if ( document.getElementById(id).value == "" )
    document.getElementById(id).value=txt;
  return ;
}


/**************
函数 ： 用来判断鼠标按的是左键还是右键。(兼容IE和ff)
用法:
onmousedown="mouse_keycode(event)"
**************/
function mouse_keycode(event){
    var event=event||window.event;
    var nav=window.navigator.userAgent;
    if (nav.indexOf("MSIE")>=1) //如果浏览器为IE 
  { 
   if(event.button==1){alert("左键")}
   else if(event.button==2){alert("右键")}
  }
  else if(nav.indexOf("Firefox")>=1) ////如果浏览器为Firefox 
  {
   if(event.button==0){alert("左键");}
   else if(event.button==2){alert("右键");}
  }
   else{ //如果浏览器为其他 
    alert("other");
   } 
}


/**************
函数 ：触发某个对象的onclick事件。（兼容IE和FF）
用法: 
<input type="button" value="aaa" id="a" onclick=" alert('cssrain') " />
<input type="button" value="触发ID为a的onclick事件" onclick=" handerToClick('a') " />
**************/
function handerToClick(objid){
	var obj=document.getElementById(objid);
	if(document.all){
		obj.fireEvent("onclick");
	}else{
	  	var e=document.createEvent('MouseEvent');
	  	e.initEvent('click',false,false);
	  	obj.dispatchEvent(e);
	}
}


/**************
回车提交。
用法:
<input   type=text   onkeydown="keysubmit()">   
**************/
function keysubmit()
{
    if(event.keyCode==13)
   {
	   form.submit();
   }
}

/**************
实现Ctrl+Enter 提交的效果.(兼容IE和FF)
在做这个效果时，发现一个问题，
当表单中如果只有一个文本框时，
回车会默认提交。(没有提交按钮也一样。)
用法:
<form action="#"  name="a">
<input   type="text" />   
<input   type="text"   onkeydown="QuickPost( event , document.a )" />   
</form>
**************/
 function QuickPost(event,form){
    var event=event||window.event;
	if((event.ctrlKey && event.keyCode == 13)||(event.altKey && event.keyCode == 83)){
 	//	event.srcElement.form.submit();
	  form.submit();
	}
}


/**************
回车自动跳到下一个文本框。
注;此方法不兼容FF，
因为在FF下，event.keycode是只读属性，不能赋值。
用法:
<form action="#"  name="a" onkeydown="QuickNext()">
<input   type="text" />   
<input   type="text"   />   
<input   type="button" value="test"  />  
</form>
**************/
function   QuickNext()   
  {  
  //判断是否为button, 是因为在HTML上会有type="button"
  //判断是否为submit,是因为HTML上会有type="submit"
  //判断是否为reset,是因为HTML上的"重置"应该要被执行
  //判断是否为空,是因为对于HTML上的"<a>链接"也应该被执行,
  //这种情况发生的情况不多,可以使用"tabindex=-1"的方式来取消链接获得焦点.
      if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!='') 
           {   event.keyCode = 9; }
  }   



/**************
TEXTAREA自适应文字的行数 .
注;此方法不兼容FF，(onpropertychange)
参数：当前对象 和 最小高度
用法:
<textarea rows=5 name=s1 cols=27 onpropertychange="textarea_scroll(this,60)" style="overflow-y:hidden">
</textarea>
**************/
	function textarea_scroll(obj,min)
	{
		if(obj.scrollHeight<min){
          obj.style.posHeight=min
		}else{
          obj.style.posHeight=obj.scrollHeight
		}
	}


/**************
改变下拉框选项后，根据选项的不同弹出不同的窗口.
用法:
<select onchange="return select_pop(this);" >
<option  selected="selected">--分公司--</option>
<option value="http://www.bj.chinaunicom.com">北京</option>
<option value="http://www.sh.chinaunicom.com">上海</option>
</select> 
**************/
function select_pop(fileurl){
    if (fileurl.options[fileurl.selectedIndex].value != "")
		window.open(fileurl.options[fileurl.selectedIndex].value,"_blank","toolbar=yes,location=yes,menubar=yes,scrollbars=yes,resizable=yes,left=50,height=500,width=700");
	return true;
}



/**************
得到字符串的字节数。
用法:
<input   type="text" name="a" />   
<input   type="button" value="test"  onclick="alert(  strlen(document.getElementById('a').value )  )" />  
**************/
//
function strlen(string){
      var str="";
      str=string;
      str=str.replace(/[^\x00-\xff]/g,"**"); 
      return str.length;
 }
//或者
function ByteLength(string){
	return string.replace(/[^\x00-\xff]/g,"00").length;	
}


/**************
文本框输入字符控制。
只能输入数字。
用法:
<input onkeyup="input_shuzi(this)" onbeforepaste="input_shuzi_before()" />
**************/
function input_shuzi(obj)
{
obj.value=obj.value.replace(/[^\d]/g,'');
}
function input_shuzi_before(){
clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''));
}

/**************
文本框输入字符控制。
只能输入数字和英文。
用法:
<input onkeyup="input_shuziyinwen(this)" onbeforepaste="input_shuziyinwen_before()" />
**************/
function input_shuziyinwen(obj)
{
obj.value=obj.value.replace(/[\W]/g,'');
}
function input_shuziyinwen_before(){
clipboardData.setData('text',clipboardData.getData('text').replace(/[\W]/g,''));
}

/**************
文本框输入字符控制。
只能输入汉字。
用法:
<input onkeyup="input_hanzi(this)" onbeforepaste="input_hanzi_before()" />
**************/
function input_hanzi(obj)
{
obj.value=obj.value.replace(/[^\u4E00-\u9FA5]/g,'');
}
function input_hanzi_before(){
clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4E00-\u9FA5]/g,''));
}
/**************
文本框提交校验。
只能输入汉字，字母，数字或混编组合。
用法:flag : true , 可以为数字；false ，不可为数字。
**************/
function text_req(objValue,flag){
	//alert(!isNum(objValue)||flag);
	if(!isNum(objValue)||flag){
		var str = /^[a-z\d\u4E00-\u9FA5]+$/i;
		//alert(str.test(objValue));
		return str.test(objValue);
	}else{
		return false;
	}
}
/**************
是数字
**************/
function isNum(objValue){
	var str = /[\d]/g;
	return str.test(objValue);
}
/**************
文本框输入字符控制。
只能输入全角。
用法:
<input onkeyup="input_quanjiao(this)" onbeforepaste="input_quanjiao_before()" />
**************/
function input_quanjiao(obj)
{
obj.value=obj.value.replace(/[^\uFF00-\uFFFF]/g,'');
}
function input_quanjiao_before(){
clipboardData.setData('text',clipboardData.getData('text').replace(/[^\uFF00-\uFFFF]/g,''));
}


/**************
*只允许输入数字和小数点。
*用法：
*<input type=text  onkeyup="clearNoNum(this)"/>
**************/
function clearNoNum(obj)
{
//先把非数字的都替换掉，除了数字和.
obj.value = obj.value.replace(/[^\d.]/g,"");
//必须保证第一个为数字而不是.
obj.value = obj.value.replace(/^\./g,"");
//保证只有出现一个.而没有多个.
obj.value = obj.value.replace(/\.{2,}/g,".");
//保证.只出现一次，而不能出现两次以上
obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}

/**************
*把输入的字母转成大写。
*用法：
*<input type="text"  name="d" onkeyup="input_to_uppercase(this)" />
**************/
function input_to_uppercase(obj)
{
obj.value=obj.value.toUpperCase();
}
/**************
*把输入的字母转成小写。
*用法：
*<input type="text"  name="e" onkeyup="input_to_lowercase(this)" /> 
**************/
function input_to_lowercase(obj)
{
obj.value=obj.value.toLowerCase();
}


/**************
*判断字符中是否包含有 http:// .
*用法：
<input type="text" name="a" />
<input type="button" value="test" onclick=" alert( urlcheck(document.getElementById('a').value) )" />
**************/
function urlcheck(string){
	var re;
	re=new RegExp("http://");
	return re.test(string.toLowerCase());	
}

/**************
*判断电子邮箱是否符合规范
*用法：
<input type="text" name="a" />
<input type="button" value="test" onclick=" alert( emailcheck(document.getElementById('a').value) )" />
**************/
function emailcheck(string){
	var re;
	re=new RegExp("^[\\w-_\\.]+@([a-z|0-9|-]+\\.)+[a-z]{2,5}$");
	return re.test(string.toLowerCase());
}


/**************
*判断用户名是否符合要求
*用法：
<input type="text" value="aaa" id="a" />
<input type="button" value="test" onclick=" alert( usernamecheck(document.getElementById('a').value )  ) " />
**************/
function usernamecheck(string){
	if((string.length<4)||(string.length>20)){return false;}
	var re;
	re=new RegExp("^[a-z|A-Z|0-9][a-z|A-Z|0-9|-]+$");
	return re.test(string);
}


/***************
* 用于检验手机号的位数以及检验此手机中是否为中国移动的手机号
* 如果还想判断联通的手机，可以改 GSMPhNo  。
用法 ：
<form name="toptransfer">
<input type="text" name="phone" />
<input type="button" value="test" onclick="checkFetionReg()"/>
</form>
**************/
function checkMBPhone(phone){
	var GSMPhNo = /^(13[4-9])|(159)|(158)|(150)|(151)/; //以134(5、6、7、8、9)或159,158,151,150开头;
	var num11 = /^\d{11}$/; //11位数字;
	if( "" != phone ){
	  if(num11.exec(phone)){
	    if(GSMPhNo.exec(phone)){
	      return true;
	    }else{
	      alert("对不起，请您正确输入中国移动GSM手机号码(以134-139、159、158、151或150开头)!");
	      return false;
	    }
	  }else{
	    alert("请正确输入11位手机号码(数字)!");
	    return false;
	  }
	}else{
	  alert("对不起，请输入您的手机号码!");
	  return false;
	}
} 
  function checkFetionReg(){ //例子
   if(checkMBPhone(document.toptransfer.phone.value))
   {
            alert("正确");
			//do your things
   }
 }



/***************
获取域名.
**************/
function getDomainName(){
  var s,siteUrl;
  s=document.location+"";
  return s.substring(7,s.indexOf('/',7));
}



/***************
* 写入COOKIE
用法 ：
setcookie:<input type="button" value="test" onclick=" setCookie('a','cssrain');alert('设置成功.') " />
getcookie:<input type="button" value="test" onclick=" alert( getCookie('a') ) " />
**************/
function setCookie(name, value){
	//document.cookie = name+"="+value
	date = new Date();
    document.cookie = name +"=" + escape(value) + ";expires=" + new Date(date.getYear()+1, date.getMonth(),date.getDate()).toGMTString() + ";path=/";
}
/***************
* 简单的读取Cookie
**************/
function getCookie(Name){ 
	var re=new RegExp(Name+"=[^;]+", "i");
	if (document.cookie.match(re)){
		return document.cookie.match(re)[0].split("=")[1];
	}else{
		return "";
	}		
}
/*
读取Cookie写法2：
function getCookie(name){
var aCookie = document.cookie.split(";");
for(var i=0; i<aCookie.length; i++)
{
	var aC = aCookie[i].split("=");
	var nTemp = aC[0].replace(' ','');
	if(name == nTemp)
	{
		return unescape(aC[1]);
	}
}
	return "";
}
*/



/***************
* 子窗口刷新父窗口.(写在子窗口里)
**************/
function opener_reload()
{
window.opener.location.reload();
}


/**************
* 不被浏览器拦截的弹出窗口JS代码:
* 程序弹出的窗口将不会被广告拦截软件拦截，但有一个缺点：你无法象对window.open弹出的窗口那样对外观进行定制。
* 用法：<input type=button onclick='window.force.open("a.html")' />
* 定义ForceWindow类构造函数
* 无参数
* 无返回值.
* 实例化一个ForceWindow对象并做为window对象的一个子对象以方便调用
* 定义后可以这样来使用：window.force.open("URL");
* 你当然也可以在使用前实例化一个ForceWindow对象：
* var myWindow = new ForceWindow();
* 这样来使用：
* myWindow.open("URL");
* 本程序测试通过的浏览器：IE 5+、Firefox 1.0、Mozilla 1.7.5、Netscape 7.2、Opera 7.23
**************/
function ForceWindow ()
{
this.r = document.documentElement;
this.f = document.createElement("FORM");
this.f.target = "_blank";
this.f.method = "post";
this.r.insertBefore(this.f, this.r.childNodes[0]);
}
ForceWindow.prototype.open = function (sUrl) //定义open方法 , 参数sUrl：字符串，要打开窗口的URL, 无返回值
{
this.f.action = sUrl;
this.f.submit();
}
window.force = new ForceWindow();


/**************
拷贝/复制文本框内容。（兼容IE和FF）
用法:
1，<input type="text"  name="d" id="d" value="&lt;http://www.cssrain.cn&gt;&lt;http://www.cssrain.cn&gt;" /><input id="Button1" type="button" onclick="copyText(document.getElementById('d'));" value="复制" />  <br/>
2，<textarea name="c"  id="c" rows="4" cols="20">&lt;http://www.cssrain.cn&gt;</textarea>
<input id="Button2" type="button" onclick="copyText(document.getElementById('c'));" value="复制" />
**************/
function copyText(obj)
{
    if( obj.type !="hidden" )
    {
        obj.focus();
    }
    obj.select(); 
    copyToClipboard(obj.value);
    alert("拷贝成功！");
}
function copyToClipboard(txt) {  
    if(window.clipboardData)  
    {  
        window.clipboardData.clearData();  
        window.clipboardData.setData("Text", txt);  
    }  
    else if(navigator.userAgent.indexOf("Opera") != -1)  
    {  
        window.location = txt;  
    }  
    else if (window.netscape)  
    {  
        try {  
            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");  
        }  
        catch (e)  
        {  
            alert("您使用的浏览器不支持此复制功能，请使用ctrl+c或者浏览器右键复制");  
        }  
        var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);  
        if (!clip)  
            return;  
        var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);  
        if (!trans)  
            return;  
        trans.addDataFlavor('text/unicode');  
        var str = new Object();  
        var len = new Object();  
        var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);  
        var copytext = txt;  
        str.data = copytext;  
        trans.setTransferData("text/unicode",str,copytext.length*2);  
        var clipid = Components.interfaces.nsIClipboard;  
        if (!clip)  
            return false;  
        clip.setData(trans,null,clipid.kGlobalClipboard);  
    }  
    return true;  
}



/**************
//间歇性循环滚动新闻。从下往上滚动。
//拷贝以下内容，到单独的页面 即可。

<div id="icefable1" style="font-size:12px;">
<div style="text-align:left; width:100%; height:20px;"><a target="_blank" href="#" title="111">111</a></div>
<div style="text-align:left; width:100%; height:20px;"><a target="_blank" href="#" title="222">222</a></div>
<div style="text-align:left; width:100%; height:20px;"><a target="_blank" href="#" title="333">333</a></div>
<div style="text-align:left; width:100%; height:20px;"><a target="_blank" href="#" title="444">444</a></div>       
<SCRIPT LANGUAGE="JavaScript">
var marqueesRows=4; //滚动行数，与上面的div数目对应
var marqueesHeight=20; //滚动区域的高度，请同时修改上面div样式内的高度
var marqueeSpeed=30; //滚动速度(越小越快)
var pauseTime=70; //停留时间
var stopscroll=false;
var preTop=0, theTop=marqueesHeight*marqueesRows, currentTop=marqueesHeight, stoptime=0;
with(icefable1){
	style.width=186; //滚动区域的宽度
	style.height=marqueesHeight;
	style.overflowX="visible";
	style.overflowY="hidden";
	noWrap=false;
	onmouseover=new Function("stopscroll=true");
	onmouseout=new Function("stopscroll=false");
	innerHTML+=innerHTML;
	scrollTop=0;
}
setInterval("scrollUp()",marqueeSpeed);
function scrollUp(){//Modified by Dakular
	if(stopscroll==true) return;
	currentTop+=1;
	if(currentTop==marqueesHeight+1){
		stoptime+=1; currentTop-=1;
		if(stoptime==pauseTime){currentTop=0; stoptime=0;}
	}else {
		preTop=(++icefable1.scrollTop);
		if(preTop==theTop){icefable1.scrollTop=0;}
	}
}
</SCRIPT>
</div>
**************/



/**************
显示/隐藏内容。
用法:
<img src="images/close.gif" id="img_a" onClick="show_hide_display(table_a,img_a)">
<table id="table_a">
<tr>
<td>aaa</td>
</tr>
</table>
**************/
function show_hide_display(t_id,i_id){//显示/隐藏
var t_id;//表格ID
var i_id;//图片ID
var default_img="images/close.gif";//默认图片
var on_img="images/close.gif";//打开时图片
var off_img="images/open.gif";//隐藏时图片
if (t_id.style.display == "none") {//如果为隐藏状态
t_id.style.display="";//切换为显示状态
i_id.src=on_img;}//换图
else{//否则
t_id.style.display="none";//切换为隐藏状态
i_id.src=off_img;}//换图
}



/**************
 * 图片自动缩小方法。
 * 用法：
<div id="d">
<img src="http://www.baidu.com/img/logo-yy.gif" width="300" height="300"/>
<img src="http://www.baidu.com/img/logo-yy.gif" />
</div>

window.onload=function(){
resizeImg(50,'d');
}
**************/
function resizeImg(maxWidth,contentId){
	var imgs=document.getElementById(contentId).getElementsByTagName("img");
	for(var i=0;i<imgs.length;i++){
		if(imgs[i].width>maxWidth){
			imgs[i].removeAttribute('width');
			imgs[i].removeAttribute('height');
			imgs[i].removeAttribute('style');
			imgs[i].width=maxWidth;
			imgs[i].style.cursor="hand";
			imgs[i].onclick = function(){
				window.open(this.src);
			}
		}
	}
}


/**************
判断浏览器和操作系统。
用法:
window.onload=function(){
       alert(getPlatform());
}
**************/
/*--GLOBAL VARIABLES--*/
var OS;
var browser;
function checkIt(string) {
	var detect = navigator.userAgent.toLowerCase();
	return detect.indexOf(string) + 1;
}
function getPlatform() {
	if (checkIt('konqueror')) {
		browser = "Konqueror";
		OS = "Linux";
	}
	else if (checkIt('safari')) 		browser = "Safari"
	else if (checkIt('omniweb')) 		browser = "OmniWeb"
	else if (checkIt('opera')) 			browser = "Opera"
	else if (checkIt('webtv')) 			browser = "WebTV";
	else if (checkIt('icab')) 			browser = "iCab"
	else if (checkIt('msie 7')) 		browser = "Internet Explorer 7"
	else if (checkIt('msie')) 			browser = "Internet Explorer"
	else if (!checkIt('compatible')) 	browser = "Netscape Navigator"
	else 								browser = "Unknown Browser";

	if (!OS) {
		if (checkIt('linux')) 		OS = "Linux";
		else if (checkIt('x11')) 	OS = "Unix";
		else if (checkIt('mac')) 	OS = "Mac"
		else if (checkIt('win')) 	OS = "Windows"
		else 						OS = "Unknown Operating System";
	}
	return browser+"|"+OS;
}


/**************
图片，超链接提示效果.tooltips
用法:
Demo1：<img src="a.gif" onmouseover="showToolTip(event,'<font style=font-size:14px;font-weight:bold;color:#000000>测试1</font><img src=b.gif  border=0 align=absmiddle>');" alt="" onmouseout="hideToolTip();" />
Demo2：<a href="a.html" onmouseover="showToolTip(event,'<font style=font-size:14px;font-weight:bold;color:#000000>测试2</font><img src=b.gif  border=0 align=absmiddle>');" alt="" onmouseout="hideToolTip();">cssrain.cn</a>
引用此方法注意 在页面上加入:
<div id="frDiv_cssrain" style="overflow: visible; position: absolute; visibility: hidden;z-index: 500">
<iframe id="ifr_cssrain" src="javascript:null" style="overflow: visible; position: relative;z-index: 500; width: 342px;height:0px;" scrolling="no" frameborder="0" marginwidth="0" marginheight="0"></iframe>
</div>
**************/
function hideToolTip(){
    parent.document.getElementById("frDiv_cssrain").style.visibility="hidden";
}
function showToolTip(event,msg){ 
        var event =event || window.event;
        var ifr_cssrain = getIFrameDocument("ifr_cssrain");  
        var e_html = ifr_cssrain.createElement("html");
        var e_body = ifr_cssrain.createElement("body");
        e_body.style.marginLeft = "0px"; 
        e_body.style.marginTop = "0px"; 
        e_body.style.marginBottom = "0px"; 
        e_body.style.marginRight = "0px"; 
        var e_div = ifr_cssrain.createElement("div");
        e_div.id = "divContent"; 
        e_div.style.wordWrap="break-word"; 
        e_div.style.backgroundColor="#aad5ff";
        e_div.style.borderStyle="solid"; 
        e_div.style.borderWidth="1px"; 
        e_div.style.borderColor="#336699"; 
        e_div.style.paddingLeft = "3px"; 
        e_div.style.paddingTop = "3px"; 
        e_div.style.paddingBottom = "3px"; 
        e_div.style.paddingRight = "3px"; 
        e_div.innerHTML = msg; 
        e_body.appendChild(e_div); 
        e_html.appendChild(e_body);      
        ifr_cssrain.body.innerHTML = e_body.innerHTML; 
        var oBody = ifr_cssrain.getElementById("divContent"); 
        var oFrame = document.getElementById("ifr_cssrain"); 
        var oFrDiv = document.getElementById("frDiv_cssrain"); 
        oFrame.style.height = oBody.offsetHeight; 
        //oFrame.style.width = oBody.offsetWidth;
        oFrDiv.style.visibility="visible"; 
/*event.x与event.y问题
说明:IE下,even对象有x,y属性,但是没有pageX,pageY属性;
Firefox下,even对象有pageX,pageY属性,但是没有x,y属性. 
解决方法:
使用mX(mX = event.x ? event.x : event.pageX;)
来代替IE下的event.x或者Firefox下的event.pageX. 
*/
        oFrDiv.style.left =  ((event.x ? event.x : event.pageX)+1)+"px"; 
        oFrDiv.style.top =  ((event.y ? event.y : event.pageY)+1)+"px";  
}
function   getIFrameDocument(aID)   { 
var   rv   =   null;   
if   (document.getElementById(aID).contentWindow){ 
rv   =   document.getElementById(aID).contentWindow.document; 
}   else   { 
//   IE 
rv   =   document.frames[aID].document; 
} 
return   rv; 
} 


/**************
*window.open居中打开.(兼容IE和FF)
*用法：
*<input type=button  onclick="NewWindow('a.html','cssrain','200','200',yes)"/>
**************/
var win = null;
function NewWindow(mypage,myname,w,h,scroll){
LeftPosition = (screen.width) ? (screen.width-w)/2 : 0;
TopPosition = (screen.height) ? (screen.height-h)/2 : 0;
settings ='height='+h+',width='+w+',top='+TopPosition+',left='+LeftPosition+',scrollbars='+scroll+',resizable';
win = window.open(mypage,myname,settings);
win.focus();
}


/* pengpeng滚动组件 */
/*
组件注意地方：滚动的部分高度或宽度要高于滚动框架的高度或宽度。调用方式放在Html架构代码下方。
关于w_demo的问题，由于横向滚动的时候需要调整整体的宽度，所以要多套一层框架。
<script language="JavaScript" type="text/javascript" src="ppRoll.js"></script>
(上下）
<div id="demo">
	<div id="demo1">
	滚动主题
	</div>
	<div id="demo2">
	</div>
</div>
（左右）
<div id="demo">
<table border=0 cellspacing=0 cellpadding=0>
    <tr>
      <td id="demo1">滚动内容(注意横向滚动内容的东西里一定要有宽度，比如嵌入一table，一定要让它有宽度)</td>
      <td id="demo2"></td>
    </tr>
  </table>
</div>
//调用方式
<script type="text/javascript">
var ppRoll = new ppRoll({
					speed:60, 		//速度
					demo:"demo",	//外框架div
					demo1:"demo1",	//滚动主体div
					demo2:"demo2",	//复制的div
					objStr:"ppRoll",	//创建的对象名
					width:"192px",	//外框架demo的宽度
					height:"360px",	//外框架demo的高度
					direction:"top"	//滚动方向，可选值：top、down、left、right
					});
</script>
*/
function ppRoll(a)
{
	this.myA = a;
	this.myA.IsPlay = 1;
	this.$(a.demo).style.overflow = "hidden";
	this.$(a.demo).style.width = a.width;
	this.$(a.demo).style.height = a.height;
	this.$(a.demo2).innerHTML=this.$(a.demo1).innerHTML;
	this.$(a.demo).scrollTop=this.$(a.demo).scrollHeight;
	this.Marquee();
	this.$(a.demo).onmouseover=function() {eval(a.objStr+".clearIntervalpp();");}
	this.$(a.demo).onmouseout=function() {eval(a.objStr+".setTimeoutpp();")}
}
ppRoll.prototype.$ = function(Id)
{
	return document.getElementById(Id);
}
ppRoll.prototype.getV = function(){ 
alert(this.$(this.myA.demo2).offsetWidth-this.$(this.myA.demo).scrollLeft);
alert(this.$(this.myA.demo2).offsetWidth);
alert(this.$(this.myA.demo).scrollLeft);}
ppRoll.prototype.Marquee = function()
{
	this.MyMar=setTimeout(this.myA.objStr+".Marquee();",this.myA.speed);
	if(this.myA.IsPlay == 1)
	{
		//向上滚动
		if(this.myA.direction == "top")
		{
			if(this.$(this.myA.demo).scrollTop>=this.$(this.myA.demo2).offsetHeight)
				this.$(this.myA.demo).scrollTop-=this.$(this.myA.demo2).offsetHeight;
			else{
				this.$(this.myA.demo).scrollTop++;
			}
		}
		//向下滚动
		if(this.myA.direction == "down")
		{
			if(this.$(this.myA.demo1).offsetTop-this.$(this.myA.demo).scrollTop>=0)
				this.$(this.myA.demo).scrollTop+=this.$(this.myA.demo2).offsetHeight;
			else{
				this.$(this.myA.demo).scrollTop--;
			}
		}
		//向左滚动
		if(this.myA.direction == "left")
		{
			if(this.$(this.myA.demo2).offsetWidth-this.$(this.myA.demo).scrollLeft<=0)
				this.$(this.myA.demo).scrollLeft-=this.$(this.myA.demo1).offsetWidth;
			else{
				this.$(this.myA.demo).scrollLeft++;
			}
		}
		//向右滚动
		if(this.myA.direction == "right")
		{
			if(this.$(this.myA.demo).scrollLeft<=0)
				this.$(this.myA.demo).scrollLeft+=this.$(this.myA.demo2).offsetWidth;
			else{
				this.$(this.myA.demo).scrollLeft--;
			}
		}

	}
}
ppRoll.prototype.clearIntervalpp = function()
{
	this.myA.IsPlay = 0;
}
ppRoll.prototype.setTimeoutpp = function()
{
	this.myA.IsPlay = 1;
}




/**************
*获取上一页的来源。
*注意此方法在本地上测试是为空。
必须放到服务器上，才有用。
**************/
function check_referrer(){
   var url=document.referrer; //document.referrer是上一页的来源
   var p=url.toLowerCase().indexOf("cssrain.cn");
   if(p>=0){
   }else{
   }
}



/**************
创建用于ajax技术的 XMLHttpRequest对象。
用法：
try{
createXMLHttpRequest();   
xmlHttp_siteTotal.open("POST", url);    
xmlHttp_siteTotal.send(xml);   
}catch(e) {
}
**************/
var xmlHttp_siteTotal;
function createXMLHttpRequest() {
    if(window.XMLHttpRequest) {
            xmlHttp_siteTotal = new XMLHttpRequest();    
    }else if(window.ActiveXObject) {
            xmlHttp_siteTotal = new ActiveXObject("Microsoft.XMLHTTP");    
    }
}




//提示光标出现
function ts_onfocus(a,b){
	document.getElementById(a).className='tishi';
	document.getElementById(b).className='left2';
}

//提示光标离开
function ts_onblur(a,b){
	document.getElementById(a).className='';
	document.getElementById(b).className='left';
}

function showSysDiv() {
 var maskObj = document.createElement("div");
	maskObj.setAttribute('id','backGroundDiv');
	maskObj.style.position = "absolute";
	maskObj.style.top = "0";
	maskObj.style.left = "0";
	maskObj.style.background = "#777";
	maskObj.style.filter = "Alpha(opacity=50);";
	maskObj.style.opacity = "0.3";
	maskObj.style.width =window.screen.availWidth;
	maskObj.style.height = window.screen.availHeight;
	maskObj.style.zIndex = "3";
	document.body.appendChild(maskObj);
  document.getElementById("backGroundDiv").focus();
 var maskObjs = document.createElement("iframe");
	maskObjs.setAttribute('id','maskObjs');
	maskObjs.style.position = "absolute";
	maskObjs.style.top = "0";
	maskObjs.style.left = "0";
	maskObjs.style.background = "#777";
	maskObjs.style.filter = "Alpha(opacity=50);";
	maskObjs.style.opacity = "0.3";
	maskObjs.style.width =window.screen.availWidth;
	maskObjs.style.height = window.screen.availHeight;
	maskObjs.style.zIndex = "3";
	document.getElementById('backGroundDiv').appendChild(maskObjs);
  document.getElementById("backGroundDiv").focus();
}

function closeSysDiv() {
	var Bigdiv = document.getElementById("backGroundDiv");
	document.body.removeChild(Bigdiv);
}
function setSelectState(state) 
{ 
 var objl=document.getElementsByTagName('select'); 
 for(var i=0;i<objl.length;i++) 
 { 
 objl[i].style.display=state; 
 } 
} 

