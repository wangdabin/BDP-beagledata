package com.joe.host.jmx;

import org.apache.log4j.Logger;

import com.joe.host.utils.HostUtils;
import com.joe.monitor.ObjectID;
import com.joe.monitor.vo.SkyObjectID;

/**
 * 主机的Object
 * @author qiaolong
 *
 */
public class HostObjectName extends SkyObjectID{

	public static final Logger LOG = Logger.getLogger(HostObjectName.class);
	
	public static final String DOMAIN = HostUtils.NAME;
	
	public static final String KEY_IP = "ip";
	public static final String KEY_HOSTNAME = "name";
	

	
	/**
	 * 
	 * @param ip
	 * @param hostname
	 * @return
	 */
	public static ObjectID getObjectName(String type,String ip,String hostname){
		TableBuilder builder = TableBuilder.create(type).put(KEY_IP, ip).put(KEY_HOSTNAME, hostname);
		return getObjectNameNoThrow(LOG,DOMAIN, builder.build());
	}
	
//	/**
//	 * 
//	 * @param name
//	 * @return
//	 */
//	public static ObjectName getMonitorName(String name){
//		return getMonitorName(LOG,DOMAIN,name);
//	}
//	
//	/**
//	 * cpu 监控的名称
//	 * @return
//	 */
//	public static ObjectName getCpuMonitor(){
//		return getMonitorName(MONITOR_NAME_CPU);
//	}
//	
//	/**
//	 * 内存的
//	 * @return
//	 */
//	public static ObjectName getMemMonitor(){
//		return getMonitorName(MONITOR_NAME_MEM);
//	}
//	
//	/**
//	 * 网络
//	 * @return
//	 */
//	public static ObjectName getNetMonitor(){
//		return getMonitorName(MONITOR_NAME_NET);
//	}
//	
//	/**
//	 * 磁盘
//	 * @return
//	 */
//	public static ObjectName getDiskMonitor(){
//		return getMonitorName(MONITOR_NAME_DISK);
//	}
}
