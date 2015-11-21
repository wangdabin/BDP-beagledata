package com.joe.monitor.define;

import com.sky.config.ConfigAble;

/**
 * 监控定义
 * @author qiaolong
 *
 */
public interface Define extends ConfigAble{
	
	static final Integer INTEGER_ZERO = new Integer(0);
	/**
	 *  被监听者的属性
	 * @return
	 */
	String getObservedAttribute();
	
	/**
	 * 间隔时间
	 * @return
	 */
	long granularityPeriod();
}
