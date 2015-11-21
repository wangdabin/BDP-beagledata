package com.joe.core.version;

/**
 * 
 * @author qiaolong
 *
 */
public interface Name {
	public static final String NAME_VERSION_SPLIT = "-";
	/**
	 * 名称
	 * @return
	 */
	String getName();
	
	/**
	 * 
	 * @param name
	 */
	void setName(String name);
	
	/**
	 * 版本
	 * @return
	 */
	String getVersion();
	
	/**
	 * 
	 * @param version
	 */
	void setVersion(String version);
	
	/**
	 * 获取当前的key
	 * @return
	 */
	String getUniqueKey();
	
	/**
	 * 指定分隔符获取key
	 * @param split
	 * @return
	 */
	String getUniqueKey(String split);
}
