package com.joe.monitor.init;

import org.apache.log4j.Logger;

import com.joe.core.init.AbstractInitAble;
import com.joe.core.init.annotation.Init;
import com.joe.monitor.manager.Manager;
import com.joe.monitor.manager.MonitorManager;

/**
 * 
 * 监控器初始化
 * @author qiaolong
 *
 */
@Init(name = "monitor",type="monitor")
public class MonitorInit extends AbstractInitAble{

	public static final Logger LOG = Logger.getLogger(MonitorInit.class);
	private Manager manager ;
	@Override
	public void doInit() {
		//在启动的之后,执行对应的监控器的初始化和启动
		LOG.debug("Monitor will init ");
		manager = MonitorManager.getInstance(getConf());
		LOG.debug("Will startall monitor");
		manager.startAll();
		LOG.debug("Monitor inited ");
	}
	
	@Override
	public void destroy() {
		LOG.debug("Monitor will destroy ");
		LOG.debug("Will stopall monitor");
		manager.stopAll();
		LOG.debug("Monitor destroyed");
	}
}
