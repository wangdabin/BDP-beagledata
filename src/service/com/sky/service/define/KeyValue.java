package com.sky.service.define;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author qiaolong
 *
 */
//@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true,value={"key","uniqueKey"})
public class KeyValue extends AbstractDefine implements Serializable{

	public static enum TYPE{
		text,
		password,
		checkbox,
		radio,
		file,
		hidden,
		host
	}
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private String name; //字段名称
	private String configKey;//配置项key值
	private List<String> values = new ArrayList<String>();//字段值
	private String description; //描述
	private TYPE type = TYPE.text;

	public KeyValue(){}
	
	public KeyValue(String configKey){
		this.configKey = configKey;
	}
	
	public KeyValue(String configKey,String value){
		this.configKey = configKey;
		this.addValue(value);
	}
	
	public KeyValue(String configKey,String... values){
		this.configKey = configKey;
		this.addValues(values);
	}
	
	public KeyValue(String name, String configKey, List<String> values,
			String description) {
		super();
		this.name = name;
		this.configKey = configKey;
		this.values = values;
		this.description = description;
	}

	public String getName() {
		if(StringUtils.isBlank(name)){
			return getConfigKey();
		}else{
			return name;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public List<String> getValues() {
		return values;
	}
	
	/**
	 * 
	 * @param value
	 */
	public void addValue(String value){
		this.values.add(value);
	}
	
	/**
	 * 
	 * @param value
	 */
	public void addValues(String...values){
		this.values.addAll(Arrays.asList(values));
	}
	
	/**
	 * 
	 * @param values
	 */
	public void addValues(Collection<String> values){
		this.values.addAll(values);
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}
	
	@Override
	public void parse(Element keyvalueNode) {
		this.parseName(keyvalueNode);
		String key = keyvalueNode.getAttribute("key");
		this.setConfigKey(key);
		NodeList nodes = keyvalueNode.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
		    if (!(node instanceof Element)) {
		    	continue;
		    }
		    Element element = (Element) node;
		    String tagName = element.getTagName();
		    String value = element.getTextContent();
		    if("value".equalsIgnoreCase(tagName)){
		    	this.addValues(this.parseValue(value));
		    }else if("description".equalsIgnoreCase(tagName)){
		    	this.setDescription(value);
		    }else{
		    	throwException(tagName);
		    }
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((configKey == null) ? 0 : configKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeyValue other = (KeyValue) obj;
		if (configKey == null) {
			if (other.configKey != null)
				return false;
		} else if (!configKey.equals(other.configKey))
			return false;
		return true;
	}
}
