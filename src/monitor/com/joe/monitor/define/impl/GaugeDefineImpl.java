package com.joe.monitor.define.impl;

import org.apache.commons.configuration.Configuration;

import com.joe.monitor.define.AbstractMonitorDefine;
import com.joe.monitor.define.GaugeDefine;

/**
 * 
 * @author qiaolong
 *
 */
public class GaugeDefineImpl extends AbstractMonitorDefine implements GaugeDefine{

	private boolean notifyHigh = false;
	private boolean notifyLow = false;
	private Number highValue = INTEGER_ZERO;
	private Number lowValue = INTEGER_ZERO;
	
	public GaugeDefineImpl(Configuration conf) {
		super(conf);
	}

	@Override
	public boolean notifyHigh() {
		return notifyHigh;
	}

	@Override
	public boolean notifyLow() {
		return notifyLow;
	}

	@Override
	public Number getHighValue() {
		return highValue;
	}

	@Override
	public Number getLowValue() {
		return lowValue;
	}

	public void setNotifyHigh(boolean notifyHigh) {
		this.notifyHigh = notifyHigh;
	}

	public void setNotifyLow(boolean notifyLow) {
		this.notifyLow = notifyLow;
	}

	public void setHighValue(Number highValue) {
		this.highValue = highValue;
	}

	public void setLowValue(Number lowValue) {
		this.lowValue = lowValue;
	}
}
