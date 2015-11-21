package com.joe.core.exception;

import com.joe.core.support.Depends;

/**
 * 如果已经安装则抛出已经安装异常
 * @author qiaolong
 *
 */
public class DependException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Depends depends;
	
	public DependException(Depends depends){
		super("The depends " + depends.dependOn() + " not installed!");
		this.depends = depends;
	}
	
	public Depends getDepends() {
		return depends;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static final DependException getException(Depends depends){
		return new DependException(depends);
	}
}
