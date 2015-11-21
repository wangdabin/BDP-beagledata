package com.joe.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.joe.user.dao.RoleDAO;
import com.joe.user.vo.Role;

/**
*
* @description 角色持久化类，继承基类实现
*
* @function
*
* @author ZhouZH
*
*/
@Repository(value = "roleDAO")
public class RoleDAOImpl extends EntityDAOImpl<Role> implements RoleDAO {

	

}
