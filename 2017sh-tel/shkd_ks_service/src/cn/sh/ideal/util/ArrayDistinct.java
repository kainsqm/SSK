package cn.sh.ideal.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class ArrayDistinct {
	/**
	 * 两个数组去掉重复数据,保留单独
	 * @param args1
	 * @param args2
	 * @return
	 */
	public static String repetArray(String args1[], String args2[]) {

		Set set = new HashSet<Integer>();

		for (int i = 0; i < args1.length; i++) {
			set.add(args1[i]);
		}
		for (int i = 0; i < args2.length; i++) {
			set.add(args2[i]);
		}

		// 得到的是不重复的值，Set的长度
		Iterator i = set.iterator();
		String[] arrays = new String[set.size()];
		int num = 0;
		while (i.hasNext()) {
			String a = (String) i.next();
			arrays[num] = a;
			num = num + 1;
		}
		// 对结果进行排序
		Arrays.sort(arrays);
		String question = StringUtils.join(arrays, ",");
		return question;

	}
	/**
	 * 两个数组比较，根据第二个参数删除第一个参数中存在的数据
	 * @param retain
	 * @param wipe
	 * @return
	 */
	public static String removeArray(String retain[],String wipe[]){
		List<String> first = new ArrayList(Arrays.asList(retain));
		List<String> last = new ArrayList(Arrays.asList(wipe));
		first.removeAll(last);
		//转换为数组对象
		String[] strs=first.toArray(new String[first.size()]);
		//讲数组对象转换为字符串
		String question = StringUtils.join(strs, ",");
		return question;
		
	}
	
	public static void main(String[] args) {
/*		String[] arr2 = { "1", "2", "3", "4", "5", "9", "6", "6" };
		String[] arr1 = { "3", "4", "5", "6", "7" };
		System.out.println(ArrayDistinct.repetArray(arr1, arr2));*/
/*		String[] a = { "0", "1", "2", "3", "4", "5", "6" };
		String[] b = { "1", "3", "5" };
		List<String> la = new ArrayList(Arrays.asList(a));
		List<String> lb = new ArrayList(Arrays.asList(b));
		la.removeAll(lb);
		String[] strs=la.toArray(new String[la.size()]);
		String question = StringUtils.join(strs, ",");
		System.out.println(question);*/
	}
}
