package com.joe.core.support;

import java.util.List;

/**
 * 需要依赖的
 * @author qiaolong
 *
 */
public interface Depends {
	
	/**
	 * 得到当前所有依赖
	 * @return
	 */
	List<String> dependOn();
}
