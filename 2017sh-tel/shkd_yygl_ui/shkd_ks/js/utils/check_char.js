//�ж�Form�����ж����ֵ���Ƿ��������ַ�
 function CheckSpecChar(FormName){
	//�������Ϊform�����
	bString = "'<>\""; //�Ƿ��ַ�
	for (var ObjID=0; ObjID < FormName.elements.length; ObjID++) {
		for(i = 0; i < FormName.elements[ObjID].value.length; i ++){
			if (bString.indexOf(FormName.elements[ObjID].value.substring(i,i+1)) >= 0){
				alert('��������ݲ��ܰ�Ƿ��ַ�:' + bString);
				FormName.elements[ObjID].focus();
				return false;
                        }
		}
	}
	return true;
}

//У�鲻�ϸ�󣬹���Զ���λ������ϸ�ʽҪ��Ŀؼ���
function CheckInputValue(input_desc,//��������ݵ��������
                         ObjName ,//�����������
                          is_empty,//false-������Ϊ��;true-����Ϊ��
                          input_type,//�������������
                          input_minlength,//��������ݵ���С����
                          input_maxlength//��������ݵ������
                          ){
	//����Ϊ��
	if(!is_empty && CheckIsNull(ObjName.value)){
		//alert(input_desc + "����Ϊ��!");
    		//ObjName.focus();
    		return false;
	}
 	if(is_empty && CheckIsNull(ObjName.value)){
    		return true;
	}
 	//��������ݵ���С�������ƣ����������Ϊ�գ����޳������Ϊ�յ����
  	if(input_minlength != 0 && ObjName.value.length < input_minlength && ObjName.value.length > 0){
    		//alert(input_desc + "���Ȳ��ܵ���" + input_minlength + "���ַ�!");
		//ObjName.focus();
    		return false;
	}

  	if(input_maxlength != 0 && ObjName.value.length > input_maxlength ){
    		//alert(input_desc + "���Ȳ��ܳ���" + input_maxlength + "���ַ�!");
		//ObjName.focus();
    		return false;
        }

	/***************
	* ͳһ���ú���CheckSpecChar(FormName)�����formһ�μ��
        * ***************/
	/*
  	 if(input_type == "String"){
    		if(!CheckString(ObjName.value)){
      			alert(input_desc + "���ܰ�<��>��'��\"��/��\\");
                        ObjName.focus();
      			return false;
    		}
  	}*/
	 if(input_type == "StringNoError"){
    		if(!CheckStringNoError(ObjName.value)){
      			//alert(input_desc + "���ܰ�<��>��'��\"��/��\\");
                       // ObjName.focus();
      			return false;
    		}
  	}

	if(input_type == "StringNoSpace"){
    		if(!CheckStringNoSpace(ObjName.value)){
      			//alert(input_desc + "���ܰ�ո�!");
			//ObjName.focus();
      			return false;
    		}
  	}

	if(input_type == "StringNo6up"){
    		if(!CheckStringNo6up(ObjName.value)){
      			//alert(input_desc + "���ܰ�\^!");
			//ObjName.focus();
      			return false;
    		}
  	}

	if(input_type == "StringNofg"){
    		if(!CheckStringNofg(ObjName.value)){
      			//alert(input_desc + "���ܰ��!");
			//ObjName.focus();
      			return false;
    		}
  	}

	if(input_type == "StringNoPercent"){
    		if(!CheckStringNoPercent(ObjName.value)){
      			//alert(input_desc + "���ܰ�%!");
			//ObjName.focus();
      			return false;
		}
	}

	if(input_type == "StringNoSpacePercent"){
		if(!CheckStringNoSpacePercent(ObjName.value)){
			//alert(input_desc + "���ܰ�ո��%!");
			//ObjName.focus();
      			return false;
		}
	}

	if(input_type == "Number"){
		if(!CheckNumber(ObjName.value)){
			//alert(input_desc + "�����֣�ֻ��ȫ�����������!");
			//ObjName.focus();
			return false;
		}
	}

	if(input_type == "Telephone"){
		if(!CheckTelephone(ObjName.value)){
			//alert(input_desc + "�ǵ绰�������֡�-���!");
			//ObjName.focus();
      			return false;
		}
	}

	if(input_type == "Money"){
		if(!CheckMoney(ObjName.value)){
			//alert(input_desc + "�ǽ������֡�.���!");
			//ObjName.focus();
			return false;
		}
	}
	if(input_type == "Rate"){
		if(!CheckRate(ObjName.value)){
			//alert(input_desc + "��ϵ���ܴ���1!");
			//ObjName.focus();
			return false;
		}
	}
	if(input_type == "Time"){
		if(!CheckTime(ObjName.value)){
			//alert(input_desc + "��ʱ�䣬��ʽ����24h:mi!");
			//ObjName.focus();
			return false;
		}
	}
	if(input_type == "Time2"){
		if(!CheckTime2(ObjName.value)){
			//alert(input_desc + "��ʱ�䣬��ʽ����24h:mi:ss!");
			//ObjName.focus();
			return false;
		}
	}
	if(input_type == "Date"){
		if(!CheckDate(ObjName,input_desc)){
			return false;
		}
	}
	if(input_type == "Month"){
		if(!CheckMonth(ObjName.value)){
			//alert(input_desc + "���·ݣ���ʽ����yyyy-MM!");
			//ObjName.focus();
			return false;
		}
	}
	if(input_type == "Email"){
		if(!CheckEmail(ObjName.value)){
			//alert(input_desc + "��Email�������ϸ�ʽҪ��!");
			//ObjName.focus();
			return false;
		}
	}

	if(input_type == "Password"){
		if(!CheckPassword(ObjName.value)){
			//alert(input_desc + "ֻ�ܰ����ֺ���ĸ!");
			//ObjName.focus();
			return false;
		}
	}

	if(input_type == "Letter"){
      		if(!CheckLetter(ObjName.value)){
        	//	alert(input_desc + "��������ĸ!");
			//ObjName.focus();
        		return false;
      		}
	}

	if(input_type == "UpLetter"){
        	if(!CheckUpLetter(ObjName.value)){
          		//alert(input_desc + "�����Ǵ�д��ĸ!");
			//ObjName.focus();
          		return false;
        	}
  	}

  	if(input_type == "LowLetter"){
          	if(!CheckLowLetter(ObjName.value)){
            		//alert(input_desc + "������Сд��ĸ!");
			//ObjName.focus();
            		return false;
          	}
  	}

	if(input_type == "Chinese"){
      		if(!CheckChinese(ObjName.value)){
        		//alert(input_desc + "������ȫ��Ϊ����!");
			//ObjName.focus();
        		return false;
      		}
	}
	if(input_type == "NoChinese"){
      		if(!CheckNoChinese(ObjName.value)){
        		//alert(input_desc + "���ܰ�����!");
			//ObjName.focus();
        		return false;
      		}
	}
  return true;
}

//�жϿؼ����ַ��Ƿ�Ϊ��
function CheckIsNull(str) {
    str = cTrim(str,0);
	if (str.length == 0) {
     		return true;  //��ֵ
	}
	return false; //��Ϊ��
}
//�ж��Ƿ�û�а�ո�
function CheckStringNoSpace(str){
	var i;
  	for (i = 0; i < str.length; i++){
    		if (str.charAt(i) == " "){
      			return false;
    		}
  	}
  	return true;
}
//�ж��Ƿ�û�а�"/"��"\"
function CheckStringNoError(str){
	//�������Ϊform�����
	bString = "'<>\"\\/"; //�Ƿ��ַ�

        for(i = 0; i < str.length; i ++){
                if (bString.indexOf(str.substring(i,i+1)) >= 0){
                        return false;
                }
        }
	return true;
}
//�ж��Ƿ�û�а�%
function CheckStringNoPercent(str){
	var i;
	for (i = 0; i < str.length; i++){
		if (str.charAt(i) == "%"){
			return false;
		}
	}
	return true;
}

//�ж��Ƿ�û�а�\^
function CheckStringNo6up(str){
	var i;
	for (i = 0; i < str.length; i++){
		if (str.charAt(i) == "\^"){
			return false;
		}
	}
	return true;
}

//�ж��Ƿ�û�а�\*
function CheckStringNofg(str){
	var i;
	for (i = 0; i < str.length; i++){
		if (str.charAt(i) == "��"){
			return false;
		}
	}
	return true;
}

//�ж��Ƿ�û�пո��%
function CheckStringNoSpacePercent(str){
	var i;
	for (i = 0; i < str.length; i++){
    		if (str.charAt(i) == " " || str.charAt(i) == "%"){
      			return false;
		}
	}
	return true;
}
//�����Ƿ�Ϊ���֣����ͣ�
function CheckNumber(str){
	var i;
	//alert(parseInt(str));
	for (i = 0; i < str.length; i++){
		if (str.charAt(i) < "0" || str.charAt(i) > "9"){
			return false;
		}
	}
	return true;
}

//�ж��Ƿ��ϵ绰���룬Telephone,�����ֺͣ����
function CheckTelephone(str){
	var i
	for (i = 0; i < str.length; i++){
		if ((str.charAt(i) < "0" || str.charAt(i) > "9") && str.charAt(i) != "-"){
			return false;
		}
	}
	return true;
}
//�����Ƿ�Ϊ�����㣩
function CheckMoney(str){
	var i;
	for (i = 0; i < str.length; i++){
		if ((str.charAt(i) < "0" || str.charAt(i) > "9") && str.charAt(i) != "."){
			return false;
		}
	}
	return true;
}
//�����Ƿ�Ϊϵ��С�ڵ���1
function CheckRate(str){
	var i;
	for (i = 0; i < str.length; i++){
		if ((str.charAt(i) < "0" || str.charAt(i) > "9") && str.charAt(i) != "."){
			return false;
		}
	}
	if (parseFloat(str) >1 ){
                return false;
        }
	return true;
}

//�����Ƿ�Ϊʱ�䣬��ʽ24h:mi
function CheckTime(str){
	var i;
	for (i = 0; i < str.length; i++){
		if ((str.charAt(i) < "0" || str.charAt(i) > "9") && str.charAt(i) != ":"){
			return false;
		}
	}
	if (str.length != 5)
		return false;
	if (str.substring(2,3) != ":" ) {
		return false;
	}
	ls_hour = str.substring(0,2);
	ls_min = str.substring(3,5);

	if (parseFloat(ls_hour) > 23  ){
		return false;
	}
	if (parseFloat(ls_min) > 59){
		return false;
	}
	return true;
}
//�����Ƿ�Ϊʱ�䣬��ʽ24h:mi:ss
function CheckTime2(str){
	var i;
	for (i = 0; i < str.length; i++){
		if ((str.charAt(i) < "0" || str.charAt(i) > "9") && str.charAt(i) != ":"){
			return false;
		}
	}
	if (str.length != 8)
		return false;
	if (str.substring(2,3) != ":" ) {
		return false;
	}
	if (str.substring(5,6) != ":" ) {
		return false;
	}
	ls_hour = str.substring(0,2);
	ls_min = str.substring(3,5);
	ls_ss = str.substring(6,8);

	if (parseFloat(ls_hour) > 23  ){
		return false;
	}
	if (parseFloat(ls_min) > 59){
		return false;
	}
	if (parseFloat(ls_ss) > 59){
		return false;
	}
	return true;
}
//�����Ƿ�Ϊ�·ݣ���ʽyyyy-MM
function CheckMonth(str){
	var i;
	for (i = 0; i < str.length; i++){
		if ((str.charAt(i) < "0" || str.charAt(i) > "9") && str.charAt(i) != "-"){
			return false;
		}
	}
	if (str.length != 7)
		return false;
	if (str.substring(4,5) != "-" ) {
		return false;
	}
	ls_year = str.substring(0,4);
	ls_month = str.substring(5,7);

	if (parseFloat(ls_year) < 1900  ){
		return false;
	}
	if (parseFloat(ls_month) > 12){
		return false;
	}
	return true;
}

//�жϿؼ��������Ƿ�Ϊyyyy-mm-dd��ʽ�����ڣ�
function CheckDate(ObjName,ObjDesc) {

	var ln =  ObjName.value.length;
	//��ֵ�����ϸ�ʽҪ��
	if (ln == 0 ) {
		return true;
	}
	if  (ln != 10) {
		//alert( ObjDesc + "ֻ�������������ڸ�ʽyyyy-mm-dd��");
		//ObjName.focus();
		return false;
	}
	bString = "0123456789-";
	for(i = 0; i < ObjName.value.length; i ++){
		if (bString.indexOf(ObjName.value.substring(i,i+1)) == -1) {
			//alert(ObjDesc + "���ڸ�ʽֻ���������ֺ�-!");
			//ObjName.focus();
     			return false;
                }
	}

	var ls_year, ls_date, ls_month;

	if (ObjName.value.substring(4,5) != "-" || ObjName.value.substring(7,8) != "-")	{
		//alert(ObjDesc + "ֻ�������������ڸ�ʽyyyy-mm-dd��");
		//ObjName.focus();
		return false;
	}
	ls_year = ObjName.value.substring(0,4);
	ls_month = ObjName.value.substring(5,7);
	ls_date = ObjName.value.substring(8,10);

	if (parseFloat(ls_year) > 2100 || parseFloat(ls_year) < 1900){
		//alert( ObjDesc + "��ݲ��ԣ�");
		//ObjName.focus();
		return false;
	}
	if (parseFloat(ls_month) > 12 || parseFloat(ls_year) < 1){
		//alert(ObjDesc + "�·ݲ��ԣ�");
		return false;
	}
	if (parseFloat(ls_date) > 31 || parseFloat(ls_date) < 1){
		//alert(ObjDesc + "���ڲ��ԣ�");
		//ObjName.focus();
		return false;
	}
	return true;
}
//�ж��Ƿ���Password��ʽ����ĸ���������
function CheckPassword(str){
	var i
  	for (i = 0; i < str.length; i++){
    		if ((str.charAt(i) < "0" || str.charAt(i) > "9") && (str.charAt(i) < "A" || str.charAt(i) > "Z") && (str.charAt(i) < "a" || str.charAt(i) > "z")){
      			return false;
    		}
  	}
  	return true;
}
//�ж��Ƿ�����ĸ
function CheckLetter(str){
	var i
  	for (i = 0; i < str.length; i++){
    		if ( (str.charAt(i) < "A" || str.charAt(i) > "Z") && (str.charAt(i) < "a" || str.charAt(i) > "z")){
      			return false;
    		}
  	}
  	return true;
}
//�ж��Ƿ��Ǵ�д��ĸ
function CheckUpLetter(str){
	var i
  	for (i = 0; i < str.length; i++){
    		if ( (str.charAt(i) < "A" || str.charAt(i) > "Z") ){
      			return false;
    		}
  	}
  	return true;
}
//�ж��Ƿ���Сд��ĸ
function CheckLowLetter(str){
	var i
  	for (i = 0; i < str.length; i++){
    		if (  (str.charAt(i) < "a" || str.charAt(i) > "z")){
      			return false;
    		}
  	}
  	return true;
}

//�ж��Ƿ�Ϊȫ������,��Ȼ���������Ե�ϵͳ�У���������ġ�����Ҳ���ܣ�
function CheckChinese(str){
	var i ;
	for (var i=0;i<str.length;i++){
    		if (parseInt(str.charCodeAt(i)) <= 256){
        		return false;
    		}
  	}
  	return true;
}
//�ж��Ƿ�û������
function CheckNoChinese(str){
	var i ;
	for (var i=0;i<str.length;i++){
    		if (parseInt(str.charCodeAt(i)) > 256){
        		return false;
    		}
  	}
  	return true;
}

//�ж��Ƿ���Email�ĸ�ʽ����@��. , @��.֮ǰ��
function CheckEmail(str){
	var index_i;
	var index_j;
  	if(str.length > 0){
		index_i = str.lastIndexOf("@");

    		if(( index_i < 1) ){
			return false;
    		}
		index_j = str.indexOf(".", index_i);
		if(( index_j < 1) ){
			return false;
    		}

  	}
  	return true;
}

//ȥ�ո���
String.prototype.Trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

String.prototype.LTrim = function() {
	return this.replace(/(^\s*)/g, "");
}

String.prototype.RTrim = function() {
	return this.replace(/(\s*$)/g, "");
}

//****************************************************************
// Description: str Ϊ�����ַ�
// iTypeΪ���ͣ��ֱ�Ϊ0 - ȥ��ǰ��ո�; 1 - ȥǰ���ո�; 2 - ȥβ���ո�
//****************************************************************
function cTrim(str,iType){
	var sTmpStr = ' ';
	var i = -1;

	if(iType == 0 || iType == 1){
		while(sTmpStr == ' '){
			++i;
			sTmpStr = str.substr(i,1);
		}
		str = str.substring(i);
	}

	if(iType == 0 || iType == 2){
		sTmpStr = ' ';
		i = str.length;
		while(sTmpStr == ' '){
			--i;
			sTmpStr = str.substr(i,1);
		}
		str = str.substring(0,i+1);
	}
	return str;
}

function isblank(s)
{
  var j=0;
  for(var i = 0;i < s.length;i++) {
    var c = s.charAt(i);
    if ((c != '') && (c != '\n') && (c != '\t') && (c != ' ')) return false;
    if (c == ' ')
    {
      j++;
    }
  }
  if (j == s.length)    //ȫ���ǿո�
  {
   return true;
  }
  else
  {
    return false;
  }
}

function isvalid(s)
{
  var j=0;
  for(var i = 0;i < s.length;i++) {
    var c = s.charAt(i);
    if ((c == '<') || (c == '>') || (c == '\\') || (c == '/') || (c == '+') || (c == '\'')) return false;
  }
  return true;
}

function isNum(s)
{
  for(var i = 0;i < s.length;i++) {
    var v = parseFloat(s.charAt(i));
    if (isNaN(v)) return false;
  }
  return true;
}


