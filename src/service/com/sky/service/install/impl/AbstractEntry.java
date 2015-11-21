package com.sky.service.install.impl;

import java.util.Map;

import org.w3c.dom.Element;

import com.sky.service.install.Entry;

public abstract class AbstractEntry implements Entry{

	private String id;
	private String name;
	private String type;
	private String key;
	private String ok;
	private String error;
	private String service;
	
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOK() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public void parse(Map<String, Entry> entrys, Element element) {
		this.setId(element.getAttribute("id"));
		this.setName(element.getAttribute("name"));
		this.setType(element.getAttribute("type"));
		this.setKey(element.getAttribute("key"));
		this.setService(element.getAttribute("service"));
		this.setOk(element.getAttribute("ok"));
		this.setError(element.getAttribute("error"));	
		entrys.put(this.getId(), this);
	}
}
