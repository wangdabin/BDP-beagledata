package com.joe.core.i18n;

import org.springframework.stereotype.Service;

import com.joe.core.annotation.I18n;
import com.joe.core.utils.Constants;

/**
 * 异常处理国际化信息
 * @author Joe
 *
 */
@I18n(name = Constants.NAME,type = Constants.TYPE)
@Service("coreI18nMessage")
public class CoreI18nMessage extends I18nMessage{
	
	public static final String BASE_NAME = "i18n.core.message";
	
	/**
	 * 
	 */
	public CoreI18nMessage(){
		super(BASE_NAME);
	}
}
