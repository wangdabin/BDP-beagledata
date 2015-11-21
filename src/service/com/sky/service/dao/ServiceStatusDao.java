package com.sky.service.dao;

import org.springframework.stereotype.Repository;

import com.joe.core.dao.HibernateGenericDao;
import com.sky.service.vo.ServiceStatusVo;

@Repository("serviceStatusDao")
public class ServiceStatusDao extends HibernateGenericDao<ServiceStatusVo>{
	
}
