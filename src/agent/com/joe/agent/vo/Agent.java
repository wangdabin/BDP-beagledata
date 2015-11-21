package com.joe.agent.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.joe.core.vo.AbstractEntity;
import com.joe.core.vo.Type;
import com.joe.host.init.Status;

@XmlRootElement
public class Agent extends AbstractEntity{
	
	private static final long serialVersionUID = -8314723001250769380L;

	private static final Type TYPE = Type.AGENT;
	
	private String ip; // ip地址
	private double installProgress = Status.INIT; //安装进度默认为初始化状态
	private String runStatus; //运行状态
	private Long startTime;//初始启动时间
	private String status;
	

	public double getInstallProgress() {
	    return installProgress;
	}

	public void setInstallProgress(double installProgress) {
	    this.installProgress = installProgress;
	}

	public Type getType() {
		return TYPE;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}
	public Long getStartTime() {
	    return startTime;
	}

	public void setStartTime(Long startTime) {
	    this.startTime = startTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
