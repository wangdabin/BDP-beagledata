package com.joe.user.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
*
* @description 权限管理表，关联操作和权限
*
* @function
*
* @author ZhouZH
*
*/
@XmlRootElement
public class RoleRelation extends BaseEntity {
	
	private Permission permission; //拥有的权限
	private Operation operation; //拥有的操作方式
	private Role role;//拥有的角色
	
	public Role getRole() {
	    return role;
	}

	public void setRole(Role role) {
	    this.role = role;
	}

	public RoleRelation()
	{
		this.permission = new Permission();
		this.operation = new Operation();
	}
	
	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
}
