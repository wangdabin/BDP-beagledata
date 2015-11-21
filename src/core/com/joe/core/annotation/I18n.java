package com.joe.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 国际化标准
 * @author Joe
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface I18n {
	
	/**
	 * 名称
	 * @return
	 */
	String name() default "";
	
	/**
	 * 类型
	 * @return
	 */
	String type() default "";
	
	/**
	 * 是否激活
	 * @return
	 */
	boolean enable() default true;
}
