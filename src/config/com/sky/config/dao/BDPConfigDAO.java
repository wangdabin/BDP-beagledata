package com.sky.config.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.joe.core.dao.HibernateGenericDao;
import com.sky.config.BDPConfigVO;
/**
 * @ClassName: BDPConfigDAO
 * @Description: BDPConfig数据库操作
 * @author WDB
 * @date 2015-4-29 下午2:43:20
 */
@Repository("bdpConfigDAO")
public class BDPConfigDAO extends HibernateGenericDao<BDPConfigVO>{

	@SuppressWarnings("unchecked")
	public BDPConfigVO findByKey(String key) {
		String hql = "FROM BDPConfigVO model WHERE model.key = ?";
		Query query = createQuery(hql, key);
		List<BDPConfigVO> list = query.list();
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
}
