package com.joe.plugin.manager;

import java.util.List;

import org.apache.commons.configuration.Configuration;

import com.joe.core.version.Name;
import com.joe.plugin.Extension;
import com.joe.plugin.Pluggable;
import com.joe.plugin.PluginRuntimeException;
import com.joe.plugin.excetion.PluginNotFoundException;

/**
 * 专门的插件工厂类
 * @author qiaolong
 *
 */
public abstract class SpecialPluginFactory extends PluginFactory{

	/**
	 * 
	 * @param conf
	 */
	public SpecialPluginFactory(Configuration conf) {
		super(conf);
	}

	/**
	 * 
	 * @param id 给定pluginname 和 version得到所有的扩展点
	 * @return
	 * @throws PluginNotFoundException
	 */
	public Extension[] getExtensions() throws PluginNotFoundException{
		return super.getExtensions(myName().getName(), myName().getVersion());
	}
	
	/**
	 * 
	 * @param pXpId 扩展点ID
	 * @return
	 * @throws PluginNotFoundException 
	 */
	public Extension[] getExtensions(String xPointId) throws PluginNotFoundException{
		return getExtensions(myName().getName(), myName().getVersion(), xPointId);
	}
	
	/**
	 * 
	 * @param extensionId 扩张ID
	 * @return
	 * @throws PluginNotFoundException
	 */
	public Extension getExtension(String extensionId) throws PluginNotFoundException{
		return getExtension(myName().getName(), myName().getVersion(),extensionId);
	}
	
	/**
	 * 
	 * @param xPointId
	 * @return
	 * @throws PluginNotFoundException
	 * @throws PluginRuntimeException
	 */
	public <T extends Pluggable> List<T> getExtensionInstances(Class<T> pointClass,String xPointId) throws PluginNotFoundException, PluginRuntimeException{
		return getExtensionInstances(pointClass, myName().getName(), myName().getVersion(), xPointId);
	}
	
	/**
	 * 
	 * @param pointClass
	 * @param extensionId
	 * @return
	 * @throws PluginNotFoundException
	 * @throws PluginRuntimeException
	 */
	public <T extends Pluggable> T getExtensionInstance(Class<T> pointClass,String extensionId) throws PluginNotFoundException, PluginRuntimeException{
		return getExtensionInstance(pointClass, myName().getName(), myName().getVersion(), extensionId);
	}
	
	/**
	 * 指定插件的名称
	 * @return
	 */
	protected abstract Name myName();
}
