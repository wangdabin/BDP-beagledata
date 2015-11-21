package com.joe.core.resource.utils;

import java.lang.reflect.Method;

/**
 * 
 * 每个method 
 * @author Joe
 *
 */
public class PathInfo {

	private String path; //资源路径
	private String method; //put 、delete、get、post
	private Method realMethod;
	
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * @return the realMethod
	 */
	public Method getRealMethod() {
		return realMethod;
	}
	/**
	 * @param realMethod the realMethod to set
	 */
	public void setRealMethod(Method realMethod) {
		this.realMethod = realMethod;
	}
}
