package com.sky.config;

import org.apache.commons.configuration.Configuration;

public interface ConfigAble {

	void setConf(Configuration conf);
	
	Configuration getConf();
}
