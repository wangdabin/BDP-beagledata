package com.joe.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * REST 初始化资源指定
 * @author Joe
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface InitResource{
	
	//所有的提供的初始化的资源
	public static final String RESOURCE = "/init/*";
	public static final String RESOURCE_PATH = "/init";
	
	//
	public static final String INIT_CONFIG_PREFIX = "sky.resource.inited";
	
	/**
	 * 资源名称 
	 * @return
	 */
	String name() default "";
	
	/**
	 * 类型
	 * @return
	 */
	String type() default "init";
	
	/**
	 * 是否激活
	 * @return
	 */
	boolean enable() default true;
}