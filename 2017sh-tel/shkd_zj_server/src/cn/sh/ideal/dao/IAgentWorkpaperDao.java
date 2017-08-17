package cn.sh.ideal.dao;

import java.util.List;

import cn.sh.ideal.model.AgentWorkpaper;

public interface IAgentWorkpaperDao {
		
			void addagentWorkpaper(AgentWorkpaper agent);
			void updateagentWorkpaper(AgentWorkpaper agent);
			List<AgentWorkpaper> listAgentworkaper(AgentWorkpaper agent);
			List<AgentWorkpaper> listzcAgentworkaper(AgentWorkpaper agent);
			int agentcount(AgentWorkpaper agent);
			int agentzccount(AgentWorkpaper agent);	
			AgentWorkpaper agentByid(Integer agentid);
			void delagentWorkpaper(Integer agentid);
			void updQC(Integer agentid);
			AgentWorkpaper qcidbyagent(Integer agentid);
}
