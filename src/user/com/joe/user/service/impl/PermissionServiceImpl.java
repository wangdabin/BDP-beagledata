package com.joe.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joe.user.dao.PermissionDAO;
import com.joe.user.service.PermissionService;
import com.joe.user.vo.Permission;

/**
 * 
 * 权限数据服务层
 * 
 * @author Joe
 * 
 */
@Service("permissionService")
@Transactional(rollbackFor = RuntimeException.class)
public class PermissionServiceImpl extends EntityServiceImpl implements
		PermissionService {

	@Resource(name = "permissionDAO")
	private PermissionDAO permissiondao = null;

	public PermissionServiceImpl() {

	}

	@Override
	public boolean NewPermission(Permission permission) {
		if (permission == null)
			return false;
		try {
			// 验证名称是否重复
			Permission tmp = this.GetPermissionByName(permission.getUrl());
			if (tmp == null) {
				// 保存
				permissiondao.saveOrUpdate(permission);
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean ModifyPermission(Permission permission) {
		if (permission == null)
			return false;
		try {
			// 保存
			permissiondao.saveOrUpdate(permission);
			return true;
		} catch (Exception ex) {

		}
		return false;
	}

	@Override
	public Permission GetPermissionByName(String name) {
		if (name == null)
			return null;
		String hql = "FROM Permission WHERE name=?";
		Object[] values = { name };

		List items = permissiondao.find(hql, values);

		if (items.size() > 0) {
			return (Permission) items.get(0);
		}
		return null;
	}

	@Override
	public Permission GetPermissionByURL(String url) {

		return permissiondao.get(url);
	}

	@Override
	public List<Permission> GetPermissionsByWhere(String likeName,
			String likeUrl) {

		if (likeName == null || likeUrl == null) {
			return null;
		}
		String hql = "FROM Permission WHERE name LIKE ? AND url LIKE ?";
		Object[] values = { "%" + likeName + "%", "%" + likeUrl + "%" };
		return permissiondao.find(hql, values);
	}
}
