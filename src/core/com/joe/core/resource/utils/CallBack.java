package com.joe.core.resource.utils;

/**
 * 
 * 处理REST资源的callback
 * com.joe.core.resource.utils.InitResourceCallback
 * com.joe.core.resource.utils.RestResourceCallback
 * com.joe.core.resource.utils.SecurityResourceCallback
 * 
 * @author Joe
 *
 * @param <T>
 */
public interface CallBack<T> {
	T execute(Class<?> clazz);
}
