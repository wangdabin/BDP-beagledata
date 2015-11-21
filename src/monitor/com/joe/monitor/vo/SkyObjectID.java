package com.joe.monitor.vo;

import java.util.Hashtable;

import javax.management.MalformedObjectNameException;

import org.apache.log4j.Logger;

import com.joe.monitor.ObjectID;

/**
 * 
 * @author qiaolong
 * 
 */
public class SkyObjectID {

	public static final String KEY_TYPE = "type"; //指定 MBean 的类型
	public static final String KEY_NAME = "name"; //指定 MBean 的名称
	
	public static final String TYPE_MONITOR = "monitor"; //监控类型的MBean
	
	//监控名称的定义。。。
	public static final String MONITOR_NAME_CPU = "cpu"; //CPU
	public static final String MONITOR_NAME_MEM = "mem"; //内存
	public static final String MONITOR_NAME_NET = "net"; //网络
	public static final String MONITOR_NAME_DISK = "disk"; //磁盘
	
	
	/**
	 * 
	 * @param objectName
	 * @return
	 * @throws MalformedObjectNameException
	 * @throws NullPointerException
	 */
	public static ObjectID getObjectName(String objectName) throws MalformedObjectNameException, NullPointerException{
		return ObjectID.getInstance(objectName);
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
	public static ObjectID getObjectName(String domain,String key,
            String value) {
		return  ObjectID.getInstance(domain, key,value);
	}
	
	/**
	 * 
	 * @param domain
	 * @param table
	 * @return
	 * @throws MalformedObjectNameException
	 * @throws NullPointerException
	 */
	public static ObjectID getObjectName(String domain,
			Hashtable<String, String> table) throws MalformedObjectNameException, NullPointerException {
		return ObjectID.getInstance(domain, table);
	}
	
	/**
	 * 不抛异常的处理
	 * @param LOG
	 * @param domain
	 * @param table
	 * @return
	 */
	protected static ObjectID getObjectNameNoThrow(Logger LOG,String domain,
			Hashtable<String, String> table) {
		try {
			return getObjectName(domain, table);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param domain
	 * @param name
	 * @return
	 * @throws MalformedObjectNameException
	 * @throws NullPointerException
	 */
	public static ObjectID getMonitorName(String domain,String name) throws MalformedObjectNameException, NullPointerException{
		TableBuilder builder = TableBuilder.create(TYPE_MONITOR).put(KEY_NAME, name);
		return getObjectName(domain, builder.build());
	}
	
	/**
	 * cpu 监控的名称
	 * @return
	 * @throws NullPointerException 
	 * @throws MalformedObjectNameException 
	 */
	public static ObjectID getCpuMonitor(String domain) throws MalformedObjectNameException, NullPointerException{
		return getMonitorName(domain,MONITOR_NAME_CPU);
	}
	
	/**
	 * 内存的
	 * @return
	 */
	public static ObjectID getMemMonitor(String domain) throws MalformedObjectNameException, NullPointerException{
		return getMonitorName(domain,MONITOR_NAME_MEM);
	}
	
	/**
	 * 网络
	 * @return
	 */
	public static ObjectID getNetMonitor(String domain) throws MalformedObjectNameException, NullPointerException{
		return getMonitorName(domain,MONITOR_NAME_NET);
	}
	
	/**
	 * 磁盘
	 * @return
	 */
	public static ObjectID getDiskMonitor(String domain) throws MalformedObjectNameException, NullPointerException{
		return getMonitorName(domain,MONITOR_NAME_DISK);
	}
	
	/**
	 * 
	 * @param LOG
	 * @param domain
	 * @param name
	 * @return
	 */
	protected static ObjectID getMonitorName(Logger LOG,String domain,String name){
		TableBuilder builder = TableBuilder.create(TYPE_MONITOR).put(KEY_NAME, name);
		return getObjectNameNoThrow(LOG,domain, builder.build());
	}
	

	public static class TableBuilder{
		private Hashtable<String, String> table;
		
		private TableBuilder(){
			this.table = createTable();
		}
		
		private TableBuilder(String type){
			this.table = createTable();
			type(type);
		}
		
		public TableBuilder type(String type){
			return put(KEY_TYPE, type);
		}
		
		public Hashtable<String,String> build(){
			return table;
		}
		
		public TableBuilder put(String key,String value){
			table.put(key, value);
			return this;
		}
		
		/**
		 * 
		 * @return
		 */
		public static final TableBuilder create(){
			return new TableBuilder();
		}
		
		/**
		 * 
		 * @param type
		 * @return
		 */
		public static final TableBuilder create(String type){
			return new TableBuilder(type);
		}
		
		/**
		 * 
		 * @return
		 */
		protected static Hashtable<String, String> createTable(){
			return new Hashtable<String,String>();
		}
	}
}
