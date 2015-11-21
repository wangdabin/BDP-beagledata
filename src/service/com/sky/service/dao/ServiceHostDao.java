package com.sky.service.dao;

import org.springframework.stereotype.Repository;

import com.joe.core.dao.HibernateGenericDao;
import com.sky.service.vo.ServiceHost;

@Repository("serviceHostDao")
public class ServiceHostDao extends HibernateGenericDao<ServiceHost>{
}
