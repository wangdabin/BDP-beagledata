package com.joe.monitor.define.handle;

import javax.management.monitor.Monitor;

import com.joe.monitor.define.Define;

/**
 * 把监控的define转换成Monitor
 * @author qiaolong
 *
 */
public interface DefineHandle {

	/**
	 * 定义一个转换规则。
	 * @param define
	 * @return
	 */
	Monitor convertTo(Define define); 
}
