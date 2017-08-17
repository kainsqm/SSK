package cn.sh.ideal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {

	static String yearMonth="yyyy-MM-dd";
	static String yearMonthSeconds="yyyy-MM-dd HH:mm:ss";
	static public String getDateStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(yearMonth);
		return format.format(date);
	}

	static public String getDateMonth(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		return format.format(date);
	}

	static public String getYear(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		return format.format(date);
	}

	static public String getMonth(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("MM");
		return format.format(date);
	}

	static public String getNextMonth(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("MM");
		Integer month = Integer.parseInt(format.format(date)) + 1;
		String nextMonth;
		if (month < 10) {
			nextMonth = "0" + month;
		} else {
			nextMonth = month.toString();
		}
		return nextMonth;
	}

	static public String getDateStrC(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		return format.format(date);
	}

	static public String getDateStrCompact(Date date) {
		if (date == null){
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String str = format.format(date);
		return str;
	}

	/**
	 * getDateStr get a string with format YYYY-MM-DD HH:mm:ss from a Date
	 * object
	 * 
	 * @param date
	 *            date
	 * @return String
	 */
	static public String getDateTimeStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(yearMonthSeconds);
		return format.format(date);
	}

	static public String getDateTimeStrC(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		return format.format(date);
	}

	public static String getCurDateStr(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date());
	}

	/**
	 * Parses text in 'YYYY-MM-DD' format to produce a date.
	 * 
	 * @param s
	 *            the text
	 * @return Date
	 * @throws ParseException
	 */
	static public Date parseDate(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(yearMonth);
		return format.parse(s);
	}

	static public Date parseDateC(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		return format.parse(s);
	}

	/**
	 * Parses text in 'YYYY-MM-DD' format to produce a date.
	 * 
	 * @param s
	 *            the text
	 * @return Date
	 * @throws ParseException
	 */
	static public Date parseDateTime(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(yearMonthSeconds);
		return format.parse(s);
	}

	static public Date parseDateTimeC(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		return format.parse(s);
	}

	/**
	 * Parses text in 'HH:mm:ss' format to produce a time.
	 * 
	 * @param s
	 *            the text
	 * @return Date
	 * @throws ParseException
	 */
	static public Date parseTime(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		return format.parse(s);
	}

	static public Date parseTimeC(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("HH时mm分ss秒");
		return format.parse(s);
	}

	static public int yearOfDate(Date s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(yearMonth);
		String d = format.format(s);
		return Integer.parseInt(d.substring(0, 4));
	}

	static public int monthOfDate(Date s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(yearMonth);
		String d = format.format(s);
		return Integer.parseInt(d.substring(5, 7));
	}

	static public int dayOfDate(Date s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(yearMonth);
		String d = format.format(s);
		return Integer.parseInt(d.substring(8, 10));
	}


	

	/**
	 * 返回两个时间相差几天
	 * 
	 * @param sd
	 * @param ed
	 * @return
	 */
	public static int getDays(Date sd, Date ed) {
		return (int) ((ed.getTime() - sd.getTime()) / (3600 * 24 * 1000));
	}

	/**
	 * 根据传入的参数  获取该参数所在星期一的日期
	 * 
	 * @param pTime
	 *            修要判断的时间
	 * @return dayForWeek 判断结果
	 * @throws ParseException 
	 * @Exception 发生异常
	 */
	public static String dayForWeekBegin(String pTime) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat(yearMonth); //设置时间格式  
        Calendar cal = Calendar.getInstance();  
		cal.setTime(sdf.parse(pTime));
		 
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        if(1 == dayWeek) {  
          cal.add(Calendar.DAY_OF_MONTH, -1);  
        }  
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值   
        String imptimeBegin = sdf.format(cal.getTime());  
         return imptimeBegin;
	}

	/**
	 * 根据传入的参数  获取该参数所在星期天的日期
	 * 
	 * @param pTime
	 *            修要判断的时间
	 * @return dayForWeek 判断结果
	 * @throws ParseException 
	 * @Exception 发生异常
	 */
	public static String dayForWeekEnd(String pTime) throws ParseException   {
		SimpleDateFormat sdf=new SimpleDateFormat(yearMonth); //设置时间格式  
        Calendar cal = Calendar.getInstance();  
		cal.setTime(sdf.parse(pTime));
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        if(1 == dayWeek) {  
          cal.add(Calendar.DAY_OF_MONTH, -1);  
        }  
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值   
        cal.add(Calendar.DATE, 6);  
        String imptimeEnd = sdf.format(cal.getTime());  
         return imptimeEnd;
	}
	/*
	 * 取日期的前后*分钟
	 */
	public static  String getinfoDate(String date,int m) throws ParseException {	
		String Date=date;
		SimpleDateFormat sdf=new SimpleDateFormat(yearMonthSeconds);
		Date da;
		Date dates;
		Long min;
		String time;
		da = sdf.parse(Date);
		if(m>0){
		min= da.getTime()-(-m*60*1000);
		}else{
		min= da.getTime()+(m*60*1000);
		}
		dates=new Date(min);
		time=sdf.format(dates);
		 
		return time;
	} 
	
	
}
