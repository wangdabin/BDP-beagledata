package com.sky.service.handler.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.joe.core.factory.BeanFactory;
import com.joe.core.support.ConfigApplyer;
import com.joe.host.vo.Host;
import com.sky.config.ConfigVO;
import com.sky.config.service.ConfigService;
import com.sky.config.utils.ConfigConvert;
import com.sky.service.Service;
import com.sky.service.define.KeyValue;
import com.sky.service.handler.AbstractConfigHandler;

/**
 * 应用配置
 * 
 * @author qiaolong
 * 
 */
@org.springframework.stereotype.Service("configHandler")
public class AgentConfigHandler extends AbstractConfigHandler {

	private static final Logger LOG = Logger.getLogger(AgentConfigHandler.class);

	@Resource(name = "configService")
	private ConfigService configService;
	
	public AgentConfigHandler(){}
	
	public AgentConfigHandler(Configuration conf) {
		super(conf);
		this.configService = BeanFactory.getBean("configService", ConfigService.class);
	}

	@Override
	public void applyConfig(Service service) {
		ConfigApplyer configApplyer = service.getConfigApplyer();
		List<Host> hosts = service.getAllHosts();
		String dir = this.buildConfDir(service);
		this.apply(service, configApplyer, hosts,dir);
	}
	
	private void apply(Service service,ConfigApplyer configApplyer,List<Host> hosts,String dir){
		List<String> files = configService.getAllFiles(service.getName(), service.getVersion());
		if(files != null){
			for(String file : files){
				LOG.debug("Will apply  service name " + service.getName() + " config file " + file);
				List<KeyValue> kvs = getConfigs(service, file);
				file = new File(dir,file).getAbsolutePath();
				configApplyer.updateConfig(hosts, file, kvs);
			}
		}
		List<Service> subServices = service.getSubService();
		if(subServices != null && !subServices.isEmpty()){
			for(Service subService : subServices){
				LOG.debug("Found sub service " + subService.getName());
				this.apply(subService, configApplyer, hosts,dir);
			}
		}
	}

	private List<KeyValue> getConfigs(Service service,String file){
		List<ConfigVO> configs = configService.getConfigsByFile(service.getName(), service.getVersion(), file);
		return ConfigConvert.covert(configs);
	}
	
	/**
	 * installDir + serviceName + config dir
	 * @param service
	 * @return
	 */
	private String buildConfDir(Service service){
		String installDir = service.getInstallDir();
		String serviceName = service.getName();
		String configDir = service.getConfigDir();
		File file = new File(new File(installDir,serviceName),configDir);
		return file.getAbsolutePath();
	}
}
