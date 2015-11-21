package com.joe.monitor;

import java.util.Hashtable;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * 用于监控标记是否一个对象
 * 
 * 域的定义是：主机名称
 * 类型定义是：主机、服务等
 * 名称定义是：cpu、内存等
 * 还会再加上 主机的IP等做为map中的一个字段
 * 
 * @author qiaolong
 * 
 */
public class ObjectID extends ObjectName {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ObjectID(String name) throws MalformedObjectNameException, NullPointerException {
		super(name);
	}

	private ObjectID(String domain, String key, String value)
			throws MalformedObjectNameException, NullPointerException {
		super(domain, key, value);
	}

	private ObjectID(String domain, Hashtable<String, String> table)
			throws MalformedObjectNameException, NullPointerException {
		super(domain, table);
	}
	
	/**
	 * 
	 * @param domain
	 * @param table
	 * @return
	 * @throws MalformedObjectNameException
	 * @throws NullPointerException
	 */
	public static ObjectID getInstance(String domain,
			Hashtable<String, String> table){
		try {
			return new ObjectID(domain, table);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param domain
	 * @param key
	 * @param value
	 * @return
	 * @throws MalformedObjectNameException
	 * @throws NullPointerException
	 */
	public static ObjectID getInstance(String domain,String key,
            String value){
		try {
			return  new ObjectID(domain,key,value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static ObjectID getInstance(String name){
		try {
			return new ObjectID(name);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param objectName
	 * @return
	 */
	public static final String getType(ObjectName objectName){
		return objectName.getDomain();
	}
	
	/**
	 * 得到名称
	 * @param objectName
	 * @return
	 */
	public static final String getName(ObjectName objectName){
		return objectName.getKeyProperty("name");
	}
	
	/**
	 * 
	 * @return
	 */
	public String getType(){
		return getType(this);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getName(){
		return getName(this);
	}
}
