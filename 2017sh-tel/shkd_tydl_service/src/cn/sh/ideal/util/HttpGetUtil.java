package cn.sh.ideal.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.log4j.Logger;

/********
 * 退出登录回调接口 当用户退出登录时，告诉子系统该用户退出
 * 
 * @author niewenqiang
 * @date 2017-6-1
 * **********/
public class HttpGetUtil {
	private static final String CHARSET = "UTF-8";
	private static Logger log = Logger.getLogger(HttpGetUtil.class);
	public static void get(String url) throws IOException {
		String httpurl = url;
		HttpURLConnection connection = null;
		 BufferedReader reader=null;
		try {
			URL getUrl = new URL(httpurl);
			// 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
			// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
			connection = (HttpURLConnection) getUrl.openConnection();
			// 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
			// 服务器
			connection.connect();
			// 取得输入流，并使用Reader读取
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), CHARSET));
			
		} catch (Exception e) {
			log.info("退出系统异常，url="+url);
		} finally {
			// 断开连接
			if(null!=reader){
				reader.close();
			}
			if(null !=connection){
				connection.disconnect();
			}
			
		}
	}
}
