package com.joe.monitor.callback.url;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.configuration.Configuration;

import com.joe.core.json.JsonAble;
import com.joe.core.rest.AbstractClient;
import com.joe.user.utils.HttpXmlClient;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * 
 * @author qiaolong
 *
 */
public class URLCallBackClient extends AbstractClient{

	public static final String NAME = "name";
	public static final String VALUE = "value";
	
	public URLCallBackClient(Configuration conf) {
		super(conf);
	}

	public void doCallBack(String resource,String name,JsonAble jsonAble){
//		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
//		this.doPost(Object.class, resource, queryParams, null, null, null);
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put(NAME, name);
		queryParams.put(VALUE,jsonAble.toJson());
		HttpXmlClient.doPost(resource, queryParams);
	}
}
