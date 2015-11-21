package com.joe.monitor.memory;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Index;

/**
 * 单个内存的信息
 * 
 * @author liuzhijun
 * 
 */
@XmlRootElement
public class MemoryUnit implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private String ip;
	private String name;
	private long total;
	private long ram;
	private long used;
	private long free;
	private long actualUsed;
	private long actualFree;
	private double usedPercent;
	private double freePercent;

	private long timeStamp;

	@Index(name = "memDayIndex")
	private String day;
	@Index(name = "memHourIndex")
	private String hour;
	@Index(name = "memMinuteIndex")
	private String minute;

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getRam() {
		return ram;
	}

	public void setRam(long ram) {
		this.ram = ram;
	}

	public long getUsed() {
		return used;
	}

	public void setUsed(long used) {
		this.used = used;
	}

	public long getFree() {
		return free;
	}

	public void setFree(long free) {
		this.free = free;
	}

	public long getActualUsed() {
		return actualUsed;
	}

	public void setActualUsed(long actualUsed) {
		this.actualUsed = actualUsed;
	}

	public long getActualFree() {
		return actualFree;
	}

	public void setActualFree(long actualFree) {
		this.actualFree = actualFree;
	}

	public double getUsedPercent() {
		return usedPercent;
	}

	public void setUsedPercent(double usedPercent) {
		this.usedPercent = usedPercent;
	}

	public double getFreePercent() {
		return freePercent;
	}

	public void setFreePercent(double freePercent) {
		this.freePercent = freePercent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (timeStamp ^ (timeStamp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemoryUnit other = (MemoryUnit) obj;
		if (id != other.id)
			return false;
		if (timeStamp != other.timeStamp)
			return false;
		return true;
	}

}
