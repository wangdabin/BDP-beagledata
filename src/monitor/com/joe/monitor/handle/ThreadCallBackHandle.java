package com.joe.monitor.handle;

import javax.management.Notification;

import org.apache.commons.configuration.Configuration;

import com.joe.core.utils.ExecutorService;
import com.joe.monitor.callback.NotificationCallBack;

/**
 * 
 * @author qiaolong
 *
 */
public class ThreadCallBackHandle extends ExecutorService implements CallBackHandle{
	
	private NotificationCallBack[] callBacks;
	private Configuration conf;
	
	@Override
	public CallBackHandle handle(NotificationCallBack[] callBacks) {
		this.callBacks = callBacks;
		return this;
	}

	@Override
	public void call(Notification notification, Object handBack) {
		getExecutorService().execute(new CallBackRunnable(callBacks, notification, handBack));
	}
	
	class CallBackRunnable implements Runnable{
		
		NotificationCallBack[] callBacks;
		Notification notification;
		Object handBack;
		
		CallBackRunnable(NotificationCallBack[] callBacks,Notification notification, Object handBack){
			this.callBacks = callBacks;
			this.notification = notification;
			this.handBack = handBack;
		}
		@Override
		public void run() {
			for(NotificationCallBack callBack : callBacks){
				callBack.setConf(getConf());
				callBack.call(notification, callBack);
			}
		}
	}

	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	@Override
	public Configuration getConf() {
		return conf;
	}
}
