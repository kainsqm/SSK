package cn.sh.ideal.util;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Properties;

import org.aspectj.util.FileUtil;


/**
 * 读取文件配置内容
 * @author gilbert
 *
 */
public class ConfigProperties {

	private static Properties pro=new Properties();
	static{
		try {
			pro.load(new BufferedInputStream(FileUtil.class.getClassLoader().getResourceAsStream("conf/config.properties")));
		} catch (IOException e) {
		}
	}
	public static String getProperty(String key){
		try {
			if(pro.get(key)!=null){
				return (String)pro.get(key);
			}else{
				pro.load(new BufferedInputStream(FileUtil.class.getClassLoader().getResourceAsStream("conf/config.properties")));
				if(pro.get(key)!=null){
					return (String)pro.get(key);
				}else{
					return null;
				}
			}
		} catch (Exception e) {
		}
		return null;
	}

}
