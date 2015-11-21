package com.joe.user.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 认证结果
 * @author qiaolong
 *
 */
@XmlRootElement
public class AuthResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ret;
	private String msg;
	private String token;
	private String errcode;
	
	/**
	 * @return the ret
	 */
	public int getRet() {
		return ret;
	}
	/**
	 * @param ret the ret to set
	 */
	public void setRet(int ret) {
		this.ret = ret;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the errcode
	 */
	public String getErrcode() {
		return errcode;
	}
	/**
	 * @param errcode the errcode to set
	 */
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
}
