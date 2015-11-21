package com.sky.service.install.impl;

import org.w3c.dom.Element;

import com.sky.service.install.Option;
import com.sky.service.install.Step;

/**
 * 选项
 * 
 * @author qiaolong
 * 
 */
public class OptionImpl implements Option {

	private String id;
	private String name;
	private String value;
	private String ok;
	private String error;
	private Step step;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOk() {
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
	
	public Step getStep() {
		return step;
	}

	public void setStep(Step step) {
		this.step = step;
	}

	@Override
	public void parse(Element element) {
		this.setId(element.getAttribute("id"));
		this.setName(element.getAttribute("name"));
		this.setValue(element.getAttribute("value"));
		this.setOk(element.getAttribute("ok"));
		this.setError(element.getAttribute("error"));
	}
	
	public static final Option build(Element node){
		Option option = new OptionImpl();
		option.parse(node);
		return option;
	}
}
