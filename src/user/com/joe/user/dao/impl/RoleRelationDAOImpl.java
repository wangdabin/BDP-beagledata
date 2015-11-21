package com.joe.user.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.joe.user.dao.RoleRelationDAO;
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
@Repository(value = "roleRelationDAO")
public class RoleRelationDAOImpl extends EntityDAOImpl<RoleRelation> implements RoleRelationDAO {

    @Override
    public void deleteAllByRoleId(int id) {
	String hql = "DELETE FROM RoleRelation rr where rr.role.id = ?";
	Query query = createQuery(hql, id);
	query.executeUpdate();
	
    }

}
