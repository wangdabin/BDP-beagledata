package com.joe.core.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.WebResource.Builder;

/**
 * 做get
 * @author Joe
 *
 * @param <T>
 */
public class GetRestCallBack<T> extends AbstractRestCallBack<T>{
	
	public GetRestCallBack(Class<T> clazz,
			MultivaluedMap<String, String> queryParams,
			Map<String, String> headers, List<Cookie> cookies) {
		super(clazz, queryParams, headers, cookies);
	}

	@Override
	public T doInRest(Builder resource) {
		T t = resource.get(clazz);
		return t;
	}
}
