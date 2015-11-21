package com.sky.config;

import org.apache.commons.configuration.Configuration;

/**
 * 
 * @author Joe
 *
 */
public class Configed implements ConfigAble{

	private Configuration conf;

	
	protected Configed(){
	}
	
	protected Configed(Configuration conf){
		this.setConf(conf);
	}
	/**
	 * @return the conf
	 */
	public Configuration getConf() {
		return conf;
	}

	/**
	 * @param conf the conf to set
	 */
	public void setConf(Configuration conf) {
		this.conf = conf;
	}
}
