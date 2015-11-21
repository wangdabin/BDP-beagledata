package com.sky.rest.entity;

import java.io.Serializable;

import com.sky.config.Configed;
import com.sky.rest.RestAPI;

/**
 * 
 * rest资源实体定义
 * @author qiaolong
 * 
 */
public class RestEntity extends Configed implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected RestAPI restApi;
	/**
	 * 
	 * @param restApi
	 * @param uri
	 */
	public RestEntity(RestAPI restApi) {
		super(restApi.getConf());
	}
	
	public RestAPI getRestApi() {
		return restApi;
	}

	public void setRestApi(RestAPI restApi) {
		this.restApi = restApi;
	}
}
