package com.joe.core.i18n;


/**
 * 异常处理国际化信息
 * @author Joe
 *
 */
public class ExceptionI18nMessage extends I18nMessage{
	
	public static final String NAME = "exception";
	public static final String TYPE = "exception";
	public static final String BASE_NAME = "i18n.core.exception";
	
	/**
	 * 
	 */
	public ExceptionI18nMessage(){
		super(BASE_NAME);
	}
}
