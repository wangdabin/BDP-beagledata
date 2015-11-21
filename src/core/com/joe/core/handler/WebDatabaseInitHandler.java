package com.joe.core.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.joe.core.database.DatabaseInitHandler;
import com.joe.core.init.InitAble;
import com.joe.core.listener.BDPContextLoaderListener;
import com.sky.config.Configed;

/**
 * Database设置成功，加载配置文件
 * @author Joe
 *
 */
public class WebDatabaseInitHandler extends Configed implements DatabaseInitHandler{

	public static final Logger LOG = Logger.getLogger(WebDatabaseInitHandler.class);
	private BDPContextLoaderListener contextLoader;
	private List<InitAble> inits = new ArrayList<InitAble>();
	
	public WebDatabaseInitHandler(Configuration conf,BDPContextLoaderListener contextLoader){
		super(conf);
		this.contextLoader = contextLoader;
	}
	
	public void doInit() {
		contextLoader.loadConfig();
		LOG.info("Will load init ...");
		this.initAll();
		LOG.info("All init loaded ...");
	}

	@Override
	public void register(InitAble init) {
		this.inits.add(init);
	}
	
	private void initAll(){
		for(InitAble init : inits){
			init.doInit();
		}
	}
}
