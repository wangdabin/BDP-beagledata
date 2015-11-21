package com.joe.monitor.beans;

/**
 * cpu使用情况监控
 * 
 * @author qiaolong
 *
 */
public class Cpu implements CpuMBean {

	float use;
	
	public Cpu(float use) {
		this.use = use;
	}

	public void setUse(float use) {
		this.use = use;
	}

	@Override
	public float getUse() {
		return use;
	}
}
