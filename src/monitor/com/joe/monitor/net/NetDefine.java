package com.joe.monitor.net;

import org.apache.commons.configuration.Configuration;

import com.joe.monitor.define.impl.GaugeDefineImpl;

/**
 * 
 * 	private String observedAttribute; // 需要监控的属性
	private long granularityPeriod; //监控时间
	
 * 	private boolean notifyHigh = false;
	private boolean notifyLow = false;
	private Number highValue = INTEGER_ZERO;
	private Number lowValue = INTEGER_ZERO;
 * 定义监控的范围等信息
 * @author qiaolong
 *
 */
public class NetDefine extends GaugeDefineImpl{

	public NetDefine(Configuration conf) {
		super(conf);
		this.setObservedAttribute(conf.getString("com.sky.monitor.net.observedAttribute", "Use")); // 监控的字段
		this.setGranularityPeriod(conf.getLong("com.sky.monitor.net.granularityPeriod", 60000l)); // 默认一分钟 间隔时间
		this.setNotifyHigh(conf.getBoolean("com.sky.monitor.net.notifyHigh",true)); // 超过阀值时是否通知
		this.setNotifyHigh(conf.getBoolean("com.sky.monitor.net.notifyLow",false)); // 低于阀值时是否通知
		
		this.setHighValue(conf.getFloat("com.sky.monitor.net.highValue", 0.001f)); // 阀值
		this.setLowValue(conf.getFloat("com.sky.monitor.net.highValue", -0.01f)); // 阀值
	}
}
