package com.joe.core.utils;

import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.sky.config.ConfigUtil;

/**
 * 核心服务配置通用类
 * @author Joe
 *
 */
public class CoreConfigUtils extends ConfigUtil{

	private static final String RESOURCE_BASE= "config.properties";
	
	private static Configuration conf;
	
	/**
	 * 
	 * @param conf
	 * @throws Exception
	 */
	public static final void updateConfig() throws Exception {
		updateConfig((PropertiesConfiguration)conf);
	}
	/**
	 * 基本配置文件
	 * @return
	 * @throws ConfigurationException 
	 * @throws IOException 
	 */
	public static Configuration create() throws IOException {
		if(conf == null){
			conf = createConfig(RESOURCE_BASE);
		}
		return conf;
	}

	public static final void reload() {
		reload(conf);
	}
}
