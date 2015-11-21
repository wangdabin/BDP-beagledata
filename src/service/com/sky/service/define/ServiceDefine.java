package com.sky.service.define;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sky.service.Service;
import com.sky.service.supports.ServiceSupport;
import com.sky.service.utils.Constants;

/**
 * 服务定义
 * @author qiaolong
 *
 */
public class ServiceDefine extends AbstractDefine implements ServiceSupport{

	private List<String> depends = new ArrayList<String>();
	private List<String> models = new ArrayList<String>();
	
	/**
	 * 目录定义
	 */
	private List<DirDefile> dirs = new ArrayList<DirDefile>();
	//命令定义
	private List<CommandDefine> commands = new ArrayList<CommandDefine>();
	
	//子服务
	private List<ServiceSupport> children = new ArrayList<ServiceSupport>();
	
	//环境变量
	private List<EnvironmentDefine> environments = new ArrayList<EnvironmentDefine>();
	
	//配置
	private List<ConfigDefine> configs = new ArrayList<ConfigDefine>();

	public List<DirDefile> getDirs() {
		return dirs;
	}

	public List<String> dependOn() {
		return depends;
	}

	public List<String> getModels() {
		return models;
	}

	public List<ServiceSupport> getChildren() {
		return children;
	}

	/**
	 * 启动脚本
	 * @return
	 */
	public CommandDefine startCommand(){
		return getCommand(Constants.COMMAND_START);
	}
	
	public CommandDefine stopCommand(){
		return getCommand(Constants.COMMAND_STOP);
	}
	
	/**
	 * 指定
	 * @param name
	 * @return
	 */
	public CommandDefine getCommand(String name){
		List<CommandDefine> commands = getCommands();
		if(commands != null && !commands.isEmpty()){
			for(CommandDefine define : commands){
				if(name.equals(define.getName())){
					return define;
				}
			}
		}
		return null;
	}
	
	public List<CommandDefine> getCommands() {
		return commands;
	}

	/**
	 * 子服务的名称
	 * @return
	 */
	public List<Service> getSubServices(){
		List<String> subService = new ArrayList<String>();
		for(ServiceSupport service : children){
			subService.add(service.getName());
		}
		return null;
	}

	public List<EnvironmentDefine> getEnvironments() {
		return environments;
	}
	
	@Override
	public EnvironmentDefine getBasicEnvironments() {
		return getEnvironment(Constants.FAVORITE_BASIC);
	}
	

	@Override
	public EnvironmentDefine getAdvancedEnvironment() {
		return getEnvironment(Constants.FAVORITE_ADVANCED);
	}
	
	/**
	 * 
	 * @param environmentName
	 * @return
	 */
	public EnvironmentDefine getEnvironment(String environmentName) {
		for(EnvironmentDefine def : getEnvironments()){
			if(environmentName.equalsIgnoreCase(def.getName())){
				return def;
			}
		}
		return null;
	}

	public void setEnvironments(List<EnvironmentDefine> environments) {
		this.environments = environments;
	}

	public List<ConfigDefine> getConfigs() {
		return configs;
	}

	@Override
	public ConfigDefine getBasicConfig() {
		return getConfig(Constants.FAVORITE_BASIC);
	}

	@Override
	public ConfigDefine getAdvancedConfig() {
		return getConfig(Constants.FAVORITE_ADVANCED);
	}
	
	/**
	 * 
	 * @param configName
	 * @return
	 */
	public ConfigDefine getConfig(String configName) {
		for(ConfigDefine def : getConfigs()){
			if(configName.equalsIgnoreCase(def.getName())){
				return def;
			}
		}
		return null;
	}

	public void setConfigs(List<ConfigDefine> configs) {
		this.configs = configs;
	}

	public void parse(Element serviceNode){
		this.parseName(serviceNode);
		NodeList nodes = serviceNode.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
		    Node node = nodes.item(i);
		    if (!(node instanceof Element)) {
		    	continue;
		    }
		    Element element = (Element) node;
		    String tagName = element.getTagName();
		    if("dirs".equalsIgnoreCase(tagName)){
		    	this.parseChild(element, DirDefile.class, getDirs());
		    }else if("commands".equalsIgnoreCase(tagName)){
		    	this.parseChild(element, CommandDefine.class, getCommands());
		    }else if("depends".equalsIgnoreCase(tagName)){
		    	String value = element.getTextContent();
		    	List<String> depends = parseValue(value);
		    	if(depends != null){
		    		this.dependOn().addAll(depends);
		    	}
		    }else if("models".equalsIgnoreCase(tagName)){
		    	String value = element.getTextContent();
		    	List<String> models = parseValue(value);
		    	if(models != null){
		    		this.getModels().addAll(models);
		    	}
		    }else if("children".equalsIgnoreCase(tagName)){
		    	this.parseChild(element, ServiceDefine.class, getChildren());
		    }else if("environments".equalsIgnoreCase(tagName)){
		    	this.parseChild(element, EnvironmentDefine.class, getEnvironments());
		    }else if("configs".equalsIgnoreCase(tagName)){
		    	this.parseChild(element, ConfigDefine.class, getConfigs());
		    }else{
		    	throwException(tagName);
		    }
		}
	}

	public static final ServiceDefine build(Element element){
		ServiceDefine define = new ServiceDefine();
		define.parse(element);
		return define;
	}
}
