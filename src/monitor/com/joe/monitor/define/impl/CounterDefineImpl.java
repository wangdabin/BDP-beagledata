package com.joe.monitor.define.impl;

import org.apache.commons.configuration.Configuration;

import com.joe.monitor.define.AbstractMonitorDefine;
import com.joe.monitor.define.CounterDefine;

/**
 * 
 * @author qiaolong
 *
 */
public class CounterDefineImpl extends AbstractMonitorDefine implements CounterDefine{

	private Number initThreshold = INTEGER_ZERO;
	private Number offset = INTEGER_ZERO;
	private boolean notify = true;
	
	public CounterDefineImpl(Configuration conf) {
		super(conf);
	}

	@Override
	public Number getInitThreshold() {
		return initThreshold;
	}
	
	@Override
	public Number getOffset() {
		return offset;
	}

	@Override
	public boolean getNotify() {
		return notify;
	}

	public void setInitThreshold(Number initThreshold) {
		this.initThreshold = initThreshold;
	}

	public void setOffset(Number offset) {
		this.offset = offset;
	}

	public void setNotify(boolean notify) {
		this.notify = notify;
	}
}
