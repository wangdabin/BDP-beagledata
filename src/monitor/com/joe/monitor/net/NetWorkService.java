package com.joe.monitor.net;

import java.util.List;

import com.joe.core.service.EntityService;


public interface NetWorkService extends EntityService<NetInterfaceUnit>{

	/**
	 * 
	 * @return
	 */
	public List<NetInterfaceUnit> getRealTimeData();

	/**
	 * 根据Ip获取到所有的网卡信息
	 * @param ip
	 * @return
	 */
	public List<NetInfo> findByIp(String ip);
	
	/**
	 * 根据网卡获取所有的网卡流量
	 * @param netInfo
	 * @return
	 */
	public List<NetInterfaceUnit> findByNetInfo(NetInfo netInfo);
	
	/**
	 * 得到指定网卡的最新流量
	 * @param netInfo
	 * @return
	 */
	public NetInterfaceUnit getRealTimeData(NetInfo netInfo);
}
