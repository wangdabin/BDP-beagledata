package com.joe.monitor.callback.url;

import javax.management.ObjectName;

import org.apache.commons.configuration.Configuration;

import com.joe.core.json.JsonAble;
import com.joe.monitor.msg.MonitorMsg;

/**
 * 抽象的URL地址回调
 * @author qiaolong
 *
 */
public abstract class AbstractURLCallBack extends URLCallBack{

	public AbstractURLCallBack(Configuration conf) {
		super(conf);
	}

	@Override
	protected JsonAble toJson(ObjectName observedObject,
			String observedAttribute, Object derivedGauge, Object trigger,
			Object handBack) {
		MonitorMsg message = new MonitorMsg();
		message.setField(observedAttribute);
		message.setName(observedObject.toString());
		message.setUse(derivedGauge);
		message.setTrigger(trigger);
		return message;
	}
}
