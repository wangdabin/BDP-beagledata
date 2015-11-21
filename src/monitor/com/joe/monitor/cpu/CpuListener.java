package com.joe.monitor.cpu;

import org.apache.commons.configuration.Configuration;

import com.joe.monitor.listener.AbstractListener;

/**
 * 
 * cpu资源监听器
 * @author qiaolong
 *
 */
public class CpuListener extends AbstractListener{

	/**
	 * 
	 */
	public CpuListener(Configuration conf){
		super(conf);
		this.register(new CpuMonitorCallBack(conf));
	}
}
