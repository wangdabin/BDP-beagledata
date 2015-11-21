package com.sky.service.handler;

import org.apache.commons.configuration.Configuration;

import com.sky.config.Configed;

/**
 * 
 * @author qiaolong
 *
 */
public abstract class AbstractConfigHandler extends Configed implements ConfigHandler{

	public AbstractConfigHandler(){}
	
	public AbstractConfigHandler(Configuration conf){
		super(conf);
	}
}
