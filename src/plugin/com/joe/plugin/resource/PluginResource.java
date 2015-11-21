package com.joe.plugin.resource;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.joe.core.utils.CoreConfigUtils;
import com.joe.core.vo.ReCode;
import com.joe.core.vo.ReCode.Data;
import com.joe.plugin.Pluggable;
import com.joe.plugin.manager.TypePluginFactory;
import com.sky.service.utils.Constants;

/**
 * 
 * @author qiaolong
 *
 */
public class PluginResource {

	private static final Map<String,Pluggable> instances = createSynchronizedMap(new HashMap<String,Pluggable>());
	
	/**
	 * 创建返回值
	 * 
	 * @param key
	 * @param msg
	 * @return
	 */
	protected ReCode createReCode(String key, String msg) {
		ReCode re = new ReCode();
		re.setData(new Data(key));
		re.setErrcode(Constants.NOT_ERROR);
		re.setRet(Constants.RET_SUCCESS);
		re.setMsg(msg);
		return re;
	}
	
	/**
	 * 根据名称和版本得到服务
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	protected <T extends Pluggable> T getInstance(Class<T> clazz,String type,String name, String version,String xpId) {
		try {
			T t = (T) instances.get(type + "-" + name + "-" + version + "-" + xpId);
			if (t == null) {
				TypePluginFactory factory = getFactory(type);
				t = factory.getSingleInstances(clazz, name,version, xpId);
				instances.put(type + "-" + name + "-" + version + "-" + xpId, t);
			}
			return t;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 创建一个安全的map
	 * @param map
	 * @return
	 */
	private static final <K,V extends Pluggable> Map<K,V> createSynchronizedMap(Map<K,V> map){
		return Collections.synchronizedMap(map);
	}
	
	/**
	 * 得到当前的插件工程类
	 * 
	 * @return
	 * @throws IOException
	 */
	protected TypePluginFactory getFactory(String type) throws IOException {
		return TypePluginFactory.getFactory(CoreConfigUtils.create(),type);
	}
}
