package com.joe.user.dao.impl;

import com.joe.core.dao.HibernateGenericDao;
import com.joe.user.dao.EntityDAO;

/**
*
* @description 基础持久化类
*
* @function
*
* @author ZhouZH
*
*/
public class EntityDAOImpl<T> extends HibernateGenericDao<T> implements EntityDAO<T> {

}
