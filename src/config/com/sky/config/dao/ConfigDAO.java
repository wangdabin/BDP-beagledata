package com.sky.config.dao;

import org.springframework.stereotype.Repository;

import com.joe.core.dao.HibernateGenericDao;
import com.sky.config.ConfigVO;

/**
 * 配置的DAO，用与配置
 * @author qiaolong
 *
 */
@Repository("configDAO")
public class ConfigDAO extends HibernateGenericDao<ConfigVO>{

}
