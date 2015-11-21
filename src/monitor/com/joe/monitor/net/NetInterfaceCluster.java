package com.joe.monitor.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.sky.monitor.utils.MonitorUtils;

/**
 * 网卡信息、接口数据、流量
 * 
 */
@XmlRootElement
public class NetInterfaceCluster implements Serializable {
	private static final long serialVersionUID = 1L;
	private String clusterID;
	private List<NetInterfaceUnit> netUnits = new ArrayList<NetInterfaceUnit>();
	private String rxBytes;
	private String txBytes;
	private String speed;

	public String getClusterID() {
		return clusterID;
	}

	public void setClusterID(String clusterID) {
		this.clusterID = clusterID;
	}

	public List<NetInterfaceUnit> getNetUnits() {
		return netUnits;
	}

	public void setNetUnits(List<NetInterfaceUnit> netUnits) {
		this.netUnits = netUnits;
	}

	public String getRxBytes() {
		double rxBytes = 0;
		for (NetInterfaceUnit netUnit : netUnits) {
			rxBytes += netUnit.getRxBytes();
		}
		this.rxBytes = MonitorUtils.DFormat(rxBytes);
		return this.rxBytes;
	}

	public void setRxBytes(String rxBytes) {
		this.rxBytes = rxBytes;
	}

	public String getTxBytes() {
		double txBytes = 0;
		for (NetInterfaceUnit netUnit : netUnits) {
			txBytes += netUnit.getTxBytes();
		}
		this.txBytes = MonitorUtils.DFormat(txBytes);
		return this.txBytes;
	}

	public void setTxBytes(String txBytes) {
		this.txBytes = txBytes;
	}

	public String getSpeed() {
		double speed = 0;
		for (NetInterfaceUnit netUnit : netUnits) {
			speed += netUnit.getSpeed();
		}
		this.speed = MonitorUtils.DFormat(speed);
		return this.speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

}