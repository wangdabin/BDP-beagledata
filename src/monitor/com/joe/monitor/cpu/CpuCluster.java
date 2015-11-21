package com.joe.monitor.cpu;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.sky.monitor.utils.MonitorUtils;

/**
 * 集群的CPU信息
 * 
 * @author liuzhijun
 * 
 */
@XmlRootElement
public class CpuCluster implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private String clusterID;
	private long total;
	private String desc;
	private String user;
	private String sys;
	private String nice;
	private String idle;
	private String wait;
	private String irq;
	private String softIrq;
	private String stolen;
	private String combined;

	private Set<CpuUnit> cpuUnits = new HashSet<CpuUnit>();

	public long getTotal() {
		this.total = this.cpuUnits.size();
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public void setCpuUnits(Set<CpuUnit> cpuUnits) {
		this.cpuUnits = cpuUnits;
	}

	
	public Set<CpuUnit> getCpuUnits() {
		return cpuUnits;
	}

	public long getId() {
		return id;
	}

	public String getClusterID() {
		return clusterID;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUser() {
		float user = 0;
		for (CpuUnit cpuHost : cpuUnits) {
			user += cpuHost.getUser();
		}
		this.user = MonitorUtils.DFormat(user / cpuUnits.size());
		return this.user;
	}

	public String getSys() {
		float sys = 0;
		for (CpuUnit cpuHost : cpuUnits) {
			sys += cpuHost.getSys();
		}
		this.sys = MonitorUtils.DFormat(sys / cpuUnits.size());
		return this.sys;
	}

	public String getNice() {
		float nice = 0;
		for (CpuUnit cpuHost : cpuUnits) {
			nice += cpuHost.getNice();
		}
		this.nice = MonitorUtils.DFormat(nice / cpuUnits.size());
		return this.nice;
	}

	public String getIdle() {
		float idle = 0;
		for (CpuUnit cpuHost : cpuUnits) {
			idle += cpuHost.getIdle();
		}
		this.idle = MonitorUtils.DFormat(idle / cpuUnits.size());
		return this.idle;
	}

	public String getWait() {
		float wait = 0;
		for (CpuUnit cpuHost : cpuUnits) {
			wait += cpuHost.getWait();
		}
		this.wait = MonitorUtils.DFormat(wait / cpuUnits.size());
		return this.wait;
	}

	public String getIrq() {
		float irq = 0;
		for (CpuUnit cpuHost : cpuUnits) {
			irq += cpuHost.getIrq();
		}
		this.irq = MonitorUtils.DFormat(irq / cpuUnits.size());
		return this.irq;
	}

	public String getSoftIrq() {
		float softIrq = 0;
		for (CpuUnit cpuHost : cpuUnits) {
			softIrq += cpuHost.getSoftIrq();
		}
		this.softIrq = MonitorUtils.DFormat(softIrq / cpuUnits.size());
		return this.softIrq;
	}

	public String getStolen() {
		float stolen = 0;
		for (CpuUnit cpuHost : cpuUnits) {
			stolen += cpuHost.getStolen();
		}
		this.stolen = MonitorUtils.DFormat(stolen / cpuUnits.size());
		return this.stolen;
	}

	public String getCombined() {
		float combined = 0;
		for (CpuUnit cpuHost : cpuUnits) {
			combined += cpuHost.getCombined();
		}
		this.combined = MonitorUtils.DFormat(combined / cpuUnits.size());
		return this.combined;
	}

	// public List<CpuUnit> getCpuUnits() {
	// return cpuUnits;
	// }

	public void setId(long id) {
		this.id = id;
	}

	public void setClusterID(String clusterID) {
		this.clusterID = clusterID;
	}

	public void setUser(String user) {
		this.user = user;
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

}