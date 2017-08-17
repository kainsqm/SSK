package cn.sh.ideal.service.impl;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sh.ideal.dao.ItExamDao;
import cn.sh.ideal.dao.ItExamExampaperDao;
import cn.sh.ideal.dao.ItExamExampaperExamineDao;
import cn.sh.ideal.dao.ItExampaperDao;
import cn.sh.ideal.dao.IvSysuserDao;
import cn.sh.ideal.model.tExam;
import cn.sh.ideal.model.tExamExampaper;
import cn.sh.ideal.model.tExamExampaperExamine;
import cn.sh.ideal.model.tExamVo;
import cn.sh.ideal.model.tExampaper;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.IExamService;

@Service("examService")
public class ExamServiceImpl implements IExamService {
	@Resource
	private ItExamDao examDao;
	
	@Resource
    private ItExampaperDao paperDao;
	
	@Resource
    private ItExamExampaperDao examExampaperDao;
	
	@Resource
	private ItExamExampaperExamineDao examExampaperExamineDao;
	
	@Resource
    private IvSysuserDao sysuoserDao;

	@Override
	public List<tExamVo> selectExamVos(tExamVo tExamVo){
	    List<tExamVo> codes = examDao.selectExamVos(tExamVo);
        for (tExamVo examVO : codes) {
            tExampaper examPaper = paperDao.getPaperByExamId(examVO.getPkAutoId()+"");
            examVO.setExamPaperName(examPaper.getExamPaperName());
            examVO.setExamPaperId(examPaper.getPkAutoId());
        }
        return codes;
	}
	 
	@Override
	@Transactional
	public String addExam(tExamVo tExamVo){
        tExam exam = changeOver(tExamVo);
        examDao.insert(exam);                //T_EXAM 考试表添加一行数据
        int examId = exam.getPkAutoId();
        tExamExampaper epaper = new tExamExampaper();
        epaper.setFkExamId(examId);
        epaper.setFkExampaperId(tExamVo.getExamPaperId());
        examExampaperDao.insert(epaper);     //T_EXAM_EXAMPAPER 考试试卷关联表
        int eaperId = epaper.getPkAutoId();
	     
        String[] listUserIds = tExamVo.getListUserIDs().split(",");
        for (int i = 0; i < listUserIds.length; i++) {
             tExamExampaperExamine exa = new tExamExampaperExamine();
             exa.setFkExamExampaperId(eaperId);
             exa.setFkUserId(Integer.parseInt(listUserIds[i]));
             exa.setExamineeStatus("0"); //考生考试状态   0 -待考中 1-考试中 2-已考完 3-取消考试资格 4-缺考 5-已自动评分 6-已手动评分 7-重新修改自动评分 8-重新修改手动评分 9-手动评分中
             exa.setIsnormal("1"); //是否正常   0-异常 1-正常
             exa.setEnabled("1");  //是否有效   0-无效 1-有效
             exa.setZuhuId(tExamVo.getZuhuId());
             examExampaperExamineDao.insert(exa);   //T_EXAM_EXAMPAPER_EXAMINE 考试状态
         }
	     return examId+"";
	}
	
	@Override
    public String updateExam(tExamVo tExamVo){
	    HashSet<String> oldUserIdSet = new HashSet<String>();
        HashSet<String> newUserIdSet = new HashSet<String>();
        
	    //更改考试表信息 selectByPrimaryKey
        int flag = examDao.updateByPrimaryKeySelective(changeOver(tExamVo));
        
        //查询考试试卷考生表id
        tExamExampaper record = new tExamExampaper();
        record.setFkExamId(tExamVo.getPkAutoId());
        record.setFkExampaperId(tExamVo.getExamPaperId());
        int e_ep_id = examExampaperDao.selectPrimaryKey(record);
        
        tExamExampaperExamine ee  = new tExamExampaperExamine();
        ee.setPkAutoId(e_ep_id);
        List<tExamExampaperExamine> examineeLists = examExampaperExamineDao.selectByeEpId(e_ep_id);
        for (tExamExampaperExamine te : examineeLists) {
            oldUserIdSet.add(te.getFkUserId()+"");
        }
        
        String[] examPaperUser = tExamVo.getListUserIDs().split(",");
        for (String str : examPaperUser) {
            newUserIdSet.add(str);
            boolean b = true;
            if(!oldUserIdSet.contains(str)){ //判断examinee表中是否存在数据，不存在则新增
                for(int i=0;i<examineeLists.size();i++){
                    tExamExampaperExamine ex =examineeLists.get(i);
                    if(ex.getFkUserId()==Integer.parseInt(str)){ 
                        tExamExampaperExamine temp = new tExamExampaperExamine();
                        temp.setEnabled("1");
                        temp.setPkAutoId(ex.getPkAutoId());
                        examExampaperExamineDao.updateByPrimaryKeySelective(temp);
                        b = false;
                        break;
                    }
                }    
                if(b){
                    tExamExampaperExamine exa = new tExamExampaperExamine();
                    exa.setFkExamExampaperId(e_ep_id);
                    exa.setFkUserId(Integer.parseInt(str));
                    exa.setExamineeStatus("0"); //考生考试状态   0 -待考中 1-考试中 2-已考完 3-取消考试资格 4-缺考 5-已自动评分 6-已手动评分 7-重新修改自动评分 8-重新修改手动评分 9-手动评分中
                    exa.setIsnormal("1"); //是否正常   0-异常 1-正常
                    exa.setEnabled("1");  //是否有效   0-无效 1-有效
                    exa.setZuhuId(tExamVo.getZuhuId());
                    examExampaperExamineDao.insert(exa);   //T_EXAM_EXAMPAPER_EXAMINE 考试状态  
                }
            }
        }
        
        if(oldUserIdSet!=null && oldUserIdSet.size()>0){
            for (String oldUserId : oldUserIdSet) {
                if(!newUserIdSet.contains(oldUserId)){
                    for(int i=0;i<examineeLists.size();i++){
                        tExamExampaperExamine ex =examineeLists.get(i);
                        if(ex.getFkUserId()==Integer.parseInt(oldUserId)){ 
                            if(ex.getExamineeStatus().equalsIgnoreCase("0")){ //如果待考中则改为无效
                                tExamExampaperExamine temp = new tExamExampaperExamine();
                                temp.setEnabled("0");
                                temp.setPkAutoId(ex.getPkAutoId());
                                examExampaperExamineDao.updateByPrimaryKeySelective(temp);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return flag+"";
    }
	
	
	private tExam changeOver(tExamVo t){
	    tExam exam = new tExam();
	    exam.setPkAutoId(t.getPkAutoId());
        exam.setExamName(t.getExamName());
        exam.setExamBeginTime(t.getToExamBeginTime());
        exam.setExamEndTime(t.getToExamEndTime());
        exam.setExamNormalScore(t.getExamNormalScore());
        exam.setExamTimeLength(t.getExamTimeLength());
        exam.setExamQuery(t.getExamQuery());
        exam.setExamStatus("1"); //考试状态   1-待考中 2-考试中 3-已考完 4-取消考试资格 5-已自动评分 6-已手动评分 7-重新修改自动评分 8-重新修改手动评分 9-手动评分中
        exam.setFkExamtargetId(t.getToExamtarget());
        exam.setFkExamtypeId(t.getToExamtype());
        exam.setFkInsertUserId(t.getFkInsertUserId());
        exam.setZuhuId(t.getZuhuId());
        return exam;
	}
	
	@Override
	public tExamVo selectByPKId(int pkAutoId){
	    tExamVo vo = examDao.selectByPKId(pkAutoId);
	    List<vSysuser> users  = sysuoserDao.getExamUsers(pkAutoId);
	    String listUserIDs = "";
	    String listUserNames = "";   
	    for (vSysuser vSysuser : users) {
	        listUserIDs += vSysuser.getUserId()+",";
	        listUserNames += vSysuser.getUserName()+",";
        }
	    vo.setListUserIDs(listUserIDs.substring(0, listUserIDs.length()-1));
	    vo.setListUserNames(listUserNames.substring(0, listUserNames.length()-1));
	    return vo;
	}
	
	@Override
	@Transactional
	public String deleteExam(int pkAutoId){
	    tExamVo vo = new tExamVo();
	    vo.setPkAutoId(pkAutoId);
	    int status = examExampaperExamineDao.selectStatusByExamId(pkAutoId);
	    if(status>0){ //状态为0才可以删除
	        return "-1";
	    }else{
	        int eefkid = examExampaperDao.selectByExamId(pkAutoId);
	        examDao.deleteByPrimaryKey(pkAutoId);
	        examExampaperDao.deleteByPrimaryKey(eefkid);
	        examExampaperExamineDao.deleteByFKID(eefkid);
	    }
	    return "1";
	}
	
	@Override
    public List<tExam> selectByCodeId(int pkAutoId){
        return examDao.selectByCodeId(pkAutoId);
    }
    

	/*******
	 * 查询最近的待考试信息
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	public List queryExam(int userId){
		return examDao.queryExam(userId);
	}
	
	/*******
	 * 查询最近的已考试信息
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	public List queryExamOver(int userId){
		return examDao.queryExamOver(userId);
	}
	
	public int selectByExamName(String examName){
	    return examDao.selectByExamName(examName);
	}
}
