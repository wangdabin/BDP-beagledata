package com.joe.host.dao;

import org.springframework.stereotype.Repository;

import com.joe.core.dao.HibernateGenericDao;
import com.joe.host.vo.Host;

@Repository("hostDAO")
public class HostDAO extends HibernateGenericDao<Host> {

}
