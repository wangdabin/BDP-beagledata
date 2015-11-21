package com.joe.agent.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joe.agent.dao.AgentDao;
import com.joe.agent.service.AgentService;
import com.joe.agent.vo.Agent;

@Service("agentService")
@Transactional
public class AgentServiceImpl implements AgentService {

	@Resource(name="agentDao")
	private AgentDao agentDao;
	
	@Override
	public void add(Agent instance) {
		agentDao.save(instance);
	}

	@Override
	public Agent get(Serializable id) {
		return agentDao.get(id);
	}

	@Override
	public List<Agent> getAll() {
		return agentDao.getAll();
	}
	
	@Override
	public void remove(Agent transientObject) {
		agentDao.remove(transientObject);
	}

	@Override
	public void removeById(Serializable id) {
		agentDao.removeById(id);
	}

	@Override
	public Agent update(Agent t) {
		agentDao.saveOrUpdate(t);
		return t;
	}

	public AgentDao getAgentDao() {
		return agentDao;
	}

	public void setAgentDao(AgentDao agentDao) {
		this.agentDao = agentDao;
	}

	@Override
	public void addBatch(List<Agent> instanceBatch) {
	}

	@Override
	public void updateStatus(String ip, String status,String comment) {
		//持久化对象
		Agent agent = getAgentByIp(ip);
		
		if(agent!=null) {
			
			//运行状态
			if("run".equals(comment)) {
				agent.setRunStatus(status);
			} 
			//安装状态
			else {
				agent.setInstallProgress(Double.parseDouble(status));
			}
			
			//定时更新任务安装进度 Or 运行状态 ： 心跳
			agentDao.saveOrUpdate(agent);
		}
	}

	/**
	 * 跟新安装进度
	 * @param ip
	 * @param progress
	 */
	public void updateInstallProgress(String ip,double progress) {
		updateStatus(ip,String.valueOf(progress),"install");
	}
	
	/**
	 * 由于一台主机只会安装一个agent客户端,所以具有ip唯一性
	 */
	@Override
	public Agent getAgentByIp(String ip) {
		//持久化对象
		Agent agent = agentDao.findUniqueBy(Agent.class, "ip", ip);
		return agent ;
	}

	/**
	 * <p>Title: addStartTime</p>
	 * <p>Description: 添加对应的agent启动时间</p>
	 * @param ip
	 * @param startTime
	 * @see com.joe.agent.service.AgentService#addStartTime(java.lang.String, long)
	 */
	@Override
	public void addStartTime(String ip, long startTime) {
	  //持久化对象
	  Agent agent = getAgentByIp(ip);
	  if(agent!=null) {
	      agent.setStartTime(startTime);
	  }
	  agentDao.saveOrUpdate(agent);
	}

}
