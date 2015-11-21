package com.joe.monitor.net;

import org.apache.commons.configuration.Configuration;

import com.joe.monitor.callback.url.AbstractURLCallBack;

/**
 * Memory监控回调
 * @author qiaolong
 *
 */
public class NetMonitorCallBack extends AbstractURLCallBack{

	public NetMonitorCallBack(Configuration conf) {
		super(conf);
	}
}
