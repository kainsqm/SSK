package cn.sh.ideal.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.sh.ideal.controller.UserController;
import cn.sh.ideal.dao.IAgentWorkpaperDao;
import cn.sh.ideal.model.AgentWorkpaper;
import cn.sh.ideal.service.IAgentWorkpaperService;

@Service("agentWorkpaperService")
public class AgentWorkpaperServiceimpl implements IAgentWorkpaperService {
	private static Logger log = Logger.getLogger(AgentWorkpaperServiceimpl.class);
	@Resource
	private IAgentWorkpaperDao agentdao;
	
	@Override
	public boolean addagentWorkpaper(AgentWorkpaper agent) {
		boolean bl=true;	
		try {
			agentdao.addagentWorkpaper(agent);
		} catch (Exception e) {
			bl=false;
			log.error(e);
		}		
		return bl;
	}

	@Override
	public boolean updateagentWorkpaper(AgentWorkpaper agent) {
		boolean bl=true;
		try {
			agentdao.updateagentWorkpaper(agent);
		} catch (Exception e) {
			bl=false;
			log.error(e);
		}
		return bl;
	}

	@Override
	public List<AgentWorkpaper> listAgentworkaper(AgentWorkpaper agent) {	
		return agentdao.listAgentworkaper(agent);
	}

	@Override
	public AgentWorkpaper agentByid(Integer agentid) {
		AgentWorkpaper agent=agentdao.agentByid(agentid);
		return agent;
	}

	@Override
	public boolean delagentWorkpaper(Integer agentid) throws SQLException{
		boolean bl=true;
		try {
			agentdao.delagentWorkpaper(agentid);
			agentdao.updQC(agentid);
		} catch (Exception e) {
			bl=false;
			log.error(e);
			throw e;
		}
		return bl;
	}


	@Override
	public int agentcount(AgentWorkpaper agent) {
		return agentdao.agentcount(agent);
	}

	@Override
	public List<AgentWorkpaper> listzcAgentworkaper(AgentWorkpaper agent) {
		return agentdao.listzcAgentworkaper(agent);
	}

	@Override
	public int agentzccount(AgentWorkpaper agent) {
		
		return agentdao.agentzccount(agent);
	}

	@Override
	public AgentWorkpaper qcidbyagent(Integer agentid) {
		return agentdao.qcidbyagent(agentid);
	}

}
