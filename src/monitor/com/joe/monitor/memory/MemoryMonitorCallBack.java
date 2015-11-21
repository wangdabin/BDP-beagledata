package com.joe.monitor.memory;

import org.apache.commons.configuration.Configuration;

import com.joe.monitor.callback.url.AbstractURLCallBack;

/**
 * Memory监控回调
 * @author qiaolong
 *
 */
public class MemoryMonitorCallBack extends AbstractURLCallBack{

	public MemoryMonitorCallBack(Configuration conf) {
		super(conf);
	}
}
