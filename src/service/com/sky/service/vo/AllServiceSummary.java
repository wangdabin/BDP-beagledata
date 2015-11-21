package com.sky.service.vo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 * @author lifei
 *
 */
@XmlRootElement
public class AllServiceSummary {
	
	private int allActive = 0;
	private int allDead = 0;
	
	private List<ServiceSummary> serviceList = new LinkedList<ServiceSummary>();

	public int getAllActive() {
		return allActive;
	}

	public void setAllActive(int allActive) {
		this.allActive = allActive;
	}

	public int getAllDead() {
		return allDead;
	}

	public void setAllDead(int allDead) {
		this.allDead = allDead;
	}

	public List<ServiceSummary> getServiceList() {
		return (serviceList == null ? new LinkedList<ServiceSummary>() : serviceList);
	}

	public void setServiceList(List<ServiceSummary> serviceList) {
		this.serviceList = serviceList;
	}
}
