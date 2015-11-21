package com.joe.monitor.net;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.joe.host.vo.Host;
import com.sky.monitor.utils.MonitorUtils;

@XmlRootElement
public class NetInterfaceHost {

	private Host host;
	private List<NetInterfaceUnit> netUnits = new ArrayList<NetInterfaceUnit>();
	private String rxBytes;
	private String txBytes;
	private String speed;

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public List<NetInterfaceUnit> getNetUnits() {
		return netUnits;
	}

	public void setNetUnits(List<NetInterfaceUnit> netUnits) {
		this.netUnits = netUnits;
	}

	public Host getHosts() {
		return host;
	}

	public void setHosts(Host hosts) {
		this.host = hosts;
	}

	public String getRxBytes() {
		float rxBytes = 0;
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
		float txByTes = 0;
		for (NetInterfaceUnit netUnit : netUnits) {
			txBytes += netUnit.getTxBytes();
		}
		this.txBytes = MonitorUtils.DFormat(txByTes);
		return this.txBytes;
	}

	public void setTxBytes(String txBytes) {
		this.txBytes = txBytes;
	}

	public String getSpeed() {
		float speed = 0;
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
