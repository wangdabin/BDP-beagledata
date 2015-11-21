package com.joe.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 设置依赖
 * @author Joe
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface DependsOn {

	/**
	 * 指定需要是是真实的检查
	 * @return
	 */
	String[] keys() default {};
	
	/**
	 * 依赖于前缀
	 * @return
	 */
	String[] prefix() default{};
}
