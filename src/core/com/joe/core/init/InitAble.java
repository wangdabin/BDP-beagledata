package com.joe.core.init;

import com.sky.config.ConfigAble;

/**
 * 
 * @author Joe
 *
 */
public interface InitAble extends ConfigAble{

	/**
	 * 执行初始化
	 */
	void doInit();
	
	/**
	 * 销毁
	 */
	void destroy();
}
