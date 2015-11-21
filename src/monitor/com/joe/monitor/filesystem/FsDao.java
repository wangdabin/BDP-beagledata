package com.joe.monitor.filesystem;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.joe.core.dao.HibernateGenericDao;

@Repository("fsDao")
public class FsDao  extends HibernateGenericDao<DiskUnit>{

	@SuppressWarnings("unchecked")
	public List<DiskUnit> findDiskEcharts(String hostName,String devName, long stime, long etime) {
		String hql = "from DiskUnit model where diskInfo.devName = ? and diskInfo.ip = ? and model.timestamp >= ? and model.timestamp <= ?";
		Query query = createQuery(hql);
		query.setString(0, devName);
		query.setString(1, hostName);
		query.setLong(2, stime);
		query.setLong(3, etime);
		return query.list();
	}
	

}
