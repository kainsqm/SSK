package cn.sh.ideal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sh.ideal.model.tExam;
import cn.sh.ideal.model.tExamVo;

public interface ItExamDao {
    int deleteByPrimaryKey(Integer pkAutoId);

    int insert(tExam record);

    int insertSelective(tExam record);

    tExam selectByPrimaryKey(Integer pkAutoId);

    int updateByPrimaryKeySelective(tExam record);

    int updateByPrimaryKey(tExam record);
    
    List<tExamVo> selectExamVos(tExamVo tExamVo);
    
    tExamVo selectByPKId(int pkAutoId);


	/*******
	 * 查询最近的待考试信息
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	public List queryExam(@Param("userId") int userId);
	
	/*******
	 * 查询最近的已考试信息
	 * @author niewenqiang
	 * 2017-4-12
	 * *******/
	public List queryExamOver(@Param("userId") int userId);
	

    List<tExam> selectByCodeId(Integer pkAutoId);
    
    /**
     * 判断试卷名称是否存在
     * @param examName
     * @return
     */
    int selectByExamName(String examName);

}