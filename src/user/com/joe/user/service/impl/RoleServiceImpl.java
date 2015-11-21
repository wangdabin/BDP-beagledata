package com.joe.user.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joe.user.dao.OperationDAO;
import com.joe.user.dao.PermissionDAO;
import com.joe.user.dao.RoleDAO;
import com.joe.user.dao.RoleRelationDAO;
import com.joe.user.dao.UserDAO;
import com.joe.user.service.RoleService;
import com.joe.user.vo.Operation;
import com.joe.user.vo.Permission;
import com.joe.user.vo.Role;
import com.joe.user.vo.RoleRelation;
import com.joe.user.vo.User;

/**
 * 
 * 角色数据服务层
 * 
 * @author Joe
 * 
 */
@Service("roleService")
@Transactional(rollbackFor = RuntimeException.class)
public class RoleServiceImpl implements RoleService {

	@Resource(name = "roleDAO")
	private RoleDAO roleDao = null;

	@Resource(name = "operationDAO")
	private OperationDAO operationDao = null;

	@Resource(name = "permissionDAO")
	private PermissionDAO permissionDao = null;

	@Resource(name = "roleRelationDAO")
	private RoleRelationDAO roleRelationDao = null;

	@Resource(name = "userDAO")
	private UserDAO userDao = null;

	public RoleServiceImpl() {
	}

	@Override
	public Role get(Serializable id) {
		return roleDao.get(id);
	}

	@Override
	public List<Role> getAll() {
		return roleDao.getAll();
	}

	@Override
	public void add(Role newInstance) {
		roleDao.save(newInstance);
	}

	@Override
	public void remove(Role transientObject) {
		roleDao.remove(transientObject);
	}

	@Override
	public void removeById(Serializable id) {
		roleDao.removeById(id);
	}

	@Override
	public Role update(Role t) {
		roleDao.saveOrUpdate(t);
		return t;
	}

	@Override
	public void addBatch(List<Role> instanceBatch) {
		// TODO Auto-generated method stub

	}

	
	
	@Override
	public void updateUsers(Role role) {
		Role roleDb = roleDao.get(role.getId());
		Set<User> users = new HashSet<User>();
		for (User user : role.getUsers()) {
			User userDb = userDao.get(user.getId());
			users.add(userDb);
		}
		roleDb.setUsers(users);
		roleDao.saveOrUpdate(roleDb);
	}

	@Override
	public Set<User> getUsersByRoleId(int roleId, boolean flag) {
		Set<User> existResult = new HashSet<User>();
		Set<User> noexistResult = new HashSet<User>();

		List<Integer> ids = new ArrayList<Integer>();
		List<User> usersDB = userDao.getAll();
		for (User u : usersDB) {
			ids.add(u.getId());
		}
		// 1.得到对应的当前role下的所有Users
		Role role = roleDao.get(roleId);
		Set<User> users = role.getUsers();
		for (User user : users) {
			User userVo = new User();
			userVo.setId(user.getId());
			userVo.setAccount(user.getAccount());
			existResult.add(userVo);
			if (ids.contains(user.getId())) {
				ids.remove(new Integer(user.getId()));
			}
		}
		for (Integer id : ids) {
			User user = userDao.get(id);
			User userVo = new User();
			userVo.setId(user.getId());
			userVo.setAccount(user.getAccount());
			noexistResult.add(userVo);
		}
		return flag ? existResult : noexistResult;
	}

	@Override
	public List<Permission> getPermissionsByRoleId(int roleId) {
		List<Permission> permissions = new ArrayList<Permission>();
		Role role = roleDao.get(roleId);
		Set<RoleRelation> roleRelations = role.getRolerelation();
		for (RoleRelation rr : roleRelations) {
			Permission permission = rr.getPermission();
			Permission permissionVo = new Permission();
			permissionVo.setUrl(permission.getUrl());
			permissionVo.setDescription(permission.getDescription());
			permissions.add(permission);
		}
		return permissions;
	}

	@Override
	public void updateRolerelations(Role role) {

		Role roleDb = roleDao.get(role.getId());
		Set<RoleRelation> rolerelations = new HashSet<RoleRelation>();
		for (RoleRelation rr : role.getRolerelation()) {
			Permission permission = rr.getPermission();
			Permission permissionDb = permissionDao.get(permission.getUrl());
			List<Operation> operations = operationDao.getAll();
			for (Operation o : operations) {
				RoleRelation roleRelation = new RoleRelation();
				roleRelation.setOperation(o);
				roleRelation.setPermission(permissionDb);
				rolerelations.add(roleRelation);
			}

		}
		roleRelationDao.deleteAllByRoleId(role.getId());
		roleDb.setRolerelation(rolerelations);
//		roleDao.saveOrUpdate(roleDb);

	}

}
