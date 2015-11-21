package com.joe.monitor;

import java.io.Serializable;
import java.util.List;

import com.joe.monitor.define.Define;
import com.joe.monitor.filter.MonitorFilter;
import com.joe.monitor.listener.MonitorListener;
import com.sky.config.ConfigAble;

/**
 * 
 * @author qiaolong
 *
 */
public interface Monitor extends Serializable,ConfigAble{
	
	/**
	 * 初始化
	 * @param server
	 */
	void init();
	
	/**
	 * 开始监控
	 */
	void start();
	
	/**
	 * 销毁
	 */
	void stop();
	
	/**
	 * 监控某个对象
	 * @param host
	 */
	void monitor(Monitored monitored);
	
	/**
	 * 不监控此对象
	 * @param monitored
	 */
	void remove(Monitored monitored);
	
	/**
	 * 删除此监控
	 */
	void remove();
	
	/**
	 * 得到所有被监控的列表
	 * @return
	 */
	List<Monitored> getAllMonitored();
	
	/**
	 * 监控的定义
	 * @return
	 */
	Define getMonitorDefine();
	
	/**
	 * 添加监控监听器
	 * @param monitorListener
	 */
	void addListener(MonitorListener monitorListener);
	
	/**
	 * 
	 * @param monitorListener
	 * @param filter
	 */
	void addListener(MonitorListener monitorListener,MonitorFilter filter);
	
	/**
	 * 
	 * @param monitorListener
	 * @param filter
	 * @param handBack
	 */
	void addListener(MonitorListener monitorListener,MonitorFilter filter,Object handBack);
	
	/**
	 * 监控的ID
	 * @return
	 */
	ObjectID getMonitorID();
}
