package com.joe.core.init;

import org.apache.commons.configuration.Configuration;

import com.sky.config.Configed;

/**
 * 
 * @author qiaolong
 *
 */
public abstract class AbstractInitAble extends Configed implements InitAble{

	public AbstractInitAble(){}
	
	public AbstractInitAble(Configuration conf){
		super(conf);
	}
}
