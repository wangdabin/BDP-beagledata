package com.joe.core.support;

import org.apache.commons.configuration.Configuration;
import com.sky.config.Configed;

public abstract class AbstractConfigApplyer extends Configed implements ConfigApplyer{

	public AbstractConfigApplyer(){
	}
	
	public AbstractConfigApplyer(Configuration conf){
		super(conf);
	}
}
