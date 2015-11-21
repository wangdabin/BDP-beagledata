package com.sky.config;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * 配置项的基础类
 * @author Joe
 *
 */
public class ConfigUtil {
	
	public static final String UUID_KEY = "sky.conf.uuid";
	/**
	 * 
	 * @param conf
	 * @throws Exception
	 */
	public static final void updateConfig(Configuration conf) throws Exception {
		updateConfig((PropertiesConfiguration)conf);
	}
	
	/**
	 * 
	 * @param conf
	 * @throws Exception
	 */
	private static final void updateConfig(PropertiesConfiguration conf) throws Exception {
		conf.save();
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public static final void reload(Configuration conf) {
		reload((PropertiesConfiguration)conf);
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public static final void reload(PropertiesConfiguration conf)  {
		conf.reload();
	}
	
	
	private static void setUUID(Configuration conf) {
		UUID uuid = UUID.randomUUID();
		conf.setProperty(UUID_KEY, uuid.toString());
	}
	
	/**
	 * 
	 * @param resource
	 * @return
	 * @throws IOException
	 * @throws ConfigurationException 
	 */
	public static Configuration createConfig(String resource) throws IOException{
		try{
			PropertiesConfiguration pro = new PropertiesConfiguration(resource);
			pro.setReloadingStrategy(new FileChangedReloadingStrategy()); //设置配置文件自动加载
			return pro;
		}catch(Exception e){
			try {
				if(resource.startsWith("/")){
					PropertiesConfiguration pro = new PropertiesConfiguration(resource.replaceFirst("^/", ""));
					pro.setReloadingStrategy(new FileChangedReloadingStrategy()); //设置配置文件自动加载
					return pro;
				}
			} catch (ConfigurationException e1) {
			}
			throw new IOException(e);
		}
	}
	
	
	public static String getUUID(Configuration conf) {
		return conf.getString(UUID_KEY);
	}
}