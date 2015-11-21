package com.joe.monitor.memory;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.joe.core.dao.HibernateGenericDao;

@Repository("memoryDao")
public class MemoryDao  extends HibernateGenericDao<MemoryUnit>{

	/**
	 * 根据ip获取当前主机最新的内存信息
	 * @param ip
	 * @return
	 */
	public MemoryUnit getLatestByIp(String ip){
		List<MemoryUnit> memoryUnitList = findBy("ip", ip, false, "timeStamp");
		return (memoryUnitList != null && memoryUnitList.size() > 0) ? memoryUnitList.get(0) : null;
	}
	/**
	 * 获取一段时间内内存的使用情况
	 * @param hostName
	 * @param stime
	 * @param etime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MemoryUnit> findCpuEcharts(String hostName, long stime,long etime) {
		String hql = "from MemoryUnit where ip = ? and timeStamp >= ? and timeStamp <= ?";
		Query query = createQuery(hql);
		query.setString(0, hostName);
		query.setLong(1, stime);
		query.setLong(2, etime);
		return query.list();
	
	};
}
