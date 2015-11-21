package com.joe.config.plugin.event;

import java.util.EventObject;

public class ConfigEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private int type;

	private String propertyName;

	private Object propertyValue;

	private boolean beforeUpdate;

	public ConfigEvent(Object source, int type, String propertyName,
			Object propertyValue, boolean beforeUpdate) {
		super(source);
		this.type = type;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.beforeUpdate = beforeUpdate;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Object getPropertyValue() {
		return propertyValue;
	}

	public int getType() {
		return type;
	}

	public boolean isBeforeUpdate() {
		return beforeUpdate;
	}
}
