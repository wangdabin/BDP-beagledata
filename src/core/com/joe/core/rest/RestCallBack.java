package com.joe.core.rest;

import com.sun.jersey.api.client.WebResource;

/**
 * 
 * @author Joe
 *
 * @param <T>
 */
public interface RestCallBack<T> {
	/**
	 * 
	 * @param session
	 * @return
	 */
	T doInRest(WebResource resource);
	
}
