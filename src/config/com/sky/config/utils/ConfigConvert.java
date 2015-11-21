package com.sky.config.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.joe.config.plugin.Config;
import com.sky.config.ConfigVO;
import com.sky.service.define.KeyValue;

/**
 * 
 * @author qiaolong
 *
 */
public class ConfigConvert {

	/**
	 * 转换工具
	 * @param conf
	 * @return
	 */
	public static final List<KeyValue> convertTo(Config conf){
		List<KeyValue> kvs = new ArrayList<KeyValue>();
		for(Iterator<String> iter = conf.getKeys();iter.hasNext();){
			String key = iter.next();
			String value = conf.getString(key);
			KeyValue kv = new KeyValue(key, value);
			kvs.add(kv);
		}
		return kvs;
	}
	
	/**
	 * 把keyvalue 添加到conf中
	 * @param conf
	 * @param kv
	 */
	public static final void addKeyValue(Config conf,KeyValue kv){
		List<String> values = kv.getValues();
		conf.setStrings(kv.getConfigKey(), values.toArray(new String[values.size()]));
	}
	
	/**
	 * 把keyvalue 集合添加到conf中
	 * @param conf
	 * @param kv
	 */
	public static final void addKeyValues(Config conf,List<KeyValue> kvs){
		for(KeyValue kv : kvs){
			List<String> values = kv.getValues();
			conf.setStrings(kv.getConfigKey(), values.toArray(new String[values.size()]));
		}
	}
	
	/**
	 * 把所有的value以逗号分割
	 * @param kv
	 * @return
	 */
	public static final String covertValues(KeyValue kv){
		return covertValues(kv.getValues());
	}
	
	/**
	 * 把所有的value以逗号分割
	 * @param kv
	 * @return
	 */
	public static final String covertValues(List<String> vs){
		if(vs != null){
			StringBuilder sb = new StringBuilder();
			for(String v : vs){
				sb.append(v).append(",");
			}
			sb.setLength(sb.length() - 1);
			return sb.toString();
		}
		return "";
	}
	
	public static final String[] covert(String value){
		if(value == null){
			return new String[0];
		}else{
			return value.split(",");
		}
	}
	
	/**
	 * 把configs 转成 keyvalues
	 * @param configs
	 * @return
	 */
	public static List<KeyValue> covert(List<ConfigVO> configs){
		if(configs != null && !configs.isEmpty()){
			List<KeyValue> kvs = new ArrayList<KeyValue>();
			for(ConfigVO config : configs){
				kvs.add(covert(config));
			}
			return kvs;
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * @param config
	 * @return
	 */
	public static KeyValue covert(ConfigVO config){
		if(config == null) return null;
		KeyValue kv = new KeyValue(config.getKey(), covert(config.getValue()));
		kv.setDescription(config.getDescription());
		return kv;
	}
}
