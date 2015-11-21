package com.joe.user.service;

import java.util.List;
import java.util.Set;

import com.joe.core.service.EntityService;
import com.joe.user.vo.Permission;
import com.joe.user.vo.Role;
import com.joe.user.vo.User;

/**
 * @ClassName: RoleService
 * @Description: 角色管理服务类
 * @author WDB
 * @date 2015-4-28 下午2:48:55
 */
public interface RoleService extends EntityService<Role> {
	/**
	 * @Title: updateUsers
	 * @Description: 根据对应传递过来的Role修改对应与user的关系
	 * @param @param role 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void updateUsers(Role role);

	 /**
	 * @Title: getUsersByRoleId
	 * @Description: 根据对应的roleid和 flag返回对应的角色已经绑定的用户或者没有绑定的用户
	 * @param @param roleId
	 * @param @param flag
	 * @return Set<User> 返回类型
	 * @throws
	 */
	 public Set<User> getUsersByRoleId(int roleId, boolean flag);
	/**
	 * @Title: getPermissionsByRoleId
	 * @Description: 得到当前角色的绑定的权限列表
	 * @param @param roleId
	 * @param @return 设定文件
	 * @return List<Permission> 返回类型
	 * @throws
	 */
	public List<Permission> getPermissionsByRoleId(int roleId);
	 /**
	 * @Title: updateRolerelations
	 * @Description: 更新角色和权限之间的关系
	 * @param @param role 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	 public void updateRolerelations(Role role);

}
