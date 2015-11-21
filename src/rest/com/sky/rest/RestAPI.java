package com.sky.rest;

import java.util.List;
import java.util.Map;

import com.sky.config.ConfigAble;
import com.sky.rest.entity.Install;
import com.sky.rest.entity.Service;

/**
 * rest客户端调用是用的通用类
 * @author qiaolong
 *
 */
public interface RestAPI extends ConfigAble{

	/**
	 * 支持的服务：
	 * key是服务的名称、list是版本列表
	 * @return
	 */
	Map<String,List<String>> supportServices();
	
	/**
	 * 根据名称和版本号创建一个服务
	 * @param name
	 * @param version
	 * @return
	 */
	Service createService(String name,String version);
	
	/**
	 * 创建一个服务安装
	 * @param name
	 * @param version
	 * @return
	 */
	Install serviceInstall(String name,String version);
}
