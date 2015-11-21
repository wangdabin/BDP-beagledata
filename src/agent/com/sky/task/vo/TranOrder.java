package com.sky.task.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * 
 * @author Joe
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TranOrder extends AbstractOrder implements Comparable<TranOrder> {
    	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 任务的类型

	public static final String TYPE_SHELL = "shell"; // 普通的shell类型
	public static final String TYPE_HTTP = "http"; // 执行http下载请求
	public static final String TYPE_HDFS = "hdfs"; // hdfs任务
	@XmlElement
	private String command;
	@XmlElementWrapper(name = "params")
	@XmlElement(name = "param")
	private String[] params;
	@XmlElement(name = "destHost")
	private String destHost;
	@XmlElement
	private String src; // 数据源 ，自己封装
	@XmlElement
	private String dest;// 目标目录
	@XmlElement
	private String serviceName; // 服务名称
	@XmlElement
	private String version; // 服务版本
	@XmlElement
	private Integer order;// 执行顺序
	@XmlElementWrapper(name = "coreFiles")
	@XmlElement(name = "core")
	private String[] coreFiles;// 文件
	@XmlElement
	private Double completion=0.00;// 完成进度
	@XmlElement
	private String type;
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return
	 */
	public String getCommand() {
		return this.command;
	}

	/**
	 * 
	 * @param command
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getParams() {
		return this.params;
	}

	/**
	 * 
	 * @param params
	 */
	public void setParams(String... params) {
		this.params = params;
	}

	public String getDestHost() {
		return destHost;
	}

	public void setDestHost(String destHost) {
		this.destHost = destHost;
	}

	/**
	 * @return the src
	 */
	public String getSrc() {
		return src;
	}

	/**
	 * @param src
	 *            the src to set
	 */
	public void setSrc(String src) {
		this.src = src;
	}

	/**
	 * @return the dest
	 */
	public String getDest() {
		return dest;
	}

	/**
	 * @param dest
	 *            the dest to set
	 */
	public void setDest(String dest) {
		this.dest = dest;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName
	 *            the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public int compareTo(TranOrder o) {
		return this.getOrder() - o.getOrder();
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String[] getCoreFiles() {
		return coreFiles;
	}

	public void setCoreFiles(String[] coreFiles) {
		this.coreFiles = coreFiles;
	}

	public Double getCompletion() {
		return completion;
	}

	public void setCompletion(Double completion) {
		this.completion = completion;
	}
}
