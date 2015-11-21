package com.joe.monitor.define;

import org.apache.commons.configuration.Configuration;

import com.sky.config.Configed;

/**
 * 
 * @author qiaolong
 *
 */
public class AbstractMonitorDefine extends Configed implements Define{

	private String observedAttribute; // 需要监控的属性
	private long granularityPeriod; //监控时间
	
	public AbstractMonitorDefine(Configuration conf){
		super(conf);
	}
	
	@Override
	public String getObservedAttribute() {
		return observedAttribute;
	}
	
	public void setObservedAttribute(String observedAttribute) {
		this.observedAttribute = observedAttribute;
	}

	@Override
	public long granularityPeriod() {
		return granularityPeriod;
	}

	public void setGranularityPeriod(long granularityPeriod){
		this.granularityPeriod = granularityPeriod;
	}
}
