package com.sky.service.vo;

import java.util.List;

public class ServiceDetails {
	
	private String name;
	
	private List<SubServices> subServices;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SubServices> getSubServices() {
		return subServices;
	}

	public void setSubServices(List<SubServices> subServices) {
		this.subServices = subServices;
	}

}
