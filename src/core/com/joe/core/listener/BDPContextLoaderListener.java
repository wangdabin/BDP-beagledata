package com.joe.core.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;

import com.joe.core.database.DatabaseInitHandler;
import com.joe.core.handler.WebDatabaseInitHandler;
import com.joe.core.init.InitAble;
import com.joe.core.init.InitFactory;
import com.joe.core.init.InitHandler;
import com.joe.core.utils.CoreConfigUtils;

/**
 * 判读是否已经初始化，如果已经初始化，则加载配置文件
 * config.properties 配置文件中
 * jdbc.inited=true 表示已经出师化将直接加载配置文件
 * 如果jdbc.inited=false 则需要初始化数据库才能加载
 * @author Joe
 * 
 */
public class BDPContextLoaderListener extends ContextLoaderListener{

	
	private static final Logger LOG = Logger.getLogger(BDPContextLoaderListener.class);
	private ServletContextEvent event;
	private Configuration conf;
	private InitFactory initFactory;
	private List<InitAble> inits;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try{
			this.event = event;
			this.conf = CoreConfigUtils.create();
			this.initFactory = new InitFactory(conf);
			this.inits = initFactory.getAllInstances();
			
			for(InitAble init : inits){
				init.setConf(conf);
			}
			
			if(conf.getBoolean(DatabaseInitHandler.JDBC_INITED)){
				LOG.info("JDBC already inited ,will load spring config");
				super.contextInitialized(event);
				
				LOG.info("Will load init ...");
				for(InitAble init : inits){
					init.setConf(conf);
					init.doInit();
				}
				LOG.info("All init load ...");
			}else{
				LOG.warn("JDBC no inited ,please init jdbc config.");
				InitHandler initHandler =  new WebDatabaseInitHandler(conf,this);
				for(InitAble init : inits){
					initHandler.register(init);
				}
				event.getServletContext().setAttribute(DatabaseInitHandler.DATABASE_INIT_HANDLE,initHandler);
			}
		}catch(Exception e){
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		for(InitAble init : inits){
			init.destroy();
		}
		super.contextDestroyed(event);
	}
	/**
	 * 重新加载配置项
	 */
	public void loadConfig(){
		if(event != null){
			LOG.info("JDBC already inited ,will load spring config");
			super.contextInitialized(event);
		}else{
			throw new RuntimeException("ServletContextEvent is null");
		}
	}
}
