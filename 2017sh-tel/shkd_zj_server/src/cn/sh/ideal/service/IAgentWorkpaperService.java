package cn.sh.ideal.service;

import java.sql.SQLException;
import java.util.List;

import cn.sh.ideal.model.AgentWorkpaper;

public interface IAgentWorkpaperService {

	boolean addagentWorkpaper(AgentWorkpaper agent);
	boolean updateagentWorkpaper(AgentWorkpaper agent);
	List<AgentWorkpaper> listAgentworkaper(AgentWorkpaper agent);
	List<AgentWorkpaper> listzcAgentworkaper(AgentWorkpaper agent);
	int agentcount(AgentWorkpaper agent);
	int agentzccount(AgentWorkpaper agent);	
	AgentWorkpaper agentByid(Integer agentid);
	boolean delagentWorkpaper (Integer agentid)throws SQLException;

	AgentWorkpaper qcidbyagent(Integer agentid);
}
