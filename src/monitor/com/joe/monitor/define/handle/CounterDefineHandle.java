package com.joe.monitor.define.handle;

import javax.management.monitor.CounterMonitor;
import javax.management.monitor.Monitor;

import com.joe.monitor.define.CounterDefine;
import com.joe.monitor.define.Define;

/**
 * 
 * 计数器定义转换器
 * @author qiaolong
 *
 */
public class CounterDefineHandle implements DefineHandle{

	@Override
	public Monitor convertTo(Define define) {
		CounterDefine def = (CounterDefine) define;
		CounterMonitor monitor = new CounterMonitor();
		monitor.setNotify(def.getNotify());
		monitor.setInitThreshold(def.getInitThreshold());
		monitor.setOffset(def.getOffset());
		monitor.setObservedAttribute(def.getObservedAttribute());
		monitor.setGranularityPeriod(def.granularityPeriod());
		return monitor;
	}
}
