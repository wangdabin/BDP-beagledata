package com.joe.monitor.filesystem;

import java.util.List;

import com.joe.core.service.EntityService;


public interface FileSystemService extends EntityService<DiskUnit>{

	public List<DiskUnit> getRealTimeData();
	
	public List<DiskInfo> getAllDiskInfo();
}
