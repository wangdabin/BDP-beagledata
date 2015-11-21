package com.joe.monitor.filesystem;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 单个磁盘的信息
 * 
 * @author liuzhijun
 * 
 */
@XmlRootElement
public class DiskUnit implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private DiskInfo diskInfo;
	private long free;
	private long used;
	private long avail;
	private long files;
	private long freefiles;
	private long diskReadBytes;
	private long diskWriteBytes;
	private double usedPercent;
	private String month;
	private String day;
	private String minute;
	private String hour;
	private long timeStamp;

	
	public DiskUnit() {
		super();
	}


	public DiskUnit(long id, DiskInfo diskInfo, long free, long used,
			long avail, long files, long freefiles, long diskReadBytes,
			long diskWriteBytes, double usedPercent, String month, String day,
			String minute, String hour, long timeStamp) {
		super();
		this.id = id;
		this.diskInfo = diskInfo;
		this.free = free;
		this.used = used;
		this.avail = avail;
		this.files = files;
		this.freefiles = freefiles;
		this.diskReadBytes = diskReadBytes;
		this.diskWriteBytes = diskWriteBytes;
		this.usedPercent = usedPercent;
		this.month = month;
		this.day = day;
		this.minute = minute;
		this.hour = hour;
		this.timeStamp = timeStamp;
	}

	
	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DiskInfo getDiskInfo() {
		return diskInfo;
	}

	public void setDiskInfo(DiskInfo diskInfo) {
		this.diskInfo = diskInfo;
	}

	public long getFree() {
		return free;
	}

	public void setFree(long free) {
		this.free = free;
	}

	public long getUsed() {
		return used;
	}

	public void setUsed(long used) {
		this.used = used;
	}

	public long getAvail() {
		return avail;
	}

	public void setAvail(long avail) {
		this.avail = avail;
	}

	public long getFiles() {
		return files;
	}

	public void setFiles(long files) {
		this.files = files;
	}

	public long getFreefiles() {
		return freefiles;
	}

	public void setFreefiles(long freefiles) {
		this.freefiles = freefiles;
	}

	public long getDiskReadBytes() {
		return diskReadBytes;
	}

	public void setDiskReadBytes(long diskReadBytes) {
		this.diskReadBytes = diskReadBytes;
	}

	public long getDiskWriteBytes() {
		return diskWriteBytes;
	}

	public void setDiskWriteBytes(long diskWriteBytes) {
		this.diskWriteBytes = diskWriteBytes;
	}

	public double getUsedPercent() {
		return usedPercent;
	}

	public void setUsedPercent(double usedPercent) {
		this.usedPercent = usedPercent;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public long getTimestamp() {
		return timeStamp;
	}

	public void setTimestamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

}
