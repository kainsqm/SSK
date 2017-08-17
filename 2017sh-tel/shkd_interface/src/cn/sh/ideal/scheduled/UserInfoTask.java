package cn.sh.ideal.scheduled;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.sh.ideal.model.SysRoleUser;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.service.IOperateLogService;
import cn.sh.ideal.service.ISysRoleUserService;
import cn.sh.ideal.service.IUserInfoService;
import cn.sh.ideal.util.ConfigProperties;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.DesUtil;
import cn.sh.ideal.util.IdealFtpClient;

import com.github.pagehelper.StringUtil;

@Component("userInfoTask")
public class UserInfoTask {
	
	@Value("#{config['recordServer']}")
	private String recordServer = "";
	
	@Value("#{config['recordUserName']}")
	private String recordUserName = "";
	
	@Value("#{config['recordPwd']}")
	private String recordPd = "";
	
	@Value("#{config['recordRootDir']}")
	private String recordRootDir = "";
	
	@Value("#{config['recordPort']}")
	private int recordPort =0;
	
	@Resource
	private IOperateLogService operateLogService;
	

	@Resource
	private IUserInfoService userInfoService;
	
	@Resource
	private ISysRoleUserService sysRoleUserService;
	
	
	private SimpleDateFormat sf =new SimpleDateFormat("yyyyMMdd");
	private static Logger log = Logger.getLogger(UserInfoTask.class);
	private IdealFtpClient ftp=new IdealFtpClient();
	
	private Map<String,Set<String>> map=new HashMap<String,Set<String>>();
	
	//每天凌晨3点执行一次
	//@Scheduled(cron="*/5 * * * * ? ")
	@Scheduled(cron="0 0 3 * * ? ")
	public void userInfoTask() {
		log.info("人员接口定时任务开始执行......");
		
		try {
			//1.拼装文件路径,并读取文件； 获取今天日期
			Date datePath=DateUtil.getFrontDateByDayCount(new Date(),0);
			String strPath=sf.format(datePath);
			//2.链接ftp;
			log.info("ftp信息：服务器ip"+recordServer+";用户名："+recordUserName+";密码："+recordPd+";端口"+recordPort+";路径："+recordRootDir);
			ftp.connectServer(recordServer,recordUserName,recordPd,recordPort,recordRootDir);
			String userInfofileName="operator_infor_"+strPath+".txt";
			log.info("同步的人员文件名为："+userInfofileName);
			readFileByLines(userInfofileName);
			//删除3天前的数据
			String deldatePath=sf.format(DateUtil.getFrontDateByDayCount(new Date(),3));
			String deluserInfofileName="operator_infor_"+deldatePath+".txt";
			ftp.delete(deluserInfofileName);
			ftp.close();
		}catch(Exception e ){
			log.error("读人员信息异常1："+e);
        	try {
        		String exc=e.getMessage();
        		if(StringUtil.isNotEmpty(exc)&&exc.length()>4000){
        			exc=exc.substring(0, 4000);
        		}
				operateLogService.addLog(Constans.DATASYNC, Constans.DATASYNC, "同步人员数据出现异常，异常信息为{0}", new Object[]{exc}, null);
				ftp.close();
        	} catch (Exception e1) {
				log.error("读人员信息异常2："+e);
				ftp.close();
			}
		}
	}
		
	 /**
     * 以行为单位读取人员数据
	 * @throws Exception 
     */
    public void  readFileByLines(String fileName){
    	BufferedReader reader = null;  
    	//try {
    		//将数据读入到by数组
			byte[] byFile;
			try {
					byFile = ftp.get(fileName);
					ByteArrayInputStream byteArray = new ByteArrayInputStream(byFile);
			        reader = new BufferedReader(new InputStreamReader(byteArray, "GBK"));   
		            String tempString = null;
		            int line = 1;
		            int sucNum=0;//正确行数量
		            int errNum=0;//错误行数量
			        StringBuffer errLine=new StringBuffer();//错误行数
			        StringBuffer errDis=new StringBuffer();//错误原因
			        String temp1 =reader.readLine();//从第二行开始读
		            while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束,
		                log.debug("line： " + line + ";原始数据： " + tempString);
		                String res="";
		                    res=analyseUserInfo(line,tempString);
		                if(res!=null && !res.equals("")){
		 	        	   errNum++;
		 	        	   errLine.append(line+",");
		 	        	   errDis.append("line： " + line + ",错误原因： " + res+"\n");
		 	           }else{
		 	        	   sucNum++;
		 	           }
		                line++;
		            }
		            reader.close();
		            deleteUserData();
		            
		            String err=errDis.toString().replace("\n", "");
		    		if(StringUtil.isNotEmpty(err)&&err.length()>4000){
		    			err=err.substring(0, 4000);
		    		}
		            operateLogService.addLog(Constans.DATASYNC, Constans.DATASYNC_USERINFO, Constans.DATASYNC_USERINFO+"：共"+(line-1)+"条数据，成功"+sucNum+"条；失败"+errNum+"条，分别是第【"+errLine+"】行，失败原因："+err, null, null);
		            log.info(Constans.DATASYNC_USERINFO+"：共"+(line-1)+"条数据，成功"+sucNum+"条；失败"+errNum+"条，分别是第【"+errLine+"】行，失败原因："+errDis);
			} catch (Exception e) {
				log.error(e);
			}
	      
    }
    
    /**
    * 解析用户数据
    *  
    **/
    public  String analyseUserInfo(int line,String data) {
    	String res="";
    	if(StringUtil.isNotEmpty(data)){
    		String[] strdata=data.split("_\\+\\|");
    		//边缘条件测试，小于34，说明数据不够，当异常数据处理
    		if(strdata.length<5){
    			res="第"+line+"行数据个数不够,实际有"+strdata.length+"个数据;";
    		}
    		//数量刚好
    		if(strdata.length==5){
    			strdata[0]=strdata[0].replace(" ", "");
    			strdata[1]=strdata[1].replace(" ", "");
    			strdata[2]=strdata[2].replace(" ", "");
    			strdata[3]=strdata[3].replace(" ", "");
    			strdata[4]=strdata[4].replace(" ", "");
    			
				//必填检验(大工号，姓名，小工号不能为空)
    			if(StringUtil.isEmpty(strdata[0])){
    				res="第"+line+"行的姓名为空;";
    			}else if(StringUtil.isEmpty(strdata[1])){
    				res="第"+line+"行的大工号为空;";
    			}else if(StringUtil.isEmpty(strdata[2])){
    				res="第"+line+"行的小工号为空;";
    			}else if(userInfoService.selectByBigWorkid(strdata[1])==null){//大工号不存在，添加该大工号,并添加该大工号下面的小工号
    				
    				try{
	    				UserInfo biguser=new UserInfo();
	    				biguser.setDataFrom(Constans.DATA_FROM_INF);
	    				biguser.setUserName(strdata[0]);
	    				biguser.setWorkId(strdata[1]);
	    				biguser.setPid(1);
	    				biguser.setPasswd(DesUtil.encrypt("123456",ConfigProperties.getProperty("DESKEY")));
	    				userInfoService.insert(biguser);
	    				//默认角色为受理员
	    				SysRoleUser role=new SysRoleUser();
	    				role.setRoleId(1);//受理员
	    				role.setEnabled("1");
	    				role.setUserId(biguser.getUserId());
	    				sysRoleUserService.insertUserRole(role);
	    				
	    				
	    				UserInfo smalluser=new UserInfo();
	    				smalluser.setDataFrom(Constans.DATA_FROM_INF);
	    				smalluser.setSmailWorkid(strdata[2]);
	    				smalluser.setPid(biguser.getUserId());
	    				smalluser.setGroupName(strdata[3]);
	    				smalluser.setJob(strdata[4]);
	    				
	    				userInfoService.insert(smalluser);
	    				
	    				if(null!=map.get(strdata[1])){
	    					map.get(strdata[1]).add(strdata[2]);
	    				}else{
	    					Set<String> set=new HashSet<String>();
	    					set.add(strdata[2]);
	    					map.put(strdata[1], set);
	    				}
	    				//map.put(strdata[1], map.get(strdata[1]));
    				}catch(Exception e){
 						log.error("插入数据库出现异常："+e);
 						res="第"+line+"行数据有误,插入数据异常:"+e.getMessage()+";";
 					}
    				//大工号存在，判断姓名是否相同，不同时更新该行数据；判断该工号下面是否有该小工号，没有时添加小工号,有时判断组室和岗位是否相同，不同时更新
    			}else if(userInfoService.selectByBigWorkid(strdata[1])!=null){
    				try{
	    				UserInfo userinfo1=userInfoService.selectByBigWorkid(strdata[1]);
	    				if(!userinfo1.getUserName().equals(strdata[0])){
	    					userinfo1.setUserName(strdata[0]);
	    					userInfoService.updUserInfo(userinfo1);
	    				}
	    				List<UserInfo> list=userInfoService.selectSmailWorkId(userinfo1);
	    				UserInfo sucUser=new UserInfo();
	    				boolean flag=false;
	    				for(int i=0;i<list.size();i++){
	    					//由于sql中将小工号用workid表示，故此处用workid取出
	    					if(list.get(i).getSmailWorkid().equals(strdata[2])){
	    						sucUser=list.get(i);
	    						flag=true;
	    						break;
	    					}
	    				}
	    				if(flag){
	    					if((sucUser.getGroupName()!=null &&!sucUser.getGroupName().equals(strdata[3])) ||
	    		    				  (sucUser.getJob()!=null && !sucUser.getJob().equals(strdata[4]))){
	    		    					sucUser.setGroupName(strdata[3]);
	    		    					sucUser.setJob(strdata[4]);
	    		    					userInfoService.updUserInfo(sucUser);
	    							}
	    				}else{//插入该小工号
	    					
	    					UserInfo smalluser=new UserInfo();
		    				smalluser.setDataFrom(Constans.DATA_FROM_INF);
		    				smalluser.setSmailWorkid(strdata[2]);
		    				smalluser.setPid(userinfo1.getUserId());
		    				smalluser.setGroupName(strdata[3]);
		    				smalluser.setJob(strdata[4]);
		    				userInfoService.insert(smalluser);
	    				}
	    				if(null!=map.get(strdata[1])){
	    					map.get(strdata[1]).add(strdata[2]);
	    				}else{
	    					Set<String> set=new HashSet<String>();
	    					set.add(strdata[2]);
	    					map.put(strdata[1], set);
	    				}
    				}catch(Exception e){
						log.error("插入数据库出现异常："+e);
						res="第"+line+"行数据有误,插入数据异常:"+e.getMessage()+";";
						throw e;
					}
    			}
    		}
    		//数据格式不规范
    		if(strdata.length>5){
    			res="第"+line+"行数据有误,实际有"+strdata.length+"个数据;";
    		}
    	}else{
    		res="第"+line+"行数据为空";
    	}
    	return res;
	}
    
    /**
     *  逻辑删除
     * 
     **/
    public void deleteUserData() {
    	//先逻辑删除大工号
    	Set<String> keys=map.keySet();
    	userInfoService.deleteBigWorkid(keys);
    	 
    	//逻辑删除小工号
    	for (String o : map.keySet()) {
    		//查出大工号的useid
    		Integer userid=userInfoService.selectByBigWorkid(o).getUserId();
    		userInfoService.deleteSmallWorkid(map.get(o), userid);
    	}
	}
    
    public static void main(String[] args) {
		String test="白季红_+|AKD4057_+|6084_+|_+|";
		String test1="_+|AKD1101_+|SH1101_+|售中_+|一级受理员";
		System.out.println(test.split("_\\+\\|").length);
		System.out.println(test1.split("_\\+\\|").length);
	}
  
}
