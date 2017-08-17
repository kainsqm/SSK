package cn.sh.ideal.scheduled;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.xml.namespace.QName;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.sh.ideal.model.RecordInfo;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.service.IOperateLogService;
import cn.sh.ideal.service.IRecordInfoService;
import cn.sh.ideal.service.IUserInfoService;
import cn.sh.ideal.util.ConfigProperties;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.voicecyber.IWebSDK;
import cn.sh.ideal.voicecyber.WebSDK;
import cn.sh.ideal.voicecyber.WebSDKReturn;

/**
 * 宇高录音接口
 * **/
@Component("recordInfoTask")
public class YGRecordInfoTask {

	@Resource
	private IOperateLogService operateLogService;

	@Resource
	private IRecordInfoService recordInfoService;

	@Resource
	private IUserInfoService userInfoService;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Logger log = Logger.getLogger(YGRecordInfoTask.class);

	// 每天五分钟执行一次
	@Scheduled(cron = "0 0/5 * * * ?")
	public void recordInfoTask() {
		log.info("录音接口定时任务开始执行......");
		URL baseUrl;
		URL url;
		IWebSDK sdkClient;
		WebSDKReturn sdkReturn;

		try {
			baseUrl = WebSDK.class.getResource(".");
			url = new URL(baseUrl,
					"http://"+ConfigProperties.getProperty("recordip")+"/VCLogWebSDK/WebSDK?wsdl"); // 创建地址															// 192.168.4.78
			WebSDK webSDK = new WebSDK(url, new QName("http://tempuri.org/",
					"WebSDK"));
			sdkClient = webSDK.getBasicHttpBindingIWebSDK(); // 获取客户端
			String stopTime = sdf.format(new Date()); // 每一次同步时，以当前时间为结束时间
			Calendar calendar=Calendar.getInstance();
			calendar.add(Calendar.HOUR, -1); //减填负数		
			String startTime=sdf.format(calendar.getTime()); //每一次同步录音 当前时间前一个小时 至 当前时间
			log.info("录音开始同步" + startTime + "至" + stopTime + "期间的录音");
			sdkReturn = sdkClient.getRecordList(startTime, stopTime, "");
			if (!sdkReturn.isResult()) { // 判断接口调用 是否出现异常
				operateLogService.addLog(Constans.DATASYNC,
						Constans.DATASYNC_RECORDINFO, "录音同步接口异常，异常信息为{0}",
						new Object[] { sdkReturn.getMessage().getValue() },
						null);
				log.info("录音同步接口异常：Error\t" + sdkReturn.getIntValue() + "\t"
						+ sdkReturn.getMessage().getValue());
			} else {
				String resultValue = sdkReturn.getStringValue().getValue();// 返回的结果 
				log.info("接收到的数据：" + resultValue);
				JSONArray resultArray = JSONArray.fromObject(resultValue); // 返回的结果转JSONArray对象
				for (int i = 0; i < resultArray.size(); i++) {
					JSONObject object = resultArray.getJSONObject(i); // 依次获取JSONARRAY对象里的值
					try {
						String RecordReference=object.getString("RecordReference");
						int isExist=recordInfoService.checkExistRecordInfo(RecordReference);
						if(isExist<=0){ //判断该录音为新的录音，避免数据出现重合
							RecordInfo recordinfo = new RecordInfo();
							recordinfo.setReservedThree(RecordReference); // 录音流水号
							recordinfo.setCalledid(object.getString("CalledID")); // 被叫号码
							recordinfo.setCallerid(object.getString("CallerID")); // 主叫号码
							recordinfo.setExtension(object.getString("Extension")); // 分机号
							recordinfo.setIsTask("0"); // 是否为任务录音 0表示否
							recordinfo.setRecordLength(Integer.parseInt(object
									.getString("RecordLength"))); // 录音时长
							String StartRecordTime = object
									.getString("StartRecordTime");
							String StopRecordTime = object
									.getString("StopRecordTime");
							// 转化日期格式
							String time[] = StartRecordTime.split("/");
							if (time[1].length() < 2) {
								time[1] = "0" + time[1];
							}
							String timestop[] = StopRecordTime.split("/");
							if (timestop[1].length() < 2) {
								timestop[1] = "0" + timestop[1];
							}
							String StartRecord = time[0] + "-" + time[1] + "-"
									+ time[2]; // 录音开始时间
							String StopRecord = timestop[0] + "-" + timestop[1]
									+ "-" + timestop[2]; // 录音结束时间
							recordinfo.setStartTime(StartRecord); // 开始录音时间
							recordinfo.setStopTime(StopRecord); // 停止录音时间
							recordinfo.setSmallWorkid(object.getString("AgentID"));// 小工号
							UserInfo paraminfo = new UserInfo();
							paraminfo.setSmailWorkid(object.getString("AgentID"));
							UserInfo userinfo = userInfoService
									.getUserBySmallworkid(paraminfo);
							if (null == userinfo) {
								recordinfo.setBigWorkid("AA007"); // 大工号
								recordinfo.setAgentName("系统管理员");// 话务员姓名
								recordinfo.setGroupName("zushi"); // 班组
							} else {
								recordinfo.setBigWorkid(userinfo.getWorkId()); // 大工号
								recordinfo.setAgentName(userinfo.getUserName());// 话务员姓名
								recordinfo.setGroupName(userinfo.getGroupName()); // 班组
							}
	
							recordinfo.setDirectionFlag(object
									.getString("DirectionFlag")); // 呼入呼出 1代表呼入
							recordinfo.setAgentID(object.getString("AgentID"));// 录音接口华为工号
							recordInfoService.insert(recordinfo);
						}
					} catch (Exception e) {
						operateLogService.addLog(Constans.DATASYNC,
								Constans.DATASYNC_RECORDINFO,
								"同步录音接口入库新增异常，异常信息为{0}", new Object[] { e
										.getMessage() }, null);
						log.info("同步录音接口入库新增出现异常:" + e.getMessage() + "录音流水号为"
								+ object.getString("RecordReference"));
						continue;
					}
				}

			}

		} catch (Exception e) {
			log.info("录音接口调用连接出现异常:" + e.getMessage());
			try {
				operateLogService.addLog(Constans.DATASYNC,
						Constans.DATASYNC_RECORDINFO, "录音接口调用连接出现异常，异常信息为{0}",
						new Object[] { e.getMessage() }, null);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				log.error(e1);
			}
		}
	}

}
