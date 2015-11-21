package com.joe.host.monitor;
import javax.management.MalformedObjectNameException;
import com.joe.agent.annotation.CallBack;
import com.joe.agent.callback.BasicCallBack;
import com.joe.host.vo.Host;
import com.joe.monitor.Monitor;
import com.joe.monitor.ObjectID;
import com.joe.monitor.cpu.CpuMonitored;
import com.joe.monitor.filesystem.DiskMonitored;
import com.joe.monitor.manager.MonitorManager;
import com.joe.monitor.memory.MemoryMonitored;
import com.joe.monitor.net.NetMonitored;
import com.joe.monitor.vo.MonitorObjectID;

/**
 * 主机agent监控。。
 * 
 * @author qiaolong
 *
 */
@CallBack(name = "monitorHost",type="monitorHost")
public class HostAgentHandle extends BasicCallBack { //implements MonitorHandle{

//	private Manager manager;
	//TODO  进行资源监控的一部分...
	@Override
	public void doStart(String hostname, String ip) {
		Host h = new Host();
		h.setName(hostname);
		h.setIp(ip);
		try {
			CpuMonitored cpuMonitored = CpuMonitored.getInstance(getConf(), "monitor", "cpu", h);
			MonitorManager mm = (MonitorManager) MonitorManager.getInstance(getConf());
			ObjectID cpuOid = MonitorObjectID.getCpuMonitor();
			Monitor cpumonitor = mm.getByObjectID(cpuOid);
			cpumonitor.monitor(cpuMonitored);
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		
		try {
			DiskMonitored diskMonitored = DiskMonitored.getInstance(getConf(), "monitor", "disk", h);
			ObjectID diskOid = MonitorObjectID.getDiskMonitor();
			MonitorManager mm = (MonitorManager) MonitorManager.getInstance(getConf());
			Monitor diskmonitor = mm.getByObjectID(diskOid);
			diskmonitor.monitor(diskMonitored);
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		
		try {
			MemoryMonitored memoryMonitored = MemoryMonitored.getInstance(getConf(),"monitor", "memory", h);
			ObjectID memoryOid = MonitorObjectID.getMemMonitor();
			MonitorManager mm = (MonitorManager) MonitorManager.getInstance(getConf());
			Monitor memroyMonitor = mm.getByObjectID(memoryOid);
			memroyMonitor.monitor(memoryMonitored);
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		try {
			NetMonitored netMonitored = NetMonitored.getInstance(getConf(), "monitor", "net", h);
			ObjectID netOid = MonitorObjectID.getNetMonitor();
			MonitorManager mm = (MonitorManager) MonitorManager.getInstance(getConf());
			Monitor netMonitor = mm.getByObjectID(netOid);
			netMonitor.monitor(netMonitored);
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void doPing(String hostname, String ip) {
	}

	@Override
	public void doDelete(String hostname, String ip) {
	}

	@Override
	public void doStop(String hostname, String ip) {
	}

//	@Override
//	public void setManager(Manager manager) {
//		this.manager = manager;
//	}
//
//	@Override
//	public void monitor(Host host) {
//		
//	}
}