/**
 *  The contents of this file are subject to the Mozilla Public
 *  License Version 1.1 (the "License"); you may not use this file
 *  except in compliance with the License. You may obtain a copy of
 *  the License at http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an "AS
 *  IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 *  implied. See the License for the specific language governing
 *  rights and limitations under the License.
 *
 *  The Original Code is pow2toolkit library.
 *
 *  The Initial Owner of the Original Code is
 *  Power Of Two S.R.L. (www.pow2.com)
 *
 *  Portions created by Power Of Two S.R.L. are
 *  Copyright (C) Power Of Two S.R.L.
 *  All Rights Reserved.
 *
 * Contributor(s):
 */

package cn.sh.ideal.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.TimeZone;
import java.util.Vector;

import org.apache.log4j.Category;


/**
 * Utility class.
 */
public class Util {

	public static final String serialcount = "1";//默认从1开始流水号	

  /** Log4j category. */
  private static Category cat = Category.getInstance(Util.class);

  /**
   * Get the last token from the input string.
   *
   * @param str
   *            the string containing the token
   * @param tokenSeparator
   *            the token separator string (i.e.: "'", ":", etc)
   * @return the last token from the input string
   */
  public static String getLastToken(String str, String tokenSeparator) {
    return str.substring(str.lastIndexOf(tokenSeparator) + 1, str.length());
  }

  /**
   * Test if the input string is null or empty (has 0 characters)
   *
   * @param s
   *            the input string to test
   * @return true if the input string is null; false otherwise
   */
  public static boolean isNull(String s) {
    return ( (s == null) || (s.length() < 1));
  }

  /**
   * Test if the input string is null or empty or if it's equal to the input
   * <code>val</code> parameter.
   *
   * @param s
   *            the input string to test
   * @param val
   *            the value string to compare to <code>s</code>
   * @return true if <code>s</code> is null, or empty, or if it's equal to
   *         the <code>val</code> string; false otherwise.
   */
  public static boolean isNull(String s, String val) {
    return (isNull(s) || (s.compareTo(val) == 0));
  }

  /**
   * Return the string representation of the input exception stack trace.
   *
   * @param t
   *            the input throwable object
   * @return the string representation of the stack trace of the input
   *         throwable object
   */
  public static String stackTrace(Throwable t) {
    StringWriter sw = new StringWriter();

    t.printStackTrace(new PrintWriter(sw));
    String s = sw.toString();

    try {
      sw.close();
    }
    catch (IOException e) {
      cat.error("::stackTrace - cannot close the StringWriter object", e);
    }

    return s;
  }

  
  /**
   * Delimit the input string with single quote characters (').
   *
   * @param v
   *            The new string value
   * @return the input string delimited with single quote characters;
   */
  public static String dbString(String v) {
    StringBuffer sb = new StringBuffer();

    return (isNull(v) ? "" : (sb.append("'").append(v).append("'")
                              .toString()));
  }

  /**
   * Dump the content of the input hash table.
   *
   * @param table
   *            table the hash table to dump
   * @param html
   *            true to set the eof as "<br>
   *            \n", false to set it as "\n"
   * @return Description of the Return Value
   */
  public static String dumpHashTable(Hashtable table, boolean html) {
    Enumeration keys = table.keys();
    Enumeration values = table.elements();
    StringBuffer sb = new StringBuffer();
    String eof = "\n";

    if (html) {
      eof = "<br>\n";

    }
    while (keys.hasMoreElements()) {
      sb.append("  key [").append(keys.nextElement().toString()).append(
          "] = [").append(values.nextElement().toString())
          .append("]").append(eof);

    }
    return sb.toString();
  }

  /**
   * Add a new parameter to the input URL string representation.
   *
   * @param URL
   *            the URL string representation
   * @param paramName
   *            the parameter name
   * @param paramValue
   *            the parameter value
   */
  public static String addURLParameter(String URL, String paramName,
                                       String paramValue) {
    String param = new StringBuffer(paramName).append("=").append(
        paramValue).toString();

    return addURLParameter(URL, param);
  }

  /**
   * Add a new parameter to the input URL string representation.
   *
   * @param URL
   *            the URL string representation
   * @param parameter
   *            the parameter string, encoded as "${paramName}=${paramValue}"
   */
  public static String addURLParameter(String URL, String parameter) {
    StringBuffer sb = new StringBuffer(URL);

    if (URL.lastIndexOf('?') == -1) {
      sb.append("?");
    }
    else {
      sb.append("&");

    }
    sb.append(parameter);
    return sb.toString();
  }

  /**
   * Remove the substring starting from the first character of the input
   * string to the input <code>until</code> string included.
   *
   * @param str
   *            the string to process
   * @param until
   *            the string to reach (it will removed, too)
   * @return the substring starting from the first characther after the input
   *         <code>until</code> string, or null if <code>until</code>
   *         token isn't found
   */
  public static String remove(String str, String until) {
    String val = null;
    int indx = str.indexOf(until);

    if (indx != -1) {
      val = (str.substring( (indx + until.length()), str.length()));

    }
    return val;
  }

  public static Long String2Long(String str) {
    Long l = new Long(0);
    try {
      l = Long.valueOf(str);
    }
    catch (NumberFormatException ex) {
    	cat.error(""+ex);
    }
    return l;
  }

  public static int String2PageId(String str) {
    int i;
    try {
      i = Integer.parseInt(str);
    }
    catch (NumberFormatException ex) {
      i = 1;
    }
    return i;
  }

  public static String NullToString(String str) {
    if (str == null) {
      str = "";
    }
    return str;
  }

  public static BigDecimal NullToBigDecimal(BigDecimal decimal) {
    if (decimal == null) {
      decimal = new BigDecimal(0);
    }
    return decimal;
  }

  public static String htmlToSql(String strHtml) {

    // String strSql = strHtml;
    StringBuffer strSql = null;

    if (strHtml != null) {
      strSql = new StringBuffer();
      for (int i = 0; i < strHtml.length(); i++) {
        String temp = strHtml.substring(i, i + 1);
        if (temp.equals("'")) {
          strSql.append("''");
        }
        else {
          strSql.append(temp);
        }
      }
      return strSql.toString();
    }

    return "";

  }

  public static String sqlToHtml(String strSql) {

    // String strSql = strHtml;
    StringBuffer strHtml = null;

    if (strSql != null) {
      strHtml = new StringBuffer();
      for (int i = 0; i < strSql.length(); i++) {
        String temp = strSql.substring(i, i + 1);

        if (temp.equals("'")) {
          strHtml.append("'");
        }

        if (temp.equals(">")) {
          strHtml.append("&gt;");
        }

        if (temp.equals("<")) {
          strHtml.append("&lt;");
        }

        if (temp.equals("&")) {
          strHtml.append("&amp;");
        }
        /*
         * if (temp.equals(" ")) { strHtml.append("&nbps;"); }
         */
        if (temp.equals("\"")) {
          strHtml.append("&quot;");
        }

        else {
          strHtml.append(temp);
        }

      }
      return strHtml.toString();
    }

    return "";

  }

  /* ���ָ����ʽ��String���͵����� */
  public static String formatDate(java.util.Date date, int type) {
    String result = null;
    String format = "yyyy-MM-dd";
    if (type == 0) {
      format = "yyyy-MM-dd";
    }

    if (type == 1) {
      format = "yyyy/MM/dd";
    }

    if (type == 2) {
      format = "yyyyMMdd";
    }
    if (type == 3) {
      format = "MM/dd/yy";
    }

    SimpleDateFormat formatter = new SimpleDateFormat(format);

    if (date != null) {
      result = formatter.format(date);
    }
    else {
      result = "";
    }

    return result;
  }

  // format double to string
  public static String formatDouble(double dblDigit) {
    String pattern = "##############.#";
    DecimalFormat format = new DecimalFormat(pattern);
    String result = format.format(dblDigit);
    return result;

  }

  /* ���ָ����ʽ��String���͵����� */
  public static java.util.Date StringToDate(String strDate) {
    java.util.Date result = null;
    String format = "yyyy-MM-dd";
    SimpleDateFormat formatter = new SimpleDateFormat(format);

    if (strDate != null && !strDate.equals("")) {
      try {
        result = formatter.parse(strDate);
      }
      catch (ParseException ex) {
        result = null;
      }
    }

    return result;
  }

  public static String filter(String str) {
    if (str == null) {
      str = "";
    }
    if (str.equals("null"))
      str = "";
    return str.trim();
  }

  public static String filterForObject(Object Obj) {
    String str = "";
    if (Obj == null) {
      str = "";
    }
    else {
      str = Obj.toString();
    }
    if (str.equals("null"))
        str = "";
    return str.trim();
  }

  public static String filterTo0(String str) {
    if (str == null) {
      str = "";
    }
    if (str.equals("null"))
      str = "";
    if (str.equals(""))
      str = "0";
    return str.trim();
  }

  public static String filterTo1(String str) {
    if (str == null) {
      str = "1";
    }
    if (str.equals("null"))
      str = "1";
    if (str.equals(""))
      str = "1";
    return str.trim();
  }

  public static int filterInteger(String str) {

    if (str == null || str.trim().equals("null") || str.trim().equals(""))
      str = "0";
    return Integer.parseInt(str.trim());
  }

  public static int intergerToInt(Integer inte) {
    int i_str = 0;
    if (inte == null) {
      i_str = 0;
    }
    else {
      i_str = inte.intValue();
    }
    return i_str;
  }

  public static String urlEncode(String strURL) {
    /*
     * try { strURL = URLEncoder.encode(strURL, "gb2312"); } catch
     * (UnsupportedEncodingException ex) { }
     */
    return strURL;

  }

  public static String urlDecode(String strURL) {
    /*
     * try { strURL = URLDecoder.decode(strURL, "gb2312"); } catch
     * (UnsupportedEncodingException ex) { }
     */
    return strURL;

  }

  public static String formatTimestamp(java.util.Date timestamp) {
    String result = null;
    String format = "yyyyMMddHHmmss";
    SimpleDateFormat formatter = new SimpleDateFormat(format);

    if (timestamp != null) {
      result = formatter.format(timestamp);
    }
    else {
      result = "";
    }

    return result;
  }

  // get all files which extension is jpg in a directory
  public static HashMap getFilesOfJPG(String filePath)  {
    HashMap hmFile = new HashMap();
    int index = 0;

    File file = new File(filePath);

    String[] files = file.list();

    if (files != null) {
      for (int i = 0; i < files.length; i++) {
        String fileName = files[i];
        index = fileName.indexOf(".");
        if (index < 0) { // if file type is directory;
          continue;
        }
        String key = fileName.substring(0, index); // get file name
        // except extension
        // name
        key = key.toLowerCase();
        String extend = fileName
            .substring(index + 1, fileName.length());
        extend = extend.toLowerCase();
        // if (extend.equals("jpg")){// if extension is jpg, put value
        // to hashmap
        hmFile.put(key, fileName);
        // }
      }
    }

    return hmFile;

  }

  // get all digit of string
  public static int getDigitCountForString(String str) {
    String temp = "";
    int count = 0;
    for (int i = 0; i < str.length(); i++) {
      temp = str.substring(i, i + 1);
      if (temp.compareTo("0") >= 0 && temp.compareTo("9") <= 0) {
        count++;
      }
    }
    return count;
  }

  public static int getTerm(java.util.Date rxDate, java.util.Date curDate) {
    int termNo = 0;
    String strRXDate = Util.formatDate(rxDate, 0);
    String strCurDate = Util.formatDate(curDate, 0);

    if (strRXDate.equals("")) {
      return termNo;
    }
    if (strCurDate.equals("")) {
      return termNo;
    }

    int rxYear = Integer.parseInt(strRXDate.substring(0, 4));
    int rxMonth = Integer.parseInt(strRXDate.substring(5, 7));

    int curYear = Integer.parseInt(strCurDate.substring(0, 4));
    int curMonth = Integer.parseInt(strCurDate.substring(5, 7));

    if (rxYear < curYear) {
      int count = 0;
      int month = curMonth - rxMonth;
      if (rxMonth > 7) { // ru xue term is autumn
        if (month < 0) { // if do not the same term
          count = 0;
        }
        else {
          count = 1;
        }
      }
      else { // ru xue term is spring
        if (month > 5) { // if do not the same term
          count = 2;
        }
        else {
          count = 1;
        }

      }

      termNo = (curYear - rxYear) * 2 + count;

    }
    else if (rxYear == curYear) { // if ru xue year is equal to current year
      int month = curMonth - rxMonth;
      if (month > 5) { // if do not the same term
        termNo = 2;
      }
      else {
        termNo = 1;
      }
    }

    return termNo;
  }

  public static String[] splitString(String str, String splitor, int count){
    String[] strResult = new String[count];
    int i = 0;
    int index = 0;
    index = str.indexOf(splitor);
    while (index >= 0) {
      String temp = str.substring(0, index);
      strResult[i] = temp;
      str = str.substring(index + 1, str.length());
      index = str.indexOf(splitor);
      i++;
    }
    strResult[i] = str;
    return strResult;
  }

  public static Vector splitString(String str, String splitor){
    Vector vResult = new Vector();
    int i = 0;
    int index = 0;
    index = str.indexOf(splitor);
    while (index >= 0) {
      String temp = str.substring(0, index);
      vResult.add(temp);
      str = str.substring(index + 1, str.length());
      index = str.indexOf(splitor);
      i++;
    }
    vResult.add(str);
    return vResult;
  }

  public static double getRate(double numerator, double denominator,
                               int decimalLength) {
    int[] length = {
        100, 1000, 10000, 100000, 1000000, 10000000};
    double rate = 0;
    rate = numerator / denominator;
    rate = rate * length[decimalLength];
    rate = Math.round(rate);
    rate = (rate * 100) / length[decimalLength];

    return rate;

  }

  /* ��-������ת����д�İ�-������ */
  public static String digitToUpper(String strDigit) {
    String result = "";
    String[] digits = {
        "��", "һ", "��", "��", "��", "��", "��", "��", "��", "��"};
    int length = strDigit.length();
    String strChar = "";
    int digit = 0;
    String max = "9";
    String min = "0";
    for (int i = 0; i < length; i++) {
      strChar = strDigit.substring(i, i + 1);
      if ( (strChar.compareTo(min) >= 0) && (strChar.compareTo(max) <= 0)) {
        digit = Integer.parseInt(strChar);
        strChar = digits[digit];
      }
      result += strChar;
    }

    return result;
  }

  /* �ַ��͵�����ת����д���ַ��͵����� */
  public static String intToUpper(String strInt) {
    String result = "";
    String[] digits = {
        "��", "һ", "��", "��", "��", "��", "��", "��", "��", "��"};
    String[] units = {
        "", "ʮ", "��", "ǧ", "��", "ʮ", "��", "ǧ", "��", "ʮ", "��", "ǧ", "��", "ʮ",
        "��", "ǧ"};

    int length = 0;
    int bit = 0;
    String strChar = "";
    String strCharUpper = "";
    int digit = 0;
    String max = "9";
    String min = "0";
    int nInt = 0;
    String flag = "0";

    try {
      nInt = Integer.parseInt(strInt);
      strInt = String.valueOf(nInt);
    }
    catch (Exception e) {
      return strInt;
    }

    length = strInt.length();
    bit = length;
    for (int i = 0; i < length; i++) {
      strChar = strInt.substring(i, i + 1);
      if ( (strChar.compareTo(min) >= 0) && (strChar.compareTo(max) <= 0)) {
        digit = Integer.parseInt(strChar);
        strCharUpper = digits[digit];
      }

      if (nInt >= 10 && nInt < 20 && i == 0) {
        flag = "1";
      }
      else {
        if (nInt > 9 && nInt < 100 && strChar.compareTo(min) == 0) {

        }
        else {
          result += strCharUpper;
        }
      }

      bit = bit - 1;
      result += units[bit];
    }

    return result;
  }

  public static String getErrorMsg(Exception e) {


    // websphere system log format:
    // ###################
    // @error: error message
    // ###################
    System.out.println("         ###################          ");
    System.out.println(" @error: " + e.toString());
    System.out.println("         ###################          ");

    String error = e.getMessage();
    if (error == null) {
      error = "";
    }
    if (error.indexOf("error.") < 0) { //���û���ҵ�
      error = "error.system";
    }

    return error;
  }

  public static java.util.Date getCurDate() {
    Calendar cal = Calendar.getInstance(TimeZone.getDefault());
    return cal.getTime();
  }

  /**
   * ɾ���ļ�
   *
   * @param picPath���ļ��ľ��·��
   */
  public static void deleteFile(String picPath) {
    File file = new File(picPath);
    if (file.exists()) {
      try {
    	  if(file.delete()){
    	  }
      }
      catch (Exception ex) {
        System.out.println("Delete file error:".concat(String
            .valueOf(String.valueOf(ex))));
      }
    }

  }

  /**
   * ��������
   *
   * @return
   */
  public static String genRandom() {
    int random = (int) (Math.random() * 1000000);
    String randomstr = String.valueOf(random);
    return randomstr;
  }

  /**
   * ��ȡ�ַ�ĳ���
   *
   * @param str:�ַ�
   * @param len:���Ƴ���
   * @param symbol:ʡ�Ա�־��
   * @return ��ȡ����ַ�
   * @author ligui
   * @createDate 2005-11-30
   * @history
   */
  public static String getLimitLengthString(String str, int len, String symbol) throws
      UnsupportedEncodingException {
    if (len == 0) { // ���Ҫ��ȡ�ĳ���Ϊ0,˵���ý�ȡ�ַ���
      return str;
    }
    int counterOfDoubleByte = 0;
    byte[] b = str.getBytes("GBK");
    if (b.length <= len) {
      return str;
    }
    for (int i = 0; i < len; i++) {
      if (b[i] < 0) {
        counterOfDoubleByte++;
      }
    }

    if (counterOfDoubleByte % 2 == 0) {
      return new String(b, 0, len, "GBK") + symbol;
    }
    else {
      return new String(b, 0, len - 1, "GBK") + symbol;
    }
  }

  /**
   * ����Ψһ�ţ������ļ���
   *
   * @return String
   * @author Ylj
   */
 /* public final static VersionFourGenerator UUID_GENERATOR = new
      VersionFourGenerator();

  public static String generateUUID() {
    System.out.println("=====get the uuid===");
    String uuid = UUID_GENERATOR.nextIdentifier().toString();
    uuid = uuid.replaceAll("-", "");
    System.out.println("=====generate uuid==:" + uuid);
    return uuid;
  }
*/
  /**
   * ��ȡ�����ԭ��
   *
   * @return String
   * @author wjk
   */
  public static String getErrorMessage(Exception e) {
    System.out.println(e);
    String error = e.getMessage();
    if (error == null)
      error = e.toString();
    if (error.trim().length() == 0) {
      error = "δ֪����";
      System.out.println(e.getStackTrace());
    }
    return error;
  }

  public static String filterForProduct_Spec_ID(String Product_Spec_ID) {
    String str = "";

    if (Product_Spec_ID == null) {
      str = "";
      return str.trim();
    }
    if (str.equals("null")) {
      str = "";
      return str.trim();
    }

    if (Product_Spec_ID.indexOf("A101") >= 0) {
//	      str += "���ȱ��� ";
      str += "�� ";
    }
    if (Product_Spec_ID.indexOf("A102") >= 0) {
//	      str += "Ʒ�Ʋ�ѯ ";
      str += "Ʒ ";
    }
    if (Product_Spec_ID.indexOf("A103") >= 0) {
//	      str += "ʵ���ѯ ";
      str += "ʵ���ѯ ";
    }
    if (Product_Spec_ID.indexOf("A104") >= 0) {
//	      str += "��ʱ���� ";
      str += "��ʱ���� ";
    }
    if (Product_Spec_ID.indexOf("A201") >= 0) {
//	      str += "��ѯת�� ";
      str += "ת ";
    }
    if (Product_Spec_ID.indexOf("A202") >= 0) {
//	      str += "���ű��� ";
      str += "���ű��� ";
    }
    if (Product_Spec_ID.indexOf("A203") >= 0) {
//	      str += "�����ת ";
      str += "�����ת ";
    }
    if (Product_Spec_ID.indexOf("A301") >= 0) {
//	      str += "��ҵ��Ƭ ";
      str += "��Ƭ ";
    }
    if (Product_Spec_ID.indexOf("A302") >= 0) {
//	      str += "��ҵ��� ";
      str += "��ҵ��� ";
    }
    if (Product_Spec_ID.indexOf("A303") >= 0) {
//	      str += "ָ·���� ";
      str += "ָ·���� ";
    }
    if (Product_Spec_ID.indexOf("A401") >= 0) {
//	      str += "����ͨѶ���� ";
      str += "����ͨѶ���� ";
    }
    if (Product_Spec_ID.indexOf("A402") >= 0) {
//	      str += "��ҵ�ܻ� ";
      str += "��ҵ�ܻ� ";
    }

    return str.trim();
  }

  public static String strReplace(String str) {
    if (str == null)
      str = "";
    str = str.replaceAll("\'", "");
    str = str.replaceAll("\\(", "");
    str = str.replaceAll("\\*", "");
    str = str.replaceAll("��", "");
    str = str.replaceAll("#", "");
    System.out.print("ddddddddd");
    return str;
  }
 
  
  //lfj 2012-11-29 add begin
  /**
	 * 返回两个时间相差几天
	 * @param sd
	 * @param ed
	 * @return
	 */
	public static int getDays(Date sd, Date ed) {
		return (int) ((ed.getTime() - sd.getTime()) / (3600 * 24 * 1000));
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			cat.error(""+e);
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
				.getTime());
		return dayBefore;
	}

	
	/**
	 * 获得指定日期(yyyy-MM-dd HH:mm:ss转 yyyy-MM-dd)
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayByString(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			cat.error(""+e);
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 0);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
				.getTime());
		return dayBefore;
	}
	
	
	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			cat.error(""+e);
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
				.format(c.getTime());
		return dayAfter;
	}
  //lfj 2012-11-29 add end
	
	
	/***
	 * 一键领取时，过滤不服规范的话务员数据
	 * @throws Exception 
	 * **/
	public static String sp_1w_onekeysqladd(String iTaskDate,int iTaskId,String iTimeGroup,String iCsrTopDCount,
			String iCsrTopwCount){
		try {
		 String vStrSql="";
		    //过滤规则一：每个CSR每天最高被质检次数
		             //查出当天最高被质检次数达到饱和状态的CSR工号，在sql中过滤掉
		     vStrSql = vStrSql +" and not exists  (select 1 from (select tr.tel_user, count(1) counts from t_qc_taskrecord tr where tr.task_id ="+
		                 iTaskId+" and tr.whichdatefortask = DATE_FORMAT('"+iTaskDate+"','%Y-%m-%d %h:%i:%s') group by tr.tel_user) T "+
		                 " where T.counts >= "+iCsrTopDCount+" and T.tel_user = tsu.USER_ID)";
		    //过滤规则二：每个CSR每周最高被质检次数
		     String vToday = iTaskDate; // 根据传入的日期 获取该日期所在周的周一和周日
			 String vweekBegin = DateUtil.dayForWeekBegin(vToday);
			 String vWeekEnd = DateUtil.dayForWeekEnd(vToday);
             String [] csrtopList=iCsrTopwCount.split(",");
             int index=Integer.parseInt(iTimeGroup)-1;
		    vStrSql= vStrSql+" and not exists (select 1 from (select rr.tel_user, count(1) counts from t_qc_taskrecord rr where rr.task_id ="+iTaskId+
		               " and rr.recordlength ="+iTimeGroup+" and rr.whichdatefortask between DATE_FORMAT('"+vweekBegin+"','%Y-%m-%d') and "+
		               " DATE_FORMAT('"+vWeekEnd+"','%Y-%m-%d') group by rr.tel_user) aa "+
		               " where aa.counts >="+csrtopList[index]+
		               " and aa.tel_user=tsu.USER_ID ) and not exists (select 1 from T_QC QC where QC.RECORD_ID = tri.RECORD_ID)";
		    return vStrSql;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
}