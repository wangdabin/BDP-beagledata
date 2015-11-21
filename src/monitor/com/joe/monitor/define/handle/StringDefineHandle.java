package com.joe.monitor.define.handle;

import javax.management.monitor.Monitor;
import javax.management.monitor.StringMonitor;

import com.joe.monitor.define.Define;
import com.joe.monitor.define.StringDefine;

/**
 * 
 * 文本定义转换器
 * @author qiaolong
 *
 */
public class StringDefineHandle implements DefineHandle{

	@Override
	public Monitor convertTo(Define define) {
		StringDefine def = (StringDefine) define;
		StringMonitor monitor = new StringMonitor();
		monitor.setNotifyDiffer(def.getNotifyDiffer());
		monitor.setNotifyMatch(def.getNotifyMatch());
		monitor.setStringToCompare(def.getStringToCompare());
		monitor.setObservedAttribute(def.getObservedAttribute());
		monitor.setGranularityPeriod(def.granularityPeriod());
		return monitor;
	}
}
