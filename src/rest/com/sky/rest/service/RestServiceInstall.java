package com.sky.rest.service;

import java.io.IOException;
import java.util.List;

import com.bdp.rest.InstallserviceClient;
import com.joe.core.version.Name;
import com.joe.core.version.Named;
import com.joe.core.vo.Bool;
import com.sky.rest.RestAPI;
import com.sky.rest.entity.Install;
import com.sky.rest.entity.RestEntity;
import com.sky.service.define.KeyValue;

/**
 * 服务安装类
 * @author qiaolong
 *
 */
public class RestServiceInstall extends RestEntity implements Install{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Name name;
	private InstallserviceClient installClient;
	private String currentName;
	/**
	 * 
	 * @param id
	 * @param name
	 * @param version
	 * @throws IOException 
	 */
	public RestServiceInstall(RestAPI restApi,String name,String version) throws IOException{
		super(restApi);
		this.name = new Named(name, version);
		this.installClient = new InstallserviceClient(restApi.getConf());
	}
	
	@Override
	public boolean hasNext() {
		Bool bool = installClient.hasNext(getName(), getVersion());
		return bool.isValue();
	}

	@Override
	public boolean hasReverse() {
		Bool bool = installClient.hasReverse(getName(), getVersion());
		return bool.isValue();
	}	
	
	@Override
	public void addKeyValue(KeyValue keyValue) {
		installClient.addKeyValue(getName(), getVersion(), keyValue);
	}

	@Override
	public void install(String callBackURL) {
		installClient.install(getName(), getVersion(), callBackURL);
	}

	@Override
	public String getName() {
		return name.getName();
	}

	@Override
	public void setName(String name) {
		this.name.setName(name);
	}

	@Override
	public String getVersion() {
		return name.getVersion();
	}

	@Override
	public void setVersion(String version) {
		this.name.setVersion(version);
	}

	@Override
	public String getUniqueKey() {
		return name.getUniqueKey();
	}

	@Override
	public String getUniqueKey(String split) {
		return name.getUniqueKey(split);
	}

	@Override
	public void next() {
		installClient.next(getName(), getVersion());
	}

	@Override
	public void reverse() {
		installClient.reverse(getName(), getVersion());
	}

	@Override
	public List<KeyValue> supportsBasic() {
		return installClient.supportsBasic(getName(), getVersion());
	}

	@Override
	public List<KeyValue> supportsAdvanced() {
		return installClient.supportsAdvanced(getName(), getVersion());
	}

	@Override
	public String getCurrentName() {
		return currentName;
	}

//	@Override
//	public void addKeyValue(List<KeyValue> keyValues) {
//	}
}
