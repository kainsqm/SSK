package cn.sh.ideal.util;

import org.apache.log4j.Logger;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentException;

import java.util.Map;
import java.io.File;

public class CommXmlService {
	private Document doc = null;
	private static Logger log = Logger.getLogger(CommXmlService.class);
	/**
	 * 根据xml文件获得bean
	 * 
	 * @param xmlfilepath
	 *            xml文件路径
	 */
	public Map getMapByXmlFilePath(String xmlfilepath, ICollDataToMap dtm)
			throws DocumentException {
		Map map = null;
		SAXReader reader = new SAXReader();
		try {
			Element root=null;
			doc = reader.read(new File(xmlfilepath));
			if (doc != null) {
				root = doc.getRootElement();
			}
			map = dtm.collDataToMap(root);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(""+e);
		}
		return map;
	}
}
