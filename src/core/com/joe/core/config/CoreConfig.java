package com.joe.core.config;

import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.joe.core.utils.CoreConfigUtils;
import com.sky.config.Configed;

/**
 * 一个配置类的bean。。
 * @author Joe
 *
 */
@Repository(value = CoreConfig.RESOURCE_NAME)
public class CoreConfig extends Configed{

	public static final String RESOURCE_NAME = "coreConfig";
	/**
	 * 
	 * @throws IOException
	 */
	public CoreConfig() throws IOException{
		super(CoreConfigUtils.create());
	}
}
