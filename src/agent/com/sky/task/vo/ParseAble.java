package com.sky.task.vo;

/**
 * 可以解析的
 * @author qiaolong
 *
 */
public interface ParseAble {
	
	/**
	 * 解析类型，通过解析类型得到对应的解析器
	 * @return
	 */
	String getType();
	
	/**
	 * 任务id
	 * @return
	 */
	long getId();
}
