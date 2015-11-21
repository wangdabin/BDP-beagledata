package com.joe.core.vo;

import java.io.Serializable;


/**
 * 
 * 实体类，是主机、系统、服务、应用、监控的基类
 * @author Joe
 *
 */
public interface Entity extends Serializable{

	/**
	 * 
	 * @return
	 */
	int getId();
	
	/**
	 * 
	 * @param id
	 */
	void setId(int id);
	
	/**
	 * 名称
	 * @return
	 */
	String getName();
	
	/**
	 * 名称
	 * @param name
	 */
	void setName(String name);
	
	/**
	 * 描述
	 * @return
	 */
	String getDesc();
	
	/**
	 * 描述
	 * @param desc
	 */
	void setDesc(String desc);
	
}
