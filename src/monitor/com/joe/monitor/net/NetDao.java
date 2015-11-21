package com.joe.monitor.net;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.joe.core.dao.HibernateGenericDao;

@Repository("netDao")
public class NetDao  extends HibernateGenericDao<NetInterfaceUnit> {

	@SuppressWarnings("unchecked")
	public List<NetInterfaceUnit> findNetEcharts(String hostName, String name,long stime, long etime) {
		String hql = "from NetInterfaceUnit where netInfo.ip = ? and netInfo.name = ? and timeStamp >= ? and timeStamp <= ?";
		Query query = createQuery(hql);
		query.setString(0, hostName);
		query.setString(1, name);
		query.setLong(2, stime);
		query.setLong(3, etime);
		return query.list();
	}

}