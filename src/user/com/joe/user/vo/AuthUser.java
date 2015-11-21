package com.joe.user.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 用于做认证的用户
 * @author qiaolong
 *
 */
@XmlRootElement
public class AuthUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	
	/**
	 * @return the j_username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the j_username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the j_password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the j_password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
