package com.joe.host.service;

import com.joe.core.service.EntityService;
import com.joe.core.vo.Total;
import com.joe.host.init.Status;
import com.joe.host.vo.AllHostSummary;
import com.joe.host.vo.Echarts;
import com.joe.host.vo.FindResult;
import com.joe.host.vo.Host;
import com.joe.host.vo.HostRange;
import com.joe.host.vo.HostSummary;

public interface HostService extends EntityService<Host> {

	public Host findByHostName(String ip);

	public Total getTotal();

	/**
	 * 根据给定的区间查找hosts
	 * 
	 * @param hostRange
	 * @return
	 */
	FindResult find(HostRange hostRange);

	/**
	 * @Title: find
	 * @Description: 根据给定的集合查找hosts
	 * @param hosts
	 * @return FindResult
	 */
	FindResult find(Host[] hosts);

	/**
	 * @Title: monitor
	 * @Description: 返回安装的状态
	 * @param host
	 * @return 安装进度
	 */
	Status monitor(Host host);

	/**
	 * @Title: monitor
	 * @Description: 返回所有主机的所安装的服务信息
	 * @param host
	 * @return
	 */
	AllHostSummary findAllHostSummary();

	/**
	 * @Title: monitor
	 * @Description: 返回所有主机的所安装的服务信息
	 * @param host
	 * @return
	 */
	HostSummary findHostSummary(String hostName);

	/**
	 * @Title: monitor
	 * @Description: 查询某段时间内cpu的使用率
	 * @param host
	 * @return
	 */
	Echarts findCpuEcharts(String hostName, long stime, long etime);

	/**
	 * @Title: monitor
	 * @Description: 查询某段时间内cpu的使用率
	 * @param host
	 * @return
	 */
	Echarts findMemoryEcharts(String hostName, long stime, long etime);

	/**
	 * @Title: monitor
	 * @Description: 查询某段时间内cpu的使用率
	 * @param host
	 * @return
	 */
	Echarts findDiskEcharts(String hostName, long stime, long etime);

	/**
	 * @Title: monitor
	 * @Description: 查询某段时间内cpu的使用率
	 * @param host
	 * @return
	 */
	Echarts findNetEcharts(String hostName, long stime, long etime);

	/**
	 * 
	 * @Title: clusterTrust
	 * @Description: 集群进行互信
	 * @throws Exception 
	 */
	public void clusterTrust() throws Exception;

	/**
	 * @Title: sshKeygen
	 * @Description: 给对应集群上的主机生成钥匙
	 * @throws
	 */
	public void sshKeygen();

}
