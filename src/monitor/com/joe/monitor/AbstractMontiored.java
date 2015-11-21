package com.joe.monitor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;

import com.sky.config.Configed;

/**
 * 抽象监控类
 * @author qiaolong
 *
 */
public abstract class AbstractMontiored extends Configed implements Monitored{

	private List<Monitor> monitors = new ArrayList<Monitor>();

	public AbstractMontiored(Configuration conf) {
		super(conf);
	}

	@Override
	public void addMontior(Monitor montior) {
		this.monitors.add(montior);
	}

	@Override
	public void removeMontior(Monitor montior) {
		this.monitors.remove(montior);
	}

	@Override
	public List<Monitor> getMontiors() {
		return monitors;
	}
}
