package com.joe.monitor.filesystem;

import org.springframework.stereotype.Repository;
import com.joe.core.dao.HibernateGenericDao;

@Repository("diskUnitDao")
public class DiskUnitDao extends HibernateGenericDao<DiskUnit> {
	
}
