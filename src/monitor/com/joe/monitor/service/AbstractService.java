package com.joe.monitor.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;

import com.joe.monitor.AbstractMontiored;

/**
 * 
 * 抽象服务
 * @author qiaolong
 *
 */
public abstract class AbstractService extends AbstractMontiored implements Service{

	public AbstractService(Configuration conf) {
		super(conf);
	}

	private String hostName; // 主机名称
	private String ip; // 服务IP
	private String path;// 服务安装目录
	
	private String name; // 服务名称
	private String version; // 服务版本号
	
	private List<Service> subServices = new ArrayList<Service>();
	
	@Override
	public void addSubService(Service service) {
		this.subServices.add(service);
	}
	
	@Override
	public void removeSubService(Service subService) {
		if(subServices.contains(subService)){
//			subService.remove();
			this.subServices.remove(subService);
		}else{
			throw new RuntimeException("The service " + getName() + " not contains subService " + subService.getName());
		}
	}
	
	@Override
	public List<Service> getSubServices() {
		return subServices;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
