package com.joe.user.service;

import java.util.List;

import com.joe.user.vo.Permission;

/**
*
* @description 权限管理基础类
*
* @function 
* 		新建权限
* 		编辑权限
* 		根据权限名称查询权限实例
* 		根据权限ID查询权限实例
* 		根据权限名称和url关键字查询权限集合
*
* @author ZhouZH
*
*/
public interface PermissionService {
	
	/**
	 * 新建权限
	 * @param permission 权限实例
	 * @return
	 */
	public boolean NewPermission(Permission permission);
	/**
	 * 编辑权限
	 * @param permission 权限实例
	 * @return
	 */
	public boolean ModifyPermission(Permission permission);
	/**
	 * 根据权限名称查询权限实例
	 * @param name 权限名称
	 * @return
	 */
	public Permission GetPermissionByName(String name);
	/**
	 * 根据权限ID查询权限实例
	 * @param Id 权限ID
	 * @return
	 */
	public Permission GetPermissionByURL(String url);
	/**
	 * 根据权限名称和url关键字查询权限集合
	 * @param likeName 权限名曾关键字
	 * @param likeUrl 权限url关键字
	 * @return
	 */
	public List<Permission> GetPermissionsByWhere(String likeName,String likeUrl);
	
}
