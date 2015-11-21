package com.joe.core.database;

import com.joe.core.init.InitHandler;

/**
 * 
 * database init 初始化成功
 * @author Joe
 *
 */
public interface DatabaseInitHandler extends InitHandler{

	//是否已经初始化jdbc的配置
	public static final String JDBC_INITED = "sky.resource.inited.jdbc";
	//servletcontext中存储的
	public static final String DATABASE_INIT_HANDLE = "sky.databaase.init.handle"; 
}
