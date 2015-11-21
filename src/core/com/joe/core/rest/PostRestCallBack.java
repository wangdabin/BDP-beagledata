package com.joe.core.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.WebResource.Builder;

/**
 * 做postcallback
 * @author Joe
 *
 * @param <T>
 */
public class PostRestCallBack<T> extends AbstractRestCallBack<T>{

	private Object value;
	public PostRestCallBack(Class<T> clazz,
			MultivaluedMap<String, String> queryParams,
			Map<String, String> headers, List<Cookie> cookies,Object value) {
		super(clazz, queryParams, headers, cookies);
		this.value = value;
	}

	@Override
	public T doInRest(Builder resource) {
		T t = null;
		if(value != null){
			t = resource.post(clazz,value);
		}else{
			t = resource.post(clazz);
		}
		return t;
	}
}
