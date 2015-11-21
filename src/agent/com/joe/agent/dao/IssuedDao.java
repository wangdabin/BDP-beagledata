package com.joe.agent.dao;

import org.springframework.stereotype.Repository;

import com.joe.core.dao.HibernateGenericDao;
import com.sky.task.vo.Task;

@Repository("issuedDao")
public class IssuedDao extends HibernateGenericDao<Task>{
}
