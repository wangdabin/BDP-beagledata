package com.joe.core.i18n;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 国际化标准
 * @author Joe
 *
 */
public abstract class I18nMessage {

	private String baseName;
	private ResourceBundle bundle;
	
	/**
	 * resource bundle baseName
	 * @param baseName
	 */
	public I18nMessage(String baseName){
		this(baseName,Locale.getDefault());
	}
	
	/**
	 * 
	 * @param baseName
	 * @param locale
	 */
	public I18nMessage(String baseName,Locale locale){
		this.baseName = baseName;
		this.bundle = ResourceBundle.getBundle(baseName, locale);
	}
	
	/**
	 * 
	 * @return
	 */
	public ResourceBundle getBundle(){
		return bundle;
	}
	
	/**
	 * @return the baseName
	 */
	public String getBaseName() {
		return baseName;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getMessage(String key){
		return bundle.getString(key);
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String[] getMessages(String key){
		return bundle.getStringArray(key);
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Object getObject(String key){
		return bundle.getObject(key);
	}
	
	/**
	 * 
	 * @return
	 */
	public Enumeration<String> getKeys(){
		return bundle.getKeys();
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key) {
		return bundle.containsKey(key);
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<String> keySet() {
		return bundle.keySet();
	}
	
	/**
	 * 直接对传入的key进行格式化
	 * @param key
	 * @param objs
	 * @return
	 */
	public String format(String key,Object...arguments){
		String msg = getMessage(key);
		return formatPattern(msg, arguments);
	}
	
	/**
	 * 
	 * @param pattern
	 * @param arguments
	 * @return
	 */
	public String formatPattern(String pattern,Object...arguments){
		return MessageFormat.format(pattern, arguments);
	}
}
