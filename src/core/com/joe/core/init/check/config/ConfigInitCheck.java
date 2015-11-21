package com.joe.core.init.check.config;

import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.configuration.Configuration;

import com.joe.core.annotation.DependsOn;
import com.joe.core.annotation.InitResource;
import com.joe.core.init.check.InitCheck;
import com.joe.core.utils.CoreConfigUtils;
import com.sky.config.Configed;

/**
 * 通过配置文件的形势检查是否已经初始化
 * @author Joe
 *
 */
public class ConfigInitCheck extends Configed implements InitCheck{

	public ConfigInitCheck(Configuration conf){
		super(conf);
	}
	
	@Override
	public boolean inited() {
		return parsePrefix(InitResource.INIT_CONFIG_PREFIX);
	}

	@Override
	public boolean inited(DependsOn dependsOn) {
		String[] keys = dependsOn.keys(); //key .
		String[] prefixs = dependsOn.prefix(); //前缀
		for(String key : keys){
			if(!parseKey(key)){
				return false;
			}
		}
		for(String prefix : prefixs){
			if(!parsePrefix(prefix)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 解析前缀
	 * @param prefix
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean parsePrefix(String prefix){
		Iterator<String> keyIter = getConf().getKeys(prefix);
		while(keyIter.hasNext()){
			if(!parseKey(keyIter.next())){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 解析指定的key
	 * @param key
	 * @return
	 */
	private boolean parseKey(String key){
		return getConf().getBoolean(key);
	}
	
	public static void main(String[] args) throws IOException {
		InitCheck init = new ConfigInitCheck(CoreConfigUtils.create());
		System.out.println(init.inited());
	}
}
