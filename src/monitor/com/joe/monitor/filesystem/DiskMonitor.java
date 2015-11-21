package com.joe.monitor.filesystem;

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
 * 一个Disk的监控器
 * @author qiaolong
 *
 */
@Monitor(name = DiskMonitor.NAME,type = DiskMonitor.NAME)
public class DiskMonitor extends AbstractMonitor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "disk";
	
	@Override
	public Define getMonitorDefine() {
		return new DiskDefine(getConf());
	}

	@Override
	public ObjectID getMonitorID() {
		return MonitorObjectID.getDiskMonitor();
	}

	@Override
	protected DefineHandle getMonitorDefineHandle() {
		return new GaugeDefineHandle();
	}

	@Override
	protected List<MonitorListener> initListeners() {
		List<MonitorListener> listeners = new ArrayList<MonitorListener>();
		listeners.add(new DiskListener(getConf()));
		return listeners;
	}
}
