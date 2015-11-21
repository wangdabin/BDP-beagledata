package com.joe.monitor.listener;

import java.util.ArrayList;
import java.util.List;

import javax.management.Notification;
import javax.management.monitor.GaugeMonitor;
import javax.management.monitor.MonitorNotification;

import org.apache.commons.configuration.Configuration;

import com.joe.monitor.callback.NotificationCallBack;
import com.joe.monitor.handle.CallBackHandle;
import com.joe.monitor.handle.ThreadCallBackHandle;
import com.sky.config.Configed;

/**
 * 
 * 定义监控的监听器
 * @author qiaolong
 *
 */
public abstract class AbstractListener extends Configed implements MonitorListener{

	private List<NotificationCallBack> callBacks = new ArrayList<NotificationCallBack>();
	
	public AbstractListener(Configuration conf){
		super(conf);
	}
	
	@Override
	public void handleNotification(Notification notification,
			Object handBack) {
		System.out.println("----------------->>>>>");
		if (notification.getType().endsWith("high")) {
			System.out.println("接入客户端超出门限值");
			CallBackHandle handle = getCallBackHandle().handle(callBacks.toArray(new NotificationCallBack[callBacks.size()]));
			handle.call(notification, handBack);
		}
		else if (notification.getType().endsWith("low")) {
			System.out.println("接入客户端低于门限值");
			CallBackHandle handle = getCallBackHandle().handle(callBacks.toArray(new NotificationCallBack[callBacks.size()]));
			handle.call(notification, handBack);
		}
//        MonitorNotification n = (MonitorNotification)notification;
//        GaugeMonitor monitor = (GaugeMonitor)n.getSource();
//        if(MonitorNotification.THRESHOLD_VALUE_EXCEEDED.equals(n.getType())){
//            System.out.println("Count类-"+monitor.getObservedAttribute()+"属性:"+n.getDerivedGauge()
//                    +">=了设定的阈值:"+n.getTrigger()+";下个阈值：");
//        }else{
//            System.out.println(n.getType()+"--"+n);
//        }
		
		
		
	}

	/**
	 * 注册对调
	 * @param callBack
	 */
	public void register(NotificationCallBack callBack){
		callBacks.add(callBack);
	}

	/**
	 * 回调句柄
	 * @return
	 */
	protected CallBackHandle getCallBackHandle(){
		return new ThreadCallBackHandle();
	}
}