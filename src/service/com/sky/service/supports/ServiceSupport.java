package com.sky.service.supports;

import java.util.List;

import com.joe.core.support.Depends;
import com.joe.core.version.Name;
import com.joe.plugin.annotation.XPoint;
import com.sky.service.define.ConfigDefine;
import com.sky.service.define.Define;
import com.sky.service.define.EnvironmentDefine;

/**
 * 
 * 支持加载器
 * @author qiaolong
 *
 */
@XPoint
public interface ServiceSupport extends Name,Depends,Define{
	public final static String X_POINT_ID = ServiceSupport.class.getName();
	
	/**
	 * 支持的模式
	 * 如hdfs 支持Namenode的 HA模式
	 * hadoop支持 MR1 模式和YARN模式
	 * @return
	 */
	List<String> getModels();
	
	/**
	 * 需要安装的子服务，这个是服务端提供给客户端的。
	 * hadop 需要安装hdfs和 MRV1或者YARN
	 * @return
	 */
	List<ServiceSupport> getChildren();
	
	/**
	 * 支持的环境变量参数
	 * 如内存等信息
	 * @return
	 */
	EnvironmentDefine getBasicEnvironments();
	
	/**
	 * 支持的环境变量参数
	 * 如内存等信息
	 * @return
	 */
	EnvironmentDefine getAdvancedEnvironment();
	
	/**
	 * 支持的基础配置信息
	 * @return
	 */
	ConfigDefine getBasicConfig();
	
	/**
	 * 高级配置项
	 * @return
	 */
	ConfigDefine getAdvancedConfig();
}
