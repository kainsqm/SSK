package cn.sh.ideal.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.IWorkOrder112Dao;
import cn.sh.ideal.model.RecordInfo;
import cn.sh.ideal.model.WorkOrder112;
import cn.sh.ideal.model.WorkTelSum112;
import cn.sh.ideal.scheduled.YGRecordInfoTask;
import cn.sh.ideal.service.IWorkOrder112Service;
import cn.sh.ideal.util.CommXmlService;
import cn.sh.ideal.util.ICollDataToMap;

@Service("workOrder112Service")
public class WorkOrder112ServiceImpl implements IWorkOrder112Service {
	private static Logger log = Logger.getLogger(WorkOrder112ServiceImpl.class);
	@Resource
	private IWorkOrder112Dao workOrder112Dao;

	@Override
	public int deleteByPrimaryKey(Integer workorderId) {
		return workOrder112Dao.deleteByPrimaryKey(workorderId);
	}

	@Override
	public int insert(WorkOrder112 workorder) {
		return workOrder112Dao.insert(workorder);
	}

	@Override
	public int insertSelective(WorkOrder112 workorder) {
		return workOrder112Dao.insertSelective(workorder);
	}

	@Override
	public WorkOrder112 selectByPrimaryKey(Integer workorderId) {
		return workOrder112Dao.selectByPrimaryKey(workorderId);
	}

	@Override
	public List<WorkOrder112> selectWorkOrder112(WorkOrder112 workorder) {
		return workOrder112Dao.selectWorkOrder112(workorder);
	}

	@Override
	public int selectCount(WorkOrder112 workorder) {
		return workOrder112Dao.selectCount(workorder);
	}

	 /**
     * 查询112工单
     * @author niewenqiang
     * **/
   public List<WorkOrder112> selectOrder112(Map<String, String> mapdata){
    	return workOrder112Dao.selectOrder112(mapdata);
    }
    
    
    /**
     * 查询112工单数量
     * @author niewenqiang
     * **/
    public int selectOrder112Count(Map<String, String> mapdata){
    	return workOrder112Dao.selectOrder112Count(mapdata);
    }
    
    
    /**
     * 查询112工单电话小结
     * @author niewenqiang
     * **/
   public  List<WorkTelSum112> select112Xj(WorkTelSum112 worksum112){
    	return workOrder112Dao.select112Xj(worksum112);
    }
    
    /**
     * 查询112工单电话小结数量
     * @author niewenqiang
     * **/
    public int select112XjCount(WorkTelSum112 worksum112){
    	return workOrder112Dao.select112XjCount(worksum112);
    }
    
    /***
     * 查询未关联的录音信息
     * **/
    public List<RecordInfo> getNoGLRecord(RecordInfo recordinfo){
    	return workOrder112Dao.getNoGLRecord(recordinfo);
    }
    
    
    /***
     * 查询未关联的录音信息数量
     * **/
   public  int getNoGLRecordCount(RecordInfo recordinfo){
    	return workOrder112Dao.getNoGLRecordCount(recordinfo);
    }
   

   /***
    * 获取录音播放地址
 * @throws Exception 
    * **/
   public String getRecordFileUrl(String recordFilename,String xmlfilepath){
		try {
			return getRecodeFileUrl(recordFilename,xmlfilepath);
		} catch (Exception e) {
			log.error(e);
		}
		return "";
   }
   

	/**
	 * 获取录音路径 
	 * @author taoyang
	 * @date 2010-09-02
	 * @param recordSerialNumber
	 * @param xmlfilepath
	 * @return
	 * @throws Exception
	 */
	public String getRecodeFileUrl(String recordSerialNumber,String xmlfilepath) throws Exception{

		final String vid = recordSerialNumber.substring(1, 3);
		String channel = recordSerialNumber.substring(3, 6);
		String date = recordSerialNumber.substring(6, 14);
		StringBuffer fileURL = new StringBuffer();
		
		CommXmlService cxService = new CommXmlService();
		Map fptMap = cxService.getMapByXmlFilePath(xmlfilepath, new ICollDataToMap(){

			public Map collDataToMap(Element root) {
				Map map = new HashMap();
				List list = root.selectNodes("/servers/server/voiceID");
				Iterator iterator_c  = list.iterator();
				while(iterator_c.hasNext()){
					Element ele = (Element)iterator_c.next();
					if(ele.getTextTrim().equals(vid)){
						Element ele_p = ele.getParent();
						Iterator iterator_p = ele_p.elementIterator();
						while(iterator_p.hasNext()){
							Element ele_c = (Element)iterator_p.next();
							map.put(ele_c.getName(), ele_c.getTextTrim());
						}
						break;
					}
				}
				return map;
			}
		});
		
		if(fptMap.size()!=0){
			String remotefilename =  recordSerialNumber + ".wav";
			String ip = fptMap.get("ip").toString();
			int port = Integer.parseInt(fptMap.get("port").toString());
			String user = fptMap.get("username").toString();
			String password = fptMap.get("password").toString();
			String path = fptMap.get("dir").toString() +"/" + date +"/"+vid +"/" + channel ;
			/*ftp://lxxz:rec123abc@10.7.241.10\vox\lxxz03\030\e0303020100818101045.wav
			fileURL.append(user).append(":").append(password)
			.append("@").append(ip).append("/").append(path)
			.append("/").append(remotefilename);*/
			fileURL.append("/").append(path).append("/").append(remotefilename);
		}
		
		return fileURL.toString();
	}

	@Override
	public int insert112sum(WorkTelSum112 record) {
		return workOrder112Dao.insert112sum(record);
	}

	@Override
	public List<String> getDeclarationBigType() {
		return workOrder112Dao.getDeclarationBigType();
	}
}
