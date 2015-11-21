package com.sky.service.service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joe.core.service.EntityService;
import com.joe.host.vo.Host;
import com.sky.service.dao.ServiceDao;
import com.sky.service.vo.ServiceStatusVo;
import com.sky.service.vo.ServiceVo;

/**
 * 
 * @author qiaolong
 *
 */
@Service("serviceVOService")
@Transactional(rollbackFor = RuntimeException.class)
public class ServiceVOService implements EntityService<ServiceVo>{

	@Resource(name = "serviceDao")
	private ServiceDao serviceDao;
	
	public ServiceVOService(){
	}
	
	@Override
	public ServiceVo get(Serializable id) {
		return serviceDao.get(id);
	}
	
	
	public List<ServiceVo> findByName(String name){
		return this.serviceDao.findByName(name);
	}
	
	/**
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	public ServiceVo get(String name,String version){
		return get(new ServiceVo(name, version));
	}

	@Override
	public List<ServiceVo> getAll() {
		return serviceDao.getAll();
	}

	@Override
	public void add(ServiceVo newInstance) {
		serviceDao.saveOrUpdate(newInstance);
	}

	@Override
	public void remove(ServiceVo transientObject) {
		serviceDao.remove(transientObject);
	}

	@Override
	public void removeById(Serializable id) {
		serviceDao.removeById(id);
	}

	@Override
	public ServiceVo update(ServiceVo t) {
		serviceDao.saveOrUpdate(t);
		return t;
	}

	@Override
	public void addBatch(List<ServiceVo> instanceBatch) {
		for(ServiceVo vo : instanceBatch){
			serviceDao.save(vo);
		}
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml");
		ServiceVOService service = context.getBean("serviceVOService", ServiceVOService.class);
		ServiceVo serviceStatus = service.get("2", "2").getParent();
		System.out.println(serviceStatus.getName());
	}
	
}
