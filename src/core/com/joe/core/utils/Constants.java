package com.joe.core.utils;

/**
 * 
 * @author Joe
 *
 */
public class Constants {

	public static final String TYPE = "core";
	public static final String NAME = "core";
	
	public static final String NOT_ERROR = "0"; //
	public static final int RET_SUCCESS = 0;
	
	//服务端错误
	public static final int CORE_APPLICATION_ERROR = 503;
	
	//数据库连接错误
	public static final String CORE_DATABASE_CONN_ERROR = "database_conn_error";
	public static final String CORE_DATABASE_DRIVER_NOT_FOUND = "data_driver_not_found";
	
	public static final int CORE_UNKNOW_ERROR = -1;
	public static final String CORE_UNKNOW_ERROR_CODE = "unknow";
	
	//数据库的设置
	public static final String CORE_DATABASE_SETUP_SUCCESS = "core_database_setup_success";
}
