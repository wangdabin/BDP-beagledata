package com.joe.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {

	/**
	 * @param url
	 * @param username
	 * @param password
	 * @param driverClassName
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static Connection getConnection(String url,String username,String password,String driverClassName) throws SQLException, ClassNotFoundException {
		Class.forName(driverClassName);
		return DriverManager.getConnection(url, username, password);
	}
}
