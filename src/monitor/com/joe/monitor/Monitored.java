package com.joe.monitor;

import java.util.List;

/**
 * 
 * 被监控的一类对象，如CPU、内存、网络等都是被监控的一类对象，
 * 主机可以包括多个 Monitored
 * @author qiaolong
 *
 */
public interface Monitored{

	/**
	 * 添加监控
	 * @param montior
	 */
	void addMontior(Monitor montior);
	
	/**
	 * 删除监控
	 * @param montior
	 */
	void removeMontior(Monitor montior);
	
	/**
	 * 得到当前所有的监控
	 * @return
	 */
	List<Monitor> getMontiors();
	
	/**
	 * 得到监控对象的名称，也是唯一键
	 * @return
	 */
	ObjectID getObjectID();
	
	/**
	 * 得到要监控的对象
	 * @return
	 */
	Object getMBean();
}
