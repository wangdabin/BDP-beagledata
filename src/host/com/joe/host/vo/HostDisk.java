package com.joe.host.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author lifei
 *
 */
@XmlRootElement
public class HostDisk {

	private double diskUsed;
	private long diskTotal;
	private String diskName;
	
	public double getDiskUsed() {
		return diskUsed;
	}
	public void setDiskUsed(double diskUsed) {
		this.diskUsed = diskUsed;
	}
	public long getDiskTotal() {
		return diskTotal;
	}
	public void setDiskTotal(long diskTotal) {
		this.diskTotal = diskTotal;
	}
	public String getDiskName() {
		return diskName;
	}
	public void setDiskName(String diskName) {
		this.diskName = diskName;
	}
}
