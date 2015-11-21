package com.sky.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;

import com.joe.core.callback.ScheduleCallBack;
import com.joe.core.exception.DependException;
import com.joe.core.exception.InstalledException;
import com.joe.core.factory.BeanFactory;
import com.joe.core.install.Install;
import com.joe.core.support.Checker;
import com.joe.core.version.Named;
import com.joe.host.service.HostService;
import com.sky.service.define.KeyValue;
import com.sky.service.handler.impl.ServiceChecker;
import com.sky.service.supports.ServiceSupport;

/**
 * 
 * @author qiaolong
 *
 */
public abstract class CopyOfAbstractInstall extends Named implements Install{

	public static final List<KeyValue> NULL_KEYVALUE = new ArrayList<KeyValue>();
	
	private Configuration conf;
	private int stepID = 0;
	private int steps = 0;
	private HostService hostService;
	protected Service service;
	protected ServiceSupport serviceSupport;
	
	public CopyOfAbstractInstall(){
		this.steps = steps();
		hostService = BeanFactory.getBean("hostService", HostService.class);
	}
	@Override
	public void init() throws IOException {
		this.service = getService();
		this.serviceSupport = getServiceSupport();
		
		Checker checker = getServiceChecker(); //检查依赖的服务是否已经安装
		if(checker != null){
			if(checker.checkInstalled(this)){ // 检查服务一否已经安装
				throw InstalledException.getException(this);
			}
			if(!checker.checkDepends(serviceSupport)){// 检查服务所依赖的服务是否已经安装
				throw DependException.getException(serviceSupport);
			}
		}
		
		
	}
	@Override
	public boolean hasNext() {
		return stepID < steps;
	}

	@Override
	public boolean hasReverse() {
		return stepID > 0;
	}

	@Override
	public void next() {
		this.stepID ++;
	}

	@Override
	public void reverse() {
		this.stepID --;
	}

	@Override
	public List<KeyValue> supportsBasic() {
		return serviceSupport.getBasicConfig().getProps();
	}

	@Override
	public List<KeyValue> supportsAdvanced() {
		return serviceSupport.getAdvancedConfig().getProps();
	}

	@Override
	public void addKeyValue(KeyValue keyValue) {
		service.addConfig(keyValue);
	}

	@Override
	public void install(ScheduleCallBack callBack) {
		service.install(callBack);
	}

	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	@Override
	public Configuration getConf() {
		return conf;
	}

	public int getStepID() {
		return stepID;
	}
	
	public HostService getHostService() {
		return hostService;
	}

	/**
	 * 计算步数
	 * @return
	 */
	protected abstract int steps();
	
	/**
	 * 得到当前的服务
	 * @return
	 */
	protected abstract Service getService() ;
	
	/**
	 * 服务的支持
	 * @return
	 */
	protected abstract ServiceSupport getServiceSupport();
	
	/**
	 * 服务安装检查器
	 * @return
	 */
	protected Checker getServiceChecker(){
		return new ServiceChecker(getConf());
	}
}
