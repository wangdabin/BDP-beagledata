package com.joe.host.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author qiaolong
 *
 */
@XmlRootElement
public class HostRange implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String startHost;
	private String stopHost;
	private String user;
	private String password;
	
	/**
	 * @return the startHost
	 */
	public String getStartHost() {
		return startHost;
	}
	/**
	 * @param startHost the startHost to set
	 */
	public void setStartHost(String startHost) {
		this.startHost = startHost;
	}
	/**
	 * @return the stopHost
	 */
	public String getStopHost() {
		return stopHost;
	}
	/**
	 * @param stopHost the stopHost to set
	 */
	public void setStopHost(String stopHost) {
		this.stopHost = stopHost;
	}
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
