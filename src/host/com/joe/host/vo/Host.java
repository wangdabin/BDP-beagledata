package com.joe.host.vo;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.joe.core.vo.AbstractEntity;

/**
 * 服务器
 * 
 * @author Joe
 * 
 */
@XmlRootElement
@JsonAutoDetect
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Host extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String FIELD_IP = "ip";
	public static final String STATUS_ACTIVE = "1";

	private boolean hasSystem = true;// 是否已经安装操作系统
	private boolean chkIpExist = false; // 检查Ip是否存在

	private String ip; // ip地址
	private String root; // root 用户
	private String rootPass;// root passWord
	private String status;
	private String name;

	// private System system; //操作系统

	public Host() {
	}

	public Host(String ip, String name) {
		super();
		this.ip = ip;
		this.name = name;
	}

	/**
	 * @return the hasSystem
	 */
	public boolean isHasSystem() {
		return hasSystem;
	}

	/**
	 * @param hasSystem
	 *            the hasSystem to set
	 */
	public void setHasSystem(boolean hasSystem) {
		this.hasSystem = hasSystem;
	}

	/**
	 * @return the chkIpExist
	 */
	public boolean isChkIpExist() {
		return chkIpExist;
	}

	/**
	 * @param chkIpExist
	 *            the chkIpExist to set
	 */
	public void setChkIpExist(boolean chkIpExist) {
		this.chkIpExist = chkIpExist;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the root
	 */
	public String getRoot() {
		return root;
	}

	/**
	 * @param root
	 *            the root to set
	 */
	public void setRoot(String root) {
		this.root = root;
	}

	/**
	 * @return the rootPass
	 */
	public String getRootPass() {
		return rootPass;
	}

	/**
	 * @param rootPass
	 *            the rootPass to set
	 */
	public void setRootPass(String rootPass) {
		this.rootPass = rootPass;
	}

	/**
	 * @param the
	 *            status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param the
	 *            name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	// /**
	// * @return the system
	// */
	// public System getSystem() {
	// return system;
	// }
	//
	// /**
	// * @param system the system to set
	// */
	// public void setSystem(System system) {
	// this.system = system;
	// }
}