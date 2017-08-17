package cn.sh.ideal.service;

import java.util.List;

import cn.sh.ideal.model.tExam;
import cn.sh.ideal.model.tExamVo;

public interface IExamService {
    
    /**
     * 查询考试信息
     * @param params
     * @author chendi
     * @return
     */
    public List<tExamVo> selectExamVos(tExamVo tExamVo);
    
    /**
     * 添加考试
     * @param tExamVo
     * @return
     */
    public String addExam(tExamVo tExamVo);
    
    /**
     * 
     * @param pkAutoId
     * @return
     */
    public tExamVo selectByPKId(int pkAutoId);
    
    /**
     * 
     * @param examId
     * @return
     */
    public String updateExam(tExamVo tExamVo);
    
    /**
     * 
     * @param pkAutoId
     * @param examPaperId
     * @return
     */
    public String deleteExam(int pkAutoId);
    
    
    

	/*******
	 * 查询最近的待考试信息
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	public List queryExam(int userId);
	
	/*******
	 * 查询最近的已考试信息
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	public List queryExamOver(int userId);
	
   
    /**
     * 查选考试类型和对象是否存在
     * @param params
     * @author chendi
     * @return
     */
    public List<tExam> selectByCodeId(int id);
    
    /**
     * 根据考试名称查询某考试是否存在
     * @param examName
     * @return
     */
    public int selectByExamName(String examName);
}
