package com.sky.service.handler.impl;

import java.util.List;

import com.joe.core.version.Name;
import com.sky.service.Service;
import com.sky.service.define.ConfigDefine;
import com.sky.service.define.EnvironmentDefine;
import com.sky.service.define.KeyValue;
import com.sky.service.define.ServiceDefine;
import com.sky.service.define.ServiceDefineManager;
import com.sky.service.handler.SupportsLoader;
import com.sky.service.utils.Constants;

/**
 * 
 * @author qiaolong
 *
 */
public class ConfigSupportLoader implements SupportsLoader{

	public ConfigSupportLoader(String resource){
		try{
			ServiceDefineManager.addResource(resource);
		}catch(Exception e){
			 throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<String> dependOn(Name name) {
		return getServiceDefine(name).dependOn();
	}

	@Override
	public List<String> supportModels(Name name) {
		return getServiceDefine(name).getModels();
	}

	@Override
	public List<Service> supportSubService(Name name) {
		return getServiceDefine(name).getSubServices();
	}
	
	@Override
	public List<KeyValue> supportBasicEnvironment(Name name) {
		return supportEnvironment(name,Constants.FAVORITE_BASIC);
	}

	@Override
	public List<KeyValue> supportAdvancedEnvironment(Name name) {
		return supportEnvironment(name,Constants.FAVORITE_ADVANCED);
	}

	/**
	 * 
	 * @param name
	 * @param environmentName
	 * @return
	 */
	private List<KeyValue> supportEnvironment(Name name,String environmentName) {
		EnvironmentDefine define = getServiceDefine(name).getEnvironment(environmentName);
		if(define != null){
			return define.getProps();
		}
		return null;
	}
	
	@Override
	public List<KeyValue> supportBasicConfig(Name name) {
		return supportConfig(name,Constants.FAVORITE_BASIC);
	}

	@Override
	public List<KeyValue> supportAdvancedConfig(Name name) {
		return supportConfig(name,Constants.FAVORITE_ADVANCED);
	}
	
	@Override
	public ServiceDefine getServiceDefine(Name name) {
		return ServiceDefineManager.getServiceDefine(name);
	}
	
	/**
	 * 
	 * @param name
	 * @param environmentName
	 * @return
	 */
	private List<KeyValue> supportConfig(Name name,String configName) {
		ConfigDefine define = getServiceDefine(name).getConfig(configName);
		if(define != null){
			return define.getProps();
		}
		return null;
	}
}
