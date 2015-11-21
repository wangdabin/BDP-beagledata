package com.sky.service.define;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.joe.core.utils.DomUtils;
import com.joe.core.version.Named;

/**
 * 
 * @author qiaolong
 *
 */
public abstract class AbstractDefine extends Named implements Define{
	private static final Logger LOG = Logger.getLogger(AbstractDefine.class);
	/**
	 * 
	 * @param node
	 */
	protected void parseName(Element node){
		String name = node.getAttribute("name");
		String version = node.getAttribute("version");
		if(StringUtils.isBlank(name)){
			throw new RuntimeException("Parse service name is null");
		}
		this.setName(name);
		this.setVersion(version);
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	protected List<String> parseValue(String value){
		if(!StringUtils.isBlank(value)){
			return Arrays.asList(value.split(","));
		}
		return new ArrayList<String>();
	}
	
	/**
	 * 
	 * @param element
	 * @param list
	 * @param define
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	protected <T extends Define> void parseChild(Element element,Class<? extends Define> clazz,List<T> list){
		try{
			NodeList children = element.getChildNodes();
	    	if(children != null && children.getLength() > 0){
	    		for (int j = 0; j < children.getLength(); j++) {
	    		    Node childrenNode = children.item(j);
	    		    if (!(childrenNode instanceof Element)) {
	    		    	continue;
	    		    }
	    		   
	    		    T t = (T) clazz.newInstance();
	    		    Element childrenElement = (Element) childrenNode;
	    		    String tagName = childrenElement.getTagName();
	    		    if("import".equalsIgnoreCase(tagName)){ // 引入其他配置项
	    		    	String file = childrenElement.getAttribute("file");
	    		    	LOG.debug("File = " + file);
	    		    	Document doc = DomUtils.parse(file);
	    		    	Element root = doc.getDocumentElement();
	    		    	t.parse(root);
	    		    }else{
	    		    	t.parse(childrenElement);
	    		    }
	    		    list.add(t);
	    		}
	    	}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param nodeName
	 * @throws RuntimeException
	 */
	protected void throwException(String nodeName)throws RuntimeException{
		throw new RuntimeException("Node name " + nodeName + " unsupport");
	}
}
