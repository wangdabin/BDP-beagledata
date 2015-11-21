package com.sky.service.define.shell;

import org.apache.commons.lang.StringUtils;

import com.sky.service.define.CommandDefine;

/**
 * 修改目录所属用户属性
 * @author qiaolong
 *
 */
public class UserAddCommand extends CommandDefine{
	public static final String SHELL = "useradd";
	
	/**
	 * 
	 * @param user
	 * @param dir
	 */
	public UserAddCommand(String user,String password){
		this(user,null,password);
	}
	
	/**
	 * 
	 * @param user
	 * @param group
	 * @param dir
	 */
	public UserAddCommand(String user,String group,String password){
		this.setShell(SHELL);
		this.addParam("-R");
		if(StringUtils.isBlank(group)){
			this.addParam(user);
		}else{
			this.addParam(user + ":" + group);
		}
		this.addParam(password);
	}
}
