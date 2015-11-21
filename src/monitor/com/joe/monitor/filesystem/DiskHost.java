package com.joe.monitor.filesystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.sky.monitor.utils.MonitorUtils;

/**
 * 记录集群中每台机器的文件系统的情况
 * 
 * @author LiuZhiJun
 * 
 */
@XmlRootElement
public class DiskHost implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String ip;
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
	private List<DiskUnit> disks = new ArrayList<DiskUnit>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public List<DiskUnit> getDisks() {
		return disks;
	}

	public void setDisks(List<DiskUnit> disks) {
		this.disks = disks;
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
		for (DiskUnit dUnit : disks) {
			total += dUnit.getDiskInfo().getTotal();
		}
		this.total = MonitorUtils.DFormat(total);
		return this.total;
	}

	public String getFree() {
		float free = 0;
		for (DiskUnit dUnit : disks) {
			free += dUnit.getFree();
		}
		this.free = MonitorUtils.DFormat(free);
		return this.free;
	}

	public String getUsed() {
		float used = 0;
		for (DiskUnit dUnit : disks) {
			used += dUnit.getUsed();
		}
		this.used = MonitorUtils.DFormat(used);
		return this.used;
	}

	public String getAvail() {
		float avail = 0;
		for (DiskUnit dUnit : disks) {
			avail += dUnit.getAvail();
		}
		this.avail = MonitorUtils.DFormat(avail);
		return this.avail;
	}

	public String getFiles() {
		float files = 0;
		for (DiskUnit dUnit : disks) {
			files += dUnit.getFiles();
		}
		this.files = MonitorUtils.DFormat(files);
		return this.files;
	}

	public String getFreefiles() {
		float freeFiles = 0;
		for (DiskUnit dUnit : disks) {
			freeFiles += dUnit.getFreefiles();
		}
		this.freefiles = MonitorUtils.DFormat(freeFiles);
		return this.freefiles;
	}

	public String getDiskReadBytes() {
		float diskReadBytes = 0;
		for (DiskUnit dUnit : disks) {
			diskReadBytes += dUnit.getDiskReadBytes();
		}
		this.diskReadBytes = MonitorUtils.DFormat(diskReadBytes);
		return this.diskReadBytes;
	}

	public String getDiskWriteBytes() {
		float diskWriteBytes = 0;
		for (DiskUnit dUnit : disks) {
			diskWriteBytes += dUnit.getDiskWriteBytes();
		}
		this.diskWriteBytes = MonitorUtils.DFormat(diskWriteBytes);
		return this.diskWriteBytes;
	}

	public String getUsedPercent() {
		float usedPercent = 0;
		for (DiskUnit dUnit : disks) {
			usedPercent += dUnit.getUsedPercent();
		}
		this.usedPercent = MonitorUtils.DFormat(usedPercent / disks.size());
		return this.usedPercent;
	}

}
