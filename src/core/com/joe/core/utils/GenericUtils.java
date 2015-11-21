package com.joe.core.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 工具类，取得类中定义的泛型类
 * @author joe
 */
@SuppressWarnings("unchecked")
public class GenericUtils {
	private static final Log log = LogFactory.getLog(GenericUtils.class);
	private GenericUtils(){
	}
	
	public static Class getSuperClassGenericType(Class clazz){
		return getSuperClassGenericType(clazz, 0);
	}
	public static Class getSuperClassGenericType(Class clazz,int index){
		//取得该类的实体类型
		Type genType = clazz.getGenericSuperclass();
		//判断该类是否是泛型类
		if(!(genType instanceof ParameterizedType)) {
			log.warn(clazz.getSimpleName());
			return Object.class;
		}
		//返回该类的实际参数类型
		Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
		if( index >= params.length || index < 0){
			return Object.class;
		}
		
		if(!(params[index] instanceof Class)){
			return Object.class;
		}
		//返回该类的泛型类
		return (Class)params[index];
	}
}
