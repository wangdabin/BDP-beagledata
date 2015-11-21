package com.joe.monitor.define.handle;

import javax.management.monitor.GaugeMonitor;
import javax.management.monitor.Monitor;

import com.joe.monitor.define.GaugeDefine;
import com.joe.monitor.define.Define;
import com.joe.monitor.define.handle.DefineHandle;

/**
 * 
 * 拥有上下阀值的转换器
 * 
 * @author qiaolong
 *
 */
public class GaugeDefineHandle implements DefineHandle{

	@Override
	public Monitor convertTo(Define define) {
		GaugeDefine def = (GaugeDefine) define;
		GaugeMonitor monitor = new GaugeMonitor();
		monitor.setNotifyHigh(def.notifyHigh());
		monitor.setNotifyLow(def.notifyLow());
		monitor.setThresholds(def.getHighValue(), def.getLowValue());
		monitor.setObservedAttribute(def.getObservedAttribute());
		monitor.setGranularityPeriod(def.granularityPeriod());
		monitor.setDifferenceMode(true);
		return monitor;
	}
}
