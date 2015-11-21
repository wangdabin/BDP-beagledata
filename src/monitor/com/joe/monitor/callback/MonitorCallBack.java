package com.joe.monitor.callback;

import javax.management.Notification;
import javax.management.ObjectName;
import javax.management.monitor.MonitorNotification;

import org.apache.commons.configuration.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.joe.monitor.message.service.BDPMessageService;
import com.joe.monitor.message.type.Level;
import com.joe.monitor.message.type.Type;
import com.joe.monitor.vo.BDPMessage;
import com.sky.config.Configed;

/**
 * 监控回调
 * @author qiaolong
 *
 */
public abstract class MonitorCallBack extends Configed implements NotificationCallBack{

	
	private static ApplicationContext context =
	    	new ClassPathXmlApplicationContext("beans.xml");
	public MonitorCallBack(Configuration conf){
		super(conf);
	}
	
	@Override
	public void call(Notification notification, Object handBack) {
		this.handleNotification((MonitorNotification) notification, handBack);
	}

	/**
	 * 
	 * @param notification
	 * @param handBack
	 */
	protected void handleNotification(MonitorNotification notification,Object handBack){
		ObjectName observedObject = notification.getObservedObject();
		String observedAttribute = notification.getObservedAttribute();
		Object derivedGauge = notification.getDerivedGauge();
		Object trigger = notification.getTrigger();
		//1.在回调的时候首先将对应的监控信息保存到对应的数据库表中
		BDPMessage message = new BDPMessage();
		message.setName(observedObject.getKeyProperty("name"));
		message.setType(Type.host.toString());
		message.setLevel(Level.info.toString());
		message.setDescription(observedAttribute + ":" + derivedGauge);
		message.setReaded(false);
		message.setCreateTime(System.currentTimeMillis());
		BDPMessageService bdpMessageService = context.getBean(BDPMessageService.class);
		bdpMessageService.add(message);
		this.handleNotification(observedObject, observedAttribute, derivedGauge, trigger, handBack);
	}
	
	/**
	 * 
	 * @param observedObject 抛出ObjectName
	 * @param observedAttribute 抛出属性
	 * @param derivedGauge 当前值
	 * @param trigger 设定值
	 * @param handBack 
	 */
	protected abstract void handleNotification(ObjectName observedObject,String observedAttribute,Object derivedGauge,Object trigger,Object handBack);
}
