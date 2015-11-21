package com.joe.monitor.callback.url;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.ObjectName;

import org.springframework.stereotype.Repository;

import com.joe.monitor.ObjectID;

/**
 * 
 * 存储在Map中的URL管理
 * @author qiaolong
 *
 */
@Repository(value = "URLManager")
public class MapURLManager implements URLManager{
	
	/**
	 * map的第一级是
	 */
	private static final Map<String,Map<String,Set<String>>> urls = createSynchronizedMap(new HashMap<String, Map<String,Set<String>>>());
	
	@Override
	public List<String> getByType(String type) {
		List<String> typeURLs = new ArrayList<String>();
		for(Map<String,Set<String>> tUrls : urls.values()){
			for(Set<String> url : tUrls.values()){
				typeURLs.addAll(url);
			}
		}
		return typeURLs;
	}
	
	@Override
	public List<String> get(String type, String name) {
		Map<String,Set<String>> tUrls = urls.get(type);
		if(tUrls != null){
			return new ArrayList<String>(tUrls.get(name));
		}
		return null;
	}

	@Override
	public List<String> get(ObjectName observedObject, String observedAttribute) {
		String type = ObjectID.getType(observedObject);
		String name = ObjectID.getName(observedObject);
		return get(type,name);
	}

	@Override
	public void register(String type, String name, String url) {
		Map<String,Set<String>> tUrls = urls.get(type);
		if(tUrls != null){
			Set<String> nameUrls = tUrls.get(name);
			if(nameUrls == null){
				nameUrls = createSynchronizedSet(new HashSet<String>());
				tUrls.put(name, nameUrls);
			}
			nameUrls.add(url);
		}else{
			tUrls = createSynchronizedMap(new HashMap<String, Set<String>>());
			urls.put(type, tUrls);
			Set<String> nameUrls = createSynchronizedSet(new HashSet<String>());
			tUrls.put(name, nameUrls);
			nameUrls.add(url);
		}
	}

	@Override
	public void remove(String type, String name, String url) {
		Map<String,Set<String>> tUrls = urls.get(type);
		if(tUrls != null){ //type下面不为空
			Set<String> nameUrls = tUrls.get(name);
			if(nameUrls != null){ // name不为空
				nameUrls.remove(url); // 删除指定url
				if(nameUrls.isEmpty()){ //删除后为空
					tUrls.remove(name); 
					if(tUrls.isEmpty()){
						urls.remove(type);
					}
				}
			}
		}
	}
	
	/**
	 * 创建一个安全的map
	 * @param map
	 * @return
	 */
	private static final <K,V> Map<K,V> createSynchronizedMap(Map<K,V> map){
		return Collections.synchronizedMap(map);
	}
	
	/**
	 * 一个线程安全的set
	 * @param set
	 * @return
	 */
	private static final <V> Set<V> createSynchronizedSet(Set<V> set){
		return Collections.synchronizedSet(set);
	}
}
