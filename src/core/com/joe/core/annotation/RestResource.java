package com.joe.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * REST 资源指定
 * @author Joe
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface RestResource{
	
	public static final String RESOURCE_PATH = "/ws";
	//所有的提供的服务的资源
	public static final String RESOURCE = "/ws/*";
	/**
	 * 资源名称 
	 * @return
	 */
	String name() default "";
	
	/**
	 * 类型
	 * @return
	 */
	String type() default "resource";
	
	/**
	 * 是否激活
	 * @return
	 */
	boolean enable() default true;
}