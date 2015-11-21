package com.joe.core.application;

import java.util.Set;

import org.apache.log4j.Logger;

import com.joe.core.annotation.RestResource;

/**
 * 
 * 程序的入口
 * 过滤所有的 /ws/* 路径
 * @author Joe
 *
 */
public class BDPApplication extends AbstractApplication<RestResource>{
	private static final Logger LOG = Logger.getLogger(BDPApplication.class);
    
	public BDPApplication() throws Exception {
		super(LOG, RestResource.class);
    }
    
    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

	@Override
	protected String getName(RestResource ann) {
		return ann.name();
	}

	@Override
	protected String getType(RestResource ann) {
		return ann.type();
	}

	@Override
	protected boolean isEnable(RestResource ann) {
		return ann.enable();
	}
}
