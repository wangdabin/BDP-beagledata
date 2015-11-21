package com.joe.core.init.check;

import com.joe.core.annotation.DependsOn;
import com.sky.config.ConfigAble;

/**
 * 检查是否服务已经初始化
 * @author Joe
 *
 */
public interface InitCheck extends ConfigAble{

	/**
	 * 检查是否已经初始化
	 * @return
	 */
	boolean inited();
	
	/**
	 * 检查依赖是否已经初始化
	 * @param dependsOn
	 * @return
	 */
	boolean inited(DependsOn dependsOn);
}
