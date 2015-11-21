package com.joe.agent.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.configuration.Configuration;

import com.joe.core.rest.AbstractClient;
import com.joe.core.vo.ReCode;
import com.sky.service.define.KeyValue;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * 
 * 用agent端来修改配置文件
 * 可以针对配置文件做单个修改
 * @author qiaolong
 * 
 */
public class ConfigClient extends AbstractClient {

	public ConfigClient(Configuration conf) {
		super(conf);
	}
	
	/**
	 * 根据制定的文件获得所有的keyvalue集合
	 * @param host
	 * @param exId config的插件扩张ID
	 * @param configFile
	 * @return
	 */
	public List<KeyValue> findAll(String host,String exId, String configFile){
		String key = "agent.ws.resource.config.keyvalues.get";
		String resource = parseResource(key, host);
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("file", configFile);
		queryParams.add("exId", exId);
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		return this.doGet(new GenericType<List<KeyValue>>() {}, resource, queryParams, headerParams, cookies);
	}
	
	/**
	 * 创建指定的文件的keyvalue值
	 * @param exId config的插件扩张ID
	 * @param configFile
	 * @param kv
	 * @return
	 */
	public ReCode newKeyValue(String host,String exId, String configFile,KeyValue kv){
		String key = "agent.ws.resource.config.keyvalue.post";
		String resource = parseResource(key, host);
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("file", configFile);
		queryParams.add("exId", exId);
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		return this.doPost(ReCode.class, resource, queryParams, headerParams, cookies,kv);
	}
	
	/**
	 * 创建指定的文件的keyvalue值
	 * @param host
	 * @param exId
	 * @param configFile
	 * @param kvs
	 * @return
	 */
	public ReCode newKeyValues(String host,String exId,String configFile,List<KeyValue> kvs){
		String key = "agent.ws.resource.config.keyvalues.post";
		String resource = parseResource(key, host);
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("file", configFile);
		queryParams.add("exId", exId);
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		return this.doPost(ReCode.class, resource, queryParams, headerParams, cookies,kvs);
	}
	
	/**
	 * 更细指定的文件的keyvalue值
	 * @param exId config的插件扩张ID
	 * @param configFile
	 * @param kv
	 * @return
	 */
	public ReCode updateKeyValue(String host,String exId,String configFile,KeyValue kv){
		String key = "agent.ws.resource.config.keyvalue.put";
		String resource = parseResource(key, host);
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("file", configFile);
		queryParams.add("exId", exId);
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		return this.doPut(ReCode.class, resource, queryParams, headerParams, cookies,kv);
	}
	
	/**
	 * 更细指定的文件的keyvalue值
	 * @param exId
	 * @param configFile
	 * @param kv
	 * @return
	 */
	public ReCode updateKeyValues(String host,String exId,String configFile,List<KeyValue> kvs){
		String key = "agent.ws.resource.config.keyvalues.put";
		String resource = parseResource(key, host);
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("file", configFile);
		queryParams.add("exId", exId);
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		return this.doPut(ReCode.class, resource, queryParams, headerParams, cookies,kvs);
	}
	
	/**
	 * 删除某个配置项
	 * @param host
	 * @param exId
	 * @param configFile
	 * @param kv
	 * @return
	 */
	public ReCode delete(String host,String exId,String configFile,String delKey){
		String key = "agent.ws.resource.config.keyvalue.delete";
		String resource = parseResource(key, host);
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("file", configFile);
		queryParams.add("exId", exId);
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		return this.doDelete(ReCode.class, resource, queryParams, headerParams, cookies,delKey);
	}
	
	/**
	 * 删除某些配置项
	 * @param host
	 * @param exId
	 * @param configFile
	 * @param kv
	 * @return
	 */
	public ReCode delete(String host,String exId,String configFile,List<String> delKeys){
		String key = "agent.ws.resource.config.keyvalues.delete";
		String resource = parseResource(key, host);
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("file", configFile);
		queryParams.add("exId", exId);
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		return this.doDelete(ReCode.class, resource, queryParams, headerParams, cookies,delKeys);
	}
	
	/**
	 * @param key
	 * @param host
	 * @return
	 */
	protected String parseResource(String key,String host){
		String resource = getConf().getString(key);
		return replace(resource, "hostname", host);
	}
	/**
	 * 得到一个文本文件的内容
	 * @param host
	 * @param configFile
	 * @return
	 */
	public String findTextContent(String host,String configFile){
		String key = "agent.ws.resource.sshkeygen.idrsapub.get";
		String resource = parseResource(key, host);
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("file", configFile);
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		return this.doGet(new GenericType<String>() {}, resource, queryParams, headerParams, cookies);
	}
	public ReCode sendTextContent(String host,String configFile,String text){
		String key = "agent.ws.resource.sshkeygen.authorized_keys.put";
		String resource = parseResource(key, host);
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("file", configFile);
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		return this.doPut(ReCode.class, resource, queryParams, headerParams, cookies,text);
	}
}
