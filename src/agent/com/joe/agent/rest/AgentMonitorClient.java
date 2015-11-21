package com.joe.agent.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;

import com.joe.core.rest.AbstractClient;
import com.joe.core.utils.CoreConfigUtils;
import com.joe.core.vo.ReCode;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * agent监控
 * @author computer
 * @version 2015-03-04 13:47:32 Automatic generation
 * 
 */
public class AgentMonitorClient extends AbstractClient {

	private String hostname;
	private int port;
	
	/**
	 * 指定主机名添加
	 * @param hostname
	 * @param port
	 * @throws java.io.IOException
	 */
	public AgentMonitorClient(String hostname,int port) throws java.io.IOException {
		super(CoreConfigUtils.create());
		this.hostname = hostname;
		this.port = port;
	}

	// POST
	public ReCode addMonitor(String type,String name,String schedule) {
		String resource = getConf().getString("server.ws.resource.monitor.type.name.add");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("schedule", schedule);
		resource = parseResource(resource);
		resource = resource.replaceAll("\\{type\\}", type);
		resource = resource.replaceAll("\\{name\\}", name);
		Map<String, String> headerParams = new HashMap<String, String>();
		List<Cookie> cookies = new ArrayList<Cookie>();
		return this.doPost(ReCode.class, resource, queryParams,
				headerParams, cookies, null);
	}

	/**
	 * 
	 * @param resource
	 * @return
	 */
	private String parseResource(String resource){
		resource = replace(resource,"hostname", hostname);
		resource = replace(resource,"port", Integer.toString(port));
		return resource;
	}
}