package com.joe.monitor.callback.url;

import java.util.List;

import javax.management.ObjectName;

/**
 * 注册URL地址管理者
 * @author qiaolong
 *
 */
public interface URLManager {

	/**
	 * 根据类型得到所有的回调URL
	 * @param domin
	 * @return
	 */
	List<String> getByType(String type);
	
	/**
	 * 
	 * @param type
	 * @param name
	 * @return
	 */
	List<String> get(String type,String name);
	
	/**
	 * 
	 * @param observedObject
	 * @param observedAttribute
	 * @return
	 */
	List<String> get(ObjectName observedObject, String observedAttribute);
	
	
	/**
	 * 注册URL地址
	 * @param name 名称的话如CPU、内存、网络等
	 * @param type 类型的话如 主机、服务等 对应的 就是 ObjectName 的 domain
	 * @param url
	 */
	void register(String type,String name,String url);
	
	/**
	 * 删除指定的类型、nmae和url地址
	 * @param type
	 * @param name
	 * @param url
	 */
	void remove(String type,String name,String url);
}
