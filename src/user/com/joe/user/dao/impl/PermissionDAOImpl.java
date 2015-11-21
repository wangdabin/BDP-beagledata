package com.joe.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.joe.user.dao.PermissionDAO;
import com.joe.user.vo.Permission;

/**
*
* @description 权限持久化类，基础基类实现
*
* @function
*
* @author ZhouZH
*
*/
@Repository(value = "permissionDAO")
public class PermissionDAOImpl extends EntityDAOImpl<Permission> implements PermissionDAO{

	

}
