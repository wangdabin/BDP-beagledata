package com.sky.service.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.joe.core.utils.CoreConfigUtils;
import com.joe.plugin.manager.TypePluginFactory;
import com.sky.service.Service;

/**
 * 服务工具类
 * @author qiaolong
 *
 */
public class ServiceUtils {
	
	//服务加载缓存
	private static final Map<String, Service> services = new HashMap<String, Service>();

	/**
	 * 指定父类
	 * @param name
	 * @param version
	 * @param subName
	 * @param parent
	 * @return
	 */
	public static final Service getService(String name, String version,String subName,Service parent) {
		try {
			if(StringUtils.isBlank(subName)){
				subName = name;
			}
			Service service = services.get(name + "-" + version + "-" + subName);
			if (service == null) {
				TypePluginFactory factory = getFactory();
				service = factory.getExtensionInstance(Service.class, name, version, subName);
				if(service != null){
					if(parent != null){
						service.setParent(parent);
					}
					service.init(); //执行服务初始化
					services.put(name + "-" + version + "-" + subName, service);
				}
			}
			return service;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 根据名称和版本得到服务
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	public static final Service getService(String name, String version,String subName) {
		return getService(name, version, subName, null);
	}
	
	public static final Service getService(String name, String version) {
		return getService(name, version, null);
	}

	/**
	 * 得到当前的插件工程类
	 * 
	 * @return
	 * @throws IOException
	 */
	public static final TypePluginFactory getFactory() throws IOException {
		return TypePluginFactory.getFactory(CoreConfigUtils.create(),Constants.TYPE);
	}
}
