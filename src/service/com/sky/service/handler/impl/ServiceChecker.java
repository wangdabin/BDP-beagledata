package com.sky.service.handler.impl;

import java.util.List;

import org.apache.commons.configuration.Configuration;

import com.joe.core.factory.BeanFactory;
import com.joe.core.support.AbstractChecker;
import com.joe.core.support.Depends;
import com.joe.core.version.Name;
import com.sky.service.dao.ServiceDao;
import com.sky.service.vo.ServiceVo;

/**
 * 检查是否安装
 * @author qiaolong
 *
 */
public class ServiceChecker extends AbstractChecker {

	private ServiceDao serviceDao;
	public ServiceChecker(){
		this(null);
	}
	
	public ServiceChecker(Configuration conf){
		super(conf);
		this.serviceDao = BeanFactory.getBean("serviceDao", ServiceDao.class);
	}
	
	@Override
	public boolean checkInstalled(Name name) {
		List<ServiceVo> vos = serviceDao.findByName(name.getName());
		return vos != null && !vos.isEmpty();
	}

	@Override
	public boolean checkDepends(Depends depends) {
		List<String> services = depends.dependOn();
		for(String service : services){
			List<ServiceVo> vos = serviceDao.findByName(service);
			if(vos == null || vos.isEmpty()){
				return false;
			}
		}
		return true;
	}
}
