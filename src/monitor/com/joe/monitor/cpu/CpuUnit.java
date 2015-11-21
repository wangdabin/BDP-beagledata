package com.joe.monitor.cpu;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Index;

/**
 * 单个Cpu信息统计 CPU 处理器ID user 在internal时间段里，用户态的CPU时间（%） ，不包含 nice值为负 进程
 * dusr/dtotal*100 nice 在internal时间段里，nice值为负进程的CPU时间（%） dnice/dtotal*100 system
 * 在internal时间段里，核心时间（%） dsystem/dtotal*100 iowait 在internal时间段里，硬盘IO等待时间（%）
 * diowait/dtotal*100 irq 在internal时间段里，软中断时间（%） dirq/dtotal*100 soft
 * 在internal时间段里，软中断时间（%） dsoftirq/dtotal*100 idle
 * 在internal时间段里，CPU除去等待磁盘IO操作外的因为任何原因而空闲的时间闲置时间 （%） didle/dtotal*100 intr/s
 * 在internal时间段里，每秒CPU接收的中断的次数 dintr/dtotal*100
 * CPU总的工作时间=total_cur=user+system+nice+idle+iowait+irq+softirq
 * total_pre=pre_user+ pre_system+ pre_nice+ pre_idle+ pre_iowait+ pre_irq+
 * pre_softirq duser=user_cur – user_pre dtotal=total_cur-total_pre 其中_cur
 * 表示当前值，_pre表示interval时间前的值。上表中的所有值可取到两位小数点。
 * 
 * @author liuzhijun
 * 
 */
@XmlRootElement
public class CpuUnit implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String ip;
	private String cpuId;
	private double user;// 用户使用百分比
	private double sys;// 系统使用量百分比
	private double nice;// 改变过优先级的进程使用的cpu情况 百分比
	private double idle;// 空闲的Cpu情况百分比
	private double wait;// Cpu等待输入输出完成的比例
	private double irq;// 在Internat时间里，软中断时间 dirq/total *100
	private double softIrq;// 在Internat时间里，软中断时间 dsoftirq /total *100
	private double stolen;//
	private double combined;//
	private String desc;

	private long timeStamp;

	@Index(name = "cpuDayIndex")
	private String day;

	@Index(name = "cpuHourIndex")
	private String hour;

	@Index(name = "cpuMinuteIndex")
	private String minute;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCpuId() {
		return cpuId;
	}

	public void setCpuId(String cpuId) {
		this.cpuId = cpuId;
	}

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

	// public Host getHost() {
	// return host;
	// }
	//
	// public void setHost(Host host) {
	// this.host = host;
	// }

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public double getUser() {
		return user;
	}

	public void setUser(double user) {
		this.user = user;
	}

	public double getSys() {
		return sys;
	}

	public void setSys(double sys) {
		this.sys = sys;
	}

	public double getNice() {
		return nice;
	}

	public void setNice(double nice) {
		this.nice = nice;
	}

	public double getIdle() {
		return idle;
	}

	public void setIdle(double idle) {
		this.idle = idle;
	}

	public double getWait() {
		return wait;
	}

	public void setWait(double wait) {
		this.wait = wait;
	}

	public double getIrq() {
		return irq;
	}

	public void setIrq(double irq) {
		this.irq = irq;
	}

	public double getSoftIrq() {
		return softIrq;
	}

	public void setSoftIrq(double softIrq) {
		this.softIrq = softIrq;
	}

	public double getStolen() {
		return stolen;
	}

	public void setStolen(double stolen) {
		this.stolen = stolen;
	}

	public double getCombined() {
		return combined;
	}

	public void setCombined(double combined) {
		this.combined = combined;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
