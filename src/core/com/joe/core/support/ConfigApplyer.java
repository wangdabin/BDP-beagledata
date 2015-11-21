package com.joe.core.support;

import java.util.List;

import com.joe.host.vo.Host;
import com.sky.service.define.KeyValue;

/**
 * 应用配置
 * @author qiaolong
 *
 */
public interface ConfigApplyer {

	/**
	 * 得到指定主机的配置项
	 * @param host
	 * @param configFile
	 * @return
	 */
	List<KeyValue> findAll(Host host, String configFile);
	
	/**
	 * 
	 * @param exId
	 * @param configFile
	 * @param kv
	 */
	void newKeyValue(Host host, String configFile,KeyValue kv);
	
	/**
	 * 
	 * @param hosts
	 * @param configFile
	 * @param kv
	 */
	void newKeyValue(List<Host> hosts, String configFile,KeyValue kv);
	
	/**
	 * 
	 * @param exId
	 * @param configFile
	 * @param kvs
	 */
	void newKeyValues(Host host,String configFile,List<KeyValue> kvs);
	
	/**
	 * 
	 * @param host
	 * @param configFile
	 * @param kvs
	 */
	void newKeyValues(List<Host> host,String configFile,List<KeyValue> kvs);
	
	/**
	 * 向指定的配置文件上添加配置项
	 * @param file
	 * @param kv
	 */
	void updateConfig(Host host,String file,KeyValue kv);
	
	/**
	 * 
	 * @param hosts
	 * @param file
	 * @param kv
	 */
	void updateConfig(List<Host> hosts,String file,KeyValue kv);
	
	/**
	 * 
	 * @param hosts
	 * @param file
	 * @param kvs
	 */
	void updateConfig(List<Host> hosts,String file,List<KeyValue> kvs);
}
