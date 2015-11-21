package com.sky.service.handler;

import com.sky.service.Service;

/**
 * 配置项修改
 * @author qiaolong
 *
 */
public interface ConfigHandler {

	/**
	 * 应用配置
	 * @param service
	 */
	void applyConfig(Service service);
}
