package com.joe.monitor.callback.url;

import java.util.List;

import javax.management.ObjectName;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.joe.core.json.JsonAble;
import com.joe.monitor.callback.MonitorCallBack;

/**
 * 
 * @author qiaolong
 *
 */
public abstract class URLCallBack extends MonitorCallBack{

	public static final Logger LOG = Logger.getLogger(URLCallBack.class);

	private URLCallBackClient client;
	public URLCallBack(Configuration conf){
		super(conf);
		client = new URLCallBackClient(conf);
	}
	
	@Override
	protected void handleNotification(ObjectName observedObject,
			String observedAttribute, Object derivedGauge, Object trigger,
			Object handBack) {
		List<String> urls = getURLManager().get(observedObject, observedAttribute); //TODO
		for(String url : urls){
			client.doCallBack(url, observedObject.toString(), this.toJson(observedObject, observedAttribute, derivedGauge, trigger, handBack));
		}
	}
	
	protected URLManager getURLManager(){
		return new MapURLManager();
	}
	
	/**
	 * 转换成json
	 * @param observedObject
	 * @param observedAttribute
	 * @param derivedGauge
	 * @param trigger
	 * @param handBack
	 * @return
	 */
	protected abstract JsonAble toJson(ObjectName observedObject,
			String observedAttribute, Object derivedGauge, Object trigger,
			Object handBack);
}
