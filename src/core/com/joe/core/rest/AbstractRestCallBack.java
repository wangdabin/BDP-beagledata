package com.joe.core.rest;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

/**
 * 做get
 * @author Joe
 *
 * @param <T>
 */
public abstract class AbstractRestCallBack<T> implements RestCallBack<T>{
	
	public static final String CONTENT_TYPE_CONF = "Content-Type";
	public static final String CONTENT_TYPE = MediaType.APPLICATION_JSON;
	
	// 默认接受的类型
	public static final MediaType[] ACCEPT_TYPE = new MediaType[] { MediaType.APPLICATION_JSON_TYPE };
	protected Class<T> clazz;
	protected MultivaluedMap<String,String> queryParams;
	protected Map<String,String> headers;
	protected List<Cookie> cookies;
	
	public AbstractRestCallBack(Class<T> clazz, MultivaluedMap<String,String> queryParams,Map<String,String> headers,List<Cookie> cookies){
		this.clazz = clazz;
		this.queryParams = queryParams;
		this.headers = headers;
		this.cookies = cookies;
	}
	
	@Override
	public T doInRest(WebResource resource) {
		if(queryParams != null){
			resource = resource.queryParams(queryParams);
		}
		Builder builder = resource.accept(ACCEPT_TYPE);
		boolean contentFlag = false;
		if(headers != null){
			for(Entry<String, String> entry : headers.entrySet()){
				String key = entry.getKey();
				if(CONTENT_TYPE_CONF.equalsIgnoreCase(key)){
					contentFlag = true;
				}
				builder = builder.header(entry.getKey(), entry.getValue());
			}
		}
		if(!contentFlag){
			builder.header(CONTENT_TYPE_CONF, CONTENT_TYPE); //设置内容类型
		}
		if(cookies != null){
			for(Cookie cookie : cookies){
				builder = builder.cookie(cookie);
			}
		}
		return this.doInRest(builder);
	}
	
	public abstract T doInRest(Builder resource);
}
