package com.joe.core.database;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.configuration.Configuration;

import com.sky.config.ConfigUtil;

/**
 * 
 * @author Joe
 *
 */
public class DialectUtils {
	
	public static final String DIALECTRESOURCE = "dialect.properties";
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private static final Configuration getDialectConfig(){
		try{
			return ConfigUtil.createConfig(DIALECTRESOURCE);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	private static final Map<String,Dialect> loadDialects(){
		Map<String,Dialect> dialects = new HashMap<String, Dialect>();
		Configuration pro = getDialectConfig();
		Iterator<String> iter = pro.getKeys();
		while(iter.hasNext()){
			String key = iter.next();
			String vs[] = key.split("\\.");
			String name = vs[0];
			String type = vs[1];
			String value = pro.getString(key);
			Dialect dialect = dialects.get(name);
			if(dialect == null){
				dialect = new Dialect();
				dialect.setName(name);
			}
			if(type.equals("dialect")){
				dialect.setDialect(value);
			}else if(type.equals("driver")){
				dialect.setDriver(value);
			}else if(type.equals("url")){
				dialect.setUrl(value);
			}
			dialects.put(name, dialect);
		}
		return dialects;
	}
	
	public static final Dialect getDialect(String name){
		Map<String,Dialect> dialects = loadDialects();
		return dialects.get(name.toUpperCase());
	}
	
	public static final Collection<Dialect> getDialects(){
		Map<String,Dialect> dialects = loadDialects();
		return dialects.values();
	}
	
	/**
	 * 
	 * 根据方言返回name 
	 * 如输入 org.hibernate.dialect.MySQLDialect 返回 mysql
	 * @param dialect
	 * @return
	 * @throws IOException
	 */
	public static final String getDialectName(String dialect){
		Map<String,Dialect> pro = loadDialects();
		for(Entry<String,Dialect> entry : pro.entrySet()){
			if(entry.getValue().getDialect().equals(dialect)){
				return entry.getKey();
			}
		}
		throw new RuntimeException("Not found dialect " + dialect);
	}
	
//	/**
//	 * 
//	 * 根据name返回方言
//	 * 如输入 MySQL 返回 org.hibernate.dialect.MySQLDialect
//	 * @param name
//	 * @return
//	 */
//	public static final String getDialect(String name){
//		Map<String,Dialect> pro = loadDialects();
//		Dialect dialect = pro.get(name.toUpperCase());
//		return dialect.getDialect();
//	}
}
