package com.joe.monitor.vo;

import org.apache.log4j.Logger;

import com.joe.monitor.ObjectID;
import com.sky.monitor.utils.Constants;

/**
 * 
 * @author qiaolong
 *
 */
public class MonitorObjectID extends SkyObjectID{
	public static final Logger LOG = Logger.getLogger(MonitorObjectID.class);
	
	public static final String DOMAIN = Constants.NAME;
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static ObjectID getMonitorName(String name){
		return getMonitorName(LOG,DOMAIN,name);
	}
	
	/**
	 * cpu 监控的名称
	 * @return
	 */
	public static ObjectID getCpuMonitor(){
		return getMonitorName(MONITOR_NAME_CPU);
	}
	
	/**
	 * 内存的
	 * @return
	 */
	public static ObjectID getMemMonitor(){
		return getMonitorName(MONITOR_NAME_MEM);
	}
	
	/**
	 * 网络
	 * @return
	 */
	public static ObjectID getNetMonitor(){
		return getMonitorName(MONITOR_NAME_NET);
	}
	
	/**
	 * 磁盘
	 * @return
	 */
	public static ObjectID getDiskMonitor(){
		return getMonitorName(MONITOR_NAME_DISK);
	}
}
