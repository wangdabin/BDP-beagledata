package com.sky.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.joe.core.callback.ScheduleCallBack;
import com.joe.core.exception.DependException;
import com.joe.core.exception.Exceptions;
import com.joe.core.exception.InstalledException;
import com.joe.core.factory.BeanFactory;
import com.joe.core.install.Install;
import com.joe.core.support.Checker;
import com.joe.core.version.Named;
import com.joe.host.service.HostService;
import com.joe.host.utils.HostUtils;
import com.joe.host.vo.Host;
import com.sky.service.define.ConfigDefine;
import com.sky.service.define.EnvironmentDefine;
import com.sky.service.define.KeyValue;
import com.sky.service.define.KeyValue.TYPE;
import com.sky.service.define.ServiceDefineManager;
import com.sky.service.handler.impl.ServiceChecker;
import com.sky.service.install.BasicKeyValues;
import com.sky.service.install.Entry;
import com.sky.service.install.Option;
import com.sky.service.install.Step;
import com.sky.service.install.StepsLoader;
import com.sky.service.supports.ServiceSupport;
import com.sky.service.utils.ServiceUtils;

/**
 * 
 * @author qiaolong
 *
 */
public abstract class AbstractInstall extends Named implements Install{

	public static final List<KeyValue> NULL_KEYVALUE = new ArrayList<KeyValue>();
	public static final int STEP_ONE = 1;
	
	private Configuration conf;
	private HostService hostService;
	private final Map<String,List<KeyValue>> basicKeyValues = new HashMap<String, List<KeyValue>>();
	private final Map<String,List<KeyValue>> advancedKeyValues = new HashMap<String, List<KeyValue>>();
	private Step currentStep;
	private StepsLoader stepsLoader;
	private Service currentService;
	private Service topService; //顶级服务，如hadoop是一个顶级服务，namenode 等是子服务
	private ServiceSupport topSupport;
	private KeyValue nextKeyValue;
	
	public AbstractInstall(){
		hostService = BeanFactory.getBean("hostService", HostService.class);
		stepsLoader = initStepsLoader();
	}

	@Override
	public void init() throws IOException {
		this.topService = this.initTopService();
		this.topSupport = this.initTopServiceSupport();
		
		Checker checker = getServiceChecker(); //检查依赖的服务是否已经安装
		if(checker != null){
			if(checker.checkInstalled(this)){ // 检查服务是否已经安装
				throw InstalledException.getException(this);
			}
			if(!checker.checkDepends(topSupport)){// 检查服务所依赖的服务是否已经安装
				throw DependException.getException(topSupport);
			}
		}
	}
	
	@Override
	public boolean hasNext() {
		if(currentStep == null){
			return true;
		}else{
			return stepsLoader.hasNext(currentStep);
		}
	}

	@Override
	public boolean hasReverse() {
		if(currentStep == null){
			return false;
		}else{
			return stepsLoader.hasReverse(currentStep);
		}
	}

	@Override
	public void next() {
		if(currentStep == null){
			this.currentStep = stepsLoader.getStart();
		}else{
			if(currentStep.isSelect()){
				currentStep = stepsLoader.next(currentStep, nextKeyValue);
			}else{
				this.currentStep = stepsLoader.next(currentStep);
			}
		}
		this.doStep();
	}

	@Override
	public void reverse() {
		this.currentStep = stepsLoader.reverse(currentStep);
		this.doStep();
	}

	/**
	 * 走了一步
	 */
	private void doStep(){
		currentService = getService(currentStep.getService());
		String parent = currentStep.getParentService();
		if(!StringUtils.isBlank(parent)){
			Service parentService = getService(parent);
			parentService.addSubService(currentService);
			currentService.setParent(parentService);
		}
		List<KeyValue> basicKV = new ArrayList<KeyValue>();
		List<KeyValue> advancedKV = new ArrayList<KeyValue>();
		if(currentStep.isBasic()){ // 基本的安装配置，安装目录等的配置
			basicKV = initBasicConfig();
		}else if(currentStep.isSelect()){ // 选择某种模式
			KeyValue kv = new KeyValue();
			kv.setConfigKey(currentStep.getKey());
			List<Option> options = currentStep.options();
			for(Option option : options){
				kv.addValue(option.getValue());
			}
			kv.setType(TYPE.radio);
			kv.setName(currentStep.getName());
			basicKV.add(kv);
		}else{
			ServiceSupport serviceSupport = getServiceSupport(currentStep.getService());
			if(currentStep.getType().equals(Entry.TYPE_HOST)){
				basicKV = hostKeyvalues();
			}else if(currentStep.getType().equals(Entry.TYPE_CONFIG)){
				ConfigDefine basic = serviceSupport.getBasicConfig();
				ConfigDefine advanced = serviceSupport.getAdvancedConfig();
				if(basic != null){
					basicKV = basic.getProps();
				}
				if(advanced != null){
					advancedKV = advanced.getProps();
				}
			}else if(currentStep.getType().equals(Entry.TYPE_ENVIRONMENT)){
				EnvironmentDefine basic = serviceSupport.getBasicEnvironments();
				EnvironmentDefine advanced = serviceSupport.getAdvancedEnvironment();
				if(basic != null){
					basicKV = basic.getProps();
				}
				if(advanced != null){
					advancedKV = advanced.getProps();
				}
			}
		}
		basicKeyValues.put(currentStep.getId(), basicKV);
		advancedKeyValues.put(currentStep.getId(), advancedKV);
	}
	
	@Override
	public List<KeyValue> supportsBasic() {
		return basicKeyValues.get(currentStep.getId());
	}

	@Override
	public List<KeyValue> supportsAdvanced() {
		return advancedKeyValues.get(currentStep.getId());
	}

	@Override
	public void addKeyValue(KeyValue keyValue) {
		if(currentStep.isBasic()){ //是基本配置
			if("installDir".equals(keyValue.getConfigKey())){
				currentService.setInstallDir(keyValue.getValues().get(0));
			}else if("username".equals(keyValue.getConfigKey())){
				currentService.setUsername(keyValue.getValues().get(0));
			}else if("password".equals(keyValue.getConfigKey())){
				currentService.setPassword(keyValue.getValues().get(0));
			}
		}else if(currentStep.isHost()){
			List<String> hosts = keyValue.getValues();
			for(String hostname : hosts){
				Host host = getHostService().findByHostName(hostname);
				currentService.addHost(host);
			}
		}else if(currentStep.isSelect()){
			if(keyValue.getConfigKey().equals(currentStep.getKey())){
				this.nextKeyValue = keyValue;
			}else{
				currentService.addConfig(keyValue);
			}
		}else{
			currentService.addConfig(keyValue);
		}
	}

	@Override
	public void install(ScheduleCallBack callBack) {
		topService.install(callBack);
	}

	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	@Override
	public Configuration getConf() {
		return conf;
	}

	public HostService getHostService() {
		return hostService;
	}
	
	/**
	 * 主机选择的配置
	 * @return
	 */
	protected List<KeyValue> hostKeyvalues(){
		KeyValue kv = new KeyValue();
		kv.setName(currentStep.getName());
		kv.setConfigKey("host-" + currentStep.getId());
		List<Host> hosts = getHostService().getAll();
		if(hosts != null){
			kv.setValues(HostUtils.parse(hosts));
		}
		kv.setType(TYPE.host);
		List<KeyValue> kvs = new ArrayList<KeyValue>();
		kvs.add(kv);
		return kvs;
	}

	/**
	 * 基本的配置，安装目录、用户名、密码等
	 * @return
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	protected List<KeyValue> initBasicConfig() {
		try {
			return BasicKeyValues.getDefaultkeyvalues();
		} catch (Exception e) {
			throw Exceptions.create(e);
		} 
	}
	
	/**
	 * 顶级服务
	 * @return
	 */
	protected Service initTopService(){
		return ServiceUtils.getService(this.getName(), this.getVersion());
	}
	/**
	 * 得到当前的服务
	 * @return
	 */
	protected Service getService(String service){
		return ServiceUtils.getService(this.getName(), this.getVersion(),service);
	}
	
	protected ServiceSupport initTopServiceSupport(){
		return ServiceDefineManager.getServiceDefine(this.getName(), this.getVersion());
	}
	/**
	 * 服务的支持
	 * @return
	 */
	protected ServiceSupport getServiceSupport(String service){
		return ServiceDefineManager.getServiceDefine(service, this.getVersion());
	}
	
	/**
	 * 步骤加载器
	 * @return
	 */
	protected abstract StepsLoader initStepsLoader() ;
	
	/**
	 * 服务安装检查器
	 * @return
	 */
	protected Checker getServiceChecker(){
		return new ServiceChecker(getConf());
	}
}
