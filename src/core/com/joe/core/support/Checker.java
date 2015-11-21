package com.joe.core.support;

import com.joe.core.version.Name;
import com.joe.plugin.annotation.XPoint;

/**
 * 检查是否已经安装，
 * 检查的依赖是否已经安装
 * @author qiaolong
 *
 */
@XPoint
public interface Checker {
	public final static String X_POINT_ID = Checker.class.getName();
	
	/**
	 * 检查是否已经安装
	 * @param service
	 * @return
	 */
	boolean checkInstalled(Name name);
	
	/**
	 * 依赖是否已经安装
	 * @param service
	 * @return
	 */
	boolean checkDepends(Depends service);
}
