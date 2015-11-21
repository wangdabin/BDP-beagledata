package com.joe.monitor.manager;

import java.util.Collection;

import com.joe.monitor.Monitor;
import com.joe.monitor.ObjectID;
import com.sky.config.ConfigAble;

/**
 * 监控器管理者
 * @author qiaolong
 *
 */
public interface Manager extends ConfigAble{

	/**
	 * 启动所有的监控
	 */
	void startAll();
	
	/**
	 * 关闭所有监控
	 */
	void stopAll();
	
	/**
	 * 添加一个监控
	 * @param id
	 * @param monitor
	 */
	void addMonitor(ObjectID id,Monitor monitor);
	
	/**
	 * 得到所有的监控
	 * @return
	 */
	Collection<Monitor> getMonitors();
	
	/**
	 * 根据ObjectID得到指定的监控
	 * @param name
	 * @return
	 */
	Monitor getByObjectID(ObjectID name);
}
