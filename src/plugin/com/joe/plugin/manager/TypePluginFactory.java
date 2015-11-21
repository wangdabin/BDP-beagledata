package com.joe.plugin.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;

import com.joe.plugin.PluginDescriptor;

/**
 * 专门的插件工厂类
 * @author qiaolong
 *
 */
public class TypePluginFactory extends PluginFactory{

	private static TypePluginFactory factory;
	private String type;
	
	/**
	 * 
	 * @param conf
	 */
	private TypePluginFactory(Configuration conf,String type) {
		super(conf);
		this.type = type;
	}

	/**
	 * 类型所有的支持
	 * @return
	 */
	public Map<String, List<String>> getSupports(){
		Map<String, List<String>> supports = new HashMap<String, List<String>>();
		PluginDescriptor[] pds = getPluginRepository().getPluginDescriptorByType(type);
		for(PluginDescriptor pd : pds){
			String name = pd.getName();
			String version = pd.getVersion();
			List<String> versions = supports.get(name);
			if(versions == null){
				versions = new ArrayList<String>();
				supports.put(name, versions);
			}
			versions.add(version);
		}
		return supports;
	}
	
	/**
	 * 根据名称获取所有的版本
	 * @param name
	 * @return
	 */
	public List<String> getSupports(String name){
		return getSupports().get(name);
	}
	
	/**
	 * 得到工厂类
	 * @param conf
	 * @param type
	 * @return
	 */
	public static TypePluginFactory getFactory(Configuration conf,String type){
		if(factory == null){
			factory = new TypePluginFactory(conf, type);
		}
		return factory;
	}
}
