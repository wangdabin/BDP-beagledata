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
import com.sky.task.vo.Task;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * 
 * @author qiaolong
 * 
 */
public class AgentTaskClient extends AbstractClient {

	public AgentTaskClient(Configuration conf) {
		super(conf);
	}

	/**
	 * 指定主机地址获取任务列表
	 * @param host
	 * @return
	 */
	// GET
	public List<Task> getAll(String host) {
		String key = "agent.ws.resource.tasks";
		String resource = parseResource(key, host);
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		return this.doGet(new GenericType<List<Task>>() {}, resource, queryParams, headerParams, cookies);
	}
	
	/**
	 * 提交任务
	 * @param host
	 * @param task
	 * @return
	 */
	//POST
	public ReCode commitTask(String host,Task task){
		String key = "agent.ws.resource.tasks";
		String resource = parseResource(key, host);
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		return this.doPost(ReCode.class, resource, queryParams, headerParams, cookies, task);
	}
	
	/**
	 * 
	 * @param host
	 * @param tId
	 * @return
	 */
	//GET
	public Task getById(String host,long tId){
		String key = "agent.ws.resource.tasks.tId";
		String resource = parseResource(key, host);
		resource = replace(resource, "tId", tId);
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		return this.doGet(Task.class, resource, queryParams, headerParams, cookies);
	}
	
	/**
	 * 把
	 * @param key
	 * @param host
	 * @return
	 */
	protected String parseResource(String key,String host){
		String resource = getConf().getString(key);
		return replace(resource, "hostname", host);
	}
}
