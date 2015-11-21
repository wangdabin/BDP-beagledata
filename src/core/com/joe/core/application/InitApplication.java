package com.joe.core.application;

import org.apache.log4j.Logger;

import com.joe.core.annotation.InitResource;

/**
 * 
 * 初始化类 resource 直接入口。。
 * @author Joe
 *
 */
public class InitApplication extends AbstractApplication<InitResource>{
	private static final Logger LOG = Logger.getLogger(InitApplication.class);
    
	public InitApplication() throws Exception {
		super(LOG, InitResource.class);
    }
    
	@Override
	protected String getName(InitResource ann) {
		return ann.name();
	}

	@Override
	protected String getType(InitResource ann) {
		return ann.type();
	}

	@Override
	protected boolean isEnable(InitResource ann) {
		return ann.enable();
	}
}
