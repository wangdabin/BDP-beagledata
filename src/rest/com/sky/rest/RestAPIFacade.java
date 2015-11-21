package com.sky.rest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;

import com.bdp.rest.ServiceClient;
import com.sky.config.Configed;
import com.sky.rest.entity.Install;
import com.sky.rest.entity.RestService;
import com.sky.rest.entity.Service;
import com.sky.rest.service.RestServiceInstall;

/**
 * 对外的Rest接口
 * @author qiaolong
 *
 */
public class RestAPIFacade extends Configed implements RestAPI{

	private ServiceClient client;
	public RestAPIFacade(Configuration conf){
		super(conf);
		client = new ServiceClient(conf);
	}

	@Override
	public Map<String, List<String>> supportServices() {
		return client.supports();
	}
	
	@Override
	public Service createService(String name, String version) {
		try {
			return new RestService(this, name, version);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Install serviceInstall(String name, String version) {
		try {
			return new RestServiceInstall(this, name, version);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
