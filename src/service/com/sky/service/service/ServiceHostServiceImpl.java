package com.sky.service.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.joe.core.version.Named;
import com.joe.system.vo.SysDictionary;
import com.joe.system.vo.SysDictionaryDao;
import com.sky.service.dao.ServiceDao;
import com.sky.service.dao.ServiceHostDao;
import com.sky.service.vo.AllServiceSummary;
import com.sky.service.vo.ServiceHost;
import com.sky.service.vo.ServiceSummary;
import com.sky.service.vo.ServiceVo;
/**
 * 
 * @author lifei
 * 
 */
@Service("serviceHostService")
public class ServiceHostServiceImpl implements ServiceHostService {
	
	public static final Logger LOG = Logger.getLogger(ServiceHostServiceImpl.class);

	@Resource(name = "serviceHostDao")
	private ServiceHostDao serviceHostDao;
	
	@Resource(name = "serviceDao")
	private ServiceDao serviceDao;
	
	@Resource(name = "sysDictionaryDao")
	private SysDictionaryDao sysDictionaryDao;
	
	@Override
	public ServiceHost get(Serializable id) {
		return serviceHostDao.get(id);
	}

	@Override
	public List<ServiceHost> getAll() {
		return serviceHostDao.getAll();
	}

	@Override
	public void add(ServiceHost newInstance) {
		serviceHostDao.save(newInstance);
	}

	@Override
	public void remove(ServiceHost transientObject) {
		serviceHostDao.remove(transientObject);
	}

	@Override
	public void removeById(Serializable id) {
		serviceHostDao.removeById(id);
	}

	@Override
	public ServiceHost update(ServiceHost t) {
		serviceHostDao.saveOrUpdate(t);
		return t;
	}

	@Override
	public void addBatch(List<ServiceHost> instanceBatch) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("static-access")
	@Override
	public AllServiceSummary findAllServiceSummary() {
		int allActive = 0;
		int allDead = 0;
		SysDictionary hostsquence = sysDictionaryDao.findUniqueBy("dictName", "index.services");
		String dictValue = hostsquence.getDictValue();
		List<String> sqList = new ArrayList<String>();
		if (!StringUtils.isEmpty(dictValue)) {
			sqList = Arrays.asList(dictValue.split(","));
		}
		List<ServiceVo> serviceList = serviceDao.getAll();
		List<ServiceSummary> sortServices = new LinkedList<ServiceSummary>();
		Map<String,ServiceSummary> map = new HashMap<String,ServiceSummary>();
		for (ServiceVo serviceVo : serviceList) {
			int active = 0;
			int dead = 0;
//			for (ServiceHost serviceHost : serviceVo.getHosts()) {
//				if (serviceHost.STATUS_ACTIVE == serviceHost.getStatus() ) {
//					active++;
//					allActive++;
//				} else {
//					dead++;
//					allDead++;
//				}
//			}
			ServiceSummary ss = new ServiceSummary();
			if (dead != 0) {
				SysDictionary policeNum = sysDictionaryDao.findUniqueBy("dictName", "police."+serviceVo.getName());
				if (policeNum != null) {
					if (dead < Integer.parseInt(StringUtils.isEmpty(policeNum.getDictValue()) ? "0" : policeNum.getDictValue())) {
						ss.setStatus(ServiceSummary.STATUS_ACTIVE);
					}
				} else {
					ss.setStatus(ServiceSummary.STATUS_ACTIVE);
				}
			} else {
				ss.setStatus(ServiceSummary.STATUS_ACTIVE);
			}
			ss.setActive(active);
			ss.setDead(dead);
			ss.setServiceName(serviceVo.getName());
			map.put(Named.getKey(serviceVo.getName(), serviceVo.getVersion()), ss);
		}
		if (sqList.size() > 0) {
			for (int i = 0; i < sqList.size(); i++) {
				sortServices.add(i, map.get(sqList.get(i)));
			}
		}
		AllServiceSummary ass = new AllServiceSummary();
		ass.setAllActive(allActive);
		ass.setAllDead(allDead);
		ass.setServiceList(sortServices);
		return ass;
	}

	@Override
	public List<ServiceHost> findServiceByHost(String hostName) {
		return serviceHostDao.findBy("hostIp", hostName);
	}
}
