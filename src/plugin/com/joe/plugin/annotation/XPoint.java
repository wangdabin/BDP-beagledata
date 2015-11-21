package com.joe.plugin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 插入点设置
 * 必须实现
 *  com.joe.plugin.Pluggable
 * @author qiaolong
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface XPoint {
	
	/**
	 * 默认是Class的simpleName
	 * @return
	 */
	String name() default "";
	
	/**
	 * 默认是Class的全名
	 * @return
	 */
	String id() default "";
	
	/**
	 * 默认是空值
	 * @return
	 */
	String schema() default "";
}
