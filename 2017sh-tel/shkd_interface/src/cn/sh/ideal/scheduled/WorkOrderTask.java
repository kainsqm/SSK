package cn.sh.ideal.scheduled;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.sh.ideal.model.WorkOrder112;
import cn.sh.ideal.model.WorkOrderCdma;
import cn.sh.ideal.model.WorkOrderCdmaSum;
import cn.sh.ideal.model.WorkTelSum112;
import cn.sh.ideal.service.IOperateLogService;
import cn.sh.ideal.service.IWorkOrder112Service;
import cn.sh.ideal.service.IWorkOrderCdmaService;
import cn.sh.ideal.service.IWorkOrderCdmaSumService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.IdealFtpClient;

import com.enterprisedt.net.ftp.FTPException;
import com.github.pagehelper.StringUtil;

@Component("workOrder112Task")
public class WorkOrderTask {
	
	@Value("#{config['recordServer']}")
	private String recordServer = "";
	
	@Value("#{config['recordUserName']}")
	private String recordUserName = "";
	
	@Value("#{config['recordPwd']}")
	private String recordMima = "";
	
	@Value("#{config['recordRootDir']}")
	private String recordRootDir = "";
	
	@Value("#{config['recordPort']}")
	private int recordPort =0;
	
	@Resource
	private IOperateLogService operateLogService;
	@Resource
	private IWorkOrder112Service workOrder112Service;
	@Resource
	private IWorkOrderCdmaService workOrderCdmaService;
	@Resource
	private IWorkOrderCdmaSumService workOrderCdmaSumService;
	
	private SimpleDateFormat sf =new SimpleDateFormat("yyyyMMdd");
	private static Logger log = Logger.getLogger(WorkOrderTask.class);
	private IdealFtpClient ftp=new IdealFtpClient();
	
	//每天凌晨4点执行一次
  //@Scheduled(cron="0 0/1 * * * ? ")
	@Scheduled(cron="0 0 4 * * ? ")
	public void workOrder112() {
		log.info("工单同步定时任务开始执行......");
		
		try {
			//1.拼装文件路径,并读取文件； 获取今天日期
			Date datePath=DateUtil.getFrontDateByDayCount(new Date(),0);
			String strPath=sf.format(datePath);
			//2.链接ftp;
			ftp.connectServer(recordServer,recordUserName,recordMima,recordPort,recordRootDir);
			String wkOrder112fileName="fault_infor_"+strPath+".txt";
			String wkOrder112SumfileName="result_infor_"+strPath+".txt";
			String wkOrdercdmafileName="cdma_infor_"+strPath+".txt";
			String wkOrdercdmaSumfileName="cdmaresult_infor_"+strPath+".txt";
			log.info("同步数据的文件名称：112工单：+"+wkOrder112fileName+"；112工单小结："+wkOrder112SumfileName+"；cdma工单："+wkOrdercdmafileName+"；cdma工单小结："+wkOrdercdmaSumfileName);
			readFileByLines(wkOrder112fileName,Constans.DATASYNC_112WKORDER);
			readFileByLines(wkOrder112SumfileName,Constans.DATASYNC_112WKORDER_SUM);
			readFileByLines(wkOrdercdmafileName,Constans.DATASYNC_CWKORDER);
			readFileByLines(wkOrdercdmaSumfileName,Constans.DATASYNC_CWKORDER_SUM);
			
			//删除3天前的数据
			String deldatePath=sf.format(DateUtil.getFrontDateByDayCount(new Date(),3));
			String delwkOrder112fileName="fault_infor_"+deldatePath+".txt";
			String delwkOrder112SumfileName="result_infor_"+deldatePath+".txt";
			String delwkOrdercdmafileName="cdma_infor_"+deldatePath+".txt";
			String delwkOrdercdmaSumfileName="cdmaresult_infor_"+deldatePath+".txt";
			
			ftp.delete(delwkOrder112fileName);
			ftp.delete(delwkOrder112SumfileName);
			ftp.delete(delwkOrdercdmafileName);
			ftp.delete(delwkOrdercdmaSumfileName);
			ftp.close();
		}catch(Exception e ){
			log.error("读工单信息异常1："+e);
        	try {
        		String exc=e.getMessage();
        		if(StringUtil.isNotEmpty(exc)&&exc.length()>4000){
        			exc=exc.substring(0, 4000);
        		}
				operateLogService.addLog(Constans.DATASYNC, Constans.DATASYNC, "同步工单数据出现异常，异常信息为{0}", new Object[]{exc}, null);
				ftp.close();
        	} catch (Exception e1) {
				log.error("读工单信息异常2："+e1);
				ftp.close();
			}
		}
	}
		
	 /**
     * 以行为单位读取工单数据
	 * @throws FTPException 
	 * @throws IOException 
	 * @throws Exception 
     */
    public void  readFileByLines(String fileName,String type) throws IOException, FTPException  {
    	BufferedReader reader = null;  
    //	try {
    		//将数据读入到by数组
			byte[] byFile =ftp.get(fileName);
	        ByteArrayInputStream byteArray = new ByteArrayInputStream(byFile);
	        reader = new BufferedReader(new InputStreamReader(byteArray, "GBK"));   
            String tempString = null;
            int line = 1;
            int sucNum=0;//正确行数量
            int errNum=0;//错误行数量
	        StringBuffer errLine=new StringBuffer();//错误行数
	        StringBuffer errDis=new StringBuffer();//错误原因
	        String oneReader=reader.readLine();//从第二行开始读
            while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束,
                log.debug("line： " + line + ";原始数据： " + tempString);
                String res="";
                switch (type) {
    			case Constans.DATASYNC_112WKORDER:
                    res=analyseWorkOrder(line,tempString);
    				break;
    			case Constans.DATASYNC_112WKORDER_SUM:
    				res=analyseWorkOrderSum(line,tempString);		
    				break;
    			case Constans.DATASYNC_CWKORDER:
    				res=analyseCdmaWorkOrder(line,tempString);
    				break;
    			case Constans.DATASYNC_CWKORDER_SUM:
    				res=analyseCdmaSumWorkOrder(line,tempString);
    				break;

    			default:
    				break;
    			}
               
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
            String err=errDis.toString().replace("\n", "");
    		if(StringUtil.isNotEmpty(err)&&err.length()>4000){
    			err=err.substring(0, 4000);
    		}
            try {
				operateLogService.addLog(Constans.DATASYNC, type, type+"：共"+(line-1)+"条数据，成功"+sucNum+"条；失败"+errNum+"条，分别是第【"+errLine+"】行，失败原因："+err, null, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error(""+e);
			}
            log.info(type+"：共"+(line-1)+"条数据，成功"+sucNum+"条；失败"+errNum+"条，分别是第【"+errLine+"】行，失败原因："+errDis);
	            
    }
    
    /**
    * 解析工单数据
    *  
    **/
    public  String analyseWorkOrder(int line,String data) {
    	String res="";
    	if(StringUtil.isNotEmpty(data)){
    		String[] strdata=data.split("_\\+\\|");
    		//边缘条件测试，小于34，说明数据不够，当异常数据处理
    		if(strdata.length<34){
    			res="第"+line+"行数据个数不够,实际有"+strdata.length+"个数据;";
    		}
    		//数量刚好
    		if(strdata.length==34){
    			//去除空格
    			for(int i=0;i<34;i++){
    				strdata[i]=strdata[i].replace(" ", "");
    			}
    			WorkOrder112 wk=new WorkOrder112();
				//唯一性、必填检验
    			if(StringUtil.isEmpty(strdata[0])){
    				res="第"+line+"行的工单流水号为空;";
    			}else if(workOrder112Service.selectByPrimaryKey(Integer.parseInt(strdata[0]))!=null){
    				res="第"+line+"行的工单流水号重复，工单流水号是："+strdata[0]+";";
    			}else{
    				try{
					wk.setWorkorderId(Integer.parseInt(strdata[0]));
					wk.setErrorNo(strdata[1]);
					wk.setIsGw(strdata[2]);
					wk.setRc(strdata[3]);
					wk.setBranch(strdata[4]);
					wk.setSites(strdata[5]);
					wk.setDeclarationTime(DateUtil.getDateTimeStr(DateUtil.parseDateTime(strdata[6])));
					wk.setBusinessType(strdata[7]);
					wk.setDeclarationBigType(strdata[8]);
					wk.setDeclarationDescription(strdata[9]);
					wk.setTestCode(strdata[10]);
					wk.setTestResult(strdata[11]);
					wk.setDeclarationRemark(strdata[12]);
					wk.setDeclarationLinkinfo(strdata[13]);
					wk.setIsSecondRedeal(strdata[14]);
					wk.setCloseedWay(strdata[15]);
					wk.setCloseedTimelength(Integer.parseInt(strdata[16]));
					wk.setAcceptedSource(strdata[17]);
					wk.setFirstAgentUserid(strdata[18]);
					wk.setFirstRedealWorkid(strdata[19]);
					wk.setFirstRedealSuggest(strdata[20]);
					wk.setFirstRedealClosedcode(strdata[21]);
					wk.setSecondRedealClosedcode(strdata[22]);
					wk.setSecondRedealRemark(strdata[23]);
					wk.setSecondWorkid(strdata[24]);
					wk.setSecondClosetime(DateUtil.getDateTimeStr(DateUtil.parseDateTime(strdata[25])));
					wk.setRepairTime(DateUtil.getDateTimeStr(DateUtil.parseDateTime(strdata[26])));
					wk.setRepairWorkid(strdata[27]);
					wk.setRepairDirection(strdata[28]);
					wk.setRepairCenter(strdata[29]);
					wk.setThreeErrorType(strdata[30]);
					wk.setThreeErrorRepairCode(strdata[31]);
					wk.setThreeWorkid(strdata[32]);
					wk.setThreeRepairTime(DateUtil.getDateTimeStr(DateUtil.parseDateTime(strdata[33])));
    				}catch(ParseException e1){
    					log.error(""+e1);
    				}try{
						workOrder112Service.insert(wk);
	 					}catch(Exception e){
	 						log.error("同步数据出现异常5："+e);
	 						res="第"+line+"行数据有误,插入数据异常:"+e.getMessage()+";";
	 						throw e;
	 					}
    			}
    		}
    		//数据格式不规范
    		if(strdata.length>34){
    			res="第"+line+"行数据有误,实际有"+strdata.length+"个数据;";
    		}
    	}else{
    		res="第"+line+"行数据为空";
    	}
    	return res;
	}
    
    /**
     * 解析cdma工单数据
     *  
     **/
     public  String analyseCdmaWorkOrder(int line,String data) {
     	String res="";
     	if(StringUtil.isNotEmpty(data)){
     		String[] strdata=data.split("_\\+\\|");
     		//边缘条件测试，小于33，说明数据不够，当异常数据处理
     		if(strdata.length<33){
     			res="第"+line+"行数据个数不够,实际有"+strdata.length+"个数据;";
     		}
     		//数量刚好
     		if(strdata.length==33){
     			for(int i=0;i<33;i++){
     				strdata[i]=strdata[i].replace(" ", "");
     			}
     			WorkOrderCdma wk=new WorkOrderCdma();
 				//唯一性、必填检验
     			if(StringUtil.isEmpty(strdata[1])){
     				res="第"+line+"行的工单流水号为空;";
     			}else if(workOrderCdmaService.selectByPrimaryKey(Integer.parseInt(strdata[1]))!=null){
     				res="第"+line+"行的工单流水号重复，工单流水号是："+strdata[1]+";";
     			}else{
 					wk.setAppealStatus(strdata[0]);
 					wk.setSerialCdma(Integer.parseInt(strdata[1]));
 					wk.setDirNum(strdata[2]);
 					wk.setWxSerial(strdata[3]);
 					try {
						wk.setComplaintTime(DateUtil.getDateTimeStr(DateUtil.parseDateTime(strdata[4])));
					
 					wk.setComplaintType(strdata[5]);
 					wk.setComplaintService(strdata[6]);
 					wk.setComplaintReason(strdata[7]);
 					wk.setAppealingNum(strdata[8]);
 					wk.setReceiptOpId(strdata[9]);
 					wk.setPretreatType(strdata[10]);
 					wk.setPretreatResult1(strdata[11]);
 					wk.setPretreatTime(DateUtil.getDateTimeStr(DateUtil.parseDateTime(strdata[12])));
 					wk.setPretreatRemark(strdata[13]);
 					wk.setTlOpId(strdata[14]);
 					wk.setTlTime(DateUtil.getDateTimeStr(DateUtil.parseDateTime(strdata[15])));
 					wk.setComplaintType2(strdata[16]);
 					wk.setComplaintService2(strdata[17]);
 					wk.setComplaintReason2(strdata[18]);
 					wk.setIsZhuan(strdata[19]);
 					wk.setPretreatRemark2(strdata[20]);
 					wk.setDeal(strdata[21]);
 					wk.setPretreatType2(strdata[22]);
 					wk.setPretreatOpId(strdata[23]);
 					wk.setPretreatDir(strdata[24]);
 					wk.setDispatchTime(DateUtil.getDateTimeStr(DateUtil.parseDateTime(strdata[25])));
 					wk.setPretreatResult2(strdata[26]);
 					wk.setRepairTime(DateUtil.getDateTimeStr(DateUtil.parseDateTime(strdata[27])));
 					wk.setRepairOpId(strdata[28]);
 					wk.setSatisfy(strdata[29]);
 					wk.setIsEnglish(strdata[30]);
 					wk.setComplaintAddr(strdata[31]);
 					wk.setDispachOp(strdata[32]);
 					} catch (ParseException e1) {
						log.error(""+e1);
					}
 					try{
 					workOrderCdmaService.insert(wk);
 					}catch(Exception e){
 						log.error("同步数据出现异常5："+e);
 						res="第"+line+"行数据有误,插入数据异常:"+e.getMessage()+";";
 						throw e;
 					}
     			}
     		}
     		//数据格式不规范
     		if(strdata.length>33){
     			res="第"+line+"行数据有误,实际有"+strdata.length+"个数据;";
     		}
     	}else{
     		res="第"+line+"行数据为空";
     	}
     	return res;
 	}
     
     /**
      * 解析cdma工单小结数据
      *  
      **/
      public  String analyseCdmaSumWorkOrder(int line,String data) {
      	String res="";
      	if(StringUtil.isNotEmpty(data)){
      		String[] strdata=data.split("_\\+\\|");
      		//边缘条件测试，小于9，说明数据不够，当异常数据处理
      		if(strdata.length<9){
      			res="第"+line+"行数据个数不够,实际有"+strdata.length+"个数据;";
      		}
      		//数量刚好
      		if(strdata.length==9){
      			for(int i=0;i<9;i++){
     				strdata[i]=strdata[i].replace(" ", "");
     			}
      			WorkOrderCdmaSum wk=new WorkOrderCdmaSum();
  				//唯一性、必填检验
      			if(StringUtil.isEmpty(strdata[5])){
      				res="第"+line+"行的工单流水号为空;";
      			}else if(workOrderCdmaService.selectByPrimaryKey(Integer.parseInt(strdata[5]))==null){
      				res="第"+line+"行的工单流水号不存在，工单流水号是："+strdata[5]+";";
      			}else{
      				try{
  					wk.setServiceType(strdata[0]);
  					wk.setResultType(strdata[1]);
  					wk.setComplaintSource(strdata[2]);
  					wk.setRemark(strdata[3]);
  					wk.setDirNum(strdata[4]);
  					wk.setSerialCdma(Integer.parseInt(strdata[5]));
  					wk.setResultSerial(strdata[6]);
  					wk.setResultTime(DateUtil.getDateTimeStr(DateUtil.parseDateTime(strdata[7])));
  					wk.setOpId(strdata[8]);
      				}catch(ParseException e1){
      					log.error(""+e1);
      				}
  					try{
  						workOrderCdmaSumService.insert(wk);
  	 					}catch(Exception e){
  	 						log.error("同步数据出现异常5："+e);
  	 						res="第"+line+"行数据有误,插入数据异常:"+e.getMessage()+";";
  	 						throw e;
  	 					}
  					
      			}
      		}
      		//数据格式不规范
      		if(strdata.length>9){
      			res="第"+line+"行数据有误,实际有"+strdata.length+"个数据;";
      		}
      	}else{
      		res="第"+line+"行数据为空";
      	}
      	return res;
  	}
    
    
    
   
    
    /**
     * 解析工单小结数据
     *  
     **/
     public  String analyseWorkOrderSum(int line,String data) {
     	String res="";
     	if(StringUtil.isNotEmpty(data)){
     		String[] strdata=data.split("_\\+\\|");
     		//边缘条件测试，小于10，说明数据不够，当异常数据处理
     		if(strdata.length<10){
     			res="第"+line+"行数据个数不够,实际有"+strdata.length+"个数据;";
     		}
     		//数量刚好
     		if(strdata.length==10){
     			for(int i=0;i<10;i++){
     				strdata[i]=strdata[i].replace(" ", "");
     			}
     			WorkTelSum112 wk=new WorkTelSum112();
 				//112流水不能为空，且在工单表中存在
     			if(StringUtil.isEmpty(strdata[6])){
     				res="第"+line+"行的112流水为空;";
     			}else if(workOrder112Service.selectByPrimaryKey(Integer.parseInt(strdata[6]))==null){
     				res="第"+line+"行的112流水号不存在，txt的112流水是："+strdata[6]+";";
     			}else{
     				try{
 					wk.setBusinessType(strdata[0]);
 					wk.setTelsumType(strdata[1]);
 					wk.setErrorSource(strdata[2]);
 					wk.setRemark(strdata[3]);
 					wk.setDeclarationNo(strdata[4]);
 					wk.setLogicNo(strdata[5]);
 					wk.setWorkorderId(Integer.parseInt(strdata[6]));
 					wk.setTelsumId(strdata[7]);
 					wk.setTelsumTime(DateUtil.getDateTimeStr(DateUtil.parseDateTime(strdata[8])));
 					wk.setTelsumWorkid(strdata[9]);
     				}catch(ParseException e1){
     					log.error(""+e1);
     				}
 					try{
 						workOrder112Service.insert112sum(wk);
  	 					}catch(Exception e){
  	 						log.error("同步数据出现异常5："+e);
  	 						res="第"+line+"行数据有误,插入数据异常:"+e.getMessage()+";";
  	 						throw e;
  	 					}
     			}
     		}
     		//数据格式不规范
     		if(strdata.length>10){
     			res="第"+line+"行数据有误,实际有"+strdata.length+"个数据;";
     		}
     	}else{
     		res="第"+line+"行数据为空";
     	}
     	return res;
 	}
	
}
