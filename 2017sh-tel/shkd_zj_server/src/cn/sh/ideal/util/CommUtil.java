/**
 * 
 */
package cn.sh.ideal.util;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @author zyz
 */
public class CommUtil {
	protected static Log log = LogFactory.getLog(CommUtil.class);

	public CommUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Object转Map
	 * 
	 * @param obj
	 * @param map
	 */
	public static void Obj2Map(Object obj, Map map) {
		if (map == null)
			map = new java.util.HashMap();
		BeanWrapper wrapper = new BeanWrapperImpl(obj);
		PropertyDescriptor descriptors[] = wrapper.getPropertyDescriptors();
		for (int i = 0; i < descriptors.length; i++) {
			String name = descriptors[i].getName();
			try {
				if (descriptors[i].getReadMethod() != null) {
					map.put(name, wrapper.getPropertyValue(name));
				}
			} catch (Exception e) {
				log.error("" + e);
			}
		}
	}

	/**
	 * Map转换ΪObject
	 * 
	 * @param map
	 * @param obj
	 */
	public static void Map2Obj(Map map, Object obj) {
		BeanWrapper wrapper = new BeanWrapperImpl(obj);
		PropertyDescriptor descriptors[] = wrapper.getPropertyDescriptors();
		for (int i = 0; i < descriptors.length; i++) {
			String name = descriptors[i].getName();
			Object v = map.get(name);
			if (v != null && descriptors[i].getWriteMethod() != null)
				wrapper.setPropertyValue(name, v);
		}
	}

	public static Map List2Map(List list, String key) {
		Map map = new HashMap();
		if (list == null)
			return null;
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			Map m = new HashMap();
			Obj2Map(obj, m);
			map.put(m.get(key), obj);
		}
		return map;
	}

	public static List descList(List l) {
		List ls = new ArrayList();
		if (l == null)
			return null;
		for (int i = l.size() - 1; i >= 0; i--) {
			ls.add(l.get(i));
		}
		return ls;
	}

	/**
	 * 根据类型转换日期
	 */
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
		if (type == 4) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		if (date != null) {
			result = formatter.format(date);
		} else {
			result = "";
		}
		return result;
	}

	public static String beforeOneDay(Date date, int type) {
		return formatDate(
				new Date(date.getYear(), date.getMonth(), date.getDate() - 1),
				type);
	}

	/**
	 * 将传入的日期向前推n天
	 * 
	 * @param date
	 *            :日期
	 * @param type
	 *            :输入格式
	 * @param n
	 *            :天数（可为负数）
	 * @author zrx
	 * @return
	 */
	public static String beforeNDay(Date date, int type, int n) {// date: type：
																	// n: 功能：
		return formatDate(
				new Date(date.getYear(), date.getMonth(), date.getDate() - n,
						date.getHours(), date.getMinutes(), date.getSeconds()),
				type);
	}

	public static String beforeOneMounth(Date date, int type) {
		return formatDate(
				new Date(date.getYear(), date.getMonth() - 1, date.getDate()),
				type);
	}

	/**
	 * 将传入的日期向前推n月
	 * 
	 * @param date
	 *            :日期
	 * @param type
	 *            :输入格式
	 * @param n
	 *            :月数（可为负数）
	 * @author zrx
	 * @return
	 */
	public static String beforeNMounth(Date date, int type, int n) {
		return formatDate(
				new Date(date.getYear(), date.getMonth() - n, date.getDate(),
						date.getHours(), date.getMinutes(), date.getSeconds()),
				type);
	}

	public static String afterOneDay(Date date, int type) {
		return formatDate(
				new Date(date.getYear(), date.getMonth(), date.getDate() + 1),
				type);
	}

	/**
	 * 将传入的日期向后延n天
	 * 
	 * @param date
	 *            :日期
	 * @param type
	 *            :输入格式
	 * @param n
	 *            :天数（可为负数）
	 * @author zrx
	 * @return
	 */
	public static String afterNDay(Date date, int type, int n) {
		return formatDate(
				new Date(date.getYear(), date.getMonth(), date.getDate() + n,
						date.getHours(), date.getMinutes(), date.getSeconds()),
				type);
	}

	/**
	 * 将传入的日期向后延n天
	 * 
	 * @param date
	 *            :日期
	 * @param type
	 *            :输出格式
	 * @param n
	 *            :天数（可为负数）
	 * @param hmsType
	 *            :[0]输出时间的时分秒为00：00：00, [1]输出时间的时分秒为23：59：59,
	 *            [其他]输出时间的时分秒为输入时间的时分秒
	 * @author zrx
	 * @return
	 */
	public static String afterNDay(Date date, int type, int n, String hmsType) {
		if ("0".equals(hmsType)) {
			return formatDate(
					new Date(date.getYear(), date.getMonth(), date.getDate()
							+ n, 00, 00, 00), type);
		} else if ("1".equals(hmsType)) {
			return formatDate(
					new Date(date.getYear(), date.getMonth(), date.getDate()
							+ n, 23, 59, 59), type);
		}
		return formatDate(
				new Date(date.getYear(), date.getMonth(), date.getDate() + n,
						date.getHours(), date.getMinutes(), date.getSeconds()),
				type);
	}

	public static String afterNDayNWQ(Date date, int type, int n, String hmsType) {
		if ("0".equals(hmsType)) {
			return formatDate(
					new Date(date.getYear(), date.getMonth(), date.getDate()
							+ n), type);
		} else if ("1".equals(hmsType)) {
			return formatDate(
					new Date(date.getYear(), date.getMonth(), date.getDate()
							+ n), type);
		}
		return formatDate(
				new Date(date.getYear(), date.getMonth(), date.getDate() + n),
				type);
	}

	public static String afterOneMounth(Date date, int type) {
		return formatDate(
				new Date(date.getYear(), date.getMonth() + 1, date.getDate()),
				type);
	}

	/**
	 * 将传入的日期向后延n月
	 * 
	 * @param date
	 *            :日期
	 * @param type
	 *            :输入格式
	 * @param n
	 *            :月数（可为负数）
	 * @author zrx
	 * @return
	 */
	public static String afterNMounth(Date date, int type, int n) {
		return formatDate(
				new Date(date.getYear(), date.getMonth() + n, date.getDate(),
						date.getHours(), date.getMinutes(), date.getSeconds()),
				type);
	}

	public static String beforeOneMounth(String date) {
		Date rdate = null;
		String format = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		if (date != null && !date.equals("")) {
			try {
				rdate = formatter.parse(date);
				rdate.setMonth(rdate.getMonth() - 1);
			} catch (ParseException ex) {
				rdate = null;
			}
		}
		return formatDate(rdate, 0);
	}

	public static String formatRate(String rate) throws ClassCastException {
		DecimalFormat format = new DecimalFormat("#.##");
		try {
			return format.format(new Double(Util.filterTo1(rate)));
		} catch (Exception e) {
			throw new ClassCastException(rate + "  类型转换异常0.00");
		}
	}

	public static String formatDouble(String value) throws ClassCastException {
		DecimalFormat format = new DecimalFormat("####################.##");
		try {
			return format.format(new Double(Util.filterTo1(value)));

		} catch (Exception e) {
			// TODO: handle exception
			throw new ClassCastException(value + "  类型转换异常");
		}
	}

	public static Date createDate(String year, String month, String day,
			String hours, String minutes, String seconds)
			throws ClassCastException, ParseException {

		DateFormat dateFormat;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		Date date = null;
		try {
			date = dateFormat.parse(year + "-" + month + "-" + day + " "
					+ hours + ":" + minutes + ":" + seconds);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error("" + e);
		}
		return date;
	}

	public static Date createDate(String ymdhms) throws ParseException {
		String year = ymdhms.substring(0, 4), month = ymdhms.substring(5, 7), day = ymdhms
				.substring(8, 10), hours = ymdhms.substring(11, 13), minutes = ymdhms
				.substring(14, 16), seconds = ymdhms.substring(17, 19);
		DateFormat dateFormat;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		Date date = dateFormat.parse(year + "-" + month + "-" + day + " "
				+ hours + ":" + minutes + ":" + seconds);
		return date;
	}

	public static Date createDateNWQ(String ymdhms) throws ParseException {
		String year = ymdhms.substring(0, 4), month = ymdhms.substring(5, 7), day = ymdhms
				.substring(8, 10);
		DateFormat dateFormat;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		Date date = dateFormat.parse(year + "-" + month + "-" + day);
		return date;
	}

	/**
	 * 
	 * @param filePath
	 *            拷贝导
	 * @param pathfilename
	 *            获取需要复制的路径
	 * @param uuid
	 * @return
	 * @throws IOException
	 */
	/*public static String copy(String filePath, String pathfilename)
			throws IOException {
		InputStream inStream = null;
		FileOutputStream fos = null;
		try {
			// logger.info("--> copy file");
			int bytesum = 0;
			int byteread = 0;
			inStream = new FileInputStream(pathfilename);

			File f = new File(pathfilename);
			if (f.length() <= 0)
				return "";
			String filename = f.getName();
			String newFile = filePath + "/" + f.getName();
			// System.out.println(newFile);
			File newF = new File(newFile);

			if (f.getAbsoluteFile().equals(newF.getAbsoluteFile()))
				return pathfilename;

			File Df = new File(filePath);

			if (!Df.exists()) {
				if (!Df.mkdirs()) {
					return "";
				}
			}

			fos = new FileOutputStream(newFile);
			byte[] buffer = new byte[Integer
					.parseInt(String.valueOf(f.length()))];
			int length = Integer.parseInt(String.valueOf(f.length()));
			while ((byteread = inStream.read(buffer)) != -1) {

				bytesum += byteread;
				fos.write(buffer, 0, byteread);
			}
			// f.delete();
			return newFile;
		} catch (Exception e) {
			log.error("" + e);
			return "";
		} finally {
			if(fos!=null){
				fos.close();
			}
			if(inStream!=null){
				
				inStream.close();
			}
		}
	}*/

	public static double getSubtractionDay(Date date1, Date date2) {
		double lo = new Long(date1.getTime() - date2.getTime());
		double day = lo / 1000 / 60 / 60 / 24;
		return day;
	}

	public static double getSubtractionHour(Date date1, Date date2) {
		double lo = new Long(date1.getTime() - date2.getTime());
		double hour = lo / 1000 / 60 / 60;
		return hour;
	}

	public static double getSubtractionMin(Date date1, Date date2) {
		double lo = new Long(date1.getTime() - date2.getTime());
		double min = lo / 1000 / 60;
		return min;
	}

	public static double getSubtractionSec(Date date1, Date date2) {
		double lo = new Long(date1.getTime() - date2.getTime());
		double sec = lo / 1000;
		return sec;
	}

	public static Object getPropertyValue(Object bean, String ss) {
		// update by lfj 2012-09-13 增加对ss的非空验证
		String methodName = "";
		// String methodName = "get" + ss.substring(0, 1).toUpperCase()
		// + ss.substring(1);
		if (ss == null || ss.equals("")) {
			methodName = "get";
		} else {
			methodName = "get" + ss.substring(0, 1).toUpperCase()
					+ ss.substring(1);
		}
		Method[] methods = bean.getClass().getDeclaredMethods();
		Object obj = "";
		for (int i = 0; i < methods.length; i++) {
			if (methodName.endsWith(methods[i].getName())) {
				try {
					obj = methods[i].invoke(bean);
				} catch (IllegalArgumentException e) {
					log.error("" + e);
				} catch (IllegalAccessException e) {
					log.error("" + e);
				} catch (InvocationTargetException e) {
					log.error("" + e);
				}
				break;
			}
		}
		return obj;
	}

	public static boolean isNull(String s) {
		return ((s == null) || (s.length() < 1));
	}

	public static String getWorkId(String userId) {
		String workId = userId;
		if (userId != null && userId.length() >= 4) {
			userId = userId.substring(0, 4);
			workId = userId;
			if ("2".equals(userId.substring(0, 1))) {
				workId = "AKB" + workId.substring(1, 4);
			} else if ("3".equals(userId.substring(0, 1))) {
				workId = "AKZ" + workId.substring(1, 4);
			} else if ("5".equals(userId.substring(0, 1))) {
				workId = "AKA" + workId.substring(1, 4);
			} else if ("7".equals(userId.substring(0, 1))) {
				workId = "AK" + workId.substring(1, 4);
			} else if ("8".equals(userId.substring(0, 1))) {
				workId = "AA" + workId.substring(1, 4);
			} else if ("9".equals(userId.substring(0, 1))) {
				workId = "AKC" + workId.substring(1, 4);
			}
		}
		return workId;
	}

	public static void main(String[] arg) {
		System.out.println(getWorkId("3212"));
		// new CommUtil().copy("c:/1/2",
		// "\\\\10.6.239.177\\新建文件夹\\GslDAO.class");
	}
}