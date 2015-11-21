package com.sky.service.vo;

import java.util.List;

public class SubServices {

	private String subName;
	private int active;
	private int dead;
	
	private List<SubServiceDetails>  serviceStatus;

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

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

	public List<SubServiceDetails> getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(List<SubServiceDetails> serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
}
