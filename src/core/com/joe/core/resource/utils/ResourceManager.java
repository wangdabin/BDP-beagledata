package com.joe.core.resource.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Path;

import org.apache.log4j.Logger;

import com.joe.core.exception.ExceptionMapperSupport;

/**
 * 资源管理者。。
 * @author Joe
 *
 */
public class ResourceManager {

	private static final Logger LOG = Logger.getLogger(ResourceManager.class);
	/**
	 * 
	 * @param classes
	 * @param callback
	 * @return
	 */
	public static final <T> List<T>  parse(Collection<Class<Object>> classes,CallBack<T> callback){
		List<T> results = new ArrayList<T>();
		for(Class<?> clazz : classes){
			if(clazz.equals(ExceptionMapperSupport.class)){ // 统一的异常处理程序
				LOG.debug("Skip class " + clazz + " is exception support");
				continue;
			}
			Path path = clazz.getAnnotation(Path.class);
			if(path == null){
				LOG.debug("Skip class " + clazz + " no path");
				continue;
			}
			results.add(callback.execute(clazz));
		}
		return results;
	}
}
