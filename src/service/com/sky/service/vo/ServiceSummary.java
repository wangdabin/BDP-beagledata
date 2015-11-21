package com.sky.service.vo;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 * @author lifei
 *
 */
@XmlRootElement
public class ServiceSummary {

	public static final int STATUS_ACTIVE = 1;
	private int active = 0;
	private int dead = 0;
	private String serviceName;
	private int status = 0;
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public int getDead() {
		return dead;
	}
	public void setDead(int dead) {
		this.dead = dead;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
