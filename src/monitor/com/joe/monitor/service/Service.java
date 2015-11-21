package com.joe.monitor.service;

import java.util.List;

import com.joe.core.version.Name;
import com.joe.monitor.Monitored;

/**
 * 被监控的服务
 * @author qiaolong
 *
 */
public interface Service extends Monitored,Name{

	/**
	 * 主机名
	 * @return
	 */
	String getHostName();
	
	/**
	 * IP地址
	 * @return
	 */
	String getIp();
	
	/**
	 * 获取安装路径
	 * @return
	 */
	String getPath();
	
	/**
	 * 添加子服务
	 * @param service
	 */
	void addSubService(Service service);
	
	/**
	 * 删除子服务
	 * @param service
	 */
	void removeSubService(Service service);
	
	/**
	 * 得到所有的子服务
	 * @return
	 */
	List<Service> getSubServices();
}
