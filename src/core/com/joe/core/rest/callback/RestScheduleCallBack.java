package com.joe.core.rest.callback;

import org.apache.log4j.Logger;

import com.joe.core.callback.ScheduleCallBack;
import com.joe.core.version.Name;

/**
 * 
 * @author qiaolong
 *
 */
public class RestScheduleCallBack implements ScheduleCallBack{

	private static final Logger LOG = Logger.getLogger(RestScheduleCallBack.class);
	private String url;
	private Name name;
	/**
	 * 回到进度URL
	 * @param callBackURL
	 */
	public RestScheduleCallBack(Name name,String uri){
		this.name = name;
		this.url = uri;
	}
	
	@Override
	public void start() {
		LOG.info("The name " + name.getName() + ",version " + name.getVersion() + " start");
	}

	@Override
	public void update(String message, double completion) {
		LOG.info("The name " + name.getName() + ",version " + name.getVersion() + " update completion " + completion);
	}

	@Override
	public void finish() {
		LOG.info("The name " + name.getName() + ",version " + name.getVersion() + " finish");		
	}

	@Override
	public void kill() {
		LOG.info("The name " + name.getName() + ",version " + name.getVersion() + " kill");	
	}

}
