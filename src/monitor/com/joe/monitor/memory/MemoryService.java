package com.joe.monitor.memory;

import java.util.List;

import com.joe.core.service.EntityService;


public interface MemoryService extends EntityService<MemoryUnit>{

	public List<MemoryUnit> getAllMemInfoGroupByHost();
}
