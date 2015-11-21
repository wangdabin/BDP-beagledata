package com.joe.monitor.memory;

import org.apache.commons.configuration.Configuration;

import com.joe.monitor.listener.AbstractListener;

/**
 * 
 * memory资源监听器
 * @author qiaolong
 *
 */
public class MemoryListener extends AbstractListener{

	/**
	 * 
	 */
	public MemoryListener(Configuration conf){
		super(conf);
		this.register(new MemoryMonitorCallBack(conf));
	}
}
