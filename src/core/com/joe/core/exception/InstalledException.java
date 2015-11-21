package com.joe.core.exception;

import org.apache.commons.lang.StringUtils;

import com.joe.core.version.Name;

/**
 * 如果已经安装则抛出已经安装异常
 * @author qiaolong
 *
 */
public class InstalledException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Name name;

	public InstalledException(Name name){
		super("The name " + name.getName() + (StringUtils.isBlank(name.getVersion()) ? "" : " version " + name.getVersion()) + " already install");
		this.name = name;
	}
	
	public Name getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static final InstalledException getException(Name name){
		return new InstalledException(name);
	}
}
