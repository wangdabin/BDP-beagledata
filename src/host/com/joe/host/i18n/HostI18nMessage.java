package com.joe.host.i18n;

import org.springframework.stereotype.Service;

import com.joe.core.annotation.I18n;
import com.joe.core.i18n.I18nMessage;
import com.joe.host.utils.HostUtils;

/**
 * 
 * @author Joe
 *
 */
@I18n(name = HostUtils.NAME,type = HostUtils.TYPE)
@Service("hostI18n")
public class HostI18nMessage extends I18nMessage {

	public static final String BASE_NAME = "i18n.host.message";
	
	/**
	 * 
	 */
	public HostI18nMessage() {
		super(BASE_NAME);
	}
}
