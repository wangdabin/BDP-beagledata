package com.joe.config.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import com.joe.plugin.Pluggable;
import com.joe.plugin.annotation.XPoint;
import com.sky.config.ConfigAble;

/**
 * 配置项插件 用于管理配置文件，不同的配置文件用不同的配置
 * 
 * @author qiaolong
 * 
 */
@XPoint
public interface Config extends Pluggable, ConfigAble {

	public final static String X_POINT_ID = Config.class.getName();

	/**
	 * 得到值
	 * 
	 * @param key
	 * @return
	 */
	String getString(String key);

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String[] getStringArray(String key);

	void setStrings(String key, String... values);

	void setString(String key, String value);

	/**
	 * 
	 * @param key
	 * @param value
	 */
	void setProperty(String key, Object value);

	/**
	 * 
	 * @param key
	 * @return
	 */
	boolean containsKey(String key);

	/**
	 * 删除指定的key
	 * 
	 * @param key
	 */
	void remove(String key);

	/**
	 * 
	 * @return
	 */
	Iterator getKeys();

	Object getProperty(String key);

	/**
	 * 
	 * @param file
	 */
	void load(String file) throws IOException;

	/**
	 * 
	 * @param in
	 */
	void load(InputStream in) throws IOException;

	/**
	 * 
	 * @throws IOException
	 */
	void save() throws IOException;

	/**
	 * 
	 * @param out
	 * @throws IOException
	 */
	void save(OutputStream out) throws IOException;
	
	/**
	 * 得到当前的ExtendsionId
	 * @return
	 */
	String getExtensionId();
}
