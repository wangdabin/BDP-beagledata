package com.joe.monitor.filesystem;

import org.apache.commons.configuration.Configuration;

import com.joe.monitor.listener.AbstractListener;

/**
 * 
 * disk资源监听器
 * @author qiaolong
 *
 */
public class DiskListener extends AbstractListener{

	/**
	 * 
	 */
	public DiskListener(Configuration conf){
		super(conf);
		this.register(new DiskMonitorCallBack(conf));
	}
}
