package com.joe.monitor.define.impl;

import org.apache.commons.configuration.Configuration;

import com.joe.monitor.define.AbstractMonitorDefine;
import com.joe.monitor.define.StringDefine;

/**
 * 
 * @author qiaolong
 *
 */
public class StringDefineImpl extends AbstractMonitorDefine implements StringDefine{

	private String stringToCompare = "";
	private boolean notifyMatch = false;
	private boolean notifyDiffer = false;
	
	public StringDefineImpl(Configuration conf) {
		super(conf);
	}

	@Override
	public String getStringToCompare() {
		return stringToCompare;
	}

	@Override
	public boolean getNotifyMatch() {
		return notifyMatch;
	}

	@Override
	public boolean getNotifyDiffer() {
		return notifyDiffer;
	}

	public void setStringToCompare(String stringToCompare) {
		this.stringToCompare = stringToCompare;
	}

	public void setNotifyMatch(boolean notifyMatch) {
		this.notifyMatch = notifyMatch;
	}

	public void setNotifyDiffer(boolean notifyDiffer) {
		this.notifyDiffer = notifyDiffer;
	}
}
