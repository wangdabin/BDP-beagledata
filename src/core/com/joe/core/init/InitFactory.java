package com.joe.core.init;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.joe.core.factory.AbstractFactory;
import com.joe.core.init.annotation.Init;

/**
 * 初始化的工厂类
 * @author qiaolong
 *
 */
public class InitFactory extends AbstractFactory<Init, InitAble> {

	public static final Logger LOG = Logger.getLogger(InitFactory.class);
	
	public InitFactory(Configuration conf) {
		super(conf, LOG, Init.class);
	}

	@Override
	protected String getName(Init ann) {
		return ann.name();
	}

	@Override
	protected String getType(Init ann) {
		return ann.type();
	}

	@Override
	protected boolean isEnable(Init ann) {
		return ann.enable();
	}
	
	/**
	 * 
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public List<InitAble> getAllInstances() throws InstantiationException, IllegalAccessException{
		List<InitAble> instances = new ArrayList<InitAble>();
		for(Class<InitAble> clazz : getAll()){
			InitAble t = clazz.newInstance();
			t.setConf(getConf());
			instances.add(t);
		}
		return instances;
	}
}
