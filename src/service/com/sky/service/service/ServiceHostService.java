package com.sky.service.service;

import java.util.List;

import com.joe.core.service.EntityService;
import com.sky.service.vo.AllServiceSummary;
import com.sky.service.vo.ServiceHost;

public interface ServiceHostService extends EntityService<ServiceHost>{
	
	/**
	 * @Title: monitor
	 * @Description: 返回所有服务信息的信息
	 * @param host
	 * @return
	 */
	 AllServiceSummary findAllServiceSummary();
	 /**
	  * @Title: monitor
	  * @Description:获取每个主机的服务信息
	  * @param host
	  * @return
	  */
	 List<ServiceHost> findServiceByHost(String hostName);
}
