package com.sky.config.service;

import java.util.List;

import com.sky.config.ConfigVO;

/**
 * 
 * @author qiaolong
 *
 */
public interface ConfigService {

	/**
	 * 根据key得到配置项
	 * @param name
	 * @param version
	 * @param key
	 * @return
	 */
	ConfigVO getConfig(String name,String version,String key);
	
	/**
	 * 根据服务得到配列表
	 * @param name
	 * @param version
	 * @return
	 */
	List<ConfigVO> getConfigs(String name,String version);
	
	/**
	 * 根据类型的到所有的配置项
	 * @param name
	 * @param version
	 * @param type
	 * @return
	 */
	List<ConfigVO> getConfigsByType(String name,String version,String type);
	
	/**
	 * 根据收藏夹获取所有的配置项
	 * @param name
	 * @param version
	 * @param favorite
	 * @return
	 */
	List<ConfigVO> getConfigsByFavorite(String name,String version,String favorite);
	
	/**
	 * 根据收藏夹和类型获取所有的配置
	 * @param name
	 * @param version
	 * @param favorite
	 * @param type
	 * @return
	 */
	List<ConfigVO> getConfigsByTypeAndFavorite(String name,String version,String favorite,String type);
	
	/**
	 * 根据文件获取所有的配置项
	 * @param name
	 * @param version
	 * @param file
	 * @return
	 */
	List<ConfigVO> getConfigsByFile(String name,String version,String file);
	
	/**
	 * 更新和保存配置项
	 * @param name
	 * @param version
	 * @param key
	 * @param value
	 * @return
	 */
	ConfigVO saveOrUpdate(String name,String version,String key,String value,String type,String file);
	
	/**
	 * 指定收藏夹和类型的配置项
	 * @param name
	 * @param version
	 * @param key
	 * @param value
	 * @param type
	 * @param favorite
	 * @return
	 */
	ConfigVO saveOrUpdate(String name, String version, String key,String value,String type,String favorite,String file);
	
	/**
	 * 更细配置项的收藏夹
	 * @param name
	 * @param version
	 * @param key
	 * @param favorite
	 * @return
	 */
	ConfigVO updateFavorite(String name,String version,String key,String favorite);
	
	/**
	 * 更新类型
	 * @param name
	 * @param version
	 * @param key
	 * @param type
	 * @return
	 */
	ConfigVO updateType(String name,String version,String key,String type);
	
	/**
	 * 更新和保存配置项
	 * @param config
	 * @return
	 */
	ConfigVO saveOrUpdate(ConfigVO config);
	
	/**
	 * 批量保存
	 * @param configs
	 */
	void saveOrUpdate(List<ConfigVO> configs);
	
	/**
	 * 得到服务的所有类型
	 * @param name
	 * @param version
	 * @return
	 */
	List<String> getAllTypes(String name,String version);
	
	/**
	 * 得到所有的收藏夹
	 * @param name
	 * @param version
	 * @return
	 */
	List<String> getAllFavorite(String name,String version);
	
	/**
	 * 得到所有的配置文件
	 * @param name
	 * @param version
	 * @return
	 */
	List<String> getAllFiles(String name,String version);
	
	/**
	 * 删除
	 * @param name
	 * @param version
	 * @param key
	 * @return
	 */
	ConfigVO remove(String name,String version,String key);
}
