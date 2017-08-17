package cn.sh.ideal.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 操作文件、文件夹server
 * @author taoyang
 * @date 2011-03-01
 *
 */
public class TSysOperFileServer {
	
	/** 超类提供的Log */
	protected static final Log logger = LogFactory.getLog(TSysOperFileServer.class);
	
	/**
	 * 根据文件夹路径、文件名获得文件对象(如果文件夹不存在及创建文件夹、文件不存在及创建文件)
	 * @param dirPath 文件夹路径,例如:webRoot/myDir
	 * @param fileName 文件名,例如:32.txt
	 * @return File 关联的文件对象
	 * @throws Exception
	 */
	public static File createFile(String dirPath,String fileName){
		File dir = new File(dirPath);
		if(!dir.exists())dir.mkdirs();
		dir = null;
		File file = new File(dirPath+"/"+fileName);
		try {
			if(!file.exists()) logger.info("create new file:"+file.createNewFile());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return file;
	}
	
	/**
	 * 往文件里写数据
	 * @param file 关联的文件对象
	 * @param content 将要写的数据内容
	 * @param isAppend 是追加还是覆盖原文件内容(true 追加,false 覆盖)
	 * @return 是否操作成功(true 成功,false 失败)
	 * @throws Exception
	 */
	public static boolean writerCntToFile(File file,String content,boolean isAppend){
		FileWriter writer = null;
		try {
			 writer = new FileWriter(file,isAppend);
			 writer.write(content);
			 writer.flush();
			 return true;
		} catch (IOException e) {

		}finally{
			if(writer!=null)
				try {
					writer.close();
				} catch (IOException e) {
				}
			writer = null;
		}
		return false;
	}
	
	/**
	 * 删除dateNum天之前的文件(以文件最后一次修改日期为标准)
	 * @param dirPath文件所在的文件夹
	 * @param dateNum多少天之前
	 */
	public static void deleteFileByDate(String dirPath,int dateNum){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -dateNum);
		
		File dir = new File(dirPath);
		if(dir.exists()){
			File[] files = dir.listFiles();
			int size = files.length;
			for(int i=0;i<size;i++){
				File file = files[i];
				if(file.isFile()){
					if(file.lastModified()<=calendar.getTimeInMillis()){
						logger.info("delete file:"+file.delete());
					}
				}else{
					deleteFileByDate(file.getPath(),dateNum);
				}
			}
		}
	}
}
