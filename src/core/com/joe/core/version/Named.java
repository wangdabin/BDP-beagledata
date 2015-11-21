package com.joe.core.version;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * @author qiaolong
 *
 */
@XmlRootElement
public class Named implements Name {

	private String name;
	private String version;

	public Named(){}
	
	public Named(String name, String version) {
		super();
		this.name = name;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String getUniqueKey() {
		return getUniqueKey(NAME_VERSION_SPLIT);
	}

	@Override
	public String getUniqueKey(String split) {
		return getKey(getName(),getVersion(),split);
	}
	
	/**
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	public static final String getKey(String name,String version){
		return getKey(name, version, NAME_VERSION_SPLIT);
	}
	
	/**
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	public static final String getKey(String name,String version,String split){
		return name + split + (StringUtils.isBlank(version) ? "" : version);
	}
}
