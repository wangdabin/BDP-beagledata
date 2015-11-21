package com.sky.service.define.shell;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sky.service.define.CommandDefine;

/**
 * 修改目录所属用户属性
 * @author qiaolong
 *
 */
public class ChownCommand extends CommandDefine{
	public static final String SHELL = "chown";

	/**
	 * 
	 * @param user
	 * @param dir
	 */
	public ChownCommand(String user,String[] dirs){
		this(user,null,dirs);
	}
	
	/**
	 * 
	 * @param user
	 * @param group
	 * @param dir
	 */
	public ChownCommand(String user,String group,String[] dirs){
		this.setShell(SHELL);
		this.addParam("-R");
		if(StringUtils.isBlank(group)){
			this.addParam(user);
		}else{
			this.addParam(user + ":" + group);
		}
		for(String dir : dirs){
			this.addParam(dir);
		}
	}
	
	/**
	 * 
	 * @param user
	 * @param dirs
	 */
	public ChownCommand(String user,List<String> dirs){
		this(user,null,dirs);
	}
	
	/**
	 * 
	 * @param user
	 * @param group
	 * @param dir
	 */
	public ChownCommand(String user,String group,List<String> dirs){
		this.setShell(SHELL);
		this.addParam("-R");
		if(StringUtils.isBlank(group)){
			this.addParam(user);
		}else{
			this.addParam(user + ":" + group);
		}
		for(String dir : dirs){
			this.addParam(dir);
		}
	}
}
