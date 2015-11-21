package com.joe.core.support;

import org.apache.commons.configuration.Configuration;

import com.sky.config.Configed;

public abstract class AbstractDeployWorder extends Configed implements DeployWorker{

	public AbstractDeployWorder(){}
	
	public AbstractDeployWorder(Configuration conf){
		super(conf);
	}
	
}
