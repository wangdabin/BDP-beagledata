package com.joe.core.factory;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.joe.core.utils.AppContextUtil;
import com.sky.config.ConfigAble;
import com.sky.config.Configed;

/**
 * 工厂类负责管理init等
 * @author Joe
 *
 * @param <A> Annotation
 * @param <T> 目标类
 */
public abstract class AbstractFactory<A extends Annotation,T> extends Configed{
	
	private Logger log;
	/**
	 * 类存储
	 */
	private Map<String,Class<T>> classes = new HashMap<String,Class<T>>();
	
	private Set<Class<T>> setClasses = new HashSet<Class<T>>();
	
	/**
	 * 实体存储
	 */
	private Map<String,T> instances = new HashMap<String,T>();
	
	public AbstractFactory(Configuration conf,Logger LOG,Class<A> clazz){
		super(conf);
		this.log = LOG;
		this.init(LOG, clazz);
	}
	
	/**
	 * 查找指定annotation 的所有的类
	 * @param LOG
	 * @param clazz
	 * @return
	 */
	protected Set<Class<?>> findClass(Logger LOG,Class<A> clazz) {
		Set<Class<?>> classSet =  AppContextUtil.findClass(clazz);
		return classSet;
	}
	
	/**
	 * 保存类型
	 * @param type
	 * @param clazz
	 */
	protected void putMyType(String name,Class<T> clazz){
		setClasses.add(clazz);
		Class<T> c = classes.get(name);
		if(c != null){
			log.error("Resource name " + name + " is already exists.");
			throw new RuntimeException("Resource name " + name + " is already exists.");
		}else{
			classes.put(name, clazz);
		}
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
					LOG.debug("Will init " + name);
					try {
						putMyType(name, (Class<T>) c);
					} catch (Exception e) {
						LOG.error("Init error " + name,e);
					}
				}else{
					LOG.info(name + " is not enable!");
				}
			 }
		}else{
			LOG.warn("Not find any " + clazz.getSimpleName());
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
	
	/**
	 * 
	 * @return
	 */
	public Collection<Class<T>> getAll(){
		return setClasses;
	}
	
	/**
	 * 
	 * @param clazz
	 * @param type
	 * @return
	 */
	public final T get(String type){
		Class<T> tClass = classes.get(type);
		try {
			T t = tClass.newInstance();
			if(t instanceof ConfigAble){
				((ConfigAble) t).setConf(getConf());
			}
			if(!instances.containsKey(type)){
				instances.put(type, t);
			}
			return t;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return null; 
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	public final T getSingleton(String type){
		T t = (T)instances.get(type);
		if(t != null){
			return t;
		}else{
			return get(type);
		}
	}
	
	public void destroy(){
		classes.clear();
		instances.clear();
		classes = null;
		instances = null;
	}

	public Logger getLog() {
		return log;
	}
}
