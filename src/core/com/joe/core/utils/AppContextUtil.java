package com.joe.core.utils;

import java.util.Set;

import net.hasor.core.AppContext;
import net.hasor.core.context.HasorFactory;

/**
 * 
 * @author Joe
 *
 */
public class AppContextUtil {

	private static AppContext appContext;
	
	/**
	 * 得到 AppContext
	 * @return
	 */
	public static final AppContext createAppContext(){
		if(appContext == null){
			appContext = HasorFactory.createAppContext();
		}
		return appContext;
	}
	
	/**
	 * 找到所有类的子类或者所有被注解的类
	 * @param clazz
	 * @return
	 */
	public static Set<Class<?>> findClass(Class<?> clazz){
		return createAppContext().findClass(clazz);
	}
}
