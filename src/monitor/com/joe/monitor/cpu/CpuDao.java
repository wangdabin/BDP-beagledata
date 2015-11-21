package com.joe.monitor.cpu;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.joe.core.dao.HibernateGenericDao;


/**
 * 
 * @author LiuZhiJun
 *
 */
@Repository("cpuDao")
public class CpuDao extends HibernateGenericDao<CpuUnit>{

	public CpuUnit getLatestByIp(String hostName) {

		List<CpuUnit> cpuUnitList = findBy("ip", hostName, false, "timeStamp");
		return (cpuUnitList != null && cpuUnitList.size() > 0) ? cpuUnitList.get(0) : null;
	
	}
	/**
	 * 查看一段时间内cpu的使用情况
	 * @param hostName
	 * @param stime
	 * @param etime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CpuUnit> findCpuEcharts(String hostName, long stime, long etime) {
		String hql = "from CpuUnit where ip = ? and timeStamp >= ? and timeStamp <= ?";
		Query query = createQuery(hql);
		query.setString(0, hostName);
		query.setLong(1, stime);
		query.setLong(2, etime);
		return query.list();
	}

	
}
