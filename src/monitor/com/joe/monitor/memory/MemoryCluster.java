package com.joe.monitor.memory;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.sky.monitor.utils.MonitorUtils;

/**
 * 内存数据
 * 
 * 集群的内存信息
 */
@XmlRootElement
public class MemoryCluster {
	private Set<MemoryUnit> units = new HashSet<MemoryUnit>();
	private String total;
	private String ram;
	private String used;
	private String free;
	private String actualUsed;
	private String actualFree;
	private String usedPercent;
	private String freePercent;

	public Set<MemoryUnit> getUnits() {
		return units;
	}

	public void setUnits(Set<MemoryUnit> units) {
		this.units = units;
	}

	public String getTotal() {
		float total = 0;
		for (MemoryUnit unit : units) {
			total += unit.getTotal();
		}
		this.total = MonitorUtils.DFormat(total);
		return this.total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getRam() {
		float ram = 0;
		for (MemoryUnit unit : units) {
			ram += unit.getRam();
		}
		this.ram = MonitorUtils.DFormat(ram);
		return this.ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getUsed() {
		float used = 0;
		for (MemoryUnit unit : units) {
			used += unit.getUsed();
		}
		this.used = MonitorUtils.DFormat(used / units.size());
		return this.used;
	}

	public void setUsed(String used) {
		this.used = used;
	}

	public String getFree() {
		float free = 0;
		for (MemoryUnit unit : units) {
			free += unit.getFree();
		}
		this.free = MonitorUtils.DFormat(free / units.size());
		return this.free;
	}

	public void setFree(String free) {
		this.free = free;
	}

	public String getActualUsed() {
		float actualUsed = 0;
		for (MemoryUnit unit : units) {
			actualUsed += unit.getActualUsed();
		}
		this.actualUsed = MonitorUtils.DFormat(actualUsed / units.size());
		return this.actualUsed;
	}

	public void setActualUsed(String actualUsed) {
		this.actualUsed = actualUsed;
	}

	public String getActualFree() {
		float actualFree = 0;
		for (MemoryUnit unit : units) {
			actualFree += unit.getActualFree();
		}
		this.actualFree = MonitorUtils.DFormat(actualFree / units.size());
		return this.actualFree;
	}

	public void setActualFree(String actualFree) {
		this.actualFree = actualFree;
	}

	public String getUsedPercent() {
		float usedPercent = 0;
		for (MemoryUnit unit : units) {
			usedPercent += unit.getUsedPercent();
		}
		this.usedPercent = MonitorUtils.DFormat(usedPercent / units.size());
		return this.usedPercent;
	}

	public void setUsedPercent(String usedPercent) {
		this.usedPercent = usedPercent;
	}

	public String getFreePercent() {
		float freePercent = 0;
		for (MemoryUnit unit : units) {
			this.freePercent += unit.getFreePercent();
		}
		this.freePercent = MonitorUtils.DFormat(freePercent / units.size());
		return this.freePercent;
	}

	public void setFreePercent(String freePercent) {
		this.freePercent = freePercent;
	}

}