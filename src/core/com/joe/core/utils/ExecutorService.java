package com.joe.core.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 
 * @author qiaolong
 *
 */
public class ExecutorService {

	public static final int DEF_THREAD_POOL = 20;
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(DEF_THREAD_POOL);
	
	public static final ScheduledExecutorService getExecutorService(){
		return scheduler;
	}
}
