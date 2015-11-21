package com.joe.monitor.cpu;

import org.apache.commons.configuration.Configuration;

import com.joe.monitor.callback.url.AbstractURLCallBack;

/**
 * Cpu监控回调
 * @author qiaolong
 *
 */
public class CpuMonitorCallBack extends AbstractURLCallBack{

	public CpuMonitorCallBack(Configuration conf) {
		super(conf);
	}
}
