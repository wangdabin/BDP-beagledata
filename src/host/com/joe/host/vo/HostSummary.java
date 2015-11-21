package com.joe.host.vo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 * @author lifei
 *
 */
@XmlRootElement
public class HostSummary {

	private long memoryTotal;
	private double memoryUsed;
	private double cpuUsed;
	private double ethUsed;
	private double emUsed;
	private List<HostDisk> diskList;
	public long getMemoryTotal() {
		return memoryTotal;
	}
	public void setMemoryTotal(long memoryTotal) {
		this.memoryTotal = memoryTotal;
	}
	public double getMemoryUsed() {
		return memoryUsed;
	}
	public void setMemoryUsed(double memoryUsed) {
		this.memoryUsed = memoryUsed;
	}
	public double getCpuUsed() {
		return cpuUsed;
	}
	public void setCpuUsed(double cpuUsed) {
		this.cpuUsed = cpuUsed;
	}
	public double getEthUsed() {
		return ethUsed;
	}
	public void setEthUsed(double ethUsed) {
		this.ethUsed = ethUsed;
	}
	public double getEmUsed() {
		return emUsed;
	}
	public void setEmUsed(double emUsed) {
		this.emUsed = emUsed;
	}
	public List<HostDisk> getDiskList() {
		return (diskList == null ? new ArrayList<HostDisk>() : diskList);
	}
	public void setDiskList(List<HostDisk> diskList) {
		this.diskList = diskList;
	}
}
