package com.joe.monitor.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.joe.core.factory.AbstractFactory;
import com.joe.monitor.Monitor;
import com.joe.monitor.ObjectID;

/**
 * 
 * 监控器管理者
 * @author qiaolong
 *
 */
public class MonitorManager extends AbstractFactory<com.joe.monitor.annotation.Monitor, Monitor> implements Manager{

	public static final Logger LOG = Logger.getLogger(MonitorManager.class);
	
	private Map<ObjectID,Monitor> monitors = new HashMap<ObjectID,Monitor>();
	
	private MonitorManager(Configuration conf) {
		//这里将对应的含有Monitor的注解的类一起交给对应的MonitorManager
		super(conf, LOG, com.joe.monitor.annotation.Monitor.class);
		try {
			this.init();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void startAll() {
		for(Monitor monitor : monitors.values()){
			monitor.start();
		}
	}

	@Override
	public void stopAll() {
		for(Monitor monitor : monitors.values()){
			monitor.stop();
		}
	}

	public Collection<Monitor> getMonitors() {
		return monitors.values();
	}

	@Override
	public Monitor getByObjectID(ObjectID id) {
		return monitors.get(id);
	}

	@Override
	protected String getName(com.joe.monitor.annotation.Monitor ann) {
		return ann.name();
	}

	@Override
	protected String getType(com.joe.monitor.annotation.Monitor ann) {
		return ann.type();
	}

	@Override
	protected boolean isEnable(com.joe.monitor.annotation.Monitor ann) {
		return ann.enable();
	}

	@Override
	public void addMonitor(ObjectID id, Monitor monitor) {
		this.initMonitor(monitor);
		monitors.put(monitor.getMonitorID(), monitor);
	}
	
	private void init() throws InstantiationException, IllegalAccessException{
		Collection<Class<Monitor>> classes = this.getAll();
		for(Class<Monitor> clazz : classes){
			Monitor monitor = clazz.newInstance();
			initMonitor(monitor); //初始化 monitor
			monitors.put(monitor.getMonitorID(), monitor);
		}
	}
	
	private void initMonitor(Monitor monitor){
		monitor.setConf(getConf());
		monitor.init();
	}
	
	
	private static Manager manager = null;
	
	/**
	 * 得到一个监控管理者
	 * @param conf
	 * @return
	 */
	public synchronized static final Manager getInstance(Configuration conf){
		if(manager == null){
			manager = new MonitorManager(conf);
		}
		return manager;
	}
}
