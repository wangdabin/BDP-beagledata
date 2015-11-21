package com.sky.config;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BDPConfigVO {
	private String key;//属性名称,唯一主键
	private String name;//属性中文名称
	private String value;//属性值,多个值以","分割
	private String description;//描述
	private Boolean editable;//是否可以编辑
	private Boolean visible;//是否可见
	public String getDescription() {
		return description;
	}
	public Boolean getEditable() {
		return editable;
	}
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
