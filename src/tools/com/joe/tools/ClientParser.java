package com.joe.tools;

import java.util.List;
import java.util.Map;

import com.joe.tools.define.ClassDefine;

/**
 * 
 * @author qiaolong
 *
 */
public interface ClientParser {

	/**
	 * 产生类定义
	 * @return
	 */
	List<ClassDefine> generateDefine();
	
	/**
	 * 产生配置项
	 * @return
	 */
	Map<String,Map<String,String>> generateRestConfig();
	
	/**
	 * 解析类型
	 * @return
	 */
	String getType();
}
