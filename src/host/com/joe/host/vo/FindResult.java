package com.joe.host.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author qiaolong
 *
 */
@XmlRootElement
public class FindResult {

	// 存在的hosts
	private List<Host> existHosts;
	// 不存在的hosts
	private List<Host> noExistHosts;

	/**
	 * @return the existHosts
	 */
	public List<Host> getExistHosts() {
		return existHosts;
	}

	/**
	 * @param existHosts the existHosts to set
	 */
	public void setExistHosts(List<Host> existHosts) {
		this.existHosts = existHosts;
	}

	/**
	 * @return the noExistHosts
	 */
	public List<Host> getNoExistHosts() {
		return noExistHosts;
	}

	/**
	 * @param noExistHosts the noExistHosts to set
	 */
	public void setNoExistHosts(List<Host> noExistHosts) {
		this.noExistHosts = noExistHosts;
	}
}
