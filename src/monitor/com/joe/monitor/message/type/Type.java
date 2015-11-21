package com.joe.monitor.message.type;

public enum Type {
	host("主机"),
	service("服务"),
	application("应用");
	
	private String type;
	
	Type(String type) {
		this.type = type;
	}
	
	public String toString() {
		return type;
	}
}
