package cn.sh.ideal.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.IAgentScoreDao;
import cn.sh.ideal.model.AgentScore;
import cn.sh.ideal.service.IAgentScoreService;
@Service("agentScoreService")
public class AgentScoreServiceimpl implements IAgentScoreService {
	
	@Resource
	private IAgentScoreDao agentScoredao;
		
	@Override
	public List<AgentScore> selectAgentscore(AgentScore agentscore) {	
		return agentScoredao.selectAgentscore(agentscore);
	}

	@Override
	public int selectAgentscorecount(AgentScore agentscore) {
		return agentScoredao.selectAgentscorecount(agentscore);
	}

	@Override
	public List<LinkedHashMap<Object, Object>> exportselectAgentscore(
			AgentScore agentscore) {
		return agentScoredao.exportselectAgentscore(agentscore);
	}

	@Override
	public List<AgentScore> nowselectAgentscore(AgentScore agentscore) {
		return agentScoredao.nowselectAgentscore(agentscore);
	}

	@Override
	public int nowselectAgentscorecount(AgentScore agentscore) {
		return  agentScoredao.nowselectAgentscorecount(agentscore);
	}

	@Override
	public List<AgentScore> getworkAgentscoreInfo(Map<String, String> map) {
		return agentScoredao.getworkAgentscoreInfo(map);
	}

	@Override
	public List<LinkedHashMap<Object, Object>> exportselectAgentscorenew(
			AgentScore agentscore) {
		return agentScoredao.exportselectAgentscorenew(agentscore);
	}

}
