package com.sky.config.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.configuration.Configuration;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joe.core.factory.BeanFactory;
import com.sky.config.ConfigVO;
import com.sky.config.dao.ConfigDAO;

/**
 * 
 * @author qiaolong
 * 
 */
@Service("configService")
@Transactional(rollbackFor = RuntimeException.class)
public class ConfigServiceImpl implements ConfigService {

	@Resource(name = "configDAO")
	private ConfigDAO configDAO;

	public ConfigServiceImpl(){
	}
	
	public ConfigServiceImpl(Configuration conf){
		this.configDAO = BeanFactory.getBean("configDAO", ConfigDAO.class);
	}
	
	@Override
	public ConfigVO getConfig(String name, String version, String key) {
		return configDAO.get(new ConfigVO(name, version, key));
	}

	@Override
	public List<ConfigVO> getConfigs(String name, String version) {
		return configDAO.createCriteria(ConfigVO.class, Restrictions.eq("name", name),Restrictions.eq("version", version)).list();
	}

	@Override
	public List<ConfigVO> getConfigsByType(String name, String version,
			String type) {
		return getConfigsByField(name, version, "type", type);
	}

	@Override
	public List<ConfigVO> getConfigsByFavorite(String name, String version,
			String favorite) {
		return getConfigsByField(name, version, "favorite", favorite);
	}

	@Override
	public List<ConfigVO> getConfigsByFile(String name, String version,
			String file) {
		return getConfigsByField(name, version, "file", file);
	}
	

	@Override
	public List<ConfigVO> getConfigsByTypeAndFavorite(String name,
			String version, String favorite, String type) {
		return configDAO.createCriteria(ConfigVO.class, Restrictions.eq("name", name),Restrictions.eq("version", version),Restrictions.eq("favorite", favorite),Restrictions.eq("type", type)).list();
	}

	/**
	 * 
	 * @param name
	 * @param version
	 * @param field
	 * @param value
	 * @return
	 */
	private List<ConfigVO> getConfigsByField(String name,String version,String field,Object value){
		return configDAO.createCriteria(ConfigVO.class, Restrictions.eq("name", name),Restrictions.eq("version", version),Restrictions.eq(field, value)).list();
	}
	
	@Override
	public ConfigVO saveOrUpdate(String name, String version, String key,
			String value,String type,String file) {
		ConfigVO config = this.getConfig(name, version, key);
		if(config != null){ //如果不为空修改value值然后保存
			config.setValue(value);
		}else{// 如果所添加的配置项在原来中没那么默认是一个高级的配置项
			config = new ConfigVO(name,version,key);
			config.setValue(value);
			config.setType(type);
			config.setFavorite(ConfigVO.FAVORITE_ADVANCED);
			config.setFile(file);
			config.setCreateTime(System.currentTimeMillis());
		}
		this.saveOrUpdate(config);
		return config;
	}
	
	@Override
	public ConfigVO saveOrUpdate(String name, String version, String key,
			String value,String type,String favorite,String file) {
		ConfigVO config = this.getConfig(name, version, key);
		if(config != null){ //如果不为空修改value值然后保存
			config.setValue(value);
			config.setFavorite(favorite);
		}else{// 如果所添加的配置项在原来中没那么默认是一个高级的配置项
			config = new ConfigVO(name,version,key);
			config.setValue(value);
			config.setType(type);
			config.setFavorite(favorite);
			config.setFile(file);
			config.setCreateTime(System.currentTimeMillis());
		}
		this.saveOrUpdate(config);
		return config;
	}

	@Override
	public ConfigVO updateFavorite(String name, String version, String key,
			String favorite) {
		ConfigVO config = this.getConfig(name, version, key);
		if(config != null){
			config.setFavorite(favorite);
			this.saveOrUpdate(config);
			return config;
		}else{
			throw new RuntimeException("The name " + name + ",version " + version + " key " + key + " not found!");
		}
	}

	@Override
	public ConfigVO updateType(String name, String version, String key,
			String type) {
		ConfigVO config = this.getConfig(name, version, key);
		if(config != null){
			config.setType(type);
			this.saveOrUpdate(config);
			return config;
		}else{
			throw new RuntimeException("The name " + name + ",version " + version + " key " + key + " not found!");
		}
	}

	@Override
	public ConfigVO saveOrUpdate(ConfigVO config) {
		if(config.getCreateTime() == 0){
			config.setCreateTime(System.currentTimeMillis());
		}
		config.setUpdateTime(System.currentTimeMillis());
		configDAO.saveOrUpdate(config);
		return config;
	}

	@Override
	public List<String> getAllTypes(String name, String version) {
		return getAllByField(name, version, "type");
	}

	@Override
	public List<String> getAllFavorite(String name, String version) {
		return getAllByField(name, version, "favorite");
	}

	@Override
	public List<String> getAllFiles(String name, String version) {
		return getAllByField(name, version, "file");
	}
	
	/**
	 * 
	 * @param name
	 * @param version
	 * @param fieldName
	 * @return
	 */
	private List<String> getAllByField(String name,String version,String fieldName){
		return configDAO.createCriteria(ConfigVO.class, Restrictions.eq("name", name),Restrictions.eq("version", version)).setProjection(Property.forName(fieldName)).setProjection(Projections.distinct(Projections.property(fieldName)))
				.list();
	}
	
	/**
	 * 根据名称、版本、类获取配置文件
	 * @param name
	 * @param version
	 * @param type
	 * @return
	 */
	public String getFile(String name,String version,String favorite){
		return (String) configDAO.createCriteria(String.class, Restrictions.eq("name", name),Restrictions.eq("version", version),Restrictions.eq("favorite", favorite)).setProjection(Projections.distinct(Projections.property("file"))).uniqueResult();
	}

	@Override
	public ConfigVO remove(String name, String version, String key) {
		ConfigVO config = new ConfigVO(name, version, key);
		configDAO.remove(config);
		return config;
	}

	@Override
	public void saveOrUpdate(List<ConfigVO> configs) {
		for(ConfigVO config : configs){
			configDAO.saveOrUpdate(config);
		}
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml");
		ConfigService configService = context.getBean("configService", ConfigService.class);
		List<String> files = configService.getAllFiles("zookeeper", "cdh5.3.0");
		System.out.println(files);
	}
}
