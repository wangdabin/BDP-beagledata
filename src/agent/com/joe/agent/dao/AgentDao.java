package com.joe.agent.dao;

import org.springframework.stereotype.Repository;

import com.joe.agent.vo.Agent;
import com.joe.core.dao.HibernateGenericDao;

@Repository("agentDao")
public class AgentDao extends HibernateGenericDao<Agent>{
}
