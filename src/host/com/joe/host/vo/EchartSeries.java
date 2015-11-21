package com.joe.host.vo;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class EchartSeries {

	private String name;
	private List<String> data;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
}
