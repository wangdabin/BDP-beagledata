package com.joe.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 认证资源指定
 * @author Joe
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface SecurityResource{
	
	//是否已经初始化jdbc的配置
	public static final String USER_ADMIN_INITED = "sky.resource.inited.useradmin";
	
	//所有的提供的安全的资源
	public static final String RESOURCE = "/security/*";

	public static final String RESOURCE_PATH = "/security";
	
	/**
	 * 资源名称 
	 * @return
	 */
	String name() default "";
	
	/**
	 * 类型
	 * @return
	 */
	String type() default "auth";
	
	/**
	 * 是否激活
	 * @return
	 */
	boolean enable() default true;
}