package com.joe.user.i18n;

import org.springframework.stereotype.Repository;

import com.joe.core.annotation.I18n;
import com.joe.core.i18n.I18nMessage;
import com.joe.user.utils.UserUtils;

/**
*
* @description
*
* @function
*
* @author ZhouZH
*
*/
@I18n(name = UserUtils.NAME,type = UserUtils.TYPE)
@Repository("userI18n")
public class UserI18nMessage extends I18nMessage {
	
public static final String BASE_NAME = "i18n.user.message";
	
	/**
	 * 
	 */
	public UserI18nMessage() {
		super(BASE_NAME);
	}
}
