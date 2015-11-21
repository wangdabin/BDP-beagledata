package com.joe.core.resource.utils;

import java.lang.reflect.Method;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;

import com.joe.core.annotation.SecurityResource;

/**
 * 
 * @author Joe
 *
 */
public class SecurityResourceCallback implements CallBack<ResourceBean> {

	private static final Logger LOG = Logger.getLogger(SecurityResourceCallback.class);
	@Override
	public ResourceBean execute(Class<?> clazz) {
		ResourceBean resourceBean = new ResourceBean();
	
		Path path = clazz.getAnnotation(Path.class);
		if(path == null){
			LOG.error("Class " + clazz + " not has path..");
			throw new RuntimeException("Class " + clazz + " not has path..");
		}
		SecurityResource resource = clazz.getAnnotation(SecurityResource.class);
		resourceBean.setEnable(resource.enable());
		String name = resource.name();
		
		String parentPath = SecurityResource.RESOURCE_PATH + path.value();
		resourceBean.setName(name);
		resourceBean.setClassName(clazz.getName());
		resourceBean.setBasePath(parentPath);
		
		Method[] methods = clazz.getMethods();
		for(Method method : methods){
			PathInfo pathInfo = parseMethod(method, parentPath);
			if(pathInfo != null){
				resourceBean.addPathInfo(pathInfo);
			}
		}
		return resourceBean;
	}
	
	/**
	 * 解析每个方法
	 * @param method
	 * @param parentPath
	 * @return
	 */
	private PathInfo parseMethod(Method method,String parentPath){
		PathInfo pathInfo = new PathInfo();
		pathInfo.setRealMethod(method);
		GET get = method.getAnnotation(GET.class);
		POST post = method.getAnnotation(POST.class);
		PUT put = method.getAnnotation(PUT.class);
		DELETE delete = method.getAnnotation(DELETE.class);
		if(get != null){
			pathInfo.setMethod(HttpMethod.GET);
		}else if(post != null){
			pathInfo.setMethod(HttpMethod.POST);
		}else if(put != null){
			pathInfo.setMethod(HttpMethod.PUT);
		}else if(delete != null){
			pathInfo.setMethod(HttpMethod.DELETE);
		}else{
			LOG.warn("Method " + method.getName() + " no rest method skip");
			return null;
		}
		Path methodPath = method.getAnnotation(Path.class);
		if(methodPath != null){
			String realPath = parentPath + methodPath.value();
			pathInfo.setPath(realPath);
		}else{
			pathInfo.setPath(parentPath);
		}
		return pathInfo;
	}
}
