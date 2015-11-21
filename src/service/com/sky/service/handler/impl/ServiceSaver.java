package com.sky.service.handler.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.configuration.Configuration;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import com.joe.core.factory.BeanFactory;
import com.joe.host.vo.Host;
import com.sky.service.Service;
import com.sky.service.dao.ServiceStatusDao;
import com.sky.service.service.ServiceVOService;
import com.sky.service.vo.ServiceStatusVo;
import com.sky.service.vo.ServiceVo;

/**
 * 服务保存
 * @author qiaolong
 *
 */
@org.springframework.stereotype.Service("serviceSaver")
@Transactional(rollbackFor = RuntimeException.class)
public class ServiceSaver {

	@Resource(name = "serviceVOService")
	private ServiceVOService serviceVOService;
	
	@Resource(name = "serviceStatusDao")
	private ServiceStatusDao serviceStatusDao;
	
	public ServiceSaver(){
	}
	
	public ServiceSaver(Configuration conf){
		this.serviceVOService = BeanFactory.getBean("serviceVOService", ServiceVOService.class);
	}
	
	public List<ServiceVo> findByName(String name){
		return serviceVOService.findByName(name);
	}
	
	/**
	 * 保存服务
	 * @param service
	 */
	public void save(Service service){
		this.save(service, null);
	}
	
	/**
	 * 递归保存所有的服务
	 * @param service
	 * @param parent
	 */
	private void save(Service service,ServiceVo parent){
		ServiceVo vo = this.covertTo(service, parent);
		this.serviceVOService.add(vo);
		if(service.hasSubService()){
			for(Service subService : service.getSubService()){
				this.save(subService,vo);
			}
		}
	}
	
	/**
	 * 
	 * @param service
	 * @param parent
	 * @return
	 */
	private ServiceVo covertTo(Service service,ServiceVo parent){
		ServiceVo vo = new ServiceVo();
		vo.setName(service.getName());
		vo.setVersion(service.getVersion());
		vo.setInstallDir(service.getInstallDir());
		vo.setHosts(new HashSet<Host>(service.getHosts()));
		vo.setParent(parent);
		vo.setUserName(service.getOwner());
		return vo;
	}

	public ServiceVOService getServiceVOService() {
		return serviceVOService;
	}
	
	/**
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	public ServiceVo getServiceVO(String name,String version){
		return this.serviceVOService.get(name, version);
	}
	
	/**
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	public Set<Host> getServiceHosts(String name,String version){
		ServiceVo vo = this.getServiceVO(name, version);
		if(vo != null){
			return vo.getHosts();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<ServiceStatusVo> getServiceStatus(String name,String version){
		String hql = "from ServiceStatusVo where serviceName = ? and serviceVersion = ?";
		Query query = serviceStatusDao.createQuery(hql);
		query.setString(0, name);
		query.setString(1, version);
		return query.list();
	}
}
