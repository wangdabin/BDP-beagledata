package com.joe.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.joe.user.dao.UserDAO;
import com.joe.user.vo.User;

/**
*
* @description 用户持久化类，继承基类实现
*
* @function
*
* @author ZhouZH
*
*/
@Repository(value = "userDAO")
public class UserDAOImpl extends EntityDAOImpl<User> implements UserDAO {

	
}
