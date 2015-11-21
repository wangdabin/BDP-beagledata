package com.joe.monitor.define;


/**
 * 一个文本监控器的定义
 * @author qiaolong
 *
 */
public interface StringDefine extends Define{

	/**
	 * 需要比较的字符串
	 * @return
	 */
	String getStringToCompare();
	
	/**
	 * 等相同的时候是否通知
	 * @return
	 */
	boolean getNotifyMatch();
	
	/**
	 * 不相同的时候是否通知
	 * @return
	 */
	boolean getNotifyDiffer();
}
