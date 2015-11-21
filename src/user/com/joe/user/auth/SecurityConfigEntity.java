package com.joe.user.auth;

import org.springframework.security.access.SecurityConfig;

/**
*
* @description 认证权限配置实体
*
* @function
*		添加操作方式属性，目的在于增加操作方式（get、put、delete、post）过滤
*
* @author ZhouZH
*
*/

public class SecurityConfigEntity extends SecurityConfig{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SecurityConfigEntity(String config) {
		super(config);
	}

	private String Operation;

	public String getOperation() {
		return Operation;
	}

	public void setOperation(String operation) {
		Operation = operation;
	}
	
}
