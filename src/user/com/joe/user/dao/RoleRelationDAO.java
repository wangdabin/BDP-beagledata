package com.joe.user.dao;

import com.joe.user.vo.Role;
import com.joe.user.vo.RoleRelation;

/**
*
* @description 角色权限关系持久化类
*
* @function
*
* @author ZhouZH
*
*/
public interface RoleRelationDAO extends EntityDAO<RoleRelation>{

    void deleteAllByRoleId(int id);

	
}
