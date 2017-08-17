package cn.sh.ideal.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sh.ideal.model.TqcInfo;
import cn.sh.ideal.service.ITqcService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/controller/hsearch")
public class HttpServerController {
	private static Logger log = Logger.getLogger(HttpServerController.class);
	@Resource
	private ITqcService tqcService;
	
	@RequestMapping(value = "/execute", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Map<String, Object>  execute(@RequestParam(value = "sdate", required = false) String sdate) {
        log.info("获取大数据分析数据开始：【"+sdate+"】");
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Date date = null;
        if (StringUtils.isNotBlank(sdate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = sdf.parse(sdate);
            } catch (ParseException e) {
                // 非法入参，做异常处理
                result.put("rescode", "-1");
                result.put("resmessage", "参数格式错误，入参格式：yyyy-MM-dd");
                result.put("resultTqc", "");
                result.put("resultTotal", "0");
                log.info("获取大数据分析数据失败：【参数格式错误，入参格式：yyyy-MM-dd】");
            }
        }
        if(date!=null){
            try {
                // 成功拿到参数，处理相应业务逻辑
                List<TqcInfo> tqcList= tqcService.getTqcInfoList(sdate);
                log.info(tqcList.toString());
                result.put("rescode", "0");
                result.put("resmessage", "成功");
                result.put("resultTqc", tqcList);
                result.put("resultTotal", tqcList.size());
                log.info("获取大数据分析数据成功：-->"+result);
            } catch (Exception e) {
                result.put("rescode", "-1");
                result.put("resmessage", "失败");
                result.put("resultTqc", e.getMessage());
                result.put("resultTotal", "0");
                log.info("获取大数据分析数据失败，失败原因："+e.getMessage());
            }
        }
        resultMap.put("Response", result);
        return resultMap;
    }
	
}
