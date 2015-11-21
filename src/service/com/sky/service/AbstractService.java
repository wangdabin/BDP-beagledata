package com.sky.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.joe.agent.handler.AgentCommandWorker;
import com.joe.agent.handler.AgentDeployWorker;
import com.joe.core.callback.ScheduleCallBack;
import com.joe.core.exception.Exceptions;
import com.joe.core.factory.BeanFactory;
import com.joe.core.support.CommandWorker;
import com.joe.core.support.ConfigApplyer;
import com.joe.core.support.DeployWorker;
import com.joe.core.utils.CoreConfigUtils;
import com.joe.core.utils.UrlUtil;
import com.joe.core.version.Named;
import com.joe.host.utils.HostUtils;
import com.joe.host.vo.Host;
import com.sky.config.ConfigVO;
import com.sky.config.service.ConfigService;
import com.sky.config.utils.ConfigConvert;
import com.sky.service.define.CommandDefine;
import com.sky.service.define.DirDefile;
import com.sky.service.define.EnvironmentDefine;
import com.sky.service.define.KeyValue;
import com.sky.service.define.ServiceDefine;
import com.sky.service.define.ServiceDefineManager;
import com.sky.service.handler.ConfigHandler;
import com.sky.service.handler.impl.ServiceSaver;
import com.sky.service.utils.Constants;
import com.sky.service.utils.ServiceUtils;
import com.sky.service.vo.ServiceStatusVo;
import com.sky.service.vo.ServiceVo;
import com.sky.task.vo.TranOrder;

/**
 * 抽象服务类
 * @author qiaolong
 *
 */
public abstract class AbstractService extends Named implements Service{
	public static final Logger LOG = Logger.getLogger(AbstractService.class);
	public static final String DEFAULT_USER = "hadoop";
	private String model;
	private Service parent;
	private String username;
	private String password;
	private String installDir; //安装目录
	private Configuration conf;
	private List<Service> subServices = new ArrayList<Service>();
	private List<Host> hosts = new ArrayList<Host>();
	private List<ServiceStatusVo> status = new ArrayList<ServiceStatusVo>();
	private List<ServiceVo> depends = new ArrayList<ServiceVo>();
	
	@Resource(name = "serviceSaver")
	private ServiceSaver saver;
	
	@Resource(name = "configService")
	private ConfigService configService;
	
	@Resource(name = "configHandler")
	private ConfigHandler configHandler;
	
	private CommandWorker commandWorker;
	private DeployWorker deployWorder;
	private ConfigApplyer configApplyer;
	private boolean installed = false;
	private boolean inited = false;
	
	public AbstractService(){
		try {
			this.setConf(CoreConfigUtils.create());
			ServiceDefineManager.addResource(defineResource());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public AbstractService(Configuration conf){
		this.setConf(conf);
		try {
			ServiceDefineManager.addResource(defineResource());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void init() {
		this.init(false);
	}
	
	/**
	 * 是否需要初始化
	 * @param force 是否强制初始化
	 */
	protected void init(boolean force){
		boolean needInit = force ? true : (inited ? false : true); 
		if(needInit){
			ServiceVo serviceVO = this.saver.getServiceVO(this.getName(), this.getVersion());
			installed = serviceVO == null ? false : true;
			if(installed){
				//安装目录
				this.installDir = serviceVO.getInstallDir();
				this.username = serviceVO.getUserName();
				
				//服务的状态
				Set<ServiceStatusVo> serviceStatus = serviceVO.getServiceStatus();
				if(serviceStatus != null){
					this.status = new ArrayList<ServiceStatusVo>(serviceStatus);
				}
				
				//服务安装的主机
				Set<Host> hosts = serviceVO.getHosts();
				if(hosts != null){
					this.hosts = new ArrayList<Host>(hosts);
				}
				
				String topServiceName = serviceVO.getTopName();
				//子服务初始化
				Set<ServiceVo> children = serviceVO.getChildren();
				if(children != null){
					this.subServices.clear();
					for(ServiceVo child : children){
						this.initChild(child,topServiceName,parent);
					}
				}
				
				List<String> depends = this.getServiceDefine().dependOn();
				if(depends != null && !depends.isEmpty()){
					depends.clear();
					for(String depend :  depends){
						List<ServiceVo> dependServices = this.saver.findByName(depend);
						if(dependServices != null){
							this.depends.addAll(dependServices);
						}
					}
				}
				
//				//更新父类
//				ServiceVo parent = serviceVO.getParent();
//				if(parent != null){
//					this.initParent(parent,topServiceName);
//				}
			}
			inited = true;
		}
	}
	
	protected List<ServiceVo> depends(){
		return depends;
	}
	
	/**
	 * 
	 * @param parent
	 */
	private void initParent(ServiceVo parent,String serviceName){
		if(!this.hasParent()){ //如果还没有父类，则初始化父类
			Service service = ServiceUtils.getService(parent.getTopName(), parent.getVersion(), parent.getName());
			this.setParent(service);
		}
	}
	
	/**
	 * 初始子服务
	 * @param children
	 * @param parent 服务的父类
	 * @param serviceName 最顶层服务的名称
	 */
	private void initChild(ServiceVo children,String serviceName,Service parent){
		Service childService = ServiceUtils.getService(serviceName, children.getVersion(), children.getName(),parent);
		if(childService != null){
			this.subServices.add(childService);
		}
	}
	
	@Override
	public List<Host> getAllHosts() {
		Set<Host> hosts = new HashSet<Host>();
		hosts.addAll(this.getHosts());
		if(this.hasSubService()){
			for(Service child : this.getSubService()){
				hosts.addAll(child.getAllHosts());
			}
		}else{
			return this.getHosts();
		}
		return new ArrayList<Host>(hosts);
	}
	
	public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
		try{
			this.saver = BeanFactory.getBean("serviceSaver", ServiceSaver.class);
			this.configService = BeanFactory.getBean("configService", ConfigService.class);
			this.configHandler = BeanFactory.getBean("configHandler", ConfigHandler.class);
		}catch (Exception e) {
			LOG.warn(e);
		}
		this.commandWorker = new AgentCommandWorker(conf);
		this.deployWorder = new AgentDeployWorker(conf);
		this.configApplyer = initConfigApplyer(conf);
	}

	@Override
	public String getConfigDir() {
		return getDir(Constants.DIR_CONF); 
	}
	
	protected String getDir(String name){
		List<DirDefile> dirs = getServiceDefine().getDirs();
		for(DirDefile dir : dirs){
			if(name.equals(dir.getName())){
				return dir.getDir();
			}
		}
		return null;
	}

	@Override
	public String getModel() {
		return model;
	}

	@Override
	public List<Service> getSubService() {
		return subServices;
	}

	@Override
	public Service getParent() {
		return parent;
	}
	
	@Override
	public void setParent(Service parent) {
		this.parent = parent;
	}
	
	@Override
	public List<KeyValue> getAllEnvironment() {
		List<ConfigVO> configs = configService.getConfigsByType(this.getName(), this.getVersion(), ConfigVO.TYPE_ENVIRONMENT);
		return ConfigConvert.covert(configs);
	}

	@Override
	public List<KeyValue> getBasicEnvironment() {
		return getFavoriteEnvironment(ConfigVO.FAVORITE_BASIC);
	}

	@Override
	public List<KeyValue> getAdvancedEnvironment() {
		return getFavoriteEnvironment(ConfigVO.FAVORITE_ADVANCED);
	}

	@Override
	public List<KeyValue> getFavoriteEnvironment(String favoriteName) {
		return this.getKeyValues(ConfigVO.TYPE_ENVIRONMENT, favoriteName);
	}

	@Override
	public void addEnvironment(KeyValue keyValue) {
		this.addKeyValue(keyValue, ConfigVO.TYPE_ENVIRONMENT);
	}
	
	/**
	 * 添加keyvalue根据类型、收藏夹添加
	 * @param keyValue
	 * @param type
	 * @param favorite
	 */
	private void addKeyValue(KeyValue keyValue,String type){
		String file = this.getFile(keyValue.getConfigKey(),type);
		String favorite = this.getFavorite(keyValue.getConfigKey(), type);
		configService.saveOrUpdate(this.getName(), this.getVersion(), keyValue.getConfigKey(), ConfigConvert.covertValues(keyValue), type,favorite,file);
	}
	
	private String getFavorite(String key,String type){
		ServiceDefine serviceDefine = this.getServiceDefine();
		EnvironmentDefine basic = null;
		if(type.equals(ConfigVO.TYPE_ENVIRONMENT)){
			basic = serviceDefine.getBasicEnvironments();
		}else{
			basic = serviceDefine.getBasicConfig();
		}
		return getFavorite(basic,key);
	}
	
	private String getFavorite(EnvironmentDefine basic,String key){
		List<KeyValue> kvs = basic.getProps();
		for(KeyValue kv : kvs){
			if(key.equals(kv.getConfigKey())){
				return ConfigVO.FAVORITE_BASIC;
			}
		}
		return ConfigVO.FAVORITE_ADVANCED;
	}
	
	/**
	 * 得到当前配置项属于哪个配置文件
	 * @param key
	 * @param type
	 * @return
	 */
	private String getFile(String key,String type){
		ServiceDefine serviceDefine = this.getServiceDefine();
		EnvironmentDefine basic = null;
		EnvironmentDefine advanced = null;
		if(type.equals(ConfigVO.TYPE_ENVIRONMENT)){
			basic = serviceDefine.getBasicEnvironments();
			advanced = serviceDefine.getAdvancedEnvironment();
			return getFile(basic,advanced,key);
		}else{
			basic = serviceDefine.getBasicConfig();
			advanced = serviceDefine.getAdvancedConfig();
		}
		return getFile(basic,advanced,key);
	}
	
	private String getFile(EnvironmentDefine basic,EnvironmentDefine advanced,String key){
		List<KeyValue> kvs = basic.getProps();
		for(KeyValue kv : kvs){
			if(key.equals(kv.getConfigKey())){
				return basic.getFile();
			}
		}
		if(advanced != null){
			return advanced.getFile();
		}else{
			return basic.getFile();
		}
	}
	
	/**
	 * 从数据库中获取
	 * @param type
	 * @param favorite
	 * @return
	 */
	private List<KeyValue> getKeyValues(String type,String favorite){
		List<ConfigVO> configs =  configService.getConfigsByTypeAndFavorite(this.getName(), this.getVersion(), favorite, type);
		return ConfigConvert.covert(configs);
	}
	
	/**
	 * 
	 * @param configVO
	 */
	public void addConfigVO(ConfigVO configVO){
		configService.saveOrUpdate(configVO);
	}

	@Override
	public void addBasicEnvironment(KeyValue keyValue) {
		addFavoriteEnvironment(Constants.FAVORITE_BASIC, keyValue);
	}

	@Override
	public void addAdvancedEnvironment(KeyValue keyValue) {
		addFavoriteEnvironment(Constants.FAVORITE_ADVANCED, keyValue);
	}

	@Override
	public void addFavoriteEnvironment(String favoriteName, KeyValue keyValue) {
		this.addKeyValue(keyValue, ConfigVO.TYPE_ENVIRONMENT,favoriteName);
	}

	/**
	 * 添加keyvalue根据类型、收藏夹添加
	 * @param keyValue
	 * @param type
	 * @param favorite
	 */
	private void addKeyValue(KeyValue keyValue,String type,String favoriteName){
		String file = this.getFile(keyValue.getConfigKey(), type);
		configService.saveOrUpdate(this.getName(), this.getVersion(), keyValue.getConfigKey(), ConfigConvert.covertValues(keyValue), type,favoriteName,file);
	}
	
	@Override
	public KeyValue removeEnvironment(String key) {
		return this.remove(key);
	}
	
	private KeyValue remove(String key){
		return ConfigConvert.covert(configService.remove(this.getName(), this.getVersion(), key));
	}

	@Override
	public boolean hasSubService() {
		return subServices  != null && !subServices.isEmpty();
	}

	@Override
	public boolean hasParent() {
		return this.parent != null;
	}

	@Override
	public List<KeyValue> getAllConfig() {
		List<ConfigVO> configs = configService.getConfigsByType(this.getName(), this.getVersion(), ConfigVO.TYPE_CONFIG);
		return ConfigConvert.covert(configs);
	}

	@Override
	public KeyValue getConfig(String key) {
		return getKeyValue(key);
	}
	
	private KeyValue getKeyValue(String key){
		return ConfigConvert.covert(configService.getConfig(this.getName(), this.getVersion(), key));
	}
	
	@Override
	public List<KeyValue> getBasicConfig() {
		return getFavoriteConfig(Constants.FAVORITE_BASIC);
	}

	@Override
	public List<KeyValue> getAdvancedConfig() {
		return getFavoriteConfig(Constants.FAVORITE_ADVANCED);
	}

	@Override
	public List<KeyValue> getFavoriteConfig(String favoriteName) {
		return this.getKeyValues(ConfigVO.TYPE_CONFIG, favoriteName);
	}

	@Override
	public void addConfig(List<KeyValue> keyValues) {
		if(keyValues != null){
			for(KeyValue kv : keyValues){
				this.addConfig(kv);
			}
		}
	}
	
	@Override
	public void addConfig(KeyValue keyValue) {
		this.addKeyValue(keyValue, ConfigVO.TYPE_CONFIG);
	}

	@Override
	public void addBasicConfig(KeyValue keyValue) {
		addFavoriteConfig(Constants.FAVORITE_BASIC,keyValue);
	}

	@Override
	public void addAdvancedConfig(KeyValue keyValue) {
		addFavoriteConfig(Constants.FAVORITE_ADVANCED,keyValue);
	}

	@Override
	public void addFavoriteConfig(String favoriteName, KeyValue keyValue) {
		this.addKeyValue(keyValue, ConfigVO.TYPE_CONFIG,favoriteName);
	}

	@Override
	public KeyValue removeConfig(String key) {
		return this.remove(key);
	}
	

	@Override
	public List<Pair> getPair() {
		List<Pair> pairs = new ArrayList<Pair>();
		String packageDownload = getConf().getString(Constants.DOWNLOAD_PACKAGE_CONF);
		UrlUtil url = new UrlUtil(packageDownload);
		url.putParam("serviceName", getName()); //服务名称
		url.putParam("version", getVersion()); //服务版本号
		Pair pair = new Pair();
		try {
			pair.setSource(url.getURL());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		pair.setTarget(getInstallDir());
		pair.setType(TranOrder.TYPE_HTTP);
		pair.setUncompress(true);
		pairs.add(pair);
		return pairs;
	}

	/**
	 * 
	 * @return
	 */
	public String getInstallDir() {
		if(this.hasParent()){
			return parent.getInstallDir();
		}else{
			return this.installDir;
		}
	}

	@Override
	public String getRealInstallDir() {
		if(this.hasParent()){
			return parent.getRealInstallDir();
		}else{
			return new File(getInstallDir(),getName()).getAbsolutePath();
		}
	}
	
	public void setInstallDir(String installDir) {
		this.installDir = installDir;
	}

	@Override
	public void addSubService(Service subService) {
		this.subServices.add(subService);
	}

	@Override
	public void removeSubService(Service subService) {
		this.subServices.add(subService);
	}

	@Override
	public List<Host> getHosts() {
		if(installed){
			Set<Host> hostSet = saver.getServiceHosts(this.getName(), this.getVersion());
			if(hostSet != null){
				return new ArrayList<Host>(hostSet);
			}else{
				return hosts;
			}
		}
		return hosts;
	}
	
	@Override
	public List<ServiceStatusVo> getServiceStatus(String name,String version){
		return saver.getServiceStatus(name, version);
	}

	@Override
	public void addHost(Host host) {
		hosts.add(host);
	}

	@Override
	public void removeHost(Host host) {
		hosts.remove(host);
	}

	@Override
	public void install(ScheduleCallBack callBack) {
		callBack.start(); //开始安装
//		//1、检查安装
//		this.checkInstall();
//		callBack.update("检查完成",0.2); //完成
		
		callBack.update("初始化完成", 0.1);
		//1、部署安装包
		this.deployPackage(); //一个全局的发布包
		callBack.update("发布包完成",0.3); //完成
		
		//2、应用配置
		this.applyConfig(); //一个全局的应用配置
		callBack.update("应用配置完成",0.6); //完成
		
		//3.初始化
		this.initAllService();
		callBack.update("初始化成功",0.8); //完成
		
		//4.启动
		this.start();
		this.saver.save(this); //保存自己到数据库
		callBack.update("启动完成",1.0); //完成
		callBack.finish();
		this.installed = true;
		this.init(true);//安装完成之后重新初始化
	}

	@Override
	public void remove() {
		
	}

	@Override
	public void start() {
		this.execute(hosts, getServiceDefine().startCommand());
	}

	@Override
	public void start(Host host) {
		this.execute(host, getServiceDefine().startCommand());
	}
	
	@Override
	public void stop() {
		this.execute(hosts, getServiceDefine().stopCommand());
	}
	
	@Override
	public void stop(Host host) {
		this.execute(host, getServiceDefine().stopCommand());
	}

	@Override
	public void setOwner(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	@Override
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getOwner() {
		return username;
	}

	/**
	 * 1
	 * 安装第二步
	 * 部署安装包
	 * @return
	 */
	protected void deployPackage(){
		this.getDeployWorker().deploy(this);
	}
	
	/**
	 * 应用配置之前
	 */
	public abstract void beforeApplyConfig();
	
	/**
	 * 2
	 * 安装第三步
	 * 应用配置
	 */
	public void applyConfig(){
		//应用配置之前
		this.beforeApplyConfig();
		if(this.hasSubService()){
			for(Service sub : this.getSubService()){ //子服务应用配置
				if(sub instanceof AbstractService){
					((AbstractService) sub).beforeApplyConfig();
				}
			}
		}
		if(configHandler != null){
			configHandler.applyConfig(this);
		}
	}
	
	/**
	 * 初始化所有的服务
	 */
	protected void initAllService(){
		this.initService();
		if(this.hasSubService()){
			for(Service sub : this.getSubService()){ //子服务应用配置
				if(sub instanceof AbstractService){
					((AbstractService) sub).initService();
				}
			}
		}
	}
	
	/**
	 * 3
	 * 安装第四步
	 * 服务初始化
	 * 如：格式化namenode等
	 */
	public abstract void initService();
	
	/**
	 * 安装包发布器
	 * @return
	 */
	protected DeployWorker getDeployWorker(){
		return deployWorder;
	}
	
	public CommandWorker getCommandWorker(){
		return commandWorker;
	}
	
	protected ServiceDefine getServiceDefine(){
		return ServiceDefineManager.getServiceDefine(this);
	}

	/**
	 * 配置项应用
	 * @return
	 */
	protected ConfigApplyer initConfigApplyer(Configuration conf){
		return null;
	}
	
	/**
	 * 指定
	 * @param name
	 * @return
	 */
	protected CommandDefine getCommand(String name){
		return getServiceDefine().getCommand(name);
	}
	
	/**
	 * 执行任务
	 * @param hosts
	 * @param command
	 */
	protected void execute(List<Host> hosts,CommandDefine command){
		this.execute(hosts, command, false);
	}
	
	/**
	 * 执行任务
	 * @param hosts
	 * @param command
	 */
	protected void execute(Host hosts,CommandDefine command){
		this.execute(hosts, command, false);
	}
	
	/**
	 * 执行任务
	 * @param hosts
	 * @param command
	 * @param syn
	 */
	protected void execute(List<Host> hosts,CommandDefine command,boolean syn){
		command.setBasePath(this.getRealInstallDir());
//		command.setBasePath("/home/hadoop/cdh5/hadoop-2.3.0-cdh5.0.0");
		if(hosts != null && !hosts.isEmpty()){
			List<String> hostnames = new ArrayList<String>();
			for(Host host : hosts){
				hostnames.add(HostUtils.buildKey(host));
			}
			getCommandWorker().execute(hostnames, command,syn);
		}
	}
	
	/**
	 * 执行任务
	 * @param hosts
	 * @param command
	 * @param syn
	 */
	protected void execute(Host host,CommandDefine command,boolean syn){
		command.setBasePath(this.getRealInstallDir());
		String hostName = HostUtils.buildKey(host);
		getCommandWorker().execute(hostName, command,syn);
	}
	
	/**
	 * 同步执行指定command
	 * @param command
	 * @param host
	 */
	protected void execute(String command,Host host){
		CommandDefine commandDefine = this.getCommand(command);
		if(commandDefine != null){
			this.execute(host, commandDefine, true);
		}else{
			throw Exceptions.create("Command " + command + " not found");
		}
	}
	
	protected void execute(String command,Host host,boolean syn){
		CommandDefine commandDefine = this.getCommand(command);
		if(commandDefine != null){
			this.execute(host, commandDefine, syn);
		}else{
			throw Exceptions.create("Command " + command + " not found");
		}
	}
	
	protected abstract String defineResource();
	
	@Override
	public ConfigApplyer getConfigApplyer() {
		return configApplyer;
	}
	
	@Override
	public boolean installed() {
		return installed;
	}
	
	/**
	 * 主机名称
	 * @return
	 */
	protected List<String> getHostNames(){
		List<Host> hosts = getHosts();
		List<String> hostnames = new ArrayList<String>();
		for(Host host : hosts){
			hostnames.add(HostUtils.buildKey(host));
		}
		return hostnames;
	}
	
    /**
     * 得到指定的服务
     * @param clazz
     * @return
     */
    protected <T extends Service> T getRealService(Class<T> clazz){
    	if(hasSubService()){
    		for(Service service : this.getSubService()){
    			if(clazz.isInstance(service)){
    				return (T) service;
    			}
    		}
    	}
    	return null;
    }
}
