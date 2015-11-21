package com.joe.user.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joe.user.dao.RoleDAO;
import com.joe.user.dao.UserDAO;
import com.joe.user.service.UserService;
import com.joe.user.vo.Role;
import com.joe.user.vo.User;

/**
 * 
 * 用户数据服务层
 * 
 * @author Joe
 * 
 */
@Service("userService")
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl implements UserService, UserDetailsService {

    protected final MessageSourceAccessor messages = SpringSecurityMessageSource
	    .getAccessor();
    @Resource(name = "userDAO")
    private UserDAO userDao;
    
    @Resource(name = "roleDAO")
    private RoleDAO roleDAO;

    @Override
    public User get(Serializable id) {
	return userDao.get(id);
    }

    @Override
    public List<User> getAll() {
	 return userDao.getAll();
    }

    @Override
    public void add(User user) {
	user.setCreateDate(System.currentTimeMillis());// 设置创建时间
	user.setLastUpdateDate(System.currentTimeMillis());//设置最后更新时间
	Set<Role> roles = user.getRoles();
	for(Role r:roles) {
	    Role role = roleDAO.get(r.getId());
	    role.getUsers().add(user);
	}
	userDao.save(user);
    }

    @Override
    public void remove(User user) {
	//删除对应的和角色之前的关系
	User userDb = userDao.get(user.getId());
	Set<Role> roles = userDb.getRoles();
	for(Role role:roles) {
	    Set<User> users = role.getUsers();
	    Set<User> newUsers = new HashSet<User>();
	    for(User u:users) {
		if(u.getId()!=user.getId()) {
		    //如果不存在,那么添加
		    newUsers.add(u);
		}
	    }
	    role.setUsers(newUsers);
	}
	userDao.remove(user);
	
    }

    @Override
    public void removeById(Serializable id) {
	userDao.removeById(id);
    }

    @Override
    public User update(User user) {
	//由于user端不维护关系,所以使用操作role端去维护关系
	//1.将包含user的role去掉user
	List<Role> all = roleDAO.getAll();
	for(Role role:all) {
	    Set<User> users = role.getUsers();
	    Set<User> newUsers = new HashSet<User>();
	    for(User u:users) {
		if(u.getId()!=user.getId()) {
		    //如果不存在,那么添加
		    newUsers.add(u);
		}
	    }
	    role.setUsers(newUsers);
	}
	Set<Role> roles = user.getRoles();
	for(Role r:roles) {
	    Role role = roleDAO.get(r.getId());
	    role.getUsers().add(user);
	}
	User userDb = userDao.get(user.getId());
	
	userDb.setAccount(user.getAccount());
	userDb.setNickName(user.getNickName());
	userDb.setLastUpdateDate(user.getLastUpdateDate());
//	BeanUtils.copyProperties(user,userDb);
	userDao.saveOrUpdate(userDb);
	return user;
    }

    @Override
    public User getUserByAccount(String userAccount) {
	if (userAccount == null)
	    return null;
	String hql = "FROM User WHERE account=?";
	Object[] values = { userAccount };
	List items = userDao.find(hql, values);
	if (items.size() > 0) {
	    return (User) items.get(0);
	}
	return null;
    }

    @Override
    public void addBatch(List<User> instanceBatch) {

    }

    @Override
    public UserDetails loadUserByUsername(String account)
	    throws UsernameNotFoundException {
	User user = this.getUserByAccount(account);
	if (user == null) {
	    throw new UsernameNotFoundException(messages.getMessage(
		    "UserServiceImpl.notFound", new Object[] { account },
		    "Username {0} not found"));
	}
	List<String> roles = new ArrayList<String>();
	for (Role key : user.getRoles()) {
	    roles.add(key.getName());
	}
	org.springframework.security.core.userdetails.User result = new org.springframework.security.core.userdetails.User(
		user.getAccount(), user.getPassword(), true, // 用户是否可用
		true, true, true, AuthorityUtils.createAuthorityList(roles
			.toArray(new String[0])));

	return result;
    }
}