package cn.sh.ideal.util;
import java.util.Map;
import org.dom4j.Element;

/**
 * 匿名类集合数据转换成Map
 * @author taoyang
 * @date 2010-08-26
 */
public interface ICollDataToMap {
	public Map collDataToMap(Element root);
}
