package com.joe.monitor.memory;

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
 * 一个memroy的监控器
 * @author qiaolong
 *
 */
@Monitor(name = MemoryMonitor.NAME,type = MemoryMonitor.NAME)
public class MemoryMonitor extends AbstractMonitor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "memory";
	
	@Override
	public Define getMonitorDefine() {
		return new MemoryDefine(getConf());
	}

	@Override
	public ObjectID getMonitorID() {
		return MonitorObjectID.getMemMonitor();
	}

	@Override
	protected DefineHandle getMonitorDefineHandle() {
		return new GaugeDefineHandle();
	}

	@Override
	protected List<MonitorListener> initListeners() {
		List<MonitorListener> listeners = new ArrayList<MonitorListener>();
		listeners.add(new MemoryListener(getConf()));
		return listeners;
	}
}
