package com.sky.service.define;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.joe.config.plugin.Config;
import com.joe.core.utils.CoreConfigUtils;
import com.joe.plugin.manager.PluginFactory;
import com.sky.config.utils.ConfigConvert;


/**
 * 
 * @author qiaolong
 *
 */
public class EnvironmentDefine extends AbstractDefine {

	private String dir;
	private String file;
	private String pluginId;
	private String extensionId;
	private List<KeyValue> props = new ArrayList<KeyValue>();
	private PluginFactory factory;
	public EnvironmentDefine() throws IOException{
		factory = PluginFactory.getFactory(CoreConfigUtils.create());
	}
	
	public String getFile() {
		return file;
	}

	private void setFile(String file) {
		this.file = file;
	}

	public List<KeyValue> getProps() {
		return props;
	}

	public String getPluginId() {
		return pluginId;
	}

	private void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

	public String getExtensionId() {
		return extensionId;
	}

	private void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}

	public String getDir() {
		return dir;
	}

	private void setDir(String dir) {
		this.dir = dir;
	}
	public static final Logger LOG = Logger.getLogger(EnvironmentDefine.class);
	@Override
	public void parse(Element environmentNode) {
		this.parseName(environmentNode);
		this.setDir(environmentNode.getAttribute("dir"));
		this.setFile(environmentNode.getAttribute("file"));
		this.setPluginId(environmentNode.getAttribute("plugin"));
		this.setExtensionId(environmentNode.getAttribute("extension"));
		NodeList nodes = environmentNode.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
		    Node node = nodes.item(i);
		    if (!(node instanceof Element)) {
		    	continue;
		    }
		    Element element = (Element) node;
		    String tagName = element.getTagName();
		    if("props".equalsIgnoreCase(tagName)){
		    	this.parseChild(element, KeyValue.class, getProps());
		    }else if("import".equalsIgnoreCase(tagName)){
		    	String file = element.getAttribute("file");
		    	LOG.debug("env file = " + file);
		    	Config config = getConfig(this.getExtensionId());
		    	if(config != null){
		    		try {
						config.load(parse(file));
						List<KeyValue> kvs = ConfigConvert.convertTo(config);
						this.props = kvs;
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
		    	}else{
		    		throw new RuntimeException("Parse file " + file + " extension id " + this.getExtensionId() + " not found");
		    	}
		    }else{
		    	throwException(tagName);
		    }
		}
	}
	
	private InputStream parse(String file){
		return EnvironmentDefine.class.getResourceAsStream(file);
	}
	
	/**
	 * 
	 * @param exId
	 * @return
	 */
	private Config getConfig(String exId) {
		try{
			return factory.getInstanceByXpId(Config.class, Config.X_POINT_ID, exId);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	/**
	 * 静态方法
	 * @param element
	 * @return
	 * @throws IOException 
	 */
	public static EnvironmentDefine build(Element element) throws IOException {
		EnvironmentDefine define = new EnvironmentDefine();
		define.parse(element);
		return define;
	}
}
