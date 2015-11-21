package com.joe.core.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import com.joe.core.jaxb.JacksonConfigurator;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.json.JSONConfiguration;

/**
 * agent的客户端
 * 
 * @author Joe
 * 
 */
public class RestClient {

	private static final Logger LOG = Logger.getLogger(RestClient.class);
	
	private static final ClientConfig config = new DefaultClientConfig();
	private static final Client c;
	
	static{
		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		config.getClasses().add(JacksonConfigurator.class);
//		config.getClasses().add(JacksonJaxbJsonProvider.class);
		c = Client.create(config);
		c.addFilter(new BDPClientFilter());
	}
	
	/**
	 * 做查询操作
	 * 
	 * @param clazz
	 * @param resource
	 * @param configValue
	 */
	public static <T> T get(Class<T> clazz, String resource) {
		return get(clazz, resource, null, null, null);
	}
	
	/**
	 * 做查询操作
	 * 
	 * @param clazz
	 * @param resource
	 * @param configValue
	 */
	public static <T> T get(Class<T> clazz, String resource,MultivaluedMap<String,String> queryParams) {
		return get(clazz, resource, queryParams, null, null);
	}
	
	/**
	 * 做查询操作
	 * 
	 * @param clazz
	 * @param resource
	 * @param configValue
	 */
	public static <T> T get(Class<T> clazz, String resource,List<Cookie> cookies) {
		return get(clazz, resource, null, null, cookies);
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
	public static <T> T get(Class<T> clazz, String resource,MultivaluedMap<String,String> queryParams,Map<String,String> headers,List<Cookie> cookies) {
		RestCallBack<T> action = new GetRestCallBack<T>(clazz, queryParams, headers, cookies);
		return execeute(action, resource);
	}

	/**
	 * 做添加操作
	 * 
	 * @param clazz
	 * @param resource
	 * @param value
	 */
	public static <T> T post(Class<T> clazz, String resource, Object value) {
		return post(clazz, resource, null, null, null, value);
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
	public static <T> T post(Class<T> clazz, String resource, MultivaluedMap<String, String> queryParams){
		RestCallBack<T> action = new PostRestCallBack<T>(clazz, queryParams, null, null, null);
		return execeute(action, resource);
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
	public static <T> T post(Class<T> clazz, String resource, MultivaluedMap<String, String> queryParams,Map<String, String> headers, List<Cookie> cookies,Object value){
		RestCallBack<T> action = new PostRestCallBack<T>(clazz, queryParams, headers, cookies, value);
		return execeute(action, resource);
	}

	/**
	 * 做修改操作
	 * 
	 * @param clazz
	 * @param resource
	 * @param value
	 */
	public static <T> T put(Class<T> clazz, String resource, Object value) {
		return put(clazz,resource, null, null, null, value);
	}

	public static <T> T put(Class<T> clazz,String resource,
			MultivaluedMap<String, String> queryParams,
			Map<String, String> headers, List<Cookie> cookies,Object value){
		RestCallBack<T> action = new PutRestCallBack<T>(clazz, queryParams, headers, cookies, value);
		return execeute(action, resource);
	}
	
	/**
	 * 做删除操作
	 * 
	 * @param clazz
	 * @param resource
	 * @param value
	 */
	public static <T> T delete(Class<T> clazz, String resource, Object value) {
		return delete(clazz, resource, null, null, null, value);
	}
	
	public static <T> T delete(Class<T> clazz, String resource,MultivaluedMap<String, String> queryParams,
			Map<String, String> headers, List<Cookie> cookies,Object value){
		RestCallBack<T> action = new DeleteRestCallBack<T>(clazz, queryParams, headers, cookies, value);
		return execeute(action, resource);
	}

	/**
	 * 
	 * @param filter
	 */
	public static final void addClientFilter(ClientFilter filter){
		c.addFilter(filter);
	}
	
	/**
	 * 
	 * @param filter
	 */
	public static final void removeFilter(ClientFilter filter){
		c.removeFilter(filter);
	}
	
	/**
	 * 
	 */
	public static final void destory(){
		c.destroy();
	}
	
	
	/**
	 * 得到文本内容
	 * @param resp
	 * @return
	 */
	public static final String getContent(ClientResponse resp) {
		resp.bufferEntity();
		InputStream in = resp.getEntityInputStream();
		try {
			in.reset(); //重新定义
			byte[] b = new byte[in.available()];
			in.read(b);
			
			return new String(b);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				in.reset(); //重新定义
			} catch (IOException e) {
			}
		}
	}
	
	public static final String getContent(InputStream in){
		try {
//			in.reset(); //重新定义
			byte[] b = new byte[in.available()];
			in.read(b);
			
			return new String(b);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				in.reset(); //重新定义
			} catch (IOException e) {
			}
		}
	}
	
	/**
	 * 
	 * @param action
	 * @param resource
	 * @return
	 */
	private static final <T> T execeute(RestCallBack<T> action, String resource) {
		//添加一个filter来处理
//		c.addFilter(new ClientFilter() {
//		    private ArrayList<Object> cookies;
//
//		    @Override
//		    public ClientResponse handle(ClientRequest request) throws ClientHandlerException {
//		        if (cookies != null) {
//		            request.getHeaders().put("Cookie", cookies);
//		        }
//		        ClientResponse response = getNext().handle(request);
//		        if (response.getCookies() != null) {
//		            if (cookies == null) {
//		                cookies = new ArrayList<Object>();
//		            }
//		            // simple addAll just for illustration (should probably check for duplicates and expired cookies)
//		            cookies.addAll(response.getCookies());
//		        }
//		        return response;
//		    }
//		});
		
		//apache的客户端的事
//		ApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
//		config.getProperties().put(ApacheHttpClientConfig.PROPERTY_HANDLE_COOKIES, true);
//		ApacheHttpClient client = ApacheHttpClient.create(config);
		
		//https 可以考虑的事
//		ClientConfig config = new DefaultClientConfig();
//
//		SSLContext ctx = SSLContext.getInstance("SSL");
//
//		ctx.init(null, myTrustManager, null);
//
//		config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES,
//
//		                             new HTTPSProperties(hostnameVerifier, ctx));
//
//		Client client = Client.create(config);
		
		//org.springframework.context.annotation.CommonAnnotationBeanPostProcessor
		WebResource r = c.resource(resource);
		T t = action.doInRest(r);
		return t;
	}
}