package cn.sh.ideal.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;


/**
 * 
 * @author 柳国春
 * @date 2009-12-01
 */
public class IdealFtpClient {
	private FTPClient ftpClient = null;
	private static Logger log = Logger.getLogger(IdealFtpClient.class);
	/**
	 * 连接FTP
	 * @param server
	 * @param user
	 * @param password
	 */
	public void connectServer(String server, String user, String password,int remotePort,String rootDir){
		if(ftpClient == null || !ftpClient.connected()){
			ftpClient = new FTPClient();
			try{
				ftpClient.setControlEncoding("utf-8");
				ftpClient.setRemoteHost(server);
				ftpClient.setRemotePort(remotePort);
				ftpClient.setTimeout(90000);
				ftpClient.connect();
				ftpClient.login(user, password);
				ftpClient.setConnectMode(FTPConnectMode.ACTIVE); 
				ftpClient.setType(FTPTransferType.BINARY); 
				ftpClient.chdir(rootDir);
				 				
			}catch(Exception e){
				log.error(""+e);
			}
		}
	}
	/**
	 * 进入目录
	 * @param path
	 */
	public void changeDir(String path){
		if(path == null || path.equals("")){
			return;
		}
		String temp[] = path.split("/");
		for(int i=0;i<temp.length;i++){
			try{
				if(temp[i] == null || temp[i].equals("")){
					continue;
				}
				ftpClient.chdir(temp[i]);
			}catch(FTPException fe){
				try{
					ftpClient.mkdir(temp[i]);
					ftpClient.chdir(temp[i]);
				}catch(Exception e){
					log.error(""+e);
				}
			}catch(Exception ee){
				log.error(""+ee);
			}
		}
	}
	/**
	 * 上传文件
	 * @param in
	 * @param filename
	 */
	public void uploadFile(InputStream in,String filename){
		try{
			ftpClient.put(in,filename);
		}catch(Exception e){
			log.error(""+e);
		}
	}
	
	
	
	public void downLoad(OutputStream out ,String filename){
		  try{
			  ftpClient.get(out, filename);
		  }catch(Exception e){
			  log.error(""+e);
		  }
	}
	

	public void downLoad(String localPath ,String remoteFile){
		  try{
			  ftpClient.get(localPath, remoteFile);
		  }catch(Exception e){
			  log.error(""+e);
		  }
	}
	
	
	/**
	 * Description: 得到文件的字节数组
	 * @author hewei
	 * CreateDate: 2010-3-8
	 * @param filename
	 * @return
	 * @throws FTPException 
	 * @throws IOException 
	 * @throws Exception
	 */
	public byte[] get(String filename) throws IOException, FTPException {
		return ftpClient.get(filename);
	}
	
	/**
	 * Description: 将字节数组写入ftp
	 * @author hewei
	 * CreateDate: 2010-3-8
	 * @param b
	 * @param filename
	 * @throws FTPException 
	 * @throws IOException 
	 * @throws Exception
	 */
	public void put(byte[] b,String filename) throws IOException, FTPException{
		ftpClient.put(b, filename);
	}
	
	public boolean isExists(String filename){
		boolean isExists = true;
		  try{
			  
			 byte [] bt = ftpClient.get(filename);
			 if(bt == null || bt.length == 0) isExists = false;
		  }catch(Exception e){
			  isExists = false;
		  }
		 return isExists;
	}
	
	
	
	public boolean delete(String filename){
		  try{
			  ftpClient.delete(filename);
			  return true;
		  }catch(Exception e){
			  log.error(""+e);
			  return false;
		  }
	}
	
	/**
	 * 关闭连接
	 *
	 */
	public void close(){
		try{
			if(ftpClient != null && ftpClient.connected()){
				ftpClient.quit();
				ftpClient = null;
			}
		}catch(Exception e){
			log.error(""+e);
		}
	}
}
