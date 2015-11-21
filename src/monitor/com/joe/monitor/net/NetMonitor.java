package com.joe.monitor.net;

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
 * 一个Net的监控器
 * @author qiaolong
 *
 */
@Monitor(name = NetMonitor.NAME,type = NetMonitor.NAME)
public class NetMonitor extends AbstractMonitor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "net";
	
	@Override
	public Define getMonitorDefine() {
		return new NetDefine(getConf());
	}

	@Override
	public ObjectID getMonitorID() {
		return MonitorObjectID.getNetMonitor();
	}

	@Override
	protected DefineHandle getMonitorDefineHandle() {
		return new GaugeDefineHandle();
	}

	@Override
	protected List<MonitorListener> initListeners() {
		List<MonitorListener> listeners = new ArrayList<MonitorListener>();
		listeners.add(new NetListener(getConf()));
		return listeners;
	}
}
