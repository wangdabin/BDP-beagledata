package com.joe.core.support;

import org.apache.commons.configuration.Configuration;

import com.sky.config.Configed;

public abstract class AbstractCommandWorker extends Configed implements CommandWorker{

	public AbstractCommandWorker(){}
	
	public AbstractCommandWorker(Configuration conf){
		super(conf);
	}
}
