package com.joe.monitor.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MemoryHost {

	private Map<String, MemoryUnit> memUnits = new HashMap<String, MemoryUnit>();
	private float total;
	private float ram;
	private float used;
	private float free;
	private float actualUsed;
	private float actualFree;
	private float usedPercent;
	private float freePercent;

	public Map<String, MemoryUnit> getMemUnits() {
		return memUnits;
	}

	public void setMemUnits(Map<String, MemoryUnit> memUnits) {
		this.memUnits = memUnits;
	}

	public float getTotal() {

		for (Entry<String, MemoryUnit> memUnit : memUnits.entrySet()) {
			this.total += memUnit.getValue().getTotal();
		}
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getRam() {
		for (Entry<String, MemoryUnit> memUnit : memUnits.entrySet()) {
			this.ram += memUnit.getValue().getRam();
		}
		return ram;
	}

	public void setRam(float ram) {
		this.ram = ram;
	}

	public float getUsed() {
		for (Entry<String, MemoryUnit> memUnit : memUnits.entrySet()) {
			this.used += memUnit.getValue().getUsed();
		}
		return used;
	}

	public void setUsed(float used) {
		this.used = used;
	}

	public float getFree() {
		for (Entry<String, MemoryUnit> memUnit : memUnits.entrySet()) {
			this.free += memUnit.getValue().getFree();
		}
		return free;
	}

	public void setFree(float free) {
		this.free = free;
	}

	public float getActualUsed() {
		for (Entry<String, MemoryUnit> memUnit : memUnits.entrySet()) {
			this.actualUsed += memUnit.getValue().getActualUsed();
		}
		return actualUsed;
	}

	public void setActualUsed(float actualUsed) {
		this.actualUsed = actualUsed;
	}

	public float getActualFree() {
		for (Entry<String, MemoryUnit> memUnit : memUnits.entrySet()) {
			this.actualFree += memUnit.getValue().getActualFree();
		}
		return actualFree;
	}

	public void setActualFree(float actualFree) {
		this.actualFree = actualFree;
	}

	public float getUsedPercent() {
		for (Entry<String, MemoryUnit> memUnit : memUnits.entrySet()) {
			this.usedPercent += memUnit.getValue().getUsedPercent();
		}
		return usedPercent / memUnits.size();
	}

	public void setUsedPercent(float usedPercent) {
		this.usedPercent = usedPercent;
	}

	public float getFreePercent() {
		for (Entry<String, MemoryUnit> memUnit : memUnits.entrySet()) {
			this.freePercent += memUnit.getValue().getFreePercent();
		}
		return freePercent / memUnits.size();
	}

	public void setFreePercent(float freePercent) {
		this.freePercent = freePercent;
	}

}
