package com.sky.rest.entity;

import java.util.List;

import com.joe.core.version.Name;
import com.sky.service.define.KeyValue;

/**
 * 定义一个安装
 * @author qiaolong
 *
 */
public interface Install extends Name{

	/**
	 * 当前步骤名称
	 * @return
	 */
	String getCurrentName();
	
	/**
	 * 是否有下一步
	 * @return
	 */
	boolean hasNext();
	
	/**
	 * 是否有上一步
	 * @return
	 */
	boolean hasReverse();
	
	/**
	 * 执行下一步
	 */
	void next();
	
	/**
	 * 执行上一步
	 */
	void reverse();
	
	/**
	 * 当前步骤支持的基本配置
	 * @return
	 */
	List<KeyValue> supportsBasic();
	
	/**
	 * 当前步骤支持的基本配置
	 * @return
	 */
	List<KeyValue> supportsAdvanced();
	
	/**
	 * 添加配置
	 * @param keyValue
	 */
	void addKeyValue(KeyValue keyValue);
	
//	/**
//	 * 添加一套集合
//	 * @param keyValues
//	 */
//	void addKeyValue(List<KeyValue> keyValues);
	
	/**
	 * 执行安装
	 * @param callBackURL
	 */
	void install(String callBackURL);
}
