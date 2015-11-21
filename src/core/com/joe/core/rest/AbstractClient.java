package com.joe.core.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.joe.core.exception.HttpClientErrorException;
import com.joe.core.utils.HttpStatus;
import com.joe.core.utils.JsonUtils;
import com.joe.core.vo.ErrorMessage;
import com.sky.config.ConfigAble;
import com.sky.config.Configed;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

/**
 * 
 * @author qiaolong
 * 
 */
public class AbstractClient extends Configed implements ConfigAble {

	private static final Logger LOG = Logger.getLogger(AbstractClient.class);
	public static final String TOKEN_KEY = "JSESSIONID";
	private String token; // 用于认证。。

	public AbstractClient(Configuration conf) {
		super(conf);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	protected List<Cookie> checkAuth(List<Cookie> cookies) {
		if (cookies == null) {
			cookies = new ArrayList<Cookie>();
			if (token != null) {
				cookies.add(new Cookie(TOKEN_KEY, token));
			}
		} else {
			boolean hasToken = false;
			for (Cookie cookie : cookies) {
				if (TOKEN_KEY.equals(cookie.getName())) {
					hasToken = true;
					break;
				}
			}
			if (!hasToken && token != null) {
				cookies.add(new Cookie(TOKEN_KEY, token));
			}
		}
		return cookies;
	}

	private <T> T parseResponse(ClientResponse resp, Class<T> clazz) {
		String content = null;
		if(LOG.isDebugEnabled()){
			content = RestClient.getContent(resp);
			LOG.debug("Real content : " + content);
		}
		int code = resp.getStatus();
		HttpStatus httpStatus = HttpStatus.valueOf(code);
		if (httpStatus.is2xxSuccessful()) {
			T t = null;
			try{
				t = resp.getEntity(clazz);
			}catch(Exception e){
				LOG.debug("================== " + e.getMessage() + " ====================",e);
				t = JsonUtils.jsonToObject(clazz, content);
			}
			return t;
		} else if (httpStatus.is4xxClientError()) {
			throw new HttpClientErrorException(httpStatus);
		} else if (httpStatus.is5xxServerError()) {
			try{
				ErrorMessage error = resp.getEntity(ErrorMessage.class);
				throw new HttpClientErrorException(httpStatus, error.getMessage());
			}catch(Exception e){
				throw new HttpClientErrorException(httpStatus, e.getMessage());
			}
		} else {
			throw new HttpClientErrorException(httpStatus);
		}
	}
	
	private <T> T parseResponse(ClientResponse resp, GenericType<T> gt) {
		String content = null;
		if(LOG.isDebugEnabled()){
			content = RestClient.getContent(resp);
			LOG.debug("Real content : " + content);
		}
		int code = resp.getStatus();
		HttpStatus httpStatus = HttpStatus.valueOf(code);
		if (httpStatus.is2xxSuccessful()) {
			T t = null;
			try{
				t = resp.getEntity(gt);
			}catch(Exception e){
				t = JsonUtils.jsonToObject(gt.getRawClass(), content);
			}
			return t;
		} else if (httpStatus.is4xxClientError()) {
			throw new HttpClientErrorException(httpStatus);
		} else if (httpStatus.is5xxServerError()) {
			try{
				ErrorMessage error = resp.getEntity(ErrorMessage.class);
				throw new HttpClientErrorException(httpStatus, error.getMessage());
			}catch(Exception e){
				throw new HttpClientErrorException(httpStatus, e.getMessage());
			}
		} else {
			throw new HttpClientErrorException(httpStatus);
		}
	}

	/**
	 * 
	 * @param clazz
	 * @param resource
	 * @param queryParams
	 * @param headers
	 * @param cookies
	 * @return
	 */
	protected <T> T doGet(Class<T> clazz, String resource,
			MultivaluedMap<String, String> queryParams,
			Map<String, String> headers, List<Cookie> cookies) {
		cookies = this.checkAuth(cookies);
		ClientResponse resp = RestClient.get(ClientResponse.class, resource,
				queryParams, headers, cookies);
		T t = parseResponse(resp, clazz);
		resp.close();
		return t;
	}
	
	/**
	 * 
	 * @param clazz
	 * @param resource
	 * @param queryParams
	 * @param headers
	 * @param cookies
	 * @return
	 */
	protected <T> T doGet(GenericType<T> clazz, String resource,
			MultivaluedMap<String, String> queryParams,
			Map<String, String> headers, List<Cookie> cookies) {
		cookies = this.checkAuth(cookies);
		ClientResponse resp = RestClient.get(ClientResponse.class, resource,
				queryParams, headers, cookies);
		T t = parseResponse(resp, clazz);
		resp.close();
		return t;
	}

	/**
	 * 
	 * @param clazz
	 * @param resource
	 * @param queryParams
	 * @param headers
	 * @param cookies
	 * @param value
	 * @return
	 */
	protected <T> T doPost(Class<T> clazz, String resource,
			MultivaluedMap<String, String> queryParams,
			Map<String, String> headers, List<Cookie> cookies, Object value) {
		cookies = this.checkAuth(cookies);
		ClientResponse resp = RestClient.post(ClientResponse.class, resource,
				queryParams, headers, cookies, value);
		T t = parseResponse(resp, clazz);
		resp.close();
		return t;
	}
	
	/**
	 * 
	 * @param clazz
	 * @param resource
	 * @param queryParams
	 * @param headers
	 * @param cookies
	 * @param value
	 * @return
	 */
	protected <T> T doPost(GenericType<T> clazz, String resource,
			MultivaluedMap<String, String> queryParams,
			Map<String, String> headers, List<Cookie> cookies, Object value) {
		cookies = this.checkAuth(cookies);
		ClientResponse resp = RestClient.post(ClientResponse.class, resource,
				queryParams, headers, cookies, value);
		T t = parseResponse(resp, clazz);
		resp.close();
		return t;
	}

	/**
	 * 
	 * @param clazz
	 * @param resource
	 * @param queryParams
	 * @param headers
	 * @param cookies
	 * @param value
	 * @return
	 */
	protected <T> T doPut(Class<T> clazz, String resource,
			MultivaluedMap<String, String> queryParams,
			Map<String, String> headers, List<Cookie> cookies, Object value) {
		cookies = this.checkAuth(cookies);
		ClientResponse resp = RestClient.put(ClientResponse.class, resource,
				queryParams, headers, cookies, value);
		T t = parseResponse(resp, clazz);
		resp.close();
		return t;
	}
	
	/**
	 * 
	 * @param clazz
	 * @param resource
	 * @param queryParams
	 * @param headers
	 * @param cookies
	 * @param value
	 * @return
	 */
	protected <T> T doPut(GenericType<T> clazz, String resource,
			MultivaluedMap<String, String> queryParams,
			Map<String, String> headers, List<Cookie> cookies, Object value) {
		cookies = this.checkAuth(cookies);
		ClientResponse resp = RestClient.put(ClientResponse.class, resource,
				queryParams, headers, cookies, value);
		T t = parseResponse(resp, clazz);
		resp.close();
		return t;
	}

	/**
	 * 
	 * @param clazz
	 * @param resource
	 * @param queryParams
	 * @param headers
	 * @param cookies
	 * @param value
	 * @return
	 */
	protected <T> T doDelete(Class<T> clazz, String resource,
			MultivaluedMap<String, String> queryParams,
			Map<String, String> headers, List<Cookie> cookies, Object value) {
		cookies = this.checkAuth(cookies);
		ClientResponse resp = RestClient.delete(ClientResponse.class, resource,
				queryParams, headers, cookies, value);
		T t = parseResponse(resp, clazz);
		resp.close();
		return t;
	}
	
	/**
	 * 
	 * @param clazz
	 * @param resource
	 * @param queryParams
	 * @param headers
	 * @param cookies
	 * @param value
	 * @return
	 */
	protected <T> T doDelete(GenericType<T> clazz, String resource,
			MultivaluedMap<String, String> queryParams,
			Map<String, String> headers, List<Cookie> cookies, Object value) {
		cookies = this.checkAuth(cookies);
		ClientResponse resp = RestClient.delete(ClientResponse.class, resource,
				queryParams, headers, cookies, value);
		T t = parseResponse(resp, clazz);
		resp.close();
		return t;
	}
	
	/**
	 * 
	 * @param resource
	 * @param name
	 * @param value
	 * @return
	 */
	protected String replace(String resource,String name,Object value){
		return resource.replaceAll("\\{" + name + "\\}", String.valueOf(value));
	}
}