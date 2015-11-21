package com.joe.monitor.net;

import org.apache.commons.configuration.Configuration;

import com.joe.monitor.listener.AbstractListener;

/**
 * 
 * net资源监听器
 * @author qiaolong
 *
 */
public class NetListener extends AbstractListener{

	/**
	 * 
	 */
	public NetListener(Configuration conf){
		super(conf);
		this.register(new NetMonitorCallBack(conf));
	}
}
