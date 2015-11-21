package com.joe.monitor.net;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.joe.core.dao.HibernateGenericDao;

@Repository("netInfoDao")
public class NetInfoDao  extends HibernateGenericDao<NetInfo> {

	/**
	 * 查看有效的网卡信息
	 * @param hostName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NetInfo> findAvailableNet(String hostName) {
		String hql = "from NetInfo where name !=  'lo' and ip = ?";
		return createQuery(hql).setString(0, hostName).list();
	}

	
}