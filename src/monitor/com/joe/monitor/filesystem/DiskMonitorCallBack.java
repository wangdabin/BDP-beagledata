package com.joe.monitor.filesystem;

import org.apache.commons.configuration.Configuration;

import com.joe.monitor.callback.url.AbstractURLCallBack;

/**
 * Disk监控回调
 * @author qiaolong
 *
 */
public class DiskMonitorCallBack extends AbstractURLCallBack{

	public DiskMonitorCallBack(Configuration conf) {
		super(conf);
	}
}
