package com.sky.service.handler;

import java.util.List;

import com.joe.core.version.Name;
import com.joe.plugin.annotation.XPoint;
import com.sky.service.Service;
import com.sky.service.define.KeyValue;
import com.sky.service.define.ServiceDefine;

/**
 * 
 * 支持加载器
 * @author qiaolong
 *
 */
@XPoint
public interface SupportsLoader {
	public final static String X_POINT_ID = SupportsLoader.class.getName();
	
	/**
	 * 依赖的服务列表
	 * @return
	 */
	List<String> dependOn(Name name);
	
	/**
	 * 支持的模式
	 * 如hdfs 支持Namenode的 HA模式
	 * hadoop支持 MR1 模式和YARN模式
	 * @return
	 */
	List<String> supportModels(Name name);
	
	/**
	 * 需要安装的子服务，这个是服务端提供给客户端的。
	 * hadop 需要安装hdfs和 MRV1或者YARN
	 * @return
	 */
	List<Service> supportSubService(Name name);
	
	/**
	 * 支持的环境变量参数
	 * 如内存等信息
	 * @return
	 */
	List<KeyValue> supportBasicEnvironment(Name name);
	
	/**
	 * 支持的环境变量参数
	 * 如内存等信息
	 * @return
	 */
	List<KeyValue> supportAdvancedEnvironment(Name name);
	
	/**
	 * 支持的基础配置信息
	 * @return
	 */
	List<KeyValue> supportBasicConfig(Name name);
	
	/**
	 * 高级配置项
	 * @return
	 */
	List<KeyValue> supportAdvancedConfig(Name name);
	
	/**
	 * 得到当前服务的定义
	 * @param name
	 * @return
	 */
	ServiceDefine getServiceDefine(Name name);
}
