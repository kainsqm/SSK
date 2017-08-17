package cn.sh.ideal.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;


public class HttpUtil {
	private static Logger log = Logger.getLogger(HttpUtil.class);
	public static String doGet(String url){
		try {
			URL getUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
			connection.connect();
	        //String NL = System.getProperty("line.separator"); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));//���ñ���,������������
	        StringBuffer sb = new StringBuffer("");  
	        String lines;
//			String content=null;
			while ((lines = reader.readLine()) != null){
					sb.append(lines);  
//					content = new String(lines.getBytes());
			}

			reader.close();
			connection.disconnect();
			return sb.toString();
		} catch (Exception e) {
			log.error("doGet获取接口数据异常"+e.getMessage(),e);
		}
		return null;
	}
	
	/**
	 * httpPost提交
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url,Map<String,String> params){
		try {
		    HttpClient httpclient = new DefaultHttpClient();  
	        HttpPost httpPost = new HttpPost(url);  
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        for(Map.Entry<String, String> entry:params.entrySet()){
	        	nvps.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));  
	       }
	        UrlEncodedFormEntity urlEncodedFormEntity=new UrlEncodedFormEntity(nvps,"utf-8");
	        httpPost.setEntity(urlEncodedFormEntity);
	        HttpResponse response = httpclient.execute(httpPost);  
	        HttpEntity entity = response.getEntity();  
	        if (entity != null) {  
	            entity = new BufferedHttpEntity(entity);  
	            InputStream in = entity.getContent();  
	            byte[] read = new byte[1024];  
	            byte[] all = new byte[0];  
	            int num;  
	            while ((num = in.read(read)) > 0) {  
	                byte[] temp = new byte[all.length + num];  
	                System.arraycopy(all, 0, temp, 0, all.length);  
	                System.arraycopy(read, 0, temp, all.length, num);  
	                all = temp;  
	            }  
	            in.close();  
	            return new String(all,"UTF-8");
	        }  
	        httpclient.getConnectionManager().shutdown();  
		} catch (Exception e) {
			log.error("doPost获取接口数据异常"+e.getMessage(),e);
		}
		return null;
	}
	
	/**
	 * JSON方式提交数据
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url,String params){
		try {
		    HttpClient httpclient = new DefaultHttpClient();  
	        HttpPost httpPost = new HttpPost(url);  
	        StringEntity stringEntity = new StringEntity(params, "UTF-8");
	        stringEntity.setContentType("application/json");
	        httpPost.setEntity(stringEntity);  
	        HttpResponse response = httpclient.execute(httpPost);  
	        HttpEntity entity = response.getEntity();  
	        if (entity != null) {  
	            entity = new BufferedHttpEntity(entity);  
	            InputStream in = entity.getContent();  
	            byte[] read = new byte[1024];  
	            byte[] all = new byte[0];  
	            int num;  
	            while ((num = in.read(read)) > 0) {  
	                byte[] temp = new byte[all.length + num];  
	                System.arraycopy(all, 0, temp, 0, all.length);  
	                System.arraycopy(read, 0, temp, all.length, num);  
	                all = temp;  
	            }  
	            in.close();  
	            return new String(all,"UTF-8");
	        }  
	        httpclient.getConnectionManager().shutdown();  
		} catch (Exception e) {		
			log.error(e);
		}
		return null;
	}
}
