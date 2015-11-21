package com.joe.core.support;

import org.apache.commons.configuration.Configuration;

import com.sky.config.Configed;

/**
 * 检查器
 * @author qiaolong
 *
 */
public abstract class AbstractChecker extends Configed implements Checker{

	public AbstractChecker(){}
	
	public AbstractChecker(Configuration conf){
		super(conf);
	}
}
