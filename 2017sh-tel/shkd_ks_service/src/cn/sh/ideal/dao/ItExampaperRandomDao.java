package cn.sh.ideal.dao;

import cn.sh.ideal.model.tExampaperRandom;

public interface ItExampaperRandomDao {
    int deleteByPrimaryKey(Integer pkAutoId);

    int insert(tExampaperRandom record);

    int insertSelective(tExampaperRandom record);

    tExampaperRandom selectByPrimaryKey(Integer pkAutoId);

    int updateByPrimaryKeySelective(tExampaperRandom record);

    int updateByPrimaryKey(tExampaperRandom record);

	int deleteByExampaperId(Integer fkExampaperId);
}