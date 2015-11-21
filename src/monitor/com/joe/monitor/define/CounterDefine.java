package com.joe.monitor.define;

/**
 * 
 * 一个计数器监控的定义
 * @author qiaolong
 *
 */
public interface CounterDefine extends Define{

	/**
	 * 初始化数值
	 * @return
	 */
	Number getInitThreshold();
	
	/**
	 * 增长的offset
	 * @return
	 */
	Number getOffset();
	
	/**
	 * 是否通知
	 * @return
	 */
	boolean getNotify();
}
