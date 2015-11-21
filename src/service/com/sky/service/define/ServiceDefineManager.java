package com.sky.service.define;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.joe.core.utils.DomUtils;
import com.joe.core.version.Name;
import com.joe.core.version.Named;

/**
 * 
 * @author qiaolong
 * 
 */
public class ServiceDefineManager {
	private static final Logger LOG = Logger.getLogger(ServiceDefineManager.class);
	private static final Set<String> resources = new HashSet<String>();
	private static final Map<String, ServiceDefine> defines = new HashMap<String, ServiceDefine>();
	public static final String CONF_SERVICES = "services";
	public static final String CONF_SERVICE = "service";
	
	/**
	 * 
	 * @param resource
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static final void addResource(String resource) throws IOException{
		if(!resources.contains(resource)){
			try{
				parse(resource);
			}catch(Exception e){
				throw new IOException(e);
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static final Set<String> getResources(){
		return resources;
	}
	
	/**
	 * 
	 * @return
	 */
	public static final Collection<ServiceDefine> getAll(){
		return defines.values();
	}
	
	/**
	 * 根据name 和 version 得到服务定义
	 * @param name
	 * @param version
	 * @return
	 */
	public static final ServiceDefine getServiceDefine(String name,String version){
		return defines.get(Named.getKey(name, version));
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static final ServiceDefine getServiceDefine(Name name){
		return defines.get(name.getUniqueKey());
	}
	
	/**
	 * 初始化
	 * @param resource
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private static final void parse(String resource) throws SAXException,
			IOException, ParserConfigurationException {
		Document doc = DomUtils.parse(resource);
		Element root = doc.getDocumentElement();
		if (CONF_SERVICES.equals(root.getTagName())) {
			LOG.warn("bad conf file : top-level element not <services>");
			NodeList nodes = root.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (!(node instanceof Element)) {
					continue;
				}
				Element element = (Element) node;
				parseService(element);
			}
		}else if(CONF_SERVICE.equals(root.getTagName())){
			parseService(root);
		}
	}
	
	private static final void parseService(Element serviceNode){
		if (CONF_SERVICE.equals(serviceNode.getTagName())) {
			ServiceDefine define = ServiceDefine.build(serviceNode);
			if(define != null){
				defines.put(define.getUniqueKey(), define);
			}
		}
	}
//	
//	public static void main(String[] args) throws IOException {
//		ServiceDefineManager.addResource(HadoopService.DEFINE_RESOURCE);
//		ServiceDefine define = ServiceDefineManager.getServiceDefine("hadoop","cdh5.3.0");
//		System.out.println(define.getName());
//	}
}
