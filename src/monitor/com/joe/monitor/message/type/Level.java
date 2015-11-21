package com.joe.monitor.message.type;

public enum Level {
	
	error("ERROR"),
	info("INFO"),
	warning("WARNING"),
	debug("DEBUG");
	
	private String level;
	
	Level(String level) {
		this.level = level;
	}
	
	public String toString() {
		return level;
	}
	
}
