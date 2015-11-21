package com.sky.service.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.joe.core.dao.HibernateGenericDao;
import com.sky.service.vo.ServiceVo;

@Repository("serviceDao")
public class ServiceDao extends HibernateGenericDao<ServiceVo>{
	
	/**
	 * 根据服务名称和version获取当前服务
	 * @param name
	 * @param version
	 * @return
	 */
	public ServiceVo get(String name,String version){
		return get(new ServiceVo(name,version));
	}
	
	/**
	 * 根据服务名称获取
	 * @param name
	 * @return
	 */
	public List<ServiceVo> findByName(String name){
		return findBy("name", name);
	}
}
