package com.joe.monitor.cpu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.sky.monitor.utils.MonitorUtils;

/**
 * 单个主机的Cpu的使用情况
 * 
 * @author liuzhijun
 * 
 */
@XmlRootElement
public class CpuHost implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<CpuUnit> cpuUnits = new ArrayList<CpuUnit>();

	private String ip;
	private String sys;
	private String nice;
	private String idle;
	private String wait;
	private String irq;
	private String softIrq;
	private String stolen;
	private String combined;

	public List<CpuUnit> getCpuUnits() {
		return cpuUnits;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setCpuUnits(List<CpuUnit> cpuUnits) {
		this.cpuUnits = cpuUnits;
	}

	public void setSys(String sys) {
		this.sys = sys;
	}

	public void setNice(String nice) {
		this.nice = nice;
	}

	public void setIdle(String idle) {
		this.idle = idle;
	}

	public void setWait(String wait) {
		this.wait = wait;
	}

	public void setIrq(String irq) {
		this.irq = irq;
	}

	public void setSoftIrq(String softIrq) {
		this.softIrq = softIrq;
	}

	public void setStolen(String stolen) {
		this.stolen = stolen;
	}

	public void setCombined(String combined) {
		this.combined = combined;
	}

	public String getSys() {
		float sys = 0;
		for (CpuUnit cpu : cpuUnits) {
			sys += cpu.getSys();
		}
		this.sys = MonitorUtils.DFormat(sys / cpuUnits.size());
		return this.sys;
	}

	public String getNice() {
		float nice = 0;
		for (CpuUnit cpu : cpuUnits) {
			nice += cpu.getNice();
		}
		this.nice = MonitorUtils.DFormat(nice / cpuUnits.size());
		return this.nice;
	}

	public String getIdle() {
		float idle = 0;
		for (CpuUnit cpu : cpuUnits) {
			idle += cpu.getIdle();
		}
		this.idle = MonitorUtils.DFormat(idle / cpuUnits.size());
		return this.idle;
	}

	public String getWait() {
		float wait = 0;
		for (CpuUnit cpu : cpuUnits) {
			wait += cpu.getWait();
		}
		this.wait = MonitorUtils.DFormat(wait / cpuUnits.size());
		return this.wait;
	}

	public String getIrq() {
		float irq = 0;
		for (CpuUnit cpu : cpuUnits) {
			irq += cpu.getIrq();
		}
		this.irq = MonitorUtils.DFormat(irq / cpuUnits.size());
		return this.irq;
	}

	public String getSoftIrq() {
		float softIrq = 0;
		for (CpuUnit cpu : cpuUnits) {
			softIrq += cpu.getSoftIrq();
		}
		this.softIrq = MonitorUtils.DFormat(softIrq / cpuUnits.size());
		return this.softIrq;
	}

	public String getStolen() {

		float stolen = 0;
		for (CpuUnit cpu : cpuUnits) {
			stolen += cpu.getStolen();
		}
		this.stolen = MonitorUtils.DFormat(stolen / cpuUnits.size());
		return this.stolen;
	}

	public String getCombined() {

		float combined = 0;
		for (CpuUnit cpu : cpuUnits) {
			combined += cpu.getCombined();
		}
		this.combined = MonitorUtils.DFormat(combined / cpuUnits.size());
		return this.combined;
	}

}
