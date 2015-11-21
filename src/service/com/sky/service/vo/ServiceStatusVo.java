package com.sky.service.vo;

import java.io.Serializable;

import com.joe.host.vo.Host;

public class ServiceStatusVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String serviceName;
	private String serviceVersion;
	private Integer serviceStatus = 0;
	private int hostId;
	private Host host;
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceVersion() {
		return serviceVersion;
	}
	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}
	public Integer getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(Integer serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public Host getHost() {
		return host;
	}
	public void setHost(Host host) {
		this.host = host;
	}
	public int getHostId() {
		return hostId;
	}
	public void setHostId(int hostId) {
		this.hostId = hostId;
	}
	
}
