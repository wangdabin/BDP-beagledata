package com.joe.core.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author qiaolong
 *
 */
@XmlRootElement
public class Total {

	private String name;
	private long total;
	
	public Total(){}
	
	/**
	 * 
	 * @param name
	 * @param total
	 */
	public Total(String name,long total){
		this.name = name;
		this.total = total;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}
}
