//��ʾ������
function ts_onfocus(a,b,c){
	document.getElementById(a).className='tishi';
	document.getElementById(b).className='left2';
	var h = document.getElementById(c).src;
	document.getElementById(c).src=h.substring(0,h.lastIndexOf ("/")+1)+"icon_tishi.gif";
}

//��ʾ����뿪
function ts_onblur(a,b){
	document.getElementById(a).className='';
    document.getElementById(b).className='left';
}
//��ʾ�ύ���ִ���
function ts_error(a,c){  
    document.getElementById(a).className='left3';
    var h = document.getElementById(c).src; 
	document.getElementById(c).src=h.substring(0,h.lastIndexOf ("/")+1)+"icon_wrong.gif";
}
//���ڴ�С�ж�(��ǰ����)
function compareDate(DateOne)   
{
if(DateOne==""){
return true;
}
var d=new  Date();
var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ("-"));   
var OneDay   = DateOne.substring(DateOne.length,DateOne.lastIndexOf ("-")+1);   
var OneYear  = DateOne.substring(0,DateOne.indexOf ("-"));
var d1= new Date(OneYear,OneMonth,OneDay);
var d2 = new Date(d.getYear(),d.getMonth()+1,d.getDate());//��ǰ����
if(d1 >= d2){//���������С�ڵ�ǰ����
  return true;
} 
return false;
} 
function compareDate2(DateOne,DateTwo)   
{

if(DateOne=="" || DateTwo==""){  
return true;
}
var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ("-"));   
var OneDay   = DateOne.substring(DateOne.length,DateOne.lastIndexOf ("-")+1);   
var OneYear  = DateOne.substring(0,DateOne.indexOf ("-"));

var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ("-"));   
var TwoDay   = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ("-")+1);   
var TwoYear  = DateTwo.substring(0,DateTwo.indexOf ("-"));

var d1= new Date(OneYear,OneMonth,OneDay);//��Ч����
var d2 = new Date(TwoYear,TwoMonth,TwoDay);//ʧЧ����
if(d2 >= d1){//
  return true;
} 
return false;
} 



//��a_Fromsel�����һ��Ԫ�ص�a_Tosel
function doselect(a_Fromsel,a_Tosel){
	var l_item;
	var i;
	var l_Fromsel;  
	var l_Tosel;

	l_Fromsel = document.all.item(a_Fromsel);
	l_Tosel = document.all.item(a_Tosel);

	for(i=0;i<l_Fromsel.options.length;i++)
	{
 		if (l_Fromsel.options(i).selected)
		{
			if(!isChosen(l_Tosel,l_Fromsel.options(i).value)){
				l_item=document.createElement("OPTION");
				l_item.value=l_Fromsel.options(i).value;
				l_item.text=l_Fromsel.options(i).text;
				l_Tosel.add(l_item);
			}
		}
	}

	DelSelected(a_Fromsel);
}

//ɾ��a_sSelectName��ѡ�е�һ��Ԫ��
function DelSelected(a_sSelectName){
	var l_Fromsel;
	l_Fromsel = document.all.item(a_sSelectName);
	for(i=l_Fromsel.options.length-1;i>=0;i--)
	{
		if(l_Fromsel.options(i).selected)
			l_Fromsel.remove(i);
	}
}

//ɾ��a_sSelectName�е�����Ԫ��
function DelAll(a_sSelectName){
	var l_Fromsel;
	l_Fromsel = document.all.item(a_sSelectName);
	for(i=l_Fromsel.options.length-1;i>=0;i--)
	{
		l_Fromsel.remove(i);
	}
}

//��a_Fromsel������Ԫ����ӵ�a_Tosel
function SelectAll(a_Fromsel,a_Tosel){
	var l_item;
	var i;
	var l_Fromsel;
	var l_Tosel;

	l_Fromsel = document.all.item(a_Fromsel);
	l_Tosel = document.all.item(a_Tosel);

	for(i=0;i<l_Fromsel.options.length;i++)
	{
		if(!isChosen(l_Tosel,l_Fromsel.options(i).text)){
			l_item=document.createElement("OPTION");
			l_item.value=l_Fromsel.options(i).value;
			l_item.text=l_Fromsel.options(i).text;
			l_Tosel.add(l_item);
		}
	}
	DelAll(a_Fromsel);
}

//�ж�a_oSelect���Ƿ��Ѵ���a_sCode
function isChosen(a_oSelect,a_sCode)
{
	var i;
	for(i=0; i<a_oSelect.options.length; i++)
	{
		if(a_oSelect.options(i).value == a_sCode)
		{
			return(true);
		}
	}
	return(false);
}

//�ж�a_oSelect���Ƿ��Ѵ���Text a_sText
function isChosenText(a_oSelect,a_sText){
	var i;
	for(i=0; i<a_oSelect.options.length; i++)
	{
		if(a_oSelect.options(i).text == a_sText)
		{
			return(true);
		}
	}
	return(false);
}


//��a_sSelectName���һ��Ԫ��
function SetSelectOption(a_sSelectName,a_sOptionValue,a_sOptionText){
	var l_oSelect;
	var l_oItem;
	l_oSelect = document.all.item(a_sSelectName);

	if(! isChosen(l_oSelect,a_sOptionValue)){
		l_oItem=document.createElement("OPTION");
		l_oItem.value=a_sOptionValue;
		l_oItem.text=a_sOptionText;
		l_oSelect.add(l_oItem);
	}
}

//ɾ��һԪ��
function DelSelectOption(a_sValue,a_sSelectName){
	var l_oItem;
	var l_oSelect;
	var i;

	l_oSelect = document.all.item(a_sSelectName);

	for(i=0;i<l_oSelect.options.length;i++){
		if(l_oSelect.options(i).value == a_sValue){
			l_oSelect.remove(i);
		}
	}
}  

//��a_sSelectName��Ԫ������ַ����a_sIDString��
function BuildGroupStr(a_sSelectName,a_sIDString){
	var i;
	var l_str_group = document.all.item(a_sSelectName);
	var l_groupstring = document.all.item(a_sIDString);
	l_groupstring.value = "";
	for(i=0;i<l_str_group.options.length;i++){
		l_groupstring.value += l_str_group.options(i).value;
		if (i != l_str_group.options.length -1)
			l_groupstring.value += ", ";
	}
}

//��a_sSelectName��ѡ���Ԫ�����ϻ�������һλ,a_iMoveTypeΪ0�����ƣ�1���ϣ�
function MoveSelectOption(a_sSelectName,a_iMoveType){
	var i;
	var l_oSelect = document.all.item(a_sSelectName);
	var l_value;
	var l_text;

	if(a_iMoveType == 0){
		for(i=0;i<l_oSelect.options.length;i++){
			if(l_oSelect.options(i).selected && i < l_oSelect.options.length - 1){
				l_value = l_oSelect.options(i).value;
				l_text = l_oSelect.options(i).text;

				l_oSelect.options(i).value = l_oSelect.options(i + 1).value;
				l_oSelect.options(i).text = l_oSelect.options(i + 1).text;

				l_oSelect.options(i + 1).value = l_value;
				l_oSelect.options(i + 1).text = l_text;

				l_oSelect.options(i).selected = false;
				l_oSelect.options(i + 1).selected = true;

				return;
			}
		}
	}else{
		for(i=l_oSelect.options.length - 1;i>=0;i--){
			if(l_oSelect.options(i).selected && i > 0){
				l_value = l_oSelect.options(i).value;
				l_text = l_oSelect.options(i).text;

				l_oSelect.options(i).value = l_oSelect.options(i - 1).value;
				l_oSelect.options(i).text = l_oSelect.options(i - 1).text;

				l_oSelect.options(i - 1).value = l_value;
				l_oSelect.options(i - 1).text = l_text;

				l_oSelect.options(i).selected = false;
				l_oSelect.options(i - 1).selected = true;

				return;
			}
		}
	}
}

//ȡ��a_sSelectNameѡ�����value��text
function GetSelected(a_sSelectName){
	var l_Fromsel;
	var l_array = Array(2);
	l_Fromsel = document.all.item(a_sSelectName);
	for(i=l_Fromsel.options.length-1;i>=0;i--)
	{
		if(l_Fromsel.options(i).selected){
			l_array[0] = l_Fromsel.options(i).value;
			l_array[1] = l_Fromsel.options(i).text;
		}
	}
	return l_array;  
}

