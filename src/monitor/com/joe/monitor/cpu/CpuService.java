package com.joe.monitor.cpu;

import java.util.List;

import com.joe.core.service.EntityService;


public interface CpuService extends EntityService<CpuUnit>{
	public List<CpuUnit> doRealTimeMonitor();
}
