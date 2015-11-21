package com.joe.plugin.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;

import com.joe.plugin.Extension;
import com.joe.plugin.ExtensionPoint;
import com.joe.plugin.Pluggable;
import com.joe.plugin.PluginDescriptor;
import com.joe.plugin.PluginRepository;
import com.joe.plugin.PluginRuntimeException;
import com.joe.plugin.excetion.PluginNotFoundException;
import com.sky.config.ConfigAble;
import com.sky.config.Configed;

/**
 * 
 * 需要的配置 
 * 	plugin.auto-activation 插件自动激活，默认true
 *  plugin.folders 插件的所在的目录
 *  plugin.excludes 不包含的插件，正则匹配
 *  plugin.includes 包含的插件，正则匹配
 * 
 * @author qiaolong
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class PluginFactory extends Configed implements
		ConfigAble {

	private static PluginFactory factory;
	private PluginRepository pluginRepository;

	public PluginFactory(Configuration conf) {
		super(conf);
		this.pluginRepository = PluginRepository.get(conf);
	}
	
	/**
	 * 根据插入点Id，得到所有的扩展
	 * @param pXpId
	 * @return
	 */
	public Extension[] getExtensionsByXpId(String pXpId){
		ExtensionPoint ep = pluginRepository.getExtensionPoint(pXpId);
		if(ep != null){
			return ep.getExtensions();
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * @param pXpId
	 * @param extensionId
	 * @return
	 */
	public Extension getExtensionByXpId(String pXpId,String extensionId){
		Extension[] es = getExtensionsByXpId(pXpId);
		if(es != null){
			return getExtension(es, extensionId);
		}
		return null;
	}
	
	/**
	 * 
	 * @param pointClass
	 * @param pXpId
	 * @return
	 * @throws PluginRuntimeException
	 */
	public <T extends Pluggable> List<T> getInstancesByXpId(Class<T> pointClass,String pXpId) throws PluginRuntimeException{
		Extension[] es = getExtensionsByXpId(pXpId);
		if(es != null){
			List<T> list = new ArrayList<T>();
			for(Extension e : es){
				list.add((T) e.getExtensionInstance());
			}
			return list;
		}
		return null;
	}
	
	/**
	 * 
	 * @param pointClass
	 * @param pXpId
	 * @param extensionId
	 * @return
	 * @throws PluginRuntimeException 
	 */
	public <T extends Pluggable> T getInstanceByXpId(Class<T> pointClass,String pXpId,String extensionId) throws PluginRuntimeException{
		Extension e = getExtensionByXpId(pXpId,extensionId);
		if(e != null){
			return (T) e.getExtensionInstance();
		}
		return null;
	}
	
	/**
	 * 
	 * @param id 给定pluginname 和 version得到所有的扩展点
	 * @return
	 * @throws PluginNotFoundException
	 */
	public Extension[] getExtensions(String name,String version) throws PluginNotFoundException{
		PluginDescriptor pd = pluginRepository.getPluginDescriptor(name, version);
		if(pd != null){
			return pd.getExtensions();
		}else{
			throw PluginNotFoundException.getNotFound(name, version);
		}
	}
	
	/**
	 * 
	 * @param name
	 * @param version
	 * @param pXpId 扩展点ID
	 * @return
	 * @throws PluginNotFoundException 
	 */
	public Extension[] getExtensions(String name,String version,String xPointId) throws PluginNotFoundException{
		List<Extension> rEs = new ArrayList<Extension>();
		Extension[] es = getExtensions(name, version);
		for(Extension e : es){
			if(e.getTargetPoint().equals(xPointId)){
				rEs.add(e);
			}
		}
		if(!rEs.isEmpty()){
			return rEs.toArray(new Extension[rEs.size()]);
		}else{
			throw PluginNotFoundException.getNotFoundPoint(name, version, xPointId); 
		}
	}
	
	/**
	 * 
	 * @param name
	 * @param version
	 * @param extensionId 扩张ID
	 * @return
	 * @throws PluginNotFoundException
	 */
	public Extension getExtension(String name,String version,String extensionId) throws PluginNotFoundException{
		Extension[] es = getExtensions(name, version);
		Extension e = getExtension(es, extensionId);
		if(e != null){
			return e;
		}
		throw PluginNotFoundException.getNotFoundExtension(name, version, extensionId);
	}
	
	/**
	 * 
	 * @param name
	 * @param version
	 * @param xPointId
	 * @return
	 * @throws PluginNotFoundException
	 * @throws PluginRuntimeException
	 */
	public <T extends Pluggable> List<T> getExtensionInstances(Class<T> pointClass,String name,String version,String xPointId) throws PluginNotFoundException, PluginRuntimeException{
		List<T> instances = new ArrayList<T>();
		Extension[] es = getExtensions(name, version, xPointId);
		for(Extension e : es){
			instances.add((T) e.getExtensionInstance());
		}
		if(!instances.isEmpty()){
			return instances;
		}else{
			throw PluginNotFoundException.getNotFoundPoint(name, version, xPointId); 
		}
	}
	
	/**
	 * 
	 * @param name
	 * @param version
	 * @param xPointId
	 * @return
	 * @throws PluginNotFoundException
	 * @throws PluginRuntimeException
	 */
	public <T extends Pluggable> T getSingleInstances(Class<T> pointClass,String name,String version,String xPointId) throws PluginNotFoundException, PluginRuntimeException{
		List<T> instances = new ArrayList<T>();
		Extension[] es = getExtensions(name, version, xPointId);
		for(Extension e : es){
			instances.add((T) e.getExtensionInstance());
		}
		if(!instances.isEmpty() && instances.size() == 1){
			return instances.get(0);
		}else{
			throw PluginNotFoundException.getNotFoundPoint(name, version, xPointId); 
		}
	}
	
	/**
	 * 
	 * @param pointClass
	 * @param name
	 * @param version
	 * @param extensionId
	 * @return
	 * @throws PluginNotFoundException
	 * @throws PluginRuntimeException
	 */
	public <T extends Pluggable> T getExtensionInstance(Class<T> pointClass,String name,String version,String extensionId) throws PluginNotFoundException, PluginRuntimeException{
		Extension e = getExtension(name, version, extensionId);
		return (T) e.getExtensionInstance();
	}
	
	public void finalize() throws Throwable {
		this.pluginRepository.finalize();
	}
	
	/**
	 * plugin库
	 * @return
	 */
	public PluginRepository getPluginRepository() {
		return pluginRepository;
	}

	private Extension getExtension(Extension[] list, String id) {
		for (int i = 0; i < list.length; i++) {
			if (id.equals(list[i].getId())) {
				return list[i];
			}
		}
		return null;
	}
	
	public synchronized static PluginFactory getFactory(Configuration conf){
		if(factory == null){
			factory = new PluginFactory(conf);
		}
		return factory;
	}
}
