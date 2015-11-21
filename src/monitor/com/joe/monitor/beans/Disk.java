package com.joe.monitor.beans;

/**
 * disk使用情况监控
 * 
 * @author qiaolong
 *
 */
public class Disk implements DiskMBean {

	private float use;
	
	public Disk(float use) {
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
