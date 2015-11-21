package com.joe.monitor.filesystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.sky.monitor.utils.MonitorUtils;

/**
 * 文件系统数据 集群文件系统的信息
 * 
 * @author LiuZhiJun
 * 
 */
@XmlRootElement
public class DiskCluster implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<DiskUnit> diskUnits = new ArrayList<DiskUnit>();

	private String clusterID;
	private String dirName;
	private String devName;
	private String total;
	private String free;
	private String used;
	private String avail;
	private String files;
	private String freefiles;
	private String diskReadBytes;
	private String diskWriteBytes;
	private String usedPercent;

	public String getClusterID() {
		return clusterID;
	}

	public void setClusterID(String clusterID) {
		this.clusterID = clusterID;
	}

	public List<DiskUnit> getDiskUnits() {
		return diskUnits;
	}

	public void setDiskUnits(List<DiskUnit> diskUnits) {
		this.diskUnits = diskUnits;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public void setFree(String free) {
		this.free = free;
	}

	public void setUsed(String used) {
		this.used = used;
	}

	public void setAvail(String avail) {
		this.avail = avail;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public void setFreefiles(String freefiles) {
		this.freefiles = freefiles;
	}

	public void setDiskReadBytes(String diskReadBytes) {
		this.diskReadBytes = diskReadBytes;
	}

	public void setDiskWriteBytes(String diskWriteBytes) {
		this.diskWriteBytes = diskWriteBytes;
	}

	public void setUsedPercent(String usedPercent) {
		this.usedPercent = usedPercent;
	}

	public String getTotal() {
		float total = 0;
		for (DiskUnit fsUnit : diskUnits) {
			total += fsUnit.getDiskInfo().getTotal();
		}

		this.total = MonitorUtils.DFormat(total);
		return this.total;
	}

	public String getFree() {
		float free = 0;
		for (DiskUnit fsUnit : diskUnits) {
			free += fsUnit.getFree();
		}
		this.free = MonitorUtils.DFormat(free);
		return this.free;
	}

	public String getUsed() {
		float used = 0;
		for (DiskUnit fsUnit : diskUnits) {
			used += fsUnit.getUsed();
		}
		this.used = MonitorUtils.DFormat(used);
		return this.used;
	}

	public String getAvail() {
		float avail = 0;
		for (DiskUnit fsUnit : diskUnits) {
			avail += fsUnit.getAvail();
		}
		this.avail = MonitorUtils.DFormat(avail / diskUnits.size());
		return this.avail;
	}

	public String getFiles() {
		float files = 0;
		for (DiskUnit fsUnit : diskUnits) {
			files += fsUnit.getFiles();
		}
		this.files = MonitorUtils.DFormat(files);
		return this.files;
	}

	public String getFreefiles() {
		float freeFile = 0;
		for (DiskUnit fsUnit : diskUnits) {
			freeFile += fsUnit.getFreefiles();
		}
		this.freefiles = MonitorUtils.DFormat(freeFile);
		return this.freefiles;
	}

	public String getDiskReadBytes() {
		float diskReadBytes = 0;
		for (DiskUnit fsUnit : diskUnits) {
			diskReadBytes += fsUnit.getDiskReadBytes();
		}
		this.diskReadBytes = MonitorUtils.DFormat(diskReadBytes);
		return this.diskReadBytes;
	}

	public String getDiskWriteBytes() {
		float diskWriteBytes = 0;
		for (DiskUnit fsUnit : diskUnits) {
			diskWriteBytes += fsUnit.getDiskWriteBytes();
		}
		this.diskWriteBytes = MonitorUtils.DFormat(diskWriteBytes);
		return this.diskWriteBytes;
	}

	public String getUsedPercent() {
		float usedPercent = 0;
		for (DiskUnit fsUnit : diskUnits) {
			usedPercent += fsUnit.getUsedPercent();
		}
		this.usedPercent = MonitorUtils.DFormat(usedPercent / diskUnits.size());
		return this.usedPercent;
	}

}