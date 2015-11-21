package com.joe.agent.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;

import com.joe.agent.client.ConfigClient;
import com.joe.core.support.AbstractConfigApplyer;
import com.joe.host.utils.HostUtils;
import com.joe.host.vo.Host;
import com.sky.service.define.KeyValue;

/**
 * 配置应用
 * @author qiaolong
 *
 */
public abstract class AgentConfigApply extends AbstractConfigApplyer{

	private ConfigClient configClient;
	private Properties extensionConfig;
	
	public AgentConfigApply(){}
	
	public AgentConfigApply(Configuration conf) throws IOException{
		super(conf);
		this.configClient = new ConfigClient(conf);
		this.extensionConfig = new Properties();
		InputStream in = AgentConfigApply.class.getResourceAsStream(resource());
		this.extensionConfig.load(in);
		in.close();
	}
	
	@Override
	public List<KeyValue> findAll(Host host, String file) {
		return configClient.findAll(HostUtils.buildKey(host), getExtensionId(file), file);
	}
	
	@Override
	public void newKeyValue(Host host, String file, KeyValue kv) {
		configClient.newKeyValue(HostUtils.buildKey(host), getExtensionId(file), file, kv);
	}
	
	@Override
	public void newKeyValue(List<Host> hosts, String file, KeyValue kv) {
		for(Host host : hosts){
			this.newKeyValue(host, file, kv);
		}
	}
	
	@Override
	public void newKeyValues(Host host, String file, List<KeyValue> kvs) {
		configClient.newKeyValues(HostUtils.buildKey(host), getExtensionId(file), file, kvs);
	}
	
	@Override
	public void newKeyValues(List<Host> hosts, String file, List<KeyValue> kvs) {
		for(Host host : hosts){
			this.newKeyValues(host, file, kvs);
		}
	}
	
	@Override
	public void updateConfig(Host host, String file, KeyValue kv) {
		configClient.updateKeyValue(HostUtils.buildKey(host), getExtensionId(file), file, kv);
	}

	@Override
	public void updateConfig(List<Host> hosts, String file, KeyValue kv) {
		for(Host host : hosts){
			this.updateConfig(host, file, kv);
		}
	}

	@Override
	public void updateConfig(List<Host> hosts, String file, List<KeyValue> kvs) {
		for(Host host : hosts){
			configClient.updateKeyValues(HostUtils.buildKey(host), getExtensionId(file), file, kvs);
		}
	}

	/**
	 * 得到文件所对应的插件ID
	 * @param file
	 * @return
	 */
	protected String getExtensionId(String file){
		String exId = extensionConfig.getProperty(file);
		if(StringUtils.isBlank(exId)){
			exId = extensionConfig.getProperty(new File(file).getName());
		}
		return exId;
	}

	/**
	 * 资源
	 * @return
	 */
	protected abstract String resource();
}
