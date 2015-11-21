package com.joe.monitor.cpu;

import java.util.ArrayList;
import java.util.List;

import com.joe.monitor.AbstractMonitor;
import com.joe.monitor.ObjectID;
import com.joe.monitor.annotation.Monitor;
import com.joe.monitor.define.Define;
import com.joe.monitor.define.handle.DefineHandle;
import com.joe.monitor.define.handle.GaugeDefineHandle;
import com.joe.monitor.listener.MonitorListener;
import com.joe.monitor.vo.MonitorObjectID;

/**
 * 一个CPU的监控器
 * @author qiaolong
 *
 */
//这里加入注解是为了让对应的Monitor类可以找到这个类
@Monitor(name = CpuMonitor.NAME,type = CpuMonitor.NAME)
public class CpuMonitor extends AbstractMonitor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "cpu";
	
	@Override
	public Define getMonitorDefine() {
		return new CpuDefine(getConf());
	}

	@Override
	public ObjectID getMonitorID() {
		return MonitorObjectID.getCpuMonitor();
	}

	@Override
	protected DefineHandle getMonitorDefineHandle() {
		return new GaugeDefineHandle();
	}

	@Override
	protected List<MonitorListener> initListeners() {
		List<MonitorListener> listeners = new ArrayList<MonitorListener>();
		listeners.add(new CpuListener(getConf()));
		return listeners;
	}
}
