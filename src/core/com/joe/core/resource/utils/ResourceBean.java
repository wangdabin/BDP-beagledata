package com.joe.core.resource.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 资源类,整理资源
 * @author Joe
 *
 */
public class ResourceBean {

	private String name; // 资源名称
	private String className; // 类名
	private String basePath; // 基础路径
	private boolean enable;
	private List<PathInfo> pathInfos = new ArrayList<PathInfo>();
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return the pathInfos
	 */
	public List<PathInfo> getPathInfos() {
		return pathInfos;
	}
	
	/**
	 * 
	 * @param pathInfo
	 */
	public void addPathInfo(PathInfo pathInfo){
		this.pathInfos.add(pathInfo);
	}
	
	/**
	 * @return the enable
	 */
	public boolean isEnable() {
		return enable;
	}
	/**
	 * @param enable the enable to set
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	/**
	 * @return the basePath
	 */
	public String getBasePath() {
		return basePath;
	}
	/**
	 * @param basePath the basePath to set
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
}
