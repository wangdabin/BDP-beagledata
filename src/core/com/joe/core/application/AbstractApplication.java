package com.joe.core.application;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;

import com.joe.core.utils.AppContextUtil;

/**
 * 
 * 工厂类负责管理dao、service、resource、i18n等
 * @author Joe
 *
 */
public abstract class AbstractApplication<A extends Annotation> extends Application{
	
	/**
	 * 类存储
	 */
	public final Set<Class<?>> classes = new HashSet<Class<?>>();
	
	/**
	 * 实体存储
	 */
	public AbstractApplication(Logger LOG,Class<A> clazz){
		this.init(LOG, clazz);
	}
	
	/**
	 * 查找指定annotation 的所有的类
	 * @param LOG
	 * @param clazz
	 * @return
	 */
	protected Set<Class<?>> findClass(Logger LOG,Class<? extends Annotation> clazz) {
		Set<Class<?>> classSet =  AppContextUtil.findClass(clazz);
		return classSet;
	}
	
	/**
	 * 保存类型
	 * @param type
	 * @param clazz
	 */
	protected void putResource(String type,Class<?> clazz){
		classes.add(clazz);
	}
	
	private final void init(Logger LOG,Class<A> clazz){
		LOG.debug("Find with annotation " + clazz);
		Set<Class<?>> classSet =  findClass(LOG, clazz);
		if(classSet != null && !classSet.isEmpty()){
			 for(Class<?> c : classSet){
				LOG.debug("Found clazz  " + c);
				Annotation ann = c.getAnnotation(clazz);
				A a = getAnn(ann); 
				String name = getName(a);
				String type = getType(a);
				boolean enable = isEnable(a);
				if(enable){
					LOG.debug("Will init resource " + name);
					putResource(type, c);
				}else{
					LOG.warn("Resource " + name + " is not enable");
				}
			 }
		}else{
			LOG.error("Resource is empty..");
			throw new RuntimeException("Resource is empty..");
		}
	}
	
	/**
	 * 
	 * @param ann
	 * @return
	 */
	protected abstract String getName(A ann);
	
	/**
	 * 
	 * @param ann
	 * @return
	 */
	protected abstract String getType(A ann);
	
	/**
	 * 
	 * @param ann
	 * @return
	 */
	protected abstract boolean isEnable(A ann);
	
	
	/**
	 * 
	 * @param ann
	 * @return
	 */
	private A getAnn(Annotation ann){
		return (A)ann;
	}
	
    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}
