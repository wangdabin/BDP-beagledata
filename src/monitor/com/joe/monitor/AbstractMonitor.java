package com.joe.monitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;

import com.joe.core.utils.CoreConfigUtils;
import com.joe.monitor.cpu.CpuMonitor;
import com.joe.monitor.define.handle.DefineHandle;
import com.joe.monitor.filter.MonitorFilter;
import com.joe.monitor.listener.MonitorListener;
import com.joe.monitor.manager.MonitorManager;
import com.joe.monitor.vo.MonitorObjectID;
import com.sky.config.Configed;
import com.sky.monitor.jmx.MBeans;


/**
 * 
 * @author qiaolong
 *
 */
public abstract class AbstractMonitor extends Configed implements Monitor {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Monitored> monitoreds = new ArrayList<Monitored>();
	private javax.management.monitor.Monitor monitor;
	
	@Override
	public void init() {
		this.initMonitor(); //初始化 Monitor
		MBeans.register(getMonitorID(), monitor); // 注册监控
	}

	@Override
	public void addListener(MonitorListener monitorListener) {
		this.addListener(monitorListener, null, null);
	}
	
	@Override
	public void addListener(MonitorListener monitorListener,
			MonitorFilter filter) {
		this.addListener(monitorListener, filter, null);
	}

	@Override
	public void addListener(MonitorListener monitorListener,
			MonitorFilter filter, Object handBack) {
		monitor.addNotificationListener(monitorListener, filter, handBack);
	}

	public List<Monitored> getAllMonitored(){
		return monitoreds;
	}
	
	@Override
	public void start() {
		this.monitor.start();
	}

	@Override
	public void stop() {
		monitor.stop();
	}

	@Override
	public void remove() {
		if(monitor.isActive()){
			stop();
		}
		monitoreds.clear();
		MBeans.unregister(getMonitorID());
		monitor = null;
	}
	
	@Override
	public void monitor(Monitored monitored) {
		MBeans.register(monitored.getObjectID(), monitored.getMBean());
		monitor.addObservedObject(monitored.getObjectID());
		monitored.addMontior(this);
		monitoreds.add(monitored);
		
	}
	
	@Override
	public void remove(Monitored monitored) {
		monitor.removeObservedObject(monitored.getObjectID());// 从监控器中删除
		MBeans.unregister(monitored.getObjectID()); // 从server中注销
		monitoreds.remove(monitored);// 从列表中删除
		monitored.removeMontior(this); // 从Monitored 中删除自己
	}

	/**
	 * 
	 * @return
	 */
	protected abstract DefineHandle getMonitorDefineHandle();
	
	/**
	 * 初始化自身的监控
	 * @return
	 */
	protected abstract List<MonitorListener> initListeners();
	
	private void initMonitor(){
		if(this.monitor == null){
			this.monitor = getMonitorDefineHandle().convertTo(getMonitorDefine());
		}
		List<MonitorListener> linsteners = this.initListeners();
		if(linsteners != null && !linsteners.isEmpty()){
			for(MonitorListener listener : linsteners){
				this.addListener(listener);
			}
		}
	}
}
