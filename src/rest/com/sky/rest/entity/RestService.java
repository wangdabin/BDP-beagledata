package com.sky.rest.entity;

import java.io.IOException;
import java.util.List;

import com.bdp.rest.ServiceClient;
import com.joe.core.vo.ReCode;
import com.joe.host.vo.Host;
import com.sky.rest.RestAPI;
import com.sky.service.define.KeyValue;

/**
 * 客户端会用到的类
 * 
 * @author qiaolong
 * 
 */
public class RestService extends RestEntity implements Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String version;
	private ServiceClient serviceClient;

	public RestService(RestAPI restApi, String name, String version)
			throws IOException {
		super(restApi);
		this.serviceClient = new ServiceClient(restApi.getConf());
		this.name = name;
		this.version = version;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public void addConfig(KeyValue keyValue) {
		ReCode recode = serviceClient.addConfig(getName(), getVersion(),
				keyValue);
		System.out.println("recode.getMsg() = " + recode.getMsg());
		System.out.println("recode.getRet() = " + recode.getRet());
	}

	@Override
	public void removeConfig(String key) {
		serviceClient.removeConfig(getName(), getVersion(), key);
	}

	@Override
	public String getModel() {
		return serviceClient.getModel(getName(), getVersion());
	}

	@Override
	public List<Service> getSubService() {
		return null;
	}

	@Override
	public Service getParent() {
		return null;
	}

	@Override
	public boolean hasSubService() {
		return getSubService() == null || getSubService().isEmpty();
	}

	@Override
	public boolean hasParent() {
		return getParent() == null;
	}

	@Override
	public void addSubService(Service subService) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeSubService(Service subService) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Host> getHosts() {
		return serviceClient.getHosts(getName(), getVersion());
	}

	@Override
	public void addHost(Host host) {
		serviceClient.addHost(getName(), getVersion(), host);
	}

	@Override
	public void removeHost(Host host) {
		serviceClient.removeHost(getName(), getVersion(), host);
	}

	@Override
	public void setOwner(String username, String password) {
		serviceClient.setOwner(getName(), getVersion(), username, password);
	}

	@Override
	public String getOwner() {
		return serviceClient.getOwner(getName(), getVersion());
	}

	@Override
	public void remove() {
		serviceClient.remove(getName(), getVersion());
	}

	@Override
	public void start() {
		serviceClient.start(getName(), getVersion());
	}

	@Override
	public void stop() {
		serviceClient.stop(getName(), getVersion());
	}

	@Override
	public void addEnvironment(KeyValue keyValue) {
		serviceClient.addEnvironment(getName(), getVersion(), keyValue);
	}

	@Override
	public void removeEnvironment(String key) {
		serviceClient.removeEnvironment(getName(), getVersion(), key);
	}
}
