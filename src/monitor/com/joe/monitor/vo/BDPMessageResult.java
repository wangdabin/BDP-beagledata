package com.joe.monitor.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class BDPMessageResult {

	private List<BDPMessage> result;

	public List<BDPMessage> getResult() {
		return result;
	}

	public void setResult(List<BDPMessage> result) {
		this.result = result;
	}
}
