package com.sky.service;

import java.util.List;

import com.joe.core.callback.ScheduleCallBack;
import com.joe.core.support.CommandWorker;
import com.joe.core.support.ConfigApplyer;
import com.joe.core.support.Deployed;
import com.joe.core.version.Name;
import com.joe.host.vo.Host;
import com.joe.plugin.Pluggable;
import com.joe.plugin.annotation.XPoint;
import com.sky.config.ConfigAble;
import com.sky.service.define.KeyValue;
import com.sky.service.vo.ServiceStatusVo;

/**
 * favorite 收藏夹
 * @author qiaolong
 *
 */
@XPoint
public interface Service extends Pluggable,Deployed,Name,ConfigAble{
	public final static String X_POINT_ID = Service.class.getName();
	
	/**
	 * 初始化，检查当前服务是否已经安装
	 * 为所有的数据从数据库中加载并赋值
	 */
	void init();
	
	/**
	 * 设置安装目录
	 * @param installDir
	 */
	void setInstallDir(String installDir);
	
	/**
	 * 安装目录，这个目录是上层目录
	 * @return
	 */
	String getInstallDir();
	
	/**
	 * 配置文件路径，是相对于服务的安装目录
	 * @return
	 */
	String getConfigDir();
	
	/**
	 * 得到真实的安装目录
	 * @return
	 */
	String getRealInstallDir();
	
	/**
	 * 得到当前服务的模式
	 * @return
	 */
	String getModel();
	
	/**
	 * 得到当前服务的子服务
	 * @return
	 */
	List<Service> getSubService();
	
	/**
	 * 得到当前服务的父亲
	 * @return
	 */
	Service getParent();
	
	/**
	 * 设置父类
	 * @param parent
	 */
	void setParent(Service parent);
	
	/**
	 * 得到所有的配置信息
	 * @return
	 */
	List<KeyValue> getAllEnvironment();
	
	/**
	 * 得到基本配置信息
	 * @return
	 */
	List<KeyValue> getBasicEnvironment();
	
	/**
	 * 得到高级配置信息
	 * @return
	 */
	List<KeyValue> getAdvancedEnvironment();
	
	/**
	 * 根据收藏夹的名称得到其中的配置项
	 * @param favoriteName
	 * @return
	 */
	List<KeyValue> getFavoriteEnvironment(String favoriteName);
	
	/**
	 * 添加配置项
	 * @param keyValue
	 */
	void addEnvironment(KeyValue keyValue);
	
	/**
	 * 添加基本的配置项
	 * @param keyValue
	 */
	void addBasicEnvironment(KeyValue keyValue);
	
	/**
	 * 添加高级配置项
	 */
	void addAdvancedEnvironment(KeyValue keyValue);
	
	/**
	 * 指定收藏夹的名称添加配置项
	 * @param favoriteName
	 * @param keyValue
	 */
	void addFavoriteEnvironment(String favoriteName,KeyValue keyValue);
	
	/**
	 * 删除配置项
	 * @param key
	 * @return
	 */
	KeyValue removeEnvironment(String key);
	
	/**
	 * 得到所有的配置信息
	 * @return
	 */
	List<KeyValue> getAllConfig();
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	KeyValue getConfig(String key);
	
	/**
	 * 得到基本配置信息
	 * @return
	 */
	List<KeyValue> getBasicConfig();
	
	/**
	 * 得到高级配置信息
	 * @return
	 */
	List<KeyValue> getAdvancedConfig();
	
	/**
	 * 根据收藏夹的名称得到其中的配置项
	 * @param favoriteName
	 * @return
	 */
	List<KeyValue> getFavoriteConfig(String favoriteName);
	
	/**
	 * 添加配置项
	 * @param keyValue
	 */
	void addConfig(KeyValue keyValue);
	
	/**
	 * 添加配置项
	 * @param keyValue
	 */
	void addConfig(List<KeyValue> keyValue);
	
	/**
	 * 添加基本的配置项
	 * @param keyValue
	 */
	void addBasicConfig(KeyValue keyValue);
	
	/**
	 * 添加高级配置项
	 */
	void addAdvancedConfig(KeyValue keyValue);
	
	/**
	 * 指定收藏夹的名称添加配置项
	 * @param favoriteName
	 * @param keyValue
	 */
	void addFavoriteConfig(String favoriteName,KeyValue keyValue);
	
	/**
	 * 删除配置项
	 * @param key
	 * @return
	 */
	KeyValue removeConfig(String key);
	
	/**
	 * 是否有子服务。。。
	 * @return
	 */
	boolean hasSubService();
	
	/**
	 * 是否有父亲
	 * @return
	 */
	boolean hasParent();
	
	/**
	 * 添加子服务
	 * @param subService
	 */
	void addSubService(Service subService);
	
	/**
	 * 删除子服务
	 * @param subService
	 */
	void removeSubService(Service subService);
	
	/**
	 * 包括子服务的所有主机
	 * @return
	 */
	List<Host> getAllHosts();
	
	/**
	 * 得到服务所在的主机
	 * @return
	 */
	List<Host> getHosts();
	
	/**
	 * 得到服务的状态和主机信息
	 * @return
	 */
	List<ServiceStatusVo> getServiceStatus(String name,String version);
	
	/**
	 * 
	 * 给服务添加主机
	 * @param host
	 */
	void addHost(Host host);
	
	/**
	 * 删除主机
	 * @param host
	 */
	void removeHost(Host host);
	
	/**
	 *	设置服务的用户与密码
	 * @param username
	 * @param password
	 */
	void setOwner(String username,String password);
	
	void setUsername(String username);
	void setPassword(String password);
	
	/**
	 * 拥有者
	 * @return
	 */
	String getOwner();
	
	/**
	 * 安装服务
	 */
	void install(ScheduleCallBack callBack);
	
	/**
	 * 删除本服务
	 */
	void remove();
	
	/**
	 * 启动服务
	 */
	void start();
	
	/**
	 * 启动服务
	 */
	void start(Host host);
	
	/**
	 * 停止服务
	 */
	void stop();
	
	/**
	 * 停止服务
	 */
	void stop(Host host);
	
	/**
	 * 命令执行者
	 * @return
	 */
	CommandWorker getCommandWorker();
	
	/**
	 * 配置应用器
	 * @return
	 */
	ConfigApplyer getConfigApplyer();
	
	/**
	 * 是否已经安装
	 * @return
	 */
	boolean installed();
	
	/**
	 * 应用配置
	 */
	void applyConfig();
}
