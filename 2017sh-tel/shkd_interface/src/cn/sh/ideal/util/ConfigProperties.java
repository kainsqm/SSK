package cn.sh.ideal.util;

import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigProperties {

	private static Logger logger = Logger.getLogger(ConfigProperties.class);

	private static Properties prop;
	private static String fileName = "/conf/config.properties";
	public static String getProperty(String key) {
		if (prop == null) {
			try {
				prop = new Properties();
				prop.load(ConfigProperties.class.getResourceAsStream(fileName));
			} catch (Exception e) {
				logger.error("读取参数异常", e);
			}
			if(prop!=null){
				return prop.getProperty(key);
			}
		}
		return "";
	}
}
