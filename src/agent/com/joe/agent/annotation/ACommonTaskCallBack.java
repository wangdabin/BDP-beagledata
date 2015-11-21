package com.joe.agent.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义任务执行的回调
 * @author qiaolong
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface ACommonTaskCallBack {
	String name() default "";
	String type() default "";
	boolean enable() default true;
}
