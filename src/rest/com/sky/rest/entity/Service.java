package com.sky.rest.entity;

import java.util.List;

import com.joe.host.vo.Host;
import com.sky.service.define.KeyValue;

/**
 * 客户端调用的服务接口
 * @author qiaolong
 *
 */
public interface Service {

	/**
	 * 服务名称
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 服务版本
	 * 
	 * @return
	 */
	String getVersion();

	/**
	 * 得到当前服务的模式
	 * 
	 * @return
	 */
	String getModel();

	/**
	 * 得到当前服务的子服务
	 * 
	 * @return
	 */
	List<Service> getSubService();

	/**
	 * 得到当前服务的父亲
	 * 
	 * @return
	 */
	Service getParent();

	/**
	 * 添加配置项
	 * 
	 * @param keyValue
	 */
	void addEnvironment(KeyValue keyValue);

	/**
	 * 删除配置项
	 * 
	 * @param key
	 * @return
	 */
	void removeEnvironment(String key);

	/**
	 * 添加配置项
	 * 
	 * @param keyValue
	 */
	void addConfig(KeyValue keyValue);

	/**
	 * 删除配置项
	 * 
	 * @param key
	 * @return
	 */
	void removeConfig(String key);

	/**
	 * 是否有子服务。。。
	 * 
	 * @return
	 */
	boolean hasSubService();

	/**
	 * 是否有父亲
	 * 
	 * @return
	 */
	boolean hasParent();

	/**
	 * 添加子服务
	 * 
	 * @param subService
	 */
	void addSubService(Service subService);

	/**
	 * 删除子服务
	 * 
	 * @param subService
	 */
	void removeSubService(Service subService);

	/**
	 * 得到服务所在的主机
	 * 
	 * @return
	 */
	List<Host> getHosts();

	/**
	 * 
	 * 给服务添加主机
	 * 
	 * @param host
	 */
	void addHost(Host host);

	/**
	 * 删除主机
	 * 
	 * @param host
	 */
	void removeHost(Host host);

	/**
	 * 设置服务的用户与密码
	 * 
	 * @param username
	 * @param password
	 */
	void setOwner(String username, String password);

	/**
	 * 拥有者
	 * 
	 * @return
	 */
	String getOwner();

	/**
	 * 删除本服务
	 */
	void remove();

	/**
	 * 启动服务
	 */
	void start();

	/**
	 * 停止服务
	 */
	void stop();
}
