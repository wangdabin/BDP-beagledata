package com.joe.monitor.net;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 单独网卡的信息
 * 
 * @author liuzhijun
 * 
 */

@XmlRootElement
public class NetInterfaceUnit implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private NetInfo netInfo; // 网卡
	private long rxBytes;
	private long txBytes;
	private long speed;

	private long timeStamp;
	private String day;
	private String minute;
	private String hour;

	public NetInterfaceUnit(long id, NetInfo netInfo, long rxBytes,
			long txBytes, long speed, long timeStamp, String day,
			String minute, String hour) {
		super();
		this.id = id;
		this.netInfo = netInfo;
		this.rxBytes = rxBytes;
		this.txBytes = txBytes;
		this.speed = speed;
		this.timeStamp = timeStamp;
		this.day = day;
		this.minute = minute;
		this.hour = hour;
	}

	public NetInterfaceUnit() {
		super();
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

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRxBytes() {
		return rxBytes;
	}

	public void setRxBytes(long rxBytes) {
		this.rxBytes = rxBytes;
	}

	public long getTxBytes() {
		return txBytes;
	}

	public void setTxBytes(long txBytes) {
		this.txBytes = txBytes;
	}

	public long getSpeed() {
		return speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	/**
	 * @return the netInfo
	 */
	public NetInfo getNetInfo() {
		return netInfo;
	}

	/**
	 * @param netInfo
	 *            the netInfo to set
	 */
	public void setNetInfo(NetInfo netInfo) {
		this.netInfo = netInfo;
	}

	@Override
	public String toString() {
		return "NetInterfaceUnit [id=" + id + ", netInfo=" + netInfo
				+ ", rxBytes=" + rxBytes + ", txBytes=" + txBytes + ", speed="
				+ speed + ", timeStamp=" + timeStamp + ", day=" + day
				+ ", minute=" + minute + ", hour=" + hour + "]";
	}

}
