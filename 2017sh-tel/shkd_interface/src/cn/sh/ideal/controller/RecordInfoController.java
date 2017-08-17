package cn.sh.ideal.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.xml.internal.ws.message.ByteArrayAttachment;

import cn.sh.ideal.model.RecordInfo;
import cn.sh.ideal.service.IOperateLogService;
import cn.sh.ideal.util.ConfigProperties;
import cn.sh.ideal.voicecyber.IWebSDK;
import cn.sh.ideal.voicecyber.WebSDK;
import cn.sh.ideal.voicecyber.WebSDKReturn;


@Controller
@RequestMapping("/controller/record")
public class RecordInfoController {
	private static Logger log = Logger.getLogger(RecordInfoController.class);
	@Resource
	private IOperateLogService operateLogService;
	
	@RequestMapping(value="/getFileHttpPath",method = RequestMethod.GET)
	public void getFileHttpPath(HttpServletRequest request,HttpServletResponse response,RecordInfo recordInfo){
		PrintWriter p = null;
		response.setContentType("text/html;charset=UTF-8");
		JSONObject object=new JSONObject();
		response.setCharacterEncoding("UTF-8");
		log.info("根据录音流水号，获取对应录音文件的URL开始执行......");
		URL baseUrl;
		URL url;
		IWebSDK sdkClient;
		WebSDKReturn sdkReturn;
		ServletOutputStream outputStream = null;
		InputStream is=null;
		try {
			//p = response.getWriter();
			baseUrl = WebSDK.class.getResource(".");
			url = new URL(baseUrl,
					"http://"+ConfigProperties.getProperty("recordip")+"/VCLogWebSDK/WebSDK?wsdl"); //创建地址
			WebSDK webSDK = new WebSDK(url, new QName("http://tempuri.org/",
					"WebSDK"));
			sdkClient = webSDK.getBasicHttpBindingIWebSDK();  //获取客户端
			try {
				 request.setCharacterEncoding("UTF-8");
				 String refID = request.getParameter("refid");//录音流水号
				 log.info("根据录音流水号，获取对应录音文件的URL,接收到的录音流水号为"+refID);
				 String resultValue="";
				 sdkReturn = sdkClient.getFileHttpPath(refID);
					if (!sdkReturn.isResult()) { //判断接口调用 是否出现异常
						log.info("根据录音流水号，获取对应录音文件的URL接口异常：Error" + sdkReturn.getIntValue()
								+ "\t" + sdkReturn.getMessage().getValue());
					}else{
						resultValue=sdkReturn.getStringValue().getValue();//返回的结果
						log.info("获取对应的URL为"+resultValue);
						outputStream = response.getOutputStream();
						try {
							HttpClient client = new DefaultHttpClient();
							HttpGet httpget = new HttpGet(resultValue);
							HttpResponse httpresponse = client.execute(httpget);
							HttpEntity entity = httpresponse.getEntity();
							is = entity.getContent();
							int cache = 10 * 1024;
							/**
							 * 根据实际运行效果 设置缓冲区大小
							 */
							byte[] buffer = new byte[cache];
							int ch = 0;
							while ((ch = is.read(buffer)) != -1) {
								outputStream.write(buffer, 0, ch);
							}
						} catch (Exception e) {
							log.error("根据HTTPURL获取文件流出现异常：" + e.getMessage());
							throw e;
						}finally{
							if(is !=null){
							is.close();
							}
							outputStream.flush();
							outputStream.close();
						}
					}		
				
				//p.print(object.toString());
			} catch (Exception e) {
				log.info("根据录音流水号，获取对应录音文件的URL接口调用异常:"+e.getMessage());
			}finally{
				//p.flush();
				//p.close();
			}

		} catch (Exception e) {
			log.info("根据录音流水号，获取对应录音文件的URL连接出现异常:"+e.getMessage());
		}
		
	}
	
	
}
