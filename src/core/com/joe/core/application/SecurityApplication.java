package com.joe.core.application;

import org.apache.log4j.Logger;

import com.joe.core.annotation.SecurityResource;


/**
 * 
 * 认证的应用入口
 * @author Joe
 *
 */
public class SecurityApplication extends AbstractApplication<SecurityResource>{
	private static final Logger LOG = Logger.getLogger(SecurityApplication.class);
    
	public SecurityApplication() throws Exception {
		super(LOG, SecurityResource.class);
    }

	@Override
	protected String getName(SecurityResource ann) {
		return ann.name();
	}

	@Override
	protected String getType(SecurityResource ann) {
		return ann.type();
	}

	@Override
	protected boolean isEnable(SecurityResource ann) {
		return ann.enable();
	}
}
