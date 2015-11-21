package com.sky.service.install;

import java.util.Map;

import org.w3c.dom.Element;

/**
 * 用于安装的实体
 * 
 * @author qiaolong
 * 
 */
public interface Entry {
	
	public static final String START_ID = "--STEP_START--"; //开始的ID定义
	public static final String OK_END = "--STEP_END--"; // 安装入口
	
	public static final String TYPE_BASIC = "basic"; // 基本配置
	public static final String TYPE_HOST = "host";// 配置项
	public static final String TYPE_ENVIRONMENT = "environment"; // 环境变量配置
	public static final String TYPE_CONFIG = "config";// 配置项
	public static final String TYPE_SELECT = "select";// 选择配置

	/**
	 * 当前步骤ID
	 * 
	 * @return
	 */
	String getId();

	/**
	 * 步骤名称
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 步骤类型
	 * 
	 * @return
	 */
	String getType();

	/**
	 * ok后的跳转
	 * 
	 * @return
	 */
	String getOK();

	/**
	 * 错误后的跳转
	 * 
	 * @return
	 */
	String getError();
	
	/**
	 * 如果是选择，这个是key
	 * @return
	 */
	String getKey();

	/**
	 * 是步骤
	 * 
	 * @return
	 */
	boolean isStep();
	
	/**
	 * 服务
	 * @return
	 */
	String getService();

	/**
	 * 是组
	 * 
	 * @return
	 */
	boolean isGroup();
	
	/**
	 * 
	 * @return
	 */
	Group getGroup();
	
	/**
	 * 最顶级的组
	 * @return
	 */
	Group getTopGroup();
	
	/**
	 * 
	 * @return
	 */
	boolean hasGroup();
	
	/**
	 * 
	 * @param entrys
	 * @param element
	 */
	void parse(Map<String,Entry> entrys,Element element);
}

