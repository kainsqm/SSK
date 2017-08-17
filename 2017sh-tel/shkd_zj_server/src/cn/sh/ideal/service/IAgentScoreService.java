package cn.sh.ideal.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.sh.ideal.model.AgentScore;

public interface IAgentScoreService {
	List<AgentScore> selectAgentscore(AgentScore agentscore);
	
	int selectAgentscorecount(AgentScore agentscore);
	
	List<LinkedHashMap<Object, Object>> exportselectAgentscore(AgentScore agentscore);
	
List<AgentScore>  nowselectAgentscore(AgentScore agentscore);
	
	int nowselectAgentscorecount(AgentScore agentscore);
	
	List<AgentScore> getworkAgentscoreInfo(Map<String,String> map);
	
	List<LinkedHashMap<Object, Object>> exportselectAgentscorenew(AgentScore agentscore);
}
