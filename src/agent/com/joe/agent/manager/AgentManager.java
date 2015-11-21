package com.joe.agent.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.joe.agent.annotation.CallBack;
import com.joe.agent.callback.AgentCallBack;
import com.joe.agent.vo.Agent;
import com.joe.agent.vo.Status;
import com.joe.core.factory.AbstractFactory;
import com.joe.host.utils.HostUtils;
import com.joe.host.vo.Host;

/**
 * 管理所有的 agent 状态的callback类
 * @author qiaolong
 *
 */
public class AgentManager extends AbstractFactory<CallBack, AgentCallBack> implements AgentCallBack{

	private static final Logger LOG = Logger.getLogger(AgentManager.class);
	
	//其中key是 IP
	private Map<String,Agent> agents = Collections.synchronizedMap(new HashMap<String,Agent>());
	
	// 单例实体类
	private Set<AgentCallBack> singletonInstance = new HashSet<AgentCallBack>();
	
	private static AgentManager manager;
	
	private AgentManager(Configuration conf) {
		super(conf, LOG, CallBack.class);
	}

	public synchronized Set<AgentCallBack> getAllInstances() throws InstantiationException, IllegalAccessException{
		if(singletonInstance.isEmpty()){
			Collection<Class<AgentCallBack>> classes = this.getAll();
			for(Class<AgentCallBack> clazz : classes){
				System.out.println(clazz.getSimpleName());
				AgentCallBack callBack = clazz.newInstance();
				callBack.setConf(getConf());
				singletonInstance.add(callBack);
			}
		}
		return singletonInstance;
	}

	@Override
	public void doPing(String hostname,String ip) {
		this.log("ping", hostname, ip);
		try {
			Agent agent = agents.get(HostUtils.buildKey(hostname,ip));
			if(agent != null){
				agent.setStatus(Status.RUNNING);
			}else{
				LOG.error("Stop agent hostname = " + hostname + " and ip = " + ip + " not found!");
			}
			
			Set<AgentCallBack> callBacks = getAllInstances();
			for(AgentCallBack callBack : callBacks){
				callBack.doPing(hostname,ip);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void doStart(String hostname,String ip) {
		this.log("start", hostname, ip);
		try {
			Agent agent = initAgent(hostname, ip, Status.RUNNING);
			agents.put(HostUtils.buildKey(hostname,ip), agent);
			
			Set<AgentCallBack> callBacks = getAllInstances();
			for(AgentCallBack callBack : callBacks){
				callBack.doStart(hostname,ip);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void doStop(String hostname,String ip) {
		this.log("stop", hostname, ip);
		try {
			Agent agent = agents.get(HostUtils.buildKey(hostname,ip));
			if(agent != null){
				agent.setStatus(Status.STOPED);
			}else{
				LOG.error("Stop agent hostname = " + hostname + " and ip = " + ip + " not found!");
			}
			
			Set<AgentCallBack> callBacks = getAllInstances();
			for(AgentCallBack callBack : callBacks){
				callBack.doStop(hostname,ip);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public void doDelete(String hostname,String ip) {
		this.log("delete", hostname, ip);
		try {
			Agent agent = agents.remove(HostUtils.buildKey(hostname,ip));
			if(agent == null){
				LOG.error("Delete agent hostname = " + hostname + " and ip = " + ip + " not found!");
			}
			
			Set<AgentCallBack> callBacks = getAllInstances();
			for(AgentCallBack callBack : callBacks){
				callBack.doDelete(hostname,ip);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected String getName(CallBack ann) {
		return ann.name();
	}

	@Override
	protected String getType(CallBack ann) {
		return ann.type();
	}

	@Override
	protected boolean isEnable(CallBack ann) {
		return ann.enable();
	}
	
	/**
	 * 
	 * @return
	 */
	public Collection<Agent> getAllAgents(){
		return agents.values();
	}
	
	/**
	 * 
	 * @return
	 */
	public Collection<Agent> getAllStoppedAgents(){
		return getStatusAgents(Status.STOPED);
	}
	
	/**
	 * 
	 * @return
	 */
	public Collection<Agent> getAllRunningAgents(){
		return getStatusAgents(Status.RUNNING);
	}
	
	/**
	 * 检查agent是否在运行
	 * @param hostname
	 * @param ip
	 * @return
	 */
	public boolean checkRunning(String hostname,String ip){
		Agent agent = agents.get(HostUtils.buildKey(hostname,ip));
		if(agent != null){
			if(Status.RUNNING.equals(agent.getStatus())){
				return true;
			}
		}else{
			throw new RuntimeException("Hostname " + hostname + " ip " + ip + " not found !");
		}
		return false;
	}
	
	/**
	 * 检查agent是否在运行
	 * @param host
	 * @return
	 */
	public boolean checkRunning(Host host){
		return this.checkRunning(host.getName(), host.getIp());
	}
	
	/**
	 * 
	 * @param status
	 * @return
	 */
	private Collection<Agent> getStatusAgents(String status){
		List<Agent> agents = new ArrayList<Agent>();
		for(Agent agent : this.getAllAgents()){
			if(agent.getStatus().equalsIgnoreCase(status)){
				agents.add(agent);
			}
		}
		return agents;
	}
	
	/**
	 * 
	 * @param hostname
	 * @param ip
	 * @param status
	 * @return
	 */
	private Agent initAgent(String hostname,String ip,String status){
		Agent agent = new Agent();
		agent.setName(hostname);
		agent.setIp(ip);
		agent.setStatus(status);
		return agent;
	}
	
	/**
	 * 
	 * @param willDo
	 * @param hostname
	 * @param ip
	 */
	private void log(String willDo,String hostname,String ip){
		LOG.info("host name : " + hostname + ",ip : " + ip + " will do " + willDo);
	}

	/**
	 * 
	 * @param conf
	 * @return
	 */
	public synchronized static AgentManager getManager(Configuration conf){
		if(manager == null){
			manager = new AgentManager(conf);
		}
		return manager;
	}
}
