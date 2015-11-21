package com.joe.core.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Bool implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean value;

	public Bool(){}
	
	public Bool(boolean value) {
		super();
		this.value = value;
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}
}
