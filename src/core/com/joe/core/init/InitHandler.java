package com.joe.core.init;

import com.sky.config.ConfigAble;

/**
 * 初始化
 * @author Joe
 *
 */
public interface InitHandler extends ConfigAble{

	/**
	 * 当数据库加载成功后，执行的数据处理
	 */
	void doInit();
	
	/**
	 * 注册所有的初始化
	 * @param inits
	 */
	void register(InitAble init);
}
