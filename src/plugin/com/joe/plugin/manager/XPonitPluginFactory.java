package com.joe.plugin.manager;

import java.util.List;

import org.apache.commons.configuration.Configuration;

import com.joe.plugin.Pluggable;
import com.joe.plugin.PluginRuntimeException;
import com.joe.plugin.excetion.PluginNotFoundException;

public abstract class XPonitPluginFactory<T extends Pluggable> extends SpecialPluginFactory{

	public XPonitPluginFactory(Configuration conf) {
		super(conf);
	}
	
	/**
	 * 
	 * @return
	 * @throws PluginNotFoundException
	 * @throws PluginRuntimeException
	 */
	public List<T> getExtensionInstances() throws PluginNotFoundException, PluginRuntimeException{
		return getExtensionInstances(xPointClass(),xPointId());
	}
	
	/**
	 * 
	 * @param extensionId
	 * @return
	 * @throws PluginNotFoundException
	 * @throws PluginRuntimeException
	 */
	public T getExtensionInstance(String extensionId) throws PluginNotFoundException, PluginRuntimeException{
		return getExtensionInstance(xPointClass(),extensionId);
	}

	/**
	 * 插入点ID
	 * @return
	 */
	protected abstract String xPointId();
	
	/**
	 * 插入点的类
	 * @return
	 */
	protected abstract Class<T> xPointClass();
}
